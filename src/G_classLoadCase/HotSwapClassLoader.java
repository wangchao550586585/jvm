package G_classLoadCase;


/**
 * 实现远程执行功能,动态加载，不需要修改后重启服务器。
 * 为了多次载入执行类加入的加载器
 * 把defineClass()公开出来,只有外部显示调用才会使用到loadByte()
 * 由虚拟机调用,依然按照原有的双亲委派规则使用loadClass()进行类加载
 * Created by Administrator on 2017/5/27.
 */
public class HotSwapClassLoader extends ClassLoader {
    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }

}
