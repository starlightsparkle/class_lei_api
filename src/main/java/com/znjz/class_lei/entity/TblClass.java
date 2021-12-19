package com.znjz.class_lei.entity;

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
public class TblClass extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 课堂id
     */
    @TableId(value = "class_id", type = IdType.AUTO)
    private Long classId;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 课程名
     */
    private String className;

    /**
     * 新建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更改时间
     */
    private LocalDateTime gmtModified;


}
