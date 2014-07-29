package network.server;

import java.util.HashSet;

/**
 * This class represents a room.
 * Any message sent in specific room will be forwarded to all clients connected
 * to the room.
 * @author ok
 *
 */
public class Room {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private String name;
	private String owner;
	private final HashSet<String> blockedUsers = new HashSet<String>();

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new room.
	 * The user who created the room becomes the owner of the room
	 * and has more privileges than a regular user.
	 * @param name Name of the room
	 * @param owner Owner of the room
	 */
	public Room(final String name, final String owner) {
		setName(name);
		setOwner(owner);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	public HashSet<String> getBlockedUsers() {
		return blockedUsers;
	}

	@Override
	public String toString() {
		return getName();
	}
}
