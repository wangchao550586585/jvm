package B_gc;

/*
 * 大对象直接进入老年代
 * -XX:+PrintGCDetails -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728  -XX:+UseSerialGC
 * 
 * 大对象：需要大量连续内存空间的java对象，例如：很长字符串及数组，byte[]
	-XX:PretenureSizeThreshold:大于这个设定值的对象直接在老年代中分配，
		目的避免在Eden区和2个Survivor区之间发生大量的内存拷贝
		[只对Serial和ParNew收集器有效]
 */
public class F_BigObjectMajorGC {
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args) {
		byte[] allocation = new byte[4 * _1MB];//直接分配到老年代
	}
}
