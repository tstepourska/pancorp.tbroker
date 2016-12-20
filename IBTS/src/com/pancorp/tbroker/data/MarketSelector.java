package com.pancorp.tbroker.data;

public class MarketSelector {
	/**
	 * 1. Initial capital requirements (lower -  better)
	 * 2. Leverage
	 * 3. Liquidity
	 * 4. Volatility
	 * 
	 * 
	 *    Stocks/ETFs
	 *   		1. If 4 or more trades per week, min 25000 in margin account (28000-30000 with cushion) 
	 *   		2. Leverage max 1:8. Min initial investment of 2000 required for margin acct
	 *   		3. more than 10000 stocks for trading US
	 *   			around 900 have avg volume of more than 2,000,000 shares per day
	 *   			more then 600 of which are traded with over 3,000,000 shares per day
	 *   		4. Average daily movement between 1 and 2 %, many have more 
	 *   
	 *   Forex
	 *   		1) USD
	 *   		2) Japanese Yen (JPY)
	 *   		3) Britich Pound (GBP)
	 *   		4) Swiss Franc (CHF)
	 *   		5) European Union Euro (EUR)
	 *   		6) Australian Dollar (AUD)
	 *   		7) New Zealand Dollar (NZD)
	 *   		8) Canadian Dollar (CAD)
	 *   
	 *   	Most typical
	 *   		EUR/USD			avg daily movement 0.5 to 1 %
	 *   		USD/JPY			avg daily movement 0.5 to 1.5 %
	 *   		GBP/USD			avg daily movement 0.5 to 1.5 %
	 *   	
	 *   	Not trading on exchange, trading against your broker
	 *   
	 *   		1. $1000 to start
	 *   		2. Leverage 1:100
	 *   		3. Liquidity - extreme
	 *   		4. Volatility not as great as in stock market (see above examples)
	 *   
	 *   Futures
	 *   		
	 *   		More risky than stock trading
	 *   
	 *   		Type of future contracts:
	 *   		
	 *   		1)Currency
	 *   		2)Interest Rates
	 *   		3)Energies
	 *   		4)Food Sector
	 *   		5)Metals
	 *   		6)Agricultural
	 *   
	 *   	Commodities Future Trading Commissions (CFTC)
	 *   		Future properties
	 *   			Underlying commodity/asset
	 *   			when it must be delivered
	 *   			currency of the transaction
	 *   			what date the contract stops trading
	 *   			tick size
	 *   			minimum legal change in price
	 *   
	 *   		Most popular
	 *   		1) S&P 500 E-mini		500 to trade a 75000 contract / around 2,500,000 contracts a day /vol 1 to 3 % per day
	 *   		2) E-mini NASDAQ 100	500 to trade 45000 contract  / around 500,000 contracts per day  /vol 1 to 5 % per day
	 *   		3) Light Sweet Crude Oil
	 *   		4) Gold					400 to trade 27000 contract                                      /vol 1 to 2.5 % per day
	 *   		5) E-mini Euro FX										around 200,000 contracts per day / vol 0.5 to 1.5 % per day
	 *   
	 *   	Before expiration contract could (should) be sold or rolled over
	 *   
	 *   		1. Init minimum 5000
	 *   		2. Leverage - initial margin that must be deposited for each contract ( paid by both buyer or seller), 
	 *   					  and maintenance margin - minimum amount that must be in the account at all times, 
	 *   					  Example: 2000 initial margin and 1500 maintenance
	 *   		3. Liquidity Good - see examples above
	 *   		4. Volatility Good - see examples above
	 *   
	 *   
	 *    Stock Options
	 *    		1. Same as stock
	 *    		2. High leverage, but many dependencies; higher than stock, but lower than futures or forex
	 *    		3. Liquidity - not very liquid  but prices are NOT subject of market manipulations
	 *    		4. Very high volatility for options traded "at the money" or "in the money"
	 *    
	 *    DAILY OPTIONS TRADING IN NOT FOR NOVICE TRADERS!!!
	 */
}
