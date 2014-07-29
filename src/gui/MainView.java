package gui;

import gui.components.Menu;
import gui.components.ScrollableMessagePane;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import controller.Controller;

/**
 * Main window.
 * @author ok
 *
 */
public class MainView extends JFrame {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;
	private final Menu menu = new Menu(this);
	private final JTabbedPane tabbedPane = new JTabbedPane();
	private final JTextField inputField = new JTextField();

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new MainView.
	 */
	public MainView() {
		setTitle("JChat");
		setSize(400, 500);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon("icons/jchat.png").getImage());
	    setDefaultCloseOperation(EXIT_ON_CLOSE);

	    initMenu();

	    inputField.setPreferredSize(new Dimension(400, 16));
	    inputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().sendMessageAction();
				inputField.setText("");
			}
	    });

	    ImageIcon icon = new ImageIcon("icons/insert-image.png");
	    JButton pictureButton = new JButton(icon);
	    pictureButton.setToolTipText("Send image...");
	    pictureButton.setPreferredSize(new Dimension(16, 16));
	    pictureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().sendImageAction();
			}
		});

	    JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
	    		inputField, pictureButton);
	    bottomSplitPane.setResizeWeight(1);
	    bottomSplitPane.setEnabled(false);
	    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
	    		tabbedPane, bottomSplitPane);
	    splitPane.setResizeWeight(1);
	    splitPane.setEnabled(false);

	    add(splitPane);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	private void initMenu() {
		menu.addItem("JChat", "Exit", "Exit application", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().exitAction();
			}
	    });
		menu.addItem("Commands", "Block user...", "Block user in this room",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputField.setText("/block USER_NAME BOOLEAN");
				inputField.requestFocus();
				inputField.select(
						inputField.getText().split("\\s")[0].length() + 1,
						inputField.getText().length()
				);
			}
	    });
		menu.addItem("Commands", "Join room...", "Join room",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputField.setText("/join ROOM_NAME");
				inputField.requestFocus();
				inputField.select(
						inputField.getText().split("\\s")[0].length() + 1,
						inputField.getText().length()
				);
			}
	    });
		menu.addItem("Commands", "Leave room", "Leave current room",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputField.setText("/leave");
				inputField.requestFocus();
			}
	    });
		menu.addItem("Commands", "List rooms", "List all rooms",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputField.setText("/rooms");
				inputField.requestFocus();
			}
	    });
		menu.addItem("Commands", "List users", "List all users in current room",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputField.setText("/users");
				inputField.requestFocus();
			}
	    });
		menu.addItem("Commands", "Show server info", "Show server info",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputField.setText("/stats");
				inputField.requestFocus();
			}
	    });
		menu.addGlue();
		menu.addItem("Help", "About...", "Show About dialog",
				new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAboutDialog();
			}
	    });
	}

	public String getInputFieldText() {
		return inputField.getText();
	}

	public String getCurrentRoomName() {
		int selectedIndex = tabbedPane.getSelectedIndex();
		return (selectedIndex >= 0)
				? tabbedPane.getTitleAt(selectedIndex).substring(1)
				: null;
	}

	/**
	 * Add new MessagePane tab.
	 * @param title Title of the tab.
	 */
	public void addMessagePane(final String title) {
		for(int i = 0; i < tabbedPane.getTabCount(); i++)
			if(tabbedPane.getTitleAt(i).equals("#" + title))
				return;
		tabbedPane.add("#" + title, new ScrollableMessagePane());
		tabbedPane.setSelectedComponent(getMessagePane(title));
	}

	/**
	 * Remove MessagePane tab.
	 * @param title Title of the tab
	 */
	public void removeMessagePane(final String title) {
		for(int i = 0; i < tabbedPane.getTabCount(); i++) {
			if(tabbedPane.getTitleAt(i).equals("#" + title)) {
				tabbedPane.remove(i);
				break;
			}
		}
	}

	/**
	 * Get the MessagePane with the given tab title
	 * @param title Title of the tab
	 * @return MessagePane with the given tab title
	 */
	public ScrollableMessagePane getMessagePane(final String title) {
		for(int i = 0; i < tabbedPane.getTabCount(); i++)
			if(tabbedPane.getTitleAt(i).equals("#" + title))
				return (ScrollableMessagePane) tabbedPane.getComponentAt(i);
		return null;
	}

	/**
	 * Show About dialog.
	 */
	public static void showAboutDialog() {
		JOptionPane.showMessageDialog(null,
				"<html><center><b><span style='font-size:15px'>JChat</span>"
				+ "</b><br><i>Copyright &copy; 2014 Ondrej Kipila</i>"
				+ "</center></html>",
				"About",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon("icons/jchat.png")
		);
	}

	@Override
	public void setVisible(final boolean isVisible) {
		super.setVisible(isVisible);
		if(isVisible)
			inputField.requestFocus();
	}
}
