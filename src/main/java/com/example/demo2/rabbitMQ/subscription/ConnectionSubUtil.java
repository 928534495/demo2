package com.example.demo2.rabbitMQ.subscription;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionSubUtil {
//连接工厂
    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("localhost");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("sub2");//虚拟主机，该虚拟主机名必须在rabbtiMQ管理页面上设置
        factory.setUsername("admin");//用户
        factory.setPassword("admin");//密码
        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}