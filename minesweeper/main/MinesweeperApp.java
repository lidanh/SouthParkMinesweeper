package minesweeper.main;

import javax.swing.SwingUtilities;

import minesweeper.controllers.GameEngine;

/**
 *  Minesweeper Game (South Park Edition)
 *  This game was an assignment for Object Oriented Software Design course, Ben Gurion University, June 2013
 *  @author     Lidan Hifi
 *  @version    1.0
 */
public class MinesweeperApp {
	public static void main(String[] args) {
		// invoke the game from the controller
		SwingUtilities.invokeLater(new GameEngine());
	}
}
