package com.ycr.kernel.image

/**
 * 圆角类型
 * created by yuchengren on 2019/4/30
 */
enum class CornerType{
    ALL, // 四个圆角

    TOP_LEFT, //左上一个圆角
    TOP_RIGHT, //右上一个圆角
    BOTTOM_LEFT, //左下一个圆角
    BOTTOM_RIGHT, //右下一个圆角

    TOP, //上 两圆角
    BOTTOM, // 下 两圆角
    LEFT, // 左 两圆角
    RIGHT, // 右 两圆角

    OTHER_TOP_LEFT, //除左上 其他三圆角
    OTHER_TOP_RIGHT,//除右上
    OTHER_BOTTOM_LEFT, //除左下
    OTHER_BOTTOM_RIGHT, //除右下

    DIAGONAL_FROM_TOP_LEFT, //斜线左上 右下 两圆角
    DIAGONAL_FROM_TOP_RIGHT //斜线右上 左下 两圆角
}