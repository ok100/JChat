package commands;

import java.io.IOException;

import messages.ServerReply;
import network.Sendable;
import network.server.ClientConnection;
import network.server.Room;
import network.server.Server;

/**
 * Join a room.
 * <p>
 * <b>Reply:</b> notify all users connected to the room about the new client
 * </b>
 * @author ok
 *
 */
public class JoinCommand extends Sendable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new JoinCommand.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param roomName Room to join
	 */
	public JoinCommand(final String id, final String sender,
			final String roomName) {
		super(id, sender, roomName);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	@Override
	public void process(final Server server) throws IOException {
		super.process(server);

		if(getSenderConnection().getRooms().contains(getRoomName()))
			// If the client is already connected to the room, do nothing
			return;

		Room room;
		if(server.getRooms().contains(getRoomName()))
			// Join existing room
			room = server.getRooms().getRoom(getRoomName());
		else
			// Create a new one
			room = new Room(getRoomName(), getSender());
		getSenderConnection().getRooms().add(room);

		for(ClientConnection c : server.getClientsInRoom(getRoomName()))
			c.getIO().write(new ServerReply(getRoomName(),
					getSender() + " joined the room"));
	}
}
