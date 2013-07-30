package minesweeper.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import minesweeper.model.Highscores;
import minesweeper.views.utils.AssetsManager;
import minesweeper.views.utils.ViewUtilities;

public class HighscoresView extends JPanel {
	private static final long serialVersionUID = -4208787347050938286L;
	
	// UI properties (static final for performance issues)
	private static final Font TEXT_FONT = AssetsManager.getBaseFont().deriveFont(Font.PLAIN, 18);
	private static final Font HEADER_FONT = AssetsManager.getBaseFont().deriveFont(Font.PLAIN, 24);
	private static final Font TITLE_FONT = AssetsManager.getBaseFont().deriveFont(Font.PLAIN, 36);
	private static final Color TITLE_COLOR = Color.white;
	private static final ImageIcon BACK_BUTTON_ICON = new ImageIcon(AssetsManager.getResource(HighscoresView.class, "back.png"));
	private static final Image BACKGROUND_IMAGE = new ImageIcon(AssetsManager.getResource(HighscoresView.class, "bg.jpg")).getImage();
	
	// View Components
	private JTable _dataTable;
	private Highscores _highscores;
	private JButton _backButton = new JButton(BACK_BUTTON_ICON);
	
	/**
	 * create new {@link HighscoresView} view object
	 */
	public HighscoresView() {
		_dataTable = new JTable();	// initialize new JTable Component for holding the highscores model data
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
		viewConstraints.anchor = GridBagConstraints.NORTHEAST;
		viewConstraints.insets = new Insets(20, 0, 0, 30);
		
		// buttons left panel
		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		// buttons panel  (left)
		_backButton.setOpaque(false);		// set transparent panel
	    _backButton.setContentAreaFilled(false);
	    _backButton.setBorderPainted(false);
		leftPanel.add(_backButton);
		
		add(leftPanel, viewConstraints);
		
		// highscores panel (right)
		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);		// set transparent panel
		rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints rightConstraints = new GridBagConstraints();
		rightConstraints.insets = new Insets(0, 0, 20, 0);
		
		// highscores view title
		JLabel title = new JLabel("Highscores");
		title.setFont(TITLE_FONT);
		title.setForeground(TITLE_COLOR);
		rightConstraints.gridx = 0;
		rightConstraints.gridy = 0;
		rightPanel.add(title, rightConstraints);
		
		rightConstraints.insets = new Insets(0, 0, 10, 0);
		
		// data table UI configuration (to be transparent, not focusable, etc.
		_dataTable.setShowHorizontalLines(false);
		_dataTable.setShowVerticalLines(false);
		_dataTable.setFocusable(false);
		_dataTable.setRowSelectionAllowed(false);
		_dataTable.getTableHeader().setFont(HEADER_FONT);
		_dataTable.setFont(TEXT_FONT);
		_dataTable.setFocusable(false);
		_dataTable.getTableHeader().setReorderingAllowed(false);
		_dataTable.setFillsViewportHeight(true);
		_dataTable.getTableHeader().setOpaque(false);
		_dataTable.setRowHeight(40);
		_dataTable.setRowMargin(15);
		_dataTable.setBackground(new Color(255,255,255));
		
		JPanel tableContainer = new JPanel();
		tableContainer.setLayout(new BorderLayout());
		tableContainer.add(_dataTable);
		
		// add right panel to the view
		rightConstraints.gridy = 1;
		tableContainer.add(_dataTable.getTableHeader(), BorderLayout.PAGE_START);
		tableContainer.add(_dataTable, BorderLayout.CENTER);
		
		rightPanel.add(tableContainer, rightConstraints);
		
		viewConstraints.weighty = 1.0;	// workaround for placing the table in the top of the view instead of in the center
		add(rightPanel, viewConstraints);
		setPreferredSize(new Dimension(600, 500));
	}
	
	/**
	 * connect between the JTable view component and the highscores model data
	 * @param highscores
	 */
	public void setHighscores(Highscores highscores) {
		_highscores = highscores;
		_dataTable.setModel(highscores);
		// update columns width
		_dataTable.getColumnModel().getColumn(0).setMinWidth(200);
		_dataTable.getColumnModel().getColumn(1).setMinWidth(100);
		_dataTable.getColumnModel().getColumn(2).setMinWidth(100);
	}
	
	/**
	 * add back event listener
	 * @param backListener
	 */
	public void addBackListener(ActionListener backListener) {
		_backButton.addActionListener(backListener);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean aFlag) {
		// repaint the table when change view visibility to true
		if (aFlag == true) {
			_dataTable.repaint();
			
			// show highscores table only when there are at least 1 record
			if (_highscores != null && _highscores.getRowCount() == 0)
				_dataTable.getTableHeader().setVisible(false);
			else
				_dataTable.getTableHeader().setVisible(true);
		}
		
		super.setVisible(aFlag);
	}
}
