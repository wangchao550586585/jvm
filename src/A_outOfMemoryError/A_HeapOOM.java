package A_outOfMemoryError;

import java.util.ArrayList;

/*
 * 
 * 测试堆
 * 
 * Debug-java Application配置
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * 测试方式
 * 限制java堆大小为20M，不可扩展
 *  -verbose:gc:表示输出虚拟机中GC的详细情况
 * -Xms20m -Xmx20M -XX:+HeapDumpOnOutOfMemoryError
 * -Xms20M|-Xmx20M：堆的最小值和最大值设置为一样即可避免堆自动扩展、
 * -XX:+HeapDumpOnOutOfMemoryError:虚拟机出现内存溢出时dump出当前的内存堆转储快照以便事后进行分析
 * 
 * 测试堆溢出
 * java.lang.OutOfMemoryError异常
 * 解决方案：通过Eclipse Memory Analazer对dump出来的堆转储快照进行分析，判断是否出现了内存泄漏(Memory Leak)，还是内存溢出(Memory Overflow)
 * 
 * 
 */
public class A_HeapOOM {
	static class OOMObject {
	}

	public static void main(String[] args) {

		ArrayList<OOMObject> arrayList = new ArrayList<OOMObject>();
		while (true)
			arrayList.add(new OOMObject());
	}
}
