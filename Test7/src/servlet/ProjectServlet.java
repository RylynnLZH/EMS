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

import bean.Project;
import bean.Page;
import dao.ProjectDao;
import dao.EmployeeDao;
import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;
import net.sf.json.JSONArray;

public class ProjectServlet extends HttpServlet {

	private static final String path = "WEB-INF/project/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置编码防止乱码
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		
			String type = request.getParameter("type");

			if (type == null) {
				showProject(request, response); // 显示主页
			} else if ("showAdd".equals(type)) {
				showAdd(request, response);
			} else if ("add".equals(type)) {
				add(request, response);
			} else if ("del".equals(type)) {
				del(request, response);
			} else if ("update".equals(type)) {
				update(request, response);
			} else if ("search".equals(type)) {
				search(request, response);
			}
		
	}

	private void search(HttpServletRequest request, HttpServletResponse response) {

		String depname = "";
		if (!"0".equals(request.getParameter("depname"))) {
			depname = request.getParameter("depname");
		}

		int nowpage = 1;
		if (request.getParameter("nowpage") != null) {
			nowpage = Integer.parseInt(request.getParameter("nowpage"));

		}
		Project dep = new Project();

		ProjectDao depDao = new ProjectDao();
		List<Project> list = new ArrayList<>();
		List<Project> lists = new ArrayList<>();
		lists = depDao.search();

		dep.setName(depname);

		list = depDao.search(dep);

		Page searchpage1 = new Page(nowpage, 5, 5, 1, list.size());
		searchpage1.setNowpage(nowpage);

		List<Project> deps = new ArrayList<>();

		deps = depDao.search(dep, (searchpage1.getNowpage() - 1) * searchpage1.getPagesize(),
				searchpage1.getPagesize());

		request.setAttribute("p", searchpage1);
		request.setAttribute("deps", deps);
		request.setAttribute("inputemp", dep);
		request.setAttribute("lists", lists);
		try {
			request.getRequestDispatcher(path + "project.jsp").forward(request, response);
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
			List<Project> deps = new ArrayList();
			String str = request.getParameter("deps");

			// 将拿到的字符串转为json格式字符串
			JSONArray jsonArray = JSONArray.fromObject(str);

			// 将json格式字符串转换成所需的数据
			deps = (List<Project>) jsonArray.toCollection(jsonArray, Project.class);

			ProjectDao ed = new ProjectDao();

			ed.update(deps);

			response.sendRedirect("project");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void del(HttpServletRequest request, HttpServletResponse response) {
		String ids = request.getParameter("ids");

		ProjectDao ed = new ProjectDao();
		ed.del(ids);
		try {
			response.sendRedirect("project");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		Project dep = new Project();
		dep.setName(name);

		ProjectDao depDao = new ProjectDao();
		depDao.addProject(dep);

		try {
			response.sendRedirect("project");
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

	private void showProject(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Project> list = new ArrayList<>();
			List<Project> lists = new ArrayList<>();
			ProjectDao deDao = new ProjectDao();
			lists = deDao.search();
			list = deDao.search();
			Page page = new Page(1, 5, 5, 1, list.size());
			Project dep = new Project();
			List<Project> deps = new ArrayList<>();
			deps = deDao.search(dep, (page.getNowpage() - 1) * page.getPagesize(), page.getPagesize());

			request.setAttribute("p", page);
			request.setAttribute("deps", deps);
			request.setAttribute("lists", lists);

			
			request.getRequestDispatcher(path + "project.jsp").forward(request, response);

			
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
