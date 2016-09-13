package server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	  public ServerSocket serverSock;	  
	  public Socket sock;
	  
	  public Server(){
		  this.createConnection();
		  this.waitForUsers();		    
	  }
	  private void waitForUsers() {
		 String message = "Welcome to chat Service";
		 showMessage(message);
		 message = "waiting for users to connect";
		 showMessage(message);
		
		try {
			while(true){
				
				showMessage("Waiting...");
				sock = serverSock.accept();
				new Thread(new Threaded(sock)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	  
	  private void createConnection() {
		
		try {
			showMessage("Establishing Port Connection");
			serverSock =  new ServerSocket(6879);
			showMessage("Connection port Extablished"+serverSock);
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	  public static void main (String [] args ) throws IOException {
		  new Server();
	  } 
	  	  
	  public void showMessage(String message){
			System.out.println("Server -"+message+"\n");
		}
}
