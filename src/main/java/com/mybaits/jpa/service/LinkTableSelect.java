package com.mybaits.jpa.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybaits.jpa.annotation.*;
import com.mybaits.jpa.util.MyBaitsJpaContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/11/17 0017.
 */
public class LinkTableSelect<T> {



    public void linkSelect(StackTraceElement stackTraceElement, T t){
        String methodName = stackTraceElement.getMethodName();
        try{
            Class clazz=Class.forName(stackTraceElement.getClassName());
            Method[] methods=clazz.getMethods();
            Method method=null;
            for (Method method1 : methods) {
                if(method1.getName().equals(methodName)){
                    method=method1;
                    break;
                }
            }
            LinkSelect linkSelect=null;
            if(method!=null){
                linkSelect=method.getAnnotation(LinkSelect.class);
                if(linkSelect==null){
                    return;
                }
            }
            if(t==null){
                return;
            }
            String className=t.getClass().getName();
            if(className==null){
                return;
            }
            Class tClass=Class.forName(className);
            List<Class> classList=new ArrayList<>();
            linkNumberSelect(linkSelect,0,classList,tClass,t);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void linkSelect(StackTraceElement stackTraceElement, List<T> list){
        String methodName = stackTraceElement.getMethodName();
        try{
            Class clazz=Class.forName(stackTraceElement.getClassName());
            Method[] methods=clazz.getMethods();
            Method method=null;
            for (Method method1 : methods) {
                if(method1.getName().equals(methodName)){
                    method=method1;
                    break;
                }
            }
            LinkSelect linkSelect=null;
            if(method!=null){
                linkSelect=method.getAnnotation(LinkSelect.class);
                if(linkSelect==null){
                    return;
                }
            }
            String className=null;
            if(list!=null&&list.size()>0){
                className=list.get(0).getClass().getName();
            }
            if(className==null){
                return;
            }
            Class tClass=Class.forName(className);
            List<Class> classList=new ArrayList<>();
            linkNumberSelect(linkSelect,0,classList,tClass,list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void linkNumberSelect(LinkSelect linkSelect,int number,List<Class> classList,Class downClass,Object object){
        if(number>=linkSelect.linkLv().getValue()){
            return;
        }
        List<Class> classNewList=new ArrayList<>();
        classNewList.addAll(classList);
        classNewList.add(downClass);
        Field[] fields=downClass.getDeclaredFields();
        for (Field field : fields) {
            OneByOne oneByOne=field.getAnnotation(OneByOne.class);
            if(oneByOne!=null){
                selectOneByOny(linkSelect,oneByOne,classNewList,object,field,number);
            }
            OneByMany oneByMany=field.getAnnotation(OneByMany.class);
            if(oneByMany!=null){
                selectOneByMany(linkSelect,oneByMany,classNewList,object,field,number);
            }
            ManyByOne manyByOne=field.getAnnotation(ManyByOne.class);
            if(manyByOne!=null){
                selectManyByOne(linkSelect,manyByOne,classNewList,object,field,number);
            }
            ManyByMany manyByMany=field.getAnnotation(ManyByMany.class);
            if(manyByMany!=null){
                selectManyByMany(linkSelect,manyByMany,classNewList,object,field,number);
            }
        }
    }

    public void linkNumberSelect(LinkSelect linkSelect,int number,List<Class> classList,Class downClass,List list){
        if(number>=linkSelect.linkLv().getValue()){
            return;
        }
        List<Class> classNewList=new ArrayList<>();
        classNewList.addAll(classList);
        classNewList.add(downClass);
        Field[] fields=downClass.getDeclaredFields();
        for (Field field : fields) {
            OneByOne oneByOne=field.getAnnotation(OneByOne.class);
            if(oneByOne!=null){
                selectOneByOny(linkSelect,oneByOne,classNewList,list,field,number);
            }
            OneByMany oneByMany=field.getAnnotation(OneByMany.class);
            if(oneByMany!=null){
                selectOneByMany(linkSelect,oneByMany,classNewList,list,field,number);
            }
            ManyByOne manyByOne=field.getAnnotation(ManyByOne.class);
            if(manyByOne!=null){
                selectManyByOne(linkSelect,manyByOne,classNewList,list,field,number);
            }
            ManyByMany manyByMany=field.getAnnotation(ManyByMany.class);
            if(manyByMany!=null){
                selectManyByMany(linkSelect,manyByMany,classNewList,list,field,number);
            }
        }
    }

    public void selectOneByOny(LinkSelect linkSelect,OneByOne oneByOne,List<Class> classList,List list,Field field,int number){
        if(!chackAnnotation(oneByOne)){
            return;
        }
        BaseMapper baseMapper=null;
        Object objectBean= oneByOne.aimEntity().getAnnotation(DaoClass.class);
        if(objectBean!=null){
            DaoClass daoClass= (DaoClass) objectBean;
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean(daoClass.daoClass());
        }else{
            String classByName=oneByOne.aimEntity().getName().substring(oneByOne.aimEntity().getName().lastIndexOf(".")+1);
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean("I"+classByName+"Dao");
        }
        if(baseMapper==null){
            System.err.println("未找到"+oneByOne.aimEntity().getName()+"对应dao");
            System.err.println("请检查实体与dao命名是否一致如：IDemoDao Demo");
            System.err.println("或者在实体上添加注解：@DaoClass(daoClass=\"IDemoDao.class\")");
            System.err.println("连表查询失败");
            return;
        }
        List keys=new ArrayList();
        for (Object o : list) {
            keys.add(getGetMethod(o,oneByOne.field()));
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in(oneByOne.aimField(),keys);
        List listResult=baseMapper.selectList(queryWrapper);
        if(listResult==null||listResult.size()==0){
            return;
        }
        for (Class aClass : classList) {
            if(aClass.getName().equals(listResult.get(0).getClass().getName())){
                return;
            }
        }
        number++;
        linkNumberSelect(linkSelect,number,classList,listResult.get(0).getClass(),listResult);
        for (Object o : list) {
            for (Object o1 : listResult) {
                if(getGetMethod(o,oneByOne.field()).equals(getGetMethod(o1,oneByOne.aimField()))){
                    setValue(o,field.getName(),o1.getClass(),o1);
                }
            }
        }
    }

    public void selectOneByOny(LinkSelect linkSelect,OneByOne oneByOne,List<Class> classList,Object object,Field field,int number){
        if(!chackAnnotation(oneByOne)){
            return;
        }
        BaseMapper baseMapper=null;
        Object objectBean= oneByOne.aimEntity().getAnnotation(DaoClass.class);
        if(objectBean!=null){
            DaoClass daoClass= (DaoClass) objectBean;
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean(daoClass.daoClass());
        }else{
            String classByName=oneByOne.aimEntity().getName().substring(oneByOne.aimEntity().getName().lastIndexOf(".")+1);
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean("I"+classByName+"Dao");
        }
        if(baseMapper==null){
            System.err.println("未找到"+oneByOne.aimEntity().getName()+"对应dao");
            System.err.println("请检查实体与dao命名是否一致如：IDemoDao Demo");
            System.err.println("或者在实体上添加注解：@DaoClass(daoClass=\"IDemoDao.class\")");
            System.err.println("连表查询失败");
            return;
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq(oneByOne.aimField(),getGetMethod(object,oneByOne.field()));
        Object objectResult=baseMapper.selectOne(queryWrapper);
        if(objectResult==null){
            return;
        }
        for (Class aClass : classList) {
            if(aClass.getName().equals(objectResult.getClass().getName())){
                return;
            }
        }
        number++;
        linkNumberSelect(linkSelect,number,classList,objectResult.getClass(),objectResult);
        setValue(object,field.getName(),objectResult.getClass(),objectResult);
    }




    public void selectOneByMany(LinkSelect linkSelect,OneByMany oneByMany,List<Class> classList,List list,Field field,int number){
        if(!chackAnnotation(oneByMany)){
            return;
        }
        BaseMapper baseMapper=null;
        Object objectBean= oneByMany.aimEntity().getAnnotation(DaoClass.class);
        if(objectBean!=null){
            DaoClass daoBean= (DaoClass) objectBean;
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean(daoBean.daoClass());
        }else{
            String classByName=oneByMany.aimEntity().getName().substring(oneByMany.aimEntity().getName().lastIndexOf(".")+1);
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean("I"+classByName+"Dao");
        }
        if(baseMapper==null){
            System.err.println("未找到"+oneByMany.aimEntity().getName()+"对应dao");
            System.err.println("请检查实体与dao命名是否一致如：IDemoDao Demo");
            System.err.println("或者在实体上添加注解：@DaoClass(daoClass=\"IDemoDao.class\")");
            System.err.println("连表查询失败");
            return;
        }
        List keys=new ArrayList();
        for (Object o : list) {
            keys.add(getGetMethod(o,oneByMany.field()));
        }
        QueryWrapper<T> queryWrapper=new QueryWrapper<T>();
        queryWrapper.in(oneByMany.aimField(),keys);
        List listResult=baseMapper.selectList(queryWrapper);
        if(listResult==null||listResult.size()==0){
            return;
        }
        for (Class aClass : classList) {
            if(aClass.getName().equals(listResult.get(0).getClass().getName())){
                return;
            }
        }
        number++;
        linkNumberSelect(linkSelect,number,classList,listResult.get(0).getClass(),listResult);
        for (Object o : list) {
            List thisList=new ArrayList();
            for (Object o1 : listResult) {
                if(getGetMethod(o,oneByMany.field()).equals(getGetMethod(o1,oneByMany.aimField()))){
                    thisList.add(o1);
                }
            }
            if(thisList.size()>0){
                setValue(o,field.getName(),List.class,thisList);
            }
        }
    }
    public void selectOneByMany(LinkSelect linkSelect,OneByMany oneByMany,List<Class> classList,Object object,Field field,int number){
        if(!chackAnnotation(oneByMany)){
            return;
        }
        BaseMapper baseMapper=null;
        Object objectBean= oneByMany.aimEntity().getAnnotation(DaoClass.class);
        if(objectBean!=null){
            DaoClass daoBean= (DaoClass) objectBean;
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean(daoBean.daoClass());
        }else{
            String classByName=oneByMany.aimEntity().getName().substring(oneByMany.aimEntity().getName().lastIndexOf(".")+1);
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean("I"+classByName+"Dao");
        }
        if(baseMapper==null){
            System.err.println("未找到"+oneByMany.aimEntity().getName()+"对应dao");
            System.err.println("请检查实体与dao命名是否一致如：IDemoDao Demo");
            System.err.println("或者在实体上添加注解：@DaoClass(daoClass=\"IDemoDao.class\")");
            System.err.println("连表查询失败");
            return;
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq(oneByMany.aimField(),getGetMethod(object,oneByMany.field()));
        List listResult=baseMapper.selectList(queryWrapper);
        if(listResult==null||listResult.size()==0){
            return;
        }
        for (Class aClass : classList) {
            if(aClass.getName().equals(listResult.get(0).getClass().getName())){
                return;
            }
        }
        number++;
        linkNumberSelect(linkSelect,number,classList,listResult.get(0).getClass(),listResult);
        setValue(object,field.getName(),List.class,listResult);
    }



    public void selectManyByOne(LinkSelect linkSelect,ManyByOne manyByOne,List<Class> classList,List list,Field field,int number){
        if(!chackAnnotation(manyByOne)){
            return;
        }
        BaseMapper baseMapper=null;
        Object objectBean= manyByOne.aimEntity().getAnnotation(DaoClass.class);
        if(objectBean!=null){
            DaoClass daoBean= (DaoClass) objectBean;
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean(daoBean.daoClass());
        }else{
            String classByName=manyByOne.aimEntity().getName().substring(manyByOne.aimEntity().getName().lastIndexOf(".")+1);
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean("I"+classByName+"Dao");
        }
        if(baseMapper==null){
            System.err.println("未找到"+manyByOne.aimEntity().getName()+"对应dao");
            System.err.println("请检查实体与dao命名是否一致如：IDemoDao Demo");
            System.err.println("或者在实体上添加注解：@DaoClass(daoClass=\"IDemoDao.class\")");
            System.err.println("连表查询失败");
            return;
        }
        Map<Object,String> map=new HashMap<>();
        for (Object o : list) {
            map.put(getGetMethod(o,manyByOne.field()).toString(),"");
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in(manyByOne.aimField(),map.keySet());
        List listResult=baseMapper.selectList(queryWrapper);
        if(listResult==null||listResult.size()==0){
            return;
        }
        for (Class aClass : classList) {
            if(aClass.getName().equals(listResult.get(0).getClass().getName())){
                return;
            }
        }
        number++;
        linkNumberSelect(linkSelect,number,classList,listResult.get(0).getClass(),listResult);
        for (Object o : list) {
            for (Object o1 : listResult) {
                if(getGetMethod(o,manyByOne.field()).equals(getGetMethod(o1,manyByOne.aimField()))){
                    setValue(o,field.getName(),field.getType(),o1);
                }
            }
        }
    }

    public void selectManyByOne(LinkSelect linkSelect,ManyByOne manyByOne,List<Class> classList,Object object,Field field,int number){
        if(!chackAnnotation(manyByOne)){
            return;
        }
        BaseMapper baseMapper=null;
        Object objectBean= manyByOne.aimEntity().getAnnotation(DaoClass.class);
        if(objectBean!=null){
            DaoClass daoBean= (DaoClass) objectBean;
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean(daoBean.daoClass());
        }else{
            String classByName=manyByOne.aimEntity().getName().substring(manyByOne.aimEntity().getName().lastIndexOf(".")+1);
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean("I"+classByName+"Dao");
        }
        if(baseMapper==null){
            System.err.println("未找到"+manyByOne.aimEntity().getName()+"对应dao");
            System.err.println("请检查实体与dao命名是否一致如：IDemoDao Demo");
            System.err.println("或者在实体上添加注解：@DaoClass(daoClass=\"IDemoDao.class\")");
            System.err.println("连表查询失败");
            return;
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq(manyByOne.aimField(),getGetMethod(object,manyByOne.field()));
        Object objectResult=baseMapper.selectOne(queryWrapper);
        if(objectResult==null){
            return;
        }
        for (Class aClass : classList) {
            if(aClass.getName().equals(objectResult.getClass().getName())){
                return;
            }
        }
        number++;
        linkNumberSelect(linkSelect,number,classList,objectResult.getClass(),objectResult);
        setValue(object,field.getName(),field.getType(),objectResult);
    }


    public void selectManyByMany(LinkSelect linkSelect,ManyByMany manyByMany,List<Class> classList,List list,Field field,int number){
        if(!chackAnnotation(manyByMany)){
            return;
        }
        BaseMapper baseMapper=null;
        Object objectBean= manyByMany.aimEntity().getAnnotation(DaoClass.class);
        if(objectBean!=null){
            DaoClass daoBean= (DaoClass) objectBean;
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean(daoBean.daoClass());
        }else{
            String classByName=manyByMany.aimEntity().getName().substring(manyByMany.aimEntity().getName().lastIndexOf(".")+1);
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean("I"+classByName+"Dao");
        }
        if(baseMapper==null){
            System.err.println("未找到"+manyByMany.aimEntity().getName()+"对应dao");
            System.err.println("请检查实体与dao命名是否一致如：IDemoDao Demo");
            System.err.println("或者在实体上添加注解：@DaoClass(daoBean=\"IDemoDao.class\")");
            System.err.println("连表查询失败");
            return;
        }
        Map<Object,String> map=new HashMap<>();
        for (Object o : list) {
            map.put(getGetMethod(o,manyByMany.field()).toString(),"");
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in(manyByMany.aimField(),map.keySet());
        List listResult=baseMapper.selectList(queryWrapper);
        if(listResult==null||listResult.size()==0){
            return;
        }
        for (Class aClass : classList) {
            if(aClass.getName().equals(listResult.get(0).getClass().getName())){
                return;
            }
        }
        number++;
        linkNumberSelect(linkSelect,number,classList,listResult.get(0).getClass(),listResult);
        for (Object o : list) {
            List thisList=new ArrayList();
            for (Object o1 : listResult) {
                if(getGetMethod(o,manyByMany.field()).equals(getGetMethod(o1,manyByMany.aimField()))){
                    thisList.add(o1);
                }
            }
            if(thisList.size()>0){
                setValue(o,field.getName(),List.class,thisList);
            }
        }
    }

    public void selectManyByMany(LinkSelect linkSelect,ManyByMany manyByMany,List<Class> classList,Object object,Field field,int number){
        if(!chackAnnotation(manyByMany)){
            return;
        }
        BaseMapper baseMapper=null;
        Object objectBean= manyByMany.aimEntity().getAnnotation(DaoClass.class);
        if(objectBean!=null){
            DaoClass daoBean= (DaoClass) objectBean;
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean(daoBean.daoClass());
        }else{
            String classByName=manyByMany.aimEntity().getName().substring(manyByMany.aimEntity().getName().lastIndexOf(".")+1);
            baseMapper= (BaseMapper) MyBaitsJpaContext.getBean("I"+classByName+"Dao");
        }
        if(baseMapper==null){
            System.err.println("未找到"+manyByMany.aimEntity().getName()+"对应dao");
            System.err.println("请检查实体与dao命名是否一致如：IDemoDao Demo");
            System.err.println("或者在实体上添加注解：@DaoClass(daoClass=\"IDemoDao.class\")");
            System.err.println("连表查询失败");
            return;
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in(manyByMany.aimField(),getGetMethod(object,manyByMany.field()));
        List listResult=baseMapper.selectList(queryWrapper);
        if(listResult==null||listResult.size()==0){
            return;
        }
        for (Class aClass : classList) {
            if(aClass.getName().equals(listResult.get(0).getClass().getName())){
                return;
            }
        }
        number++;
        linkNumberSelect(linkSelect,number,classList,listResult.get(0).getClass(),listResult);
        setValue(object,field.getName(),List.class,listResult);
    }

    public boolean chackAnnotation(OneByOne oneByOne){
        if(oneByOne.aimEntity()==null){
            return false;
        }
        if(oneByOne.aimField()==null||oneByOne.aimField()==""||oneByOne.aimField().length()<1){
            return false;
        }
        if(oneByOne.field()==null||oneByOne.field()==""||oneByOne.field().length()<1){
            return false;
        }
        return true;
    }

    public boolean chackAnnotation(OneByMany oneByMany){
        if(oneByMany.aimEntity()==null){
            return false;
        }
        if(oneByMany.aimField()==null||oneByMany.aimField()==""||oneByMany.aimField().length()<1){
            return false;
        }
        if(oneByMany.field()==null||oneByMany.field()==""||oneByMany.field().length()<1){
            return false;
        }
        return true;
    }
    public boolean chackAnnotation(ManyByOne manyByOne){
        if(manyByOne.aimEntity()==null){
            return false;
        }
        if(manyByOne.aimField()==null||manyByOne.aimField()==""||manyByOne.aimField().length()<1){
            return false;
        }
        if(manyByOne.field()==null||manyByOne.field()==""||manyByOne.field().length()<1){
            return false;
        }
        return true;
    }
    public boolean chackAnnotation(ManyByMany manyByMany){
        if(manyByMany.aimEntity()==null){
            return false;
        }
        if(manyByMany.aimField()==null||manyByMany.aimField()==""||manyByMany.aimField().length()<1){
            return false;
        }
        if(manyByMany.field()==null||manyByMany.field()==""||manyByMany.field().length()<1){
            return false;
        }
        return true;
    }



    /**
     * 根据属性，获取get方法的值
     * @param ob 对象
     * @param name 属性名
     * @return
     * @throws Exception
     */
    public Object getGetMethod(Object ob , String name){
        try {
            Method[] m = ob.getClass().getMethods();
            for(int i = 0;i < m.length;i++){
                if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
                    return m[i].invoke(ob);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据属性，拿到set方法，并把值set到对象中
     * @param obj 对象
     * @param filedName 属性名称
     * @param typeClass value的类型
     * @param value 需要设置的值
     */
    public  void setValue(Object obj,String filedName,Class<?> typeClass,Object value){
        filedName = removeLine(filedName);
        String methodName = "set" + filedName.substring(0,1).toUpperCase()+filedName.substring(1);
        try{
            Method method =  obj.getClass().getDeclaredMethod(methodName, typeClass);
            method.invoke(obj, value);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 处理字符串  如：  abc_dex ---> abcDex
     * @param str
     * @return
     */
    public   String removeLine(String str){
        if(null != str && str.contains("_")){
            int i = str.indexOf("_");
            char ch = str.charAt(i+1);
            char newCh = (ch+"").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i+1), newCh);
            String newStr2 = newStr.replace("_", "");
            return newStr2;
        }
        return str;
    }

}
