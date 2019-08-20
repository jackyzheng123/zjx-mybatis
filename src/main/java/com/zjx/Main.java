package com.zjx;

import com.zjx.config.MySqlSession;
import com.zjx.entity.User;
import com.zjx.mapper.UserMapper;

public class Main {

    public static void main(String[] args) {
        MySqlSession mySqlSession = new MySqlSession();
        UserMapper mapper = mySqlSession.getMapper(UserMapper.class);
        User user = mapper.getById(1);
        System.out.println(user);

    }

}
