package edu.yxs.sqldemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class ClassM extends JFrame implements ActionListener{
	//����˵���ť
    Button btnadd=new Button("���");
    Button btndelete=new Button("ɾ��");
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
    String userName="sa";
    String userPwd="123";
    
    //����һ��Object����
    Object[][] arr;
    
    public void connDB() { //�������ݿⷽ��
    	try {
    		conn=DriverManager.getConnection(dbURL,userName,userPwd);
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
    
    public ClassM() {  //�޲ι��췽��
    	super("�༶��Ϣ����");
    	//���ð�ť�������ɫ
    	btnadd.setFont(f1);
    	btnadd.setBackground(new Color(131,175,155));
    	btndelete.setFont(f1);
    	btndelete.setBackground(new Color(131,175,155));
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
    	mb.add(btndelete);
    	mb.add(btnupdate);
    	mb.add(btnsearch);
    	mb.add(btndisplay);
    	mb.add(btnreturn);
    	//�������ݿ�
    	this.connDB();
    	
        //ע���¼�������
    	btnadd.addActionListener(this);
    	btndelete.addActionListener(this);
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
    
    /*public void delete(String s) {   //ɾ����Ϣ����
		try {
			//stmt.executeUpdate("delete  from ��ʦ��  where ��ʦ��="+s);   //�ֲ�����һ��Ҫ��ʼ��
			stmt.executeUpdate("delete  from �༶��  where �༶��="+"'"+s+"'");
			//JOptionPane.showMessageDialog(null,"ɾ���ɹ���");
		}catch(Exception e) {
			e.printStackTrace();
		}
    }*/
    
    public void update() {
		String cno1=null,cno2=null,cno3=null; //�ֲ�����һ��Ҫ��ʼ��
		int row=tb.getSelectedRow();  //�������ѡ��������
		if(row==-1) {  //��ʾδ��ѡ��
			JOptionPane.showMessageDialog(null,"��ѡ��Ҫ�޸ĵ���Ϣ��");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from �༶��");
				while(rs.next() && x<=row) {
					cno1=rs.getString("�༶��");
					cno2=rs.getString("�༶����");
					cno3=rs.getString("����ѧԺ");
					x++;
				}
				this.dispose();
				//this.delete(cno1);
				new ClassMadd(cno1,cno2,cno3);
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
    		rs=stmt.executeQuery("select * from �༶�� ");
    		while(rs.next()) {
    			list.add(rs.getString("�༶��"));
    			list.add(rs.getString("�༶����"));
    			list.add(rs.getString("����ѧԺ"));
    			list.add(rs.getInt("�༶����"));
    			list.add(rs.getString("������"));
    			i++;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	arr=new Object[i][5];
    	String[] listname= {"�༶��","�༶����","����ѧԺ","�༶����","������"};
    	//�ö�ά�������洢��������
    	try {
    		rs=stmt.executeQuery("select * from �༶��  order by �༶��");
    		while(rs.next()) {
    			arr[j][0]=rs.getString("�༶��");
    			arr[j][1]=rs.getString("�༶����");
    			arr[j][2]=rs.getString("����ѧԺ");
    			arr[j][3]=rs.getString("�༶����");
    			arr[j][4]=rs.getString("������");
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
		arr=new Object[1][5];
		try {
			rs=stmt.executeQuery("select * from �༶��  where �༶��="+str);
			while(rs.next()) {
				arr[0][0]=rs.getString("�༶��");
    			arr[0][1]=rs.getString("�༶����");
    			arr[0][2]=rs.getString("����ѧԺ");
    			arr[0][3]=rs.getString("�༶����");
    			arr[0][4]=rs.getString("������");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list={"�༶��","�༶����","����ѧԺ","�༶����","������"};
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
    
    public void delete() {   //ɾ����Ϣ����
		String sno=null;  //�����ַ�������������ѧ��   
		int row=tb.getSelectedRow();  //�������ѡ��������
		if(row==-1) {  //��ʾδ��ѡ��
			JOptionPane.showMessageDialog(null,"��ѡ��Ҫɾ���ļ�¼��");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from �༶��");
				while(rs.next() && x<=row) {
					sno=rs.getString("�༶��");
					x++;
				}
				stmt.executeUpdate("delete  from �༶��  where �༶��="+"'"+sno+"'");   //�ֲ�����һ��Ҫ��ʼ��
				//stmt.executeUpdate("delete  from �ɼ���  where ѧ��="+sno);
				JOptionPane.showMessageDialog(null,"ɾ���ɹ���");
				this.dispose();
				new ClassM().display();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
    
    //�÷�������ȷ���Ƿ������ݿ����ҵ��γ̺�
  	public boolean searchtest(String str) {
  		boolean x=false;
  		this.connDB();
  		try {
  			rs=stmt.executeQuery("select * from �༶��");
  			while(rs.next()) {
  				if(rs.getString("�༶��").trim().equals(str)) {  //��java�У��ж��ַ����Ƿ���ͬ��һ��Ҫʹ��equals����!!!!!!!!
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
		JLabel stuno=new JLabel("����༶�ţ�");
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
					JOptionPane.showMessageDialog(null,"������༶��");
				}else {
					if(!(searchtest(stuno1.getText().trim()))) {
						f.dispose();
						JOptionPane.showMessageDialog(null,"�Բ��𣬸ð༶�����ڣ�");
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
    		new ClassMadd();
    	}
    	if(e.getSource()==btndelete) {  
    		JOptionPane.showMessageDialog(null,"ɾ���������������");
			this.delete();
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
    		new ClassM().display();
    	}
    	if(e.getSource()==btnreturn) {
    		this.dispose();
    		new GLFrame();
    	}
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ClassM().display();
	}

}

/*2017.12.06*/