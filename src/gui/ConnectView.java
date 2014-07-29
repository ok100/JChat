package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.Controller;

/**
 * Pop-up window with connection progress.
 * @author ok
 *
 */
public class ConnectView {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private final JDialog dialog;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new ConnectView.
	 */
	public ConnectView() {
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().exitAction();
			}
		});

		dialog = new JOptionPane(
				new JLabel("Connecting to server..."),
				JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.CANCEL_OPTION,
				null,
				new Object[] {exitButton},
				exitButton
		).createDialog("JChat");
		dialog.setModal(false);
		dialog.setIconImage(new ImageIcon("icons/jchat.png").getImage());
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	public void setVisible(final boolean visible) {
		dialog.setVisible(visible);
	}
}
