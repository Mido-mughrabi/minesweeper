package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameView extends JFrame{
	
	JPanel mineField = new JPanel();
	int n;
	int m;
	public GameView(int n, int m)
	{
		this.n = n;
		this.m = m;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		refillMineField();
		setContentPane(mineField);
		setSize(800,600);
		setVisible(true);
	}
	
	public void refillMineField()
	{
		mineField.removeAll();
		mineField.setLayout(new GridLayout(n, m));
		for(int i =0; i<n;i++)
		{
			for(int j =0; j<m;j++)
			{
				Cell c = new Cell(-1);
				c.setVisible(true);
				mineField.add(c);
			}
		}
	}
}
