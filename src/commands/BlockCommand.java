package commands;

import java.io.IOException;
import java.util.HashSet;

import messages.ServerReply;
import network.Sendable;
import network.SocketIO;
import network.server.Server;

/**
 * Block or unblock an user in a single room.
 * This command can be executed only by admin of the room.
 * Target user will be notified about the block/unblock event.
 * <p>
 * <b>Reply:</b> whether the user was succesfully blocked/unblocked
 * </b>
 * @author ok
 *
 */
public class BlockCommand extends Sendable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;
	private final String targetUser;
	private final boolean isBlocked;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new BlockCommand.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param roomName Room in which the command was sent
	 * @param targetUser Target user
	 * @param isBlocked <code>true</code> to block;
	 *                  <code>false</code> to unblock
	 */
	public BlockCommand(final String id, final String sender,
			final String roomName, final String targetUser,
			final boolean isBlocked) {
		super(id, sender, roomName);
		this.targetUser = targetUser;
		this.isBlocked = isBlocked;
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	@Override
	public void process(final Server server) throws IOException {
		super.process(server);

		if(!server.getRooms().getRoom(getRoomName()).getOwner()
				.equals(getSender())) {
			getSenderConnection().getIO().write(new ServerReply(getRoomName(),
					"You are not admin of this room"));
			return;
		}

		if(server.getClientByName(targetUser) == null) {
			getSenderConnection().getIO().write(new ServerReply(getRoomName(),
					"Unknown user " + targetUser));
			return;
		}

		HashSet<String> blockedUsers
				= server.getRooms().getRoom(getRoomName()).getBlockedUsers();
		SocketIO targetIO = server.getClientByName(targetUser).getIO();
		SocketIO senderIO = getSenderConnection().getIO();

		if(isBlocked && !blockedUsers.contains(targetUser)) {
			blockedUsers.add(targetUser);
			targetIO.write(new ServerReply(getRoomName(),
					"You was blocked by " + getSender()));
			senderIO.write(new ServerReply(getRoomName(),
					targetUser + " is blocked"));
		} else if(!isBlocked && blockedUsers.contains(targetUser)) {
			blockedUsers.remove(targetUser);
			targetIO.write(new ServerReply(getRoomName(),
					"You was unblocked by " + getSender()));
			senderIO.write(new ServerReply(getRoomName(),
					targetUser + " is unblocked"));
		}
	}
}
