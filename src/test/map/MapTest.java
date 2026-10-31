package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * The contract test suite for the Map ADT, written against the Map interface
 * alone. A concrete subclass supplies createMap() to pick the implementation.
 */
public abstract class MapTest {

  protected Map<String, Integer> map;

  protected abstract Map<String, Integer> createMap();

  @BeforeEach
  public void setup() {
    map = createMap();
  }

  @Test
  public void newMapIsEmpty() {
    assertEquals(0, map.size());
  }

  @Test
  public void getOnEmptyMapReturnsNull() {
    assertNull(map.get("a"));
  }

  @Test
  public void containsKeyOnEmptyMapReturnsFalse() {
    assertFalse(map.containsKey("a"));
  }

  @Test
  public void removeOnEmptyMapReturnsFalse() {
    assertFalse(map.remove("a"));
  }

  @Test
  public void removeOnEmptyMapLeavesItEmpty() {
    map.remove("a");
    assertEquals(0, map.size());
  }

  @Test
  public void iteratorOnEmptyMapHasNoKeys() {
    assertFalse(map.iterator().hasNext());
  }

  @Test
  public void entriesOnEmptyMapHaveNoPairs() {
    assertFalse(map.entries().iterator().hasNext());
  }

  @Test
  public void putNewKeyReturnsTrue() {
    assertTrue(map.put("a", 1));
  }

  @Test
  public void putGrowsSize() {
    map.put("a", 1);
    assertEquals(1, map.size());
  }

  @Test
  public void containsKeyPutKeyReturnsTrue() {
    map.put("a", 1);
    assertTrue(map.containsKey("a"));
  }

  @Test
  public void getReturnsThePutValue() {
    map.put("a", 1);
    assertEquals(1, map.get("a"));
  }

  @Test
  public void containsKeyAbsentKeyReturnsFalse() {
    map.put("a", 1);
    assertFalse(map.containsKey("z"));
  }

  @Test
  public void getAbsentKeyReturnsNull() {
    map.put("a", 1);
    assertNull(map.get("z"));
  }

  @Test
  public void containsKeyDoesNotChangeSize() {
    map.put("a", 1);
    map.containsKey("a");
    assertEquals(1, map.size());
  }

  @Test
  public void getDoesNotChangeSize() {
    map.put("a", 1);
    map.get("a");
    assertEquals(1, map.size());
  }

  @Test
  public void putExistingKeyReturnsFalse() {
    map.put("a", 1);
    assertFalse(map.put("a", 2));
  }

  @Test
  public void putExistingKeyDoesNotGrowSize() {
    map.put("a", 1);
    map.put("a", 2);
    assertEquals(1, map.size());
  }

  @Test
  public void putExistingKeyReplacesTheValue() {
    map.put("a", 1);
    map.put("a", 2);
    assertEquals(2, map.get("a"));
  }

  @Test
  public void removeOnlyKeyReturnsTrue() {
    map.put("a", 1);
    assertTrue(map.remove("a"));
  }

  @Test
  public void removeOnlyKeyLeavesEmpty() {
    map.put("a", 1);
    map.remove("a");
    assertEquals(0, map.size());
  }

  @Test
  public void containsKeyRemovedKeyReturnsFalse() {
    map.put("a", 1);
    map.remove("a");
    assertFalse(map.containsKey("a"));
  }

  @Test
  public void getRemovedKeyReturnsNull() {
    map.put("a", 1);
    map.remove("a");
    assertNull(map.get("a"));
  }

  @Test
  public void removeAbsentKeyReturnsFalse() {
    map.put("a", 1);
    assertFalse(map.remove("z"));
  }

  @Test
  public void removeAbsentKeyLeavesSizeUnchanged() {
    map.put("a", 1);
    map.remove("z");
    assertEquals(1, map.size());
  }

  @Test
  public void removeAbsentKeyKeepsTheExistingKey() {
    map.put("a", 1);
    map.remove("z");
    assertTrue(map.containsKey("a"));
  }

  @Test
  public void removeAbsentKeyKeepsTheExistingValue() {
    map.put("a", 1);
    map.remove("z");
    assertEquals(1, map.get("a"));
  }

  @Test
  public void putRemovedKeyAgainReturnsTrue() {
    map.put("a", 1);
    map.remove("a");
    assertTrue(map.put("a", 2));
  }

  @Test
  public void oneKeyIteratorReturnsThatKey() {
    map.put("a", 1);
    Iterator<String> it = map.iterator();
    assertEquals("a", it.next());
  }

  @Test
  public void oneKeyIteratorHasNoNextAfterIt() {
    map.put("a", 1);
    Iterator<String> it = map.iterator();
    it.next();
    assertFalse(it.hasNext());
  }

  @Test
  public void nextPastEndThrows() {
    map.put("a", 1);
    Iterator<String> it = map.iterator();
    it.next();
    try {
      it.next();
      fail("expected NoSuchElementException after the last key");
    } catch (NoSuchElementException e) {
      return;
    }
  }

  @Test
  public void hasNextDoesNotAdvance() {
    map.put("a", 1);
    Iterator<String> it = map.iterator();
    it.hasNext();
    it.hasNext();
    assertEquals("a", it.next());
  }

  @Test
  public void oneEntryIteratorReturnsThatKey() {
    map.put("a", 1);
    Iterator<Map.Entry<String, Integer>> it = map.entries().iterator();
    assertEquals("a", it.next().key());
  }

  @Test
  public void oneEntryIteratorReturnsThatValue() {
    map.put("a", 1);
    Iterator<Map.Entry<String, Integer>> it = map.entries().iterator();
    assertEquals(1, it.next().value());
  }

  @Test
  public void oneEntryIteratorHasNoNextAfterIt() {
    map.put("a", 1);
    Iterator<Map.Entry<String, Integer>> it = map.entries().iterator();
    it.next();
    assertFalse(it.hasNext());
  }

  @Test
  public void entryNextPastEndThrows() {
    map.put("a", 1);
    Iterator<Map.Entry<String, Integer>> it = map.entries().iterator();
    it.next();
    try {
      it.next();
      fail("expected NoSuchElementException after the last entry");
    } catch (NoSuchElementException e) {
      return;
    }
  }

  @Test
  public void putSeveralKeysGrowsSize() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    assertEquals(3, map.size());
  }

  @Test
  public void containsKeyReturnsTrueForEveryPutKey() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    assertTrue(map.containsKey("a"));
    assertTrue(map.containsKey("b"));
    assertTrue(map.containsKey("c"));
  }

  @Test
  public void getReturnsTheValueForEveryPutKey() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    assertEquals(1, map.get("a"));
    assertEquals(2, map.get("b"));
    assertEquals(3, map.get("c"));
  }

  @Test
  public void putExistingKeyAmongSeveralDoesNotGrowSize() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.put("b", 20);
    assertEquals(3, map.size());
  }

  @Test
  public void putExistingKeyAmongSeveralReplacesItsValue() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.put("b", 20);
    assertEquals(20, map.get("b"));
  }

  @Test
  public void putExistingKeyAmongSeveralKeepsTheOtherValues() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.put("b", 20);
    assertEquals(1, map.get("a"));
    assertEquals(3, map.get("c"));
  }

  @Test
  public void removeOneOfSeveralShrinksSize() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.remove("b");
    assertEquals(2, map.size());
  }

  @Test
  public void removeOneOfSeveralTakesItOut() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.remove("b");
    assertFalse(map.containsKey("b"));
  }

  @Test
  public void removeFirstPutKeyKeepsTheOthers() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.remove("a");
    assertTrue(map.containsKey("b"));
    assertTrue(map.containsKey("c"));
  }

  @Test
  public void removeMiddlePutKeyKeepsTheOthers() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.remove("b");
    assertTrue(map.containsKey("a"));
    assertTrue(map.containsKey("c"));
  }

  @Test
  public void removeLastPutKeyKeepsTheOthers() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.remove("c");
    assertTrue(map.containsKey("a"));
    assertTrue(map.containsKey("b"));
  }

  @Test
  public void removeOneOfSeveralKeepsTheOtherValues() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.remove("a");
    assertEquals(2, map.get("b"));
    assertEquals(3, map.get("c"));
  }

  @Test
  public void removingEveryKeyLeavesMapEmpty() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    map.remove("b");
    map.remove("a");
    map.remove("c");
    assertEquals(0, map.size());
  }

  @Test
  public void iteratorVisitsEveryKeyOnce() {
    map.put("b", 2);
    map.put("c", 3);
    map.put("a", 1);
    List<String> seen = new ArrayList<>();
    for (String key : map) {
      seen.add(key);
    }
    Collections.sort(seen);
    assertEquals(threeKeys(), seen);
  }

  @Test
  public void nextWithoutHasNextWalksAll() {
    map.put("b", 2);
    map.put("c", 3);
    map.put("a", 1);
    Iterator<String> it = map.iterator();
    List<String> seen = new ArrayList<>();
    seen.add(it.next());
    seen.add(it.next());
    seen.add(it.next());
    Collections.sort(seen);
    assertEquals(threeKeys(), seen);
  }

  @Test
  public void twoIteratorsAreIndependent() {
    map.put("a", 1);
    map.put("b", 2);
    Iterator<String> first = map.iterator();
    Iterator<String> second = map.iterator();
    first.next();
    first.next();
    assertTrue(second.hasNext());
  }

  @Test
  public void fullWalkLeavesSizeUnchanged() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    for (String key : map) {
      // just walk it
    }
    assertEquals(3, map.size());
  }

  @Test
  public void fullWalkKeepsEveryKey() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    for (String key : map) {
      // just walk it
    }
    assertTrue(map.containsKey("a"));
    assertTrue(map.containsKey("b"));
    assertTrue(map.containsKey("c"));
  }

  @Test
  public void entriesVisitEveryKeyOnce() {
    map.put("b", 2);
    map.put("c", 3);
    map.put("a", 1);
    List<String> seen = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : map.entries()) {
      seen.add(entry.key());
    }
    Collections.sort(seen);
    assertEquals(threeKeys(), seen);
  }

  @Test
  public void entriesPairEachKeyWithItsValue() {
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);
    List<String> seen = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : map.entries()) {
      seen.add(entry.key() + "=" + entry.value());
    }
    Collections.sort(seen);
    assertEquals(threePairs(), seen);
  }

  @Test
  public void entriesShowTheReplacedValue() {
    map.put("a", 1);
    map.put("a", 2);
    Iterator<Map.Entry<String, Integer>> it = map.entries().iterator();
    assertEquals(2, it.next().value());
  }

  @Test
  public void putPastInitialCapacityGrowsSize() {
    for (int i = 1; i <= 12; i++) {
      map.put("k" + i, i);
    }
    assertEquals(12, map.size());
  }

  @Test
  public void putPastInitialCapacityKeepsEveryKey() {
    for (int i = 1; i <= 12; i++) {
      map.put("k" + i, i);
    }
    for (int i = 1; i <= 12; i++) {
      assertTrue(map.containsKey("k" + i));
    }
  }

  @Test
  public void putPastInitialCapacityKeepsEveryValue() {
    for (int i = 1; i <= 12; i++) {
      map.put("k" + i, i);
    }
    for (int i = 1; i <= 12; i++) {
      assertEquals(i, map.get("k" + i));
    }
  }

  @Test
  public void putInDescendingOrderPastInitialCapacityKeepsEveryValue() {
    for (int i = 12; i >= 1; i--) {
      map.put("k" + i, i);
    }
    for (int i = 1; i <= 12; i++) {
      assertEquals(i, map.get("k" + i));
    }
  }

  @Test
  public void putNullKeyThrows() {
    try {
      map.put(null, 1);
      fail("expected IllegalArgumentException when putting a null key");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void putNullValueThrows() {
    try {
      map.put("a", null);
      fail("expected IllegalArgumentException when putting a null value");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void getNullThrows() {
    try {
      map.get(null);
      fail("expected IllegalArgumentException when calling get with null");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void containsKeyNullThrows() {
    try {
      map.containsKey(null);
      fail("expected IllegalArgumentException when calling containsKey with null");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  @Test
  public void removeNullThrows() {
    try {
      map.remove(null);
      fail("expected IllegalArgumentException when removing null");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  // The keys "a", "b", "c", in ascending order.
  private List<String> threeKeys() {
    List<String> keys = new ArrayList<>();
    keys.add("a");
    keys.add("b");
    keys.add("c");
    return keys;
  }

  // The pairs a=1, b=2, c=3, in ascending order of key.
  private List<String> threePairs() {
    List<String> pairs = new ArrayList<>();
    pairs.add("a=1");
    pairs.add("b=2");
    pairs.add("c=3");
    return pairs;
  }
}
