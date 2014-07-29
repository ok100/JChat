package network.server;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents server statistics.
 * @author ok
 *
 */
public class ServerStats {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private final Server server;
	private final Date startDate = new Date();
	private long messages = 0;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Creates a server statistics.
	 * @param server Server from which the stats will retrieve
	 */
	public ServerStats(final Server server) {
		this.server = server;
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	public long getMessages() {
		return messages;
	}

	public void setMessages(final long messages) {
		this.messages = messages;
	}

	public String getUptime() {
		long delta = (new Date().getTime() - startDate.getTime()) / 1000;
		int secs = (int) (delta % 60);
		delta /= 60;
		int mins = (int) (delta % 60);
		delta /= 60;
		int hours = (int) (delta % 24);
		delta /= 24;
		int days = (int) delta;

		return String.format("%dd %dh %dm %ds", days, hours, mins, secs);
	}

	public String getStats() {
		return String.format("start %s, up %s, %d user(s), %d message(s)",
				new SimpleDateFormat("yyyy/MM/dd kk:mm:ss").format(startDate),
				getUptime(),
				server.getLoggedClients().size(),
				getMessages()
		);
	}
}
