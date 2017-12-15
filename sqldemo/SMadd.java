package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class SMadd extends JFrame implements ActionListener,ItemListener{
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
	JLabel no=new JLabel("ѧ��:");
	JTextField sno=new JTextField(10);
	JLabel name=new JLabel("����:");
	JTextField sname=new JTextField(20);
	JLabel sex=new JLabel("�Ա�:");
	JComboBox ssex=new JComboBox();
	//JTextField ssex=new JTextField(4);
	JLabel age=new JLabel("����:");
	JTextField sage=new JTextField(4);
	JLabel classno=new JLabel("�༶��:");
	JTextField sclassno=new JTextField(10);
	JLabel pwd=new JLabel("����:");
	JTextField spwd=new JTextField(10);
	JLabel pwd1=new JLabel("ȷ������:");
	JTextField spwd1=new JTextField(10);
	//��������
	Font f1=new Font("����",Font.BOLD,20);
    Font f2=new Font("��Բ",Font.ITALIC,30);
    Font f3=new Font("����",Font.BOLD,18);
    Font f4=new Font("����",Font.PLAIN,40);
    //���尴ť
    Button btnsure=new Button("ȷ��");
    Button btnagain=new Button("����");
    Button btncancel=new Button("ȡ��");
    
    int sex1=0;  //���������б��ı�
    String[] sexlist= {"��","Ů"};
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
	
	public SMadd() {  //���췽��
		super("ѧ����Ϣ���ҳ��");
		setLayout(new FlowLayout());
		
		//��������
		no.setFont(f3);
		name.setFont(f3);
		sex.setFont(f3);
	    age.setFont(f3);
		classno.setFont(f3);
		pwd.setFont(f3);
		pwd1.setFont(f3);
		//��ť���塢��ɫ
		btnsure.setFont(f1);
		btnsure.setBackground(new Color(131,175,155)); //����ɫ
		btncancel.setFont(f1);
		btncancel.setBackground(new Color(131,175,155)); //����ɫ
		btnagain.setFont(f1);
		btnagain.setBackground(new Color(131,175,155)); //����ɫ
		//�����������񲼾ֹ���
		p1.setLayout(new GridLayout(7,2));
		//p2.setLayout(new GridLayout(4,2));
		//�Ա�������б����ѡ��
		ssex.addItem("��");
		ssex.addItem("Ů");
		//ssex.setOpaque(false);
		//����ǩ���ı�����ӽ����
		p1.add(sex);
		p1.add(ssex);
		p1.add(no);
		p1.add(sno);
		p1.add(name);
		p1.add(sname);
		p1.add(age);
		p1.add(sage);
		p1.add(classno);
		p1.add(sclassno);
		p1.add(pwd);
		p1.add(spwd);
		p1.add(pwd1);
		p1.add(spwd1);
		
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
	    ssex.addItemListener(this);
	    btnsure.addActionListener(this);
	    btnagain.addActionListener(this);
	    btncancel.addActionListener(this);
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED) {
			JComboBox j=(JComboBox)e.getSource();
			sex1=j.getSelectedIndex();
		}
	}
	
	public void insert() {  //��Ӳ��뷽��
		//������������
		try {
			this.connDB();
			//��������
			stmt.executeUpdate("insert into ѧ����  values("+"'"+sno.getText().trim()+"'"+","+"'"+sname.getText().trim()+"'"+","+"'"+sexlist[sex1]+"'"+","+sage.getText().trim()+","+"'"+sclassno.getText().trim()+"'"+","+"'"+spwd1.getText().trim()+"'"+")");
			//System.out.println("insert success!");
			JOptionPane.showMessageDialog(null,"��Ϣ��ӳɹ���");
		    this.dispose();
		    new SM();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	//����Ѱ��ѧ���Ƿ����
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
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsure) {
			//���ж�������Ϣ�Ƿ�Ϊ�գ����ж������Ƿ���ȷ
			if(sno.getText().equals("") || sname.getText().equals("") || sage.getText().equals("") || sclassno.getText().equals("") || spwd.getText().equals("") || spwd1.getText().equals("") ) {
				JOptionPane.showMessageDialog(null,"��Ϣ����Ϊ�գ�");
			}else if(!spwd.getText().trim().equals(spwd1.getText().trim())) {  //ȷ�������Ƿ���ͬ
				JOptionPane.showMessageDialog(null,"������ȷ�����룡");
				spwd1.setText("");
			}else if(searchtest(sno.getText())) {
				JOptionPane.showMessageDialog(null,"��ѧ���Ѵ���");
				sno.setText("");
				sname.setText("");
				sage.setText("");
				sclassno.setText("");
				spwd.setText("");
				spwd1.setText("");
			}else {
				this.insert();
			}

		}
		if(e.getSource()==btnagain) {
			sno.setText("");
			sname.setText("");
			sage.setText("");
			sclassno.setText("");
			spwd.setText("");
			spwd1.setText("");
		}
		if(e.getSource()==btncancel) {
			this.dispose();
			new SM().display();;
			//System.exit(0);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SMadd();
    }

}

/*2017.11.26*/