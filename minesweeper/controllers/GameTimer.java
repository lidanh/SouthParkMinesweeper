package minesweeper.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *  Game Timer
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class GameTimer implements Runnable {
	private JLabel _timerLabel;
	private Timer _timer;
	
	private long _startTime, _elapsedTime, _pauseTime;

	private int seconds, minutes, hours;
	private boolean _isRunning;

	/**
	 * initialize new {@link GameTimer} object
	 * @param timerLabel the view of this timer
	 */
	public GameTimer(JLabel timerLabel) {
		_timerLabel = timerLabel;
		
		// update the view every 1 second
		_timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (_isRunning)
					updateTimeUI();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		start();
	}
	
	/**
	 * reset the timer values & view to zero
	 */
	public void reset() {
		seconds = 0;
		minutes = 0;
		hours = 0;
		_pauseTime = 0;
		_startTime = 0;
		
		_timerLabel.setText(this.toString());
	}
	
	/**
	 * start timer
	 */
	public void start() {
		if (_isRunning) {	// if timer is running (=not paused)
			_startTime = System.currentTimeMillis();
			_timer.start();
		} else {
			// if timer is not running (for example paused)
			_startTime = System.currentTimeMillis() + _pauseTime;
			_pauseTime = 0;
			_timer.start();
			_isRunning = true;
		}
	}
	
	/**
	 * stop/pause the timer
	 */
	public void stop() {
		if (_isRunning) {
			_pauseTime -= System.currentTimeMillis() - _startTime;
			_timer.stop();
			_isRunning = false;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
	}
	
	/**
	 * update the timer view with the current elapsed time
	 */
	private void updateTimeUI() {
    	_elapsedTime = System.currentTimeMillis() - _startTime;
    	
    	// calculates the seconds, minutes and hours based on elapsed milliseconds
    	seconds = (int)((_elapsedTime/1000) % 60);
	    minutes = (int)((_elapsedTime/(1000*60)) % 60);
	    hours = (int)((_elapsedTime/(1000*60*60)) % 24);
	    
		_timerLabel.setText(this.toString());
	}
}