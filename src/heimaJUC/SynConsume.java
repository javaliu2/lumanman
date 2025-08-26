package heimaJUC;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 同步消耗
 * 一个人有一个收件箱
 * 邮递员将信封投递给每个人的收件箱
 * 使用 GuardedObject 保证人和邮政员之间的一一对应
 */
public class SynConsume {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            new Person().start();
        }
        Thread.sleep(1000);
        Set<Integer> ids = MailBoxes.getIds();
        for (Integer id : ids) {
            System.out.println(id);
            new Postman(id, "message" + id).start();
        }
    }
}
class Postman extends Thread {
    private int id;
    private String message;
    public Postman(int id, String message) {
        this.id = id;
        this.message = message;
    }
    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxes.getGuardedObject(id);
        guardedObject.complete(message);
    }
}
class Person extends Thread {
    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxes.createGuardedObject();
        Object message = guardedObject.get(5000);
        System.out.println(Thread.currentThread().getName() + "获取到消息：" + message);
    }
}
class MailBoxes {
    private static Map<Integer, GuardedObject> boxes = new Hashtable<>();

    private static int id = 1;
    private static synchronized int generateId() {
        return id++;
    }
    public static GuardedObject createGuardedObject() {
        GuardedObject object = new GuardedObject(generateId());
        boxes.put(object.getId(), object);
        return object;
    }
    public static Set<Integer> getIds() {
        return boxes.keySet();
    }
    public static GuardedObject getGuardedObject(int id) {
        return boxes.get(id);
    }
}
class GuardedObject {
    private Integer id;
    private Object result;

    public Integer getId() {
        return id;
    }

    public GuardedObject(int id) {
        this.id = id;
    }
    public Object get(long timeout) {
        synchronized (this) {
            long begin = System.currentTimeMillis();
            long passedTime = 0;
            while (result == null) {
                long waitTime = timeout - passedTime;
                if (waitTime <= 0) {
                    break;
                }
                try {
                    wait(waitTime);  // 不能直接使用timeout，因为如果虚假唤醒的话，继续等待timeout个时间，那么总的等待时间就超过timeout了
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                passedTime = System.currentTimeMillis() - begin;
            }
            return result;
        }
    }
    public void complete(Object result) {
        synchronized (this) {
            this.result = result;
            notifyAll();
        }
    }
}

