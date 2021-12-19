package com.znjz.class_lei.controller;

import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblClass;
import com.znjz.class_lei.common.entities.TblUser;
import com.znjz.class_lei.mapper.TblUserMapper;
import com.znjz.class_lei.service.TblClassService;
import com.znjz.class_lei.service.TblUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestContoller {
    @Autowired
    private TblClassService tblClassService;
    @Autowired
    private TblUserMapper tblUserMapper;
    @Autowired
    private TblUserService tblUserService;
    @GetMapping("/test")
    public ResultBody test()
    {
//        TblClass tblClass=new TblClass();
//        tblClass.setClassName("test")
//                .setCreateId(1L);
//        tblClassService.insertClass(tblClass);
//
        TblUser tblUser = new TblUser();
        tblUser.setUsername("dddd");
        tblUser.setPassword("111");
        tblUser.setRoleId(1l);
        tblUserService.save(tblUser);

        return ResultBody.success();
    }
}
