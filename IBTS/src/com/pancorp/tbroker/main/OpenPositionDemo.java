package com.pancorp.tbroker.main;

import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.OrderStatus;
import com.ib.controller.OrderType;
import com.ib.controller.Types.Action;
import com.ib.controller.Types.SecType;
import com.ib.controller.Types.TimeInForce;
import com.pancorp.tbroker.handler.ConnectionHandlerAdapter;
import com.pancorp.tbroker.market.TopMktDataAdapter;
import com.ib.controller.ApiController;
import com.ib.controller.ApiController.IOrderHandler;
import com.ib.controller.ApiController.IPositionHandler;
import com.ib.controller.ApiController.ITopMktDataHandler;
import com.ib.controller.NewContract;
import com.ib.controller.NewOrder;
import com.ib.controller.NewOrderState;
import com.ib.controller.NewTickType;

public class OpenPositionDemo extends ConnectionHandlerAdapter implements ILogger {
	private final ApiController m_controller = new ApiController(this,this,this);
	private NewContract m_contract;
	private String m_symbol;
	private int m_position;
	private double m_bid;
	private double m_ask;
	private boolean m_placedOrder;
	
	public static void main(String[] args){
		new OpenPositionDemo().run(args[0]);
	}
	
	void run(String symbol){
		m_symbol = symbol;
		m_controller.connect("127.0.0.1", 7497, 0);//, "");
	}
	
	/**
	 * Callback method occurs when TWS (IB Gateway) sends the API 
	 * next valid order ID, that is stored in the ApiController class 
	 */
	@Override
	public void connected(){
		print("requesting positions");
		
		//first retrieve the position
		m_controller.reqPositions(new IPositionHandler(){
			/**
			 * Callback method overwritten in line
			 * called for all open position. Below interested only 
			 * in one position
			 * 
			 * @param account
			 * @param contract
			 * @param position
			 * @param avgCost
			 */
			//@Override 
			public void position(String account, NewContract contract, int position, double avgCost){
				if(contract.symbol().equals(m_symbol)&&contract.secType()==SecType.STK){
					m_contract = contract;
					m_position = position;
				}
			}

			/**
			 * Callback after all positions have been fed back
			 */
			@Override
			public void positionEnd() {
				onHavePosition();
			}
		});
	}
	
	protected void onHavePosition(){
		print("current position is " + m_position);
		
		//no open position exists for this symbol
		if(m_position==0){
			print("requesting market data");
			
			m_controller.reqTopMktData(m_contract, 
					"", //genericTickList, 
					false, //snapshot, 
				new TopMktDataAdapter() {

					@Override
					public void tickPrice(NewTickType tickType, 
							double price, 
							int canAutoExecute) {
						if(tickType == NewTickType.BID){
							m_bid = price;
							print("received bid " + price);
						}
						else if(tickType == NewTickType.ASK){
							m_ask = price;
							print("receive ask " + price);
						}
						
						checkPrices(this);
					}
			});
		}
	}
	
	void checkPrices(ITopMktDataHandler handler){
		if(m_bid!=0 && m_ask !=0 &&!m_placedOrder){
			m_placedOrder= true;
			
			print("desubscribing market data");
			m_controller.cancelTopMktData(handler);
		}
		
		placeOrder();
	}
	
	void placeOrder(){
		double midPrice = Math.round((m_bid + m_ask)/2*100) / 100.0 + 1;
		
		m_contract.exchange("SMART"); //smart routing
		m_contract.primaryExch("ISLAND"); // works for all US stocks
		
		NewOrder order = new NewOrder();
		order.action(Action.BUY); //m_position > 0 ? Action.SELL : 
		order.totalQuantity(100);
		
		//place LIMIT order at the current price (mid price)
		order.orderType(OrderType.LMT);
		order.lmtPrice(midPrice);
		order.tif(TimeInForce.DAY);
		
		print("placing order " + order);
		
		m_controller.placeOrModifyOrder(m_contract, order, new IOrderHandler() {
			
			@Override public void handle(int errorCode, String errMsg) {
				print("Order code and message: " + errorCode + " " + errMsg);
			}
			
			/**
			 * Callback
			 */
			@Override
			public void orderStatus(OrderStatus status, int filled, int remaining, 
					double avgFilledPrice,long permId, int parentId, 
					double lastFillPrice, int clientId, String whyHeld) {
				if(status==OrderStatus.Filled){
					print("Order has been filled: status=" + status + ", filled=" + filled + ", remaining=" + remaining + 
							",avgFilledPrice=" + avgFilledPrice+",permId=" + permId + ", parentId=" + parentId + 
							",lastFillPrice=" + lastFillPrice + ", clientId=" + clientId + ", whyHeld=" + whyHeld);
					System.exit(0);
				}
			}

			/**
			 * Callback
			 */
			@Override
			public void orderState(NewOrderState orderState) {
				print("Order state: " + orderState);
			}
		});
	//}
	}

	@Override
	public void log(String valueOf){
	}
	
	void print(String str){
		System.out.println(str);
	}
}
