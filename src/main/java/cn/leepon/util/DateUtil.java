package cn.leepon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期格式化工具类
 * @author leepon
 *
 */
public class DateUtil {

	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public final static String FORMAT_SHORT = "yyyy-MM-dd";
	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public final static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public final static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 中文简写 如：2010年12月01日
	 */
	public final static String FORMAT_SHORT_CN = "yyyy年MM月dd";
	/**
	 * 中文全称 如：2010年12月01日 23时15分06秒
	 */
	public final static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
	/**
	 * 精确到毫秒的完整中文时间
	 */
	public final static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

	/**
	 * 获得默认的 date pattern
	 */
	public static String getPattern() {
		return FORMAT_SHORT;
	}

	/**
	 * 根据预设格式返回当前日期
	 * 
	 * @return
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * 根据用户格式返回当前日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getNow(String format) {
		return format(new Date(), format);
	}

	/**
	 * 使用预设格式格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, getPattern());
	}

	/**
	 * 使用用户格式格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if(null ==pattern){
			pattern = getPattern();
		}
		if (date != null || !StringUtils.isEmpty(pattern)) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return returnValue;
	}

	/**
	 * 使用预设格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getPattern());
	}

	/**
	 * 使用用户格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 在日期上增加数个整月
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的月数
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加天数
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的天数
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * 按默认格式的字符串距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 按用户格式字符串距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}
	
	/**
	 * 将日期格式的字符串转换为长整型
	 * 
	 * @param date
	 *         2015-9-13
	 *         
	 * @param pattern
	 *         格式化类型
	 *         
	 * @return
	 *         时间戳
	 */
	public static long getTimestamp(String datestr, String pattern) {
		Date date = parse(datestr, pattern);
		long time = date.getTime(); 
		return time;
		
	}

	/**
	 * 将时间戳转换为日期格式的字符串
	 * 
	 * @param timestamp
	 *          时间戳
	 * 
	 * @param pattern
	 *          格式化类型
	 *          
	 * @return
	 *          日期格式的字符串
	 */
	public static String getDate(long timestamp, String pattern) {
			Date date = new Date(timestamp);
			return format(date,pattern);
	}

	/**
	 * 获取当前时间点的时间戳
	 * 精确到毫秒级
	 * 
	 * @return
	 */
	public static long curTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 秒数格式化
	 * 
	 * @param time
	 * @return
	 */

	public static String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	private static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}
	
	/**
	 * 今日
	 * 
	 * @param pattern
	 *         日期格式化类型
	 * @return
	 *         当天的字符串日期
	 */
	public static String getToday(String pattern){
		Date date = new Date();
		return format(date, pattern);
	}
	
	 /**
	  * 昨日
	  * @return
	  */
    public String getYesterday() {
        Date today = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(today);
        ca.add(Calendar.DAY_OF_YEAR, -1);
        return format(ca.getTime());
    }
	
	/**
	 * 获取今天以前（负）、以后（正）的第n天的日期
	 * @param n 
	 *        今天以前的第n天，为负整数，以后为正整数
	 *        
	 * @param pattern
	 *        
	 * @return
	 */
	public static String getAgoBackDate(int n, String pattern) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, n);
		date = cal.getTime();
        return format(date,pattern);
	}
	
	/**
	 * 获取今天以前（负）、以后（正）的第n天的日期,格式：YYYY-MM-dd
	 * @param n 
	 *        今天以前的第n天，为负整数，以后为正整数
	 *        
	 * @param pattern
	 *        
	 * @return
	 */
	public static String getAgoBackDate(int n){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, n);
		date = cal.getTime();
        return format(date);
	}
	
	
	/**
	 * 判断是否闰年
	 * 
	 * @param year
	 * 
	 * @return
	 */
    public static boolean isLeapYear(int year) {
        return new GregorianCalendar().isLeapYear(year);
    }
    
    /**
     * 获取Date中的秒
     * @param d
     * @return
     */
    public static int getSecond(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.SECOND);
    }
     
    
    /**
     * 获取Date中的分钟
     * @param d
     * @return
     */
    public static int getMin(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MINUTE);
    }
     
    /**
     * 获取Date中的小时(24小时)
     * @param d
     * @return
     */
    public static int getHour(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.HOUR_OF_DAY);
    }
     
   
    /**
     * 获取xxxx-xx-xx的日
     * @param d
     * @return
     */
    public static int getDay(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.DAY_OF_MONTH);
    }
     
    /**
     * 获取月份，1-12月
     * @param d
     * @return
     */
    public static int getMonth(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MONTH) + 1;
    }
     
    /**
     * 获取19xx,20xx形式的年
     * @param d
     * @return
     */
    public static int getYear(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.YEAR);
    }
    
     
    
    /**
     * 获取输入日期所在月份的最后一天
     * 
     * @param date
     * 
     * @param pattern
     * 
     * @return
     */
    public static String getLastDayOfMonth(Date date,String pattern){
    	if(null == pattern){
    		pattern = getPattern();
    	}
    	Calendar calendar = Calendar.getInstance(); 
    	calendar.setTime(date); 
    	int maxday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
    	calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), maxday, 23, 59, 59);
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	return sdf.format(calendar.getTime());
    	
    }    
    
    /**
     * 获取n月前的第一天
     * @param n
     * @return
     */
    public String getLastMonFirstDay(int n) {
        Calendar cal=Calendar.getInstance();
        Date now=new Date();
        cal.setTime(now);
        cal.add(Calendar.MONTH, -n);
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        String resultTime=year+"-"+month+"-01";
        return format(parse(resultTime));
    }
    
    /**
     * 获取上个月第一天
     * @return
     */
    public static String getLastMonFirstDay() {
        Calendar cal=Calendar.getInstance();
        Date now=new Date();
        cal.setTime(now);
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        String resultTime=year+"-"+month+"-01";
        return format(parse(resultTime));
    }
    
    /**
     * 本月第一天
     * @return
     */
    public static String getThisMonFirstDay() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 2;
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String months = "";
        if (month > 1) {
            month--;
        } else {
            year--;
            month = 12;
        }
        if (!(String.valueOf(month).length() > 1)) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }
        String firstDay = "" + year + "-" + months + "-01";
        return format(parse(firstDay));
    }
  
    /**
     * 高效比对两个时间戳（毫秒）是否是一天
     */
    //=========================================================================
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    
    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }

    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }
    
    //=========================================================================
    
	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		boolean b = isSameDayOfMillis(currentTimeMillis, currentTimeMillis+3);
		System.err.println(b);
//		System.err.println(getToday(null));
//		String day = getLastDayOfMonth(new Date(), null);
//		System.err.println(day);
		//System.err.println(getLastMonFirstDay());
	}

}
