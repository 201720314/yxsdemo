package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GLFrame extends JFrame implements ActionListener{

	///////////////注意////////////////
	//在类里面只有成员变量和方法，切忌在成员变量里直接定义
	//“管理员”标签
	JLabel l=new JLabel("--管理员--");
	//定义面板容器
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	//设置字体类型
	Font f1=new Font("宋体",Font.BOLD,30);
    Font f2=new Font("幼圆",Font.ITALIC,30);
    Font f3=new Font("楷体",Font.BOLD,18);
    Font f4=new Font("隶书",Font.PLAIN,40);
	
	//设置6个按钮，以便管理员操作
	JButton btnTM=new JButton("教师信息管理");
	JButton btnSM=new JButton("学生信息管理");
	JButton btnCM=new JButton("课程信息管理");
	JButton btnSCM=new JButton("成绩信息管理");
	JButton btnCSM=new JButton("班级信息管理");
	JButton btnEXIT=new JButton("退出管理系统");
	
	public GLFrame() {   //构造方法
		super("管理员管理页面");
		setLayout(new FlowLayout());
		//设置标签的颜色
	    l.setFont(f1);
	    l.setForeground(Color.blue);
	    //设置按钮字体和颜色
	    btnTM.setFont(f3);
	    btnTM.setContentAreaFilled(false);
	    //btnTM.setBackground(Color.blue);
	    btnSM.setFont(f3);
	    btnSM.setContentAreaFilled(false);
	    btnCM.setFont(f3);
	    btnCM.setContentAreaFilled(false);
	    btnSCM.setFont(f3);
	    btnSCM.setContentAreaFilled(false);
	    btnCSM.setFont(f3);
	    btnCSM.setContentAreaFilled(false);
	    btnEXIT.setFont(f3);
	    btnEXIT.setContentAreaFilled(false);
	    
		p1.add(l);
		p1.setOpaque(false);
		p2.setLayout(new GridLayout(3,2,10,10));
		p2.setOpaque(false);
		p2.add(btnTM);
		p2.add(btnSM);
		p2.add(btnCM);
		p2.add(btnSCM);
		p2.add(btnCSM);
		p2.add(btnEXIT);
		 
		//布局管理器
		this.add(p1);
		this.add(p2);
		
		//设置背景图片
		ImageIcon ic=new ImageIcon("C:\\Users\\YXS\\eclipse-workspace\\edu-a-s\\src\\edu\\yxs\\sqldemo\\5.jpg");
		JLabel l=new JLabel(ic);
		l.setBounds(0,0,ic.getIconWidth(),ic.getIconHeight());
		//l.setBounds(0,0,this.getWidth(),this.getHeight());
		//JPanel ip=(JPanel)this.getContentPane();
		this.getLayeredPane().add(l,new Integer(Integer.MIN_VALUE));
		((JPanel)this.getContentPane()).setOpaque(false);//设置透明
		
		//设置顶层容器的大小、位置、可见性及close功能
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350,300);
		setLocationRelativeTo(null);
		setVisible(true);
		
		btnTM.addActionListener(this);
		btnSM.addActionListener(this);
		btnCM.addActionListener(this);
		btnSCM.addActionListener(this);
		btnCSM.addActionListener(this);
		btnEXIT.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		//按钮为“教师信息管理”，跳转页面
		if(e.getSource().equals(btnTM)) {
			this.dispose();
			new TM().display();
		}
		//按钮为“学生信息管理”，跳转页面
		if(e.getSource().equals(btnSM)) {
			//new SM().display();
			this.dispose();
			new SM().display();
		}
		//按钮为“课程信息管理”，跳转页面
		if(e.getSource().equals(btnCM)) {
			this.dispose();
			new CM().display();
		}
		//按钮为“成绩信息管理”，跳转页面
		if(e.getSource().equals(btnSCM)) {
			this.dispose();
			new SCM().display();
		}
		//按钮为“班级信息管理”，跳转页面
		if(e.getSource().equals(btnCSM)) {
			this.dispose();
			new ClassM().display();
		}
		//按钮为“退出管理系统”，程序退出
		if(e.getSource().equals(btnEXIT)) {
        	//System.exit(0);
			this.dispose();
			new DLFrame();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GLFrame();
	}

}

/*2017.11.23*/