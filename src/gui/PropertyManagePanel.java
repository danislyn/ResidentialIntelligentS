package gui;

import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

import db.operation.ResourceOperation;

import beans.Resource;

public class PropertyManagePanel extends JPanel {
	private JTable contentTable;
	
	private ResourceModel tableModel;
	private List<Resource> resourceCache = new ArrayList<Resource>();

	/**
	 * Create the panel.
	 */
	public PropertyManagePanel() {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 494, 500);
		add(scrollPane);
		
		contentTable = new JTable();
		contentTable.setFont(new Font("宋体", Font.PLAIN, 14));
		contentTable.setRowHeight(24);
		scrollPane.setViewportView(contentTable);

	}
	
	public void updateTable(){
		resourceCache = ResourceOperation.getResources();
		
		if(tableModel == null){
			tableModel = new ResourceModel(resourceCache);
		}
		else{
			tableModel.setData(resourceCache);
		}
		
		contentTable.setModel(tableModel);
		contentTable.updateUI();
		repaint();
	}
	
	
	
	//======================================================
	class ResourceModel extends AbstractTableModel{
		String[] columns = {"房号", "资源类型", "本期总量", "本期用量", "本期截止日期"};
		List<Resource> data = new ArrayList<Resource>();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		final DecimalFormat decimalFormat = new DecimalFormat(".00");
		
		public ResourceModel(List<Resource> data){
			this.data = data;
		}
		
		public void setData(List<Resource> data){
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
			Resource res = data.get(rowIndex);
			String val = "";
			
			switch (columnIndex) {
			case 0:
				val = res.houseId;
				break;
			case 1:
				val = res.type == 1 ? "水" : "电";
				break;
			case 2:
				val = String.valueOf(res.total);
				break;
			case 3:
				val = decimalFormat.format(res.consumed);
				break;
			case 4:
				val = sdf.format(res.endDate);
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
