package com.znjz.class_lei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.znjz.class_lei.common.entities.TblUserSign;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TblUserSignMapper extends BaseMapper<TblUserSign> {
    List<TblUserSign> getFinishStudent(Long classSignId);
}
