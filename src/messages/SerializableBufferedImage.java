package messages;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Serializable BufferedImage.
 * <p>
 * <b>Note:</b> Each pixel value of the source image is saved into
 * a 2-dimensional array of ints. The original image can be obtained with
 * <code>getImage()</code> method.
 * </p>
 * For animated GIFs, only the first frame is saved.
 * @author ok
 *
 */
public class SerializableBufferedImage implements Serializable {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;
	private transient BufferedImage img = null;
	private final int width;
	private final int height;
	private final int[][] data;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new serializable BufferedImage.
	 * @param img Source image
	 */
	public SerializableBufferedImage(final BufferedImage img) {
		this.img = img;
		width = img.getWidth();
		height = img.getHeight();
		data = new int[width][height];
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				data[x][y] = img.getRGB(x, y);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	public BufferedImage getImage() {
		if(img == null) {
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			for(int x = 0; x < width; x++)
				for(int y = 0; y < height; y++)
					img.setRGB(x, y, data[x][y]);
		}
		return img;
	}
}
