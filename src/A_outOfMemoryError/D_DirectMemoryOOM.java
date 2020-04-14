package A_outOfMemoryError;
import java.lang.reflect.Field;

import sun.misc.Unsafe;

/*
 * 本机直接内存溢出
 * 使用DirectByteBuffer也会抛出异常，但是它并没有真正的向操作系统申请分配内存
 * 
 * 
 * DirectMemory容量可以通过 -MM:MaxDirectMemorySize指定，如果不指定，则默认与java堆的最大值(-Xmx指定)一样。
 * 
 * -Xmx20M -XX:MaxDirectMemorySize=10M
 * 
 * Exception in thread "main" java.lang.OutOfMemoryError
 */
@SuppressWarnings("restriction")
public class D_DirectMemoryOOM {
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args) throws Exception {
		// 使用unsafe申请分配内存
		Field field = Unsafe.class.getDeclaredFields()[0];
		field.setAccessible(true);
		Unsafe object = (Unsafe) field.get(null);
		while (true) {
			object.allocateMemory(_1MB);
		}
	}
}
