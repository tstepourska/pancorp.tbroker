package com.pancorp.tbroker.data;

import java.sql.Timestamp;
import java.util.Calendar;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;

import com.pancorp.tbroker.util.Globals;
import com.pancorp.tbroker.util.Utils;

public class YahooMarketDataRecord {
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(YahooMarketDataRecord.class);
	
	private String record;
	
	private String ticker;
	private String exchange;
	
	private double YAH_TRADE_DATA_ASK;								
	private double YAH_TRADE_DATA_BID;								
	private int YAH_TRADE_DATA_ASK_SIZE;						
	private int YAH_TRADE_DATA_BID_SIZE;						
	private String YAH_TRADE_DATA_LAST_TRADE_DATE;					
	private String YAH_TRADE_DATA_LAST_TRADE_TIME;				
	private double YAH_TRADE_DATA_LAST_TRADE_PRICE;				
	private int YAH_TRADE_DATA_LAST_TRADE_SIZE;			
	private double YAH_TRADE_DATA_CHANGE;					
	private double YAH_TRADE_DATA_CHANGE_PERCENT;				
	private double YAH_TRADE_DATA_OPEN;
	private double YAH_TRADE_DATA_DAY_HIGH;
	private double YAH_TRADE_DATA_DAY_LOW;
	///String YAH_TRADE_DATA_DAY_RANGE;
	private int YAH_TRADE_DATA_VOLUME;
	private int YAH_TRADE_DATA_AVG_DAY_VOLUME;
	private double YAH_TRADE_DATA_PREVIOUS_CLOSE;
	//String YAH_HISTORIC_PERFORM_52W_RANGE;
	private double YAH_HISTORIC_PERFORM_52W_HIGH;
	private double YAH_HISTORIC_PERFORM_52W_LOW;
	private double YAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH;
	private double YAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW;
	private double YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH;
	private double YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW;
	//java.sql.Date last_trade_date_time;
	private java.sql.Timestamp last_trade_date_time;
	
	//approximate time stamp of the running update
	//used for quering by the latest date/time 
	//to select symbols from latest update batch
	private java.sql.Timestamp runningLoadDateTime;
	
	public YahooMarketDataRecord(String tkr, String rec, String ex) throws Exception {	
		this.ticker = tkr;
		this.record = rec;
		this.exchange = ex;
		
		load();
	}
	public YahooMarketDataRecord()  {	
	}
	
	private boolean NA(String s){
		if(s==null||s.equalsIgnoreCase("N/A")||s.trim().length()<=0)
			return true;
		
		return false;
	}
	
	private void load() throws Exception {
		load(this.ticker,
		this.record,
		this.exchange);
	}
	
	public boolean load(String tkr, String rec, String ex) throws Exception {
		boolean valid = false;
		this.ticker = tkr;
		this.record = rec;
		this.exchange = ex;
		
		if(lg.isTraceEnabled())
			lg.trace("parseTickerRecord: ticker: " + this.ticker + ":"+this.exchange);
		
		//26.72,26.62,200,100,"12/29/2016","9:51am",26.70,100,+0.40,"+1.52%",26.25,26.70,26.25,
		//"26.25 - 26.70",		//not needed
		//5429,139067,26.30,"20.53 - 39.66",39.66,20.53,-12.96,6.17,-32.68%,+30.05%
		
		String arr[] = this.record.split(",");
		if(lg.isTraceEnabled())
		lg.trace("parseTickerRecord: arr length: " + arr.length);
		
		if(NA(arr[0]))
			YAH_TRADE_DATA_ASK = Globals.NA;
		else{
		YAH_TRADE_DATA_ASK 			= Double.parseDouble(arr[0]);	
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_ASK: " + YAH_TRADE_DATA_ASK);
		
		if(NA(arr[1]))
			YAH_TRADE_DATA_BID = Globals.NA;
		else{
		YAH_TRADE_DATA_BID 			= Double.parseDouble(arr[1]);	
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_BID: " + YAH_TRADE_DATA_BID);
		
		if(NA(arr[2]))
			YAH_TRADE_DATA_ASK_SIZE = Globals.NA;
		else{
		YAH_TRADE_DATA_ASK_SIZE 	= Integer.parseInt(arr[2]);		
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_ASK_SIZE: " + YAH_TRADE_DATA_ASK_SIZE);
		
		if(NA(arr[3]))
			YAH_TRADE_DATA_BID_SIZE = Globals.NA;
		else{
		YAH_TRADE_DATA_BID_SIZE		= Integer.parseInt(arr[3]);		
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_BID_SIZE: " + YAH_TRADE_DATA_BID_SIZE);
		
		try {
			//combine date and time into a single Timestamp field
			YAH_TRADE_DATA_LAST_TRADE_DATE = arr[4];	
			if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_LAST_TRADE_DATE: " + YAH_TRADE_DATA_LAST_TRADE_DATE);
		
			YAH_TRADE_DATA_LAST_TRADE_TIME = arr[5];	
			if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_LAST_TRADE_TIME: " + YAH_TRADE_DATA_LAST_TRADE_TIME);
		
			Calendar cal = Calendar.getInstance();
			String[] aa = YAH_TRADE_DATA_LAST_TRADE_DATE.split("\\/");
			if(lg.isTraceEnabled()){
			lg.trace("date array length: "+aa.length);
			for(int i=0;i<aa.length;i++){
				lg.trace("date: " + aa[i]);
			}
			}
			int year = Integer.parseInt(aa[2].substring(0, 4)); 
			int month= Integer.parseInt(aa[0].substring(1))-1; 
			int date = Integer.parseInt(aa[1]); 
			if(lg.isTraceEnabled()){
			lg.trace("date: year="+year+", month="+month+", date="+date);
			}
		
			String[] tt = YAH_TRADE_DATA_LAST_TRADE_TIME.split("\\:");
			if(lg.isTraceEnabled()){
			for(int i=0;i<tt.length;i++){
				lg.trace("time: " + tt[i]);
			}
			}
			int hourOfDay = Integer.parseInt(tt[0].substring(1)); 
			int minute = Integer.parseInt(tt[1].substring(0, 2));
			String ampm = tt[1].substring(2,4);
			int second = 0;
		
			if(ampm.equalsIgnoreCase("pm"))
			hourOfDay = hourOfDay + 12;
			if(lg.isTraceEnabled()){
			lg.trace("time: hour="+hourOfDay+", minute="+minute+", second="+second+", ampm="+ampm);
			}
		
			cal.set(year, month, date, hourOfDay, minute, second);
			if(lg.isTraceEnabled()){
			//lg.trace("calendar: ts: " + cal.);
			}
			//this.last_trade_date_time = new java.sql.Date((cal.getTime()).getTime());
			this.last_trade_date_time = new java.sql.Timestamp(cal.getTimeInMillis());
			valid = true;
		}
		catch(Exception e){
			lg.error("Error parsing last trade date - time: " + e.getMessage());
			last_trade_date_time = null;
		}
		if(lg.isTraceEnabled())
			lg.trace("last_trade_date_time: " + last_trade_date_time);
		
		if(NA(arr[6]))
			YAH_TRADE_DATA_LAST_TRADE_PRICE = Globals.NA;
		else{
		YAH_TRADE_DATA_LAST_TRADE_PRICE = Double.parseDouble(arr[6]);		
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_LAST_TRADE_PRICE: " + YAH_TRADE_DATA_LAST_TRADE_PRICE);
		
		if(NA(arr[7]))
			YAH_TRADE_DATA_LAST_TRADE_SIZE = Globals.NA;
		else{
		YAH_TRADE_DATA_LAST_TRADE_SIZE  = Integer.parseInt(arr[7]);	
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_LAST_TRADE_SIZE: " + YAH_TRADE_DATA_LAST_TRADE_SIZE);
		
		if(NA(arr[8]))
			YAH_TRADE_DATA_CHANGE = Globals.NA;
		else{
		YAH_TRADE_DATA_CHANGE			= Double.parseDouble(arr[8]);	//! make sure it shows negative numbers correctly
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_CHANGE: " + YAH_TRADE_DATA_CHANGE);
		
		if(NA(arr[9]))
			YAH_TRADE_DATA_CHANGE_PERCENT = Globals.NA;
		else{
			try {
		//convert String into double
		String tmp = arr[9];
		if(tmp.startsWith("\""))
			tmp = tmp.substring(1);
		if(tmp.startsWith("\""))
			tmp = tmp.substring(1);
		if(tmp.endsWith("\""))
			tmp = tmp.substring(0,tmp.length()-1);
		int idx = tmp.indexOf("%");
		if(idx>-1)
			YAH_TRADE_DATA_CHANGE_PERCENT= Double.parseDouble(tmp.substring(0, idx)); //! make sure it shows negative numbers correctly
		else
		YAH_TRADE_DATA_CHANGE_PERCENT	= Double.parseDouble(tmp);			//! make sure it shows negative numbers correctly	
		
		valid = true;
			}
			catch(Exception e){
				lg.error("Error parsing YAH_TRADE_DATA_CHANGE_PERCENT: " + e.getMessage());
				Utils.logError(lg, e);
			}
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_CHANGE_PERCENT: " + YAH_TRADE_DATA_CHANGE_PERCENT);
		
		if(NA(arr[10]))
			YAH_TRADE_DATA_OPEN = Globals.NA;
		else{
		YAH_TRADE_DATA_OPEN				= Double.parseDouble(arr[10]);
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_OPEN: " + YAH_TRADE_DATA_OPEN);
		
		if(NA(arr[11]))
			YAH_TRADE_DATA_DAY_HIGH = Globals.NA;
		else{
		YAH_TRADE_DATA_DAY_HIGH			= Double.parseDouble(arr[11]);
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_DAY_HIGH: " + YAH_TRADE_DATA_DAY_HIGH);
		
		if(NA(arr[12]))
			YAH_TRADE_DATA_DAY_LOW = Globals.NA;
		else{
		YAH_TRADE_DATA_DAY_LOW			= Double.parseDouble(arr[12]);
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_DAY_LOW: " + YAH_TRADE_DATA_DAY_LOW);
		// not needed, derive from day high and day low
		//YAH_TRADE_DATA_DAY_RANGE		= arr[13];
		
		if(NA(arr[13]))
			YAH_TRADE_DATA_VOLUME = Globals.NA;
		else{
		YAH_TRADE_DATA_VOLUME			= Integer.parseInt(arr[13]);
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_VOLUME: " + YAH_TRADE_DATA_VOLUME);
		
		if(NA(arr[14]))
			YAH_TRADE_DATA_AVG_DAY_VOLUME = Globals.NA;
		else{
		YAH_TRADE_DATA_AVG_DAY_VOLUME	= Integer.parseInt(arr[14]);
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_AVG_DAY_VOLUME: " + YAH_TRADE_DATA_AVG_DAY_VOLUME);
		
		if(NA(arr[15]))
			YAH_TRADE_DATA_PREVIOUS_CLOSE = Globals.NA;
		else{
		YAH_TRADE_DATA_PREVIOUS_CLOSE	= Double.parseDouble(arr[15]);
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_TRADE_DATA_PREVIOUS_CLOSE: " + YAH_TRADE_DATA_PREVIOUS_CLOSE);
		//convert String range into 2 numeric fields
		//YAH_HISTORIC_PERFORM_52W_RANGE	= arr[17];		//
		
		if(NA(arr[16]))
			YAH_HISTORIC_PERFORM_52W_HIGH = Globals.NA;
		else{
		YAH_HISTORIC_PERFORM_52W_HIGH				= Double.parseDouble(arr[16]);
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_HISTORIC_PERFORM_52W_HIGH: " + YAH_HISTORIC_PERFORM_52W_HIGH);
		
		if(NA(arr[17]))
			YAH_HISTORIC_PERFORM_52W_LOW = Globals.NA;
		else{
		YAH_HISTORIC_PERFORM_52W_LOW				= Double.parseDouble(arr[17]);
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_HISTORIC_PERFORM_52W_LOW: " + YAH_HISTORIC_PERFORM_52W_LOW);
		
		if(NA(arr[18]))
			YAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH = Globals.NA;
		else{
		YAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH		= Double.parseDouble(arr[18]);	//! make sure it shows negative numbers correctly
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH: " + YAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH);
		
		if(NA(arr[19]))
			YAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW = Globals.NA;
		else{
		YAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW		= Double.parseDouble(arr[19]);
		valid = true;
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW: " + YAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW);
		
		if(NA(arr[20]))
			YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH = Globals.NA;
		else{
			try {
			//convert to numeric
			if(arr[20].indexOf("%")>-1)
				YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH 	= Double.parseDouble(arr[20].substring(0, arr[20].length()-2)); //! make sure it shows negative numbers correctly
			else
				YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH 	= Double.parseDouble(arr[20]);
			
			valid = true;
			}
			catch(Exception e){
				lg.error("Error parsing YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH: " + e.getMessage());
				Utils.logError(lg, e);
			}
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH: " + YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH);
		
		if(NA(arr[21]))
			YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW = Globals.NA;
		else{
			try {
			//convert to numeric
			if(arr[21].indexOf("%")>-1)
				YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW	= Double.parseDouble(arr[21].substring(0, arr[21].length()-2));
			else
				YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW 	= Double.parseDouble(arr[21]);
			
			valid = true;
			}
			catch(Exception e){
				lg.error("Error parsing YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW: " + e.getMessage());
				Utils.logError(lg, e);
			}
		}
		if(lg.isTraceEnabled())
			lg.trace("YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW: " + YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW);
		
		return valid;
	}
	
	public void loadStatement(PreparedStatement ps) throws SQLException, Exception{
		ps.setString(1,ticker);					//symbol String 1-14 chars
		ps.setString(2, exchange);               //exchange
		ps.setDouble(3,YAH_TRADE_DATA_ASK);		//TRADE_DATA_ASK double,
		ps.setDouble(4,YAH_TRADE_DATA_BID);		//TRADE_DATA_BID double,
		ps.setInt(5,YAH_TRADE_DATA_ASK_SIZE); 	//TRADE_DATA_ASK_SIZE int, 
		ps.setInt(6,YAH_TRADE_DATA_BID_SIZE);	//TRADE_DATA_BID_SIZE int,
		
		ps.setTimestamp(7,last_trade_date_time);	//TRADE_DATA_LAST_TRADE_DATE_TIME Timestamp,
		
		ps.setDouble(8,YAH_TRADE_DATA_LAST_TRADE_PRICE);		//TRADE_DATA_LAST_TRADE_PRICE double,
		ps.setInt(9,YAH_TRADE_DATA_LAST_TRADE_SIZE);			//TRADE_DATA_LAST_TRADE_SIZE int,
		ps.setDouble(10,YAH_TRADE_DATA_CHANGE);				//TRADE_DATA_CHANGE double,
		ps.setDouble(11,YAH_TRADE_DATA_CHANGE_PERCENT);		//TRADE_DATA_CHANGE_PERCENT double,
		ps.setDouble(12,YAH_TRADE_DATA_OPEN);				//TRADE_DATA_OPEN double,
		ps.setDouble(13,YAH_TRADE_DATA_DAY_HIGH);			//TRADE_DATA_DAY_HIGH double,
		ps.setDouble(14,YAH_TRADE_DATA_DAY_LOW);				//TRADE_DATA_DAY_LOW double,
		ps.setInt(15,YAH_TRADE_DATA_VOLUME);					//TRADE_DATA_VOLUME double,
		ps.setInt(16,YAH_TRADE_DATA_AVG_DAY_VOLUME);			//TRADE_DATA_AVG_DAY_VOLUME double,
		ps.setDouble(17,YAH_TRADE_DATA_PREVIOUS_CLOSE);		//TRADE_DATA_PREVIOUS_CLOSE double,

		ps.setDouble(18,YAH_HISTORIC_PERFORM_52W_HIGH);		//HISTORIC_PERFORM_52W_HIGH double,
		ps.setDouble(19,YAH_HISTORIC_PERFORM_52W_LOW);		//HISTORIC_PERFORM_52W_LOW double,
		ps.setDouble(20,YAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH);	//HISTORIC_PERFORM_CHANGE_FROM_52HIGH double,
		ps.setDouble(21,YAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW);		//HISTORIC_PERFORM_CHANGE_FROM_52LOW double,
		ps.setDouble(22,YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH);	//HISTORIC_PERFORM_PERC_CHANGE_52W_HIGH double,
		ps.setDouble(23,YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW);	//HISTORIC_PERFORM_PERC_CHANGE_52W_LOWd double,
		ps.setTimestamp(24, this.runningLoadDateTime);
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public double getYAH_TRADE_DATA_ASK() {
		return YAH_TRADE_DATA_ASK;
	}

	public void setYAH_TRADE_DATA_ASK(double yAH_TRADE_DATA_ASK) {
		YAH_TRADE_DATA_ASK = yAH_TRADE_DATA_ASK;
	}

	public double getYAH_TRADE_DATA_BID() {
		return YAH_TRADE_DATA_BID;
	}

	public void setYAH_TRADE_DATA_BID(double yAH_TRADE_DATA_BID) {
		YAH_TRADE_DATA_BID = yAH_TRADE_DATA_BID;
	}

	public int getYAH_TRADE_DATA_ASK_SIZE() {
		return YAH_TRADE_DATA_ASK_SIZE;
	}

	public void setYAH_TRADE_DATA_ASK_SIZE(int yAH_TRADE_DATA_ASK_SIZE) {
		YAH_TRADE_DATA_ASK_SIZE = yAH_TRADE_DATA_ASK_SIZE;
	}

	public int getYAH_TRADE_DATA_BID_SIZE() {
		return YAH_TRADE_DATA_BID_SIZE;
	}

	public void setYAH_TRADE_DATA_BID_SIZE(int yAH_TRADE_DATA_BID_SIZE) {
		YAH_TRADE_DATA_BID_SIZE = yAH_TRADE_DATA_BID_SIZE;
	}

	public String getYAH_TRADE_DATA_LAST_TRADE_DATE() {
		return YAH_TRADE_DATA_LAST_TRADE_DATE;
	}

	public void setYAH_TRADE_DATA_LAST_TRADE_DATE(String yAH_TRADE_DATA_LAST_TRADE_DATE) {
		YAH_TRADE_DATA_LAST_TRADE_DATE = yAH_TRADE_DATA_LAST_TRADE_DATE;
	}

	public String getYAH_TRADE_DATA_LAST_TRADE_TIME() {
		return YAH_TRADE_DATA_LAST_TRADE_TIME;
	}

	public void setYAH_TRADE_DATA_LAST_TRADE_TIME(String yAH_TRADE_DATA_LAST_TRADE_TIME) {
		YAH_TRADE_DATA_LAST_TRADE_TIME = yAH_TRADE_DATA_LAST_TRADE_TIME;
	}

	public double getYAH_TRADE_DATA_LAST_TRADE_PRICE() {
		return YAH_TRADE_DATA_LAST_TRADE_PRICE;
	}

	public void setYAH_TRADE_DATA_LAST_TRADE_PRICE(double yAH_TRADE_DATA_LAST_TRADE_PRICE) {
		YAH_TRADE_DATA_LAST_TRADE_PRICE = yAH_TRADE_DATA_LAST_TRADE_PRICE;
	}

	public int getYAH_TRADE_DATA_LAST_TRADE_SIZE() {
		return YAH_TRADE_DATA_LAST_TRADE_SIZE;
	}

	public void setYAH_TRADE_DATA_LAST_TRADE_SIZE(int yAH_TRADE_DATA_LAST_TRADE_SIZE) {
		YAH_TRADE_DATA_LAST_TRADE_SIZE = yAH_TRADE_DATA_LAST_TRADE_SIZE;
	}

	public double getYAH_TRADE_DATA_CHANGE() {
		return YAH_TRADE_DATA_CHANGE;
	}

	public void setYAH_TRADE_DATA_CHANGE(double yAH_TRADE_DATA_CHANGE) {
		YAH_TRADE_DATA_CHANGE = yAH_TRADE_DATA_CHANGE;
	}

	public double getYAH_TRADE_DATA_CHANGE_PERCENT() {
		return YAH_TRADE_DATA_CHANGE_PERCENT;
	}

	public void setYAH_TRADE_DATA_CHANGE_PERCENT(double yAH_TRADE_DATA_CHANGE_PERCENT) {
		YAH_TRADE_DATA_CHANGE_PERCENT = yAH_TRADE_DATA_CHANGE_PERCENT;
	}

	public double getYAH_TRADE_DATA_OPEN() {
		return YAH_TRADE_DATA_OPEN;
	}

	public void setYAH_TRADE_DATA_OPEN(double yAH_TRADE_DATA_OPEN) {
		YAH_TRADE_DATA_OPEN = yAH_TRADE_DATA_OPEN;
	}

	public double getYAH_TRADE_DATA_DAY_HIGH() {
		return YAH_TRADE_DATA_DAY_HIGH;
	}

	public void setYAH_TRADE_DATA_DAY_HIGH(double yAH_TRADE_DATA_DAY_HIGH) {
		YAH_TRADE_DATA_DAY_HIGH = yAH_TRADE_DATA_DAY_HIGH;
	}

	public double getYAH_TRADE_DATA_DAY_LOW() {
		return YAH_TRADE_DATA_DAY_LOW;
	}

	public void setYAH_TRADE_DATA_DAY_LOW(double yAH_TRADE_DATA_DAY_LOW) {
		YAH_TRADE_DATA_DAY_LOW = yAH_TRADE_DATA_DAY_LOW;
	}
/*
	public String getYAH_TRADE_DATA_DAY_RANGE() {
		return YAH_TRADE_DATA_DAY_RANGE;
	}

	public void setYAH_TRADE_DATA_DAY_RANGE(String yAH_TRADE_DATA_DAY_RANGE) {
		YAH_TRADE_DATA_DAY_RANGE = yAH_TRADE_DATA_DAY_RANGE;
	}*/

	public int getYAH_TRADE_DATA_VOLUME() {
		return YAH_TRADE_DATA_VOLUME;
	}

	public void setYAH_TRADE_DATA_VOLUME(int yAH_TRADE_DATA_VOLUME) {
		YAH_TRADE_DATA_VOLUME = yAH_TRADE_DATA_VOLUME;
	}

	public int getYAH_TRADE_DATA_AVG_DAY_VOLUME() {
		return YAH_TRADE_DATA_AVG_DAY_VOLUME;
	}

	public void setYAH_TRADE_DATA_AVG_DAY_VOLUME(int yAH_TRADE_DATA_AVG_DAY_VOLUME) {
		YAH_TRADE_DATA_AVG_DAY_VOLUME = yAH_TRADE_DATA_AVG_DAY_VOLUME;
	}

	public double getYAH_TRADE_DATA_PREVIOUS_CLOSE() {
		return YAH_TRADE_DATA_PREVIOUS_CLOSE;
	}

	public void setYAH_TRADE_DATA_PREVIOUS_CLOSE(double yAH_TRADE_DATA_PREVIOUS_CLOSE) {
		YAH_TRADE_DATA_PREVIOUS_CLOSE = yAH_TRADE_DATA_PREVIOUS_CLOSE;
	}

	/*
	public String getYAH_HISTORIC_PERFORM_52W_RANGE() {
		return YAH_HISTORIC_PERFORM_52W_RANGE;
	}

	public void setYAH_HISTORIC_PERFORM_52W_RANGE(String yAH_HISTORIC_PERFORM_52W_RANGE) {
		YAH_HISTORIC_PERFORM_52W_RANGE = yAH_HISTORIC_PERFORM_52W_RANGE;
	}*/

	public double getYAH_HISTORIC_PERFORM_52W_HIGH() {
		return YAH_HISTORIC_PERFORM_52W_HIGH;
	}

	public void setYAH_HISTORIC_PERFORM_52W_HIGH(double yAH_HISTORIC_PERFORM_52W_HIGH) {
		YAH_HISTORIC_PERFORM_52W_HIGH = yAH_HISTORIC_PERFORM_52W_HIGH;
	}

	public double getYAH_HISTORIC_PERFORM_52W_LOW() {
		return YAH_HISTORIC_PERFORM_52W_LOW;
	}

	public void setYAH_HISTORIC_PERFORM_52W_LOW(double yAH_HISTORIC_PERFORM_52W_LOW) {
		YAH_HISTORIC_PERFORM_52W_LOW = yAH_HISTORIC_PERFORM_52W_LOW;
	}

	public double getYAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH() {
		return YAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH;
	}

	public void setYAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH(double yAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH) {
		YAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH = yAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH;
	}

	public double getYAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW() {
		return YAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW;
	}

	public void setYAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW(double yAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW) {
		YAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW = yAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW;
	}

	public double getYAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH() {
		return YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH;
	}

	public void setYAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH(double yAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH) {
		YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH = yAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH;
	}

	public double getYAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW() {
		return YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW;
	}

	public void setYAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW(double yAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW) {
		YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW = yAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW;
	}
	
	public //Date 
	Timestamp 
	getLast_trade_date_time() {
		return last_trade_date_time;
	}

	public void setLast_trade_date_time(//Date 
			Timestamp
			last_trade_date_time) {
		this.last_trade_date_time = last_trade_date_time;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public java.sql.Timestamp getRunningLoadDateTime() {
		return runningLoadDateTime;
	}
	public void setRunningLoadDateTime(java.sql.Timestamp runningLoadDateTime) {
		this.runningLoadDateTime = runningLoadDateTime;
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb
		.append("\nTicker: ").append(this.ticker)
		
		.append("\nYAH_TRADE_DATA_ASK: " + YAH_TRADE_DATA_ASK)								
		.append("\nYAH_TRADE_DATA_BID: " + YAH_TRADE_DATA_BID)								
		.append("\nYAH_TRADE_DATA_ASK_SIZE: " + YAH_TRADE_DATA_ASK_SIZE)					
		.append("\nYAH_TRADE_DATA_BID_SIZE: " + YAH_TRADE_DATA_BID_SIZE)					
		
		//combine date and time into a single Timestamp field
		//.append("\nYAH_TRADE_DATA_LAST_TRADE_DATE: " + YAH_TRADE_DATA_LAST_TRADE_DATE)						
		//.append("\nYAH_TRADE_DATA_LAST_TRADE_TIME: " + YAH_TRADE_DATA_LAST_TRADE_TIME)				
		.append("\nTimestamp: " + this.last_trade_date_time)		
		
		.append("\nYAH_TRADE_DATA_LAST_TRADE_PRICE: " + YAH_TRADE_DATA_LAST_TRADE_PRICE)				
		.append("\nYAH_TRADE_DATA_LAST_TRADE_SIZE: " + YAH_TRADE_DATA_LAST_TRADE_SIZE)			
		.append("\nYAH_TRADE_DATA_CHANGE: " + YAH_TRADE_DATA_CHANGE)		//! make sure it shows negative numbers correctly
		
		//convert String into double
		.append("\nYAH_TRADE_DATA_CHANGE_PERCENT: " + YAH_TRADE_DATA_CHANGE_PERCENT)			//! make sure it shows negative numbers correctly	
		
		.append("\nYAH_TRADE_DATA_OPEN: " + YAH_TRADE_DATA_OPEN)	
		.append("\nYAH_TRADE_DATA_DAY_HIGH: " + YAH_TRADE_DATA_DAY_HIGH)	
		.append("\nYAH_TRADE_DATA_DAY_LOW: " + YAH_TRADE_DATA_DAY_LOW)	
		
		//not needed
		//.append("\nYAH_TRADE_DATA_DAY_RANGE: " + YAH_TRADE_DATA_DAY_RANGE)	
		
		
		.append("\nYAH_TRADE_DATA_VOLUME: " + YAH_TRADE_DATA_VOLUME)	
		.append("\nYAH_TRADE_DATA_AVG_DAY_VOLUME: " + YAH_TRADE_DATA_AVG_DAY_VOLUME)	
		.append("\nYAH_TRADE_DATA_PREVIOUS_CLOSE: " + YAH_TRADE_DATA_PREVIOUS_CLOSE)	
		
		//convert String range into 2 numeric fields
		//.append("\nYAH_HISTORIC_PERFORM_52W_RANGE: " + YAH_HISTORIC_PERFORM_52W_RANGE)	
		
		.append("\nYAH_HISTORIC_PERFORM_52W_HIGH: " + YAH_HISTORIC_PERFORM_52W_HIGH)	
		.append("\nYAH_HISTORIC_PERFORM_52W_LOW: " + YAH_HISTORIC_PERFORM_52W_LOW)	
		.append("\nYAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH: " + YAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH)		//! make sure it shows negative numbers correctly
		.append("\nYAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW: " + YAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW)	
		.append("\nYAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH: " + YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH)	 //! make sure it shows negative numbers correctly
		.append("\nYAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW: " + YAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW)	
		;
		
		return sb.toString();
	}
}
