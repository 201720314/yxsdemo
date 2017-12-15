package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;

public class TeacherFrame extends JFrame implements ActionListener{
	JLabel l=new JLabel("--教师页面--");
	//定义面板容器
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	//设置字体类型
	Font f1=new Font("宋体",Font.BOLD,30);
    Font f2=new Font("幼圆",Font.ITALIC,30);
    Font f3=new Font("楷体",Font.BOLD,17);
    Font f4=new Font("隶书",Font.PLAIN,40);
	
	//设置6个按钮，以便管理员操作
	JButton btnTmg=new JButton("个人信息管理");
	JButton btnSsh=new JButton("学生信息查询");
	JButton btnCsh=new JButton("课程信息查询");
	JButton btnSCmg=new JButton("成绩信息管理");
	JButton btnCSsh=new JButton("班级信息查询");
	JButton btnEXIT=new JButton("退出");
	
	String tno;
	String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=教务系统";
	String useName="sa";
	String usePwd="123";
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	Object[][] arr;
	String tpwd;
	JScrollPane scroll1,scroll2,scroll3,scroll4,scroll5,scroll6;
	JTable tb1,tb2,tb3,tb4,tb5,tb6; 
	int row;
	
	public TeacherFrame(String str) {   //构造方法
		super("教师页面");
		setLayout(new FlowLayout());
		//设置标签的颜色
	    l.setFont(f1);
	    l.setForeground(Color.blue);
	    //设置按钮字体和颜色
	    btnTmg.setFont(f3);
	    btnTmg.setContentAreaFilled(false);
	    //btnTM.setBackground(Color.blue);
	    btnSsh.setFont(f3);
	    btnSsh.setContentAreaFilled(false);
	    btnCsh.setFont(f3);
	    btnCsh.setContentAreaFilled(false);
	    btnSCmg.setFont(f3);
	    btnSCmg.setContentAreaFilled(false);
	    btnCSsh.setFont(f3);
	    btnCSsh.setContentAreaFilled(false);
	    btnEXIT.setFont(f3);
	    btnEXIT.setContentAreaFilled(false);
	    
	    this.tno=str;
	    
		p1.add(l);	
		p1.setOpaque(false);
		p2.setOpaque(false);
		p2.setLayout(new GridLayout(3,2,10,10));
		p2.add(btnTmg);
		p2.add(btnSsh);
		p2.add(btnCsh);
		p2.add(btnSCmg);
		p2.add(btnCSsh);
		p2.add(btnEXIT);
		 
		//布局管理器
		this.add(p1);
		this.add(p2);
		
		ImageIcon ic=new ImageIcon("C:\\Users\\YXS\\eclipse-workspace\\edu-a-s\\src\\edu\\yxs\\sqldemo\\6.jpg");
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
		
		this.connDB();
		
		btnTmg.addActionListener(this);
		btnSsh.addActionListener(this);
		btnCsh.addActionListener(this);
		btnSCmg.addActionListener(this);
		btnCSsh.addActionListener(this);
		btnEXIT.addActionListener(this);
	}
	
	public void connDB() {
		try {
			conn=DriverManager.getConnection(dbURL,useName,usePwd);
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
	
	public void tmg(String str) {   //个人信息管理方法
		JFrame f=new JFrame("个人信息");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnchange=new Button("修改密码");
		Button btnrt=new Button("返回");
		btnchange.setFont(f3);
		btnchange.setBackground(new Color(131,175,155));
		btnrt.setFont(f3);
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
		f.add(btnchange);
		f.add(btnrt);
		f.add(scroll1);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
		btnchange.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
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
					rs=stmt.executeQuery("select * from 教师表 where 教师号="+tno);
					while(rs.next()) {
						tpwd=rs.getString("登录密码").trim();
					}
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
				if(tf2.getText().equals("") || tf1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "密码不能为空！请重新修改！");
				}else{
					if(!tpwd.equals(tf1.getText().trim())) {     //spwd.equals(tf1.getText().trim())
					    JOptionPane.showMessageDialog(null,"初始密码错误，请重新输入密码！");  //"原密码是"+spwd+"输入密码是"+tf1.getText();
					    tf1.setText("");
					    tf2.setText("");
					}else {
						try {
							stmt.executeUpdate("update 教师表 set 登录密码 ="+tf2.getText().trim()+"where 教师号="+tno);
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
				new TeacherFrame(tno);
			}
		});
	}
	
	public void ssh(String str) {  //学生信息查询
		JFrame f=new JFrame("学生信息");
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
		int i=0,j=0;
		ArrayList al=new ArrayList();
		try {
			rs=stmt.executeQuery("select * from 学生信息表  where 教师号="+str);
			while(rs.next()) {
				al.add(rs.getString("学号"));
				al.add(rs.getString("姓名"));
				al.add(rs.getString("性别"));
				al.add(rs.getInt("年龄"));
				al.add(rs.getString("班级名称"));
				al.add(rs.getString("课程名"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		arr=new Object[i][6];
		try {
			rs=stmt.executeQuery("select * from 学生信息表  where 教师号="+str);
			while(rs.next()) {
				arr[j][0]=rs.getString("学号");
				arr[j][1]=rs.getString("姓名");
				arr[j][2]=rs.getString("性别");
				arr[j][3]=rs.getString("年龄");
				arr[j][4]=rs.getString("班级名称");
				arr[j][5]=rs.getString("课程名");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"学号","姓名","性别","年龄","班级名称","课程名"};
		tb2=new JTable(arr,list); //创建表格
		scroll2=new JScrollPane(tb2);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(scroll2);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
	}
	
	public void csh(String str) {  //课程信息查询
		JFrame f=new JFrame("课程信息");
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
		int i=0,j=0;
		ArrayList al=new ArrayList();
		try {
			rs=stmt.executeQuery("select  distinct 班级号  ,班级名称,课程号,课程名  from 学生信息表  where 教师号="+str);
			while(rs.next()) {
				al.add(rs.getString("班级号"));
				al.add(rs.getString("班级名称"));
				al.add(rs.getString("课程号"));
				al.add(rs.getString("课程名"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		arr=new Object[i][4];
		try {
			rs=stmt.executeQuery("select  distinct 班级号  ,班级名称,课程号,课程名  from 学生信息表  where 教师号="+str);
			while(rs.next()) {
				arr[j][0]=rs.getString("班级号");
				arr[j][1]=rs.getString("班级名称");
				arr[j][2]=rs.getString("课程号");
				arr[j][3]=rs.getString("课程名");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"班级号","班级名称","课程号","课程名"};
		tb3=new JTable(arr,list); //创建表格
		scroll3=new JScrollPane(tb3);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(scroll3);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
	}
	
	public void scmg(String str) {  //成绩信息管理
		JFrame f=new JFrame("学生成绩信息");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("返回");
		Button btnchange=new Button("修改成绩");
		btnchange.setFont(f3);
		btnchange.setBackground(new Color(131,175,155));
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		int i=0,j=0;
		ArrayList al=new ArrayList();
		try {
			rs=stmt.executeQuery("select * from 学生信息表  where 教师号="+str);
			while(rs.next()) {
				al.add(rs.getString("学号"));
				al.add(rs.getString("姓名"));
				al.add(rs.getString("性别"));
				al.add(rs.getInt("年龄"));
				al.add(rs.getString("班级名称"));
				al.add(rs.getString("课程名"));
				al.add(rs.getString("成绩"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		arr=new Object[i][7];
		try {
			rs=stmt.executeQuery("select * from 学生信息表  where 教师号="+str);
			while(rs.next()) {
				arr[j][0]=rs.getString("学号");
				arr[j][1]=rs.getString("姓名");
				arr[j][2]=rs.getString("性别");
				arr[j][3]=rs.getString("年龄");
				arr[j][4]=rs.getString("班级名称");
				arr[j][5]=rs.getString("课程名");
				arr[j][6]=rs.getString("成绩");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"学号","姓名","性别","年龄","班级名称","课程名","成绩"};
		tb4=new JTable(arr,list); //创建表格
		scroll4=new JScrollPane(tb4);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnchange);
		f.add(btnrt);
		f.add(scroll4);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
		btnchange.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				scchange();
			}
		});
	}
	
	public void scchange() {  //修改成绩页面
		JFrame f=new JFrame("修改成绩");
		f.setLayout(new FlowLayout());
		JLabel l=new JLabel("输入班级号");
		JTextField tf=new JTextField(10);
		JButton jb1=new JButton("确定");
		jb1.setFont(f3);
		jb1.setBackground(new Color(131,175,155));
		JButton jb2=new JButton("返回");
		jb2.setFont(f3);
		jb2.setBackground(new Color(131,175,155));
		
		f.add(l);
		f.add(tf);
		f.add(jb1);
		f.add(jb2);
		
		jb1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new SCmg(tno,tf.getText());  //重新刷新成绩页面，为班级成绩
			}
		});
		jb2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				scmg(tno);  //取消按钮，重新显示页面
			}
		});
		
		f.setSize(200,150);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	//bug方法  只能新建类来解决
	/*public void scmg(String s1,String s2) { //精确到班级成绩的页面
		String csname=null;
		this.connDB();
		try {
			rs=stmt.executeQuery("select distinct 班级号 ,班级名称 from 学生信息表 where 班级号="+"'"+s2+"'");
			while(rs.next()) {
				csname=rs.getString("班级名称").trim();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		JFrame f=new JFrame(csname+" --学生成绩信息");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("返回");
		Button btnchange=new Button("修改");
		btnchange.setFont(f3);
		btnchange.setBackground(new Color(131,175,155));
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		int i=0,j=0;
		ArrayList al=new ArrayList();
		try {
			rs=stmt.executeQuery("select * from 学生信息表  where 教师号="+s1+"and 班级号='"+s2+"'");
			while(rs.next()) {
				al.add(rs.getString("学号"));
				al.add(rs.getString("姓名"));
				al.add(rs.getString("性别"));
				al.add(rs.getInt("年龄"));
				al.add(rs.getString("班级名称"));
				al.add(rs.getString("课程号"));
				al.add(rs.getString("课程名"));
				al.add(rs.getString("成绩"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		arr=new Object[i][8];
		try {
			rs=stmt.executeQuery("select * from 学生信息表  where 教师号="+s1+"and 班级号= '"+s2+"'");
			while(rs.next()) {
				arr[j][0]=rs.getString("学号");
				arr[j][1]=rs.getString("姓名");
				arr[j][2]=rs.getString("性别");
				arr[j][3]=rs.getString("年龄");
				arr[j][4]=rs.getString("班级名称");
				arr[j][5]=rs.getString("课程号");
				arr[j][6]=rs.getString("课程名");
				arr[j][7]=rs.getString("成绩");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"学号","姓名","性别","年龄","班级名称","课程号","课程名","成绩"};
		tb5=new JTable(arr,list); //创建表格
		scroll5=new JScrollPane(tb5);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnchange);
		f.add(btnrt);
		f.add(scroll5);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		
		//String sno1=null; //局部变量一定要初始化，获取学生学号
		String cno=null; //获取课程号
		
		/////////////////////////////////////////////////////////////////
		//*Bug:千万不要在同一个方法里定义获取行数！！！！！！！！！！！无法执行                    //
		//row=tb5.getSelectedRow();  //代表鼠标选定的行数                                              //
		/////////////////////////////////////////////////////////////////
		
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				scmg(tno);
			}
		});
		
		btnchange.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(row==-1) {  //表示未被选中
					JOptionPane.showMessageDialog(null,"请选择要修改的信息！");
				}else {
					//int x=0;
					try {
						//String sno1=(String)tb1.getModel().getValueAt(row,column);
						f.dispose();
						//scchange1(sno1,cno);
					}catch(Exception e1) {
						e1.printStackTrace();
					}
				}
				//f.dispose();
				//scchange();
			}
		});
	   }
	public void test() {  //用来获取行数
		
	}*/
	
	public void cssh(String str) {  //班级信息查询
		JFrame f=new JFrame("班级信息");
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
		int i=0,j=0;
		ArrayList al=new ArrayList();
		try {
			rs=stmt.executeQuery("select  distinct 学生信息表.班级号  ,班级表.班级名称,班级表.所属学院,班级表.班级人数,班级表.班主任 from 班级表,学生信息表  where 学生信息表.班级号=班级表.班级号 and 教师号="+str);
			while(rs.next()) {
				al.add(rs.getString("班级号"));
				al.add(rs.getString("班级名称"));
				al.add(rs.getString("所属学院"));
				al.add(rs.getInt("班级人数"));
				al.add(rs.getString("班主任"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		arr=new Object[i][5];
		try {
			rs=stmt.executeQuery("select  distinct 学生信息表.班级号  ,班级表.班级名称,班级表.所属学院,班级表.班级人数,班级表.班主任 from 班级表,学生信息表  where 学生信息表.班级号=班级表.班级号 and 教师号="+str);
			while(rs.next()) {
				arr[j][0]=rs.getString("班级号");
				arr[j][1]=rs.getString("班级名称");
				arr[j][2]=rs.getString("所属学院");
				arr[j][3]=rs.getInt("班级人数");
				arr[j][4]=rs.getString("班主任");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"班级号","班级名称","所属学院","班级人数","班主任"};
		tb6=new JTable(arr,list); //创建表格
		scroll6=new JScrollPane(tb6);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(scroll6);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		//按钮为“教师信息管理”，跳转页面
		if(e.getSource().equals(btnTmg)) {
			this.dispose();
			this.tmg(tno);
		}
		//按钮为“学生信息管理”，跳转页面
		if(e.getSource().equals(btnSsh)) {
			//new SM().display();
			this.dispose();
			this.ssh(tno);
		}
		//按钮为“课程信息管理”，跳转页面
		if(e.getSource().equals(btnCsh)) {
			this.dispose();
			this.csh(tno);
		}
		//按钮为“成绩信息管理”，跳转页面
		if(e.getSource().equals(btnSCmg)) {
			this.dispose();
			this.scmg(tno);
		}
		//按钮为“班级信息管理”，跳转页面
		if(e.getSource().equals(btnCSsh)) {
			this.dispose();
			this.cssh(tno);
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
		new TeacherFrame("20050002");
	}

}
