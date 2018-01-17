package com.springmvc.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="t_wx_token")
public class WxToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6701709301945428686L;
	/**
	 * 获取到的凭证
	 */
	@Id
	@Column(name="ACCESS_TOKEN")
	private String access_token;
	/**
	 * 凭证有效时间，单位：秒
	 */
	@Column(name="EXPIRES_IN")
	private int expires_in;

	/**
	 * 错误时微信会返回错误码等信息，JSON数据包示例如下（该示例为AppID无效错误）:
	 */
	@Column(name="ERRCODE")
	private int errcode;
	@Column(name="ERRMSG")
	private String errmsg;
	@Column(name="CREATE_DATE")
	private Timestamp createDate;


	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
