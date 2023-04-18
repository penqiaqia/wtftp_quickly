package com.ftp.qanlong.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * 
 * @author panjiexian 打包文件实体类
 *
 */
public class BrokerInfo {
	private int id;
	private String name;
	private String localPath;
	private String remotePath;
	private String resUser;
	private String copyUser;

	public BrokerInfo() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getResUser() {
		return resUser;
	}

	public void setResUser(String resUser) {
		this.resUser = resUser;
	}

	public String getCopyUser() {
		return copyUser;
	}

	public void setCopyUser(String copyUser) {
		this.copyUser = copyUser;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BrokerInfo other = (BrokerInfo) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "id:" + id + ", name:" + name + ", localPath:" + localPath + ", remotePath:" + remotePath + ", resUser:"
				+ resUser + ", copyUser:" + copyUser;
	}

	public BrokerInfo(String name, String localPath, String remotePath, String resUser, String copyUser) {
		super();
		this.name = name;
		this.localPath = localPath;
		this.remotePath = remotePath;
		this.resUser = resUser;
		this.copyUser = copyUser;
	}

	public BrokerInfo(int id, String name, String localPath, String remotePath, String resUser, String copyUser) {
		super();
		this.id = id;
		this.name = name;
		this.localPath = localPath;
		this.remotePath = remotePath;
		this.resUser = resUser;
		this.copyUser = copyUser;
	}

}
