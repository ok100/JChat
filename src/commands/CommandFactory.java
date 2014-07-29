package commands;

import java.util.Arrays;

import network.Sendable;

/**
 * Parse the given string and create a corresponding Command.
 * @author ok
 *
 */
public class CommandFactory {
	// Class methods.
	// -------------------------------------------------------------------------
	/**
	 * Create command.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param roomName Room in which the command was sent
	 * @param str String to parse
	 * @return Resulting Command
	 */
	public static Sendable createCommand(final String id, final String sender,
			final String roomName, final String str) {
		String[] t = str.split("\\s");
		String cmdName = t[0];
		String[] args = Arrays.copyOfRange(t, 1, t.length);

		try {
			switch(cmdName) {
			case "/auth":
				return new AuthCommand(id, sender);
			case "/block":
				return new BlockCommand(id, sender, roomName, args[0],
						Boolean.parseBoolean(args[1]));
			case "/join":
				return new JoinCommand(id, sender, args[0]);
			case "/leave":
				return new LeaveCommand(id, sender, roomName);
			case "/rooms":
				return new RoomsCommand(id, sender, roomName);
			case "/stats":
				return new StatsCommand(id, sender, roomName);
			case "/users":
				return new UsersCommand(id, sender, roomName);
			default:
				return new UnknownCommand(id, sender, roomName);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return new SyntaxErrorCommand(id, sender, roomName);
		}
	}
}
