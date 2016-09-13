/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author uncle
 */
public class DatabaseConnection {
    
   	private Connection con;
	private ResultSet rs;
	private Statement stmt;
	
	public DatabaseConnection(){
		String connString = "jdbc:mysql://localhost/document_reader?user=root&password=";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(connString);
			stmt = con.createStatement();
		}catch(SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	public void insert(String name, String pword, String llogin, String dcreated){
		String insertQuery = "INSERT INTO Users ( Name, Password, Last_Login, Date_Created) VALUES ('"+name+"',"
				+ "'"+pword+"',"+"'"+llogin+"', "+"'"+dcreated+"');";
               try {
                   stmt.executeUpdate(insertQuery);
               } catch (SQLException ex) {
                   Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
               }
	}
	
	/*public void update(){
		System.out.println("Please enter Id Number");
		Scanner input= new Scanner(System.in);
		int id=input.nextInt();
		String updateQuery = "UPDATE students SET lname = 'lol' WHERE id_number = "+id+";";
                stmt.executeUpdate(updateQuery);
	}*/
	
	/*public void delete(){
		String deleteQuery = "DELETE FROM students WHERE id_number = '1';";
                stmt.executeUpdate(deleteQuery);
	}*/
	
	/*public void read(){
		String selectQuery = "SELECT * FROM students;";
		try {
			rs = stmt.executeQuery(selectQuery);
			while(rs.next()) {
				System.out.println(rs.getString("fname") + ' ' + rs.getString("lname"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}*/
	
	public void close(){
		try {
			con.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		DatabaseConnection conc = new DatabaseConnection();
		conc.insert("Chevounne", "Getten", "2013-01-07","2015-01-06");
	//	conc.read();
		//conc.update();
		//conc.read();
		//conc.delete();
		//conc.read();
		//conc.close();
	}
}
