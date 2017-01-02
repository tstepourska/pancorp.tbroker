package com.pancorp.tbroker.oscillator;

public class MACrossover {

	/**
	 * There are several ways to utilize the moving average. The first is to look at the angle of the moving average. 
	 * If it is mostly moving horizontally for an extended amount of time, then the price isn't trending, it is ranging. 
	 * If it is angled up, an uptrend is underway. 
	 * Moving averages don't predict though; they simply show what the price is doing on average over a period of time.
	 * 
	 * Crossovers are another way to utilize moving averages. By plotting both a 200-day and 50-day moving average on your chart, 
	 * a buy signal occurs when the 50-day crosses above the 200-day. 
	 * A sell signal occurs when the 50-day drops below the 200-day. The time frames can be altered to suit 
	 * your individual trading time frame.
	 * 
	 * When the price crosses above a moving average, it can also be used as a buy signal, and when the price 
	 * crosses below a moving average, it can be used as a sell signal. Since price is more volatile 
	 * than the moving average, this method is prone to more false signals, as the chart above shows.      
	 */
	public MACrossover() {
		// TODO Auto-generated constructor stub
	}

}
