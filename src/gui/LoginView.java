package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import gui.components.IntegerField;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.Controller;

/**
 * Login dialog.
 * @author ok
 *
 */
public class LoginView {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private final JCheckBox startServerCheckbox = new JCheckBox();
	private final JTextField serverAddressField = new JTextField();
	private final IntegerField serverPortField = new IntegerField();
	private final JTextField userField = new JTextField();
	private final JDialog dialog;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new LoginView.
	 */
	public LoginView() {
		final JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().loginAction();
			}
		});

		final JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().exitAction();
			}
		});

		class Listener implements ItemListener, DocumentListener {
			@Override
			public void itemStateChanged(ItemEvent e) {
				update();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				update();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update();
			}

			private void update() {
				serverAddressField.setEnabled(!startServerCheckbox
						.isSelected());
				connectButton.setEnabled(userField.getText().length() > 0
						&& serverPortField.getText().length() > 0
						&& serverPortField.getNumber() <= 65535
						&& (startServerCheckbox.isSelected()
								|| serverAddressField.getText().length() > 0)
				);
			}
		}

		Listener inputCheck = new Listener();
		startServerCheckbox.addItemListener(inputCheck);
		serverAddressField.getDocument().addDocumentListener(inputCheck);
		serverPortField.getDocument().addDocumentListener(inputCheck);
		userField.getDocument().addDocumentListener(inputCheck);
		connectButton.setEnabled(false);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(4, 2));
		p.add(new JLabel("Start server"));
		p.add(startServerCheckbox);
		p.add(new JLabel("Server:"));
		p.add(serverAddressField);
		p.add(new JLabel("Port:"));
		p.add(serverPortField);
		p.add(new JLabel("Nickname:"));
		p.add(userField);

		dialog = new JOptionPane(
				p,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION,
				null,
				new Object[] {connectButton, exitButton},
				connectButton
		).createDialog("JChat");
		dialog.setModal(false);
		dialog.setIconImage(new ImageIcon("icons/jchat.png").getImage());
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	public boolean getStartServer() {
		return startServerCheckbox.isSelected();
	}

	public String getServerAddress() {
		return getStartServer() ? "localhost" : serverAddressField.getText();
	}

	public int getServerPort() {
		return serverPortField.getNumber();
	}

	public String getUser() {
		return userField.getText();
	}

	public void setVisible(final boolean visible) {
		dialog.setVisible(visible);
	}
}
