package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Department;
import bean.Employee;

/**
 * 员工Dao
 * 
 * @author Rylynn
 *
 */
public class EmployeeDao {

	/**
	 * 查询所有员工信息 包含部门信息
	 * 
	 * @return
	 */
	public List<Employee> search() {
		List<Employee> list = new ArrayList();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(
					"select e.*,d.name as d_name,emp_count from employee as e left join department as d on e.d_id=d.id");
			while (rs.next()) {
				Employee emp = new Employee();
				Department dep = new Department();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				emp.setImg(rs.getString("img"));
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				dep.setEmp_count(rs.getInt("emp_count"));
				emp.setDep(dep);
				list.add(emp);
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
		return list;
	}

	/**
	 * 分页查找所有员工信息
	 * 
	 * @param startIndex
	 *            开始页
	 * @param pageSize
	 *            每页显示数据 即每次查找条数
	 * @return
	 */
	public List<Employee> search(int startIndex, int pageSize) {
		List<Employee> list = new ArrayList();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(
					"select e.*,d.name as d_name,emp_count from employee as e left join department as d on e.d_id = d.id limit "
							+ startIndex + "," + pageSize + "");
			while (rs.next()) {
				Employee emp = new Employee();
				Department dep = new Department();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				emp.setImg(rs.getString("img"));
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				dep.setEmp_count(rs.getInt("emp_count"));
				emp.setDep(dep);
				list.add(emp);
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
		return list;
	}

	/**
	 * 根据条件查找
	 * 
	 * @param emp
	 *            条件信息
	 * @return 返回查找的list
	 */
	public List<Employee> search(Employee emp) {
		List<Employee> list = new ArrayList();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();

			String sql = "select e.*,d.name as d_name from employee as e left join department as  d on e.d_id = d.id where 1=1 ";

			if (emp.getName() != null && !emp.getName().equals("")) {
				sql += " and name= '" + emp.getName() + "'";
			}
			if (emp.getSex() != null && !emp.getSex().equals("请选择性别")) {
				sql += " and sex= '" + emp.getSex() + "'";
			}
			if (emp.getAge() != -1) {
				sql += " and age= " + emp.getAge();

			}
			if (emp.getD_id() != null && emp.getD_id() != 0) {
				sql += " and d_id=" + emp.getD_id();
			}

			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				Employee em = new Employee();
				em.setId(rs.getInt("id"));
				em.setName(rs.getString("name"));
				em.setSex(rs.getString("sex"));
				em.setAge(rs.getInt("age"));
				em.setImg(rs.getString("img"));
				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				em.setDep(dep);
				list.add(em);

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

		return list;
	}

	/**
	 * 根据条件分页查找
	 * 
	 * @param emp
	 *            条件信息
	 * @param startIndex
	 *            开始页
	 * @param pageSize
	 *            每页显示数量
	 * @return 返回查找的list
	 */
	public List<Employee> search(Employee emp, int startIndex, int pageSize) {
		List<Employee> searchlist = new ArrayList();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();

			String sql = "select e.*,d.name as d_name from employee as e left join department as d on e.d_id = d.id where 1=1 ";

			if (emp.getName() != null && !emp.getName().equals("")) {
				sql += " and name= '" + emp.getName() + "'";
			}
			if (emp.getSex() != null && !emp.getSex().equals("请选择性别")) {
				sql += " and sex= '" + emp.getSex() + "'";
			}
			if (emp.getAge() != -1) {
				sql += " and age= " + emp.getAge();

			}
			if (emp.getD_id() != null && emp.getD_id() != 0) {
				sql += " and d_id= " + emp.getD_id();
			}
			sql += " limit " + startIndex + "," + pageSize + "";

			
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Employee em = new Employee();
				Department dep = new Department();
				em.setId(rs.getInt("id"));
				em.setName(rs.getString("name"));
				em.setSex(rs.getString("sex"));
				em.setAge(rs.getInt("age"));
				em.setImg(rs.getString("img"));
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				em.setDep(dep);
				searchlist.add(em);
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

		return searchlist;

	}

	/**
	 * 根据员工id查找数据
	 * 
	 * @param ids
	 *            员工id字符串
	 * @return
	 */
	public List<Employee> find(String ids) {
		List<Employee> emps = new ArrayList();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(
					"select e.*,d.name as d_name from employee as e left join department as d on e.d_id = d.id where e.id in("
							+ ids + ")");
			while (rs.next()) {
				Employee emp = new Employee();
				Department dep = new Department();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));
				emp.setDep(dep);
				emps.add(emp);
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
		return emps;
	}

	/**
	 * 增加员工
	 * 
	 * @param emp
	 *            员工信息
	 */
	public void add(Employee emp) {
		List<Employee> list = new ArrayList();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			int rs = stat.executeUpdate("insert into employee(name,sex,age,d_id,img) values('" + emp.getName() + "','"
					+ emp.getSex() + "'," + emp.getAge() + "," + emp.getDep().getId() + ",'"+emp.getImg()+"')");
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
	 * 批量删除员工
	 * 
	 * @param ids
	 *            想要删除员工的id字符串
	 */
	public void del(String ids) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			int rs = stat.executeUpdate("delete from employee where id in(" + ids + ")");
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
	 * 更新员工数据
	 * 
	 * @param emps
	 *            想要更新的员工信息集合
	 */
	public void update(List<Employee> emps) {
		List<Employee> list = new ArrayList();
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			for (Employee emp : emps) {
				int rs = stat.executeUpdate("update employee set name = '" + emp.getName() + "',sex='" + emp.getSex()
						+ "',age=" + emp.getAge() + ",d_id=" + emp.getD_id() + " where id=" + emp.getId() + "");
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
