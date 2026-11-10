package map;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A hash table implementation of the Map ADT, with open addressing and linear
 * probing.
 *
 * @param <K> the type of keys in this map.
 * @param <V> the type of values in this map.
 */
public class HashMap<K, V> implements Map<K, V> {

  private Entry<K, V>[] slots;  // one entry per slot, or null when the slot is empty
  private int size;             // how many live pairs the map holds
  private int used;             // how many slots are non-empty: live entries and tombstones
  private int primeIndex;       // the current capacity is PRIMES[primeIndex]

  private static final int[] PRIMES = {
      7, 17, 37, 79, 163, 331, 673, 1361, 2729, 5471,
      10949, 21911, 43853, 87719, 175447, 350899, 701819, 1403641
  };

  private static final double MAX_LOAD_FACTOR = 0.5;

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
  public HashMap() {
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

  // Returns the slot of the key's live entry if the key is present. Otherwise
  // returns the slot where a new key goes: the first tombstone on the probe path,
  // or the empty slot that ends it.
  private int probe(K key) {
    int i = indexFor(key);
    int firstTombstone = -1;
    while (slots[i] != null) {
      if (slots[i].deleted) {
        if (firstTombstone == -1) {
          firstTombstone = i;  // remember the first tombstone on the path
        }
      } else if (slots[i].key.equals(key)) {
        return i;  // found the key
      }
      i = (i + 1) % slots.length;  // another key or a tombstone; keep probing
    }
    if (firstTombstone != -1) {
      return firstTombstone;  // the key is new; reuse the tombstone, not the empty slot
    }
    return i;
  }

  // Returns true if slot i holds a live entry with the given key.
  private boolean holdsKey(int i, K key) {
    return slots[i] != null && !slots[i].deleted && slots[i].key.equals(key);
  }

  @Override
  public boolean put(K key, V value) {
    if (key == null || value == null) {
      throw new IllegalArgumentException();
    }
    int i = probe(key);
    if (holdsKey(i, key)) {
      slots[i].value = value;  // key present; replace its value
      return false;
    }
    if (slots[i] == null) {
      used++;  // filling an empty slot; one more slot is now in use
    }
    slots[i] = new Entry<>(key, value);
    size++;
    rehashIfNeeded();
    return true;
  }

  // Rehashes once more than half the slots are in use: grows when the live
  // entries passed half, and cleans at the same size when the tombstones did.
  private void rehashIfNeeded() {
    if (used > MAX_LOAD_FACTOR * slots.length) {
      if (size > MAX_LOAD_FACTOR * slots.length) {
        int capacity;
        if (primeIndex + 1 < PRIMES.length) {
          capacity = PRIMES[++primeIndex];  // the next prime
        } else {
          capacity = 2 * slots.length + 1;  // past the array: double and add one
        }
        rehash(capacity);
      } else {
        rehash(slots.length);
      }
    }
  }

  @Override
  public V get(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    int i = probe(key);
    if (holdsKey(i, key)) {
      return slots[i].value;
    }
    return null;
  }

  @Override
  public boolean containsKey(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    return holdsKey(probe(key), key);
  }

  @Override
  public boolean remove(K key) {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    int i = probe(key);
    if (holdsKey(i, key)) {
      slots[i].deleted = true;  // leave a tombstone instead of emptying the slot
      size--;
      return true;
    }
    return false;
  }

  // Rebuilds the table into a fresh array of the given capacity, moving every
  // live entry and leaving the tombstones behind. primeIndex is not changed
  // here: rehashIfNeeded sets it before a growing rehash, and a same-size clean keeps it.
  @SuppressWarnings("unchecked")
  private void rehash(int capacity) {
    Entry<K, V>[] old = slots;
    slots = (Entry<K, V>[]) new Entry[capacity];
    used = 0;
    for (Entry<K, V> e : old) {
      if (e != null && !e.deleted) {  // skip empty slots and tombstones
        slots[probe(e.key)] = e;      // the first empty slot on e's path
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

  // Returns the key of each entry, using an EntryIterator for the traversal.
  private class KeyIterator implements Iterator<K> {
    private Iterator<Map.Entry<K, V>> entries;  // the traversal this iterator takes its keys from

    KeyIterator() {
      entries = new EntryIterator();
    }

    @Override
    public boolean hasNext() {
      return entries.hasNext();
    }

    @Override
    public K next() {
      Map.Entry<K, V> entry = entries.next();
      return entry.key();
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
