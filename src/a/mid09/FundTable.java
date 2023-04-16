package a.mid09;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
public class FundTable extends JTable{
	private FundModel fundModel;
	private FundDB db;
	
	public FundTable() throws Exception {
		db=new FundDB();//有幾筆資料問db
		db.queryData("SELECT * FROM allianz");//queryData查詢數據
		fundModel=new FundModel();
		setModel(fundModel);//想要自記寫Table Model，用setModel建構
		fundModel.setColumnIdentifiers(db.getHeader());//新增表頭	
	}
	
	
	private class FundModel extends DefaultTableModel{

	//右鍵sourse>..methods 因為繼承DefaultTableModel
		@Override
		public int getRowCount() {//獲取行數
			return db.getRows(); //17.db.queryData("SELECT * FROM allianz");後可以知道
		}
		@Override
		public int getColumnCount() {//獲取列數
			return db.getCols();//DundDB
		}
		@Override
		public Object getValueAt(int row, int column) {//獲取值
			return db.getData(row+1, column+1);//這邊不是資料庫 從0開始 要+1 //GiftDB
		}
		
	}
}
	