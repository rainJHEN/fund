//想要自記寫Table Model，用setModel建構 --寫內部類別 或 super(繼承) TableModel 
package a.mid09;

import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserTable extends JTable{
	private UserModel userModel;
	private UserDB db;
	
	public UserTable() throws Exception {
		db=new UserDB();//有幾筆資料問db
		db.queryData("SELECT * FROM observe");
		userModel=new UserModel();
		setModel(userModel);
		//新增表頭
		userModel.setColumnIdentifiers(db.getHeader());
	}


	
	private class UserModel extends DefaultTableModel{
	//右鍵sourse>..methods 因為繼承DefaultTableModel
		@Override
		public int getRowCount() {
			return db.getRows(); //17.db.queryData("SELECT * FROM gift");後可以知道
		}

		@Override
		public int getColumnCount() {
			return db.getCols();//GiftDB
		}

		@Override
		public Object getValueAt(int row, int column) {
			return db.getData(row+1, column+1);//這邊不是資料庫 從0開始 要+1 //GiftDB
		}
		//即時連線
		@Override
		public void setValueAt(Object aValue, int row, int column) {
			db.updateData(row+1, column+1, (String)aValue);
		}
		//上右鍵下來
		@Override
		public boolean isCellEditable(int row, int column) {
			return column >0;
		}


		
	}
}

//想要自記寫Table Model，用setModel建構 --寫內部類別 或 super TableModel 