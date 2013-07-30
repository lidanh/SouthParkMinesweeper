package minesweeper.model;

/**
 *  Field Model- represent a cell which can contains zero, number or a mine
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class Field {
	private FieldValue _value;
	private FieldState _state;
	
	/**
	 * create a field
	 */
	public Field() {
		_value = FieldValue.ZERO;
		_state = FieldState.UNFLAGGED;
	}
	
	/**
	 * get field state
	 * @see FieldState
	 * @return field state
	 */
	public FieldState getState() {
		return _state;
	}
	
	/**
	 * set field state
	 * @see FieldState
	 * @param state field state
	 */
	public void setState(FieldState state) {
		_state = state;
	}
	
	/**
	 * get field value
	 * @see FieldValue
	 */
	public FieldValue getValue() {
		return _value;
	}
	
	/**
	 * set field value
	 * @see FieldValue
	 * @param value
	 */
	public void setValue(FieldValue value) {
		_value = value;
	}
}