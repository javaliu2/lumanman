package codefun2000;

import java.util.*;

public class _2333 {
    // map<clientID, map<factor, weight>>
    public static void main(String[] args) {
        Map<String, Map<String, Integer>> records = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        while (n-- > 0) {
            String record = sc.nextLine();
            String[] values = record.split(",");
            String clientID = values[1], factorID = values[2];
            Integer factor = Integer.valueOf(values[3]);
            if (factor < 0 || factor > 100) {
                factor = 0;
            }
            Map<String, Integer> fw = records.getOrDefault(clientID, new HashMap<>());
            Integer old_factor = fw.getOrDefault(factorID, 0);
            fw.put(factorID, old_factor + factor);
            records.put(clientID, fw);
        }
        int factor_cnt = sc.nextInt();
        sc.nextLine();
        Map<String, Integer> fw = new HashMap<>();
        while (factor_cnt-- > 0) {
            String factors = sc.nextLine();
            String[] values = factors.split(",");
            String factorID = values[0];
            Integer factor = Integer.valueOf(values[1]);
            fw.put(factorID, factor);
        }

//        System.out.println(records);
//        System.out.println(fw);
        List<String> sortedClientIDs = new ArrayList<>(records.keySet());
        Collections.sort(sortedClientIDs);
        // 计算每一个client的收费
        for (String clientID : sortedClientIDs) {
            int clientV = 0;
            Map<String, Integer> fws = records.get(clientID);
            for (Map.Entry<String, Integer> entry2 : fws.entrySet()) {
                String factorID = entry2.getKey();
                Integer value = entry2.getValue();
                Integer weight = fw.getOrDefault(factorID, 0);
                clientV += value * weight;
            }
            System.out.printf("%s,%d\n", clientID, clientV);
        }
        sc.close();
    }
}
class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // 读取计费日志的数量
        int n = Integer.parseInt(in.nextLine());

        // 用于存储唯一的计费日志头部（时间戳 + 客户标识 + 计费因子）
        Set<String> heads = new HashSet<>();

        // 存储每个客户的总费用
        Map<String, Integer> ans = new HashMap<>();

        // 存储日志
        List<String> logs = new ArrayList<>();

        // 读取计费日志并去重
        for (int i = 0; i < n; i++) {
            String line = in.nextLine();  // 读取一行计费日志
            // 获取日志的头部（即前3个字段：时间戳、客户标识、计费因子）
            String head = line.substring(0, line.lastIndexOf(","));

            // 如果头部已经存在，则跳过该日志
            if (heads.contains(head)) continue;

            // 保存日志和头部信息
            logs.add(line);
            heads.add(head);
        }

        // 存储计费因子的单价
        Map<String, Integer> factor = new HashMap<>();
        int m = Integer.parseInt(in.nextLine());  // 读取计费因子的数量

        // 读取计费因子及其对应的单价
        for (int i = 0; i < m; i++) {
            String line = in.nextLine();
            String[] fac = line.split(",");  // 按逗号分割
            factor.put(fac[0], Integer.parseInt(fac[1]));  // 保存计费因子及单价
        }

        // 计算每个客户的费用
        for (String line : logs) {
            String[] log = line.split(",");  // 按逗号分割日志
            String client = log[1];           // 客户标识
            String fac = log[2];              // 计费因子
            int number = Integer.parseInt(log[3]);  // 计费时长

            // 校验计费时长，若不在有效范围则设为0
            if (number <= 0 || number > 100) number = 0;

            // 计算当前日志的费用
            int bill = number * factor.getOrDefault(fac, 0);
            // 更新客户的总费用
            ans.put(client, ans.getOrDefault(client, 0) + bill);
        }

        // 对客户的费用进行排序
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(ans.entrySet());
        sortedList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey());  // 按客户标识的字典序排序
            }
        });

        // 输出每个客户及其总费用
        for (Map.Entry<String, Integer> entry : sortedList) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }

        // 关闭扫描器
        in.close();
    }
}


