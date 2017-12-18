package com.yuchengren.mvp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * Created by yuchengren on 2017/2/3.
 */

public class DateHelper {

	/**
	 * 时间格式精度 天
	 */
	public static final String FORMAT_ACCURACY_DAY = "yyyy-MM-dd";
	/**
	 * 时间格式精度 秒
	 */
	public static final String FORMAT_ACCURACY_SECOND = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间格式精度 毫秒
	 */
	public static final String FORMAT_ACCURACY_MILLIS = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 时间格式 年
	 */
	public static final String FORMAT_YEAR = "yyyy";
	/**
	 * 时间格式 月
	 */
	public static final String FORMAT_MONTH = "MM";
	/**
	 * 时间格式 天
	 */
	public static final String FORMAT_DAY = "dd";

	/**
	 * 日期格式化为字符串
	 * @param date 日期
	 * @param format 转化的字符串格式
	 * @return 日期格式化后的字符串
	 */
	public static String formatDateToString(Date date, String format){
		String dateString = "";
		try{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			dateString = simpleDateFormat.format(date);
		}catch (Exception e){
			e.printStackTrace();
		}
		return dateString;
	}

	/**
	 * 默认的精度为天的 日期格式化
	 * @param date 日期
	 * @return 按精度天格式化的日期字符串
	 */
	public static String formatDateToDayString(Date date){
		return formatDateToString(date,FORMAT_ACCURACY_DAY);
	}

	/**
	 * 默认的精度为秒的 日期格式化
	 * @param date 日期
	 * @return 按精度秒格式化的日期字符串
	 */
	public static String formatDateToSecondString(Date date){
		return formatDateToString(date,FORMAT_ACCURACY_SECOND);
	}

	/**
	 * 默认的精度为豪秒的 日期格式化
	 * @param date 日期
	 * @return 按精度天格式化的日期字符串
	 */
	public static String formatDateToMillisString(Date date){
		return formatDateToString(date,FORMAT_ACCURACY_MILLIS);
	}

	/**
	 * 字符串解析为日期
	 * @param dateString 日期字符串
	 * @param format 日期字符串对应的格式
	 * @return 日期
	 */
	public static Date parseStringToDate(String dateString, String format){
		Date date = null;
		try{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.parse(dateString);
		} catch (Exception e){
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 不同格式的字符串日期之间的转化
	 * @param dateString 日期字符串
	 * @param originFormat 传入的日期字符串的格式
	 * @param convertFormat 要转化的日期字符串格式
	 * @return 转化的日期字符串
	 */
	public static String convertStringToString(String dateString, String originFormat, String convertFormat){
		return formatDateToString(parseStringToDate(dateString,originFormat),convertFormat);
	}

	/**
	 * 求两个日期相差天数
	 *
	 * @param startDate 起始日期，格式yyyy-MM-dd
	 * @param endDate 终止日期，格式yyyy-MM-dd
	 * @return 两个日期相差天数
	 */
	public static long getIntervalDays(String startDate, String endDate) {
		return ((java.sql.Date.valueOf(endDate)).getTime() - (java.sql.Date.valueOf(startDate)).getTime()) / (3600 * 24 * 1000);
	}



	/**
	 * 按指定格式获取当前时间的日期字符串
	 * @param format 指定获取的日期字符串格式
	 * @return 按指定格式获取的当前时间的日期字符串
	 */
	public static String getCurrentTime(String format){
		return formatDateToString(new Date(),format);
	}

	public static String getCurrentSecondTime(){
		return formatDateToString(new Date(),FORMAT_ACCURACY_SECOND);
	}

	public static String getCurrentMillisTime(){
		return formatDateToString(new Date(),FORMAT_ACCURACY_MILLIS);
	}
	public static String getCurrentDayTime(){
		return formatDateToString(new Date(),FORMAT_ACCURACY_DAY);
	}

	/**
	 * 获取当前时间的年
	 * @return 当前时间的年
	 */
	public static String getCurrentYear(){
		return getCurrentTime(FORMAT_YEAR);
	}

	/**
	 * 获取当前时间的月
	 * @return 当前时间的月
	 */
	public static String getCurrentMonth(){
		return getCurrentTime(FORMAT_MONTH);
	}

	/**
	 * 获取当前时间的天
	 * @return 当前时间的天
	 */
	public static String getCurrentDay(){
		return getCurrentTime(FORMAT_DAY);
	}

	/**
	 * 添加年数
	 * @param originDate 传入的日期
	 * @param addYear 添加的年数
	 * @return 添加年数后的日期
	 */
	public static Date addYear(Date originDate, int addYear){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(originDate);
		calendar.add(GregorianCalendar.YEAR, addYear);
		return calendar.getTime();
	}

	/**
	 * 添加天数
	 * @param originDate 传入的日期
	 * @param addDay 添加的天数
	 * @return 添加天数后的日期
	 */
	public static Date addDay(Date originDate, int addDay){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(originDate);
		calendar.add(GregorianCalendar.DATE, addDay);
		return calendar.getTime();
	}


}
