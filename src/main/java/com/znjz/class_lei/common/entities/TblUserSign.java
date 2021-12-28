package com.znjz.class_lei.common.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TblUserSign implements Serializable {
    private static final long serialVersionUID = 1L;
    private String locationXy;
    private String username;
    private Long  userId;

}
