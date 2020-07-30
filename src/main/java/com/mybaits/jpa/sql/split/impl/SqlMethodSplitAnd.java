package com.mybaits.jpa.sql.split.impl;


import java.util.ArrayList;
import java.util.List;
import com.mybaits.jpa.jpaEnum.KeyWord;
import com.mybaits.jpa.sql.split.ISqlMethodSplit;
/**
 * 处理jpa 方法名关键字
 * Created by Administrator on 2019/12/20 0020.
 */
public class SqlMethodSplitAnd implements ISqlMethodSplit {

    @Override
    public List<String> sqlMethodSplit(List<String> attributes) {
        List<String> cruxList=new ArrayList<String>();
        for (String attribute : attributes) {
            if(attribute.indexOf(KeyWord.And.getValue())==-1){
                cruxList.add(attribute);
                continue;
            }
            String[] cruxs=attribute.split(KeyWord.And.getValue());
            if(cruxs.length>0){
                for (String s : cruxs) {
                    if(!s.equals("")){
                        cruxList.add(KeyWord.And.getValue());
                        cruxList.add(s);
                    }
                }
            }
        }
        return cruxList;
    }
}
