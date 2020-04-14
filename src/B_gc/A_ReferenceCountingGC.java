package B_gc;

/*
-XX:+PrintGCTimeStamps
-XX:+PrintGCDetails
-Xloggc:<filename>
 *
 *测试java是不是使用的引用计数算法判断对象存活
 *通过240K->160K(123712K)可知虚拟机没有因为这2个对象互相引用就不回收他们，也侧面说明虚拟机并不是通过引用计数算法来判断对象是否存活的
 *
 *判断对象是否存活常用算法
	***引用计数算法：给对象添加一个引用计数器，每当有一个地方引用它时，计数器值+1，当引用失效时，计数器值-1.
			任何时刻计数器都为0的对象就是不可能在被使用。
			效率高，
			很难解决对象之间的相互循环引用的问题
 * 
 * 
	***根搜索算法(java/c#/lisp)
		通过一系列名为"GC Roots"的对象作为起始点，从这些节点开始向下搜索，搜索所走过的路径称为引用链
		当一个对象到GC Roots没有任何引用链相连(用图论的话来说就是从GC Roots到这个对象不可达)时，
		则证明此对象是不可用的，
		java语言中，作为GC Roots的对象包括下面几种
		1:虚拟机栈(栈帧中的本地变量表)中的引用的对象
		2：方法去中的类静态属性引用的对象
		3：方法区中的常量引用的对象
		4：本地方法栈中jni(即一般说的native方法)的引用的对象
 */
public class A_ReferenceCountingGC {

	public Object install = null;
	private static final int _1MB = 1024 * 1024;
	private byte[] bigSize = new byte[2 * _1MB];// 占点内存。以便在gc日志中看清楚是否非收回

	public static void main(String[] args) {
		A_ReferenceCountingGC objA = new A_ReferenceCountingGC();// 这里被引用一次
		A_ReferenceCountingGC objB = new A_ReferenceCountingGC();
		objA.install = objB;
		objB.install = objA;// 引用2次
		objA = null;// 失效，减去一次.
		objB = null;
		System.gc();
	}

}
