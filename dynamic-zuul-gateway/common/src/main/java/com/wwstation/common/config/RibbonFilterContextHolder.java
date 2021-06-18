package com.wwstation.common.config;

/**
 * @author william
 * @description
 * @Date: 2021-06-04 14:46
 */
public class RibbonFilterContextHolder {
    private static final ThreadLocal<RibbonFilterContext> contextHolder = new InheritableThreadLocal<RibbonFilterContext>() {
        @Override
        protected RibbonFilterContext initialValue() {
            return new RibbonFilterContext();
        }
    };

    public static RibbonFilterContext getCurrentContext() {
        return contextHolder.get();
    }

    public static void setCurrentContext(RibbonFilterContext context) {
        contextHolder.set(context);
    }

    public static void clearCurrentContext() {
        contextHolder.remove();
    }
}
