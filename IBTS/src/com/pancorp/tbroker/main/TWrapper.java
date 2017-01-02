package com.pancorp.tbroker.main;

import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ib.client.EClientSocket;
import com.ib.client.TagValue;
import com.ib.controller.NewContract;
import com.ib.controller.ApiController.IBulletinHandler;
import com.ib.controller.ApiController.ITopMktDataHandler;
import com.ib.controller.Types.NewsType;

//import com.ib.controller.ApiConnection.ILogger;
//import com.ib.controller.ApiController;

public class TWrapper extends SimpleWrapper
//ApiController 
{
	private static Logger lg = LogManager.getLogger(TWrapper.class);
	
	private int m_reqId;	// used for all requests except orders; designed not to conflict with m_orderId

	public TWrapper() { //IConnectionHandler handler, ILogger inLogger, ILogger outLogger) {
		super();
		EClientSocket c = new EClientSocket(this);
		lg.debug("Constructor: client created");
		this.setM_client(c);
		lg.debug("Constructor: client set");
	//handler, inLogger, outLogger);
	}
	
	public void connect( String host, int port, int clientId) {
		host = host != null ? host : "127.0.0.1";
		
		lg.debug("connect: host:"+host+", port: " + port+", clientId: "+clientId);
		this.getM_client().eConnect(host, port, clientId);
	}

	public boolean isConnected(){
		return this.getM_client().isConnected();
	}
	
	public void reqTopMktData(NewContract contract, String genericTickList, boolean snapshot, ITopMktDataHandler handler) {
	    	int reqId = m_reqId++;
	    	if(lg.isTraceEnabled())
	    		lg.trace("reqTopMktData: reqId: " + reqId);
	    	//m_topMktDataMap.put( reqId, handler);
	    this.getM_client().reqMktData( reqId, contract.getContract(), genericTickList, snapshot, Collections.<TagValue>emptyList() );
			//sendEOM();
	 }
	
	// ---------------------------------------- Time handling ----------------------------------------
	public interface ITimeHandler {
		void currentTime(long time);
	}

	public void reqCurrentTime( ITimeHandler handler) {
		//m_timeHandler = handler;
		this.getM_client().reqCurrentTime();
		//sendEOM();
	}
/*
	@Override public void currentTime(long time) {
		m_timeHandler.currentTime(time);
		//recEOM();
	}*/
	
	// ---------------------------------------- Bulletins handling ----------------------------------------
		public interface IBulletinHandler {
			void bulletin(int msgId, NewsType newsType, String message, String exchange);
		}

		public void reqBulletins( boolean allMessages, IBulletinHandler handler) {
			//m_bulletinHandler = handler;
			this.getM_client().reqNewsBulletins( allMessages);
			//sendEOM();
		}

		public void cancelBulletins() {
			this.getM_client().cancelNewsBulletins();
		}

		/*@Override public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
			m_bulletinHandler.bulletin( msgId, NewsType.get( msgType), message, origExchange);
			//recEOM();
		}*/
}
