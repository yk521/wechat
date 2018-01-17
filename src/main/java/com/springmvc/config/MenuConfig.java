package com.springmvc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import com.springmvc.utils.CheckUtils;

@Configuration
public class MenuConfig implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory
			.getLogger(MenuConfig.class);
	
	String accessToken = "";
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		logger.info("MenuConfig.start");
		createMenu();
	}
	private static void createMenu() {
		String appid = "wxaac84f28ff85395b";
		String secret = "c84e9aceef82ca4fe4c35a02f6608fe1";
		String accessToken = ApiConfig.getInstance().getAccessToken();
		if (CheckUtils.isNullOrEmpty(accessToken)) {
			logger.error("系统配置参数accessToken错误：" + "[" + accessToken + "]");
			// 强制系统退出
			System.exit(-1);
		}
	}

}
