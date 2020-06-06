package com.zx.thread;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * java.sql.Connection是个接口 ，最终实现由数据库提供方案实现
 * 由于只是个示例，所以通过动态代理构造一个Connection
 * 该代理仅实现在commit方法调用时休眠100ms
 */
public class ConnectionDriver {
    static class ConnectionHandler implements InvocationHandler{

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.getName().equals("commit")){
                TimeUnit.MILLISECONDS.sleep(100);
            }
            return null;
        }
    }

    public static final Connection createConnection(){
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class<?>[]{Connection.class},new ConnectionHandler());
    }
}
