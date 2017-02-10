package com.ts.test.daily;

import java.util.ArrayDeque;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.ts.test.data.DataFactory;
import com.ts.test.main.Calculator;
import com.ts.test.main.Test;
import com.ts.test.model.Candle;
import com.ts.test.model.Contract;
import com.ts.test.model.IBar;
import com.ts.test.util.Constants;

public class StrategyThread extends Thread {
	private static Logger lg = LogManager.getLogger(StrategyThread.class); 
	private Contract contract;
	private DataFactory datafactory;
	private boolean working = true;
	private volatile ArrayDeque<IBar> ticks;
	ArrayDeque<IBar> candles;
	ArrayDeque<IBar> currentCandle = null;
	String tfUnit;
	long tfFactor = 1; //5 sec
	Calculator calc;
	int period = 21;
	int timeframeSize = 15;
	String timeframeUnit = Constants.TFU_MIN;
	
	public StrategyThread(Contract c, DataFactory df, String tfu){
		this.setName(c.symbol()+"_vlad");
		contract = c;
		datafactory = df;
		ticks = new ArrayDeque<>();
		candles = new ArrayDeque<>();
		this.tfUnit = tfu;
		
		switch(tfUnit){
		case Constants.TFU_MIN:
			tfFactor = Constants.MIN;
			break;
		case Constants.TFU_HOUR:
			tfFactor = Constants.HOUR;
			break;
		case Constants.TFU_DAY:
			tfFactor = Constants.DAY;
			break;
			default:
		}
		
		calc = new Calculator(period, timeframeSize, timeframeUnit, tfFactor);
	}
	
	/**
	 * Adds tick to a queue. Called by DataFactory
	 * @param t
	 */
	public void addTick(IBar t) throws InterruptedException {
		synchronized(this){
			ticks.addFirst(t);
		}
		recalculate(t);
	}
	
	/**
	 * 
	 */
	private void recalculate(IBar t){
		//translate latest tick into a candle
		
		//add to current candle tick queue
		currentCandle.addFirst(t);
		
		//check  the current candle queue size
		if(currentCandle.size()>=tfFactor){	//current candle queue is full			
			//creating new Candle from the  current candle queue
			double open = currentCandle.peekLast().open();
			double high = calc.maxHigh(currentCandle);
			double low = calc.minLow(currentCandle);
			long volume = calc.sumVolume(currentCandle);
			long time = currentCandle.peekLast().time();

			
			Candle c = new Candle(time,open,t.close(),high,low,t.wap(),volume,t.count());
			//adding it to a day candle queue 
			candles.addFirst(c);
			
			//recalculate all indicators and patterns
			
			//make trading decisions
			
		}
		else {

			candles.addFirst(t);
		}
	}
	
	@Override
	public void run(){
		lg.info("Started thread for " + this.contract.symbol());
		String symbol = this.contract.symbol();
		
		//subscribe to data
		datafactory.subscribe(symbol, this);
		try {Thread.sleep(5005);}catch(InterruptedException e){}
		
		while(working){
		}
	}
	
	public static void main(String[] args){
		
	}
}
