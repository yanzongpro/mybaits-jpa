package com.mybaits.jpa.annotation;

import com.mybaits.jpa.jpaEnum.LinkLv;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/11/17 0017.
 * 连表查询
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface LinkSelect {

    /**
     * 是否连表查询
     * @return
     */
    public boolean isSelect() default true;

    /**
     * 连表查询几级
     * @return
     */
    public LinkLv linkLv() default LinkLv.ONE;



}
