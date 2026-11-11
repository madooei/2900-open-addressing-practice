package practice;

/** Practice problem: count the subarrays that sum to k by trying every subarray. */
public final class SubarraySumBruteForce {

  private SubarraySumBruteForce() {
    // This class should not be instantiated!
  }

  // Returns how many subarrays of nums sum to exactly k. Subarrays that start
  // or end at different positions count separately. Assumes nums is not null.
  public static int subarraySum(int[] nums, int k) {
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
}
