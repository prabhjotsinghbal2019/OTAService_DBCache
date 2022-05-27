/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kochar.common;

import com.kochar.services.KSimpleLogger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import org.apache.commons.dbcp2.BasicDataSource;

public final class OTADbConnectPoolHelper {
    
    public static OTADbConnectPoolHelper dbConnect_Pool_Instances = null;
        
    public static String DBSERVER = KTSharedContext.Instance().getOtaDbIp();
    public static String DBNAME = KTSharedContext.Instance().getDbName();
    public static String DBUSERNAME = KTSharedContext.Instance().getDbUserName();
    public static String DBPASSWORD = KTSharedContext.Instance().getDbPassword();
    
    BasicDataSource dataSource = null;
    
    public static OTADbConnectPoolHelper getInstance() throws SQLException {
        if (null == dbConnect_Pool_Instances) {
            dbConnect_Pool_Instances = new OTADbConnectPoolHelper();
        }
        return dbConnect_Pool_Instances;
    }

    public static void releaseInstance() {
        if (dbConnect_Pool_Instances != null) {
            try {
                dbConnect_Pool_Instances.shutdownDataSource();
            } catch (SQLException ex) {
                KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, OTADbConnectPoolHelper.class.getName(), "releaseInstance()", ex.toString(), ex )));        
            } finally {
                dbConnect_Pool_Instances = null;
            }
        }
    }
    
    private OTADbConnectPoolHelper() {
        KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.INFO, this.getClass().getName(), "OTADbConnectPoolHelper()", "Setting up db data source.", null )));        
        setupDataSource();
        printDataSourceStats();
        KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.INFO, this.getClass().getName(), "OTADbConnectPoolHelper()", "Done.", null )));        
    }

    private void setupDataSource() {   
        try {

                String connectionUrl = "jdbc:postgresql://" + getDBSERVER() + "/" + getDBNAME();

                dataSource = new BasicDataSource();
                dataSource.setUrl(connectionUrl);
                dataSource.setUsername(getDBUSERNAME());
                dataSource.setPassword(getDBPASSWORD());
                dataSource.setMinIdle(5);
                dataSource.setMaxIdle(10);
                dataSource.setMaxOpenPreparedStatements(100);                

        } catch (Exception e) {
            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "setupDataSource()",e.toString(),e )));        
            dataSource = null;
        } finally {
              
        }
    }
    
    public Connection getConnection() throws SQLException {
        return (dataSource == null ? null : dataSource.getConnection());
    }

    public boolean relConnection(Connection c) throws SQLException {
        if (dataSource != null && c != null) {
            c.close();
            return true;
        }
        return false;
    }

    public void printDataSourceStats() {
        if (dataSource != null) {
          BasicDataSource bds = (BasicDataSource) dataSource;
          KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.INFO, this.getClass().getName(), "printDataSourceStats()", "AutoCommitOnReturn: " + bds.getAutoCommitOnReturn(), null )));
          KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.INFO, this.getClass().getName(), "printDataSourceStats()", "NumActive: " + bds.getNumActive(), null )));
          KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.INFO, this.getClass().getName(), "printDataSourceStats()", "NumIdle: " + bds.getNumIdle(), null )));
        }
    }
  
    private void shutdownDataSource() throws SQLException {
        if (dataSource != null) {
          KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.INFO, this.getClass().getName(), "shutdownDataSource()", "Shutting down db data source.", null )));        
          BasicDataSource bds = (BasicDataSource) dataSource;
          bds.close();
          KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.INFO, this.getClass().getName(), "shutdownDataSource()", "Done.", null )));        
        }
    }

    private String getDBSERVER()
    {
        DBSERVER = KTSharedContext.Instance().getOtaDbIp();
        return DBSERVER;
    }
            
    private String getDBNAME()
    {
        DBNAME = KTSharedContext.Instance().getDbName();
        return DBNAME;
    }
    
    private String getDBUSERNAME()
    {
        DBUSERNAME = KTSharedContext.Instance().getDbUserName();
        return DBUSERNAME;
    }
    
    private String getDBPASSWORD()
    {
        DBPASSWORD = KTSharedContext.Instance().getDbPassword();
        return DBPASSWORD;
    }
            
}
