//專門處理資料類別
package a.mid09;

import java.net.FileNameMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class UserDB {
	private final static String USER = "root"; 
	private final static String PASSWORD = "root";
	private final static String URL = "jdbc:mysql://localhost:3306/fund";
	private Connection conn; //連線
	private ResultSet rs;
	private String[] fieldNames;
	
	public UserDB() throws SQLException {
		Properties prop = new Properties();
		prop.put("user", USER); prop.put("password", PASSWORD);
		conn = DriverManager.getConnection(URL, prop);//直接宣告拋出例外throws SQLException 因為這邊資料錯 就不跑了
		
	}
	
	//開始寫查詢跟方法
	public void queryData(String sql) throws SQLException {
		Statement stmt= conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);/*針對滾輪+更新*/
		rs=stmt.executeQuery(sql);
		//System.out.println(rs); >>com.mysql.cj.jdbc.result.UpdatableResultSet@52719fb6
		//System.out.println(sql); >>SELECT * FROM observe2
		ResultSetMetaData rsmd=rs.getMetaData();
		//System.out.println(rsmd); >> 每個欄位的明細，像這個id是甚麼資料庫、甚麼名子、長度
		int colCount = rsmd.getColumnCount();
		//System.out.println(colCount); >> 3
		
		//順便欄位字串陣列
		fieldNames=new String[colCount];
		//System.out.println(fieldNames); >>做一個存放物件的字串 [Ljava.lang.String;@7b36aa0c
		for (int i=0;i<colCount;i++) {
			fieldNames[i]=rsmd.getColumnName(i+1);
			//SQL從1
			//java從0
			//fieldNames[0](java)=rsmd.getColumnName(i+1);(資料庫的1)
		}
		
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
	
	public void updateData (int row,int col,String data) {
		try {
			rs.absolute(row);
			rs.updateString(col, data);
			rs.updateRow();
		}catch (Exception e) {
			System.out.println(e);
		}	
	}

}