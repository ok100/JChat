package messages;

/**
 * This class represents a special case of text message which is used for
 * server -> client communication exclusively.
 * @author ok
 *
 */
public class ServerReply extends TextMessage {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new server reply.
	 * @param roomName Room in which the reply was sent
	 * @param msg Message text
	 */
	public ServerReply(final String roomName, final String msg) {
		super(null, "Server", roomName, msg);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	public boolean isOK() {
		return getData().equals("OK");
	}
}
