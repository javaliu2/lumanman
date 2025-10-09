package leetcode.pointer;

public class _75颜色分类 {
    public void sortColors_oneptr(int[] nums) {
        int ptr = 0, n = nums.length;
        for (int i = 0; i < n; ++i) {
            if (nums[i] == 0) {
                swap(nums, ptr, i);
                ptr++; // exclude
            }
        }
        for (int i = ptr; i < n; ++i) {
            if (nums[i] == 1) {
                swap(nums, ptr, i);
                ptr++; // exclude
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    public void sortColors_twoptr1(int[] nums) {
        int p0 = 0, p1 = 0, n = nums.length;
        for (int i = 0; i < n; ++i) {
            if (nums[i] == 1) {
                swap(nums, p1, i);
                p1++;
            } else if (nums[i] == 0) {
                swap(nums, p0, i);
                if (p0 < p1) {
                    swap(nums, p1, i);
                }
                p1++;
                p0++;
            }
        }
    }

    public void sortColors(int[] nums) {
        int p0 = 0, n = nums.length, p2 = n - 1;
        for (int i = 0; i <= p2; ++i) {
            while (i <= p2 && nums[i] == 2) {
                swap(nums, i, p2);
                p2--;
            }
            if (nums[i] == 0) {
                swap(nums, i, p0);
                p0++;
            }
        }
    }
}
