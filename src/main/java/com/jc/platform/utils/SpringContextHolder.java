package com.jc.platform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description 以静态变量保存Spring ApplicationContext,
 * 可在任何代码任何地方任何时候取出ApplicaitonContext
 */
@Component
public class SpringContextHolder implements ApplicationContextAware,
        DisposableBean {

    private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory
            .getLogger(SpringContextHolder.class);

    /**
     * @return 返回ApplicationContext
     * @description 取得存储在静态变量中的ApplicationContext
     * @author
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param name bean的名字
     * @return 返回取得的bean
     * @description 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型
     * @author
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * @param requiredType bean的类型
     * @return 返回取得的bean
     * @description 静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型
     * @author
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * @description 清除SpringContextHolder中的ApplicationContext为Null
     * @author
     */
    public static void clearHolder() {
        logger.debug("清除SpringContextHolder中的ApplicationContext:"
                + applicationContext);
        applicationContext = null;
    }

    /**
     * @param applicationContext 注入的Context
     * @description 实现ApplicationContextAware接口, 注入Context到静态变量中
     * @author
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextHolder.applicationContext != null) {
            logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                    + SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext = applicationContext; // NOSONAR
    }

    /**
     * @throws Exception
     * @description 实现DisposableBean接口, 在Context关闭时清理静态变量.
     * @author
     */
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }
}