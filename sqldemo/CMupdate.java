package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class CMupdate extends JFrame implements ActionListener{
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
	JLabel no=new JLabel("课程号:");
	JTextField cno=new JTextField(10);
	JLabel name=new JLabel("课程名:");
	JTextField cname=new JTextField(20);
	JLabel tno=new JLabel("教师号:");
	JTextField ctno=new JTextField(10);
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
	
	public CMupdate(String str) {  //含参构造方法
		super("课程信息修改页面");
		setLayout(new FlowLayout());
		
		cno.setText(str);
		cno.setEditable(false);
		
		//设置字体
		no.setFont(f3);
		name.setFont(f3);
		tno.setFont(f3);
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
		p1.add(no);
		p1.add(cno);
		p1.add(name);
		p1.add(cname);
		p1.add(tno);
		p1.add(ctno);
		
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
			stmt.executeUpdate("insert into 课程表  values("+"'"+cno.getText().trim()+"'"+","+"'"+cname.getText().trim()+"'"+","+"'"+ctno.getText().trim()+"'"+")");
			//System.out.println("insert success!");
			JOptionPane.showMessageDialog(null,"课表信息修改成功！");
		    this.dispose();
		    new CM().display();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String s) {   //删除信息方法
		try {
			//stmt.executeUpdate("delete  from 教师表  where 教师号="+s);   //局部变量一定要初始化
			stmt.executeUpdate("delete  from 课程表  where 课程号="+s);
			//JOptionPane.showMessageDialog(null,"删除成功！");
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsure) {
			if(cno.getText().equals("") || cname.getText().equals("") || ctno.getText().equals("") ) {
				JOptionPane.showMessageDialog(null,"信息不能为空！");
			}else {
				this.delete(cno.getText());
				this.insert();
			}
		}
		if(e.getSource()==btnagain) {
			//cno.setText("");
			cname.setText("");
			ctno.setText("");
		}
		if(e.getSource()==btncancel) {
			this.dispose();
			new CM().display();;
			//System.exit(0);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CMadd();
    }
}
