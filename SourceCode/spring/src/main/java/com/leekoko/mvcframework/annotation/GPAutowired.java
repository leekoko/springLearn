package com.leekoko.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * 创建注解
 */
@Target({ElementType.FIELD})   //使用位置
@Retention(RetentionPolicy.RUNTIME)  //生命周期
@Documented
public @interface GPAutowired {
    String value() default "";
}
