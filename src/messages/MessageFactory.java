package messages;

import java.io.IOException;

/**
 * Parse the given string and create a corresponding Message.
 * <p>
 * <ul>
 * 	<li>Strings starting with '!' character will be treated
 *      as GraphicsMessage</li>
 * 	<li>Other strings will be treated as TextMessage</li>
 * </ul>
 * </p>
 * @author ok
 *
 */
public class MessageFactory {
	// Class methods.
	// -------------------------------------------------------------------------
	/**
	 * Create message.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param roomName Room in which the message was sent
	 * @param str String to parse
	 * @return Resulting Message
	 */
	public static Message<?> createMessage(final String id, final String sender,
			final String roomName, final String str) {
		if(str.startsWith("!")) {
			try {
				return new GraphicsMessage(id, sender, roomName,
						str.substring(1));
			} catch (IOException e) {
				return new TextMessage(id, sender, roomName, str);
			}
		} else {
			return new TextMessage(id, sender, roomName, str);
		}
	}
}
