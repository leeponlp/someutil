package cn.leepon.util;


import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
		
	private static Properties properties;
	private static volatile PropertyUtil instance = null;
	public static PropertyUtil getInstance() {
		if (instance == null) {
			synchronized (PropertyUtil.class) {
				if (instance == null) {
					instance = new PropertyUtil();
					// instance.init();
				}
			}
		}
		return instance;
	}


	private PropertyUtil() {
		init();
	}

	private void init() {
		try {
			properties = new Properties();
			properties
					.load(getClass().getResourceAsStream("/const.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
