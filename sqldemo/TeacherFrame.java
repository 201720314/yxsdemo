package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;

public class TeacherFrame extends JFrame implements ActionListener{
	JLabel l=new JLabel("--��ʦҳ��--");
	//�����������
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	//������������
	Font f1=new Font("����",Font.BOLD,30);
    Font f2=new Font("��Բ",Font.ITALIC,30);
    Font f3=new Font("����",Font.BOLD,17);
    Font f4=new Font("����",Font.PLAIN,40);
	
	//����6����ť���Ա����Ա����
	JButton btnTmg=new JButton("������Ϣ����");
	JButton btnSsh=new JButton("ѧ����Ϣ��ѯ");
	JButton btnCsh=new JButton("�γ���Ϣ��ѯ");
	JButton btnSCmg=new JButton("�ɼ���Ϣ����");
	JButton btnCSsh=new JButton("�༶��Ϣ��ѯ");
	JButton btnEXIT=new JButton("�˳�");
	
	String tno;
	String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ";
	String useName="sa";
	String usePwd="123";
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	Object[][] arr;
	String tpwd;
	JScrollPane scroll1,scroll2,scroll3,scroll4,scroll5,scroll6;
	JTable tb1,tb2,tb3,tb4,tb5,tb6; 
	int row;
	
	public TeacherFrame(String str) {   //���췽��
		super("��ʦҳ��");
		setLayout(new FlowLayout());
		//���ñ�ǩ����ɫ
	    l.setFont(f1);
	    l.setForeground(Color.blue);
	    //���ð�ť�������ɫ
	    btnTmg.setFont(f3);
	    btnTmg.setContentAreaFilled(false);
	    //btnTM.setBackground(Color.blue);
	    btnSsh.setFont(f3);
	    btnSsh.setContentAreaFilled(false);
	    btnCsh.setFont(f3);
	    btnCsh.setContentAreaFilled(false);
	    btnSCmg.setFont(f3);
	    btnSCmg.setContentAreaFilled(false);
	    btnCSsh.setFont(f3);
	    btnCSsh.setContentAreaFilled(false);
	    btnEXIT.setFont(f3);
	    btnEXIT.setContentAreaFilled(false);
	    
	    this.tno=str;
	    
		p1.add(l);	
		p1.setOpaque(false);
		p2.setOpaque(false);
		p2.setLayout(new GridLayout(3,2,10,10));
		p2.add(btnTmg);
		p2.add(btnSsh);
		p2.add(btnCsh);
		p2.add(btnSCmg);
		p2.add(btnCSsh);
		p2.add(btnEXIT);
		 
		//���ֹ�����
		this.add(p1);
		this.add(p2);
		
		ImageIcon ic=new ImageIcon("C:\\Users\\YXS\\eclipse-workspace\\edu-a-s\\src\\edu\\yxs\\sqldemo\\6.jpg");
		JLabel l=new JLabel(ic);
		l.setBounds(0,0,ic.getIconWidth(),ic.getIconHeight());
		//l.setBounds(0,0,this.getWidth(),this.getHeight());
		//JPanel ip=(JPanel)this.getContentPane();
		this.getLayeredPane().add(l,new Integer(Integer.MIN_VALUE));
		((JPanel)this.getContentPane()).setOpaque(false);//����͸��
		
		//���ö��������Ĵ�С��λ�á��ɼ��Լ�close����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350,300);
		setLocationRelativeTo(null);
		setVisible(true);
		
		this.connDB();
		
		btnTmg.addActionListener(this);
		btnSsh.addActionListener(this);
		btnCsh.addActionListener(this);
		btnSCmg.addActionListener(this);
		btnCSsh.addActionListener(this);
		btnEXIT.addActionListener(this);
	}
	
	public void connDB() {
		try {
			conn=DriverManager.getConnection(dbURL,useName,usePwd);
			stmt=conn.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeDB() {
		try {
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tmg(String str) {   //������Ϣ������
		JFrame f=new JFrame("������Ϣ");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnchange=new Button("�޸�����");
		Button btnrt=new Button("����");
		btnchange.setFont(f3);
		btnchange.setBackground(new Color(131,175,155));
		btnrt.setFont(f3);
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
		f.add(btnchange);
		f.add(btnrt);
		f.add(scroll1);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
		btnchange.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				change();
			}
		});
	}
	
	public void change() {  //�����޸�����ҳ�棬�´��������޸�����
		this.connDB();
		JFrame f=new JFrame("�޸�����");
		f.setLayout(new FlowLayout());
		JPanel p=new JPanel();
		JPanel p1=new JPanel();
		p.setLayout(new GridLayout(3,2));
		JLabel btn1=new JLabel("��ʼ���룺");
		btn1.setFont(f3);
		JTextField tf1=new JTextField(10);
		JLabel btn2=new JLabel("�޸����룺");
		btn2.setFont(f3);
		JTextField tf2=new JTextField(10);
		Button ok=new Button("ȷ��");
		ok.setBackground(new Color(131,175,155));
		Button cancel=new Button("ȡ��");
		cancel.setBackground(new Color(131,175,155));
		p.add(btn1);
		p.add(tf1);
		p.add(btn2);
		p.add(tf2);
		p1.add(ok);
		p1.add(cancel);
		f.add(p);
		f.add(p1);
		
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setLocation(530,300);
		//f.setLocationRelativeTo(null);
		f.setSize(300,150);
		f.setVisible(true);
		
		ok.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {   //��ȡ��ʼ����
					rs=stmt.executeQuery("select * from ��ʦ�� where ��ʦ��="+tno);
					while(rs.next()) {
						tpwd=rs.getString("��¼����").trim();
					}
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
				if(tf2.getText().equals("") || tf1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "���벻��Ϊ�գ��������޸ģ�");
				}else{
					if(!tpwd.equals(tf1.getText().trim())) {     //spwd.equals(tf1.getText().trim())
					    JOptionPane.showMessageDialog(null,"��ʼ��������������������룡");  //"ԭ������"+spwd+"����������"+tf1.getText();
					    tf1.setText("");
					    tf2.setText("");
					}else {
						try {
							stmt.executeUpdate("update ��ʦ�� set ��¼���� ="+tf2.getText().trim()+"where ��ʦ��="+tno);
						}catch(Exception e1) {
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null,"�����޸ĳɹ��������µ�¼��");
						f.dispose();
						new DLFrame();
					}
					
				}
			}
		});
		cancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
	}
	
	public void ssh(String str) {  //ѧ����Ϣ��ѯ
		JFrame f=new JFrame("ѧ����Ϣ");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("����");
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		int i=0,j=0;
		ArrayList al=new ArrayList();
		try {
			rs=stmt.executeQuery("select * from ѧ����Ϣ��  where ��ʦ��="+str);
			while(rs.next()) {
				al.add(rs.getString("ѧ��"));
				al.add(rs.getString("����"));
				al.add(rs.getString("�Ա�"));
				al.add(rs.getInt("����"));
				al.add(rs.getString("�༶����"));
				al.add(rs.getString("�γ���"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		arr=new Object[i][6];
		try {
			rs=stmt.executeQuery("select * from ѧ����Ϣ��  where ��ʦ��="+str);
			while(rs.next()) {
				arr[j][0]=rs.getString("ѧ��");
				arr[j][1]=rs.getString("����");
				arr[j][2]=rs.getString("�Ա�");
				arr[j][3]=rs.getString("����");
				arr[j][4]=rs.getString("�༶����");
				arr[j][5]=rs.getString("�γ���");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"ѧ��","����","�Ա�","����","�༶����","�γ���"};
		tb2=new JTable(arr,list); //�������
		scroll2=new JScrollPane(tb2);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(scroll2);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
	}
	
	public void csh(String str) {  //�γ���Ϣ��ѯ
		JFrame f=new JFrame("�γ���Ϣ");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("����");
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		int i=0,j=0;
		ArrayList al=new ArrayList();
		try {
			rs=stmt.executeQuery("select  distinct �༶��  ,�༶����,�γ̺�,�γ���  from ѧ����Ϣ��  where ��ʦ��="+str);
			while(rs.next()) {
				al.add(rs.getString("�༶��"));
				al.add(rs.getString("�༶����"));
				al.add(rs.getString("�γ̺�"));
				al.add(rs.getString("�γ���"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		arr=new Object[i][4];
		try {
			rs=stmt.executeQuery("select  distinct �༶��  ,�༶����,�γ̺�,�γ���  from ѧ����Ϣ��  where ��ʦ��="+str);
			while(rs.next()) {
				arr[j][0]=rs.getString("�༶��");
				arr[j][1]=rs.getString("�༶����");
				arr[j][2]=rs.getString("�γ̺�");
				arr[j][3]=rs.getString("�γ���");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"�༶��","�༶����","�γ̺�","�γ���"};
		tb3=new JTable(arr,list); //�������
		scroll3=new JScrollPane(tb3);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(scroll3);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
	}
	
	public void scmg(String str) {  //�ɼ���Ϣ����
		JFrame f=new JFrame("ѧ���ɼ���Ϣ");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("����");
		Button btnchange=new Button("�޸ĳɼ�");
		btnchange.setFont(f3);
		btnchange.setBackground(new Color(131,175,155));
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		int i=0,j=0;
		ArrayList al=new ArrayList();
		try {
			rs=stmt.executeQuery("select * from ѧ����Ϣ��  where ��ʦ��="+str);
			while(rs.next()) {
				al.add(rs.getString("ѧ��"));
				al.add(rs.getString("����"));
				al.add(rs.getString("�Ա�"));
				al.add(rs.getInt("����"));
				al.add(rs.getString("�༶����"));
				al.add(rs.getString("�γ���"));
				al.add(rs.getString("�ɼ�"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		arr=new Object[i][7];
		try {
			rs=stmt.executeQuery("select * from ѧ����Ϣ��  where ��ʦ��="+str);
			while(rs.next()) {
				arr[j][0]=rs.getString("ѧ��");
				arr[j][1]=rs.getString("����");
				arr[j][2]=rs.getString("�Ա�");
				arr[j][3]=rs.getString("����");
				arr[j][4]=rs.getString("�༶����");
				arr[j][5]=rs.getString("�γ���");
				arr[j][6]=rs.getString("�ɼ�");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"ѧ��","����","�Ա�","����","�༶����","�γ���","�ɼ�"};
		tb4=new JTable(arr,list); //�������
		scroll4=new JScrollPane(tb4);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnchange);
		f.add(btnrt);
		f.add(scroll4);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
		btnchange.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				scchange();
			}
		});
	}
	
	public void scchange() {  //�޸ĳɼ�ҳ��
		JFrame f=new JFrame("�޸ĳɼ�");
		f.setLayout(new FlowLayout());
		JLabel l=new JLabel("����༶��");
		JTextField tf=new JTextField(10);
		JButton jb1=new JButton("ȷ��");
		jb1.setFont(f3);
		jb1.setBackground(new Color(131,175,155));
		JButton jb2=new JButton("����");
		jb2.setFont(f3);
		jb2.setBackground(new Color(131,175,155));
		
		f.add(l);
		f.add(tf);
		f.add(jb1);
		f.add(jb2);
		
		jb1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new SCmg(tno,tf.getText());  //����ˢ�³ɼ�ҳ�棬Ϊ�༶�ɼ�
			}
		});
		jb2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				scmg(tno);  //ȡ����ť��������ʾҳ��
			}
		});
		
		f.setSize(200,150);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	//bug����  ֻ���½��������
	/*public void scmg(String s1,String s2) { //��ȷ���༶�ɼ���ҳ��
		String csname=null;
		this.connDB();
		try {
			rs=stmt.executeQuery("select distinct �༶�� ,�༶���� from ѧ����Ϣ�� where �༶��="+"'"+s2+"'");
			while(rs.next()) {
				csname=rs.getString("�༶����").trim();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		JFrame f=new JFrame(csname+" --ѧ���ɼ���Ϣ");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("����");
		Button btnchange=new Button("�޸�");
		btnchange.setFont(f3);
		btnchange.setBackground(new Color(131,175,155));
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
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
		tb5=new JTable(arr,list); //�������
		scroll5=new JScrollPane(tb5);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnchange);
		f.add(btnrt);
		f.add(scroll5);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		
		//String sno1=null; //�ֲ�����һ��Ҫ��ʼ������ȡѧ��ѧ��
		String cno=null; //��ȡ�γ̺�
		
		/////////////////////////////////////////////////////////////////
		//*Bug:ǧ��Ҫ��ͬһ�������ﶨ���ȡ���������������������������޷�ִ��                    //
		//row=tb5.getSelectedRow();  //�������ѡ��������                                              //
		/////////////////////////////////////////////////////////////////
		
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				scmg(tno);
			}
		});
		
		btnchange.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(row==-1) {  //��ʾδ��ѡ��
					JOptionPane.showMessageDialog(null,"��ѡ��Ҫ�޸ĵ���Ϣ��");
				}else {
					//int x=0;
					try {
						//String sno1=(String)tb1.getModel().getValueAt(row,column);
						f.dispose();
						//scchange1(sno1,cno);
					}catch(Exception e1) {
						e1.printStackTrace();
					}
				}
				//f.dispose();
				//scchange();
			}
		});
	   }
	public void test() {  //������ȡ����
		
	}*/
	
	public void cssh(String str) {  //�༶��Ϣ��ѯ
		JFrame f=new JFrame("�༶��Ϣ");
		//JPanel p=new JPanel();
		f.setLayout(new FlowLayout());
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setSize(500,150);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		Button btnrt=new Button("����");
		btnrt.setFont(f3);
		btnrt.setBackground(new Color(131,175,155));
		//btnrt.setSize(20,10);

		this.connDB();
		int i=0,j=0;
		ArrayList al=new ArrayList();
		try {
			rs=stmt.executeQuery("select  distinct ѧ����Ϣ��.�༶��  ,�༶��.�༶����,�༶��.����ѧԺ,�༶��.�༶����,�༶��.������ from �༶��,ѧ����Ϣ��  where ѧ����Ϣ��.�༶��=�༶��.�༶�� and ��ʦ��="+str);
			while(rs.next()) {
				al.add(rs.getString("�༶��"));
				al.add(rs.getString("�༶����"));
				al.add(rs.getString("����ѧԺ"));
				al.add(rs.getInt("�༶����"));
				al.add(rs.getString("������"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		arr=new Object[i][5];
		try {
			rs=stmt.executeQuery("select  distinct ѧ����Ϣ��.�༶��  ,�༶��.�༶����,�༶��.����ѧԺ,�༶��.�༶����,�༶��.������ from �༶��,ѧ����Ϣ��  where ѧ����Ϣ��.�༶��=�༶��.�༶�� and ��ʦ��="+str);
			while(rs.next()) {
				arr[j][0]=rs.getString("�༶��");
				arr[j][1]=rs.getString("�༶����");
				arr[j][2]=rs.getString("����ѧԺ");
				arr[j][3]=rs.getInt("�༶����");
				arr[j][4]=rs.getString("������");
				j++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] list= {"�༶��","�༶����","����ѧԺ","�༶����","������"};
		tb6=new JTable(arr,list); //�������
		scroll6=new JScrollPane(tb6);
		//f.add(btnrt,BorderLayout.NORTH);
		f.add(btnrt);
		f.add(scroll6);  //�˴��и�Сbug��ֻ����ӽ�������壬���ܽ�ͷ��������ʾ����
		//f1.add(btnrt); 
		btnrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.dispose();
				new TeacherFrame(tno);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		//��ťΪ����ʦ��Ϣ��������תҳ��
		if(e.getSource().equals(btnTmg)) {
			this.dispose();
			this.tmg(tno);
		}
		//��ťΪ��ѧ����Ϣ��������תҳ��
		if(e.getSource().equals(btnSsh)) {
			//new SM().display();
			this.dispose();
			this.ssh(tno);
		}
		//��ťΪ���γ���Ϣ��������תҳ��
		if(e.getSource().equals(btnCsh)) {
			this.dispose();
			this.csh(tno);
		}
		//��ťΪ���ɼ���Ϣ��������תҳ��
		if(e.getSource().equals(btnSCmg)) {
			this.dispose();
			this.scmg(tno);
		}
		//��ťΪ���༶��Ϣ��������תҳ��
		if(e.getSource().equals(btnCSsh)) {
			this.dispose();
			this.cssh(tno);
		}
		//��ťΪ���˳�����ϵͳ���������˳�
		if(e.getSource().equals(btnEXIT)) {
        	//System.exit(0);
			this.dispose();
			new DLFrame();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TeacherFrame("20050002");
	}

}
