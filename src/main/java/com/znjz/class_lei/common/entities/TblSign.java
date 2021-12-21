package com.znjz.class_lei.common.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import com.znjz.class_lei.common.entities.BaseEntity;
import io.swagger.models.auth.In;
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
public class TblSign extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 签到id
     */
    @TableId(value = "sign_id", type = IdType.AUTO)
    private Long signId;

    private Long classId;

    private Long userId;

    /**
     * 是否是教师的第一次签到
     */
    private Integer isTeacher;

    /**
     * 是gps签到还是普通签到 1为gps 0为二维码
     */
    private Integer signType;

    /**
     * 每一节课每一次签到的标识
     */
    private Long classSignId;

    /**
     * gps签到
     */
    private String locationXy;

    //签到名字
    @TableField(exist = false)
    private String signName;

    //签到状态
    @TableField(exist = false)
    private Integer status;

}
