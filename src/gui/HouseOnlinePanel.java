package gui;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

import db.operation.UserOperation;

import beans.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HouseOnlinePanel extends JPanel {
	private MainFrame mainFrame;
	
	private JTable contentTable;
	
	private UserModel tableModel;
	private List<User> userCache = new ArrayList<User>();

	/**
	 * Create the panel.
	 */
	public HouseOnlinePanel(MainFrame parent) {
		mainFrame = parent;
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 469, 500);
		add(scrollPane);
		
		contentTable = new JTable();
		contentTable.setFont(new Font("宋体", Font.PLAIN, 14));
		contentTable.setRowHeight(24);
		scrollPane.setViewportView(contentTable);
		
		JButton button = new JButton("\u53D1\u6D88\u606F");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User user = userCache.get(contentTable.getSelectedRow());
				mainFrame.startChat(user.username);
			}
		});
		button.setFont(new Font("宋体", Font.PLAIN, 14));
		button.setBounds(489, 10, 120, 30);
		add(button);

	}
	
	public void updateTable(){
		userCache = UserOperation.getOnlineUsers();
		
		if(tableModel == null){
			tableModel = new UserModel(userCache);
		}
		else{
			tableModel.setData(userCache);
		}
		
		contentTable.setModel(tableModel);
		contentTable.updateUI();
		repaint();
	}
	
	
	
	//======================================================
	class UserModel extends AbstractTableModel{
		String[] columns = {"房号", "别名", "状态", "最近登录时间"};
		List<User> data = new ArrayList<User>();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		public UserModel(List<User> data){
			this.data = data;
		}
		
		public void setData(List<User> data){
			this.data = data;
		}
		
		@Override
	    public String getColumnName(int column) {
	        return columns[column];
	    }
		
		@Override
		public int getColumnCount() {
			return columns.length;
		}
		
		@Override
		public int getRowCount() {
			if(data == null){
				return 0;
			}
			return data.size();
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			User user = data.get(rowIndex);
			String val = "";
			
			switch (columnIndex) {
			case 0:
				val = user.username;
				break;
			case 1:
				val = "房号" + user.resident;
				break;
			case 2:
				val = user.online == 1 ? "在线" : "离线";
				break;
			case 3:
				val = sdf.format(user.lastLogin);
				break;
			}
			return val;
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
	        return false;
	    }
	}
	
}
