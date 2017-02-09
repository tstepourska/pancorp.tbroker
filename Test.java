package com.pancorp.tbroker;

import java.util.LinkedList;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pancorp.tbroker.model.Bar;

public class Test {
	private static Logger lg = LogManager.getLogger(Test.class);
	
	//tfFactor represents how many 5 sec Bars are in a single timeframe unit
	public static long tfFactor = 0;
	static long MIN = 12;  // 1*12;
	static long HOUR =720; // 1*12*60;
	static long DAY = 17280;  //1*12*60*24;
	
/*	public void setQueue(LinkedList<Bar> q){
		this.queue = q;
	}
	*/
	
	/**
	 * Calculates Simple Moving Average of close price for specified number of timeframe units
	 * 5, 10, 20
	 * 
	 * -->Locate stock that breaking out up(down) strongly
	 * -->Select 2 SMA to apply to the chart (ex. 5 and 10)
	 * -->Make sure price has not been touching the 5 SMA or 10 SMA excessively in the last 10 bars
	 * -->Wait for the price to close above(below) both moving averages in the counter direction 
	 * of the primary trend on the SAME BAR
	 * -->Enter the trade on the next bar
	 * ============================
	 * ~ price goes crosses above the SMA  - ~
	 *============================== 
	 * Breakouts in the morning:
	 * 
	 *-->Price greater  than 10 dollars
	 *-->Greater than 40000 shares traded every 5 minutes
	 *-->Less than 2% from its moving average
	 *-->Volatility has to be solid enough to hit profit target 1.62%
	 *-->Cannot have a number of bars that are 2% in range (high to low)
	 *-->Must open the trade between 9:50 and 10:10 am
	 *-->Need to exit the trade no later than 12 noon
	 *-->Close the trade out if the stock closes above or below its 10-period MA after 11 am
	 * 
	 * @param smaSize			--number of units to calculate MA upon, ex 21
	 * @param timeframeSize		--number of timeframes in a unit, ex 5 (5-min timeframe)
	 * @param timeframeUnit		--time unit used in timeframe, ex MIN (5-min timeframe)
	 * @param queue				--list of real time Bars to calculate from
	 * 
	 * @return double
	 * 
	 * @throws NotEnoughDataException
	 * @throws Exception
	 */
	public double SMA(int smaPeriod, int timeframeSize, String timeframeUnit, LinkedList<Bar> queue) throws NotEnoughDataException, Exception {
		double sma = 0;
		double total = 0;
		double value;
		
		switch(timeframeUnit){
		case "MIN":
			tfFactor = MIN;
			break;
		case "HOUR":
			tfFactor = HOUR;
			break;
		case "DAY":
			tfFactor = DAY;
			break;
			default:
		}
		
		long numOfBars = smaPeriod*tfFactor;
		p("calcSMA: numOfBars: " + numOfBars + ", queue size: " + queue.size());
		
		if(queue.size()<numOfBars)
			throw new NotEnoughDataException();
		
		for(int i=0;i<numOfBars;i++){
			if(i%tfFactor==0){
				value = queue.get(i).close();
				total+=value;
			}
		}
		
		sma = total/smaPeriod;
		return sma;
	}
	
	/**
	 * Calculates Exponential Moving Average of close price for specified number of timeframe units.
	 * EMA is a weighted moving average (WMA) that gives more weighting to recent price data than 
	 * SMA does. The EMA responds more quickly to recent price changes than the SMA. 
	 * The formula calculating EMA involves using a multiplier and stating with SMA.
	 * 
	 * @param smaSize			--number of units to calculate MA upon, ex 21
	 * @param timeframeSize		--number of timeframes in a unit, ex 5 (5-min timeframe)
	 * @param timeframeUnit		--time unit used in timeframe, ex MIN (5-min timeframe)
	 * @param queue				--list of real time Bars to calculate from
	 * 
	 * @return double
	 * 
	 * @throws NotEnoughDataException
	 * @throws Exception
	 */
	public double EMA(int emaPeriod, int timeframeSize, String timeframeUnit, LinkedList<Bar> queue) throws NotEnoughDataException, Exception {
		String fp = "EMA: ";
		double ema = 0;
		
		//Step 1. Calculate SMA
		double sma = SMA(emaPeriod,timeframeSize, timeframeUnit, queue);
		if(lg.isDebugEnabled())
			lg.debug(fp + "sma: " + sma);
		
		//Step 2. Calculating the weighting multiplier
		double multiplier = 2/emaPeriod + 1;
		if(lg.isDebugEnabled())
			lg.debug(fp + "multiplier: " + multiplier);
		
		//Step 3: Calculate EMA
		//get the closing price of the last bar
		double close = queue.get(0).close();
		//get the ema for previous timeframe unit
		double ema0 = queue.get(1).ema();
		
		ema = (close - ema0)*multiplier+ema0;
		
		return ema;
	}
	
	/**
	 * between 0 and 20  - overbought
	 * between -80 and -100  - oversold
	 * 
	 * @param latestClose
	 * @param highestHigh
	 * @param lowestLow
	 * 
	 * @return		between 0 and -100
	 */
	public double WilliamsR(double latestClose, double highestHigh, double lowestLow){
		double wr = 0;

		wr = (highestHigh - latestClose)/(highestHigh - lowestLow)*(-100);
		return wr;
	}
	
	
	/** This exception indicates that there is not enough Bars in the queue, 
	 * application must wait until enough data has been obtained, 
	 * or go to database to get historical data
	 * @author Txs285
	 *
	 */
	public static class NotEnoughDataException extends Exception {
		private static final long serialVersionUID = -1908829006025105879L;
		public NotEnoughDataException(){}
	}

	public static void main(String[] args){
		Test t = new Test();
		p("test created");
		
		//method params
		LinkedList<Bar> queue = new LinkedList<Bar>();
		int smaSize=  21;
		int timeframeSize = 15;
		String timeframeUnit = "MIN";
		
		//to initialize queue for unit test
		Bar b;
		double basePrice= 28.7;
		double dev = 0;
		boolean positive = true;
		int volume = 300000;
		int cnt= 10;
		//tfFactor = MIN;
		long queueSize = 302; // smaSize*tfFactor+50;

		queue = new LinkedList<>();
		double closeP = 28.7;
		double sma = 0;
		p("variables assigned");
		
		Random rValue = new Random(System.currentTimeMillis());
		Random rSign = new Random(System.currentTimeMillis());
		p("seeded randomizers");
		
		for(int i=0;i<queueSize; i++) { //smaSize*tfFactor;i++){
			dev = rValue.nextDouble();
			//p("dev: " + dev);
			positive = rSign.nextBoolean();
			//p("positive: " + positive);
			
			if(!positive)
				dev = Math.abs(dev) * (-1);
			
			closeP = closeP+dev;
			p("closeP: " + closeP);
			b= new Bar(0,
					basePrice,
					basePrice,
					basePrice,
					closeP,	//close!
					basePrice,
					volume,
					cnt
					);
			queue.addFirst(b);		
			
			try {
				sma = t.SMA(smaSize, timeframeSize, timeframeUnit, queue);
				p("sma["+i+"]: " + sma);
			}
			catch(NotEnoughDataException e){
				try {
					Thread.sleep(400);
				}catch(InterruptedException ie){}
				continue;
			}
			catch(Exception e){
				p("Error: " + e.getMessage());
			}
		}
		p("Queue is full: " + queue.size());
		//t.setQueue(q);
		

	}
	
	private static void p(String msg){
		System.out.println(msg);
	}
}
