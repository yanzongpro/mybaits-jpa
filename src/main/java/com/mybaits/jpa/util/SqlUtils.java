package com.mybaits.jpa.util;


import com.mybaits.jpa.jpaEnum.KeyWord;
import com.mybaits.jpa.sql.split.ISqlMethodSplit;
import com.mybaits.jpa.sql.split.impl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/12/20 0020.
 */
public class SqlUtils {


    /**
     * 首字母大写
     * @return
     */
    public static String firstCapitalization(String info){
        StringBuffer sb=new StringBuffer(info);
        if(info!=null&&info!=""&&info.length()>0){
            String charInfo=sb.substring(0,1);
            sb.replace(0,1,charInfo.toUpperCase());
            return sb.toString();
        }
        return info;
    }

    /**
     * 驼峰命名转换为下划线命名
     * @param para
     * @return
     */
    public static String HumpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        if (!para.contains("_")) {
            for(int i=0;i<para.length();i++){
                if(i!=0&&Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }


    /**
     * 验证参数是否合法
     * @param key 属性
     * @param type 条件类型
     * @param param 参数
     */
    public static void checkParam(String key,String type,Object param){
        if(KeyWord.And.getValue().equals(type)){
            if(param==null){
                throw new RuntimeException("列名"+key+" "+ KeyWord.And.getValue()+" 条件不能为null");
            }
        }
        if(KeyWord.Or.getValue().equals(type)){
            if(param==null){
                throw new RuntimeException("列名"+key+" "+ KeyWord.Or.getValue()+" 条件不能为null");
            }
        }
        if(KeyWord.In.getValue().equals(type)){
            if(param==null){
                throw new RuntimeException("列名"+key+" "+ KeyWord.In.getValue()+" 条件不能为null");
            }
        }
        if(KeyWord.NotIn.getValue().equals(type)){
            if(param==null){
                throw new RuntimeException("列名"+key+" "+ KeyWord.In.getValue()+" 条件不能为null");
            }
        }
        if(KeyWord.Like.getValue().equals(type)){
            if(param==null){
                throw new RuntimeException("列名"+key+" "+ KeyWord.Like.getValue()+" 条件不能为null");
            }
        }
        if(KeyWord.NotLike.getValue().equals(type)){
            if(param==null){
                throw new RuntimeException("列名"+key+" "+ KeyWord.NotLike.getValue()+" 条件不能为null");
            }
        }
        if(KeyWord.After.getValue().equals(type)){
            if(param==null){
                throw new RuntimeException("列名"+key+" "+ KeyWord.After.getValue()+" 条件不能为null");
            }
        }
        if(KeyWord.Before.getValue().equals(type)){
            if(param==null){
                throw new RuntimeException("列名"+key+" "+ KeyWord.Before.getValue()+" 条件不能为null");
            }
        }
        if(KeyWord.LessThan.getValue().equals(type)){
            if(param==null){
                throw new RuntimeException("列名"+key+" "+ KeyWord.LessThan.getValue()+" 条件不能为null");
            }
        }
        if(KeyWord.GreaterThan.getValue().equals(type)){
            if(param==null){
                throw new RuntimeException("列名"+key+" "+ KeyWord.GreaterThan.getValue()+" 条件不能为null");
            }
        }
    }

    /**
     * 获取方法关键字
     * @param mehtod 方法名称
     * @return 方法关键字集合
     */
    public static List<String> getKeyword(String mehtod){
        ISqlMethodSplit iSqlMethodSplit=null;
        List<String> keyWords=new ArrayList<>();
        keyWords.add(mehtod);
        if(mehtod.indexOf(KeyWord.And.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitAnd();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.NotLike.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitNotLike();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.Like.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitLike();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.IsNotNull.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitIsNotNull();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.IsNull.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitIsNull();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.NotIn.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitNotIn();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.In.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitIn();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.OrderBy.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitOrderBy();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.Or.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitOr();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.After.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitAfter();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.Before.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitBefore();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }

        if(mehtod.indexOf(KeyWord.LessThan.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitLessThan();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.GreaterThan.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitGreaterThan();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.Asc.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitAsc();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        if(mehtod.indexOf(KeyWord.Desc.getValue())!=-1){
            iSqlMethodSplit=new SqlMethodSplitDesc();
            keyWords=iSqlMethodSplit.sqlMethodSplit(keyWords);
        }
        return keyWords;
    }

}
