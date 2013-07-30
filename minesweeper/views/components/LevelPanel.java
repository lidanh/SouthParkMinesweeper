package minesweeper.views.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import minesweeper.model.Level;
import minesweeper.views.ChooseLevelView;
import minesweeper.views.utils.AssetsManager;

/**
 *  Level Panel Component (for choose level view)
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class LevelPanel extends JPanel {
	private static final long serialVersionUID = -2202997374908577219L;
	
	// UI properties (static final for performance issues)
	public static final int HORIZONTAL_PANEL = 1;
	public static final int VERTICAL_PANEL = 2;
	private static final Font HEADER_FONT = AssetsManager.getBaseFont().deriveFont(Font.PLAIN, 24);
	private static final Font TEXT_FONT = AssetsManager.getBaseFont().deriveFont(Font.PLAIN, 18);
	private static final Color BG_COLOR = new Color(255,255,255, 200);

	// Component fields & inner components
	private int _direction;
	private Level _level;
	
	private JLabel _levelImage;
	private JLabel _levelName;
	private JLabel _levelSize;
	private JLabel _levelMinesCount;
	private JPanel _customPanel;

	/**
	 * create new level panel
	 * @param level level
	 * @param panelDirection horizontal or vertical panel (see LevelPanel.HORIZONTAL_PANEL or LevelPanel.VERTICAL_PANEL)
	 */
	public LevelPanel(Level level, int panelDirection) {
		_level = level;
		_direction = panelDirection;
		initializeUI();
		addInternalListeners();
	}

	/**
	 * create new level panel for custom level, with custom panel (instead of size & mines labels)
	 * @param level
	 * @param panelDirection
	 * @param customPanel
	 */
	public LevelPanel(Level level, int panelDirection, JPanel customPanel) {
		_level = level;
		_direction = panelDirection;
		_customPanel = customPanel;
		initializeUI();
	}

	/**
	 * Initialize panel UI
	 */
	private void initializeUI() {
		setBackground(BG_COLOR);

		// create panel labels
		_levelImage = new JLabel(new ImageIcon(AssetsManager.getResource(ChooseLevelView.class, _level.getAssetsPrefix() + "_thumb.jpg")));
		_levelName = new JLabel(_level.getLevelName());
		_levelName.setFont(HEADER_FONT);
		if (_level.getSize() != null) {
			_levelSize = new JLabel(_level.getSize().getWidth() + " x " + _level.getSize().getHeight());
			_levelSize.setFont(TEXT_FONT);
			_levelMinesCount = new JLabel(String.valueOf(_level.getMines()) + " mines");
			_levelMinesCount.setFont(TEXT_FONT);
		}

		// set panel layout
		setLayout(new GridBagLayout());
		GridBagConstraints panelConstraints = new GridBagConstraints();

		if (_direction == VERTICAL_PANEL) {			// UI for VERTICAL panel
			panelConstraints.insets = new Insets(10, 10, 10, 10);
			panelConstraints.gridx = 0;
			panelConstraints.gridy = 0;
			add(_levelImage, panelConstraints);	// level image
			panelConstraints.gridy = 1;
			add(_levelName, panelConstraints);	// level name
			if (_level.getSize() != null) {
				panelConstraints.gridy = 2;
				add(_levelSize, panelConstraints);	// level size
				panelConstraints.gridy = 3;
				panelConstraints.weighty = 1.0;
				add(_levelMinesCount, panelConstraints);	// level mines count
			}
		} else {	// UI for HORIZONTAL panel
			panelConstraints.insets = new Insets(15, 15, 15, 15);
			panelConstraints.anchor = GridBagConstraints.NORTHWEST;
			panelConstraints.gridx = 0;
			panelConstraints.gridy = 0;
			panelConstraints.gridheight = 2;
			add(_levelImage, panelConstraints);	//	level image
			panelConstraints.gridx = 1;
			panelConstraints.gridy = 0;
			add(_levelName, panelConstraints);	// level name

			if (_level.getSize() != null) {		// add custom panel
				_customPanel = new JPanel();
				GridLayout layout = new GridLayout(1, 2, 20, 0);
				_customPanel.setLayout(layout);
				_customPanel.add(_levelSize);
				_customPanel.add(_levelMinesCount);
			}
			
			_customPanel.setOpaque(false);	// transparent panel
			panelConstraints.anchor = GridBagConstraints.CENTER;
			panelConstraints.gridy = 1;
			add(_customPanel, panelConstraints);
		}
	}
	
	/**
	 * initialize panel's internal listeners
	 */
	private void addInternalListeners() {
		// create mouse event handlers for setting the panel's click feel as a button
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				setLocation(getLocation().x - 3, getLocation().y - 3);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				setLocation(getLocation().x + 3, getLocation().y + 3);
			}
		});
	}

	/**
	 * get panel level data
	 * @return	{@link Level} object model
	 */
	public Level getLevel() {
		return _level;
	}
}
