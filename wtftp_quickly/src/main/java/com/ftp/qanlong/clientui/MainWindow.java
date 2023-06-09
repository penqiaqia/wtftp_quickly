package com.ftp.qanlong.clientui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.ftp.qanlong.control.Control;
import com.ftp.qanlong.entity.BrokerInfo;
import com.ftp.qanlong.utils.Util;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {
	private Font fonts = new Font("宋体", Font.BOLD, 20);
	private Font fonth = new Font("黑体", Font.BOLD, 20);
	private Dimension windowSize = new Dimension(800, 600);
	private JButton btnNewButton = new JButton("一键更新文件");
	private JButton btnNewButton_1 = new JButton("邮件模版");
	private JButton btnNewButton_2 = new JButton("添加券商信息");
	private JButton btnNewButton_4 = new JButton("一键发布");
	private List<JCheckBox> boxes = new ArrayList<JCheckBox>();
	private JButton btnNewButton_3 = new JButton("删除券商");
	private JPanel panel_1 = new JPanel();
	private JFileChooser chooser = new JFileChooser();
	private Control ctr;
	private List<BrokerInfo> pis;
//	private LinkedList<BrokerPanel> listpane = new LinkedList<BrokerPanel>();

	public MainWindow(Control ctr) {
		this.ctr = ctr;
		init();
	}

	public List<JCheckBox> getBoxes() {
		return boxes;
	}

	public JButton getBtnNewButton() {
		return btnNewButton;
	}

	public JButton getBtnNewButton_3() {
		return btnNewButton_3;
	}

	private void init() {
//		Rectangle rect = Util.getwindowDimension();
//		setSize(rect.width, rect.height);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("委托组自动打包发布系统");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		});

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu_1 = new JMenu("管理");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmFtp = new JMenuItem("FTP管理");
		mnNewMenu_1.add(mntmFtp);
		mntmFtp.addActionListener(l -> {
			ctr.setFTP();
		});

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, this.getWidth(), this.getHeight() - Util.getButtomHight(this));
		panel.setLayout(null);
//		getContentPane().setLayout(null);
		getContentPane().add(panel, BorderLayout.CENTER);

		JPanel upBtnPanel = new JPanel();
		upBtnPanel.setBounds(0, 0, panel.getWidth(), (int) (panel.getHeight() * 0.08));
		panel.add(upBtnPanel);
		upBtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(e -> {
			int selected = 0;
			for (int i = 0; i < boxes.size(); i++) {
				if (boxes.get(i).isSelected()) {
					selected++;
				}
			}
			if (selected == 0) {
				JOptionPane.showMessageDialog(this, "请选择！");
				return;
			}
			ctr.showChooser();

		});

		upBtnPanel.add(btnNewButton);

		upBtnPanel.add(btnNewButton_1);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctr.addBroker();
			}
		});

		upBtnPanel.add(btnNewButton_2);

		btnNewButton_3.setEnabled(false);
		upBtnPanel.add(btnNewButton_3);
		btnNewButton_3.addActionListener(e -> {
			int selected = 0;
			int result = JOptionPane.showConfirmDialog(this, "确定删除吗？");
			if (result == 0) {
				for (int i = 0; i < boxes.size(); i++) {
					if (boxes.get(i).isSelected()) {
						selected++;
						ctr.dropFTPByName(pis.remove(i));
						boxes.remove(i);
						panel_1.remove(i);
						i--;
					}
				}
				if (selected == 0) {
					JOptionPane.showMessageDialog(this, "请选择！");
				} else {
					panel_1.updateUI();
				}

			}
		});

		JPanel downBtnPanel = new JPanel();
		downBtnPanel.setBounds(0, (int) (panel.getHeight() * 0.99), panel.getWidth(), (int) (panel.getHeight() * 0.1));
		panel.add(downBtnPanel);

		downBtnPanel.add(btnNewButton_4);

		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, (int) (panel.getHeight() * 0.08), panel.getWidth(), (int) (panel.getHeight() * 0.9));
		panel.add(scrollPane);

		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(0, 5, 1, 1));
		updateBrokerPanel();

//		String[] files = null;
//		try {
//			if (ctr.loginFtp("222.71.242.179 ", 21, "yfzxwr", "6n+Zxw2Y-m")) {
//				files = ctr.getFileList("");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		DefaultMutableTreeNode top = new DefaultMutableTreeNode("top");
//		for (String str : files) {
//			top.add(new DefaultMutableTreeNode(str));
//		}

	}

//该方法实现需要修改，此处主要展示券商信息
	public void updateBrokerPanel() {
		panel_1.removeAll();
		boxes.clear();
		pis = ctr.getpis();
		for (int i = 0; i < pis.size(); i++) {
			BrokerInfo pi = pis.get(i);
			JCheckBox box = new JCheckBox();
			box.setText(pi.getName());
			box.setToolTipText(pi.getName());
			boxes.add(box);
		}
		for (int i = 0; i < boxes.size(); i++) {
			panel_1.add(boxes.get(i), i);
		}
		panel_1.updateUI();

	}

//	public static void main(String[] args) {
//		MainWindow mw = new MainWindow(new Control());
//		mw.setVisible(true);
//	}
}
