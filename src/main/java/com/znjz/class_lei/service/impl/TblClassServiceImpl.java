package com.znjz.class_lei.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znjz.class_lei.common.entities.TblClass;
import com.znjz.class_lei.mapper.TblClassMapper;
import com.znjz.class_lei.service.TblClassService;
import com.znjz.class_lei.service.TblUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author znjz
 * @since 2021-12-19
 */
@Service
public class TblClassServiceImpl extends ServiceImpl<TblClassMapper, TblClass> implements TblClassService {
    @Autowired
    private TblClassMapper classMapper;
    @Autowired
    private TblUserService tblUserService;

    @Override
    public int insertClass(TblClass tblClass) {
        return classMapper.insert(tblClass);
    }

    @Override
    public List<TblClass> listWithCreateByMe() {
        Map<String, Object> params = new HashMap<>();
        QueryWrapper<TblClass> query = Wrappers.query();
        query.eq("create_id", tblUserService.getCurrentUser().getUserId());
        query.orderByDesc("gmt_created");
        return list(query);

    }


}
