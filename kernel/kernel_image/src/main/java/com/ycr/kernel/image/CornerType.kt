package com.ycr.kernel.image

/**
 * 圆角类型
 * created by yuchengren on 2019/4/30
 */
enum class CornerType{
    ALL,

    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,

    TOP,
    BOTTOM,
    LEFT,
    RIGHT,

    OTHER_TOP_LEFT,
    OTHER_TOP_RIGHT,
    OTHER_BOTTOM_LEFT,
    OTHER_BOTTOM_RIGHT,

    DIAGONAL_FROM_TOP_LEFT,
    DIAGONAL_FROM_TOP_RIGHT
}