package F_jvmExecution;

/**
 * 方法动态分派
 * Created by Administrator on 2017/5/26.
 */
public class DynamicDispatch {
    static abstract class Human {
        protected abstract void sayHello();
    }

    static class Man extends Human {

        @Override
        protected void sayHello() {
            System.out.println("say man");
        }
    }

    static class WoMan extends Human {

        @Override
        protected void sayHello() {
            System.out.println("say woman");
        }
    }

    public static void main(String[] args) {
        //2次调用中的invokevirtual指令把常量池中的类方法符号引用解析到了不同的直接引用上
        //这就是重写的本质。把运行期根据实际类型确定方法执行版本的分派过程称为动态分配
        Human man = new Man();
        Human woMan = new WoMan();
        man.sayHello();
        woMan.sayHello();
        man=new WoMan();
        man.sayHello();
    }
}
