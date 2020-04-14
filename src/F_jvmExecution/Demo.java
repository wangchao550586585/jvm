package F_jvmExecution;

/**
 * 局部变量表slot复用对垃圾收集的影响之一
 *
 * -verbose:gc:查看垃圾收集过程
 *  局部变量表以变量槽为最小单位(variable slot)
 * Created by Administrator on 2017/5/23.
 */
public class Demo {
    public static void main(String[] args) {
        //添加变量a赋值操作,把变量对应的局部变量表清空
         {
             byte[] bytes = new byte[64 * 1024 * 1024];
         }
         int a = 0;
         System.gc();
     }
    /* public static void main(String[] args) {

         //虽然离开了bytes作用域,但此之后没有任何对局部变量表的读写操作,局部变量表关联没打断
         {
             byte[] bytes = new byte[64 * 1024 * 1024];
         }
         System.gc();
     }*/
    /*public static void main(String[] args) {
        //没回收内存，是因为bytes还在作用域中
        byte[] bytes = new byte[64 * 1024 * 1024];
        System.gc();
    }*/
}
