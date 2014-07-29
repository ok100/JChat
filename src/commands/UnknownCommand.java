package commands;

import java.io.IOException;

import messages.ServerReply;
import network.Sendable;
import network.server.Server;

/**
 * Unknown command.
 * @author ok
 *
 */
public class UnknownCommand extends Sendable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new UnknownCommand.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param roomName Room in which the command was sent
	 */
	public UnknownCommand(final String id, final String sender,
			final String roomName) {
		super(id, sender, roomName);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	@Override
	public void process(final Server server) throws IOException {
		super.process(server);

		getSenderConnection().getIO().write(new ServerReply(getRoomName(),
				"Unknown command"));
	}
}
