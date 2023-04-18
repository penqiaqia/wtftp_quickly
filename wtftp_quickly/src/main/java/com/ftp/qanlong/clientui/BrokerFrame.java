package com.ftp.qanlong.clientui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ftp.qanlong.control.Control;

public class BrokerFrame extends JDialog {
	// 字体等公共信息，可以用一个文件管理
	private Font fonts = new Font("宋体", Font.BOLD, 15);
	private Font fonth = new Font("黑体", Font.BOLD, 20);
	private JTextField nameField;
	private JTextField ftpAddress;
	private JTextArea resvUser;
	private JTextArea copyUserTxtArea;
//	private JCheckBox chckbxNewCheckBox = new JCheckBox();
	private JPanel panel = new JPanel();
	private Dimension d = new Dimension(600, 220);
	private JScrollPane scroll = new JScrollPane();
	private Control ctr;

	public JTextField getNameField() {
		return nameField;
	}

	public void setNameField(JTextField nameField) {
		this.nameField = nameField;
	}

	public JTextField getFtpAddress() {
		return ftpAddress;
	}

	public void setFtpAddress(JTextField ftpAddress) {
		this.ftpAddress = ftpAddress;
	}

	public JTextArea getResvUser() {
		return resvUser;
	}

	public void setResvUser(JTextArea resvUser) {
		this.resvUser = resvUser;
	}

	public JTextArea getCopyUserTxtArea() {
		return copyUserTxtArea;
	}

	public void setCopyUserTxtArea(JTextArea copyUserTxtArea) {
		this.copyUserTxtArea = copyUserTxtArea;
	}

//	public JCheckBox getChckbxNewCheckBox() {
//		return chckbxNewCheckBox;
//	}
//
//	public void setChckbxNewCheckBox(JCheckBox chckbxNewCheckBox) {
//		this.chckbxNewCheckBox = chckbxNewCheckBox;
//	}

	public BrokerFrame(Control ctr) {
		this.ctr = ctr;
		init();
	}

	public void init() {
		setTitle("券商信息设置");
		setSize(new Dimension(600, 287));
		setModal(true);
		getContentPane().add(panel);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		panel.setLayout(null);
		panel.setBounds(2, 2, 601, 180);
		JLabel lblNewLabel = new JLabel("券 商 名：");
		lblNewLabel.setBounds(6, 6, 65, 16);
		panel.add(lblNewLabel);
		panel.add(scroll);

		nameField = new JTextField();
		setName(nameField.getText());
		nameField.setToolTipText("券商名，如果是营业部请加上营业部名称，本地路径中券商名请与此处保持一致");
		nameField.setBounds(83, 6, 509, 26);
		panel.add(nameField);
		nameField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("ftp 地 址：");
		lblNewLabel_1.setBounds(6, 34, 65, 16);
		panel.add(lblNewLabel_1);

		ftpAddress = new JTextField();
		ftpAddress.setToolTipText("ftp路径到日期目录即可；如/申万/场内Linux");
		ftpAddress.setBounds(83, 29, 509, 26);
		panel.add(ftpAddress);
		ftpAddress.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("邮件收件人：");
		lblNewLabel_2.setBounds(6, 62, 78, 16);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("邮件抄送人：");
		lblNewLabel_2_1.setBounds(6, 119, 78, 16);
		panel.add(lblNewLabel_2_1);

		scroll.setBounds(83, 117, 509, 81);
		copyUserTxtArea = new JTextArea();
		copyUserTxtArea.setLineWrap(true);
		copyUserTxtArea.setColumns(2);
		copyUserTxtArea.setToolTipText("规则同收件人");
		scroll.setViewportView(copyUserTxtArea);

		JButton btnNewButton = new JButton("保存");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				chckbxNewCheckBox.setText(nameField.getText());
//				chckbxNewCheckBox.setToolTipText(nameField.getText());
				String name = nameField.getText();
				String remote = ftpAddress.getText();
				String resv = resvUser.getText();
				String copy = copyUserTxtArea.getText();
				copy = copy.replace(" ", "");
				copy = copy.replace("\n", "");
				copy = copy.replace("	", "");
				try {
					ctr.savaBroker(name, remote, resv, copy);
					reinit();
					ctr.updateMainBroker();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnNewButton.setBounds(175, 210, 117, 29);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("取消");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BrokerFrame.this.dispose();
			}
		});
		btnNewButton_1.setBounds(314, 210, 117, 29);
		panel.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(83, 63, 511, 44);
		panel.add(scrollPane);
		
				resvUser = new JTextArea();
				resvUser.setLineWrap(true);
				scrollPane.setViewportView(resvUser);
				resvUser.setToolTipText("该券商发布时候的收件人，各个收件人之间用;隔开；如zsan@qq.com;sili@qq.com;");
				resvUser.setColumns(10);

//		chckbxNewCheckBox.setBounds(6, 143, 160, 31);
//		add(chckbxNewCheckBox);
	}

	public void reinit() {
		nameField.setText("");
		ftpAddress.setText("");
		resvUser.setText("");
		copyUserTxtArea.setText("");
		dispose();
	}
}
