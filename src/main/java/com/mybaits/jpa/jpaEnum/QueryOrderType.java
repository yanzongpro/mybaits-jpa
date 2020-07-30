package com.mybaits.jpa.jpaEnum;

/**
 * Created by Administrator on 2019/11/15 0015.
 */
public enum QueryOrderType {

    NOSORT("NOSORT"),
    ASC("ASC"),
    DESC("DESC");
    private String value;//中文描述

    private QueryOrderType(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }
}
