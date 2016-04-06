package cn.leepon.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student implements Comparable<Student>{
	
	private int age;
	
	private String name;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public String toString() {
		return "Student [age=" + age + ", name=" + name + "]";
	}

	public Student(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}

	@Override
	public int compareTo(Student o) {
		// TODO Auto-generated method stub
		 int i = 0;
         i = age - o.age; // 使用字符串的比较
         if(i == 0) { // 如果名字一样,比较年龄, 返回比较年龄结果
              return name.compareTo(o.name);
         } else {
              return i; // 名字不一样, 返回比较名字的结果.
         }
	}
	
	public static void main(String[] args) {
		List<Student> list = new ArrayList<>();
		list.add(new Student(10, "lihua"));
		list.add(new Student(8, "hannan"));
		list.add(new Student(8, "xiaohua"));
		list.add(new Student(13, "xiaohua"));
		list.add(new Student(5, "zhangsan"));
		Collections.sort(list); 
		for (Student student : list) {
			System.err.println(student);
		}
	}

}
