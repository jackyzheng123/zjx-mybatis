package com.zjx.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Description 实体类
 * @Author Carson Cheng
 * @Date 2019/8/20 17:07
 * @Version V1.0
 **/
public class User implements Serializable {

    private Integer id;
    private String uesrname;
    private String password;

    public User() {
    }

    public User(String uesrname, String password) {
        this.uesrname = uesrname;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uesrname='" + uesrname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUesrname() {
        return uesrname;
    }

    public void setUesrname(String uesrname) {
        this.uesrname = uesrname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
