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

import bean.DepPro;
import bean.Employee;
import dao.DepProDao;
import net.sf.json.JSONArray;

public class DepProServlet extends HttpServlet {
	private static final String path = "WEB-INF/department/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置编码防止乱码
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		

			String type = request.getParameter("type");

			if ("add".equals(type)) {
				add(request, response);
			} else if ("del".equals(type)) {
				del(request, response);
			} else if ("add1".equals(type)) {
				add1(request, response);
			} else if ("del1".equals(type)) {
				del1(request, response);
			}
		

	}

	private void del(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			String str = request.getParameter("dps");

			str = str.replace("%7b", "{");
			str = str.replace("%7d", "}");

			List<DepPro> list = new ArrayList<>();

			JSONArray jsonArray = JSONArray.fromObject(str);
			// 将json格式字符串转换成所需的数据

			list = (List<DepPro>) JSONArray.toCollection(jsonArray, DepPro.class);
			DepProDao dp = new DepProDao();
			boolean flag = dp.del(list);

			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			String str = request.getParameter("dps");

			str = str.replace("%7b", "{");
			str = str.replace("%7d", "}");
			List<DepPro> list = new ArrayList<>();

			JSONArray jsonArray = JSONArray.fromObject(str);
			// 将json格式字符串转换成所需的数据
			list = (List<DepPro>) jsonArray.toCollection(jsonArray, DepPro.class);

			DepProDao dp = new DepProDao();
			boolean flag = dp.add(list);

			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void add1(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			int pid = Integer.parseInt(request.getParameter("pid"));
			int did = Integer.parseInt(request.getParameter("did"));

			DepProDao dp = new DepProDao();
			boolean flag = dp.add(did, pid);

			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void del1(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			int pid = Integer.parseInt(request.getParameter("pid"));
			int did = Integer.parseInt(request.getParameter("did"));

			DepProDao dp = new DepProDao();
			boolean flag = dp.del(did, pid);

			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
