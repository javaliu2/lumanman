package leetcode.simulation;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class _2327_知道秘密的人数 {
    /**
     * 审题错误，人家问的是，n天之后知道秘密的人数，而我们这里的计算是整过过程中，传播的人数
     * @param n
     * @param delay
     * @param forget
     * @return
     */
    public int peopleAwareOfSecret(int n, int delay, int forget) {
        // simulation
        int sum = 0;
        int MOD = (int) Math.pow(10, 9) + 7;
        List<Person> l = new ArrayList<>();
        l.add(new Person(1));
        for (int i = 1; i < n; ++i) {
            Iterator<Person> iterator = l.iterator();
            List<Person> newPersons = new ArrayList<>(); // 临时存储要新增的人
            while (iterator.hasNext()) {
                Person p = iterator.next();
                p.day++;
                if (p.day > delay && p.day <= forget) {
                    // 传播一个人
                    newPersons.add(new Person(1));
                    sum = (sum + 1) % MOD;
                } else if (p.day > forget) {
                    // 移出列表
                    iterator.remove();
                }
            }
            l.addAll(newPersons);
        }
        return sum;
    }

    class Person {
        int day;

        Person(int day) {
            this.day = day;
        }
    }
    @Test
    public void test() {
        int n = 4, delay = 1, forget = 4;
        int i = peopleAwareOfSecret(n, delay, forget);
        System.out.println(i);
    }
}
