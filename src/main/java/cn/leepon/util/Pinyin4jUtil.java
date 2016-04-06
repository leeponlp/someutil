package cn.leepon.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin4jUtil {
	
	/** 
     * 汉字转换位汉语拼音首字母，英文字符不变，特殊字符丢失 支持多音字，生成方式如（长沙市长:cssc,zssz,zssc,cssz） 
     *  
     * @param chines 
     *            汉字 
     * @return 拼音 
     */  
    public static String converterToFirstSpell(String chines) {  
        StringBuffer pinyinName = new StringBuffer();  
        char[] nameChar = chines.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < nameChar.length; i++) {  
            if (nameChar[i] > 128) {  
                try {  
                    // 取得当前汉字的所有全拼  
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(  
                            nameChar[i], defaultFormat);  
                    if (strs != null) {  
                        for (int j = 0; j < strs.length; j++) {  
                            // 取首字母  
                            pinyinName.append(strs[j].charAt(0));  
                            if (j != strs.length - 1) {  
                                pinyinName.append(",");  
                            }  
                        }  
                    }  
                    // else {  
                    // pinyinName.append(nameChar[i]);  
                    // }  
                } catch (BadHanyuPinyinOutputFormatCombination e) {  
                    e.printStackTrace();  
                }  
            } else {  
                pinyinName.append(nameChar[i]);  
            }  
            pinyinName.append(" ");  
        }  
        // return pinyinName.toString();  
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));  
    }  
  
    /** 
     * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失 
     * 支持多音字，生成方式如（重当参:zhongdangcen,zhongdangcan,chongdangcen 
     * ,chongdangshen,zhongdangshen,chongdangcan） 
     *  
     * @param chines 
     *            汉字 
     * @return 拼音 
     */  
    public static String converterToSpell(String chines) {  
        StringBuffer pinyinName = new StringBuffer();  
        char[] nameChar = chines.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < nameChar.length; i++) {  
            if (nameChar[i] > 128) {  
                try {  
                    // 取得当前汉字的所有全拼  
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(  
                            nameChar[i], defaultFormat);  
                    if (strs != null) {  
                        for (int j = 0; j < strs.length; j++) {  
                            pinyinName.append(strs[j]);  
                            if (j != strs.length - 1) {  
                                pinyinName.append(",");  
                            }  
                        }  
                    }  
                } catch (BadHanyuPinyinOutputFormatCombination e) {  
                    e.printStackTrace();  
                }  
            } else {  
                pinyinName.append(nameChar[i]);  
            }  
            pinyinName.append(" ");  
        }  
        // return pinyinName.toString();  
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));  
    }  
  
    /** 
     * 去除多音字重复数据 
     *  
     * @param theStr 
     * @return 
     */  
    private static List<Map<String, Integer>> discountTheChinese(String theStr) {  
        // 去除重复拼音后的拼音列表  
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();  
        // 用于处理每个字的多音字，去掉重复  
        Map<String, Integer> onlyOne = null;  
        String[] firsts = theStr.split(" ");  
        // 读出每个汉字的拼音  
        for (String str : firsts) {  
            onlyOne = new HashMap<String, Integer>();  
            String[] china = str.split(",");  
            // 多音字处理  
            for (String s : china) {  
                Integer count = onlyOne.get(s);  
                if (count == null) {  
                    onlyOne.put(s, new Integer(1));  
                } else {  
                    onlyOne.remove(s);  
                    count++;  
                    onlyOne.put(s, count);  
                }  
            }  
            mapList.add(onlyOne);  
        }  
        return mapList;  
    }  
  
    /** 
     * 解析并组合拼音，对象合并方案(推荐使用) 
     *  
     * @return 
     */  
    private static String parseTheChineseByObject(  
            List<Map<String, Integer>> list) {  
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据  
        // 遍历每一组集合  
        for (int i = 0; i < list.size(); i++) {  
            // 每一组集合与上一次组合的Map  
            Map<String, Integer> temp = new HashMap<String, Integer>();  
            // 第一次循环，first为空  
            if (first != null) {  
                // 取出上次组合与此次集合的字符，并保存  
                for (String s : first.keySet()) {  
                    for (String s1 : list.get(i).keySet()) {  
                        String str = s + s1;  
                        temp.put(str, 1);  
                    }  
                }  
                // 清理上一次组合数据  
                if (temp != null && temp.size() > 0) {  
                    first.clear();  
                }  
            } else {  
                for (String s : list.get(i).keySet()) {  
                    String str = s;  
                    temp.put(str, 1);  
                }  
            }  
            // 保存组合数据以便下次循环使用  
            if (temp != null && temp.size() > 0) {  
                first = temp;  
            }  
        }  
        String returnStr = "";  
        if (first != null) {  
            // 遍历取出组合字符串  
            for (String str : first.keySet()) {  
                returnStr += (str + ",");  
            }  
        }  
        if (returnStr.length() > 0) {  
            returnStr = returnStr.substring(0, returnStr.length() - 1);  
        }  
        return returnStr;  
    }  
    
    
    /**
     * 将汉字转换为全拼
     * 
     * @param src
     * @return String
     */
    public static String getPinYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        // System.out.println(t1.length);
        String[] t2 = new String[t1.length];
        // System.out.println(t2.length);
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断能否为汉字字符
                // System.out.println(t1[i]);
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
                    t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
                } else {
                    // 如果不是汉字字符，间接取出字符并连接到字符串t4后
                    t4 += Character.toString(t1[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return t4;
    }
 
    /**
     * 提取每个汉字的首字母
     * 
     * @param str
     * @return String
     */
    public static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }
 
    /**
     * 将字符串转换成ASCII码
     * 
     * @param cnStr
     * @return String
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        // 将字符串转换成字节序列
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            // System.out.println(Integer.toHexString(bGBK[i] & 0xff));
            // 将每个字符转换成ASCII码
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }
    
    
    /**
     * 将字符串转换成拼音数组
     * 
     * @param src
     * @return
     */
    public static String[] stringToPinyin(String src) {
        return stringToPinyin(src, false, null);
    }
    /**
     * 将字符串转换成拼音数组
     * 
     * @param src
     * @return
     */
    public static String[] stringToPinyin(String src,String separator) {
        return stringToPinyin(src, true, separator);
    }
 
    /**
     * 将字符串转换成拼音数组
     * 
     * @param src
     * @param isPolyphone
     *            是否查出多音字的所有拼音
     * @param separator
     *            多音字拼音之间的分隔符
     * @return
     */
    public static String[] stringToPinyin(String src, boolean isPolyphone,
            String separator) {
        // 判断字符串是否为空
        if ("".equals(src) || null == src) {
            return null;
        }
        char[] srcChar = src.toCharArray();
        int srcCount = srcChar.length;
        String[] srcStr = new String[srcCount];
 
        for (int i = 0; i < srcCount; i++) {
            srcStr[i] = charToPinyin(srcChar[i], isPolyphone, separator);
        }
        return srcStr;
    }
 
    /**
     * 将单个字符转换成拼音
     * 
     * @param src
     * @return
     */
    public static String charToPinyin(char src, boolean isPolyphone,
            String separator) {
        // 创建汉语拼音处理类
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出设置，大小写，音标方式
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
 
        StringBuffer tempPinying = new StringBuffer();
         
 
        // 如果是中文
        if (src > 128) {
            try {
                // 转换得出结果
                String[] strs = PinyinHelper.toHanyuPinyinStringArray(src,
                        defaultFormat);
                 
                         
                // 是否查出多音字，默认是查出多音字的第一个字符
                if (isPolyphone && null != separator) {
                    for (int i = 0; i < strs.length; i++) {
                        tempPinying.append(strs[i]);
                        if (strs.length != (i + 1)) {
                            // 多音字之间用特殊符号间隔起来
                            tempPinying.append(separator);
                        }
                    }
                } else {
                    tempPinying.append(strs[0]);
                }
 
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            tempPinying.append(src);
        }
 
        return tempPinying.toString();
 
    }
 
     
    public static String hanziToPinyin(String hanzi){
        return hanziToPinyin(hanzi," ");
    }
    /**
     * 将汉字转换成拼音
     * @param hanzi
     * @param separator
     * @return
     */
    @SuppressWarnings("deprecation")
	public static String hanziToPinyin(String hanzi,String separator){
            // 创建汉语拼音处理类
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            // 输出设置，大小写，音标方式
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
             
            String pinyingStr="";
            try {
                pinyingStr=PinyinHelper.toHanyuPinyinString(hanzi, defaultFormat, separator);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }   
            return pinyingStr;
    }
    /**
     * 将字符串数组转换成字符串
     * @param str 
     * @param separator 各个字符串之间的分隔符
     * @return
     */
    public static String stringArrayToString(String[] str, String separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            sb.append(str[i]);
            if (str.length != (i + 1)) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
    /**
     * 简单的将各个字符数组之间连接起来
     * @param str
     * @return
     */
    public  static String stringArrayToString(String[] str){
        return stringArrayToString(str,"");
    }
    /**
     * 将字符数组转换成字符串
     * @param str 
     * @param separator 各个字符串之间的分隔符
     * @return
     */
    public static String charArrayToString(char[] ch, String separator) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            sb.append(ch[i]);
            if (ch.length != (i + 1)) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
     
    /**
     * 将字符数组转换成字符串
     * @param str 
     * @return
     */
    public static String charArrayToString(char[] ch) {
        return charArrayToString(ch," ");
    }
 
    /**
     * 取汉字的首字母
     * @param src
     * @param isCapital 是否是大写
     * @return
     */
    public static char[]  getHeadByChar(char src,boolean isCapital){
        //如果不是汉字直接返回
        if (src <= 128) {
            return new char[]{src};
        }
        //获取所有的拼音
        String []pinyingStr=PinyinHelper.toHanyuPinyinStringArray(src);
        //创建返回对象
        int polyphoneSize=pinyingStr.length;
        char [] headChars=new char[polyphoneSize];
        int i=0;
        //截取首字符
        for(String s:pinyingStr){
            char headChar=s.charAt(0);
            //首字母是否大写，默认是小写
            if(isCapital){
                headChars[i]=Character.toUpperCase(headChar);
             }else{
                headChars[i]=headChar;
             }
            i++;
        }
         
        return headChars;
    }
    /**
     * 取汉字的首字母(默认是大写)
     * @param src
     * @return
     */
    public static char[]  getHeadByChar(char src){
        return getHeadByChar(src,true);
    }
    /**
     * 查找字符串首字母
     * @param src 
     * @return
     */
    public  static String[] getHeadByString(String src){
        return getHeadByString( src, true);
    }
    /**
     * 查找字符串首字母
     * @param src 
     * @param isCapital 是否大写
     * @return
     */
    public  static String[] getHeadByString(String src,boolean isCapital){
        return getHeadByString( src, isCapital,null);
    }
    /**
     * 查找字符串首字母
     * @param src 
     * @param isCapital 是否大写
     * @param separator 分隔符
     * @return
     */
    public  static String[] getHeadByString(String src,boolean isCapital,String separator){
        char[]chars=src.toCharArray();
        String[] headString=new String[chars.length];
        int i=0;
        for(char ch:chars){
             
            char[]chs=getHeadByChar(ch,isCapital);
            StringBuffer sb=new StringBuffer();
            if(null!=separator){
                int j=1;
                 
                for(char ch1:chs){
                    sb.append(ch1);
                    if(j!=chs.length){
                        sb.append(separator);
                    }
                    j++;
                }
            }else{
                sb.append(chs[0]);
            }
            headString[i]=sb.toString();
            i++;
        }
        return headString;
    }
    
    /**
     * 汉子转拼音
     * @param str
     * @return
     */
    public static String chineseToPinyin(String str){
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
         
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String[] pinyins;
            try {
                pinyins = PinyinHelper.toHanyuPinyinStringArray(ch,format);
                if (pinyins != null){
                    result.append(pinyins[0]);
                } else {
                    result.append(ch);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
    
    
    public static void main(String[] args) {
    	String str = "长沙市长";  
        
        String pinyin = Pinyin4jUtil.converterToSpell(str);  
        System.out.println(str+" pin yin ："+pinyin);  
          
        pinyin = Pinyin4jUtil.converterToFirstSpell(str);  
        System.out.println(str+" short pin yin ："+pinyin);  
        
        
        pinyin = Pinyin4jUtil.getPinYin(str);
        System.err.println(pinyin);
        pinyin = Pinyin4jUtil.getPinYinHeadChar(str);
        System.err.println(pinyin);
        
        pinyin = Pinyin4jUtil.chineseToPinyin(str);
        System.err.println(pinyin);
	}

}
