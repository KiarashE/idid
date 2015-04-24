package se.kth.studadm.server;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import se.kth.studadm.client.GreetingService;
import se.kth.studadm.shared.CalendarUtils;
import se.kth.studadm.shared.FieldVerifier;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Choose a year as 2015");
		}
 
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");	
		
		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		getDates();
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	private ArrayList<CalendarUtils> getDates(){
		LocalDate lds = LocalDate.parse("2015-01-01");
		LocalDate lde = LocalDate.parse("2015-12-31");
		
		DateTimeFormatter formatter = DateTimeFormatter.ISO_WEEK_DATE;

		CalendarUtils calendarYear = new CalendarUtils();
		ArrayList<CalendarUtils> calData = new ArrayList<CalendarUtils>();
		
		while(lde.compareTo(lds) >= 0){
			calendarYear.setDate(lds.toString());
			calendarYear.setWeekdate(formatter.format(lds));
			lds = lds.plusDays(1);
		}
		
		return calData;
	}

}
