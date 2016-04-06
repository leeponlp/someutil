package cn.leepon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 基于DBUtils封装的数据库操作工具类
 * @author leepon
 *
 */
public class DButil {
	
	    private final static Logger LOGGER = LoggerFactory.getLogger(DButil.class);
	
	    private static final QueryRunner runner = new QueryRunner();
	    
	    /**
	     * 打开数据库连接
	     * @param jdbcurl
	     * @param username
	     * @param password
	     * @return conn
	     */
	    public static Connection openConn(String jdbcurl, String username, String password) {
	        Connection conn = null;
	        try {
	            String  driver = "com.mysql.jdbc.Driver";
	            String  url = jdbcurl;
	            Class.forName(driver);
	            conn = DriverManager.getConnection(url, username, password);
	        } catch (Exception e) {
	            e.printStackTrace();
	            LOGGER.info("获取数据库连接失败！");
	        }
	        return conn;
	    }
	 
	    /**
	     * 打开数据库
	     * @param type:MySQL，Oracle，SQLServer
	     * @param host
	     * @param port
	     * @param name
	     * @param username
	     * @param password
	     * @return
	     */
	    public static Connection openConn(String type, String host, String port, String name, String username, String password) {
	        Connection conn = null;
	        try {
	            String driver;
	            String url;
	            if (type.equalsIgnoreCase("MySQL")) {
	                driver = "com.mysql.jdbc.Driver";
	                url = "jdbc:mysql://" + host + ":" + port + "/" + name;
	            } else if (type.equalsIgnoreCase("Oracle")) {
	                driver = "oracle.jdbc.driver.OracleDriver";
	                url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + name;
	            } else if (type.equalsIgnoreCase("SQLServer")) {
	                driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	                url = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + name;
	            } else {
	                throw new RuntimeException();
	            }
	            Class.forName(driver);
	            conn = DriverManager.getConnection(url, username, password);
	        } catch (Exception e) {
	            e.printStackTrace();
	            LOGGER.info("获取数据库连接失败！");
	        }
	        return conn;
	    }
	 
	    
	    /**
	     * 关闭数据库连接
	     * 
	     * @param conn
	     */
	    public static void closeConn(Connection conn) {
	        try {
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            LOGGER.info("关闭数据库连接失败！");
	        }
	    }
	    
	    /**
	     * 开启事务
	     * 
	     * @param conn
	     */
	    public static void startTransaction(Connection conn){
	    	try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOGGER.info("开启事务处理失败！");
			}
	     }
	    
	    /**
	     * 回滚事务
	     * 
	     * @param conn
	     */
	    public static void rollbackTransaction(Connection conn){
	    	if(conn == null){
				return;
			}
			try {
				conn.rollback();
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.info("回滚事务处理失败！");
			}finally{
				closeConn(conn);
			}

	    }
	    
	    /**
	     * 提交事务
	     * 
	     * @param conn
	     */
	    public static void commitTransaction(Connection conn){
			if(conn == null){
				return;
			}
			try {
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.info("提交事务处理失败！");
			}finally{
				closeConn(conn);
			}
		}
	 
	    /**
	     * 查询（返回Array结果）
	     * @param conn
	     * @param sql The SQL to execute.
	     * @param params
	     * @return
	     */
	    public static Object[] queryArray(Connection conn, String sql, Object... params) {
	        Object[] result = null;
	        try {
	            result = runner.query(conn, sql, new ArrayHandler(), params);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * 查询（返回ArrayList结果）
	     * @param conn
	     * @param sql The SQL to execute.
	     * @param params
	     * @return
	     */
	    public static List<Object[]> queryArrayList(Connection conn, String sql, Object... params) {
	        List<Object[]> result = null;
	        try {
	            result = runner.query(conn, sql, new ArrayListHandler(), params);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * 查询（返回Map结果）
	     * @param conn
	     * @param sql  The SQL to execute.
	     * @param params
	     * @return
	     */
	    public static Map<String, Object> queryMap(Connection conn, String sql, Object... params) {
	        Map<String, Object> result = null;
	        try {
	            result = runner.query(conn, sql, new MapHandler(), params);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * 查询（返回MapList结果）
	     * @param conn
	     * @param sql  The SQL to execute.
	     * @param params
	     * @return
	     */
	    public static List<Map<String, Object>> queryMapList(Connection conn, String sql, Object... params) {
	        List<Map<String, Object>> result = null;
	        try {
	            result = runner.query(conn, sql, new MapListHandler(), params);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * 查询（返回Bean结果）
	     * @param cls
	     * @param map 字段名和表名
	     * @param conn
	     * @param sql  The SQL to execute.
	     * @param params
	     * @return
	     */
	    public static <T> T queryBean(Class<T> cls, Map<String, String> map, Connection conn, String sql, Object... params) {
	        T result = null;
	        try {
	            if (MapUtil.isNotEmpty(map)) {
	                result = runner.query(conn, sql, new BeanHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
	            } else {
	                result = runner.query(conn, sql, new BeanHandler<T>(cls), params);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * 查询（返回BeanList结果）
	     * @param cls
	     * @param map 字段名和表名
	     * @param conn
	     * @param sql  The SQL to execute.
	     * @param params
	     * @return
	     */
	    public static <T> List<T> queryBeanList(Class<T> cls, Map<String, String> map, Connection conn, String sql, Object... params) {
	        List<T> result = null;
	        try {
	            if (MapUtil.isNotEmpty(map)) {
	                result = runner.query(conn, sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
	            } else {
	                result = runner.query(conn, sql, new BeanListHandler<T>(cls), params);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * 查询指定列名的值（单条数据）
	     * @param column
	     * @param conn
	     * @param sql The SQL to execute.
	     * @param params
	     * @return
	     */
	    public static <T> T queryColumn(String column, Connection conn, String sql, Object... params) {
	        T result = null;
	        try {
	            result = runner.query(conn, sql, new ScalarHandler<T>(column), params);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * 查询指定列名的值（多条数据）
	     * @param column
	     * @param conn
	     * @param sql The SQL to execute.
	     * @param params
	     * @return
	     */
	    public static <T> List<T> queryColumnList(String column, Connection conn, String sql, Object... params) {
	        List<T> result = null;
	        try {
	            result = runner.query(conn, sql, new ColumnListHandler<T>(column), params);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * 查询指定列名对应的记录映射
	     * @param column
	     * @param conn
	     * @param sql The SQL to execute.
	     * @param params
	     * @return
	     */
	    public static <T> Map<T, Map<String, Object>> queryKeyMap(String column, Connection conn, String sql, Object... params) {
	        Map<T, Map<String, Object>> result = null;
	        try {
	            result = runner.query(conn, sql, new KeyedHandler<T>(column), params);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	 
	    /**
	     * 更新（包括UPDATE、INSERT、DELETE，返回受影响的行数）
	     * @param conn
	     * @param sql The SQL to execute.
	     * @param params
	     * @return
	     */
	    public static int update(Connection conn, String sql, Object... params) {
	        int result = 0;
	        try {
	        	startTransaction(conn);
	            result = runner.update(conn, sql, params);
	            commitTransaction(conn); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	            rollbackTransaction(conn); 
	        }
	        return result;
	    }
	    
	    /**
	     * 插入单条记录
	     * @param cls
	     * @param conn
	     * @param sql The SQL to execute.
	     * @param params
	     */
	    public static <T> void insert(Class<T> cls,Connection conn, String sql, Object... params){
	    	try {
	    		startTransaction(conn);
				runner.insert(conn, sql, new BeanHandler<T>(cls), params);
				commitTransaction(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				rollbackTransaction(conn); 
			}
	    }
	    
	    /**
	     * 批量处理多条记录
	     * @param rsh
	     * @param conn
	     * @param sql
	     * @param obj
	     */
	    public static void insertBatch(Connection conn, String sql,ResultSetHandler<?> rsh,Object[][] obj){
	    	try {
	    		startTransaction(conn);
				runner.insertBatch(conn,sql,rsh, obj); 
				commitTransaction(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				rollbackTransaction(conn); 
			}
	    }
	    
	   /**
	     * 批量处理多条记录
	     * @param conn 
	     * @param sql  The SQL to execute.
	     * @param obj 
	     * @return The number of rows updated per statement.
	     */
	    public static <T> int[] Batch(Connection conn, String sql,Object[][] obj){
	    	
	    	int[] rows = null;
	    	try {
	    		startTransaction(conn); 
				rows = runner.batch(conn,sql, obj);
				commitTransaction(conn); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				rollbackTransaction(conn); 
			}
			return rows; 
	    }
	   
}
