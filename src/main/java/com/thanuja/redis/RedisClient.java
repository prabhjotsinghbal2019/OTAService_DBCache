/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thanuja.redis;

import com.kochar.common.LogItem;
import com.kochar.common.ServiceMessage;
import com.kochar.services.KSimpleLogger;
import java.util.logging.Level;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.FlushMode;

/**
 *
 * @author thanuja
 */
public class RedisClient {

    /**
     * Sets a new key-value pair.
     *
     * @param key The name of the key
     * @param value The string value of the key
     */
    public static void setKey(String key, String value) {
        try (Jedis jedis = RedisConnection.getJedisPool(RedisConnection.REDIS_POOL_MASTER).getResource()) {
            jedis.set(key, value);            

        }

    }

    /**
     * Sets a new key-value pair with a time to live.
     *
     * @param key The name of the key
     * @param value The string value of the key
     * @param ttl The time to live of the key in seconds
     */
    public static void setKey(String key, String value, int ttl) {
        try (Jedis jedis = RedisConnection.getJedisPool(RedisConnection.REDIS_POOL_MASTER).getResource()) {
            //jedis.set(key, value);
            //jedis.expire(key, ttl);
            jedis.setex(key, ttl, value);

        }
    }

    /**
     * Sets a new key-value pair with a time to live only if key does not exist
     *
     * @param key The name of the key
     * @param value The string value of the key
     * @param ttl The time to live of the key in seconds
     * @return  true if key added false otherwise 
     */
    public static boolean setKeyIfAbsent(String key, String value, int ttl) {
        try (Jedis jedis = RedisConnection.getJedisPool(RedisConnection.REDIS_POOL_MASTER).getResource()) {
            long rVal = jedis.setnx(key, value);
            if (rVal > 0) { jedis.expire(key, ttl); }
            //jedis.setex(key, ttl, value);
            return (rVal > 0);
        }
    }

    /**
     * Returns a value of a key. Returns null if key does not exists.
     *
     * @param key The name of the key
     * @return The value of the key
     */
    public static String getKey(String key) {
        try (Jedis jedis = RedisConnection.getJedisPool(RedisConnection.REDIS_POOL_READREPLICA).getResource()) {
            return jedis.get(key);
        }
    }

    /**
     * @param key The name of the key
     * @return The remaining time to live of the key in seconds. -2 if the key
     * does not exist.-1 if the key exists but has no associated expire.
     */
    public static long getTtl(String key) {
        try (Jedis jedis = RedisConnection.getJedisPool(RedisConnection.REDIS_POOL_READREPLICA).getResource()) {
            return jedis.ttl(key);
        }
    }
    
    
    /**
     * Test if the specified key exists. The command returns true if the key exists, otherwise false is returned. Note that even keys set with an empty string as value will return true.
     * @param key The name of the key
     * @return  true if the key exists, otherwise false
     */
    public static boolean keyExists(String key) {
        try (Jedis jedis = RedisConnection.getJedisPool(RedisConnection.REDIS_POOL_READREPLICA).getResource()) {
            return jedis.exists(key);
        }
    }
    
    /**
     * @param key The name of the key
     * @return 1 if the key was removed, 0 if the key does not exist
     * 
     */
    public static long delKey(String key) {
        try (Jedis jedis = RedisConnection.getJedisPool(RedisConnection.REDIS_POOL_MASTER).getResource()) {
            return jedis.del(key);
        }
    }
    
    /**
     * Delete all the keys of all the existing databases, not just the currently selected one. This command never fails.
     * @param  
     * 
     * 
     */
    public static void delAllKeys() {
        try (Jedis jedis = RedisConnection.getJedisPool(RedisConnection.REDIS_POOL_MASTER).getResource()) {
            jedis.flushAll(FlushMode.ASYNC);
        }
    }
    
    /**
     * Return the number of keys in the currently selected database.
     * @param  
     * @return The number of keys
     * 
     */
    public static long dbSize() {
        try (Jedis jedis = RedisConnection.getJedisPool(RedisConnection.REDIS_POOL_READREPLICA).getResource()) {
            return jedis.dbSize();
        }
    }
       
    /**
     * Close the JedisPool objects
     * @param  
     * 
     * 
     */
    public static void close() {
        try {
            RedisConnection.retJedisPool(RedisConnection.REDIS_POOL_MASTER);
            RedisConnection.retJedisPool(RedisConnection.REDIS_POOL_READREPLICA);
        } catch(Exception e) {
            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, "RedisClient", "close()", e.toString(), e)));                
        }
    }

}
