/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ib.apidemo.AccountInfoPanel.MktValModel;
import com.ib.apidemo.AccountInfoPanel.Table;
import com.ib.apidemo.util.HtmlButton;
import com.ib.apidemo.util.VerticalPanel;
import com.ib.apidemo.util.NewTabbedPanel.NewTabPanel;
import com.ib.controller.MarketValueTag;
import com.ib.controller.ApiController.IMarketValueSummaryHandler;

public class MarketValueSummaryPanel extends NewTabPanel implements IMarketValueSummaryHandler {
	private MktValModel m_model = new MktValModel();

	MarketValueSummaryPanel() {
		HtmlButton sub = new HtmlButton( "Subscribe") {
			protected void actionPerformed() {
				subscribe();
			}
		};
		
		HtmlButton desub = new HtmlButton( "Desubscribe") {
			protected void actionPerformed() {
				desubscribe();
			}
		};
		
		JPanel buts = new VerticalPanel();
		buts.add( sub);
		buts.add( desub);

		JTable table = new Table( m_model, 2);
		JScrollPane scroll = new JScrollPane( table);
		
		setLayout( new BorderLayout() );
		add( scroll);
		add( buts, BorderLayout.EAST);
	}

	/** Called when the tab is first visited. */
	@Override public void activated() {
		subscribe();
	}

	/** Called when the tab is closed by clicking the X. */
	@Override public void closed() {
		desubscribe();
	}

	private void subscribe() {
		com.ib.apidemo.ApiDemo.INSTANCE.controller().reqMarketValueSummary( "All", this);
	}

	private void desubscribe() {
		com.ib.apidemo.ApiDemo.INSTANCE.controller().cancelMarketValueSummary( this);
		m_model.clear();
	}

	@Override public void marketValueSummary(String account, MarketValueTag tag, String value, String currency) {
		m_model.handle( account, currency, tag, value);
	}

	@Override public void marketValueSummaryEnd() {
	}
}
