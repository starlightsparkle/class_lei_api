package com.znjz.class_lei.service.impl;




import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.znjz.class_lei.common.entities.TblUser;
import com.znjz.class_lei.config.AiConfig;
import com.znjz.class_lei.mapper.TblUserMapper;
import com.znjz.class_lei.service.TblUserService;
import com.znjz.class_lei.utils.RabbitUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author znjz
 * @since 2021-12-18
 */
@Service
public class TblUserServiceImpl extends ServiceImpl<TblUserMapper, TblUser> implements TblUserService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    RabbitUtils rabbitUtils;

    //是否已经注册
    @Override
    public boolean isRegisteredFace() {
        Long userId = getCurrentUser().getUserId();

        return false;
    }

    @Override
    public HttpResponse<String> registerFace(MultipartFile file) throws UnirestException {

        File toFile = transferToFile(file);
        return request(toFile);
    }

    public File transferToFile(MultipartFile multipartFile) {
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file=File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    //请求注册
    public HttpResponse<String> request(File file) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse response = Unirest.post("http://139.9.89.11:5000/register")
                .header("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                .field("file", file)
                .field("num", 1)
                .field("name", getCurrentUser().getUserId())
                .asString();

        HttpResponse response1 = Unirest.post("http://114.132.240.69:5000/embedding ")
                .header("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                .asString();
        return response;
    }

    @Override
    public TblUser getByUsername(String username) {
        QueryWrapper<TblUser> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username);
        TblUser tblUser=getOne(wrapper);
        return tblUser;
    }

    @Override
    public TblUser getCurrentUser() {
        //获取当前用户名
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取当前用户
        return getByUsername(username);
    }

    @Override
    public boolean register(TblUser tblUser) {
        //设置密码
        String password=passwordEncoder.encode(tblUser.getPassword());
        tblUser.setPassword(password);
        save(tblUser);
        String queueName=String.valueOf(tblUser.getUserId());
        rabbitUtils.createOrBindQueue(queueName,"common");
        return true;
    }
}
