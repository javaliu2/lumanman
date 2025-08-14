package algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

public class HeapSort {
    void print(int[] nums) {
        buildMaxHeap(nums);
        for (int i = nums.length - 1; i >= 0; --i) {
            System.out.println(nums[0]);
            swap(nums, 0, i);
            maxHeapify(nums, 0, i);
        }
    }

    void printMin(int[] nums) {
        buildMinHeap(nums);
        for (int i = nums.length - 1; i >= 0; --i) {
            System.out.println(nums[0]);
            swap(nums, 0, i);
            minHeapify(nums, 0, i);
        }
    }
    void buildMaxHeap(int[] nums) {
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

    void buildMinHeap(int[] nums) {
        int n = nums.length;
        for (int i = n / 2 - 1; i >= 0; --i) {
            minHeapify(nums, i, n);
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
    @Test
    public void test() {
        int[] nums = {7,5,2,3,4,9,6};
        buildMaxHeap(nums);
        System.out.println(Arrays.toString(nums));
        // output:[9, 5, 7, 3, 4, 2, 6]
        // 以上输出是合理的，因为最大堆只要求节点的值大于左右节点的值，
        // 不必须大于左子树所有节点的值以及右子树所有节点的值
        // 满足节点的值大于左右子树所有节点的值的树是二叉搜索树（BST），
        // 另外最大堆是完全二叉树，而BST没有这个要求

        nums = new int[]{7,5,2,3,4,9,6};
        buildMinHeap(nums);
        System.out.println(Arrays.toString(nums));
    }

    @Test
    public void testPrint() {
        int[] nums = {7,5,2,3,4,9,6};
        print(nums);
        System.out.println("---");
        nums = new int[]{7,5,2,3,4,9,6};
        printMin(nums);
    }
}
