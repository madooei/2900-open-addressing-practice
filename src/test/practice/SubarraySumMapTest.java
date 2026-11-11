package practice;

/** Runs the subarray sum scenarios against the prefix-sum map solution. */
public class SubarraySumMapTest extends SubarraySumTest {

  @Override
  protected int subarraySum(int[] nums, int k) {
    return SubarraySumMap.subarraySum(nums, k);
  }
}
