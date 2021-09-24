package com.wwstation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author william
 * @description
 * @Date: 2021-08-29 23:44
 */
@Getter
@AllArgsConstructor
public enum PA {
    久坐(1.00,1.00),
    低运动量(1.11,1.12),
    适中运动量(1.25,1.27),
    高运动量(1.48,1.45),
    ;
    private Double male;
    private Double female;
}
