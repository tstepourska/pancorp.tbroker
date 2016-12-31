package com.pancorp.tbroker.ftp;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamException;
import org.apache.logging.log4j.LogManager;

import com.pancorp.tbroker.util.Utils;

public final class FTPFileLoader {
	private static org.apache.logging.log4j.Logger lg = LogManager.getLogger(FTPFileLoader.class);
	/*
	private static final String LOG_PATH = "/Users/pankstep/log/TBroker/ftp.log";
	private static final String FTP_REMOTE = "/symboldirectory/";
	//private static final String FILE_NAME_MASK_KEY = "yyyymm";
	private static final String FTP_SERVER = "ftp://ftp.nasdaqtrader.com";
	private static final String FTP_USER = "anonymous";
	private static final String FTP_PWD = "pankstep@yahoo.com";
	private static final String FTP_LOCAL_DIR = "/Users/pankstep/Jts/data/";
	private static final String UPDATE_FREQUENCY = "every_night_before_business_day"; //TODO to implement logic: at 9 pm?
	*/
	
	private static final String FILE_NASDAQ = "nasdaqlisted.txt";
	private static final String FILE_OTHER = "otherlisted.txt";
	
	private static final String LOG_PATH_KEY = "ftp.log";
	private static final String FTP_REMOTE_KEY = "ftp.remote.dir";
	private static final String FTP_SERVER_KEY = "ftp.server";
	private static final String FTP_USER_KEY = "ftp.user";
	private static final String FTP_PWD_KEY = "ftp.pwd";
	private static final String FTP_LOCAL_DIR_KEY = "ftp.local.dir";
	private static final String UPDATE_FREQUENCY_KEY = "update.frequency";
	private static final String FILE_NASDAQ_KEY = "file.nasdaq.listed";
	private static final String FILE_OTHER_KEY = "file.other.listed";
	
	//private long remoteSize = 0;
	
	/**
	 * Connects to remote server and fetches the report file
	 * 
	 * @param cfg
	 * @param fiscalMonth
	 * 
	 */
	public void invoke(Properties cfg)//,long fiscalMonth) 
	throws FileNotFoundException, Exception {
		//String filename = null;
		OutputStream output = null;
		//custom stream for command listener
		OutputStream logOutput = new FileOutputStream(cfg.getProperty(LOG_PATH_KEY), true);
		boolean binaryTransfer = true;
		boolean error = false;
		boolean success = false;
		String remote = null;
		String local = null;
		FTPClient ftp;
		int reply;
		
		//retrieve config values
		String remoteDirectory = cfg.getProperty(FTP_REMOTE_KEY);
		//String fileMask        = FILE_NAME_MASK_KEY;
		String server          = cfg.getProperty(FTP_SERVER_KEY);
		String username        = cfg.getProperty(FTP_USER_KEY);
		String password		   = cfg.getProperty(FTP_PWD_KEY);
		//String destFilename    = FILE_NASDAQ;
		
		ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(logOutput)));
		
		try {
			ftp.connect(server);
			lg.debug("connected");
			
			//After the connection attempt you should check the reply code to verify success
			reply = ftp.getReplyCode();
			lg.info("reply: " + reply);
			
			if(!FTPReply.isPositiveCompletion(reply)){
				ftp.disconnect();
				lg.error("FTP server refused connection. Reply: " + reply);
				throw new Exception("FTP server refused connection. Reply: " + reply);
			}
			
		}
		catch(IOException e){
			Utils.logError(lg,e);
			if(ftp.isConnected()){
				try { ftp.disconnect(); } catch(IOException ex){}
			}
			
			throw e;
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		__main:
			try {
				if(!ftp.login(username, password)){
					ftp.logout();
					error = true;
					break __main;
				}
				
				//lg.info("remote system is " + ftp.getSystemName());
				if(binaryTransfer)
					ftp.setFileType(FTP.BINARY_FILE_TYPE);
				//use passive mode as default because most of us are behind firewalls these days
				ftp.enterLocalPassiveMode();
				
				
				//lookup file(s) corresponding the mask
				ArrayList<String> filenames = findReportFile(ftp, remoteDirectory); //, fileMask);
				
				for(String filename: filenames){		
					local = cfg.getProperty(FTP_LOCAL_DIR_KEY) + filename + "."+ df.format(new Date(System.currentTimeMillis()));
					lg.debug("local file: " + local);
				
					remote = remoteDirectory + filename;

					output = new FileOutputStream(local);
					success = ftp.retrieveFile(remote, output);
					output.flush();
					output.close();
					lg.info("FTP transaction complete: " + success);
					///////////////////////////////////////
				
					//check if the file transferred correctly
					File fe = new File(local);
					if(fe.exists()&&fe.length()>0){
					lg.info("local file eixsts and is not empty: " + fe.length());
					}
					else {
						//throw new FileNotFoundException(
						lg.error("Local file "+filename+" not found or empty, FTP unsuccessful");
					}
				}	//end of for each filename
				
				ftp.logout();
				lg.debug("logged out of the FTP");
			}
			catch(FTPConnectionClosedException e){
				error = true;
				lg.error("Remote server closed connection. FTPConnectionClosedException: " + e.getMessage());
			}
			catch(CopyStreamException cse){
				//-If an I/O error occurs while actually transferring the file
				//The CopyStreamException allows you to determine the number of bytes transferred 
				//and the IOException actually causing the error
				error = true;
				Utils.logError(lg, cse);
			}
			catch(IOException ioe){
				//if an I/O error occurs while sending a command to the server or 
				//receiving a reply from the server
				error = true;
				Utils.logError(lg, ioe);
			}
			finally {
				lg.trace("finally");
				if(ftp.isConnected()){
					if(lg.isTraceEnabled())
					lg.trace("ftp trying to disconnect");
					try {
						ftp.disconnect();
						if(lg.isTraceEnabled())
						lg.trace("ftp disconnected: " + !ftp.isConnected());
					}catch(Exception e){
						lg.error("Error ftp disconnect: " + e.getMessage());
					}
				}
				
				if(output!=null){
					if(lg.isTraceEnabled())
						lg.trace("closing local output..");
					try {
						output.close();
						if(lg.isTraceEnabled())
							lg.trace("closed local output");
						}catch(Exception e){
							lg.error("Error closing local output: " + e.getMessage());
						}
				}
			}
		
		if(error || !success)
			throw new Exception("Could not complete FTP task");
		
		lg.info("Completed FTP task");
		
		//return local filename
		//return local;
	}	//end main
	

	public ArrayList<String> findReportFile(FTPClient f, String rDir//, String fMask
			) throws Exception {
		String fName = null;
		FTPFile[] files = null;
		FTPFile fi = null;
		//FTPFile latest = null;
		//Calendar cal = null;
		ArrayList<String> ff = new ArrayList<>();
		
		//initialize parsing directory engine
		FTPListParseEngine engine = f.initiateListParsing(rDir);
		while(engine.hasNext()){
			files = engine.getNext(25);
			
			for(int i=0;i<files.length;i++){
				fi = files[i];
				if(!fi.isFile())
					continue;
				
				fName = fi.getName();
				//lg.debug("file name: " + fName);
				
				if(fName.compareTo(FILE_NASDAQ)==0 || fName.compareTo(FILE_OTHER)==0){
				
				//if(fName.indexOf(fMask)>=0){
					//cal = fi.getTimestamp();
					//TODO: based on configuration check the timestamp to see whether file has  been updated since last sweep
					////if(latest==null || latest.getTimestamp().before(cal)){
					//	lg.info("saving current as latest");
					//	latest = fi;
					//}
					
					ff.add(fName);
				}
			}
		}
		
		if(ff.size()<=0){
			lg.info("No file found");
			return null;
		}
		
		return ff;
	}

	/*
	public String findReportFile(FTPClient f, String rDir, String fMask) throws Exception {
		String fName = null;
		FTPFile[] files = null;
		FTPFile fi = null;
		FTPFile latest = null;
		Calendar cal = null;
		ArrayList<FTPFile> ff = new ArrayList<>();
		
		//////EXAMPLE//////////////////
		// FTPClient f = new FTPClient();
		// f.connect(server);
		// f.login(username,password);
		// FTPListParseEngine engine = f.initiateListParsing(directory);
		// while(engine.hasNext()){
		// 		FTPFile[] files = engine.getNext(25); // page size you want
		// 		do whatever you want with these files, display them, etc
		// 		expensive FTPFile objects are not created until needed
		// }
		///////////////////////////////
		
		//initialize parsing directory engine
		FTPListParseEngine engine = f.initiateListParsing(rDir);
		while(engine.hasNext()){
			files = engine.getNext(25);
			
			for(int i=0;i<files.length;i++){
				fi = files[i];
				if(!fi.isFile())
					continue;
				
				fName = fi.getName();
				lg.debug("file name found: " + fName);
				
				if(fName.indexOf(fMask)>=0){
					//remoteSize = fi.geSize();
					//cal = fi.getTimestamp();
					ff.add(fi);
					
					//return fName
				}
			}
		}
		
		if(ff.size()<=0){
			lg.info("No file found");
			return null;
		}
		else if(ff.size()==1){
			fName =( (FTPFile)ff.get(0)).getName();
		}
		else {
			//found more than 1, getting the latest
			for(int i=0;i<ff.size();i++){
				fi = (FTPFile)ff.get(i);
				fName = fi.getName();
				cal = fi.getTimestamp();
				
				if(latest==null || latest.getTimestamp().before(cal)){
					lg.info("saving current as latest");
					latest = fi;
				}
			}
			fName = latest.getName();
		}
		return fName;
	}
	*/
	public FTPFileLoader() {
		// TODO Auto-generated constructor stub
	}

}
