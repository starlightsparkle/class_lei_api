package com.znjz.class_lei.mapper;

import com.znjz.class_lei.common.entities.TblUser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author znjz
 * @since 2021-12-18
 */
@Mapper
public interface TblUserMapper extends BaseMapper<TblUser> {
    List<TblUser> unfinishUserList(Long classSignId,Long classId);
}
