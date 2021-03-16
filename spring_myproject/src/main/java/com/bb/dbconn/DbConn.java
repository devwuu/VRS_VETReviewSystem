package com.bb.dbconn;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.*;
import java.util.Properties;

public class DbConn {
	
	private static final DbConn dbConn = new DbConn();
	
	private DbConn() {		
	}
	
	public static DbConn getInstanceOf() {
		return dbConn;
	}
	
	public Connection oracleConn() {
		
		Connection conn = null;
		
		
		try {
			Properties p = new Properties();
			String path = DbConn.class.getResource("conn.properties").getPath();
			path = URLDecoder.decode(path, "utf-8");
			p.load(new FileReader(path));
			
			
			Class.forName(p.getProperty("driver"));
			conn = DriverManager.getConnection(p.getProperty("url"), p.getProperty("oracleId"), p.getProperty("oraclePw"));
			
			
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
		
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return conn;
	
	}


	
	
}
