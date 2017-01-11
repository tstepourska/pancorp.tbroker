package com.pancorp.tbroker.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;

import com.pancorp.tbroker.event.Events;
import com.pancorp.tbroker.util.Constants;
import com.pancorp.tbroker.util.Globals;

public class PatternCache extends LinkedList<Candle> {
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(PatternCache.class);
	private static final long serialVersionUID = 2939335574409341461L;
	///public static final int MIN_PATTERN_CANDLES = 1;
	//public static final int MAX_PATTERN_CANDLES = 5;
	
	//boolean complete = false;
	int direction = Constants.DIR_NONE;
	
	//LinkedList<Candle> 
	Map<PatternEnum,ArrayList<Candle>> patterns;

	public PatternCache() {
		super();
		patterns = new HashMap<>();
		
		ArrayList<Candle> tmp;
		for(int i=0;i<PatternEnum.values().length;i++){
			tmp = new ArrayList<Candle>();
			patterns.put(PatternEnum.values()[i], tmp);
		}
	}
	
	
	/**
	 * Adds to the head of the list. 
	 * If pattern does not match, clears patter and adds item 
	 * to the TrendCache
	 * It patter matches, partially or whole ...
	 */
	public void addCandle(Candle c, TrendCache trend) {
		
		for(int i=0;i<PatternEnum.values().length;i++) {
			//PatternEnum.values().
			//PatternEnum p = c.getSimplePattern();
			int trendSize = trend.size();
			int trendDirection = trend.direction;
			boolean foundPattern = false;
			
			if(trendSize<=Globals.MIN_TREND_CANDLES){
				//not enough candles, just add a new one if applicable
				trend.checkTrend(c);
				return;
			}
			
			//trend size is min_trend + 1 (4), so last candle possibly is 
			//the first one of a reversal pattern 
			if(trend.direction!=c.getDirection()){	//something happening!
				switch(c.getDirection()){
				case Constants.DIR_WHITE:	//white candle in a black trend
					//Stack<Candle> candleStack = new Stack<>();
			
					break;
				case Constants.DIR_BLACK:	//black candle in a white trend
					break;
				default: // direction 0 - doji
					//1   candle is doji, 2nd candle in the Morning Doji Star -  bullish reversal (70%)
				}
			}
			
			//check most recent 
			Candle last = peekFirst();
		
		}
		
		
			
			switch(c.getDirection()){
			case Constants.DIR_WHITE:
				up = true;
				break;
			case Constants.DIR_NONE:
				return 0;	//no direction, do nothing to empty list
				default:
					up = false;
			}
			addFirst(c); //start a trend
			
			return size();
		
		
		return size();	
	}
	
	/**
	 * Checks Three Line Strike pattern:
	 * --> 3 black candles within downtrend, each posts lower low and closes 
	 * near the intrabar low
	 * --> 4th bar opens even lower, but reverses in a wide range and closes 
	 * above the high if the first candle in series. No lower shadow.
	 * 
	 * @param c
	 * @param trend
	 * @return
	 */
	private static boolean ThreeLineStrike(Candle c, TrendCache trend){
		boolean foundPattern = false;
		
		//1  Three Line Strike (80%)
		//		-->3 previous black candles within downtrend, each posts lower low and closes near the intrabar low
		//for(int i=0;i<3;i++){
			//first one has to be the lowest
			Candle cd1 = trend.removeFirst();
			double low1 = cd1.low();
			double lowShadow1 = cd1.getLower_shadow_len();
			double high1 = cd1.high();
			//candleStack.push(cd1);
			//if(lowShadow1 <= CalculationCache.AVG_LOWER_SHADOW_LEN/2)	//short low shadow - close to intrabar low
			
			//previous higher low
			Candle cd2 = trend.removeFirst();
			double low2 = cd2.low();
			double lowShadow2 = cd2.getLower_shadow_len();
			double high2 = cd2.high();
			//candleStack.push(cd1);
			
			//this one even higher low
			Candle cd3 = trend.removeFirst();
			double low3 = cd3.low();
			double lowShadow3 = cd3.getLower_shadow_len();
			double high3 = cd3.high();
			
			//put back candles
			trend.addFirst(cd3);
			trend.addFirst(cd2);
			trend.addFirst(cd1);
			
			if(lowShadow1 > CalculationCache.AVG_LOWER_SHADOW_LEN/2||
				lowShadow2 > CalculationCache.AVG_LOWER_SHADOW_LEN/2 ||
				lowShadow3 > CalculationCache.AVG_LOWER_SHADOW_LEN/2 //||						
				//low1 >= low2 ||	checked in the trend
				//low2 >= low3		//checked in the trend
			){
				return false;
			}
			//previous candles passed all checks here
			//checking the current one
			//	-->current white candle opens even lower but reverses 
			//in a wide range and closes above the high
			// of the first candle in series. No lower shadow
			if(c.low()<low3 && c.getLower_shadow_len()==0 && c.close()> cd1.high()){
				foundPattern = true;
				//TODO assign what pattern found or what event to fire
			}
		
		return foundPattern;
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
	private static Events Hammer(Candle c){
		//boolean match = false;
		

		
		return null;
	}
	
	
	
	/**
	 * Percent of [open] price above which leg or body is considered long, 
	 * below is average
	 * //TODO: recalculate and reload based on previous average candle values;
	 * 2-3 times below avg - short, 2-3 times above avg - long
	 */
	//public static double DOJI_LONG_THRESHOLD_PERC = 0.033;
	
	/* TODO: define criteria for avg shadow */
	/* TODO: define criteria for avg body */
	
	/**
	 * Determines pattern of this candlestick 
	 * based solely on its properties. No consideration 
	 * to previous candlesticks or patterns is given here
	 */
	private void calcSimplePattern(Candle c){
		PatternEnum simplePattern = null;
		//double open = open();
		//double close = close();
		//double high = high();
		//double low = low();
		
		//find the difference between open and close
		//double dBody = open-close;	
		//double dShadows = high-low;
		//double deviationYesOrNo = open*DOJI_BODY_RANGE_PERC;
		
		//determine if the candlestick is Doji and which one
		if(c.getBody_len()<=c.open()*Globals.PATTERN_BODY_DEVIATION_PERC){			
			simplePattern = PatternEnum.DOJI;
			
			//equal or above this range (for both legs) legs are considered long
			//double longLegThreshold = open*DOJI_LONG_THRESHOLD_PERC;
			
			//determine what kind of Doji
			switch(c.getDirection()){
				case c.WHITE:
					if(high==close&&low<open && dShadows>longLegThreshold/2)	//check length of 1 leg 
						simplePattern = PatternEnum.DRAGONFLY_DOJI;
					else if(high>close && low==open && dShadows>longLegThreshold/2)	//check length of 1 leg 
						simplePattern = PatternEnum.TOMBSTONE_DOJI;
					else if(high>close && low<open && dShadows > longLegThreshold) //check length of 2 legs together
						simplePattern = PatternEnum.LONG_LEGGED_DOJI;
						break;
				case c.BLACK:
					if(high==open&&low<close && dShadows>longLegThreshold/2)	//check length of 1 leg 
						simplePattern = PatternEnum.DRAGONFLY_DOJI;
					else if(high>open && low==open && dShadows>longLegThreshold/2)	//check length of 1 leg 
						simplePattern = PatternEnum.TOMBSTONE_DOJI;
					else if(high>open && low<close && dShadows > longLegThreshold)  //check length of 2 legs together
						simplePattern = PatternEnum.LONG_LEGGED_DOJI;
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
					 simplePattern = PatternEnum.BULLISH_MARABOZU;
			}
			else{	//short or average body
				if(low==open || Math.abs(open-low)<deviationYesOrNo){	//no lower shadow
					if(Math.abs(high-close) > open*DOJI_LONG_THRESHOLD_PERC)	//upper shadow is longer than average
						simplePattern = PatternEnum.LONG_UPPER_SHADOW;
				}
				else {	//both shadows
					if(shortBody){
						if(open-low>open*DOJI_LONG_THRESHOLD_PERC && high-close > open*DOJI_LONG_THRESHOLD_PERC)	//both shadows are long
							simplePattern = PatternEnum.HIGH_WAVE;
						else 
							simplePattern = PatternEnum.SPINNING_TOP;
					}
				}
			}
		}
		else if(direction==BLACK){ 	//black
			if(longBody){
				if((open ==high || Math.abs(open-high) < deviationYesOrNo)&& close==low || Math.abs(close-low)<deviationYesOrNo)	
					simplePattern = PatternEnum.BEARISH_MARABOZU;
			}
			else{	//short or average body
				if(high==open|| Math.abs(high-open)<deviationYesOrNo){	//no upper shadow
					if(Math.abs(close -low)> open*DOJI_LONG_THRESHOLD_PERC)	//lower shadow is longer than average
						simplePattern = PatternEnum.LONG_LOWER_SHADOW;
				}
				else {	//both shadows
					if(shortBody){
						if(high-open > open*DOJI_LONG_THRESHOLD_PERC && (close-low)> open*DOJI_LONG_THRESHOLD_PERC)		//both shadows are long
							simplePattern = PatternEnum.HIGH_WAVE;
						else 
							simplePattern = PatternEnum.SPINNING_TOP;
					}
				}
			}
		}
		//if case not specified above, simplePattern remains null
	}


}
