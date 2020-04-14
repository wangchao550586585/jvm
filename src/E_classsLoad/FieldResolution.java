package E_classsLoad;

/**
 * 检验当出现多个相同字段时是否能解析
 * 能
 * Created by Administrator on 2017/5/22.
 */
public class FieldResolution {
    interface Interface0{
        int A=0;
    }interface Interface1 extends Interface0{
        int A=1;
    }
    interface Interface2{
        int A=2;
    }
    static class Parent implements Interface1{
        public static int A=3;
    }
    static class Sub extends Parent implements Interface2{
        public static int A=4;
    }

    public static void main(String[] args) {
        System.out.println(Sub.A);
    }
}
