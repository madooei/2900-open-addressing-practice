package practice;

import map.HashMap;
import map.Map;

/** Solutions to the subarray sum equals k problem. */
public final class SubarraySum {

  private SubarraySum() {
    // This class should not be instantiated!
  }

  // Returns how many subarrays of nums sum to exactly k. Subarrays that start
  // or end at different positions count separately. Assumes nums is not null.
  public static int subarraySumBruteForce(int[] nums, int k) {
    int answer = 0;
    for (int start = 0; start < nums.length; start++) {
      int sum = 0;
      for (int end = start; end < nums.length; end++) {
        sum += nums[end];
        if (sum == k) {
          answer++;  // the subarray from start through end sums to k
        }
      }
    }
    return answer;
  }

  // Returns how many subarrays of nums sum to exactly k. Subarrays that start
  // or end at different positions count separately. Assumes nums is not null.
  public static int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> counts = new HashMap<>();  // prefix sum -> times seen so far
    counts.put(0, 1);  // the empty prefix: a sum of zero elements

    int sum = 0;
    int answer = 0;
    for (int num : nums) {
      sum += num;
      Integer seen = counts.get(sum - k);
      if (seen != null) {
        answer += seen;  // each earlier occurrence closes one subarray ending here
      }
      Integer occurrences = counts.get(sum);
      if (occurrences == null) {
        counts.put(sum, 1);
      } else {
        counts.put(sum, occurrences + 1);
      }
    }
    return answer;
  }
}
