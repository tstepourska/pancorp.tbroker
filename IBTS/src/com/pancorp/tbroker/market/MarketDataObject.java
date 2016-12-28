/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.pancorp.tbroker.market;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
/*
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;


import com.ibts.apidemo.util.HtmlButton;
import com.ibts.apidemo.util.NewTabbedPanel;
import com.ibts.apidemo.util.TCombo;
import com.ibts.apidemo.util.UpperField;
import com.ibts.apidemo.util.VerticalPanel;
//import com.ibts.apidemo.util.NewTabbedPanel.NewTabPanel;
import com.ibts.apidemo.util.VerticalPanel.StackPanel;
*/
import com.ibts.client.ScannerSubscription;
import com.ibts.controller.Bar;
import com.ibts.controller.Instrument;
import com.ibts.controller.NewContract;
import com.ibts.controller.NewContractDetails;
import com.ibts.controller.ScanCode;
import com.ibts.controller.ApiController.IDeepMktDataHandler;
import com.ibts.controller.ApiController.IHistoricalDataHandler;
import com.ibts.controller.ApiController.IRealTimeBarHandler;
import com.ibts.controller.ApiController.IScannerHandler;
import com.ibts.controller.Types.BarSize;
import com.ibts.controller.Types.DeepSide;
import com.ibts.controller.Types.DeepType;
import com.ibts.controller.Types.DurationUnit;
import com.ibts.controller.Types.MktDataType;
import com.ibts.controller.Types.WhatToShow;
import com.pancorp.tbroker.market.MarketTabbedObject.NewTab;
import com.pancorp.tbroker.market.NewTabbedObject.NewTabPanel;

public class MarketDataObject //extends JPanel 
{
	private final NewContract m_contract = new NewContract();
	private final MarketTabbedObject m_requestPanel = new MarketTabbedObject();
	private final MarketTabbedObject m_resultsPanel = new MarketTabbedObject();
	private TopResultsPanel m_topResultPanel;
	
	MarketDataObject() {
		
		m_requestPanel.addTab( "Top Market Data", new TopRequestPanel() );
		m_requestPanel.addTab( "Deep Book", new DeepRequestPanel() );
		m_requestPanel.addTab( "Historical Data", new HistRequestPanel() );
		m_requestPanel.addTab( "Real-time Bars", new RealtimeRequestPanel() );
		m_requestPanel.addTab( "Market Scanner", new ScannerRequestObject() );

	}

	private class TopRequestPanel extends NewTab {
		
		TopRequestPanel() {

		}

		protected void onTop() {
		
			if (m_topResultPanel == null) {
				m_topResultPanel = new TopResultsPanel();
				//m_resultsPanel.addTab( "Top Data", m_topResultPanel, true, true);
			}
			
			//m_topResultPanel.m_model.addRow( m_contract);
		}
	}
	
	private class TopResultsPanel extends NewTabPanel {
		///final TopModel m_model = new TopModel();
		//final JTable m_tab = new TopTable( m_model);
		
		TopResultsPanel() {
			
		}
		
		/** Called when the tab is first visited. */
		@Override public void activated() {
		}

		/** Called when the tab is closed by clicking the X. */
		@Override public void closed() {
			//m_model.desubscribe();
			m_topResultPanel = null;
		}

		void onReqType() {
			//com.ibts.apidemo.ApiDemo.INSTANCE.controller().reqMktDataType( m_typeCombo.getSelectedItem() );
		}
		
		class TopTable //extends JTable 
		{
			/*public TopTable(TopModel model) { super( model); }

			@Override public TableCellRenderer getCellRenderer(int rowIn, int column) {
				TableCellRenderer rend = super.getCellRenderer(rowIn, column);
				m_model.color( rend, rowIn, getForeground() );
				return rend;
			}*/
		}
	}		
	
	private class DeepRequestPanel {
		
		DeepRequestPanel() {

		}

		protected void onDeep() {

			DeepResultsObject resultPanel = new DeepResultsObject();
			//m_resultsPanel.addTab( "Deep " + m_contract.symbol(), resultPanel, true, true);
			//com.ibts.apidemo.ApiDemo.INSTANCE.controller().reqDeepMktData(m_contract, 6, resultPanel);
		}
	}

	public static class DeepResultsObject extends NewTab
	implements IDeepMktDataHandler {
		final DeepModel m_buy = new DeepModel();
		final DeepModel m_sell = new DeepModel();

		public DeepResultsObject() {
		}
		
		/*protected void onDesub() {
			com.ibts.apidemo.ApiDemo.INSTANCE.controller().cancelDeepMktData( this);
		}*/

		/*@Override public void activated() {
		}*/

		/** Called when the tab is closed by clicking the X. */
		/*@Override public void closed() {
			com.ibts.apidemo.ApiDemo.INSTANCE.controller().cancelDeepMktData( this);
		}*/
		
		@Override public void updateMktDepth(int pos, String mm, DeepType operation, DeepSide side, double price, int size) {
			if (side == DeepSide.BUY) {
				m_buy.updateMktDepth(pos, mm, operation, price, size);
			}
			else {
				m_sell.updateMktDepth(pos, mm, operation, price, size);
			}
		}

		class DeepModel //extends AbstractTableModel 
		{
			final ArrayList<DeepRow> m_rows = new ArrayList<DeepRow>();

			public int getRowCount() {
				return m_rows.size();
			}

			public void updateMktDepth(int pos, String mm, DeepType operation, double price, int size) {
				switch( operation) {
					case INSERT:
						m_rows.add( pos, new DeepRow( mm, price, size) );
						//fireTableRowsInserted(pos, pos);
						break;
					case UPDATE:
						m_rows.get( pos).update( mm, price, size);
						//fireTableRowsUpdated(pos, pos);
						break;
					case DELETE:
						if (pos < m_rows.size() ) {
							m_rows.remove( pos);
						}
						else {
							// this happens but seems to be harmless
							// System.out.println( "can't remove " + pos);
						}
						//fireTableRowsDeleted(pos, pos);
						break;
				}
			}

			public int getColumnCount() {
				return 3;
			}
			
			public String getColumnName(int col) {
				switch( col) {
					case 0: return "Mkt Maker";
					case 1: return "Price";
					case 2: return "Size";
					default: return null;
				}
			}

			public Object getValueAt(int rowIn, int col) {
				DeepRow row = m_rows.get( rowIn);
				
				switch( col) {
					case 0: return row.m_mm;
					case 1: return row.m_price;
					case 2: return row.m_size;
					default: return null;
				}
			}
		}
		
		static class DeepRow {
			String m_mm;
			double m_price;
			int m_size;

			public DeepRow(String mm, double price, int size) {
				update( mm, price, size);
			}
			
			void update( String mm, double price, int size) {
				m_mm = mm;
				m_price = price;
				m_size = size;
			}
		}
	}
///		end of DeepResultsObject
	
	private class HistRequestPanel extends NewTab  {
	
		HistRequestPanel() { 		
/*			m_end.setText( "20120101 12:00:00");
			m_duration.setText( "1");
			m_durationUnit.setSelectedItem( DurationUnit.WEEK);
			m_barSize.setSelectedItem( BarSize._1_hour);
			

			
	    	VerticalPanel paramPanel = new VerticalPanel();
			paramPanel.add( "End", m_end);
			paramPanel.add( "Duration", m_duration);
			paramPanel.add( "Duration unit", m_durationUnit);
			paramPanel.add( "Bar size", m_barSize);
			paramPanel.add( "What to show", m_whatToShow);
			paramPanel.add( "RTH only", m_rthOnly);
*/
		}
	
		protected void onHistorical() {
	
			BarResultsObject panel = new BarResultsObject(true);
			//com.ibts.apidemo.ApiDemo.INSTANCE.controller().reqHistoricalData(m_contract, m_end.getText(), m_duration.getInt(), m_durationUnit.getSelectedItem(), m_barSize.getSelectedItem(), m_whatToShow.getSelectedItem(), m_rthOnly.isSelected(), panel);
			//m_resultsPanel.addTab( "Historical " + m_contract.symbol(), panel, true, true);
		}
	}

	private class RealtimeRequestPanel {
	
		
		RealtimeRequestPanel() { 		
			
		}
	
		protected void onRealTime() {
	
			BarResultsObject panel = new BarResultsObject(false);
			//com.ibts.apidemo.ApiDemo.INSTANCE.controller().reqRealTimeBars(m_contract, m_whatToShow.getSelectedItem(), m_rthOnly.isSelected(), panel);
			//m_resultsPanel.addTab( "Real-time " + m_contract.symbol(), panel, true, true);
		}
	}
	
	public static class BarResultsObject extends NewTab implements IHistoricalDataHandler, IRealTimeBarHandler {
		final BarModel m_model = new BarModel();
		final ArrayList<Bar> m_rows = new ArrayList<Bar>();
		final boolean m_historical;
		
		public BarResultsObject( boolean historical) {
			m_historical = historical;
		}

		/** Called when the tab is first visited. */
		public void activated() {
		}

		/** Called when the tab is closed by clicking the X. */
		public void closed() {
			if (m_historical) {
				//com.ibts.apidemo.ApiDemo.INSTANCE.controller().cancelHistoricalData( this);
			}
			else {
				//com.ibts.apidemo.ApiDemo.INSTANCE.controller().cancelRealtimeBars( this);
			}
		}

		@Override public void historicalData(Bar bar, boolean hasGaps) {
			m_rows.add( bar);
		}
		
		@Override public void historicalDataEnd() {
			fire();
		}

		@Override public void realtimeBar(Bar bar) {
			m_rows.add( bar); 
			fire();
		}
		
		private void fire() {
			//SwingUtilities.invokeLater( new Runnable() {
				//public void run() {
					//m_model.fireTableRowsInserted( m_rows.size() - 1, m_rows.size() - 1);
					//m_chart.repaint();
				//}
			///});
		}

		class BarModel //extends AbstractTableModel 
		{
			public int getRowCount() {
				return m_rows.size();
			}

			public int getColumnCount() {
				return 7;
			}
			
			public String getColumnName(int col) {
				switch( col) {
					case 0: return "Date/time";
					case 1: return "Open";
					case 2: return "High";
					case 3: return "Low";
					case 4: return "Close";
					case 5: return "Volume";
					case 6: return "WAP";
					default: return null;
				}
			}

			public Object getValueAt(int rowIn, int col) {
				Bar row = m_rows.get( rowIn);
				switch( col) {
					case 0: return row.formattedTime();
					case 1: return row.open();
					case 2: return row.high();
					case 3: return row.low();
					case 4: return row.close();
					case 5: return row.volume();
					case 6: return row.wap();
					default: return null;
				}
			}
		}		
	}
	
	public class ScannerRequestObject //extends JPanel 
	{
		
		public ScannerRequestObject() {
			
			/*VerticalPanel paramsPanel = new VerticalPanel();
			paramsPanel.add( "Scan code", m_scanCode);
			paramsPanel.add( "Instrument", m_instrument);
			paramsPanel.add( "Location", m_location, Box.createHorizontalStrut(10), go);
			paramsPanel.add( "Stock type", m_stockType);
			paramsPanel.add( "Num rows", m_numRows);
			*/
			
		}

		protected void onGo() {
			ScannerSubscription sub = new ScannerSubscription();
			/*sub.numberOfRows( m_numRows.getInt() );
			sub.scanCode( m_scanCode.getSelectedItem().toString() );
			sub.instrument( m_instrument.getSelectedItem().toString() );
			sub.locationCode( m_location.getText() );
			sub.stockTypeFilter( m_stockType.getSelectedItem().toString() );
			
			ScannerResultsPanel resultsPanel = new ScannerResultsPanel();
			m_resultsPanel.addTab( sub.scanCode(), resultsPanel, true, true);

			com.ibts.apidemo.ApiDemo.INSTANCE.controller().reqScannerSubscription( sub, resultsPanel);
			*/
		}
	}

	public static class ScannerResultsObject extends NewTabPanel implements IScannerHandler {
		final HashSet<Integer> m_conids = new HashSet<Integer>();
		//final TopModel m_model = new TopModel();

		public ScannerResultsObject() {
			
		}

		/** Called when the tab is first visited. */
		@Override public void activated() {
		}

		/** Called when the tab is closed by clicking the X. */
		@Override public void closed() {
			//com.ibts.apidemo.ApiDemo.INSTANCE.controller().cancelScannerSubscription( this);
			//m_model.desubscribe();
		}

		@Override public void scannerParameters(String xml) {
			try {
				File file = File.createTempFile( "pre", ".xml");
				FileWriter writer = new FileWriter( file);
				writer.write( xml);
				writer.close();

				Desktop.getDesktop().open( file);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override public void scannerData(int rank, final NewContractDetails contractDetails, String legsStr) {
			if (!m_conids.contains( contractDetails.conid() ) ) {
				m_conids.add( contractDetails.conid() );
				//SwingUtilities.invokeLater( new Runnable() {
				//	@Override public void run() {
					//	m_model.addRow( contractDetails.contract() );
				//	}
				//});
			}
		}

		@Override public void scannerDataEnd() {
			// we could sort here
		}
	}
}
