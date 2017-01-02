package com.pancorp.tbroker.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import com.pancorp.tbroker.util.Utils;

public class DataFactory {

	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(DataFactory.class);
	
	public final static String db_url = "jdbc:mysql://localhost:3306/tbroker?autoReconnect=true&useSSL=false";
	public final static String db_driver = "com.mysql.jdbc.Driver";
	public final static String db_user    = "pancorp";
	public final static String db_password = "m1lle0n$$$";
	
	public static String sqlInsertYahMarketData = "INSERT INTO tbl_yahoo_market_data (" + 
			"symbol,exchange, TRADE_DATA_ASK,TRADE_DATA_BID,TRADE_DATA_ASK_SIZE,TRADE_DATA_BID_SIZE,TRADE_DATA_LAST_TRADE_DATE_TIME,"+
			"TRADE_DATA_LAST_TRADE_PRICE,TRADE_DATA_LAST_TRADE_SIZE,TRADE_DATA_CHANGE,TRADE_DATA_CHANGE_PERCENT,TRADE_DATA_OPEN,"+
			"TRADE_DATA_DAY_HIGH,TRADE_DATA_DAY_LOW,TRADE_DATA_VOLUME,TRADE_DATA_AVG_DAY_VOLUME,"+
			"TRADE_DATA_PREVIOUS_CLOSE,HISTORIC_PERFORM_52W_HIGH,HISTORIC_PERFORM_52W_LOW,"+
			"HISTORIC_PERFORM_CHANGE_FR52W_HIGH,HISTORIC_PERFORM_CHANGE_FR52W_LOW,HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH,"+
			"HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW,RUN_UPDATE_DATE_TIME) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
/*	public static int insertMarketDataRecord(YahooMarketDataRecord r){
		int result = 0;
		
		
		Connection con=null;
		PreparedStatement ps = null;
		
		try {
			Class.forName(db_driver);  
			con=DriverManager.getConnection(db_url,db_user,db_password);  
			ps = con.prepareStatement(sqlInsertYahMarketData);
			
			ps.setString	(1, r.getTicker());					//symbol String 1-14 chars
			ps.setString	(2, r.getExchange());               //exchange
			ps.setDouble	(3, r.getYAH_TRADE_DATA_ASK());		//TRADE_DATA_ASK double,
			ps.setDouble	(4, r.getYAH_TRADE_DATA_BID());		//TRADE_DATA_BID double,
			ps.setInt		(5, r.getYAH_TRADE_DATA_ASK_SIZE()); 	//TRADE_DATA_ASK_SIZE int, 
			ps.setInt		(6, r.getYAH_TRADE_DATA_BID_SIZE());	//TRADE_DATA_BID_SIZE int,			
			ps.setTimestamp	(7, r.getLast_trade_date_time());	//TRADE_DATA_LAST_TRADE_DATE_TIME Timestamp,		
			ps.setDouble	(8, r.getYAH_TRADE_DATA_LAST_TRADE_PRICE());		//TRADE_DATA_LAST_TRADE_PRICE double,
			ps.setInt		(9, r.getYAH_TRADE_DATA_LAST_TRADE_SIZE());			//TRADE_DATA_LAST_TRADE_SIZE int,
			ps.setDouble	(10,r.getYAH_TRADE_DATA_CHANGE());				//TRADE_DATA_CHANGE double,
			ps.setDouble	(11,r.getYAH_TRADE_DATA_CHANGE_PERCENT());		//TRADE_DATA_CHANGE_PERCENT double,
			ps.setDouble	(12,r.getYAH_TRADE_DATA_OPEN());				//TRADE_DATA_OPEN double,
			ps.setDouble	(13,r.getYAH_TRADE_DATA_DAY_HIGH());			//TRADE_DATA_DAY_HIGH double,
			ps.setDouble	(14,r.getYAH_TRADE_DATA_DAY_LOW());				//TRADE_DATA_DAY_LOW double,
			ps.setInt		(15,r.getYAH_TRADE_DATA_VOLUME());					//TRADE_DATA_VOLUME double,
			ps.setInt		(16,r.getYAH_TRADE_DATA_AVG_DAY_VOLUME());			//TRADE_DATA_AVG_DAY_VOLUME double,
			ps.setDouble	(17,r.getYAH_TRADE_DATA_PREVIOUS_CLOSE());		//TRADE_DATA_PREVIOUS_CLOSE double,
			//ps.setDouble(17,r.getYAH_HISTORIC_PERFORM_52W_RANGE double,
			ps.setDouble	(18,r.getYAH_HISTORIC_PERFORM_52W_HIGH());		//HISTORIC_PERFORM_52W_HIGH double,
			ps.setDouble	(19,r.getYAH_HISTORIC_PERFORM_52W_LOW());		//HISTORIC_PERFORM_52W_LOW double,
			ps.setDouble	(20,r.getYAH_HISTORIC_PERFORM_CHANGE_FR52W_HIGH());	//HISTORIC_PERFORM_CHANGE_FROM_52HIGH double,
			ps.setDouble	(21,r.getYAH_HISTORIC_PERFORM_CHANGE_FR52W_LOW());		//HISTORIC_PERFORM_CHANGE_FROM_52LOW double,
			ps.setDouble	(22,r.getYAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_HIGH());	//HISTORIC_PERFORM_PERC_CHANGE_52W_HIGH double,
			ps.setDouble	(23,r.getYAH_HISTORIC_PERFORM_PERC_CHANGE_FR52W_LOW());	//HISTORIC_PERFORM_PERC_CHANGE_52W_LOWd double,
			ps.setTimestamp	(24,r.getRunningLoadDateTime());	//RUN_UPDATE_DATE_TIME Timestamp	must be equal for all records inserted during  the same run
			
			result = ps.executeUpdate();
			if(lg.isTraceEnabled())
			lg.trace("Inserted " + result + " record(s)");
		}
		catch(SQLException sqle){
			lg.error("SQLException: code: " + sqle.getErrorCode() + ", msg: " + sqle.getMessage());
		}
		catch(Exception e){
			Utils.logError(lg, e);
		}  
		finally {
			try {
			con.close();
			}
			catch(Exception e){
				
			}
		}
		
		return result;
	}
	*/

	public static LinkedList<String> getTickerList(){
		LinkedList<String> results = null;
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sym = null;
		String exch = null;
		
		try{  
			Class.forName(db_driver);  
			con=DriverManager.getConnection(db_url,db_user,db_password);  
			
			String sql = "SELECT symbol,exchange FROM tbl_nasdaq_sec_listing WHERE test_issue='N' AND is_etf='N' AND (financial_status IS NULL OR financial_status='N')";
			ps = con.prepareStatement(sql);
			
			rs=ps.executeQuery();  
			results = new LinkedList<String>();
			
			while(rs.next())  {
				sym = rs.getString(1);
				exch = rs.getString(2);
				if(exch==null)
					exch = "";
				results.add(sym+","+exch);
			}
			if(lg.isDebugEnabled())
				lg.debug("Found " + results.size() + " tickers");
		}
		catch(SQLException sqle){
			lg.error("SQLException: code: " + sqle.getErrorCode() + ", msg: " + sqle.getMessage());
		}
		catch(Exception e){
			Utils.logError(lg, e);
		}  
		finally {
			try {
			con.close();
			}
			catch(Exception e){
				
			}
		}
		
		return results;
	}
	
	public static int loadNASDAQList(LinkedList<String[]> lists){
		int status = 0;
		int r = 0;
		Connection con=null;
		PreparedStatement ps0 = null;
		PreparedStatement ps = null;
		//String ts = lists.removeLast()[0];
		lg.info("List size: " + lists.size());
		
		try{  
			Class.forName(db_driver);  
			con=DriverManager.getConnection(db_url,db_user,db_password);  
			
			//truncate table data
			String truncate = "TRUNCATE TABLE tbl_nasdaq_sec_listing";
			ps0 = con.prepareStatement(truncate);
			ps0.executeUpdate();
			
			/*
			try {
			Thread.sleep(500);
			}
			catch(InterruptedException ie){}
			*/
			
			String sql = 
			"INSERT INTO tbl_nasdaq_sec_listing ("
			+ "symbol, name, exchange,market_category, test_issue,financial_status,round_lot_size,is_etf,next_shares) VALUES (?,?,?,?,?,?,?,?,?)";
			
			//Date dt = new Date(System.currentTimeMillis());
			
			ps=con.prepareStatement(sql);  
			
			while(!lists.isEmpty()){
				String[] a = lists.removeFirst();

				ps.setString(1, a[0]);	//symbol
				ps.setString(2, a[1]);	//name
				ps.setString(3, "NASDAQ");	//exchange
				ps.setString(4, a[2]);	//market category
				ps.setString(5, a[3]);	//test issue
				ps.setString(6, a[4]);	//financial status
				ps.setString(7, a[5]);	//round lot size
				ps.setString(8, a[6]);	//is etf
				ps.setString(9, a[7]);	//next shares
				//mysql inserts default current timestap
				//ps.setDate(10, dt);		//last updated
				//ps.setString(11, null);		//CQS symbol
				//ps.setString(12, null);		//NASDAQ symbol
				
				r = ps.executeUpdate();
				//lg.debug("inserted " + r + " record(s)");
				status = status + r;
			}
			
			/*
			ResultSet rs=stmt.executeQuery("select * from emp");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			*/
			//con.close();  
		}
		catch(SQLException sqle){
			lg.error("SQLException: code: " + sqle.getErrorCode() + ", msg: " + sqle.getMessage());
		}
		catch(Exception e){
			Utils.logError(lg, e);
		}  
		finally {
			try {
				ps.close();
				}
				catch(Exception e){}
			try {
			con.close();
			}
			catch(Exception e){}
		}
		
		
		return status;
	}
	

	public static int loadNASDAQOtherList(LinkedList<String[]> lists){
		int status = 0;
		int r = 0;
		Connection con=null;
		//String ts = lists.removeLast()[0];
		lg.info("List size: " + lists.size());
		
		try{  
			Class.forName(db_driver);  
			con=DriverManager.getConnection(db_url,db_user,db_password);  

			String sql = 
			"INSERT INTO tbl_nasdaq_sec_listing ("
			+ "symbol, name, exchange, market_category, test_issue,financial_status,round_lot_size,is_etf,next_shares) "+
			" VALUES (?,?,?,?,?,?,?,?,?)";
			
			//Date dt = new Date(System.currentTimeMillis());
			
			PreparedStatement ps=con.prepareStatement(sql);  
			
			while(!lists.isEmpty()){
				String[] a = lists.removeFirst();

				ps.setString(1, a[0]);	//ACT Symbol
				ps.setString(2, a[1]);  //name / desc
				//set exchange - convert from proprietary to generic
				String e=a[2];
				switch(e){
				case "A":
					ps.setString(3, "NYSE MKT");
					break;
				case "N":
					ps.setString(3, "NYSE");
					break;
				case "P":
					ps.setString(3, "NYSE ARCA");
					break;				
				case "Z":
					ps.setString(3, "BATS");
					break;
					default:
						ps.setString(3, null);
				}
				
				ps.setString(4, null); //market category
				ps.setString(5, a[6]); //test issue
				ps.setString(6, null); //financial status
				ps.setString(7, a[5]);  //round lot size
				ps.setString(8, a[4]); //ETF
				ps.setString(9, null); //next shares
				//ps.setDate(10, dt);		//lst updated
				//ps.setString(11, a[2]); //CQS symbol
				//ps.setString(12, a[7]); //NASDAQ symbol
				
				r = ps.executeUpdate();
				//lg.debug("inserted " + r + " record(s)");
				status = status + r;
			}
		}
		catch(SQLException sqle){
			lg.error("SQLException: code: " + sqle.getErrorCode() + ", msg: " + sqle.getMessage());
		}
		catch(Exception e){ //System.out.println(e);
			Utils.logError(lg, e);
		}  
		finally {
			try {
			con.close();
			}
			catch(Exception e){
				
			}
		}
		
		
		return status;
	}

	private DataFactory() {	}
}
