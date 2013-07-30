package minesweeper.model;

import java.io.Serializable;

/**
 *  Highscore record- contains player name, level name, time and difficulty factor.
 *  The difficulty factor is the ratio between the mines count and the board size,
 *  and the highscores is ordered by this field.
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class HighscoreRecord implements Comparable<HighscoreRecord>, Serializable {
	
	private static final long serialVersionUID = 1285629130672686246L;
	private String _time;
	private String _playerName;
	private String _levelName;
	private double _difficultyFactor;
	
	/**
	 * create a new record
	 * @param playerName
	 * @param time
	 * @param level
	 * @see Level
	 */
	public HighscoreRecord(String playerName, String time, Level level) {
		_time = time;
		_playerName = playerName;
		_levelName = level.getLevelName();
		_difficultyFactor = (double)level.getMines() / (double)(level.getSize().getHeight() * level.getSize().getWidth());
	}
	
	/**
	 * get total time
	 * @return total time
	 */
	public String getTime() {
		return _time;
	}
	
	/**
	 * get player name
	 * @return player name
	 */
	public String getPlayerName() {
		return _playerName;
	}
	
	/**
	 * get level name
	 * @return level name
	 */
	public String getLevelName() {
		return _levelName;
	}
	
	/**
	 * compare records by difficulty factor and by time (for a two equal factors)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(HighscoreRecord o) {
		if (_difficultyFactor < o._difficultyFactor) {
			return -1;
		} else if (_difficultyFactor == o._difficultyFactor) {
			return _time.compareTo(o.getTime());
		} else {
			return 1;
		}
	}
}