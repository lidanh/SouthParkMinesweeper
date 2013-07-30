package minesweeper.views.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import minesweeper.views.utils.AssetsManager;

/**
 *  Menu Button Object
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class MenuButton extends JButton {
	private static final long serialVersionUID = 3189723695795512417L;
	
	// UI properties (static final for performance issues)
	private static final Font TEXT_FONT = AssetsManager.getBaseFont().deriveFont(Font.PLAIN, 36);
	private static final Color TEXT_COLOR = new Color(3, 109, 168);
	private static final Color TEXT_COLOR_HOVER = new Color(226,0,1);
	
	/**
	 * create new menu button
	 * @param text button label
	 */
	public MenuButton(String text) {
		// set button UI properties
		setText(text);
		setForeground(TEXT_COLOR);
	    setFont(TEXT_FONT);
	    
	    // transparent button
		setOpaque(false);
	    setContentAreaFilled(false);
	    setBorderPainted(false);
	    
	    addInternalListeners();
	}
	
	/**
	 * add button listeners- for mouse over and set location ("label" feels as a button on click)
	 */
	private void addInternalListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(TEXT_COLOR_HOVER);	// change button text color on mouse over
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setForeground(TEXT_COLOR);		// change button text color on mouse over
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setForeground(TEXT_COLOR);
				setLocation(getLocation().x - 3, getLocation().y - 3);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				setLocation(getLocation().x + 3, getLocation().y + 3);
			}
		});
	}
}
