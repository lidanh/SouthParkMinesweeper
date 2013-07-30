package minesweeper.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import minesweeper.views.utils.AssetsManager;

public class WinView extends JPanel {
	private static final long serialVersionUID = 4198225274957353247L;
	
	// UI properties (static final for performance issues)
	private static final Color BACKGROUND_COLOR = new Color(226, 78, 37);
	private static final Font TEXT_FONT = AssetsManager.getBaseFont().deriveFont(Font.PLAIN, 22);
	private static final Color PLAYER_NAME_TEXT_FIELD_BORDER = new Color(250, 194, 142);
	private static final ImageIcon WIN_IMAGE = new ImageIcon(AssetsManager.getResource(WinView.class, "top.png"));
	
	// View Components
	private JTextField _playerNameTextField;
	private JButton _continueButton;
	private JLabel _timeLabel;
	
	/**
	 * create new {@link WinView} view object
	 */
	public WinView() {
		setVisible(false);
		initializeUI();
	}
	
	/**
	 * initialize view components
	 */
	private void initializeUI() {
		setBackground(BACKGROUND_COLOR);
		setLayout(new GridBagLayout());
		GridBagConstraints viewConstraints = new GridBagConstraints();
		viewConstraints.insets = new Insets(10, 0, 10, 0);
		
		// total time labels
		JLabel timeTitleLabel = new JLabel("Total Time:");
		timeTitleLabel.setFont(TEXT_FONT);
		viewConstraints.gridx = 1;
		viewConstraints.gridy = 0;
		add(timeTitleLabel, viewConstraints);
		_timeLabel = new JLabel();
		viewConstraints.gridy = 1;
		_timeLabel.setFont(TEXT_FONT);
		add(_timeLabel, viewConstraints);
		
		// player name text field
		_playerNameTextField = new JTextField(10);
		_playerNameTextField.setFont(TEXT_FONT);
		Border line = BorderFactory.createLineBorder(PLAYER_NAME_TEXT_FIELD_BORDER);
		Border empty = new EmptyBorder(5, 5, 5, 5);
		CompoundBorder border = new CompoundBorder(line, empty);
		_playerNameTextField.setBorder(border);
		_playerNameTextField.setBackground(BACKGROUND_COLOR);
		viewConstraints.gridy = 2;
		add(_playerNameTextField, viewConstraints);
		
		// continue button
		_continueButton = new JButton("Continue");
		viewConstraints.gridy = 3;
		add(_continueButton, viewConstraints);
		
		// add win image
		JLabel winLabel = new JLabel(WIN_IMAGE);
		viewConstraints.gridx = 0;
		viewConstraints.gridy = 0;
		viewConstraints.gridheight = 4;
		add(winLabel, viewConstraints);
	}
	
	/**
	 * add save & continue event listener
	 * @param continueListener click listener
	 */
	public void addContinueListener(ActionListener continueListener) {
		_continueButton.addActionListener(continueListener);
	}
	
	/**
	 * get player name from the player name text field
	 * @return player name
	 */
	public String getPlayerName() {
		return _playerNameTextField.getText();
	}
	
	/**
	 * set player name
	 * @param name player name
	 */
	public void setPlayerName(String name) {
		_playerNameTextField.setText(name);
	}
	
	/**
	 * set total game time
	 * @param time game time
	 */
	public void setTime(String time) {
		_timeLabel.setText(time);
	}
}
