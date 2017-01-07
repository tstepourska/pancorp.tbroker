package com.pancorp.tbroker.model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.pancorp.tbroker.event.Events;
import com.pancorp.tbroker.event.TBrokerEvent;

public class PatternLibrary {

	
	
	Map<CandlePatternEnum,LinkedList<Candle>> lib;
	private PatternLibrary() {
		lib = new HashMap<>();
	}
	
	public static TBrokerEvent matchPattern(LinkedList<Candle> q, Candle c){
		TBrokerEvent e = new TBrokerEvent(TBrokerEvent.events.NONE);
		
		Class<PatternLibrary> yourClass = PatternLibrary.class;
		for (Method method : yourClass.getMethods()){
		    boolean b = method.invoke(obj, args);           
		}
		
		return e;
	}
	
	/**
	 * Matches against Hammer pattern
	 * --A specific type of spinning top candle
	 * --Occurs after a DOWNSWING 
	 * --May be black or white
	 * --Contains a small real body
	 * --Lower shadow 2-3x the size of the real body
	 * --Little or now upper shadow
	 * --A STRONG bullish reversal signal
	 * 
	 * @return
	 */
	private static Events Hammer(LinkedList<Candle> q, Candle c){
		//boolean match = false;
		
		//not enough candles to define downswing
		if(q.size()<MIN_TREND_CANDLES)
			return Events.NONE;
		
		for(int i=0;i<q.size()-1;i++){
			
		}
		
		return match;
	}
	
/*	private static Events Downswing(LinkedList<Candle> q, Candle c){
		
		//starting from least recent
		Candle c1 = q.get(i);
		Candle c2 = q.get(i+1);
		double diffLow1_2 = c1.low() - c2.low();
		double length1 = c1.high() - c1.low();
		if()
		
		double diffHigh1_2 = c1.high() - c2.high();
		
		double length2 = c2.high() - c2.low();
		double thresholdHigh = c1.high() - length1/4;
		double thresholdLow = c2.low() - length2/4;
		return match;
	}*/

}
