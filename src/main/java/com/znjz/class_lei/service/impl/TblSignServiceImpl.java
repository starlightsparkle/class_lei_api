package com.znjz.class_lei.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblClass;
import com.znjz.class_lei.common.entities.TblSign;
import com.znjz.class_lei.common.errorHandler.BizException;
import com.znjz.class_lei.mapper.TblSignMapper;
import com.znjz.class_lei.service.TblClassService;
import com.znjz.class_lei.service.TblSignService;
import com.znjz.class_lei.service.TblUserService;
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
}
