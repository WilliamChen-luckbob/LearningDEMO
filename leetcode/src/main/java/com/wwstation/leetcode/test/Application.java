package com.wwstation.leetcode.test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author william
 * @description
 * @Date: 2021-09-15 18:51
 */
public class Application {
    public static void main(String[] args) {
        int[] ints={-2,1,-3,4,-1,2,1,-5,4};
        int i = maxSubArray(ints);
    }

    public static int maxSubArray(int[] nums) {
        //之前和
        int pre = 0;
        //最大和
        int maxAns = nums[0];

        for (int x : nums) {
            //计算当前和与当前值谁大取谁（当前值大于等于之前和，相当于用当前值替换之前和）
            pre = Math.max(pre + x, x);
            //将结果与最大和比较
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

}
