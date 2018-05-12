package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Cell extends JPanel{
	JLabel valueLbl = new JLabel();
	JButton fieldBtn = new JButton();
	int x, y;
	boolean locked;
	
	public boolean isLocked() {
		return locked;
	}

	@Override
	public void paint(Graphics g) {

		if(locked)
		{
			ImageIcon mineIcon = new ImageIcon(getClass().getResource("../resources/flag.ico"));
			fieldBtn.setIcon(mineIcon);
			fieldBtn.setBackground(new Color(255, 0, 17));
		}
		else
		{
			fieldBtn.setIcon(null);
			fieldBtn.setBackground(null);
		}
		fieldBtn.repaint();
		super.paint(g);
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
		repaint();
	}

	public Cell(int x, int y,int value)
	{
		this.x = x;
		this.y = y;
		
		setLayout(new BorderLayout());
		valueLbl.setHorizontalAlignment(SwingConstants.CENTER);
		valueLbl.setVerticalAlignment(SwingConstants.CENTER);
		
		fieldBtn.putClientProperty("x",x);
		fieldBtn.putClientProperty("y",y);
		if(value == -1)
		{
			valueLbl.setText("*");
		}
		else if(value > 0)
		{
			valueLbl.setText(String.valueOf(value));
		}
		valueLbl.setVisible(true);
		valueLbl.setLayout(new BorderLayout());
		fieldBtn.setVisible(true);
		valueLbl.add(fieldBtn);
		add(valueLbl);
		
	}

	public int getXLocation() {
		return x;
	}

	public int getYLocation() {
		return y;
	}

	public void addBtnActionListener(MouseListener mineBtnActionListener) {
		// TODO Auto-generated method stub
		fieldBtn.addMouseListener(mineBtnActionListener);
	}
	
}
