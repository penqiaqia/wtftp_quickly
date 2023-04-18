package com.ftp.qanlong.control;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import javax.swing.JCheckBox;

import com.ftp.qanlong.clientui.BrokerFrame;
import com.ftp.qanlong.clientui.FTPSetting;
import com.ftp.qanlong.clientui.FileChooserUI;
import com.ftp.qanlong.clientui.Login;
import com.ftp.qanlong.clientui.MainWindow;
import com.ftp.qanlong.dao.SqliteDao;
import com.ftp.qanlong.entity.BrokerInfo;
import com.ftp.qanlong.entity.Ftp;
import com.ftp.qanlong.entity.User;
import com.ftp.qanlong.utils.Util;
import com.google.zxing.WriterException;

public class Control {
	private SqliteDao sqlite3dao = new SqliteDao();
//	private MysqlDao mysqldao = new MysqlDao();
	private Login login;
	private FTPSetting fs;
	private BrokerFrame bf;
	private MainWindow mWindow;
	private MyFTP mf;
	private FileChooserUI fc;

	public FileChooserUI getFc() {
		return fc;
	}

	public void setFc(FileChooserUI fc) {
		this.fc = fc;
	}

	public MyFTP getMf() {
		return mf;
	}

	public void setMf(MyFTP mf) {
		this.mf = mf;
	}

	public BrokerFrame getBf() {
		return bf;
	}

	public void setBf(BrokerFrame bf) {
		this.bf = bf;
	}

	public MainWindow getmWindow() {
		return mWindow;
	}

	public void setmWindow(MainWindow mWindow) {
		this.mWindow = mWindow;
	}

	public FTPSetting getFs() {
		return fs;
	}

	public void setFs(FTPSetting fs) {
		this.fs = fs;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public List<User> getUsers() {
		return sqlite3dao.getUsers();
	}

	public boolean checkLogin(User user) {
		return sqlite3dao.existUser(user)
				|| (user.getAccount().equals("admin") && user.getPwd().equals(Util.codingMD5("8")))
				|| (user.getAccount().equals("super") && user.getPwd().equals(Util.codingMD5("8")));
	}

	public void saveUser(User user) {
		sqlite3dao.saveUser(user);

	}

	public void loginOK(User user) {
		if (user.getAccount().equalsIgnoreCase("super")) {
			mWindow.getBtnNewButton_3().setEnabled(true);
			mWindow.getBtnNewButton().setEnabled(true);
		}
		login.setVisible(false);
		mWindow.setVisible(true);
	}

	public Image getImg() throws WriterException, IOException {
		String tip = "请你指导一下,谢谢!!";
		return Util.generateCodeImg(tip, 200, 200);
	}

	public List<BrokerInfo> getpis() {
		return sqlite3dao.getBrokerInfo();
	}

	public Ftp getFtp() {
		return sqlite3dao.getFtp();
	}

	public void addBroker() {
		bf.setVisible(true);

	}

	public void savaBroker(String name, String remote, String resv, String copy)
			throws FileNotFoundException, IOException {
		BrokerInfo bi = new BrokerInfo(name, Util.getProperty("local"), remote, resv, copy);
		sqlite3dao.saveBrokerInfo(bi);

	}

//	public static void main(String[] args) {
//		String str = "qwe@qq.com;123@qq.com;qwe@qq.com;123@qq.com";
//		String[] arr = str.split(";");
//		System.out.println(Arrays.toString(arr));
//	}

	public void setFTP() {
		fs.setVisible(true);

	}

	public boolean conectFtp(String ip, int port, String userName, String pwd) throws SocketException, IOException {
		boolean suc = mf.loginFtp(ip, port, userName, pwd);
		if (suc) {
			mf.loginout();
		}
		return suc;
	}

	public void saveftp(Ftp ftp) {
		sqlite3dao.insertFtp(ftp);
		fs.setVisible(false);

	}

	public void updateMainBroker() {
		mWindow.updateBrokerPanel();

	}

	public void dropFTPByName(BrokerInfo bi) {
		sqlite3dao.deleteBroker(bi);

	}

	public void updateFile() {
		File sourceFile = fc.getSourceFile();
		String fileName = sourceFile.getName();
		List<BrokerInfo> pis = sqlite3dao.getBrokerInfo();
		String type = fc.getFileType();
		List<JCheckBox> boxes = mWindow.getBoxes();
		for (int i = 0; i < boxes.size(); i++) {
			if (boxes.get(i).isSelected()) {
				if (type.equals("客户端")) {
					// TODO
					String target = pis.get(i).getLocalPath();
					target = target + "/" + "客户端/" + fileName;
					File targerFile = new File(target);
					try {
						Util.copy(sourceFile, targerFile);
					} catch (IOException e) {
						e.printStackTrace();
						System.err.println(" 请检查路径!!!");
					}
				} else if (type.equals("通用脚本")) {
					// TODO
				} else if (type.equals("个性化脚本")) {
					// TODO
				} else if (type.equals("文档")) {

				}
			}
		}

	}

	public void showChooser() {
		fc.setVisible(true);

	}

}
