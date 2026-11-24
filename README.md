# Open Addressing — Practice

One practice problem: count the subarrays of an array that sum to a target. It has three solutions, the last one with a map of prefix sums, and a JUnit suite that runs against one solution at a time or all of them.

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
        SubarraySumBruteForce.java      # try every subarray
        SubarraySumPrefixSums.java      # count pairs of prefix sums
        SubarraySumMap.java             # one pass with a map of prefix sums
      demo/                             # one demo per solution class
        SubarraySumBruteForceDemo.java
        SubarraySumPrefixSumsDemo.java
        SubarraySumMapDemo.java
    test/
      practice/
        SubarraySumTest.java            # the scenarios, written once
        SubarraySumBruteForceTest.java  # runs them against SubarraySumBruteForce
        SubarraySumPrefixSumsTest.java  # runs them against SubarraySumPrefixSums
        SubarraySumMapTest.java         # runs them against SubarraySumMap
  scripts/
    test.sh                             # compile and run the JUnit tests
    run.sh                              # compile and run one demo
```

## How to compile and run

- `scripts/test.sh` — compiles everything and runs the full JUnit suite.
- `scripts/test.sh practice.SubarraySumBruteForceTest` — compiles everything and runs only that test class.
- `scripts/run.sh demo.SubarraySumMapDemo` — compiles everything and runs that one demo. Every solution class has its own demo, `<Class>Demo`.

## What's here

- `practice.SubarraySumBruteForce` — `subarraySum`, which tries every subarray.
- `practice.SubarraySumPrefixSums` — `subarraySum`, which counts the pairs of prefix sums that differ by the target.
- `practice.SubarraySumMap` — `subarraySum`, which makes one pass with a map from each prefix sum to how many times it has occurred.
- `map.Map<K, V>` and `map.HashMap<K, V>` — copied unchanged from the chapter so this code compiles and runs on its own. `SubarraySumMap` uses the `HashMap`.
- `practice.SubarraySumTest` — the scenarios for all three solutions, with one subclass per solution.
- `demo.SubarraySumBruteForceDemo`, `demo.SubarraySumPrefixSumsDemo`, and `demo.SubarraySumMapDemo` — one demo per solution class, each on an example the notes trace.
