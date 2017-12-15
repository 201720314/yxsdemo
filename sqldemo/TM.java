package edu.yxs.sqldemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class TM extends JFrame implements ActionListener,ItemListener{
	//定义按钮，添加进JMenuBar
	Button btnadd=new Button("添加");
	Button btndelete=new Button("删除");
	Button btnupdate=new Button("修改");
	Button btnsearch=new Button("查询");
	Button btndisplay=new Button("刷新显示");
	Button btnreturn=new Button("返回");
	//定义菜单栏
	JMenuBar mb=new JMenuBar();
	//定义字体
	Font f1=new Font("行楷",Font.BOLD,15);
	Font f2=new Font("楷体",Font.ITALIC,24);
	
	//定义表格
	JTable tb;
	JTable tb1;
	//定义滚轮面板
	JScrollPane jsp;
	JScrollPane scroll1;
	
	//定义数据库对象,定义连接变量
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	//定义连接字符串
	String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=教务系统";
	String username="sa";
	String userpwd="123";
	
	Object[][] arr;
	//连接数据库
	public void connDB(){  //连接数据库方法
		try {
			conn=DriverManager.getConnection(dbURL,username,userpwd);
			stmt=conn.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeDB() {  //关闭数据库方法
		try {
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public TM() {   //构造方法
		super("教师信息管理系统");
		//设置按钮颜色和字体
		btnadd.setFont(f1);
		btnadd.setBackground(new Color(131,175,155));
		btndelete.setFont(f1);
		btndelete.setBackground(new Color(131,175,155));
		btnupdate.setFont(f1);
		btnupdate.setBackground(new Color(131,175,155));
		btnsearch.setFont(f1);
		btnsearch.setBackground(new Color(131,175,155));
		btndisplay.setFont(f1);
		btndisplay.setBackground(new Color(131,175,155));
		btnreturn.setFont(f1);
		btnreturn.setBackground(new Color(131,175,155));
		
		//将按钮添加进菜单栏中
		mb.add(btnadd);
		mb.add(btndelete);
		mb.add(btnupdate);
		mb.add(btnsearch);
		mb.add(btndisplay);
		mb.add(btnreturn);
		
		//连接数据库
		this.connDB();
		//给按钮注册监听器
		btnadd.addActionListener(this);
		btndelete.addActionListener(this);
		btnupdate.addActionListener(this);
		btnsearch.addActionListener(this);
		btndisplay.addActionListener(this);
		btnreturn.addActionListener(this);
		
		setSize(500,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		this.setJMenuBar(mb);
	}
	
	public void display() {
		//this.connDB();
		String sql="select * from 教师表";
		ArrayList ar=new ArrayList();  //建立数列进行存储数据库成员信息
		int i=0,j=0;
		try {
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				ar.add(rs.getString("教师号"));
				ar.add(rs.getString("姓名"));
				ar.add(rs.getString("性别"));
				ar.add(rs.getString("学历"));
				ar.add(rs.getString("职称"));
				ar.add(rs.getString("所属学院"));
				ar.add(rs.getString("登录密码"));
				i++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		//创建二维数组进行存储
		arr=new Object[i][7];
		String[] list= {"教师号","姓名","性别","学历","职称","所属学院","登录密码"};
		try {
			rs=stmt.executeQuery("select * from 教师表  order by 教师号");
			while(rs.next()) {
				arr[j][0]=rs.getString("教师号");
				arr[j][1]=rs.getString("姓名");
				arr[j][2]=rs.getString("性别");
				arr[j][3]=rs.getString("学历");
				arr[j][4]=rs.getString("职称");
				arr[j][5]=rs.getString("所属学院");
				arr[j][6]=rs.getString("登录密码");
				j++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		tb=new JTable(arr,list);
		jsp=new JScrollPane(tb);
		this.add(jsp);
	}
	
	public void delete() {   //删除信息方法
		String tno=null;  //定义字符变量，来接收教师号   
		int row=tb.getSelectedRow();  //代表鼠标选定的行数
		if(row==-1) {  //表示未被选中
			JOptionPane.showMessageDialog(null,"请选择要删除的记录！");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from 教师表");
				while(rs.next() && x<=row) {
					tno=rs.getString("教师号");
					x++;
				}
				stmt.executeUpdate("delete  from 课程表  where 教师号="+tno);
				stmt.executeUpdate("delete  from 教师表  where 教师号="+tno);   //局部变量一定要初始化
				JOptionPane.showMessageDialog(null,"删除成功！");
				this.dispose();
				new TM().display();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
			
	public void update() {
		String sno1=null; //局部变量一定要初始化
		int row=tb.getSelectedRow();  //代表鼠标选定的行数
		if(row==-1) {  //表示未被选中
			JOptionPane.showMessageDialog(null,"请选择要修改的信息！");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from 教师表");
				while(rs.next() && x<=row) {
					sno1=rs.getString("教师号");
					x++;
				}
				this.dispose();
				new TMupdate(sno1);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void show(String str) {   //查询结果方法
		JFrame f=new JFrame("查询结果");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("返回");
		btnrt.setFont(f1);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		arr=new Object[1][7];
		try {
			rs=stmt.executeQuery("select * from 教师表  where 教师号="+str);
			while(rs.next()) {
				arr[0][0]=rs.getString("教师号");
				arr[0][1]=rs.getString("姓名");
				arr[0][2]=rs.getString("性别");
				arr[0][3]=rs.getString("学历");
				arr[0][4]=rs.getString("职称");
				arr[0][5]=rs.getString("所属学院");
				arr[0][6]=rs.getString("登录密码");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"教师号","姓名","性别","学历","职称","所属学院","登录密码"};
		tb1=new JTable(arr,list); //创建表格
		scroll1=new JScrollPane(tb1);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(scroll1);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
			}
		});
	}
	
	//该方法用来确认是否在数据库中找到教师号
	public boolean searchtest(String str) {
		boolean x=false;
		this.connDB();
		try {
			rs=stmt.executeQuery("select * from 教师表");
			while(rs.next()) {
				if(rs.getString("教师号").trim().equals(str)) {  //在java中，判断字符串是否相同，一定要使用equals函数!!!!!!!!
					x=true;
				}
			}
			//return x;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return x;
	}
	
	//查找方法
	public void search() {   //等效于将一个窗口写在方法里面
		JFrame f=new JFrame("查询");
		f.setLayout(new FlowLayout());
		f.setSize(240,180);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JLabel stuno=new JLabel("输入教师号：");
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
					JOptionPane.showMessageDialog(null,"请输入教师号");
				}else {
					if(!searchtest(stuno1.getText().trim())) {
						f.dispose();
						JOptionPane.showMessageDialog(null,"对不起，该教师不存在！");
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
	
	public void itemStateChanged(ItemEvent e) {
		
	}
	
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource()==btnadd) {
    		this.dispose();
    		new TMadd();
    	}
    	if(e.getSource()==btndelete) {
    		JOptionPane.showMessageDialog(null,"删除操作，谨慎操作！");
    		this.delete();
    	}
    	if(e.getSource()==btnupdate) {
    		//this.dispose();
    		this.update(); 
    	}
    	if(e.getSource()==btnsearch) {
    		this.search();
    	}
    	if(e.getSource()==btndisplay) {
    		this.dispose();
    		new TM().display();
    	}
    	if(e.getSource()==btnreturn) {
    		this.dispose();
        	new GLFrame();
    	}
    }
    
	public static void main(String[] args) {
		new TM();
	}
	

}

/*2017.12.04*/