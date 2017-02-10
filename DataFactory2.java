package com.ts.test.data;

import java.util.ArrayDeque;
import java.util.HashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.util.LinkedList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ts.test.daily.StrategyThread;
import com.ts.test.main.Test;
import com.ts.test.model.IBar;
import com.ts.test.model.Tick;
import com.ts.test.util.Constants;

public class DataFactory extends Thread {
	private static Logger lg = LogManager.getLogger(DataFactory.class); 
	private volatile ArrayDeque<IBar> pileup;
	//up to 100 queues by symbol, each up to 17280 ticks per day
	private volatile HashMap<String,StrategyThread> map; 
	private boolean working = true;
	
	Connection con = null;
	PreparedStatement ps = null;
	String sql = "";
	String url = null;
	String user = null;
	String password = null;
	
	public DataFactory() throws Exception {
		pileup = new ArrayDeque<>();
		map = new HashMap<>(); 
		
		con  = DriverManager.getConnection(url, user, password);
		ps = con.prepareStatement(sql);
	}
	
	public synchronized void subscribe(String sym, StrategyThread t){
		map.put(sym, t);
	}
	
	public synchronized void unsubscribe(String sym){
		map.remove(sym); //(sym, t);
	}
	
	/**
	 * Adds new tick into the pileup queue
	 * Make sure to check that tick is not null 
	 * when calling this method
	 * 
	 * @param t
	 */
	public synchronized void addFirst(IBar t){
		pileup.addFirst(t);
	}
	
	/**
	 * Removes last element of the queue.
	 * Only DataFactory object can remove an element
	 * 
	 * @return IBar
	 */
	private synchronized IBar removeLast(){
		if(!this.pileup.isEmpty())
			return this.pileup.removeLast();
		return null;
	}
	
	/**
	 * Saves tick data in a database, then sorts it out into an 
	 * appropriate queue by symbol
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	private int recordTick(IBar t) throws Exception {
		int status = -1;
		//ArrayDeque<IBar> deck;
		
		String sym  = t.symbol();
		ps.setString(1,sym);
		ps.setString(2, t.exchange());
		ps.setDouble(3, t.open());
		ps.setDouble(4, t.close());
		ps.setDouble(5, t.high());
		ps.setDouble(6, t.low());
		ps.setLong  (7, t.time());
		ps.setLong  (8, t.volume());
		ps.setDouble(9, t.wap());
		ps.setInt   (10, t.count());
		
		status = ps.executeUpdate();
		lg.debug("inserted: " + status);
		
		//sort by symbol
		synchronized(this){
			if(!this.map.containsKey(sym)){
				;	//do nothing, not subscribed
				
				//deck = new ArrayDeque<>();
				//deck.add(t);
				//map.put(sym, deck);
			}
			else{
				try {
					map.get(sym).addTick(t);
				}
				catch(InterruptedException ie){
					//retry
					try {
						Thread.sleep(Constants.SLEEP_DF_INTERRUPTED);
						if(this.map.containsKey(sym))
							map.get(sym).addTick(t);
					}
					catch(InterruptedException ie2){}
				}
			}
		}
		
		return status;
	}
	
	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}

	@Override
	public void run(){
		IBar b;
		int st = -1;
		while(working){
			try {
				b = this.removeLast();
				if(b==null)
					Thread.sleep(Constants.SLEEP_WAIT_FOR_BAR);			
				else
					st = recordTick(b);
			}
			catch(InterruptedException ie){ lg.error(ie.getMessage()); }
			catch(Exception e){ st = Constants.STATUS_ERROR; lg.error(e.getMessage()); }
		}
	}
}
