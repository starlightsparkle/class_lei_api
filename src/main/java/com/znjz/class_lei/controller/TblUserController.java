package com.znjz.class_lei.controller;


import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblUser;
import com.znjz.class_lei.service.TblUserService;
import io.swagger.annotations.Api;
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
@Api(value = "UserController|用户接口")
public class TblUserController extends BaseController {
    @Autowired
    private TblUserService tblUserService;
    @PostMapping("/register")
    public ResultBody register(@RequestBody TblUser tblUser)
    {
        return ResultBody.success(tblUserService.register(tblUser));
    }
    @GetMapping("/current")
    public ResultBody getCurrent()
    {
        return ResultBody.success(tblUserService.getCurrentUser());
    }
}
