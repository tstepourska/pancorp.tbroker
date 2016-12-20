package com.pancorp.tbroker.condition;

public abstract class AbstractCondition implements ICondition {
	public boolean met;
	
	@Override
	public void setMet(boolean b){
		this.met = b;
	}
	@Override
	public boolean isMet(){
		return this.met;
	}
}
