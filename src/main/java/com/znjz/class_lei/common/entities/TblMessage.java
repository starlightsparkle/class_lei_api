package com.znjz.class_lei.common.entities;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.znjz.class_lei.common.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author znjz
 * @since 2021-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TblMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id 
     */
    @TableId(value = "message_id", type = IdType.AUTO)
    private Long messageId;

    /**
     * 是否是全局消息 1为全局 0为课内消息
     */
    private Long messageType;

    /**
     * 接受人 *为全局 或者为课id 现在只有两种消息一个是全局另一个是课内消息
     */
    private Long messageReceive;

    /**
     * 标题
     */
    private String messageTitle;

    /**
     * 内容
     */
    private String messageContent;




}
