package com.springmvc.api;

import com.springmvc.enums.ResultType;
import com.springmvc.menu.BaseModel;
import com.springmvc.utils.BeanUtils;
import com.springmvc.utils.StrUtil;

/**
 * 微信API响应报文对象基类
 *
 */
public class BaseResponse extends BaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8378254415751971L;
	private String errcode;
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        String result = this.errmsg;
        //将接口返回的错误信息转换成中文，方便提示用户出错原因
        if (StrUtil.isNotBlank(this.errcode) && !ResultType.SUCCESS.getCode().toString().equals(this.errcode)) {
            ResultType resultType = ResultType.get(this.errcode);
            if(BeanUtils.nonNull(resultType)) {
                result = resultType.getDescription();
            }
        }
        return result;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
