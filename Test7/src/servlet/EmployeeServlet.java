package servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import bean.Department;
import bean.Employee;
import bean.Page;
import dao.DepartmentDao;
import dao.EmployeeDao;
import net.sf.json.JSONArray;

public class EmployeeServlet extends HttpServlet {
	private static final String path = "WEB-INF/employee/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置编码防止乱码
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		

			// 拿到标记
			String type = request.getParameter("type");

			// 根据拿到的标记 判断所需功能
			if (type == null) {
				showEmployee(request, response); // 显示主页
			} else if ("showAdd".equals(type)) {
				showAdd(request, response); // 添加页面
			} else if ("add".equals(type)) {
				addEmployee(request, response); // 添加功能
			} else if ("del".equals(type)) {
				delEmployee(request, response); // 删除功能
			} else if ("showUpdate".equals(type)) {
				showUpdate(request, response); // 增加页面
			} else if ("update".equals(type)) {
				updateEmployee(request, response); // 更新功能
			} else if ("page".equals(type)) {
				showPage(request, response); // 分页功能
			} else if ("search".equals(type)) {
				showSearch(request, response);
			}
		
	}

	private void showSearch(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Department> deps = new ArrayList<>();
		DepartmentDao depDao = new DepartmentDao();
		deps = depDao.search();
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		int age = -1;
		try {
			if (request.getParameter("age") != null && !"".equals(request.getParameter("age"))) {
				age = Integer.parseInt(request.getParameter("age"));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		Integer did = null;
		if (!"请选择部门".equals(request.getParameter("depid"))) {
			did = Integer.parseInt(request.getParameter("depid"));
		}

		Employee empss = new Employee();
		empss.setName(name);
		empss.setAge(age);
		empss.setSex(sex);
		empss.setD_id(did);
		int nowpage = 1;
		if (request.getParameter("nowpage") != null && !"".equals(request.getParameter("nowpage"))) {
			nowpage = Integer.parseInt(request.getParameter("nowpage"));
		}

		EmployeeDao eDao = new EmployeeDao();
		List<Employee> searchemps = eDao.search(empss);
		Page searchpage = new Page(nowpage, 10, 5, 1, searchemps.size());

		List<Employee> pageemps = eDao.search(empss, (searchpage.getNowpage() - 1) * searchpage.getPagesize(),
				searchpage.getPagesize());

		request.setAttribute("p", searchpage);
		request.setAttribute("inputemp", empss);
		request.setAttribute("emps", pageemps);
		request.setAttribute("deps", deps);
		try {
			request.getRequestDispatcher(path + "employee.jsp").forward(request, response);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 显示增加页面
	private void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {

			List<Department> deps = new ArrayList<>();
			DepartmentDao depDao = new DepartmentDao();

			deps = depDao.search();
			request.setAttribute("deps", deps);

			
			request.getRequestDispatcher(path + "add.jsp").forward(request, response);

			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 显示增加页面
	private void showUpdate(HttpServletRequest request, HttpServletResponse response) {

		try {
			// 根据拿到的id查询 并返回需要的数据
			String ids = request.getParameter("ids");
			EmployeeDao ed = new EmployeeDao();
			List<Department> deps = new ArrayList<>();
			DepartmentDao depDao = new DepartmentDao();
			deps = depDao.search();
			List<Employee> emps = ed.find(ids);
			request.setAttribute("emps", emps);
			request.setAttribute("deps", deps);

			request.getRequestDispatcher(path + "update.jsp").forward(request, response);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 更新页面
	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Employee> emps = new ArrayList();
			String str = request.getParameter("emps");

			// 将拿到的字符串转为json格式字符串
			JSONArray jsonArray = JSONArray.fromObject(str);

			// 将json格式字符串转换成所需的数据
			emps = (List<Employee>) jsonArray.toCollection(jsonArray, Employee.class);

			EmployeeDao ed = new EmployeeDao();
			ed.update(emps);

			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 删除功能
	private void delEmployee(HttpServletRequest request, HttpServletResponse response) {

		String ids = request.getParameter("ids");

		EmployeeDao ed = new EmployeeDao();
		ed.del(ids);
		try {
			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 员工信息表首页
	List<Employee> allemps;

	public void showEmployee(HttpServletRequest request, HttpServletResponse response) {
		try {
			EmployeeDao ed = new EmployeeDao();
			DepartmentDao de = new DepartmentDao();
			// 查询全部值计算出全部页数
			List<Department> deps = new ArrayList<>();
			deps = de.search();
			allemps = ed.search();
			Page page = new Page(1, 10, 5, 1, allemps.size());

			// 根据初始值查出数据并返回
			List<Employee> emps = ed.search((page.getNowpage() - 1) * page.getPagesize(), page.getPagesize());

			request.setAttribute("p", page);
			request.setAttribute("emps", emps);
			request.setAttribute("deps", deps);

			
			request.getRequestDispatcher(path + "employee.jsp").forward(request, response);

			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 分页功能
	public void showPage(HttpServletRequest request, HttpServletResponse response) {
		try {
			int nowpage = Integer.parseInt(request.getParameter("nowpage"));
			Page p = new Page(nowpage, 10, 5, 1, allemps.size());
			EmployeeDao ed = new EmployeeDao();

			// 根据传来的页码查询结果并返回
			List<Employee> emps = ed.search((nowpage - 1) * p.getPagesize(), p.getPagesize());

			System.out.println("12" + emps.get(0).getImg());
			request.setAttribute("p", p);
			request.setAttribute("emps", emps);
			try {
				request.getRequestDispatcher(path + "employee.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 增加功能
	public void addEmployee(HttpServletRequest request, HttpServletResponse response) {

		try {

			FileItemFactory factory = new DiskFileItemFactory();// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String filepath = "d:/webImg/";

			Employee emp = new Employee();
			Department dep = new Department();

			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				if (item.getFieldName().equals("name")) {
					String name = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
					emp.setName(name);

				} else if (item.getFieldName().equals("sex")) {
					String sex = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
					emp.setSex(sex);

				} else if (item.getFieldName().equals("age")) {
					int age = 0;
					if (!"".equals(new String(item.getString().getBytes("ISO-8859-1"), "utf-8"))
							&& new String(item.getString().getBytes("ISO-8859-1"), "utf-8") != null)
						age = Integer.parseInt(new String(item.getString().getBytes("ISO-8859-1"), "utf-8"));
					emp.setAge(age);
				} else if (item.getFieldName().equals("dep")) {
					Integer d_id = null;
					if (!"".equals(new String(item.getString().getBytes("ISO-8859-1"), "utf-8"))
							&& new String(item.getString().getBytes("ISO-8859-1"), "utf-8") != null)
						d_id = Integer.parseInt(new String(item.getString().getBytes("ISO-8859-1"), "utf-8"));
					dep.setId(d_id);
				} else if (item.getFieldName().equals("touxiang")) {
					UUID uuid = UUID.randomUUID();
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					String filename = uuid.toString() + houzhui;
					File savedFile = new File(filepath, filename);
					item.write(savedFile);
					emp.setImg(filename);
				}

			}

			emp.setDep(dep);
			EmployeeDao ed = new EmployeeDao();
			ed.add(emp);
			// request.getRequestDispatcher("employee").forward(request, response);
			response.sendRedirect("employee");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
