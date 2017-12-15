package edu.yxs.sqldemo;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SM extends JFrame implements ActionListener{   //ѧ������ϵͳ
	//�������
	//JPanel p1=new JPanel();
	//JPanel p2=new JPanel();
	//����5����ť
	Button btnAdd=new Button("���");
	Button btnDelete=new Button("ɾ��");
	Button btnUpdate=new Button("�޸�");
	Button btnSearch=new Button("��ѯ");
	Button btnDisplay=new Button("ˢ����ʾ");
	Button btnCancel=new Button("����");
	//������������
	Font f1=new Font("����",Font.BOLD,30);
	Font f2=new Font("��Բ",Font.ITALIC,30);
	Font f3=new Font("�п�",Font.BOLD,15);
    Font f4=new Font("����",Font.PLAIN,40);
	//�����˵���
	JMenuBar mb=new JMenuBar();
	//������
	JTable stable;
	JTable stable1;
	//���������
	JScrollPane scroll;
	JScrollPane scroll1;
	
	//�����������ݿ����
	//�������ݿ�͵������ݿ�
	Connection conn;
	Statement stmt;
	ResultSet rs;
		
	//���������ַ�
	String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ";
	String userName = "sa";
	String userPwd = "123";
	
	//��������
	Object[][] arr;  //����ObjectΪ������ĸ��࣬���Խ��������������͵�����Ԫ��
	
	public void connDB() {   //�������ݿⷽ��
		try {
			//�������ݿ�
			conn=DriverManager.getConnection(dbURL,userName,userPwd);
			stmt=conn.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
			//System.out.println("����ʧ�ܣ�");
		}
	}
	
	public void closeDB() {  //�ر����ݿⷽ��
		try {
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
			//System.out.println("�ر�ʧ�ܣ�");
		}
	}
	
	public SM() {
		super("ѧ����Ϣ����ҳ��");
		//���ֹ���
		//this.add("South",p1);
		//this.add("center",p1);
		//��������
		btnAdd.setFont(f3);
		btnAdd.setBackground(new Color(131,175,155)); //����ɫ
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
		
		//����ť��ӽ��˵����У���ֱ�ӷŽ����İ�ť��ͬ
		mb.add(btnAdd);
		mb.add(btnDelete);
		mb.add(btnUpdate);
		mb.add(btnSearch);
		mb.add(btnDisplay);
		mb.add(btnCancel);
		
		//�������ݿ�
		this.connDB();
		
		//ע��ʱ�������
		btnAdd.addActionListener(this);
		btnDelete.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnSearch.addActionListener(this);
		btnDisplay.addActionListener(this);
		btnCancel.addActionListener(this);

		//���ô��ڵĴ�С��λ��
		setSize(500,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setJMenuBar(mb);  //��Ӳ˵����Ժ�����Ҫ�ü��������رմ���
		this.setVisible(true);
	}
	
	
	public void display() {  //��ʾ����ѧ������Ϣ
		int i=0,j=0,k=0;
		//���ȿɱ����鼯��
		ArrayList al=new ArrayList();
		
		//ע�⣺��try{}catch{}Ŀ�ļ������ݿ������ݵ�����
		try {
			rs=stmt.executeQuery("select * from ѧ����  ");
			while(rs.next()) {   //����������al������������Ϣ
				//ע�⣺���trim()ƥ�䵽null���ݣ������ݿ�ᱨ��
				al.add(rs.getString("ѧ��").trim());
				al.add(rs.getString("����").trim());
				al.add(rs.getString("�Ա�").trim());
				al.add(rs.getInt("����"));
				al.add(rs.getString("�༶��").trim());
				al.add(rs.getString("����").trim());
				i++;  //����ͳ����Ϣ����
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//�����ά����
		arr=new Object[i][6];
		//�����˵���ͷ����
		String[] columnNames= {"ѧ��","����","�Ա�","����","�༶��","����"};
		//�ô����������ն���Ϣ���з���
		try {
			rs=stmt.executeQuery("select * from ѧ����  order by ѧ��");
			while(rs.next()) {   //����������al������������Ϣ
				arr[j][0]=rs.getString("ѧ��").trim();
				arr[j][1]=rs.getString("����").trim();
				arr[j][2]=rs.getString("�Ա�").trim();
				arr[j][3]=rs.getInt("����");
				arr[j][4]=rs.getString("�༶��").trim();
				arr[j][5]=rs.getString("����").trim();
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		stable=new JTable(arr,columnNames); //�������
		scroll=new JScrollPane(stable);
		this.add(scroll);  //��������
	}
	
	public void delete() {   //ɾ����Ϣ����
		String sno=null;  //�����ַ�������������ѧ��   
		int row=stable.getSelectedRow();  //�������ѡ��������
		if(row==-1) {  //��ʾδ��ѡ��
			JOptionPane.showMessageDialog(null,"��ѡ��Ҫɾ���ļ�¼��");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from ѧ����");
				while(rs.next() && x<=row) {
					sno=rs.getString("ѧ��");
					x++;
				}
				stmt.executeUpdate("delete  from ѧ����  where ѧ��="+sno);   //�ֲ�����һ��Ҫ��ʼ��
				stmt.executeUpdate("delete  from �ɼ���  where ѧ��="+sno);
				JOptionPane.showMessageDialog(null,"ɾ���ɹ���");
				this.dispose();
				new SM().display();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		String sno1=null; //�ֲ�����һ��Ҫ��ʼ��
		int row=stable.getSelectedRow();  //�������ѡ��������
		if(row==-1) {  //��ʾδ��ѡ��
			JOptionPane.showMessageDialog(null,"��ѡ��Ҫ�޸ĵ���Ϣ��");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from ѧ����");
				while(rs.next() && x<=row) {
					sno1=rs.getString("ѧ��");
					x++;
				}
				this.dispose();
				new SMupdate(sno1);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	public void show(String str) {   //��ѯ�������
		JFrame f1=new JFrame("��ѯ���");
		f1.setLayout(new FlowLayout());
		f1.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f1.setSize(500,150);
		f1.setVisible(true);
		f1.setLocationRelativeTo(null);
		Button btnrt=new Button("����");
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
		f1.add(btnrt);
		f1.add(scroll1);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt);
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f1.dispose();
			}
		});
	}
	
	//�÷�������ȷ���Ƿ������ݿ����ҵ�ѧ��
	public boolean searchtest(String str) {
		boolean x=false;
		this.connDB();
		try {
			rs=stmt.executeQuery("select * from ѧ����");
			while(rs.next()) {
				if(rs.getString("ѧ��").trim().equals(str)) {
					x=true;
				}
			}
			//return x;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return x;
	}
	
	public void search() {   //��Ч�ڽ�һ������д�ڷ�������
		JFrame f=new JFrame("��ѯ");
		f.setLayout(new FlowLayout());
		f.setSize(240,180);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JLabel stuno=new JLabel("����ѧ�ţ�");
		JTextField stuno1=new JTextField(10);
		Button ok=new Button("ȷ��");
		Button cancel=new Button("ȡ��");
		p1.add(stuno);
		p1.add(stuno1);
		p2.add(ok);
		p2.add(cancel);
		f.add(p1);
		f.add(p2);
		//Ϊ���ע�������
		ok.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(stuno1.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"������ѧ��");
				}else {
					if(!(searchtest(stuno1.getText().trim()))) {
						f.dispose();
						JOptionPane.showMessageDialog(null,"�Բ��𣬸�ѧ�������ڣ�");
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
            JOptionPane.showMessageDialog(null,"ɾ���������������");
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