package E_classsLoad;


public class SuperClass {
    static {
        System.out.println("super class");
    }

    public static int value = 123;
    public static  final String HELOO_WORLD="hello world";
}

class SubClass extends SuperClass {
    static {
        System.out.println("sub class");
    }
}

class Main {
    public static void main(String[] args) {
        /*
         * 被动使用类字段1
         * 通过子类引用父类的静态字段,不会导致子类初始化
         * Created by Administrator on 2017/5/20.
         */
        //System.out.println(SubClass.value);
       /* 被动使用类字段2
        通过数组定义来引用类,不会触发此类的初始化*/
        //SuperClass[] sca = new SuperClass[10];

 /*       被动使用类字段3
        常量在编译阶段会存入调用类的常量池中,本质没有直接引用到定义常量的类,因此不会触发定义常量的类的初始化*/
        System.out.println(SuperClass.HELOO_WORLD);

    }
}
