package minesweeper.model;

/**
 *  Abstract size for sizable element (for example Board)
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class Size {
	private int _width, _height;
	
	/**
	 * create a new size object for a given width and height
	 * @param width
	 * @param height
	 */
	public Size(int width, int height) {
		this._width = width;
		this._height = height;
	}
	
	/**
	 * get width
	 * @return width
	 */
	public int getWidth() {
		return _width;
	}
	
	/**
	 * get height
	 * @return height
	 */
	public int getHeight() {
		return _height;
	}
}