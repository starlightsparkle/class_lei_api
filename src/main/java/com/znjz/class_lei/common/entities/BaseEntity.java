package com.znjz.class_lei.common.entities;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {



	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime gmtCreate;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime gmtModified;

	private Integer statu;
}
