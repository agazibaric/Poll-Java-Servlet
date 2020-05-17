package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet that sets background color 
 * according to the given parameter under the name "bgcolor".<p>
 * 
 * If parameter is not given or given color is not valid,
 * default white color is used.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/setcolor")
public class BgColorServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Default color.
	 */
	private static final String DEFAULT_COLOR = "FFFFFF";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String color = req.getParameter("bgcolor");
		if (color == null) {
			color = DEFAULT_COLOR;
		} else {
			color = color.trim();
			if (color.isEmpty() || !color.matches("[A-Fa-f0-9]{6}")) {
				color = DEFAULT_COLOR;
			}
		}
		
		req.getSession().setAttribute("pickedBgCol", color);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
		
	}
	

}
