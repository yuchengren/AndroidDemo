package com.yuchengren.mvp.Bean;

import java.io.Serializable;

/**
 * 网络请求结果
 *
 * @author Zhoujun 2014-1-17上午9:30:53
 */

public class ResponseEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5996858695388534809L;
	private String code;// 状态码
	private String msg;// 信息
	private String result;// 结果

	public ResponseEntity() {
		super();
	}

	public ResponseEntity(String code, String msg, String result) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
