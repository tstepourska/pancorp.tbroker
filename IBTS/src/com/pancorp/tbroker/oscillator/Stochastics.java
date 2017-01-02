package com.pancorp.tbroker.oscillator;

public class Stochastics {


	
	/**
	 * Formula
	 * 
	 * Stochastics is measured with the %K line and the %D line, and it is the %D line that we follow closely, 
	 * for it will indicate any major signals in the chart. Mathematically, the %K line looks like this:
	 * 
	 * %K = 100[(C – L5close)/(H5 – L5)]
	 * C = the most recent closing price
	 * L5 = the low of the five previous trading sessions
	 * H5 = the highest price traded during the same 5 day period.
	 * 
	 * The formula for the more important %D line looks like this:
	 * %D = 100 X (H3/L3)
	 * 
	 * We show you these formulas for interest's sake only. Today's charting software does all the calculations, 
	 * making the whole technical analysis process so much easier and thus more exciting for the average investor. 
	 * For the purpose of realizing when a stock has moved into an overbought or oversold position, 
	 * stochastics is the favored technical indicator as it is easy to perceive and has a high degree of accuracy.
	 * 
	 * Reading the Chart
	 * The K line is the fastest and the D line is the slower of the two lines. The investor needs to watch 
	 * as the D line and the price of the issue begin to change and move into either the overbought (over the 80 line) 
	 * or the oversold (under the 20 line) positions. The investor needs to consider selling the stock 
	 * when the indicator moves above the 80 level. Conversely, the investor needs to consider buying an issue 
	 * that is below the 20 line and is starting to move up with increased volume.
	 */

	public Stochastics() {
		// TODO Auto-generated constructor stub
	}
}
