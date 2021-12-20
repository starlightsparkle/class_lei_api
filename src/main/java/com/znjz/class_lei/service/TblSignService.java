package com.znjz.class_lei.service;

import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblSign;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author znjz
 * @since 2021-12-19
 */
public interface TblSignService extends IService<TblSign> {
    ResultBody startSign(TblSign tblSign, Long time);
    boolean endSign(Long classSignId);
}
