package com.znjz.class_lei.controller;


import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.entity.TblClass;
import com.znjz.class_lei.service.TblClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author znjz
 * @since 2021-12-19
 */
@RestController
@RequestMapping("/class")
public class TblClassController extends BaseController {
    @Autowired
    private TblClassService  tblClassService;


    @PostMapping("/add")
    public ResultBody add(@RequestBody TblClass tblClass){
        tblClass.setCreateId(1L);
        return success(tblClassService.insertClass(tblClass));
    }





}
