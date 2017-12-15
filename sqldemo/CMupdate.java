package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class CMupdate extends JFrame implements ActionListener{
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
	JLabel no=new JLabel("�γ̺�:");
	JTextField cno=new JTextField(10);
	JLabel name=new JLabel("�γ���:");
	JTextField cname=new JTextField(20);
	JLabel tno=new JLabel("��ʦ��:");
	JTextField ctno=new JTextField(10);
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
	
	public CMupdate(String str) {  //���ι��췽��
		super("�γ���Ϣ�޸�ҳ��");
		setLayout(new FlowLayout());
		
		cno.setText(str);
		cno.setEditable(false);
		
		//��������
		no.setFont(f3);
		name.setFont(f3);
		tno.setFont(f3);
		//��ť���塢��ɫ
		btnsure.setFont(f1);
		btnsure.setBackground(new Color(131,175,155)); //����ɫ
		btncancel.setFont(f1);
		btncancel.setBackground(new Color(131,175,155)); //����ɫ
		btnagain.setFont(f1);
		btnagain.setBackground(new Color(131,175,155)); //����ɫ
		//�����������񲼾ֹ���
		p1.setLayout(new GridLayout(4,2));
		//p2.setLayout(new GridLayout(4,2));
		//����ǩ���ı�����ӽ����
		p1.add(no);
		p1.add(cno);
		p1.add(name);
		p1.add(cname);
		p1.add(tno);
		p1.add(ctno);
		
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
	
	public void insert() {  //���뷽��
		try {
			this.connDB();
			//��������
			stmt.executeUpdate("insert into �γ̱�  values("+"'"+cno.getText().trim()+"'"+","+"'"+cname.getText().trim()+"'"+","+"'"+ctno.getText().trim()+"'"+")");
			//System.out.println("insert success!");
			JOptionPane.showMessageDialog(null,"�α���Ϣ�޸ĳɹ���");
		    this.dispose();
		    new CM().display();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String s) {   //ɾ����Ϣ����
		try {
			//stmt.executeUpdate("delete  from ��ʦ��  where ��ʦ��="+s);   //�ֲ�����һ��Ҫ��ʼ��
			stmt.executeUpdate("delete  from �γ̱�  where �γ̺�="+s);
			//JOptionPane.showMessageDialog(null,"ɾ���ɹ���");
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsure) {
			if(cno.getText().equals("") || cname.getText().equals("") || ctno.getText().equals("") ) {
				JOptionPane.showMessageDialog(null,"��Ϣ����Ϊ�գ�");
			}else {
				this.delete(cno.getText());
				this.insert();
			}
		}
		if(e.getSource()==btnagain) {
			//cno.setText("");
			cname.setText("");
			ctno.setText("");
		}
		if(e.getSource()==btncancel) {
			this.dispose();
			new CM().display();;
			//System.exit(0);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CMadd();
    }
}
