package com.znjz.class_lei.common.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public  class TblSelection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 选课id
     */
    private Long selectId;

    /**
     * 选课人id
     */
    private Long userId;

    /**
     * 课程id
     */
    private Long classId;




}
