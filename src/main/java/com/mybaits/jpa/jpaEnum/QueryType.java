package com.mybaits.jpa.jpaEnum;

/**
 * 查询条件枚举
 * Created by Administrator on 2019/11/15 0015.
 */
public enum QueryType {

    EQ("="),
    NE("!="),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    LIKE("like"),
    LIKELEFT("likeLeft"),
    LIKERIGHT("likeRight"),
    NOTLIKE("notLike");
    private String value;//中文描述

    private QueryType(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }
}
