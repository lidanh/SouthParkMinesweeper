package minesweeper.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.SwingUtilities;

import minesweeper.model.Field;
import minesweeper.model.FieldState;
import minesweeper.model.FieldValue;
import minesweeper.model.HighscoreRecord;
import minesweeper.model.Highscores;
import minesweeper.model.Level;
import minesweeper.model.Minefield;
import minesweeper.views.AppWindow;
import minesweeper.views.ChooseLevelView;
import minesweeper.views.GameView;
import minesweeper.views.HighscoresView;
import minesweeper.views.LoseView;
import minesweeper.views.PauseView;
import minesweeper.views.WinView;
import minesweeper.views.components.FieldButton;
import minesweeper.views.components.LevelPanel;
import minesweeper.views.components.MinefieldPanel;
import minesweeper.views.fx.ShakeEffect;
import minesweeper.views.fx.SoundPlayer;
import minesweeper.views.utils.AssetsManager;

/**
 *  Game Engine - The Controller part of the MVC Architecture
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class GameEngine implements Runnable {
	private Minefield _minefield;	// minefield model data (the game matrix)
	private GameTimer _timer;		// game timer
	private boolean _onGame;		// if game is running or not
	private AppWindow _window;		// the main application window
	private GameView _gameView;		// the game view panel
	private Level _level;			// the current level
	private Highscores _highscores;	// the highscores model data
	private String _lastPlayerName = "Player 1";	// last player name
	private static final String HIGHSCORES_FILE = "Highscores";	// highscores file name

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		_window.setVisible(true);
	}

	/**
	 * create new Game Engine
	 */
	public GameEngine() {
		// initialize- set initial level to easy, load highscores, generate new minefield for the chosen level, update UI properties for the chosen level
		_level = Level.easy();
		AssetsManager.updateLevelUI(_level);
		loadHighscores();
		_minefield = generateMinefield();

		// create application window, and show the game view
		_window = new AppWindow(new MinefieldPanel(_minefield, new FieldClickListener()));
		_gameView = _window.getGameView();
		_gameView.updateLevelUI();
		_timer = new GameTimer(_gameView.getTimerLabel());
		_window.setWindowInScreenCenter();
		_window.showView(_gameView);

		// add views event listeners (connects the controller and the views)
		final ChooseLevelView levelView = _window.getLevelView();
		levelView.addChooseLevelListener(new ChooseLevelListener());
		levelView.addCustomLevelListener(new CustomLevelListener());

		final PauseView pauseView = _window.getPauseView();
		pauseView.addResumeListener(new ResumeListener());
		pauseView.addNewGameListener(new NewGameListener());
		pauseView.addExitListener(new ExitApplicationListener());
		pauseView.addHighscoresListener(new ShowHighscoresListener());
		pauseView.addChooseLevelListener(new ShowLevelViewListener());

		final LoseView loseView = _window.getLoseView();
		loseView.addNewGameListener(new NewGameListener());

		final HighscoresView highscoresView = _window.getHighscoresView();
		highscoresView.setHighscores(_highscores);
		highscoresView.addBackListener(new PauseListener());

		_window.getWinView().addContinueListener(new WinGameContinueListener());
		_gameView.setPauseListener(new PauseListener());

		resetUIForNewGame();		
	}

	/**
	 * save the highscores records to the disk
	 */
	private void saveHighscores() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(HIGHSCORES_FILE);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(_highscores.getRecords());
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * load the highscores file and initialize new highscores model object with the loaded records
	 */
	@SuppressWarnings("unchecked")
	private void loadHighscores() {
		try {
			// try load the highscores file
			File highscoresFile = new File(HIGHSCORES_FILE);
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(highscoresFile));
			_highscores = new Highscores(((List<HighscoreRecord>)objectInputStream.readObject()));
			objectInputStream.close();
		} catch (EOFException fileNotFoundException) {
			// in case of exception- initialize new highscores object
			_highscores = new Highscores();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			_highscores = new Highscores();
		}
	}

	/**
	 * generate new {@link Minefield} for the current {@link Level}
	 * @return new empty {@link Minefield} for a new game
	 */
	private Minefield generateMinefield() {
		return new Minefield(_level);
	}

	/**
	 * assign random mines, in the beginning of the game
	 * @param ignoreField the field which represented by the {@link FieldButton} the user click on. we need this field to validate that no mine will be there when assigning the mines.
	 */
	private void assignRandomMines(Field ignoreField) {
		int remainingMines = _minefield.getMines();

		while (remainingMines > 0) {
			// generate random row & column indexes
			int randomRow = (int) (Math.random() * (_minefield.getSize().getHeight()));
			int randomCol = (int) (Math.random() * (_minefield.getSize().getWidth()));

			// get field model
			Field field = _minefield.getField(randomRow, randomCol);

			// if the field is not ignored, and the field value is not equals to mine
			if (!field.getValue().equals(FieldValue.MINE) && field != ignoreField) {
				field.setValue(FieldValue.MINE);  // place a mine inside the field

				// increment the numbers around this field
				for (int dr = -1; dr <= 1; dr++) {
					for (int dc = -1; dc <= 1; dc++) {
						if (inBoard(randomRow + dr, randomCol + dc)) {
							Field f = _minefield.getField(randomRow + dr, randomCol + dc);
							f.setValue(f.getValue().nextValue());
						}
					}
				}

				remainingMines--;
			}
		}
	}

	/**
	 * open all the fields in the board (in case of GAME OVER for example)
	 */
	private void openAllFields() {
		for (int i = 0; i < _minefield.getSize().getHeight(); i++) {
			for (int j = 0; j < _minefield.getSize().getWidth(); j++) {
				_gameView.openField(i, j);
			}
		}
	}

	/**
	 * open a specific field
	 * @param fieldButton to open
	 */
	private void openField(FieldButton fieldButton) {
		// open the field only if is enable and not flagged
		if (fieldButton.isEnabled() && !fieldButton.isFlagged()) {
			if (fieldButton.isMine()) {
				// if the field is mine - lose the game
				_onGame = false;
				_timer.stop();
				SwingUtilities.invokeLater(new ShakeEffect(_window)); 	// shake the window
				SoundPlayer.playLoseGameSound();
				openAllFields();	// open all fields
				_window.getLoseView().placeMinefield(_gameView.getMinefieldPanel());
				fieldButton.setMineClickIcon(); // change the icon of the field that triggered the explosion
				_window.showView(_window.getLoseView());	// show lose view
			} else {
				// if the field is not mine - clear it recursively
				clearField(fieldButton.getRow(), fieldButton.getCol());
				_gameView.repaint();
			}
		}
	}

	/**
	 * clear the field recursively
	 * @param row	field row
	 * @param col	field column
	 */
	private void clearField(int row, int col) {
		if (inBoard(row, col)) {	// if the field is in board
			Field field = _minefield.getField(row, col);	//	get the field data
			if (!field.getValue().equals(FieldValue.MINE) && !field.getState().equals(FieldState.FLAGGED) && !field.getState().equals(FieldState.OPENED)) {
				// open the field if is not mine, and if was not flagged yet, and if was not opened yet
				_gameView.openField(row, col);
				field.setState(FieldState.OPENED);

				// open fields recursively only if field value is zero
				if (field.getValue().equals(FieldValue.ZERO)) {
					for (int dr = -1; dr <= 1; dr++) {
						for (int dc = -1; dc <= 1; dc++) {
							clearField(row + dr, col + dc);
						}
					}
				}
			}
		}
	}

	/**
	 * BONUS PART
	 * clear all the fields recursively around an opened & number field button.
	 * if the cell shows a number (denote it by k), and the user has marked
	 * k adjacent cells of it, then the rest adjacent cells are opened. 
	 * This may end the game if the user was wrong. On the other hand, 
	 * if the user has marked fewer than k cells then the adjacent cells are
	 * not opened, although the user sees them pressed temporarily
	 * @param e mouse event
	 */
	private void clearFieldsAround(MouseEvent e) {
		FieldButton fieldButton = ((FieldButton)e.getSource());

		// only if the button is disabled, opened and its value is a number
		if (!fieldButton.isEnabled() && fieldButton.getField().getState().equals(FieldState.OPENED) && !fieldButton.getValue().equals(FieldValue.MINE) && !fieldButton.getValue().equals(FieldValue.ZERO)) {
			// count the flags around the button
			int expectedFlags = Integer.parseInt(fieldButton.getValue().toString());
			int totalFlags = 0;

			for (int dr = -1; dr <= 1 && totalFlags <= expectedFlags; dr++) {
				for (int dc = -1; dc <= 1 && totalFlags <= expectedFlags; dc++) {
					if (inBoard(fieldButton.getRow() + dr, fieldButton.getCol() + dc) && _minefield.getField(fieldButton.getRow() + dr, fieldButton.getCol() + dc).getState().equals(FieldState.FLAGGED))
						totalFlags++;
				}
			}

			// if the flags around the button is equals to the field's number- open the fields recursively
			if (totalFlags >= expectedFlags) {
				for (int dr = -1; dr <= 1; dr++) {
					for (int dc = -1; dc <= 1; dc++) {
						if (inBoard(fieldButton.getRow() + dr, fieldButton.getCol() + dc))
							openField(_gameView.getField(fieldButton.getRow() + dr, fieldButton.getCol() + dc));
					}
				}
			}
		}
	}

	/**
	 * create a new game
	 * generate new minefield for the current level, redraw the minefield panel, show game view, reset UI
	 */
	private void createNewGame() {
		_onGame = false;
		_minefield = generateMinefield();
		_gameView.redrawMinefieldPanel(new MinefieldPanel(_minefield, new FieldClickListener()));
		_window.showView(_gameView);
		resetUIForNewGame();
	}

	/**
	 * reset UI for new game:
	 * reset the timer
	 * show resume button on {@link PauseView}
	 * play new game sound
	 * pack the window
	 */
	private void resetUIForNewGame() {
		_timer.reset();
		_window.getPauseView().toggleResumeButton(true);
		_gameView.setFlags(_minefield.getFlags());
		SoundPlayer.playNewGameSound();
		_window.pack();
	}

	/**
	 * flag a field
	 * @param e
	 */
	private void flagField(MouseEvent e) {
		FieldButton fieldButton = ((FieldButton)e.getSource());

		if (fieldButton.isEnabled()) {	// if the field button is enabled
			if (fieldButton.isUnflagged() && _minefield.getFlags() > 0) {	// and the field button was not flagged yet, and the flags > 0
				// mark the field as FLAGGED
				fieldButton.flag();
				_minefield.decrementFlags();
				SoundPlayer.playFlagSound();
				// if it is correct flag (there is a mine in this field)- increment the correct flags counter
				if (fieldButton.getField().getValue().equals(FieldValue.MINE)) {
					_minefield.incrementCorrectFlags();
				}
			} else if (fieldButton.isFlagged()) {
				// unflag the field
				fieldButton.unflag();
				_minefield.incrementFlags();
				SoundPlayer.playUnflagSound();
			}
		}

		// update flags label
		_gameView.setFlags(_minefield.getFlags());
	}

	/**
	 * win the game:
	 * stop the timer
	 * hide the resume button from {@link PauseView} view
	 * set player name & play time and show {@link WinView} view
	 */
	private void winGame() {
		_timer.stop();
		_onGame = false;
		_window.getPauseView().toggleResumeButton(false);
		WinView view = _window.getWinView();
		view.setPlayerName(_lastPlayerName);
		view.setTime(_timer.toString());
		_window.showView(view);
		SoundPlayer.playWinGameSound();
	}

	/**
	 * create a new highscore record for the current game
	 * @param playerName player name
	 */
	private void saveHighscoreRecord(String playerName) {
		_lastPlayerName = playerName;
		_highscores.addRecord(new HighscoreRecord(playerName, _timer.toString(), _level));
		saveHighscores();
	}

	/**
	 * change the game level
	 * @param level
	 */
	private void setLevel(Level level) {
		// change the level & update the UI only if a different level was chosen
		if (_level != level) {
			_level = level;
			AssetsManager.updateLevelUI(_level);
			_gameView.updateLevelUI();
			_window.getLevelView().updateBackground();
			_window.setWindowInScreenCenter();
		}
	}

	/**
	 * determine if a given cell (row & column) is in the game board
	 * @param row
	 * @param col
	 * @return true if is in the game board, false if not
	 */
	private boolean inBoard(int row, int col) {
		return (row >= 0 && row < _minefield.getSize().getHeight() && col >= 0 && col < _minefield.getSize().getWidth());
	}

	/**
	 * show {@link HighscoresView} view
	 */
	private void showHighscores() {
		_highscores.sort();
		_window.showView(_window.getHighscoresView());
		_window.pack();
	}

	/**
	 *  New Game Listener
	 *  @author     Lidan Hifi
	 *  @version    1.0
	 */
	class NewGameListener extends MouseAdapter implements ActionListener {
		@Override
		public void mouseReleased(MouseEvent e) {
			createNewGame();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			createNewGame();
		}

	}

	/**
	 *  Create Custom Level Listener
	 *  @author     Lidan Hifi
	 *  @version    1.0
	 */
	class CustomLevelListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			ChooseLevelView view = _window.getLevelView();
			setLevel(Level.custom(view.getCustomWidth(), view.getCustomHeight(), view.getCustomMines()));
			createNewGame();
		}
	}

	/**
	 *  Choose Level & Create New Game Listener
	 *  @author     Lidan Hifi
	 *  @version    1.0
	 */
	class ChooseLevelListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			LevelPanel source = (LevelPanel)e.getSource();
			setLevel(source.getLevel());
			createNewGame();
		}
	}

	/**
	 *  Show level view Listener
	 *  @author     Lidan Hifi
	 *  @version    1.0
	 */
	class ShowLevelViewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			_window.showView(_window.getLevelView());
			_window.pack();
		}
	}

	/**
	 *  Field Click Listener
	 *  @author     Lidan Hifi
	 *  @version    1.0
	 */
	class FieldClickListener extends MouseAdapter {
		/**
		 * BONUS PART - right & left click simultaneously
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			int bothMask = InputEvent.BUTTON1_DOWN_MASK | InputEvent.BUTTON3_DOWN_MASK;
			if ((e.getModifiersEx() & bothMask) == bothMask){
				clearFieldsAround(e);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {	//	Left click = open a field
				FieldButton fieldButton = ((FieldButton)e.getSource());

				// Assigning random mines & start the timer in the first click
				if (!_onGame) {
					assignRandomMines(((FieldButton)e.getSource()).getField());
					_onGame = true;
					_timer.start();
				}
				openField(fieldButton);
			} else if (SwingUtilities.isRightMouseButton(e)) { //	Right click = flag a field
				flagField(e);
			} else if (SwingUtilities.isMiddleMouseButton(e)) { // 	Middle click = open fields around the pressed field
				clearFieldsAround(e);
			}

			if (_minefield.getCorrectFlags() == _minefield.getMines()) {
				winGame();	// if the correct flags is equals to total mines
			}

		}
	}

	/**
	 *  Win View - Save Highscores Record Listener
	 *  @author     Lidan Hifi
	 *  @version    1.0
	 */
	class WinGameContinueListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// save highscores record and show the highscores view
			saveHighscoreRecord(_window.getWinView().getPlayerName());
			showHighscores();
		}
	}

	/**
	 *  Pause game Listener
	 *  @author     Lidan Hifi
	 *  @version    1.0
	 */
	class PauseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// stop the timer and show the pause view
			if (_onGame)
				_timer.stop();

			_window.showView(_window.getPauseView());
		}
	}

	/**
	 *  Resume Game Listener
	 *  @author     Lidan Hifi
	 *  @version    1.0
	 */
	class ResumeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// resume the timer and show the game view
			if (_onGame)
				_timer.start();

			_window.showView(_gameView);
			_window.pack();
		}
	}

	/**
	 *  Exit Application Listener
	 *  @author     Lidan Hifi
	 *  @version    1.0
	 */
	class ExitApplicationListener extends WindowAdapter implements ActionListener, MouseListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}

		@Override
		public void mouseClicked(MouseEvent e) { }

		@Override
		public void mousePressed(MouseEvent e) { }

		@Override
		public void mouseReleased(MouseEvent e) {
			System.exit(0);
		}

		@Override
		public void mouseEntered(MouseEvent e) { }

		@Override
		public void mouseExited(MouseEvent e) { }

	}

	/**
	 *  Show Highscores View Listener
	 *  @author     Lidan Hifi
	 *  @version    1.0
	 */
	class ShowHighscoresListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// show highscores view
			showHighscores();
		}
	}
}
