package cn.leepon.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
 
/**
 * XML工具类
 * @since 2014-04-25
 */
public class XmlTool {
	
	 public static Document read(File file) {
	        return read(file, null);
	    }
	 
	    
	    /**
	     * 
	     * @param file
	     * @param charset
	     * @return
	     * @throws DocumentException
	     */
	    public static Document read(File file, String charset) {
	        if (null == file) {
				return null;
			}
	        SAXReader reader = new SAXReader();
	        if (!StringUtils.isEmpty(charset)) {
	        	reader.setEncoding(charset);
			}
	        Document document = null;
	        try {
	            document = reader.read(file);
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	        return document;
	    }
	 
	    public static Document read(URL url) {
	        return read(url, null);
	    }
	 
	    /**
	     * 
	     * @param url
	     * @param charset
	     * @return
	     * @throws DocumentException
	     */
	    public static Document read(URL url, String charset) {
	    	if (null == url) {
				return null;
			}
	        SAXReader reader = new SAXReader();
	        if (!StringUtils.isEmpty(charset)) {
	        	reader.setEncoding(charset);
			}
	        Document document = null;
	        try {
	            document = reader.read(url);
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	        return document;
	    }
	    
	    
	    public static Document read(InputStream in,String charset){
	    	if (null == in) {
	            return null;
	        }
	        SAXReader reader = new SAXReader();
	        if (!StringUtils.isEmpty(charset)) {
	        	reader.setEncoding(charset);
			}
	        Document document = null;
	        try {
	            document = reader.read(in);
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	        return document;
	    	
	    }
	    
	
    /**
     * 获取根节点
     * 
     * @param doc
     * @return
     */
    public static Element getRootElement(Document doc) {
        if (null == doc) {
            return null;
        }
        return doc.getRootElement();
    }
 
    /**
     * 获取节点eleName下的文本值，若eleName不存在则返回默认值defaultValue
     * 
     * @param eleName
     * @param defaultValue
     * @return
     */
    public static String getElementValue(Element eleName, String defaultValue) {
        if (null == eleName) {
            return defaultValue == null ? "" : defaultValue;
        } else {
            return eleName.getTextTrim();
        }
    }
 
    public static String getElementValue(String eleName, Element parentElement) {
        if (null == parentElement) {
            return null;
        } else {
            Element element = parentElement.element(eleName);
            
            if (element !=null) {
                return element.getTextTrim();
            } else {
                try {
                    throw new Exception("找不到节点" + eleName);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
 
    /**
     * 获取节点eleName下的文本值
     * 
     * @param eleName
     * @return
     */
    public static String getElementValue(Element eleName) {
        return getElementValue(eleName, null);
    }
 
   
    public static Document findCDATA(Document body, String path) {
        return XmlTool.stringToXml(XmlTool.getElementValue(path,
                body.getRootElement()));
    }
 
    /**
     * 递归遍历XML返回对象
     * 1、转化XML为简单类型的Map<String, Object>
     * 2、如果子节点的Name和第一个相同，默认按照List处理
     * @param root
     * @return
     */
    public static Object parse(Element root) {
        List<?> elements = root.elements();
        if (elements.size() == 0) {
            // 没有子元素
            return root.getTextTrim();
        } else {
            // 有子元素
            String prev = null;
            boolean guess = true; // 默认按照数组处理
     
            Iterator<?> iterator = elements.iterator();
            while (iterator.hasNext()) {
                Element elem = (Element) iterator.next();
                String name = elem.getName();
                if (prev == null) {
                    prev = name;
                } else {
                    guess = name.equals(prev);
                    break;
                }
            }
            iterator = elements.iterator();
            if (guess) {
                List<Object> data = new ArrayList<Object>();
                while (iterator.hasNext()) {
                    Element elem = (Element) iterator.next();
                    ((List<Object>) data).add(parse(elem));
                }
                return data;
            } else {
                Map<String, Object> data = new HashMap<String, Object>();
                while (iterator.hasNext()) {
                    Element elem = (Element) iterator.next();
                    ((Map<String, Object>) data).put(elem.getName(), parse(elem));
                }
                return data;
            }
        }
    }
   
 
    /**
     * 将文档树转换成字符串
     * 
     * @param doc
     * @return
     */
    public static String xmltoString(Document doc) {
        return xmltoString(doc, null);
    }
 
    /**
     * 
     * @param doc
     * @param charset
     * @return
     * @throws IOException
     */
    public static String xmltoString(Document doc, String charset) {
        if (null == doc) {
            return "";
        }
        if (!StringUtils.isEmpty(charset)) {
            return doc.asXML();
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        StringWriter strWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(strWriter, format);
        try {
            xmlWriter.write(doc);
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strWriter.toString();
    }
 
    /**
     * 持久化Document
     * @param doc
     * @param charset
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static void xmltoFile(Document doc, File file, String charset)
            throws Exception {
        if (null == doc) {
            throw new NullPointerException("doc cant not null");
        }
        if (!StringUtils.isEmpty(charset)) {
        	throw new NullPointerException("charset cant not null");
		}

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        FileOutputStream os = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(os, charset);
        XMLWriter xmlWriter = new XMLWriter(osw, format);
        try {
            xmlWriter.write(doc);
            xmlWriter.close();
            if (osw != null) {
                osw.close();
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 
     * @param doc
     * @param charset
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static void xmltoFile(Document doc, String filePath, String charset)
            throws Exception {
        xmltoFile(doc, new File(filePath), charset);
    }
 
     
    /**
     * 
     * @param doc
     * @param filePath
     * @param charset
     * @throws Exception
     */
    public static void writDocumentToFile(Document doc, String filePath, String charset)
            throws Exception {
        xmltoFile(doc, new File(filePath), charset);
    }
     
    public static Document stringToXml(String text) {
        try {
            return DocumentHelper.parseText(text);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
     
    public static Document createDocument() {
        return DocumentHelper.createDocument();
    }
}