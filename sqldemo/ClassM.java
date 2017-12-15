package edu.yxs.sqldemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class ClassM extends JFrame implements ActionListener{
	//定义菜单按钮
    Button btnadd=new Button("添加");
    Button btndelete=new Button("删除");
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
    String userName="sa";
    String userPwd="123";
    
    //定义一个Object数组
    Object[][] arr;
    
    public void connDB() { //连接数据库方法
    	try {
    		conn=DriverManager.getConnection(dbURL,userName,userPwd);
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
    
    public ClassM() {  //无参构造方法
    	super("班级信息管理");
    	//设置按钮字体和颜色
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
    	//将按钮添加进菜单栏
    	mb.add(btnadd);
    	mb.add(btndelete);
    	mb.add(btnupdate);
    	mb.add(btnsearch);
    	mb.add(btndisplay);
    	mb.add(btnreturn);
    	//连接数据库
    	this.connDB();
    	
        //注册事件监听器
    	btnadd.addActionListener(this);
    	btndelete.addActionListener(this);
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
    
    /*public void delete(String s) {   //删除信息方法
		try {
			//stmt.executeUpdate("delete  from 教师表  where 教师号="+s);   //局部变量一定要初始化
			stmt.executeUpdate("delete  from 班级表  where 班级号="+"'"+s+"'");
			//JOptionPane.showMessageDialog(null,"删除成功！");
		}catch(Exception e) {
			e.printStackTrace();
		}
    }*/
    
    public void update() {
		String cno1=null,cno2=null,cno3=null; //局部变量一定要初始化
		int row=tb.getSelectedRow();  //代表鼠标选定的行数
		if(row==-1) {  //表示未被选中
			JOptionPane.showMessageDialog(null,"请选择要修改的信息！");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from 班级表");
				while(rs.next() && x<=row) {
					cno1=rs.getString("班级号");
					cno2=rs.getString("班级名称");
					cno3=rs.getString("所属学院");
					x++;
				}
				this.dispose();
				//this.delete(cno1);
				new ClassMadd(cno1,cno2,cno3);
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
    		rs=stmt.executeQuery("select * from 班级表 ");
    		while(rs.next()) {
    			list.add(rs.getString("班级号"));
    			list.add(rs.getString("班级名称"));
    			list.add(rs.getString("所属学院"));
    			list.add(rs.getInt("班级人数"));
    			list.add(rs.getString("班主任"));
    			i++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	arr=new Object[i][5];
    	String[] listname= {"班级号","班级名称","所属学院","班级人数","班主任"};
    	//用二维数组来存储所有数据
    	try {
    		rs=stmt.executeQuery("select * from 班级表  order by 班级号");
    		while(rs.next()) {
    			arr[j][0]=rs.getString("班级号");
    			arr[j][1]=rs.getString("班级名称");
    			arr[j][2]=rs.getString("所属学院");
    			arr[j][3]=rs.getString("班级人数");
    			arr[j][4]=rs.getString("班主任");
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
		arr=new Object[1][5];
		try {
			rs=stmt.executeQuery("select * from 班级表  where 班级号="+str);
			while(rs.next()) {
				arr[0][0]=rs.getString("班级号");
    			arr[0][1]=rs.getString("班级名称");
    			arr[0][2]=rs.getString("所属学院");
    			arr[0][3]=rs.getString("班级人数");
    			arr[0][4]=rs.getString("班主任");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list={"班级号","班级名称","所属学院","班级人数","班主任"};
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
    
    public void delete() {   //删除信息方法
		String sno=null;  //定义字符变量，来接收学号   
		int row=tb.getSelectedRow();  //代表鼠标选定的行数
		if(row==-1) {  //表示未被选中
			JOptionPane.showMessageDialog(null,"请选择要删除的记录！");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from 班级表");
				while(rs.next() && x<=row) {
					sno=rs.getString("班级号");
					x++;
				}
				stmt.executeUpdate("delete  from 班级表  where 班级号="+"'"+sno+"'");   //局部变量一定要初始化
				//stmt.executeUpdate("delete  from 成绩表  where 学号="+sno);
				JOptionPane.showMessageDialog(null,"删除成功！");
				this.dispose();
				new ClassM().display();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
    
    //该方法用来确认是否在数据库中找到课程号
  	public boolean searchtest(String str) {
  		boolean x=false;
  		this.connDB();
  		try {
  			rs=stmt.executeQuery("select * from 班级表");
  			while(rs.next()) {
  				if(rs.getString("班级号").trim().equals(str)) {  //在java中，判断字符串是否相同，一定要使用equals函数!!!!!!!!
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
		JLabel stuno=new JLabel("输入班级号：");
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
					JOptionPane.showMessageDialog(null,"请输入班级号");
				}else {
					if(!(searchtest(stuno1.getText().trim()))) {
						f.dispose();
						JOptionPane.showMessageDialog(null,"对不起，该班级不存在！");
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
    		new ClassMadd();
    	}
    	if(e.getSource()==btndelete) {  
    		JOptionPane.showMessageDialog(null,"删除，请谨慎操作！");
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
    		new ClassM().display();
    	}
    	if(e.getSource()==btnreturn) {
    		this.dispose();
    		new GLFrame();
    	}
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ClassM().display();
	}

}

/*2017.12.06*/