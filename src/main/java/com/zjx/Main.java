package com.zjx;

import com.zjx.config.MySqlSession;
import com.zjx.entity.User;
import com.zjx.mapper.UserMapper;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        MySqlSession mySqlSession = new MySqlSession();
        UserMapper mapper = mySqlSession.getMapper(UserMapper.class);

        User user = mapper.getById(1);
        System.out.println(user);

        User user1 = new User("abc", "123456");
        mapper.add(user1);

        Map<String, Object> params = new HashMap<>(10);
        params.put("id", user.getId());
        params.put("username", "carson cheng");
        params.put("password", "zjx123");
        mapper.update(params);

        mapper.deleteById(2);
    }

}
