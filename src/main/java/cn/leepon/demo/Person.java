package cn.leepon.demo;

public class Person {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void print() {
		System.out.println("Person.print");
	}

	public Person(String name) {
		super();
		this.name = name;
	}
	
	
}
