package com.kochar.caches;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.kochar.common.Attribute;
import com.kochar.common.KTSharedContext;
import com.kochar.common.LogItem;
import com.kochar.common.ServiceMessage;
import com.kochar.msg.TripletBean;
import com.kochar.services.KSimpleLogger;
import com.thanuja.redis.RedisClient;
import java.util.logging.Level;


/**
 *
 * @author gundeep.kaur
 */
public class KTTripletsInMemoryCache extends Attribute  
{
    private static final long TTL_SECS = KTSharedContext.Instance().getMemCacheTTL(); 
    
    private static KTTripletsInMemoryCache instance = null;
 
    public static KTTripletsInMemoryCache getInstance() {
        if (instance == null) {
            instance = new KTTripletsInMemoryCache();
        }
        return instance;
    }

    public static void releaseInstance()
    {
        if (instance != null) {
            try {
                instance.Close();
                instance = null;
            } catch (Exception ex) {
                KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, "KTTripletsInMemoryCache", "releaseInstance()", ex.toString(), ex)));                
            }
        }
    }
 
    public KTTripletsInMemoryCache() {
        super();
        try {
            if (KTSharedContext.Instance().getCachesClearMode() == true) {
                RedisClient.delAllKeys();
            }
        } catch (Exception e) {
            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "ctor()", e.toString(), e)));
        }
    }
 
    public void put(String key, int value) {
        RedisClient.setKeyIfAbsent(key, String.valueOf(value), (int) TTL_SECS);
    }
    
    public boolean contains(TripletBean key) {
        return (RedisClient.keyExists(key.toMemento())); 
    }
 
    public Integer get(TripletBean key) {
            String v = (String) RedisClient.getKey(key.toMemento());
            if (v == null)
                return -1;
            else {
                return Integer.valueOf(v);
            }
    }
 
    public void remove(String key) {
        RedisClient.delKey(key);
    }
 
    public int size() {
         return (int)RedisClient.dbSize();
    }
    
    public void Close()
    {
        try {
            RedisClient.close();
        } catch (Exception ex) {
            KSimpleLogger.Instance().SendMessage(new ServiceMessage(new LogItem(Level.SEVERE, this.getClass().getName(), "Close()", ex.toString(), ex)));
        }
    }
    
}
