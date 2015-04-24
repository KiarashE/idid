package se.kth.studadm.client;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("calendar")
public interface CalendarService extends RemoteService {
	String calendarServer(String name) throws IllegalArgumentException;
	List<List<String>> getCalenderYearDates(String name) throws IllegalArgumentException;
}
