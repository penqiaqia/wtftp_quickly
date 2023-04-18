package com.ftp.qanlong.clientui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.ftp.qanlong.control.Control;
import com.ftp.qanlong.entity.User;
import com.ftp.qanlong.utils.Util;
import com.google.zxing.WriterException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class Login extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Font fonts = new Font("宋体", Font.BOLD, 20);
	private Font fonth = new Font("黑体", Font.BOLD, 20);
	private Dimension d = new Dimension(180, 30);
	private static JPasswordField portTF = new JPasswordField();
	private JPanel panel = new JPanel(new BorderLayout());
	private JPanel northPanel = new JPanel(new GridLayout(2, 0));
	private JPanel paneH = new JPanel(new FlowLayout(1));
	private JPanel paneP = new JPanel(new FlowLayout(1));
	private JPanel paneBtn = new JPanel(new FlowLayout(1));
	private JCheckBox chckbxNewCheckBox = new JCheckBox("记住账号");
	private JComboBox<String> combox = new JComboBox<String>();
	private Control lc;

	public Login(Control lc) {
		this.lc = lc;
		this.setSize(350, 200);
		this.setTitle("委托组自动打包发布系统");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(connetJpanel());
		this.setLocationRelativeTo(null);
		this.setResizable(false);

	}

	public JPanel connetJpanel() {
		JLabel hostLabel = new JLabel("账号：");
		hostLabel.setFont(fonts);
		JLabel portLabel = new JLabel("密码：");
		portLabel.setFont(fonts);
		portTF.setFont(fonth);
		portTF.setPreferredSize(d);
		JButton connetBtn = new JButton("登录");
		connetBtn.setFont(fonts);
		connetBtn.addActionListener(e -> {
			String account = (String) this.combox.getSelectedItem();
			account = account.trim();
			if (account == null || account.equals("")) {
				JOptionPane.showMessageDialog(null, "账号不能为空");
				return;
			}
			String pwd = new String(portTF.getPassword());
			if (pwd == null || pwd.equals("")) {
				JOptionPane.showMessageDialog(null, "密码不能为空");
				return;
			}
			pwd = Util.codingMD5(pwd);
			User user = new User(account, pwd);
			if (lc.checkLogin(user)) {
				if (chckbxNewCheckBox.isSelected()) {
					lc.saveUser(user);
				}
				lc.loginOK(user);
			} else {
				JOptionPane.showMessageDialog(this, "账号或密码错误 ");
			}

		});
		JButton cancelBtn = new JButton("取消");
		cancelBtn.setFont(fonts);
		cancelBtn.addActionListener(e -> {
			System.exit(0);
		});
		paneH.add(hostLabel);
		initUser();
		paneH.add(combox);
		paneP.add(portLabel);
		paneP.add(portTF);
		paneBtn.add(connetBtn);
		paneBtn.add(cancelBtn);
		northPanel.add(paneH);

		northPanel.add(paneP);
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel cecnterPanel = new JPanel();
		cecnterPanel.add(chckbxNewCheckBox);

		JButton btn = new JButton("获取账号");
		btn.addActionListener(e -> {
			Image image = null;
			try {
				image = lc.getImg();
			} catch (WriterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JDialog jd = getJDialog(image);
			jd.setVisible(true);
		});
		cecnterPanel.add(btn);
		panel.add(cecnterPanel, BorderLayout.CENTER);
		panel.add(paneBtn, BorderLayout.SOUTH);
		return panel;
	}

	public void initUser() {
		combox.setEditable(true);
		combox.setFont(fonth);
		combox.setPreferredSize(d);
		List<User> users = lc.getUsers();
		if (users.size() != 0) {
			for (User user : users) {
				combox.addItem(user.getAccount());
			}
		}
	}

	public JDialog getJDialog(Image img) {
		JDialog dia = new JDialog(this, true);
		dia.setSize(40, 40);
		dia.setAlwaysOnTop(true);
		dia.setLocationRelativeTo(null);
		ImageIcon icon = new ImageIcon(img);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(icon);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				dia.dispose();
			}
		});
		panel.add(label, BorderLayout.CENTER);
		dia.getContentPane().add(panel);
		dia.pack();
		return dia;
	}

}
