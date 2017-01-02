package com.pancorp.tbroker.main;

import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ib.client.Contract;
import com.ib.client.ScannerSubscription;
import com.ib.contracts.StkContract;
import com.ib.controller.AccountSummaryTag;
import com.ib.controller.ApiController;
import com.ib.controller.Formats;
import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;
import com.ib.controller.NewTickType;
import com.ib.controller.OrderType;
import com.ib.controller.Types;
import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.ApiController.IConnectionHandler;
import com.ib.controller.ApiController.IContractDetailsHandler;
import com.ib.controller.ApiController.IDeepMktDataHandler;
import com.ib.controller.ApiController.IHistoricalDataHandler;
import com.ib.controller.ApiController.IOrderHandler;
import com.ib.controller.ApiController.IRealTimeBarHandler;
import com.ib.controller.ApiController.IScannerHandler;
import com.ib.controller.ApiController.ITimeHandler;
import com.ib.controller.Types.Action;
import com.ib.controller.Types.BarSize;
import com.ib.controller.Types.DurationUnit;
import com.ib.controller.Types.MktDataType;
import com.ib.controller.Types.NewsType;
import com.ib.controller.Types.SecType;
import com.ib.controller.Types.WhatToShow;
import com.pancorp.tbroker.market.ITopMktDataHandler;
import com.pancorp.tbroker.market.MarketDataObject;
import com.pancorp.tbroker.market.MarketDataObject.BarResultsObject;
import com.pancorp.tbroker.market.MarketDataObject.ScannerResultsObject;
import com.pancorp.tbroker.market.ContractInfoObject.DetailsResultsObject;
import com.pancorp.tbroker.market.TopMktDataAdapter;
import com.pancorp.tbroker.util.Globals;

public class TBrokerApi implements IConnectionHandler 
{
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(TBrokerApi.class);

	private final ILogger inLogger = new Logger(null); //LogManager.getLogger("InLog"));
	private final ILogger outLogger = new Logger(null);//LogManager.getLogger("OutLog"));
	//private final TWrapper m_controller = new TWrapper();// this, m_inLogger, m_outLogger);
	private final ApiController wrapper = new ApiController(this, inLogger, outLogger);
	private final ArrayList<String> m_acctList = new ArrayList<String>();

	// getter methods
	public ArrayList<String> accountList() 	{ return m_acctList; }
	public ApiController controller() 		{ return wrapper; }

	public void invoke() {
		if(lg.isTraceEnabled())
			lg.trace("Client #" + Globals.clientId + " connecting to " + Globals.host + " on port " + Globals.port);
		wrapper.connect( Globals.host, Globals.port, Globals.clientId);
		
		//////////////////////////////////////////
		if(!wrapper.getM_client().isConnected()){
			lg.error("Could not connect");
			return;
		}
		connected();
		
		NewContract contract = new NewContract();
		contract.secType(Types.SecType.STK);
		contract.symbol("IBM");
		contract.currency("USD");
		contract.exchange("ISLAND"); //ISLAND		
		//contract.primaryExch("");
		
		//String genericTickList = null;
		//boolean snapshot = true;
		//ITopMktDataHandler handler = null;
		//TopMktDataAdapter h1 = new TopMktDataAdapter();
		
        //if(lg.isTraceEnabled())
        //lg.trace("requesting Top Market Data");
		//wrapper.reqTopMktData(contract, genericTickList, snapshot, h1);
		
		//m_controller.tickPrice(reqId, tickType, price, canAutoExecute);
		
		//if(lg.isTraceEnabled())
		 //       lg.trace("requesting Frozen Market Data");
		//int reqId=10;
		//int marketDataType = 2; //2-frozen;  1- realtime
		//wrapper.marketDataType(reqId, marketDataType) ;
		//cancel
		//wrapper.cancelTopMktData(h1);
		
		//cancel market data
		//wrapper.cancelTopMktData(stockListener);//cancelMktData(tickerId);
		
		//request market depth
		//int numRows = 7;
		//IDeepMktDataHandler h2 = new MarketDataObject.DeepResultsObject();
		//wrapper.reqDeepMktData(contract, numRows, h2);
		//cancel
		//wrapper.cancelDeepMktData(h2);
		
		//request historical data
		int tickerId = 1;
		Contract ctrct = new Contract();
		ctrct.m_exchange = "ISLAND";
		//ctrct.m_symbol("IBM");
		String endDateTime = "20161224 11:30:25 EST"; //yyyymmdd hh:mm:ss tmz
		//int formatDate - client: 1 -- dates applying to bars returned in the format: yyyymmdd{space}{space}hh:mm:dd
		//						   2 - dates are returned as a long integer specifying the number of seconds since 1/1/1970 GMT.
		/*int duration = 5;
		DurationUnit durationUnit = Types.DurationUnit.DAY; 
		BarSize barSize = Types.BarSize._10_mins;
		WhatToShow whatToShow = Types.WhatToShow.TRADES;*/
		boolean rthOnly = true; //get data for regular trading hours only (no outside trading hrs)
		/*IHistoricalDataHandler h3 = new MarketDataObject.BarResultsPanel(true);
		wrapper.reqHistoricalData(contract, endDateTime, duration, durationUnit, barSize, whatToShow, rthOnly, h3);
		//cancel
		wrapper.cancelHistoricalData(h3);
		*/
		/*
		//request real time bars
		WhatToShow whatToShow = Types.WhatToShow.TRADES;
		IRealTimeBarHandler h4 = new BarResultsObject(false); // boolean historical
		wrapper.reqRealTimeBars(contract, whatToShow, rthOnly, h4);
		//cancel
		wrapper.cancelRealtimeBars(h4);
		*/
		
		/*
		//request market scanner subscription
		ScannerSubscription sub = new ScannerSubscription();
		//sub. what to set?\ for subscription?	 
		IScannerHandler h5 = new ScannerResultsObject();
		wrapper.reqScannerParameters(h5);  //returns XML file with all valid scan codes
		wrapper.reqScannerSubscription(sub, h5);
		//end of a snapshot:
		int reqId = 3; //remember your started reqId!
		wrapper.scannerDataEnd(reqId);
		//cancel
		wrapper.cancelScannerSubscription(h5);
		*/
		
		// request contract details
		/*IContractDetailsHandler h6 = new DetailsResultsObject();
		wrapper.reqContractDetails(contract, h6);
		*/
		
		//placing an order
		NewOrder order = new NewOrder();
		String v = "AccountNumber";
		order.account(v);
		order.action(Action.BUY);
		int clientId = 1;
		order.totalQuantity(100);
		order.clientId(clientId);
		order.orderId(1);
		order.orderType(OrderType.LMT);
		order.lmtPrice(35.37D);
		//IOrderHandler h7 = new IOrderHandler();
		//wrapper.placeOrModifyOrder(contract, order, h7);
    }
	
	public void connected() {
		//if(lg.isTraceEnabled())
		//	lg.trace("connected");
		show( "connected");
		//m_connectionPanel.m_status.setText( "connected");
		
		wrapper.reqCurrentTime( new ITimeHandler() {
			@Override public void currentTime(long time) {
				show( "Server date/time is " + Formats.fmtDate(time * 1000) );
			}
		});
		
		/*m_controller.reqBulletins( true, new IBulletinHandler() {
			@Override public void bulletin(int msgId, NewsType newsType, String message, String exchange) {
				String str = String.format( "Received bulletin:  type=%s  exchange=%s", newsType, exchange);
				show( str);
				show( message);
			}
		});*/
	}
	
	public boolean isConnected(){
		return wrapper.getM_client().isConnected();
	}
	
	public void disconnect() {
		wrapper.disconnect();
	}
	
	public void disconnected() {
		show( "disconnected");
		//m_connectionPanel.m_status.setText( "disconnected");
	}

	public void accountList(ArrayList<String> list) {
		show( "Received account list");
		m_acctList.clear();
		m_acctList.addAll( list);
		
		if(lg.isTraceEnabled())
		for(String s: m_acctList){
			lg.trace("accountList: " + s);
		}
	}

	public void show( final String str) {
		/*SwingUtilities.invokeLater( new Runnable() {
			@Override public void run() {
				m_msg.append(str);
				m_msg.append( "\n\n");
				
				Dimension d = m_msg.getSize();
				m_msg.scrollRectToVisible( new Rectangle( 0, d.height, 1, 1) );
			}
		});*/
		
		lg.info(str);
	}

	public void error(Exception e) {
		show("Error: " + e.toString() );
	}
	
	public void message(int id, int errorCode, String errorMsg) {
		show("Message: " + id + " " + errorCode + " " + errorMsg);
	}
	
	private static class Logger implements ILogger {
		final private JTextArea m_area;

		Logger( JTextArea area) {
			m_area = area;
		}

		@Override public void log(final String str) {
			SwingUtilities.invokeLater( new Runnable() {
				@Override public void run() {
//					m_area.append(str);
//					
//					Dimension d = m_area.getSize();
//					m_area.scrollRectToVisible( new Rectangle( 0, d.height, 1, 1) );
				}
			});
		}
	}
	
}

// do clearing support
// change from "New" to something else
// more dn work, e.g. deltaNeutralValidation
// add a "newAPI" signature
// probably should not send F..A position updates to listeners, at least not to API; also probably not send FX positions; or maybe we should support that?; filter out models or include it 
// finish or remove strat panel
// check all ps
// must allow normal summary and ledger at the same time
// you could create an enum for normal account events and pass segment as a separate field
// check pacing violation
// newticktype should break into price, size, and string?
// give "already subscribed" message if appropriate

// BUGS
// When API sends multiple snapshot requests, TWS sends error "Snapshot exceeds 100/sec" even when it doesn't
// When API requests SSF contracts, TWS sends both dividend protected and non-dividend protected contracts. They are indistinguishable except for having different conids.
// Fundamentals financial summary works from TWS but not from API 
// When requesting fundamental data for IBM, the data is returned but also an error
// The "Request option computation" method seems to have no effect; no data is ever returned
// When an order is submitted with the "End time" field, it seems to be ignored; it is not submitted but also no error is returned to API.
// Most error messages from TWS contain the class name where the error occurred which gets garbled to gibberish during obfuscation; the class names should be removed from the error message 
// If you exercise option from API after 4:30, TWS pops up a message; TWS should never popup a message due to an API request
// TWS did not desubscribe option vol computation after API disconnect
// Several error message are misleading or completely wrong, such as when upperRange field is < lowerRange
// Submit a child stop with no stop price; you get no error, no rejection
// When a child order is transmitted with a different contract than the parent but no hedge params it sort of works but not really, e.g. contract does not display at TWS, but order is working
// Switch between frozen and real-time quotes is broken; e.g.: request frozen quotes, then realtime, then request option market data; you don't get bid/ask; request frozen, then an option; you don't get anything
// TWS pops up mkt data warning message in response to api order

// API/TWS Changes
// we should add underConid for sec def request sent API to TWS so option chains can be requested properly
// reqContractDetails needs primary exchange, currently only takes currency which is wrong; all requests taking Contract should be updated
// reqMktDepth and reqContractDetails does not take primary exchange but it needs to; ideally we could also pass underConid in request
// scanner results should return primary exchange
// the check margin does not give nearly as much info as in TWS
// need clear way to distinguish between order reject and warning

// API Improvements
// add logging support
// we need to add dividendProt field to contract description
// smart live orders should be getting primary exchange sent down

// TWS changes
// TWS sends acct update time after every value; not necessary
// support stop price for trailing stop order (currently only for trailing stop-limit)
// let TWS come with 127.0.0.1 enabled, same is IBG
// we should default to auto-updating client zero with new trades and open orders

// NOTES TO USERS
// you can get all orders and trades by setting "master id" in the TWS API config
// reqManagedAccts() is not needed because managed accounts are sent down on login
// TickType.LAST_TIME comes for all top mkt data requests
// all option ticker requests trigger option model calc and response
// DEV: All Box layouts have max size same as pref size; but center border layout ignores this
// DEV: Box layout grows items proportionally to the difference between max and pref sizes, and never more than max size

//TWS sends error "Snapshot exceeds 100/sec" even when it doesn't; maybe try flush? or maybe send 100 then pause 1 second? this will take forever; i think the limit needs to be increased

//req open orders can only be done by client 0 it seems; give a message
//somehow group is defaulting to EqualQuantity if not set; seems wrong
//i frequently see order canceled - reason: with no text
//Missing or invalid NonGuaranteed value. error should be split into two messages
//Rejected API order is downloaded as Inactive open order; rejected orders should never be sen
//Submitting an initial stop price for trailing stop orders is supported only for trailing stop-limit orders; should be supported for plain trailing stop orders as well 
//EMsgReqOptCalcPrice probably doesn't work since mkt data code was re-enabled
//barrier price for trail stop lmt orders why not for trail stop too?
//All API orders default to "All" for F; that's not good
