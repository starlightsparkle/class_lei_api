package com.znjz.class_lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.znjz.class_lei.common.entities.TblClass;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author znjz
 * @since 2021-12-19
 */
public interface TblClassService extends IService<TblClass> {
    /**
     * 新建课程
     * @param tblClass
     * @return
     */
    public int insertClass(TblClass tblClass);

    /**
     * 获取当前用户创建的课程
     * @return
     */
    public Object listWithCreateByMe(int pageNum,int pageSize);


    /**
     * 获取当前用户加入的课程
     * @return
     */
    public Object listClassesJoined(int page, int limit);

    /**
     * 选课
     * @param classId 课程id
     */
    public void joinClass(long classId);

    /**
     * 退课
     * @param classId 课程id
     */
    public void dropClass(long classId);




}
