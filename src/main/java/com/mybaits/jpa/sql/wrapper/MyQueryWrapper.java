package com.mybaits.jpa.sql.wrapper;

import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Created by Administrator on 2019/12/24 0024.
 */
public class MyQueryWrapper<T> extends QueryWrapper<T>{



    private SharedString sqlSelect;

    public MyQueryWrapper() {
        this((T) null);
    }

    public MyQueryWrapper(T entity) {
        this.sqlSelect = new SharedString();
        super.setEntity(entity);
        super.initNeed();
    }



    public MyQueryWrapper(T entity, String... columns) {
        this.sqlSelect = new SharedString();
        super.setEntity(entity);
        super.initNeed();
        this.select(columns);
    }

    private MyQueryWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString lastSql, SharedString sqlComment) {
        this.sqlSelect = new SharedString();
        super.setEntity(entity);
        this.entityClass = entityClass;
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
    }


    public MyQueryWrapper(MyQueryWrapper<T> myQueryWrapper) {
        this.sqlSelect = new SharedString();
        super.setEntity(myQueryWrapper.getEntity());
        if(myQueryWrapper.getEntity()!=null){
            this.entityClass = (Class<T>) myQueryWrapper.getEntity().getClass();
        }
        this.paramNameSeq = myQueryWrapper.paramNameSeq;
        this.paramNameValuePairs = myQueryWrapper.getParamNameValuePairs();
        this.expression = myQueryWrapper.getExpression();
        this.lastSql = myQueryWrapper.lastSql;
        this.sqlComment = myQueryWrapper.sqlComment;
    }


    public MyQueryWrapper(MyQueryWrapper<T> myQueryWrapper1,MyQueryWrapper<T> myQueryWrapper2) {
        this.sqlSelect = new SharedString();
        super.setEntity(myQueryWrapper2.getEntity());
        if(myQueryWrapper1.getEntity()!=null){
            this.entityClass = (Class<T>) myQueryWrapper2.getEntity().getClass();
        }
        this.paramNameSeq = myQueryWrapper2.paramNameSeq;
        Set<String> sortSet = new TreeSet<String>((o1, o2) -> o1.compareTo(o2));
//        int a=myQueryWrapper2.getParamNameValuePairs().size();
//        for (String key : myQueryWrapper1.getParamNameValuePairs().keySet()) {
//            System.out.println(key.substring(0,key.length()-1)+""+a);
//            myQueryWrapper2.getParamNameValuePairs().put(key.substring(0,key.length()-1)+""+a,myQueryWrapper2.getParamNameValuePairs().get(key));
//            a++;
//        }
        myQueryWrapper2.getParamNameValuePairs().putAll(myQueryWrapper1.getParamNameValuePairs());
        this.paramNameValuePairs = myQueryWrapper2.getParamNameValuePairs();
//        myQueryWrapper2.getExpression().getNormal().add(SqlKeyword.AND);
//        for (ISqlSegment iSqlSegment : myQueryWrapper1.getExpression().getNormal()) {
//            myQueryWrapper2.getExpression().getNormal().add(iSqlSegment);
//        }
        myQueryWrapper2.getExpression().getNormal().add(SqlKeyword.AND);
        myQueryWrapper2.getExpression().add(myQueryWrapper1.getExpression());
        this.expression = myQueryWrapper2.getExpression();
        this.lastSql = myQueryWrapper2.lastSql;
        this.sqlComment = myQueryWrapper2.sqlComment;
    }



    public MyQueryWrapper<T> select(String... columns) {
        if(ArrayUtils.isNotEmpty(columns)) {
            this.sqlSelect.setStringValue(String.join(",", columns));
        }

        return (MyQueryWrapper)this.typedThis;
    }

    public MyQueryWrapper<T> select(Predicate<TableFieldInfo> predicate) {
        return this.select(this.entityClass, predicate);
    }

    public MyQueryWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        this.entityClass = entityClass;
        this.sqlSelect.setStringValue(TableInfoHelper.getTableInfo(this.getCheckEntityClass()).chooseSelect(predicate));
        return (MyQueryWrapper)this.typedThis;
    }

    public String getSqlSelect() {
        return this.sqlSelect.getStringValue();
    }

    public LambdaQueryWrapper<T> lambda() {
        return super.lambda();
    }

    public MyQueryWrapper<T> instance() {
        return new MyQueryWrapper(this.entity, this.entityClass, this.paramNameSeq, this.paramNameValuePairs, this.expression, SharedString.emptyString(), SharedString.emptyString());
    }



}
