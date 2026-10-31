package practice;

/** Runs the subarray sum scenarios against the try-every-subarray solution. */
public class SubarraySumBruteForceTest extends SubarraySumTest {

  @Override
  protected int subarraySum(int[] nums, int k) {
    return SubarraySum.subarraySumBruteForce(nums, k);
  }
}
