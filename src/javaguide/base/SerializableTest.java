package javaguide.base;

import java.io.*;

public class SerializableTest {
    public static void serializeObject(Human human, String filename) {
        // try-with-resources语法
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(human);
            System.out.println("对象已序列化至：" + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Human deserializeObject(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Human) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        // 将Human对象序列化至文件
        Human xs = new Human("xs", "male", 25);
        xs.setWeight(75.0);
//        System.out.print("before serialize: ");
//        System.out.println(xs);
        String filename = "huamn.ser";
//        SerializableTest.serializeObject(xs, filename);
        Human human1 = SerializableTest.deserializeObject(filename);
        System.out.println(human1);
    }
}
class Human implements Serializable {
    // 该属性名称是死的，不显式的定义的话，默认的值是java根据类的属性、方法、构造函数等计算出来的hash值，
    // 一旦类的结构发生改变，该值就会发生变化，导致类的反序列化失败
//    private static final long serialVersionUID = 1L;
    private String name;
    /**
     * Context: without new_attr的xs序列化至huamn.ser,
     * 然后反序列化huamn.ser报错：
     * java.io.InvalidClassException: javaguide.base.Human; local class incompatible:
     * stream classdesc serialVersionUID = -1427374746855852501,
     * local class serialVersionUID = -7162026825614614710s
     */
//    int new_attr;
    private String gender;
    private int age;

    // transient关键字只能用于属性，表明该属性不可被序列化，
    // 当反序列化之后，被该关键字修饰的属性的值是其类型的默认值
    private transient double weight;

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                '}';
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Human(String name) {
        this.name = name;
    }

    public Human() {

    }
    public Human(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
}
