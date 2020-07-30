package com.mybaits.jpa.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybaits.jpa.jpaEnum.KeyWord;
import com.mybaits.jpa.sql.wrapper.JpaQueryWrapper;
import com.mybaits.jpa.util.PageInfoHelp;
import com.mybaits.jpa.util.SqlUtils;
import org.apache.commons.collections.map.HashedMap;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/11/13 0013.
 */
public class MyBatisJapService<D extends BaseMapper<T>, T extends PageInfoHelp> extends ServiceImpl<D,T> {


    JpaQueryWrapper jpaQueryWrapper=new JpaQueryWrapper();
    /**
     * 查询分页
     * @param t 泛型分页对象
     * @return
     */
    public PageInfo<T> jpaPageInfo(T t){
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        PageInfoHelp.setDefault(t);
        PageHelper.startPage(t.getPageNum(), t.getPageSize());
        QueryWrapper<T> queryWrapper=jpaQueryWrapper.getPageInfoQueryWrapper(t);
        List<T> infos = baseMapper.selectList(queryWrapper);
        LinkTableSelect<T> linkTableSelect=new LinkTableSelect<T>();
        linkTableSelect.linkSelect(stackTrace[1],infos);
        return new PageInfo<T>(infos);
    }
    /**
     * 查询分页
     * @param t 泛型分页对象
     * @return
     */
    public PageInfo<T> jpaPageInfo(T t, QueryWrapper<T> queryWrapper){
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        PageInfoHelp.setDefault(t);
        PageHelper.startPage(t.getPageNum(), t.getPageSize());
        queryWrapper=jpaQueryWrapper.getPageInfoQueryWrapper(t,queryWrapper);
        List<T> infos = baseMapper.selectList(queryWrapper);
        LinkTableSelect<T> linkTableSelect=new LinkTableSelect<T>();
        linkTableSelect.linkSelect(stackTrace[1],infos);
        return new PageInfo<T>(infos);
    }


    /**
     * 单个查询
     * @param params 查询参数
     * @return
     */
    public int jpaCount(Object...params){
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        String methodName = stackTrace[1].getMethodName();
        methodName=methodName.replace(KeyWord.countBy.getValue(), KeyWord.And.getValue());
        Map<String,String> attributes=new HashedMap();
        methodName=setMethodAttribute(methodName,attributes);
        List<String> keyWords= SqlUtils.getKeyword(methodName);
        QueryWrapper<T> queryWrapper=jpaQueryWrapper.getQueryWrapper(keyWords,attributes,params);
        int  count=baseMapper.selectCount(queryWrapper);
        return count;
    }

    /**
     * 单个查询
     * @param params 查询参数
     * @return
     */
    public int jpaCount(QueryWrapper<T> queryWrapper,Object...params){
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        String methodName = stackTrace[1].getMethodName();
        methodName=methodName.replace(KeyWord.countBy.getValue(), KeyWord.And.getValue());
        Map<String,String> attributes=new HashedMap();
        methodName=setMethodAttribute(methodName,attributes);
        List<String> keyWords=SqlUtils.getKeyword(methodName);
        queryWrapper=jpaQueryWrapper.getQueryWrapper(queryWrapper,keyWords,attributes,params);
        int  count=baseMapper.selectCount(queryWrapper);
        return count;
    }





    /**
     * 单个查询
     * @param params 查询参数
     * @return
     */
    public T jpaFindByOne(Object...params){
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        String methodName = stackTrace[1].getMethodName();
        methodName=methodName.replace(KeyWord.findBy.getValue(), KeyWord.And.getValue());
        Map<String,String> attributes=new HashedMap();
        methodName=setMethodAttribute(methodName,attributes);
        List<String> keyWords=SqlUtils.getKeyword(methodName);
        QueryWrapper<T> queryWrapper=jpaQueryWrapper.getQueryWrapper(keyWords,attributes,params);
        T t=baseMapper.selectOne(queryWrapper);
        LinkTableSelect<T> linkTableSelect=new LinkTableSelect<T>();
        linkTableSelect.linkSelect(stackTrace[1],t);
        return t;
    }


    /**
     * 单个查询
     * @param params 查询参数
     * @return
     */
    public T jpaFindByOne(QueryWrapper<T> queryWrapper,Object...params){
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        String methodName = stackTrace[1].getMethodName();
        methodName=methodName.replace(KeyWord.findBy.getValue(), KeyWord.And.getValue());
        Map<String,String> attributes=new HashedMap();
        methodName=setMethodAttribute(methodName,attributes);
        List<String> keyWords=SqlUtils.getKeyword(methodName);
        queryWrapper=jpaQueryWrapper.getQueryWrapper(queryWrapper,keyWords,attributes,params);
        T t=baseMapper.selectOne(queryWrapper);
        LinkTableSelect<T> linkTableSelect=new LinkTableSelect<T>();
        linkTableSelect.linkSelect(stackTrace[1],t);
        return t;
    }

    /**
     * 集合查询
     * @param params 查询参数
     * @return
     */
    public List<T> jpaFindByMany(Object...params){
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        String methodName = stackTrace[1].getMethodName();
        methodName=methodName.replace(KeyWord.findBy.getValue(), KeyWord.And.getValue());
        Map<String,String> attributes=new HashedMap();
        methodName=setMethodAttribute(methodName,attributes);
        List<String> keyWords=SqlUtils.getKeyword(methodName);
        QueryWrapper<T> queryWrapper=jpaQueryWrapper.getQueryWrapper(keyWords,attributes,params);
        List<T> list=baseMapper.selectList(queryWrapper);
        LinkTableSelect<T> linkTableSelect=new LinkTableSelect<T>();
        linkTableSelect.linkSelect(stackTrace[1],list);
        return list;
    }
    /**
     * 集合查询
     * @param params 查询参数
     * @return
     */
    public List<T> jpaFindByMany(QueryWrapper<T> queryWrapper,Object...params){
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        String methodName = stackTrace[1].getMethodName();
        methodName=methodName.replace(KeyWord.findBy.getValue(), KeyWord.And.getValue());
        Map<String,String> attributes=new HashedMap();
        methodName=setMethodAttribute(methodName,attributes);
        List<String> keyWords=SqlUtils.getKeyword(methodName);
        queryWrapper=jpaQueryWrapper.getQueryWrapper(queryWrapper,keyWords,attributes,params);
        List<T> list=baseMapper.selectList(queryWrapper);
        LinkTableSelect<T> linkTableSelect=new LinkTableSelect<T>();
        linkTableSelect.linkSelect(stackTrace[1],list);
        return list;
    }

    /**
     * jpa delete
     * @param params 删除参数
     * @return 返回影响行数
     */
    public int jpaDelete(Object...params){
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        String methodName = stackTrace[1].getMethodName();
        methodName=methodName.replace(KeyWord.deleteBy.getValue(), KeyWord.And.getValue());
        Map<String,String> attributes=new HashedMap();
        methodName=setMethodAttribute(methodName,attributes);
        List<String> keyWords=SqlUtils.getKeyword(methodName);
        QueryWrapper<T> queryWrapper=jpaQueryWrapper.getQueryWrapper(keyWords,attributes,params);
        return baseMapper.delete(queryWrapper);
    }

    /**
     * jpa delete
     * @param params 删除参数
     * @return 返回影响行数
     */
    public int jpaDelete(QueryWrapper<T> queryWrapper,Object...params){
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        String methodName = stackTrace[1].getMethodName();
        methodName=methodName.replace(KeyWord.deleteBy.getValue(), KeyWord.And.getValue());
        Map<String,String> attributes=new HashedMap();
        methodName=setMethodAttribute(methodName,attributes);
        List<String> keyWords=SqlUtils.getKeyword(methodName);
        queryWrapper=jpaQueryWrapper.getQueryWrapper(queryWrapper,keyWords,attributes,params);
        return baseMapper.delete(queryWrapper);
    }


    public boolean saveBatch(Collection<T> entityList) {
        if(entityList!=null&&entityList.size()>0){
            return super.saveBatch(entityList);
        }
        return true;
    }

    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        if(entityList!=null&&entityList.size()>0){
            return super.saveOrUpdateBatch(entityList);
        }
        return true;
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        if(entityList!=null&&entityList.size()>0){
            return super.saveBatch(entityList, batchSize);
        }
        return true;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        if(entityList!=null&&entityList.size()>0){
            return super.saveOrUpdateBatch(entityList, batchSize);
        }
        return true;
    }

    public boolean updateBatchById(Collection<T> entityList) {
        if(entityList!=null&&entityList.size()>0){
            return super.updateBatchById(entityList);
        }
        return true;
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if(entityList!=null&&entityList.size()>0){
            return super.updateBatchById(entityList, batchSize);
        }
        return true;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        if(idList!=null&&idList.size()>0){
            return super.removeByIds(idList);
        }
        return true;
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        if(columnMap!=null&&columnMap.keySet().size()>0){
            return super.removeByMap(columnMap);
        }
        return true;
    }


    /**
     * 设置参数统配符号
     * @param method
     * @return
     */
    public String setMethodAttribute(String method,Map<String,String> attributes){
        Type superClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[1];
        Class<T> classType =null;
        if (type instanceof ParameterizedType) {
            classType = (Class<T>) ((ParameterizedType) type).getRawType();
        } else {
            classType = (Class<T>) type;
        }
        Field[] fields = classType.getDeclaredFields();
        int a=1;
        for (Field field : fields) {
            String name= SqlUtils.firstCapitalization(field.getName());
            if(method.indexOf(name)==-1){
                continue;
            }
            String attribut="["+a+"]";
            a++;
            method=method.replaceAll(name,attribut);
            attributes.put(attribut,name);
        }
        return method;
    }
}
