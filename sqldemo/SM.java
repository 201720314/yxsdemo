package edu.yxs.sqldemo;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SM extends JFrame implements ActionListener{   //学生管理系统
	//定义面板
	//JPanel p1=new JPanel();
	//JPanel p2=new JPanel();
	//创建5个按钮
	Button btnAdd=new Button("添加");
	Button btnDelete=new Button("删除");
	Button btnUpdate=new Button("修改");
	Button btnSearch=new Button("查询");
	Button btnDisplay=new Button("刷新显示");
	Button btnCancel=new Button("返回");
	//设置字体类型
	Font f1=new Font("宋体",Font.BOLD,30);
	Font f2=new Font("幼圆",Font.ITALIC,30);
	Font f3=new Font("行楷",Font.BOLD,15);
    Font f4=new Font("隶书",Font.PLAIN,40);
	//创建菜单栏
	JMenuBar mb=new JMenuBar();
	//定义表格
	JTable stable;
	JTable stable1;
	//定义滚动栏
	JScrollPane scroll;
	JScrollPane scroll1;
	
	//定义连接数据库对象
	//连接数据库和调用数据库
	Connection conn;
	Statement stmt;
	ResultSet rs;
		
	//定义连接字符
	String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=教务系统";
	String userName = "sa";
	String userPwd = "123";
	
	//定义数组
	Object[][] arr;  //由于Object为所有类的父类，可以接收任意数据类型的数组元素
	
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
	
	public SM() {
		super("学生信息管理页面");
		//布局管理
		//this.add("South",p1);
		//this.add("center",p1);
		//设置字体
		btnAdd.setFont(f3);
		btnAdd.setBackground(new Color(131,175,155)); //淡草色
		btnDelete.setFont(f3);
		btnDelete.setBackground(new Color(131,175,155));
		btnUpdate.setFont(f3);
		btnUpdate.setBackground(new Color(131,175,155));
		btnSearch.setFont(f3);
		btnSearch.setBackground(new Color(131,175,155));
		btnDisplay.setFont(f3);
		btnDisplay.setBackground(new Color(131,175,155));
		btnCancel.setFont(f3);
		btnCancel.setBackground(new Color(131,175,155));
		
		//将按钮添加进菜单栏中，与直接放进面板的按钮不同
		mb.add(btnAdd);
		mb.add(btnDelete);
		mb.add(btnUpdate);
		mb.add(btnSearch);
		mb.add(btnDisplay);
		mb.add(btnCancel);
		
		//连接数据库
		this.connDB();
		
		//注册时间监听器
		btnAdd.addActionListener(this);
		btnDelete.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnSearch.addActionListener(this);
		btnDisplay.addActionListener(this);
		btnCancel.addActionListener(this);

		//设置窗口的大小和位置
		setSize(500,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setJMenuBar(mb);  //添加菜单栏以后则不需要用监听器来关闭窗口
		this.setVisible(true);
	}
	
	
	public void display() {  //显示所有学生的信息
		int i=0,j=0,k=0;
		//长度可变数组集合
		ArrayList al=new ArrayList();
		
		//注意：该try{}catch{}目的即求数据库中数据的组数
		try {
			rs=stmt.executeQuery("select * from 学生表  ");
			while(rs.next()) {   //首先用数组al来接收所有信息
				//注意：如果trim()匹配到null数据，则数据库会报错
				al.add(rs.getString("学号").trim());
				al.add(rs.getString("姓名").trim());
				al.add(rs.getString("性别").trim());
				al.add(rs.getInt("年龄"));
				al.add(rs.getString("班级号").trim());
				al.add(rs.getString("密码").trim());
				i++;  //用来统计信息组数
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//定义二维数组
		arr=new Object[i][6];
		//创建菜单栏头部名
		String[] columnNames= {"学号","姓名","性别","年龄","班级号","密码"};
		//该代码块才是最终对信息进行分组
		try {
			rs=stmt.executeQuery("select * from 学生表  order by 学号");
			while(rs.next()) {   //首先用数组al来接收所有信息
				arr[j][0]=rs.getString("学号").trim();
				arr[j][1]=rs.getString("姓名").trim();
				arr[j][2]=rs.getString("性别").trim();
				arr[j][3]=rs.getInt("年龄");
				arr[j][4]=rs.getString("班级号").trim();
				arr[j][5]=rs.getString("密码").trim();
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		stable=new JTable(arr,columnNames); //创建表格
		scroll=new JScrollPane(stable);
		this.add(scroll);  //创建滚条
	}
	
	public void delete() {   //删除信息方法
		String sno=null;  //定义字符变量，来接收学号   
		int row=stable.getSelectedRow();  //代表鼠标选定的行数
		if(row==-1) {  //表示未被选中
			JOptionPane.showMessageDialog(null,"请选择要删除的记录！");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from 学生表");
				while(rs.next() && x<=row) {
					sno=rs.getString("学号");
					x++;
				}
				stmt.executeUpdate("delete  from 学生表  where 学号="+sno);   //局部变量一定要初始化
				stmt.executeUpdate("delete  from 成绩表  where 学号="+sno);
				JOptionPane.showMessageDialog(null,"删除成功！");
				this.dispose();
				new SM().display();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		String sno1=null; //局部变量一定要初始化
		int row=stable.getSelectedRow();  //代表鼠标选定的行数
		if(row==-1) {  //表示未被选中
			JOptionPane.showMessageDialog(null,"请选择要修改的信息！");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from 学生表");
				while(rs.next() && x<=row) {
					sno1=rs.getString("学号");
					x++;
				}
				this.dispose();
				new SMupdate(sno1);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	public void show(String str) {   //查询结果方法
		JFrame f1=new JFrame("查询结果");
		f1.setLayout(new FlowLayout());
		f1.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f1.setSize(500,150);
		f1.setVisible(true);
		f1.setLocationRelativeTo(null);
		Button btnrt=new Button("返回");
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));

		this.connDB();
		arr=new Object[1][6];
		try {
			rs=stmt.executeQuery("select * from 学生表  where 学号="+str);
			while(rs.next()) {
				arr[0][0]=rs.getString("学号");
				arr[0][1]=rs.getString("姓名");
				arr[0][2]=rs.getString("性别");
				arr[0][3]=rs.getInt("年龄");
				arr[0][4]=rs.getString("班级号");
				arr[0][5]=rs.getString("密码");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] columnNames= {"学号","姓名","性别","年龄","班级号","密码"};
		stable1=new JTable(arr,columnNames); //创建表格
		scroll1=new JScrollPane(stable1);
		f1.add(btnrt);
		f1.add(scroll1);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt);
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f1.dispose();
			}
		});
	}
	
	//该方法用来确认是否在数据库中找到学号
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
	
	public void search() {   //等效于将一个窗口写在方法里面
		JFrame f=new JFrame("查询");
		f.setLayout(new FlowLayout());
		f.setSize(240,180);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JLabel stuno=new JLabel("输入学号：");
		JTextField stuno1=new JTextField(10);
		Button ok=new Button("确定");
		Button cancel=new Button("取消");
		p1.add(stuno);
		p1.add(stuno1);
		p2.add(ok);
		p2.add(cancel);
		f.add(p1);
		f.add(p2);
		//为组件注册监听器
		ok.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(stuno1.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"请输入学号");
				}else {
					if(!(searchtest(stuno1.getText().trim()))) {
						f.dispose();
						JOptionPane.showMessageDialog(null,"对不起，该学生不存在！");
					}else {
					    f.dispose();            
					    //new SM(stuno1.getText());
					    show(stuno1.getText());
					}
				}
			}
		});
		cancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				//
			}
		});
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Window w=(Window)e.getComponent();  
				w.dispose();
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnAdd) {
			this.dispose();
			new SMadd();
		}
		if(e.getSource()==btnDelete) {
            JOptionPane.showMessageDialog(null,"删除，请谨慎操作！");
			this.delete();
		}
        if(e.getSource()==btnUpdate) {
			this.update(); 
		}
        if(e.getSource()==btnSearch) {
        	this.search(); 
        }
        if(e.getSource()==btnDisplay) {
        	this.dispose();
        	new SM().display();
        }
        if(e.getSource()==btnCancel) {
        	this.dispose();
        	new GLFrame();
        }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SM().display();
	}

}

/*2017.11.25*/