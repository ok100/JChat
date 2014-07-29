package gui;

import java.awt.Component;
import java.io.File;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Common GUI methods.
 * @author ok
 *
 */
public class GuiHelper {
	// Constructors.
	// -------------------------------------------------------------------------
	private GuiHelper() {}

	// Class methods.
	// -------------------------------------------------------------------------
	/**
	 * Load native look and feel.
	 */
	public static void loadNativeLookAndFeel() {
		String[] looks = {
				"com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
				"com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
		};
		try {
			for(LookAndFeelInfo i : UIManager.getInstalledLookAndFeels())
				if(Arrays.asList(looks).contains(i.getClassName()))
					UIManager.setLookAndFeel(i.getClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			System.err.println("Unable to load native look and feel");
		}
	}

	/**
	 * Show error message.
	 * @param msg Message to show
	 */
	public static void showErrorMessage(final String msg) {
		JOptionPane.showMessageDialog(null, msg, "JChat",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Show open/close file dialog.
	 * @param parent Parent component
	 * @param type Type of the dialog
	 *        <ul>
	 *        <li><code>JFileChooser.OPEN_DIALOG</code> for Open dialog</li>
	 *        <li><code>JFileChooser.SAVE_DIALOG</code> for Save dialog</li>
	 *        </ul>
	 * @param filter File name/extension filter
	 * @param selectedFile Pre-selected file
	 * @return Absolute path of the selected file if a file was selected;
	 *         <code>null</code> otherwise
	 */
	public static String showFileChooser(final Component parent, final int type,
			final FileNameExtensionFilter filter, final File selectedFile) {
		JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		fc.setSelectedFile(selectedFile);

		int result = (type == JFileChooser.OPEN_DIALOG)
				? fc.showOpenDialog(parent)
				: fc.showSaveDialog(parent);
		if(result == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile().getAbsolutePath();
		return null;
	}
}
