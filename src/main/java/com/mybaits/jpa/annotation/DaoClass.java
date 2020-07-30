package com.mybaits.jpa.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/11/17 0017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
public @interface DaoClass {

    public Class daoClass() default void.class;

}
