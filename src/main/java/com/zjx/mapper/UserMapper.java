package com.zjx.mapper;

import com.zjx.entity.User;

import java.util.Map;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/8/20 17:09
 * @Version V1.0
 **/
public interface UserMapper {

    User getById(Integer id);

    boolean add(User user);

    boolean update(Map<String, Object> params);

    boolean deleteById(Integer id);
}
