package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet that calculates sin(x) and cos(x), x is in given range.
 * Range is given by parameters: 'a' is lower limit, 'b' is upper limit.<p>
 * When servlet is done with calculations it forwards job of displaying result to the <br>
 * '/WEB-INF/pages/trigonometric.jsp'.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Default value for A.
	 */
	private static final Integer A_DEF = 0;
	/**
	 * Default value for B.
	 */
	private static final Integer B_DEF = 360;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer a = getValidValue(req.getParameter("a"), A_DEF);
		Integer b = getValidValue(req.getParameter("b"), B_DEF);
		
		if (a > b) {
			// Swap values
			a = a + b;
			b = a - b;
			a = a - b;
		}
		
		if (b > a + 720) {
			b = a + 7200;
		}
		
		List<TrigonometricValues> list = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			list.add(new TrigonometricValues(i));
		}
		
		req.setAttribute("trigValues", list);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
		
	}
	
	/**
	 * Method returns valid Integer value of given {@code value}. <br>
	 * If given {@code value} is not valid, {@code defValue} is returned.
	 * 
	 * @param value    value that is checked
	 * @param defValue default value that is returned if given value is not valid
	 * @return         valid Integer value
	 */
	private Integer getValidValue(String value, Integer defValue) {
		if (value == null)
			return defValue;
		
		value = value.trim();
		if (value.isEmpty())
			return defValue;
		
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException ex) {
			return defValue;
		}
	}
	
	/**
	 * Class contains value {@code x} and calculated {@code sin(x)} and {@code cos(x)}.
	 * 
	 * @author Ante Gazibaric
	 * @version 1.0
	 *
	 */
	public static class TrigonometricValues {
		
		/**
		 * Value whose sin and cos is calculated.<br>
		 * Value is in degrees.
		 */
		private int x;
		/**
		 * Sine of x.
		 */
		private double sinX;
		/**
		 * Cosine of x.
		 */
		private double cosX;

		/**
		 * Constructor that creates new {@link TrigonometricValues} object.
		 * 
		 * @param x {@link #x}
		 */
		public TrigonometricValues(int x) {
			this.x = x;
			this.sinX = Math.sin(Math.toRadians(x));
			this.cosX = Math.cos(Math.toRadians(x));
		}
		
		/**
		 * Default constructor that creates new {@link TrigonometricValues} object.<br>
		 * It sets x value to the zero.
		 */
		public TrigonometricValues() {
			this(0);
		}

		/**
		 * Method returns x value.
		 * 
		 * @return x value
		 */
		public int getX() {
			return x;
		}

		/**
		 * Method returns sine of x value.
		 * 
		 * @return sine of x value
		 */
		public double getSinX() {
			return sinX;
		}

		/**
		 * Method returns cosine of x value.
		 * 
		 * @return cosine of x value
		 */
		public double getCosX() {
			return cosX;
		}

	}

}
