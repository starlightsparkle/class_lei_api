package com.znjz.class_lei.controller;

import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.errorHandler.BizException;
import com.znjz.class_lei.mapper.TblUserMapper;
import com.znjz.class_lei.service.QueueServer;
import com.znjz.class_lei.service.TblClassService;
import com.znjz.class_lei.service.TblUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "测试用接口")
@RestController
@RequestMapping("/text")
public class TestContoller extends BaseController {
    @Autowired
    private TblClassService tblClassService;
    @Autowired
    private TblUserMapper tblUserMapper;
    @Autowired
    private TblUserService tblUserService;
    @Autowired
    private QueueServer queueServer;


    @GetMapping("/test")
    public ResultBody test()
    {
        System.out.println(current());
        return ResultBody.success();
    }


    @ApiOperation(value="发送全局消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="msg",value="消息",dataType="string", paramType = "query",example = "发送了全局消息，所有人都看到了吗？"),
    })
    @PostMapping
    public ResultBody sendCommonMess(@RequestParam String msg)
    {
        queueServer.sendCommonMessage(msg);
        return ResultBody.success();
    }

    
    @ApiOperation(value="发送课程消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="rky",value="路由路径",dataType="string", paramType = "query",example="7"),
            @ApiImplicitParam(name="msg",value="消息",dataType="string", paramType = "query"),
    })
    @PostMapping("/send")
    public ResultBody sendMess(String msg,String rky)
    {
        queueServer.sendMessage(msg,rky);
        return ResultBody.success();
    }


    @GetMapping("/error")
    public ResultBody error()
    {
        throw new BizException("test");
    }


}
