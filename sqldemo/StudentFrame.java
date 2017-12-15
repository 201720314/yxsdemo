package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class StudentFrame extends JFrame implements ActionListener{

	///////////////注意////////////////
	//在类里面只有成员变量和方法，切忌在成员变量里直接定义
	//“管理员”标签
	JLabel l=new JLabel("--学生页面--");
	//定义面板容器
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	//设置字体类型
	Font f1=new Font("宋体",Font.BOLD,30);
    Font f2=new Font("幼圆",Font.ITALIC,20);
    Font f3=new Font("楷体",Font.BOLD,15);
    Font f4=new Font("隶书",Font.PLAIN,18);
	
	//设置6个按钮，以便管理员操作
	Button btnSelf=new Button("个人信息管理");
	Button btnSCsh=new Button("成绩信息查询");
	Button btnCsh=new Button("课程信息查询");
	Button btnCSsh=new Button("班级信息查询");
	Button btnEXIT=new Button("退出");
	
	//
	String dbURL="jdbc:sqlserver://localhost:1433;databaseName=教务系统";
	String userName="sa";
	String userPwd="123";
	
	//
	Connection conn;
	Statement stmt;
	ResultSet rs;
	//
	Object[][] arr;
	String sno;
	String spwd;
	JScrollPane scroll1;
	JScrollPane jsp1;
	JTable stable1;
	JTable tb1;
	public void connDB() {
		try {
			conn=DriverManager.getConnection(dbURL,userName,userPwd);
			stmt=conn.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeDB() {
		try {
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public StudentFrame(String str) {   //构造方法
		super("学生页面");
		setLayout(new FlowLayout());
		//设置标签的颜色
	    l.setFont(f1);
	    l.setForeground(Color.blue);
	    //设置按钮字体和颜色
	    btnSelf.setFont(f2);
	    btnSelf.setBackground(new Color(0,255,255));
	    btnSCsh.setFont(f3);
	    btnSCsh.setBackground(new Color(255,160,122));
	    btnCsh.setFont(f4);
	    btnCsh.setBackground(new Color(30,144,255));
	    btnCSsh.setFont(f3);
	    btnCSsh.setBackground(new Color(0,255,0));
	    btnEXIT.setFont(f2);
	    btnEXIT.setBackground(new Color(220,20,60));
	    
		p1.add(l);
		p1.setOpaque(false);
		p2.setLayout(new GridLayout(4,2,10,10));
		p2.setOpaque(false);
		p2.add(btnSelf);
		p2.add(btnSCsh);
		p2.add(btnCsh);
		p2.add(btnCSsh);
		p2.add(btnEXIT);
		
		this.connDB();
		this.sno=str;
		//布局管理器
		this.add(p1);
		this.add(p2);
		
		ImageIcon ic=new ImageIcon("C:\\Users\\YXS\\eclipse-workspace\\edu-a-s\\src\\edu\\yxs\\sqldemo\\1.jpg");
		JLabel l=new JLabel(ic);
		l.setBounds(0,0,ic.getIconWidth(),ic.getIconHeight());
		//l.setBounds(0,0,this.getWidth(),this.getHeight());
		//JPanel ip=(JPanel)this.getContentPane();
		this.getLayeredPane().add(l,new Integer(Integer.MIN_VALUE));
		((JPanel)this.getContentPane()).setOpaque(false);//设置透明
		
		//设置顶层容器的大小、位置、可见性及close功能
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350,300);
		setLocationRelativeTo(null);
		setVisible(true);
		
		btnSelf.addActionListener(this);
		btnSCsh.addActionListener(this);
		btnCsh.addActionListener(this);
		btnCSsh.addActionListener(this);
		btnEXIT.addActionListener(this);
	}
	
	public void cssearch(String str) {   //班级查询方法
		JFrame f=new JFrame("班级信息页面");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("返回");
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		arr=new Object[1][6];
		try {
			rs=stmt.executeQuery("select * from 班级表1  where 学号="+str);
			while(rs.next()) {
				arr[0][0]=rs.getString("学号");
				arr[0][1]=rs.getString("班级号");
    			arr[0][2]=rs.getString("班级名称");
    			arr[0][3]=rs.getString("所属学院");
    			arr[0][4]=rs.getString("班级人数");
    			arr[0][5]=rs.getString("班主任");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list={"学号","班级号","班级名称","所属学院","班级人数","班主任"};
		tb1=new JTable(arr,list); //创建表格
		jsp1=new JScrollPane(tb1);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(jsp1);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new StudentFrame(sno);
			}
		});
	}
	
	public void csearch(String str) {   //课程查询方法
		JFrame f=new JFrame("课程信息页面");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("返回");
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);
        
		int i=0,j=0;
		ArrayList ar=new ArrayList();
		this.connDB();
		try {
			rs=stmt.executeQuery("select * from 课程表2 where 学号="+str);
			while(rs.next()){
				ar.add(rs.getString("学号"));
				ar.add(rs.getString("课程号"));
				ar.add(rs.getString("课程名"));
				ar.add(rs.getString("教师号"));
				ar.add(rs.getString("教师姓名"));
				j++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		arr=new Object[j][5];
		try {
			rs=stmt.executeQuery("select * from 课程表2  where 学号="+str);
			while(rs.next()) {
				arr[i][0]=rs.getString("学号");
				arr[i][1]=rs.getString("课程号");
    			arr[i][2]=rs.getString("课程名");
    			arr[i][3]=rs.getString("教师号");
    			arr[i][4]=rs.getString("教师姓名");
    			i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list={"学号","课程号","课程名","教师号","教师姓名"};
		tb1=new JTable(arr,list); //创建表格
		jsp1=new JScrollPane(tb1);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(jsp1);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new StudentFrame(sno);
			}
		});
	}
	
	public void scsearch(String str) {  //查询成绩页面
		int i=0,j=0;
		JFrame f=new JFrame("成绩信息页面");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,200);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("返回");
		btnrt.setFont(f3);
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
			rs=stmt.executeQuery("select * from 成绩表1  where 学号="+str+"order by 课程号");
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
				new StudentFrame(str);
			}
		});   	
    }
	
	public void display(String str) {   //用来显示个人信息
		JFrame f1=new JFrame("个人信息");
		f1.setLayout(new FlowLayout());
		f1.setSize(500,150);
		f1.setVisible(true);
		f1.setLocationRelativeTo(null);
		Button btnchange=new Button("修改密码");
		Button btnrt=new Button("返回");
		btnchange.setFont(f3);
		btnchange.setBackground(new Color(131,175,155));
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
		f1.add(btnchange);
		f1.add(btnrt);
		f1.add(scroll1);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt);
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f1.dispose();
				new StudentFrame(sno);
			}
		});
		btnchange.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f1.dispose();
				change();
			}
		});
	}
	
	public void change() {  //创建修改密码页面，新窗口用于修改密码
		this.connDB();
		JFrame f=new JFrame("修改密码");
		f.setLayout(new FlowLayout());
		JPanel p=new JPanel();
		JPanel p1=new JPanel();
		p.setLayout(new GridLayout(3,2));
		JLabel btn1=new JLabel("初始密码：");
		btn1.setFont(f3);
		JTextField tf1=new JTextField(10);
		JLabel btn2=new JLabel("修改密码：");
		btn2.setFont(f3);
		JTextField tf2=new JTextField(10);
		Button ok=new Button("确定");
		ok.setBackground(new Color(131,175,155));
		Button cancel=new Button("取消");
		cancel.setBackground(new Color(131,175,155));
		p.add(btn1);
		p.add(tf1);
		p.add(btn2);
		p.add(tf2);
		p1.add(ok);
		p1.add(cancel);
		f.add(p);
		f.add(p1);
		
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setLocation(530,300);
		//f.setLocationRelativeTo(null);
		f.setSize(300,150);
		f.setVisible(true);
		
		ok.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {   //获取初始密码
					rs=stmt.executeQuery("select * from 学生表 where 学号="+sno);
					while(rs.next()) {
						spwd=rs.getString("密码").trim();  //!!!对于数据库中传回来的值一定要用trim();因为后面会有多余的空格！！！
						/*bug:数据库中数据会有空格，mmp，记于12/10/凌晨1:56,耻！*/
						//spwd="123";
					}
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
				if(tf1.getText().equals("") || tf2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "密码不能为空！请重新修改！");
				}else {
					if(!spwd.equals(tf1.getText())) {     //spwd.equals(tf1.getText().trim())
					    JOptionPane.showMessageDialog(null,"初始密码错误，请重新输入密码！");  //"原密码是"+spwd+"输入密码是"+tf1.getText();
					    tf1.setText("");
					    tf2.setText("");
				    }else {
					    try {
						    stmt.executeUpdate("update 学生表 set 密码 ="+"'"+tf2.getText().trim()+"'"+"where 学号="+sno);
					    }catch(Exception e1) {
						    e1.printStackTrace();
					    }
					    JOptionPane.showMessageDialog(null,"密码修改成功！请重新登录！");
					    f.dispose();
					    new DLFrame();
				    }
			    }
			}
		});
		cancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new StudentFrame(sno);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		//按钮为“个人信息修改”，跳转页面
		if(e.getSource().equals(btnSelf)) {
			this.dispose();
			this.display(sno);
		}
		//按钮为“成绩信息查询”，跳转页面
		if(e.getSource().equals(btnSCsh)) {
			//new SM().display();
			this.dispose();
			this.scsearch(sno);
		}
		//按钮为“课程信息查询”，跳转页面
		if(e.getSource().equals(btnCsh)) {
			this.dispose();
			this.csearch(sno);
		}
		//按钮为“班级信息查询”，跳转页面
		if(e.getSource().equals(btnCSsh)) {
			this.dispose();
			this.cssearch(sno);
		}
		//按钮为“退出管理系统”，程序退出
		if(e.getSource().equals(btnEXIT)) {
        	//System.exit(0);
			this.dispose();
			new DLFrame();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new StudentFrame("1610421084");
	}

}

/*2017.12.07*/