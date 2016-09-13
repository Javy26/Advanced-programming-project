package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Threaded implements Runnable{
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private OutputStream fileOutput;
	private InputStream fileInput;
	
	private final static String DEFAULTFILEPATH = "C:/Documents and Settings/Daswin/Desktop/project_testing";
	public final static int FILE_SIZE = 6022386; // file size temporary hard coded
    // should bigger than the file to be downloaded
	
	public Threaded(Socket connection){
		this.connection = connection;
		
	}

	@Override
	public void run() {
		String message = "";
		System.out.println("thread Started");
		
		try {
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			do{
				message = (String)input.readObject();
				
				if(message.equals("Find Files")){
					message = (String)input.readObject();	//get file and path from client
					populateFiles(message);					//send 
				}
				
				if(message.equals("Sending File Operation")){
					message = (String)input.readObject();			//catch file path on server
					showMessage("File Requested: "+message);		
					sendFile(message);
				}
				
				if(message.equals("Sending User File")){
					receiveUpload();
				}
				
				if(message.equals("Sending delete Request")){
					deleteFile();
				}
				
				
				output.writeObject("waiting..");
				output.flush();
			}while(!message.equals("END"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SocketException e){			
			System.out.println("Client connection terminated");
			this.close();
		}catch(EOFException e){
			close();
		}catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
	
	private void deleteFile() {
		
		try {
			String message = (String)input.readObject();													//get file path
			File file = new File(DEFAULTFILEPATH+message);													//create file instance
			file.delete();																					//delete File
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}																					
	}

	private void populateFiles(String message) {
		try {
			output.writeObject("Sending Files"); 									//signal beginning of file list
			showMessage("Sending Files: ");
			File filePath = new File(DEFAULTFILEPATH+message);						//set path
			File[] files = filePath.listFiles();									//get list of files
			for(File file:files){													//for all files in list
				output.writeObject(file.getName());									//send their names
				showMessage(file.getName());
				output.flush();														//flush stream
			}
			output.writeObject("end");												//signal end of file names
			showMessage("End Of Gui file list.");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void sendFile(String file){
		  FileInputStream fis = null;
		  BufferedInputStream bis = null;
		  OutputStream os = null;		  
		  //Socket sock = null;
		  
		 try {
			 output.writeObject("Sending File");
			 showMessage("Sending File: "+file);
			// File FilePath = new File("C:/Documents and Settings/Daswin/Desktop/project_testing/";
			  File myFile = new File (DEFAULTFILEPATH+file);
			  showMessage(myFile.getName());
			  output.writeObject(myFile.getName());
			  byte [] mybytearray  = new byte [(int)myFile.length()];
			  fis = new FileInputStream(myFile);
			  bis = new BufferedInputStream(fis);
			  bis.read(mybytearray,0,mybytearray.length);
			  fileOutput = connection.getOutputStream();
			  showMessage("Sending " + myFile.getName() + "(" + mybytearray.length + " bytes)");
			  fileOutput.write(mybytearray,0,mybytearray.length);
			  fileOutput.flush();
			  showMessage("Done.");
			  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	
	private void receiveUpload() {
		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;	    
		//String path = "C:/Documents and Settings/Daswin/Desktop/project_testing/";
		String userMessage = "",folder,fileName;		
		
		try {
			folder = (String)input.readObject();		//get folder name
			fileName = (String)input.readObject();		//get file name
			
			byte [] mybytearray  = new byte [FILE_SIZE];
			fileInput = connection.getInputStream();
			fos = new FileOutputStream(DEFAULTFILEPATH+folder+"/"+fileName);
			bos = new BufferedOutputStream(fos);
			bytesRead = fileInput.read(mybytearray,0,mybytearray.length);
			  
			bos.write(mybytearray, 0 , bytesRead);		 
			//bos.flush();
			bos.close();
			showMessage("File " + fileName
			   + " downloaded (" + bytesRead + " bytes read)");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage(String message){
		System.out.println("Server -"+message+"\n");
	}

}
