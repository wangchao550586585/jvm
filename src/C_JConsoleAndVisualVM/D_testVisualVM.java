package C_JConsoleAndVisualVM;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * 测试BTrace动态日志跟踪
 * 
 * VisualVM，早于1.6的平台需要打开-Dcom.sun.management.jmxremote
下载插件会jdk1.6\lib\visualvm放在此目录下

生成dump文件2种方式
1:应用程序点击右键，选择堆dump
2:应用程序窗口双击应用程序节点打开应用程序标签，然后在"监视"标签中单机“堆Dump”

保存以及导入
dump文件保存，创建dump文件后，右键-->另存为,不这样的话关闭visualVM时默认当做临时文件处理掉
导入：点击window视图-->文件-->装入-->选择文件

dump文件中Profiler页签
CPU分析:统计每个方法的执行次数，执行耗时
内存分析：统计每个方法关联的对象数以及这些对象所占的时间

注意：jdk1.5之后，在client模式下的虚拟机加入并且自动开启了类共享，这是一个在多虚拟机进程中共享rt.jar中的类数据以提高加载速度和节省内存的优化
VisualVM的Profiler功能可能会因为类共享而导致被监视的应用程序崩溃，建立关闭类共享优化-Xshare:off

BTrace动态日志跟踪
BTrace：在不停止目标程序运行的前提下，通过HotSpot虚拟机的HotSwap技术动态加入原本并不存在的调试代码。
范围使用：排除错误，如方法参数，返回值
1：下载BTrace插件
2: 点击应用程序面板右键，点击Trace Application
 * 
 * import com.sun.btrace.annotations.*;  
import static com.sun.btrace.BTraceUtils.*;  
@BTrace  
public class TracingScript {  
   @OnMethod(  
      clazz="C_JConsoleAndVisualVM.D_testVisualVM",  
      method="add",  
      location=@Location(Kind.RETURN)  
   )  
   public static void func(@Self C_JConsoleAndVisualVM.D_testVisualVM instance,int a,int b ,@Return int result){  
 
    jstack();
    println(strcat("方法参数A:",str(a)));
        println(strcat("方法参数B:",str(b)));
            println(strcat("方法结果:",str(result)));
   }  
}  
 */
public class D_testVisualVM {
	public int add(int a, int b) {
		return a + b;
	}

	public static void main(String[] args) throws Exception {
		D_testVisualVM b_Trace = new D_testVisualVM();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < 10; i++) {
			br.readLine();
			int a = (int) (Math.random() * 1000);
			int b = (int) (Math.random() * 1000);
			System.out.println(b_Trace.add(a, b));
		}
	}
}
