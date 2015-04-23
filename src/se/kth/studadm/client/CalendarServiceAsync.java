package se.kth.studadm.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface CalendarServiceAsync {
	void calendarServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
