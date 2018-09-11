package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import bean.Score;
import bean.DepPro;
import bean.Department;
import bean.Employee;
import bean.Grade;
import bean.Page;
import dao.ProjectDao;
import dao.ScoreDao;
import dao.DepProDao;
import dao.DepartmentDao;
import dao.EmployeeDao;
import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ScoreServlet extends HttpServlet {

	private static final String path = "WEB-INF/score/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置编码防止乱码
			request.setCharacterEncoding("utf-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
			String type = request.getParameter("type");

			if (type == null) {
				showScore(request, response); // 显示主页
			} else if ("update".equals(type)) {
				update(request, response);
			} else if ("search".equals(type)) {
				search(request, response);
			} else if ("searchByDep".equals(type)) {
				searchByDep(request, response);
			}
		
	}

	private void searchByDep(HttpServletRequest request, HttpServletResponse response) {

		try {
			PrintWriter out = response.getWriter();

			int did = Integer.parseInt(request.getParameter("did"));

			List<DepPro> dp = new ArrayList();
			DepProDao dd = new DepProDao();
			dp = dd.search(did);

			JSON json = JSONArray.fromObject(dp);

			out.print(json);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void search(HttpServletRequest request, HttpServletResponse response) {

		try {
			List<Score> list = new ArrayList();
			List<Score> scos = new ArrayList<>();
			List<Department> alldeps = new ArrayList<>();
			List<DepPro> allpros = new ArrayList<>();
			List<Grade> allgrades = new ArrayList<>();

			ScoreDao deDao = new ScoreDao();
			DepartmentDao depDao = new DepartmentDao();
			DepProDao proDao = new DepProDao();

			Score sc = new Score();
			int nowpage = Integer.parseInt(request.getParameter("nowpage"));
			String ename = request.getParameter("ename");
			int did = Integer.parseInt(request.getParameter("did"));

			String pname = request.getParameter("pname");

			Integer value = null;
			if (!"".equals(request.getParameter("value"))) {
				value = Integer.parseInt(request.getParameter("value"));
			}

			String grade = request.getParameter("grade");

			alldeps = depDao.search();
			allpros = proDao.search(did);
			allgrades = deDao.search();

			Employee emp = new Employee();
			Department dep = new Department();
			Project pro = new Project();
			dep.setId(did);
			emp.setDep(dep);
			emp.setName(ename);
			pro.setName(pname);
			sc.setPro(pro);
			sc.setEmp(emp);
			sc.setGrade(grade);
			sc.setValue(value);

			list = deDao.search(sc);

			Page page = new Page(nowpage, 10, 5, 1, list.size());

			scos = deDao.search(sc, (page.getNowpage() - 1) * page.getPagesize(), page.getPagesize());

			request.setAttribute("p", page);
			request.setAttribute("scos", scos);
			request.setAttribute("alldeps", alldeps);
			request.setAttribute("allpros", allpros);
			request.setAttribute("allgrades", allgrades);
			request.setAttribute("sc", sc);

			request.getRequestDispatcher(path + "score.jsp").forward(request, response);
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
			// PrintWriter out = response.getWriter();

			List<Score> scos = new ArrayList();
			String str = request.getParameter("values");

			// 将拿到的字符串转为json格式字符串
			JSONArray jsonArray = JSONArray.fromObject(str);

			// 将json格式字符串转换成所需的数据
			scos = (List<Score>) jsonArray.toCollection(jsonArray, Score.class);

			ScoreDao ed = new ScoreDao();

			for (Score sc : scos) {

				if (sc.getId() == 0) {
					ed.add(scos);
					break;

				} else {
					ed.update(scos);
				}
			}

			response.sendRedirect("score");
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

	private void showScore(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Score> list = new ArrayList();
			List<Score> scos = new ArrayList<>();
			List<Department> alldeps = new ArrayList<>();
			List<DepPro> allpros = new ArrayList<>();
			List<Grade> allgrades = new ArrayList<>();

			ScoreDao deDao = new ScoreDao();
			DepartmentDao depDao = new DepartmentDao();
			DepProDao proDao = new DepProDao();

			alldeps = depDao.search();
			allpros = proDao.search(0);
			allgrades = deDao.search();

			Score sc = new Score();
			list = deDao.search(sc);

			Page page = new Page(1, 10, 5, 1, list.size());

			scos = deDao.search(sc, (page.getNowpage() - 1) * page.getPagesize(), page.getPagesize());

			request.setAttribute("p", page);
			request.setAttribute("scos", scos);
			request.setAttribute("alldeps", alldeps);
			request.setAttribute("allpros", allpros);
			request.setAttribute("allgrades", allgrades);

			request.getRequestDispatcher(path + "score.jsp").forward(request, response);

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
