package com.ts.test.main;

import java.util.ArrayDeque;
import java.util.LinkedList; 
import java.util.Random; 

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
 
import com.ts.test.model.Bar; 
import com.ts.test.model.IBar;
import com.ts.test.util.Constants;

public class Calculator {

	private static Logger lg = LogManager.getLogger(Test.class); 
	 
	//tfFactor represents how many 5 sec Bars are in a single timeframe unit 
	private long tfFactor = 0; 
	int period;
	int timeframeSize;
	String timeframeUnit;
	
	public Calculator(int p, int tfSize, String tfUnit, long tf){
		this.tfFactor = tf;
		this.period = p;
		this.timeframeSize = tfSize;
		this.timeframeUnit = tfUnit;
	}
	
	/** 
	26 	 * Calculates Simple Moving Average of close price for specified number of timeframe units 
	27 	 * 5, 10, 20 
	28 	 *  
	29 	 * -->Locate stock that breaking out up(down) strongly 
	30 	 * -->Select 2 SMA to apply to the chart (ex. 5 and 10) 
	31 	 * -->Make sure price has not been touching the 5 SMA or 10 SMA excessively in the last 10 bars 
	32 	 * -->Wait for the price to close above(below) both moving averages in the counter direction  
	33 	 * of the primary trend on the SAME BAR 
	34 	 * -->Enter the trade on the next bar 
	35 	 * ============================ 
	36 	 * ~ price goes crosses above the SMA  - ~ 
	37 	 *==============================  
	38 	 * Breakouts in the morning: 
	39 	 *  
	40 	 *-->Price greater  than 10 dollars 
	41 	 *-->Greater than 40000 shares traded every 5 minutes 
	42 	 *-->Less than 2% from its moving average 
	43 	 *-->Volatility has to be solid enough to hit profit target 1.62% 
	44 	 *-->Cannot have a number of bars that are 2% in range (high to low) 
	45 	 *-->Must open the trade between 9:50 and 10:10 am 
	46 	 *-->Need to exit the trade no later than 12 noon 
	47 	 *-->Close the trade out if the stock closes above or below its 10-period MA after 11 am 
	48 	 *  
	49 	 * @param smaSize			--number of units to calculate MA upon, ex 21 
	50 	 * @param timeframeSize		--number of timeframes in a unit, ex 5 (5-min timeframe) 
	51 	 * @param timeframeUnit		--time unit used in timeframe, ex MIN (5-min timeframe) 
	52 	 * @param queue				--list of real time Bars to calculate from 
	53 	 *  
	54 	 * @return double 
	55 	 *  
	56 	 * @throws NotEnoughDataException 
	57 	 * @throws Exception 
	58 	 */ 
	public double SMA(
			LinkedList<Bar> queue) throws NotEnoughDataException, Exception { 
		 		double sma = 0; 
		 		double total = 0; 
		 		double value; 
	
		 		long numOfBars = period*tfFactor; 
		 		lg.debug("calcSMA: numOfBars: " + numOfBars + ", queue size: " + queue.size()); 
		 		 
		 		if(queue.size()<numOfBars) 
		 			throw new NotEnoughDataException(); 
		 		 
		 		for(int i=0;i<numOfBars;i++){ 
		 			if(i%tfFactor==0){ 
		 				value = queue.get(i).close(); 
						total+=value; 
		 			} 
				} 
				 
		 		sma = total/period; 
		 		return sma; 
	} 
	
	/** 
95 	 * Calculates Exponential Moving Average of close price for specified number of timeframe units. 
96 	 * EMA is a weighted moving average (WMA) that gives more weighting to recent price data than  
97 	 * SMA does. The EMA responds more quickly to recent price changes than the SMA.  
98 	 * The formula calculating EMA involves using a multiplier and stating with SMA. 
99 	 *  
100 	 * @param smaSize			--number of units to calculate MA upon, ex 21 
101 	 * @param timeframeSize		--number of timeframes in a unit, ex 5 (5-min timeframe) 
102 	 * @param timeframeUnit		--time unit used in timeframe, ex MIN (5-min timeframe) 
103 	 * @param queue				--list of real time Bars to calculate from 
104 	 *  
105 	 * @return double 
106 	 *  
107 	 * @throws NotEnoughDataException 
108 	 * @throws Exception 
109 	 */ 
	public double EMA(LinkedList<Bar> queue) throws NotEnoughDataException, Exception { 
		 		String fp = "EMA: "; 
		 		double ema = 0; 
		 		 
		 		//Step 1. Calculate SMA 
		 		double sma = SMA(queue); 
		 		if(lg.isDebugEnabled()) 
		 			lg.debug(fp + "sma: " + sma); 
		 		 
		 		//Step 2. Calculating the weighting multiplier 
		 		double multiplier = 2/period + 1; 
		 		if(lg.isDebugEnabled()) 
		 			lg.debug(fp + "multiplier: " + multiplier); 
		 		 
		 		//Step 3: Calculate EMA 
		 		//get the closing price of the last bar 
		 		double close = queue.get(0).close(); 
		 		//get the ema for previous timeframe unit 
		 		//double ema0 = queue.get(1).ema(); 
		 		 
		 		//ema = (close - ema0)*multiplier+ema0; 
		 		 
		 		return ema; 
	} 

	/** 
136 	 * between 0 and 20  - overbought 
137 	 * between -80 and -100  - oversold 
138 	 *  
139 	 * @param latestClose 
140 	 * @param highestHigh 
141 	 * @param lowestLow 
142 	 *  
143 	 * @return		between 0 and -100 
144 	 */ 
 	public double WilliamsR(double latestClose, double highestHigh, double lowestLow){ 
 		double wr = 0; 
 

 		wr = (highestHigh - latestClose)/(highestHigh - lowestLow)*(-100); 
 		return wr; 
 	} 
 	
 	public double maxHigh(ArrayDeque<IBar> q){
 		double max = 0;
 		return max;
 	}
 	
 	public double minLow(ArrayDeque<IBar> q){
 		double min = 0;
 		return min;
 	}
 	
 	public long sumVolume(ArrayDeque<IBar> q){
 		long sum = 0;
 		return sum;
 	}


	/** This exception indicates that there is not enough Bars in the queue,  
154 	 * application must wait until enough data has been obtained,  
155 	 * or go to database to get historical data 
156 	 * @author Txs285 
157 	 * 
158 	 */ 
 	public static class NotEnoughDataException extends Exception { 
 		private static final long serialVersionUID = -1908829006025105879L; 
 		public NotEnoughDataException(){} 
 	} 


/*	public static void main(String[] args){
			 		Calculator t = new Calculator(); 
			 		lg.debug("test created"); 
			 		 
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
			 		lg.debug("variables assigned"); 

			Random rValue = new Random(System.currentTimeMillis()); 
			 		Random rSign = new Random(System.currentTimeMillis()); 
			 		lg.debug("seeded randomizers"); 
			 		 
			 		for(int i=0;i<queueSize; i++) { //smaSize*tfFactor;i++){ 
			 			dev = rValue.nextDouble(); 
			 			//p("dev: " + dev); 
			 			positive = rSign.nextBoolean(); 
			 			//p("positive: " + positive); 
			 			 
			 			if(!positive) 
			 				dev = Math.abs(dev) * (-1); 
			 			 
			 			closeP = closeP+dev; 
			 			lg.debug("closeP: " + closeP); 
			 			b= new Bar(
			 					0, //time
			 					basePrice, //open
			 					closeP,	//close! 
			 					basePrice, //high
			 					basePrice, //low
			 					basePrice, //wap
			 					volume, 
			 					cnt 
			 					); 
			 			queue.addFirst(b);		 
			 			 
			try { 
				 				sma = t.SMA(smaSize, timeframeSize, timeframeUnit, queue); 
				 				lg.debug("sma["+i+"]: " + sma); 
				 			} 
				 			catch(NotEnoughDataException e){ 
				 				try { 
				 					Thread.sleep(400); 
				 				}catch(InterruptedException ie){} 
				 				continue; 
				 			} 
				 			catch(Exception e){ 
				 				lg.error("Error: " + e.getMessage()); 
				 			} 
				 		} 
			 		lg.debug("Queue is full: " + queue.size()); 
				 		//t.setQueue(q); 

	}*/
}
