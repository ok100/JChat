package network.server;

import java.util.HashSet;

/**
 * This class represents a HashSet of Rooms.
 * @author ok
 *
 */
public class RoomSet extends HashSet<Room> {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Instance methods.
	// -------------------------------------------------------------------------
	public Room getRoom(final String roomName) {
		for(Room r : this)
			if(r.getName().equals(roomName))
				return r;
		return null;
	}

	public boolean contains(final String roomName) {
		for(Room r : this)
			if(r.getName().equals(roomName))
				return true;
		return false;
	}

	public void remove(final String roomName) {
		for(Room r : this) {
			if(r.getName().equals(roomName)) {
				remove(r);
				break;
			}
		}
	}
}
