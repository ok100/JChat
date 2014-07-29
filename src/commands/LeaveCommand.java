package commands;

import java.io.IOException;

import messages.ServerReply;
import network.Sendable;
import network.server.ClientConnection;
import network.server.Server;

/**
 * Leave a room.
 * <p>
 * <b>Reply:</b> notify all users connected to the room about the leaving
 * client
 * </b>
 * @author ok
 *
 */
public class LeaveCommand extends Sendable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new LeaveCommand
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param roomName Room to leave
	 */
	public LeaveCommand(final String id, final String sender,
			final String roomName) {
		super(id, sender, roomName);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	@Override
	public void process(final Server server) throws IOException {
		super.process(server);

		getSenderConnection().getRooms().remove(getRoomName());
		for(ClientConnection c : server.getClientsInRoom(getRoomName()))
			c.getIO().write(new ServerReply(getRoomName(),
					getSender() + " left the room"));
	}
}
