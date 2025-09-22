package javaguide.base;

public class ClassLoaderStudy {
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoaderStudy.class.getClassLoader();
        System.out.println(classLoader);
    }
}
