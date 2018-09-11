package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CountListener implements HttpSessionListener ,ServletContextListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		ServletContext application = event.getSession().getServletContext();
		int count = 0;
		int online = 0;
		if(application.getAttribute("count")!=null) {
			count = (Integer) application.getAttribute("count");
		}
		if(application.getAttribute("online")!=null) {
			online = (Integer) application.getAttribute("online");
		}
		count++;
		online++;
		application.setAttribute("count", count);
		application.setAttribute("online", online);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		ServletContext application = event.getSession().getServletContext();
		int online = 0;
		if(application.getAttribute("online")!=null) {
			online = (Integer) application.getAttribute("online");
		}
		online--;
		application.setAttribute("online", online);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
