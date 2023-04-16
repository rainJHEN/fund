package a.mid09;

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
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import c.mid09.BCrypt;
import c.mid09.Member;

public class FundLogin extends JFrame{
	private static Connection conn;
	private final static String USER = "root";
	private final static String PASSWORD = "root";
	private final static String URL = "jdbc:mysql://localhost:3306/fund";
	private final static String SQL_CHECK = "SELECT count(*) count FROM user WHERE account = ?";//檢查帳號是否重複
	private final static String SQL_INSERT = "INSERT INTO user (account,passwd,realname) VALUES (?,?,?)";
	private final static String SQL_LOGIN="SELECT * FROM user WHERE account=?"; //尋找這個登入帳號
	public static boolean isLoggedIn = false;
	
	
	
	FundLogin(){
		
		
		JTabbedPane tabpane =new JTabbedPane();
		tabpane.setSize(230,250);
		add(tabpane);
		
		//登入
		String tabT1="登入";
		
		JPanel login =new JPanel();
		JLabel ac01=new JLabel("帳號 : ");
		JTextField sc01=new JTextField(15);
		login.add(ac01);login.add(sc01);
		
		
		JLabel ac02=new JLabel("密碼 : ");
		//JTextField sc02=new JTextField(15);
		JPasswordField sc02=new JPasswordField(15); //JPasswordField為繼承JTextField的東西，會呈現***
		login.add(ac02);login.add(sc02);
		
		JButton okac =new JButton("確定");
		JButton noac =new JButton("清除");
		login.add(okac);login.add(noac);
		
		
		tabpane.addTab(tabT1, login);
		
		
		//登入
		okac.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Properties prop=new Properties();
				prop.put("user",USER);prop.put("password",PASSWORD);
				//要把登入的資料丟入prop，"user""password"這個呀，不是資料庫裡面的，是登入系統的固定都是這兩個名子
				
				try (Connection conn=DriverManager.getConnection(URL,prop);PreparedStatement pstmd=conn.prepareStatement(SQL_LOGIN)){
					pstmd.setString(1, sc01.getText());
					ResultSet rs=pstmd.executeQuery();//結果集(ResultSet)是資料中查詢結果返回的一種物件
					if(rs.next()) {
						//偵測帳密正確後 rs.getString("這邊是欄位")
						if(BCrypt.checkpw(sc02.getText(), rs.getString("passwd"))) { //BCrypt(明碼寫前面,加密寫後面) rs(表結果)與裡面的密碼做偵測
							//登入 Member在class裡
							Member member = new Member (rs.getInt("id"),rs.getString("account"),rs.getString("realname"),null); //把結果丟入member
							
							JFrame jFrame = new JFrame();
						    //這邊跳新頁面
							//System.out.printf("歡迎%s/ %s\n",member.getRealname(),member.getAccount());
							setVisible(false);
							isLoggedIn = true;
							//revalidate();
						    new FundUser();
						    
						   
						}else {
							//有此帳號 密碼錯
							//System.out.println("登入失敗1");
							 JFrame jFrame = new JFrame();
						     JOptionPane.showMessageDialog(jFrame, "錯誤!  請重新確認");
						}
					}else {
						//帳號錯
						//System.out.println("登入失敗2");
						JFrame jFrame = new JFrame();
						JOptionPane.showMessageDialog(jFrame, "錯誤!  請重新確認");
					}
				}catch (Exception e1) {
					System.out.println(e1);
				}
				
				
			}
		});
		
		//noac清除資料
			noac.addActionListener(new ActionListener() {	
				public void actionPerformed(ActionEvent e) {
					sc01.setText("");
					sc02.setText("");
				}
			});
		
		
//----------------------------------------------------------------------------		
		
		
		//註冊
		String tabT2="註冊";
		
		JPanel newlogin =new JPanel();
		JLabel ne01=new JLabel("新帳號 : ");
		JTextField nesc01=new JTextField(15);
		newlogin.add(ne01);newlogin.add(nesc01);
		
		
		JLabel ne02=new JLabel("新密碼 : ");
		//JTextField nesc02=new JTextField(15);
		JPasswordField nesc02=new JPasswordField(15);
		newlogin.add(ne02);newlogin.add(nesc02);
		
		JLabel ne03=new JLabel("姓名 : ");
		JTextField nesc03=new JTextField(15);
		newlogin.add(ne03);newlogin.add(nesc03);
		
		JButton okne =new JButton("確定註冊");
		JButton none =new JButton("取消");
		newlogin.add(okne);newlogin.add(none);
		
		tabpane.addTab(tabT2, newlogin);
		
		//-----註冊--------
		okne.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Properties prop =new Properties();
				prop.put("user",USER);prop.put("password",PASSWORD);
				try {
					conn=DriverManager.getConnection(URL,prop);
					if(checkAccount(nesc01.getText())) {
						if(addMember(nesc01.getText(), nesc02.getText(), nesc03.getText())) {
							//System.out.println("註冊成功");
							JFrame jFrame = new JFrame();
							JOptionPane.showMessageDialog(jFrame, "註冊成功");
							//dispose();
//							setVisible(false);
//							revalidate();
						}else {
							//System.out.println("註冊失敗");
							JFrame jFrame = new JFrame();
							JOptionPane.showMessageDialog(jFrame, "註冊失敗");
						}
					}else {
						//相同帳號
						//System.out.println("account exist 帳號重複");
						JFrame jFrame = new JFrame();
						JOptionPane.showMessageDialog(jFrame, "此帳號已註冊");
					}
				}catch (Exception e1) {
					System.out.println(e1);
				}
				

			}

		});
		
		//none清除資料
		none.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				nesc01.setText("");
				nesc02.setText("");
				nesc03.setText("");
			}
		});
		
		

		setLayout(null);
		setSize(230,250);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
//註冊判斷的東東
	private static boolean checkAccount(String account) throws Exception {
		boolean ret;
		PreparedStatement pstmt=conn.prepareStatement(SQL_CHECK);
		pstmt.setString(1, account);
		ResultSet rs =pstmt.executeQuery();
		rs.next();
		ret=rs.getInt("count")==0;//=0表示沒有重複
		pstmt.close();
		
		return ret;
	}
//註冊判斷的東東	
	private static boolean addMember(String account,String passwd,String realname) throws Exception{
		boolean isok=false;
		PreparedStatement pstmt=conn.prepareStatement(SQL_INSERT);
		pstmt.setString(1, account);
		pstmt.setString(2, BCrypt.hashpw(passwd, BCrypt.gensalt())); //BCrypt.hashpw不要密碼呈現明碼 BCrypt.gensalt()產生亂數
		pstmt.setString(3, realname);
		isok=pstmt.executeUpdate()!=0;
		pstmt.close();
		
		return isok;
	}
	public static void main(String[] args) {
		new FundLogin();
	}
	
	
}