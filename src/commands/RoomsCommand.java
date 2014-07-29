package commands;

import java.io.IOException;

import messages.ServerReply;
import network.Sendable;
import network.server.Room;
import network.server.Server;

/**
 * List all rooms on the server.
 * <p>
 * <b>Reply:</b> a list of all rooms on the server
 * </b>
 * @author ok
 *
 */
public class RoomsCommand extends Sendable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new RoomsCommand.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param roomName Room in which the command was sent
	 */
	public RoomsCommand(final String id, final String sender,
			final String roomName) {
		super(id, sender, roomName);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	@Override
	public void process(final Server server) throws IOException {
		super.process(server);

		String msg = "Rooms:";
		for(Room r : server.getRooms())
			msg += " #" + r.getName();
		getSenderConnection().getIO().write(new ServerReply(getRoomName(),
				msg));
	}
}
