package com.mybaits.jpa.service;


import com.mybaits.jpa.sql.wrapper.MyQueryWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2019/12/24 0024.
 */
public class QueryWrapperFactory<T> {

    private static Map<String,MyQueryWrapper> map;


    public QueryWrapperFactory(){
        map=new HashMap<>();
    }

//    public MyQueryWrapper getQueryWrapper(StackTraceElement stackTraceElement, Object...params){
//        String methodName = stackTraceElement.getMethodName();
//        String className=stackTraceElement.getClassName();
//        MyQueryWrapper queryWrapper=map.get(className+"."+methodName);
//        if(queryWrapper==null){
//            return null;
//        }
//        MyQueryWrapper queryWrapperOut=setQueryWrapperParam(new ObjcetUtils<MyQueryWrapper>().cloneObject(queryWrapper),params);
//        return queryWrapperOut;
//    }
//    public MyQueryWrapper getQueryWrapper(StackTraceElement stackTraceElement,MyQueryWrapper<T> myQueryWrapper, Object...params){
//        String methodName = stackTraceElement.getMethodName();
//        String className=stackTraceElement.getClassName();
//        MyQueryWrapper queryWrapper=map.get(className+"."+methodName);
//        if(queryWrapper==null){
//            return null;
//        }
//        MyQueryWrapper queryWrapperOut=setQueryWrapperParam(new ObjcetUtils<MyQueryWrapper>().cloneObject(queryWrapper),params);
//        return queryWrapperOut;
//    }

    public void putQueryWrapper(StackTraceElement stackTraceElement,MyQueryWrapper queryWrapper){
        String methodName = stackTraceElement.getMethodName();
        String className=stackTraceElement.getClassName();
        map.put(className+"."+methodName,queryWrapper);
    }

    private MyQueryWrapper setQueryWrapperParam(MyQueryWrapper queryWrapper,Object...params){
        Set<String> set=queryWrapper.getParamNameValuePairs().keySet();
        Set<String> sortSet = new TreeSet<String>((o1, o2) -> o1.compareTo(o2));
        sortSet.addAll(set);
        if(params.length<0){
            return queryWrapper;
        }
        int index=0;
        for (String key : sortSet) {
            if(params.length>index){
                queryWrapper.getParamNameValuePairs().put(key,params[index]);
            }
        }
        return queryWrapper;
    }



}
