package minesweeper.views.components;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;

import minesweeper.model.Field;
import minesweeper.model.FieldState;
import minesweeper.model.FieldValue;
import minesweeper.views.utils.AssetsManager;
import minesweeper.views.utils.ViewUtilities;

/**
 *  Field Button Component
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class FieldButton extends JToggleButton {
	private static final long serialVersionUID = 9132269106149870392L;
	
	// UI properties (static final for performance issues)
	private static final ImageIcon ICON_NORMAL = new ImageIcon(AssetsManager.getResource(FieldButton.class, "field.png"));
	private static final ImageIcon ICON_NORMAL_MOUSEOVER = new ImageIcon(AssetsManager.getResource(FieldButton.class, "field_hover.png"));
	private static final ImageIcon ICON_MINE = new ImageIcon(AssetsManager.getResource(FieldButton.class, "mine.png"));
	private static final ImageIcon ICON_MINE_CLICKED = new ImageIcon(AssetsManager.getResource(FieldButton.class, "mine_click.png"));
	private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
	private static final Border BUTTON_BORDER = BorderFactory.createEmptyBorder();
	public static final Dimension BUTTON_SIZE = new Dimension(ICON_NORMAL.getIconWidth(), ICON_NORMAL.getIconHeight());
	
	// component objects
	private ImageIcon _iconFlagged;
	private ImageIcon _iconWrongFlag;
	private int _row, _col;
	private Field _field;
	
	/**
	 * create new {@link FieldButton} component, part of a minefield matrix
	 * @param row button row
	 * @param col button column
	 * @param field {@link Field} data
	 */
	public FieldButton(int row, int col, Field field) {
		super();
	
		_field = field;
		_row = row;
		_col = col;
		
		// Initialize Button UI
		setFocusable(false);
		setBorder(BUTTON_BORDER);
		UIManager.put("textInactiveText", new ColorUIResource(AssetsManager.getCurrentLevelForegroundColor()));
		setFont(BUTTON_FONT);
		setSize(BUTTON_SIZE);
		setIcon(ICON_NORMAL);
		setRolloverIcon(ICON_NORMAL_MOUSEOVER);
		
		// set icons for the current level
		_iconFlagged = new ImageIcon(AssetsManager.getResource(this.getClass(), AssetsManager.getCurrentLevelPrefix() + "_flag.png"));
		_iconWrongFlag = new ImageIcon(AssetsManager.getResource(this.getClass(), AssetsManager.getCurrentLevelPrefix() + "_flag_x.png"));
	}
	
	/**
	 * get button row
	 * @return button row
	 */
	public int getRow() {
		return _row;
	}
	
	/**
	 * get button column
	 * @return button column
	 */
	public int getCol() {
		return _col;
	}
	
	/**
	 * get field value
	 * @return {@link FieldValue} represents the field value
	 */
	public FieldValue getValue() {
		return _field.getValue();
	}
	
	/**
	 * get field data
	 * @return {@link Field} object contains the button's data
	 */
	public Field getField() {
		return _field;
	}
	
	/**
	 * flag field
	 */
	public void flag() {
		_field.setState(FieldState.FLAGGED);
		setIcon(_iconFlagged);
		setRolloverIcon(null);
	}
	
	/**
	 * remove a flag
	 */
	public void unflag() {
		_field.setState(FieldState.UNFLAGGED);
		setIcon(ICON_NORMAL);
		setRolloverIcon(ICON_NORMAL_MOUSEOVER);
	}
	
	/**
	 * open field
	 */
	public void open() {
		if (_field.getState().equals(FieldState.FLAGGED)) {		// if field was flagged
			if (_field.getValue().equals(FieldValue.MINE)) {		// if field has mine
				setIcon(_iconFlagged);	// correct flag
			} else {
				setIcon(_iconWrongFlag);	// wrong flag
			}
		} else {
			_field.setState(FieldState.OPENED);	// change field state to opened
			if (_field.getValue().equals(FieldValue.MINE)) {	// if field has mine
				setIcon(ICON_MINE);	// set icon to mine
			} else {
				setIcon(null);	// null icon
				setRolloverIcon(null);
				// set number
				setText("<html><font color=" + ViewUtilities.convertToHex(AssetsManager.getCurrentLevelForegroundColor()) + ">" + _field.getValue().toString() + "</font></html>");
			}
		}
		
		// disable the button so we cannot click again
		setDisabledIcon(getIcon());
		setSelected(true);
		setEnabled(false);
	}
	
	/**
	 * check if field button is flagged
	 * @return true if has a flag, false if not
	 */
	public boolean isFlagged() {
		return _field.getState().equals(FieldState.FLAGGED);
	}
	
	/**
	 * check if field button is unflagged
	 * @return true if is unflagged, false if not
	 */
	public boolean isUnflagged() {
		return _field.getState().equals(FieldState.UNFLAGGED);
	}
	
	/**
	 * check if field button is mine
	 * @return true if mine, false if not
	 */
	public boolean isMine() {
		return _field.getValue().equals(FieldValue.MINE);
	}

	/**
	 * set field button icon to mine click (so the user knows if this button triggered the GAME OVER)
	 */
	public void setMineClickIcon() {
		setIcon(ICON_MINE_CLICKED);
		setDisabledIcon(getIcon());
	}
}
