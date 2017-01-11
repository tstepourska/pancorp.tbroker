/**
 * Copyright 2016-2017 PanCorp Group
 */
package com.pancorp.tbroker.model;

import com.ib.controller.Bar;
import com.pancorp.tbroker.util.Constants;

/**
 * 
 */

/**
 * @author pankstep
 *
 */
public class Candle extends Bar {
	
	//public enum LENGTH_TYPE  {LONG, SHORT, AVERAGE};
	
	private double body_len 		= 0;
	private double upper_shadow_len = 0;
	private double lower_shadow_len = 0;
	private double amp 				= 0;
	
	private int direction;
	//private PatternEnum simplePattern;
	//private LENGTH_TYPE bodyType;
	//private LENGTH_TYPE upperShadowType;
	//private LENGTH_TYPE lowerShadowType;
	
	/**
	 * Constructor
	 */
	public Candle( long time, double high, double low, double open, double close, double wap, long volume, int count) {
		super(time,high,low,open,close,wap,volume,count);
		
		calcProperties();
	}
/*
	public PatternEnum getSimplePattern(){
		return this.simplePattern;
	}
	
	public void setSimplePattern(PatternEnum p){
		this.simplePattern = p;
	}*/
	
	/**
	 * Returns candlestick direction
	 * @return int   --Negative - down (black or red candle), positive - up (while or green candle)
	 */
	public int getDirection(){
		return this.direction;
	}
	
	/**
	 * @return the body_len
	 */
	public double getBody_len() {
		return body_len;
	}

	/**
	 * @param body_len the body_len to set
	 */
	public void setBody_len(double body_len) {
		this.body_len = body_len;
	}

	/**
	 * @return the upper_shadow_len
	 */
	public double getUpper_shadow_len() {
		return upper_shadow_len;
	}

	/**
	 * @param upper_shadow_len the upper_shadow_len to set
	 */
	public void setUpper_shadow_len(double upper_shadow_len) {
		this.upper_shadow_len = upper_shadow_len;
	}

	/**
	 * @return the lower_shadow_len
	 */
	public double getLower_shadow_len() {
		return lower_shadow_len;
	}

	/**
	 * @param lower_shadow_len the lower_shadow_len to set
	 */
	public void setLower_shadow_len(double lower_shadow_len) {
		this.lower_shadow_len = lower_shadow_len;
	}

	/**
	 * @return the bodyType
	 */
/*	public LENGTH_TYPE getBodyType() {
		return bodyType;
	}*/

	/**
	 * @param lenT the lenT to set
	 */
/*	public void setBodyType(LENGTH_TYPE lenT) {
		this.bodyType = lenT;
	}*/

	/**
	 * @return the upperShadowType
	 */
/*	public LENGTH_TYPE getUpperShadowType() {
		return upperShadowType;
	}*/

	/**
	 * @param upperShadowType the upperShadowType to set
	 */
/*	public void setUpperShadowType(LENGTH_TYPE upperShadowType) {
		this.upperShadowType = upperShadowType;
	}*/

	/**
	 * @return the lowerShadowType
	 */
/*	public LENGTH_TYPE getLowerShadowType() {
		return lowerShadowType;
	}*/

	/**
	 * @param lowerShadowType the lowerShadowType to set
	 */
/*	public void setLowerShadowType(LENGTH_TYPE lowerShadowType) {
		this.lowerShadowType = lowerShadowType;
	}*/

	/**
	 * @return the amp
	 */
	public double getAmp() {
		return amp;
	}

	/**
	 * @param amp the amp to set
	 */
	public void setAmp(double amp) {
		this.amp = amp;
	}	
	
	/**
	 * Calculates candlestick direction, amplitude, 
	 * length of the body and each shadow
	 */
	private void calcProperties(){
		amp 	 = high() - low();
		body_len = open() - close();
			
		if(body_len==0){
			direction = Constants.DIR_NONE;
		}
		else if(body_len>0)
			direction = Constants.DIR_BLACK;
		else
			direction = Constants.DIR_WHITE;	
		
		switch(direction){
		case Constants.DIR_WHITE:	
			upper_shadow_len = high() - close();
			lower_shadow_len = open() - low();
			break;
			default: //BLACK or DOJI
			upper_shadow_len = high() - open();
			lower_shadow_len = close() - low();
		}
		
		//get rid of minus sign if any
		body_len = Math.abs(body_len);
	}
}