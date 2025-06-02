package javaguide.base.multi_thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Java程序天生就是多线程
 * output:
 * [1] main ： 主线程，程序入口执行逻辑
 * [2] Reference Handler  ： 处理引用对象（软/弱/虚引用）清理
 * [3] Finalizer ： 调用finalize()，已废弃
 * [4] Signal Dispatcher ： 接受并分发OS信号
 * [11] Common-Cleaner ： 用于代替finalize()，在对象被 GC 时，异步执行注册的清理任务（如关闭资源、释放内存）
 * [12] Monitor Ctrl-Break ： 监听Ctrl+Break触发线程堆栈打印（Windows）
 */
public class ThreadPeek {
    public static void main(String[] args) {
        // 获取 Java 线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        // 遍历线程信息，仅打印线程 ID 和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
    }
}
