/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.pancorp.tbroker.market;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class NewTabbedObject //extends JPanel 
{
	
	private final HashMap<String,Tab> m_map = new HashMap<String,Tab>();
	private int m_count = 2;
	
	public NewTabbedObject() {
		
	}

	public void addTab( final String title, final JComponent tab) {
		addTab( title, tab, false, false);
	}
	
	public void addTab( final String titleIn, final JComponent comp, boolean select, boolean canClose) {
		final String title = m_map.containsKey( titleIn)
			? titleIn + " " + m_count++ : titleIn;
		
	
		Tab tab = new Tab( title, comp);
		m_map.put( title, tab);
		
		if (m_map.size() == 1 || select) {
			select( title);
		}
		else {
			//button.setSelected( false);
		}
	}

	/** Select the correct panel and underline the correct html button. */
	public void select(String title) {
		
		Tab selectedTab = m_map.get( title);

		// select or deselect all buttons
		for( Tab tab : m_map.values() ) {
			//tab.m_button.setSelected( tab == selectedTab);
		}

		// call activated() on selected tab?
		if (!selectedTab.m_activated && selectedTab.m_comp instanceof INewTab) {
			((INewTab)selectedTab.m_comp).activated();
			selectedTab.m_activated = true;
		}
	}
	
	private Tab getSelectedTab() {
		for( Tab tab : m_map.values() ) {
			//if (tab.m_button.isSelected() ) {
			///	return tab;
			//}
		}
		return null;
	}

	public interface INewTab {
		void activated(); // called when the tab is first visited
		void closed();    // called when the tab is closed
	}
	
	public static abstract class NewTabPanel extends JPanel implements INewTab {
	}
	
	private static class Tab {
		String m_title;
		JComponent m_comp;
	
		boolean m_activated;

		public Tab(String title, JComponent comp) {
			m_title = title;
			m_comp = comp;
		
		}
	}


	public void onClosed() {
		Tab tab = getSelectedTab();
		if (tab.m_comp instanceof INewTab) {
			((INewTab)tab.m_comp).closed();
		}
		
		m_map.remove( tab.m_title);
		
		if (!m_map.isEmpty() ) {
			Entry<String, Tab> entry = m_map.entrySet().iterator().next();
			select( entry.getValue().m_title);
		}
	}

}
