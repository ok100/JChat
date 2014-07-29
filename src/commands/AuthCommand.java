package commands;

import java.io.IOException;

import messages.ServerReply;
import network.Sendable;
import network.server.ClientConnection;
import network.server.Server;

/**
 * Check if an user with the same nickname as the sender is already connected.
 * <p>
 * <b>Reply:</b> <code>ERROR</code> if an user with the same nickname as
 * the sender is already connected; otherwise <code>OK</code>
 * </p>
 * @author ok
 *
 */
public class AuthCommand extends Sendable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new AuthCommand.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 */
	public AuthCommand(final String id, final String sender) {
		super(id, sender, null);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	@Override
	public void process(final Server server) throws IOException {
		super.process(server);

		ClientConnection conn = getSenderConnection();
		if(server.getClientByName(getSender()) == null) {
			conn.setUser(getSender());
			conn.setLoggedIn(true);
			conn.getIO().write(new ServerReply(null, "OK"));
		} else {
			conn.getIO().write(new ServerReply(null, "ERROR"));
		}
	}
}
