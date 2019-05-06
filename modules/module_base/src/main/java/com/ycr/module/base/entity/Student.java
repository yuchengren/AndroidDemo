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

    private Student(Parcel in){
        code = in.readString();
        name = in.readString();
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
