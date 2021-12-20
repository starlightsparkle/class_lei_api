package com.znjz.class_lei.controller;

import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblClass;
import com.znjz.class_lei.common.entities.TblUser;
import com.znjz.class_lei.common.errorHandler.BizException;
import com.znjz.class_lei.mapper.TblUserMapper;
import com.znjz.class_lei.service.QueueServer;
import com.znjz.class_lei.service.TblClassService;
import com.znjz.class_lei.service.TblUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping
    public ResultBody sendCommonMess(@RequestParam String msg)
    {
        queueServer.sendCommonMessage(msg);
        return ResultBody.success();
    }


    @PostMapping("/send")
    public ResultBody sendCommonMess(String msg,String rky)
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
