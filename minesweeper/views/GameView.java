package minesweeper.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import minesweeper.controllers.GameTimer;
import minesweeper.views.components.FieldButton;
import minesweeper.views.components.MinefieldPanel;
import minesweeper.views.fx.SoundPlayer;
import minesweeper.views.utils.AssetsManager;

/**
 *  Game View
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class GameView extends JPanel {
	private static final long serialVersionUID = -3532742031193269836L;
	
	// UI properties (static final for performance issues)
	private static final Font TEXT_FONT = AssetsManager.getBaseFont().deriveFont(Font.PLAIN, 24);
	private static final ImageIcon PAUSE_ICON = new ImageIcon(AssetsManager.getResource(GameView.class, "pause.png"));
	private static final ImageIcon SPEAKER_ON_ICON = new ImageIcon(AssetsManager.getResource(GameView.class, "speaker_on.png"));
	private static final ImageIcon SPEAKER_OFF_ICON = new ImageIcon(AssetsManager.getResource(GameView.class, "speaker_off.png"));
	private static final Image DEFAULT_BACKGROUND =  new ImageIcon(AssetsManager.getResource(GameView.class, "bg.jpg")).getImage();
	
	// View Components
	private MinefieldPanel _minefieldPanel;
	private JLabel _timeLabel = new JLabel("");
	private JLabel _flagsIcon = new JLabel();
	private JLabel _flagsLabel = new JLabel("");
	private JPanel _minefieldPanelHolder = new JPanel();
	private JButton _pauseButton = new JButton(PAUSE_ICON);
	private Image _background;

	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		// draw the view background image
	    g.drawImage(_background, 0, 0, null);
	}
	
	/**
	 * create new {@link GameView} view object for a given minefield panel
	 * @param minefieldPanel game board
	 */
	public GameView(MinefieldPanel minefieldPanel) {
		_minefieldPanelHolder.setOpaque(false); // transparent minefield panel
		_minefieldPanel = minefieldPanel;
		initializeUI();
		redrawMinefieldPanel(minefieldPanel);
	}
	
	/**
	 * Initialize view components
	 */
	private void initializeUI() {
		// set transparent
	    _pauseButton.setOpaque(false);
	    _pauseButton.setContentAreaFilled(false);
	    _pauseButton.setBorderPainted(false);
	    
	    // time & flags labels
		_timeLabel.setFont(TEXT_FONT);
		_timeLabel.setPreferredSize(new Dimension(120, 10));
		_flagsLabel.setFont(TEXT_FONT);
		
		// view layout
		setLayout(new GridBagLayout());
		GridBagConstraints viewConstraints = new GridBagConstraints();
		viewConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		viewConstraints.insets = new Insets(0, 0, 0, 30);
		
		// buttons panel (LEFT SIDE)
		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);	// transparent panel
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(_pauseButton);
		JToggleButton speakerButton = new JToggleButton(SPEAKER_ON_ICON);
		speakerButton.setOpaque(false);
	    speakerButton.setContentAreaFilled(false);
	    speakerButton.setBorderPainted(false);
		speakerButton.setSelectedIcon(SPEAKER_OFF_ICON);
		speakerButton.addActionListener(new ActionListener() {		// toggle sound on/off click listener
			@Override
			public void actionPerformed(ActionEvent e) {
				SoundPlayer.toggleSound();
			}
		});
		leftPanel.add(speakerButton, viewConstraints);
		add(leftPanel, viewConstraints);	// add the left panel to the view
		
		
		// game panel (RIGHT SIDE)
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);	// transparent panel
		rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints rightConstraints = new GridBagConstraints();
		
		// game info (flags & time labels)
		JPanel gameInfoPanel = new JPanel(new GridLayout(1, 2));
		gameInfoPanel.setOpaque(false);
		JPanel flagsPanel = new JPanel();
		flagsPanel.setOpaque(false);
		
		flagsPanel.setLayout(new BoxLayout(flagsPanel, BoxLayout.X_AXIS));
		flagsPanel.add(_flagsIcon);
		flagsPanel.add(_flagsLabel);
		gameInfoPanel.add(flagsPanel);
		gameInfoPanel.add(_timeLabel);
		
		rightConstraints.gridx = 1;
		rightConstraints.gridy = 0;
		rightConstraints.anchor = GridBagConstraints.LINE_END;
		rightPanel.add(gameInfoPanel, rightConstraints);	// add the game info panel to the right panel
		
		// add the board placeholder to the right panel
		rightConstraints.gridx = 0;
		rightConstraints.gridy = 1;
		rightConstraints.gridwidth = 2;
		rightPanel.add(_minefieldPanelHolder, rightConstraints);
		
		add(rightPanel);	// add the right panel to the view
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#getInsets()
	 */
	@Override
	public Insets getInsets()
	{
		return new Insets(30,30,30,30);
	}
	
	/**
	 * update level background image & foreground labels color, based on the user level choose
	 */
	public void updateLevelUI() {
		// background image for specific level, or use default (catch block)
		try {
			_background = new ImageIcon(AssetsManager.getResource(GameView.class, AssetsManager.getCurrentLevelPrefix() + "_bg.jpg")).getImage();
		} catch (Exception e) {
			_background = DEFAULT_BACKGROUND;
		}
		
		// set labels foreground color
		_flagsLabel.setForeground(AssetsManager.getCurrentLevelForegroundColor());
		_timeLabel.setForeground(AssetsManager.getCurrentLevelForegroundColor());
	}

	/**
	 * get timer label (for initializing the timer- the label is the VIEW of {@link GameTimer})
	 * @return timer label
	 */
	public JLabel getTimerLabel() {
		return _timeLabel;
	}

	/**
	 * set flags value
	 * @param flags
	 */
	public void setFlags(int flags) {
		_flagsLabel.setText(String.valueOf(flags));
	}

	/**
	 * open field in the game
	 * @param row
	 * @param col
	 */
	public void openField(int row, int col) {
		_minefieldPanel.openField(row, col);
	}
	
	/**
	 * set pause button listener
	 * @param pauseListener
	 */
	public void setPauseListener(ActionListener pauseListener) {
		_pauseButton.addActionListener(pauseListener);
	}

	/**
	 * redraw the minefield panel.
	 * removes the old board and replace it with a new game board
	 * updates the flag icon for the current level 
	 * @param minefieldPanel
	 */
	public void redrawMinefieldPanel(MinefieldPanel minefieldPanel) {
		_minefieldPanel = minefieldPanel;
		_minefieldPanelHolder.removeAll();
		_minefieldPanelHolder.add(_minefieldPanel);
		_flagsIcon.setIcon(new ImageIcon(AssetsManager.getResource(FieldButton.class, AssetsManager.getCurrentLevelPrefix() + "_flag.png")));
		_minefieldPanelHolder.updateUI();
		repaint();
	}
	
	/**
	 * get the minefield panel (the board)
	 * @return {@link MinefieldPanel} object which contains the game board
	 */
	public MinefieldPanel getMinefieldPanel() {
		return _minefieldPanel;
	}

	/**
	 * get field button for a given row & column
	 * @param row button row
	 * @param col button column
	 * @return {@link FieldButton} object which is placed in the given row & column
	 */
	public FieldButton getField(int row, int col) {
		return _minefieldPanel.getField(row, col);
	}
}
