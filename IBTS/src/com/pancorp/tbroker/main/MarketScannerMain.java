package com.pancorp.tbroker.main;

import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import com.pancorp.tbroker.data.StockSelector;
import com.pancorp.tbroker.market.MarketScannerWrapper;
import com.pancorp.tbroker.market.NASDAQListingsLoader;
import com.pancorp.tbroker.market.YahooMarketDataLoader;
import com.pancorp.tbroker.util.Globals;
import com.pancorp.tbroker.util.Utils;
import com.ib.client.ContractDetails;
import com.ib.client.ScannerSubscription;
import com.ib.client.TagValue;
import com.ib.controller.ScanCode;

/**
 * Select all stocks from list of exchanges sorted by time zone offset
 * descending where: yesterday closing price is between 10 and 100 USD or CAD
 * 
 * 
 * @author pankstep
 *
 */

public class MarketScannerMain {

	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(MarketScannerMain.class);

	public MarketScannerMain() {
	}

	public static void main(String[] args) {

		//load latest list of available stocks
		try {
			String cfgFile = args[0];
			NASDAQListingsLoader nll = new NASDAQListingsLoader();
			nll.invoke(cfgFile);
		} catch (Exception e) {
			Utils.logError(lg, e);
			System.exit(1);
		}

		//load latest market data for the list above
		try {
			YahooMarketDataLoader ydl = new YahooMarketDataLoader();
			ydl.invoke();
		} catch (Exception e) {
			Utils.logError(lg, e);
			System.exit(1);
		}
		
		//1st level filter
		StockSelector ss = new StockSelector();
	}
	/*
	 * MarketScannerWrapper mWrapper = new MarketScannerWrapper();
	 * 
	 * if(!mWrapper.isConnected()) mWrapper.disconnect();
	 * 
	 * mWrapper.connect(Globals.host, Globals.port, Globals.clientId);
	 * 
	 * if(!mWrapper.isConnected()){ lg.error(
	 * "Connection attempt failed. Exiting.."); System.exit(1); }
	 * 
	 * mWrapper.reqScannerParameters(); lg.trace(
	 * "Called mWrapper.reqScannerParameters");
	 */
	// wrapper callback method 'reqScannerParameters(String xml)' will feed the
	// info into the pipe
	// then triggers event handling which calls reqDataSubscription based on
	// received scannerParameters

	// wrapper callback method 'scannerData(int reqId, int rank, ContractDetails
	// contractDetails, String distance, String benchmark,
	// String projection, String legsStr) ' will feed the info into the pipe

	// wrapper callback method 'scannerDataEnd(int reqId)' will signal the end
	// of the data

	/*
	 * IConnectionHandler handler, ILogger inLogger, ILogger outLogger
	 * ApiController c = new ApiController(handler, inLogger, outLogger);
	 */

	// }

}
