package G_classLoadCase;

import G_classLoadCase.ClassModifier;
import G_classLoadCase.HackSystem;
import G_classLoadCase.HotSwapClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * JavaClass执行工具
 * Created by Administrator on 2017/6/1.
 */
public class JavaClassExecuter {
    /**
     * 执行外部传来的java类的byte数组
     * 将输入类的byte数组中代表java.lang.System的CONSTANT_UTF8_info常量修改为劫持后的HackSystem类
     * 执行方法为该类的main(),输出结果为该类向System.out/err输出的信息
     *
     * @param classByte 代表一个java类的byte数组
     * @return 执行结果
     */
    public static String execute(byte[] classByte) {
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "G_classLoadCase.HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modiBytes);
        try {
            Method method = clazz.getMethod("main", new Class[]{String[].class});
            method.invoke(null, new String[]{null});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HackSystem.getBufferString();
    }
}
