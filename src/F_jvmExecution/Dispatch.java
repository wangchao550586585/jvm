package F_jvmExecution;

/**
 * 单分派/多分派
 * 方法的接受者与方法的参数统称为方法宗量
 * 根据分派基于多少种宗量,将分派划分为单/多分派
 * 单分派:根据一个宗量对目标方法进行选择
 * 多分派:根据多1个宗量对目标方法进行选择
 * Created by Administrator on 2017/5/26.
 */
public class Dispatch {
    static class QQ {
    }

    static class _360 {
    }

    public static class Fatcher {
        public void hardChoice(QQ arg) {
            System.out.println("fatcher qq");

        }

        public void hardChoice(_360 arg) {
            System.out.println("fatcher 360");
        }
    }
    public static class Son extends Fatcher
    {
        @Override
        public void hardChoice(QQ arg) {
            System.out.println ("son qq");
        }

        @Override
        public void hardChoice(_360 arg) {
            System.out.println("son 360");
        }
    }

    public static void main(String[] args) {
        //静态分派过程(编译阶段编译器选择过程),选择目标方法有2依据
        //1：静态类型
        //2：参数类型
        //最终产生了2条指令，invokevirtual指令,
        //2条指令参数指向Fatcher.hardChoice(360)/Fatcher.hardChoice(qq)
        //因为根据2宗量选择，所以java静态分派属于多分派类型
        Fatcher fatcher = new Fatcher();
        Son son = new Son();
        fatcher.hardChoice(new _360());
        son.hardChoice(new QQ());

        //动态分派过程(运行阶段虚拟机的选择),
        //由于编译期已经决定目标方法的签名必须为hardChoice(QQ),此时唯一可以影响虚拟机选择的因素
        //只有方法接受者的实际类型是Fatcher/Son，因为只有一个宗量作为选择,所以java动态分派属于单分派
    }
}
