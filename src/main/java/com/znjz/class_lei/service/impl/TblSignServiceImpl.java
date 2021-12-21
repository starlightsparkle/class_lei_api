package com.znjz.class_lei.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblClass;
import com.znjz.class_lei.common.entities.TblSelection;
import com.znjz.class_lei.common.entities.TblSign;
import com.znjz.class_lei.common.errorHandler.BizException;
import com.znjz.class_lei.mapper.TblSignMapper;
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
}
