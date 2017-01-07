package com.pancorp.tbroker.handler;

import com.ib.controller.ApiController.IAccountHandler;
import com.ib.controller.Position;

public class AccountHandler implements IAccountHandler {

	public AccountHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Sends the actual account values 
	 * 
	 * @param account String 	--String that indicates the account.
	 * 
	 * @param key String		--A string that indicates one type of account value. There is a long list of possible keys 
	 * 							that can be sent, here are just a few examples:
	 * 								• CashBalance - account cash balance
	 * 								• DayTradesRemaining - number of day trades left
	 * 								• EquityWithLoanValue - equity with Loan Value
	 * 								• InitMarginReq - current initial margin requirement
	 * 								• MaintMarginReq - current maintenance margin
	 * 								• NetLiquidation - net liquidation value
	 * 
	 * @param value String 		--The value associated with the key.
	 * 
	 * @param currency String  --String that defines the currency type, in case the value is a currency type.
	 */
	@Override
	public void accountValue(String account, String key, String value, String currency) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accountTime(String timeStamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accountDownloadEnd(String account) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * is responsible for sending your current portfolio information from TWS
	 * 
	 * @param Position
	 */
	@Override
	public void updatePortfolio(Position position) {
		
	}

}
