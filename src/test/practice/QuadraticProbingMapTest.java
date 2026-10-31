package practice;

import java.lang.reflect.Field;
import map.Map;
import map.MapTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Runs the Map contract suite against QuadraticProbingMap, then reads the
 * private slots array by reflection to check that every operation follows the
 * quadratic probe.
 */
public class QuadraticProbingMapTest extends MapTest {

  @Override
  protected Map<String, Integer> createMap() {
    return new QuadraticProbingMap<>();
  }

  @Test
  public void firstCollisionJumpsOneSlot() {
    QuadraticProbingMap<String, Integer> table = new QuadraticProbingMap<>();
    table.put("bee", 40);
    table.put("pig", 50);
    assertEquals("pig", keyAt(table, 6));
  }

  @Test
  public void secondCollisionJumpsFourSlotsFromHome() {
    QuadraticProbingMap<String, Integer> table = new QuadraticProbingMap<>();
    table.put("bee", 40);
    table.put("pig", 50);
    table.put("hen", 60);
    assertEquals("hen", keyAt(table, 2));
  }

  @Test
  public void secondCollisionSkipsTheNextSlot() {
    QuadraticProbingMap<Integer, String> table = new QuadraticProbingMap<>();
    table.put(0, "a");
    table.put(7, "b");
    table.put(14, "c");
    assertEquals(Integer.valueOf(14), keyAt(table, 4));
  }

  @Test
  public void getFollowsTheQuadraticProbe() {
    QuadraticProbingMap<String, Integer> table = new QuadraticProbingMap<>();
    table.put("bee", 40);
    table.put("pig", 50);
    table.put("hen", 60);
    assertEquals(60, table.get("hen"));
  }

  @Test
  public void containsKeyFollowsTheQuadraticProbe() {
    QuadraticProbingMap<Integer, String> table = new QuadraticProbingMap<>();
    table.put(0, "a");
    table.put(7, "b");
    table.put(14, "c");
    assertTrue(table.containsKey(14));
  }

  @Test
  public void putExistingKeyFollowsTheQuadraticProbe() {
    QuadraticProbingMap<String, Integer> table = new QuadraticProbingMap<>();
    table.put("bee", 40);
    table.put("pig", 50);
    table.put("hen", 60);
    assertFalse(table.put("hen", 99));
  }

  @Test
  public void removeFollowsTheQuadraticProbe() {
    QuadraticProbingMap<String, Integer> table = new QuadraticProbingMap<>();
    table.put("bee", 40);
    table.put("pig", 50);
    table.put("hen", 60);
    table.remove("hen");
    assertTrue(isTombstone(table, 2));
  }

  // 0, 17, 34, and 51 take four different slots out of 7. The fourth put grows
  // the table to 17 slots, where all four keys have home slot 0. The rehash
  // places 0 in slot 0, then 51, 17, and 34 at offsets 1, 4, and 9.
  @Test
  public void rehashFollowsTheQuadraticProbe() {
    QuadraticProbingMap<Integer, String> table = new QuadraticProbingMap<>();
    table.put(0, "a");
    table.put(17, "b");
    table.put(34, "c");
    table.put(51, "d");
    assertEquals(Integer.valueOf(34), keyAt(table, 9));
  }

  private static Object keyAt(QuadraticProbingMap<?, ?> table, int slot) {
    Object[] slots = (Object[]) readField(table, "slots");
    return readField(slots[slot], "key");
  }

  private static boolean isTombstone(QuadraticProbingMap<?, ?> table, int slot) {
    Object[] slots = (Object[]) readField(table, "slots");
    return (Boolean) readField(slots[slot], "deleted");
  }

  // Reads a private field of an object by reflection.
  private static Object readField(Object object, String name) {
    try {
      Field field = object.getClass().getDeclaredField(name);
      field.setAccessible(true);
      return field.get(object);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }
}
