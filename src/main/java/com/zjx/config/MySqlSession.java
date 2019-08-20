package com.zjx.config;

import java.lang.reflect.Proxy;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/8/20 18:09
 * @Version V1.0
 **/
public class MySqlSession {

    private Executor executor = new MyExecutor();

    private MyConfiguration config = new MyConfiguration();

    public <T> T selectOne(String statement, Object parameter) {
        return executor.query(statement, parameter);
    }

    public <T> T getMapper(Class<T> clazz){
        // 动态代理调用
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MyMapperProxy(this, config));
    }
}
