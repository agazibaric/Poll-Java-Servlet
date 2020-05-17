package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw13.servlets.ServletUtil.BandInfo;

/**
 * Web servlet that creates XLS file 
 * that contains band informations with current voting results.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati-glasanja.xls\"");
		
		Path sourceFile = Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt"));
		Path destinationPath = Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"));
		if (!Files.exists(destinationPath)) {
			return;
		}
		List<BandInfo> bandList = ServletUtil.getVotingList(Files.readAllLines(destinationPath), sourceFile);
		
		HSSFWorkbook workbook = createXLSDocument(bandList);
		OutputStream ostream = resp.getOutputStream();
		workbook.write(ostream);
		ostream.flush();
		workbook.close();
	}
	
	/**
	 * Method creates {@link HSSFWorkbook} object 
	 * that contains band informations given in {@code bandList}.
	 * 
	 * @param bandList list of {@link BandInfo} objects.
	 * @return         {@link HSSFWorkbook} object 
	 */
	private HSSFWorkbook createXLSDocument(List<BandInfo> bandList) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Informacije o bendovima");
		
		HSSFRow firstRow = sheet.createRow(0);
		firstRow.createCell(0).setCellValue("Ime benda");
		firstRow.createCell(1).setCellValue("Broj glasova");
		firstRow.createCell(2).setCellValue("Link");
		
		for (int index = 0, n = bandList.size(); index < n; index++) {
			createNewRow(sheet, bandList.get(index), index + 1);
		}
		return workbook;
	}
	
	/**
	 * Method creates new {@link HSSFRow} in given {@code sheet}<br>
	 * which contains name, number of votes and link of the song of the given {@code band}.<br>
	 * Given {@code index} represents index of row in sheet.
	 * 
	 * @param sheet HSSFSheet sheet to which row is added
	 * @param band  band whose informations are stored in row
	 * @param index index of row in sheet
	 */
	private void createNewRow(HSSFSheet sheet, BandInfo band, int index) {
		HSSFRow row = sheet.createRow(index);
		row.createCell(0).setCellValue(band.getName());
		row.createCell(1).setCellValue(band.getNumberOfVotes());
		row.createCell(2).setCellValue(band.getLink());
	}

}
