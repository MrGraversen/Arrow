# Arrow
Code kata to figure out efficient permutation generation and transformation (Colloquially, brute forcing 🕵️)

## Goals

* Generate permutations of an ideograph (the english alphabet, numbers, emojis, whatever!)
* Expose permutations as an infinite resource (A `java.util Stream<String>` in this case)
* Work on a continous flow of computed permutations in a chain of operations, allowing a "lifecycle" for the infinite resource
* Allow async computation
