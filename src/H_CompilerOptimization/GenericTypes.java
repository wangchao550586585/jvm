package H_CompilerOptimization;



/**
 * 语法糖
 * 泛型
 * Created by Administrator on 2017/6/3.
 */
public class GenericTypes {
    //1：无法重载，是因为泛型擦除了，特征签名变得一致
   /* public static void method(List<String> list) {
        System.out.println("String");
    }

    public static void method(List<Integer> list) {
        System.out.println("Integer");
    }*/

   //2：能够重载，是因为在class文件格式中,方法名称和特征签名相同，但是返回值不同,被认为不同方法
 /*   public static String method(List<String> list) {
        System.out.println("String");
        return "";
    }

    public static int method(List<Integer> list) {
        System.out.println("Integer");
        return 1;
    }
*/
 //3:之所以能够根据反射获取参数化类型的原因是因为，虽然泛型擦除了但是元数据信息中保存了泛型信息
}
