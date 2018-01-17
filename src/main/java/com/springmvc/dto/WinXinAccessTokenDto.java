package com.springmvc.dto;

import java.io.Serializable;

public class WinXinAccessTokenDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "WinXinAccessTokenDto [accessToken=" + accessToken + "]";
	}

}
