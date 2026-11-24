package demo;

import java.util.Arrays;
import practice.SubarraySumMap;

/** A demo of SubarraySumMap on the example the notes trace. */
public final class SubarraySumMapDemo {

  private SubarraySumMapDemo() {
    // This class should not be instantiated!
  }

  public static void main(String[] args) {
    int[] nums = {1, -1, 1};
    int k = 1;
    System.out.println("nums: " + Arrays.toString(nums) + ", k = " + k);

    // The first [1], the last [1], and the whole array sum to 1, so expect 3.
    int count = SubarraySumMap.subarraySum(nums, k);
    System.out.println("subarrays that sum to k: " + count);
  }
}
