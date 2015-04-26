package se.kth.studadm.server;


import se.kth.studadm.client.CalendarService;
import se.kth.studadm.shared.FieldVerifier;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.core.java.util.Arrays;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CalendarServiceImpl extends RemoteServiceServlet implements CalendarService {
	
	public String calendarServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.  
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Choose a year as 2015");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");	
		
		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return input;
	}
	
	

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	




	public List<List<String>> getCalenderYearDates(String year) throws IllegalArgumentException { 
		// TODO Auto-generated method stub

		String dateFrom = year + "-01-01";
		String dateTo = year + "-12-31";
		
		List<List<String>> dates = new ArrayList<List<String>>();
		
		LocalDate lds = LocalDate.parse(dateFrom);
		LocalDate lde = LocalDate.parse(dateTo);
		
		DateTimeFormatter formatter = DateTimeFormatter.ISO_WEEK_DATE;
		
		while(lde.compareTo(lds) >= 0){
			
			List<String> items = new ArrayList<String>();
			items.add(lds.toString());
			items.add(formatter.format(lds));
			
			dates.add(items);
			
			lds = lds.plusDays(1);
		}
		
		return dates;
	}

}
