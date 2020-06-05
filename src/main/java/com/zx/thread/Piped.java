package com.zx.thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-06-05
 * 管道通信
 * 管道输入输出流
 * 主要用于线程间的数据传输，而传输的媒介为内存
 * 本代码实现main线程向printThread线程传输数据
 * 会与Piped类型的流，必须先要进行绑定，也就是调用connect方法
 * 如果未将输入输出流绑定，会抛异常
 * 但是我不懂怎么退出
 */
public class Piped {
    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int receive = 0;
        try{
            while((receive = System.in.read()) != -1){
                out.write(receive);
            }
        }finally {
            out.close();
        }
    }

    static class Print implements  Runnable{

        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try{
                while((receive = in.read()) != -1){
                    System.out.print((char) receive);
                }
            }catch (IOException e){
            }
        }
    }
}
