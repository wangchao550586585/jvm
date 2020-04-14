package C_JConsoleAndVisualVM;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * 线程监控
 */
public class B_testSynAddRunalbe {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		createBusyThread();
		createLockThread(new Object());
	}

	// 线程锁等待
	public static void createLockThread(final Object lock) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "testLockThread");
		thread.start();
	}

	// 线程死循环
	public static void createBusyThread() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while (true)
					;
			}
		}, "testBusyThread");
		thread.start();
	}
}
