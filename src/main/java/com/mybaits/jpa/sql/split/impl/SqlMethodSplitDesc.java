package com.mybaits.jpa.sql.split.impl;

import com.mybaits.jpa.jpaEnum.KeyWord;
import com.mybaits.jpa.sql.split.ISqlMethodSplit;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理jpa 方法名关键字
 * Created by Administrator on 2019/12/20 0020.
 */
public class SqlMethodSplitDesc implements ISqlMethodSplit {

    @Override
    public List<String> sqlMethodSplit(List<String> attributes) {
        List<String> cruxList=new ArrayList<>();
        for (String attribute : attributes) {
            if(attribute.indexOf(KeyWord.Desc.getValue())==-1){
                cruxList.add(attribute);
                continue;
            }
            String[] cruxs=attribute.split(KeyWord.Desc.getValue());
            if(cruxs.length>0){
                for (String s : cruxs) {
                    cruxList.add(s);
                    cruxList.add(KeyWord.Desc.getValue());
                }
            }
        }
        return cruxList;
    }
}
