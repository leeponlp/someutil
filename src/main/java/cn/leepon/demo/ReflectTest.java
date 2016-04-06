package cn.leepon.demo;

import org.junit.Test;

import cn.leepon.util.Reflect;

public class ReflectTest {
	
	@Test
	public void demo1(){
		
		Reflect.on(Person.class).method("print").call();//Person.print
		Reflect pr = Reflect.on(Person.class).create();
		pr.field("name").set("mario");
		System.out.println(pr.field("name").get().unwrap());//mario
		
	}

}
