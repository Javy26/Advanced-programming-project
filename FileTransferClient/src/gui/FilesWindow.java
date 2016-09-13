package gui;





import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.Client;

public class FilesWindow extends JFrame implements MouseListener{
	
	private static final Logger logger = LogManager.getLogger(FilesWindow.class);


	private ArrayList<String> files = new ArrayList<String>();
	private ArrayList<JLabel> names =  new ArrayList<JLabel>();
	private ArrayList<JPanel> Panelnames =  new ArrayList<JPanel>();
	private JPanel file;
	private Desktop d;
	private File newFile;
	private JLabel label;
	private Client client;
	private String folderName;
	
	public FilesWindow(String folder, Client c){
		super("File Explorer");
		//this.files = files;
		this.client = c;
		this.folderName = folder;
		this.setLayout(new GridLayout(names.size(),1));						//layout rows = number of file names
		this.initializeComponents();
		this.addComponentsToPanels();
		this.addPanelsToWIndows();
		this.setWindowProperties();
		//this.setListeners();
	}

	private void initializeComponents() {
		//file  = new JPanel(new FlowLayout());
		files = client.getFiles("/"+folderName);
		for(int i =0;i<files.size();i++){									//for all names
			label =  new JLabel(files.get(i));								//create a label			
			names.add(label);												//add label to label arrayList
		}
	}

	private void addComponentsToPanels() {
		for(JLabel label: names){										//for all labels
			//file.add(label);									
			file =  new JPanel(new GridLayout(1,1));					//create a new panel	
			file.addMouseListener(this);								//add mouselistner
			file.add(label);											//add the current label to the panel
			Panelnames.add(file);										//add to panel arrayList
		}
	}

	private void addPanelsToWIndows() {
		//this.add(file);
		for(JPanel panel: Panelnames)									//for all panels
			this.add(panel);											//add current panel to window
	}

	private void setWindowProperties() {
		this.setSize(400,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int count = 0;		
		JFileChooser chooser;
		for(JPanel panel: Panelnames){			
			if(e.getSource().equals(panel)){
				
				client.setTransferComplete(false);
				client.sendMessage("Send Single File");
				client.sendMessage("/"+folderName+"/"+files.get(count));
				
				client.showMessage(files.get(count));
				client.waitForTansfer();
				
				newFile = new File("C:/Documents and Settings/Daswin/Desktop/project_testing 2/"+files.get(count));
				logger.info(newFile.canExecute());
				logger.info(newFile.canRead());
				try {
					Desktop.getDesktop().open(newFile.getAbsoluteFile());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			count++;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
