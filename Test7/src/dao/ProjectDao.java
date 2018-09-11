package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import bean.Project;
import bean.Employee;

/**
 * 项目Dao
 * 
 * @author Rylynn
 *
 */
public class ProjectDao {

	/**
	 * 查询所有项目信息
	 * 
	 * @return
	 */
	public List<Project> search() {

		List<Project> deps = new ArrayList<>();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			String sql = "select * from project";
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Project dep = new Project();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));

				deps.add(dep);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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

		return deps;
	}

	/**
	 * 根据条件进行项目查找
	 * 
	 * @param dep
	 *            条件信息
	 * @return
	 */
	public List<Project> search(Project dep) {

		List<Project> deps = new ArrayList<>();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			String sql = "select * from project where 1=1 ";

			if (!"".equals(dep.getName())) {
				sql += " and name= '" + dep.getName() + "'";
			}

			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Project de = new Project();
				de.setId(rs.getInt("id"));
				de.setName(rs.getString("name"));

				deps.add(de);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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

		return deps;
	}

	/**
	 * 根据查询条件进行分页查找
	 * 
	 * @param dep
	 *            条件信息
	 * @param nowpage
	 *            开始
	 * @param pagesize
	 *            每页显示页数
	 * @return
	 */
	public List<Project> search(Project dep, int nowpage, int pagesize) {

		List<Project> deps = new ArrayList<>();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			String sql = "select * from project where 1=1 ";

			if (dep.getName() != null && !"".equals(dep.getName())) {
				sql += " and name= '" + dep.getName() + "'";
			}
			sql += " limit " + nowpage + "," + pagesize;

			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {

				Project de = new Project();
				de.setId(rs.getInt("id"));
				de.setName(rs.getString("name"));

				deps.add(de);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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

		return deps;
	}

	/**
	 * 增加项目
	 * 
	 * @param dep
	 *            项目信息
	 */
	public void addProject(Project dep) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			String sql = "insert into Project(name) values('" + dep.getName() + "')";
			int rs = stat.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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

	}

	/**
	 * 批量删除项目
	 * 
	 * @param ids
	 *            项目id字符串
	 */
	public void del(String ids) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			int rs = stat.executeUpdate("delete from project where id in(" + ids + ")");
			rs = stat.executeUpdate("update employee set d_id = null where d_id in(" + ids + ")");
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
	}

	/**
	 * 更新项目
	 * 
	 * @param deps
	 *            想要更新的项目的集合
	 */
	public void update(List<Project> deps) {
		List<Project> list = new ArrayList();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			for (Project dep : deps) {
				int rs = stat.executeUpdate(
						"update Project set name = '" + dep.getName() + "' where id=" + dep.getId() + "");
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
	}

}