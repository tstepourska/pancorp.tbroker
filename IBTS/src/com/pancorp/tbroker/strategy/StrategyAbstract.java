package com.pancorp.tbroker.strategy;

import java.util.HashMap;
import java.util.LinkedList;

import com.pancorp.tbroker.condition.ICondition;
import com.ibts.client.Contract;

public abstract class StrategyAbstract extends Thread implements IStrategy {
	
	
	/**
	 * Strategies very according
	 * market conditions
	 * time of the day
	 * timeframe for trading
	 * 
	 * 3 categories
	 * 		breakout
	 * 		retracement
	 * 		reversal
	 * 
	 * Example:
	 * primary strategy is designed for non-trending market. It is a 
	 * breakout strategy which aims to jump on bandwagon upon the 
	 * continuation of a strong trend
	 */
	
	/**
	 * Setup is the set of characteristics that enable you to identify 
	 * a high probability trade prior to your entry trigger being hit.
	 * 
	 * Example:
	 * 1. Opening gap id 1%-3%
	 * 2. The 50-day moving average must be clearly in the direction 
	 * of the proposed trade
	 * 3. The gap should be into resistance/support, but not breaching it
	 * 4. Evidence of strong volume pre-market
	 * 
	 * Example for secondary strategy:
	 * 1. Price must be in a clear up/down trend, according to my definition 
	 * of a trend, which is XYZ
	 * 2. Price breaks  through yesterday's high / low of the day to make new high / low
	 * 3. Price pulls back to yesterday's high / low but does not break it.
	 */
	
	/**
	 * Signals to trigger my entry
	 * 
	 * Example: Primary strategy:
	 * 
	 * To go long upon breach of the open on a 5 minute chart providing the open 
	 * is high of the day (H.O.D). Reverse for a short trade
	 * 
	 * Example Secondary strategy
	 * 
	 * To go long when the stock resumes the direction of the trend and hits 
	 * yesterday high on a 10 minute chart. Reverse for a short trade
	 * 
	 *
	 */
	
	/**
	 * TODO  Specify what you need to do to CLOSE your position:
	 * If you are long, you have to SELL to close
	 * If you are short, you have to BUY to close
	 */
	
	/**
	 * Recording trades
	 * Entries, exits, stops, targets, S&R levels, open/close, high/low, 
	 * duration of trades, key lessons learned
	 */
	
	/**
	 * Winning trade: Optional, for advanced strategy. Beginners can exit right after their 
	 * profit target is made. 
	 * Winning trades exit rule(s) / condition(s)
	 * Close position immediately, if the price hits crossing XYZ moving 
	 * average, or other rule
	 */
	
	/**
	 * Winning  trade: closing half of the position at the first target, or at the first sign
	 * of weakness, and let the otoher half run
	 * For ex, upon increase/decrease in volume compared to the previous price bar
	 */
	
	/**
	 * Winning  trade: closing the other half of the position at the 
	 * --very end of the day
	 * --within the X% of the high of the day (H.O.D)
	 * For ex, upon increase/decrease in volume compared to the previous price bar
	 */
	

	
	///////////////////////////////////////////////////////////////////////////////////
	// 		COMMON MEMBERS
	///////////////////////////////////////////////////////////////////////////////////
	/**
	 * On/off switch
	 */
	public boolean working = true;
	
	protected HashMap openedOrders;
	protected HashMap<String, ICondition> entryConditions;
	protected HashMap<String, ICondition> exitConditions;	
	protected double tradeAmount;
	
	/**
	 * Position
	 */
	public boolean open = false;
	
	private Contract contract;
	

	//////////////////////////////////////////////////////////////////////////////////
	//		END OF COMMON MEMBERS
	//////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return the contract
	 */
	public Contract getContract() {
		return this.contract;
	}

	/**
	 * @param c the contract to set
	 */
	public void setContract(Contract c) {
		this.contract = c;
	}

	//////////////////////////////////////////////////////////////////////////////////
	//		PUBLIC METHODS
	//////////////////////////////////////////////////////////////////////////////////
	/**
	 * @return the tradeAmount
	 */
	public double getTradeAmount() {
		return tradeAmount;
	}

	/**
	 * @param tradeAmount the tradeAmount to set
	 */
	public void setTradeAmount(double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	
	@Override
	public HashMap getOpenedOrders(){
		return this.openedOrders;
	}
	
	@Override
	public HashMap<String, ICondition> getEntryConditions(){
		return this.entryConditions;
	}
	
	@Override
	public void setEntryConditions(HashMap<String, ICondition> m){
		this.entryConditions = m;
	}
	
	@Override
	public HashMap<String, ICondition> getExitConditions(){
		return this.exitConditions;
	}
	
	@Override
	public void setExitConditions(HashMap<String, ICondition> m){
		this.exitConditions = m;
	}	
	
	public void update(String location) {
		
	}
	
	public void init(String location){
		
	}
	
	@Override
	public void createOrder() throws Exception{
		
	}
	
	@Override
	public void submitOrder() throws Exception{
		
	}
	/////////////////////////////////////////////////////////////////
	//		END OF PUBLIC METHODS
	/////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////
	//		ABSTRACT METHODS
	//////////////////////////////////////////////////
	//@Override
	//public abstract boolean query() throws Exception;
	@Override
	public abstract void enter()  throws Exception;
	@Override
	public abstract void exit() throws Exception;
	//////////////////////////////////////////////////
	//		END OF ABSTRACT METHODS
	//////////////////////////////////////////////////
}
