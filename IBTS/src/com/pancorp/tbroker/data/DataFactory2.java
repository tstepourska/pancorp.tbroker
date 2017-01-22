package com.pancorp.tbroker.data;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
//import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import com.ib.controller.Bar;
import com.ib.controller.NewContract;
import com.pancorp.tbroker.model.Candle;
import com.pancorp.tbroker.util.Utils;

public class DataFactory2 {

	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(DataFactory2.class);
	
	public final static String db_url = "jdbc:mysql://localhost:3306/tbroker?autoReconnect=true&useSSL=false";
	public final static String db_driver = "com.mysql.jdbc.Driver";
	public final static String db_user    = "pancorp";
	public final static String db_password = "m1lle0n$$$";
	
	
	public void insertBar(Bar bar, NewContract contr){
		Connection con=null;
		PreparedStatement ps = null;
			
		double open = bar.open();
		double close = bar.close();
		
		double high = bar.high();
		double low = bar.low();
		int count = bar.count();
		//bar.formattedTime();
		long time = bar.time();
		long volume = bar.volume();
		double wap = bar.wap();
		
		Candle c = new Candle(bar);
		
		//double amp = c.getAmp();
		double bl = c.getBody_len();
		double usl = c.getUpper_shadow_len();
		double lsl = c.getLower_shadow_len();	
		int dir = c.getDirection();
		
		try{  
			Class.forName(db_driver);  
			con=DriverManager.getConnection(db_url,db_user,db_password);  
	
			String sql = "INSERT INTO tbl_candle (symbol, exchange, open, close, high, low, count,c_datetime, volume, wap, body_len, up_shadow_len,low_shadow_len, direction) "+
						"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, contr.symbol());
			ps.setString(2, contr.primaryExch());
			ps.setDouble(3, open);
			ps.setDouble(4, close);
			ps.setDouble(5, high);
			ps.setDouble(6, low);
			ps.setInt(7, count);
			ps.setTimestamp(8, new Timestamp(time));
			ps.setLong(9, volume);
			ps.setDouble(10, wap);
			ps.setDouble(11, bl);
			ps.setDouble(12, usl);
			ps.setDouble(13, lsl);
			ps.setInt(14, dir);
			
			int updated =ps.executeUpdate();  
			lg.info("inserted " + updated + " record");
			/*
			while(rs.next())  {
				sym = rs.getString(1);
				exch = rs.getString(2);
				if(exch==null)
					exch = "";
				results.add(sym+","+exch);
			}
			if(lg.isDebugEnabled())
				lg.debug("Found " + results.size() + " tickers");
			*/
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
	}
	
	public DataFactory2() {	}
}
