package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置编码防止乱码
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		

			String type = request.getParameter("type");

			if (type == null) {
				showIndex(request, response); // 显示主页
			} else if ("exit".equals(type)) {
				exit(request, response);
			}
		
	}

	private void exit(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 清空所有session信息
			request.getSession().invalidate();
			response.sendRedirect("user");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void showIndex(HttpServletRequest request, HttpServletResponse response) {
		try {

			// 判断是否含有session

			request.getRequestDispatcher("WEB-INF/login/index.jsp").forward(request, response);

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
