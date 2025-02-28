package leetcode.priority_queue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

public class _2353设计食物评分系统 {
    public static void main(String[] args) {
        String[] foods = {"kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"};
        String[] cuisines = {"korean", "japanese", "japanese", "greek", "japanese", "korean"};
        int[] ratings = {9, 12, 8, 15, 14, 7};
        FoodRatings obj = new FoodRatings(foods, cuisines, ratings);
//        obj.changeRating(food, newRating);
//        String param_2 = obj.highestRated(cuisine);
    }
}

/**
 * 73/78, 超时
 */
class FoodRatings {
    /**
     * 1、需求分析
     * 保存所有食物的信息，可以修改食物的评分，
     * 返回某种烹饪方式下评分最高的食物（如果评分相等，返回字典序较小的食物）
     * 2、设计
     * 食物信息存储: {烹饪方式: [(食物，评分)]}
     * (食物，评分)可以使用类来实现，然后使用优先级队列保存该对象列表，
     * 另外，需要保存食物到烹饪方式的映射以便changeRating()中调用
     */
    Map<String, String> food2cuisine;
    Map<String, PriorityQueue<Food>> info;

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        int n = foods.length;
        food2cuisine = new HashMap<>();
        info = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            String food_name = foods[i], cuisine = cuisines[i];
            int rating = ratings[i];
            Food food = new Food(food_name, rating);
            food2cuisine.put(food_name, cuisine);
            PriorityQueue<Food> v = info.getOrDefault(cuisine, new PriorityQueue<Food>());
            v.add(food);
            info.put(cuisine, v);
        }

        for (Map.Entry<String, PriorityQueue<Food>> item : info.entrySet()) {
            System.out.print("{key: " + item.getKey());
            System.out.println(", value: " + item.getValue() + "}");
        }
    }

    public void changeRating(String foodName, int newRating) {
        String cuisine = food2cuisine.get(foodName);
        if (cuisine != null) {
            // 获取该类别的食物队列
            PriorityQueue<Food> queue = info.get(cuisine);
            if (queue != null) {
                // 找到并移除该食物
                queue.removeIf(food -> food.getName().equals(foodName));
                // 更新该食物的 rating
                Food updatedFood = new Food(foodName, newRating);
                queue.add(updatedFood);  // 重新添加该食物到队列中
            }
        }
    }

    public String highestRated(String cuisine) {
        PriorityQueue<Food> queue = info.get(cuisine);
        if (queue != null && !queue.isEmpty()) {
            return queue.peek().getName();  // 获取第一个食物（优先级最高的）
        }
        return null;  // 如果该类别不存在或队列为空，返回 null
    }
}

class Food implements Comparable<Food> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return rating == food.rating && Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rating);
    }

    private String name;
    private int rating;
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getName() {
        return name;
    }

    Food() {

    }

    Food(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    @Override
    public int compareTo(Food other) {
        if (this.rating != other.rating) {
            return Integer.compare(other.rating, this.rating);
        }
        return this.name.compareTo(other.name);
    }
}
