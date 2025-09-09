package javaguide.base;

/**
 * Java 中 String 学习：
 * Warning: File ./StringStudy.class does not contain class StringStudy
 * Compiled from "StringStudy.java"
 * public class javaguide.base.StringStudy {
 *   public javaguide.base.StringStudy();
 *     Code:
 *        0: aload_0                           // 槽位0表示当前对象引用。编号表示当前指令在code数组中的偏移，单位是字节
 *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *        4: return
 *
 *   public static void main(java.lang.String[]);
 *     Code:
 *        0: ldc           #2                  // String hello 读取常量池中#2号元素，将其压入操作数栈
 *        2: astore_1      // 将操作数栈顶元素弹出，放到局部变量表中1号槽位（slot）中
 *        3: new           #3                  // class java/lang/String ，分配对象，压栈
 *        6: dup           // 复制栈顶元素，用于构造函数调用，栈内容：【string_ref,string_ref】
 *        7: ldc           #2                  // String hello，压常量池中#2号元素，【string_ref,string_ref，#2】
 *        9: invokespecial #4                  // Method java/lang/String."<init>":(Ljava/lang/String;)V【string_ref】
 *       12: astore_2       // 将构造完毕的String对象置于槽位2处，栈变空【】
 *       13: getstatic     #5                  // Field java/lang/System.out:Ljava/io/PrintStream;，将静态对象引用压栈【PrintStream】
 *       16: aload_1       // 将槽位1的字符串常量"hello"压栈，【PrintStream，"hello"】
 *       17: aload_2       // 将槽位2的字符串对象String("hello")压栈，【PrintStream，"hello", String("hello")】
 *       18: if_acmpne     25  // a表示引用地址的比较，ne是not equals，弹出栈顶的两个引用地址，然后比较，【PrintStream】
 *       21: iconst_1          // 将常量1压栈，表示true，即不相等，【PrintStream，true】
 *       22: goto          26
 *       25: iconst_0          // 将常量0压栈，表示false，即相等,【PrintStream， false】
 *       26: invokevirtual #6                  // Method java/io/PrintStream.println:(Z)V
 *       29: return
 * }
 */
public class StringStudy {

    public static void main(String[] args) {
        String a = "hello";
        String b = new String("hello");
        System.out.println(a == b);
    }
}
