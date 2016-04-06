package cn.leepon.demo;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import cn.leepon.util.RegexUtil;

public class StringTest {
	
	@Test
	public void demo1(){
		String a = "000001";
		boolean digit = RegexUtil.isNUM(a);
		System.err.println(digit);
		//String regex = "[^1-9]";
		//Pattern pattern = Pattern.compile(regex);
		//Matcher matcher = pattern.matcher(a);
		//System.err.println(matcher.replaceAll("").trim());
		
	}
	
	@Test
	public void demo2(){
		List<String> list = new ArrayList<>();
		list.add("xxx000001");
		list.add("xxx000201");
		list.add("xxxcds000031");
		list.add("xxx000149");
		list.add("xxxavc001001");
		list.add("xxx020301");
		list.add("xxx100001");
		
		//List<String> numlist = new ArrayList<>();
		for (String string : list) {
			String num = RegexUtil.getNum(string);
			System.err.println(num);
		}
	}
		
	
	@Test
	public void demo3(){
//		String[] all = RegexUtil.init("abd1234", "\\D+").getAll();
//		for (String string : all) {
//			System.err.println(string);
//		}
		
		boolean email = RegexUtil.isEmail("leepon1010@163.com");
		boolean phone = RegexUtil.isPhone("18801916618");
		boolean mobile = RegexUtil.isMobile("021-20662612");
		String filterStr = RegexUtil.filterStr("$lee:[a#&b]c(");
		boolean url = RegexUtil.isURL("http://git.ipo.com/hf-dev-data/hf-java-analyse");
		boolean ip = RegexUtil.isIP("196.255.255.255");
		System.err.println(email);
		System.err.println(phone);
		System.err.println(mobile);
		System.err.println(filterStr);
		System.err.println(url);
		System.err.println(ip);
	}
	
	@Test
	public void demo4(){
		String str1 = "helloworld";
		String str2 = "hello"+new String("world");
		System.err.println(str1==str2); 
		System.out.println("aa" == "aa"); // true
		System.out.println(new String("aa") == new String("aa")); // false
		System.out.println("aa" == new String("aa")); // false

		System.out.println("aa".equals(new String("aa"))); // true	
		
	}
	
	@Test
	public void demo5(){
		for(;;){
			System.err.println("hehe!!"); 
		}
	}

}
