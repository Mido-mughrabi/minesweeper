package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Cell extends JPanel{
	JLabel valueLbl = new JLabel();
	JButton fieldBtn = new JButton();
	
	public Cell(int value)
	{
		setLayout(new BorderLayout());
		valueLbl.setHorizontalAlignment(SwingConstants.CENTER);
		valueLbl.setVerticalAlignment(SwingConstants.CENTER);
		if(value == -1)
		{
			valueLbl.setText("*");
		}
		else
		{
			valueLbl.setText(String.valueOf(value));
		}
		valueLbl.setVisible(true);
		valueLbl.setLayout(new BorderLayout());
		fieldBtn.setVisible(true);
		fieldBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fieldBtn.setVisible(false);
			}
		});
		valueLbl.add(fieldBtn);
		add(valueLbl);
		
	}
	
}
