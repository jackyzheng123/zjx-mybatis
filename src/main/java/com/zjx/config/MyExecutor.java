package com.zjx.config;

import com.zjx.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/8/20 18:17
 * @Version V1.0
 **/
public class MyExecutor implements Executor {

    private MyConfiguration config = new MyConfiguration();

    @Override
    public <T> T query(String sql, Object parameter) {

        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, parameter.toString());
            resultSet = pstm.executeQuery();


            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setUesrname(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
            }
            return (T) user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
            }
        }

        return null;
    }

    private Connection getConnection() {
        return config.build("jdbc.xml");
    }

}
