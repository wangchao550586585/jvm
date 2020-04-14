package F_jvmExecution;

/**
 * 方法静态分派
 * Created by Administrator on 2017/5/26.
 */
public class StaticDispatch {
    static abstract class Human {

    }

    static class Man extends Human {

    }

    static class Woman extends Human {

    }

    public void sayHello(Human guy) {
        System.out.println("hello,guy");
    }

    public void sayHello(Man guy) {
        System.out.println("hello,man-guy");

    }

    public void sayHello(Woman guy) {
        System.out.println("hello,woman-guy");
    }

    public static void main(String[] args) {
        //所有依赖静态类型来定位方法执行版本的分派动作,称为静态分配
        //如:方法重载，静态分配发生在编译阶段

        //human-->变量静态类型/外观类型
        //Man-->变量实际类型
        //静态类型的变化在使用时发生,变量本身静态类型不会改变,最终的静态类型编译器可知
        //实际类型变化的结果在运行期才确定,编译器在编译程序时并不知道一个对象的实际类型是什么

        //实际类型变化
        Human human = new Man();
        Human woman = new Woman();
        StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.sayHello(human);
        staticDispatch.sayHello(woman);

        //静态类型变化
        staticDispatch.sayHello((Man)human);
        staticDispatch.sayHello((Woman)woman);
    }

}
