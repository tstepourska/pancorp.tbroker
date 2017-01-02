package com.pancorp.tbroker.strategy.stock;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ib.client.Contract;
import com.pancorp.tbroker.strategy.StrategyAbstract;
import com.pancorp.tbroker.util.Utils;

public class StrategyIntradayLong extends StrategyAbstract {
	private static Logger lg = LogManager.getLogger(StrategyIntradayLong.class);	
		
	public StrategyIntradayLong(Contract c){
		this.setName("Vlad");
		
		this.setContract(c);
	}
	
	@Override
	public void run(){
		//started after ticker was found

		boolean buy = false;
		
		try {
			while(working && !buy){
				buy = queryEnter();
				if(buy)
					break;
				Thread.sleep(600000);    //10-min
			}
			
			enter();
			
		} catch (Exception e2) {
			Utils.logError(lg, e2);
			working = false;
		}

		//check when filled
		open = true;

		boolean sell = false;
		//monitor: receive 1-minute bars and look for exit condition(s)
		try {
			while(!sell){
			
				sell = queryExit();
				if(sell){
					break;
				}
				else {
					//if switched off, sell immediately
					if(!working)
						break;
				}

				Thread.sleep(60000);
			}
			
			exit();			
		}
		catch(Exception e){
			Utils.logError(lg, e);
		}
	}
	
	//entry conditions:

	
	//targets:
	//profit $100 per contract per week
	//stop loss is $66 per contract (2/3 of profit)
	
	//exit conditions:
	//profit target reached (less losses, commissions and other fees)
	//OR
	//stop loss reached
	
	//timeframes
	/// 60-min, 30-min, 15-min, 10-min, 5-min, 3-min, 1-min
	// ?optional? - daily charts to determine the global trend - optional?
	// 1-minute timeframe to enter the trade
	// 5-minute timeframe to exit the trade
	// 15-minute timeframe to help determine the trend throughout the duration of the trade
	
	/**
	 * 
	 */
	public boolean queryEnter() throws Exception{
		boolean buy = false;
		
		return buy;
	}
	
	/**
	 * 
	 */
	public boolean queryExit() throws Exception{
		boolean exit = false;
		// 1-minute timeframe to check for exit conditions(price)
		
		return exit;
	}
	
	@Override
	public void enter()  throws Exception{
		// 1-minute timeframe to enter the trade
		//submit limit order to buy
		//check account amt
		//create limit BUY order with the price .... to adjust by percentage
		createOrder();
		
		//create stop loss SELL order		
		//create target profile SELL order
		//==================================
		//create OCO order with 2 orders above
		createOrder();
		
		//submit limit BUY order
		submitOrder();	
	}

	@Override
	public void exit() throws Exception {
		// 1-minute timeframe to exit the trade
		//cancel any existing orders and submit market order to sell ?
		submitOrder();
	}
}
