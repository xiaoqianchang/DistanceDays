package com.adups.distancedays.utils;

import java.util.Collection;

/**
 * $
 * <p>
 * Created by Chang.Xiao on 2019/10/12.
 *
 * @version 1.0
 */
public class ToolUtil {

    public static <T> boolean isEmptyCollects(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
}
