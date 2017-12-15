package edu.yxs.sqldemo;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TestConnect {
	/*public static void main(String[] args) {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=教务系统";
		String userName = "sa";
		String userPwd = "123";
		String str="insert into 学生表 values('1610421004','王润','男',21,'20160002','1997')";
		try {
			Connection conn=DriverManager.getConnection(dbURL,userName,userPwd);
			Statement stmt=conn.createStatement();
			System.out.println("connect sqlserver success!");
			stmt.executeUpdate(str);
			System.out.println("插入成功");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=教务系统";
		String userName = "sa";
		String userPwd = "123";
		try {
			//Class.forName(driverName);
			Connection conn=DriverManager.getConnection(dbURL,userName,userPwd);
			Statement stmt=conn.createStatement();
			System.out.println("connect sqlserver success!");
			//需要执行的语句
			String sql="select * from 教师表";
			ResultSet rs=stmt.executeQuery(sql);
			//System.out.println("管理员号"+"\t"+"登录密码");
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
		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=教务系统";
		String userName = "sa";
		String userPwd = "123";
		try {
			//Class.forName(driverName);
			Connection conn=DriverManager.getConnection(dbURL,userName,userPwd);
			Statement stmt=conn.createStatement();
			System.out.println("connect sqlserver success!");
			//需要执行的语句
			String sql="select * from 课程表";
			ResultSet rs=stmt.executeQuery(sql);
			//System.out.println("管理员号"+"\t"+"登录密码");
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
	//定义一个数组来存储所有数据
	ArrayList list=new ArrayList();
	try {
		rs=stmt.executeQuery("select * from 课程表 ");
		while(rs.next()) {
			list.add(rs.getString("课程号"));
			list.add(rs.getString("课程名"));
			list.add(rs.getString("教师号"));
			i++;
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	arr=new Object[i][3];
	String[] listname= {"课程号","课程名","教师号"};
	//用二维数组来存储所有数据
	try {
		rs=stmt.executeQuery("select * from 课程表 order by 课程号");
		while(rs.next()) {
			arr[j][0]=rs.getString("课程号");
			arr[j][1]=rs.getString("课程名");
			arr[j][2]=rs.getString("教师号");
			j++;
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	tb=new JTable(arr,listname);
	jsp=new JScrollPane(tb);
	this.add(jsp);
}*/

