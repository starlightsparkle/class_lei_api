package com.znjz.class_lei.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znjz.class_lei.common.entities.*;
import com.znjz.class_lei.common.errorHandler.BizException;
import com.znjz.class_lei.mapper.TblSignMapper;
import com.znjz.class_lei.mapper.TblUserMapper;
import com.znjz.class_lei.service.*;
import com.znjz.class_lei.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            queueServer.sendMessage(String.valueOf(tblSign.getClassSignId()),String.valueOf(tblSign.getClassId()));
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
    public List<TblSign> signList(Long classId) {
        Long userId=tblUserService.getCurrentUser().getUserId();
        TblClass tblClass=tblClassService.getById(classId);
        if(tblClass==null||!tblClass.getCreateId().equals(userId))
        {
            throw new BizException("不是课堂的创建者，无法查看发起的签到列表");
        }
        QueryWrapper<TblSign> wrapper=new QueryWrapper();
        wrapper.eq("class_id",classId).eq("is_teacher",1);
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
                    "月"+tblSign.getGmtCreated().getDayOfMonth()+"日"+tblSign.getGmtCreated().getHour()+"点"+tblSign.getGmtCreated().getMonthValue()+"分的签到";
            tblSign.setSignName(name);
        }
        return tblSignList;
    }

    @Override
    public List<TblUser> finishStudentlist(Long classSignId) {
        QueryWrapper<TblSign> wrapper1=new QueryWrapper<>();
        wrapper1.eq("class_sign_id",classSignId).eq("is_teacher",1);
        TblSign tblSign=getOne(wrapper1);
        if(!tblSign.getUserId().equals(tblUserService.getCurrentUser().getUserId()))
        {
            throw new BizException("不是课堂的创建者，无法查看已完成签到表");
        }
        QueryWrapper<TblUser> wrapper=new QueryWrapper<>();
        wrapper.inSql("user_id","select user_id  from tbl_sign where class_sign_id = '"+classSignId+"' and is_teacher = '0'");
        return tblUserService.list(wrapper);
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
        wrapper1.eq("class_id",classId).eq("user_id",tblUserService.getCurrentUser().getUserId());
        TblSelection tblSelection=tblSelectionService.getOne(wrapper1);
        if(tblSelection==null)
        {
            throw new BizException("未加入课堂，无法查看已完成签到列表");
        }
        QueryWrapper<TblSign> wrapper=new QueryWrapper<>();
        wrapper.eq("class_id",classId).eq("user_id",tblUserService.getCurrentUser().getUserId());
        return list(wrapper);
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
        wrapper.eq("is_teacher",1).eq("class_id",classId).notInSql("class_sign_id","select class_sign_id from tbl_sign where class_id = '"+classId+"' and user_id = '"+tblUserService.getCurrentUser().getUserId()+"'");
        return list(wrapper);
    }
}
