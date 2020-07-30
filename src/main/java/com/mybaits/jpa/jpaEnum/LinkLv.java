package com.mybaits.jpa.jpaEnum;

/**
 * Created by Administrator on 2019/11/17 0017.
 */
public enum LinkLv {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    All(1000);
    private int value;//中文描述

    private LinkLv(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }


}
