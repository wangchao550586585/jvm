package B_gc;

/*
 *空间分配担保
 *
 * * -XX:+PrintGCDetails -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8  
 * -XX:+UseSerialGC -XX 
 * 
 * 由于新生代使用复制算法，当Minor GC时如果存活对象过多，无法完全放入Survivor区，就会向老年代借用内存存放对象，以完成Minor GC
 * 在触发Minor GC时，虚拟机会先检测之前晋升到老年代内存的平均大小是否大于老年代的剩余内存，
 * 如果大于，则将Minor GC变为一次Full GC，如果小于，则查看虚拟机是否允许担保失败
 * （-XX:+/-HandlePromotionFailure。从jdk6.0开始，允许担保失败已变为HotSpot虚拟机所有收集器默认设置，
 * 虚拟机将不再识别该参数设置，详见JDK-6990095 : Deprecate and eliminate -XX:-HandlePromotionFailure），
 * 如果允许担保失败，则只执行一次Minor GC，否则也要将Minor GC变为一次Full GC
 * （直到GC结束时才能确定到底有多少对象需要被移动至老年代，所以在GC前，只能使用粗略的平均值进行判断）
 */
public class H_SpaceAllocationGuarantee {
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args) {
		byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
		allocation1 = new byte[_1MB * 2];
		allocation2 = new byte[_1MB * 2];
		allocation3 = new byte[_1MB * 2];
		allocation1 = null;
		allocation4 = new byte[_1MB * 2];
		allocation5 = new byte[_1MB * 2];
		allocation6 = new byte[_1MB * 2];
		allocation4 = null;
		allocation5 = null;
		allocation6 = null;
		allocation7 = new byte[_1MB * 2];
	}
}
