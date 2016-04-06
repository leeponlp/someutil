package cn.leepon.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组工具类
 *
 */
public class ArrayUtil {
	
	/**
     * 泛型数组转为列表.
     * 
     * @param <T>
     * @param array
     * @return array = null 或 长度为0返回，长度为0的列表
     */
    public static <T> List<T> toList(T[] array) {
        List<T> rtn = new ArrayList<T>();
        if (array != null) {
            for (T t : array) {
                rtn.add(t);
            }
        }
        return rtn;
    }
 
    /**
     * Array is Null.
     * @param <T>
     * @param array
     * @return
     */
    public static <T> boolean isNull(T[] array) {
        return !isNotNull(array);
    }
 
    /**
     * Array is not Null.
     * @param <T>
     * @param array
     * @return
     */
    public static <T> boolean isNotNull(T[] array) {
        return array != null && array.length > 0;
    }

}
