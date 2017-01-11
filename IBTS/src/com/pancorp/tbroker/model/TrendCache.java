package com.pancorp.tbroker.model;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import com.pancorp.tbroker.util.Constants;
import com.pancorp.tbroker.util.Globals;

public class TrendCache extends LinkedList<Candle> {
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(TrendCache.class);
	private static final long serialVersionUID = 2939335574409341461L;

	
	int direction = Constants.DIR_NONE;

	public TrendCache() {
		super();
	}
	
	/**
	 * Adds to the head of the list. If size exceed max,
	 * removes the last element. If trend interrupts,
	 * clears the queue, and starts new trend, if applicable.
	 */
	public int checkTrend(Candle c) {
		//check most recent 
		Candle last = peekFirst();
		
		if(last==null)  {//cache is empty	
			if(c.getDirection()==Constants.DIR_NONE)  //Doji, not part of a trend
				;//TODO add a reversal signal
			else
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
				direction = Constants.DIR_WHITE;
			else if(diffHigh>0 && diffLow > 0) //downtrend
				direction = Constants.DIR_BLACK;
			else 	//no trend
				clear();				
			
			this.addFirst(c);
			return direction;
		}

		//cache size > 1
		if(direction > 0){  //uptrend
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
		if(size()>Globals.MAX_TREND_CANDLES)
			removeLast();
		
		return size();	
	}
}
