//定期定額
//Q.複利公式未完整
package a.mid09;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CFrome extends JFrame{
	private JButton ok01,no01;
	public CFrome(){
		JPanel jpa=new JPanel();
		JLabel lba=new JLabel("請注意 ! 此為過去資料試算，並非投資後實際損益");
		jpa.setLayout(new FlowLayout());
		jpa.add(lba);
		
		JPanel jpaa=new JPanel();
		JLabel lbaa=new JLabel("註:"
				+ "(1)假設投資期間基金無收益分配情形"
				+ "(2)以月分作為複利的期次"
				+ "(3)申購手續費預定為1.0%計算");
		jpaa.setLayout(new FlowLayout());
		jpaa.add(lbaa);
		
		JPanel jpb=new JPanel();
		JLabel lbb1=new JLabel("每月投資金額 :");
		JTextField lbb2 =new JTextField(15);
		JLabel lbb3=new JLabel("元(台幣)");
		jpb.setLayout(new FlowLayout());
		jpb.add(lbb1);jpb.add(lbb2);jpb.add(lbb3);
		
		JPanel jpc=new JPanel();
		JLabel lbc1=new JLabel("目標投資年報酬率 :");
		JTextField lbc2 =new JTextField(15);
		JLabel lbc3=new JLabel("%");
		jpc.setLayout(new FlowLayout());
		jpc.add(lbc1);jpc.add(lbc2);jpc.add(lbc3);
		
		JPanel jpd=new JPanel();
		JLabel lbd1=new JLabel("投資期間 :");
		JTextField lbd2 =new JTextField(15);
		JLabel lbd3=new JLabel("年");
		jpd.setLayout(new FlowLayout());
		jpd.add(lbd1);jpd.add(lbd2);jpd.add(lbd3);
		
		JPanel jpf=new JPanel();
		JLabel lbf1=new JLabel("申購手續費 :");
		JTextField lbf2 =new JTextField("1.0            ");
		JLabel lbf3=new JLabel("%");
		jpf.setLayout(new FlowLayout());
		jpf.add(lbf1);jpf.add(lbf2);jpf.add(lbf3);
		//----------------------------
		JPanel jpea=new JPanel();
		JLabel lbea1=new JLabel("您的投資到期總金額 : ");
		JTextField lbea2=new JTextField(15);
		lbea2.setEditable(false);//不能編輯
		jpea.setLayout(new FlowLayout());
		jpea.add(lbea1);jpea.add(lbea2);
		
		JPanel jpeb=new JPanel();
		JLabel lbeb1=new JLabel("投資成本 : ");
		JTextField lbeb2=new JTextField(15);
		lbeb2.setEditable(false);//不能編輯
		jpeb.setLayout(new FlowLayout());
		jpeb.add(lbeb1);jpeb.add(lbeb2);
		
		JPanel jpec=new JPanel();
		JLabel lbec1=new JLabel("投資獲利 : ");
		JTextField lbec2=new JTextField(15);
		lbec2.setEditable(false);//不能編輯
		jpec.setLayout(new FlowLayout());
		jpec.add(lbec1);jpec.add(lbec2);
		//-----------------------------------
		
		ok01=new JButton("試算結果");
		no01=new JButton("清除重填");
		JPanel down=new JPanel(new FlowLayout());
		down.add(ok01);down.add(no01);
		
		
		//ok01計算
		ok01.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Double cash=Double.parseDouble(lbb2.getText());// 投资金额
//				Double cash2 = cash;
				Double last=Double.parseDouble(lbd2.getText());// 投资期限（月）
				
				Double rate1=Double.parseDouble(lbc2.getText());
				Double rate2=Math.pow((1.0+rate1/100), last);// 年化收益率

				Double cost=Double.parseDouble(lbf2.getText());// 手续费（百分比）
				

//				for(int i=1;i<=last*12;i++) {
//					cash2=cash2*rate2+cash;
//				}
				//cash2=(double) Math.round(cash2-cash); //Math.round四捨五入
				Double cash2=((cash*last*12)*rate2)-(cash*(cost/100)*last*12);//投資到期總金額的複利計算方法??????
				cash2 = Math.round(cash2 * 100) / 100.0;
				Double cash3=(double) Math.round(cash*last*12*(101/100));
				cash3 = Math.round(cash3 * 100) / 100.0;
				Double cash4=cash2-cash3;
				
				
				lbea2.setText(String.valueOf(cash2));//您的投資到期總金額
				lbeb2.setText(String.valueOf(cash3));//成本
				lbec2.setText(String.valueOf(cash4));//獲利

				
			}
		});
		
		//no01清除資料
		no01.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				lbb2.setText("");
				lbc2.setText("");
				lbd2.setText("");
				lbea2.setText("");
				lbeb2.setText("");
				lbec2.setText("");
			}
		});
		
		this.setLayout(new GridLayout(13,1));
		this.add(jpa);//敘述
		jpa.setBackground(Color.yellow);
		this.add(jpaa);//敘述
		jpaa.setBackground(Color.yellow);
		this.add(jpb);//錢
		this.add(jpc);//報酬率
		this.add(jpd);//期間
		this.add(jpf);//手續費
		this.add(jpea);//計算結果
		this.add(jpeb);
		this.add(jpec);
		this.add(down);//按鈕

		setTitle("定期定額計算");
		//setSize(800,480);
		this.pack();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}


	public static void main(String[] args) {
		new CFrome();
		
	}
}	