package com.znjz.class_lei.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.znjz.class_lei.common.entities.TblUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author znjz
 * @since 2021-12-18
 */
public interface TblUserService extends IService<TblUser> {
    /**
     *
     * @description 根据用户名获取用户详细信息
     * <br/>
     * @param username
     * @return com.znjz.class_lei.common.entities.TblUser
     * @author sjj
     * @date 2021/12/20 14:53
     */
    TblUser getByUsername(String username);
    /**
     *
     * @description 获取当前用户
     * <br/>
     * @param
     * @return com.znjz.class_lei.common.entities.TblUser
     * @author sjj
     * @date 2021/12/20 14:54
     */
    TblUser getCurrentUser();
    /**
     *
     * @description 注册用户
     * <br/>
     * @param tblUser
     * @return com.znjz.class_lei.common.entities.TblUser
     * @author sjj
     * @date 2021/12/20 14:55
     */
    boolean register(TblUser tblUser);
}
