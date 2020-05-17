package hr.fer.zemris.java.hw13.servlets;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

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

/**
 * Web servlet that renders pie chart image that shows usage of operating systems. <br>
 * {@code ReportImageServlet} is mapped to "/reportImage".
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/reportImage")
public class ReportImageServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/png");
		BufferedImage image = new BufferedImage(500, 300, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = image.createGraphics();
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "Operating systems");
		chart.draw(g2d, new Rectangle(500, 300));
		
		try {
			OutputStream ostream = resp.getOutputStream();
			ImageIO.write(image, "png", ostream);
			ostream.flush();
		} catch (IOException ex) {
		}
	}
	
	/**
	 * Method creates pie chart data set that contains usage of operating systems.
	 * 
	 * @return {@link PieDataset} that contains usage of operating systems
	 */
	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Other", 5);
		dataset.setValue("Linux", 2);
		dataset.setValue("maxOS", 13);
		dataset.setValue("Windows", 80);
		return dataset;
	}
	
	/**
	 * Method creates {@link JFreeChart} object that contains given {@code dataset}.
	 * 
	 * @param dataset PieDataset that JFreeChart object shows
	 * @param title   title of the JFreeChart
	 * @return        {@link JFreeChart} object with given specifications
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
