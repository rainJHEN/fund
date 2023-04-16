//我處理介面
package a.mid09;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class Fund01 extends JFrame{
	private FundTable fundTable;//把隔壁的Table中間布景放進來
	private FundUser fundUser;

	
	public Fund01() throws Exception{
		super ("基金");
		
		//視窗左邊的元件	
		JPanel left=new JPanel();
		setLayout(new BorderLayout());
		//自由縮放圖片
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("dir1/ball1.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));//.getScaledInstance 創建此圖像的縮放版本  SCALE_DEFAULT 表示默認的圖像缩放算法
		JLabel label = new JLabel(imageIcon);
		//登入鈕/
		JButton login=new JButton("登入");
		left.setBackground(Color.LIGHT_GRAY);
		left.setOpaque(true);//底預設false為使panel變成了透明效果。
		left.add(label);
		left.add(login);
		add(left,BorderLayout.WEST);
		
		//登入
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FundLogin();	
			}
		});
		

		//視窗上方的元件
		JPanel top=new JPanel();
		JLabel topname=new JLabel("基金名稱 :");
		JTextField j1=new JTextField(8);//數字指框框多長
		JButton search=new JButton("搜尋");
		//加到我的最愛
		JLabel leftlove=new JLabel("  加入觀察 :");
		JTextField jlove=new JTextField(5);//數字指框框多長
		JButton searchlove=new JButton("加入");
		
		top.add(topname);
		top.add(j1);
		top.add(search);
		top.add(leftlove);
		top.add(jlove);
		top.add(searchlove);
		add(top,BorderLayout.NORTH);
		
		//加到我的最愛
		searchlove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String ccc = "";
					try {
						ccc = FundDB.getLove(jlove.getText(),FundDB.catchdata(jlove.getText()).get(1).toString(),FundDB.catchdata(jlove.getText()).get(2).toString());
					} catch (Exception e1) {
						System.out.println(e1);
					}
					JTextArea ccclove=new JTextArea(ccc);//使用JTextArea表多行文字
					JScrollPane scrolove=new JScrollPane(ccclove);
					add(scrolove);
					
				} catch (Exception e1) {
					System.out.println(e1);
				}
			}	
		});
		

		//搜尋
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Frame2();	
			}
			class Frame2 extends JFrame {
				public Frame2() {
					String aaa=FundDB.getName(j1.getText());
					JTextArea lta=new JTextArea(aaa);//使用JTextArea表多行文字
					add(lta);

					this.pack();
					this.setVisible(true);
					this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}

			}
		});
		
		

		fundTable=new FundTable();//把隔壁的FundTable中間布景放進來
		fundTable.setGridColor(Color.DARK_GRAY);//線條顏色
		fundTable.setAutoCreateRowSorter(true);//使用表啟用默認排序
		

		JScrollPane jsp=new JScrollPane(fundTable);//卷軸卷軸!!!!!
		add(jsp,BorderLayout.CENTER);
		  


		init();//把2個JFrame窗口連接在一起
		setSize(800,600);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}
	
	


	//-------來定義按鈕-把2個JFrame窗口連接在一起-------
	//定義一個不用回傳的方法
	private void init() {
		JMenuBar bar = new JMenuBar();//JMenuBar創建一个新的菜单欄
		JMenu menu = new JMenu("計算工具");
		JMenuItem item = new JMenuItem("定期定額");
		JMenuItem item2 = new JMenuItem("單筆投資");
		
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			new CFrome();//定期定額
			}
		});
		
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			new AFrome();//單筆投資
			}
		});
		
		menu.add(item);menu.add(item2);
		bar.add(menu);
		this.setJMenuBar(bar);
	}
	//---------------------------
	
	
	
	public static void main(String[] args) {
		try { //因為前面都拋出 在最後這邊來try就好
			new Fund01();
		}catch (Exception e) {
			System.out.println(e);
			System.exit(0);//離開 0代表直接結束
		}
		
	}

}
