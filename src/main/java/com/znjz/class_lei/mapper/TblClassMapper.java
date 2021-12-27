package com.znjz.class_lei.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.znjz.class_lei.common.entities.TblClass;
import com.znjz.class_lei.common.entities.TblSelection;
import com.znjz.class_lei.common.entities.Vo.classVo;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author znjz
 * @since 2021-12-19
 */

public interface TblClassMapper extends BaseMapper<TblClass> {

    public List<classVo> selectClassWitchSelected(long user_id);

}
