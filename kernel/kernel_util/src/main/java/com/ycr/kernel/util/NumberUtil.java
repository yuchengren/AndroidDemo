package com.ycr.kernel.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by yuchengren on 2017/2/9.
 */

public class NumberUtil {

	public static final String DECIMAL_FORMAT_MONEY = "0.00";

	/**
	 * 转为非科学计数法的String
	 *
	 * @param number
	 * @return
	 */
	public static String doubleToUnGroupString(double number) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);// 取消科学计数显示
		return nf.format(number);
	}

	/**
	 * 格式为精度为分的金额模式
	 */
	public static double formatToMoneyDouble(double number) {
		double formatNumber = 0.00;
		try{
			DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT_MONEY);
			formatNumber = Double.parseDouble(decimalFormat.format(number));
		}catch (Exception e){
			e.printStackTrace();
		}
		return  formatNumber;
	}

	/**
	 * 格式为精度为分的金额模式
	 */
	public static String formatToMoneyString(double number) {
		String formatNumberString = "";
		try{
			DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT_MONEY);
			formatNumberString = decimalFormat.format(number);
		}catch (Exception e){
			e.printStackTrace();
		}
		return  formatNumberString;
	}

	public static String formatToString(double number,String pattern) {
		String formatNumberString = "";
		try{
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			formatNumberString = decimalFormat.format(number);
		}catch (Exception e){
			e.printStackTrace();
		}
		return  formatNumberString;
	}

	/**
	 * 提供精确的加法运算。
	 *
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 *
	 * @param v1
	 *            被减法
	 * @param v2
	 *            减法
	 * @return 两个参数的差
	 */

	public static double subtract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 *
	 * @param v1
	 *            被乘法
	 * @param v2
	 *            乘法
	 * @return 两个参数的积
	 */

	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}


	/**
	 * 提供精确的除法法运算。
	 *
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数除法
	 */

	public static double divide(double v1, double v2, boolean isRound) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		if(isRound){
			return b1.divide(b2,2,BigDecimal.ROUND_DOWN).doubleValue();
		}else{
			return b1.divide(b2,10,BigDecimal.ROUND_DOWN).doubleValue();
		}
	}

	/**
	 * 格式为精度为分的金额模式
	 */
	public static double formatToMoneyDouble(double number, RoundingMode mode) {
		double formatNumber = 0.00;
		try{
			DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT_MONEY);
			decimalFormat.setRoundingMode(mode);
			formatNumber = Double.parseDouble(decimalFormat.format(number));
		}catch (Exception e){
			e.printStackTrace();
		}
		return  formatNumber;
	}

}
