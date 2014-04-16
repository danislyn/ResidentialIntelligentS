package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import db.operation.AnnouncementOperation;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AnnouncementDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextPane textPane;
	
	private AnnouncementManagePanel parent;

	/**
	 * Create the dialog.
	 */
	public AnnouncementDialog(AnnouncementManagePanel current) {
		this.parent = current;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 450, 413);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("\u6807\u9898");
		label.setFont(new Font("ËÎÌו", Font.PLAIN, 14));
		label.setBounds(20, 15, 54, 30);
		contentPanel.add(label);
		
		textField = new JTextField();
		textField.setFont(new Font("ËÎÌו", Font.PLAIN, 14));
		textField.setBounds(82, 16, 330, 30);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 66, 392, 266);
		contentPanel.add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("ËÎÌו", Font.PLAIN, 14));
		scrollPane.setViewportView(textPane);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String title = textField.getText();
						String content = textPane.getText();
						AnnouncementOperation.newAnnouncement(title, content);
						
						parent.updateContent();
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
