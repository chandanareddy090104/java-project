package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
class JdbcDemo{
	String url="jdbc:mysql://localhost:3306/gqt";
	String userName="root";
	String password="admin";
	Connection con=null;
	PreparedStatement pstmt=null;
	public void createTable()throws Exception {
		con=DriverManager.getConnection(url, userName, password);
		pstmt=con.prepareStatement("CREATE TABLE IF NOT EXISTS course(cid INT PRIMARY KEY,cname VARCHAR(20),cfees FLOAT,cduration INT)");
		pstmt.execute();
		System.out.println("table created successfully..");
		pstmt.close();
		con.close();
	}
	public void insertRecords() throws Exception{
		con=DriverManager.getConnection(url, userName, password);
		pstmt=con.prepareStatement("INSERT INTO course VALUES(?,?,?,?)");
		Scanner sc=new Scanner(System.in);
		System.out.print("enter no. of records:");
		int n=sc.nextInt();
		for(int i=1;i<=n;i++) {
			System.out.print("Enter cid:");
			int cid=sc.nextInt();
			System.out.print("Enter cname:");
			String cname=sc.next();
			System.out.print("Enter cfees:");
			float cfees=sc.nextFloat();
			System.out.print("Enter duration:");
			int cduration=sc.nextInt();
			pstmt.setInt(1, cid);
			pstmt.setString(2, cname);
			pstmt.setFloat(3, cfees);
			pstmt.setInt(4,cduration);
			pstmt.addBatch();
		}
		pstmt.executeBatch();
		System.out.println("All records inserted successfully..");
		pstmt.close();
		con.close();
	}
	public void retrieveTableRecords() throws Exception{
		con=DriverManager.getConnection(url, userName, password);
		pstmt=con.prepareStatement("SELECT * FROM course");
		ResultSet res=pstmt.executeQuery();
		while(res.next()) {
			System.out.print("-------------------------------\n");
			System.out.println(res.getInt(1)+"  | "+res.getString(2)+"  | "+res.getFloat(3)+"  | "+res.getInt(4));
		}
		System.out.println("-------------------------------\n");
		res.close();
		pstmt.close();
		con.close();
	}
	public void modifyRecords()throws Exception {
		con=DriverManager.getConnection(url, userName, password);
		Scanner sc=new Scanner(System.in);
		pstmt=con.prepareStatement("UPDATE course SET cname=? WHERE cid=?");
		System.out.print("Enter new cname to modify:");
		String cname=sc.next();
		System.out.print("Enter cid:");
		int cid=sc.nextInt();
		pstmt.setString(1,cname);
		pstmt.setInt(2, cid);
		pstmt.executeUpdate();
		System.out.println("cname modified successfully..");
		pstmt.close();
		con.close();
	}
	public void deleteRecords() throws Exception{
		con=DriverManager.getConnection(url, userName, password);
		pstmt=con.prepareStatement("DELETE FROM course WHERE cid=?");
		Scanner sc=new Scanner(System.in);
		System.out.print("enter cid");
		int cid=sc.nextInt();
		pstmt.setInt(1, cid);
		pstmt.executeUpdate();
		System.out.println("1 row deleted successfully..");
		pstmt.close();
		con.close();
	}
	public void exitFromRecords() {
		System.exit(0);
	}
}

public class jdbcAssignment {
	public static void main(String[] args)throws Exception{
		    Scanner sc=new Scanner(System.in);
			JdbcDemo jd=new JdbcDemo();	
			while(true) {
				System.out.println();
				System.out.print("1. Create Table\n2.Insert Records\n3. Retrieve Table Records\n4. Modify Records\n5. Delete Records\n6. Exit From Records\n\n");
				System.out.print("enter your choice:");
				int choice=sc.nextInt();
				switch(choice) {
				case 1: jd.createTable();break;
				case 2: jd.insertRecords();break;
				case 3: jd.retrieveTableRecords();break;
				case 4: jd.modifyRecords();break;
				case 5: jd.deleteRecords();break;
				case 6: jd.exitFromRecords();break;
				default: System.out.print("enter valid choice...\n");
				}
			}
	}
}
