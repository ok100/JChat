package gui.components;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * A JTextField which only accepts integers.
 * @author ok
 *
 */
public class IntegerField extends JTextField {
	// Constants and fields.
	// -------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;

	// Constructors.
	// -------------------------------------------------------------------------
	public IntegerField() {
		super();
	}

	// Instance methods.
	// -------------------------------------------------------------------------
	public int getNumber() {
		return Integer.parseInt(getText());
	}

	public void setNumber(final int n) {
		setText(Integer.toString(n));
	}

	@Override
	protected Document createDefaultModel() {
		return new PlainDocument() {
			private static final long serialVersionUID = 1L;

			@Override
			public void insertString(int offset, String str, AttributeSet a)
					throws BadLocationException {
				if(str.matches("^[0-9]*$"))
					super.insertString(offset, str, a);
			}
		};
	}
}
