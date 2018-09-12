package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.User;



/**
 * 用户Dao
 * 
 * @author Rylynn
 *
 */
public class UserDao {

	/**
	 * 查找登陆信息
	 * 
	 * @param username
	 *            用户name
	 * @param password
	 *            用户密码
	 * @return 是否有此信息
	 */
	public boolean search(User user) {
		boolean flag = false;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");

			
			
			String sql = "select * from user where username=? and password=?";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());

			ResultSet rs = pstat.executeQuery();
			
			if (rs.next()) {
				flag = true;
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public boolean search(String username) {
		boolean flag = false;
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");

			
			
			String sql = "select * from user where username=?";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, username);
			

			ResultSet rs = pstat.executeQuery();
			
			if (rs.next()) {
				flag = true;
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	
	public boolean add(User user) {
		boolean flag = false;
		Connection conn = null;
		int rs = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");

			
			
			String sql = "insert into user(username,password) values(?,?)";
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());

			rs = pstat.executeUpdate();
			
			

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs>0;
	}
}
