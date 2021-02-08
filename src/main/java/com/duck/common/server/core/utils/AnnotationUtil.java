package com.duck.common.server.core.utils;

import com.duck.common.server.core.interceptors.Require;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 5duck
 * @date 2021-01-28
 */
public class AnnotationUtil {
    public AnnotationUtil() {
    }

    public static List<String> findRequired(Annotation[] annotations) {
        List<String> levelList = new ArrayList<>();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> aClass = annotation.annotationType();
            Require required = (Require) aClass.getAnnotation(Require.class);
            if (required != null) {
                levelList.add(required.level().getLevel());
            }
        }
        return levelList;
    }
}
