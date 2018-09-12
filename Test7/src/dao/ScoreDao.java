package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.security.auth.callback.Callback;

import bean.Department;
import bean.Employee;
import bean.Grade;
import bean.Project;
import bean.Score;

/**
 * 业绩Dao
 * 
 * @author Rylynn
 *
 */
public class ScoreDao {

	/**
	 * 查找所有的业绩
	 * 
	 * @return
	 */
	public List<Grade> search() {

		List<Grade> gras = new ArrayList<>();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8", "root",
					"2586");
			Statement stat = conn.createStatement();
			String sql = "select * from grade";
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Grade gra = new Grade();
				gra.setId(rs.getInt("id"));
				gra.setName(rs.getString("name"));
				gras.add(gra);
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

		return gras;
	}

	/**
	 * 根据条件查找
	 * 
	 * @param sea
	 *            查询条件信息
	 * @return
	 */
	public List<Score> search(Score sea) {

		List<Score> list = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			Statement stat = conn.createStatement();

			String where = " where 1=1 ";
			if (sea.getEmp() != null && sea.getEmp().getName() != null && !sea.getEmp().getName().equals("")) {
				where += " and e_name = '" + sea.getEmp().getName() + "'";
			}
			if (sea.getEmp() != null && sea.getEmp().getDep().getId() != null
					&& sea.getEmp().getDep().getId()!=0) {
				int did = sea.getEmp().getDep().getId();
				where += " and d_id = " + did + "";
			}
			if (sea.getPro() != null && !"请选择项目".equals(sea.getPro().getName())) {
				where += " and p_name = '" + sea.getPro().getName() + "'";
			}
			if (sea.getValue() != null && sea.getValue() != 0) {
				where += " and s_value = " + sea.getValue() + "";
			}

			if (sea.getGrade() != null && !sea.getGrade().equals("请选择等级")) {
				where += " and grade = '" + sea.getGrade() + "'";
			}

			String sql = "select * from v_all " + where;

			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				Score sco = new Score();
				sco.setId(rs.getInt("id"));
				sco.setValue((Integer) rs.getObject("s_value"));
				sco.setGrade(rs.getString("grade"));

				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));

				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));

				emp.setDep(dep);
				sco.setEmp(emp);
				sco.setPro(pro);

				list.add(sco);
			}

			rs.close();
			stat.close();
			conn.close();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据查找条件分页查找
	 * 
	 * @param sea
	 *            查找条件信息
	 * @param nowpage
	 *            开始页
	 * @param pagesize
	 *            每页显示页数
	 * @return
	 */
	public List<Score> search(Score sea, int nowpage, int pagesize) {

		List<Score> list = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			Statement stat = conn.createStatement();

			String where = " where 1=1 ";
			if (sea.getEmp() != null && sea.getEmp().getName() != null && !sea.getEmp().getName().equals("")) {
				where += " and e_name = '" + sea.getEmp().getName() + "'";
			}
			if (sea.getEmp() != null && sea.getEmp().getDep().getId() != null
					&& sea.getEmp().getDep().getId()!=0) {
				int did = sea.getEmp().getDep().getId();
				where += " and d_id = " + did + "";
			}
			if (sea.getPro() != null && !"请选择项目".equals(sea.getPro().getName())) {
				where += " and p_name = '" + sea.getPro().getName() + "'";
			}
			if (sea.getValue() != null && sea.getValue() != 0) {
				where += " and s_value = " + sea.getValue() + "";
			}

			if (sea.getGrade() != null && !sea.getGrade().equals("请选择等级")) {
				where += " and grade = '" + sea.getGrade() + "'";
			}

			String sql = "select * from v_all " + where + " limit " + nowpage + "," + pagesize;

			
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				Score sco = new Score();
				sco.setId(rs.getInt("id"));
				sco.setValue((Integer) rs.getObject("s_value"));
				sco.setGrade(rs.getString("grade"));

				Employee emp = new Employee();
				emp.setId(rs.getInt("e_id"));
				emp.setName(rs.getString("e_name"));

				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("d_name"));

				Project pro = new Project();
				pro.setId(rs.getInt("p_id"));
				pro.setName(rs.getString("p_name"));

				emp.setDep(dep);
				sco.setEmp(emp);
				sco.setPro(pro);

				list.add(sco);
			}

			rs.close();
			stat.close();
			conn.close();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 更新业绩
	 * 
	 * @param scos
	 *            想要更新的业绩集合
	 */
	public void update(List<Score> scos) {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			Statement stat = conn.createStatement();

			for (Score sco : scos) {
				int rs = stat.executeUpdate(
						"update score set value = " + sco.getValue() + " where id = " + sco.getId() + "");

			}

			stat.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 批量增加业绩
	 * 
	 * @param scos
	 *            想要增加的业绩集合
	 */
	public void add(List<Score> scos) {

		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			Statement stat = conn.createStatement();

			for (Score sco : scos) {

				String sql = "insert into score(e_id,p_id,value) values(" + sco.getEmp().getId() + ","
						+ sco.getPro().getId() + "," + sco.getValue() + ")";

				int rs = stat.executeUpdate(sql);

			}

			stat.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
