package com.ftp.qanlong.clientui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ftp.qanlong.control.Control;

public class FileChooserUI extends JDialog {
	private Font fonts = new Font("宋体", Font.BOLD, 20);
	private Font fonth = new Font("黑体", Font.BOLD, 20);
	private Dimension d = new Dimension(180, 30);
	private String[] arr = { "客户端", "通用脚本", "个性化脚本", "文档" };
	private JTextField textField;
	private JComboBox<String> combobox;
	private String fileType;
	private File sourceFile;
	private Control ctr;

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public File getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	public FileChooserUI(Control ctr) {
		this.ctr = ctr;
		init();
	}

	private void init() {
		setTitle("选择更新文件类型");
		setSize(new Dimension(400, 250));
		setModal(true);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("更新类型：");
		lblNewLabel.setBounds(60, 10, 65, 16);
		panel.add(lblNewLabel);
		combobox = new JComboBox<String>();
		combobox.setBounds(148, 5, 235, 39);
		for (int i = 0; i < arr.length; i++) {
			combobox.addItem(arr[i]);

		}
		panel.add(combobox);

		JFileChooser chooser = new JFileChooser();
//		chooser.setMultiSelectionEnabled(true);
		chooser.setDialogTitle("选择需要更新的源文件");

		JButton btnNewButton = new JButton("选择");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int value = chooser.showOpenDialog(FileChooserUI.this);
				if (JFileChooser.APPROVE_OPTION == value) {
					sourceFile = chooser.getSelectedFile();
					textField.setText(sourceFile.getPath());
				}
			}
		});
		btnNewButton.setBounds(50, 57, 75, 29);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("保存");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileType = arr[combobox.getSelectedIndex()];
				if (sourceFile==null||!sourceFile.exists()) {
					JOptionPane.showMessageDialog(FileChooserUI.this, "文件不存在或没有选择文件");
					return;
				}
				FileChooserUI.this.setVisible(false);
				ctr.updateFile();
				
			}
		});
		btnNewButton_1.setBounds(115, 156, 65, 29);
		panel.add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("取消");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileChooserUI.this.setVisible(false);
			}
		});
		btnNewButton_1_1.setBounds(229, 156, 65, 29);
		panel.add(btnNewButton_1_1);

		textField = new JTextField();
		textField.setBounds(148, 56, 235, 30);
		panel.add(textField);
		textField.setColumns(10);
	}
}
