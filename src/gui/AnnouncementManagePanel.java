package gui;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;

import db.operation.AnnouncementOperation;

import beans.Announcement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class AnnouncementManagePanel extends JPanel {
	
	private JTextPane textPane;

	/**
	 * Create the panel.
	 */
	public AnnouncementManagePanel() {
		setLayout(null);
		
		JButton button = new JButton("\u53D1\u5E03\u901A\u77E5");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AnnouncementDialog dialog = new AnnouncementDialog(getThis());
				dialog.setVisible(true);
			}
		});
		button.setFont(new Font("ËÎÌו", Font.PLAIN, 14));
		button.setBounds(520, 10, 120, 30);
		add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 500, 440);
		add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("ËÎÌו", Font.PLAIN, 14));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);

	}
	
	private AnnouncementManagePanel getThis(){
		return this;
	}
	
	public void updateContent(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String contentText = "";
		
		List<Announcement> list = AnnouncementOperation.getAnnouncements();
		for(Announcement a : list){
			contentText += "                    " + a.title + "\n";
			contentText += "                [" + sdf.format(a.createTime) + "]\n";
			contentText += "  " + a.content + "\n\n\n";
		}
		
		textPane.setText(contentText);
		textPane.updateUI();
		repaint();
	}
	
}
