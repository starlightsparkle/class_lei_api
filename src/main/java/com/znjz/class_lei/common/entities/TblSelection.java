package com.znjz.class_lei.common.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import com.znjz.class_lei.common.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author znjz
 * @since 2021-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TblSelection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 选课id
     */
    @TableId(value = "select_id", type = IdType.AUTO)
    private Long selectId;

    /**
     * 选课人id
     */
    private Long userId;

    /**
     * 课程id
     */
    private Long classId;

    /**
     * 新建时间
     */
    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;


}
