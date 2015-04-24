package se.kth.studadm.client;

import java.util.Date;
import java.util.List;

import se.kth.studadm.shared.CalendarUtils;
import se.kth.studadm.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarModel;
import com.google.gwt.user.datepicker.client.CalendarUtil;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Idid implements EntryPoint {


	private TextBox t = new TextBox();

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private final CalendarServiceAsync calendarService = GWT.create(CalendarService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("2015");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		//RootPanel.get("calendartable").add(getCalendarTable());
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("submittest").add(getSubmit());
		setCalendarView();



		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				//testmethod1();
				
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}


				calendarService.calendarServer("2000", new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("fan det funkar ju inte");
					}

					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						Window.alert("hipppi det funkar " + result);

					}
				});






				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");

				greetingService.greetServer(textToServer, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						dialogBox.setText("Remote Procedure Call - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(SERVER_ERROR);
						dialogBox.center();
						closeButton.setFocus(true);
					}

					public void onSuccess(String result) {
						dialogBox.setText("Remote Procedure Call");
						serverResponseLabel
						.removeStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML(result);
						dialogBox.center();
						closeButton.setFocus(true);
					}
				});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}

	private HorizontalPanel getSubmit(){
		HorizontalPanel p = new HorizontalPanel();
		p.setSize("100%", "300px");
		p.setBorderWidth(1);


		p.add(t);

		Button b = new Button();
		b.setText("Submit");
		b.setSize("100px", "40px");
		p.add(b);

		FormPanel f = new FormPanel();

		f.addSubmitHandler(new SubmitHandler() {

			@Override
			public void onSubmit(SubmitEvent event) {
				// TODO Auto-generated method stub

				Window.alert("submited");

			}
		});


		return p;
	}

	private void setCalendarView(){
		String url = "http://127.0.0.1:5984/idid/_design/calendar/_view/dates2015";
		url = URL.encode(url);

		RequestBuilder req = new RequestBuilder(RequestBuilder.GET, url);
		req.setTimeoutMillis(5000);
		req.setHeader("Content-Type","application/json; charset=utf-8");

		try {
			req.sendRequest(null, new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					Rows rows = JsonUtils.safeEval(response.getText());
					populateCalnderView(rows);
				}

				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("misslyckad " + exception.getStackTrace());
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}	

	private void populateCalnderView(Rows rows){

		final FlexTable ft = new FlexTable();
		ft.setBorderWidth(1);

		for(int i = 0; i < rows.getRows().length(); i++){
			String date = rows.getRows().get(i).getValue().toString();
			//String week = rows.getRows().get(i).getValue().toString();
			ft.setWidget(i, 0, new Label(date));
			//ft.setWidget(i, 1, new Label(week));
		}
		RootPanel.get("calendartable").add(ft);
		
	}







	private void setCalendarListYear(){


		calendarService.getCalenderYearDates("2020", new AsyncCallback<List<List<String>>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Something strange funkar inte");

			}

			@Override
			public void onSuccess(List<List<String>> result) {
				// TODO Auto-generated method stub
				testmethod1(result);

			}
		});

	}



	private void testmethod1(List<List<String>> result){
		GWT.log("va faaan");
		String data = "";
		String url = "http://127.0.0.1:5984/idid/_bulk_docs";
		url = URL.encode(url);

		data = "{\"docs\":[";
		for(int i = 0; i < result.size(); i++){
			data = data + "{\"type\":\"calendar\",\"year\":\"" + "2020" + "\",\"date\":\"" + result.get(i).get(0) + "\",\"week\":\"" + result.get(i).get(1) + "\"}";

			if(i < result.size()-1)
				data = data + ",";
		}
		data = data + "]}";

		data = URL.decodePathSegment(data);
		//GWT.log("data: "  +  data);

		//curl -d '{"docs":[{"name":"mitt förnamn och efternamn"}]}' -X POST  http://127.0.0.1:5984/idid/_bulk_docs -H "Content-Type:application/json" [{"ok":true,"


		RequestBuilder req = new RequestBuilder(RequestBuilder.POST, url);
		req.setTimeoutMillis(5000);
		req.setHeader("Content-Type","application/json; charset=utf-8");

		try {
			req.sendRequest(data, new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					// TODO Auto-generated method stub
					GWT.log("################# " + response.getText());
				}

				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					Window.alert("misslyckad " + exception.getStackTrace());
				}
			});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	


}




