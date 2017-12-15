package edu.yxs.sqldemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class CM extends JFrame implements ActionListener{
	//����˵���ť
    Button btnadd=new Button("���");
    Button btnupdate=new Button("�޸�");
    Button btnsearch=new Button("��ѯ");
    Button btndisplay=new Button("ˢ����ʾ");
    Button btnreturn=new Button("����");
    //����˵���
    JMenuBar mb=new JMenuBar();
    //����������
    JScrollPane jsp;
    JScrollPane jsp1;
    //������
    JTable tb;
    JTable tb1;
    //��������
    Font f1=new Font("�п�",Font.BOLD,15);
    Font f2=new Font("����",Font.ITALIC,36);
    
    //�����������ݿ��������
    Connection conn;
    Statement stmt;
    ResultSet rs;
    
  //���ö˿ڳ���
    String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ";
    String username="sa";
    String userpwd="123";
    
    //����һ��Object����
    Object[][] arr;
    
    public void connDB() { //�������ݿⷽ��
    	try {
    		conn=DriverManager.getConnection(dbURL,username,userpwd);
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
    
    public CM() {  //�޲ι��췽��
    	super("�γ���Ϣ����");
    	//���ð�ť�������ɫ
    	btnadd.setFont(f1);
    	btnadd.setBackground(new Color(131,175,155));
    	btnupdate.setFont(f1);
    	btnupdate.setBackground(new Color(131,175,155));
    	btnsearch.setFont(f1);
    	btnsearch.setBackground(new Color(131,175,155));
    	btndisplay.setFont(f1);
    	btndisplay.setBackground(new Color(131,175,155));
    	btnreturn.setFont(f1);
    	btnreturn.setBackground(new Color(131,175,155));
    	//����ť��ӽ��˵���
    	mb.add(btnadd);
    	mb.add(btnupdate);
    	mb.add(btnsearch);
    	mb.add(btndisplay);
    	mb.add(btnreturn);
    	//�������ݿ�
    	this.connDB();
    	
        //ע���¼�������
    	btnadd.addActionListener(this);
    	btnupdate.addActionListener(this);
    	btnsearch.addActionListener(this);
    	btndisplay.addActionListener(this);
    	btnreturn.addActionListener(this);
    	
    	setSize(500,300);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
    	setVisible(true);
    	//���˵�����ӽ�������
    	this.setJMenuBar(mb);
    }   
    
    public void update() {
		String cno1=null; //�ֲ�����һ��Ҫ��ʼ��
		int row=tb.getSelectedRow();  //�������ѡ��������
		if(row==-1) {  //��ʾδ��ѡ��
			JOptionPane.showMessageDialog(null,"��ѡ��Ҫ�޸ĵ���Ϣ��");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from �γ̱�");
				while(rs.next() && x<=row) {
					cno1=rs.getString("�γ̺�");
					x++;
				}
				this.dispose();
				new CMupdate(cno1);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
    
    public void display() {
    	int i=0,j=0;
    	//����һ���������洢��������
    	ArrayList list=new ArrayList();
    	try {
    		rs=stmt.executeQuery("select * from �γ̱� ");
    		while(rs.next()) {
    			list.add(rs.getString("�γ̺�"));
    			list.add(rs.getString("�γ���"));
    			list.add(rs.getString("��ʦ��"));
    			i++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	arr=new Object[i][3];
    	String[] listname= {"�γ̺�","�γ���","��ʦ��"};
    	//�ö�ά�������洢��������
    	try {
    		rs=stmt.executeQuery("select * from �γ̱� order by �γ̺�");
    		while(rs.next()) {
    			arr[j][0]=rs.getString("�γ̺�");
    			arr[j][1]=rs.getString("�γ���");
    			arr[j][2]=rs.getString("��ʦ��");
    			j++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	tb=new JTable(arr,listname);
    	jsp=new JScrollPane(tb);
    	this.add(jsp);
    }
     
    public void show(String str) {   //��ѯ�������
		JFrame f=new JFrame("��ѯ���");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("����");
		btnrt.setFont(f1);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		arr=new Object[1][4];
		try {
			rs=stmt.executeQuery("select * from �γ̱�1  where �γ̺�="+str);
			while(rs.next()) {
				arr[0][0]=rs.getString("�γ̺�");
    			arr[0][1]=rs.getString("�γ���");
    			arr[0][2]=rs.getString("��ʦ��");
    			arr[0][3]=rs.getString("��ʦ����");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list={"�γ̺�","�γ���","��ʦ��","��ʦ����"};
		tb1=new JTable(arr,list); //�������
		jsp1=new JScrollPane(tb1);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(jsp1);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
			}
		});
	}
    
    //�÷�������ȷ���Ƿ������ݿ����ҵ��γ̺�
  	public boolean searchtest(String str) {
  		boolean x=false;
  		this.connDB();
  		try {
  			rs=stmt.executeQuery("select * from �γ̱�");
  			while(rs.next()) {
  				if(rs.getString("�γ̺�").trim().equals(str)) {  //��java�У��ж��ַ����Ƿ���ͬ��һ��Ҫʹ��equals����!!!!!!!!
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
		JLabel stuno=new JLabel("����γ̺ţ�");
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
					JOptionPane.showMessageDialog(null,"������γ̺�");
				}else {
					if(!(searchtest(stuno1.getText().trim()))) {
						f.dispose();
						JOptionPane.showMessageDialog(null,"�Բ��𣬸ÿγ̲����ڣ�");
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
		//���¼��������Ӧ�ô���
    	if(e.getSource()==btnadd) {  
    		this.dispose();
    		new CMadd();
    	}
    	if(e.getSource()==btnupdate) {
    		//this.dispose();
    		this.update();
    	}
    	if(e.getSource()==btnsearch) {
    		this.search();
    	}
    	if(e.getSource()==btndisplay) {
    		this.dispose();
    		new CM().display();
    	}
    	if(e.getSource()==btnreturn) {
    		this.dispose();
    		new GLFrame();
    	}
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CM().display();
	}

}

/*2017.12.05*/