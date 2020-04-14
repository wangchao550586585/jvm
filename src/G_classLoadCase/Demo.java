package G_classLoadCase;

import java.io.FileInputStream;

/**实现远程执行功能
 * Created by Administrator on 2017/6/1.
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        FileInputStream is = new FileInputStream("E:/idea-workspace/jvm/out/production/jvm/G_classLoadCase/TestClass.class");
        byte[] b = new byte[is.available()];
        is.read(b);
        is.close();
        System.out.println(JavaClassExecuter.execute(b));
    }
}
