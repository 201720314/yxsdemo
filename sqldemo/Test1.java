/*该测试程序用来测试JComboBox类中的getSelectedIndex()方法返回什么结果。
  结果可知该方法返回下拉列表中的索引值，类似于数组的下标
*/
package edu.yxs.sqldemo;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Test1 extends JFrame implements ItemListener{
	JComboBox j;
	Panel p;
	
	public Test1() {
		super("测试下拉列表索引框");
		setLayout(new FlowLayout());
		j=new JComboBox();
		p=new Panel();
		j.addItem("管理员");
		j.addItem("教师");
		j.addItem("学生");
		j.addItemListener(this);
		p.add(j);
		this.add(p);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(360,240);
		setVisible(true);
	}
    
	public void itemStateChanged(ItemEvent e) {
		//该高级事件用于监听用户的选定与否
		if(e.getStateChange()==ItemEvent.SELECTED) {  //判断下拉列表是否选定
			JComboBox j1=(JComboBox)e.getSource();  //强制类型转换+获取事件源
			System.out.println(j1.getSelectedIndex());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test1();
	}

}