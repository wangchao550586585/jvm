package E_classsLoad;

import java.io.IOException;
import java.io.InputStream;

/**
 * 不同的类加载器对instanceof关键字运算影响
 * Created by Administrator on 2017/5/23.
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(filename);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object newInstance = classLoader.loadClass("E_classsLoad.ClassLoaderTest").newInstance();
        System.out.println(newInstance.getClass());
        System.out.println(newInstance instanceof E_classsLoad.ClassLoaderTest);

    }
}
