package com.mybaits.jpa.sql.split.impl;

import com.mybaits.jpa.jpaEnum.KeyWord;
import com.mybaits.jpa.sql.split.ISqlMethodSplit;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理jpa 方法名关键字
 * Created by Administrator on 2019/12/20 0020.
 */
public class SqlMethodSplitLike implements ISqlMethodSplit {

    @Override
    public List<String> sqlMethodSplit(List<String> attributes) {
        List<String> cruxList=new ArrayList<>();
        for (String attribute : attributes) {
            if(attribute.equals(KeyWord.NotLike.getValue())){
                cruxList.add(attribute);
                continue;
            }
            if(attribute.indexOf(KeyWord.Like.getValue())==-1){
                cruxList.add(attribute);
                continue;
            }
            String[] cruxs=attribute.split(KeyWord.Like.getValue());
            if(cruxs.length>0){
                for (String s : cruxs) {
                    cruxList.add(s);
                    cruxList.add(KeyWord.Like.getValue());
                }
            }
        }
        return cruxList;
    }
}
