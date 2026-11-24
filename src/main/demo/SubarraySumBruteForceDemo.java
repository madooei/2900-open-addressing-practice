package demo;

import java.util.Arrays;
import practice.SubarraySumBruteForce;

/** A demo of SubarraySumBruteForce on the example the notes trace. */
public final class SubarraySumBruteForceDemo {

  private SubarraySumBruteForceDemo() {
    // This class should not be instantiated!
  }

  public static void main(String[] args) {
    int[] nums = {3, 5, -2, 4, 1};
    int k = 8;
    System.out.println("nums: " + Arrays.toString(nums) + ", k = " + k);

    // [3, 5] and [5, -2, 4, 1] sum to 8, so expect 2.
    int count = SubarraySumBruteForce.subarraySum(nums, k);
    System.out.println("subarrays that sum to k: " + count);
  }
}
