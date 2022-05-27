/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kochar.common;

import redis.clients.jedis.Protocol;

/**
 *
 * @author PBal
 */
public class KTSharedContext {
    
    public static final String DBCONN_DBIP = "localhost:5433";
    public static final String DBCONN_DBNAME = "postgres";
    public static final String DBCONN_DBUNAME = "postgres";
    public static final String DBCONN_DBUPASS = "postgres";

    
    private static KTSharedContext _instance = null;

    public static KTSharedContext Instance() {
        if (_instance == null) {
            _instance = new KTSharedContext();
        }
        return _instance;
    }

    public static void ReleaseInstance() {
        if (_instance != null) {
            _instance.Close();
            _instance = null;
        }
    }

    public KTSharedContext() {
        
    }
    
    public void Close()
    {
    }

    public String getOtaDbIp() {
        return DBCONN_DBIP;
    }

    public String getDbName() {
        return DBCONN_DBNAME;
    }
    
    public String getDbUserName() {
        return DBCONN_DBUNAME;
    }    
    
    public String getDbPassword() {
        return DBCONN_DBUPASS;
    }
    
    public String getRedisMasterHost() {
        return Protocol.DEFAULT_HOST;
    }

    public int getRedisMasterPort() {
        return Protocol.DEFAULT_PORT;
    }

    public String getRedisMasterPassword() {
        return null;
    }

    public int getRedisMasterTimeout() {
        return Protocol.DEFAULT_TIMEOUT;
    }

    public String getRedisReadReplicaHost() {
        return Protocol.DEFAULT_HOST;
    }

    public int getRedisReadReplicaPort() {
        return Protocol.DEFAULT_PORT;
    }

        public String getRedisReadReplicaPassword() {
        return null;
    }

    public int getRedisReadReplicaTimeout() {
        return Protocol.DEFAULT_TIMEOUT;
    }
    
    public int getMemCacheTTL() {
        return Integer.MAX_VALUE;
    }
    
    public boolean getCachesClearMode() {
        return false;
    }

}
