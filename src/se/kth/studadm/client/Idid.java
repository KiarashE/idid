package se.kth.studadm.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.kth.studadm.client.resources.MyCssResource;
import se.kth.studadm.client.resources.MyResources;
import se.kth.studadm.shared.FieldVerifier;
import se.kth.studadm.shared.WeeksData;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
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
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Idid implements EntryPoint {


	private TextBox t = new TextBox();
	public static MyResources resources;
	public static MyCssResource css;

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
		
		resources = MyResources.INSTANCE;
        resources.css().ensureInjected();
        css = resources.css();
		
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
		VerticalPanel vpanel = new VerticalPanel();
		
		ArrayList<WeeksData> weekPack = getWeekNumbers(rows);
		
		for(int i = 0; i < weekPack.size(); i++){
			String weekCaption = "Vecka " + weekPack.get(i).getWeekNr();
			
			CaptionPanel caption = new CaptionPanel();
			caption.setCaptionText(weekCaption);
			vpanel.add(caption);	
			
			ArrayList<String> weekData = weekPack.get(i).getWeekData();
			FlexTable ft = new FlexTable();
			ft.setBorderWidth(1);
			ft.setStylePrimaryName(css.getCaltable());
			ft.setCellPadding(5);
			
			for(int j = 0; j < weekData.size(); j++){
				TextBox timeBoxFrom = getTimeBox("from-"+weekData.get(j));
				timeBoxFrom.setStylePrimaryName(css.getTimebox());
				TextBox timeBoxTo =getTimeBox("to-"+weekData.get(j));
				timeBoxTo.setStylePrimaryName(css.getTimebox());
				
				DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
				Date date = new Date();
				date = dateFormat.parse(weekData.get(j));
				
				Label dateFrom = new Label(weekData.get(j));
				dateFrom.setStylePrimaryName(css.getTimebox());
				
				
				ft.setWidget(j, 0, getLabel("Från"));
				ft.setWidget(j, 1, dateFrom);
				ft.setWidget(j, 2, timeBoxFrom);
				ft.setWidget(j, 3, getLabel("Till"));
				ft.setWidget(j, 4, getDatePicker(date));
				ft.setWidget(j, 5, timeBoxTo);
				ft.setWidget(j, 6, getLunchBox());
				ft.setWidget(j, 7, getTotDayhours(timeBoxFrom, timeBoxTo));
			}
			caption.add(ft);
			
		}
		RootPanel.get("calendartable").add(vpanel);
	}
	
	public Label getLabel(String str){
		Label label = new Label(str);
		label.setStylePrimaryName(css.getTimebox());
		return label;
	}
	
	public DateBox getDatePicker(Date date){
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
		DateBox dateBox = new DateBox();
		dateBox.setStylePrimaryName(css.getDatepicker());
		dateBox.setValue(date);
		dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
	    dateBox.getDatePicker().setYearArrowsVisible(true);
		return dateBox;
	}
	
	public TextBox getTotDayhours(TextBox boxFrom, TextBox boxTo){
		final TextBox textBoxFrom = boxFrom;
		final TextBox textBoxTo = boxTo;
		
		JsDate from = JsDate.create();
		from.setHours(10);
		from.setMinutes(0);
		
		JsDate to = JsDate.create();
		to.setHours(11);
		to.setMinutes(0);
		
		double tot = to.getTime() - from.getTime();
		
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
		Date ftime = new Date();
		ftime = dateFormat.parse("2015-04-27 10:40:00");
	
		Date ttime = new Date();
		ttime = dateFormat.parse("2015-04-27 10:50:00");
		
		
		long x = (ttime.getTime() - ftime.getTime());
		
		Date difftime =  new Date();
		difftime.setTime(x);
		
		final JsDate diff = JsDate.create(tot);
		GWT.log(difftime.toString());
		
		
		final TextBox totDayHours = new TextBox();
		totDayHours.setStylePrimaryName(css.getDayhours());
		totDayHours.setMaxLength(5);
		totDayHours.setSize("40px", "14px");
		totDayHours.setAlignment(TextAlignment.CENTER);
		
		textBoxFrom.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				if(!textBoxFrom.getText().isEmpty() && !textBoxFrom.getText().isEmpty()){
					totDayHours.setText(diff.toTimeString());
				} else {
					totDayHours.setText("");
				}
			}
		});

		textBoxTo.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				if(!textBoxFrom.getText().isEmpty() && !textBoxFrom.getText().isEmpty()){
					totDayHours.setText(diff.toTimeString());
				} else {
					totDayHours.setText("");
				}
			}
		});
		
		

		return totDayHours;
	}
	
	
	
	public TextBox getLunchBox(){
		final TextBox lunchbox = new TextBox();
		lunchbox.setStylePrimaryName(css.getLunchbox());
		lunchbox.setMaxLength(5);
		lunchbox.setSize("40px", "14px");
		lunchbox.setAlignment(TextAlignment.CENTER);
		lunchbox.setText("00:30");
		return lunchbox;
	}
	
	public TextBox getTimeBox(String boxname){
		final TextBox timebox = new TextBox();
		timebox.setStylePrimaryName(css.getTimebox());
		timebox.setMaxLength(5);
		timebox.setSize("40px", "14px");
		timebox.setAlignment(TextAlignment.CENTER);
		timebox.setName(boxname);
		
		timebox.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				String formatted = "";
				String timeStr = timebox.getText();
				int timeStrSize = timeStr.length();
				
				boolean hasSepChar = true;
				if(timeStr.indexOf(':') < 0){
					hasSepChar = false;
				}
				
				if(hasSepChar == false && timeStrSize == 1){
					formatted = "0" + timeStr + ":00";
				} else if(hasSepChar == false && timeStrSize == 2) {
					formatted = timeStr + ":00";
				} else if(hasSepChar == false && timeStrSize == 3) {
					formatted = "0" + timeStr.substring(0,1) + ":" + timeStr.substring(1, 3);
				} else if(hasSepChar == false && timeStrSize == 4) {
					formatted = timeStr.substring(0, 2) + ":" + timeStr.substring(2, 5);
				} else if(hasSepChar == true && timeStrSize == 3 && timeStr.charAt(1) == ':') {
					formatted = "0" + timeStr.substring(0, 2) + "0" + timeStr.substring(2) ;
				} else if(hasSepChar == true && timeStrSize == 4 && timeStr.charAt(1) == ':') {
					formatted = "0" + timeStr.substring(0, 2) + timeStr.substring(2) ;
				} else if(hasSepChar == true && timeStrSize == 4 && timeStr.charAt(2) == ':') {
					formatted = timeStr.substring(0, 2) + ":" + "0" + timeStr.substring(3) ;
				} else if(hasSepChar == true && timeStrSize == 5 && timeStr.charAt(2) == ':') {
					formatted = timeStr;
				}
				timebox.setText(formatted);
			}
		});
		return timebox;
	}
	
	public ArrayList<WeeksData> getWeekNumbers(Rows rows){
		ArrayList<WeeksData> weeksDataList = new ArrayList<WeeksData>();
		WeeksData weeksData = null;
		String weekNrTemp = "0";
		
		for(int i = 0; i < rows.getRows().length(); i++){
			String weekNr = rows.getRows().get(i).getWeekNr();
			String date = rows.getRows().get(i).getDate();
			
			if(!weekNrTemp.equalsIgnoreCase(weekNr)){
				weeksData = new WeeksData();
				weeksDataList.add(weeksData);
				weeksData.setWeekNr(weekNr);
				weekNrTemp = weekNr;
			}
			weeksData.getWeekData().add(date);
		}
		return weeksDataList;
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




