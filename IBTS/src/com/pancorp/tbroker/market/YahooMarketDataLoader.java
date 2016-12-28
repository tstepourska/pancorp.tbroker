package com.pancorp.tbroker.market;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;


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
	
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(MarketScannerWrapper.class);
	
	private int counter = 0;
	private LinkedList<String> tickers;
	
	public YahooMarketDataLoader(LinkedList<String> tt) {
		this.tickers = tt;
	}
	
	public void invoke(){
		while(!this.tickers.isEmpty()){
			try {
			query(buildUrl());
			}
			catch(Exception e){
				Utils.logError(lg, e);
			}
		}
	}
	
	private String buildUrl(){
		int tickCount = 0;
		StringBuilder ur = new StringBuilder();
		ur.append(Globals.YAH_DATA);
		
		while(!this.tickers.isEmpty()){		
			ur.append(this.tickers.remove());
			tickCount++;
			
			if(tickCount<(Globals.YAH_MAX_TICKERS-1))
				ur.append("+");
			
			if(tickCount>=Globals.YAH_MAX_TICKERS)
				break;
		}
		
		ur.append(Globals.YAH_QUERY_TAGS);
		
		String r = ur.toString();
		
		if(lg.isDebugEnabled())
			lg.debug("buildUrl: result: " + r);
		
		return r;
	}
	
	private void query(String u) throws Exception{
		
		URL url = null;
		HttpURLConnection http = null;	
		InputStreamReader reader = null;
		FileWriter os = null;
		
		try {
			url = new URL(u);
			http = (HttpURLConnection)url.openConnection();
			http.connect();
			reader = new InputStreamReader(http.getInputStream());
			
			counter++;
			os = new FileWriter(Globals.BASEDIR+Globals.DATADIR + "yahoo_data_" + counter);
			int c;
			while((c=reader.read())!=1){
				os.write(c);
			}
		}
		catch(Exception e){
			lg.error("Error: "+ e.getMessage());
		}
		finally {
			try {
				os.flush();
				os.close();
			}catch(Exception e1){}
			
			try {
				reader.close();
			}catch(Exception e2){}
			
			try {
				http.disconnect();
			}catch(Exception e3){}
		}
	}
	
}
