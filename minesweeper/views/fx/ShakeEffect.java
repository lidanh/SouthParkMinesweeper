package minesweeper.views.fx;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *  Shake Effect Component
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class ShakeEffect implements Runnable {
	private JFrame _view;
	private int xOffset, yOffset;

	/**
	 * initialize new ShakeEffect object
	 * @param view the view for shaking
	 */
	public ShakeEffect(JFrame view) {
		_view = view;

		xOffset = _view.getX();
		yOffset = _view.getY();
	}

	/**
	 * run the shake effect with {@link SwingUtilities.InvokeLater}
	 */
	@Override
	public void run() {
		try
		{
			for(int i = 1; i < 16; i++)
			{
				// set bounds of the JFrame
				_view.setBounds(xOffset + 30, yOffset + 30, _view.getWidth(), _view.getHeight());
				Thread.sleep(40);
				_view.setBounds(xOffset, yOffset, _view.getWidth(), _view.getHeight());
				Thread.sleep(40);
			}
		}

		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

