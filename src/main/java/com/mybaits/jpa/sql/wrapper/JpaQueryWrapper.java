package com.mybaits.jpa.sql.wrapper;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybaits.jpa.annotation.PageQuery;
import com.mybaits.jpa.jpaEnum.KeyWord;
import com.mybaits.jpa.jpaEnum.QueryOrderType;
import com.mybaits.jpa.jpaEnum.QueryType;
import com.mybaits.jpa.util.SqlUtils;
import org.apache.commons.collections.map.HashedMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 查询sql构造器
 * T : 实体
 */
public class JpaQueryWrapper<T> {


    /**
     * 根据泛型对象反射查询分页条件属性
     * @param t 分页对象
     * @return
     */
    public QueryWrapper<T> getPageInfoQueryWrapper(T t){
        QueryWrapper<T> queryWrapper=new QueryWrapper<T>();
        try{
            Field[] fields = t.getClass().getDeclaredFields();
            List<PageQuery> pageOrder=new ArrayList<>();
            Map<PageQuery,String> columns=new HashedMap();
            for(Field field: fields){
                String name = field.getName();
                field.setAccessible(true);
                Object resultValue = field.get(t);
                PageQuery pageQuery=field.getAnnotation(PageQuery.class);
                if(pageQuery!=null){
                    if(pageQuery.column()!=null&&pageQuery.column()!=""&&pageQuery.column().length()>0){
                        name=pageQuery.column();
                    }
                    if(!pageQuery.queryOrderType().getValue().equals(QueryOrderType.NOSORT.getValue())){
                        columns.put(pageQuery,name);
                        pageOrder.add(pageQuery);
                    }
                    if(resultValue==null){
                        continue;
                    }
                    if(!pageQuery.isQuery()){
                        continue;
                    }
                    if(QueryType.EQ.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.eq(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.NE.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.ne(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.GT.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.gt(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.GE.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.ge(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.LT.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.lt(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.LE.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.le(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.LIKE.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.like(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.NOTLIKE.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.notLike(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.LIKELEFT.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.likeLeft(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.LIKERIGHT.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.likeRight(SqlUtils.HumpToUnderline(name),resultValue);
                    }
                    continue;
                }else{
                    if(resultValue==null){
                        continue;
                    }
                    TableField tableField=field.getAnnotation(TableField.class);
                    if(tableField!=null){
                        if(tableField.exist()){
                            if(resultValue instanceof String){
                                queryWrapper.like(SqlUtils.HumpToUnderline(name),resultValue);
                            }else {
                                queryWrapper.eq(SqlUtils.HumpToUnderline(name),resultValue);
                            }
                            continue;
                        }
                    }else{
                        if(resultValue instanceof String){
                            queryWrapper.like(SqlUtils.HumpToUnderline(name),resultValue);
                        }else {
                            queryWrapper.eq(SqlUtils.HumpToUnderline(name),resultValue);
                        }
                        continue;
                    }
                }
            }
            pageOrder.sort((a, b) -> a.orderByNumber() - b.orderByNumber());
            for (int i = 0; i < pageOrder.size();i++) {
                PageQuery query = pageOrder.get(i);
                if(query.queryOrderType().getValue().equals(QueryOrderType.ASC.getValue())){
                    queryWrapper.orderByAsc(SqlUtils.HumpToUnderline(columns.get(query)));
                }else{
                    queryWrapper.orderByDesc(SqlUtils.HumpToUnderline(columns.get(query)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return queryWrapper;
    }



    /**
     * 根据泛型对象反射查询分页条件属性
     * @param t 分页对象
     * @return
     */
    public QueryWrapper<T> getPageInfoQueryWrapper(T t, QueryWrapper<T> queryWrapper){
        try{
            Field[] fields = t.getClass().getDeclaredFields();
            List<PageQuery> pageOrder=new ArrayList<>();
            Map<PageQuery,String> columns=new HashedMap();
            for(Field field: fields){
                String name = field.getName();
                field.setAccessible(true);
                Object resultValue = field.get(t);
                PageQuery pageQuery=field.getAnnotation(PageQuery.class);
                if(pageQuery!=null){
                    if(pageQuery.column()!=null&&pageQuery.column()!=""&&pageQuery.column().length()>0){
                        name=pageQuery.column();
                    }
                    if(!pageQuery.queryOrderType().getValue().equals(QueryOrderType.NOSORT.getValue())){
                        columns.put(pageQuery,name);
                        pageOrder.add(pageQuery);
                    }
                    if(resultValue==null){
                        continue;
                    }
                    if(!pageQuery.isQuery()){
                        continue;
                    }
                    if(QueryType.EQ.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.eq(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.NE.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.ne(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.GT.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.gt(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.GE.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.ge(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.LT.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.lt(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.LE.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.le(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.LIKE.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.like(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.NOTLIKE.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.notLike(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.LIKELEFT.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.likeLeft(SqlUtils.HumpToUnderline(name),resultValue);
                    }else if(QueryType.LIKERIGHT.getValue().equals(pageQuery.queryType().getValue())){
                        queryWrapper.likeRight(SqlUtils.HumpToUnderline(name),resultValue);
                    }
                    continue;
                }else{
                    if(resultValue==null){
                        continue;
                    }
                    TableField tableField=field.getAnnotation(TableField.class);
                    if(tableField!=null){
                        if(tableField.exist()){
                            if(resultValue instanceof String){
                                queryWrapper.like(SqlUtils.HumpToUnderline(name),resultValue);
                            }else {
                                queryWrapper.eq(SqlUtils.HumpToUnderline(name),resultValue);
                            }
                            continue;
                        }
                    }else{
                        if(resultValue instanceof String){
                            queryWrapper.like(SqlUtils.HumpToUnderline(name),resultValue);
                        }else {
                            queryWrapper.eq(SqlUtils.HumpToUnderline(name),resultValue);
                        }
                        continue;
                    }
                }
            }
            pageOrder.sort((a, b) -> a.orderByNumber() - b.orderByNumber());
            for (int i = 0; i < pageOrder.size();i++) {
                PageQuery query = pageOrder.get(i);
                if(query.queryOrderType().getValue().equals(QueryOrderType.ASC.getValue())){
                    queryWrapper.orderByAsc(SqlUtils.HumpToUnderline(columns.get(query)));
                }else{
                    queryWrapper.orderByDesc(SqlUtils.HumpToUnderline(columns.get(query)));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return queryWrapper;
    }


    /**
     * 获取查询构造器
     * @param keyWords 方法解析关键字集合
     * @param attributes
     * @param params
     * @return
     */
    public QueryWrapper<T> getQueryWrapper(List<String> keyWords,Map<String,String> attributes,Object...params){
        QueryWrapper<T> queryWrapper=new QueryWrapper<T>();
        setQueryWrapper(queryWrapper,keyWords,attributes,params);
        return queryWrapper;
    }

    /**
     * 获取查询构造器
     * @param keyWords 方法解析关键字集合
     * @param attributes
     * @param params
     * @return
     */
    public QueryWrapper<T> getQueryWrapper(QueryWrapper<T> queryWrapper,List<String> keyWords,Map<String,String> attributes,Object...params){
        setQueryWrapper(queryWrapper,keyWords,attributes,params);
        return queryWrapper;
    }


    /**
     * 设置构造器参数
     * @param queryWrapper 构造器
     * @param keyWords 关键字集合
     * @param attributes 属性集合
     * @param params 参数
     */
    public void setQueryWrapper(QueryWrapper<T> queryWrapper,List<String> keyWords,Map<String,String> attributes,Object...params){
        int paramIndex=0;
        for (int i = 0; i < keyWords.size(); i++) {
            String keyWord= keyWords.get(i);
            Object param=null;
            if(params.length>paramIndex){
                param=params[paramIndex];
            }
            if(KeyWord.And.getValue().equals(keyWord)){
                String key=keyWords.get(i+1);
                key=attributes.get(key);
                if(i+2>=keyWords.size()){
                    SqlUtils.checkParam(key, KeyWord.And.getValue(),param);
                    queryWrapper.eq(SqlUtils.HumpToUnderline(key),param);
                    paramIndex++;
                    continue;
                }else{
                    setParamInfo(queryWrapper,key,keyWords.get(i+2),param);
                    i++;
                    paramIndex++;
                    continue;
                }
            }else if (KeyWord.Or.getValue().equals(keyWord)){
                String key=keyWords.get(i+1);
                key=attributes.get(key);
                if(i+2>=keyWords.size()){
                    SqlUtils.checkParam(key, KeyWord.Or.getValue(),param);
                    queryWrapper.or().eq(SqlUtils.HumpToUnderline(key),param);
                    paramIndex++;
                    continue;
                }else{
                    setParamInfo(queryWrapper,key,keyWords.get(i+2),param);
                    i++;
                    paramIndex++;
                    continue;
                }
            }else if(KeyWord.OrderBy.getValue().equals(keyWord)){
                String key=keyWords.get(i+1);
                key=attributes.get(key);
                if(i+2>=keyWords.size()){
                    queryWrapper.orderByAsc(SqlUtils.HumpToUnderline(key));
                    paramIndex++;
                    continue;
                }else{
                    if(KeyWord.Asc.getValue().equals(keyWords.get(i+2))){
                        queryWrapper.orderByAsc(SqlUtils.HumpToUnderline(key));
                        i++;
                        paramIndex++;
                        continue;
                    }else if(KeyWord.Desc.getValue().equals(keyWords.get(i+2))){
                        queryWrapper.orderByDesc(SqlUtils.HumpToUnderline(key));
                        i++;
                        paramIndex++;
                        continue;
                    }
                }
            }
        }
    }

    /**
     * 设置查询参数
     * @param queryWrapper 构造器
     * @param key 字段
     * @param keyWord2 条件
     * @param param 参数
     */
    public void setParamInfo(QueryWrapper<T> queryWrapper,String key,String keyWord2,Object param){
        if(KeyWord.And.getValue().equals(keyWord2)){
            SqlUtils.checkParam(key, KeyWord.And.getValue(),param);
            queryWrapper.eq(SqlUtils.HumpToUnderline(key),param);
        }else if(KeyWord.NotLike.getValue().equals(keyWord2)){
            SqlUtils.checkParam(key, KeyWord.NotLike.getValue(),param);
            queryWrapper.notLike(SqlUtils.HumpToUnderline(key),param);
        }else if(KeyWord.Like.getValue().equals(keyWord2)){
            SqlUtils.checkParam(key, KeyWord.Like.getValue(),param);
            queryWrapper.like(SqlUtils.HumpToUnderline(key),param);
        }else if(KeyWord.After.getValue().equals(keyWord2)){
            SqlUtils.checkParam(key, KeyWord.After.getValue(),param);
            queryWrapper.gt(SqlUtils.HumpToUnderline(key),param);
        }else if(KeyWord.Before.getValue().equals(keyWord2)){
            SqlUtils.checkParam(key, KeyWord.Before.getValue(),param);
            queryWrapper.lt(SqlUtils.HumpToUnderline(key),param);
        }else if(KeyWord.LessThan.getValue().equals(keyWord2)){
            SqlUtils.checkParam(key, KeyWord.LessThan.getValue(),param);
            queryWrapper.le(SqlUtils.HumpToUnderline(key),param);
        }else if(KeyWord.GreaterThan.getValue().equals(keyWord2)){
            SqlUtils.checkParam(key, KeyWord.GreaterThan.getValue(),param);
            queryWrapper.ge(SqlUtils.HumpToUnderline(key),param);
        }else if(KeyWord.IsNotNull.getValue().equals(keyWord2)){
            queryWrapper.isNotNull(SqlUtils.HumpToUnderline(key));
        }else if(KeyWord.IsNull.getValue().equals(keyWord2)){
            queryWrapper.isNull(SqlUtils.HumpToUnderline(key));
        }else if(KeyWord.In.getValue().equals(keyWord2)){
            SqlUtils.checkParam(key, KeyWord.In.getValue(),param);
            Collection collection=(Collection<?>) param;
            if(collection.size()>0){
                queryWrapper.in(SqlUtils.HumpToUnderline(key),(Collection<?>) param);
            }
        }else if(KeyWord.NotIn.getValue().equals(keyWord2)){
            SqlUtils.checkParam(key, KeyWord.NotIn.getValue(),param);
            Collection collection=(Collection<?>) param;
            if(collection.size()>0){
                queryWrapper.notIn(SqlUtils.HumpToUnderline(key),(Collection<?>) param);
            }
        }else{
            SqlUtils.checkParam(key, KeyWord.And.getValue(),param);
            queryWrapper.eq(SqlUtils.HumpToUnderline(key),param);
        }
    }

}
