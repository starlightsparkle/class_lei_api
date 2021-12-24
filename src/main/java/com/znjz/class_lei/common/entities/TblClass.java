package com.znjz.class_lei.common.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
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
     * 课程内容
     */
    private String classContent;


}
