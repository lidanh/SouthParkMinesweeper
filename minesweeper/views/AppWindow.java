package minesweeper.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import minesweeper.views.components.MinefieldPanel;

/**
 *  Application Window
 *  Contains View Panels (see showView method)
 *  for example {@link GameView}, {@link ChooseLevelView}, etc.
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class AppWindow extends JFrame {
	private static final long serialVersionUID = 920817072842328898L;

	private static final String FRAME_TITLE = "Minesweeper South Park Edition";
	private static Point _mouseDraggingCoordinates; 	// point for window dragging
	
	// application views fields
	private ChooseLevelView _chooseLevelView = new ChooseLevelView();
	private GameView _gameView;
	private PauseView _pauseView = new PauseView();
	private LoseView _loseView = new LoseView();
	private HighscoresView _highscoresView = new HighscoresView();
	private WinView _winView = new WinView();

	/**
	 * create new application window
	 * @param minefieldPanel	game panel, for instantiate {@link GameView} object
	 */
	public AppWindow(MinefieldPanel minefieldPanel) {
		_gameView = new GameView(minefieldPanel);
		
		// set basic UI parameters
		setTitle(FRAME_TITLE);
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// initialize UI Components
		initializeUI();
	}
	
	/**
	 * set window in the screen center
	 */
	public void setWindowInScreenCenter() {
		Dimension size = getToolkit().getScreenSize();
		this.setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 4);
	}

	/**
	 * initialize UI Components
	 */
	private void initializeUI() {
		// set border layout
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		
		// mouse listeners for window dragging
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				_mouseDraggingCoordinates = null;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				_mouseDraggingCoordinates = e.getPoint();
			}
		});

		addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				Point currentPoint = e.getLocationOnScreen();	// get pointer location
				setLocation(currentPoint.x - _mouseDraggingCoordinates.x, currentPoint.y - _mouseDraggingCoordinates.y);	// set window location
			}
		});

		// event handler for window closing
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				Frame frame = (Frame)we.getSource();
				frame.dispose();
			}
		});
	}
	
	/**
	 * get {@link ChooseLevelView}
	 * @return	{@link ChooseLevelView} object
	 */
	public ChooseLevelView getLevelView() { return _chooseLevelView; }
	
	/**
	 * get {@link GameView}
	 * @return	{@link GameView} object
	 */
	public GameView getGameView() { return _gameView; }
	
	/**
	 * get {@link PauseView}
	 * @return	{@link PauseView} object
	 */
	public PauseView getPauseView() { return _pauseView; }
	
	/**
	 * get {@link LoseView}
	 * @return	{@link LoseView} object
	 */
	public LoseView getLoseView() { return _loseView; }
	
	/**
	 * get {@link HighscoresView}
	 * @return	{@link HighscoresView} object
	 */
	public HighscoresView getHighscoresView() { return _highscoresView; }
	
	/**
	 * get {@link WinView}
	 * @return	{@link WinView} object
	 */
	public WinView getWinView() { return _winView; }
	
	/**
	 * show view in the application window
	 * @param view - Any application view (for example {@link GameView})
	 */
	public void showView(JPanel view) {
		// remove any components from the frame's content pane
		Container c = getContentPane();
		for (Component comp : c.getComponents()) {
			c.remove(comp);
		}
		
		// add the given view to the frame's center
		c.add(view, BorderLayout.CENTER);
		view.updateUI();
		
		// set the view visibility and repaint the application window
		view.setVisible(true);
		repaint();
	}
}
