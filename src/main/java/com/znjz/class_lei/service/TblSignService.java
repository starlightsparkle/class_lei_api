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
    /**
     *
     * @description 开始签到
     * <br/>
     * @param tblSign
     * @param time
     * @return com.znjz.class_lei.common.entities.ResultBody
     * @author sjj
     * @date 2021/12/21 15:21
     */
    ResultBody startSign(TblSign tblSign, Long time);
    /**
     *
     * @description 结束签到
     * <br/>
     * @param classSignId
     * @return boolean
     * @author sjj
     * @date 2021/12/21 15:22
     */
    boolean endSign(Long classSignId);
    /**
     *
     * @description 签到
     * <br/>
     * @param classSignId
     * @return boolean
     * @author sjj
     * @date 2021/12/21 15:22
     */
    boolean sign(Long classSignId,Integer signType,String locationXy);
}
