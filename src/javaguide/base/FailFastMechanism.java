package javaguide.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 快速失败机制
 */
public class FailFastMechanism {

    public static void main(String[] args) {
        List<String> l = new ArrayList<>();
        l.add("xs");
        l.add("cr");
        for (String s : l) {
            if (s.equals("xs")) {
                l.remove(s);
            }
            l.add("jj");
        }
    }
}
