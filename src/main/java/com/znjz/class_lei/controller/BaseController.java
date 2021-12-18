package com.znjz.class_lei.controller;

import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.errorHandler.CommonEnum;

public class BaseController {




    public ResultBody success(){
        return ResultBody.success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public ResultBody success(Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(CommonEnum.SUCCESS.getResultCode());
        rb.setMessage(CommonEnum.SUCCESS.getResultMsg());
        rb.setResult(data);
        return rb;
    }

    /**
     * 返回成功消息
     */
    public ResultBody success(String message)
    {
        return ResultBody.success(message);
    }

    /**
     * 返回失败消息
     */
    public ResultBody error(String message)
    {
        return ResultBody.error(message);
    }

    /**
     * 失败
     */
    public static ResultBody error(String code, String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }





}
