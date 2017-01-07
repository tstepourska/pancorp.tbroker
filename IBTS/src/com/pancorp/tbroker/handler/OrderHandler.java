package com.pancorp.tbroker.handler;

import com.ib.controller.ApiController.IOrderHandler;
import com.ib.controller.NewOrderState;
import com.ib.controller.OrderStatus;

public class OrderHandler implements IOrderHandler {

	public OrderHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void orderState(NewOrderState orderState) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * After you place your order, TWS responds by sending back the EWrapper orderStatus() method
	 * 
	 * @param OrderStatus	--The order status. Possible values include:
	 * 							>PendingSubmit - indicates that you have transmitted the order, but have not yet received 
	 * 											 confirmation that it has been accepted by the order destination. 
	 * 											 NOTE: This order status is not sent by TWS and should be explicitly set 
	 * 											 by the API developer when an order is submitted.
	 *                          >PendingCancel - indicates that you have sent a request to cancel the order but 
	 *                                           have not yet received cancel confirmation from the order destination. 
	 *                                           At this point, your order is not confirmed canceled. You may still receive 
	 *                                           an execution while your cancellation request is pending. 
	 *                                           NOTE: This order status is not sent by TWS and should be explicitly set 
	 *                                           by the API developer when an order is canceled.
	 *                          >PreSubmitted  - indicates that a simulated order type has been accepted by the IB system 
	 *                                           and that this order has yet to be elected. The order is held in the 
	 *                                           IB system until the election criteria are met. At that time the order 
	 *                                           is transmitted to the order destination as specified .
	 *                          >Submitted     - indicates that your order has been accepted at the order destination and is working.
	 *                          >Cancelled     - indicates that the balance of your order has been confirmed canceled by the IB system. 
	 *                                           This could occur unexpectedly when IB or the destination has rejected your order.
	 *                          >Filled        - indicates that the order has been completely filled.
	 *                          >Inactive      - indicates that the order has been accepted by the system (simulated orders) or 
	 *                                           an exchange (native orders) but that currently the order is inactive due to system, 
	 *                                           exchange or other issues.
	 *                                           
	 * @param filled int	--Specifies the number of shares that have been executed.
	 * 		 				  For more information about partial fills, see Order Status for Partial Fills.
	 * 
	 * @param remaining int --Specifies the number of shares still outstanding.
	 * 
	 * @param avgFilledPrice double		--The average price of the shares that have been executed. This parameter is valid 
	 *                                    only if the filled parameter value is greater than zero. Otherwise, 
	 *                                    the price parameter will be zero.
	 *                                    
	 * @param permId long (int?)    	--The TWS id used to identify orders. Remains the same over TWS sessions.
	 * 
	 * @param parentId int				--The order ID of the parent order, used for bracket and auto trailing stop orders.
	 * 
	 * @param lastFilledPrice double	--The last price of the shares that have been executed. This parameter is valid 
	 *                                    only if the filled parameter value is greater than zero. Otherwise, 
	 *                                    the price parameter will be zero.
	 * 
	 * @param clientId int				--The ID of the client (or TWS) that placed the order. Note that TWS orders 
	 *                                    have a fixed clientId and orderId of 0 that distinguishes them from API orders.
	 * 
	 * @param whyHeld String            --This field is used to identify an order held when TWS is trying to locate 
	 *                                    shares for a short sell. The value used to indicate this is 'locate'.
	 */
	@Override
	public void orderStatus(OrderStatus status, int filled, int remaining, double avgFillPrice, long permId,
			int parentId, double lastFillPrice, int clientId, String whyHeld) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handle(int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}

}
