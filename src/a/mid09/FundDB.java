//專門處理基金資料類別
package a.mid09;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class FundDB {
	private final static String USER = "root"; 
	private final static String PASSWORD = "root";
	private final static String URL = "jdbc:mysql://localhost:3306/fund";
	private final static String SQL_ID="SELECT * FROM allianz WHERE id = ? ";
	private final static String SQL_INSERT = "INSERT INTO observe (id,name,company) VALUES (?,?,?)";
	
	private static Connection conn; //連線
	private ResultSet rs;
	private String[] fieldNames;
	
	public FundDB() throws SQLException {
		Properties prop = new Properties();//其中的driver、url、user與password等設定，可以將之撰寫在一個.properties(特性)檔案中
		prop.put("user", USER); prop.put("password", PASSWORD);
		conn = DriverManager.getConnection(URL, prop);//直接宣告拋出例外throws SQLException 因為這邊資料錯 就不跑了		
	}
	
	
	
	
	//開始寫查詢跟方法
	public void queryData(String sql) throws SQLException {//queryData為allianz所有資料
		Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);/*針對滾輪+更新*/
		rs=stmt.executeQuery(sql);
	

		ResultSetMetaData rsmd=rs.getMetaData(); //官方的ResultSetMetaData
		int colCount = rsmd.getColumnCount();//想知道幾個欄位名稱 colCount上面的欄位 id.name..
		fieldNames=new String[colCount];//問有幾個元素
		//順邊攔位字串陣列
		for (int i=0;i<colCount;i++) {
			fieldNames[i]=rsmd.getColumnName(i+1);//Column從幾開始?????1
			//getColumnName SQL用 從1開始
			//fieldNames java用 從0開始
		}
		
	}
	

//---搜尋的方法---
	public static String getName(String name) {
		String str_sum="先給空值";
		try {
			String se="SELECT * FROM allianz WHERE name LIKE ? OR company LIKE ?";
			PreparedStatement pstse=conn.prepareStatement(se);
			pstse.setString(1, "%"+name+"%");
			pstse.setString(2, "%"+name+"%");
			ResultSet rsse=pstse.executeQuery();
			str_sum="";
			while (rsse.next()) {
				String v1=rsse.getString("id");
				String v2=rsse.getString("name");
				String v3=rsse.getString("company");
				String temp_return=String.format(" id= %s 、 基金名稱= %s ( %s ) \n",v1,v2,v3);
				
				str_sum=str_sum+temp_return;
				//System.out.println(str_sum);
				//System.out.printf("%s:%s:%s\n",v1,v2,v3);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return str_sum;
	}
//-------------
	//SELECT * FROM observe JOIN allianz ON observe.id=allianz.id;
	//SELECT getid.id,allianz.name,allianz.company,allianz.day,allianz.OneMonth,allianz.ThreeMonth,allianz.SixMonth, allianz.OneYear,allianz.ThreeYear,allianz.FiveYear,allianz.TenYear FROM observe JOIN allianz ON observe.id=allianz.id;
	//加入我的最愛
	
	public static String getLove(String id,String name,String company)  {
		try {
			if (FundLogin.isLoggedIn) {
				//以登入程式碼
				if(addMember(id, name, company)) {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "新增成功(請重新整理)");
				}else {
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "新增失敗");
				}
			}else {
				//未登入
				JFrame jFrame = new JFrame();
	            JOptionPane.showMessageDialog(jFrame, "請先登入");
			}
				
		} catch (Exception e) {
			System.out.println(e);
		}	
		return "";
		
	}
		

		




		public static ArrayList catchdata(String idd) throws Exception{
			ArrayList<String> a=new ArrayList<String>();
			PreparedStatement pstmt=conn.prepareStatement(SQL_ID);
			pstmt.setString(1, idd);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				String v1=rs.getString("id");
				String v2=rs.getString("name");
				String v3=rs.getString("company");
				a.add(v1);a.add(v2);a.add(v3);	
				
				
				String str="";
				for(String aa:a) {
					str+=aa+",";
				}
//				System.out.println("aa"+str);
			}
			return a;	//String.join(",",a) 錯
		}
	
	


		private static boolean addMember(String id,String name,String company) throws Exception{
			boolean isok=false;
			PreparedStatement pstmt=conn.prepareStatement(SQL_INSERT);
			pstmt.setString(1, id);
			pstmt.setString(2, name); 
			pstmt.setString(3, company);
			isok=pstmt.executeUpdate()!=0;
			pstmt.close();
			
			return isok;
		}
	
	//如果有例外 代表移動或取得有問題 return 0例外
	public int getRows() {
		try {
			rs.last();
			return rs.getRow(); //到最後一筆了 >總比數
		} catch (SQLException e) {
			return 0;
		}
	}
	
	public int getCols(){
		return fieldNames.length;
	}
	
	public String getData(int row,int col) {
		try {
			rs.absolute(row);
			String data=rs.getString(col);
			return data;
		} catch (Exception e) {
			System.out.println(e);
			return("xx");
		}
		
	}
	public String[] getHeader() {
		return fieldNames;
	}

}