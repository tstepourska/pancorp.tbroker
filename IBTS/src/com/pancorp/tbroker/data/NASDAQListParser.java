package com.pancorp.tbroker.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;

import com.pancorp.tbroker.util.Globals;
import com.pancorp.tbroker.util.Utils;

public class NASDAQListParser {
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(NASDAQListParser.class);

	public NASDAQListParser() {
		
	}

	public void parseNASDAQList(String f){
		long mStart = System.currentTimeMillis();
		BufferedReader reader = null;
		int count = 0;
		
		try {
			reader = new BufferedReader(new FileReader(f));
			String line;
			String[] arr;// = new String[8];
			LinkedList<String[]> list = new LinkedList<>();
			while((line = reader.readLine())!=null){
				
				if(line.startsWith("Symbol")) {//count==0){
					//skip header				
					continue;
				}
				else if(line.startsWith("File")){
					//handle timestamp - last line

					continue;
				}
				
				count++;
				arr = line.split("\\|");
				/*if(lg.isTraceEnabled())
				for(int i=0;i<arr.length;i++){
					lg.trace("arr["+i+"]: " + arr[i]);
					
				}*/
				list.add(arr);
				
				//to comment
				//if(count>3)
				//	break;
			}
			lg.info(count + " records to load");
			int r = DataFactory.loadNASDAQList(list);
			long mEnd = System.currentTimeMillis();
			double dur = (mEnd - mStart);
			lg.info("loaded " + r + " records in " + dur + " millisec");
		}
		catch(Exception e){
			Utils.logError(lg, e);
		}
		finally{
			try {
				reader.close();
			}
			catch(Exception e){}
		}
	}
	
	public void parseNASDAQOtherList(String f){
		long mStart = System.currentTimeMillis();
		BufferedReader reader = null;
		int count = 0;
		
		try {
			reader = new BufferedReader(new FileReader(f));
			String line;
			String[] arr;// = new String[8];
			LinkedList<String[]> list = new LinkedList<>();
			while((line = reader.readLine())!=null){
				
				if(line.startsWith("ACT Symbol")) {
					//skip header				
					continue;
				}
				else if(line.startsWith("File Creation")){
					//handle timestamp - last line

					continue;
				}
				
				count++;
				arr = line.split("\\|");
				/*if(lg.isTraceEnabled())
				for(int i=0;i<arr.length;i++){
					lg.trace("arr["+i+"]: " + arr[i]);				
				}*/
				list.add(arr);
				
				//to comment
				//if(count>3)
				//	break;
			}
			lg.info(count + " records to load");
			int r = DataFactory.loadNASDAQOtherList(list);
			long mEnd = System.currentTimeMillis();
			double dur = (mEnd - mStart);
			lg.info("loaded " + r + " records in " + dur + " millisec");
		}
		catch(Exception e){
			Utils.logError(lg, e);
		}
		finally{
			try {
				reader.close();
			}
			catch(Exception e){}
		}
	}
	
	public static void main(String[] args){
		NASDAQListParser parser = new NASDAQListParser();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String f = null;
		
		f = Globals.BASEDIR + Globals.DATADIR + "nasdaqlisted.txt."+df.format(new Date(System.currentTimeMillis()));		
		parser.parseNASDAQList(f);
		
		f = Globals.BASEDIR + Globals.DATADIR + "otherlisted.txt."+df.format(new Date(System.currentTimeMillis()));
		parser.parseNASDAQOtherList(f);
		
	}
}
