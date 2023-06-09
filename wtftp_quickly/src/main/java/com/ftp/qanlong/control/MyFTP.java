package com.ftp.qanlong.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.ftp.qanlong.entity.Ftp;
import com.ftp.qanlong.ftpfactory.FTPFactoryOverWriter;

public class MyFTP {
	private Control ctr;
	private static FTPClient ftp;

	public MyFTP(Control ctr) {
		super();
		this.ctr = ctr;
	}

	public boolean existDir(String remote) throws IOException {
		return ftp.changeWorkingDirectory(remote);
	}

	public boolean loginout() throws IOException {
		if (ftp.isConnected()) {
			ftp.logout();
			ftp.disconnect();
		}
		return FTPReply.isPositiveCompletion(ftp.getReplyCode());
	}

	public boolean createDir(String remote) throws IOException {
		String[] arr = remote.split("/");
		String temp = "/";
		for (String str : arr) {
			temp += str + "/";
			if (!existDir(str)) {
				ftp.makeDirectory(temp);
				ftp.changeWorkingDirectory(temp);
			}
		}

		return true;
	}

	/**
	 * 本地路径为完整路径，远程路径到文件夹结束
	 */
	public boolean uploadFtp(String local, String remote, String file) throws SocketException, IOException {
		boolean suc = false;
		long result = 0;
		long total = 0;
		byte[] b = new byte[1024 * 1024];
		if (relogin()) {
			if (!existDir(remote)) {
				createDir(remote);
			}
			if (!remote.endsWith("/")) {
				remote = remote + "/" + file;
			} else {
				remote = remote + file;
			}

			System.out.println(remote);
			File localfiel = new File(local);
			total = localfiel.length();
			FileInputStream in = new FileInputStream(localfiel);
			System.out.println(ftp.getReplyCode());
			OutputStream out = ftp.storeFileStream(remote);
			int a;
			while ((a = in.read(b)) != -1) {
				out.write(b, 0, a);
				result += a;
			}
			out.flush();
			out.close();
			in.close();
			suc = ftp.completePendingCommand();
		}
		System.out.println("result:" + result + "==total:" + total);
		return suc;
	}

	public boolean relogin() throws SocketException, IOException {
		boolean suc = true;
		if (!ftp.isConnected()) {
			Ftp f = ctr.getFtp();
			suc = loginFtp(f.getIp(), f.getPort(), f.getUsername(), f.getPassword());
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		}
		return suc;
	}

	public boolean download(String local, String remote) throws SocketException, IOException {
		boolean suc = false;
		byte[] b = new byte[1024 * 2048];
		if (relogin()) {
			OutputStream out = new FileOutputStream(local);
			InputStream in = ftp.retrieveFileStream(remote);
			int a;
			while ((a = in.read(b)) != -1) {
				out.write(b, 0, a);
			}
			out.flush();
			out.close();
			in.close();
			suc = ftp.completePendingCommand();
		}
		return suc;
	}

	public boolean loginFtp(String ip, int port, String account, String pwd) throws SocketException, IOException {
		ftp = new FTPClient();
		ftp.setParserFactory(new FTPFactoryOverWriter());
		ftp.setControlEncoding("gbk");
		ftp.setConnectTimeout(5 * 1000);
		ftp.connect(ip, port);
		int reply = ftp.getReplyCode();
		boolean suc = ftp.login(account, pwd);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		if (!FTPReply.isPositiveCompletion(reply)) {
			System.out.println(reply);
			ftp.disconnect();
			return false;
		}
		ftp.enterLocalPassiveMode();
		ftp.setSoTimeout(15000);
		return suc;
	}

	public String[] getFileList(String pathName) throws IOException {
		FTPFile[] ftpFile = ftp.listFiles();
		System.out.println(ftpFile.length);
		String[] file = new String[ftpFile.length];
		for (int i = 0; i < file.length; i++) {
			file[i] = ftpFile[i].getName();
			System.out.println(file[i]);
		}
		ftp.logout();
		return file;

	}

	public static void main(String[] args) throws SocketException, IOException {
		MyFTP fc = new MyFTP(new Control());
		boolean suc = fc.loginFtp("222.71.242.179 ", 21, "yfzxwr", "6n+Zxw2Y-m");
//		System.out.println(fc.getFileList(""));
		fc.download("/Users/panjiexian/Desktop/555.jpg", "/54/465/123/444.jpg");

	}

}
