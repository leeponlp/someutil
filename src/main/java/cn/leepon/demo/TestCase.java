package cn.leepon.demo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.junit.Test;

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
		

}
