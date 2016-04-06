package cn.leepon.util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * 使用DBCP数据源
 * @author leepon
 *
 */
public class JdbcUtil {
	
	//驱动
	private final static String driver = "com.mysql.jdbc.Driver";
	
	//连接配置
	private final static String urlConf = "?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
	
	//校验连接
	private final static String validation ="select 1";
	
	public static BasicDataSource getDataSource(String url,String username,String password) {
		//数据源对象
		BasicDataSource ds = new BasicDataSource();
		//赋值
		ds.setDriverClassName(driver);
		ds.setUrl(url+urlConf);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setMaxActive(60);
		ds.setMaxWait(10000);
		ds.setMaxIdle(10);
		ds.setInitialSize(10);
		ds.setRemoveAbandoned(true);
		ds.setDefaultAutoCommit(true);
		ds.setDefaultReadOnly(false);
		ds.setTestOnBorrow(true);
		ds.setValidationQuery(validation);
		
		return ds;
	}
	
	
	//绑定本地线程
	public static ThreadLocal<Connection> container = new ThreadLocal<Connection>();
	
	
	//开启事务
	public static void startTransaction(){
		Connection conn = null;
		try{
			conn = container.get();
			conn.setAutoCommit(false);
			container.set(conn);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	//提交事务
	public static void commitTransaction(){
		Connection conn = container.get();
		if(conn == null){
			return;
		}
		try {
			conn.commit();
			container.remove();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			release(conn, null, null);
		}
	}
	
	//回滚事务
	public static void rollbackTransaction(){
		Connection conn = container.get();
		if(conn == null){
			return;
		}
		try {
			conn.rollback();
			container.remove();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			release(conn, null, null);
		}
	}
	
	//获取连接
	public static Connection getConnection(String url,String username,String password){
		try {
			return getDataSource(url, username, password).getConnection(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//释放
	public static void release(Connection conn,Statement st,ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				rs = null;
			}
		}
		if(st != null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				st = null;
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				conn = null;
			}
		}
	}	
	
	
	 // 关闭数据库连接
    public static void closeConn(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}



















