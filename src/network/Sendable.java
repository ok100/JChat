package network;

import java.io.IOException;
import java.io.Serializable;

import network.server.ClientConnection;
import network.server.Server;

/**
 * This class represents an object which can be send over socket.
 * @author ok
 *
 */
public abstract class Sendable implements Serializable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;
	private final String id;
	private final String sender;
	private final String roomName;
	private transient Server server;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Creates an object which can be send over socket.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param roomName The room in which the object was sent
	 */
	public Sendable(final String id, final String sender,
			final String roomName) {
		this.id = id;
		this.sender = sender;
		this.roomName = roomName;
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	public String getSender() {
		return sender;
	}

	public String getRoomName() {
		return roomName;
	}

	public ClientConnection getSenderConnection() {
		for(ClientConnection c : server.getAllClients())
			if(c.getID().equals(id))
				return c;
		return null;
	}

	/**
	 * Process the object on server.
	 * @param server The server on which the processing is being done
	 * @throws IOException if an I/O error occurs while processing the object
	 */
	public void process(final Server server) throws IOException {
		this.server = server;
	}
}
