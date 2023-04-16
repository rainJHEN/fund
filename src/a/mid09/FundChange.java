package a.mid09;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import c.mid09.BCrypt;
import c.mid09.Member;




public class FundChange extends JFrame{
	private static Connection conn;
	private final static String USER = "root";
	private final static String PASSWORD = "root";
	private final static String URL = "jdbc:mysql://10.10.243.31:3306/fund";
	private final static String SQL_LOGIN = "SELECT * FROM user WHERE account = ?";
	private final static String SQL_CHPASSWD = "UPDATE user SET passwd = ? WHERE id = ?";
	
	FundChange() {
		JPanel acp =new JPanel();
		JLabel acl=new JLabel("帳號 :");
		JTextField acf=new JTextField(15);
		acp.setLayout(new FlowLayout());
		acp.add(acl);acp.add(acf);
		
		JPanel app =new JPanel();
		JLabel apl=new JLabel("原密碼 :");
		JPasswordField apf=new JPasswordField(15);
		app.setLayout(new FlowLayout());
		app.add(apl);app.add(apf);
		
		JPanel anp =new JPanel();
		JLabel anl=new JLabel("新密碼 :");
		JPasswordField anf=new JPasswordField(15);
		anp.setLayout(new FlowLayout());
		anp.add(anl);anp.add(anf);
		
		JPanel bu=new JPanel();
		JButton ok=new JButton("修改");
		JButton no=new JButton("清除");
		bu.setLayout(new FlowLayout());
		bu.add(ok);bu.add(no);
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Properties prop = new Properties();
				prop.put("user", USER); prop.put("password", PASSWORD);
				try (Connection conn = DriverManager.getConnection(URL, prop);
						PreparedStatement pstmt = conn.prepareStatement(SQL_LOGIN)) {
					pstmt.setString(1, acf.getText());
					ResultSet rs = pstmt.executeQuery();//executeQuery可執行SELECT查詢資料庫的SQL，會回傳java.sql.ResultSet物件，代表查詢結果，查詢結果會是一筆一筆的資料，可以使用ResultSet的next()移動到下一筆，他會傳回true跟false
					if (rs.next()) {
						if (BCrypt.checkpw(apf.getText(), rs.getString("passwd"))) {//前面是我輸入的號碼/後面是資料庫的亂碼 去檢查是否相同
							// login
							Member member = new Member(rs.getInt("id"),
									rs.getString("account"), rs.getString("realname"), null);
						
//							// change password process
							String newPasswd = anf.getText();
							
							PreparedStatement pstmt2 = conn.prepareStatement(SQL_CHPASSWD);
							pstmt2.setString(1, BCrypt.hashpw(newPasswd, BCrypt.gensalt()));
							pstmt2.setInt(2, member.getId());
							if (pstmt2.executeUpdate() > 0 ) {
								//System.out.println("change passwd success!");
								 JFrame jFrame = new JFrame();
							     JOptionPane.showMessageDialog(jFrame, "更換成功");
							     setVisible(false);
							}else {
								//System.out.println("change passwd failure!");
								JFrame jFrame = new JFrame();
							    JOptionPane.showMessageDialog(jFrame, "更換失敗");
							}
	
						}else {
							//System.out.println("login failure(1)");
							JFrame jFrame = new JFrame();
						    JOptionPane.showMessageDialog(jFrame, "錯誤");
						}
					}else {
						// 帳號不存在 System.out.println("login failure(2)");
						JFrame jFrame = new JFrame();
					    JOptionPane.showMessageDialog(jFrame, "錯誤");
					}
					
				}catch (Exception e1) {
					System.out.println(e1);
				}
				
			}
		});
		
		no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				acf.setText("");
				apf.setText("");
				anf.setText("");
			}
		});
		
		setTitle("更改密碼");
		this.setLayout(new GridLayout(4,1));
		add(acp);add(app);add(anp);add(bu);
		setSize(250,200);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new FundChange();
	}
}
