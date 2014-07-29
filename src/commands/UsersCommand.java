package commands;

import java.io.IOException;

import messages.ServerReply;
import network.Sendable;
import network.server.ClientConnection;
import network.server.Server;

/**
 * List all users on the server.
 * <p>
 * <b>Reply:</b> a list of all users on the server
 * </b>
 * @author ok
 *
 */
public class UsersCommand extends Sendable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Constants and fields.
	// -------------------------------------------------------------------------
	/**
	 * Create a new UsersCommand.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param roomName Room in which the command was sent
	 */
	public UsersCommand(final String id, final String sender,
			final String roomName) {
		super(id, sender, roomName);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	@Override
	public void process(final Server server) throws IOException {
		super.process(server);

		String msg = "Users in room #" + getRoomName() + ":";
		for(ClientConnection c : server.getClientsInRoom(getRoomName()))
			msg += " " + c.getUser();
		getSenderConnection().getIO().write(new ServerReply(getRoomName(),
				msg));
	}
}
