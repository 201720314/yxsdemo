package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class SCmg extends JFrame implements ActionListener{
	
	Font f1=new Font("宋体",Font.BOLD,30);
    Font f2=new Font("幼圆",Font.ITALIC,30);
    Font f3=new Font("楷体",Font.BOLD,17);
    Font f4=new Font("隶书",Font.PLAIN,40);
    
	Button btnrt=new Button("返回");
	Button btnchange=new Button("修改");
	
	String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=教务系统";
	String useName="sa";
	String usePwd="123";
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	JTable tb;
	JScrollPane scroll;
	
	//int row;
	Object[][] arr;
	String csname;
	String csno;
	String tno;
	
	public void connDB(){  //连接数据库方法
		try {
			conn=DriverManager.getConnection(dbURL,useName,usePwd);
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
	
	public SCmg(String s1,String s2) {
		tno=s1;
		csno=s2;
		this.connDB();
		try {
			rs=stmt.executeQuery("select distinct 班级号 ,班级名称 from 学生信息表 where 班级号="+"'"+s2+"'");
			while(rs.next()) {
				csname=rs.getString("班级名称").trim();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.setTitle(csname+" --学生成绩信息");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,150);
		setVisible(true);
		setLocationRelativeTo(null);
		
		btnchange.setFont(f3);
		btnchange.setBackground(new Color(131,175,155));
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		
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
		tb=new JTable(arr,list); //创建表格
		scroll=new JScrollPane(tb);
		//f.add(btnrt,BorderLayout.NORTH);
		add(btnchange);
		add(btnrt);
		add(scroll);  //此处有个小bug，只有添加进滚轮面板，才能将头部名称显示出来
		//row=tb.getSelectedRow();
		
		btnchange.addActionListener(this);
		btnrt.addActionListener(this);
		
	}
	
	public void scchange(String sno,String cno) {  //成绩修改精确到个人页面
		String sname=null; 
		String cname=null;
		this.connDB();
		try {
			rs=stmt.executeQuery("select * from 学生信息表  where 学号="+sno+"and 课程号="+cno);
			while(rs.next()) {
				sname=rs.getString("姓名").trim();
				cname=rs.getString("课程名").trim();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		JFrame f=new JFrame(sname+"同学 --成绩页面");
		f.setLayout(new FlowLayout());
		f.setLocation(470,280);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(320,200);
		f.setVisible(true);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(4,2));
		JLabel l0=new JLabel("学号");
		JTextField tf0=new JTextField(10);
		tf0.setText(sno);
		tf0.setEditable(false);
		JLabel l1=new JLabel("姓名");
		JTextField tf1=new JTextField(10);
		tf1.setText(sname);
		tf1.setEditable(false);
		JLabel l2=new JLabel("课程名");
		JTextField tf2=new JTextField(10);
		tf2.setText(cname);
		tf2.setEditable(false);
		JLabel l3=new JLabel("成绩");
		JTextField tf3=new JTextField(10);
		JButton btn1=new JButton("确定");
		btn1.setFont(f3);
		JButton btn2=new JButton("取消");
		btn2.setFont(f3);
		p.setOpaque(false);
		p.add(l0);
		p.add(tf0);
		p.add(l1);
		p.add(tf1);
		p.add(l2);
		p.add(tf2);
		p.add(l3);
		p.add(tf3);
		f.add(p);
		f.add(btn1);
		f.add(btn2);
		
		btn1.addMouseListener(new MouseAdapter() {  //确认数据更新
			public void mouseClicked(MouseEvent e) {
			    f.dispose();
				try {
					stmt.executeUpdate("update 成绩表 set 成绩="+"'"+tf3.getText()+"'"+" where 学号="+sno+"and 课程号="+cno);
					JOptionPane.showMessageDialog(null,"成绩修改成功！");
					new TeacherFrame(tno);
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btn2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
			}
		});
	}
	
	public void se() {
		this.connDB();
		int row=tb.getSelectedRow();
		if(row==-1) {  //表示未被选中
			JOptionPane.showMessageDialog(null,"请选择要修改的信息！");
		}else {
			//int x=0;
			try {
				String sno=(String)tb.getModel().getValueAt(row,0);
				String cno=(String)tb.getModel().getValueAt(row,5);
				this.dispose();
				scchange(sno,cno);
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnchange) {
			se();
		}
		if(e.getSource()==btnrt) {
			this.dispose();
			new TeacherFrame(tno).scmg(tno);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SCmg("20050002","2016rj02");
	}

}
