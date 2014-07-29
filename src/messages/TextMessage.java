package messages;

/**
 * Text message.
 * @author ok
 *
 */
public class TextMessage extends Message<String> {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new text message.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param room Room in which the message was sent
	 * @param data Message text
	 */
	public TextMessage(final String id, final String sender, final String room,
			final String data) {
		super(id, sender, room, data);
	}
}
