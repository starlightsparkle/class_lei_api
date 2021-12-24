package com.znjz.class_lei.controller;


import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblClass;
import com.znjz.class_lei.service.TblClassService;
import io.swagger.annotations.*;
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
@Api(tags = "课程接口")
@RestController
@RequestMapping("/class")
public class TblClassController extends BaseController {
    @Autowired
    private TblClassService  tblClassService;


    @ApiOperation(value="添加一个课程", notes="应当不添加修改时间和创建时间")
    @PostMapping("/add")
    public ResultBody add(@RequestBody TblClass tblClass){

        tblClass.setCreateId(current().getUserId());
        return success(tblClassService.insertClass(tblClass));
    }


    @ApiOperation(value="加入一个课程", notes="必须登陆")
    @PostMapping("/join")
    public ResultBody joinClass(long classId){
        tblClassService.joinClass(classId);
        return success();
    }


    @ApiOperation(value="退出一个课程", notes="必须登陆")
    @DeleteMapping("/delete")
    public ResultBody dropClass(long classId){
        tblClassService.dropClass(classId);
        return success();
    }


    @ApiOperation(value="获取我建的课程列表", notes="分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum",value="页号(从一开始)",dataType="Integer", paramType = "query",example="1"),
            @ApiImplicitParam(name="pageSize",value="分页大小（一页有几个）",dataType="Integer", paramType = "query",example = "10"),
    })
    @GetMapping("/listByMe")
    public ResultBody listCreateByMe(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return success(tblClassService.listWithCreateByMe(pageNum, pageSize));
    }


    @ApiOperation(value="获取我选的课程列表", notes="分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum",value="页号(从一开始)",dataType="Integer", paramType = "query",example="1"),
            @ApiImplicitParam(name="pageSize",value="分页大小（一页有几个）",dataType="Integer", paramType = "query",example = "10"),
            })
    @GetMapping("/list")
    public ResultBody Classjoined(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize){
        return success(tblClassService.listClassesJoined(pageNum,pageSize));
    }




























}
