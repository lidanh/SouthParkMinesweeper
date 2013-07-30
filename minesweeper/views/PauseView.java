package minesweeper.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import minesweeper.views.components.MenuButton;
import minesweeper.views.utils.AssetsManager;
import minesweeper.views.utils.ViewUtilities;

public class PauseView extends JPanel {
	private static final long serialVersionUID = -829555363674215407L;
	
	// UI properties (static final for performance issues)
	private static final Image BACKGROUND_IMAGE = new ImageIcon(AssetsManager.getResource(PauseView.class, "bg.png")).getImage();
	
	// View Components
	private MenuButton _resumeButton;
	private MenuButton _newGameButton;
	private MenuButton _highscoresButton;
	private MenuButton _changeLevelButton;
	private MenuButton _exitButton;
	
	/**
	 * create new {@link PauseView} view object
	 */
	public PauseView() {
		setVisible(false);
		initializeUI();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		// scale the background image and paint it in the view's center
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		Image scaledImage = ViewUtilities.getScaleImage(BACKGROUND_IMAGE, getSize());
		int x = (this.getWidth() - scaledImage.getWidth(null)) / 2;
	    int y = (this.getHeight() - scaledImage.getHeight(null)) / 2;
	    g.drawImage(scaledImage, x, y, null);
	}
	
	/**
	 * initialize view components
	 */
	private void initializeUI() {		
		setLayout(new GridBagLayout());
		
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		
		layoutConstraints.anchor = GridBagConstraints.CENTER;
		layoutConstraints.insets = new Insets(0, 0, 30, 0);
		layoutConstraints.gridx = 0;
		
		// resume button
		_resumeButton = new MenuButton("Resume");
		layoutConstraints.gridy = 1;
		add(_resumeButton, layoutConstraints);
		
		// new game button
		_newGameButton = new MenuButton("New Game");
		layoutConstraints.gridy = 2;
		add(_newGameButton, layoutConstraints);
		
		// choose level button
		_changeLevelButton = new MenuButton("Choose Level");
		layoutConstraints.gridy = 3;
		add(_changeLevelButton, layoutConstraints);
		
		// highscores button
		_highscoresButton = new MenuButton("Highscores");
		layoutConstraints.gridy = 4;
		add(_highscoresButton, layoutConstraints);
		
		// exit game button
		_exitButton = new MenuButton("Exit");
		layoutConstraints.gridy = 5;
		add(_exitButton, layoutConstraints);
	}
	
	/**
	 * toggle resume menu button (button is visible when the user pause the game, hidden when come to the menu from {@link LoseView} view)
	 * @param value
	 */
	public void toggleResumeButton(boolean value) {
		_resumeButton.setVisible(value);
	}
	
	/**
	 * add resume game menu button listener
	 * @param resumeListener
	 */
	public void addResumeListener(ActionListener resumeListener) {
		_resumeButton.addActionListener(resumeListener);
	}	
	
	/**
	 * add new game menu button listener
	 * @param newGameListener
	 */
	public void addNewGameListener(ActionListener newGameListener) {
		_newGameButton.addActionListener(newGameListener);
	}
	
	/**
	 * add choose level menu button listener
	 * @param chooseLevelListener
	 */
	public void addChooseLevelListener(ActionListener chooseLevelListener) {
		_changeLevelButton.addActionListener(chooseLevelListener);
	}
	
	/**
	 * add highscores menu button listener
	 * @param highscoresListener
	 */
	public void addHighscoresListener(ActionListener highscoresListener) {
		_highscoresButton.addActionListener(highscoresListener);
	}

	/**
	 * add exit menu button listener
	 * @param exitListener
	 */
	public void addExitListener(ActionListener exitListener) {
		_exitButton.addActionListener(exitListener);
	}
}
