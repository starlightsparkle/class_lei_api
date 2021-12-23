package com.znjz.class_lei.service;

import com.znjz.class_lei.common.entities.TblMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author znjz
 * @since 2021-12-23
 */
public interface TblMessageService extends IService<TblMessage> {
   /**
    *
    * @description 获取当前用户的消息列表
    * <br/>
    * @param
    * @return java.util.List<com.znjz.class_lei.common.entities.TblMessage>
    * @author sjj
    * @date 2021/12/23 16:58
    */
    List<TblMessage> getmessageList();
}
