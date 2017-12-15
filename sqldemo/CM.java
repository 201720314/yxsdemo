package edu.yxs.sqldemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class CM extends JFrame implements ActionListener{
	//定义菜单按钮
    Button btnadd=new Button("添加");
    Button btnupdate=new Button("修改");
    Button btnsearch=new Button("查询");
    Button btndisplay=new Button("刷新显示");
    Button btnreturn=new Button("返回");
    //定义菜单栏
    JMenuBar mb=new JMenuBar();
    //定义滚轮面板
    JScrollPane jsp;
    JScrollPane jsp1;
    //定义表格
    JTable tb;
    JTable tb1;
    //定义字体
    Font f1=new Font("行楷",Font.BOLD,15);
    Font f2=new Font("宋体",Font.ITALIC,36);
    
    //创建连接数据库变量对象
    Connection conn;
    Statement stmt;
    ResultSet rs;
    
  //设置端口常量
    String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=教务系统";
    String username="sa";
    String userpwd="123";
    
    //定义一个Object数组
    Object[][] arr;
    
    public void connDB() { //连接数据库方法
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
    
    public CM() {  //无参构造方法
    	super("课程信息管理");
    	//设置按钮字体和颜色
    	btnadd.setFont(f1);
    	btnadd.setBackground(new Color(131,175,155));
    	btnupdate.setFont(f1);
    	btnupdate.setBackground(new Color(131,175,155));
    	btnsearch.setFont(f1);
    	btnsearch.setBackground(new Color(131,175,155));
    	btndisplay.setFont(f1);
    	btndisplay.setBackground(new Color(131,175,155));
    	btnreturn.setFont(f1);
    	btnreturn.setBackground(new Color(131,175,155));
    	//将按钮添加进菜单栏
    	mb.add(btnadd);
    	mb.add(btnupdate);
    	mb.add(btnsearch);
    	mb.add(btndisplay);
    	mb.add(btnreturn);
    	//连接数据库
    	this.connDB();
    	
        //注册事件监听器
    	btnadd.addActionListener(this);
    	btnupdate.addActionListener(this);
    	btnsearch.addActionListener(this);
    	btndisplay.addActionListener(this);
    	btnreturn.addActionListener(this);
    	
    	setSize(500,300);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
    	setVisible(true);
    	//将菜单栏添加进容器中
    	this.setJMenuBar(mb);
    }   
    
    public void update() {
		String cno1=null; //局部变量一定要初始化
		int row=tb.getSelectedRow();  //代表鼠标选定的行数
		if(row==-1) {  //表示未被选中
			JOptionPane.showMessageDialog(null,"请选择要修改的信息！");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from 课程表");
				while(rs.next() && x<=row) {
					cno1=rs.getString("课程号");
					x++;
				}
				this.dispose();
				new CMupdate(cno1);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
    
    public void display() {
    	int i=0,j=0;
    	//定义一个数组来存储所有数据
    	ArrayList list=new ArrayList();
    	try {
    		rs=stmt.executeQuery("select * from 课程表 ");
    		while(rs.next()) {
    			list.add(rs.getString("课程号"));
    			list.add(rs.getString("课程名"));
    			list.add(rs.getString("教师号"));
    			i++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	arr=new Object[i][3];
    	String[] listname= {"课程号","课程名","教师号"};
    	//用二维数组来存储所有数据
    	try {
    		rs=stmt.executeQuery("select * from 课程表 order by 课程号");
    		while(rs.next()) {
    			arr[j][0]=rs.getString("课程号");
    			arr[j][1]=rs.getString("课程名");
    			arr[j][2]=rs.getString("教师号");
    			j++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	tb=new JTable(arr,listname);
    	jsp=new JScrollPane(tb);
    	this.add(jsp);
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
		arr=new Object[1][4];
		try {
			rs=stmt.executeQuery("select * from 课程表1  where 课程号="+str);
			while(rs.next()) {
				arr[0][0]=rs.getString("课程号");
    			arr[0][1]=rs.getString("课程名");
    			arr[0][2]=rs.getString("教师号");
    			arr[0][3]=rs.getString("教师姓名");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list={"课程号","课程名","教师号","教师姓名"};
		tb1=new JTable(arr,list); //创建表格
		jsp1=new JScrollPane(tb1);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(jsp1);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
			}
		});
	}
    
    //该方法用来确认是否在数据库中找到课程号
  	public boolean searchtest(String str) {
  		boolean x=false;
  		this.connDB();
  		try {
  			rs=stmt.executeQuery("select * from 课程表");
  			while(rs.next()) {
  				if(rs.getString("课程号").trim().equals(str)) {  //在java中，判断字符串是否相同，一定要使用equals函数!!!!!!!!
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
		JLabel stuno=new JLabel("输入课程号：");
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
					JOptionPane.showMessageDialog(null,"请输入课程号");
				}else {
					if(!(searchtest(stuno1.getText().trim()))) {
						f.dispose();
						JOptionPane.showMessageDialog(null,"对不起，该课程不存在！");
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
		//对事件进行相对应得处理
    	if(e.getSource()==btnadd) {  
    		this.dispose();
    		new CMadd();
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
    		new CM().display();
    	}
    	if(e.getSource()==btnreturn) {
    		this.dispose();
    		new GLFrame();
    	}
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CM().display();
	}

}

/*2017.12.05*/