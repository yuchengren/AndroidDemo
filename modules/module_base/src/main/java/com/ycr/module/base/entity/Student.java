package com.ycr.module.base.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuchengren on 2019/2/25.
 */
public class Student implements Parcelable {

    public String code;
    public String name;

    public Student(){

    }

    public Student(String code,String name){
        this.code = code;
        this.name = name;
    }

    private Student(Parcel in){
        code = in.readString();
        name = in.readString();
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(!(obj instanceof Student)){
            return false;
        }
        Student s = (Student) obj;
        return s.code.equals(this.code);
    }

    @Override
    public String toString() {
        return "code = "+code+",name="+name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>(){

        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
