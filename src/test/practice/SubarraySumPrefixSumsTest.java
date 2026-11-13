package practice;

/** Runs the subarray sum scenarios against the prefix-sum pairs solution. */
public class SubarraySumPrefixSumsTest extends SubarraySumTest {

  @Override
  protected int subarraySum(int[] nums, int k) {
    return SubarraySumPrefixSums.subarraySum(nums, k);
  }
}
