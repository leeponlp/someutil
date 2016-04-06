package cn.leepon.util;

import org.apache.log4j.Logger;

public class ParseUtil {
	
	private static Logger logger = Logger.getLogger(ParseUtil.class);
	
	public static int parseInt(String s){
		
		int value =0;
		if (null !=s && ("").equals(s.trim())) {
			try {
				value = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				//e.printStackTrace();
				logger.info("格式化异常："+e.getMessage());
			}
		}
		return value;
	}
	
	public static boolean parseBoolean(String s){
		boolean value =false;
		if (null !=s && ("").equals(s.trim())) {
			try {
				value = Boolean.parseBoolean(s);
			} catch (NumberFormatException e) {
				//e.printStackTrace();
				logger.info("格式化异常："+e.getMessage());
			}
		}
		return value;
	}
	
	public static double parseDouble(String s){
		double d =0.00;
		if (null !=s && ("").equals(s.trim())) {
			try {
				d = Double.parseDouble(s);
			} catch (NumberFormatException e) {
				//e.printStackTrace();
				logger.info("格式化异常："+e.getMessage());
			}
		}
		return d;
	}
	
	public static float parseFloat(String s){
		float f = 0;
		if (null !=s && ("").equals(s.trim())) {
			try {
				f = Float.parseFloat(s);
			} catch (NumberFormatException e) {
				//e.printStackTrace();
				logger.info("格式化异常："+e.getMessage());
			}
		}
		return f;
	}

}
