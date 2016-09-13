package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.Client;

public class MainWindow extends JFrame implements ActionListener{
	private JPanel headPnl, folder1Pnl, folder2Pnl, folder3Pnl, folder4Pnl, folder5Pnl;
	private ArrayList<JPanel> folders = new ArrayList<JPanel>();
	private JLabel imageLbl;
	private JButton folder1nameLbl,folder2nameLbl,folder3nameLbl,folder4nameLbl,folder5nameLbl;
	private ArrayList<JButton> folderNames = new ArrayList<JButton>();
	private ArrayList<String> files = new ArrayList<String>();
	private Client client;
	
	public MainWindow(Client c){
		super("File Explorer");
		//this.files = files;
		this.client = c;
		this.setLayout(new GridLayout(3,2));
		this.initializeComponents();
		this.addComponentsToPanels();
		this.addPanelsToWIndows();
		this.setWindowProperties();
		this.setListeners();
	}

	private void initializeComponents() {
		/*folders.add(folder1Pnl);folders.add(folder2Pnl);folders.add(folder3Pnl);
		folders.add(folder4Pnl);folders.add(folder5Pnl);
		for(JPanel panel:folders)
			panel = new JPanel(new GridLayout(1,2));*/
		
		folder1Pnl  = new JPanel(new GridLayout(1,2));
		folder1Pnl.setSize(100, 100);
		folder2Pnl  = new JPanel(new GridLayout(1,2));
		folder3Pnl  = new JPanel(new GridLayout(1,2));
		folder4Pnl  = new JPanel(new GridLayout(1,2));
		folder5Pnl  = new JPanel(new GridLayout(1,2));
		//folder1Pnl  = new JPanel(new GridLayout(1,2));
		
		ImageIcon icon = new ImageIcon("C:/Documents and Settings/Daswin/My Documents/Advanced Programming/FileTransferClient/Lib/folder_image2.png");
		imageLbl = new JLabel(icon);
		
		folder1nameLbl = new JButton("Finance");
		folder1nameLbl.setIcon(icon);
		folder2nameLbl = new JButton("Human Resources");
		folder3nameLbl = new JButton("Athletics");
		folder4nameLbl = new JButton("Health");
		folder5nameLbl = new JButton("Auxilary");
		
		folderNames.add(folder1nameLbl);
		folderNames.add(folder2nameLbl);
		folderNames.add(folder3nameLbl);
		folderNames.add(folder4nameLbl);
		folderNames.add(folder5nameLbl);
	}

	private void addComponentsToPanels() {
		//folder1Pnl.add(imageLbl);
		folder1Pnl.add(folder1nameLbl);
		
		//folder2Pnl.add(imageLbl);
		folder2Pnl.add(folder2nameLbl);
		
		folder3Pnl.add(folder3nameLbl);
		//folder3Pnl.add(imageLbl);
		
		
		//folder4Pnl.add(imageLbl);
		folder4Pnl.add(folder4nameLbl);
		
		//folder5Pnl.add(imageLbl);
		folder5Pnl.add(folder5nameLbl);
		
	}

	private void addPanelsToWIndows() {
		for(JButton button: folderNames)
			this.add(button);		
	}

	private void setWindowProperties() {
		this.setSize(400,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void setListeners() {
		
		for(JButton button: folderNames)
			button.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(folder1nameLbl)){
			new FilesWindow(folder1nameLbl.getText(), client);
		}
		if(e.getSource().equals(folder2nameLbl)){
			new FilesWindow(folder2nameLbl.getText(), client);
		}
		if(e.getSource().equals(folder3nameLbl)){
			new FilesWindow(folder3nameLbl.getText(), client);
		}
		if(e.getSource().equals(folder4nameLbl)){
			new FilesWindow(folder4nameLbl.getText(), client);
		}
		if(e.getSource().equals(folder5nameLbl)){
			new FilesWindow(folder5nameLbl.getText(), client);
		}
	}
	
	/*public static void main(String[] args){
		new MainWindow();
	}*/
}
