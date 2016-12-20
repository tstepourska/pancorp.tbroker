package com.pancorp.tbroker.strategy;

import java.util.HashMap;

import com.pancorp.tbroker.condition.ICondition;

public interface IStrategy {
	//public void execute();
	//public boolean query() throws Exception;
	public void enter()  throws Exception;
	public void createOrder() throws Exception;
	public void submitOrder() throws Exception;
	public void exit() throws Exception;
	
	public HashMap getOpenedOrders();
	public HashMap<String, ICondition> getEntryConditions();	
	public void setEntryConditions(HashMap<String, ICondition> m);	
	public HashMap<String, ICondition> getExitConditions();
	public void setExitConditions(HashMap<String, ICondition> m);

}
