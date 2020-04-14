package E_classsLoad;

/**
 * 验证
 * 虚拟机保证一个类的clinit()在多线程环境中被正确加锁和同步,如果多个线程同时去初始化一个类,只会有一个线程执行,其他阻塞等待
 * Created by Administrator on 2017/5/23.
 */
public class DeadLoopClass {
    static {
        //不添加if无法编译
        if (true) {
            System.out.println(Thread.currentThread());
            while (true) {
            }
        }
    }

    public static void main(String[] args) {
       Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "start");
                DeadLoopClass deadLoopClass = new DeadLoopClass();
                System.out.println(Thread.currentThread()+"run over");
            }
        };
        Thread thread = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread.start();
        thread2.start();
    }
}
