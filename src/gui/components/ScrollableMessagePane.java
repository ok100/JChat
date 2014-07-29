package gui.components;

import javax.swing.JScrollPane;

import messages.Message;


/**
 * This class represents a scrollable MessagePane.
 * @author ok
 *
 */
public class ScrollableMessagePane extends JScrollPane {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;
	private final MessagePane view = new MessagePane();

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new scrollable MessagePane.
	 */
	public ScrollableMessagePane() {
		setViewportView(view);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	/**
	 * Scroll to the bottom of the MessagePane.
	 */
	public void scrollBottom() {
		view.setCaretPosition(view.getDocument().getLength());
	}

	/**
	 * Write message to the MessagePane and scroll bottom.
	 * @param msg Message to write
	 */
	public void write(final Message<?> msg) {
		view.write(msg);
		scrollBottom();
	}
}
