import gui.GuiHelper;
import controller.Controller;

/**
 * Application entry point,
 * @author ok
 *
 */
public class JChat {
	/**
	 * Start the app.
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		GuiHelper.loadNativeLookAndFeel();
		Controller.getInstance();
	}
}
