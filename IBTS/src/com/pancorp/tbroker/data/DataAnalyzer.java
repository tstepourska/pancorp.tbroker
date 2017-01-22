package com.pancorp.tbroker.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import com.ib.client.Contract;
import com.pancorp.tbroker.event.Events;
import com.pancorp.tbroker.event.TBrokerEvent;
import com.pancorp.tbroker.main.TBroker;
import com.pancorp.tbroker.model.CalculationCache;
import com.pancorp.tbroker.model.Candle;
import com.pancorp.tbroker.model.PatternEnum;
import com.pancorp.tbroker.model.PatternCache;
import com.pancorp.tbroker.model.TrendCache;

public class DataAnalyzer extends Thread{
	
	TrendCache trendCache;
	CalculationCache calcCache;
	PatternCache patternCache;
	
	Contract contract;
	TBroker tbroker;
	
	Map<PatternEnum,LinkedList<Candle>> lib;
	public DataAnalyzer(Contract c, TBroker tb) {
		this.contract = c;
		this.tbroker = tb;
		
		lib = new HashMap<>();
		trendCache = new TrendCache();
		calcCache = new CalculationCache();
		patternCache = new PatternCache();
	}
	
	public void run(){

		Candle candle;
		boolean working = true;
		TBrokerEvent e = null;
		//get all data 
		//select instrument - future, stock, forex
		
		//HashMap<String,Stack<Candle>> map = new HashMap<>();
		
		//list =  new LinkedList<>();
		
		loop:
		while(working){
		// 15-minute timeframe to help determine the trend throughout the duration of the trade
			//TODO
		 long time=0;
		 double high=0;
		 double low=0;
		 double open=0;
		 double close=0;
		 double wap=0; 
		 long volume=0; 
		 int count = 0;
		candle = new Candle(time, high, low,open, close, wap, volume, count);
		
		calcCache.recalc(candle);
		//int trend = trendCache.checkTrend(candle);
		
		//not enough candles to define downswing
		//if(q.size()<MIN_TREND_CANDLES)
		//	return Events.NONE;
		
		patternCache.addCandle(candle, trendCache);
	
		//put into database:
		//DataFactory.insertCandle(candle);
		//TODO what patterns to save for monitoring
		/*switch(e.getAction()){
		case BUY:
			
			break;
		case CANCEL:
			break;
		case NONE:
			break;
		case ADD_TO_MONITOR:
			list.add(candle);
			break;
		case SELL:
			break;
		default:
			break;
		
		}*/
		
		
		//in.setTicker("IBM");
		//in.setType(InstrumentTypes.getValue( InstrumentTypes.STOCK));
		
		//keep getting data for selected security in 10-mins (5-mins?) bars and scan it for indicators
		
		//once found, return
		working = false;
		}
		
		return c;
	}

}
