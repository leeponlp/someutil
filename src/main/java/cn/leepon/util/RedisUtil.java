package cn.leepon.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import cn.leepon.context.JRedisPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisUtil {
	
	protected static Logger logger = Logger.getLogger(RedisUtil.class);  
    
	private static JedisPool jedisPool = null;
	
    private RedisUtil(){}
    
    static {    
        JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxActive(JRedisPoolConfig.MAX_ACTIVE);     
        config.setMaxIdle(JRedisPoolConfig.MAX_IDLE);  
        config.setMaxWait(JRedisPoolConfig.MAX_WAIT);   
        config.setTestOnBorrow(JRedisPoolConfig.TEST_ON_BORROW);  
        config.setTestOnReturn(JRedisPoolConfig.TEST_ON_RETURN);   
        //redis如果设置了密码：  
        //jedisPool = new JedisPool(config, JRedisPoolConfig.REDIS_IP,JRedisPoolConfig.REDIS_PORT,10000,JRedisPoolConfig.REDIS_PASSWORD);      
        //redis未设置了密码：  
        jedisPool = new JedisPool(config, JRedisPoolConfig.REDIS_IP,JRedisPoolConfig.REDIS_PORT);   
   }  
   
    private static JedisPool getPool() {    
	    return jedisPool;   
	}  
   
       
    /** 
     * 同步获取Jedis实例 
     * @return Jedis 
     */ 
    private synchronized static Jedis getJedis() {    
        if (jedisPool == null) {    
        	getPool();  
        }  
        Jedis jedis = null;  
        try {    
            if (jedisPool != null) {    
                jedis = jedisPool.getResource();   
            }  
        } catch (Exception e) {    
            logger.error("Get jedis error : "+e.getMessage());  
        }
        return jedis;  
    }
       
       
    /** 
     * 释放jedis资源 
     * @param jedis 
     */ 
    private static void returnResource(final Jedis jedis) {  
        if (jedis != null && jedisPool !=null) {  
            jedisPool.returnResource(jedis);  
        }  
    }  
       
       
    /** 
     * 设置 String 
     * @param key 
     * @param value 
     */ 
    public static void setString(String key ,String value){  
    	Jedis jedis = getJedis();
        try {  
            value = StringUtils.isEmpty(value) ? "" : value;  
            jedis.set(key,value);
            returnResource(jedis);
        } catch (Exception e) {  
            logger.error("Set key error : "+e.getMessage());  
        }  
    }  
       
    /** 
     * 设置 过期时间 
     * @param key 
     * @param seconds 以秒为单位 
     * @param value 
     */ 
    public static void setString(String key ,int seconds,String value){  
    	Jedis jedis = getJedis();
    	if (seconds <= 0) {   
            setString(key, value);  
        } 
        try {  
            value = StringUtils.isEmpty(value) ? "" : value;  
            jedis.setex(key, seconds, value); 
            returnResource(jedis); 
        } catch (Exception e) {  
            logger.error("Set keyex error : "+e.getMessage());  
        }  
    } 
    
    /** 
     * 获取String值 
     * @param key 
     * @return value 
     */ 
    public static String getString(String key){ 
    	Jedis jedis = getJedis();
        if(jedis == null || !jedis.exists(key)){  
            return null;  
        }
        String value = jedis.get(key);
        returnResource(jedis);
        return value;   
    }
    
        
    /**
     * 删除key对应的value
     * @param key
     */
    public static void delString(String key){
    	Jedis jedis = getJedis();
        if(jedis == null || !jedis.exists(key)){  
            return;  
        }  
        jedis.del(key);
        returnResource(jedis);
    }
    
    /**
     * 删除key对应的value
     * @param key
     */
    public static void delString(String... keys){
    	Jedis jedis = getJedis();
    	for (String key : keys) {
    		if(jedis == null || !jedis.exists(key)){  
    			return;  
    		}  
    		jedis.del(key);
		}
    	returnResource(jedis); 
    }
    
    /**
     * 缓存List<String>
     * @param key
     * @param collection
     * @return 
     */
    public static void setListCache(String key,List<String> list){
    	Jedis jedis = getJedis();
    	for (String str : list) {
    		str = StringUtils.isEmpty(str)?"":str;
    		jedis.rpush(key, str);
		}
    	returnResource(jedis); 
    }
    
    /**
     * 缓存List<String> 设置过期时间
     * @param key
     * @param collection
     * @return 
     */
    public static void setListCache(String key,int seconds,List<String> list){
    	Jedis jedis = getJedis();
    	if (seconds <=0) {
    		setListCache(key, list); 
		}else{
			setListCache(key, list); 
			jedis.expire(key, seconds);
		}
    	returnResource(jedis); 
    }

    
    
    /**
     * 返回List集合中的所有成员
     * @param key
     * @return
     */
    public static List<String> getListCache(String key){
    	Jedis jedis = getJedis();
        if(jedis == null || !jedis.exists(key)){  
            return null;  
        } 
        List<String> list = jedis.lrange(key, 0, -1);
        returnResource(jedis); 
        return list; 
    }
    
    /**
     *存放Set集合中的所有成员 
     * @param key
     * @param set
     */
    public static void setSetCache(String key,Set<String> set){
    	Jedis jedis = getJedis(); 
    	for (String str : set) {
    		str = StringUtils.isEmpty(str)?"":str;
    		jedis.sadd(key, str);  
		}
    	returnResource(jedis); 
    }
    
    /**
     * 存放Set集合中的所有成员 设置过期时间
     * @param key
     * @param set
     */
    public static void setSetCache(String key,int seconds,Set<String> set){
    	Jedis jedis = getJedis();
    	if (seconds <= 0) {   
    		setSetCache(key, set);  
        }else{
        	setSetCache(key, set);  
        	jedis.expire(key, seconds);
        }
    	returnResource(jedis); 
    }


    
    /** 
     * 返回Set集合中的所有成员 
     * @param String  key 
     * @return 成员集合 
     * */  
    public static Set<String> getSetCache(String key) {  
        Jedis jedis = getJedis(); 
        if(jedis == null || !jedis.exists(key)){  
            return null;  
        }
        Set<String> smembers = jedis.smembers(key);
        returnResource(jedis); 
        return smembers;  
    } 
    
    /**
     * 缓存map
     * @param key
     * @param map
     */
    public static void setMapCache(String key,Map<String, String> map){
    	Jedis jedis = getJedis();
    	jedis.hmset(key, map);
    	returnResource(jedis); 
    }
    
    /**
     * 缓存map 设置过期时间
     * @param key
     * @param seconds
     * @param map
     */
    public static void setMapCache(String key,int seconds,Map<String, String> map){
    	Jedis jedis = getJedis();
    	if (seconds<=0) {
			setMapCache(key, map); 
		}else{
			jedis.hmset(key, map);
			jedis.expire(key, seconds);
		}
    	returnResource(jedis); 
    	
    }
    
    /**
     * 返回map
     * @param key
     * @return
     */
    public static Map<String, String> getMapCache(String key){
    	Jedis jedis = getJedis(); 
        if(jedis == null || !jedis.exists(key)){  
            return null;  
        }
        Map<String, String> map = jedis.hgetAll(key);
        returnResource(jedis);
        return map;
    }
    
    /**
     * 返回所有key
     * @return
     */
    public static Set<String> getKeys(){
    	Jedis jedis = getJedis(); 
        if(jedis == null){  
            return null;  
        }
        Set<String> keys = jedis.keys("*");
        returnResource(jedis); 
		return keys; 
    }
    
    
    public static void main(String[] args) {
    	Set<String> keys = RedisUtil.getKeys();
    	System.err.println(keys.size());
    	for (String str : keys) {
			System.err.println(str);
		}
    	
	}

}
