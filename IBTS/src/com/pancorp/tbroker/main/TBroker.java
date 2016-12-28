package com.pancorp.tbroker.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.ibts.client.Contract;
import com.ibts.controller.AccountSummaryTag;
import com.ibts.controller.ApiController;
import com.ibts.controller.ApiConnection.ILogger;
import com.ibts.controller.ApiController.IAccountHandler;
import com.ibts.controller.ApiController.IAccountSummaryHandler;
import com.ibts.controller.ApiController.IConnectionHandler;

import com.pancorp.tbroker.data.DataAnalyzer;
import com.pancorp.tbroker.strategy.IStrategy;
import com.pancorp.tbroker.strategy.StrategyAbstract;
import com.pancorp.tbroker.strategy.StrategySelector;

//import com.pancorp.tbroker.util.TLogger;
import com.pancorp.tbroker.util.Utils;

/**
 * @author 
 *
 */
public class TBroker{
	private static Logger lg = LogManager.getLogger(TBroker.class);
	
	private static void init(String[] args) throws Exception {
		if(lg.isTraceEnabled())
		lg.trace("init: ");
	}

	/**
	 * @param args
	 * 	properties file name
	 *  
	 */
	public static void main(String[] args) {
		
		//1. Initialize global properties and variables
		try {
			init(args);
		}
		catch(Exception e){
			Utils.logError(lg, e);
		}
		
		//2. Check the account amount and margin
		//2.a. Initialize TBrokerApi
		TBrokerApi api = new TBrokerApi();
		api.invoke();
		
		//TWrapper w = new TWrapper();
		//w.connect();
		
		if(api.isConnected()){
			lg.trace("connected");
/*
		
		try {
			
			for(int i=5;i>0;i--){
			lg.trace("disconnecting in "+i+" sec");
			Thread.sleep(1000);
			
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			api.disconnect();
			*/
		}


		
		//////////////////////////////////////////////////////
		//3. Start DataAnalizer
		//////////////////////////////////////////////////////
		// TODO figure out relations between DataAnalyzer, Strategy, StrategySelector and TBrokerApi
	/*	DataAnalyzer da = new DataAnalyzer();
		Contract c = da.invoke();
		/////////////////////////////////////////////////////
		
		//when found, select and start strategy
		//do not receive data bulk until started strategy expires
		StrategySelector ss = new StrategySelector();
		StrategyAbstract strategy = ss.resolveStrategy(c);
		strategy.setContract(c);
		
		//try {
			//strategy.join();
		//strategy.start();
		//} catch (InterruptedException e) {
			
		//	e.printStackTrace();
		//}
		
		//as of now, only one strategy is allowed to run at a time
		boolean working = true;
		while(working){
			try {
				Thread.sleep(30000);
				
				//TODO check on strategy or implement callback
				//if still running and it is the end of the day
				// handle!!  - close position ?
			}
			catch(Exception e){
				Utils.logError(lg, e);
				//TODO to handle this exception!!!
			}
		}
		//strategy exits, or force closing, if end of day
		*/
		
		//record trade stats

	}
}