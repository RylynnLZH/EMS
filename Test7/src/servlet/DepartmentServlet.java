package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.apache.taglibs.standard.tag.el.sql.UpdateTag;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import bean.DepPro;
import bean.Department;
import bean.Page;
import bean.Project;
import dao.DepProDao;
import dao.DepartmentDao;
import dao.EmployeeDao;
import dao.ProjectDao;
import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;
import net.sf.json.JSONArray;

/**
 * 部门Servlet
 * 
 * @author Rylynn
 *
 */
public class DepartmentServlet extends HttpServlet {

	// 路径
	private static final String path = "WEB-INF/department/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置编码防止乱码
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		

			String type = request.getParameter("type");

			// 显示主页
			if (type == null) {
				showDepartment(request, response);

				// 显示增加页面
			} else if ("showAdd".equals(type)) {
				showAdd(request, response);

				// 增加方法
			} else if ("add".equals(type)) {
				add(request, response);

				// 删除方法
			} else if ("del".equals(type)) {
				del(request, response);

				// 更新方法
			} else if ("update".equals(type)) {
				update(request, response);

				// 查找方法
			} else if ("search".equals(type)) {
				search(request, response);

				// 部门-项目管理
			} else if ("project".equals(type)) {
				project(request, response);

				// 部门-项目管理
			} else if ("project1".equals(type)) {
				project1(request, response);

				// 部门-项目管理
			} else if ("project2".equals(type)) {
				project2(request, response);
			}
		

	}

	// 部门-项目管理
	private void project(HttpServletRequest request, HttpServletResponse response) {

		int depid = -1;
		if (request.getParameter("id") != null) {
			depid = Integer.parseInt(request.getParameter("id"));
		}

		List<DepPro> list = new ArrayList<>();
		List<Project> lie = new ArrayList<>();
		DepProDao dp = new DepProDao();
		Department dep = new Department();
		DepartmentDao depDao = new DepartmentDao();

		dep = depDao.search(depid);
		list = dp.search(depid);
		lie = dp.liesearch(depid);

		request.setAttribute("dep", dep);
		request.setAttribute("did", depid);
		request.setAttribute("lies", lie);
		request.setAttribute("list", list);
		try {
			request.getRequestDispatcher(path + "dep_pro.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void project1(HttpServletRequest request, HttpServletResponse response) {

		int depid = -1;
		if (request.getParameter("id") != null) {
			depid = Integer.parseInt(request.getParameter("id"));
		}

		List<DepPro> list = new ArrayList<>();
		List<Project> pros = new ArrayList<>();
		List<Project> lie = new ArrayList<>();
		DepProDao dp = new DepProDao();
		Department dep = new Department();
		DepartmentDao depDao = new DepartmentDao();
		ProjectDao pd = new ProjectDao();

		dep = depDao.search(depid);
		list = dp.search(depid);
		lie = dp.liesearch(depid);
		pros = pd.search();

		request.setAttribute("dep", dep);
		request.setAttribute("did", depid);
		request.setAttribute("lies", lie);
		request.setAttribute("list", list);
		request.setAttribute("pros", pros);
		try {
			request.getRequestDispatcher(path + "dep_pro4.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void project2(HttpServletRequest request, HttpServletResponse response) {

		int depid = -1;
		if (request.getParameter("id") != null) {
			depid = Integer.parseInt(request.getParameter("id"));
		}

		List<DepPro> list = new ArrayList<>();
		List<Project> pros = new ArrayList<>();
		List<Project> lie = new ArrayList<>();
		DepProDao dp = new DepProDao();
		Department dep = new Department();
		DepartmentDao depDao = new DepartmentDao();
		ProjectDao pd = new ProjectDao();

		dep = depDao.search(depid);
		list = dp.search(depid);
		lie = dp.liesearch(depid);
		pros = pd.search();

		request.setAttribute("dep", dep);
		request.setAttribute("did", depid);
		request.setAttribute("lies", lie);
		request.setAttribute("list", list);
		request.setAttribute("pros", pros);
		try {
			request.getRequestDispatcher(path + "dep_pro5.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void search(HttpServletRequest request, HttpServletResponse response) {

		String depname = request.getParameter("depname");
		int nowpage = 1;
		if (request.getParameter("nowpage") != null) {
			nowpage = Integer.parseInt(request.getParameter("nowpage"));
		}

		int depcount = -1;
		if (request.getParameter("depcount") != null && !"".equals(request.getParameter("depcount"))) {
			depcount = Integer.parseInt(request.getParameter("depcount"));
		}
		Department dep = new Department();

		DepartmentDao depDao = new DepartmentDao();
		List<Department> list = new ArrayList<>();
		List<Department> lists = new ArrayList<>();
		lists = depDao.search();

		dep.setName(depname);
		dep.setEmp_count(depcount);
		list = depDao.search(dep);

		Page searchpage = new Page(nowpage, 5, 5, 1, list.size());

		List<Department> deps = new ArrayList<>();

		deps = depDao.search(dep, (searchpage.getNowpage() - 1) * searchpage.getPagesize(), searchpage.getPagesize());
		request.setAttribute("p", searchpage);
		request.setAttribute("deps", deps);
		request.setAttribute("inputemp", dep);
		request.setAttribute("lists", lists);
		try {
			request.getRequestDispatcher(path + "department.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Department> deps = new ArrayList();
			String str = request.getParameter("deps");

			// 将拿到的字符串转为json格式字符串
			JSONArray jsonArray = JSONArray.fromObject(str);

			// 将json格式字符串转换成所需的数据
			deps = (List<Department>) jsonArray.toCollection(jsonArray, Department.class);

			DepartmentDao ed = new DepartmentDao();

			ed.update(deps);

			response.sendRedirect("department");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void del(HttpServletRequest request, HttpServletResponse response) {
		String ids = request.getParameter("ids");

		DepartmentDao ed = new DepartmentDao();
		ed.del(ids);
		try {
			response.sendRedirect("department");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		Department dep = new Department();
		dep.setName(name);

		DepartmentDao depDao = new DepartmentDao();
		depDao.addDepartment(dep);

		try {
			response.sendRedirect("department");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(path + "adddep.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showDepartment(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Department> list = new ArrayList<>();
			List<Department> lists = new ArrayList<>();
			DepartmentDao deDao = new DepartmentDao();
			lists = deDao.search();
			list = deDao.search();
			Page page = new Page(1, 5, 5, 1, list.size());
			Department dep = new Department();
			List<Department> deps = new ArrayList<>();
			deps = deDao.search(dep, (page.getNowpage() - 1) * page.getPagesize(), page.getPagesize());

			request.setAttribute("p", page);
			request.setAttribute("deps", deps);
			request.setAttribute("lists", lists);

			request.getRequestDispatcher(path + "department.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
