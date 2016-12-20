package com.pancorp.tbroker.strategy.stock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pancorp.tbroker.strategy.StrategyAbstract;

public class StrategyIntradayShort extends StrategyAbstract {
	private static Logger log = LogManager.getLogger(StrategyIntradayShort.class);
	
	
	public StrategyIntradayShort(){
		this.setName("George");
	}

	@Override
	public void run(){
	
	}
	
	//@Override
	public void query() throws Exception{
		
	}
	
	@Override
	public void enter()  throws Exception{
		
	}

	@Override
	public void exit(){
		
	}
}
