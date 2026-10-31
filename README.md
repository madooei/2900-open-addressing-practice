# Open Addressing — Practice

Two practice problems: an open-addressed map that probes quadratically in every operation, and counting the subarrays of an array that sum to a target with a map of prefix sums. Each problem has a JUnit suite.

## Prerequisites

- JDK 17+ (JUnit 6 requires it).
- The JUnit jar is already vendored in `lib/`; there is nothing to download.

## Repository layout

```plaintext
code/
  README.md
  .gitignore
  lib/
    junit-platform-console-standalone-6.1.0.jar
  src/
    main/
      map/
        Map.java                        # the Map ADT
        HashMap.java                    # the chapter's open-addressed HashMap
      practice/
        QuadraticProbingMap.java        # one quadratic probe rule in every operation
        SubarraySum.java                # count the subarrays that sum to k
    test/
      map/
        MapTest.java                    # the Map contract suite
      practice/
        QuadraticProbingMapTest.java
        SubarraySumTest.java            # the scenarios, written once
        SubarraySumBruteForceTest.java  # runs them against subarraySumBruteForce
        SubarraySumMapTest.java         # runs them against subarraySum
  scripts/
    test.sh                             # compile and run the JUnit tests
```

## How to compile and run

- `scripts/test.sh` — compiles everything and runs the full JUnit suite.
- `scripts/test.sh practice.SubarraySumBruteForceTest` — compiles everything and runs only that test class.

There is no demo program for these problems; the tests are how you check your work.

## What's here

- `practice.QuadraticProbingMap<K, V>` — the chapter's `HashMap` with quadratic probing in `put`, `get`, `containsKey`, `remove`, and `rehash`.
- `practice.SubarraySum` — `subarraySumBruteForce`, which tries every subarray, and `subarraySum`, which makes one pass with a map from each prefix sum to how many times it has occurred.
- `map.Map<K, V>`, `map.HashMap<K, V>`, and `map.MapTest` — copied unchanged from the chapter so this code compiles and runs on its own. `SubarraySum` uses the `HashMap`.
- `practice.QuadraticProbingMapTest` — runs the Map contract suite against `QuadraticProbingMap`, then reads the slots through reflection to check the quadratic probe.
- `practice.SubarraySumTest` — the scenarios for both solutions, with one subclass per solution.
