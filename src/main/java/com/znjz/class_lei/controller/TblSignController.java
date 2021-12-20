package com.znjz.class_lei.controller;


import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblSign;
import com.znjz.class_lei.service.TblSignService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
@Api
public class TblSignController extends BaseController {
    @Autowired
    private TblSignService tblSignService;
    @PostMapping("/start")
    public ResultBody start(@RequestParam("classId") Long classId,@RequestParam("signType") Integer signType,@RequestParam(value = "locationXy",required = false) String locationXy,@RequestParam("time") Long time)
    {
        TblSign tblSign=new TblSign();
        tblSign.setClassId(classId)
                .setSignType(signType)
                .setLocationXy(locationXy);
        return tblSignService.startSign(tblSign,time);
    }
    @DeleteMapping("/end")
    public ResultBody end(Long classSignId)
    {
        return ResultBody.success(tblSignService.endSign(classSignId));
    }
}
