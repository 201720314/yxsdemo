package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GLFrame extends JFrame implements ActionListener{

	///////////////ע��////////////////
	//��������ֻ�г�Ա�����ͷ������м��ڳ�Ա������ֱ�Ӷ���
	//������Ա����ǩ
	JLabel l=new JLabel("--����Ա--");
	//�����������
	JPanel p1=new JPanel();
	JPanel p2=new JPanel();
	//������������
	Font f1=new Font("����",Font.BOLD,30);
    Font f2=new Font("��Բ",Font.ITALIC,30);
    Font f3=new Font("����",Font.BOLD,18);
    Font f4=new Font("����",Font.PLAIN,40);
	
	//����6����ť���Ա����Ա����
	JButton btnTM=new JButton("��ʦ��Ϣ����");
	JButton btnSM=new JButton("ѧ����Ϣ����");
	JButton btnCM=new JButton("�γ���Ϣ����");
	JButton btnSCM=new JButton("�ɼ���Ϣ����");
	JButton btnCSM=new JButton("�༶��Ϣ����");
	JButton btnEXIT=new JButton("�˳�����ϵͳ");
	
	public GLFrame() {   //���췽��
		super("����Ա����ҳ��");
		setLayout(new FlowLayout());
		//���ñ�ǩ����ɫ
	    l.setFont(f1);
	    l.setForeground(Color.blue);
	    //���ð�ť�������ɫ
	    btnTM.setFont(f3);
	    btnTM.setContentAreaFilled(false);
	    //btnTM.setBackground(Color.blue);
	    btnSM.setFont(f3);
	    btnSM.setContentAreaFilled(false);
	    btnCM.setFont(f3);
	    btnCM.setContentAreaFilled(false);
	    btnSCM.setFont(f3);
	    btnSCM.setContentAreaFilled(false);
	    btnCSM.setFont(f3);
	    btnCSM.setContentAreaFilled(false);
	    btnEXIT.setFont(f3);
	    btnEXIT.setContentAreaFilled(false);
	    
		p1.add(l);
		p1.setOpaque(false);
		p2.setLayout(new GridLayout(3,2,10,10));
		p2.setOpaque(false);
		p2.add(btnTM);
		p2.add(btnSM);
		p2.add(btnCM);
		p2.add(btnSCM);
		p2.add(btnCSM);
		p2.add(btnEXIT);
		 
		//���ֹ�����
		this.add(p1);
		this.add(p2);
		
		//���ñ���ͼƬ
		ImageIcon ic=new ImageIcon("C:\\Users\\YXS\\eclipse-workspace\\edu-a-s\\src\\edu\\yxs\\sqldemo\\5.jpg");
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
		
		btnTM.addActionListener(this);
		btnSM.addActionListener(this);
		btnCM.addActionListener(this);
		btnSCM.addActionListener(this);
		btnCSM.addActionListener(this);
		btnEXIT.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		//��ťΪ����ʦ��Ϣ��������תҳ��
		if(e.getSource().equals(btnTM)) {
			this.dispose();
			new TM().display();
		}
		//��ťΪ��ѧ����Ϣ��������תҳ��
		if(e.getSource().equals(btnSM)) {
			//new SM().display();
			this.dispose();
			new SM().display();
		}
		//��ťΪ���γ���Ϣ��������תҳ��
		if(e.getSource().equals(btnCM)) {
			this.dispose();
			new CM().display();
		}
		//��ťΪ���ɼ���Ϣ��������תҳ��
		if(e.getSource().equals(btnSCM)) {
			this.dispose();
			new SCM().display();
		}
		//��ťΪ���༶��Ϣ��������תҳ��
		if(e.getSource().equals(btnCSM)) {
			this.dispose();
			new ClassM().display();
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
		new GLFrame();
	}

}

/*2017.11.23*/