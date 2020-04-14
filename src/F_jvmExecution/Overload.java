package F_jvmExecution;

import java.io.Serializable;

/**
 * 重载方法匹配优先级
 * Created by Administrator on 2017/5/26.
 */
public class Overload {
    public static void sayHello(Object arg) {
        System.out.println("hello object");
    }

    public static void sayHello(int arg) {
        System.out.println("hello int");
    }

    public static void sayHello(long arg) {
        System.out.println("hello long");
    }

    public static void sayHello(Character arg) {
        System.out.println("hello charcter");
    }

    public static void sayHello(char arg) {
        System.out.println("hello char");
    }
    public static void sayHello(char... arg) {
        System.out.println("hello char");
    }
    public static void sayHello(Serializable arg) {
        System.out.println("hello Serializable");
    }

    public static void main(String[] args) {
        //Character和Serializable优先级一样，所以需要明确指定
        //在单个参数中能成立的自动转型,char To int,在可变参数不成立
        //char-->int-->long-->Character-->Serializable-->Object-->char...
        sayHello('a');
    }

}
