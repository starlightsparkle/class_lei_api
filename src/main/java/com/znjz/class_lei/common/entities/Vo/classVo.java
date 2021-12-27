package com.znjz.class_lei.common.entities.Vo;

import com.znjz.class_lei.common.entities.TblSelection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class classVo extends TblSelection {


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
