package B_gc;
/*
 * 一次对象自我拯救的演示
 * 演示2点：
 * 1：对象可以在被GC时自我拯救
 * 2：这种自救机会只有一次，因为finalize()最多只会被系统调用一次
 * 
 * 这种拯救方法是为了使C/C++程序员更容易接收java的一种妥协
	运行代价高，不确定性大，无法保证各个对象的调用顺序。
	finalize()能做的工作，使用try-finally或者其他方式都可以做得更好，更及时
	
引用：
	在jdk1.2之前，java中的引用的定义很传统，如果reference类型的数据存储的数值代表的是另外一块内存的起始地址
	就称这块内存代表着一个引用。
	局限性：一个对象在这种定义下只有被引用或者没有被引用2种状态，对于如何描述一些"食之无味，弃之可惜"的对象就显得无能为力

引申出：当内存空间还足够时，则能保留在内存之中，如果内存在进行垃圾收集后还是非常紧张，则可以抛弃这些对象。

jdk1.2之后，java对引用进行了扩充,4中引用强度依次逐渐减弱
1：强引用:类似Object obj=new Object()这类引用，只要强引用还存在，垃圾收集器永远不会回收掉被引用的对象
2：软引用:用来描述一些还有用，但并非必需的对象。
	对于软引用关联着的对象，在系统将要发生内存溢出异常之前，将会把这些对象列进回收范围之中并进行第二次回收，
	如果这次回收还是没有足够的内存，抛出内存溢出异常，
	jdk1.2之后，提供了SoftReference类来实现软引用
3：弱引用：用来描述非必须对象，被弱引用关联的对象只能生存到下一次垃圾收集发生之前，
	当垃圾收集器工作时，无论当前内存是否足够，都会回收掉被弱引用关联的对象
	jdk1.2之后，提供了WeakReference类来实现弱引用
4：虚引用：也称为幽灵引用或者幻影引用，一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，
	也无法通过虚引用来取得一个对象实例
	为一个对象设置虚引用关联的唯一目的就是希望能在这个对象被收集器回收时受到一个系统通知
	jdk1.2之后，提供了PhantomReference类来实现虚引用

在跟搜索算法中不可达的对象，并非"非死不可"，判断其死亡需要至少经历2次标记
1：没有与GC Roots相连接的引用链，标记一次，并且进行一次筛选。
	筛选的条件是此对象是否有必要执行finalize()方法，
		对象没有覆盖finalize()方法或者finalize()方法已经被虚拟机调用过，则虚拟机判定为没有必要执行：、
		判定又必要执行后，对象会被放置在一个名为F-Queue的队列中，
			并在稍后虚拟机自动创建一条低优先级的Finalizer线程去执行
				执行是指虚拟机会触发这个方法，但并不会等待它运行结束
					目的避免对象在finalize方法中执行缓慢或者死循环，导致F-Queue队列中的其他对象永久处于等待状态
					甚至整个内存回收系统崩溃。
2:GC对F-Queue中的对象进行第二次标记
			生存：对象与引用链上任何一个对象建立关联，如把自己赋值给某个类变量或对象的成员变量。
			否则死亡
	
 */
public class B_FinalizeEscapaGC {
	public static B_FinalizeEscapaGC SAVE_HOOK = null;

	public void isAlive() {
		System.out.println("yes");
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalice method executed");
		B_FinalizeEscapaGC.SAVE_HOOK = this;// 激活
	}

	public static void main(String[] args) throws Exception {
		SAVE_HOOK = new B_FinalizeEscapaGC();

		// 对象第一次拯救自己
		SAVE_HOOK = null;
		System.gc();
		// 因为finalize()优先级很低，暂停0.5秒，以等待它,否则会被垃圾回收。
		// Thread.sleep(500);
		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("no");
		}
		
		// 与上代码一样,自救失败，因为finalize只会调用一次
		SAVE_HOOK = null;
		System.gc();
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("no");
		}

	}

}
