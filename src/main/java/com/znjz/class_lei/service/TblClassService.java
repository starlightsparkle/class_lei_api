package com.znjz.class_lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.znjz.class_lei.entity.TblClass;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author znjz
 * @since 2021-12-19
 */
public interface TblClassService extends IService<TblClass> {
    public int insertClass(TblClass tblClass);
}
