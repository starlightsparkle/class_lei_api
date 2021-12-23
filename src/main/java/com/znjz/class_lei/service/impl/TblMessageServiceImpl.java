package com.znjz.class_lei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.znjz.class_lei.common.entities.TblMessage;
import com.znjz.class_lei.common.entities.TblSelection;
import com.znjz.class_lei.common.entities.TblUser;
import com.znjz.class_lei.mapper.TblMessageMapper;
import com.znjz.class_lei.service.TblMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znjz.class_lei.service.TblSelectionService;
import com.znjz.class_lei.service.TblUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author znjz
 * @since 2021-12-23
 */
@Service
public class TblMessageServiceImpl extends ServiceImpl<TblMessageMapper, TblMessage> implements TblMessageService {

    @Autowired
    private TblUserService tblUserService;
    @Autowired
    private TblSelectionService tblSelectionService;
    @Override
    public List<TblMessage> getmessageList() {
        TblUser tblUser=tblUserService.getCurrentUser();
        QueryWrapper<TblSelection> tblSelectionQueryWrapper =new QueryWrapper<>();
        tblSelectionQueryWrapper.eq("user_id",tblUser.getUserId());
        List<TblSelection> tblSelections=tblSelectionService.list(tblSelectionQueryWrapper);
        List<Long> classIds=new ArrayList<>();
        for (TblSelection tblSelection : tblSelections) {
            classIds.add(tblSelection.getClassId());
        }
        QueryWrapper<TblMessage> tblMessageQueryWrapper=new QueryWrapper<>();
        classIds.add(0L);
        tblMessageQueryWrapper.eq("message_type",1).or()
                .in("message_receive",classIds).orderByDesc("gmt_created");
        return list(tblMessageQueryWrapper);
    }
}
