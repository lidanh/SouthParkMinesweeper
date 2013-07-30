package minesweeper.views.components;

import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import minesweeper.model.Minefield;
import minesweeper.model.Size;

/**
 *  Minefield panel, contains a matrix of {@link FieldButton}
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class MinefieldPanel extends JPanel {
	private static final long serialVersionUID = 7885981401108580383L;
	private FieldButton _fields[][];	// field button matrix
	
	/**
	 * create new minefield panel
	 * @param minefield	{@link Minefield} object model (minefield data)
	 * @param fieldClickListener mouse listener for field's click
	 */
	public MinefieldPanel(Minefield minefield, MouseListener fieldClickListener) {
		Size gameSize = minefield.getSize();
		
		GridLayout grid = new GridLayout(gameSize.getHeight(), gameSize.getWidth());
		_fields = new FieldButton[gameSize.getHeight()][gameSize.getWidth()];
		setLayout(grid);
		setSize(gameSize.getWidth() * FieldButton.BUTTON_SIZE.width, gameSize.getHeight() * FieldButton.BUTTON_SIZE.height);
		
		setOpaque(false);	// transparent panel
		
		// initialize panel buttons
		for (int i = 0; i < _fields.length; i++) {
			for (int j = 0; j < _fields[i].length; j++) {
				_fields[i][j] = new FieldButton(i, j, minefield.getField(i, j));
				_fields[i][j].addMouseListener(fieldClickListener);
				
				add(_fields[i][j]);
			}
		}
	}
	
	/**
	 * open field button
	 * @param row
	 * @param col
	 */
	public void openField(int row, int col) {
		_fields[row][col].open();
	}

	/**
	 * get field button
	 * @param row
	 * @param col
	 * @return {@link FieldButton} object, part of the minefield buttons matrix
	 */
	public FieldButton getField(int row, int col) {
		return _fields[row][col];
	}
}
