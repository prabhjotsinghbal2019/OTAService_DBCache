/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thanuja.redis;

import com.kochar.common.KTSharedContext;
import java.time.Duration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 *
 * @author thanuja
 */
public class RedisConnection {
    
    public static final short REDIS_POOL_MASTER = 0;
    public static final short REDIS_POOL_READREPLICA = 1;
    public static final short REDIS_POOL_SIZE = 2;
    

    private static final JedisPool[] jedisPool = new JedisPool[REDIS_POOL_SIZE];

    /**
     * Returns a pool of Jedis connections.Create Create a new pool of Jedis connections if jedisPool is null.
     *
     * @param poolId
     * @return JedisPool
     */
    protected static JedisPool getJedisPool(short poolId) {

        if (null == jedisPool[poolId]) {
            createJedisPool(poolId);
        }
        return jedisPool[poolId];

    }

    protected static void retJedisPool(short poolId) {

        if (null != jedisPool[poolId]) {
            closeJedisPool(poolId);
        }
 
    }
    /**
     * Create a new pool of Jedis connections.
     */
    private static void createJedisPool(short poolId) {

        String host = Protocol.DEFAULT_HOST;
        int port = Protocol.DEFAULT_PORT;
        int timeout = Protocol.DEFAULT_TIMEOUT;
        String password = null;
        
        switch(poolId) {
            
            case REDIS_POOL_MASTER:
                
                host = KTSharedContext.Instance().getRedisMasterHost();
                port = KTSharedContext.Instance().getRedisMasterPort();
                timeout = KTSharedContext.Instance().getRedisMasterTimeout();
                password = KTSharedContext.Instance().getRedisMasterPassword();
                
                break;
                
            case REDIS_POOL_READREPLICA:
                
                host = KTSharedContext.Instance().getRedisReadReplicaHost();
                port = KTSharedContext.Instance().getRedisReadReplicaPort();
                timeout = KTSharedContext.Instance().getRedisReadReplicaTimeout();
                password = KTSharedContext.Instance().getRedisReadReplicaPassword();
                
                break;
        }

        //jedisPool = new JedisPool(host, port);

        /*
        For a secured redis installation the following constructor can be used to create the JedisPool
        */
        
        final JedisPoolConfig jedisPoolConfig = buildPoolConfig();
        jedisPool[poolId] = new JedisPool(jedisPoolConfig, host, port, timeout, password);
    }
    
    private static JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }
    
    private static void closeJedisPool(short poolId) {
        jedisPool[poolId].close();
        jedisPool[poolId] = null;
    }

}
