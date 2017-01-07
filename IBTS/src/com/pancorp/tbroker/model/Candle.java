/**
 * 
 */
package com.pancorp.tbroker.model;

import com.ib.controller.Bar;

/**
 * For the definition of Doji:
 * 
 *  Point is the smallest possible price change on the left side of the decimal point. ($1 ?)
 *  
 *  1/8 point for $20 stock: 0.00630517*100%
 *  1 1/4 point for $200 stock: 0.00625*100%
 */

/**
 * @author pankstep
 *
 */
public class Candle extends Bar {

	/** Values for candlestick direction */
	public static final int BLACK = -1;
	public static final int WHITE = 1;
	public static final int DOJI = 0;
	
	//public static enum SinglePattern {DOJI,MARABOZU};
	
	/**
	 * Percent from the open price that allowed for Doji pattern (considered the same)
	 */
	public static final double DOJI_BODY_RANGE_PERC = 0.005;
	//public static final double _DOJI_LEG_RANGE_PERC = 0.01;
	
	/**
	 * Percent of [open] price above which leg or body is considered long, 
	 * below is average
	 * //TODO: recalculate and reload based on previous average candle values;
	 * 2-3 times below avg - short, 2-3 times above avg - long
	 */
	public static double DOJI_LONG_THRESHOLD_PERC = 0.033;
	
	/* TODO: define criteria for avg shadow */
	/* TODO: define criteria for avg body */
	
	private int direction;
	private String simplePattern;
	
	/**
	 * Constructor
	 */
	public Candle( long time, double high, double low, double open, double close, double wap, long volume, int count) {
		super(time,high,low,open,close,wap,volume,count);
		calcDirection();
		calcSimplePattern();
	}
	
	public String getSimplePattern(){
		return this.simplePattern;
	}
	
	/**
	 * Determines pattern of this candlestick 
	 * based solely on its properties. No consideration 
	 * to previous candlesticks or patterns is given here
	 */
	private void calcSimplePattern(){
		double open = open();
		double close = close();
		double high = high();
		double low = low();
		
		//find the difference between open and close
		double dBody = open-close;	
		double dShadows = high-low;
		double deviationYesOrNo = open*DOJI_BODY_RANGE_PERC;
		
		//determine if the candlestick is Doji and which one
		if(dBody<=deviationYesOrNo){			
			simplePattern = CandlePattern.getValue(CandlePattern.DOJI);
			
			//equal or above this range (for both legs) legs are considered long
			double longLegThreshold = open*DOJI_LONG_THRESHOLD_PERC;
			
			//determine what kind of Doji
			switch(direction){
				case WHITE:
					if(high==close&&low<open && dShadows>longLegThreshold/2)	//check length of 1 leg 
						simplePattern = CandlePattern.getValue(CandlePattern.DRAGONFLY_DOJI);
					else if(high>close && low==open && dShadows>longLegThreshold/2)	//check length of 1 leg 
						simplePattern = CandlePattern.getValue(CandlePattern.TOMBSTONE_DOJI);
					else if(high>close && low<open && dShadows > longLegThreshold) //check length of 2 legs together
						simplePattern = CandlePattern.getValue(CandlePattern.LONG_LEGGED_DOJI);
						break;
				case BLACK:
					if(high==open&&low<close && dShadows>longLegThreshold/2)	//check length of 1 leg 
						simplePattern = CandlePattern.getValue(CandlePattern.DRAGONFLY_DOJI);
					else if(high>open && low==open && dShadows>longLegThreshold/2)	//check length of 1 leg 
						simplePattern = CandlePattern.getValue(CandlePattern.TOMBSTONE_DOJI);
					else if(high>open && low<close && dShadows > longLegThreshold)  //check length of 2 legs together
						simplePattern = CandlePattern.getValue(CandlePattern.LONG_LEGGED_DOJI);
					break;
				default:
			}
			return;
		}
		
		//TODO recalculate based on average over previous values
		boolean longBody = Math.abs(dBody) > open*DOJI_LONG_THRESHOLD_PERC; //candlestick is considered long
		boolean shortBody = Math.abs(dBody) < open*DOJI_LONG_THRESHOLD_PERC/3; //candlestick is considered short
		
		if(direction==WHITE){	//white 
			if(longBody){
				if((open ==low || Math.abs(open-low) < deviationYesOrNo)&&close==high || Math.abs(high-close)<deviationYesOrNo)	
					                                                                                                          simplePattern = CandlePattern.getValue(CandlePattern.BULLISH_MARABOZU);
			}
			else{	//short or average body
				if(low==open || Math.abs(open-low)<deviationYesOrNo){	//no lower shadow
					if(Math.abs(high-close) > open*DOJI_LONG_THRESHOLD_PERC)	//upper shadow is longer than average
						simplePattern = CandlePattern.getValue(CandlePattern.LONG_UPPER_SHADOW);
				}
				else {	//both shadows
					if(shortBody){
						if(open-low>open*DOJI_LONG_THRESHOLD_PERC && high-close > open*DOJI_LONG_THRESHOLD_PERC)	//both shadows are long
							simplePattern = CandlePattern.getValue(CandlePattern.HIGH_WAVE);
						else 
							simplePattern = CandlePattern.getValue(CandlePattern.SPINNING_TOP);
					}
				}
			}
		}
		else if(direction==BLACK){ 	//black
			if(longBody){
				if((open ==high || Math.abs(open-high) < deviationYesOrNo)&& close==low || Math.abs(close-low)<deviationYesOrNo)	
					simplePattern = CandlePattern.getValue(CandlePattern.BEARISH_MARABOZU);
			}
			else{	//short or average body
				if(high==open|| Math.abs(high-open)<deviationYesOrNo){	//no upper shadow
					if(Math.abs(close -low)> open*DOJI_LONG_THRESHOLD_PERC)	//lower shadow is longer than average
						simplePattern = CandlePattern.getValue(CandlePattern.LONG_LOWER_SHADOW);
				}
				else {	//both shadows
					if(shortBody){
						if(high-open > open*DOJI_LONG_THRESHOLD_PERC && (close-low)> open*DOJI_LONG_THRESHOLD_PERC)		//both shadows are long
							simplePattern = CandlePattern.getValue(CandlePattern.HIGH_WAVE);
						else 
							simplePattern = CandlePattern.getValue(CandlePattern.SPINNING_TOP);
					}
				}
			}
		}
		//if case not specified above, simplePattern remains null
	}
	
	/**
	 * Returns candlestick direction
	 * @return int   --Negative - down (black or red candle), positive - up (while or green candle)
	 */
	public int getDirection(){
		return this.direction;
	}
	
	
	/**
	 * Calculates candlestick direction, based on 
	 * open and close prices. 
	 */
	private void calcDirection(){
		//find the difference between open and close
		double d = open()-close();	
				
		if(d==0){
			direction = DOJI;
		}
		else if(d<0)
			direction = BLACK;
		else
			direction = WHITE;	
	}
}
