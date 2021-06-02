package com.example.jdbcTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-12-14 12:47
 **/
public class TestJdbc {
    public static void main(String[] args) {
        // 加载Class到AppClassLoader（系统类加载器），然后注册驱动类
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://192.168.79.28:63307/project_public_platform?characterEncoding=utf8&useSSL=false";
// 通过java库获取数据库连接spring.datasource.username=ztdcuser
//spring.datasource.password=bml[epraw2eSFCce
        try {
            Connection conn = java.sql.DriverManager.getConnection(url, "ztdcuser", "bml[epraw2eSFCce");
            PreparedStatement preparedStatement = conn.prepareStatement("select * from t_env_config");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println(id);
                System.out.println(name);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
