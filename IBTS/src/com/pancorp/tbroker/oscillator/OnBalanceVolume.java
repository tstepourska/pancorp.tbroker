package com.pancorp.tbroker.oscillator;

/**
 * Volume itself is a valuable indicator, and OBV takes a lot volume information and compiles it 
 * into a signal one-line indicator. The indicator measures cumulative buying/selling pressure 
 * by adding the volume on up days and subtracting volume on losing days. 
 * 
 * Ideally, volume should confirm trends. A rising price should be accompanied by a rising OBV; 
 * a falling price should be accompanied by a falling OBV.
 * 
 * For example, the figure shows shares for thetrending higher along with OBV. 
 * Since OBV didn't drop below its trendline, it was a good indication that the price 
 * was likely to continue trending higher after the pullbacks.
 * 
 * If OBV is rising and price isn't, price is likely to follow the OBV and start rising. 
 * If price is rising and OBV is flat-lining or falling, the price may be near a top.
 * If the price is falling and OBV is flat-lining or rising, the price could be nearing a bottom.
 * 
 * 
 * @author pankstep
 *
 */
public class OnBalanceVolume {

	public OnBalanceVolume() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
