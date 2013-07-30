package minesweeper.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import minesweeper.model.Level;
import minesweeper.views.components.LevelPanel;
import minesweeper.views.utils.AssetsManager;
import minesweeper.views.utils.ViewUtilities;

/**
 *  Choose Level View
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class ChooseLevelView extends JPanel {
	private static final long serialVersionUID = 1462285316565995421L;
	
	// UI properties (static final for performance issues)
	private static final Color TEXT_COLOR = Color.white;
	private static final Font TEXT_FONT = AssetsManager.getBaseFont().deriveFont(Font.PLAIN, 14);
	
	// View Components
	private Image _background;
	private LevelPanel _easyPanel;
	private LevelPanel _intermediatePanel;
	private LevelPanel _expertPanel;
	private LevelPanel _customPanel;
	
	private JPanel _customSpinnersPanel;
	private JSpinner _heightSpinner;
	private JSpinner _widthSpinner;
	private JSpinner _minesSpinner;
	private JButton _startCustomButton;
	
	/**
	 * create new {@link ChooseLevelView} View
	 */
	public ChooseLevelView() {
		setVisible(false);
		initializeUI();
		addInternalListeners();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		// scale the background image and paint it in the view's center
		Image scaledImage = ViewUtilities.getScaleImage(_background, getSize());
		int x = (this.getWidth() - scaledImage.getWidth(null)) / 2;
	    int y = (this.getHeight() - scaledImage.getHeight(null)) / 2;
	    g.drawImage(scaledImage, x, y, null);
	}
	
	/**
	 * change the view background to the current level's background image
	 */
	public void updateBackground() {
		_background = new ImageIcon(AssetsManager.getResource(this.getClass(), AssetsManager.getCurrentLevelPrefix() + ".jpg")).getImage();
	}
	
	/**
	 * initialize view components
	 */
	private void initializeUI() {
		updateBackground();
		setForeground(TEXT_COLOR);
		
		// create Level Panels
		_easyPanel = new LevelPanel(Level.easy(), LevelPanel.VERTICAL_PANEL);
		_intermediatePanel = new LevelPanel(Level.intermediate(), LevelPanel.VERTICAL_PANEL);
		_expertPanel = new LevelPanel(Level.expert(), LevelPanel.VERTICAL_PANEL);
		
		// Custom Level Panel
		_customSpinnersPanel = new JPanel();
		_widthSpinner = new JSpinner();
		_widthSpinner.setModel(new SpinnerNumberModel(9, 9, 30, 1));
		_customSpinnersPanel.add(_widthSpinner);
		JLabel xLabel = new JLabel(" x ");
		xLabel.setFont(TEXT_FONT);
		_customSpinnersPanel.add(xLabel);
		_heightSpinner = new JSpinner();
		_heightSpinner.setModel(new SpinnerNumberModel(9, 9, 30, 1));
		_customSpinnersPanel.add(_heightSpinner);
		JLabel withLabel = new JLabel(" with ");
		withLabel.setFont(TEXT_FONT);
		_customSpinnersPanel.add(withLabel);
		_minesSpinner = new JSpinner();
		_customSpinnersPanel.add(_minesSpinner);
		_minesSpinner.setModel(new SpinnerNumberModel(10, 10, maximumCustomMinesValue(), 1));
		JLabel minesLabel = new JLabel(" mines ");
		minesLabel.setFont(TEXT_FONT);
		_customSpinnersPanel.add(minesLabel);
		_startCustomButton = new JButton("Start");
		_customSpinnersPanel.add(_startCustomButton);
		_customPanel = new LevelPanel(Level.customEmpty(), LevelPanel.HORIZONTAL_PANEL, _customSpinnersPanel);
		
		// View Layout
		setLayout(new GridBagLayout());
		GridBagConstraints viewConstraints = new GridBagConstraints();
		viewConstraints.anchor = GridBagConstraints.CENTER;
		viewConstraints.insets = new Insets(20, 20, 20, 20);
		
		viewConstraints.gridy = 0;
		viewConstraints.gridx = 0;
		add(_easyPanel, viewConstraints);			// add easy level panel
		
		viewConstraints.gridx = 1;
		add(_intermediatePanel, viewConstraints);	// add intermediate level panel
		
		viewConstraints.gridx = 2;
		add(_expertPanel, viewConstraints);			// add expert level panel
		
		viewConstraints.gridx = 0;
		viewConstraints.gridy = 1;
		viewConstraints.gridwidth = 3;
		add(_customPanel, viewConstraints);			// add custom level panel
	}
	
	/**
	 * add view event listeners
	 */
	public void addInternalListeners() {
		// spinners change event for setting the maximum number of mines as a function of the board chosen size
		_widthSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateCustomMinesValue();
			}
		});
		
		_heightSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateCustomMinesValue();
			}
		});
	}
	
	/**
	 * update the custom mines spinner's maximum value, as a function of chosen board size
	 */
	public void updateCustomMinesValue() {
		// set the maxmimum number of mines in the mines spinner
		((SpinnerNumberModel)_minesSpinner.getModel()).setMaximum(maximumCustomMinesValue());
		
		// if we change only the width & height- update the mines value automatically
		if (maximumCustomMinesValue() < getCustomMines()) {
			_minesSpinner.setValue(maximumCustomMinesValue());
		}
	}
	
	/**
	 * get max custom mines: width * height - 2.
	 * if it's (-1), we open one cell and flag all the others.
	 * so when it's (-2), it is almost impossible to win the game (actually, there is probability of 0.02 to win, and 0.98 to lose)
	 * @return maximum number of mines, based on the chosen board size
	 */
	private int maximumCustomMinesValue() {
		return getCustomHeight() * getCustomWidth() - 2;
	}
	
	/**
	 * get custom level mines spinner value
	 * @return - custom level mines
	 */
	public int getCustomMines() {
		return (Integer)_minesSpinner.getValue();
	}
	
	/**
	 * get custom level height spinner value
	 * @return custom level height
	 */
	public int getCustomHeight() {
		return (Integer)_heightSpinner.getValue();
	}
	
	/**
	 * get custom level width spinner value
	 * @return custom level width
	 */
	public int getCustomWidth() {
		return (Integer)_widthSpinner.getValue();
	}
	
	/**
	 * add custom level listener- mouse click on "Start Custom Game" button
	 * @param customLevelListener
	 */
	public void addCustomLevelListener(MouseListener customLevelListener) {
		_startCustomButton.addMouseListener(customLevelListener);
	}
	
	/**
	 * add choose level listener- mouse click on level panel and initialize a new game for the chosen level
	 * @param chooseLevelListener
	 */
	public void addChooseLevelListener(MouseListener chooseLevelListener) {
		_easyPanel.addMouseListener(chooseLevelListener);
		_intermediatePanel.addMouseListener(chooseLevelListener);
		_expertPanel.addMouseListener(chooseLevelListener);
	}
}