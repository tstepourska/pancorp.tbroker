package com.pancorp.tbroker.oscillator;

/**
 * The RSI is another oscillator, but because its movement is contained between zero and 100, 
 * it provides some different information than the MACD.
 * 
 * One way to interpret the RSI is by viewing the price as "overbought" - and due for a correction - 
 * when the indicator is above 70, and the price as oversold - and due for a bounce - 
 * when the indicator is below 30. 
 * In a strong uptrend, the price will often reach 70 and beyond for sustained periods, 
 * and downtrends can stay at 30 or below for a long time. While general overbought and oversold 
 * levels can be accurate occasionally, they may not provide the most timely signals for trend traders.
 * 
 * An alternative is to buy near oversold conditions when the trend is up, 
 * and take short trades near overbought conditions in a downtrend.
 * 
 * Say the long-term trend of a stock is up. A buy signal occurs when the RSI moves below 50 and then back above it. 
 * Essentially this means a pullback in price has occurred, and the trader is buying 
 * once the pullback appears to have ended (according to the RSI) and the trend is resuming. 
 * 50 is used because the RSI doesn't typically reach 30 in an uptrend unless a potential reversal is underway.
 * 
 * A short-trade signal occurs when the trend is down and the RSI moves above 50 and then back below it.
 * 
 * @author pankstep
 *
 */
public class RelativeStrengthIndex {

	public RelativeStrengthIndex() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
