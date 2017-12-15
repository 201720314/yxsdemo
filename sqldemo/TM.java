package edu.yxs.sqldemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class TM extends JFrame implements ActionListener,ItemListener{
	//���尴ť����ӽ�JMenuBar
	Button btnadd=new Button("���");
	Button btndelete=new Button("ɾ��");
	Button btnupdate=new Button("�޸�");
	Button btnsearch=new Button("��ѯ");
	Button btndisplay=new Button("ˢ����ʾ");
	Button btnreturn=new Button("����");
	//����˵���
	JMenuBar mb=new JMenuBar();
	//��������
	Font f1=new Font("�п�",Font.BOLD,15);
	Font f2=new Font("����",Font.ITALIC,24);
	
	//������
	JTable tb;
	JTable tb1;
	//����������
	JScrollPane jsp;
	JScrollPane scroll1;
	
	//�������ݿ����,�������ӱ���
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	//���������ַ���
	String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ";
	String username="sa";
	String userpwd="123";
	
	Object[][] arr;
	//�������ݿ�
	public void connDB(){  //�������ݿⷽ��
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
	
	public TM() {   //���췽��
		super("��ʦ��Ϣ����ϵͳ");
		//���ð�ť��ɫ������
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
		
		//����ť��ӽ��˵�����
		mb.add(btnadd);
		mb.add(btndelete);
		mb.add(btnupdate);
		mb.add(btnsearch);
		mb.add(btndisplay);
		mb.add(btnreturn);
		
		//�������ݿ�
		this.connDB();
		//����ťע�������
		btnadd.addActionListener(this);
		btndelete.addActionListener(this);
		btnupdate.addActionListener(this);
		btnsearch.addActionListener(this);
		btndisplay.addActionListener(this);
		btnreturn.addActionListener(this);
		
		setSize(500,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		this.setJMenuBar(mb);
	}
	
	public void display() {
		//this.connDB();
		String sql="select * from ��ʦ��";
		ArrayList ar=new ArrayList();  //�������н��д洢���ݿ��Ա��Ϣ
		int i=0,j=0;
		try {
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				ar.add(rs.getString("��ʦ��"));
				ar.add(rs.getString("����"));
				ar.add(rs.getString("�Ա�"));
				ar.add(rs.getString("ѧ��"));
				ar.add(rs.getString("ְ��"));
				ar.add(rs.getString("����ѧԺ"));
				ar.add(rs.getString("��¼����"));
				i++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		//������ά������д洢
		arr=new Object[i][7];
		String[] list= {"��ʦ��","����","�Ա�","ѧ��","ְ��","����ѧԺ","��¼����"};
		try {
			rs=stmt.executeQuery("select * from ��ʦ��  order by ��ʦ��");
			while(rs.next()) {
				arr[j][0]=rs.getString("��ʦ��");
				arr[j][1]=rs.getString("����");
				arr[j][2]=rs.getString("�Ա�");
				arr[j][3]=rs.getString("ѧ��");
				arr[j][4]=rs.getString("ְ��");
				arr[j][5]=rs.getString("����ѧԺ");
				arr[j][6]=rs.getString("��¼����");
				j++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		tb=new JTable(arr,list);
		jsp=new JScrollPane(tb);
		this.add(jsp);
	}
	
	public void delete() {   //ɾ����Ϣ����
		String tno=null;  //�����ַ������������ս�ʦ��   
		int row=tb.getSelectedRow();  //�������ѡ��������
		if(row==-1) {  //��ʾδ��ѡ��
			JOptionPane.showMessageDialog(null,"��ѡ��Ҫɾ���ļ�¼��");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from ��ʦ��");
				while(rs.next() && x<=row) {
					tno=rs.getString("��ʦ��");
					x++;
				}
				stmt.executeUpdate("delete  from �γ̱�  where ��ʦ��="+tno);
				stmt.executeUpdate("delete  from ��ʦ��  where ��ʦ��="+tno);   //�ֲ�����һ��Ҫ��ʼ��
				JOptionPane.showMessageDialog(null,"ɾ���ɹ���");
				this.dispose();
				new TM().display();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
			
	public void update() {
		String sno1=null; //�ֲ�����һ��Ҫ��ʼ��
		int row=tb.getSelectedRow();  //�������ѡ��������
		if(row==-1) {  //��ʾδ��ѡ��
			JOptionPane.showMessageDialog(null,"��ѡ��Ҫ�޸ĵ���Ϣ��");
		}else {
			int x=0;
			try {
				rs=stmt.executeQuery("select * from ��ʦ��");
				while(rs.next() && x<=row) {
					sno1=rs.getString("��ʦ��");
					x++;
				}
				this.dispose();
				new TMupdate(sno1);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
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
		arr=new Object[1][7];
		try {
			rs=stmt.executeQuery("select * from ��ʦ��  where ��ʦ��="+str);
			while(rs.next()) {
				arr[0][0]=rs.getString("��ʦ��");
				arr[0][1]=rs.getString("����");
				arr[0][2]=rs.getString("�Ա�");
				arr[0][3]=rs.getString("ѧ��");
				arr[0][4]=rs.getString("ְ��");
				arr[0][5]=rs.getString("����ѧԺ");
				arr[0][6]=rs.getString("��¼����");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"��ʦ��","����","�Ա�","ѧ��","ְ��","����ѧԺ","��¼����"};
		tb1=new JTable(arr,list); //�������
		scroll1=new JScrollPane(tb1);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(scroll1);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
			}
		});
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
	
	//���ҷ���
	public void search() {   //��Ч�ڽ�һ������д�ڷ�������
		JFrame f=new JFrame("��ѯ");
		f.setLayout(new FlowLayout());
		f.setSize(240,180);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JLabel stuno=new JLabel("�����ʦ�ţ�");
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
					JOptionPane.showMessageDialog(null,"�������ʦ��");
				}else {
					if(!searchtest(stuno1.getText().trim())) {
						f.dispose();
						JOptionPane.showMessageDialog(null,"�Բ��𣬸ý�ʦ�����ڣ�");
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
	
	public void itemStateChanged(ItemEvent e) {
		
	}
	
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource()==btnadd) {
    		this.dispose();
    		new TMadd();
    	}
    	if(e.getSource()==btndelete) {
    		JOptionPane.showMessageDialog(null,"ɾ������������������");
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
    		new TM().display();
    	}
    	if(e.getSource()==btnreturn) {
    		this.dispose();
        	new GLFrame();
    	}
    }
    
	public static void main(String[] args) {
		new TM();
	}
	

}

/*2017.12.04*/