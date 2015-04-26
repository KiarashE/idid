package se.kth.studadm.shared;

import java.util.ArrayList;

public class WeeksData {
	public String weekNr;
	public ArrayList<String> weekData = new ArrayList<String>();
	
	public String getWeekNr() {
		return weekNr;
	}
	
	public void setWeekNr(String weekNr) {
		this.weekNr = weekNr;
	}
	
	public ArrayList<String> getWeekData() {
		return weekData;
	}
	
	public void setWeekData(ArrayList<String> weekData) {
		this.weekData = weekData;
	}
	

}
