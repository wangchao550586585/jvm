package B_gc;

/*
 * 
 * 动态对象年龄判定以及长期存活的对象将进入老年代
 * 
 * 虚拟机采用分代收集的思想管理内存，
	虚拟机给每个对象定义一个对象年龄(Age)计数器，用来识别哪些对象应该放在老年代还是新生代
	对象在Eden出生并且经过一次Minor GC后依然存活，并且能被Survivor容纳的话，被移动到此空间，并将对象年龄设为1
	对象在Survivor区中每熬过一次Minor GC，年龄增加一岁，当他的年龄达到一定程度(默认15)，晋升到老年代。

 * -XX:+PrintGCDetails -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8  
 * -XX:+UseSerialGC -XX:MaxTenuringThreshold=1
 * -XX:+PrintTenuringDistribution
 * 
 * -XX:MaxTenuringThreshold:设置晋升老年代的年龄阀值
 * -XX:+PrintTenuringDistribution:打印年龄详细信息
 */
public class G_MajorGC {
	private static final int _1MB = 1024 * 1024;

	/*动态对象年龄判定
	如果在Survivor空间中相同年龄所有对象大小的总和大于Suvivor空间的一半，
	年龄大于或者等于该年龄的对象就可以直接进入老年代，无需等待MaxTenuringThreshold中要求的年龄*/
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		byte[] allocation1, allocation2, allocation3, allocation4;
		// 执行第一次Minor GC
		allocation1 = new byte[_1MB / 4];
		allocation2 = new byte[_1MB / 4];
		allocation3 = new byte[4 * _1MB];
		allocation4 = new byte[4 * _1MB];
		allocation4 = null;
		// 执行第二次Minor GC,allocation1进入老年代
		allocation4 = new byte[4 * _1MB];
	}

	// 长期存活的对象将进入老年代
	@SuppressWarnings("unused")
	public static void testTenuringThreshold() {
		byte[] allocation1, allocation2, allocation3;
		// 执行第一次Minor GC
		allocation1 = new byte[_1MB / 4];
		allocation2 = new byte[4 * _1MB];
		allocation3 = new byte[4 * _1MB];
		allocation3 = null;
		// 执行第二次Minor GC,allocation1进入老年代
		allocation3 = new byte[4 * _1MB];
	}

}
