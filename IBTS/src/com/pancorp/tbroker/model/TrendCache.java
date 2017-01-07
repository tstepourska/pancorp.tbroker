package com.pancorp.tbroker.model;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

public class TrendCache extends LinkedList<Candle> {
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(TrendCache.class);
	private static final long serialVersionUID = 2939335574409341461L;
	public static final int MIN_TREND_CANDLES = 3;
	public static final int MAX_TREND_CANDLES = 10;
	
	boolean up = false;

	public TrendCache() {
		super();
	}
	
	/**
	 * Adds to the head of the list. If size exceed max,
	 * removes the last element. If trend interrupts,
	 * clears the queue, and starts new trend, if applicable.
	 */
	public int trend(Candle c) {
		//check most recent 
		Candle last = peekFirst();
		
		if(last==null)  {//cache is empty		
			switch(c.getDirection()){
			case Candle.WHITE:
				up = true;
				break;
			case Candle.DOJI:
				return 0;	//no direction, do nothing to empty list
				default:
					up = false;
			}
			addFirst(c); //start a trend
			
			return size();
		}
			
		//last candle is not null
		double diffHigh = last.high() - c.high(); //check high
		double diffLow = last.low() - c.low();	//check low
		
		//single candle in the cache, any trend can start
		if(size()==1){ 
			if(diffHigh==0 || diffLow==0)	//no trend
				clear();			
			else if(diffHigh<0 && diffLow < 0)		//uptrend
				up = true;
			else if(diffHigh>0 && diffLow > 0) //downtrend
				up = false;
			else 	//no trend
				clear();				
			
			this.addFirst(c);
			return size();
		}

		//cache size > 1
		if(up){  //uptrend
			if(diffHigh < 0 && diffLow < 0)   //uptrend continues
				;  
			else
				clear();
		}
		else { //downtrend
			if(diffHigh>0 && diffLow > 0)     //downtrend continues
				;
			else
				clear();
		}

		addFirst(c);	// add new candle in any case
		if(size()>MAX_TREND_CANDLES)
			removeLast();
		
		return size();	
	}
}
