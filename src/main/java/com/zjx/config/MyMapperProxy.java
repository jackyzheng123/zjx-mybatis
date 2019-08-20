package com.zjx.config;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/8/20 19:11
 * @Version V1.0
 **/
public class MyMapperProxy implements InvocationHandler {

    private MySqlSession mySqlSession;
    private MyConfiguration myConfiguration;

    public MyMapperProxy(MySqlSession mySqlSession, MyConfiguration myConfiguration) {
        this.mySqlSession = mySqlSession;
        this.myConfiguration = myConfiguration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapperBean = myConfiguration.readMapper("userMapper.xml");

        // 是否是xml文件对应接口
        if(!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())){
            return null;
        }

        List<Function> list = mapperBean.getList();
        if (!CollectionUtils.isEmpty(list)) {
            for (Function func : list) {
                // id是否与方法名一样
                if (method.getName().equals(func.getFuncName())) {
                    return mySqlSession.selectOne(func.getSql(), String.valueOf(args[0]));
                }
            }
        }

        return null;
    }
}
