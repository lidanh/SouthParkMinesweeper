package minesweeper.model;

import java.awt.Color;

/**
 *  Game level
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class Level {
	private Size _size;
	private String _assetsPrefix;
	private String _name;
	private int _mines; 
	private Color _lablesColor = new Color(44,70,123); // default labels color
	
	/**
	 * create a new {@link Level} object. 
	 * this is a protected constructor, and new objects can initialize by public static methods
	 * @param width board width
	 * @param height board height
	 * @param mines total mines
	 * @param assetsPrefix assets prefix- for level theme (sounds, images, etc.)
	 * @param name level name
	 */
	protected Level(int width, int height, int mines, String assetsPrefix, String name) {
		_size = new Size(width, height);
		_mines = mines;
		_assetsPrefix = assetsPrefix;
		_name = name;
	}
	
	/**
	 * create a new {@link Level} object.
	 * @param assetsPrefix assets prefix- for level theme (sounds, images, etc.)
	 * @param name level name
	 */
	protected Level(String assetsPrefix, String name) {
		_assetsPrefix = assetsPrefix;
		_name = name;
	}
	
	/**
	 * change game labels color (time and flags labels)
	 * @see Color
	 * @param color
	 */
	private void setLablesColor(Color color) {
		_lablesColor = color;
	}
	
	/**
	 * get board size for this level
	 * @return board size
	 */
	public Size getSize() {
		return _size;
	}
	
	/**
	 * get total mines for this level
	 * @return total mines
	 */
	public int getMines() {
		return _mines;
	}
	
	/**
	 * get lables color for this level
	 * @see Color
	 * @return lables color (time & flags labels)
	 */
	public Color getColor() {
		return _lablesColor;
	}
	
	/**
	 * get level's assets prefix (for level theme)
	 * @return assets prefix as string
	 */
	public String getAssetsPrefix() {
		return _assetsPrefix;
	}
	
	/**
	 * get level name
	 * @return level name
	 */
	public String getLevelName() {
		return _name;
	}
	
	/**
	 * create a new easy {@link Level}
	 * @return an easy {@link Level}
	 */
	public static Level easy() {
		return new Level(9, 9, 10, "01", "Easy");
	}
	
	/**
	 * create a intermediate easy {@link Level}
	 * @return an intermediate {@link Level}
	 */
	public static Level intermediate() {
		return new Level(16, 16, 40, "02", "Intermediate");
	}
	
	/**
	 * create a new expert {@link Level}
	 * @return an expert {@link Level}
	 */
	public static Level expert() {
		Level expertLevel = new Level(30, 16, 99, "03", "Expert");
		expertLevel.setLablesColor(Color.white);
		return expertLevel;
	}
	
	/**
	 * create a new custom {@link Level} for a given width, height and mines
	 * @return a custom {@link Level}
	 */
	public static Level custom(int width, int height, int mines) {
		return new Level(width, height, mines, "c", "Custom");
	}
	
	/**
	 * create a new empty custom {@link Level} without any mines, and zero-sized
	 * @return an empty {@link Level}
	 */
	public static Level customEmpty() {
		return new Level("c", "Custom");
	}
}