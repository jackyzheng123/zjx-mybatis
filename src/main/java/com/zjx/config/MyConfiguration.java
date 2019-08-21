package com.zjx.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/8/20 17:14
 * @Version V1.0
 **/
public class MyConfiguration {

    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    /**
     * 读取xml信息并处理
     *
     * @param resource
     * @return
     */
    public Connection build(String resource) {
        Element root = null;
        try {
            InputStream inputStream = classLoader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(inputStream);
            root = doc.getRootElement();

        } catch (DocumentException e) {
            throw new RuntimeException("error occured whild evaling xml " + resource);
        }
        return evalDataSource(root);
    }

    /**
     * 获取数据库连接
     *
     * @param node
     * @return
     */
    private Connection evalDataSource(Element node) {
        if (!node.getName().equals("database")) {
            throw new RuntimeException("root should be <database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        List properties = node.elements("property");

        for (Object item : properties) {
            Element element = (Element) item;
            String name = element.attributeValue("name");
            String value = getValue(element);
            if (name == null || value == null) {
                throw new RuntimeException("[database]: <property> should contain name and value");
            }

            switch (name) {
                case "driverClassName":
                    driverClassName = value;
                    break;
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                default:
                    throw new RuntimeException("[database]: <property> unknow name");
            }
        }

        Connection connection = null;
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    /**
     * 读取userMapper.xml文件
     *
     * @return
     */
    public MapperBean readMapper(String path) {
        MapperBean mapper = new MapperBean();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(inputStream);
            Element root = doc.getRootElement();
            mapper.setInterfaceName(root.attributeValue("nameSpace").trim()); // 把映射文件命名空间作为接口名

            List<Function> list = new ArrayList<>(); // 存储方法
            for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {

                Element element = (Element) iterator.next();
                String sqlType = element.getName().trim(); // select
                String funcName = element.attributeValue("id").trim();
                String sql = element.getText().trim();

                String parameter = element.attributeValue("parameterType");
                String parameterType = StringUtils.isEmpty(parameter) ? "" : parameter.trim();

                String rtType = element.attributeValue("resultType");
                String resultType = StringUtils.isEmpty(rtType) ? "" : rtType.trim();

                Function function = new Function(); // 用来存储一条方法的信息
                function.setSqlType(sqlType);
                function.setFuncName(funcName);
                function.setSql(sql);
                function.setParameterType(parameterType);

                // 利用反射设置返回类型
                Object newInstance = null;
                try {
                    if (!StringUtils.isEmpty(resultType)) {
                        newInstance = Class.forName(resultType).newInstance();
                        function.setResultType(newInstance);
                    }
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                list.add(function);
            }
            mapper.setList(list);

        } catch (DocumentException e) {
            throw new RuntimeException("error occured whild evaling xml " + path);
        }
        return mapper;
    }
}
