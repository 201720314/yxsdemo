/*�ò��Գ�����������JComboBox���е�getSelectedIndex()��������ʲô�����
  �����֪�÷������������б��е�����ֵ��������������±�
*/
package edu.yxs.sqldemo;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Test1 extends JFrame implements ItemListener{
	JComboBox j;
	Panel p;
	
	public Test1() {
		super("���������б�������");
		setLayout(new FlowLayout());
		j=new JComboBox();
		p=new Panel();
		j.addItem("����Ա");
		j.addItem("��ʦ");
		j.addItem("ѧ��");
		j.addItemListener(this);
		p.add(j);
		this.add(p);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(360,240);
		setVisible(true);
	}
    
	public void itemStateChanged(ItemEvent e) {
		//�ø߼��¼����ڼ����û���ѡ�����
		if(e.getStateChange()==ItemEvent.SELECTED) {  //�ж������б��Ƿ�ѡ��
			JComboBox j1=(JComboBox)e.getSource();  //ǿ������ת��+��ȡ�¼�Դ
			System.out.println(j1.getSelectedIndex());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test1();
	}

}