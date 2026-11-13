package practice;

/** Practice problem: count the subarrays that sum to k as pairs of prefix sums. */
public final class SubarraySumPrefixSums {

  private SubarraySumPrefixSums() {
    // This class should not be instantiated!
  }

  // Returns how many subarrays of nums sum to exactly k. Subarrays that start
  // or end at different positions count separately. Assumes nums is not null.
  public static int subarraySum(int[] nums, int k) {
    int[] prefix = new int[nums.length + 1];  // prefix[0] stays 0: the sum of no elements
    for (int j = 1; j <= nums.length; j++) {
      prefix[j] = prefix[j - 1] + nums[j - 1];
    }

    int answer = 0;
    for (int j = 1; j <= nums.length; j++) {
      for (int i = 0; i < j; i++) {
        if (prefix[i] == prefix[j] - k) {
          answer++;  // the subarray from index i through index j - 1 sums to k
        }
      }
    }
    return answer;
  }
}
