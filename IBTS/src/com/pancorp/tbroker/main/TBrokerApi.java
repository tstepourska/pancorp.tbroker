package com.pancorp.tbroker.main;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ibts.controller.ApiConnection.ILogger;
import com.ibts.controller.AccountSummaryTag;
import com.ibts.controller.ApiController;
import com.ibts.controller.ApiController.IAccountHandler;
import com.ibts.controller.ApiController.IAccountSummaryHandler;
import com.ibts.controller.ApiController.IBulletinHandler;
import com.ibts.controller.ApiController.IConnectionHandler;
import com.ibts.controller.ApiController.ITimeHandler;
import com.ibts.controller.Formats;
import com.ibts.controller.Types.NewsType;
import com.pancorp.tbroker.util.TLogger;

public class TBrokerApi implements IConnectionHandler {
	private static Logger lg = LogManager.getLogger(TBrokerApi.class);

	static TBrokerApi INSTANCE = new TBrokerApi();

	private final ILogger m_inLogger = new TLogger(LogManager.getLogger("InLog"));
	private final ILogger m_outLogger = new TLogger(LogManager.getLogger("InLog"));
	private final ApiController m_controller = new ApiController( this, m_inLogger, m_outLogger);
	private final ArrayList<String> m_acctList = new ArrayList<String>();

	// getter methods
	public ArrayList<String> accountList() 	{ return m_acctList; }
	public ApiController controller() 		{ return m_controller; }
	
	public static void main(String[] args) {
		INSTANCE.run();
	}
	
	private void run() {
        String m_port = "7497"; //default 7496
        String m_clientId = "0";  //default 0
        String m_host = "127.0.0.1";
        
        // make initial connection to local host, port 7496, client id 0
		//m_controller.connect( "127.0.0.1", 7496, 0);
		
		int port = Integer.parseInt( m_port );
		int clientId = Integer.parseInt( m_clientId);
		m_controller.connect( m_host, port, clientId);
		
		//////////////////////////////////////////
		connected();
		
		//
		//Request account summary
		String group = "All"; //pass All to get all accounts summary
		AccountSummaryTag[] tags = null; //??
		IAccountSummaryHandler accSummHandler = null; //??
		m_controller.reqAccountSummary(group, tags, accSummHandler);
		//TODO assign total account amount to application global variable
		
		//OR 
		/*
		boolean subscribe = false;//??
		String acctCode = null; //??
		//TODO  implement IAccountHandler without Swing
		IAccountHandler handler = null; //new AccountInfoPanel();
		m_controller.reqAccountUpdates(subscribe, acctCode, handler);
		*/
    }
	
	@Override public void connected() {
		show( "connected");
		//m_connectionPanel.m_status.setText( "connected");
		
		m_controller.reqCurrentTime( new ITimeHandler() {
			@Override public void currentTime(long time) {
				show( "Server date/time is " + Formats.fmtDate(time * 1000) );
			}
		});
		
		m_controller.reqBulletins( true, new IBulletinHandler() {
			@Override public void bulletin(int msgId, NewsType newsType, String message, String exchange) {
				String str = String.format( "Received bulletin:  type=%s  exchange=%s", newsType, exchange);
				show( str);
				show( message);
			}
		});
	}
	
	@Override public void disconnected() {
		show( "disconnected");
		//m_connectionPanel.m_status.setText( "disconnected");
	}

	@Override public void accountList(ArrayList<String> list) {
		show( "Received account list");
		m_acctList.clear();
		m_acctList.addAll( list);
	}

	@Override public void show( final String str) {
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

	@Override public void error(Exception e) {
		show( e.toString() );
	}
	
	@Override public void message(int id, int errorCode, String errorMsg) {
		show( id + " " + errorCode + " " + errorMsg);
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
