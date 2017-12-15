//登录页面
package edu.yxs.sqldemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.EventListener;
import java.sql.*;
//登录页面
public class DLFrame extends JFrame implements ActionListener,ItemListener{
	JPanel p1,p2,p3;
	Font f1,f2,f3,f4;
    /*Font f1=new Font("宋体",Font.BOLD,26);  //如果变量已经声明，则再定义必须放在方法里面(*此处出错)
    Font f2=new Font("幼圆",Font.ITALIC,20);
    Font f3=new Font("黑体",Font.BOLD,30);
    Font f4=new Font("隶书",Font.PLAIN,40);*/
    JLabel head1=new JLabel("用户登录  / ");
    JLabel head2=new JLabel("LOGIN ");
    /*head1.setFont(f1);
    head1.setForeground(Color.BLUE);
    head2.setFont(f2);
    head2.setForeground(Color.GRAY);*/
    
    //定义标签和文本框以及下拉列表
    //用户名标签和输入文本框
	JLabel usename=new JLabel("用户ID：");
	JTextField usenametext=new JTextField(10);
	
	//密码标签和密码文本框，JPasswordField对象会自动对密码进行隐藏处理
	JLabel password=new JLabel("密 码：");
	JPasswordField txtPwd=new JPasswordField(10);
	
	//添加验证码
	JLabel test=new JLabel("验证码：");
	//此处后期需要重写
	
	//角色标签和下拉列表，此处使用swing中的JComboBox类，也可使用awt中的Choice类
	JLabel role=new JLabel("角色：");
	JComboBox boxrole=new JComboBox();
	
	//创建三个选择按钮
	JButton a=new JButton("登录");
	JButton b=new JButton("重置");
	JButton c=new JButton("取消");
	
	//创建Connection和Statement对象
	//连接数据库和调用数据库
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	//定义连接字符
	String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=教务系统";
	String userName = "sa";
	String userPwd = "123";
	
	//定义变量来接收下拉列表的索引值,也可返回具体值
	int index=0;
	String index1;
	
	static int ok=1;
	static int cancel=0;
	int actionCode=0;
	
	public DLFrame(){        //构造方法
		super("合肥师范学院官网");
		setLayout(new FlowLayout());
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		f1=new Font("宋体",Font.BOLD,30);
	    f2=new Font("幼圆",Font.ITALIC,30);
	    f3=new Font("楷书",Font.BOLD,18);
	    f4=new Font("隶书",Font.PLAIN,40);
	    
	    //设置面板1的标签
	    head1.setFont(f1);
	    head1.setForeground(Color.BLUE);
	    head2.setFont(f2);
	    head2.setForeground(Color.GRAY);
	    //p1.setBackground(new Color(255,240,240));
	    p1.setBackground(null);
	    p1.setOpaque(false);
		p1.add(head1);
	    p1.add(head2);
	    
	    //面板2为4行2列的网格布局管理器
	    p2.setLayout(new GridLayout(4,2));
	    /*usename.setFont(f3);
	    password.setFont(f3);
	    role.setFont(f3);*/
	    //下拉列表中添加数据
	    boxrole.addItem("管理员");
	    boxrole.addItem("教师");
	    boxrole.addItem("学生");
	    //p2.setBackground(new Color(240,255,240));
	    p2.setBackground(null);
	    p2.setOpaque(false);
	    boxrole.setOpaque(false);
	    usenametext.setOpaque(false);
	    txtPwd.setOpaque(false);
	    p2.add(role);
	    p2.add(boxrole);
	    p2.add(usename);
	    p2.add(usenametext);
	    p2.add(password);
	    p2.add(txtPwd);
	    
	    
	    
	    //将3个按钮添加进面板3中
	    //p3.setBackground(new Color(230,230,250));
	    p3.setBackground(null);
	    p3.setOpaque(false);//设置透明
	    a.setContentAreaFilled(false);
	    b.setContentAreaFilled(false);
	    c.setContentAreaFilled(false);
	    //setGround(a);
	    p3.add(a);
	    p3.add(b);
	    p3.add(c);
	    
	    //将三个面板添加进框架容器中
	    this.add(p1);
	    this.add(p2);
	    this.add(p3);
	    //this.add(new MyPanel());
	    //设置背景图片  
	    
        //bug:此处窗口图片无法完全自主显示！
		ImageIcon ic=new ImageIcon("C:\\Users\\YXS\\eclipse-workspace\\edu-a-s\\src\\edu\\yxs\\sqldemo\\4.jpg");
		JLabel l=new JLabel(ic);
		l.setBounds(0,0,ic.getIconWidth(),ic.getIconHeight());
		//l.setBounds(0,0,this.getWidth(),this.getHeight());
		//JPanel ip=(JPanel)this.getContentPane();
		this.getLayeredPane().add(l,new Integer(Integer.MIN_VALUE));
		((JPanel)this.getContentPane()).setOpaque(false);//设置透明		
	    
	    //设置顶层容器的大小、位置、可见性及close功能
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(350,300);
	    setLocationRelativeTo(null);
	    setVisible(true);
	    
	    //注册事件监听器
	    //这里的this就是本类，已经是ActionListener的实现类    --有待探究
	    boxrole.addItemListener(this);  
	    a.addActionListener(this);
	    b.addActionListener(this);
	    c.addActionListener(this);
	}
	
	public void connDB() {   //连接数据库方法
		//对于jdbc4版本，加载驱动可以选择性省略
		/*try {
			//加载驱动
			Class.forName("com.microsoft.sqlsever.jdbc.SQLSeverDriver");
			//System.out.println("加载成功");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}*/
		try {
			//连接数据库
			//两条语句选择性使用，Debug时此处出问题！
			//conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=教务系统","sa","123");
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
	
	//实现ItemListener的抽象方法
	//对下拉列表进行事件处理
	public void itemStateChanged(ItemEvent e) {
		//该高级事件用于监听用户的选定与否
		if(e.getStateChange()==ItemEvent.SELECTED) {  //判断下拉列表是否选定
			JComboBox j=(JComboBox)e.getSource();  //强制类型转换+获取事件源
			index=j.getSelectedIndex();
			//index1=j.getSelectedItem();
		}
	}
	
	//该方法用来确认是否在数据库中找到学号
	public boolean searchsno(String str) {
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
	
	//该方法用来确认是否在数据库中找到教师号
	public boolean searchtno(String str) {
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
	
	//该方法用来确认是否能在数据库中找到管理员ID
	public boolean searchmanagerno(String str) {
		boolean x=false;
		this.connDB();
		try {
			rs=stmt.executeQuery("select * from 管理员");
			while(rs.next()) {
				if(rs.getString("管理员号").trim().equals(str)) {  //在java中，判断字符串是否相同，一定要使用equals函数!!!!!!!!
					x=true;
				}
			}
			//return x;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return x;
	}
	
	//实现ActionListener的抽象方法
	//对三个按钮进行事件处理
	public void actionPerformed(ActionEvent e){
		Object source=e.getSource();
		String un;
		String pw;
		boolean success= false;  //用于判断是否登录成功
		if(source==a) {  //如果事件源是“确定”按钮
			if(usenametext.getText().equals("") || txtPwd.getText().equals("")) {  //判断用户名和密码是否为空
				//JOptionPane类用作提示框，常见方法:1.showMessageDialog; 2.showOptionDialog
				JOptionPane.showMessageDialog(null,"登录名和密码不能为空！");
			}else {
				//用户名和密码均不为空，进行连接数据库
				this.connDB();
				//这里先连接数据库，再执行数据库操作步骤，等效为将一个try{}catch{}分解成几个，效果一样！
				try {
					if(index==0) {  //管理员登录
						//trim()方法：从当前 String 对象移除所有前导空白字符和尾部空白字符
						
						if(!searchmanagerno(usenametext.getText().trim())) {
							JOptionPane.showMessageDialog(null,"对不起，此用户不存在！请重新登录!");
							usenametext.setText("");
							txtPwd.setText("");
						}else {
							rs=stmt.executeQuery("select * from 管理员  where 管理员号="+ usenametext.getText().trim());
							while(rs.next()) {
								pw=rs.getString(2).trim();
								if(txtPwd.getText().equals(pw)) {
									JOptionPane.showMessageDialog(null,"管理员登录成功！");
									this.setVisible(false);
									new GLFrame();  //进入管理员界面
								}else {
									JOptionPane.showMessageDialog(null,"密码错误！请重试");
									txtPwd.setText("");
								}
							}
						}
					}
					/////////////////////////////////////////////
					/*
					此处仍然有bug，就是对于不存在的用户无法准确识别
			                    记于11/22，后续完善！
					*/
					/////////////////////////////////////////////
					/*if(index==0) {  //管理员登录
						rs=stmt.executeQuery("select * from 管理员");
						while(rs.next()) {
							un=rs.getString(1).trim();
							pw=rs.getString(2).trim();
							if(un==usename.getText().trim() && pw==txtPwd.getText().trim()) {
								success=true;
								break;
							}
						}
						if(success==false) {
							JOptionPane.showMessageDialog(null,"登录失败！");
						}else {
							JOptionPane.showMessageDialog(null,"管理员登录成功！");
							this.setVisible(false);
							//new ManagerFrame();  //进入管理员界面
						}
					}*/
					//*该bug已经通过布尔类型的search方法改进，记于12/07！
					if(index==1) {  //教师登录
						if(!searchtno(usenametext.getText().trim())) {
							JOptionPane.showMessageDialog(null,"对不起，此用户不存在！请重新登录!");
							usenametext.setText("");
							txtPwd.setText("");
						}else {
							rs=stmt.executeQuery("select * from 教师表  where 教师号="+ usenametext.getText().trim());
							while(rs.next()) {
								pw=rs.getString("登录密码").trim();
								if(txtPwd.getText().equals(pw)) {
									JOptionPane.showMessageDialog(null,"教师登录成功！");
									this.setVisible(false);
									new TeacherFrame(usenametext.getText());  //进入教师界面
								}
								else {
									JOptionPane.showMessageDialog(null,"密码错误！请重试");
									txtPwd.setText("");
								}
							}
						}
					}
					if(index==2) {  //学生登录
						if(!searchsno(usenametext.getText().trim())) {
							JOptionPane.showMessageDialog(null,"对不起，此用户不存在！请重新登录!");
							usenametext.setText("");
							txtPwd.setText("");
						}else {
							rs=stmt.executeQuery("select * from 学生表  where 学号="+ usenametext.getText().trim());
							while(rs.next()) {
								pw=rs.getString("密码").trim();
								if(txtPwd.getText().equals(pw)) {
									JOptionPane.showMessageDialog(null,"学生登录成功！");
									this.setVisible(false);
									new StudentFrame(usenametext.getText());  //进入学生界面
								}
								else {
									JOptionPane.showMessageDialog(null,"密码错误！请重试");
									txtPwd.setText("");
								}
							}
						}
					}
				}catch(Exception e1) {
					e1.printStackTrace();
					//System.out.println("连接失败！");
				}
				closeDB();
			}
		}
		if(source==b) {   //如果事件源是“重置”按钮
			usenametext.setText("");    
			txtPwd.setText("");
		}
		if(source==c) {   //如果事件源是“取消”按钮
		    //System.exit(0);
			this.dispose();
			new DLFrame();
		}
	}
	
	public static void main(String[] args){
		new DLFrame();
	}
}

/*2017.11.21*/
