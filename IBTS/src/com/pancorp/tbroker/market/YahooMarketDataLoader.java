package com.pancorp.tbroker.market;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import com.pancorp.tbroker.data.DataFactory;
import com.pancorp.tbroker.data.YahooMarketDataRecord;
import com.pancorp.tbroker.util.Globals;
import com.pancorp.tbroker.util.Utils;

/**
 * Yahoo! Finance provides the simplest way to import financial data into a spreadsheet. 
 * The data (including stock prices, indices and company fundamentals) can be automatically downloaded in a CSV 
 * by simply entering a URL into your browser’s address bar. 
 * The CSV can then be opened in Excel and manipulated as required.
 * 
 * Real-time data is limited to a few US exchanges and most countries have a 10-30min delay due to licensing restrictions.
 * 
 * Step 1: Call the Yahoo! Finance API
 * -----------------------------------
 * Start with the base URL: http://finance.yahoo.com/d/quotes.csv
 * 
 * Step 2: Add stock codes
 * -----------------------
 * Add ?s= to your base URL followed by the stock symbols you want to download.
 * To download multiple companies at once, simply use the “+” sign in between the company codes.
 * 
 * http://finance.yahoo.com/d/quotes.csv?s=^AORD+BHP.AX+BLT.L+AAPL
 * 
 * The above URL will call:
 * • Australian All Ordinaries Index
 * • BHP Billiton Ltd in Australia
 * • BHP Billiton Ltd in UK
 * • Apple Inc. in USA.
 * 
 * How to Find the Correct Stock Code
 * 
 * All listed companies have a stock ticker between 1 and 4 characters. E.g. Apple has the stock ticker AAPL.
 * 
 * As there are multiple exchanges around the world, you must specify which exchange your code relates to by adding a suffix.
 * 
 * • Australian listed companies require the suffix “.AX” to be added to the companies stock code (e.g. BHP.AX).
 * • UK listed companies require the suffix “.L” to be added to the companies stock code (e.g. BLT.L).
 * 
 * Note: Most US listed companies do not require a suffix
 * 
 * Most stock indices use a “^" prefix prior to the index code along with a country identifier.
 * 
 * • The Australian All Ordinaries index has the prefix “^A” (e.g. ^AORD).
 * • The NASDAQ Composite uses the code “^IXIC”.
 * 
 * To find the correct suffix for a company or prefix for an index, visit http://finance.yahoo.com 
 * and start typing the name of the company into the “Look Up” box.
 * 
 * Step 3: Specify the information you want to download using "Tags"
 * ----------------------------------------------------------------
 * 
 * Add &f= to your URL string followed by the Yahoo! Tags for the data you want to download.
 * 
 * http://finance.yahoo.com/d/quotes.csv?s=^AORD+BHP.AX+BLT.L+AAPL&f=nd1l1v
 * 
 * The above URL will fetch:
 * • Name
 * • Last Trade Date
 * • Last Trade Price
 * • Volume
 * 
 * A few caveats:
 * 
 * • Dow Jones data will not import due to licensing restrictions.
 * • Financial data (e.g. dividends, ratios and target prices) are often only available for US listed companies.
 * • Most exchanges have a delay on their data of 10 - 30mins.
 * • You’re limited to calling 200 codes at a time.
 * 
 * 
 * 
 * @author pankstep
 *
 */

public class YahooMarketDataLoader {
	
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(YahooMarketDataLoader.class);
	
	private LinkedList<String> tickers;
	private Timestamp tStamp;
	LinkedList<String[]> tkrBuffer = null;
	private ArrayList<String> emptyRecords = null;
	private ArrayList<String> nullLines = null;
	private ArrayList<String> notInserted = null;
	private int loaded = 0;
	YahooMarketDataRecord rec = null;
	
	public YahooMarketDataLoader() {
		//Timestamp ts = new Timestamp(System.currentTimeMillis());
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss");
		//tStamp = df.format(ts);
		rec = new YahooMarketDataRecord();
		emptyRecords= new ArrayList<>();
		nullLines= new ArrayList<>();
		notInserted = new ArrayList<>();
	}
	
	public void invoke(){
		int recordCounter = 0;
		int result = 0;
		try {		
			tickers = DataFactory.getTickerList();
			if(lg.isDebugEnabled())
				lg.debug("Found " + tickers.size() + " tickers");

			tkrBuffer = new LinkedList<>();
			
			//date/time must be set here to make sure it is identical
			//for all records inserted during this run
			tStamp = new Timestamp(System.currentTimeMillis());
			rec.setRunningLoadDateTime(tStamp);
		
			while(!this.tickers.isEmpty()){
				//fill up the tkr buffer with max number of tickers
				for(int i=0;i<Globals.YAH_MAX_TICKERS;i++){
					if(this.tickers.isEmpty())
						break;
				
					tkrBuffer.add((this.tickers.removeFirst()).split(","));
				}		
			
				recordCounter = tkrBuffer.size();
				if(lg.isDebugEnabled())
					lg.debug("Loading next " + recordCounter + " records..");
				
				String ur = this.buildUrl(tkrBuffer);
				result = query(ur, tkrBuffer);		
				lg.debug("Loaded "+result + " records");
				loaded = loaded + result;
			}
		}
		catch(Exception e){
			Utils.logError(lg, e);
		}
		finally{
			lg.info("Total loaded: "+loaded + " records"); 
			lg.info("emptyRecords: " + emptyRecords.size() + "::" + emptyRecords);
			lg.info("nullLines: " + nullLines.size() + "::" + nullLines);
			lg.info("Other not inserted records: " + notInserted.size() + "::" + notInserted);
			long tot = loaded + emptyRecords.size() + nullLines.size();
			lg.info("Total tickers checked: " + tot);
		}
	}
	
	/**
	 * 
	 * @param li
	 * @return
	 */
	private String buildUrl(LinkedList<String[]> li){
		Iterator<String[]> it = li.iterator();
		int tickCount = 0;
		StringBuilder ur = new StringBuilder();
		
		//append start of URL
		ur.append(Globals.YAH_DATA);
		
		while(it.hasNext()){	
			if(tickCount>0)
				ur.append("+");

			ur.append(it.next()[0]);
			tickCount++;
		}

		//append parameter tags
		ur.append(Globals.YAH_QUERY_TAGS);
		
		String r = ur.toString();		
		if(lg.isDebugEnabled())
			lg.debug("buildUrl: result: " + r);
		
		return r;
	}
	
	/**
	 * Builds URL for a single ticker.
	 * Use for testing only!
	 * 
	 * @param ticker
	 * @return
	 */
/*	private String buildUrl(String ticker){
		
		StringBuilder ur = new StringBuilder();
		ur.append(Globals.YAH_DATA);
		
		//while(!this.tickers.isEmpty()){		
			ur.append(ticker);
			//tickCount++;
			
			//if(tickCount<(Globals.YAH_MAX_TICKERS-1))
			//	ur.append("+");
			
			//if(tickCount>=Globals.YAH_MAX_TICKERS)
			//	break;
		//}
		
		ur.append(Globals.YAH_QUERY_TAGS);
		
		String r = ur.toString();
		
		if(lg.isDebugEnabled())
			lg.debug("buildUrl: result: " + r);
		
		return r;
	}*/
	
	private int query(String u, LinkedList<String[]> buff) throws Exception{
		
		URL url = null;
		HttpURLConnection http = null;	
		BufferedReader r = null;	
		String line = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		
		String[] arr;
		String exchange = null;
		boolean valid= false;
		
		try {
			
			url = new URL(u);
			http = (HttpURLConnection)url.openConnection();
			http.connect();
			r = new BufferedReader(new InputStreamReader(http.getInputStream()));
			
			Class.forName(DataFactory.db_driver);  
			con=DriverManager.getConnection(DataFactory.db_url,DataFactory.db_user,DataFactory.db_password);  
			ps = con.prepareStatement(DataFactory.sqlInsertYahMarketData);
	
			//os = new FileWriter(Globals.BASEDIR+Globals.DATADIR + "yahoo_data_" + counter + "_" + tStamp);
			while(!buff.isEmpty()){
			//while((line=r.readLine())!=null){
				arr = buff.removeFirst();
				line=r.readLine();
				if(lg.isDebugEnabled())
					lg.debug("line: " + line);
				if(line==null){
					nullLines.add(arr[0]+":"+arr[1]);
					continue;
				}
				if(arr[1]==null || arr[1].trim().length()<=0)
					exchange = null;
				else
					exchange = arr[1];
				valid = rec.load(arr[0], line, exchange);
				if(lg.isDebugEnabled())
					lg.debug("record valid: " + valid);
				if(!valid){
					emptyRecords.add(rec.getTicker()+":"+rec.getExchange());
					continue;
				}
				
				rec.loadStatement(ps);
				if(lg.isTraceEnabled())
					lg.trace("PreparedStatement loaded ");
				int inserted = ps.executeUpdate();
				if(inserted==0){
					notInserted.add(arr[0]+":"+arr[1]);
				}
				result = result + inserted;
				if(lg.isTraceEnabled())
				lg.trace("result: " + result);
				
				//os.write(line);
				//lineCounter++;
				//if(lineCounter>3)
				//	break;
			}
		}
		catch(SQLException sqle){
			lg.error("SQLException: code: " + sqle.getErrorCode() + ", msg: " + sqle.getMessage());
		}
		catch(Exception e){
			lg.error("Error: "+ e.getMessage());
		}
		finally {
			/*try {
				os.flush();
				os.close();
			}catch(Exception e1){}
			*/
			try {
				r.close();
			}catch(Exception e2){}
			
			try {
				http.disconnect();
			}catch(Exception e3){}
			
			try {
				ps.close();
				}
				catch(Exception e){
					
				}
			try {
				con.close();
				}
				catch(Exception e){
					
				}
		}
		
		return result;
	}
	
	//for testing only
	public static void main(String[] args){
		YahooMarketDataLoader ld = new YahooMarketDataLoader();
		
		ld.invoke();
	}
	
}
