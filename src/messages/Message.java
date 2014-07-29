package messages;

import java.io.IOException;
import java.util.Date;

import network.Sendable;
import network.server.ClientConnection;
import network.server.Server;

/**
 * Generic message.
 * This class should be subclassed for specific message type.
 * @author ok
 *
 * @param <T> Type of the data
 */
public abstract class Message<T> extends Sendable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;
	private final Date date = new Date();
	private T data;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new message.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param room Room in which the message was sent
	 * @param data Message data
	 */
	public Message(final String id, final String sender, final String room,
			final T data) {
		super(id, sender, room);
		setData(data);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	public Date getDate() {
		return date;
	}

	public T getData() {
		return data;
	}

	public void setData(final T data) {
		this.data = data;
	}

	@Override
	public void process(final Server server) throws IOException {
		super.process(server);

		if(server.getRooms().getRoom(getRoomName()).getBlockedUsers()
				.contains(getSender())) {
			getSenderConnection().getIO().write(new ServerReply(getRoomName(),
					"You are blocked in this room"));
			return;
		}

		server.getStats().setMessages(server.getStats().getMessages() + 1);
		for(ClientConnection c : server.getLoggedClients())
			if(c.getRooms().contains(getRoomName()))
				c.getIO().write(this);
	}
}
