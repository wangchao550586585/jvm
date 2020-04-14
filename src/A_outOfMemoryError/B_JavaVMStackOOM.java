package A_outOfMemoryError;
/*
内存溢出Out Of Memory Error
创建线程导致内存溢出异常
 * 可能会导致系统假死、
 * 
 * -Xss2M:配置栈内存
 */
public class B_JavaVMStackOOM {
	private void dontStop() {
		while (true) {
		}
	}

	public void stackLeakByThread() {
		while (true) {
			new Thread(new Runnable() {
				public void run() {
					dontStop();
				}
			}).start();
		}
	}

	public static void main(String[] args) {
		B_JavaVMStackOOM javaVMStackOOM = new B_JavaVMStackOOM();
		javaVMStackOOM.stackLeakByThread();
	}

}
