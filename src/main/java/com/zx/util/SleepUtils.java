package com.zx.util;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 */
public class SleepUtils {
    public static final void second(long seconds){
        try{
            TimeUnit.SECONDS.sleep(seconds);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
