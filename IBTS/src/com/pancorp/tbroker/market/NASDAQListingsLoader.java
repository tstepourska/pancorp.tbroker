package com.pancorp.tbroker.market;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import com.pancorp.tbroker.data.NASDAQListParser;
import com.pancorp.tbroker.ftp.FTPFileLoader;
import com.pancorp.tbroker.util.Globals;
import com.pancorp.tbroker.util.Utils;

public class NASDAQListingsLoader {
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(NASDAQListingsLoader.class);
	
	public NASDAQListingsLoader() {
	
	}
	
	private Properties init(String cfgFile) throws FileNotFoundException, Exception {
		Properties pp = new Properties();
		pp.load(new FileInputStream(cfgFile));
		
		return pp;
	}
	
	public void invoke(String cfgFile){
		try {
			//String cfgFile = args[0];
			//String cfgFile = "/Users/pankstep/run/TBroker/cfg/ftp.nasdaqlist.properties";
			FTPFileLoader ftp = new FTPFileLoader();
			Properties cfg = init(cfgFile);
			ftp.invoke(cfg);
			
			NASDAQListParser parser = new NASDAQListParser();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			String f = null;
			
			f = Globals.BASEDIR + Globals.DATADIR + "nasdaqlisted.txt."+df.format(new Date(System.currentTimeMillis()));		
			parser.parseNASDAQList(f);
			
			f = Globals.BASEDIR + Globals.DATADIR + "otherlisted.txt."+df.format(new Date(System.currentTimeMillis()));
			parser.parseNASDAQOtherList(f);
		}
		catch(FileNotFoundException fnfe){
			Utils.logError(lg, fnfe);
			System.exit(1);
		}
		catch(Exception e){
			Utils.logError(lg, e);
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		NASDAQListingsLoader nll = new NASDAQListingsLoader();
		nll.invoke(args[0]);
		
	}

}
