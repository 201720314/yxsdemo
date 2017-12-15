package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class StudentFrame extends JFrame implements ActionListener{

	///////////////ע��////////////////
	//��������ֻ�г�Ա�����ͷ������м��ڳ�Ա������ֱ�Ӷ���
	//������Ա����ǩ
	JLabel l=new JLabel("--ѧ��ҳ��--");
	//�����������
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	//������������
	Font f1=new Font("����",Font.BOLD,30);
    Font f2=new Font("��Բ",Font.ITALIC,20);
    Font f3=new Font("����",Font.BOLD,15);
    Font f4=new Font("����",Font.PLAIN,18);
	
	//����6����ť���Ա����Ա����
	Button btnSelf=new Button("������Ϣ����");
	Button btnSCsh=new Button("�ɼ���Ϣ��ѯ");
	Button btnCsh=new Button("�γ���Ϣ��ѯ");
	Button btnCSsh=new Button("�༶��Ϣ��ѯ");
	Button btnEXIT=new Button("�˳�");
	
	//
	String dbURL="jdbc:sqlserver://localhost:1433;databaseName=����ϵͳ";
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
	
	public StudentFrame(String str) {   //���췽��
		super("ѧ��ҳ��");
		setLayout(new FlowLayout());
		//���ñ�ǩ����ɫ
	    l.setFont(f1);
	    l.setForeground(Color.blue);
	    //���ð�ť�������ɫ
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
		//���ֹ�����
		this.add(p1);
		this.add(p2);
		
		ImageIcon ic=new ImageIcon("C:\\Users\\YXS\\eclipse-workspace\\edu-a-s\\src\\edu\\yxs\\sqldemo\\1.jpg");
		JLabel l=new JLabel(ic);
		l.setBounds(0,0,ic.getIconWidth(),ic.getIconHeight());
		//l.setBounds(0,0,this.getWidth(),this.getHeight());
		//JPanel ip=(JPanel)this.getContentPane();
		this.getLayeredPane().add(l,new Integer(Integer.MIN_VALUE));
		((JPanel)this.getContentPane()).setOpaque(false);//����͸��
		
		//���ö��������Ĵ�С��λ�á��ɼ��Լ�close����
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
	
	public void cssearch(String str) {   //�༶��ѯ����
		JFrame f=new JFrame("�༶��Ϣҳ��");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("����");
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		arr=new Object[1][6];
		try {
			rs=stmt.executeQuery("select * from �༶��1  where ѧ��="+str);
			while(rs.next()) {
				arr[0][0]=rs.getString("ѧ��");
				arr[0][1]=rs.getString("�༶��");
    			arr[0][2]=rs.getString("�༶����");
    			arr[0][3]=rs.getString("����ѧԺ");
    			arr[0][4]=rs.getString("�༶����");
    			arr[0][5]=rs.getString("������");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list={"ѧ��","�༶��","�༶����","����ѧԺ","�༶����","������"};
		tb1=new JTable(arr,list); //�������
		jsp1=new JScrollPane(tb1);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(jsp1);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new StudentFrame(sno);
			}
		});
	}
	
	public void csearch(String str) {   //�γ̲�ѯ����
		JFrame f=new JFrame("�γ���Ϣҳ��");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("����");
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);
        
		int i=0,j=0;
		ArrayList ar=new ArrayList();
		this.connDB();
		try {
			rs=stmt.executeQuery("select * from �γ̱�2 where ѧ��="+str);
			while(rs.next()){
				ar.add(rs.getString("ѧ��"));
				ar.add(rs.getString("�γ̺�"));
				ar.add(rs.getString("�γ���"));
				ar.add(rs.getString("��ʦ��"));
				ar.add(rs.getString("��ʦ����"));
				j++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		arr=new Object[j][5];
		try {
			rs=stmt.executeQuery("select * from �γ̱�2  where ѧ��="+str);
			while(rs.next()) {
				arr[i][0]=rs.getString("ѧ��");
				arr[i][1]=rs.getString("�γ̺�");
    			arr[i][2]=rs.getString("�γ���");
    			arr[i][3]=rs.getString("��ʦ��");
    			arr[i][4]=rs.getString("��ʦ����");
    			i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list={"ѧ��","�γ̺�","�γ���","��ʦ��","��ʦ����"};
		tb1=new JTable(arr,list); //�������
		jsp1=new JScrollPane(tb1);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(jsp1);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new StudentFrame(sno);
			}
		});
	}
	
	public void scsearch(String str) {  //��ѯ�ɼ�ҳ��
		int i=0,j=0;
		JFrame f=new JFrame("�ɼ���Ϣҳ��");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,200);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("����");
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		ArrayList list=new ArrayList();
    	try {
    		rs=stmt.executeQuery("select * from �ɼ���1 ");
    		while(rs.next()) {
    			list.add(rs.getString("ѧ��"));
    			list.add(rs.getString("����"));
    			list.add(rs.getString("�γ̺�"));
    			list.add(rs.getString("�γ���"));
    			list.add(rs.getString("��ʦ����"));
    			list.add(rs.getString("�ɼ�"));
    			i++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		arr=new Object[i][6];
		try {
			rs=stmt.executeQuery("select * from �ɼ���1  where ѧ��="+str+"order by �γ̺�");
			while(rs.next()) {
				arr[j][0]=rs.getString("ѧ��");
    			arr[j][1]=rs.getString("����");
    			arr[j][2]=rs.getString("�γ̺�");
    			arr[j][3]=rs.getString("�γ���");
    			arr[j][4]=rs.getString("��ʦ����");
    			arr[j][5]=rs.getString("�ɼ�");
    			j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] listname={"ѧ��","����","�γ̺�","�γ���","��ʦ����","�ɼ�"};
		tb1=new JTable(arr,listname); //�������
		jsp1=new JScrollPane(tb1);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(jsp1);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new StudentFrame(str);
			}
		});   	
    }
	
	public void display(String str) {   //������ʾ������Ϣ
		JFrame f1=new JFrame("������Ϣ");
		f1.setLayout(new FlowLayout());
		f1.setSize(500,150);
		f1.setVisible(true);
		f1.setLocationRelativeTo(null);
		Button btnchange=new Button("�޸�����");
		Button btnrt=new Button("����");
		btnchange.setFont(f3);
		btnchange.setBackground(new Color(131,175,155));
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));

		this.connDB();
		arr=new Object[1][6];
		try {
			rs=stmt.executeQuery("select * from ѧ����  where ѧ��="+str);
			while(rs.next()) {
				arr[0][0]=rs.getString("ѧ��");
				arr[0][1]=rs.getString("����");
				arr[0][2]=rs.getString("�Ա�");
				arr[0][3]=rs.getInt("����");
				arr[0][4]=rs.getString("�༶��");
				arr[0][5]=rs.getString("����");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] columnNames= {"ѧ��","����","�Ա�","����","�༶��","����"};
		stable1=new JTable(arr,columnNames); //�������
		scroll1=new JScrollPane(stable1);
		f1.add(btnchange);
		f1.add(btnrt);
		f1.add(scroll1);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
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
	
	public void change() {  //�����޸�����ҳ�棬�´��������޸�����
		this.connDB();
		JFrame f=new JFrame("�޸�����");
		f.setLayout(new FlowLayout());
		JPanel p=new JPanel();
		JPanel p1=new JPanel();
		p.setLayout(new GridLayout(3,2));
		JLabel btn1=new JLabel("��ʼ���룺");
		btn1.setFont(f3);
		JTextField tf1=new JTextField(10);
		JLabel btn2=new JLabel("�޸����룺");
		btn2.setFont(f3);
		JTextField tf2=new JTextField(10);
		Button ok=new Button("ȷ��");
		ok.setBackground(new Color(131,175,155));
		Button cancel=new Button("ȡ��");
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
				try {   //��ȡ��ʼ����
					rs=stmt.executeQuery("select * from ѧ���� where ѧ��="+sno);
					while(rs.next()) {
						spwd=rs.getString("����").trim();  //!!!�������ݿ��д�������ֵһ��Ҫ��trim();��Ϊ������ж���Ŀո񣡣���
						/*bug:���ݿ������ݻ��пո�mmp������12/10/�賿1:56,�ܣ�*/
						//spwd="123";
					}
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
				if(tf1.getText().equals("") || tf2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "���벻��Ϊ�գ��������޸ģ�");
				}else {
					if(!spwd.equals(tf1.getText())) {     //spwd.equals(tf1.getText().trim())
					    JOptionPane.showMessageDialog(null,"��ʼ��������������������룡");  //"ԭ������"+spwd+"����������"+tf1.getText();
					    tf1.setText("");
					    tf2.setText("");
				    }else {
					    try {
						    stmt.executeUpdate("update ѧ���� set ���� ="+"'"+tf2.getText().trim()+"'"+"where ѧ��="+sno);
					    }catch(Exception e1) {
						    e1.printStackTrace();
					    }
					    JOptionPane.showMessageDialog(null,"�����޸ĳɹ��������µ�¼��");
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
		//��ťΪ��������Ϣ�޸ġ�����תҳ��
		if(e.getSource().equals(btnSelf)) {
			this.dispose();
			this.display(sno);
		}
		//��ťΪ���ɼ���Ϣ��ѯ������תҳ��
		if(e.getSource().equals(btnSCsh)) {
			//new SM().display();
			this.dispose();
			this.scsearch(sno);
		}
		//��ťΪ���γ���Ϣ��ѯ������תҳ��
		if(e.getSource().equals(btnCsh)) {
			this.dispose();
			this.csearch(sno);
		}
		//��ťΪ���༶��Ϣ��ѯ������תҳ��
		if(e.getSource().equals(btnCSsh)) {
			this.dispose();
			this.cssearch(sno);
		}
		//��ťΪ���˳�����ϵͳ���������˳�
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