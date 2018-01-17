package com.springmvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.springmvc.enums.MenuType;
import com.springmvc.menu.Menu;
import com.springmvc.menu.MenuButton;
import com.springmvc.utils.RestResult;
import com.springmvc.utils.WeiXinTokenUtil;


@Controller
public class TokenController {
	
	   /**
     * 默认连接超时时间(毫秒)
     * 由于目前的设计原因，该变量定义为静态的，超时时间不能针对每一次的请求做定制
     * 备选优化方案：
     * 1.考虑是否重新设计这个工具类，每次请求都需要创建一个实例;
     * 2.请求方法里加入超时时间参数
     * 或者说是否没必要定制,10秒是一个比较适中的选择，但有些请求可能就是需要快速给出结果T_T
     */
    public static final  int     CONNECT_TIMEOUT = 10 * 1000;
    private static final Charset UTF_8           = Charset.forName("UTF-8");
	private static String ACCESS_TOKEN = "u7tHRjI6U98XpDSed82M0XzuSkl_1xip0zkLp91ihQF8ZUOZ295wdYOJ3JpSyfLii1_eSCs6Cs8Tl10csfH_vr0aBpkMbktGCQtUPrz6Md2UfvmHa7S9RfdmXYzQGFG2JLGdADABDY";
	private static String APPID = "wxaac84f28ff85395b";
	private static String SECRET = "c84e9aceef82ca4fe4c35a02f6608fe1";
    /**
     * 服务器地址的有效性
     * 微信消息接收和token验证
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/weixin_token")
    public void getToken(Model model, HttpServletRequest request,HttpServletResponse response) throws IOException {
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            //1512622929
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null && WeiXinTokenUtil.checkSignature(signature, timestamp, nonce)) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
   
    /**
     * 
     * 获取微信access_token
     * https请求方式: GET
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping("/getAccessToken")
    public @ResponseBody String getAccessToken() throws IOException{
    	try {
	    	String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + SECRET;
	    	GetMethod getMethod = new GetMethod(url);
	    	HttpClient httpClient = new HttpClient();
	    	int execute = httpClient.executeMethod(getMethod);
	    	System.out.println(execute);
	    	System.out.println(getMethod.getResponseBodyAsString());
	    	JSONObject response = JSONObject.parseObject(getMethod.getResponseBodyAsString());
	    	String access_token = response.getString("access_token");
	    	ACCESS_TOKEN = access_token;
	    	String expires_in = response.getString("expires_in");
	    	String errcode = response.getString("errcode");
	    	String errmsg = response.getString("errmsg");
//	    	System.out.println(access_token);
//	    	System.out.println(expires_in);
//	    	System.out.println(errcode);
//	    	System.out.println(errmsg);
	    	if (execute == 200) {
	    		return "success";
	    	} else {
	    		return "false";
	    	}
    	} catch(Exception e) {
    		e.getStackTrace();
    		return "false";
    	}
    	
    }
    
    /**
     * 获取微信服务器IP地址
     * http请求方式: GET
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping("/getIpList")
    public @ResponseBody RestResult getIpList() throws IOException{
    	try {
	    	String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + ACCESS_TOKEN;
	    	GetMethod getMethod = new GetMethod(url);
	    	HttpClient httpClient = new HttpClient();
	    	int execute = httpClient.executeMethod(getMethod);
	    	System.out.println(execute);
	    	System.out.println(getMethod.getResponseBodyAsString());
	    	JSONObject response = JSONObject.parseObject(getMethod.getResponseBodyAsString());
	    	String ip_list = response.getString("ip_list");
	    	String errcode = response.getString("errcode");
	    	String errmsg = response.getString("errmsg");
	    	System.out.println(ip_list);
	    	System.out.println(errcode);
	    	System.out.println(errmsg);
	    	if (execute == 200) {
	    		return new RestResult("success", response);
	    	} else {
	    		return new RestResult(0, "error", response);
	    	}
    	} catch(Exception e) {
    		e.getStackTrace();
    		return new RestResult("error");
    	}
    }
   
    @RequestMapping("/getMenu")
    public @ResponseBody RestResult getMenu() throws IOException{
    	
    	String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + ACCESS_TOKEN;
    	PostMethod postMethod = new PostMethod(url);
    	Menu menu = new Menu();
    	
    	MenuButton menuButtonOne = new MenuButton();
    	menuButtonOne.setType(MenuType.CLICK);
    	menuButtonOne.setKey("sousuo");
    	menuButtonOne.setName("搜索");
    	
    	MenuButton menuButtonOneOne = new MenuButton();
    	menuButtonOneOne.setKey("baidu");
    	menuButtonOneOne.setName("百度");
    	menuButtonOneOne.setType(MenuType.VIEW);
    	menuButtonOneOne.setUrl("http://www.baidu.com");
    	
    	MenuButton menuButtonOneTwo = new MenuButton();
    	menuButtonOneTwo.setKey("guge");
    	menuButtonOneTwo.setName("谷歌");
    	menuButtonOneTwo.setType(MenuType.VIEW);
    	menuButtonOneTwo.setUrl("http://www.google.com");
    	
    	MenuButton menuButtonOneThree = new MenuButton();
    	menuButtonOneThree.setKey("sougou");
    	menuButtonOneThree.setName("搜狗");
    	menuButtonOneThree.setType(MenuType.VIEW);
    	menuButtonOneThree.setUrl("http://www.baidu.com");
    	
    	List<MenuButton> menuButtonOneList = new ArrayList<MenuButton>();
    	menuButtonOneList.add(menuButtonOneOne);
    	menuButtonOneList.add(menuButtonOneTwo);
    	menuButtonOneList.add(menuButtonOneThree);
    	menuButtonOne.setSubButton(menuButtonOneList);
    	
    	MenuButton menuButtonTwo = new MenuButton();
    	menuButtonTwo.setType(MenuType.CLICK);
    	menuButtonTwo.setKey("yinyue");
    	menuButtonTwo.setName("音乐");
    	
    	MenuButton menuButtonTwoOne = new MenuButton();
    	menuButtonTwoOne.setKey("wangyiyun");
    	menuButtonTwoOne.setName("网易云");
    	menuButtonTwoOne.setType(MenuType.VIEW);
    	menuButtonTwoOne.setUrl("http://music.163.com");
    	
    	MenuButton menuButtonTwoTwo = new MenuButton();
    	menuButtonTwoTwo.setKey("kugou");
    	menuButtonTwoTwo.setName("酷狗");
    	menuButtonTwoTwo.setType(MenuType.VIEW);
    	menuButtonTwoTwo.setUrl("http://www.kugou.com");
    	
    	MenuButton menuButtonTwoThree = new MenuButton();
    	menuButtonTwoThree.setKey("kuwo");
    	menuButtonTwoThree.setName("酷我");
    	menuButtonTwoThree.setType(MenuType.VIEW);
    	menuButtonTwoThree.setUrl("http://www.kuwo.cn");
    	
    	List<MenuButton> menuButtonTwoList = new ArrayList<MenuButton>();
    	menuButtonTwoList.add(menuButtonTwoOne);
    	menuButtonTwoList.add(menuButtonTwoTwo);
    	menuButtonTwoList.add(menuButtonTwoThree);
    	menuButtonTwo.setSubButton(menuButtonTwoList);
    	
    	MenuButton menuButtonThree = new MenuButton();
    	menuButtonThree.setType(MenuType.CLICK);
    	menuButtonThree.setKey("shipin");
    	menuButtonThree.setName("视频");
    	
    	MenuButton menuButtonThreeOne = new MenuButton();
    	menuButtonThreeOne.setKey("aiqiyi");
    	menuButtonThreeOne.setName("爱奇艺");
    	menuButtonThreeOne.setType(MenuType.VIEW);
    	menuButtonThreeOne.setUrl("http://www.iqiyi.com");
    	
    	MenuButton menuButtonThreeTwo = new MenuButton();
    	menuButtonThreeTwo.setKey("youku");
    	menuButtonThreeTwo.setName("优酷");
    	menuButtonThreeTwo.setType(MenuType.VIEW);
    	menuButtonThreeTwo.setUrl("http://www.youku.com");
    	
    	MenuButton menuButtonThreeThree = new MenuButton();
    	menuButtonThreeThree.setKey("tengxun");
    	menuButtonThreeThree.setName("腾讯");
    	menuButtonThreeThree.setType(MenuType.VIEW);
    	menuButtonThreeThree.setUrl("https://v.qq.com");
    	
    	List<MenuButton> menuButtonThreeList = new ArrayList<MenuButton>();
    	menuButtonThreeList.add(menuButtonThreeOne);
    	menuButtonThreeList.add(menuButtonThreeTwo);
    	menuButtonThreeList.add(menuButtonThreeThree);
    	menuButtonThree.setSubButton(menuButtonThreeList);
    	
    	List<MenuButton> menuButtonList = new ArrayList<MenuButton>();
    	menuButtonList.add(menuButtonOne);
    	menuButtonList.add(menuButtonTwo);
    	menuButtonList.add(menuButtonThree);
    	menu.setButton(menuButtonList);
    	MenuButton arrayMenu[] = new MenuButton[] {menuButtonOne, menuButtonTwo, menuButtonThree};
    	String jsonMenu = menu.toJsonString();
    	String jsonArrayMenu = JSONObject.toJSON(arrayMenu).toString();
    	//postMethod.addParameter("button", jsonArrayMenu);
    	String thisMenu = "[{\"name\":\"搜索\",\"sub_button\":[{\"name\":\"搜狗\",\"type\":\"VIEW\",\"key\":\"sougou\",\"url\":\"http://www.baidu.com\"},{},{}],\"type\":\"CLICK\",\"key\":\"sousuo\"},{\"name\":\"音乐\",\"sub_button\":[{\"name\":\"网易云\",\"type\":\"VIEW\",\"key\":\"wangyiyun\",\"url\":\"http://music.163.com\"},{\"name\":\"酷狗\",\"type\":\"VIEW\",\"key\":\"kugou\",\"url\":\"http://www.kugou.com\"},{\"name\":\"酷我\",\"type\":\"VIEW\",\"key\":\"kuwo\",\"url\":\"http://www.kuwo.cn\"}],\"type\":\"CLICK\",\"key\":\"yinyue\"},{\"name\":\"视频\",\"sub_button\":[{\"name\":\"爱奇艺\",\"type\":\"VIEW\",\"key\":\"aiqiyi\",\"url\":\"http://www.iqiyi.com\"},{\"name\":\"优酷\",\"type\":\"VIEW\",\"key\":\"youku\",\"url\":\"http://www.youku.com\"},{\"name\":\"腾讯\",\"type\":\"VIEW\",\"key\":\"tengxun\",\"url\":\"https://v.qq.com\"}],\"type\":\"CLICK\",\"key\":\"shipin\"}]";
    	postMethod.addParameter("button", thisMenu);
    	System.out.println(jsonArrayMenu);
//    	StringEntity requestEntity = new StringEntity(jsonMenu, ContentType.APPLICATION_JSON);
    	//配置请求参数
//        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(CONNECT_TIMEOUT).build();
//        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
//    	HttpUriRequest request = null;
//    	request = new HttpPost(url);
//    	((HttpPost) request).setEntity(requestEntity);
//    	CloseableHttpResponse response = null;
//    	//发起请求
//        response = client.execute(request);
//        int resultCode = response.getStatusLine().getStatusCode();
//        HttpEntity entity = response.getEntity();
//        String resultJson = EntityUtils.toString(entity, UTF_8);
//        if (HttpStatus.SC_OK == resultCode) {
//        }
    	HttpClient httpClient = new HttpClient();
    	int execute = httpClient.executeMethod(postMethod);
    	System.out.println(execute);
    	System.out.println(postMethod.getResponseBodyAsString());
    	RestResult restResult = new RestResult(1, postMethod.toString());
    	return restResult;
    } 
    
    @RequestMapping("/url")
    public String url() {
    	System.out.println("url");
    	return "redirect:http://www.baidu.com";
    }
    
    
}
