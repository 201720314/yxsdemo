package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ClassMupdate extends JFrame implements ActionListener{
	//�������
	Panel p=new Panel();
	Panel p1=new Panel();
	Panel p2=new Panel();
	/*Panel psno=new Panel();
	Panel psname=new Panel();
	Panel pssex=new Panel();
	Panel psage=new Panel();
	Panel psclass=new Panel();
	Panel pspwd=new Panel();*/
	//�������Ա�ǩ���ı���
	JLabel csno=new JLabel("�༶��:");
	JTextField csno1=new JTextField(10);
	JLabel csname=new JLabel("�༶����:");
	JTextField csname1=new JTextField(20);
	JLabel dept=new JLabel("����ѧԺ:");
	JTextField dept1=new JTextField(10);
	JLabel sum=new JLabel("�༶����");
	JTextField sum1=new JTextField(10);
	JLabel cm=new JLabel("������");
	JTextField cm1=new JTextField(20);
	//��������
	Font f1=new Font("����",Font.BOLD,20);
    Font f2=new Font("��Բ",Font.ITALIC,30);
    Font f3=new Font("����",Font.BOLD,18);
    Font f4=new Font("����",Font.PLAIN,40);
    //���尴ť
    Button btnsure=new Button("ȷ��");
    Button btnagain=new Button("����");
    Button btncancel=new Button("ȡ��");
    
    //���������ַ�
  	String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ";
  	String userName = "sa";
  	String userPwd = "123";
  	Connection conn;
  	Statement stmt;
  	ResultSet rs;
  	
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
	
	public ClassMupdate(String s1,String s2,String s3) {  //���ι��췽��
		super("�༶��Ϣ�޸�ҳ��");
		setLayout(new FlowLayout());
		
		csno1.setText(s1);
		csno1.setEditable(false);
		csname1.setText(s2);
		csname1.setEditable(false);
		dept1.setText(s3);
		dept1.setEditable(false);
		
		//��������
		csno.setFont(f3);
		csname.setFont(f3);
		dept.setFont(f3);
		sum.setFont(f3);
		cm.setFont(f3);
		//��ť���塢��ɫ
		btnsure.setFont(f1);
		btnsure.setBackground(new Color(131,175,155)); //����ɫ
		btncancel.setFont(f1);
		btncancel.setBackground(new Color(131,175,155)); //����ɫ
		btnagain.setFont(f1);
		btnagain.setBackground(new Color(131,175,155)); //����ɫ
		//�����������񲼾ֹ���
		p1.setLayout(new GridLayout(6,2));
		//p2.setLayout(new GridLayout(4,2));
		//����ǩ���ı�����ӽ����
		p1.add(csno);
		p1.add(csno1);
		p1.add(csname);
		p1.add(csname1);
		p1.add(dept);
		p1.add(dept1);
		p1.add(sum);
		p1.add(sum1);
		p1.add(cm);
		p1.add(cm1);
		
		//����ť��ӽ����
		p.add(btnsure);
		p.add(btnagain);
		p.add(btncancel);
		
		//������
		this.add(p1);
		//this.add(p2);
		this.add(p);
		
		//���ö��������Ĵ�С��λ�á��ɼ��Լ�close����
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(500,350);
	    setLocationRelativeTo(null);
	    setVisible(true);
	    
	    //ע�������
	    btnsure.addActionListener(this);
	    btnagain.addActionListener(this);
	    btncancel.addActionListener(this);
	}
	
	public void delete(String s) {   //ɾ����Ϣ����
		try {
			//stmt.executeUpdate("delete  from ��ʦ��  where ��ʦ��="+s);   //�ֲ�����һ��Ҫ��ʼ��
			stmt.executeUpdate("delete  from �༶��  where �༶��="+"'"+s+"'");
			//JOptionPane.showMessageDialog(null,"ɾ���ɹ���");
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	public void insert() {  //���뷽��
		try {
			this.connDB();
			//��������
			stmt.executeUpdate("insert into �༶��  values("+"'"+csno1.getText().trim()+"'"+","+"'"+csname1.getText().trim()+"'"+","+"'"+dept1.getText().trim()+"'"+","+sum1.getText().trim()+","+"'"+cm1.getText().trim()+"'"+")");
			//System.out.println("insert success!");
			JOptionPane.showMessageDialog(null,"�༶��Ϣ��ӳɹ���");
		    this.dispose();
		    new ClassM().display();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsure) {
			if(csno1.getText().equals("") || csname1.getText().equals("") || dept1.getText().equals("") || sum1.getText().equals("") || cm1.getText().equals("") ) {
				JOptionPane.showMessageDialog(null,"��Ϣ����Ϊ�գ�");
			}else {
				this.delete(csno1.getText());
				this.insert();
			}
		}
		if(e.getSource()==btnagain) {
			csno1.setText("");
			csname1.setText("");
			dept1.setText("");
			sum1.setText("");
			cm1.setText("");
		}
		if(e.getSource()==btncancel) {
			this.dispose();
			new ClassM().display();;
			//System.exit(0);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ClassMupdate("","","");
    }
}

/*2017.12.06*/