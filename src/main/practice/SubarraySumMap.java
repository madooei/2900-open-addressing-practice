package practice;

import map.HashMap;
import map.Map;

/** Practice problem: count the subarrays that sum to k with a map of prefix sums. */
public final class SubarraySumMap {

  private SubarraySumMap() {
    // This class should not be instantiated!
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
