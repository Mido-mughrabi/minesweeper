package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.Game;
import view.Cell;
import view.GameView;

public class Controller {
	Game game;
	GameView gameView;
	TimeCounter timeCounter = new TimeCounter();
	public Controller(Game game, GameView gameView)
	{
		this.game = game;
		this.gameView = gameView;
		gameView.addMineButtonsActionListener(new MineBtnActionListener());
		gameView.addNewGameMnuItmActionListener(new NewGameBtnActionListener());
		gameView.addExitMnuItmActionListener(new ExitBtnActionListener());
	}
	
	class MineBtnActionListener implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			JButton mineBtn = (JButton) e.getSource();
			Cell cell = gameView.getCell((int)mineBtn.getClientProperty("x"), (int)mineBtn.getClientProperty("y"));
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				if(game.isFirstMove())
				{
					game.getMineField().setFirstLocation(cell.getXLocation(),cell.getYLocation());
					gameView.setField(game.getMineField().getField());
					gameView.addMineButtonsActionListener(new MineBtnActionListener());
					//init the time counter
					timeCounter = new TimeCounter();
					game.setStartingTime(System.currentTimeMillis());
					timeCounter.setCanCount(true);
					timeCounter.start();
				}
				
				if(!game.isGameOver() && !game.isWin() && !cell.isLocked())
				{
					mineBtn.setVisible(false);
					game.uncover(cell.getXLocation(),cell.getYLocation());
					gameView.updateField(game.getMineField().getVisibilityMask());
				}
				
				if(game.isWin())
				{
					timeCounter.setCanCount(false);
					int dialogResult = gameView.showWinDialog();
					if (dialogResult == JOptionPane.YES_OPTION)
					{
						NewGameBtnActionListener newGame = new NewGameBtnActionListener();
						newGame.actionPerformed(null);
						gameView.updateTime(0);
					}
					else if(dialogResult == JOptionPane.NO_OPTION)
					{
						System.exit(0);
					}
				}
				
				if(game.isGameOver())
				{
					timeCounter.setCanCount(false);
					int dialogResult = gameView.showGameOverDialog();
					if (dialogResult == JOptionPane.YES_OPTION)
					{
						NewGameBtnActionListener newGame = new NewGameBtnActionListener();
						newGame.actionPerformed(null);
						gameView.updateTime(0);
					}
					else if(dialogResult == JOptionPane.NO_OPTION)
					{
						System.exit(0);
					}
				}
			}
			else if(e.getButton() == MouseEvent.BUTTON3)
			{
				cell.setLocked(!cell.isLocked());
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ExitBtnActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	class NewGameBtnActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			int x = 9;
			int y = 9;
			int mines = 10;
			switch (gameView.getHardness()) {
			case EASY:
				x = 9;
				y = 9;
				mines = 10;
				break;
			case MEDIUM:
				x = 16;
				y = 16;
				mines = 40;
				break;
			case HARD:
				x = 16;
				y = 30;
				mines = 99;
				break;
			case CUSTOM:
				x = gameView.getCustomHardnessX();
				y = gameView.getCustomHardnessY();
				mines = gameView.getCustomHardnessMines();
				break;
			default:
				break;
			}
			game = new Game(x,y,mines);
			gameView.setField(game.getMineField().getField());
			gameView.addMineButtonsActionListener(new MineBtnActionListener());
			gameView.updateTime(0);
			timeCounter.setCanCount(false);
		}
		
	}

	class TimeCounter extends Thread
	{
		boolean canCount;
		
		@Override
		public void run() {
			while(canCount)
			{
				try {
					long currentTimeInGame = (System.currentTimeMillis()-game.getStartingTime())/1000;
					gameView.updateTime(currentTimeInGame);
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public void setCanCount(boolean canCount)
		{
			this.canCount = canCount;
		}
	}
}

