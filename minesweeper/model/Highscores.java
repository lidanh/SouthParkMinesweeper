package minesweeper.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 *  Highscores table data model
 *  it extends {@link AbstractTableModel} so the JTable can show the data simply.
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class Highscores extends AbstractTableModel {

	private static final long serialVersionUID = 5482408105017180966L;
	public final static int RECORDS = 10;
	private List<HighscoreRecord> _records;
	private final String[] _columns = { "Player Name", "Level", "Time" };
	
	/**
	 * create a new highscores table
	 */
	public Highscores() {
		_records = new ArrayList<HighscoreRecord>();
	}
	
	/**
	 * create a new highscores table for a given list of records (when we load the highscores from the disk)
	 * @param records
	 */
	public Highscores(List<HighscoreRecord> records) {
		_records = records;
	}
	
	/**
	 * get the table records
	 * @see HighscoreRecord
	 * @return table records
	 */
	public List<HighscoreRecord> getRecords() {
		return _records;
	}
	
	/**
	 * add a record to the highscores table
	 * @see HighscoreRecord
	 * @param record
	 */
	public void addRecord(HighscoreRecord record) {
		_records.add(record);
		Collections.sort(_records);
		
		// if the table contains more then RECORDS elements
		if (_records.size() > RECORDS) {
			_records.removeAll(_records.subList(RECORDS, _records.size()));
		}
	}
	
	/**
	 * sort the table using Collections.sort method
	 */
	public void sort() {
		Collections.sort(_records);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return _records.size();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return _columns.length;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return _records.get(rowIndex).getPlayerName();
		case 1:
			return _records.get(rowIndex).getLevelName();
		case 2:
			return _records.get(rowIndex).getTime();
		default:
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return _columns[column];
	}
}