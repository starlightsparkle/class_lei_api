package com.znjz.class_lei.controller;

import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.utils.QRCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "二维码生成接口")
@RequestMapping("/images")
public class QRCodeController {

    @ApiOperation(value="获取二维码")
    /**
     * 根据 content 生成二维码
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    @GetMapping("/getQRCodeBase64")
    public ResultBody getQRCode(@RequestParam("content") String content,
                                @RequestParam(value = "logoUrl", required = false) String logoUrl,
                                @RequestParam(value = "width", required = false) Integer width,
                                @RequestParam(value = "height", required = false) Integer height) {
        return ResultBody.success(QRCodeUtil.getBase64QRCode(content, logoUrl));
    }
}
