package hr.fer.zemris.java.hw13.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ServletContextListener that stores current system time 
 * {@code System.currentTimeMillis()} in context attributes under the name "startTime".
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebListener
public class AppInfoListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().removeAttribute("startTime");
	}

}
