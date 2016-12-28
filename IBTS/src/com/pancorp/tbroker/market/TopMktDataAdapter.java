package com.pancorp.tbroker.market;

import org.apache.logging.log4j.LogManager;

import com.ibts.controller.NewTickType;
import com.ibts.controller.ApiController.ITopMktDataHandler;
import com.ibts.controller.Types.MktDataType;

public class TopMktDataAdapter implements ITopMktDataHandler {
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(TopMktDataAdapter.class);
	
	@Override public void tickPrice(NewTickType tickType, double price, int canAutoExecute) {
		lg.info("Market Data: tickType: " + tickType + ", price: " + price + ", canAutoExecute: " + canAutoExecute);
	}
	@Override public void tickSize(NewTickType tickType, int size) {
		lg.info("Market Data: tickType: " + tickType + ", size: " + size);
	}
	@Override public void tickString(NewTickType tickType, String value) {
		lg.info("Market Data: tickType: " + tickType + ", value: " + value);
	}
	@Override public void tickSnapshotEnd() {
		lg.info("Market Data: tickSnapshotEnd");
	}
	@Override public void marketDataType(MktDataType marketDataType) {
		lg.info("Market Data: marketDataType: " + marketDataType);
	}
}
