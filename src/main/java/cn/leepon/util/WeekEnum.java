package cn.leepon.util;

import java.util.Calendar;
import java.util.Date;

public enum WeekEnum {
	

    MONDAY("星期一", "Monday", "Mon.", 1),  
    TUESDAY("星期二", "Tuesday", "Tues.", 2),  
    WEDNESDAY("星期三", "Wednesday", "Wed.", 3),  
    THURSDAY("星期四", "Thursday", "Thur.", 4),  
    FRIDAY("星期五", "Friday", "Fri.", 5),  
    SATURDAY("星期六", "Saturday", "Sat.", 6),  
    SUNDAY("星期日", "Sunday", "Sun.", 7);  
      
    private final String name_cn;  
    private final String name_en;  
    private final String name_enShort;  
    private final int number;  
      
    private WeekEnum(String name_cn, String name_en, String name_enShort, int number) {  
        this.name_cn = name_cn;  
        this.name_en = name_en;  
        this.name_enShort = name_enShort;  
        this.number = number;  
    }  
      
    public String getChineseName() {  
        return name_cn;  
    }  
  
    public String getName() {  
        return name_en;  
    }  
  
    public String getShortName() {  
        return name_enShort;  
    }  
  
    public int getNumber() {  
        return number;  
    } 
    
    
    /** 
     * 获取日期的星期。失败返回null。 
     * @param date 日期 
     * @return 星期 
     */  
    public static WeekEnum getWeek(Date date) { 
    	
        WeekEnum week = null;  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK);  
        switch (weekNumber) {  
        case 0:  
            week = WeekEnum.SUNDAY;  
            break;  
        case 1:  
            week = WeekEnum.MONDAY;  
            break;  
        case 2:  
            week = WeekEnum.TUESDAY;  
            break;  
        case 3:  
            week = WeekEnum.WEDNESDAY;  
            break;  
        case 4:  
            week = WeekEnum.THURSDAY;  
            break;  
        case 5:  
            week = WeekEnum.FRIDAY;  
            break;  
        case 6:  
            week = WeekEnum.SATURDAY;  
            break;  
        }  
        return week;  
    }
    
    
    public static void main(String[] args) {
		WeekEnum week = getWeek(DateUtil.parse("2015-01-13"));
		System.err.println(week.name_cn);
	}

}
