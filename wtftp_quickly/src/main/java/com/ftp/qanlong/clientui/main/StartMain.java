package com.ftp.qanlong.clientui.main;

import com.ftp.qanlong.clientui.BrokerFrame;
import com.ftp.qanlong.clientui.FTPSetting;
import com.ftp.qanlong.clientui.FileChooserUI;
import com.ftp.qanlong.clientui.Login;
import com.ftp.qanlong.clientui.MainWindow;
import com.ftp.qanlong.control.Control;
import com.ftp.qanlong.control.MyFTP;

public class StartMain {

	public static void main(String[] args) {
		Control ctr = new Control();
		Login login = new Login(ctr);
		MainWindow mw = new MainWindow(ctr);
		FTPSetting set = new FTPSetting(ctr);
		BrokerFrame bf = new BrokerFrame(ctr);
		MyFTP mf = new MyFTP(ctr);
		FileChooserUI fc=new FileChooserUI(ctr);
		ctr.setLogin(login);
		ctr.setmWindow(mw);
		ctr.setFs(set);
		ctr.setBf(bf);
		ctr.setMf(mf);
		ctr.setFc(fc);
		login.setVisible(true);

	}

}
