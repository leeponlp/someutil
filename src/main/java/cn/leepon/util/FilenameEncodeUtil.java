package cn.leepon.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Encoder;

/**
 * 下载文件时，针对不同浏览器，进行附件名的编码
 * @notice 推荐使用encodeUrlFilename()方法，encodeDownloadFilename()采用BASE64Encoder类受限
 */
@SuppressWarnings("all")
public class FilenameEncodeUtil {
	
	/**
	 * 下载文件时，针对不同浏览器，进行附件名的编码
	 * @param filename 下载文件名
	 * @param agent 客户端浏览器
	 * @return 编码后的下载附件名
	 * @throws IOException */
	public static String encodeDownloadFilename(String filename, String agent)
			throws IOException {
		if (agent.contains("Firefox")) { // 火狐浏览器
			filename = "=?UTF-8?B?"
					+ new BASE64Encoder().encode(filename.getBytes("utf-8"))
					+ "?=";
			filename = filename.replaceAll("\r\n", "");
		} else { // IE及其他浏览器
			filename = URLEncoder.encode(filename, "utf-8");
			filename = filename.replace("+"," ");
		}
		return filename;
	}
	
	
	
	/**Add by YuanZhicheng at 2015-01-21
	 * 下载文件时，针对不同浏览器，进行附件名的编码
	 * @param filename 下载文件名
	 * @param agent 客户端浏览器
	 * @return 编码后的下载附件名 */
	public static String encodeUrlFilename(String filename, String agent) {
		String urlFName = null;
		filename = filename.replaceAll("\r\n", "");
		filename = filename.replace("+", " ");
		if ( StringUtils.containsIgnoreCase(agent, "Firefox") ) { //火狐浏览器
			try {
				urlFName = Base64.encodeBase64String(filename.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				urlFName = filename;
				e.printStackTrace();
			}
			urlFName = ("=?UTF-8?B?"+ urlFName + "?=");
		} else {//IE及其他浏览器
			try {
				urlFName = URLEncoder.encode(filename, "utf-8");
			} catch (UnsupportedEncodingException e) {
				urlFName = filename;
				e.printStackTrace();
			}
		}
		return urlFName;
	}
	
	
	
}
