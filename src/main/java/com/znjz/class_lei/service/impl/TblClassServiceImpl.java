package com.znjz.class_lei.service.impl;

import com.znjz.class_lei.entity.TblClass;
import com.znjz.class_lei.mapper.TblClassMapper;
import com.znjz.class_lei.service.TblClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public int insertClass(TblClass tblClass) {
        return classMapper.insert(tblClass);
    }


}
