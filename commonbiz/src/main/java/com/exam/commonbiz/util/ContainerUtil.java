package com.exam.commonbiz.util;

import java.util.Collection;

/**
 * @Author yuexingxing
 * @time 2020/6/15
 */
public class ContainerUtil {

    public static <V> boolean isEmpty(Collection<V> c) {
        return (c == null || c.size() == 0);
    }
}
