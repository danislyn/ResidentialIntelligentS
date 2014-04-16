package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import beans.Message;

import server.Server;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel menuPanel;
	private JPanel mainPanel;
	
	private HouseOnlinePanel houseOnlinePanel;
	private AlarmMonitorPanel alarmMonitorPanel;
	private PropertyManagePanel propertyManagePanel;
	private AnnouncementManagePanel announcementManagePanel;
	
	private Server server;
	

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		this.setTitle("智能小区监控");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 850, 580);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		menuPanel = new JPanel();
		menuPanel.setBounds(10, 10, 150, 522);
		contentPane.add(menuPanel);
		menuPanel.setLayout(null);
		
		JButton houseOnlineBtn = new JButton("\u5728\u7EBF\u4F4F\u6237");
		houseOnlineBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(houseOnlinePanel == null){
					houseOnlinePanel = new HouseOnlinePanel(getThis());
				}
				updateResultPane(houseOnlinePanel);
				houseOnlinePanel.updateTable();
			}
		});
		houseOnlineBtn.setFont(new Font("宋体", Font.PLAIN, 14));
		houseOnlineBtn.setBounds(10, 10, 120, 30);
		menuPanel.add(houseOnlineBtn);
		
		JButton alarmMonitorBtn = new JButton("\u62A5\u8B66\u67E5\u770B");
		alarmMonitorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateResultPane(alarmMonitorPanel);
			}
		});
		alarmMonitorBtn.setFont(new Font("宋体", Font.PLAIN, 14));
		alarmMonitorBtn.setBounds(10, 62, 120, 30);
		menuPanel.add(alarmMonitorBtn);
		
		JButton propertyManageBtn = new JButton("\u7269\u4E1A\u6284\u8868");
		propertyManageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(propertyManagePanel == null){
					propertyManagePanel = new PropertyManagePanel();
				}
				updateResultPane(propertyManagePanel);
				propertyManagePanel.updateTable();
			}
		});
		propertyManageBtn.setFont(new Font("宋体", Font.PLAIN, 14));
		propertyManageBtn.setBounds(10, 114, 120, 30);
		menuPanel.add(propertyManageBtn);
		
		JButton announcementManageBtn = new JButton("\u901A\u77E5\u7EF4\u62A4");
		announcementManageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(announcementManagePanel == null){
					announcementManagePanel = new AnnouncementManagePanel();
				}
				updateResultPane(announcementManagePanel);
				announcementManagePanel.updateContent();
			}
		});
		announcementManageBtn.setFont(new Font("宋体", Font.PLAIN, 14));
		announcementManageBtn.setBounds(10, 166, 120, 30);
		menuPanel.add(announcementManageBtn);
		
		mainPanel = new JPanel();
		mainPanel.setBounds(170, 10, 654, 522);
		contentPane.add(mainPanel);
		mainPanel.setLayout(null);
		
		startServer();
		customInit();
	}
	
	private void startServer(){
		server = new Server(this);
		server.startService();
	}
	
	private void customInit(){
		//因为有消息推送，所以必须事先初始化
		alarmMonitorPanel = new AlarmMonitorPanel(this);
	}
	
	private MainFrame getThis(){
		return this;
	}
	
	
	private void updateResultPane(JPanel panel){
		panel.setBounds(0, 0, 770, 450);
		panel.setVisible(true);
		mainPanel.removeAll();
		mainPanel.add(panel, null);
		mainPanel.updateUI();
		repaint();
	}
	
	
	//==============================================
	public void pushAlarmMessage(Message msg){
		alarmMonitorPanel.refreshMessageList(msg);
		updateResultPane(alarmMonitorPanel);
	}
	
	public void pushChatMessage(Message msg){
		server.pushChatMessage(msg);
	}
	
	public void startChat(String username){
		server.startP2P(username);
	}
	
	
	//==============================================
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
