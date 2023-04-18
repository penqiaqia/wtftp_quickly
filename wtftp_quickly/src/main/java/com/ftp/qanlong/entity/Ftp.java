package com.ftp.qanlong.entity;

import java.util.Objects;

public class Ftp {
	private String ip;
	private static int port = 21;
	private String username;
	private String password;

	public Ftp(String ip, int port, String username, String password) {
		super();
		this.ip = ip;
		Ftp.port = port;
		this.username = username;
		this.password = password;
	}

	public Ftp(String ip, String username, String password) {
		this(ip, port, username, password);
	}

	public Ftp() {
		super();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ftp other = (Ftp) obj;
		return Objects.equals(ip, other.ip);
	}

	@Override
	public String toString() {
		return "ip:" + ip + ", port:" + port + ", username:" + username + ", password:" + password;
	}

}
