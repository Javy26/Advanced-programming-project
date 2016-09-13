package Client;
import gui.MainWindow;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
	
	private ServerSocket serverSock;
	private Socket sock;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private InputStream fileInput = null;
	private OutputStream fileOutput; 
	private ArrayList<String> files = new ArrayList<String>();
	private boolean transferComplete;

	public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded
  
	public Client(){
		this.createConnections();
		this.createStreams();		
		this.waitForServer();	    											
	}
	
	private void createConnections(){
		
		try {
			sock =  new Socket(InetAddress.getLocalHost(),6879);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void waitForServer(){
		String message =" ";
		 System.out.println("Connecting...");
		 new MainWindow(this);
		 
		 try {
			do{
				 message=(String)input.readObject();
				 if(message.equals("Sending Files")){
					  receivingFiles();
				  }
				 
				 if(message.equals("Sending File")){
					 message = (String)input.readObject();
					 showMessage("File name: "+message);
					 getFile(message);
				 }
			 }while(message!= "END");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SocketException e){
			close();
			System.out.println("Server connection terminated");
		}catch(EOFException e){
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void close() {
		
		try {
			output.close();
			input.close();
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void createStreams() {
		
		try {
			this.output = new ObjectOutputStream(sock.getOutputStream());
			this.input = new ObjectInputStream(sock.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void receivingFiles() {
		String fileName = "";
		
		try {
			do{
				fileName = (String)input.readObject();					//get file names
				files.add(fileName);										//add name to arraylist of names			
			}while(!fileName.equals("end"));
			transferComplete = true;
			showMessage("Transfer Complete");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage(String message) {
		System.out.println(message);
	}

	public static void main (String [] args ) throws IOException {
		new Client();
	}
  
	public void getFile(String filepath){
	  int bytesRead;
	    int current = 0;
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;	    
	    String path = "C:/Documents and Settings/Daswin/Desktop/project_testing 2/";
	  
	  try {
		// receive file
		  byte [] mybytearray  = new byte [FILE_SIZE];
		  fileInput = sock.getInputStream();
		  fos = new FileOutputStream(path+filepath);
		  bos = new BufferedOutputStream(fos);
		  bytesRead = fileInput.read(mybytearray,0,mybytearray.length);
		  showMessage("bytesRead is: "+bytesRead);
		  current = bytesRead;
		  showMessage("current is: "+current);

		  /*do {
		     bytesRead =fileInput.read(mybytearray, current, (mybytearray.length-current));
		     showMessage("Current bytesRead is: "+bytesRead);
		     if(bytesRead >= 0) current += bytesRead;
		     showMessage("Current Current is: "+current);
		  } while(bytesRead > -1);*/

		  bos.write(mybytearray, 0 , bytesRead);
		  bos.flush();
		  showMessage("File " + filepath
		      + " downloaded (" + current + " bytes read)");
		  
		  transferComplete=true;
		  
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
          
  }
  
	public void sendMessage(String message){
	  
	  try {		
		  output.writeObject(message);
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
	
	public ArrayList<String> getFiles(String folderName){
		transferComplete = false;
		sendMessage("Find Files");
		sendMessage(folderName);		
		while(transferComplete==false){
			
		}
		return files;
	}

	public void waitForTansfer() {
		do{
			
		}while(transferComplete==false);
		showMessage("Transfer Complete");
	}

	public void setTransferComplete(boolean b) {
		transferComplete = b;
	}

}
