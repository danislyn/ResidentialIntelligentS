package gui;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import beans.Message;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AlarmMonitorPanel extends JPanel {
	
	private MainFrame mainFrame;
	
	private JList alarmList;
	private List<Message> messageList = new ArrayList<Message>();
	private Vector<String> messageVector = new Vector<String>();

	/**
	 * Create the panel.
	 */
	public AlarmMonitorPanel(MainFrame parent) {
		mainFrame = parent;
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 469, 500);
		add(scrollPane);
		
		alarmList = new JList();
		alarmList.setFont(new Font("宋体", Font.PLAIN, 14));
		scrollPane.setViewportView(alarmList);
		
		JButton replyBtn = new JButton("\u56DE\u590D");
		replyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Message msg = messageList.get(alarmList.getSelectedIndex());
				
			}
		});
		replyBtn.setFont(new Font("宋体", Font.PLAIN, 14));
		replyBtn.setBounds(489, 10, 120, 30);
		add(replyBtn);

	}
	
	
	//================================
	//用于ServiceHandler刷新
	public void refreshMessageList(Message newMsg){
		System.out.println(newMsg.toString());
		messageList.add(newMsg);
		messageVector.add(newMsg.toString());
		
		alarmList.setListData(messageVector);
		this.updateUI();
		repaint();
	}
	
}
