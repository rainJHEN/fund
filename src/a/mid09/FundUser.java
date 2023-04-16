package a.mid09;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class FundUser extends JFrame{
	private UserTable userTable;//把隔壁的UserTable中間布景放進來
	
	public FundUser() throws Exception{
		
		userTable=new UserTable();//把隔壁的UserTable中間布景放進來
		JScrollPane jsp=new JScrollPane(userTable);//卷軸卷軸!!!!!
		add(jsp,BorderLayout.CENTER);
		
		
		
		setSize(640,480);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		JMenuBar baruser = new JMenuBar();//JMenuBar創建一个新的菜单欄
		JMenu menu = new JMenu("設定");
		JMenuItem item = new JMenuItem("更改密碼");
		JMenuItem item2 = new JMenuItem("註銷帳號");
		
		
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			new FundChange();//更改密碼
			}
		});
		
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FundUserdel();//註銷帳號
			}
		});
		
		menu.add(item);menu.add(item2);
		baruser.add(menu);
		this.setJMenuBar(baruser);
		setTitle("基金個人介面");
		setSize(400,400);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		try {
			new FundUser();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}