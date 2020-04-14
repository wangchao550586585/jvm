package A_outOfMemoryError;
import java.util.ArrayList;

/*
 *
 *限制方法区大小间接的限制常量池的容量
 *-XX:PermSize=10M -XX:MaxPermSize=10M
 *	-XX:PermSize:限制方法区大小
 *	-XX:MaxPermSize:最大大小
 *
 *Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
 *PermGen space:运行时常量池属于方法区
 *
 *String.intern()：native方法，如果池中已经包含一个等于此String对象的字符串，则返回代表池中这个字符串的String对象，否则将此String对象包含的字符串添加到常量池中
 */
public class C_RuntimeConstantPoolOOM {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		int i = 0;
		while (true)
			list.add(String.valueOf(i++).intern());
	}
}
