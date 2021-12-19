package com.znjz.class_lei.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znjz.class_lei.common.entities.TblUser;
import com.znjz.class_lei.mapper.TblUserMapper;
import com.znjz.class_lei.service.TblUserService;
import org.springframework.stereotype.Service;

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

    @Override
    public TblUser getByUsername(String username) {
        QueryWrapper<TblUser> wrapper=new QueryWrapper<>();
        wrapper.eq("username",username);
        TblUser tblUser=getOne(wrapper);
        return tblUser;
    }
}
