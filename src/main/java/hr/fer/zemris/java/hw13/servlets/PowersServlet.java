package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Web servlet that creates XLS file that contains powers of given numbers.
 * Servlet expects parameter 'a' representing lower limit and parameter 'b'
 * representing upper limit of numbers whose power is calculated. It also
 * expects parameter 'n' which represents number of power operations performed
 * and also 'n' represents power degree.
 * <p>
 * Pseudocode:
 * <ul>
 * <li>for i = 1 to n:</li>
 * <ul>
 * <li>for j = a to b:</li>
 * <ul>
 * <li>write: (j, j^n)</li>
 * </ul>
 * </ul>
 * </ul>
 * <p>
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Maximum value for 'a' and 'b'.
	 */
	private static final Integer MAX_VALUE = 100;
	/**
	 * Minimum value for 'a' and 'b'.
	 */
	private static final Integer MIN_VALUE = -100;
	/**
	 * Maximum value for 'n'.
	 */
	private static final Integer MAX_N = 5;
	/**
	 * Minimum value for 'n'.
	 */
	private static final Integer MIN_N = 1;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer a = null;
		Integer b = null;
		Integer n = null;
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
		} catch (NumberFormatException | NullPointerException ex) {
			req.getRequestDispatcher("/WEB-INF/pages/powersError.jsp").forward(req, resp);
			return;
		}

		if (!isValidValue(a, MIN_VALUE, MAX_VALUE) || !isValidValue(b, MIN_VALUE, MAX_VALUE)
				|| !isValidValue(n, MIN_N, MAX_N)) {
			req.getRequestDispatcher("/WEB-INF/pages/powersError.jsp").forward(req, resp);
			return;
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		
		HSSFWorkbook workbook = createXLSDocument(a, b, n);
		OutputStream ostream = resp.getOutputStream();
		workbook.write(ostream);
		ostream.flush();
		workbook.close();
	}
	
	/**
	 * Method checks if given value {@code x} is in given range
	 * from {@code min} to the {@code max}, including bounds.
	 * 
	 * @param x   value that is checked
	 * @param min minimum value for the given {@code x}
	 * @param max maximum value for the given {@code x}
	 * @return    {@code true} is given value is in given range, <br>
	 * 			  {@code false} otherwise
	 */
	private boolean isValidValue(int x, int min, int max) {
		return x >= min && x <= max;
	}
	
	/**
	 * Method creates {@link HSSFWorkbook} object from given informations.
	 * 
	 * @param a lower limit of the numbers whose power is calculated
	 * @param b upper limit of the numbers whose power is calculated
	 * @param n power degree
	 * @return  {@link HSSFWorkbook} object 
	 */
	private HSSFWorkbook createXLSDocument(int a, int b, int n) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		for (int i = 1; i <= n; i++) {
			setPage(workbook.createSheet(getOrdinalStringFor(i)), a, b, i);
		}
		return workbook;
	}
	
	/**
	 * Method fills given {@code sheet} with powers of the numbers in given range.
	 * 
	 * @param sheet HSSFSheet that is filled
	 * @param a lower limit of the numbers whose power is calculated
	 * @param b upper limit of the numbers whose power is calculated
	 * @param n power degree
	 */
	private void setPage(HSSFSheet sheet, int a, int b, int n) {
		HSSFRow firstRow = sheet.createRow(0);
		firstRow.createCell(0).setCellValue("x");
		firstRow.createCell(1).setCellValue("x^n");
		for (int i = 1; a <= b; i++, a++) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(a);
			row.createCell(1).setCellValue(Math.pow(a, n));
		}
	}
	
	/**
	 * Method returns ordinal number string for given {@code number}.
	 * 
	 * @param number number whose ordinal number is returned
	 * @return       ordinal number string for given {@code number}.
	 */
	private String getOrdinalStringFor(int number) {
		if (number == 1)
			return "1st";
		if (number == 2)
			return "2nd";
		if (number == 3)
			return "3rd";
		return number + "th";
	}

}
