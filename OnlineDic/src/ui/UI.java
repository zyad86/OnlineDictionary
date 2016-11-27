package ui;

import java.awt.*;
import javax.swing.*;

public class UI{
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		UI ui = new UI();
		
	}
	
	
	
	JFrame frame = new JFrame("Online Dictionary");
	JLabel label = new JLabel("input");//标签
	JLabel label1 = new JLabel("Online Dictionary");//标签
	JButton jbt = new JButton("Search");//按钮
	JCheckBox jchk1 = new JCheckBox("百度",false);//复选框
	JCheckBox jchk2 = new JCheckBox("有道",false);
	JCheckBox jchk3 = new JCheckBox("金山",false);
	JTextField jtf = new JTextField();//文本框
	
	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();
	JPanel jp3 = new JPanel();

	JLabel jlb1 = new JLabel();
	JLabel jlb2 = new JLabel();
	JLabel jlb3 = new JLabel();
	
	JButton jbt1 = new JButton();
	JButton jbt2 = new JButton();
	JButton jbt3 = new JButton();

	JTextArea jta1 = new JTextArea();
	JTextArea jta2 = new JTextArea();
	JTextArea jta3 = new JTextArea();
	




	
	public UI(){
		
//框架		
//		frame.setLayout(new BorderLayout());
		frame.setSize(600, 650);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setLayout(null);
//标签		
		label1.setBounds(200, 10, 180, 25);
		java.awt.Font font = new java.awt.Font("宋体", java.awt.Font.PLAIN, 20);
		label1.setFont(font);
		frame.add(label1);
		label.setBounds(20, 50, 80, 28);
		frame.add(label);


//按钮		
		jbt.setBounds(450, 50, 75, 28);
		frame.add(jbt);
		
		
//复选框		
		jchk1.setBounds(35, 90, 70, 28);
		frame.add(jchk1);
		jchk2.setBounds(230, 90, 70, 28);
		frame.add(jchk2);
		jchk3.setBounds(420, 90, 70, 28);
		frame.add(jchk3);
//文本框
		jtf.setBounds(80, 50, 350, 28);
		frame.add(jtf);
//文本区
	{
		jp1.setBounds(35, 130, 500, 120);	
		jp1.setBackground(Color.white);	
		jp1.setLayout(null);
		
		ImageIcon image1 = new ImageIcon("baidu.png");
		image1.setImage(image1.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT ));
		jlb1.setIcon(image1);
		jlb1.setSize(30, 30);
		jp1.add(jlb1);
		jlb1.setLocation(0, 0);
		jlb1.setVisible(true);
		
		ImageIcon img1 = new ImageIcon("dianzan.png");
		img1.setImage(img1.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT ));
		jbt1.setIcon(img1);
		jbt1.setSize(30, 30);
		jp1.add(jbt1);
		jbt1.setLocation(460, 80);
		jbt1.setVisible(true);
		
		jta1.setSize(440, 90);
		jp1.add(jta1);
		jta1.setLocation(35, 15);
		jta1.setLineWrap(true);
		jta1.setEditable(false);
		jta1.setVisible(true);
		
		jp1.setVisible(true);
		frame.getContentPane().add(jp1,BorderLayout.CENTER);
		
		////////////////////////////////////////////////////
		
		jp2.setBounds(35, 290, 500, 120);
		jp2.setBackground(Color.white);	
		jp2.setLayout(null);
		ImageIcon image2 = new ImageIcon("youdao.png");
		image2.setImage(image2.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT ));
		jlb2.setIcon(image2);
		jlb2.setSize(30, 30);
		jp2.add(jlb2);
		jlb2.setLocation(0, 0);
		jlb2.setVisible(true);
		
		ImageIcon img2 = new ImageIcon("dianzan.png");
		img2.setImage(img2.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT ));
		jbt2.setIcon(img2);
		jbt2.setSize(30, 30);
		jp2.add(jbt2);
		jbt2.setLocation(460, 80);
		jbt2.setVisible(true);
		
		jta2.setSize(440, 90);
		jp2.add(jta2);
		jta2.setLocation(35, 15);
		jta2.setLineWrap(true);
		jta2.setEditable(false);
		jta2.setVisible(true);
		
		jp2.setVisible(true);
		frame.getContentPane().add(jp2,BorderLayout.CENTER);
		
		/////////////////////////////////////////////////////////////
		
		jp3.setBounds(35, 440, 500, 120);
		jp3.setBackground(Color.white);	
		jp3.setLayout(null);
		ImageIcon image3 = new ImageIcon("jinshan.png");
		image3.setImage(image3.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT ));
		jlb3.setIcon(image3);
		jlb3.setSize(30, 30);
		jp3.add(jlb3);
		jlb3.setLocation(0, 0);
		jlb3.setVisible(true);
		
		ImageIcon img3 = new ImageIcon("dianzan.png");
		img3.setImage(img3.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT ));
		jbt3.setIcon(img3);
		jbt3.setSize(30, 30);
		jp3.add(jbt3);
		jbt3.setLocation(460, 80);
		jbt3.setVisible(true);
		
		jta3.setSize(440, 90);
		jp3.add(jta3);
		jta3.setLocation(35, 15);
		jta3.setLineWrap(true);
		jta3.setEditable(false);
		jta3.setVisible(true);
		
		jp3.setVisible(true);
		frame.getContentPane().add(jp3,BorderLayout.CENTER);	
		
		
	}
		
		frame.setVisible(true);
	}
}