package edu.yxs.sqldemo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
public class Check {
	static String s;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final JFrame f=new JFrame("—È÷§¬Î");
		Panel p=new MyPanel();
		//s=p.s1;
		System.out.println(s);
		f.add(new MyPanel());
		
		//f.setLocation(400,600);
		f.setSize(200, 100);
		f.setLocationRelativeTo(null);   //æ”÷–
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

