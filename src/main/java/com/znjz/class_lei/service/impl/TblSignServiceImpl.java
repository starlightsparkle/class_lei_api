package com.znjz.class_lei.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.znjz.class_lei.common.entities.*;
import com.znjz.class_lei.common.errorHandler.BizException;
import com.znjz.class_lei.mapper.TblSignMapper;
import com.znjz.class_lei.mapper.TblUserMapper;
import com.znjz.class_lei.mapper.TblUserSignMapper;
import com.znjz.class_lei.service.*;
import com.znjz.class_lei.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author znjz
 * @since 2021-12-19
 */
@Service
public class TblSignServiceImpl extends ServiceImpl<TblSignMapper, TblSign> implements TblSignService {

    @Autowired
    private TblClassService tblClassService;
    @Autowired
    private TblUserService tblUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private QueueServer queueServer;
    @Autowired
    private TblSelectionService tblSelectionService;
    @Autowired
    private TblUserMapper tblUserMapper;
    @Autowired
    private TblMessageService tblMessageService;
    @Autowired
    private TblUserSignMapper tblUserSignMapper;
    @Override
    public ResultBody startSign(TblSign tblSign, Long time) {
        tblSign.setUserId(tblUserService.getCurrentUser().getUserId());
        TblClass tblClass=tblClassService.getById(tblSign.getClassId());
        if(tblClass!=null&&tblSign.getUserId().equals(tblClass.getCreateId()))
        {
            Long classSignId= RandomUtil.randomLong();
            redisUtil.set(String.valueOf(classSignId),time,time*60);
            tblSign.setClassSignId(classSignId);
            tblSign.setIsTeacher(1);
            save(tblSign);
            String msg=tblSign.getSignType()+","+String.valueOf(tblSign.getClassSignId());
            queueServer.sendMessage(msg,String.valueOf(tblSign.getClassId()));
            String messageTitle="签到通知";
            String messageContent=tblClass.getClassName()+"的签到开始了！快去签到吧。";
            TblMessage tblMessage=new TblMessage();
            tblMessage.setMessageContent(messageContent)
                    .setMessageReceive(tblClass.getClassId())
                    .setMessageTitle(messageTitle)
                    .setMessageType(0L);
            tblMessageService.save(tblMessage);
            return ResultBody.success();
        }
        throw new BizException("不是当前班级用户，无法发起签到");
    }

    @Override
    public boolean endSign(Long classSignId) {
        QueryWrapper<TblSign> wrapper=new QueryWrapper<>();
        wrapper.eq("class_sign_id",classSignId);
        List<TblSign> tblSign=list(wrapper);
        TblClass tblClass=tblClassService.getById(tblSign.get(0).getClassId());
        if(tblClass==null||!tblClass.getCreateId().equals(tblUserService.getCurrentUser().getUserId()))
        {
            throw new BizException("不是当前班级用户，无法结束签到");
        }
        else if (!redisUtil.hasKey(String.valueOf(classSignId)))
        {
            throw new BizException("签到已经结束，无法停止");
        }
        else
        {
            redisUtil.del(String.valueOf(classSignId));
            return true;
        }
    }

    @Override
    public boolean sign(Long classSignId,Integer signType,String locationXy) {
        QueryWrapper<TblSign> wrapper=new QueryWrapper<>();
        wrapper.eq("class_sign_id",classSignId);
        List<TblSign> tblSign=list(wrapper);
        QueryWrapper<TblSelection> wrapper1=new QueryWrapper<>();
        wrapper1.eq("user_id",tblUserService.getCurrentUser().getUserId()).eq("class_id",tblSign.get(0).getClassId());
        wrapper.eq("user_id",tblUserService.getCurrentUser().getUserId());
        if(getOne(wrapper)!=null)
        {
            throw new BizException("已经完成签到，无需签到！");
        }
        if(tblSelectionService.getOne(wrapper1)==null)
        {
            throw new BizException("当前没有选这门课，无法进行签到！");
        }
        else if(!redisUtil.hasKey(String.valueOf(classSignId)))
        {
            throw new BizException("当前签到已经结束，无法签到！");
        }
        else
        {
            TblSign tblSignnew =new TblSign();
            tblSignnew.setSignType(signType)
                    .setLocationXy(locationXy)
                    .setUserId(tblUserService.getCurrentUser().getUserId())
                    .setIsTeacher(0)
                    .setClassId(tblSign.get(0).getClassId())
                    .setClassSignId(classSignId);
            save(tblSignnew);
            return true;
        }
    }

    @Override
    public boolean faceSign(Long classSignId, Integer signType, MultipartFile file) throws UnirestException, JsonProcessingException {
        File toFile = transferToFile(file);
        HttpResponse<String> response = request(toFile);
        Long cur = tblUserService.getCurrentUser().getUserId();

        ObjectMapper objMapper=new ObjectMapper();//jackson提供工具类
        String strBody=null;
        System.out.println("ai结果为："+response.getBody());
        Res respons=null;
        //3.把strBody转化成java类
            respons=objMapper.readValue(response.getBody(), Res.class);

        Long value=-1L;
        System.out.println(respons);
        try{
             value = Long.valueOf(respons.getUrls());
        }catch (Exception e){
            throw new UnirestException("未知注册的人脸");
        }
        if (value!=cur)
            throw new UnirestException("是id为的"+value+"未知注册的人脸");
        System.out.println("ai识别为 "+value);
        sign(classSignId,signType,"");
        return true;
    }



    @Override
    public void text(MultipartFile file) throws UnirestException {
        File toFile = transferToFile(file);
        HttpResponse<String> response = request(toFile);
        System.out.println(response.getBody());

    }

    //请求注册
    public HttpResponse<String> request(File file) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse response = Unirest.post("http://139.9.89.11:5000/a")
                .header("User-Agent", "apifox/1.0.0 (https://www.apifox.cn)")
                .field("file", file)
                .asString();
        return response;
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

    @Override
    public List<TblSign> signList(Long classId) {
        Long userId=tblUserService.getCurrentUser().getUserId();
        TblClass tblClass=tblClassService.getById(classId);
//        if(tblClass==null||!tblClass.getCreateId().equals(userId))
//        {
//            throw new BizException("不是课堂的创建者，无法查看发起的签到列表");
//        }
        QueryWrapper<TblSign> wrapper=new QueryWrapper();
        wrapper.eq("class_id",classId).eq("is_teacher",1).orderByDesc("gmt_created");
        List<TblSign> tblSignList=list(wrapper);
        for (TblSign tblSign : tblSignList) {
            if(redisUtil.hasKey(String.valueOf(tblSign.getClassSignId())))
            {
                tblSign.setStatus(1);
            }
            else
            {
                tblSign.setStatus(0);
            }
            String name=tblSign.getGmtCreated().getYear()+"年"+tblSign.getGmtCreated().getMonthValue()+
                    "月"+tblSign.getGmtCreated().getDayOfMonth()+"日"+tblSign.getGmtCreated().getHour()+"点"+tblSign.getGmtCreated().getMinute()+"分的签到";
            tblSign.setSignName(name);
        }
        return tblSignList;
    }




    @Override
    public List<TblUserSign> finishStudentlist(Long classSignId) {
        QueryWrapper<TblSign> wrapper1=new QueryWrapper<>();
        wrapper1.eq("class_sign_id",classSignId).eq("is_teacher",1);
        TblSign tblSign=getOne(wrapper1);
        if(!tblSign.getUserId().equals(tblUserService.getCurrentUser().getUserId()))
        {
            throw new BizException("不是课堂的创建者，无法查看已完成签到表");
        }
//        QueryWrapper<TblUser> wrapper=new QueryWrapper<>();
//        wrapper.inSql("user_id","select user_id  from tbl_sign where class_sign_id = '"+classSignId+"' and is_teacher = '0'");
//        return tblUserService.list(wrapper);
        return tblUserSignMapper.getFinishStudent(classSignId);
    }

    @Override
    public List<TblUser> unfinishStudentlist(Long classSignId) {
        QueryWrapper<TblSign> wrapper1=new QueryWrapper<>();
        wrapper1.eq("class_sign_id",classSignId).eq("is_teacher",1);
        TblSign tblSign=getOne(wrapper1);
        if(!tblSign.getUserId().equals(tblUserService.getCurrentUser().getUserId()))
        {
            throw new BizException("不是课堂的创建者，无法查看未完成签到表");
        }
        QueryWrapper<TblSign> wrapper=new QueryWrapper<>();
        wrapper.eq("class_sign_id",classSignId);
        List<TblSign> tblSignList=list(wrapper);
        return tblUserMapper.unfinishUserList(classSignId,tblSignList.get(0).getClassId());
    }

    @Override
    public List<TblSign> finishSignList(Long classId) {
        QueryWrapper<TblSelection> wrapper1=new QueryWrapper<>();
        wrapper1.eq("class_id",classId).eq("user_id",tblUserService.getCurrentUser().getUserId()).orderByDesc("gmt_created");
        TblSelection tblSelection=tblSelectionService.getOne(wrapper1);
        if(tblSelection==null)
        {
            throw new BizException("未加入课堂，无法查看已完成签到列表");
        }
        QueryWrapper<TblSign> wrapper=new QueryWrapper<>();
        wrapper.eq("class_id",classId).eq("user_id",tblUserService.getCurrentUser().getUserId()).orderByDesc("gmt_created");;
        List<TblSign> tblSignList= list(wrapper);
        for (TblSign tblSign : tblSignList) {
            if(redisUtil.hasKey(String.valueOf(tblSign.getClassSignId())))
            {
                tblSign.setStatus(1);
            }
            else
            {
                tblSign.setStatus(0);
            }
            String name=tblSign.getGmtCreated().getYear()+"年"+tblSign.getGmtCreated().getMonthValue()+
                    "月"+tblSign.getGmtCreated().getDayOfMonth()+"日"+tblSign.getGmtCreated().getHour()+"点"+tblSign.getGmtCreated().getMinute()+"分的签到";
            tblSign.setSignName(name);
        }
        return tblSignList;
    }

    @Override
    public List<TblSign> unfinishSignList(Long classId) {
        QueryWrapper<TblSelection> wrapper1=new QueryWrapper<>();
        wrapper1.eq("class_id",classId).eq("user_id",tblUserService.getCurrentUser().getUserId());
        TblSelection tblSelection=tblSelectionService.getOne(wrapper1);
        if(tblSelection==null)
        {
            throw new BizException("未加入课堂，无法查看未签到列表");
        }
        QueryWrapper<TblSign> wrapper=new QueryWrapper<>();
        wrapper.eq("is_teacher",1).eq("class_id",classId).notInSql("class_sign_id","select class_sign_id from tbl_sign where class_id = '"+classId+"' and user_id = '"+tblUserService.getCurrentUser().getUserId()+"'").orderByDesc("gmt_created");;
        List<TblSign> tblSignList= list(wrapper);
        for (TblSign tblSign : tblSignList) {
            if(redisUtil.hasKey(String.valueOf(tblSign.getClassSignId())))
            {
                tblSign.setStatus(1);
            }
            else
            {
                tblSign.setStatus(0);
            }
            String name=tblSign.getGmtCreated().getYear()+"年"+tblSign.getGmtCreated().getMonthValue()+
                    "月"+tblSign.getGmtCreated().getDayOfMonth()+"日"+tblSign.getGmtCreated().getHour()+"点"+tblSign.getGmtCreated().getMinute()+"分的签到";
            tblSign.setSignName(name);
        }
        return tblSignList;
    }

    @Override
    public boolean exchangeSign(Long classSignId, Long userId,int status) {
        Long currentUserId=tblUserService.getCurrentUser().getUserId();
        QueryWrapper<TblSign> wrapper1=new QueryWrapper<>();
        wrapper1.eq("class_sign_id",classSignId).eq("is_teacher",1);
        TblSign tblSign=getOne(wrapper1);
        if(tblSign==null||!tblSign.getUserId().equals(currentUserId))
        {
            throw new BizException("不是当前签到的发起人，不允许修改签到状态");
        }
        QueryWrapper<TblSign> wrapper=new QueryWrapper<>();
        wrapper.eq("class_sign_id",classSignId).eq("user_id",userId);
        TblSign tblSign1=getOne(wrapper);
        if(status==0)
        {
            if(tblSign1==null)
            {
                throw new BizException("该学生未签到，无法将状态改成未签到状态！");
            }
            else
            {
                removeById(tblSign1.getSignId());
                return true;
            }
        }
        else
        {
            QueryWrapper<TblSelection> wrapper2=new QueryWrapper<>();
            wrapper2.eq("class_id",tblSign.getClassId()).eq("user_id",userId);
            TblSelection tblSelection=tblSelectionService.getOne(wrapper2);
            if(tblSelection==null)
            {
                throw new BizException("该学生未选课，无法更改签到状态！");
            }
            else if(tblSign1!=null)
            {
                throw new BizException("该学生已经签到，无需签到！");
            }
            else
            {
                TblSign tblSign2=new TblSign();
                tblSign2.setClassSignId(classSignId)
                        .setClassId(tblSign.getClassId())
                        .setSignType(tblSign.getSignType())
                        .setLocationXy(tblSign.getLocationXy())
                        .setUserId(userId);
                save(tblSign2);
                return true;
            }
        }

    }
}
