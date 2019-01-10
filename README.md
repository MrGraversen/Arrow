# Arrow
Code kata to figure out efficient permutation generation and transformation (Colloquially, brute forcing 🕵️)

## Goals

* Generate permutations of an ideograph (the english alphabet, numbers, emojis, whatever!)
* Expose permutations as an infinite resource (A `java.util Stream<String>` in this case)
* Work on a continous flow of computed permutations in a chain of operations, allowing a "lifecycle" for the infinite resource
* Allow async computation

## Examples

### Simply getting started

```java
final CombinationsService combinationsService = CombinationsService.usingEnglishAlphabet();

combinationsService.computeNext();  // => a
combinationsService.computeNext();  // => b
combinationsService.computeNext();  // => c
```

### Work on permutations as a `Stream`

```java
final CombinationsService combinationsService = CombinationsService.usingEnglishAlphabet();

// Discard all elements which are shorter than 3 characters
combinationsService.computeCombinations().dropWhile(s -> s.length() < 3).forEach(System.out::println);  // => 'aaa' and onwards
```

### Locking onto a target

```java
final CombinationsService combinationsService = CombinationsService.usingEnglishAlphabet();

// Construct the (no lifecycle hooks) Arrow instance
final Arrow arrow = Arrow.using(combinationsService).build();

try (arrow)
{
    final Future<ArrowResult> result = arrow.tryFind("HELLO");
    final ArrowResult arrowResult = result.get(); 
    // => [result=HELLO, attempts=305223758, duration=15674 ms, performance=19473252.39 op/s]
}
```

We may add additional lifecycle hooks with "loggers" and "transformers". For example, tranform combinations to MD5 and print to `System.out`

```java
final Arrow arrow = Arrow.using(combinationsService)
        .withLoggers(System.out::println)
        .withTransformers(HashingTransformers.md5())
        .build();
```

Of course, adding multiple steps (especially printing to standard out) severely slows down the `Stream`.

Using the previous setup and the following method call:

```java
arrow.tryFind("02c425157ecd32f259548b33402ff6d3");  // => MD5("zzzz")
```

The runtime performance is about **0.8%** compared to the previous result. If `System.out` is skipped, it becomes much faster.

```
[result=zzzz, attempts=3727490, duration=23101 ms, performance=161356.22 op/s]
```
