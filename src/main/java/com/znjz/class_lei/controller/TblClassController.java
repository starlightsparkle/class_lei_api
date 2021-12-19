package com.znjz.class_lei.controller;


import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblClass;
import com.znjz.class_lei.service.TblClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author znjz
 * @since 2021-12-19
 */
@Api(value = "ClassController|课程接口")
@RestController
@RequestMapping("/class")
public class TblClassController extends BaseController {
    @Autowired
    private TblClassService  tblClassService;

    @ApiOperation(value="添加一个课程", notes="应当不添加修改时间和创建时间")
    @ApiModelProperty()
    @PostMapping("/add")
    public ResultBody add(@RequestBody TblClass tblClass){
        tblClass.setCreateId(current().getUserId());
        return success(tblClassService.insertClass(tblClass));
    }

    @ApiOperation(value="获取我建的课程列表", notes="应当不添加修改时间和创建时间")
    @GetMapping("/listByMe")
    public ResultBody listWithCreateByMe(){

        return success(tblClassService.listWithCreateByMe());
    }























}
