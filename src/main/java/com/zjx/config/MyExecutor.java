package com.zjx.config;

import com.alibaba.fastjson.JSONObject;
import com.zjx.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/8/20 18:17
 * @Version V1.0
 **/
public class MyExecutor implements Executor {

    private MyConfiguration config = new MyConfiguration();
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    @Override
    public <T> T executeSql(String sqlType, String sql, Object[] args) {

        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(sql);

            if (sqlType.equalsIgnoreCase(SqlTypeEnum.SELECT.name())) {
                pstm.setString(1, args[0].toString()); // 设置参数
                resultSet = pstm.executeQuery();
                User user = new User();
                while (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                    user.setUesrname(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                }
                return (T) user;
            } else if (sqlType.equalsIgnoreCase(SqlTypeEnum.INSERT.name())) {
                User user = (User) args[0];
                pstm.setString(1, user.getUesrname());
                pstm.setString(2, user.getPassword());
                pstm.execute();
                return (T)(Boolean)true;
            } else if (sqlType.equalsIgnoreCase(SqlTypeEnum.UPDATE.name())) {
                Map<String, Object> map = (Map<String, Object>) args[0];
                pstm.setString(1, map.get("username").toString());
                pstm.setString(2, map.get("password").toString());
                pstm.setInt(3, (Integer) map.get("id"));
                pstm.execute();
                return (T)(Boolean)true;
            } else if (sqlType.equalsIgnoreCase(SqlTypeEnum.DELETE.name())) {
                pstm.setInt(1, (Integer) args[0]);
                pstm.execute();
                return (T)(Boolean)true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            /*try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
        }

        return null;
    }

    private Connection getConnection() {
        if (threadLocal.get() != null) {
            return threadLocal.get();
        }
        Connection connection = config.build("jdbc.xml");
        threadLocal.set(connection);
        return connection;
    }

}
