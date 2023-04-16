package a.mid09;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class AFrome extends JFrame{
	
	public AFrome(){
		
		JLabel al = new JLabel("這邊做單筆買賣計算");
		add(al, BorderLayout.CENTER);
		
		setTitle("單筆投資計算");
		setSize(400,400);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	

	public static void main(String[] args) {
		new AFrome();

	}

}
