package com.ts.test.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;

import com.ts.test.model.Tick;

public class DataFactory {
	Connection con = null;
	PreparedStatement ps = null;
	String sql = "";
	String url = null;
	String user = null;
	String password = null;
	
	public DataFactory() throws Exception {
		con  = DriverManager.getConnection(url, user, password);
		ps = con.prepareStatement(sql);
	}
	
	public int insertTick(Tick t) throws Exception {
		int status = -1;
		
		ps.setString(1, t.symbol());
		ps.setString(2, t.exchange());
		ps.setDouble(3, t.open());
		ps.setDouble(4, t.close());
		ps.setDouble(5, t.high());
		ps.setDouble(6, t.low());
		ps.setLong(7, t.time());
		ps.setLong(8, t.volume());
		ps.setDouble(9, t.wap());
		ps.setInt(10, t.count());
		
		status = ps.executeUpdate();
		return status;
	}
}
