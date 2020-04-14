package C_JConsoleAndVisualVM;
import java.util.ArrayList;

/*
 * 
 * JConsole:java监视与管理控制台
	JDK/bin/jsonsole.exe启动JConsole

 * 测试以64K/50毫秒往java堆中填充数据，填充1000次,然后观察JConsole工具[内存]Eden和老年代的使用情况
 *
 * -Xms100m -Xmx100m -XX:+UseSerialGC
 *UseSerialGC: 串行垃圾回收器

 * 问题1:没有配置新生代的内存，可以从监控图中算出内存多少
 * 新生代Eden分配值(JConsole工具内存可得)/0.8(默认比例8：1)=新生代总内存
 * 
 * 问题2：调用System.gc()之后，老年代的柱状图依然显示为峰值状态，代码需要如何调整才能让System.gc()回收掉填充到堆中的对象
 * System.gc()之后，空间未能回收是因为List<OOMObject>list对象依然存活着，fileHeap()方法依然没有退出，因此list对象在执行System.gc()时依然处于作用域中，
 * System.gc()移动到fileHeap()方法外，调用就可以回收全部的内存
 * 
 */
public class A_testEdenAndOld {
	static class OOMObject {
		public byte[] placeholder = new byte[64 * 1024];// 64K
	}

	public static void main(String[] args) throws InterruptedException {
		fillHeap(1000);
//		System.gc();
	}

	private static void fillHeap(int num) throws InterruptedException {
		ArrayList<Object> list = new ArrayList<Object>();
		for (int i = 0; i < num; i++) {
			Thread.sleep(50);
			list.add(new OOMObject());

		}
		System.gc();
	}
}
