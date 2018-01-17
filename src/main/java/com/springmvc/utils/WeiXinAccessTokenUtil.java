package com.springmvc.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.springmvc.dto.WinXinAccessTokenDto;


/**
 * 获取微信access_token工具类
 */
public class WeiXinAccessTokenUtil implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(WeiXinAccessTokenUtil.class);

	// 第三方用户唯一凭证
	public static String appid = "wxaac84f28ff85395b";
	// 第三方用户唯一凭证密钥
	public static String appsecret = "c84e9aceef82ca4fe4c35a02f6608fe1";
	public static WinXinAccessTokenDto accessToken = null;
	private int accessTokenTime = 7200;

	/**
	 * 初始化token
	 */
	public void initToken() {
		new Thread(new WeiXinAccessTokenUtil()).start();
	}
	/**
	 * 获取微信access_token
	 */
	public static WinXinAccessTokenDto getAccessToken(String appid, String secret) {
		if (StringUtils.isEmpty(appid) || StringUtils.isEmpty(secret)) {
			log.error("appid or secret is null");
			return null;
		}
		WinXinAccessTokenDto winXinAccessTokenDto = new WinXinAccessTokenDto();
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid
					+ "&secret=" + secret;
			HttpClient httpClient = new HttpClient();
			GetMethod getMethod = new GetMethod(url);
			int execute = httpClient.executeMethod(getMethod);
			System.out.println("execute:" + execute);
			String getResponse = getMethod.getResponseBodyAsString();
			String getAccessToken = getMethod.getResponseBodyAsString(0);
			System.out.println(getAccessToken);
			winXinAccessTokenDto.setAccessToken(getResponse);
		} catch (IOException e) {
			log.error("getAccessToken failed,desc:::" + e);
			e.printStackTrace();
		}
		System.out.println(winXinAccessTokenDto);
		return winXinAccessTokenDto;
	}
	
	public void run() {
		while (true) {
			try {
				accessToken = getAccessToken(appid, appsecret);
				if (null != accessToken) {
					log.info("获取access_token成功， token:{}",accessToken.getAccessToken());
					// 休眠7100秒
					Thread.sleep((accessTokenTime - 100) * 1000);
				} else {
					// 如果access_token为null，10秒后再获取
					Thread.sleep(10 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
				}
				log.error("{}", e);
			}
		}
	}
}
