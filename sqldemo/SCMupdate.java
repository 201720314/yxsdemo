package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class SCMupdate extends JFrame implements ActionListener{
	//定义面板
	Panel p=new Panel();
	Panel p1=new Panel();
	Panel p2=new Panel();
	/*Panel psno=new Panel();
	Panel psname=new Panel();
	Panel pssex=new Panel();
	Panel psage=new Panel();
	Panel psclass=new Panel();
	Panel pspwd=new Panel();*/
	//定义属性标签及文本框
	JLabel sno=new JLabel("学号:");
	JTextField sno1=new JTextField(10);
	JLabel cno=new JLabel("课程号:");
	JTextField cno1=new JTextField(20);
	JLabel sc=new JLabel("成绩:");
	JTextField sc1=new JTextField(10);
	//定义字体
	Font f1=new Font("宋体",Font.BOLD,20);
    Font f2=new Font("幼圆",Font.ITALIC,30);
    Font f3=new Font("楷体",Font.BOLD,18);
    Font f4=new Font("隶书",Font.PLAIN,40);
    //定义按钮
    Button btnsure=new Button("确定");
    Button btnagain=new Button("重置");
    Button btncancel=new Button("取消");
    
    //定义连接字符
  	String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=教务系统";
  	String userName = "sa";
  	String userPwd = "123";
  	Connection conn;
  	Statement stmt;
  	ResultSet rs;
  	
  	public void connDB() {   //连接数据库方法
		try {
			//连接数据库
			conn=DriverManager.getConnection(dbURL,userName,userPwd);
			stmt=conn.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
			//System.out.println("连接失败！");
		}
	}
	
	public void closeDB() {  //关闭数据库方法
		try {
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
			//System.out.println("关闭失败！");
		}
	}
	
	public SCMupdate(String s1,String s2) {  //含参构造方法
		super("成绩信息修改页面");
		setLayout(new FlowLayout());
		
		sno1.setText(s1);
		sno1.setEditable(false);
		cno1.setText(s2);
		cno1.setEditable(false);
		
		//设置字体
		sno.setFont(f3);
		cno.setFont(f3);
		sc.setFont(f3);
		//按钮字体、颜色
		btnsure.setFont(f1);
		btnsure.setBackground(new Color(131,175,155)); //淡草色
		btncancel.setFont(f1);
		btncancel.setBackground(new Color(131,175,155)); //淡草色
		btnagain.setFont(f1);
		btnagain.setBackground(new Color(131,175,155)); //淡草色
		//设置面板的网格布局管理
		p1.setLayout(new GridLayout(4,2));
		//p2.setLayout(new GridLayout(4,2));
		//将标签和文本框添加进面板
		p1.add(sno);
		p1.add(sno1);
		p1.add(cno);
		p1.add(cno1);
		p1.add(sc);
		p1.add(sc1);
		
		//将按钮添加进面板
		p.add(btnsure);
		p.add(btnagain);
		p.add(btncancel);
		
		//添加面板
		this.add(p1);
		//this.add(p2);
		this.add(p);
		
		//设置顶层容器的大小、位置、可见性及close功能
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(500,350);
	    setLocationRelativeTo(null);
	    setVisible(true);
	    
	    //注册监听器
	    btnsure.addActionListener(this);
	    btnagain.addActionListener(this);
	    btncancel.addActionListener(this);
	}
	
	public void insert() {  //插入方法
		try {
			this.connDB();
			//插入数据
			stmt.executeUpdate("insert into 成绩表  values("+"'"+sno1.getText().trim()+"'"+","+"'"+cno1.getText().trim()+"'"+","+"'"+sc1.getText().trim()+"'"+")");
			//System.out.println("insert success!");
			JOptionPane.showMessageDialog(null,"成绩信息添加成功！");
		    this.dispose();
		    new CM().display();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String s1,String s2) {   //删除信息方法
		try {
			//stmt.executeUpdate("delete  from 教师表  where 教师号="+s);   //局部变量一定要初始化
			stmt.executeUpdate("delete  from 成绩表  where 学号="+s1+"and 课程号="+s2);
			//JOptionPane.showMessageDialog(null,"删除成功！");
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsure) {
			if(sno1.getText().equals("") || cno1.getText().equals("") || sc1.getText().equals("") ) {
				JOptionPane.showMessageDialog(null,"信息不能为空！");
			}else {
				this.delete(sno1.getText(),cno1.getText());
				this.insert();
			}

		}
		if(e.getSource()==btnagain) {
			sno1.setText("");
			cno1.setText("");
			sc1.setText("");
		}
		if(e.getSource()==btncancel) {
			this.dispose();
			new SCM().display();;
			//System.exit(0);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SCMupdate(" "," ");
    }
}
