//��¼ҳ��
package edu.yxs.sqldemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.util.EventListener;
import java.sql.*;
//��¼ҳ��
public class DLFrame extends JFrame implements ActionListener,ItemListener{
	JPanel p1,p2,p3;
	Font f1,f2,f3,f4;
    /*Font f1=new Font("����",Font.BOLD,26);  //��������Ѿ����������ٶ��������ڷ�������(*�˴�����)
    Font f2=new Font("��Բ",Font.ITALIC,20);
    Font f3=new Font("����",Font.BOLD,30);
    Font f4=new Font("����",Font.PLAIN,40);*/
    JLabel head1=new JLabel("�û���¼  / ");
    JLabel head2=new JLabel("LOGIN ");
    /*head1.setFont(f1);
    head1.setForeground(Color.BLUE);
    head2.setFont(f2);
    head2.setForeground(Color.GRAY);*/
    
    //�����ǩ���ı����Լ������б�
    //�û�����ǩ�������ı���
	JLabel usename=new JLabel("�û�ID��");
	JTextField usenametext=new JTextField(10);
	
	//�����ǩ�������ı���JPasswordField������Զ�������������ش���
	JLabel password=new JLabel("�� �룺");
	JPasswordField txtPwd=new JPasswordField(10);
	
	//�����֤��
	JLabel test=new JLabel("��֤�룺");
	//�˴�������Ҫ��д
	
	//��ɫ��ǩ�������б��˴�ʹ��swing�е�JComboBox�࣬Ҳ��ʹ��awt�е�Choice��
	JLabel role=new JLabel("��ɫ��");
	JComboBox boxrole=new JComboBox();
	
	//��������ѡ��ť
	JButton a=new JButton("��¼");
	JButton b=new JButton("����");
	JButton c=new JButton("ȡ��");
	
	//����Connection��Statement����
	//�������ݿ�͵������ݿ�
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	//���������ַ�
	String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ";
	String userName = "sa";
	String userPwd = "123";
	
	//������������������б������ֵ,Ҳ�ɷ��ؾ���ֵ
	int index=0;
	String index1;
	
	static int ok=1;
	static int cancel=0;
	int actionCode=0;
	
	public DLFrame(){        //���췽��
		super("�Ϸ�ʦ��ѧԺ����");
		setLayout(new FlowLayout());
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		f1=new Font("����",Font.BOLD,30);
	    f2=new Font("��Բ",Font.ITALIC,30);
	    f3=new Font("����",Font.BOLD,18);
	    f4=new Font("����",Font.PLAIN,40);
	    
	    //�������1�ı�ǩ
	    head1.setFont(f1);
	    head1.setForeground(Color.BLUE);
	    head2.setFont(f2);
	    head2.setForeground(Color.GRAY);
	    //p1.setBackground(new Color(255,240,240));
	    p1.setBackground(null);
	    p1.setOpaque(false);
		p1.add(head1);
	    p1.add(head2);
	    
	    //���2Ϊ4��2�е����񲼾ֹ�����
	    p2.setLayout(new GridLayout(4,2));
	    /*usename.setFont(f3);
	    password.setFont(f3);
	    role.setFont(f3);*/
	    //�����б����������
	    boxrole.addItem("����Ա");
	    boxrole.addItem("��ʦ");
	    boxrole.addItem("ѧ��");
	    //p2.setBackground(new Color(240,255,240));
	    p2.setBackground(null);
	    p2.setOpaque(false);
	    boxrole.setOpaque(false);
	    usenametext.setOpaque(false);
	    txtPwd.setOpaque(false);
	    p2.add(role);
	    p2.add(boxrole);
	    p2.add(usename);
	    p2.add(usenametext);
	    p2.add(password);
	    p2.add(txtPwd);
	    
	    
	    
	    //��3����ť��ӽ����3��
	    //p3.setBackground(new Color(230,230,250));
	    p3.setBackground(null);
	    p3.setOpaque(false);//����͸��
	    a.setContentAreaFilled(false);
	    b.setContentAreaFilled(false);
	    c.setContentAreaFilled(false);
	    //setGround(a);
	    p3.add(a);
	    p3.add(b);
	    p3.add(c);
	    
	    //�����������ӽ����������
	    this.add(p1);
	    this.add(p2);
	    this.add(p3);
	    //this.add(new MyPanel());
	    //���ñ���ͼƬ  
	    
        //bug:�˴�����ͼƬ�޷���ȫ������ʾ��
		ImageIcon ic=new ImageIcon("C:\\Users\\YXS\\eclipse-workspace\\edu-a-s\\src\\edu\\yxs\\sqldemo\\4.jpg");
		JLabel l=new JLabel(ic);
		l.setBounds(0,0,ic.getIconWidth(),ic.getIconHeight());
		//l.setBounds(0,0,this.getWidth(),this.getHeight());
		//JPanel ip=(JPanel)this.getContentPane();
		this.getLayeredPane().add(l,new Integer(Integer.MIN_VALUE));
		((JPanel)this.getContentPane()).setOpaque(false);//����͸��		
	    
	    //���ö��������Ĵ�С��λ�á��ɼ��Լ�close����
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(350,300);
	    setLocationRelativeTo(null);
	    setVisible(true);
	    
	    //ע���¼�������
	    //�����this���Ǳ��࣬�Ѿ���ActionListener��ʵ����    --�д�̽��
	    boxrole.addItemListener(this);  
	    a.addActionListener(this);
	    b.addActionListener(this);
	    c.addActionListener(this);
	}
	
	public void connDB() {   //�������ݿⷽ��
		//����jdbc4�汾��������������ѡ����ʡ��
		/*try {
			//��������
			Class.forName("com.microsoft.sqlsever.jdbc.SQLSeverDriver");
			//System.out.println("���سɹ�");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}*/
		try {
			//�������ݿ�
			//�������ѡ����ʹ�ã�Debugʱ�˴������⣡
			//conn=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ","sa","123");
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
	
	//ʵ��ItemListener�ĳ��󷽷�
	//�������б�����¼�����
	public void itemStateChanged(ItemEvent e) {
		//�ø߼��¼����ڼ����û���ѡ�����
		if(e.getStateChange()==ItemEvent.SELECTED) {  //�ж������б��Ƿ�ѡ��
			JComboBox j=(JComboBox)e.getSource();  //ǿ������ת��+��ȡ�¼�Դ
			index=j.getSelectedIndex();
			//index1=j.getSelectedItem();
		}
	}
	
	//�÷�������ȷ���Ƿ������ݿ����ҵ�ѧ��
	public boolean searchsno(String str) {
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
	
	//�÷�������ȷ���Ƿ������ݿ����ҵ���ʦ��
	public boolean searchtno(String str) {
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
	
	//�÷�������ȷ���Ƿ��������ݿ����ҵ�����ԱID
	public boolean searchmanagerno(String str) {
		boolean x=false;
		this.connDB();
		try {
			rs=stmt.executeQuery("select * from ����Ա");
			while(rs.next()) {
				if(rs.getString("����Ա��").trim().equals(str)) {  //��java�У��ж��ַ����Ƿ���ͬ��һ��Ҫʹ��equals����!!!!!!!!
					x=true;
				}
			}
			//return x;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return x;
	}
	
	//ʵ��ActionListener�ĳ��󷽷�
	//��������ť�����¼�����
	public void actionPerformed(ActionEvent e){
		Object source=e.getSource();
		String un;
		String pw;
		boolean success= false;  //�����ж��Ƿ��¼�ɹ�
		if(source==a) {  //����¼�Դ�ǡ�ȷ������ť
			if(usenametext.getText().equals("") || txtPwd.getText().equals("")) {  //�ж��û����������Ƿ�Ϊ��
				//JOptionPane��������ʾ�򣬳�������:1.showMessageDialog; 2.showOptionDialog
				JOptionPane.showMessageDialog(null,"��¼�������벻��Ϊ�գ�");
			}else {
				//�û������������Ϊ�գ������������ݿ�
				this.connDB();
				//�������������ݿ⣬��ִ�����ݿ�������裬��ЧΪ��һ��try{}catch{}�ֽ�ɼ�����Ч��һ����
				try {
					if(index==0) {  //����Ա��¼
						//trim()�������ӵ�ǰ String �����Ƴ�����ǰ���հ��ַ���β���հ��ַ�
						
						if(!searchmanagerno(usenametext.getText().trim())) {
							JOptionPane.showMessageDialog(null,"�Բ��𣬴��û������ڣ������µ�¼!");
							usenametext.setText("");
							txtPwd.setText("");
						}else {
							rs=stmt.executeQuery("select * from ����Ա  where ����Ա��="+ usenametext.getText().trim());
							while(rs.next()) {
								pw=rs.getString(2).trim();
								if(txtPwd.getText().equals(pw)) {
									JOptionPane.showMessageDialog(null,"����Ա��¼�ɹ���");
									this.setVisible(false);
									new GLFrame();  //�������Ա����
								}else {
									JOptionPane.showMessageDialog(null,"�������������");
									txtPwd.setText("");
								}
							}
						}
					}
					/////////////////////////////////////////////
					/*
					�˴���Ȼ��bug�����Ƕ��ڲ����ڵ��û��޷�׼ȷʶ��
			                    ����11/22���������ƣ�
					*/
					/////////////////////////////////////////////
					/*if(index==0) {  //����Ա��¼
						rs=stmt.executeQuery("select * from ����Ա");
						while(rs.next()) {
							un=rs.getString(1).trim();
							pw=rs.getString(2).trim();
							if(un==usename.getText().trim() && pw==txtPwd.getText().trim()) {
								success=true;
								break;
							}
						}
						if(success==false) {
							JOptionPane.showMessageDialog(null,"��¼ʧ�ܣ�");
						}else {
							JOptionPane.showMessageDialog(null,"����Ա��¼�ɹ���");
							this.setVisible(false);
							//new ManagerFrame();  //�������Ա����
						}
					}*/
					//*��bug�Ѿ�ͨ���������͵�search�����Ľ�������12/07��
					if(index==1) {  //��ʦ��¼
						if(!searchtno(usenametext.getText().trim())) {
							JOptionPane.showMessageDialog(null,"�Բ��𣬴��û������ڣ������µ�¼!");
							usenametext.setText("");
							txtPwd.setText("");
						}else {
							rs=stmt.executeQuery("select * from ��ʦ��  where ��ʦ��="+ usenametext.getText().trim());
							while(rs.next()) {
								pw=rs.getString("��¼����").trim();
								if(txtPwd.getText().equals(pw)) {
									JOptionPane.showMessageDialog(null,"��ʦ��¼�ɹ���");
									this.setVisible(false);
									new TeacherFrame(usenametext.getText());  //�����ʦ����
								}
								else {
									JOptionPane.showMessageDialog(null,"�������������");
									txtPwd.setText("");
								}
							}
						}
					}
					if(index==2) {  //ѧ����¼
						if(!searchsno(usenametext.getText().trim())) {
							JOptionPane.showMessageDialog(null,"�Բ��𣬴��û������ڣ������µ�¼!");
							usenametext.setText("");
							txtPwd.setText("");
						}else {
							rs=stmt.executeQuery("select * from ѧ����  where ѧ��="+ usenametext.getText().trim());
							while(rs.next()) {
								pw=rs.getString("����").trim();
								if(txtPwd.getText().equals(pw)) {
									JOptionPane.showMessageDialog(null,"ѧ����¼�ɹ���");
									this.setVisible(false);
									new StudentFrame(usenametext.getText());  //����ѧ������
								}
								else {
									JOptionPane.showMessageDialog(null,"�������������");
									txtPwd.setText("");
								}
							}
						}
					}
				}catch(Exception e1) {
					e1.printStackTrace();
					//System.out.println("����ʧ�ܣ�");
				}
				closeDB();
			}
		}
		if(source==b) {   //����¼�Դ�ǡ����á���ť
			usenametext.setText("");    
			txtPwd.setText("");
		}
		if(source==c) {   //����¼�Դ�ǡ�ȡ������ť
		    //System.exit(0);
			this.dispose();
			new DLFrame();
		}
	}
	
	public static void main(String[] args){
		new DLFrame();
	}
}

/*2017.11.21*/
