package com.mybaits.jpa.annotation;

import com.mybaits.jpa.jpaEnum.QueryOrderType;
import com.mybaits.jpa.jpaEnum.QueryType;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/11/15 0015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Documented
public @interface PageQuery {

    /**
     * 是否是分页查询参数
     * @return 默认false
     */
    public boolean isQuery() default true;

    /**
     * 查询分页映射到哪个属性（驼峰命名属性）
     * @return 默认为空字符串（不做属性映射）
     */
    public String column() default "";

    /**
     * 分页查询类型
     * @return 默认等于
     */
    public QueryType queryType() default QueryType.EQ;


    /**
     * 排序方式
     * @return 默认不排序
     */
    public QueryOrderType queryOrderType() default QueryOrderType.NOSORT;

    /**
     * 排序顺序
     * @return 默认为首个排序
     */
    public int orderByNumber() default 1;




}
