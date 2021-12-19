package com.znjz.class_lei.controller;

import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblClass;
import com.znjz.class_lei.service.TblClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestContoller {
    @Autowired
    private TblClassService tblClassService;
    @GetMapping("/test")
    public ResultBody test()
    {
        TblClass tblClass=new TblClass();
        tblClass.setClassName("test")
                .setCreateId(1L);
        tblClassService.insertClass(tblClass);
        return ResultBody.success(tblClassService.list());
    }
}
