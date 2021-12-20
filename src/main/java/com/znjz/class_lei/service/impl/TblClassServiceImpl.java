package com.znjz.class_lei.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znjz.class_lei.common.entities.TblClass;
import com.znjz.class_lei.common.entities.TblSelection;
import com.znjz.class_lei.common.entities.TblUser;
import com.znjz.class_lei.mapper.TblClassMapper;
import com.znjz.class_lei.mapper.TblSelectionMapper;
import com.znjz.class_lei.service.TblClassService;
import com.znjz.class_lei.service.TblUserService;
import com.znjz.class_lei.utils.RabbitUtils;
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
    private RabbitUtils rabbitUtils;
    @Autowired
    private TblClassMapper classMapper;
    @Autowired
    private TblUserService tblUserService;
    @Autowired
    private TblSelectionMapper selectionMapper;

    /**
     * 获取当前用户
     * @return 当前用户
     */
    private TblUser currentUser(){
        return tblUserService.getCurrentUser();
    }

    /**
     * 新建课程
     * @param tblClass
     * @return
     */
    @Override
    public int insertClass(TblClass tblClass) {
        classMapper.insert(tblClass);//新建
        //加入消息队列路由
        rabbitUtils.createOrBindQueue(currentUser().getUserId().toString(),tblClass.getClassId().toString());
        return 1;
    }

    @Override
    public List<TblClass> listWithCreateByMe() {
        Map<String, Object> params = new HashMap<>();
        QueryWrapper<TblClass> query = Wrappers.query();
        query.eq("create_id", tblUserService.getCurrentUser().getUserId());
        query.orderByDesc("gmt_created");
        return list(query);
    }

    @Override
    public void joinClass(long classId) {
        TblSelection tblSelection = new TblSelection();
        TblSelection selection = tblSelection.setClassId(classId).setUserId(currentUser().getUserId());
        selectionMapper.insert(selection);
        rabbitUtils.createOrBindQueue(currentUser().getUserId().toString(),String.valueOf(classId));
    }

    @Override
    public void dropClass(long classId){
        QueryWrapper<TblSelection> query = Wrappers.query();
        selectionMapper.delete(query.eq("class_id",classId));
        rabbitUtils.deleterkey(currentUser().getUserId().toString(),
                String.valueOf(classId));
    }




}
