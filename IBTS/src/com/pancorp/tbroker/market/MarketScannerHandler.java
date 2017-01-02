package com.pancorp.tbroker.market;

import java.util.Vector;

import org.apache.logging.log4j.LogManager;

import com.ib.client.ScannerSubscription;
import com.ib.client.TagValue;
import com.ib.controller.ScanCode;

public class MarketScannerHandler {
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(MarketScannerHandler.class);
	
	private MarketScannerWrapper wrapper;
	
	/**
	 * After scanner parameters have been updated,
	 * we can request 
	 */
	public void onScannerParamsUpdate(){
		if(lg.isTraceEnabled())
			lg.trace("onScannerParameterUpdate: setting up subscription");		

		ScannerSubscription subscription = new ScannerSubscription();
		
		int m_numberOfRows = 100; 
		String m_instrument = null; //stocks, futures, forex
		String m_locationCode = "CA"; //? 
		String m_scanCode = ScanCode.ALL_SYMBOLS_ASC.toString(); //.TOP_OPEN_PERC_GAIN.toString(); //TODO how to enter many?
		ScanCode.LIMIT_UP_DOWN.toString();
		//ScanCode.L
		double m_abovePrice = 10.00; 
		double m_belowPrice = 100.00; 
		int m_aboveVolume = 2000000; 
		int m_averageOptionVolumeAbove = 2000000; 
		double m_marketCapAbove = 0; 
		double m_marketCapBelow = 0; 
		String m_moodyRatingAbove = null; 
		String m_moodyRatingBelow = null; 
		String m_spRatingAbove = null; 
		String m_spRatingBelow = null; 
		String m_maturityDateAbove = null; 
		String m_maturityDateBelow = null; 
		double m_couponRateAbove = 0.00; 
		double m_couponRateBelow = 0.00; 
		String m_excludeConvertible = null; 
		String m_scannerSettingPairs = null; 
		String  m_stockTypeFilter = null;
		
		subscription.numberOfRows(m_numberOfRows); 
		subscription.instrument(m_instrument);
		subscription.locationCode(m_locationCode);
		subscription.scanCode(m_scanCode);
		subscription.abovePrice(m_abovePrice);
		subscription.belowPrice(m_belowPrice);
		subscription.aboveVolume(m_aboveVolume);
		subscription.averageOptionVolumeAbove(m_averageOptionVolumeAbove);
		subscription.marketCapAbove(m_marketCapAbove);
		subscription.marketCapBelow(m_marketCapBelow);
		subscription.moodyRatingAbove(m_moodyRatingAbove);
		subscription.moodyRatingBelow(m_moodyRatingBelow);
		subscription.spRatingAbove(m_spRatingAbove);
		subscription.spRatingBelow(m_spRatingBelow);
		subscription.maturityDateAbove(m_maturityDateAbove);
		subscription.maturityDateBelow(m_maturityDateBelow);
		subscription.couponRateAbove(m_couponRateAbove);
		subscription.couponRateBelow(m_couponRateBelow);
		subscription.excludeConvertible(m_excludeConvertible);
		subscription.scannerSettingPairs(m_scannerSettingPairs);
		subscription.stockTypeFilter(m_stockTypeFilter);
		
		Vector<TagValue> scannerSubscriptionOptions = null;

		int tickerId = 1;
		lg.trace("Calling reqScannerSubscription");
		wrapper.reqScannerSubscription(tickerId, subscription, scannerSubscriptionOptions);
	}
	
	public void onScannerDataReceived(){
		if(lg.isTraceEnabled())
			lg.trace("onScannerDataReceived");
		//TODO what goes after that?
	}
	
	public MarketScannerWrapper getWrapper() {
		return wrapper;
	}

	public void setWrapper(MarketScannerWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public MarketScannerHandler(MarketScannerWrapper w) {
		this.wrapper = w;
	}

}
