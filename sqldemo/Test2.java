//该类用来测试设置窗口图片
package edu.yxs.sqldemo;
import javax.swing.*;
import java.awt.*;

public class Test2 extends JFrame{
	public Test2() {
		super("图片设置");
		ImageIcon ic=new ImageIcon("C:\\Users\\YXS\\eclipse-workspace\\edu-a-s\\src\\edu\\yxs\\sqldemo\\4.jpg");
		JLabel l=new JLabel(ic);
		l.setBounds(0,0,ic.getIconWidth(),ic.getIconHeight());
		//l.setBounds(0,0,this.getWidth(),this.getHeight());
		//JPanel ip=(JPanel)this.getContentPane();
		this.getLayeredPane().add(l,new Integer(Integer.MIN_VALUE));
		((JPanel)this.getContentPane()).setOpaque(false);//设置透明
		/*JPanel p=new JPanel();
		JLabel l=new JLabel();
		Icon ic=new ImageIcon("C:\\Users\\YXS\\eclipse-workspace\\edu-a-s\\src\\edu\\yxs\\sqldemo\\1.jpg");
		l.setIcon(ic);
		l.setBounds(0,0,ic.getIconWidth(),ic.getIconHeight());
		//JPanel ip=(JPanel)this.getContentPane();
		p.add(l,new Integer(Integer.MIN_VALUE));
		getContentPane().add(p);
		pack();*/
		setSize(500,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public static void main(String[] args) {
		new Test2();
	}

}
