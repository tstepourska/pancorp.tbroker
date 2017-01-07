package com.pancorp.tbroker.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.ib.client.Contract;
import com.pancorp.tbroker.model.Candle;

public class DataAnalyzer {
	Stack<Candle> stack;
	
	public Contract invoke(){
		Contract c = new Contract();
		Candle candle;
		boolean working = true;
		//get all data 
		//select instrument - future, stock, forex
		
		//HashMap<String,Stack<Candle>> map = new HashMap<>();
		
		stack =  new Stack<>();
		
		while(working){
		// 15-minute timeframe to help determine the trend throughout the duration of the trade
		 long time=0;
		 double high=0;
		 double low=0;
		 double open=0;
		 double close=0;
		 double wap=0; 
		 long volume=0; 
		 int count = 0;
		candle = new Candle(time, high, low,open, close, wap, volume, count);
		String pattern = candle.getSimplePattern();
		checkPattern(pattern);
		
		//in.setTicker("IBM");
		//in.setType(InstrumentTypes.getValue( InstrumentTypes.STOCK));
		
		//keep getting data for selected security in 10-mins (5-mins?) bars and scan it for indicators
		
		//once found, return
		working = false;
		}
		
		return c;
	}
	
	private boolean checkPattern(String p){
		boolean save = false;
		//TODO what patterns to save for monitoring
		
		return save;
	}

}
