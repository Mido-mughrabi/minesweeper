package model;

public class Game {
	boolean gameOver;
	boolean win;
	
	Minefield mineField;
	int maxArea ;
	
	public Game(int n, int m, int mines)
	{
		mineField = new Minefield(n,m,mines);
		maxArea = mineField.n * mineField.m - mineField.minesNr;
	}
	
	public Game()
	{
		this(10,10,10);
	}
	
	public void uncover(int x, int y)
	{
		if(mineField.uncover(x, y) == -1)
		{
			gameOver = true;
		}
		
		if(mineField.discoveredArea == maxArea)
		{
			win = true;
		}
	}
	
	public Minefield getMineField() {
		return mineField;
	}

	public boolean isGameOver() {
		return gameOver;
	}
	
	public boolean isWin()
	{
		return win;
	}

	public boolean isFirstMove() {
		return mineField.discoveredArea == 0;
	}
	
}
