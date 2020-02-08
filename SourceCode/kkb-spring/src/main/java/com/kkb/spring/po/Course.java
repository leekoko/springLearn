package com.kkb.spring.po;

public class Course {

	private String name;

	private Integer age;

	private String teacherName;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public void init() {
		System.out.println("我是对象初始化方法");
	}
}
