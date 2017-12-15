package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class SCMadd extends JFrame implements ActionListener{
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
	JLabel sno=new JLabel("ѧ��:");
	JTextField sno1=new JTextField(10);
	JLabel cno=new JLabel("�γ̺�:");
	JTextField cno1=new JTextField(20);
	JLabel sc=new JLabel("�ɼ�:");
	JTextField sc1=new JTextField(10);
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
	
	public SCMadd() {  //���췽��
		super("�ɼ���Ϣ���ҳ��");
		setLayout(new FlowLayout());
		
		//��������
		sno.setFont(f3);
		cno.setFont(f3);
		sc.setFont(f3);
		//��ť���塢��ɫ
		btnsure.setFont(f1);
		btnsure.setBackground(new Color(131,175,155)); //����ɫ
		btncancel.setFont(f1);
		btncancel.setBackground(new Color(131,175,155)); //����ɫ
		btnagain.setFont(f1);
		btnagain.setBackground(new Color(131,175,155)); //����ɫ
		//�����������񲼾ֹ���
		p1.setLayout(new GridLayout(8,2));
		//p2.setLayout(new GridLayout(4,2));
		//����ǩ���ı�����ӽ����
		p1.add(sno);
		p1.add(sno1);
		p1.add(cno);
		p1.add(cno1);
		p1.add(sc);
		p1.add(sc1);
		
		
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
			stmt.executeUpdate("insert into �ɼ���  values("+"'"+sno1.getText().trim()+"'"+","+"'"+cno1.getText().trim()+"'"+","+"'"+sc1.getText().trim()+"'"+")");
			//System.out.println("insert success!");
			JOptionPane.showMessageDialog(null,"�ɼ���Ϣ��ӳɹ���");
		    this.dispose();
		    new CM().display();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean searchtest(String s1,String s2) {
  		boolean x=false;
  		this.connDB();
  		try {
  			rs=stmt.executeQuery("select * from �ɼ���");
  			while(rs.next()) {
  				if(rs.getString("ѧ��").trim().equals(s1) && rs.getString("�γ̺�").trim().equals(s2)) {  //��java�У��ж��ַ����Ƿ���ͬ��һ��Ҫʹ��equals����!!!!!!!!
  					x=true;
  				}
  			}
  			//return x;
  		}catch(Exception e) {
  			e.printStackTrace();
  		}
  		return x;
  	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsure) {
			if(sno1.getText().equals("") || cno1.getText().equals("") || sc1.getText().equals("") ) {
				JOptionPane.showMessageDialog(null,"��Ϣ����Ϊ�գ�");
			}else if(searchtest(sno1.getText(),cno1.getText())){
				JOptionPane.showMessageDialog(null,"�����ɼ���¼�Ѿ����ڣ���������ӣ�");
				sno1.setText("");
				cno1.setText("");
				sc1.setText("");
			}else {
				this.insert();
			}
		}
		if(e.getSource()==btnagain) {
			sno1.setText("");
			cno1.setText("");
			sc1.setText("");
		}
		if(e.getSource()==btncancel) {
			this.dispose();
			new SCM().display();;
			//System.exit(0);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SCMadd();
    }

}

/*2017.12.06*/