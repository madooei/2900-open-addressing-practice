package demo;

import java.util.Arrays;
import practice.SubarraySumPrefixSums;

/** A demo of SubarraySumPrefixSums on the example the notes trace. */
public final class SubarraySumPrefixSumsDemo {

  private SubarraySumPrefixSumsDemo() {
    // This class should not be instantiated!
  }

  public static void main(String[] args) {
    int[] nums = {3, 5, -2, 4, 1};
    int k = 8;
    System.out.println("nums: " + Arrays.toString(nums) + ", k = " + k);

    // [3, 5] and [5, -2, 4, 1] sum to 8, so expect 2.
    int count = SubarraySumPrefixSums.subarraySum(nums, k);
    System.out.println("subarrays that sum to k: " + count);
  }
}
