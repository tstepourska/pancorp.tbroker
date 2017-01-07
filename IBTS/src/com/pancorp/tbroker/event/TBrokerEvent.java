package com.pancorp.tbroker.event;

public class TBrokerEvent {

	private Events action = null;
	
	public TBrokerEvent(Events e) {
		this.action = e;
	}

	/**
	 * @return the action
	 */
	public Events getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Events action) {
		this.action = action;
	}
}
