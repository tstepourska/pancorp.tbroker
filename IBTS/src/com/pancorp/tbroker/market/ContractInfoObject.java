/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.pancorp.tbroker.market;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ibts.apidemo.util.HtmlButton;
import com.ibts.apidemo.util.NewTabbedPanel;
import com.ibts.apidemo.util.NewTabbedPanel.INewTab;
import com.ibts.apidemo.util.TCombo;
import com.ibts.apidemo.util.VerticalPanel;

import com.ibts.controller.ApiController.IContractDetailsHandler;
import com.ibts.controller.ApiController.IFundamentalsHandler;
import com.ibts.controller.NewContract;
import com.ibts.controller.NewContractDetails;
import com.ibts.controller.Types.FundamentalType;

public class ContractInfoObject
{
	private final NewContract m_contract = new NewContract();
	private final NewTabbedPanel m_requestPanels = new NewTabbedPanel();
	private final NewTabbedPanel m_resultsPanels = new NewTabbedPanel();
	
	public ContractInfoObject() {
		//m_requestPanels.addTab( "Contract details", new DetailsRequestPanel() );
		//m_requestPanels.addTab( "Fundamentals", new FundaRequestObject() );
	
		//add( m_requestPanels, BorderLayout.NORTH);
		//add( m_resultsPanels);
	}
	
	class DetailsRequestPanel  {
		//ContractPanel m_contractPanel = new ContractPanel( m_contract);
		
		DetailsRequestPanel() {
			HtmlButton but = new HtmlButton( "Query") {
				@Override protected void actionPerformed() {
					onQuery();
				}
			};
		}
		
		protected void onQuery() {
			
			DetailsResultsObject panel = new DetailsResultsObject();
			//m_resultsPanels.addTab( m_contract.symbol() + " " + "Description", panel, true, true);
			//com.ibts.apidemo.ApiDemo.INSTANCE.controller().reqContractDetails(m_contract, panel);
		}
	}

	public static class DetailsResultsObject //extends JPanel 
	implements IContractDetailsHandler {
		JLabel m_label = new JLabel();
		JTextArea m_text = new JTextArea();
		
		public DetailsResultsObject() {
			
		}

		@Override public void contractDetails(ArrayList<NewContractDetails> list) {
			// set label
			if (list.size() == 0) {
				m_label.setText( "No matching contracts were found");
			}
			else if (list.size() > 1) {
				m_label.setText( list.size() + " contracts returned; showing first contract only");
			}
			else {
				m_label.setText( null);
			}
			
			// set text
			if (list.size() == 0) {
				m_text.setText( null);
			}
			else {
				m_text.setText( list.get( 0).toString() );
			}
		}
	}
	
	public class FundaRequestObject  {
		TCombo<FundamentalType> m_type = new TCombo<FundamentalType>( FundamentalType.values() );
		
		FundaRequestObject() {
			
		}
		
		protected void onQuery() {
		
			FundaResultObject panel = new FundaResultObject();
			FundamentalType type = m_type.getSelectedItem();
			//m_resultsPanels.addTab( m_contract.symbol() + " " + type, panel, true, true);
			//com.ibts.apidemo.ApiDemo.INSTANCE.controller().reqFundamentals( m_contract, type, panel); 
		}
	}	
	
	class FundaResultObject  implements INewTab, IFundamentalsHandler {
		String m_data;
		JTextArea m_text = new JTextArea();

		FundaResultObject() {
			
		}

		protected void onView() {
			try {
				File file = File.createTempFile( "tws", ".xml");
				FileWriter writer = new FileWriter( file);
				writer.write( m_text.getText() );
				writer.flush();
				writer.close();
				Desktop.getDesktop().open( file);
			}
			catch( Exception e) {
				e.printStackTrace();
			}
		}

		/** Called when the tab is first visited. */
		@Override public void activated() {
			//com.ibts.apidemo.ApiDemo.INSTANCE.controller().reqFundamentals(m_contract, FundamentalType.ReportRatios, this);
		}
		
		/** Called when the tab is closed by clicking the X. */
		@Override public void closed() {
		}

		@Override public void fundamentals(String str) {
			m_data = str;
			m_text.setText( str);
		}
	}
}
