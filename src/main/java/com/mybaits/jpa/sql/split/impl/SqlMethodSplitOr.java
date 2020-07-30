package com.mybaits.jpa.sql.split.impl;

import com.mybaits.jpa.jpaEnum.KeyWord;
import com.mybaits.jpa.sql.split.ISqlMethodSplit;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理jpa 方法名关键字
 * Created by Administrator on 2019/12/20 0020.
 */
public class SqlMethodSplitOr implements ISqlMethodSplit {

    @Override
    public List<String> sqlMethodSplit(List<String> attributes) {
        List<String> cruxList=new ArrayList<>();
        for (String attribute : attributes) {
            if(attribute.equals(KeyWord.OrderBy.getValue())){
                cruxList.add(attribute);
                continue;
            }
            if(attribute.indexOf(KeyWord.Or.getValue())==-1){
                cruxList.add(attribute);
                continue;
            }
            String[] cruxs=attribute.split(KeyWord.Or.getValue());
            if(cruxs.length>0){
                int index=0;
                for (String s : cruxs) {
                    if(index!=0){
                        cruxList.add(KeyWord.Or.getValue());
                    }
                    cruxList.add(s);
                    index++;
                }
            }
        }
        return cruxList;
    }
}
