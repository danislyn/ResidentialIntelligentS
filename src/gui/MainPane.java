package gui;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import beans.Message;

import server.Server;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class MainPane extends JPanel {
	
	private MainFrame mainFrame;
	private Server server;
	
	private JList notificationList;
	private JList messageJList;
	private Vector<String> messageVector;
	
	private JScrollPane contentPane;
	
	/**
	 * Create the panel.
	 */
	public MainPane(MainFrame parent) {
		mainFrame = parent;
		setLayout(null);
		
		JPanel menuPanel = new JPanel();
		menuPanel.setBounds(10, 10, 113, 430);
		add(menuPanel);
		menuPanel.setLayout(null);
		
		JButton onlineQueryBtn = new JButton("\u5728\u7EBF\u6237\u4E3B");
		onlineQueryBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			}
		});
		onlineQueryBtn.setBounds(10, 10, 93, 30);
		menuPanel.add(onlineQueryBtn);
		
		JButton messageQueueBtn = new JButton("\u6237\u4E3B\u6D88\u606F");
		messageQueueBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			}
		});
		messageQueueBtn.setBounds(10, 60, 93, 30);
		menuPanel.add(messageQueueBtn);
		
		contentPane = new JScrollPane();
		contentPane.setBounds(133, 10, 500, 430);
		add(contentPane);
		contentPane.setLayout(null);
		
		notificationList = new JList();
		notificationList.setBounds(643, 10, 147, 430);
		add(notificationList);

		customInit();
	}
	
	private void customInit(){
		server = new Server(this);
		server.startService();
	}
	
	
	
	
	//================================
	//ÓÃÓÚServiceHandlerË¢ÐÂ
	public void refreshMessageList(Message newMsg){
		System.out.println(newMsg.toString());
		
		if(messageJList == null){
			messageJList = new JList();
		}
		if(messageVector == null){
			messageVector = new Vector<String>();
		}
		
		messageVector.add(newMsg.toString());
		System.out.println(messageVector.size());
		
		messageJList.setListData(messageVector);
		
		notificationList.setListData(messageVector);
		
		contentPane.setViewportView(messageJList);
		
		System.out.println("list model " + messageJList.getModel().getSize());
		this.updateUI();
		this.repaint();
	}
}
