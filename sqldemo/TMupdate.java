//更新类
package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class TMupdate extends JFrame implements ActionListener,ItemListener{
	//定义面板
	Panel p=new Panel();
	Panel p1=new Panel();
	Panel p2=new Panel();
	//定义属性标签及文本框
	JLabel no=new JLabel("教师号:");
	JTextField tno=new JTextField(10);
	JLabel name=new JLabel("姓名:");
	JTextField tname=new JTextField(20);
	JLabel sex=new JLabel("性别:");
	JComboBox tsex=new JComboBox();
	//JTextField ssex=new JTextField(4);
	JLabel xl=new JLabel("学历:");
	JTextField txl=new JTextField(4);
	JLabel zc=new JLabel("职称:");
	JTextField tzc=new JTextField(10);
	JLabel dept=new JLabel("所属学院:");
	JTextField tdept=new JTextField(10);
	JLabel pwd=new JLabel("密码:");
	JTextField tpwd=new JTextField(10);
	JLabel pwd1=new JLabel("确认密码:");
	JTextField tpwd1=new JTextField(10);
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
	
	public TMupdate(String str) {  //含参构造方法
		super("教师信息修改页面");
		setLayout(new FlowLayout());
		
		tno.setText(str);
		tno.setEditable(false);
		
		//设置字体
		no.setFont(f3);
		name.setFont(f3);
		sex.setFont(f3);
	    xl.setFont(f3);
		zc.setFont(f3);
		dept.setFont(f3);
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
		p1.setLayout(new GridLayout(8,2));
		//p2.setLayout(new GridLayout(4,2));
		//性别框下拉列表添加选项
		tsex.addItem("男");
		tsex.addItem("女");
		//将标签和文本框添加进面板
		p1.add(sex);
		p1.add(tsex);
		p1.add(no);
		p1.add(tno);
		p1.add(name);
		p1.add(tname);
		p1.add(xl);
		p1.add(txl);
		p1.add(zc);
		p1.add(tzc);
		p1.add(dept);
		p1.add(tdept);
		p1.add(pwd);
		p1.add(tpwd);
		p1.add(pwd1);
		p1.add(tpwd1);
		
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
	    tsex.addItemListener(this);
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
	
	public void delete(String s) {   //删除信息方法
		try {
			stmt.executeUpdate("delete  from 教师表  where 教师号="+s);   //局部变量一定要初始化
			stmt.executeUpdate("delete  from 课程表  where 教师号="+s);
			//JOptionPane.showMessageDialog(null,"删除成功！");
		}catch(Exception e) {
			e.printStackTrace();
		}
}
	
	public void insert() {  //插入方法
		try {
			this.connDB();
			//插入数据
			stmt.executeUpdate("insert into 教师表  values("+tno.getText().trim()+","+"'"+tname.getText().trim()+"'"+","+"'"+sexlist[sex1]+"'"+","+"'"+txl.getText().trim()+"'"+","+"'"+tzc.getText().trim()+"'"+","+"'"+tdept.getText().trim()+"'"+","+tpwd1.getText().trim()+")");
			//System.out.println("insert success!");
			JOptionPane.showMessageDialog(null,"信息修改成功！");
		    this.dispose();
		    new TM().display();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsure) {
			if(tno.getText().equals("") || tname.getText().equals("") || txl.getText().equals("") || tpwd.getText().equals("") || tpwd1.getText().equals("") || tzc.getText().equals("") || tdept.getText().equals("") ) {
				JOptionPane.showMessageDialog(null,"信息不能为空！");
			}else if(!tpwd.getText().trim().equals(tpwd1.getText().trim())) {  //确认密码是否相同
				JOptionPane.showMessageDialog(null,"请重新确认密码！");
			}else {
				//确认时，将删除信息再重新添加
				this.delete(tno.getText());
				this.insert();
			}

		}
		if(e.getSource()==btnagain) {
			//tno.setText("");
			tname.setText("");
			txl.setText("");
			tzc.setText("");
			tpwd.setText("");
			tpwd1.setText("");
		}
		if(e.getSource()==btncancel) {
			this.dispose();
			new TM().display();;
			//System.exit(0);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TMupdate("  ");
    }
}

/*2017.12.06*/