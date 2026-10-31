package practice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The scenarios for the subarray sum equals k problem. A concrete subclass
 * supplies subarraySum() to pick the solution under test.
 */
public abstract class SubarraySumTest {

  // Calls the solution under test.
  protected abstract int subarraySum(int[] nums, int k);

  @Test
  public void emptyArrayHasNoSubarrays() {
    assertEquals(0, subarraySum(new int[] {}, 0));
  }

  @Test
  public void singleElementEqualToKCounts() {
    assertEquals(1, subarraySum(new int[] {8}, 8));
  }

  @Test
  public void singleElementNotEqualToKDoesNotCount() {
    assertEquals(0, subarraySum(new int[] {3}, 8));
  }

  @Test
  public void noSubarrayReachesK() {
    assertEquals(0, subarraySum(new int[] {1, 2, 3}, 7));
  }

  @Test
  public void subarrayAtTheStartCounts() {
    assertEquals(1, subarraySum(new int[] {3, 5, 1}, 8));
  }

  @Test
  public void overlappingSubarraysBothCount() {
    assertEquals(2, subarraySum(new int[] {1, 1, 1}, 2));
  }

  @Test
  public void subarrayWithANegativeValueCounts() {
    assertEquals(2, subarraySum(new int[] {3, 5, -2, 4, 1}, 8));
  }

  @Test
  public void repeatedPrefixSumCountsEachOccurrence() {
    assertEquals(3, subarraySum(new int[] {1, -1, 1}, 1));
  }

  @Test
  public void negativeValuesCanSumToZero() {
    assertEquals(1, subarraySum(new int[] {-1, -1, 1}, 0));
  }

  @Test
  public void allZerosWithKZeroCountsEverySubarray() {
    assertEquals(6, subarraySum(new int[] {0, 0, 0}, 0));
  }

  // The values 2, -1, 2, -1, ..., forty in all. The subarrays that sum to 3
  // have length 3 or 6 when they start on a 2 (19 and 18 of them), and length
  // 6 or 9 when they start on a -1 (17 and 16 of them): 70 in all.
  @Test
  public void longArrayCountsEveryMatch() {
    int[] nums = new int[40];
    for (int i = 0; i < nums.length; i += 2) {
      nums[i] = 2;
      nums[i + 1] = -1;
    }
    assertEquals(70, subarraySum(nums, 3));
  }
}
