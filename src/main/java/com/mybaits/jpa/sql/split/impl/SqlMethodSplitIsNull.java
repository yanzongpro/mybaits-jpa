package com.mybaits.jpa.sql.split.impl;

import com.mybaits.jpa.jpaEnum.KeyWord;
import com.mybaits.jpa.sql.split.ISqlMethodSplit;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理jpa 方法名关键字
 * Created by Administrator on 2019/12/20 0020.
 */
public class SqlMethodSplitIsNull implements ISqlMethodSplit {

    @Override
    public List<String> sqlMethodSplit(List<String> attributes) {
        List<String> cruxList=new ArrayList<>();
        for (String attribute : attributes) {
            if(attribute.equals(KeyWord.IsNotNull.getValue())){
                cruxList.add(attribute);
                continue;
            }
            if(attribute.indexOf(KeyWord.IsNull.getValue())==-1){
                cruxList.add(attribute);
                continue;
            }
            String[] cruxs=attribute.split(KeyWord.IsNull.getValue());
            if(cruxs.length>0){
                for (String s : cruxs) {
                    cruxList.add(s);
                    cruxList.add(KeyWord.IsNull.getValue());
                }
            }
        }
        return cruxList;
    }
}
