package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ClassMupdate extends JFrame implements ActionListener{
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
	JLabel csno=new JLabel("班级号:");
	JTextField csno1=new JTextField(10);
	JLabel csname=new JLabel("班级名称:");
	JTextField csname1=new JTextField(20);
	JLabel dept=new JLabel("所属学院:");
	JTextField dept1=new JTextField(10);
	JLabel sum=new JLabel("班级人数");
	JTextField sum1=new JTextField(10);
	JLabel cm=new JLabel("班主任");
	JTextField cm1=new JTextField(20);
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
	
	public ClassMupdate(String s1,String s2,String s3) {  //含参构造方法
		super("班级信息修改页面");
		setLayout(new FlowLayout());
		
		csno1.setText(s1);
		csno1.setEditable(false);
		csname1.setText(s2);
		csname1.setEditable(false);
		dept1.setText(s3);
		dept1.setEditable(false);
		
		//设置字体
		csno.setFont(f3);
		csname.setFont(f3);
		dept.setFont(f3);
		sum.setFont(f3);
		cm.setFont(f3);
		//按钮字体、颜色
		btnsure.setFont(f1);
		btnsure.setBackground(new Color(131,175,155)); //淡草色
		btncancel.setFont(f1);
		btncancel.setBackground(new Color(131,175,155)); //淡草色
		btnagain.setFont(f1);
		btnagain.setBackground(new Color(131,175,155)); //淡草色
		//设置面板的网格布局管理
		p1.setLayout(new GridLayout(6,2));
		//p2.setLayout(new GridLayout(4,2));
		//将标签和文本框添加进面板
		p1.add(csno);
		p1.add(csno1);
		p1.add(csname);
		p1.add(csname1);
		p1.add(dept);
		p1.add(dept1);
		p1.add(sum);
		p1.add(sum1);
		p1.add(cm);
		p1.add(cm1);
		
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
	
	public void delete(String s) {   //删除信息方法
		try {
			//stmt.executeUpdate("delete  from 教师表  where 教师号="+s);   //局部变量一定要初始化
			stmt.executeUpdate("delete  from 班级表  where 班级号="+"'"+s+"'");
			//JOptionPane.showMessageDialog(null,"删除成功！");
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	public void insert() {  //插入方法
		try {
			this.connDB();
			//插入数据
			stmt.executeUpdate("insert into 班级表  values("+"'"+csno1.getText().trim()+"'"+","+"'"+csname1.getText().trim()+"'"+","+"'"+dept1.getText().trim()+"'"+","+sum1.getText().trim()+","+"'"+cm1.getText().trim()+"'"+")");
			//System.out.println("insert success!");
			JOptionPane.showMessageDialog(null,"班级信息添加成功！");
		    this.dispose();
		    new ClassM().display();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsure) {
			if(csno1.getText().equals("") || csname1.getText().equals("") || dept1.getText().equals("") || sum1.getText().equals("") || cm1.getText().equals("") ) {
				JOptionPane.showMessageDialog(null,"信息不能为空！");
			}else {
				this.delete(csno1.getText());
				this.insert();
			}
		}
		if(e.getSource()==btnagain) {
			csno1.setText("");
			csname1.setText("");
			dept1.setText("");
			sum1.setText("");
			cm1.setText("");
		}
		if(e.getSource()==btncancel) {
			this.dispose();
			new ClassM().display();;
			//System.exit(0);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ClassMupdate("","","");
    }
}

/*2017.12.06*/