package javaguide.multi_thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * Java中使用 PipedReader 和 PipedWriter 完成线程间的通信
 */
public class PipedReaderTest {
    public static void main(String[] args) throws IOException {
        // 1. 创建管道
        PipedReader pipedReader = new PipedReader();
        PipedWriter pipedWriter = new PipedWriter();

        // 2. 连接管道
        pipedWriter.connect(pipedReader);
        // or
//        pipedReader.connect(pipedWriter);

        // 3. 创建读取线程
        Thread readThread = new Thread(() -> {
            int data;
            try {
                while ((data = pipedReader.read()) != -1) {
                    System.out.print((char) data);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // 4. 创建写线程
        Thread writeThread = new Thread(() -> {
            try {
                pipedWriter.write("Hello, World.");
                pipedWriter.close();  // 写完要关闭，否则 readThread 会阻塞在read()上
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // 5. 启动两个线程
        readThread.start();
        writeThread.start();
    }
}
