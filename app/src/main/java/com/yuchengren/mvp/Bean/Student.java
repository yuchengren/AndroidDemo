package com.yuchengren.mvp.Bean;

/**
 * Created by yuchengren on 2016/12/28.
 */

public class Student {
	private String code;
	private String name;
	private String sex;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Student() {
	}

	public Student(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student{" +
				"code='" + code + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
