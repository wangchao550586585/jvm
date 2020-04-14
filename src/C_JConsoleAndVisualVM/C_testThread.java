package C_JConsoleAndVisualVM;

/*
 * 线程死锁
 */
public class C_testThread implements Runnable {
	int a, b;

	public C_testThread(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public void run() {
		synchronized (Integer.valueOf(a)) {
			synchronized (Integer.valueOf(b)) {
				System.out.println(a + b);
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(new C_testThread(1, 2)).start();
			new Thread(new C_testThread(2, 1)).start();
		}
	}

}
