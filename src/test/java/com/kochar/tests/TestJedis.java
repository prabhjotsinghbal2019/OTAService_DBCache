/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.kochar.tests;

import com.thanuja.redis.RedisClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 *
 * @author Admin
 */
public class TestJedis {
    
    public TestJedis() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

     @Test
     public void testRedisConnect() {
         
         Jedis jedis = new Jedis();
         jedis.set("events/city/rome", "32,15,223,828");
         String cachedResponse = jedis.get("events/city/rome");    
         assertEquals(cachedResponse, "32,15,223,828");
     }
     
    @Test 
    public void testRedisClient()
    {
        RedisClient.setKey("name", "thanuja", 300);
   
        assertEquals( RedisClient.getKey("name"), "thanuja" );
        assertEquals( RedisClient.getTtl("name"), 300 );
        
        RedisClient.delKey("name");

        assertEquals( RedisClient.getKey("name"), null );
        assertEquals( RedisClient.getTtl("name"), -2 );
        
    }

    @Test 
    public void testRedisClientWithTTL()
    {
        try {
            RedisClient.setKey("name", "thanuja", 30);
            
            assertEquals( RedisClient.getKey("name"), "thanuja" );
            assertEquals( RedisClient.getTtl("name"), 30 );
            
            for (int i = 0; i < 25; i++) {
                    Thread.sleep(1000);
                    assertEquals( RedisClient.getKey("name"), "thanuja" );
                    assertTrue( RedisClient.getTtl("name") > 0 );
            }
            
            Thread.sleep(6000);
            assertEquals( RedisClient.getKey("name"), null );
            assertEquals( RedisClient.getTtl("name"), -2 );
            
        } catch (InterruptedException ex) {
                    System.out.println(ex);
        }
        
        
    }

    @Test 
    public void testRedisClientKeyIfAbsentWithTTL()
    {
        try {
            assertEquals(RedisClient.setKeyIfAbsent("name", "thanuja", 30), true);
            
            assertEquals( RedisClient.getKey("name"), "thanuja" );
            assertEquals( RedisClient.getTtl("name"), 30 );

            assertEquals(RedisClient.setKeyIfAbsent("name", "thanuja", 30), false);
            
            for (int i = 0; i < 25; i++) {
                    Thread.sleep(1000);
                    assertEquals( RedisClient.getKey("name"), "thanuja" );
                    assertTrue( RedisClient.getTtl("name") > 0 );
            }
            
            Thread.sleep(6000);
            assertEquals( RedisClient.getKey("name"), null );
            assertEquals( RedisClient.getTtl("name"), -2 );
            
        } catch (InterruptedException ex) {
                    System.out.println(ex);
        }
        
        
    }

    
}
