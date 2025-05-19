package leetcode.array;

public class _3024三角形类型 {
    public String triangleType(int[] nums) {
        if ((nums[0] + nums[1] > nums[2]) && (nums[0] + nums[2] > nums[1]) && (nums[2] + nums[1] > nums[0])) {  // 任意两条边之和大于第三条边，而不能仅仅是一组数据成立即可
            if (nums[0] == nums[1] && nums[1] == nums[2]) {
                return "equilateral";
            }
            if (nums[0] == nums[1] || nums[1] == nums[2] || nums[0] == nums[2]) {
                return "isosceles";
            }
            return "scalene";
        } else {
            return "none";
        }
    }
}
