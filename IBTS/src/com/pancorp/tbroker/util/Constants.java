package com.pancorp.tbroker.util;

public enum Constants {

	CONSTANTS;
	
	
	///////////////////////////////////////////////////////////////
	//		CONSTANTS
	///////////////////////////////////////////////////////////////
	/**
	 * Percent of total account amount, that I can afford to lose, 
	 * shared between all trades; ex $10000 * 0.05 = $500
	 * Total trailing stops amount for all concurrent positions 
	 * must not exceed this number: new position can't be opened
	 */
	public static final double TOTAL_STOP_LOSS_PERCENT = 0.05;
	
	/**
	 * Percent of original amount spent on a single position that can be lost
	 *  0.03-0.07
	 * TODO   is is the same for long and short positions?
	 */
	public static final double TRAILING_STOP_PERCENT   = 0.03;
	
	/**
	 * Percent of  total account amount that can be invested 
	 * into one economic sector, shared between all positions,
	 * ex $10000 * 0.03 = $300
	 * 
	 * TODO to confirm if this is applicable to day trading
	 */
	public static final double TOTAL_SECTOR_EXPOSURE_PERCENT = 0.03;
	
	/**
	 * Percent of the total account amount that allowed to be spent  
	 * on a single trade.
	 * Usually between 1 (for most accounts) and 3 (very small accounts) %
	 */
	public static final double LOSS_PER_TRADE				= 0.03;
	
	/**
	 * Percent of the total account amount that allowed to be lost 
	 * per day
	 */
	public static final double LOSS_PER_DAY					= 0.03;
	
	/**
	 * Largest % drawdown on each strategy employed, multiplied 
	 * by this factor (may be within the range between 1.5 to 2.0)
	 * If reached, STOP trading this strategy immediately (close and disable)
	 */
	public static final double STRATEGY_DRAWDOWN_FACTOR = 1.6;
	
	/**
	 * Optional for losing trades. Allows exit before hitting trailing stop.
	 * If the price does not move this 
	 * number of points in my favour, position must be closed
	 */
	//public static final double FAVOUR_POINTS	 = 0.3;  //TODO to determine

	public static final String endOfDay = "16:45:00";
	
	////////////////////////////////////////////////////////////////////////
	// 			END OF CONSTANTS
	////////////////////////////////////////////////////////////////////////

}
