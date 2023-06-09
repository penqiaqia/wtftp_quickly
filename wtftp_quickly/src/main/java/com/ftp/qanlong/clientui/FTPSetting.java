package com.ftp.qanlong.clientui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.ftp.qanlong.control.Control;
import com.ftp.qanlong.entity.Ftp;

public class FTPSetting extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Font fonts = new Font("宋体", Font.BOLD, 15);
	private Font fonth = new Font("黑体", Font.BOLD, 20);
	private Dimension d = new Dimension(300, 200);
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private JLabel lblNewLabel_4 = new JLabel("");
	private JPanel panel = new JPanel();
	private Control ctr;

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JTextField getTextField_1() {
		return textField_1;
	}

	public void setTextField_1(JTextField textField_1) {
		this.textField_1 = textField_1;
	}

	public JTextField getTextField_2() {
		return textField_2;
	}

	public void setTextField_2(JTextField textField_2) {
		this.textField_2 = textField_2;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public FTPSetting(Control ctr) {
		this.ctr = ctr;
		init();
	}

	public Ftp getFTP() {
		String ftpip = textField.getText();
		String port = textField_1.getText();
		String user = textField_2.getText();
		String pwd = new String(passwordField.getPassword());
		return new Ftp(ftpip, Integer.parseInt(port), user, pwd);
	}

	public void setFtp() {
		Ftp f = ctr.getFtp();
		if (f != null) {
			textField.setText(f.getIp());
			textField_1.setText(Integer.toString(f.getPort()));
			textField_2.setText(f.getUsername());
			passwordField.setText(f.getPassword());
		}
	}

	public void reinit() {
		lblNewLabel_4.setText("");
		setFtp();
		panel.updateUI();

	}

	private void init() {
		setSize(new Dimension(320, 211));
		setTitle("FTP设置");
		setLocationRelativeTo(null);
		setModal(true);

		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("地址：");
		lblNewLabel.setFont(fonth);
		panel_5.add(lblNewLabel, BorderLayout.WEST);

		textField = new JTextField();
		panel_5.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		textField.setText("222.71.242.179 ");

		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_1 = new JLabel("端口：");
		lblNewLabel_1.setFont(fonth);
		panel_4.add(lblNewLabel_1, BorderLayout.WEST);

		textField_1 = new JTextField();
		textField_1.setToolTipText("一般默认为21，一般不用修改");
		textField_1.setText("21");
		panel_4.add(textField_1, BorderLayout.CENTER);
		textField_1.setColumns(10);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_2 = new JLabel("账号：");
		lblNewLabel_2.setFont(fonth);
		panel_3.add(lblNewLabel_2, BorderLayout.WEST);

		textField_2 = new JTextField();
		panel_3.add(textField_2, BorderLayout.CENTER);
		textField_2.setColumns(10);
		textField_2.setText("yfzxwr");

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_3 = new JLabel("密码：");
		lblNewLabel_3.setFont(fonth);
		panel_2.add(lblNewLabel_3, BorderLayout.WEST);

		passwordField = new JPasswordField("6n+Zxw2Y-m");
		panel_2.add(passwordField, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnNewButton_1 = new JButton("连接");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ip = textField.getText();
				int port = Integer.parseInt(textField_1.getText());
				String userName = textField_2.getText();
				String pwd = new String(passwordField.getPassword());
				try {
					if (ctr.conectFtp(ip, port, userName, pwd)) {
						lblNewLabel_4.setText("连接成功");
						lblNewLabel_4.setBackground(Color.GREEN);
					} else {
						lblNewLabel_4.setText("连接失败");
						lblNewLabel_4.setBackground(Color.RED);
					}
				} catch (SocketException e) {
					e.printStackTrace();
					lblNewLabel_4.setText("连接失败");
					lblNewLabel_4.setBackground(Color.RED);
				} catch (IOException e) {
					e.printStackTrace();
					lblNewLabel_4.setText("连接失败");
					lblNewLabel_4.setBackground(Color.RED);
				}
				panel.updateUI();

			}
		});
		panel_1.add(lblNewLabel_4);
		btnNewButton_1.setFont(fonts);
		panel_1.add(btnNewButton_1);

		JButton btnNewButton = new JButton("保存");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctr.saveftp(getFTP());
				reinit();
			}
		});
		btnNewButton.setFont(fonts);
		panel_1.add(btnNewButton);

		JButton btnNewButton_2 = new JButton("取消");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FTPSetting.this.setVisible(false);
				reinit();
			}
		});
		btnNewButton_2.setFont(fonts);
		panel_1.add(btnNewButton_2);
		setFtp();
	}

}
