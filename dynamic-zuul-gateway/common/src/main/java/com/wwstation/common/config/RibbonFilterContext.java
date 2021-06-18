package com.wwstation.common.config;

import lombok.Data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author william
 * @description
 * @Date: 2021-06-04 14:45
 */
@Data
public class RibbonFilterContext {

    private final Map<String, String> attributes = new HashMap<>();

    public RibbonFilterContext add(String key, String value) {
        attributes.put(key, value);
        return this;
    }

    public String get(String key) {
        return attributes.get(key);
    }

    public RibbonFilterContext remove(String key) {
        attributes.remove(key);
        return this;
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }
}
