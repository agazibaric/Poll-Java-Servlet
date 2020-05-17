package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.servlets.ServletUtil.BandInfo;

/**
 * Web servlet that loads bend informations from bend definitions file
 * and sends list of {@link BandInfo} objects further to the '/WEB-INF/pages/glasanjeIndex.jsp'
 * for displaying the voting options.
 * 
 * @author Ante Gazibaric
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<BandInfo> bandInfoList = ServletUtil.getBandInfoList(Files.readAllLines(Paths.get(fileName)));
		Collections.sort(bandInfoList);
		req.setAttribute("bandList", bandInfoList);		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		
	}
	
}
