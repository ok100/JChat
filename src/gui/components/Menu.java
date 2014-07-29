package gui.components;

import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Helper class for easier menu creation.
 * @author ok
 *
 */
public class Menu extends JMenuBar {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Constructors.
	// -------------------------------------------------------------------------
	/**
	 * Create a new menu.
	 * @param parent Parent window
	 */
	public Menu(final JFrame parent) {
		parent.setJMenuBar(this);
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	/**
	 * Add item to the menu.
	 * @param menu Menu the item will be added to
	 * @param title Title of the item
	 * @param toolTip Tooltip of the item
	 * @param listener Item listener
	 */
	public void addItem(final String menu, final String title,
			final String toolTip, final ActionListener listener) {
		JMenu m = null;
		for(int i = 0; i < getMenuCount(); i++) {
			if(getMenu(i) != null && getMenu(i).getText().equals(menu)) {
				m = getMenu(i);
				break;
			}
		}
		if(m == null)
			m = new JMenu(menu);

		JMenuItem i = new JMenuItem(title);
		i.setToolTipText(toolTip);
		i.addActionListener(listener);
		m.add(i);
		add(m);
	}

	/**
	 * Add horizontal glue to the menu.
	 * After calling this method, all newly added items will be right-aligned.
	 */
	public void addGlue() {
		add(Box.createHorizontalGlue());
	}
}
