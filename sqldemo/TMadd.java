package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class TMadd extends JFrame implements ActionListener,ItemListener{
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
	JLabel no=new JLabel("��ʦ��:");
	JTextField tno=new JTextField(10);
	JLabel name=new JLabel("����:");
	JTextField tname=new JTextField(20);
	JLabel sex=new JLabel("�Ա�:");
	JComboBox tsex=new JComboBox();
	//JTextField ssex=new JTextField(4);
	JLabel xl=new JLabel("ѧ��:");
	JTextField txl=new JTextField(4);
	JLabel zc=new JLabel("ְ��:");
	JTextField tzc=new JTextField(10);
	JLabel dept=new JLabel("����ѧԺ:");
	JTextField tdept=new JTextField(10);
	JLabel pwd=new JLabel("����:");
	JTextField tpwd=new JTextField(10);
	JLabel pwd1=new JLabel("ȷ������:");
	JTextField tpwd1=new JTextField(10);
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
	
	public TMadd() {  //���췽��
		super("��ʦ��Ϣ���ҳ��");
		setLayout(new FlowLayout());
		
		//��������
		no.setFont(f3);
		name.setFont(f3);
		sex.setFont(f3);
	    xl.setFont(f3);
		zc.setFont(f3);
		dept.setFont(f3);
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
		p1.setLayout(new GridLayout(8,2));
		//p2.setLayout(new GridLayout(4,2));
		//�Ա�������б����ѡ��
		tsex.addItem("��");
		tsex.addItem("Ů");
		//����ǩ���ı�����ӽ����
		p1.add(sex);
		p1.add(tsex);
		p1.add(no);
		p1.add(tno);
		p1.add(name);
		p1.add(tname);
		p1.add(xl);
		p1.add(txl);
		p1.add(zc);
		p1.add(tzc);
		p1.add(dept);
		p1.add(tdept);
		p1.add(pwd);
		p1.add(tpwd);
		p1.add(pwd1);
		p1.add(tpwd1);
		
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
	    tsex.addItemListener(this);
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
	
	//�÷�������ȷ���Ƿ������ݿ����ҵ���ʦ��
	public boolean searchtest(String str) {
		boolean x=false;
		this.connDB();
		try {
			rs=stmt.executeQuery("select * from ��ʦ��");
			while(rs.next()) {
				if(rs.getString("��ʦ��").trim().equals(str)) {  //��java�У��ж��ַ����Ƿ���ͬ��һ��Ҫʹ��equals����!!!!!!!!
					x=true;
				}
			}
			//return x;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return x;
	}
	
	public void insert() {  //���뷽��
		try {
			this.connDB();
			//��������
			stmt.executeUpdate("insert into ��ʦ��  values("+tno.getText().trim()+","+"'"+tname.getText().trim()+"'"+","+"'"+sexlist[sex1]+"'"+","+"'"+txl.getText().trim()+"'"+","+"'"+tzc.getText().trim()+"'"+","+"'"+tdept.getText().trim()+"'"+","+tpwd1.getText().trim()+")");
			//System.out.println("insert success!");
			JOptionPane.showMessageDialog(null,"��Ϣ��ӳɹ���");
		    this.dispose();
		    new TM().display();;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnsure) {
			if(tno.getText().equals("") || tname.getText().equals("") || txl.getText().equals("") || tpwd.getText().equals("") || tpwd1.getText().equals("") || tzc.getText().equals("") || tdept.getText().equals("") ) {
				JOptionPane.showMessageDialog(null,"��Ϣ����Ϊ�գ�");
			}else if(!tpwd.getText().trim().equals(tpwd1.getText().trim())) {  //ȷ�������Ƿ���ͬ
				JOptionPane.showMessageDialog(null,"������ȷ�����룡");
				tpwd1.setText("");
			}else if(searchtest(tno.getText())) {
				JOptionPane.showMessageDialog(null,"�ý�ʦ�Ѵ���!���������!");
				tno.setText("");
				tname.setText("");
				txl.setText("");
				tzc.setText("");
				tdept.setText("");
				tpwd.setText("");
				tpwd1.setText("");
			}else {
				this.insert();
			}

		}
		if(e.getSource()==btnagain) {
			tno.setText("");
			tname.setText("");
			txl.setText("");
			tzc.setText("");
			tdept.setText("");
			tpwd.setText("");
			tpwd1.setText("");
		}
		if(e.getSource()==btncancel) {
			this.dispose();
			new TM().display();;
			//System.exit(0);
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TMadd();
    }

}

/*2017.12.04*/