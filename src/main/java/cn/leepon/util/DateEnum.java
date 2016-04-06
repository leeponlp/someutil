package cn.leepon.util;

import java.util.Date;

public enum DateEnum {
	
	    MM_dd("MM-dd"),  
	    yyyy_MM("yyyy-MM"),  
	    yyyy_MM_dd("yyyy-MM-dd"),  
	    MM_dd_HH_mm("MM-dd HH:mm"),  
	    MM_dd_HH_mm_ss("MM-dd HH:mm:ss"),  
	    yyyy_MM_dd_HH_mm("yyyy-MM-dd HH:mm"),  
	    yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss"),  
	      
	    MM_dd_EN("MM/dd"),  
	    yyyy_MM_EN("yyyy/MM"),  
	    yyyy_MM_dd_EN("yyyy/MM/dd"),  
	    MM_dd_HH_mm_EN("MM/dd HH:mm"),  
	    MM_dd_HH_mm_ss_EN("MM/dd HH:mm:ss"),  
	    yyyy_MM_dd_HH_mm_EN("yyyy/MM/dd HH:mm"),  
	    yyyy_MM_dd_HH_mm_ss_EN("yyyy/MM/dd HH:mm:ss"),  
	      
	    MM_dd_CN("MM月dd日"),  
	    yyyy_MM_CN("yyyy年MM月"),  
	    yyyy_MM_dd_CN("yyyy年MM月dd日"),  
	    MM_dd_HH_mm_CN("MM月dd日 HH:mm"),  
	    MM_dd_HH_mm_ss_CN("MM月dd日 HH:mm:ss"),  
	    yyyy_MM_dd_HH_mm_CN("yyyy年MM月dd日 HH:mm"),  
	    yyyy_MM_dd_HH_mm_ss_CN("yyyy年MM月dd日 HH:mm:ss"),  
	      
	    HH_mm("HH:mm"),  
	    HH_mm_ss("HH:mm:ss");  
	      
	      
	    private String value;  
	      
	    private DateEnum(String value) {  
	        this.value = value;  
	    }  
	      
	    public String getValue() {  
	        return value;  
	    } 
	    
	    public static void main(String[] args) {
			String format = DateUtil.format(new Date(), DateEnum.HH_mm_ss.value);
			System.err.println(format);
		}

}
