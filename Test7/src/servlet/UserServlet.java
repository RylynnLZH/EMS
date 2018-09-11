package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;
import com.sun.prism.Image;

import bean.User;
import bean.ValidatedCode;
import dao.UserDao;
import util.CreateMD5;
import util.RandomNumber;

public class UserServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		String type = request.getParameter("type");
		if (type == null) {
			showLogin(request, response);
		} else if ("doLogin".equals(type)) {
			doLogin(request, response);
		} else if ("randomImage".equals(type)) {
			randomImage(request, response);
		} else if ("showRegister".equals(type)) {
			showRegister(request, response);
		} else if ("doRegister".equals(type)) {
			doRegister(request, response);
		} else if ("rename".equals(type)) {
			rename(request, response);
		}

	}

	private void rename(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
		
		String username = request.getParameter("username");
		
		UserDao userDao = new UserDao();
		boolean flag = userDao.search(username);
		
		out.print(flag);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doRegister(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = new User();
			user.setUsername(username);
			user.setPassword(CreateMD5.getMd5(password+username+"lzh"));

			UserDao userDao = new UserDao();
			boolean flag = userDao.add(user);

			response.sendRedirect("user");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showRegister(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("WEB-INF/login/register.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showLogin(HttpServletRequest request, HttpServletResponse response) {
		try {

			Cookie[] cookies = request.getCookies();
			String cookie = "";
			if (cookies != null) {

				for (int i = 0; i < cookies.length; i++) {
					if ("userName".equals(cookies[i].getName())) {
						cookie = cookies[i].getValue();
					}

				}
			}

			request.setAttribute("userName", cookie);
			request.getRequestDispatcher("WEB-INF/login/login.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		try {
			PrintWriter out = response.getWriter();

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = new User();
			user.setUsername(username);
			user.setPassword(CreateMD5.getMd5(password+username+"lzh"));
			String random = request.getParameter("random");

			if (random.equals(session.getAttribute("rand"))) {

				UserDao ud = new UserDao();
				boolean flag = ud.search(user);

				if (flag) {

					// 把用户数据保存在session域对象中
					session.setAttribute("username", username);
					// 设置用户闲时消除登陆状态 N*60 N分钟
					session.setMaxInactiveInterval(5 * 60);
					if (request.getCookies() == null) {
						Cookie cookie = new Cookie("userName", username);
						cookie.setMaxAge(5 * 60);

						response.addCookie(cookie);
					}

					out.print(flag);
				} else {
					out.print(flag);
				}
			} else {
				request.setAttribute("mes", "验证码输入错误!");
				request.getRequestDispatcher("WEB-INF/login/login.jsp").forward(request, response);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void randomImage(HttpServletRequest request, HttpServletResponse response) {
		RandomNumber rn = new RandomNumber();
		try {

			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			ValidatedCode vc = rn.generateImage();
			ServletOutputStream outstream = response.getOutputStream();

			// 输出图像到页面
			ImageIO.write(vc.getImage(), "JPEG", outstream);
			outstream.close();
			request.getSession().setAttribute("rand", vc.getRand());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
