package edu.yxs.sqldemo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.util.Random;

class MyPanel extends Panel{
	static String s1;
	public void paint(Graphics g){
		int width=70;
		int height=40;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,0, width, height);
		g.setColor(Color.green);            //设置上下文颜色
		g.drawRect(0,0,width-1,height-1); //绘制边框
		Random r=new Random();  //产生随机数
		//绘制背景圆点
		for(int i=0;i<200;i++){
			int x=r.nextInt(width)-2;
			int y=r.nextInt(height)-2;
			g.drawOval(x, y, 2, 2);
		}
		g.setFont(new Font("黑体",Font.BOLD,30));
		g.setColor(Color.yellow);
		char[] chars=("0123456789"+"abcdefghijklmnopqrstuvwxyz"+"HIJKLMNOPQRSTUVWXYZ").toCharArray();
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<4;i++){
			int pos=r.nextInt(chars.length);
			char c=chars[pos];
			sb.append(c+" ");
		}
		g.drawString(sb.toString(),20,30);
		s1=sb.toString();
		
		g.setColor(Color.red);
		for(int i=0;i<200;i++){
			int x=r.nextInt(width)-2;
			int y=r.nextInt(height)-2;
			g.drawOval(x, y, 2, 2);
		}
	}
	
	public static void main(String[] args) {
		new MyPanel();
	}
	
}
