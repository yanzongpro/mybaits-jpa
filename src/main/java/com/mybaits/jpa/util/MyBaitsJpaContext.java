package com.mybaits.jpa.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Administrator on 2019/11/17 0017.
 */

public class MyBaitsJpaContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;


    /**
     * 获得spring上下文
     * @return ApplicationContext spring上下文
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 获取bean
     * @param name service注解方式name为小驼峰格式
     * @return  Object bean的实例对象
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }


    /**
     * 获取bean
     * @param clazz
     * @return  Object bean的实例对象
     */
    public static Object getBean(Class clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MyBaitsJpaContext.applicationContext = applicationContext;
    }
}
