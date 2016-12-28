/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.pancorp.tbroker.market;

import java.util.HashMap;

public class MarketTabbedObject //extends JPanel 
{
	private final HashMap<String,Object> m_map;
	//private int m_count = 2;
	
	public MarketTabbedObject() {
		m_map = new HashMap<String,Object>();
	}

	public void addTab( final String title, final Object tab) {
		m_map.put(title, tab);
	}

	public interface INewTab {
		//void activated(); // called when the tab is first visited
		//void closed();    // called when the tab is closed
	}
	
	public static abstract class NewTab implements INewTab {
	}
}
