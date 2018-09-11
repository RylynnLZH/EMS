package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import bean.Department;
import bean.Employee;

/**
 * 部门Dao
 * 
 * @author Rylynn
 *
 */
public class DepartmentDao {

	
	/**
	 * 查找所有部门所有信息
	 * 
	 * @return 返回一个list
	 */
	public List<Department> search() {

		List<Department> deps = new ArrayList<>();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			String sql = "select * from department";
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmp_count(rs.getInt("emp_count"));
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
	 * 根据id查出部门所有信息
	 * 
	 * @param id
	 *            部门id
	 * @return 返回一个部门对象
	 */
	public Department search(int id) {

		Connection conn = null;
		Department dep = new Department();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			String sql = "select * from department where id=" + id;
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmp_count(rs.getInt("emp_count"));

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

		return dep;
	}

	
	/**
	 * 根据条件查找部门
	 * 
	 * @param dep
	 *            传入部门对象
	 * @return 返回查找到的部门信息list
	 */
	public List<Department> search(Department dep) {

		List<Department> deps = new ArrayList<>();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			String sql = "select * from department where 1=1 ";

			if (!"0".equals(dep.getName())) {
				sql += " and name= '" + dep.getName() + "'";
			}

			if (dep.getEmp_count() != -1) {
				sql += " and emp_count = " + dep.getEmp_count();
			}

			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Department de = new Department();
				de.setId(rs.getInt("id"));
				de.setName(rs.getString("name"));
				de.setEmp_count(rs.getInt("emp_count"));
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
	 * 根据信息查找并且分页显示
	 * 
	 * @param dep
	 *            查找条件
	 * @param nowpage
	 *            查找起始
	 * @param pagesize
	 *            查找数量
	 * @return 返回查找到的部门信息list
	 */
	public List<Department> search(Department dep, int nowpage, int pagesize) {

		List<Department> deps = new ArrayList<>();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			String sql = "select * from department where 1=1 ";

			if (dep.getName() != null && !"0".equals(dep.getName())) {
				sql += " and name= '" + dep.getName() + "'";
			}

			if (dep.getName() != null && dep.getEmp_count() != -1) {
				sql += " and emp_count = " + dep.getEmp_count();
			}
			sql += " limit " + nowpage + "," + pagesize;
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Department de = new Department();
				de.setId(rs.getInt("id"));
				de.setName(rs.getString("name"));
				de.setEmp_count(rs.getInt("emp_count"));
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
	 * 增加部门
	 * 
	 * @param dep
	 *            要增加的部门
	 */
	public void addDepartment(Department dep) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			String sql = "insert into Department(name) values('" + dep.getName() + "')";
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
	 * 批量删除部门
	 * 
	 * @param ids
	 *            想要删除部门id的字符串
	 */
	public void del(String ids) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			int rs = stat.executeUpdate("delete from department where id in(" + ids + ")");

			// 删除部门的同时将员工中在此部门的d_id设为null
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
	 * 批量更新部门信息
	 * 
	 * @param deps
	 *            想要更改的所有部门的集合
	 */
	public void update(List<Department> deps) {
		List<Department> list = new ArrayList();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			for (Department dep : deps) {
				int rs = stat.executeUpdate(
						"update department set name = '" + dep.getName() + "' where id=" + dep.getId() + "");
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