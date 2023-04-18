package com.ftp.qanlong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.ftp.qanlong.entity.BrokerInfo;
import com.ftp.qanlong.entity.Ftp;
import com.ftp.qanlong.entity.User;
import com.ftp.qanlong.utils.Util;

public class SqliteDao {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String dbPath = "./src/main/resources/mydb.db";

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		Connection conn = Util.getSqlite3Conn(dbPath);
		String sql = "select account, pwd from user order by lasttime desc";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String account = rs.getString("account");
				System.out.println(account);
				User user = new User(account, "");
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
			Util.closeRS(rs);
		}

		return users;
	}

	public void saveUser(User user) {
		Connection conn = Util.getSqlite3Conn(dbPath);
		String sql = "replace into user(account,pwd,lasttime) values(?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getAccount());
			ps.setString(2, user.getPwd());
			String sDate = sdf.format(new Date());
			ps.setString(3, sDate);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
		}

	}

	public void insertFtp(Ftp f) {
		Connection conn = Util.getMySqlConn();
		String sql = "replace into ftp (ip,port,username,password) values(?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, f.getIp());
			ps.setInt(2, f.getPort());
			ps.setString(3, f.getUsername());
			ps.setString(4, f.getPassword());
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
		}

	}

	public void insertUser(User user) {
		Connection conn = Util.getMySqlConn();
		String sql = "insert into user(account,pwd) values(?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getAccount());
			ps.setString(2, user.getPwd());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
		}

	}

	public boolean existUser(User user) {
		Connection conn = Util.getMySqlConn();
		String sql = "select account,pwd from user where account=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String account = "";
		String pwd = "";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getAccount());
			rs = ps.executeQuery();
			while (rs.next()) {
				account = rs.getString("account");
				pwd = rs.getString("pwd");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
			Util.closeRS(rs);
		}
		return account.equals(user.getAccount()) && pwd.equals(user.getPwd()) ? true : false;
	}

	public List<Ftp> getFtps() {
		List<Ftp> ftps = new ArrayList<Ftp>();
		Connection conn = Util.getMySqlConn();
		String sql = "select ip, port,username,password from ftp";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String ip = rs.getString("ip");
				int port = rs.getInt("port");
				String username = rs.getString("username");
				String password = rs.getString("password");
				Ftp ftp = new Ftp(ip, port, username, password);
				ftps.add(ftp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
			Util.closeRS(rs);
		}
		return ftps;
	}

	public Ftp getFtp() {
		Connection conn = Util.getMySqlConn();
		String sql = "select ip, port,username,password from ftp";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Ftp ftp = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String ip = rs.getString("ip");
				int port = rs.getInt("port");
				String username = rs.getString("username");
				String password = rs.getString("password");
				ftp = new Ftp(ip, port, username, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
			Util.closeRS(rs);
		}
		return ftp;
	}

	public boolean existFtp(Ftp f) {
		Connection conn = Util.getMySqlConn();
		String sql = "select ip from ftp where ip=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ip = "";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, f.getIp());
			rs = ps.executeQuery();
			if (rs.next()) {
				ip = rs.getString("ip");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
			Util.closeRS(rs);
		}
		return ip != "" ? true : false;

	}

	public void updateFtp(Ftp f) {
		Connection conn = Util.getMySqlConn();
		String sql = "update ftp set port=?,username=?,password=? where ip=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, f.getPort());
			ps.setString(2, f.getUsername());
			ps.setString(3, f.getPassword());
			ps.setString(4, f.getIp());
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
		}

	}

	public List<BrokerInfo> getBrokerInfo() {
		Connection conn = Util.getMySqlConn();
		List<BrokerInfo> pis = new ArrayList<BrokerInfo>();
		String sql = "select id, name,local,remote,resuser,copyuser from brokerinfo";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String local = rs.getString("local");
				String remote = rs.getString("remote");
				String resuser = rs.getString("resuser");
				String copyuser = rs.getString("copyuser");
				BrokerInfo bi = new BrokerInfo(id, name, local, remote, resuser, copyuser);
				pis.add(bi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
			Util.closeRS(rs);
		}
		return pis;

	}

	public void saveBrokerInfo(BrokerInfo brokerInfos) {
		Connection conn = Util.getMySqlConn();
		String sql = "replace into brokerInfo(name,local,remote,resuser,copyuser) values(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, brokerInfos.getName());
			ps.setString(2, brokerInfos.getLocalPath());
			ps.setString(3, brokerInfos.getRemotePath());
			ps.setString(4, brokerInfos.getResUser());
			ps.setString(5, brokerInfos.getCopyUser());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
		}
	}

	public void deleteBroker(BrokerInfo bi) {
		Connection conn = Util.getMySqlConn();
		String sql = "delete from brokerInfo where id=?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bi.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeConn(conn);
			Util.closePS(ps);
		}

	}

//	public static void main(String[] args) {
//		SqliteDao dao = new SqliteDao();
//		dao.saveUser(new User("admin", "8"), "./src/main/resources/mydb.db");
//	}

}
