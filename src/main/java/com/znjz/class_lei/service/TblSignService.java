package com.znjz.class_lei.service;

import com.znjz.class_lei.common.entities.ResultBody;
import com.znjz.class_lei.common.entities.TblSign;
import com.baomidou.mybatisplus.extension.service.IService;
import com.znjz.class_lei.common.entities.TblUser;

import java.util.List;

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
    /**
     *
     * @description 获取发起签到的列表
     * <br/>
     * @param classId
     * @return java.util.List<com.znjz.class_lei.common.entities.TblSign>
     * @author sjj
     * @date 2021/12/22 14:58
     */
    List<TblSign> signList(Long classId);
    /**
     *
     * @description 教师获取已完成签到的学生名单
     * <br/>
     * @param classSignId
     * @return java.util.List<com.znjz.class_lei.common.entities.TblUser>
     * @author sjj
     * @date 2021/12/22 14:58
     */
    List<TblUser> finishStudentlist(Long classSignId);
    /**
     *
     * @description 教师获取未完成签到的学生名单
     * <br/>
     * @param classSignId
     * @return java.util.List<com.znjz.class_lei.common.entities.TblUser>
     * @author sjj
     * @date 2021/12/22 14:58
     */
    List<TblUser> unfinishStudentlist(Long classSignId);
    /**
     *
     * @description 学生获取完成的签到列表
     * <br/>
     * @param classId
     * @return java.util.List<com.znjz.class_lei.common.entities.TblSign>
     * @author sjj
     * @date 2021/12/22 14:59
     */

    List<TblSign> finishSignList(Long classId);
    /**
     *
     * @description 学生获取未完成签到的列表
     * <br/>
     * @param classId
     * @return java.util.List<com.znjz.class_lei.common.entities.TblSign>
     * @author sjj
     * @date 2021/12/22 14:59
     */
    List<TblSign> unfinishSignList(Long classId);
    /**
     *
     * @description 教师修改学生的签到状态
     * <br/>
     * @param classSignId
     * @param userId
     * @param status
     * @return boolean
     * @author sjj
     * @date 2021/12/24 21:43
     */
    boolean exchangeSign(Long classSignId,Long userId,int status);
}
