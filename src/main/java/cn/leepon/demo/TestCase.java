package cn.leepon.demo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import cn.leepon.util.HttpClientUtil;
import cn.leepon.util.ResourceUtil;


public class TestCase {
	
	@Test
	public void demo1(){
		List<Student> list = new ArrayList<>();
		list.add(new Student(10, "lihua"));
		list.add(new Student(8, "hannan"));
		list.add(new Student(8, "xiaohua"));
		list.add(new Student(13, "xiaohua"));
		list.add(new Student(5, "zhangsan"));
		Collections.sort(list, new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				// TODO Auto-generated method stub
				return o1.getAge()-o2.getAge();
			}
		});
		
		for (Student student : list) {
			System.err.println(student);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void demo2(){
		
		for(;;){
			 System.out.println("working"+new Date(System.currentTimeMillis()).toLocaleString());
			    try {
			        Thread.sleep(10000);
			    } catch (InterruptedException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
		}
	}
	
	@Test
	public void demo3(){
		
		Object[] objs = new Object[]{"leepon","平安好房"};
		String value = ResourceUtil.getValue("config", "100", objs);
		System.err.println(value);
	}
	
	@Test
	public void demo4(){
		
		String url = "http://139.129.43.139:8000/v1.0/hospital/doctor/list";
		List<NameValuePair> list = new ArrayList<>();
		list.add(new BasicNameValuePair("token", "644D48B0706344419EC2720E5A364DFE"));
		list.add(new BasicNameValuePair("hospitalId", "9910007"));
		list.add(new BasicNameValuePair("deptCode", "24"));
		list.add(new BasicNameValuePair("doctorCode", "2912"));
		list.add(new BasicNameValuePair("clinicDates", "2016-09-29"));
		list.add(new BasicNameValuePair("scheduleFlag", "1"));
		String executeByPOST = HttpClientUtil.executeByPOST(url, list);
		System.err.println(executeByPOST);
		
	}
		

}
