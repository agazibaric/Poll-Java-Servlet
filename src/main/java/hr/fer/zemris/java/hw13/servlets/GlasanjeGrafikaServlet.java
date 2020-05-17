package hr.fer.zemris.java.hw13.servlets;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.hw13.servlets.ServletUtil.BandInfo;

/**
 * Web servlet that displays pie chart png image 
 * that contains informations about current voting results.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Path sourceFile = Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"));
		Path destinationPath = Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"));
		if (!Files.exists(destinationPath)) {
			return;
		}
		List<BandInfo> bandList = ServletUtil.getVotingList(Files.readAllLines(destinationPath), sourceFile);
		
		resp.setContentType("image/png");
		BufferedImage image = new BufferedImage(500, 300, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = image.createGraphics();
		PieDataset dataset = createDataset(bandList);
		JFreeChart chart = createChart(dataset, "Bendovi");
		chart.draw(g2d, new Rectangle(500, 300));
		
		try {
			OutputStream ostream = resp.getOutputStream();
			ImageIO.write(image, "png", ostream);
			ostream.flush();
		} catch (IOException ex) {
		}
	}
	
	/**
	 * Method creates {@link PieDataset} that contains
	 * informations given in {@code bandList}.
	 * 
	 * @param bandList list of {@link BandInfo} objects
	 * @return         {@link PieDataset} object
	 */
	private PieDataset createDataset(List<BandInfo> bandList) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		bandList.forEach(band -> dataset.setValue(band.getName(), band.getNumberOfVotes()));
		return dataset;
	}

	/**
	 * Method creates {@link JFreeChart} object from given {@code dataset}.
	 * 
	 * @param dataset dataset that pie chart displays
	 * @param title   title pf the pie chart
	 * @return        {@link JFreeChart} that contains given dataset 
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
            title,                  // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(250);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
	
}
