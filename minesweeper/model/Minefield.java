package minesweeper.model;

/**
 *  Minefield model- holds a matrix of fields
 *  @see Field
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class Minefield {
	private int _flags;
	private int _correctFlags;
	private int _mines;
	private Size _size;
	private Field[][] _board;
	
	/**
	 * create a new {@link Minefield} object for a given width, height and total mines
	 * @param width
	 * @param height
	 * @param mines
	 */
	public Minefield(int width, int height, int mines) {
		_size = new Size(width, height);
		_mines = mines;
		_flags = mines;
		_correctFlags = 0;
		initializeBoard();
	}
	
	/**
	 * create a new {@link Minefield} object for a given level
	 * @see Level
	 * @param level
	 */
	public Minefield(Level level) {
		this(level.getSize().getWidth(), level.getSize().getHeight(), level.getMines());
	}
	
	/**
	 * initialize the {@link Minefield} board
	 */
	private void initializeBoard() {
		_board = new Field[_size.getHeight()][_size.getWidth()];
		
		for (int i = 0; i < _board.length; i++) {
			for (int j = 0; j < _board[i].length; j++) {
				_board[i][j] = new Field();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < _board.length; i++) {
			for (int j = 0; j < _board[i].length; j++) {
				sb.append(_board[i][j].getValue() + " ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * get total flags
	 * @return flags
	 */
	public int getFlags() {
		return _flags;
	}
	
	/**
	 * get correct flags
	 * @return correct flags
	 */
	public int getCorrectFlags() {
		return _correctFlags;
	}
	
	/**
	 * determine if game has finished. it finishes when the correct flags is equal to the number of mines
	 * @return true if finished, false if not
	 */
	public boolean finishGame() {
		return _correctFlags == _mines;
	}
	
	/**
	 * increment correct flags by 1
	 */
	public void incrementCorrectFlags() {
		_correctFlags++;
	}
	
	/**
	 * increment total flags by 1
	 */
	public void incrementFlags() {
		if (_flags < _mines)
			_flags++;
	}
	
	/**
	 * decrement total flags by 1
	 */
	public void decrementFlags() {
		if (_flags >= 0)
			_flags--;
	}
	
	/**
	 * get total mines
	 * @return total mines
	 */
	public int getMines() {
		return _mines;
	}
	
	/**
	 * get board size
	 * @see Size
	 * @return board size object
	 */
	public Size getSize() {
		return _size;
	}
	
	/**
	 * get the game board- a matrix of {@link Field} objects
	 * @return
	 */
	public Field[][] getBoard() {
		return _board;
	}

	/**
	 * get a specific {@link Field} for a given row & column indexes
	 * @param row
	 * @param col
	 * @return {@link Field} object
	 */
	public Field getField(int row, int col) {
		return _board[row][col];
	}
}