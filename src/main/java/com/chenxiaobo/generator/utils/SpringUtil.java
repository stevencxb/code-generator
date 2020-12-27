package com.chenxiaobo.generator.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Title: SpringUtil
 * @Description: SpringUtil
 * @Author <a href="mailto:chenxiaobo@sinoland666.com">陈晓博</a>
 * @Date 2017-12-12 9:00
 * @Version V1.0
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }

    }

    /**
     * 获取applicationContext
     * @Author 陈晓博
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     * @Author 陈晓博
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     * @Author 陈晓博
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @Author 陈晓博
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}