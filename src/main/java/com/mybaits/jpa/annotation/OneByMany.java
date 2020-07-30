package com.mybaits.jpa.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/11/17 0017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Documented
public @interface OneByMany {

    /**
     * 映射目标实体
     * @return 默认无实体
     */
    public  Class aimEntity() default void.class;


    /**
     * 注解实体映射字段
     *
     * @return 默认id
     */
    public String field() default "id";


    /**
     * 目标实体映射字段
     * @return 默认无映射
     */
    public String aimField() default "";



}
