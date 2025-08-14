package algorithm.sort;

import org.junit.Test;

public class QuickSelect {

    public static int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, k);
    }

    private static int quickSelect(int[] nums, int left, int right, int k) {
        int pivotIndex = partition(nums, left, right);
        int count = pivotIndex - left + 1; // 左边（含 pivot）有多少个数

        if (count == k) {
            return nums[pivotIndex];
        } else if (count > k) {  // 第k个数就在前count个数里，所以还是第k个
            return quickSelect(nums, left, pivotIndex - 1, k);
        } else {  // 第k个数不在前count个数里，所以是右边序列的第count-k个
            return quickSelect(nums, pivotIndex + 1, right, k - count);
        }
    }

    /**
     * 倒序排列
     */
    private static int partition(int[] nums, int left, int right) {
        int pivot = nums[right]; // 选择最后一个元素作为 pivot
        int i = left;
        for (int j = left; j < right; j++) {
            if (nums[j] >= pivot) { // 找第 k 大，所以大于 pivot 放左边
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right);
        return i;
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {3,2,1,5,6,4};
        int k = 2;
        System.out.println(findKthLargest(arr, k)); // 输出第2大的元素
    }
}

/**
 * 官方实现
 */
class QuickSelectSolution {
    int quickselect(int[] nums, int l, int r, int k) {
        if (l == r) return nums[k];
        int pivot = nums[l], i = l - 1, j = r + 1;
        while (i < j) {
            do i++; while (nums[i] < pivot);
            do j--; while (nums[j] > pivot);
            if (i < j){
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
        }
        if (k <= j) return quickselect(nums, l, j, k);
        else return quickselect(nums, j + 1, r, k);
    }
    public int findKthLargest(int[] _nums, int k) {
        int n = _nums.length;
        // n-k是第k个元素在升序数组中的下标
        return quickselect(_nums, 0, n - 1, n - k);
    }

    @Test
    public void test() {
        int[] nums = {7, 5, 2, 3, 4, 9, 6};
        int k = 3;
        int res = findKthLargest(nums, k);
        System.out.println(res);
    }
}

class MaxHeapSelectSolution {
    public int findKthLargest(int[] nums, int k) {
        int heapSize = nums.length;
        buildHeap(nums);
        for (int i = nums.length - 1; i >= nums.length - k + 1; --i) {
            swap(nums, 0, i);
            --heapSize;
            maxHeapify(nums, 0, heapSize);
        }
        return nums[0];
    }

    void buildHeap(int[] nums) {
        int n = nums.length;
        for (int i = n / 2 - 1; i >= 0; --i) {
            maxHeapify(nums, i, n);
        }
    }

    private void maxHeapify(int[] nums, int i, int heapSize) {
        int largest = i, l = i * 2 + 1, r = i * 2 + 2;
        if (l < heapSize && nums[l] > nums[largest]) {
            largest = l;
        }
        if (r < heapSize && nums[r] > nums[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap(nums, i, largest);
            maxHeapify(nums, largest, heapSize);
        }
    }
    private void minHeapify(int[] nums, int i, int heapSize) {
        int smallest = i, l = i * 2 + 1, r = i * 2 + 2;
        if (l < heapSize && nums[l] < nums[smallest]) {
            smallest = l;
        }
        if (r < heapSize && nums[r] < nums[smallest]) {
            smallest = r;
        }
        if (smallest != i) {
            swap(nums, i, smallest);
            minHeapify(nums, smallest, heapSize);
        }
    }
    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

