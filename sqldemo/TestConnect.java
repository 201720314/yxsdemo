package edu.yxs.sqldemo;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TestConnect {
	/*public static void main(String[] args) {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ";
		String userName = "sa";
		String userPwd = "123";
		String str="insert into ѧ���� values('1610421004','����','��',21,'20160002','1997')";
		try {
			Connection conn=DriverManager.getConnection(dbURL,userName,userPwd);
			Statement stmt=conn.createStatement();
			System.out.println("connect sqlserver success!");
			stmt.executeUpdate(str);
			System.out.println("����ɹ�");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ";
		String userName = "sa";
		String userPwd = "123";
		try {
			//Class.forName(driverName);
			Connection conn=DriverManager.getConnection(dbURL,userName,userPwd);
			Statement stmt=conn.createStatement();
			System.out.println("connect sqlserver success!");
			//��Ҫִ�е����
			String sql="select * from ��ʦ��";
			ResultSet rs=stmt.executeQuery(sql);
			//System.out.println("����Ա��"+"\t"+"��¼����");
			//System.out.println("cno"+"\t"+"cname"+"\t"+"cpno"+"\t"+"ccredit");
			while(rs.next()) {
				//System.out.print(rs.getInt(1)+"\t");
				System.out.print(rs.getString(1)+"\t");
				System.out.print(rs.getString(2)+"\t");
				//System.out.println(rs.getInt(4)+"\t");
				/*System.out.print(rs.getInt("cno")+"\t");
				System.out.print(rs.getString("cname")+"\t");
				System.out.print(rs.getString("cpno")+"\t");
				System.out.println(rs.getInt("ccredit")+"\t");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			   e.printStackTrace();
			   System.out.print("connect failed");
		}
	}*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=����ϵͳ";
		String userName = "sa";
		String userPwd = "123";
		try {
			//Class.forName(driverName);
			Connection conn=DriverManager.getConnection(dbURL,userName,userPwd);
			Statement stmt=conn.createStatement();
			System.out.println("connect sqlserver success!");
			//��Ҫִ�е����
			String sql="select * from �γ̱�";
			ResultSet rs=stmt.executeQuery(sql);
			//System.out.println("����Ա��"+"\t"+"��¼����");
			//System.out.println("cno"+"\t"+"cname"+"\t"+"cpno"+"\t"+"ccredit");
			while(rs.next()) {
				//System.out.print(rs.getInt(1)+"\t");
				System.out.print(rs.getString(1)+"\t");
				System.out.print(rs.getString(2)+"\t");
				//System.out.println(rs.getInt(4)+"\t");
				/*System.out.print(rs.getInt("cno")+"\t");
				System.out.print(rs.getString("cname")+"\t");
				System.out.print(rs.getString("cpno")+"\t");
				System.out.println(rs.getInt("ccredit")+"\t");*/
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			   e.printStackTrace();
			   System.out.print("connect failed");
		}
	}
}

/*public void display() {
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
}*/

