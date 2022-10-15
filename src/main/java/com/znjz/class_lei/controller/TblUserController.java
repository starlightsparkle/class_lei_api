package com.znjz.class_lei.controller;


import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblUser;
import com.znjz.class_lei.service.TblUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author znjz
 * @since 2021-12-18
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class TblUserController extends BaseController {
    @Autowired
    private TblUserService tblUserService;

    @ApiOperation(value="注册")
    @PostMapping("/register")
    public ResultBody register(@RequestBody TblUser tblUser)
    {
        return ResultBody.success(tblUserService.register(tblUser));
    }

    //上转一个图片（base64）到ai服务器中
    @ApiOperation(value="人脸注册")
    @PostMapping("/faceRegister")
    public ResultBody registerFace()
    {

        return ResultBody.success();
    }
    //
    @ApiOperation(value="是否已经人脸注册")
    @GetMapping("/haveFaceRegister")
    public ResultBody isRegisteredFace()
    {

        return ResultBody.success(1);
    }


    @ApiOperation(value="获取当前用户信息")
    @GetMapping("/current")
    public ResultBody getCurrent()
    {
        return ResultBody.success(tblUserService.getCurrentUser());
    }
}
