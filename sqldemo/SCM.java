package edu.yxs.sqldemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class SCM extends JFrame implements ActionListener{
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
    
    public SCM() {  //无参构造方法
    	super("成绩信息管理");
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
		String sno1=null,cno1=null; //局部变量一定要初始化
		int row=tb.getSelectedRow();  //代表鼠标选定的行数
		if(row==-1) {  //表示未被选中
			JOptionPane.showMessageDialog(null,"请选择要修改的信息！");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from 成绩表");
				while(rs.next() && x<=row) {
					sno1=rs.getString("学号");
					cno1=rs.getString("课程号");
					x++;
				}
				this.dispose();
				new SCMupdate(sno1,cno1);
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
    		rs=stmt.executeQuery("select * from 成绩表1 ");
    		while(rs.next()) {
    			list.add(rs.getString("学号"));
    			list.add(rs.getString("姓名"));
    			list.add(rs.getString("课程号"));
    			list.add(rs.getString("课程名"));
    			list.add(rs.getString("教师姓名"));
    			list.add(rs.getString("成绩"));
    			i++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	arr=new Object[i][6];
    	String[] listname= {"学号","姓名","课程号","课程名","教师姓名","成绩"};
    	//用二维数组来存储所有数据
    	try {
    		rs=stmt.executeQuery("select * from 成绩表1 order by 学号");
    		while(rs.next()) {
    			arr[j][0]=rs.getString("学号");
    			arr[j][1]=rs.getString("姓名");
    			arr[j][2]=rs.getString("课程号");
    			arr[j][3]=rs.getString("课程名");
    			arr[j][4]=rs.getString("教师姓名");
    			arr[j][5]=rs.getString("成绩");
    			j++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	tb=new JTable(arr,listname);
    	jsp=new JScrollPane(tb);
    	this.add(jsp);
    }
     
    public void show(String s1) {   //查询结果方法
    	int i=0,j=0;
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
		ArrayList list=new ArrayList();
    	try {
    		rs=stmt.executeQuery("select * from 成绩表1 ");
    		while(rs.next()) {
    			list.add(rs.getString("学号"));
    			list.add(rs.getString("姓名"));
    			list.add(rs.getString("课程号"));
    			list.add(rs.getString("课程名"));
    			list.add(rs.getString("教师姓名"));
    			list.add(rs.getString("成绩"));
    			i++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		arr=new Object[i][6];
		try {
			rs=stmt.executeQuery("select * from 成绩表1  where 学号="+s1+"order by 课程号");
			while(rs.next()) {
				arr[j][0]=rs.getString("学号");
    			arr[j][1]=rs.getString("姓名");
    			arr[j][2]=rs.getString("课程号");
    			arr[j][3]=rs.getString("课程名");
    			arr[j][4]=rs.getString("教师姓名");
    			arr[j][5]=rs.getString("成绩");
    			j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] listname={"学号","姓名","课程号","课程名","教师姓名","成绩"};
		tb1=new JTable(arr,listname); //创建表格
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
    
    //该方法用来确认是否在数据库中找到学号
  	public boolean searchtestsno(String str) {
  		boolean x=false;
  		this.connDB();
  		try {
  			rs=stmt.executeQuery("select * from 成绩表");
  			while(rs.next()) {
  				if(rs.getString("学号").trim().equals(str)) {  //在java中，判断字符串是否相同，一定要使用equals函数!!!!!!!!
  					x=true;
  				}
  			}
  			//return x;
  		}catch(Exception e) {
  			e.printStackTrace();
  		}
  		return x;
  	}
    
   //该方法用来确认是否在数据库中找到课程号
   public boolean searchtestcno(String str) {
  		boolean x=false;
  		this.connDB();
  		try {
  			rs=stmt.executeQuery("select * from 成绩表");
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
  	
    public void searchsno() {   //用于学号查询
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
					if(!(searchtestsno(stuno1.getText().trim()))) {
						f.dispose();
						JOptionPane.showMessageDialog(null,"对不起，该记录不存在！");
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
				search();
			}
		});
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Window w=(Window)e.getComponent();  
				w.dispose();
			}
		});
	}
    
    public void searchcno() {   //用于课程查询
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
					if(!(searchtestcno(stuno1.getText().trim()))) {
						f.dispose();
						JOptionPane.showMessageDialog(null,"对不起，该记录不存在！");
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
				search();
			}
		});
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Window w=(Window)e.getComponent();  
				w.dispose();
			}
		});
	}
    
    public void search() {  //查询成绩页面，供选择查询方式
    	Frame f=new Frame("成绩查询选择");
    	f.setLayout(new FlowLayout());
    	f.setSize(240,100);
    	f.setLocationRelativeTo(null);
    	f.setVisible(true);
    	Button btnsno=new Button("学号查询");
    	Button btncno=new Button("课程号查询");
    	Button btncancel=new Button("取消");
    	btnsno.setBackground(new Color(131,175,155));
    	btncno.setBackground(new Color(131,175,155));
    	btncancel.setBackground(Color.GRAY);
    	f.add(btnsno);
    	f.add(btncno);
    	f.add(btncancel);    	
    	btnsno.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			f.dispose();
    			searchsno();
    		}
    	});
    	btncno.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			f.dispose();
    			searchcno();
    		}
    	});
    	btncancel.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
    			f.dispose();
    		}
    	});
    	f.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e) {
    			Window window=(Window)e.getComponent();
    			window.dispose();
    		}
    	});
    }
    
	public void actionPerformed(ActionEvent e) {
		//对事件进行相对应得处理
    	if(e.getSource()==btnadd) {  
    		this.dispose();
    		new SCMadd();
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
    		new SCM().display();
    	}
    	if(e.getSource()==btnreturn) {
    		this.dispose();
    		new GLFrame();
    	}
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SCM().display();
	}

}

/*2017.12.05*/