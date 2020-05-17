package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.servlets.ServletUtil.BandInfo;

/**
 * Web servlet that is called when voting is performed.
 * It gets bend ID for which it's voted from context parameters under the name 'id'.
 * Then it adds one vote to the band which has that ID and updates the file with voting results.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

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
		
		List<BandInfo> votingList = ServletUtil.getVotingList(lines, sourceFile);
		String voteID = req.getParameter("id");
		if (voteID != null) {
			voteID = voteID.trim();
			if (!voteID.isEmpty()) {
				updateList(voteID, votingList);
			}
		}
		ServletUtil.updateFile(votingList, destinationPath);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
		
	}
	
	/**
	 * Method that updates given {@code votingList} by adding one vote
	 * to the band that has given {@code id}.
	 * 
	 * @param id         ID of the band that got the vote
	 * @param votingList list of {@link BandInfo} objects
	 */
	private void updateList(String id, List<BandInfo> votingList) {
		for (BandInfo band : votingList) {
			if (band.getId().equals(id)) {
				band.addVote();
				break;
			}
		}
	}
	
}
