package H_CompilerOptimization;


import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import java.util.EnumSet;
import java.util.Set;

import static javax.lang.model.element.ElementKind.*;
import static javax.lang.model.element.Modifier.*;
import static javax.tools.Diagnostic.Kind.*;

/**
 * 对java程序命名进行检查
 * 通过注解api实现一个编译器插件
 * 继承AbstractProcessor类,实现process
 */
//*:表示支持所有的代码
@SupportedAnnotationTypes("*")
//可以处理那些版本的java代码
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameCheckProcessor extends AbstractProcessor {
    private NameChecker nameChecker;
























































    /**
     * 初始化名称检查插件
     *
     * @param processingEnv
     */
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        //processingEnv:全局上下文,创建新的代码,向编译器输出信息,获取其他工具都需要他
        nameChecker = new NameChecker(processingEnv);
    }

    /**
     * 对输入的语法树的各个节点进行名称检查
     *
     * @param annotations 获取到此注解处理器所要处理的注解集合
     * @param roundEnv    访问当前round中的语法树节点
     * @return
     */
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                nameChecker.checkNames(element);
            }
        }
        //返回false表示不需要改变或生成语法树的内容,无需构造新的javaCompiler实例
        return false;
    }
}

class NameChecker {
    private final Messager messager;
    NameCheckerScanner nameCheckerScanner = new NameCheckerScanner();

    public NameChecker(ProcessingEnvironment processingEnv) {
        this.messager = processingEnv.getMessager();
    }

    public void checkNames(Element element) {
        nameCheckerScanner.scan(element);
    }


    /***
     * 检查java类
     */
    private class NameCheckerScanner extends ElementScanner8<Void, Void> {
        @Override
        public Void visitType(TypeElement e, Void p) {
            scan(e.getTypeParameters(), p);
            checkCameCass(e, true);
            super.visitType(e, p);
            return null;
        }

        /**
         * 检查方法命名是否合法
         *
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() == METHOD) {
                Name simpleName = e.getSimpleName();
                if (simpleName.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    messager.printMessage(WARNING, "一个普通方法" + simpleName + "不应该与类名重复", e);
                    checkCameCass(e, false);
                }

            }
            super.visitExecutable(e, aVoid);
            return null;
        }

        /**
         * 检查变量命名是否合法
         *
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            //
            if (e.getKind() == ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e)) {
                checkAllCaps(e);
            } else {
                checkCameCass(e, false);
            }
            return null;
        }


        /**
         * 判断变量是否是常量
         *
         * @param e
         * @return
         */
        private Boolean heuristicallyConstant(Element e) {
            if (e.getEnclosingElement().getKind() == INTERFACE) {
                return true;
            } else if (e.getKind() == FIELD && e.getModifiers().containsAll(EnumSet.of(PUBLIC, STATIC, FINAL))) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 检查Element是否符合驼峰命名,
         *
         * @param e
         * @param initialCaps
         */
        private void checkCameCass(Element e, boolean initialCaps) {
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if (Character.isUpperCase(firstCodePoint)) {
                previousUpper = true;
                if (!initialCaps) {
                    messager.printMessage(WARNING, "名称" + name + "应该小写开头", e);
                    return;
                }
            } else if (Character.isLowerCase(firstCodePoint)) {
                if (initialCaps) {
                    messager.printMessage(WARNING, "名称" + name + "应该大写开头", e);
                    return;
                }
            } else {
                conventional = false;
            }
            if (conventional) {
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (Character.isUpperCase(cp)) {
                        if (previousUpper) {
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    } else {
                        previousUpper = false;
                    }
                }
            } else {
                messager.printMessage(WARNING, "名称" + name + "应该符合驼峰命名", e);
            }
        }

        /**
         * 大写命名检查,要求第一个字母必须大写英文字母,其余部分下划线OR大写字母
         *
         * @param e
         */
        private void checkAllCaps(Element e) {
            String name = e.getSimpleName().toString();
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if (!Character.isUpperCase(firstCodePoint)) {
                conventional = false;
            } else {
                boolean previousUnderscore = false;
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (cp == (int) '_') {
                        if (previousUnderscore) {
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    } else {
                        previousUnderscore = false;
                        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                            conventional = false;
                            break;
                        }
                    }
                }
            }
            if (!conventional) {
                messager.printMessage(WARNING, "常量" + name + "应该全部大写OR下划线命名,并且以字母开头", e);
            }
        }
    }
}