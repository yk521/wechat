package com.springmvc.utils;

public class RestResult {

	public RestResult() {
		this.status=1;
	}

	/**
	 * status为0
	 * message为传递的参数
	 * @param message
	 */
	public RestResult(String message) {
		this.status=0;
		this.message = message;
	}
	/**
	 * status为传递的第一个参数
	 * message为传递的第二个参数
	 * @param status
	 * @param message
	 */
	public RestResult(Integer status, String message) {
		this.status=status;
		this.message = message;
	}
	/**
	 * status为1
	 * message为传递的第一个参数
	 * result为传递的第二个参数
	 * @param message
	 * @param result
	 */
	public RestResult(String message, Object result) {
		this.status=1;
		this.message = message;
		this.result = result;
	}
	
	/**
	 * status为传递的第一个参数
	 * message为传递的第二个参数
	 * result为传递的第三个参数
	 * @param message
	 * @param result
	 */
	public RestResult(Integer status, String message, Object result) {
		this.status=1;
		this.message = message;
		this.result = result;
	}
	
	public RestResult(Integer status, Object result) {
		this.status=status;
		this.result = result;
	}
	/**
	 * 返回状态：0失败，1成功
	 */
	private int status;

	/**
	 * 返回错误code
	 */
	private String errCode;
	/**
	 * 返回错误信息
	 */
	private String message;

	/**
	 * 返回结果
	 */
	private Object result;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public void setSuccessResult(String message, Object result) {
		this.status = 1;
		this.message = message;
		this.result = result;
	}

	public void setErrorResult(String message) {
		this.status = 0;
		this.message = message;
	}

	public void setErrorResult(String message, Object result) {
		this.status = 0;

	}
}
