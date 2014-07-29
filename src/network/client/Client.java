package network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

import commands.CommandFactory;
import messages.Message;
import messages.ServerReply;
import network.Sendable;
import network.SocketIO;
import network.exceptions.AuthErrorException;
import network.exceptions.ConnectionErrorException;

/**
 * JChat client.
 * @author ok
 *
 */
public class Client extends Observable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private boolean isConnected = false;
	private String id;
	private Socket socket;
	private SocketIO io;
	private String user;
	private final Thread worker = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				while(true) {
					setChanged();
					notifyObservers((Message<?>) io.read());
				}
			} catch (IOException e) {
				setChanged();
				notifyObservers(new ConnectionErrorException());
			}
		}
	});

	// Instance methods.
	// -------------------------------------------------------------------------
	public boolean isConnected() {
		return isConnected;
	}

	public String getID() {
		return id;
	}

	public SocketIO getIO() {
		return io;
	}

	public String getUser() {
		return user;
	}

	/**
	 * Connect to the given server and start receiving incoming data.
	 * This method is non-blocking as the listener runs in a new thread.
	 * @param host Server address
	 * @param port Server port
	 * @param user Nickname
	 * @throws UnknownHostException if the IP address of the server could not
	 *         be determined
	 * @throws IOException if an I/O error occurs while creating I/O streams
	 * @throws AuthErrorException if the nickname is already taken
	 */
	public void connect(final String host, final int port, final String user)
			throws UnknownHostException, IOException, AuthErrorException {
		this.user = user;
		socket = new Socket(host, port);
		id = socket.getLocalSocketAddress().toString();
		io = new SocketIO(socket);

		io.write(CommandFactory.createCommand(id, user, null, "/auth"));
		if(!((ServerReply) io.read()).isOK())
			throw new AuthErrorException();

		worker.start();
		isConnected = true;
	}

	/**
	 * Disconnect from server.
	 */
	@SuppressWarnings("deprecation")
	public void disconnect() {
		try {
			if(isConnected) {
				worker.stop();
				io.close();
				socket.close();
				isConnected = false;
			}
		} catch (IOException e) {
			System.err.println("An error occured while disconnecting client");
		}
	}

	/**
	 * Send a given sendable object to the server.
	 * @param obj Object to send
	 */
	public void send(final Sendable obj) {
		try {
			io.write(obj);
		} catch (IOException e) {
			System.err.println("Unable to send message");
		}
	}
}
