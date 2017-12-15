package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class SMadd extends JFrame implements ActionListener,ItemListener{
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
	JLabel no=new JLabel("学号:");
	JTextField sno=new JTextField(10);
	JLabel name=new JLabel("姓名:");
	JTextField sname=new JTextField(20);
	JLabel sex=new JLabel("性别:");
	JComboBox ssex=new JComboBox();
	//JTextField ssex=new JTextField(4);
	JLabel age=new JLabel("年龄:");
	JTextField sage=new JTextField(4);
	JLabel classno=new JLabel("班级号:");
	JTextField sclassno=new JTextField(10);
	JLabel pwd=new JLabel("密码:");
	JTextField spwd=new JTextField(10);
	JLabel pwd1=new JLabel("确认密码:");
	JTextField spwd1=new JTextField(10);
	//定义字体
	Font f1=new Font("宋体",Font.BOLD,20);
    Font f2=new Font("幼圆",Font.ITALIC,30);
    Font f3=new Font("楷体",Font.BOLD,18);
    Font f4=new Font("隶书",Font.PLAIN,40);
    //定义按钮
    Button btnsure=new Button("确定");
    Button btnagain=new Button("重置");
    Button btncancel=new Button("取消");
    
    int sex1=0;  //接收下拉列表文本
    String[] sexlist= {"男","女"};
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
	
	public SMadd() {  //构造方法
		super("学生信息添加页面");
		setLayout(new FlowLayout());
		
		//设置字体
		no.setFont(f3);
		name.setFont(f3);
		sex.setFont(f3);
	    age.setFont(f3);
		classno.setFont(f3);
		pwd.setFont(f3);
		pwd1.setFont(f3);
		//按钮字体、颜色
		btnsure.setFont(f1);
		btnsure.setBackground(new Color(131,175,155)); //淡草色
		btncancel.setFont(f1);
		btncancel.setBackground(new Color(131,175,155)); //淡草色
		btnagain.setFont(f1);
		btnagain.setBackground(new Color(131,175,155)); //淡草色
		//设置面板的网格布局管理
		p1.setLayout(new GridLayout(7,2));
		//p2.setLayout(new GridLayout(4,2));
		//性别框下拉列表添加选项
		ssex.addItem("男");
		ssex.addItem("女");
		//ssex.setOpaque(false);
		//将标签和文本框添加进面板
		p1.add(sex);
		p1.add(ssex);
		p1.add(no);
		p1.add(sno);
		p1.add(name);
		p1.add(sname);
		p1.add(age);
		p1.add(sage);
		p1.add(classno);
		p1.add(sclassno);
		p1.add(pwd);
		p1.add(spwd);
		p1.add(pwd1);
		p1.add(spwd1);
		
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
	    ssex.addItemListener(this);
	    btnsure.addActionListener(this);
	    btnagain.addActionListener(this);
	    btncancel.addActionListener(this);
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED) {
			JComboBox j=(JComboBox)e.getSource();
			sex1=j.getSelectedIndex();
		}
	}
	
	public void insert() {  //添加插入方法
		//用来插入数据
		try {
			this.connDB();
			//插入数据
			stmt.executeUpdate("insert into 学生表  values("+"'"+sno.getText().trim()+"'"+","+"'"+sname.getText().trim()+"'"+","+"'"+sexlist[sex1]+"'"+","+sage.getText().trim()+","+"'"+sclassno.getText().trim()+"'"+","+"'"+spwd1.getText().trim()+"'"+")");
			//System.out.println("insert success!");
			JOptionPane.showMessageDialog(null,"信息添加成功！");
		    this.dispose();
		    new SM();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	//用来寻找学号是否存在
	public boolean searchtest(String str) {
		boolean x=false;
		this.connDB();
		try {
			rs=stmt.executeQuery("select * from 学生表");
			while(rs.next()) {
				if(rs.getString("学号").trim().equals(str)) {
					x=true;
				}
			}
			//return x;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return x;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsure) {
			//先判断填入信息是否为空，在判断密码是否正确
			if(sno.getText().equals("") || sname.getText().equals("") || sage.getText().equals("") || sclassno.getText().equals("") || spwd.getText().equals("") || spwd1.getText().equals("") ) {
				JOptionPane.showMessageDialog(null,"信息不能为空！");
			}else if(!spwd.getText().trim().equals(spwd1.getText().trim())) {  //确认密码是否相同
				JOptionPane.showMessageDialog(null,"请重新确认密码！");
				spwd1.setText("");
			}else if(searchtest(sno.getText())) {
				JOptionPane.showMessageDialog(null,"该学生已存在");
				sno.setText("");
				sname.setText("");
				sage.setText("");
				sclassno.setText("");
				spwd.setText("");
				spwd1.setText("");
			}else {
				this.insert();
			}

		}
		if(e.getSource()==btnagain) {
			sno.setText("");
			sname.setText("");
			sage.setText("");
			sclassno.setText("");
			spwd.setText("");
			spwd1.setText("");
		}
		if(e.getSource()==btncancel) {
			this.dispose();
			new SM().display();;
			//System.exit(0);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SMadd();
    }

}

/*2017.11.26*/