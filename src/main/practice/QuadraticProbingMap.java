package practice;

import java.util.Iterator;
import java.util.NoSuchElementException;
import map.Map;

/**
 * A hash table implementation of the Map ADT, with open addressing and
 * quadratic probing. Every operation, rehash included, uses the same probe rule.
 *
 * @param <K> the type of keys in this map.
 * @param <V> the type of values in this map.
 */
public class QuadraticProbingMap<K, V> implements Map<K, V> {

  private Entry<K, V>[] slots;  // one entry per slot, or null when the slot is empty
  private int size;             // how many live pairs the map holds
  private int used;             // how many slots are non-empty: live entries and tombstones
  private int primeIndex;       // the current capacity is PRIMES[primeIndex]

  private static final int[] PRIMES = {
      7, 17, 37, 79, 163, 331, 673, 1361, 2729, 5471,
      10949, 21911, 43853, 87719, 175447, 350899, 701819, 1403641
  };

  private static class Entry<K, V> implements Map.Entry<K, V> {
    K key;
    V value;
    boolean deleted;  // false for a live entry, true for a tombstone

    Entry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public K key() {
      return key;
    }

    @Override
    public V value() {
      return value;
    }
  }

  // slots only ever holds Entry<K, V>, so the cast is safe.
  @SuppressWarnings("unchecked")
  public QuadraticProbingMap() {
    slots = (Entry<K, V>[]) new Entry[PRIMES[0]];  // start with 7 slots
    size = 0;                                      // the map starts empty
    used = 0;                                      // no slots are taken yet
    primeIndex = 0;                                // capacity is PRIMES[0]
  }

  @Override
  public int size() {
    return size;
  }

  // Turns a key into the index of its home slot: hash, then compress to the array's range.
  private int indexFor(K key) {
    return Math.floorMod(key.hashCode(), slots.length);
  }

  @Override
  public boolean put(K key, V value) {
    if (key == null || value == null) {
      throw new IllegalArgumentException();
    }
    int i = indexFor(key);
    int step = 1;
    int firstTombstone = -1;
    while (slots[i] != null) {
      if (slots[i].deleted) {
        if (firstTombstone == -1) {
          firstTombstone = i;  // remember the first tombstone on the path
        }
      } else if (slots[i].key.equals(key)) {
        slots[i].value = value;  // key already present; update its value
        return false;
      }
      i = (i + step) % slots.length;  // offsets 1, then 4, then 9, ... from the home slot
      step += 2;
    }
    if (firstTombstone != -1) {
      i = firstTombstone;  // the key is new; reuse the tombstone, not the empty slot
    } else {
      used++;              // filling an empty slot; one more slot is now in use
    }
    slots[i] = new Entry<>(key, value);
    size++;
    if (used > slots.length / 2) {           // more than half the slots are in use
      if (size > slots.length / 2) {         // the live entries passed the threshold: grow
        int capacity;
        if (primeIndex + 1 < PRIMES.length) {
          capacity = PRIMES[++primeIndex];   // the next prime
        } else {
          capacity = 2 * slots.length + 1;   // past the table: double and add one
        }
        rehash(capacity);
      } else {                               // tombstones pushed used past half: clean at the same size
        rehash(slots.length);
      }
    }
    return true;
  }

  @Override
  public V get(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    int i = indexFor(key);
    int step = 1;
    while (slots[i] != null) {
      if (!slots[i].deleted && slots[i].key.equals(key)) {
        return slots[i].value;  // found the key
      }
      i = (i + step) % slots.length;  // the same jumps put makes
      step += 2;
    }
    return null;  // reached an empty slot; the key is not present
  }

  @Override
  public boolean containsKey(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    int i = indexFor(key);
    int step = 1;
    while (slots[i] != null) {
      if (!slots[i].deleted && slots[i].key.equals(key)) {
        return true;
      }
      i = (i + step) % slots.length;  // the same jumps put makes
      step += 2;
    }
    return false;
  }

  @Override
  public boolean remove(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    int i = indexFor(key);
    int step = 1;
    while (slots[i] != null) {
      if (!slots[i].deleted && slots[i].key.equals(key)) {
        slots[i].deleted = true;  // leave a tombstone instead of emptying the slot
        size--;
        return true;
      }
      i = (i + step) % slots.length;  // the same jumps put makes
      step += 2;
    }
    return false;  // reached an empty slot; the key was not there
  }

  // Rebuilds the table into a fresh array of the given capacity, moving every
  // live entry and leaving the tombstones behind. primeIndex is not changed
  // here: put sets it before a growing rehash, and a same-size clean keeps it.
  @SuppressWarnings("unchecked")
  private void rehash(int capacity) {
    Entry<K, V>[] old = slots;
    slots = (Entry<K, V>[]) new Entry[capacity];
    size = 0;
    used = 0;
    for (Entry<K, V> e : old) {
      if (e != null && !e.deleted) {      // skip empty slots and tombstones
        int i = Math.floorMod(e.key.hashCode(), capacity);
        int step = 1;
        while (slots[i] != null) {        // probe to the first open slot
          i = (i + step) % capacity;      // the same jumps put makes
          step += 2;
        }
        slots[i] = e;                     // reuse the entry
        size++;
        used++;
      }
    }
  }

  @Override
  public Iterator<K> iterator() {
    return new KeyIterator();
  }

  @Override
  public Iterable<Map.Entry<K, V>> entries() {
    return new Entries();
  }

  // Traverses the slots array, returning the key of each live entry and skipping
  // empty slots and tombstones.
  private class KeyIterator implements Iterator<K> {
    private int slot = 0;  // the next slot to examine

    KeyIterator() {
      advance();  // position slot on the first live entry, if there is one
    }

    private void advance() {
      while (slot < slots.length && (slots[slot] == null || slots[slot].deleted)) {
        slot++;  // skip empty slots and tombstones
      }
    }

    @Override
    public boolean hasNext() {
      return slot < slots.length;
    }

    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      K key = slots[slot].key;
      slot++;
      advance();
      return key;
    }
  }

  // The Iterable that entries() returns. Each call to iterator() starts a new traversal.
  private class Entries implements Iterable<Map.Entry<K, V>> {
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
      return new EntryIterator();
    }
  }

  // Traverses the slots array, returning each live entry and skipping empty slots
  // and tombstones.
  private class EntryIterator implements Iterator<Map.Entry<K, V>> {
    private int slot = 0;  // the next slot to examine

    EntryIterator() {
      advance();  // position slot on the first live entry, if there is one
    }

    private void advance() {
      while (slot < slots.length && (slots[slot] == null || slots[slot].deleted)) {
        slot++;  // skip empty slots and tombstones
      }
    }

    @Override
    public boolean hasNext() {
      return slot < slots.length;
    }

    @Override
    public Map.Entry<K, V> next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Entry<K, V> entry = slots[slot];
      slot++;
      advance();
      return entry;
    }
  }
}
