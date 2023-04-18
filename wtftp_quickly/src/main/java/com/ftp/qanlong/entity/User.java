package com.ftp.qanlong.entity;

import java.util.Objects;

public class User {
	private String account;
	private String pwd;

	public User(String account, String pwd) {
		super();
		this.account = account;
		this.pwd = pwd;
	}

	public User() {
		super();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(account, other.account);
	}

	@Override
	public String toString() {
		return "account:" + account + ", pwd:" + pwd;
	}

}
