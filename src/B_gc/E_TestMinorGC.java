package B_gc;

/*
 * 测试新生代
 * -XX:+PrintGCDetails -verbose:gc  -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseSerialGC
 * -Xms20M -Xmx20M:限制堆大小20M
 *  -Xmn10M ：分配给新生代,剩余的10M分配给老年代
 *  -XX:SurvivorRatio=8：决定了新生代中的Eden区与Survivor区的空间比例时8：1
 *  -XX:+UseSerialGC:使用Serial+Serial Old的收集器组合进行内存回收
 *  
 * 自动内存管理	
	解决2个问题：给对象分配内存，回收分配对象的内存
对象优先在Eden分配，当Eden没有足够的空间进行分配时，虚拟机发起一次Minor GC
GC期间虚拟机将对象全部放入Survivor空间，如果不能放入，则通过分配担保机制提前转移到老年代区。

新生代GC(Minor GC)：指发生在新生代的垃圾收集动作，因为java对象大多具备朝生夕灭的特征，MinorGC非常频繁，回收速度也快
老年代GC(Major GC/Full GC)：指发生在老年代的GC，出现了Major GC经常会伴随至少一次的Minor GC，速度比MinOr GC慢10倍以上

以下运行过程为:堆大小设置20M，分配10M给新生代和老年代，新生代的Eden和Survivor区的比例为8：1，新生代总可用空间为9M
allocation1,2,3存入新生代Eden区域
，当执行到allocation4时，因为可用空间不足，所以执行Minor GC，
发现allocation1,2,3无法放入Survivor空间，
提前allocation1,2,3存入老年代，在在Eden放入allocation4

新生代占用4M
老年代占用6M
 * 
 */
public class E_TestMinorGC {
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args) {
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation4 = new byte[4 * _1MB];// 出现一次你Minor GC
	}
}
