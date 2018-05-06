package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import model.Game;
import view.Cell;
import view.GameView;

public class Controller {
	Game game;
	GameView gameView;
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
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				if(!game.isGameOver() && !game.isWin())
				{
					mineBtn.setVisible(false);
					game.uncover((int)mineBtn.getClientProperty("x"),(int)mineBtn.getClientProperty("y"));
					gameView.updateField(game.getMineField().getVisibilityMask());
				}
				
				if(game.isWin())
				{
					System.out.println("you win");
				}
				
				if(game.isGameOver())
				{
					System.out.println("game over");
				}
			}
			else if(e.getButton() == MouseEvent.BUTTON3)
			{
				//toDo lock Cell at right click
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
			int x = 10;
			int y = 10;
			int mines = 10;
			switch (gameView.getHardness()) {
			case EASY:
				x = 10;
				y = 10;
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
		}
		
	}
}

