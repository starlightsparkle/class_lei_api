package com.znjz.class_lei.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znjz.class_lei.common.entities.TblUser;
import com.znjz.class_lei.mapper.TblUserMapper;
import com.znjz.class_lei.service.TblUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
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
        return save(tblUser);
    }
}
