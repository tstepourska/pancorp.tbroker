package com.pancorp.tbroker.strategy;

import com.ib.client.Contract;

public class StrategySelector {
	/**
	 * 1. Select a Market
	 * 
	 * 2. Select a Timeframe
	 * 
	 * 3. Select a Trading Style/Approach - technical analysis
	 * 
	 * 4. Define entry points
	 * 
	 * 5. Define exit points
	 * 
	 * 6. Evaluate your trading strategy
	 * 
	 * 7. Improve your trading strategy
	 */
	/**
	 * TODO to determine the difference of trending market vs side-moving market
	 * This has to be updatable often, per security to be able to choose 
	 * strategy
	 * 
	 * In trading markets should use trend following strategies
	 * In non-trading (side-moving) markets should use trend-fading strategies
	 */
	private boolean trending = false;
	
	private Contract contract;
	
	/**
	 * It is preferrable (and easier) to trade a trending 
	 * market. Therefore, we need to select a market that 
	 * is trending from a list of markets (2-3?)
	 */
	
	/**
	 * High / low volatility markets
	 */

	/**
	 * @return the trending
	 */
	public boolean isTrending() {
		return trending;
	}

	/**
	 * @param trending the trending to set
	 */
	public void setTrending(boolean trending) {
		this.trending = trending;
	}
	
	public StrategyAbstract resolveStrategy(Contract c){
		StrategyAbstract s = null;
		
		return s;
	}
}
