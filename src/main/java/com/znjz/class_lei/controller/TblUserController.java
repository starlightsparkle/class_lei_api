package com.znjz.class_lei.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author znjz
 * @since 2021-12-18
 */

@Api(value = "text")
@RestController
@RequestMapping("/user")
public class TblUserController extends BaseController {


    @ApiOperation(value="根据用户编号获取用户姓名", notes="test: 仅1和2有正确返回")
    @ApiImplicitParam(paramType="query", name = "userNumber", value = "用户编号", required =false, dataType = "Integer")
    @GetMapping("/getUserName")
    public String getUserName(@RequestParam Integer userNumber){
        if(userNumber == 1){
            return "张三丰";
        }
        else if(userNumber == 2){
            return "慕容复";
        }
        else{
            return "未知";
        }
    }


}
