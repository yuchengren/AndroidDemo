package com.ycr.module.base.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yuchengren on 2017/12/22.
 */
public class People{

	@SerializedName(value = "name",alternate = {"Name","nameCn"})
	String name;
	int sex;
	int gendar;

	public People() {
	}

	public People(String name, int sex, int gendar) {
		this.name = name;
		this.sex = sex;
		this.gendar = gendar;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getGendar() {
		return gendar;
	}

	public void setGendar(int gendar) {
		this.gendar = gendar;
	}

	@Override
	public String toString() {
		return "People{" +
				"name='" + name + '\'' +
				", sex=" + sex +
				", gendar=" + gendar +
				'}';
	}


}
