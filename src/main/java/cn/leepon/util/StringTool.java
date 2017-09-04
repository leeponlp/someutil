package cn.leepon.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串处理工具类
 * @author leepon
 *
 */

public class StringTool {
	
	private final static String EMPTY = "";

	/** 
	 * 清除search字符  
	 * 
	 */
	public static String clear(String src, String search) {
		if(src == null) return null;
		return StringUtils.replace(src, search,EMPTY);
	}
	
	/** 
	 * 清除空格" "  
	 *
	 **/
	public static String clearBlank(String src){
		if(src == null) return null;
		return clear(src, " ");
	}
	
	/** 
	 * 清除字符集  
	 *
	 */
	public static String clear(String src, String... searchs) {
		if(src == null) return null;
		if(searchs == null || searchs.length <= 0) return src;
		for(String s : searchs){
			src = clear(src, s);
		}
		return src;
	}
	
	/** 
	 * 先Trim
	 * 再清除search字符  
	 * 
	 */
	public static String trimClear(String src, String search) {
		if(src == null) return null;
		src = StringUtils.trim(src);
		return clear(src, search);
	}
	
	/** 
	 * 替换search字符
	 * 
	 */
	public static String replace(String src, String search, String rep) {
		if(rep == null) rep = EMPTY;
		if(src == null || search == null) return src;
		return StringUtils.replace(src, search, rep);
	}
	
	 /**
     * 去掉字符串中的指定字符.
     * 
     * @param src
     *            输入字符串
     * @param chars
     *            要去除的字符
     * 
     * 例：trim("  abcdxxxx", ' ', 'x') ==> "abcd"
     * 
     * @return 输出字符串
     * 
     */
    public static String trim(String src, char... chars) {
        if (src == null) {
            return EMPTY;
        }
        int count = src.length();
        int len = src.length();
        int st = 0;
 
        while (st < len) {
            if (src.charAt(st) < ' ') {//非打印字符
                st++;
            } else {
                boolean found = false;
                for (char c : chars) {
                    if (src.charAt(st) == c) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    st++;
                } else {
                    break;
                }
            }
        }
        while (st < len) {
            if (src.charAt(len - 1) < ' ') {
                len--;
            } else {
                boolean found = false;
                for (char c : chars) {
                    if (src.charAt(len - 1) == c) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    len--;
                } else {
                    break;
                }
            }
        }
        return ((st > 0) || (len < count)) ? src.substring(st, len) : src;
    }
 
    /**
     * 去掉字符串左边的指定字符.
     * 
     * @param src
     *            输入字符串
     * @param chars
     *            要去除的字符
     * 
     * 例：trimLeft("  abcdxxxx", ' ', 'x') ==> "abcdxxxx"
     * 
     * @return 输出字符串
     * 
     */
    public static String trimLeft(String src, char... chars) {
        if (src == null) {
            return EMPTY;
        }
        int count = src.length();
        int len = src.length();
        int st = 0;
 
        while (st < len) {
            if (src.charAt(st) < ' ') {//非打印字符
                st++;
            } else {
                boolean found = false;
                for (char c : chars) {
                    if (src.charAt(st) == c) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    st++;
                } else {
                    break;
                }
            }
        }
        return ((st > 0) || (len < count)) ? src.substring(st, len) : src;
    }
 
    /**
     * 去掉字符串右边的指定字符.
     * 
     * @param src
     *            输入字符串
     * @param chars
     *            要去除的字符
     * 
     * 例：trimRight("  abcdxxxx", ' ', 'x') ==> "  abcd"
     * 
     * @return 输出字符串
     * 
     */
    public static String trimRight(String src, char... chars) {
        if (src == null) {
            return EMPTY;
        }
        int count = src.length();
        int len = src.length();
        int st = 0;
 
        while (st < len) {
            if (src.charAt(len - 1) < ' ') {
                len--;
            } else {
                boolean found = false;
                for (char c : chars) {
                    if (src.charAt(len - 1) == c) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    len--;
                } else {
                    break;
                }
            }
        }
        return ((st > 0) || (len < count)) ? src.substring(st, len) : src;
    }
    
    /**
     * 判断字符串是空.
     * 
     * @param inputStr
     *            输入字符串
     * 
     * @return boolean
     * 
     */
    public static boolean isEmpty(String inputStr) {
        return null == inputStr || EMPTY.equals(inputStr);
    }
 
    /**
     * 判断字符串是空.
     * 
     * @param inputStr
     *            输入字符串
     * 
     * @return boolean
     * 
     */
    public static boolean isNotEmpty(String inputStr) {
        return !isEmpty(inputStr);
    }
    
    /**
     * 字符串左填充.
     * 
     * @param src
     * @param padchar
     * @param len
     * @return
     */
    public static String padLeft(String src, String padchar, int len) {
        if (isEmpty(padchar)) {
            return src;
        }
        String out = src;
        if (isEmpty(out)) {
            out = EMPTY;
        }
        if (len <= 0 || out.length() >= len) {
            return out;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(padchar);
        }
        sb.append(out);
        out = sb.substring(sb.length() - len);
        return out;
    }
 
    /**
     * 字符串右填充.
     * 
     * @param src
     * @param padchar
     * @param len
     * @return
     */
    public static String padRight(String src, String padchar, int len) {
        if (isEmpty(padchar)) {
            return src;
        }
        String out = src;
        if (isEmpty(out)) {
            out = EMPTY;
        }
        if (len <= 0 || out.length() >= len) {
            return out;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(out);
        for (int i = 0; i < len; i++) {
            sb.append(padchar);
        }
        out = sb.substring(0, len - 1);
        return out;
    }
 
    /**
     * 
     * 截取字符串指定开始位置的指定长度的子串，null作为""处理.
     * "abcde", 2, 1 ==〉"c"
     * "abcde", 2, 4 ==〉"cde"
     * "abcde", 20, 1 ==〉""
     * 
     * @param src   原字符串
     * @param startindex    开始位置，第一个字符为0
     * @param length    截取的长度
     * @return
     */
    public static String mid(String src, int startindex, int length) {
        if (isEmpty(src))
            return EMPTY;
 
        if (startindex >= src.length())
            return EMPTY;
        int len = src.length() - startindex;
        if (length < len)
            len = length;
 
        return src.substring(startindex, len);
    }
 
    /**
     * 截取左起指定长度的子串，null作为""处理.
     * @param src   原字符串
     * @param length    截取的长度
     * @return
     */
    public static String left(String src, int length) {
        return mid(src, 0, length);
    }
 
    /**
     * 截取右起指定长度的子串，null作为""处理.    
     * @param src   原字符串
     * @param length    截取的长度
     * @return
     */
    public static String right(String src, int length) {
        if (isEmpty(src))
            return EMPTY;
 
        int start = src.length() - length;
        if (start < 0){
            start = 0;
        }
        return mid(src, start, length);
    }
 
    /**
     * 取得字符串字节长度.
     * 
     * @param text
     * @return
     */
    public static int getLengthAsByte(String text) {
        if (null == text) {
            return 0;
        } else {
            return text.getBytes().length;
        }
    }
     
    /**
     * validateField Method.
     * Method Description:
     * @param field
     * @param length
     * @return
     * @author yuegy
     * @date 2011-3-28
     */
    public static boolean checkField(String field, int length) {
        return length <= getLengthAsByte(field);
    }
    
    /**
     * 检查字符串小数点前不超过intBit位，小数点后不超过decimalBit位.
     * 
     * @param str
     *            检查对象
     * @param intBit
     *            整数部分位数
     * @param decimalBit
     *            小数部分位数
     * @return boolean
     */
    public static boolean checkDataAccuracy(String str, int intBit, int decimalBit) {
        boolean b = false;
        if (str == null || "".equals(str)) {
            return b;
        }
        String s = "^(\\d{0," + intBit + "})(\\.\\d{0," + decimalBit + "})?$";
        Pattern p = Pattern.compile(s);
        Matcher m = p.matcher(str);
        b = m.matches();
        return b;
    }
    
    
    private static int compare(String str, String target) {
		int d[][]; // 矩阵
		int n = str.length();
		int m = target.length();
		int i; // 遍历str的
		int j; // 遍历target的
		char ch1; // str的
		char ch2; // target的
		int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) { // 初始化第一列
			d[i][0] = i;
		}

		for (j = 0; j <= m; j++) { // 初始化第一行
			d[0][j] = j;
		}

		for (i = 1; i <= n; i++) { // 遍历str
			ch1 = str.charAt(i - 1);
			// 去匹配target
			for (j = 1; j <= m; j++) {
				ch2 = target.charAt(j - 1);
				if (ch1 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}

				// 左边+1,上边+1, 左上角+temp取最小
				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
			}
		}
		return d[n][m];
	}

	private static int min(int one, int two, int three) {
		return (one = one < two ? one : two) < three ? one : three;
	}

	/**
	 * 
	 * 获取两字符串的相似度
	 * 
	 * 
	 * 
	 * @param str
	 * 
	 * @param target
	 * 
	 * @return
	 * 
	 */

	public static float getSimilarity(String str, String target) {
		return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
	}

 
     

}
