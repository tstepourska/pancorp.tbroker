package com.pancorp.tbroker.util;

public enum Globals {

	GLOBALS;
	
	///////// IB CONNECTION PROPERTIES ////////////////////
	public static int port				 = 7497;
	public static String host            = "127.0.0.1";
	public static int clientId			 = 0;
	
	///////// END OF IB CONNECTION PROPERTIES /////////////
	
	///////
	public static final String BASEDIR 				= "/Users/pankstep/run/TBroker/";
	public static final String DATADIR 				= "data/";
	
	//for IB
	public static final String SCANNER_INPUT_FILE 	= "scanner_params.xml";
	
	//Yahoo data URL
	public static final String YAH_DATA			= "http://finance.yahoo.com/d/quotes.csv?s=";
	public static final int YAH_MAX_TICKERS		= 190; // real number is 200;
	
	public static final String YAH_TAGS_PARAM_PREFIX					= "&f=";
	
	//YAHOO TAGS  
	//Company Details:
	public static final String YAH_COM_DETAILS_NAME						= "n"; //	Name
	public static final String YAH_COM_DETAILS_EXCHANGE					= "x"; //	Stock Exchange
	public static final String YAH_COM_DETAILS_MARKET_CAP				= "j1"; //	Market Capitalisation
	public static final String YAH_COM_DETAILS_FLOAT_SHARES				= "f6"; //	Float Shares
	public static final String YAH_COM_DETAILS_BOOK_VALUE				= "b4"; //	Book Value

	// Trading Data: Applicable to the most recent trading day (including today).
	public static final String YAH_TRADE_DATA_ASK						= "a";	//	Ask
	public static final String YAH_TRADE_DATA_BID						= "b";	//	Bid
	public static final String YAH_TRADE_DATA_ASK_SIZE					= "a5";	//	Ask Size
	public static final String YAH_TRADE_DATA_BID_SIZE					= "b6";	//	Bid Size
	public static final String YAH_TRADE_DATA_LAST_TRADE_DATE			= "d1";	//	Last trade Date
	public static final String YAH_TRADE_DATA_LAST_TRADE_TIME			= "t1";	//	Last trade Time
	public static final String YAH_TRADE_DATA_LAST_TRADE_PRICE			= "l1";	//	Last trade Price
	public static final String YAH_TRADE_DATA_LAST_TRADE_SIZE			= "k3";	//	Last trade Size
	public static final String YAH_TRADE_DATA_CHANGE					= "c1";	//	Change
	public static final String YAH_TRADE_DATA_CHANGE_PERCENT			= "p2";	//	Change in Percent
	public static final String YAH_TRADE_DATA_OPEN						= "o";	//	Open
	public static final String YAH_TRADE_DATA_DAY_HIGH					= "h";	//	Day's High
	public static final String YAH_TRADE_DATA_DAY_LOW					= "g";	//	Day's Low
	//public static final String YAH_TRADE_DATA_DAY_RANGE					= "m";	//	Day's Range
	public static final String YAH_TRADE_DATA_VOLUME					= "v";	//	Volume
	public static final String YAH_TRADE_DATA_AVG_DAY_VOLUME			= "a2";	//	Average Daily Volume
	public static final String YAH_TRADE_DATA_PREVIOUS_CLOSE			= "p";	//	Previous Close
	
	//Historical Performance:  52-week data only.
	//public static final String YAH_HISTORIC_PERFORM_52W_RANGE			= "w";	//	52-week Range
	public static final String YAH_HISTORIC_PERFORM_52W_HIGH			= "k";	//	52-week High
	public static final String YAH_HISTORIC_PERFORM_52W_LOW				= "j";	//	52-week Low
	public static final String YAH_HISTORIC_PERFORM_CHANGE_FROM_52HIGH	= "k4";	//	Change From 52-week High
	public static final String YAH_HISTORIC_PERFORM_CHANGE_FROM_52LOW	= "j5";	//	Change From 52-week Low
	public static final String YAH_HISTORIC_PERFORM_PERC_CHANGE_52W_HIGH= "k5";	//	% Change From 52-week High
	public static final String YAH_HISTORIC_PERFORM_PERC_CHANGE_52W_LOW	= "j6";	//	% Change From 52-week Low
	
	//Fundamental Analysis
	//Dividend and Target Price data are not always available and often restricted to US listed companies.
	public static final String YAH_FUNDAMENTALS_EARN_PER_SHARE			= "e";	//	Earnings/Share
	public static final String YAH_FUNDAMENTALS_EBITDA					= "j4";	//	EBITDA
	public static final String YAH_FUNDAMENTALS_PE_RATIO				= "r";	//	P/E Ratio
	public static final String YAH_FUNDAMENTALS_PEG_RATIO				= "r5";	//	PEG Ratio
	public static final String YAH_FUNDAMENTALS_DIV_SHARE				= "d";	//	Dividend/Share
	public static final String YAH_FUNDAMENTALS_EX_DIV_DATE				= "q";	//	Ex-Dividend Date
	public static final String YAH_FUNDAMENTALS_DIV_PAY_DATE			= "r1";	//	Dividend Pay Date
	public static final String YAH_FUNDAMENTALS_DIV_YIELD				= "y";	//	Dividend Yield
	public static final String YAH_FUNDAMENTALS_1YR_TARGET_PRICE		= "t8";	//	1 yr Target Price
	
	//Technical Analysis
	//The 50 and 200 day moving averages are two of the most commonly used indicators in the finance industry.
	public static final String YAH_TECH_ANALYS_50_DAY_MA					= "m3";	//	50-day Moving Average
	public static final String YAH_TECH_ANALYS_200_DAY_MA					= "m4";	//	200-day Moving Average
	public static final String YAH_TECH_ANALYS_CHANGE_FROM_200_DAY_MA		= "m5";	//	Change From 200-day Moving Average
	public static final String YAH_TECH_ANALYS_PER_CHANGE_FROM_200_DAY_MA	= "m6";	//	Percent Change From 200-day Moving Average
	public static final String YAH_TECH_ANALYS_CHANGE_FROM_50_DAY_MA		= "m7";	//	Change From 50-day Moving Average
	public static final String YAH_TECH_ANALYS_PER_CHANGE_FROM_50_DAY_MA	= "m8";	//	Percent Change From 50-day Moving Average
	
	
	public static final String YAH_QUERY_TAGS = 
				YAH_TAGS_PARAM_PREFIX+
	YAH_TRADE_DATA_ASK+								//double
	YAH_TRADE_DATA_BID+								//double
	YAH_TRADE_DATA_ASK_SIZE+						//int
	YAH_TRADE_DATA_BID_SIZE+						//int
	YAH_TRADE_DATA_LAST_TRADE_DATE+					//Date or Timestamp
	YAH_TRADE_DATA_LAST_TRADE_TIME+					//  ""
	YAH_TRADE_DATA_LAST_TRADE_PRICE+				//  double
	YAH_TRADE_DATA_LAST_TRADE_SIZE +				// int
	YAH_TRADE_DATA_CHANGE+							// ?
	YAH_TRADE_DATA_CHANGE_PERCENT+					// double
	YAH_TRADE_DATA_OPEN+
	YAH_TRADE_DATA_DAY_HIGH+
	YAH_TRADE_DATA_DAY_LOW+
	//YAH_TRADE_DATA_DAY_RANGE+
	YAH_TRADE_DATA_VOLUME+
	YAH_TRADE_DATA_AVG_DAY_VOLUME+
	YAH_TRADE_DATA_PREVIOUS_CLOSE+
	//YAH_HISTORIC_PERFORM_52W_RANGE+
	YAH_HISTORIC_PERFORM_52W_HIGH+
	YAH_HISTORIC_PERFORM_52W_LOW	+
	YAH_HISTORIC_PERFORM_CHANGE_FROM_52HIGH+
	YAH_HISTORIC_PERFORM_CHANGE_FROM_52LOW+
	YAH_HISTORIC_PERFORM_PERC_CHANGE_52W_HIGH+
	YAH_HISTORIC_PERFORM_PERC_CHANGE_52W_LOW
	;
	
	public static final int NA = -999;

	
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
