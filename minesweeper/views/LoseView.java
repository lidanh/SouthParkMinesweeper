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
import minesweeper.views.components.MinefieldPanel;
import minesweeper.views.utils.AssetsManager;
import minesweeper.views.utils.ViewUtilities;

public class LoseView extends JPanel {
	private static final long serialVersionUID = -4435671725833546280L;
	
	// UI properties (static final for performance issues)
	private static final Image BACKGROUND_IMAGE = new ImageIcon(AssetsManager.getResource(LoseView.class, "bg.jpg")).getImage();
	private static final Color PANEL_BACKGROUND_COLOR = new Color(255,255,255,220);
	
	// View Components
	private MenuButton _newGameButton;
	private JPanel _minefieldPlaceHolder;
	
	/**
	 * create new {@link LoseView} view object
	 */
	public LoseView() {
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
		GridBagConstraints viewConstraints = new GridBagConstraints();
		
		// minefield place holder (holds the final last game board)
		_minefieldPlaceHolder = new JPanel();
		_minefieldPlaceHolder.setBackground(PANEL_BACKGROUND_COLOR);
		viewConstraints.anchor = GridBagConstraints.CENTER;
		viewConstraints.gridx = 0;
		viewConstraints.gridy = 0;
		viewConstraints.insets = new Insets(0, 0, 10, 0);
		add(_minefieldPlaceHolder, viewConstraints);
		
		// create new game button
		_newGameButton = new MenuButton("New Game");
		viewConstraints.anchor = GridBagConstraints.SOUTH;
		viewConstraints.gridy = 1;
		add(_newGameButton, viewConstraints);
	}
	
	/**
	 * set the game final board into the minefield placeholder panel
	 * @param minefieldPanel  game board from the {@link GameView} view
	 */
	public void placeMinefield(MinefieldPanel minefieldPanel) {
		// remove all the component from the placeholder and add the last game board
		_minefieldPlaceHolder.removeAll();
		_minefieldPlaceHolder.add(minefieldPanel);
	}
	
	/**
	 * add new game button listener
	 * @param newGameListener
	 */
	public void addNewGameListener(ActionListener newGameListener) {
		_newGameButton.addActionListener(newGameListener);
	}
}
