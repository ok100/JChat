package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * A SocketIO object which reads and writes sendable data to the specified
 * socket.
 * @author ok
 *
 */
public class SocketIO {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private final ObjectOutputStream outputStream;
	private final ObjectInputStream inputStream;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Creates a SocketIO object which reads and writes sendable data
	 * to the specified socket.
	 * @param socket Socket to write and read from
	 * @throws IOException if an I/O error occurs while creating I/O streams
	 */
	public SocketIO(final Socket socket) throws IOException {
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		inputStream = new ObjectInputStream(socket.getInputStream());
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	/**
	 * Read data from the socket.
	 * @return Read data
	 * @throws IOException if an I/O error occurs while reading from stream
	 */
	public Sendable read() throws IOException {
		try {
			return (Sendable) inputStream.readObject();
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	/**
	 * Write data to the socket.
	 * @param o Data to write
	 * @throws IOException if an I/O error occurs while writing to stream
	 */
	public void write(final Sendable o) throws IOException {
		outputStream.writeObject(o);
	}

	/**
	 * Close all I/O streams.
	 * @throws IOException if an I/O error occurs while closing streams
	 */
	public void close() throws IOException {
		inputStream.close();
		outputStream.close();
	}
}
