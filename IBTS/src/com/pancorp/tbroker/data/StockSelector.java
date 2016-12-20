package com.pancorp.tbroker.data;

public class StockSelector {

	//high volume  <2,000,000 per day
	//large day range > 
	
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
