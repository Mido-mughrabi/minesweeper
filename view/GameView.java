package view;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;


public class GameView extends JFrame{
	int n;
	int m;
	int[][] field;
	JPanel mineFieldPnl = new JPanel();
	Cell[][] cells;
	JMenuBar menuBar = new JMenuBar();
	JMenu gameMnu= new JMenu("Game");
	JMenuItem newGameMnuItm = new JMenuItem("New game");
	ButtonGroup group = new ButtonGroup();
	JRadioButtonMenuItem easyOptionMnuItm = new JRadioButtonMenuItem("easy");
	JRadioButtonMenuItem mediumOptionMnuItm = new JRadioButtonMenuItem("medium");
	JRadioButtonMenuItem hardOptionMnuItm = new JRadioButtonMenuItem("hard");
	JRadioButtonMenuItem customOptionMnuItm = new JRadioButtonMenuItem("custom");
	JTextField customXTextField = new JTextField("10");
	JTextField customYTextField = new JTextField("10");
	JTextField customMinesTextField = new JTextField("10");
	JMenuItem exitMnuItm;
	JMenu timeMnu= new JMenu("Time: 0");
	
	public GameView(int[][] field)
	{
		setField(field);
		refillMineField();
		//add the menus
		initMenus();
		//init JFrame
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setContentPane(mineFieldPnl);
		setSize(800,600);
		setVisible(true);
	}
	
	public void setField(int[][] field) {
		this.field = field;
		this.n = field.length;
		this.m = field[0].length;
		cells = new Cell[n][m];
		
		refillMineField();
	}

	private void initMenus() {
		menuBar.add(gameMnu);
		menuBar.add(Box.createHorizontalGlue());
		timeMnu.setEnabled(false);
		menuBar.add(timeMnu);
		gameMnu.add(newGameMnuItm);
		gameMnu.addSeparator();

		easyOptionMnuItm.setSelected(true);
		easyOptionMnuItm.setActionCommand("EASY");
		group.add(easyOptionMnuItm);
		gameMnu.add(easyOptionMnuItm);
		
		mediumOptionMnuItm.setActionCommand("MEDIUM");
		group.add(mediumOptionMnuItm);
		gameMnu.add(mediumOptionMnuItm);
		
		hardOptionMnuItm.setActionCommand("HARD");
		group.add(hardOptionMnuItm);
		gameMnu.add(hardOptionMnuItm);
		
		customOptionMnuItm.setActionCommand("CUSTOM");
		group.add(customOptionMnuItm);
		gameMnu.add(customOptionMnuItm);
		
		gameMnu.add(customXTextField);
		gameMnu.add(customYTextField);
		gameMnu.add(customMinesTextField);
		
		gameMnu.addSeparator();
		exitMnuItm = new JMenuItem("Exit");
		gameMnu.add(exitMnuItm);
		setJMenuBar(menuBar);
	}

	public void refillMineField()
	{
		mineFieldPnl.removeAll();
		mineFieldPnl.setLayout(new GridLayout(n, m));
		for(int i =0; i<n;i++)
		{
			for(int j =0; j<m;j++)
			{
				cells[i][j] = new Cell(i,j,field[i][j]);
				cells[i][j].setVisible(true);
				mineFieldPnl.add(cells[i][j]);
			}
		}
		mineFieldPnl.repaint();
		mineFieldPnl.revalidate();
	}

	public void updateField(boolean[][] mask)
	{
		for(int i =0; i<n;i++)
		{
			for(int j =0; j<m;j++)
			{
				cells[i][j].fieldBtn.setVisible(!mask[i][j]);
			}
		}
	}
	
	public Hardness getHardness()
	{
		return Hardness.valueOf(group.getSelection().getActionCommand().toUpperCase());
	}
	
	public void addMineButtonsActionListener(MouseListener mineBtnActionListener) {
		for(Component component: mineFieldPnl.getComponents())
		{
			if(component instanceof Cell)
			{
				Cell cell = (Cell) component;
				cell.addBtnActionListener(mineBtnActionListener);
			}
		}
		
	}
	
	public void addNewGameMnuItmActionListener(ActionListener actionListener)
	{
		newGameMnuItm.addActionListener(actionListener);
		easyOptionMnuItm.addActionListener(actionListener);
		mediumOptionMnuItm.addActionListener(actionListener);
		hardOptionMnuItm.addActionListener(actionListener);
		customOptionMnuItm.addActionListener(actionListener);
	}
	
	public void addExitMnuItmActionListener(ActionListener actionListener)
	{
		exitMnuItm.addActionListener(actionListener);
	}

	public int getCustomHardnessX() {
		int result;
		try
		{
			//height should be between 9 and 24
			result = Math.max(9, Math.min(Integer.valueOf(customXTextField.getText()), 24));
		}
		catch (Exception e) {
			result = 10;
		}
		customXTextField.setText(String.valueOf(result));
		return result;
	}

	public int getCustomHardnessY() {
		int result;
		try
		{
			//width should be between 9 and 30
			result =  Math.max(9, Math.min(Integer.valueOf(customYTextField.getText()), 30));
		}
		catch(Exception e)
		{
			result = 10;
		}
		customYTextField.setText(String.valueOf(result));
		return result;
	}

	public int getCustomHardnessMines() {
		int result;
		int maxArea = getCustomHardnessX() * getCustomHardnessY();
		try
		{
			//at least one mine, max 0.25 * field is mines
			result = Math.max(1, Math.min(Integer.valueOf(customMinesTextField.getText()), maxArea/4));;
		}
		catch(Exception e)
		{
			result = 10;
		}
		customMinesTextField.setText(String.valueOf(result));
		return result;
	}

	public Cell getCell(int x, int y) {
		return cells[x][y];
	}

	public int showGameOverDialog() {
		Object[] options = {"yes", "exit instead", "cancel"};
		return JOptionPane.showOptionDialog(this, "game over\nplay again?","hard luck",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
	}

	public int showWinDialog() {
		Object[] options = {"yes", "exit instead", "cancel"};
		return JOptionPane.showOptionDialog(this, "you win\nplay again?","congratulations",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
	}

	public void updateTime(long currentTimeInGame) {
		timeMnu.setText("Time: "+String.valueOf(currentTimeInGame));
	}
	
}
