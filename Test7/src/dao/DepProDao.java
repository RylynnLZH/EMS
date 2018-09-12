package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import bean.DepPro;
import bean.Project;

/**
 * 部门-项目Dao
 * 
 * @author Rylynn
 *
 */
public class DepProDao {

	DepPro pro;

	/**
	 * 根据部门id查找出此部门所有的项目
	 * 
	 * @return 返回查找到的list
	 */
	public List<DepPro> search(int id) {

		List<DepPro> list = new ArrayList();

		try {
			// 2.利用反射 ,加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 3.与数据库建立连接
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			// 4.建立statment sql语句执行器
			Statement stat = conn.createStatement();

			// 5.执行sql语句并得到结果\
			ResultSet rs = null;
			if(id==0) {
				rs = stat.executeQuery("select * from project");
				while (rs.next()) {
					DepPro pro = new DepPro();
					pro.setpName(rs.getString("name"));
					list.add(pro);
				}

			}else {
				rs = stat.executeQuery("select d_id,id,name from v_dep_pro where d_id = " + id + "");

				while (rs.next()) {
					DepPro pro = new DepPro();
					pro.setDid(rs.getInt("d_id"));
					pro.setPid(rs.getInt("id"));
					pro.setpName(rs.getString("name"));
					list.add(pro);
				}
			}

			// 6.对结果集进行处理

			// 遍历Department 将查询的信息存进list
			

			// 7.关闭连接
			rs.close();
			stat.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 根据部门id和项目id查找
	 * 
	 * @param did
	 *            部门id
	 * @param pid
	 *            项目id
	 * @return 返回查找到的list
	 */
	public List<DepPro> search(int did, int pid) {

		List<DepPro> list = new ArrayList();

		try {
			// 2.利用反射 ,加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 3.与数据库建立连接
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			// 4.建立statment sql语句执行器
			Statement stat = conn.createStatement();

			// 5.执行sql语句并得到结果
			ResultSet rs = stat.executeQuery("select * from r_dep_pro where d_id = " + did + " and p_id = " + pid + "");

			// 6.对结果集进行处理

			// 遍历Department 将查询的信息存进list
			while (rs.next()) {
				DepPro pro = new DepPro();
				pro.setDid(rs.getInt("d_id"));
				pro.setPid(rs.getInt("p_id"));

				list.add(pro);
			}

			// 7.关闭连接
			rs.close();
			stat.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 查找该部门没有的项目
	 * 
	 * @param id
	 *            部门id
	 * @return 防护查找到的list
	 */
	public List<Project> liesearch(int id) {

		List<Project> list = new ArrayList();

		try {
			// 2.利用反射 ,加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 3.与数据库建立连接
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			// 4.建立statment sql语句执行器
			Statement stat = conn.createStatement();

			// 5.执行sql语句并得到结果
			ResultSet rs = stat.executeQuery(
					"select * from project where id not in(select id from v_dep_pro where d_id = " + id + ")");

			// 6.对结果集进行处理

			// 遍历Department 将查询的信息存进list
			while (rs.next()) {
				Project pro = new Project();
				pro.setId(rs.getInt("id"));
				pro.setName(rs.getString("name"));
				list.add(pro);
			}

			// 7.关闭连接
			rs.close();
			stat.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 批量删除
	 * 
	 * 
	 */
	public boolean del(List<DepPro> list) {
		int rs = 0;
		try {
			// 2.利用反射 ,加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 3.与数据库建立连接
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			// 4.建立statment sql语句执行器

			conn.setAutoCommit(false);
			Statement stat = conn.createStatement();

			// 5.执行sql语句并得到结果

			/**
			 * id为存储选中将删除行的数组 遍历数组 删除记录
			 */
			for (DepPro dep : list) {

				rs = stat.executeUpdate(
						"delete from r_dep_pro where d_id = " + dep.getDid() + " and p_id = " + dep.getPid() + "");
			}

			conn.commit();

			// 6.对结果集进行处理

			// 7.关闭连接
			// rs.close();
			stat.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs > 0;
	}

	/**
	 * 根据条件删除
	 * 
	 * @param did
	 *            部门id
	 * @param pid
	 *            项目id
	 * @return 返回是否删除成功
	 */
	public boolean del(int did, int pid) {
		int rs = 0;
		try {
			// 2.利用反射 ,加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 3.与数据库建立连接
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			// 4.建立statment sql语句执行器

			conn.setAutoCommit(false);
			Statement stat = conn.createStatement();

			// 5.执行sql语句并得到结果

			/**
			 * id为存储选中将删除行的数组 遍历数组 删除记录
			 */

			List<DepPro> list = new ArrayList();

			list = search(did, pid);

			if (list.size() > 0) {
				rs = stat.executeUpdate("delete from r_dep_pro where d_id = " + did + " and p_id = " + pid + "");

			} else {
				rs = -1;
			}

			conn.commit();

			// 6.对结果集进行处理

			// 7.关闭连接
			// rs.close();
			stat.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs > 0;
	}

	/**
	 * 新增数据
	 * 
	 * 
	 * @return 返回是否新增成功
	 */
	public boolean add(List<DepPro> list) {
		boolean flag = false;
		int rs = 0;
		PreparedStatement pstat = null;
		try {
			// 2.利用反射 ,加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 3.与数据库建立连接
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			// 4.建立statment sql语句执行器

			// 5.执行sql语句并得到结果

			for (DepPro dep : list) {
				pstat = (PreparedStatement) conn.prepareStatement("insert into r_dep_pro(d_id,p_id) values(?,?)");

				pstat.setInt(1, dep.getDid());
				pstat.setInt(2, dep.getPid());
				rs = pstat.executeUpdate();
			}

			// 6.对结果集进行处理
			if (rs > 0)
				flag = true; // 新增成功 返回true

			// 7.关闭连接
			// rs.close();
			pstat.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	
	/**
	 * 新增数据
	 * 
	 * @param did
	 *            部门id
	 * @param pid
	 *            项目id
	 * @return
	 */
	public boolean add(int did, int pid) {

		int rs = 0;
		PreparedStatement pstat = null;
		try {
			// 2.利用反射 ,加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 3.与数据库建立连接
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/st?characterEncoding=utf-8",
					"root", "2586");

			// 4.建立statment sql语句执行器

			// 5.执行sql语句并得到结果

			List<DepPro> list = new ArrayList();

			list = search(did, pid);

			if (list.size() > 0) {
				rs = -1;
			} else {
				pstat = (PreparedStatement) conn.prepareStatement("insert into r_dep_pro(d_id,p_id) values(?,?)");
				// System.out.println(sql);
				pstat.setInt(1, did);
				pstat.setInt(2, pid);
				rs = pstat.executeUpdate();
				pstat.close();
			}

			// 6.对结果集进行处理

			// 7.关闭连接
			// rs.close();

			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs > 0;
	}

}
