package cn.leepon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
 
/**
 * HTTP工具类
 * @author leepon
 *
 */
public class HttpClientUtil {
	
	static Logger logger = Logger.getLogger(HttpClientUtil.class);
	
	// 设置请求类型常量
	public static final int METHOD_GET = 1;
	
	public static final int METHOD_POST = 2;
	
	private static HttpClient httpClient = null;
	
	/**
	 * 创建线程安全的客户端对象
	 * 
	 * @return
	 *       httpClient
	 */
	public synchronized static HttpClient getHttpClient(){
		 if (httpClient == null) {
	            httpClient = new DefaultHttpClient();
	        }
	        return httpClient;
	}
	
	
	/**
	 * POST方法提交JSON串
	 * @param url
	 * @param json
	 * @return
	 */
	public static String executeByPOST(String url,JSONObject json){
		
		String result = "";
		//获取客户端对象
		HttpClient client = getHttpClient();
		
		HttpPost post = new HttpPost(url);
		try {
			StringEntity s = new StringEntity(json.toString());
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			result = EntityUtils.toString(res.getEntity());
		}catch(Exception e){
			logger.info(e.getMessage());
		}
			
		return result;		
	}
	
	/**
     * POST方式调用
     *
     * @param url
     * @param params 参数为NameValuePair键值对对象
     * @return 响应字符串
     * @throws java.io.UnsupportedEncodingException
     */
    public static String executeByPOST(String url, List<NameValuePair> params) {
    	
    	//创建客户端对象
        HttpClient httpclient = getHttpClient();
 
        HttpPost post = new HttpPost(url); 
 
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            if (params != null) {
            	//创建请求实体
                post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
            }
            //执行请求实体获取返回对象
            responseJson = httpclient.execute(post, responseHandler);
            logger.info("HttpClient POST请求结果：" + responseJson);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            logger.info("HttpClient POST请求异常：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return responseJson;
    }
    
    
    /**
     * Get方式请求
     *
     * @param url    带参数占位符的URL，例：http:///xxx/user/center.aspx?_action=GetSimpleUserInfo&codes={0}&email={1}
     * @param params 参数值数组，需要与url中占位符顺序对应
     * @return 响应字符串
     * @throws java.io.UnsupportedEncodingException
     */
        public static String executeByGET(String url, Object[] params) {
        	
        //创建客户端对象
        HttpClient httpclient = getHttpClient();
        //格式化url
        String messages = MessageFormat.format(url, params);
 
        HttpGet get = new HttpGet(messages);
 
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
        	//执行请求实体获取返回对象
            responseJson = httpclient.execute(get, responseHandler);
            logger.info("HttpClient GET请求结果：" + responseJson);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            logger.info("HttpClient GET请求异常：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("HttpClient GET请求异常：" + e.getMessage());
        } finally {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
        }
        return responseJson;
    }
        
        
        /**
         * @param url
         * @return
         */
        public static String executeByGET(String url) {
            HttpClient httpclient = getHttpClient();
     
            HttpGet get = new HttpGet(url);
     
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseJson = null;
            try {
                responseJson = httpclient.execute(get, responseHandler);
                logger.info("HttpClient GET请求结果：" + responseJson);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                logger.info("HttpClient GET请求异常：" + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("HttpClient GET请求异常：" + e.getMessage());
            } finally {
                httpclient.getConnectionManager().closeExpiredConnections();
                httpclient.getConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
            }
            return responseJson;
        }
        
        /**
         * 字符串转Json
         * @param str
         * @return
         */
        public static JSONObject toJson(String str){
        	if(!StringUtils.isEmpty(str)){
        		return JSONObject.parseObject(str);
        	}
			return null;
        }
        
        
       /**
         * 
         * @param url
         * 
         * @param params
         *          参数为NameValuePair键值对对象
         * @param method
         *          访问的方法，如果是1，代表GET方法；如果是2，代表POST方法
         * @return
         * @throws Exception
         */
    	public static HttpEntity getEntity(String url, List<NameValuePair> params, int method) throws Exception {
    		// 创建HttpEntity的引用
    		HttpEntity responseEntity = null;
    		// 创建客户端对象
    		HttpClient client = new DefaultHttpClient();
    		// 创建请求实体
    		HttpUriRequest request = null;
    		// 判断方法时GET还是POST,并且创建请求对象
    		switch (method) {
    		case 1:
    			StringBuilder sb = new StringBuilder(url);
    			// 循环加入请求参数
    			if (params != null && !params.isEmpty()) {
    				sb.append("?");
    				for (NameValuePair pair : params) {
    					sb.append(pair.getName());
    					sb.append("=");
    					sb.append(pair.getValue());
    					sb.append("&");
    				}
    				sb.deleteCharAt(sb.length() - 1);
    			}
    			request = new HttpGet(sb.toString());
    			break;
    		case 2:
    			request = new HttpPost(url);
    			if (params != null && !params.isEmpty()) {
    				// 创建请求实体
    				((HttpResponse) request).setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
    			}
    			break;
    		}
    		// 执行请求，获取相应对象
    		HttpResponse response = client.execute(request);
    		// 获取响应实体
    		if (response.getStatusLine().getStatusCode() == 200) {
    			responseEntity = response.getEntity();
    		}
    		return responseEntity;
    	}
    	
    	/**
    	 * @param url
    	 * 
         * @param params
         *          参数为NameValuePair键值对对象
         * @param method
         *          访问的方法，如果是1，代表GET方法；如果是2，代表POST方法
         * @return
         * @throws Exception
    	 */
    	public static String parseEntity(String url, List<NameValuePair> params, int method){
    		
    		HttpEntity entity =null;
    		BufferedReader br = null;
    		StringBuffer sb = new StringBuffer("");
    		try {
				entity = getEntity(url, params, method);
				if(null != entity){
					InputStream stream = entity.getContent();
					br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
					String line = "";
					String NL = System.getProperty("line.separator");
					while ((line = br.readLine()) != null) {
						sb.append(line + NL);
					}
					br.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sb.toString();  
    		
    	}
    	
    	
    	public static void main(String[] args) {
			String executeByGET = executeByGET("http://api.release.dev.anhouse.com.cn/hft/1.0/home_city_list");
			System.err.println(executeByGET); 
		}
}
