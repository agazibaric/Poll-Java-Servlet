package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * Web servlet that loads voting results from file and 
 * combining it with file with definitions of the band it makes list of {@link BandInfo} objects,
 * which sends further to the '/WEB-INF/pages/glasanjeRez.jsp' for displaying the results.
 * It also sends to the above mentioned .jsp list of winnerBands.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Path sourceFile = Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"));
		Path destinationPath = Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"));
		if (!Files.exists(destinationPath)) {
			ServletUtil.createFile(sourceFile, destinationPath);
		}
		List<String> lines = Files.readAllLines(destinationPath);
		if (lines.size() == 0) {
			ServletUtil.createFile(sourceFile, destinationPath);
		}

		List<BandInfo> bands = ServletUtil.getVotingList(lines, sourceFile);
		Collections.sort(bands, ServletUtil.BandInfo.BY_NUM_OF_VOTES);
		List<BandInfo> winnerBands = ServletUtil.getWinnerBands(bands);

		req.setAttribute("votingInfo", bands);
		req.setAttribute("winnerBands", winnerBands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
