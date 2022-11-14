package com.dugujiujian.java_utils.main.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: zhouwei
 * @Date: 2022-11-10
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface EnumValue {
}
