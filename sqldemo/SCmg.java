package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class SCmg extends JFrame implements ActionListener{
	
	Font f1=new Font("����",Font.BOLD,30);
    Font f2=new Font("��Բ",Font.ITALIC,30);
    Font f3=new Font("����",Font.BOLD,17);
    Font f4=new Font("����",Font.PLAIN,40);
    
	Button btnrt=new Button("����");
	Button btnchange=new Button("�޸�");
	
	String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ";
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
	
	public void connDB(){  //�������ݿⷽ��
		try {
			conn=DriverManager.getConnection(dbURL,useName,usePwd);
			stmt=conn.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeDB() {  //�ر����ݿⷽ��
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
			rs=stmt.executeQuery("select distinct �༶�� ,�༶���� from ѧ����Ϣ�� where �༶��="+"'"+s2+"'");
			while(rs.next()) {
				csname=rs.getString("�༶����").trim();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		this.setTitle(csname+" --ѧ���ɼ���Ϣ");
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
			rs=stmt.executeQuery("select * from ѧ����Ϣ��  where ��ʦ��="+s1+"and �༶��='"+s2+"'");
			while(rs.next()) {
				al.add(rs.getString("ѧ��"));
				al.add(rs.getString("����"));
				al.add(rs.getString("�Ա�"));
				al.add(rs.getInt("����"));
				al.add(rs.getString("�༶����"));
				al.add(rs.getString("�γ̺�"));
				al.add(rs.getString("�γ���"));
				al.add(rs.getString("�ɼ�"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		arr=new Object[i][8];
		try {
			rs=stmt.executeQuery("select * from ѧ����Ϣ��  where ��ʦ��="+s1+"and �༶��= '"+s2+"'");
			while(rs.next()) {
				arr[j][0]=rs.getString("ѧ��");
				arr[j][1]=rs.getString("����");
				arr[j][2]=rs.getString("�Ա�");
				arr[j][3]=rs.getString("����");
				arr[j][4]=rs.getString("�༶����");
				arr[j][5]=rs.getString("�γ̺�");
				arr[j][6]=rs.getString("�γ���");
				arr[j][7]=rs.getString("�ɼ�");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"ѧ��","����","�Ա�","����","�༶����","�γ̺�","�γ���","�ɼ�"};
		tb=new JTable(arr,list); //�������
		scroll=new JScrollPane(tb);
		//f.add(btnrt,BorderLayout.NORTH);
		add(btnchange);
		add(btnrt);
		add(scroll);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//row=tb.getSelectedRow();
		
		btnchange.addActionListener(this);
		btnrt.addActionListener(this);
		
	}
	
	public void scchange(String sno,String cno) {  //�ɼ��޸ľ�ȷ������ҳ��
		String sname=null; 
		String cname=null;
		this.connDB();
		try {
			rs=stmt.executeQuery("select * from ѧ����Ϣ��  where ѧ��="+sno+"and �γ̺�="+cno);
			while(rs.next()) {
				sname=rs.getString("����").trim();
				cname=rs.getString("�γ���").trim();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		JFrame f=new JFrame(sname+"ͬѧ --�ɼ�ҳ��");
		f.setLayout(new FlowLayout());
		f.setLocation(470,280);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(320,200);
		f.setVisible(true);
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(4,2));
		JLabel l0=new JLabel("ѧ��");
		JTextField tf0=new JTextField(10);
		tf0.setText(sno);
		tf0.setEditable(false);
		JLabel l1=new JLabel("����");
		JTextField tf1=new JTextField(10);
		tf1.setText(sname);
		tf1.setEditable(false);
		JLabel l2=new JLabel("�γ���");
		JTextField tf2=new JTextField(10);
		tf2.setText(cname);
		tf2.setEditable(false);
		JLabel l3=new JLabel("�ɼ�");
		JTextField tf3=new JTextField(10);
		JButton btn1=new JButton("ȷ��");
		btn1.setFont(f3);
		JButton btn2=new JButton("ȡ��");
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
		
		btn1.addMouseListener(new MouseAdapter() {  //ȷ�����ݸ���
			public void mouseClicked(MouseEvent e) {
			    f.dispose();
				try {
					stmt.executeUpdate("update �ɼ��� set �ɼ�="+"'"+tf3.getText()+"'"+" where ѧ��="+sno+"and �γ̺�="+cno);
					JOptionPane.showMessageDialog(null,"�ɼ��޸ĳɹ���");
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
		if(row==-1) {  //��ʾδ��ѡ��
			JOptionPane.showMessageDialog(null,"��ѡ��Ҫ�޸ĵ���Ϣ��");
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
