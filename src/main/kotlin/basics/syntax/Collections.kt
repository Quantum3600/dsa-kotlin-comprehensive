/**
 * ============================================================================
 * TOPIC: Collections in Kotlin
 * DIFFICULTY: Beginner to Intermediate
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * Kotlin provides rich collection APIs including List, Set, Map, and their
 * mutable variants. Understanding collections is essential for data manipulation.
 * 
 * KEY CONCEPTS:
 * 1. List (ordered, allows duplicates)
 * 2. Set (unordered, no duplicates)
 * 3. Map (key-value pairs)
 * 4. Mutable vs Immutable collections
 * 5. Collection operations (filter, map, reduce, etc.)
 * 6. Sequences for lazy evaluation
 * 
 * ============================================================================
 * COLLECTION TYPES
 * ============================================================================
 * 
 * IMMUTABLE (Read-only):
 * - List<T>: listOf()
 * - Set<T>: setOf()
 * - Map<K,V>: mapOf()
 * 
 * MUTABLE:
 * - MutableList<T>: mutableListOf(), arrayListOf()
 * - MutableSet<T>: mutableSetOf(), hashSetOf()
 * - MutableMap<K,V>: mutableMapOf(), hashMapOf()
 * 
 * ============================================================================
 */

package basics.syntax

class Collections {
    
    /**
     * LIST - ORDERED COLLECTION
     */
    fun listExamples() {
        println("=== List Examples ===")
        
        // Immutable list
        val fruits = listOf("Apple", "Banana", "Orange")
        println("Fruits: $fruits")
        println("First: ${fruits[0]}")
        println("Last: ${fruits.last()}")
        println("Size: ${fruits.size}")
        
        // Mutable list
        val numbers = mutableListOf(1, 2, 3)
        numbers.add(4)
        numbers.add(0, 0)  // Insert at index
        numbers.removeAt(numbers.size - 1)
        println("\nMutable list: $numbers")
        
        // List operations
        val scores = listOf(85, 92, 78, 95, 88)
        println("\nScores: $scores")
        println("Max: ${scores.maxOrNull()}")
        println("Min: ${scores.minOrNull()}")
        println("Average: ${scores.average()}")
        println("Sum: ${scores.sum()}")
        
        println()
    }
    
    /**
     * SET - UNIQUE ELEMENTS
     */
    fun setExamples() {
        println("=== Set Examples ===")
        
        // Immutable set
        val colors = setOf("Red", "Green", "Blue", "Red")
        println("Colors: $colors")  // "Red" appears only once
        println("Contains Green: ${"Green" in colors}")
        
        // Mutable set
        val tags = mutableSetOf("kotlin", "java")
        tags.add("python")
        tags.add("kotlin")  // Won't add duplicate
        println("\nTags: $tags")
        
        // Set operations
        val set1 = setOf(1, 2, 3, 4)
        val set2 = setOf(3, 4, 5, 6)
        println("\nSet1: $set1")
        println("Set2: $set2")
        println("Union: ${set1 union set2}")
        println("Intersection: ${set1 intersect set2}")
        println("Difference: ${set1 subtract set2}")
        
        println()
    }
    
    /**
     * MAP - KEY-VALUE PAIRS
     */
    fun mapExamples() {
        println("=== Map Examples ===")
        
        // Immutable map
        val ages = mapOf(
            "Alice" to 25,
            "Bob" to 30,
            "Charlie" to 35
        )
        println("Ages: $ages")
        println("Alice's age: ${ages["Alice"]}")
        println("Keys: ${ages.keys}")
        println("Values: ${ages.values}")
        
        // Mutable map
        val scores = mutableMapOf("Math" to 90, "Physics" to 85)
        scores["Chemistry"] = 88
        scores["Math"] = 95  // Update value
        println("\nScores: $scores")
        
        // Iterate map
        println("\nIterating map:")
        for ((subject, score) in scores) {
            println("$subject: $score")
        }
        
        println()
    }
    
    /**
     * COLLECTION OPERATIONS - FILTER
     */
    fun filterExamples() {
        println("=== Filter Operations ===")
        
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        
        // Filter
        val evens = numbers.filter { it % 2 == 0 }
        println("Evens: $evens")
        
        // FilterNot
        val odds = numbers.filterNot { it % 2 == 0 }
        println("Odds: $odds")
        
        // FilterIndexed
        val skipFirstTwo = numbers.filterIndexed { index, _ -> index >= 2 }
        println("Skip first 2: $skipFirstTwo")
        
        // FilterIsInstance
        val mixed: List<Any> = listOf(1, "two", 3, "four", 5)
        val strings = mixed.filterIsInstance<String>()
        println("Strings only: $strings")
        
        println()
    }
    
    /**
     * COLLECTION OPERATIONS - MAP/TRANSFORM
     */
    fun mapExamples2() {
        println("=== Map/Transform Operations ===")
        
        val numbers = listOf(1, 2, 3, 4, 5)
        
        // Map
        val squared = numbers.map { it * it }
        println("Squared: $squared")
        
        // MapIndexed
        val indexed = numbers.mapIndexed { index, value -> "[$index]=$value" }
        println("Indexed: $indexed")
        
        // MapNotNull
        val nullableNumbers = listOf("1", "two", "3", "four", "5")
        val parsed = nullableNumbers.mapNotNull { it.toIntOrNull() }
        println("Parsed: $parsed")
        
        // FlatMap
        val nested = listOf(listOf(1, 2), listOf(3, 4), listOf(5))
        val flattened = nested.flatMap { it }
        println("Flattened: $flattened")
        
        println()
    }
    
    /**
     * COLLECTION OPERATIONS - REDUCE/FOLD
     */
    fun reduceFoldExamples() {
        println("=== Reduce/Fold Operations ===")
        
        val numbers = listOf(1, 2, 3, 4, 5)
        
        // Sum using reduce
        val sum = numbers.reduce { acc, num -> acc + num }
        println("Sum: $sum")
        
        // Product using reduce
        val product = numbers.reduce { acc, num -> acc * num }
        println("Product: $product")
        
        // Fold with initial value
        val sumPlus10 = numbers.fold(10) { acc, num -> acc + num }
        println("Sum + 10: $sumPlus10")
        
        // String concatenation
        val words = listOf("Kotlin", "is", "awesome")
        val sentence = words.reduce { acc, word -> "$acc $word" }
        println("Sentence: $sentence")
        
        println()
    }
    
    /**
     * COLLECTION OPERATIONS - GROUPING
     */
    fun groupingExamples() {
        println("=== Grouping Operations ===")
        
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        
        // GroupBy
        val grouped = numbers.groupBy { if (it % 2 == 0) "even" else "odd" }
        println("Grouped: $grouped")
        
        // Partition
        val (evens, odds) = numbers.partition { it % 2 == 0 }
        println("Evens: $evens")
        println("Odds: $odds")
        
        // Group strings by length
        val words = listOf("a", "ab", "abc", "abcd", "ab", "a")
        val byLength = words.groupBy { it.length }
        println("\nWords by length: $byLength")
        
        println()
    }
    
    /**
     * COLLECTION OPERATIONS - SORTING
     */
    fun sortingExamples() {
        println("=== Sorting Operations ===")
        
        val numbers = listOf(5, 2, 8, 1, 9, 3)
        
        // Sorted (returns new list)
        println("Original: $numbers")
        println("Sorted: ${numbers.sorted()}")
        println("Sorted desc: ${numbers.sortedDescending()}")
        
        // SortedBy
        val words = listOf("apple", "pie", "zoo", "a")
        println("\nWords: $words")
        println("By length: ${words.sortedBy { it.length }}")
        println("By length desc: ${words.sortedByDescending { it.length }}")
        
        // Sort in place (mutable)
        val mutable = mutableListOf(5, 2, 8, 1, 9)
        mutable.sort()
        println("\nMutable sorted: $mutable")
        
        println()
    }
    
    /**
     * COLLECTION OPERATIONS - SEARCHING
     */
    fun searchingExamples() {
        println("=== Searching Operations ===")
        
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        
        // Find
        val firstEven = numbers.find { it % 2 == 0 }
        println("First even: $firstEven")
        
        // FindLast
        val lastEven = numbers.findLast { it % 2 == 0 }
        println("Last even: $lastEven")
        
        // First/Last with predicate
        val firstGreaterThan5 = numbers.first { it > 5 }
        println("First > 5: $firstGreaterThan5")
        
        // Single (expects exactly one match)
        val justFive = numbers.single { it == 5 }
        println("Single value 5: $justFive")
        
        // Any, All, None
        println("\nAny > 5: ${numbers.any { it > 5 }}")
        println("All > 0: ${numbers.all { it > 0 }}")
        println("None < 0: ${numbers.none { it < 0 }}")
        
        println()
    }
    
    /**
     * COLLECTION OPERATIONS - AGGREGATION
     */
    fun aggregationExamples() {
        println("=== Aggregation Operations ===")
        
        val numbers = listOf(1, 2, 3, 4, 5)
        
        println("Count: ${numbers.count()}")
        println("Sum: ${numbers.sum()}")
        println("Average: ${numbers.average()}")
        println("Max: ${numbers.maxOrNull()}")
        println("Min: ${numbers.minOrNull()}")
        
        // Count with predicate
        println("Count even: ${numbers.count { it % 2 == 0 }}")
        
        // SumOf
        val words = listOf("a", "ab", "abc")
        println("\nTotal characters: ${words.sumOf { it.length }}")
        
        println()
    }
    
    /**
     * COLLECTION OPERATIONS - CHUNKING
     */
    fun chunkingExamples() {
        println("=== Chunking Operations ===")
        
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        
        // Chunk
        val chunks = numbers.chunked(3)
        println("Chunked(3): $chunks")
        
        // Windowed
        val windows = numbers.windowed(3)
        println("Windowed(3): $windows")
        
        // Windowed with step
        val stepped = numbers.windowed(3, step = 2)
        println("Windowed(3, step=2): $stepped")
        
        // Zip
        val letters = listOf("a", "b", "c")
        val nums = listOf(1, 2, 3)
        println("\nZipped: ${letters.zip(nums)}")
        
        println()
    }
    
    /**
     * SEQUENCES - LAZY EVALUATION
     */
    fun sequenceExamples() {
        println("=== Sequences (Lazy Evaluation) ===")
        
        // Sequence from collection
        val numbers = (1..1000).asSequence()
            .filter { it % 2 == 0 }
            .map { it * it }
            .take(5)
            .toList()
        
        println("First 5 squared evens: $numbers")
        
        // Generate sequence
        val fibonacci = sequence {
            var a = 0
            var b = 1
            
            while (true) {
                yield(a)
                val next = a + b
                a = b
                b = next
            }
        }
        
        println("First 10 Fibonacci: ${fibonacci.take(10).toList()}")
        
        println("\nSequences are lazy - operations only execute when needed")
        
        println()
    }
}

/**
 * ============================================================================
 * COLLECTION OPERATION CATEGORIES
 * ============================================================================
 * 
 * TRANSFORMATION:
 * - map, flatMap, mapIndexed, flatten
 * 
 * FILTERING:
 * - filter, filterNot, filterIndexed, filterIsInstance
 * 
 * AGGREGATION:
 * - sum, average, count, max, min, reduce, fold
 * 
 * SEARCHING:
 * - find, first, last, single, any, all, none
 * 
 * GROUPING:
 * - groupBy, partition, associate
 * 
 * SORTING:
 * - sorted, sortedBy, sortedDescending, reversed
 * 
 * COMBINING:
 * - zip, union, intersect, subtract
 * 
 * CHUNKING:
 * - chunked, windowed, zipWithNext
 * 
 * ============================================================================
 * PERFORMANCE TIPS
 * ============================================================================
 * 
 * 1. Use sequences for large collections or multiple operations
 * 2. Use appropriate data structure (List, Set, Map)
 * 3. Prefer immutable collections when possible
 * 4. Use in-place operations on mutable collections
 * 5. Consider laziness with sequences
 * 6. Use asSequence() for chain operations on large data
 * 7. Remember: sequences are lazy, collections are eager
 * 
 * ============================================================================
 */

fun main() {
    val demo = Collections()
    
    println("=== Kotlin Collections ===\n")
    
    demo.listExamples()
    demo.setExamples()
    demo.mapExamples()
    demo.filterExamples()
    demo.mapExamples2()
    demo.reduceFoldExamples()
    demo.groupingExamples()
    demo.sortingExamples()
    demo.searchingExamples()
    demo.aggregationExamples()
    demo.chunkingExamples()
    demo.sequenceExamples()
    
    println("=== Key Takeaways ===")
    println("✓ List: Ordered, allows duplicates")
    println("✓ Set: Unordered, unique elements")
    println("✓ Map: Key-value pairs")
    println("✓ Rich operations: filter, map, reduce, etc.")
    println("✓ Sequences for lazy evaluation")
    println("✓ Prefer immutable collections")
}
