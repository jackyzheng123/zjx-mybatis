package com.zjx.config;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/8/20 18:15
 * @Version V1.0
 **/
public interface Executor {

    <T> T query(String statement, Object parameter);
}
