package com.ddf.datastructure.sort;

import java.util.Arrays;

/**
 * 选择排序
 *
 * 假定数组长度为n
 * 即先循环[0]到[n-1]中找出最小的元素然后放到[0]中，
 * 接着在剩余的[1]到[n-1]找出最小的元素然后放到[1]中,
 * 最终循环找n-1次就可以确定排序，因为最后一个元素不需要和其它比较了，其它所有已排好的元素都和最后一个元素比较过才确定位置了，
 * 所以剩余的一个元素也是有序的
 * 最差时间复杂度O(n²),最好时间复杂度O(n)
 *
 * @author dongfang.ding
 * @date 2019/6/26 17:04
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {20, 19, 18, 17, 16, 15, 14, 13};
        int[] sort = sort(arr);

        System.out.println("排序前： " + Arrays.toString(arr));
        System.out.println("排序后： " + Arrays.toString(sort));
    }

    public static int[] sort(int[] arr) {
        int[] dest = new int[arr.length];
        // 不改变原数组
        System.arraycopy(arr, 0, dest, 0, dest.length);

        int minIndex;
        int temp;
        // 找dest.length - 1次，最后一个元素已经和所有已确定位置的元素都比较过，没必要再循环
        for (int i = 0; i < dest.length - 1; i++) {
            // 假定每一次找，剩余元素的第一个元素都是默认最小(最大)值
            minIndex = i;
            // 从当前剩余数组元素中的第二个元素开始和默认的剩余元素中的第一个元素比，确定最小值，然后依次类推和剩下的接着比较，直到找到最小的
            for (int j = minIndex + 1; j < dest.length; j ++) {
                if (dest[minIndex] > dest[j]) {
                    minIndex = j;
                }
            }
            // 如果最小值不是当前正在循环位置的元素，那么说明当前该位置的元素需要交换
            if (minIndex != i) {
                temp = dest[i];
                dest[i] = dest[minIndex];
                dest[minIndex] = temp;
            }
        }
        return dest;
    }
}
