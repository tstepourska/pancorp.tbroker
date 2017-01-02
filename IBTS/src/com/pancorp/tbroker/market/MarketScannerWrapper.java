package com.pancorp.tbroker.market;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import com.ib.client.AnyWrapper;
import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.ScannerSubscription;
import com.ib.client.TagValue;
import com.ib.client.UnderComp;
import com.pancorp.tbroker.util.Globals;

public class MarketScannerWrapper implements com.ib.client.EWrapper{// implements AnyWrapper{
	
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(MarketScannerWrapper.class);
			
	// main client
	private EClientSocket m_client;
	private MarketScannerHandler m_handler;
	private boolean isConnected = false;
	
	public MarketScannerWrapper() {
		m_client = new EClientSocket(this);
		m_handler = new MarketScannerHandler(this);
	}

	public EClientSocket getM_client() {
		return m_client;
	}

	public void setM_client(EClientSocket m_client) {
		this.m_client = m_client;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}


	@Override
	public void error(Exception e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(int id, int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
	
	public void connect( String host, int port, int clientId) {
		m_client.eConnect(host, port, clientId);
		this.setConnected(m_client.isConnected());
	}

	public void disconnect() {
		m_client.eDisconnect();
	}


	@Override
	public void connectionClosed() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Requests scanner parameters
	 */
	public void reqScannerParameters(){
		lg.trace("Called reqScannerParameters");
		m_client.reqScannerParameters();
	}
	
	/**
	 * Requests scanner subscription
	 */
	public void reqScannerSubscription(int tickerId, ScannerSubscription subscription, Vector<TagValue> scannerSubscriptionOptions){
		if(lg.isTraceEnabled())
		lg.trace("Called reqScannerSubscription: tickerId="+tickerId+",subscription: " + subscription);
		m_client.reqScannerSubscription(tickerId, subscription,scannerSubscriptionOptions);
	}
	
	public void cancelScannerSubscription( int tickerId){
		m_client.cancelScannerSubscription(tickerId);
	}

	/**
	 * Returns the XML document, to display the XML file
	 *  that lists all valid scan codes.
	 *  Callback: its only parameter, xml, which is a String
	 */
	@Override
	public void scannerParameters(String xml) {
		lg.trace("Callback: reqScannerParameters");
		//FileOutputStream fos = null;
		FileWriter w = null;
		try{
			//fos = new FileOutputStream(Globals.BASEDIR + Globals.DATADIR + Globals.SCANNER_INPUT_FILE);
			w = new FileWriter(Globals.BASEDIR + Globals.DATADIR + Globals.SCANNER_INPUT_FILE);
			
			w.write(xml);
		}
		catch(Exception e){
			lg.error("Error: " + e.getMessage());
		}
		finally{
			try {
				//fos.flush();
				//fos.close();
				
				w.flush();
				w.close();
			}
			catch(Exception e2){
				
			}
		}
		logIn("scannerParameters");

		//To parse XML string and choose or save scanner params
		//signal that message is received
		
		//after callback completed, trigger next event
		this.m_handler.onScannerParamsUpdate();
	}

	//@Override
	public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr) {
		if(lg.isTraceEnabled())
			lg.trace("Callback: scannerData: reqId="+reqId+",rank="+rank);
		logIn("scannerData");		
		
		//after callback completed, trigger next event
		this.m_handler.onScannerDataReceived();
	}

	//@Override
	public void scannerDataEnd(int reqId) {
		if(lg.isTraceEnabled())
			lg.trace("Callback: scannerDataEnd");
		logIn("scannerDataEnd");	
	}

	@Override
	public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
		if(lg.isTraceEnabled())
			lg.trace("Callback: tickPrice: tickerId="+tickerId+",field="+field+",price="+price);
		
	}

	@Override
	public void tickSize(int tickerId, int field, int size) {
		if(lg.isTraceEnabled())
			lg.trace("Callback: tickSize: tickerId="+tickerId+",field="+field+",size="+size);
	}

	@Override
	public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta, double undPrice) {
		if(lg.isTraceEnabled())
			lg.trace("Callback: ticOptionComputation: tickerId="+tickerId+",field="+field+",impliedVol="+impliedVol);
		
	}

	@Override
	public void tickGeneric(int tickerId, int tickType, double value) {
		if(lg.isTraceEnabled())
			lg.trace("Callback: tickGeneric: tickerId="+tickerId+",tickType="+tickType+",value="+value);
		
	}

	@Override
	public void tickString(int tickerId, int tickType, String value) {
		if(lg.isTraceEnabled())
			lg.trace("Callback: tickString: tickerId="+tickerId+",tickType="+tickType+",value="+value);
		
	}

	@Override
	public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints,
			double impliedFuture, int holdDays, String futureExpiry, double dividendImpact, double dividendsToExpiry) {
		if(lg.isTraceEnabled())
			lg.trace("Callback: tickEFP: tickerId="+tickerId+",tickType="+tickType+",basisPoints="+basisPoints);
		
	}

	@Override
	public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId,
			int parentId, double lastFillPrice, int clientId, String whyHeld) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openOrderEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAccountValue(String key, String value, String currency, String accountName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue,
			double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAccountTime(String timeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accountDownloadEnd(String accountName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextValidId(int orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contractDetails(int reqId, ContractDetails contractDetails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bondContractDetails(int reqId, ContractDetails contractDetails) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contractDetailsEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execDetails(int reqId, Contract contract, Execution execution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execDetailsEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price,
			int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void managedAccounts(String accountsList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveFA(int faDataType, String xml) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume,
			int count, double WAP, boolean hasGaps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume,
			double wap, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void currentTime(long time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fundamentalData(int reqId, String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deltaNeutralValidation(int reqId, UnderComp underComp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tickSnapshotEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void marketDataType(int reqId, int marketDataType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commissionReport(CommissionReport commissionReport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void position(String account, Contract contract, int pos, double avgCost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void positionEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accountSummary(int reqId, String account, String tag, String value, String currency) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accountSummaryEnd(int reqId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verifyMessageAPI(String apiData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verifyCompleted(boolean isSuccessful, String errorText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayGroupList(int reqId, String groups) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayGroupUpdated(int reqId, String contractInfo) {
		// TODO Auto-generated method stub
		
	}
/***********************************************************/
	
	//helpers
	protected void logIn(String method) {
		/*m_messageCounter++;
		if (m_messageCounter == MAX_MESSAGES) {
			m_output.close();
			initNextOutput();
			m_messageCounter = 0;
		}    	
		m_output.println("[W] > " + method);*/
		lg.info("logIn: "+method);
	}

}
