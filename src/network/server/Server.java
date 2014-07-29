package network.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashSet;

/**
 * JChat server.
 * @author ok
 *
 */
public class Server {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private boolean isStarted = false;
	private ServerStats stats;
	private ServerSocket socket;
	private final HashSet<ClientConnection> clients
			= new HashSet<ClientConnection>();
	private final Thread worker = new Thread(new Runnable() {
		@Override
		public void run() {
			while(true) {
				try {
					clients.add(new ClientConnection(Server.this,
							socket.accept()));
				} catch (IOException e) {
					System.err.println("Unable to connect client");
				}
			}
		}
	});

	// Instance methods.
	// -------------------------------------------------------------------------
	public boolean isStarted() {
		return isStarted;
	}

	public ServerStats getStats() {
		return stats;
	}

	public HashSet<ClientConnection> getAllClients() {
		return clients;
	}

	public HashSet<ClientConnection> getLoggedClients() {
		HashSet<ClientConnection> clients = new HashSet<ClientConnection>();
		for(ClientConnection c : getAllClients())
			if(c.isLoggedIn())
				clients.add(c);
		return clients;
	}

	public HashSet<ClientConnection> getClientsInRoom(final String roomName) {
		HashSet<ClientConnection> clients = new HashSet<ClientConnection>();
		for(ClientConnection c : getLoggedClients())
			if(c.getRooms().contains(roomName))
				clients.add(c);
		return clients;
	}

	public ClientConnection getClientByName(final String user) {
		for(ClientConnection c : getLoggedClients())
			if(c.getUser().equals(user))
				return c;
		return null;
	}

	public RoomSet getRooms() {
		RoomSet rooms = new RoomSet();
		for(ClientConnection c : getLoggedClients())
			rooms.addAll(c.getRooms());
		return rooms;
	}

	/**
	 * Start listening at the given port.
	 * This method is non-blocking as the listener runs in a new thread.
	 * @param port Listening port
	 * @throws IOException if an I/O error occurs while initializing the server
	 *         socket
	 */
	public void start(final int port) throws IOException {
		socket = new ServerSocket(port, 10, InetAddress.getByName("0.0.0.0"));
		stats = new ServerStats(this);
		worker.start();
		isStarted = true;
	}

	/**
	 * Stop the server.
	 * @throws IOException if an I/O error occurs while closing the server
	 *         socket
	 */
	@SuppressWarnings("deprecation")
	public void stop() throws IOException {
		if(!isStarted)
			return;
		worker.stop();
		socket.close();
		isStarted = false;
	}
}
