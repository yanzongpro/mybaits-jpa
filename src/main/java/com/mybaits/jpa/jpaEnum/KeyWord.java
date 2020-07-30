package com.mybaits.jpa.jpaEnum;

/**
 * Created by Administrator on 2019/12/20 0020.
 */
public enum  KeyWord {

    findBy("findBy"),
    deleteBy("deleteBy"),
    countBy("countBy"),
    And("And"),
    Like("Like"),
    NotLike("NotLike"),
    IsNull("IsNull"),
    IsNotNull("IsNotNull"),
    In("In"),
    NotIn("NotIn"),
    OrderBy("OrderBy"),
    Or("Or"),
    After("After"),
    Before("Before"),
    LessThan("LessThan"),
    GreaterThan("GreaterThan"),
    Asc("Asc"),
    Desc("Desc");
    private String value;//中文描述

    private KeyWord(String value){
        this.value=value;
    }

    /**
     * 定义方法,返回描述,跟常规类的定义没区别
     * @return
     */
    public String getValue(){
        return value;
    }
}
