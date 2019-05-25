package com.ycr.kernel.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Created by yuchengren on 2017/2/9.
 */

object NumberUtil {

    val DECIMAL_FORMAT_MONEY_CENT = "0.00"
    val DECIMAL_FORMAT_MONEY_JIAO = "0.0"
    val DECIMAL_FORMAT_MONEY_YUAN = "0"

    /**
     * 转为非科学计数法的String
     *
     * @param number
     * @return
     */
    @JvmStatic fun doubleToUnGroupString(number: Double): String {
        val nf = NumberFormat.getInstance()
        nf.isGroupingUsed = false// 取消科学计数显示
        return nf.format(number)
    }

    /**
     * 格式为精度为分的金额模式
     */
    @JvmStatic fun formatToMoneyDouble(number: Double,mode: RoundingMode = RoundingMode.HALF_EVEN): Double {
        var formatNumber = 0.00
        try {
            val decimalFormat = DecimalFormat(DECIMAL_FORMAT_MONEY_CENT).apply { roundingMode = mode }
            formatNumber = java.lang.Double.parseDouble(decimalFormat.format(number))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return formatNumber
    }

    /**
     *
     * @param number 单位:元
     * @return
     */
    @JvmStatic fun formatToCentString(number: Double,mode: RoundingMode = RoundingMode.HALF_EVEN): String {
        var formatNumberString = ""
        try {
            val decimalFormat = DecimalFormat(DECIMAL_FORMAT_MONEY_CENT).apply { roundingMode = mode }
            formatNumberString = decimalFormat.format(number)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return formatNumberString
    }

    /**
     * @param cent 分
     * @return 两位小数的0.00式的MoneyString
     */
    @JvmStatic fun formatToCentString(cent: Long): String {
        return formatToCentString(cent / 100.0)
    }

    @JvmStatic fun formatToMoneyString(cent: Long): String {
        val pattern: String
        if (cent % 100 == 0L) {
            pattern = DECIMAL_FORMAT_MONEY_YUAN
        } else if (cent % 10 == 0L) {
            pattern = DECIMAL_FORMAT_MONEY_JIAO
        } else {
            pattern = DECIMAL_FORMAT_MONEY_CENT
        }
        return formatToString(cent / 100.0, pattern)
    }

    @JvmStatic fun formatToString(number: Double, pattern: String,mode: RoundingMode = RoundingMode.HALF_EVEN): String {
        var formatNumberString = ""
        try {
            val decimalFormat = DecimalFormat(pattern).apply { roundingMode = mode }
            formatNumberString = decimalFormat.format(number)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return formatNumberString
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1
     * 被加数
     * @param v2
     * 加数
     * @return 两个参数的和
     */

    @JvmStatic fun add(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.add(b2).toDouble()
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1
     * 被减法
     * @param v2
     * 减法
     * @return 两个参数的差
     */

    @JvmStatic fun subtract(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.subtract(b2).toDouble()
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1
     * 被乘法
     * @param v2
     * 乘法
     * @return 两个参数的积
     */

    @JvmStatic fun multiply(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.multiply(b2).toDouble()
    }


    /**
     * 提供精确的除法法运算。
     *
     * @param v1
     * 被除数
     * @param v2
     * 除数
     * @return 两个参数除法
     */

    @JvmStatic fun divide(v1: Double, v2: Double, isRound: Boolean): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return if (isRound) {
            b1.divide(b2, 2, BigDecimal.ROUND_DOWN).toDouble()
        } else {
            b1.divide(b2, 10, BigDecimal.ROUND_DOWN).toDouble()
        }
    }

}
