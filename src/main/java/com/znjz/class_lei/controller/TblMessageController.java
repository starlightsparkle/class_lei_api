package com.znjz.class_lei.controller;


import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblMessage;
import com.znjz.class_lei.service.TblMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author znjz
 * @since 2021-12-23
 */
@RestController
@RequestMapping("/message")
@Api(tags = "消息接口")
public class TblMessageController extends BaseController {
    @Autowired
    private TblMessageService tblMessageService;

    @ApiOperation(value="获取当前用户的消息列表", notes="必须登陆")
    @GetMapping("/list")
    public ResultBody list()
    {
        return ResultBody.success(tblMessageService.getmessageList());
    }

}
