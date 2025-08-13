package algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class QuickSort {

    void quickSort_lomuto(int[] nums, int left, int right) {
        if (left < right) {
            int pivot_idx = partition_lomuto(nums, left, right);  // 返回pivot最终位置
            quickSort_lomuto(nums, left, pivot_idx - 1);
            quickSort_lomuto(nums, pivot_idx + 1, right);
        }
    }

    void quickSort_hoare(int[] nums, int left, int right) {
        if (left < right) {
            int border_idx = partition_hoare(nums, left, right);  // 返回边界下标
            quickSort_hoare(nums, left, border_idx);
            quickSort_hoare(nums, border_idx + 1, right);
        }
    }

    void quickSort_xs(int[] nums, int left, int right) {
        if (left < right) {
            int idx = partition_xs(nums, left, right);  // 边界下标
            quickSort_lomuto(nums, left, idx);
            quickSort_lomuto(nums, idx + 1, right);
        }
    }
    int partition_lomuto(int[] nums, int left, int right) {
        int pivot = nums[right];  // pivot固定在右边
        int i = left;  // i维护"<=pivot"的区域
        for (int j = left; j < right; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right);  // 把pivot放到正确的位置
        return i; // pivot 的位置
    }

    int partition_hoare(int[] nums, int left, int right) {
        int pivot = nums[left + ((right - left) >> 1)];
        int i = left - 1;
        int j = right + 1;
        while (true) {
            // 从左找第一个 >= pivot 的
            do { i++; } while (nums[i] < pivot);
            // 从右找第一个 <= pivot 的
            do { j--; } while (nums[j] > pivot);

            if (i >= j) return j; // j 是分界点
            swap(nums, i, j);
        }
    }

    int partition_xs(int[] nums, int left, int right) {
        int pivot = nums[left + ((right - left) >> 1)];
        int i = left, j = right;
        while (i < j) {
            while (nums[i] < pivot) {
                i++;
            }
            while (nums[j] > pivot) {
                j--;
            }
            // 找到一个逆序对，交换元素
            if (i < j) {
                swap(nums, i, j);
                i++;
                j--;
            }
        }
        return j;  // 返回边界下标
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    @Test
    public void test() {
        int[] nums = {4,5,2,3,7,9,6};
        quickSort_hoare(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{4,5,2,3,7,9,6};
        quickSort_lomuto(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{4,5,2,3,7,9,6};
        quickSort_xs(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }
}
