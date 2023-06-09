package com.znjz.class_lei.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblSign;
import com.znjz.class_lei.service.TblSignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author znjz
 * @since 2021-12-19
 */

@RestController
@RequestMapping("/sign")
@Api(tags = "签到接口")
public class TblSignController extends BaseController {
    @Autowired
    private TblSignService tblSignService;

    @ApiOperation("开始签到")
    @PostMapping("/start")
    public ResultBody start(@RequestParam("classId") Long classId,@RequestParam("signType") Integer signType,@RequestParam(value = "locationXy",required = false) String locationXy,@RequestParam("time") Long time)
    {
        TblSign tblSign=new TblSign();
        tblSign.setClassId(classId)
                .setSignType(signType)
                .setLocationXy(locationXy);
        return tblSignService.startSign(tblSign,time);
    }

    @ApiOperation("结束签到")
    @DeleteMapping("/end")
    public ResultBody end(Long classSignId)
    {
        return ResultBody.success(tblSignService.endSign(classSignId));
    }

    @ApiOperation("签到")
    @PostMapping("/sign")
    public ResultBody sign(@RequestParam("classSignId") Long classSignId,
                           @RequestParam("signType") Integer signType,@RequestParam(value = "locationXy",required = false) String locationXy)
    {
        return ResultBody.success(tblSignService.sign(classSignId,signType,locationXy));
    }

    @ApiOperation("人脸签到")
    @PostMapping("/faceSign")
    public ResultBody sign(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam("classSignId") Long classSignId,
            @RequestParam("signType") Integer signType) throws UnirestException, JsonProcessingException {
        System.out.println(file.getName()+" "+classSignId+" "+signType);
        tblSignService.faceSign(classSignId,signType,file);
        return ResultBody.success();
    }

    @ApiOperation("人脸签到text")
    @PostMapping("/faceSigntext")
    public ResultBody signTEXT(
            @RequestParam(value = "file") MultipartFile file) throws UnirestException {
        tblSignService.text(file);
        return ResultBody.success();
    }

    @ApiOperation("获取发起的签到列表")
    @GetMapping("/list")
    public ResultBody list(Long classId)
    {
        return ResultBody.success(tblSignService.signList(classId));
    }
    @ApiOperation("获取某一次签到的完成学生")
    @GetMapping("/finishstudent")
    public ResultBody finishStudent(Long classSignId)
    {
        return ResultBody.success(tblSignService.finishStudentlist(classSignId));
    }
    @ApiOperation("获取某一次签到的未完成学生")
    @GetMapping("/unfinishstudent")
    public ResultBody unfinishStudent(Long classSignId)
    {
        return ResultBody.success(tblSignService.unfinishStudentlist(classSignId));
    }
    @ApiOperation("获取已完成签到列表")
    @GetMapping("/finishsign")
    public ResultBody finishSign(Long classId)
    {
        return ResultBody.success(tblSignService.finishSignList(classId));
    }
    @ApiOperation("获取未完成签到列表")
    @GetMapping("/unfinishsign")
    public ResultBody unfinishSign(Long classId)
    {
        return ResultBody.success(tblSignService.unfinishSignList(classId));
    }
    @ApiOperation("更改签到状态")
    @PostMapping("/exchange")
    public ResultBody exchange(Long classSignId,Long userId,int status)
    {
        return ResultBody.success(tblSignService.exchangeSign(classSignId,userId,status));
    }
}
