package A_outOfMemoryError;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/*
 * 借助cglib(创建多个类)使得方法区内存溢出异常,还需要导入相应的包,cglib-nodep-3.2.4.jar导入其他异常
 *  限制方法区大小
 *-XX:PermSize=10M -XX:MaxPermSize=10M
 *	-XX:PermSize:限制方法区大小
 *	-XX:MaxPermSize:最大大小
 *
 *
 *java.lang.OutOfMemoryError: PermGen space
 */
public class E_JavaMethodAreaOOM {

	public static void main(String[] args) {
		while (true) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {
				public Object intercept(Object obj, Method arg1, Object[] arg2,
						MethodProxy proxy) throws Throwable {
					return proxy.invokeSuper(obj, arg2);
				}
			});
			enhancer.create();
		}
	}

	static class OOMObject {
		public void say(String name) {
			System.out.println(name);
		}
	}
}
