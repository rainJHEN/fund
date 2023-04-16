//註銷帳號-請看雲端20230215進度，mid09內的FundUserdel，更改為需要帳密才可刪除
package a.mid09;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class FundUserdel extends JFrame{
	private static Connection conn;
	private final static String USER = "root";
	private final static String PASSWORD = "root";
	private final static String URL = "jdbc:mysql://localhost:3306/fund";
	private final static String SQL_DEL="DELETE FROM user WHERE account=?";;
	private final static String SQL_CHECK = "SELECT count(*) count FROM user WHERE account = ?";
	
	FundUserdel(){
		//註銷帳號		
		JPanel dellogin =new JPanel();
		JLabel dellb=new JLabel("請輸入您要註銷的帳號 :");
		JTextField delsc=new JTextField(15);
		dellogin.setLayout(new FlowLayout());
		dellogin.add(dellb);dellogin.add(delsc);
				
		JButton delok =new JButton("註銷帳號");
		JButton delno =new JButton("取消");
		dellogin.setLayout(new FlowLayout());
		dellogin.add(delok);dellogin.add(delno);
		dellogin.setBackground(Color.lightGray);
	
		delok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Properties prop=new Properties();
				prop.put("user", USER);prop.put("password", PASSWORD);
				

				try (Connection conn=DriverManager.getConnection(URL,prop);
						PreparedStatement pstmd=conn.prepareStatement(SQL_DEL)){
					
					if(checkAccount(delsc.getText())) {
						pstmd.setString(1, delsc.getText());
						int num=pstmd.executeUpdate();
						
						JFrame jFrame = new JFrame();
						JOptionPane.showMessageDialog(jFrame, "已註銷此帳號");
					}else {
						JFrame jFrame = new JFrame();
						JOptionPane.showMessageDialog(jFrame, "無此帳號");
					}
					
				} catch (Exception e1) {
					System.out.println(e1);
				}
			}
		});
		
		//delno清除資料
		delno.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				delsc.setText("");
			}
		});
		
		
		this.add(dellogin);
		
		setSize(230,250);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		
	}
//註冊判斷的東東
	
	private static boolean checkAccount(String account) throws Exception {
		boolean ret;
		PreparedStatement pstmt=conn.prepareStatement(SQL_CHECK);
		pstmt.setString(1, account);
		ResultSet rs =pstmt.executeQuery();
		rs.next();
		ret=rs.getInt("count")==1;//=1表示有這筆資料
		pstmt.close();
			
		return ret;
	}
	
	
	public static void main(String[] args) {
		new FundUserdel();
	}
	

}
