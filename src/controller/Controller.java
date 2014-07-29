package controller;

import gui.ConnectView;
import gui.GuiHelper;
import gui.LoginView;
import gui.MainView;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import messages.GraphicsMessage;
import messages.Message;
import messages.MessageFactory;
import network.Sendable;
import network.client.Client;
import network.exceptions.AuthErrorException;
import network.exceptions.ConnectionErrorException;
import network.server.Server;
import commands.CommandFactory;
import commands.LeaveCommand;

/**
 * Controller singleton class.
 * @author ok
 *
 */
public class Controller implements Observer {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static Controller instance = null;
	private final Server server = new Server();
	private final Client client = new Client();
	private LoginView loginView;
	private ConnectView connectView;
	private MainView mainView;

	// Constructors.
	// -------------------------------------------------------------------------
	private Controller() {
		client.addObserver(this);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				loginView = new LoginView();
				connectView = new ConnectView();
				mainView = new MainView();
				loginView.setVisible(true);
			}
		});
	}

	// Class methods.
	// -------------------------------------------------------------------------
	public static Controller getInstance() {
		if(instance == null)
			instance = new Controller();
		return instance;
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	@Override
	public void update(final Observable obs, final Object obj) {
		if(obj instanceof Message<?>) {
			final Message<?> msg = (Message<?>) obj;
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					mainView.addMessagePane(msg.getRoomName());
					mainView.getMessagePane(msg.getRoomName()).write(msg);
				}
			});
		} else if(obj instanceof ConnectionErrorException) {
			GuiHelper.showErrorMessage("Connection to server lost");
			System.exit(0);
		}
	}

	private void viewsVisibility(final boolean loginVisible,
			final boolean connectVisible, final boolean mainVisible) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				loginView.setVisible(loginVisible);
				connectView.setVisible(connectVisible);
				mainView.setVisible(mainVisible);
			}
		});
	}

	private void showConnectErrorDialog(final String msg) {
		viewsVisibility(false, false, false);
		GuiHelper.showErrorMessage(msg);
		viewsVisibility(true, false, false);
	}

	private void login() {
		if(loginView.getStartServer() && !server.isStarted()) {
			try {
				server.start(loginView.getServerPort());
			} catch (IOException e) {
				showConnectErrorDialog("Unable to start server at port "
						+ loginView.getServerPort());
				return;
			}
		}

		try {
			client.connect(loginView.getServerAddress(),
					loginView.getServerPort(), loginView.getUser());
		} catch (UnknownHostException e) {
			showConnectErrorDialog("Unknown host: "
					+ loginView.getServerAddress());
			return;
		} catch (IOException e) {
			showConnectErrorDialog(String.format("Unable to connect to %s:%d",
					loginView.getServerAddress(), loginView.getServerPort()));
			return;
		} catch (AuthErrorException e) {
			showConnectErrorDialog(String.format("User with nickname \"%s\""
					+ "is already connected", loginView.getUser()));
			return;
		}

		client.send(CommandFactory.createCommand(client.getID(),
				client.getUser(), null, "/join default"));
		viewsVisibility(false, false, true);
	}

	/**
	 * Login.
	 * <p>
	 * <b>Note:</b> This method should be run in the event dispatch thread.
	 * </p>
	 */
	public void loginAction() {
		loginView.setVisible(false);
		connectView.setVisible(true);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				login();
			}
		});
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Send message.
	 * <p>
	 * <b>Note:</b> This method should be run in the event dispatch thread.
	 * </p>
	 */
	public void sendMessageAction() {
		String s = mainView.getInputFieldText();
		if(s.equals(""))
			return;

		Sendable msg;
		if(s.startsWith("/"))
			msg = CommandFactory.createCommand( client.getID(),
					client.getUser(), mainView.getCurrentRoomName(), s);
		else
			msg = MessageFactory.createMessage(client.getID(), client.getUser(),
					mainView.getCurrentRoomName(), s);

		if(msg instanceof LeaveCommand)
			mainView.removeMessagePane(msg.getRoomName());

		client.send(msg);
	}

	/**
	 * Show send image dialog.
	 * <p>
	 * <b>Note:</b> This method should be run in the event dispatch thread.
	 * </p>
	 */
	public void sendImageAction() {
		String file = GuiHelper.showFileChooser(
				mainView,
				JFileChooser.OPEN_DIALOG,
				new FileNameExtensionFilter("Images",
						ImageIO.getReaderFileSuffixes()),
				null
		);

		if(file != null) {
			client.send(MessageFactory.createMessage(
					client.getID(),
					client.getUser(),
					mainView.getCurrentRoomName(),
					"!" + file
			));
		}
	}

	/**
	 * Show save image dialog.
	 * <p>
	 * <b>Note:</b> This method should be run in the event dispatch thread.
	 * </p>
	 * @param msg Message to save
	 */
	public void saveImageAction(final GraphicsMessage msg) {
		String file = GuiHelper.showFileChooser(
				mainView,
				JFileChooser.SAVE_DIALOG,
				null,
				new File(msg.getFileName())
		);

		if(file != null) {
			try {
				msg.save(new File(file));
			} catch (IOException e) {
				GuiHelper.showErrorMessage("Unable to save image");
			}
		}
	}

	/**
	 * Exit the app.
	 * <p>
	 * <b>Note:</b> This method should be run in the event dispatch thread.
	 * </p>
	 */
	public void exitAction() {
		client.disconnect();
		try {
			server.stop();
		} catch (IOException e) {
			System.err.println("An error occured while stopping the server");
		}
		System.exit(0);
	}
}
