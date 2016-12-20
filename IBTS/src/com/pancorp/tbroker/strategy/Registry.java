package com.pancorp.tbroker.strategy;

public class Registry {

	// SWING
	//Swing Trading positions typically last two to six days, but may last as long as two weeks.
	//The goal of Swing Trading is to identify the overall trend and then capture gains with swing trading within that trend. 
	//Technical Analysis is often used to help traders take advantage of the current trend in a security. 
	
	//The distinction between swing trading and day trading is the holding position time. 
	//Swing trading involves at least an overnight hold up to several week. By holding overnight, 
	//the swing trader incurs the unpredictability of overnight risk resulting in gaps up or down against the position. 
	//Swing trading assumes a larger price range and price move and therefore requires careful position sizing 
	// (take smaller position sizes) to minimize downside risk
	//Involves a mix of fundamental and technical analysis. 
	//Usually rely on larger time frame charts including the 15-minute, 60-minute, daily and weekly charts. 
	//Tend to require more holding time to generate the anticipated price move.
	
	//A swing trader tends to look for multi-day chart patterns. Some of the more common patterns 
	//involve moving average crossovers, cup-and-handle patterns, head and shoulders patterns, flags, and triangles. 
	//Key reversal candlesticks, such as hammers for reversal bottoms and shooting stars for reversal price tops, 
	//are commonly used in addition to other indicators to devise a solid trading game plan. 
	//Stop-losses tend to also be wider when swing trading to match the proportionate profit target.
	
	/////////////////////
	//1. bullish swing 
	//////////////////
	// security is uptrend: "go long" that security by buying shares, call options, or futures contracts. 
	// you should look for an initial movement upward as the major part of a trend, followed by a reversal or pull back, 
	// also known as the "counter trend." Then, following the counter trend you will want to see a resumption of the initial upward movement.
	//Since it is unknown how many days or weeks a pullback or counter trend may last, you should enter a bullish swing trade only after it appears 
	//that the stock has resumed the original uptrend. One way this is determined is to isolate the counter trend move. 
	//If the stock trades higher than the pullback's previous day's high, the swing trader could enter the trade 
	//after performing a risk analysis. This possible point of entry is known as the "entry point." 
	//This should be examined against two other price points to assess risk and determine your upside target.
	
	//First, find the lowest point of the pullback to determine the "stop out" point. If the stock declines 
	//lower than this point, you should exit the trade in order to limit losses. Then find highest point of 
	//the recent uptrend. This becomes the profit target. If the stock hits your target price or higher, 
	//you should consider exiting at least a portion of your position, to lock in some gains.

	//In other words, the difference between the profit target and the entry point is the approximate reward of the trade. 
	//The difference between the entry point and the stop out point is the approximate risk.

	//When determining whether it's worthwhile to enter a swing trade, consider using two-to-one as a minimum reward-to-risk ratio. 
	//So your potential profit should be at least twice as much as your potential loss. If the ratio is higher than that, 
	//the trade is considered better; if it's lower it's worse.
	
	//If you're swing trading by buying the stock, you would enter your trade using a buy-stop limit order. 
	//If you're trading in-the-money options, you would use a Contingent buy order. That way, as soon as the stock 
	//hits your intended entry point, your order will be activated, and the trade should be executed soon after..

	//Once either a stock or call option position is open, you would then enter a One-Cancels-Other order 
	//to sell the stock or call option as soon as it hits either your stop loss price or your profit taking price. 
	//This kind of Advanced Order ensures that as soon as one of the sell orders is executed, the other order is cancelled.

	
	
	//2. bearish swing
	//If the overall trend is down, then the trader could short shares or futures contracts, or buy put options.
	//Although they're usually not as orderly as an uptrend, downtrends also tend to move in a step-like 
	//or zig-zag fashion. For example, a stock could decline over the course of many days. Then it may retrace 
	//part of the loss over the next few days before turning south once more. 
	
	//Since it's very difficult to predict exactly how long a bear rally, or "counter trend" may last, 
	//you should enter a bearish swing trade only after it seems that the stock has continued downwards. 
	//To do this, examine the bear rally very closely. If the stock heads lower than the counter trend's previous day's low, 
	//the swing trader could enter a bearish position.

	//As with bullish swing trades, the entry point would be compared to the stop out and profit target points 
	//to analyze the potential rewards and risks of the trade. On a bearish swing trade, the stop out point 
	//is the highest price of the recent counter trend. So if the stock rose higher than this price, 
	//you would exit the trade to minimize losses. The profit target is the lowest price of the recent downtrend. 
	//So if the stock reached this price or lower, you should consider exiting at least some of the position to lock in some gains.

	//The difference between the entry point and the profit target is the targeted reward of the trade. 
	// The difference between the stop out point and the entry point is the assumed risk. 
	//It is preferred to have a reward-to-risk ratio of two-to-one or greater.
	
	//As with bullish swing trades, if the reward-to-risk ratio is acceptable, you could enter your trade 
	//using a sell-stop limit order. This would result in selling the stock short once it hits your entry point. 
	//Selling short is the process of borrowing shares from your online broker and selling them in the open market, 
	//with the intention of purchasing the shares back for less cost in the future. An alternative to short selling 
	//would be to buy an in-the-money put option. If you choose to use options, you would use a Contingent order 
	//to buy the put after the stock hit the entry price.

	//After your trade is open, you could then place a One-Cancels-Other order to cover both your stop loss price 
	//and your profit taking price. If one of these trades were executed, the other order would be cancelled.
	
	
	//Fading: Working Against the Trend
	//Swing traders usually go with the main trend of the stock. But some traders like to go against it 
	//and trade the counter trend instead. This is known as "fading," but it has many other names: 
	//counter-trend trading, contrarian trading, and trading the fade. 
	//During an uptrend, you could take a bearish position near the swing high because you expect the stock 
	//to retrace and go back down. 
	//During a downtrend to trade the fade, you would buy shares near the swing low if you expect the stock 
	//to rebound and go back up.

	//Obviously, then fading, you'll want to exit the trade before the counter trend ends, 
	//and the stock resumes the main trend, whether bullish or bearish.

	//Many times neither a bullish nor bearish trend is present, but the security is moving 
	//in a somewhat predictable pattern between parallel support and resistance areas. 
	//There are swing trading opportunities in this case too, with the trader taking a long position 
	//near the support area and taking a short position near the resistance area.
	

	
	private Registry() {

	}

}
