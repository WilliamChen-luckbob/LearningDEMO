package com.infiai.webcommon.utils.reflect;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 动态反射扫描的类型枚举
 * @author william
 * @description
 * @Date: 2021-09-10 16:37
 */
@Getter
@AllArgsConstructor
public enum DynamicFieldScanTypeEnum {
    /**
     * 默认使用方式，见到就抓取
     */
    DEFAULT("","默认见到就抓取"),

    /**
     * 扫描的属性今后将被用于redis的key
     */
    SCAN_4_REDIS_KEY("scan4RedisKey","扫描这些属性值，结果最终会被拼装并作为redis的key"),
    ;
    /**
     * 枚举代码
     */
    private String code;
    /**
     * 枚举描述
     */
    private String desc;
}

