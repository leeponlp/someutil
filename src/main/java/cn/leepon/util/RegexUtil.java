package cn.leepon.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class RegexUtil {
	
	private Logger logger = Logger.getLogger(RegexUtil.class);
	
    private static RegexUtil ru;
    
    public static RegexUtil init(CharSequence res,String regex){
        if (ru==null||(!res.equals(ru.res)||!regex.equals(ru.regex))) {
            //在没有实例或内容不同的时候新建一个实例
        	ru = new RegexUtil(res, regex);
        }
        return ru;
    }
     
    private CharSequence res;
    private String regex;
    private Pattern pattern;
    private Matcher matcher;
    private int size = 0;
    //所有匹配到的字符串
    private String[] list;
     
    private RegexUtil(CharSequence res,String regex){
        this.res = res;
        this.regex = regex;
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(res);
        while(matcher.find()){
            size++;
        }
        list = new String[size];
        matcher.reset();
        for (int i = 0; i < size; i++) {
            if(matcher.find())list[i] = matcher.group();
        }
    }
     
    /**是否能匹配到至少一个*/
    public boolean find(){
        return size>0;
    }
     
    /**匹配整个字符串，必须整个字符串满足正则表达式才算true*/
    public boolean match(){
        return find()&&get(0).equals(res);
    }
     
    /**获取所有匹配的字符串个数*/
    public int size(){
        return size;
    }
     
    /**获取所有匹配到的字符串*/
    public String[] getAll(){
        return list;
    }
     
    /**获取匹配到的第N个字符串*/
    public String get(int index){
        if (index<0) {
        	logger.info("匹配范围过小！！");
            return null;
        }
        if (index>=size) {
        	logger.info("匹配范围过大！！");
            return null;
        }
        return list[index];
    }
     
    /**替换所有匹配到的字符串*/
    public String replaceAll(String replacement){
        return matcher.replaceAll(replacement);
    }
     
    /**替换第一个匹配到的字符串*/
    public String replaceFirst(String replacement){
        return matcher.replaceFirst(replacement);
    }
 
    /**替换最后一个匹配到的字符串*/
    public String replaceTail(String replacement){
        return replace(size-1, replacement);
    }
     
    /**替换第N个匹配到的字符串*/
    public String replace(int index,String replacement){
        matcher.reset();
        boolean isFind = false;
        while(index>=0){
            isFind = matcher.find();
            index--;
        }
        if(isFind){
            StringBuffer sb = new StringBuffer();
            matcher.appendReplacement(sb, replacement);
            matcher.appendTail(sb);
            return sb.toString();
        }
        return (String) res;
    }
    
	
		
	/**
	 * 截取数字部分
	 * @param str
	 * @return
	 */
	public static String getNum(String str){
		return RegexUtil.init(str, "\\d+").get(0);
	}
	
	/**
	 * 截取非数字部分
	 * @param str
	 * @return
	 */
	public static String getNotNum(String str){
		return RegexUtil.init(str, "\\D+").get(0);
	}
	
	/**
	 * 判断一个字符串是否都为数字
	 * @param str
	 * @return
	 */
	public static boolean isNUM(String str){
		return RegexUtil.init(str,Validation.STR_NUM).match();
	}

	/**
	 * 校验url
	 * @param str
	 * @return
	 */
	public static boolean isURL(String str){
		return RegexUtil.init(str, Validation.URL).match();
	}
	
	/**
	 * 判断字段是否为Email
	 * @param email
	 * @return
	 */
	 public static  boolean isEmail(String email) {  
		 return RegexUtil.init(email, Validation.EMAIL).match();
	    }  

	 /**
	  * 判断是否为手机号码
	  * @param phone
	  * @return
	  */
	 public static boolean isPhone(String phone){
		 return RegexUtil.init(phone, Validation.PHONE).match();
	 }
	 
	 /**
	  * 判断是否为电话号码
	  * @param mobile
	  * @return
	  */
	 public static boolean isMobile(String mobile){
		 return RegexUtil.init(mobile, Validation.MOBILE).match();
	 }
	 
	 /**
	  * 判断输入字符都是英文
	  * @param str
	  * @return
	  */
	 public static boolean isENG(String str){
		 return RegexUtil.init(str, Validation.STR_ENG).match();
	 }
	 
	 /**
	  * 判断输入字符是否由英文和数字组成
	  * @param str
	  * @return
	  */
	 public static boolean isENG_NUM(String str){
		 return RegexUtil.init(str, Validation.STR_ENG_NUM).match();
	 }
	 
	 /**
	  * 判断输入字符是否由英文、数字及下划线组成
	  * @param str
	  * @return
	  */
	 public static boolean isENG_NUM_(String str){
		 return RegexUtil.init(str, Validation.STR_ENG_NUM_).match();
	 }
	 
	 /**
	  * 校验整数
	  * @param str
	  * @return
	  */
	 public static boolean isInteger(String str){
		 return RegexUtil.init(str, Validation.INTEGER).match();
	 }
	 
	 /**
	  * 校验正整数>=0
	  * @return
	  */
	 public static boolean isINTEGER_POSITIVE(String str){
		 return RegexUtil.init(str, Validation.INTEGER_POSITIVE).match();
	 }
	 
	 /**
	  * 校验负整数<=0
	  * @param str
	  * @return
	  */
	 public static boolean isINTEGER_NEGATIVE(String str){
		 return RegexUtil.init(str, Validation.DOUBLE_NEGATIVE).match();
	 }
	 
	 /**
	  * 校验身份证
	  * @param str
	  * @return
	  */
	 public static boolean isID_CARD(String str){
		 return RegexUtil.init(str, Validation.IDCARD).match();
	 }
	 
	 /**
	  * 校验IP地址
	  * @param str
	  * @return
	  */
	 public static boolean isIP(String str){
		 return RegexUtil.init(str, Validation.IP).match();
	 }
	 
	 /**
	  * 过滤特殊字符
	  * @param str
	  * @return
	  */
	 public static String filterStr(String str){
		 return RegexUtil.init(str, Validation.STR_SPECIAL).replaceAll("").trim();
	 }
	 
}
