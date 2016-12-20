package com.pancorp.tbroker.util;

public enum Globals {

	GLOBALS;
	
	private static double accountTotal		= 10000.00;
	
	public static double getAccountTotal(){
		return accountTotal;
	}
	
	public static synchronized void setAccountTotal(double d){
		accountTotal = d;
	}
	
	///////////////////////////////////////////////////////////////////////
	//		STRATEGIES GLOBAL VARIABLES
	///////////////////////////////////////////////////////////////////////

	
	/**
	 * Success ratio: probability of the trade being successful. 
	 * Number of profitable trades divided by a number of total trades 
	 * multiplied by 100
	 * 
	 * Has to be established, monitored and updated, depending on 
	 * the trade setup (TODO - to define and test it) and numerous 
	 * external variables
	 * For example, the setup may have a higher probability of success 
	 * if it appears just above the round number (for a long position) 
	 * than it does if it appears just beneath it
	 */
	public static double PROBABILITY_OF_SUCCESS = 50;  //TODO - to determine and test!!
	
	/**
	 * To calculate, need to know Sharpe and Success ratios
	 * 
	 * Example:
	 * 		Success ratio: 2:1 (66%)
	 * 		Sharpe ratio: 1.5:1 so if we risk $40, we stand to make $60 on the winning trades
	 * 
	 * The success ratio tells us that we win 2 out of every 3 trades, and the Sharpe ratio 
	 * tells us, that, of the 2 winners, we make 2 * +$60+ = +$120
	 * Our one losing trade of the three costs us -$40
	 * On all three trades we risked $40 and ended up with a net gain of +$80
	 * 
	 * Therefore, if we divide the net gain by the amount we risked, we arrive at 
	 * risk-reward ratio of 2:1
	 * 
	 * Caution: assumed that we only trade the setups defined in our plan and that 
	 * they have been thoroughly back and forward tested to determine their probability 
	 * of success (Success Ratio)
	 */
	public static double RISK_REWARD_RATIO      = 50;
	
	/**
	 * Average number of $$$ made on profitable trades relative to the average 
	 * number of $$$ lost on unprofitable trades: divide avg $$$ gained in profitable 
	 * trades on combined figure of the average number of $$$ gained and lost, 
	 * multiplied by 100
	 */
	public static double SHARPE_RATIO      		= 50;	//TODO to determine and test
	////////////////////////////////////////////////////////////////////////////////////
	//		END OF STRATEGIES GLOBAL VARIABLES
	////////////////////////////////////////////////////////////////////////////////////


}
