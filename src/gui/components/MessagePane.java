package gui.components;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import messages.GraphicsMessage;
import messages.Message;
import messages.ServerReply;
import messages.TextMessage;
import controller.Controller;

/**
 * Message viewer.
 * @author ok
 *
 */
public class MessagePane extends JTextPane {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;
	private final StyledDocument doc = getStyledDocument();

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new message pane.
	 */
	public MessagePane() {
		setEditable(false);
		addStyles();
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	private void addStyles() {
		Style def = StyleContext.getDefaultStyleContext()
				.getStyle(StyleContext.DEFAULT_STYLE);

		Style regular = doc.addStyle("regular", def);

		Style s = doc.addStyle("bold", regular);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("italic", regular);
		StyleConstants.setItalic(s, true);
	}

	private void writeText(final String str, final String style) {
		try {
			doc.insertString(doc.getLength(), str, doc.getStyle(style));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void writeMessageHeader(final Message<?> msg) {
		if(!getText().equals(""))
			writeText("\n", "regular");
		String date = new SimpleDateFormat("kk:mm:ss").format(msg.getDate());
		writeText(date, "italic");
		writeText(" " + msg.getSender() + ":    ", "bold");
	}

	private void write(final TextMessage msg) {
		writeText(msg.getData(), "regular");
	}

	private void write(final ServerReply msg) {
		writeText(msg.getData(), "italic");
	}

	private void write(final GraphicsMessage msg) {
		JButton button = new JButton(msg.getThumbnail());
		button.setToolTipText(msg.toString());
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Controller.getInstance().saveImageAction(msg);
			}
		});
		Style s = doc.addStyle("button", doc.getStyle("regular"));
		StyleConstants.setComponent(s, button);
		try {
			doc.insertString(doc.getLength(), " ", doc.getStyle("button"));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write message.
	 * @param msg Message to write
	 */
	public void write(final Message<?> msg) {
		writeMessageHeader(msg);

		if(msg instanceof ServerReply)
			write((ServerReply) msg);
		else if(msg instanceof GraphicsMessage)
			write((GraphicsMessage) msg);
		else if(msg instanceof TextMessage)
			write((TextMessage) msg);
	}
}
