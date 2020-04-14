package A_outOfMemoryError;
/*
 * 栈溢出
 * -Xoss：设置本地方法栈大小，由于hotspot虚拟机不区分虚拟机栈和本地方法栈，因此对于hotspot来说，这个参数无效
 * 如果线程请求的栈深度大于虚拟机所允许的最大深度，StackOverflowError异常
 * 如果虚拟机在扩展栈时无法申请到足够的内存空间，OutOfMemoryError异常
 * 存在重叠问题，当栈空间无法继续分配时，到底是内存太小，还是栈空间太大。其本质是对同一事物的2种描述
 * 
 * 测试方式
 *  -Xss128k
 * 1：-Xss配置：减少栈内存容量
 * 2：定义大量的本地变量
 * 
 * 
 * 实验结果：在单个线程下，无论栈帧大，还是虚拟机栈容量小，当内存无法分配时，虚拟机抛出的都是StackOverflowErrow异常
 * 创建线程时每次在栈开辟一个独立的空间，不停的开，倒是可以抛出OutOfMemoryError异常
 * 如果建立过多线程导致内存溢出
 * 解决方案：1：减少线程数
 * 		2：更换64位虚拟机(内存限制为4GB，32位为2GB)
 * 		3：减少最大堆和栈容量
 */
public class B_JavaVMStackSOF {
	private int stackLength = 1;

	public void stackLeak() {
		stackLength++;
		stackLeak();
	}

	public static void main(String[] args) throws Throwable {
		B_JavaVMStackSOF javaVMStackSOF = new B_JavaVMStackSOF();

		try {
			javaVMStackSOF.stackLeak();
		} catch (Throwable e) {
			System.out.println("stack length:" + javaVMStackSOF.stackLength);
			throw e;
			//stack length:11434
		}
	}
}
