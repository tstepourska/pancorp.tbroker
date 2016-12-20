package com.pancorp.tbroker.condition;

public class EntryCondition extends AbstractCondition {
	
	//TREND FOLLOWING (volume must confirm the trend - increase if moving in direction of primary trend)
	//===================
	
	//Simple Moving Averages  (preferable 5 to 10 days) (or 50-periods MA)
	//-------------------------------------------
	//	BUY: closing price moves above the moving average
	//	SELL: closing price moves below the moving average
	
	//Crossover of Moving Averages
	//------------------------------------------
	// 2 MA - fast (14 bars/days) and slow (20 bars/days)
	//
	//	BUY: fast crosses slow from below
	//	SELL:fast crosses slow from above
	
	//Turtle Trading
	//-------------------------------------------
	// Looks at high and low through the past 20 days 
	// and generates the following signals:
	//
	//	BUY:	current prices move higher than the high
	//			of the previous 20 bars
	//	SELL: 	current prices move lower than the low 
	//			of the previous 20 bars
	
	//Moving Average Convergence Divergence (MACD)
	//----------------------------------------------
	// 2 MA: difference between 26-bar exponential MA (EMA) and 12-bar
	//       plotted on a chart and oscillates above and below zero
	//
	// 9-bar EMA of the MACD, called the "signal line", is then plotted 
	// on top of the MACD, functioning as a trigger:
	//
	//	BUY: signal line crosses MACD from below
	//	SELL: signal line crosses MACD from above
	
	/////////////////////////////////////////////////
	
	// TREND FADING
	//======================
	
	//Williams %R (overbought / oversold indicator)
	//----------------------------------------
	//	Shows the relationship of the closing price to a high-low range
	//  over specific period of time (typically 14 days)
	//
	// Subtract current day's close from the lowest intraday low 
	// of the last 'X' number of days, then divides this distance 
	// by the highest high minus the lowest low of the last number of days
	//
	// The result is plotted on a chart and oscillates between 0 and 100
	// 
	//	BUY:  when indicator has value below 20
	//	SELL: when indicator has value above 80
	
	//Relative Strength Index (RSI) (overbought / oversold indicator)
	//----------------------------------------
	// Compares the magnitude of a stock's recent gains 
	// to the magnitude of its recent losses and turns 
	// this information into a number between 0 and 100
	// Takes a single parameter - the number of time periods (recommended 14)
	//
	//	BUY:	RSI crosses the 30-line (over-sold zone) from below
	//	SELL:	RSI crosses the 70-line (over-bought zone) from above
	
	//Bollinger Bands and Channels
	//-----------------------------
	// Consists of moving average (most popular is 21=bar) and two standard deviations, 
	// one above MA and one below
	// Important: BB contain up to 95%of the closing prices, 
	// depending on settings
	//
	//	BUY:	prices move below  the lower BB
	//	SELL:	prices move above the upper BB
}
