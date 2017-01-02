package com.pancorp.tbroker.data;

import com.pancorp.tbroker.util.Globals;

public class StockSelector {

	//Selection for trade following
	//high volume  <2,000,000 per day
	//large day range > 
	//returns about 14 distinct rows
	String sql = "SELECT run_update_date_time, symbol, exchange, (TRADE_DATA_DAY_HIGH-TRADE_DATA_DAY_LOW)/TRADE_DATA_DAY_HIGH AS PERC_DAY_RANGE "+
				"FROM tbl_yahoo_market_data WHERE "+
				"run_update_date_time IN (SELECT MAX(run_update_date_time) FROM tbl_yahoo_market_data) "+ 
				"AND TRADE_DATA_AVG_DAY_VOLUME > "+ Globals.AVG_DAY_VOLUME + 
				" AND TRADE_DATA_PREVIOUS_CLOSE > "+ Globals.PRICE_PREV_CLOSE_MIN + 
				" AND TRADE_DATA_PREVIOUS_CLOSE < "+ Globals.PRICE_PREV_CLOSE_MAX + 
				" AND (TRADE_DATA_DAY_HIGH-TRADE_DATA_DAY_LOW)/TRADE_DATA_DAY_HIGH > "+ Globals.PRICE_DAY_RANGE_PERC_MIN + 
				" ORDER BY PERC_DAY_RANGE DESC";
	
	//for reversal stock candidate (trend fading category of strategies)
	
	// 1) hitting new highs or new lows
	// 2) at least 5-10 consequtive candles of the same color
	// 3) RSI below 10 or above 90 to indicate extreme conditions
	// 4) Think of these stocks as a rubber band, the more streched out it is,
	//    the better the snap back potential
	// 5) Look to buy the first candle that begins to reverse with a stop 
	//    either at the high / low or minus 20 cents
	// 6) Use trailing stops to keep in winning trade as long as possible
	
	
}
