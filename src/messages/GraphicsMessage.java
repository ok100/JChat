package messages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Graphics message.
 * @author ok
 *
 */
public class GraphicsMessage extends Message<SerializableBufferedImage> {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;
	private final String fileName;
	private final long fileSize;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new graphics message.
	 * @param id ID of the sender
	 * @param sender Nickname of the sender
	 * @param room Room in which the message was sent
	 * @param fileName Path to the image
	 * @throws IOException if an I/O error occurs while loading the image
	 */
	protected GraphicsMessage(final String id, final String sender,
			final String room, final String fileName) throws IOException {
		super(id, sender, room, null);
		BufferedImage img = ImageIO.read(new File(fileName));
		if(img == null)
			throw new IOException(fileName + " is not an image file");
		setData(new SerializableBufferedImage(img));
		this.fileName = Paths.get(fileName).getFileName().toString();
		fileSize = new File(fileName).length();
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	public String getFileName() {
		return fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public ImageIcon getThumbnail() {
		BufferedImage img = getData().getImage();
		int new_w = 128;
		int new_h = img.getHeight() / (img.getWidth() / new_w);

		BufferedImage thumb = new BufferedImage(new_w, new_h,
				BufferedImage.TYPE_INT_ARGB);
		thumb.createGraphics().drawImage(img, 0, 0, new_w, new_h, null);
		return new ImageIcon(thumb);
	}

	/**
	 * Save image to the given file.
	 * @param file Path to the file
	 * @throws IOException if an I/O error occurs while saving image
	 */
	public void save(final File file) throws IOException {
		BufferedImage img = getData().getImage();
		BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_3BYTE_BGR);
		out.createGraphics().drawImage(img, 0, 0,
				img.getWidth(), img.getHeight(), null);
		String type = fileName.substring(fileName.lastIndexOf('.') + 1);
		ImageIO.write(out, type, file);
	}

	@Override
	public String toString() {
		return String.format("%s (%dx%d, %.1fkB)",
				getFileName(),
				getData().getImage().getWidth(),
				getData().getImage().getHeight(),
				(float) getFileSize() / 1024
		);
	}
}
