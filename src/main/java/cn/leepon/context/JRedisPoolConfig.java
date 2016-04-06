package cn.leepon.context;


public class JRedisPoolConfig {
	
	//可用连接实例的最大数目，默认值为8；  
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
	public static final int MAX_ACTIVE =1024;
	
	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。  
	public static final int MAX_IDLE =200;
	
	//超时时间 
	public static final int TIMEOUT =10000;
	
	//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
	public static final long MAX_WAIT =1000;
	
	//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用
	public static final boolean TEST_ON_BORROW = true;
	
	//在return一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用
	public static final boolean TEST_ON_RETURN = true;
	
	//Redis服务器IP 
	public static final String REDIS_IP = "10.59.72.31";
	
    //Redis的端口号
	public static final int REDIS_PORT =6379;
	
	//访问密码
	public static final String REDIS_PASSWORD = "";
	
	

}
