package model;

import java.util.Random;

public class Minefield {
	int n;
	int m;
	int minesNr;
	int[][] field;
	boolean[][] visibilityMask;
	int discoveredArea;
	int firstX,firstY;	//first move location
	//toDo, array of points to know where is each mine

	public Minefield(int n, int m,int mines)
	{
		this.n = n;
		this.m = m;
		this.minesNr = mines;
		field = new int[n][m];
		visibilityMask = new boolean[n][m];
		init();
	}
	
	public Minefield()
	{
		this(10,10,10);
	}
	
	private void init()
	{
		fillRandom(firstX,firstY);
		calculateValues();
	}
	
	private void calculateValues() {
		for(int i = 0; i < n;i++)
		{
			for(int j = 0; j < m;j++)
			{
				calculateValue(i,j);
			}
		}		
	}

	private void calculateValue(int x, int y) {
		if (field[x][y] == -1) {return;}
		
		int value = 0;
		for(int i = x-1; i <= x+1;i++)
		{
			for(int j = y-1; j <= y+1;j++)
			{
				if(i>=0 && i < n && j>=0 && j < m && field[i][j] == -1)
				{
					value++;
				}
			}
		}
		field[x][y] = value;
	}

	private void fillRandom(int x, int y) {
		resetField();
		Random random = new Random();
		for(int i = 0; i < minesNr;)
		{
			int rndN = random.nextInt(n);
			int rndM = random.nextInt(m);
			if((rndN != firstX || rndM != firstY) && field[rndN][rndM] != -1)
			{
				field[rndN][rndM] = -1;
				i++;
			}
		}	
	}

	private void resetField() {
		for(int i =0; i<n;i++)
		{
			for(int j=0;j<m;j++)
			{
				field[i][j] = 0;
			}
		}
	}

	public void print()
	{
		for(int i =0; i< n;i++)
		{
			for(int j=0;j<m;j++)
			{
				if(visibilityMask[i][j] == false)
				{
					System.out.print("X" + " ");
				}
				else if(field[i][j] == -1)
				{
					System.out.print("*" + " ");
				}
				else
				{
					System.out.print(field[i][j] + " ");
				}
			}
			System.out.println();
		}
	}

	public void printUncensoredField()
	{
		for(int i =0; i< n;i++)
		{
			for(int j=0;j<m;j++)
			{
				if(field[i][j] == -1)
				{
					System.out.print("*" + " ");
				}
				else
				{
					System.out.print(field[i][j] + " ");
				}
			}
			System.out.println();
		}
	}
	
	public int uncover(int x, int y)
	{
		//out of the board
		if(x < 0 || x >= n || y < 0 || y >= m)
		{
			return 0;
		}
		//already discovered
		if(visibilityMask[x][y])
		{
			return field[x][y];
		}
		//setDiscovered
		visibilityMask[x][y] = true;
		discoveredArea++;
		//in case of 0, flood fill 
		if(field[x][y] == 0)
		{
			uncover(x+1,y);
			uncover(x-1,y);
			uncover(x,y-1);
			uncover(x,y+1);
		}
		return field[x][y];
	}

	public int[][] getField() {
		return field;
	}

	public boolean[][] getVisibilityMask() {
		return visibilityMask;
	}

	public void setFirstLocation(int firstX, int firstY) {
		this.firstX = firstX;
		this.firstY = firstY;
		init();	
	}
}
