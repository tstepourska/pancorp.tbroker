package com.pancorp.tbroker.model;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;

import com.pancorp.tbroker.util.Globals;

public class CalculationCache extends LinkedList<Candle> {
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(CalculationCache.class);
	private static final long serialVersionUID = 2939335527384591461L;

	
	public static volatile double AVG_BODY_LEN;
	public static volatile double AVG_UPPER_SHADOW_LEN;
	public static volatile double AVG_LOWER_SHADOW_LEN;
	public static volatile double HIGH;
	public static volatile double LOW;
	//public static volatile double AVG_AMPLITUDE;
	
	//do calculation of all averages with a new item (while keeping extra)
	private double sum_body_len 		= 0;
	private double sum_upper_shadow_len = 0;
	private double sum_lower_shadow_len = 0;
			//double sum_amp = 0;

	public CalculationCache() {
		super();
		AVG_BODY_LEN = 0;
		AVG_UPPER_SHADOW_LEN = 0;
		AVG_LOWER_SHADOW_LEN =0;
		//AVG_AMPLITUDE=0;
		HIGH = 0;
		LOW = 0;
		
		sum_body_len 		= 0;
		sum_upper_shadow_len = 0;
		sum_lower_shadow_len = 0;
	}
	
	/**
	 * 
	 */
	public void recalc(Candle c) {
		//update min and max first
		if(c.high()>HIGH)
			HIGH = c.high();
		if(LOW<c.low())
			LOW = c.low();
		
		//add candle to the cache
		addFirst(c);
		
		double bLen 	= c.getBody_len();
		double upShLen 	= c.getUpper_shadow_len();
		double loShLen 	= c.getLower_shadow_len();
		
		sum_body_len 			= sum_body_len + bLen;
		sum_upper_shadow_len 	= sum_upper_shadow_len + upShLen;
		sum_lower_shadow_len	= sum_lower_shadow_len + loShLen;
		
		//remove extra item after all calculations are done
		if(size()>Globals.MAX_CALC_CANDLES){
			Candle oldest = removeLast();
			//double bLenWO1 = AVG_BODY_LEN - oldest.getBody_len();
			//double upLenWO1 = AVG_UPPER_SHADOW_LEN - oldest.getUpper_shadow_len();
			//double loLenWO1 = AVG_LOWER_SHADOW_LEN - oldest.getLower_shadow_len();
			
			sum_body_len 			= sum_body_len - oldest.getBody_len();
			sum_upper_shadow_len 	= sum_upper_shadow_len - oldest.getUpper_shadow_len();
			sum_lower_shadow_len	= sum_lower_shadow_len- oldest.getLower_shadow_len();
		}
		
		
	/*
		Iterator<Candle> it = this.iterator();
		while(it.hasNext()){
			c = it.next();					
			
			sum_body_len 			= sum_body_len + c.getBody_len();
			//sum_amp 				= sum_amp + c.getAmp();
			sum_upper_shadow_len 	= sum_upper_shadow_len + c.getUpper_shadow_len();
			sum_lower_shadow_len 	= sum_lower_shadow_len + c.getLower_shadow_len();
		}*/
		
		int s = size(); //no zero, as we just added a candle, but take into consideration MAX
		if(s>0){
		AVG_BODY_LEN 		= sum_body_len / s;
		AVG_UPPER_SHADOW_LEN = sum_upper_shadow_len / s;
		AVG_LOWER_SHADOW_LEN = sum_lower_shadow_len / s;
		}
		else{
			lg.error("recalc: ERROR: division by zero, check Globals.MAX_CAL_CANDLES and Globals.MIN_CALC_CANDLES");
		}
		
		//average = average + ((value - average) / nbValues);
		//AVG_AMPLITUDE = sum_amp / s;
		
		

	}
}
