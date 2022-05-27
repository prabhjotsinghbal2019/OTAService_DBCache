/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kochar.common;

import com.kochar.services.KSimpleLogger;
import java.sql.Connection;  
import java.sql.SQLException;
import java.util.logging.Level;

public class OTADbConnect {
    
    public static final int DBCONN_DBSTATUSWRITER = 0;
    public static final int DBCONN_DBHEALTHMONITOR = 1;
    public static final int DBCONN_DBERROREVENTS = 2;
    public static final int DBCONN_DBCACHEWRITER = 3;
    public static final int DBCONN_DBCACHEREADER = 4;
    public static final int DBCONN_DBGUIDREADER = 5;
    
    public static OTADbConnect[] dbConnect_Instances = new OTADbConnect[6];
    
    //
    // DB Connection Instance from the Connection Pool
    //
    private Connection dbConn = null;

    public static OTADbConnect getInstance(int instId) {
        if (null == dbConnect_Instances[instId]) {
            dbConnect_Instances[instId] = new OTADbConnect();
        }
        return dbConnect_Instances[instId];
    }

    public static void ReleaseInstance(int instId) {
        if (dbConnect_Instances[instId] != null) {
            dbConnect_Instances[instId].closeConnection();
            dbConnect_Instances[instId] = null;
        }
    }
    
    private OTADbConnect()
    {
        try {
            OTADbConnectPoolHelper.getInstance();
        } catch (SQLException ex) {
            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "OTADbConnect()", ex.toString() ,ex )));                    
        }
    }

    public Connection getConnection() {   
        try {
            if (dbConn != null) {
                closeConnection();
            }
            dbConn = OTADbConnectPoolHelper.getInstance().getConnection();
        } catch (SQLException e) {
            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "getConnection()",e.toString(),e )));        
            dbConn = null;
        }
        return dbConn;
    }

    public void setAutoCommit(boolean f) {
        try {
            if (dbConn != null) {
                dbConn.setAutoCommit(f);
            }
        } catch (SQLException e) {
            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "setAutoCommit()",e.toString(),e )));        
        }
    }
    
    public void commit() {
        try {
            if (dbConn != null) {
                dbConn.commit();
            }
        } catch (SQLException e) {
            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "commit()",e.toString(),e )));        
        }
    }

    public void rollback() {
        try {
            if (dbConn != null) {
                dbConn.rollback();
            }
        } catch (SQLException e) {
            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "rollback()",e.toString(),e )));        
        }
    }

    public void closeConnection() {
        try {
            if (dbConn != null) {
                OTADbConnectPoolHelper.getInstance().relConnection(dbConn);
                dbConn = null;
            }
        } catch (SQLException e) {
            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "closeConnection()",e.toString(),e )));        
        }
    }
            
}
