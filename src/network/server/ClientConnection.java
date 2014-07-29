package network.server;

import java.io.IOException;
import java.net.Socket;

import commands.CommandFactory;
import network.SocketIO;

/**
 * Connection between server and specific client.
 * @author ok
 *
 */
public class ClientConnection {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private final String id;
	private final Server server;
	private final SocketIO io;
	private String user;
	private final RoomSet rooms = new RoomSet();
	private boolean isLoggedIn = false;
	private final Thread worker = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				while(true)
					io.read().process(server);
			} catch (IOException e) {
				disconnect();
			}
		}
	});

	// Instance methods.
	// -------------------------------------------------------------------------
	public String getID() {
		return id;
	}

	public SocketIO getIO() {
		return io;
	}

	public String getUser() {
		return user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public RoomSet getRooms() {
		return rooms;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(final boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	/**
	 * Create a new connection between server and specific client.
	 * @param server Server the client is connecting to
	 * @param socket Client socket
	 * @throws IOException if an I/O error occurs while creating I/O streams
	 */
	public ClientConnection(final Server server, final Socket socket)
			throws IOException {
		this.server = server;
		io = new SocketIO(socket);
		id = socket.getRemoteSocketAddress().toString();
		worker.start();
	}

	/**
	 * Disconnect the client.
	 */
	@SuppressWarnings("deprecation")
	public void disconnect() {
		try {
			for(Room room : rooms)
				CommandFactory.createCommand(id, user, room.getName(),
						"/leave").process(server);
			server.getAllClients().remove(this);
			worker.stop();
			io.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("An error occured while disconnecting client");
		}
	}
}
