package com.pancorp.tbroker.condition;

public class ExitCondition extends AbstractCondition {
	/**
	 * 1. Stop loss rules to protect your capital
	 * 
	 * MUST cancel stop-loss order when closed your position 
	 * or your profit target reached (or use OCO - one cancel other - orders,
	 * or bracket orders)
	 * TODO - figure out trailing stops with regards to OCO
	 */
	
	/**
	 * 2. Profit-taking exits to realize your gains
	 */
	
	/**
	 * Two above rules can be expressed as:
	 * 
	 * 		A fixed dollar amount (e.g. $1000)
	 * 
	 * 		A percentage of the current price (e.g. 1% of the entry price): multiply 
	 * 		the entry price by (1- your stop loss in percent form) to get your exit point
	 * 
	 * 		A percentage of the volatility (e.g. 50% of the average daily movement).
	 * 		Underlying idea is to adjust your stop loss based on market volatility:
	 * 		apply larger stop loss (or higher profit target) in a volatile markets 
	 * 		and smaller stop loss (or lower profit target) in quiet markets.
	 * 		require 2 steps:
	 * 			1. determine the average volatility of the market
	 * 			2. Multiply this number by percentage you specified
	 * 
	 * 		Based on technical analysis (e.g. support and resistance levels)
	 */
	
	/**
	 * 3. Time-stops to get you out of a  trade and 
	 * free your capital if the market is not moving 
	 * at all
	 * 
	 * TODO  specify a timeout, for example, 3 times timeframe you are using
	 */
}
