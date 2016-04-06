package cn.leepon.demo;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;

import cn.leepon.util.FastJsonUtil;
import cn.leepon.util.XmlTool;

public class TestXml {
	
	@Test
	public void demo1(){
		Document document = XmlTool.read(new File("1.xml"));
		Element rootElement = XmlTool.getRootElement(document);
		Object object = XmlTool.parse(rootElement);
		String jsonString = FastJsonUtil.toJSONString(object);
		System.err.println(jsonString);
	}
	
	@Test
	public void demo2(){
		Document document = XmlTool.read(new File("1.xml"));
		String xmltoString = XmlTool.xmltoString(document);
		System.err.println(xmltoString);
	}

}
