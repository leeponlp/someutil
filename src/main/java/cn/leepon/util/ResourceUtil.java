package cn.leepon.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;


/**
 * 资源文件工具类
 * 
 * @author leepon
 *
 */
public class ResourceUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2624292829510925381L;
	
	/**
	 * 系统默认语言环境为中文
	 */
	private final static String LANGUAGE = "zh";
	
	/**
	 * 系统默认国家为中国
	 */
	private final static String COUNTRY = "CN";
	
	private static Locale getLocale(){
		return new Locale(LANGUAGE, COUNTRY);
	}
	
	/**
	 * 通过key从资源文件读取内容
	 * 
	 * @param filename
	 *            资源文件名
	 * 
	 * @param key
	 *            索引
	 * 
	 * @return 索引对应的内容
	 */
	
	public static String getValue(String filename,String key){
		//获取默认语言环境
		Locale locale = getLocale();
		if(null==filename ||"".equals(filename)||null == key || "".equals(key)){
			return "";
		}
		ResourceBundle resourceBundle = ResourceBundle.getBundle(filename, locale);
		String value ="";
		try {
			value = resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
	
	
	/**
	 * 通过key从资源文件读取内容，并格式化
	 * 
	 * @param filename
	 *            资源文件名
	 * 
	 * @param key
	 *            索引
	 * 
	 * @param objs
	 *            格式化参数
	 * 
	 * @return 格式化后的内容
	 */
	public static String getValue(String filename,String key,Object[] objs){
		String pattern = getValue(filename, key);
		if(null == objs || objs.length==0){
			return pattern;
		}
		String value = MessageFormat.format(pattern, objs);
		return value;		
	}
	
	
	/**
	 * 读取资源文件所有的key
	 * 
	 * @param baseName
	 *            资源文件名
	 * 
	 * @return List<String>
	 *            键的集合
	 *        
	 */
	public static List<String> getKeyList(String baseName){
		
		Locale locale = getLocale();
		ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
		
		List<String> keylist = new ArrayList<String>();
		Set<String> keySet = resourceBundle.keySet();
		for (String key : keySet) {
			keylist.add(key);
		}
		
		return keylist;
		
	}
	
	/**
	 * 读取资源文件key->value键值对
	 * 
	 * @param baseName
	 *            资源文件名
	 * 
	 * @return Map<String, String>
	 *            键值对
	 *        
	 */
	
	public static Map<String, String> getKeyValueMap(String baseName){
		
		//校验文件名非空
		if (null == baseName || "".equals(baseName)) {
			return null;
		}
		
		//缓存键值对
		Map<String,String> map = new HashMap<String, String>();
		
		//获取所有的key
		List<String> keyList = getKeyList(baseName);
		
		//遍历key
		for (String key : keyList) {
			String value = getValue(baseName, key);
			map.put(key, value);
		}
		
		return map;
	}
	
	public static void main(String[] args) {
//		List<String> valueList = PropertyUtil.getKeyList("conf");
//		for (String string : valueList) {
//			System.err.println(string);
//		}
		
		Map<String,String> map = getKeyValueMap("conf");
		
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			System.err.println("key:"+key+"->value"+value);
		}	
			
				
	}

}
