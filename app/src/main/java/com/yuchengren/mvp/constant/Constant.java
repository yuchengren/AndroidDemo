package com.yuchengren.mvp.constant;

/**
 * Created by yuchengren on 2016/12/29.
 */

public interface Constant {
	/**
	 * 网络请求状态
	 */
	public static final String REQUEST_SUCCESS = "0";
	public static final String E0099 = "E0099";// 系统异常

	public static final String E0001 = "E0001";// 传入参数不能为空
	public static final String E0002 = "E0002";// 传入参数 TradeType 不能为空
	public static final String E0003 = "E0003";// 传入参数 TradeType 不正确
	public static final String E0004 = "E0004";// 传入数据源名称不存在

	public static final String EM0001 = "EM0001";// 会员类查询数据不存在
	public static final String EC0001 = "EC0001";// 柜台类数据不存在
	public static final String EA0001 = "EA0001";// 销售类数据不存在
	public static final String EP0001 = "EP0001";// 商品类数据不存在
	public static final String EK0001 = "EK0001";// 库存管理数据不存在
	public static final String ES0001 = "ES0001";// 系统配置类数据查询不存在 消息内容：查询数据不存在
	public static final String ES0002 = "ES0002";// 获取服务器文件时连接超时 消息内容：请求url失败
	public static final String ES0003 = "ES0003";// URL格式错误 消息内容：URL路径或格式错误
	public static final String EU0001 = "EU0001";// 内容为执行存储过错报的错误


}
