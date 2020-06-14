package com.zx.thread;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-10
 * 书上说HashMap并发执行put操作，会导致Entry的链表形成环形数据结构。
 * 一旦形成环形数据结构，Entry的next节点永不为空，就换产生死循环获取Entry
 * 但是用了这个例子并没有出现这个问题
 * 难道是hashMap升级了1.8的原因，因为看书似乎是1.8之前的了。
 * 果然看到网上说：死循环问题在JDK 1.8 之前是存在的，JDK 1.8 通过增加loHead和loTail进行了修复
 */
public class BadHashMap {
    public static void main(String[] args) throws InterruptedException {
        final HashMap<String, String> map = new HashMap<>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "ftf");
        t.start();
        t.join();
        String s = map.get(UUID.randomUUID().toString());
        System.out.println(s);
    }
}
