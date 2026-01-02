/**
 * ============================================================================
 * PROBLEM: Count Frequency of Elements in an Array
 * DIFFICULTY: Easy
 * CATEGORY: Hashing/Basic
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, count the frequency (number of occurrences) of
 * each distinct element in the array.
 * 
 * INPUT FORMAT:
 * - An array of integers
 * - Example: [1, 2, 2, 3, 1, 4, 2]
 * 
 * OUTPUT FORMAT:
 * - A map/dictionary showing each element and its frequency
 * - Example: {1=2, 2=3, 3=1, 4=1}
 * 
 * CONSTRAINTS:
 * - 1 <= array.size <= 10^5
 * - -10^9 <= array[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To count frequencies, we need to track how many times each element appears.
 * A hash map (HashMap/Map) is perfect for this because:
 * - Keys store the unique elements
 * - Values store their occurrence counts
 * - O(1) average time for lookup and update
 * 
 * VISUAL EXAMPLE:
 * Array: [1, 2, 2, 3, 1, 4, 2]
 * 
 * Process each element:
 * Index 0: 1 → map = {1: 1}
 * Index 1: 2 → map = {1: 1, 2: 1}
 * Index 2: 2 → map = {1: 1, 2: 2}
 * Index 3: 3 → map = {1: 1, 2: 2, 3: 1}
 * Index 4: 1 → map = {1: 2, 2: 2, 3: 1}
 * Index 5: 4 → map = {1: 2, 2: 2, 3: 1, 4: 1}
 * Index 6: 2 → map = {1: 2, 2: 3, 3: 1, 4: 1}
 * 
 * ALGORITHM STEPS:
 * 1. Create an empty hash map to store frequencies
 * 2. Iterate through each element in the array
 * 3. For each element:
 *    a. If element exists in map, increment its count
 *    b. If element doesn't exist, add it with count 1
 * 4. Return the frequency map
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Hash Map: O(n) time, O(n) space - OPTIMAL (implemented)
 * 2. Nested Loops: O(n²) time, O(1) space - Inefficient, not recommended
 * 3. Sorting + Counting: O(n log n) time, O(1) space - Modifies array
 * 4. Array as Hash (for small range): O(n) time, O(range) space - Limited use
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - We traverse the array once: O(n)
 * - HashMap operations (get/put) are O(1) average
 * - Total: O(n) * O(1) = O(n)
 * 
 * SPACE COMPLEXITY: O(k) where k is number of distinct elements
 * - HashMap stores at most k unique elements
 * - In worst case (all elements unique): O(n)
 * - In best case (all elements same): O(1)
 * - Average case: O(k) where k ≤ n
 * 
 * WHY HASHMAP IS OPTIMAL:
 * - Single pass through array (can't do better than O(n))
 * - Constant time operations for lookup and update
 * - Only stores unique elements, not the entire array
 * 
 * ============================================================================
 */

package basics.hashing

class CountingFrequencies {
    
    /**
     * Count frequency of each element using HashMap
     * TIME: O(n), SPACE: O(k) where k is unique elements
     * 
     * @param arr The input array
     * @return Map of element to frequency
     */
    fun countFrequencies(arr: IntArray): Map<Int, Int> {
        // Create a mutable map to store frequencies
        val frequencyMap = mutableMapOf<Int, Int>()
        
        // Iterate through each element in the array
        for (element in arr) {
            // Get current count (default 0 if not present) and increment
            // getOrDefault returns 0 if key doesn't exist
            frequencyMap[element] = frequencyMap.getOrDefault(element, 0) + 1
        }
        
        return frequencyMap
    }
    
    /**
     * Count frequency using compute method
     * TIME: O(n), SPACE: O(k)
     * 
     * Alternative implementation using compute function
     */
    fun countFrequenciesCompute(arr: IntArray): Map<Int, Int> {
        val frequencyMap = mutableMapOf<Int, Int>()
        
        for (element in arr) {
            // compute updates the value for given key
            // If key doesn't exist, old value is null, so we use 0
            frequencyMap.compute(element) { _, oldValue -> 
                (oldValue ?: 0) + 1 
            }
        }
        
        return frequencyMap
    }
    
    /**
     * Count frequency using groupingBy (Kotlin idiomatic)
     * TIME: O(n), SPACE: O(k)
     * 
     * Most concise Kotlin approach
     */
    fun countFrequenciesGrouping(arr: IntArray): Map<Int, Int> {
        // groupingBy creates a grouping source
        // eachCount counts occurrences of each element
        // Need to convert to List for groupingBy to work
        return arr.toList().groupingBy { it }.eachCount()
    }
    
    /**
     * Count frequency using array as hash table (limited range)
     * TIME: O(n + range), SPACE: O(range)
     * 
     * Only works when array elements are in a known small range
     * Example: For array elements in range [0, 100]
     */
    fun countFrequenciesArray(arr: IntArray, minValue: Int, maxValue: Int): IntArray {
        // Calculate range and create frequency array
        val range = maxValue - minValue + 1
        val frequency = IntArray(range)
        
        // Count frequencies
        for (element in arr) {
            // Map element to array index
            val index = element - minValue
            frequency[index]++
        }
        
        return frequency
    }
    
    /**
     * Print frequencies in sorted order of elements
     * 
     * @param frequencyMap Map of element to frequency
     */
    fun printSortedFrequencies(frequencyMap: Map<Int, Int>) {
        // Sort by keys (elements) and print
        frequencyMap.toSortedMap().forEach { (element, frequency) ->
            println("$element appears $frequency time(s)")
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [1, 2, 2, 3, 1, 4, 2]
 * 
 * EXECUTION TRACE:
 * 
 * Initial: frequencyMap = {}
 * 
 * Index 0: element = 1
 * - getOrDefault(1, 0) = 0
 * - frequencyMap[1] = 0 + 1 = 1
 * State: {1=1}
 * 
 * Index 1: element = 2
 * - getOrDefault(2, 0) = 0
 * - frequencyMap[2] = 0 + 1 = 1
 * State: {1=1, 2=1}
 * 
 * Index 2: element = 2
 * - getOrDefault(2, 0) = 1 (exists!)
 * - frequencyMap[2] = 1 + 1 = 2
 * State: {1=1, 2=2}
 * 
 * Index 3: element = 3
 * - getOrDefault(3, 0) = 0
 * - frequencyMap[3] = 0 + 1 = 1
 * State: {1=1, 2=2, 3=1}
 * 
 * Index 4: element = 1
 * - getOrDefault(1, 0) = 1 (exists!)
 * - frequencyMap[1] = 1 + 1 = 2
 * State: {1=2, 2=2, 3=1}
 * 
 * Index 5: element = 4
 * - getOrDefault(4, 0) = 0
 * - frequencyMap[4] = 0 + 1 = 1
 * State: {1=2, 2=2, 3=1, 4=1}
 * 
 * Index 6: element = 2
 * - getOrDefault(2, 0) = 2 (exists!)
 * - frequencyMap[2] = 2 + 1 = 3
 * State: {1=2, 2=3, 3=1, 4=1}
 * 
 * Return: {1=2, 2=3, 3=1, 4=1} ✓
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. DATA ANALYSIS: Finding most/least common items
 * 2. TEXT PROCESSING: Word frequency in documents
 * 3. STATISTICS: Mode calculation (most frequent value)
 * 4. RECOMMENDATION SYSTEMS: Popular items tracking
 * 5. ANOMALY DETECTION: Identifying rare events
 * 6. VOTING SYSTEMS: Counting votes
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = CountingFrequencies()
    
    println("=== Count Frequency of Elements ===\n")
    
    // Test 1: Normal case with duplicates
    println("Test 1: arr = [1, 2, 2, 3, 1, 4, 2]")
    val arr1 = intArrayOf(1, 2, 2, 3, 1, 4, 2)
    val freq1 = solution.countFrequencies(arr1)
    println("Frequencies: $freq1")
    println("Expected: {1=2, 2=3, 3=1, 4=1}\n")
    
    // Test 2: All elements same
    println("Test 2: arr = [5, 5, 5, 5]")
    val arr2 = intArrayOf(5, 5, 5, 5)
    val freq2 = solution.countFrequencies(arr2)
    println("Frequencies: $freq2")
    println("Expected: {5=4}\n")
    
    // Test 3: All elements unique
    println("Test 3: arr = [1, 2, 3, 4, 5]")
    val arr3 = intArrayOf(1, 2, 3, 4, 5)
    val freq3 = solution.countFrequencies(arr3)
    println("Frequencies: $freq3")
    println("Expected: {1=1, 2=1, 3=1, 4=1, 5=1}\n")
    
    // Test 4: Single element
    println("Test 4: arr = [7]")
    val arr4 = intArrayOf(7)
    val freq4 = solution.countFrequencies(arr4)
    println("Frequencies: $freq4")
    println("Expected: {7=1}\n")
    
    // Test 5: With negative numbers
    println("Test 5: arr = [-1, -2, -1, 0, 1, -2]")
    val arr5 = intArrayOf(-1, -2, -1, 0, 1, -2)
    val freq5 = solution.countFrequencies(arr5)
    println("Frequencies: $freq5")
    println("Expected: {-1=2, -2=2, 0=1, 1=1}\n")
    
    // Test 6: Compare different implementations
    println("Test 6: Compare implementations")
    val arr6 = intArrayOf(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5)
    println("HashMap approach: ${solution.countFrequencies(arr6)}")
    println("Compute approach: ${solution.countFrequenciesCompute(arr6)}")
    println("Grouping approach: ${solution.countFrequenciesGrouping(arr6)}")
    println("All should be same\n")
    
    // Test 7: Sorted frequency output
    println("Test 7: Sorted frequency output")
    println("Array: ${arr1.contentToString()}")
    solution.printSortedFrequencies(freq1)
    println()
    
    // Test 8: Array as hash table (limited range)
    println("Test 8: Array as hash table [range 0-10]")
    val arr8 = intArrayOf(1, 2, 2, 3, 1, 4, 2, 5, 3, 1)
    val freqArray = solution.countFrequenciesArray(arr8, 0, 10)
    println("Array: ${arr8.contentToString()}")
    for (i in freqArray.indices) {
        if (freqArray[i] > 0) {
            println("$i appears ${freqArray[i]} time(s)")
        }
    }
    println()
    
    // Performance comparison
    println("=== Performance Comparison ===")
    val largeArr = IntArray(100000) { (it % 1000) }
    
    var start = System.nanoTime()
    solution.countFrequencies(largeArr)
    var time = (System.nanoTime() - start) / 1000000
    println("HashMap approach: $time ms")
    
    start = System.nanoTime()
    solution.countFrequenciesCompute(largeArr)
    time = (System.nanoTime() - start) / 1000000
    println("Compute approach: $time ms")
    
    start = System.nanoTime()
    solution.countFrequenciesGrouping(largeArr)
    time = (System.nanoTime() - start) / 1000000
    println("Grouping approach: $time ms")
}
