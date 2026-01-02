/**
 * ============================================================================
 * PROBLEM: Find Elements with Highest and Lowest Frequency
 * DIFFICULTY: Easy
 * CATEGORY: Hashing/Basic
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, find the element(s) with the highest frequency
 * and the element(s) with the lowest frequency. If multiple elements have the
 * same highest/lowest frequency, return any one of them.
 * 
 * INPUT FORMAT:
 * - An array of integers
 * - Example: [1, 2, 2, 3, 1, 4, 2]
 * 
 * OUTPUT FORMAT:
 * - A pair (highest frequency element, lowest frequency element)
 * - Example: (2, 4) or (2, 3)
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
 * This problem builds on frequency counting. We need to:
 * 1. First count the frequency of each element (using HashMap)
 * 2. Then find which element has the maximum frequency
 * 3. And which element has the minimum frequency
 * 
 * VISUAL EXAMPLE:
 * Array: [1, 2, 2, 3, 1, 4, 2]
 * 
 * Step 1: Count frequencies
 * Frequency map: {1: 2, 2: 3, 3: 1, 4: 1}
 * 
 * Step 2: Find max and min frequencies
 * Element | Frequency
 * --------|----------
 *    1    |    2
 *    2    |    3     ← Maximum (appears most)
 *    3    |    1     ← Minimum (appears least)
 *    4    |    1     ← Minimum (appears least)
 * 
 * Result: Highest = 2 (freq: 3), Lowest = 3 or 4 (freq: 1)
 * 
 * ALGORITHM STEPS:
 * 1. Create frequency map using HashMap
 * 2. Initialize maxFreq = 0, minFreq = Int.MAX_VALUE
 * 3. Initialize maxElement and minElement
 * 4. Iterate through frequency map:
 *    a. If frequency > maxFreq, update maxFreq and maxElement
 *    b. If frequency < minFreq, update minFreq and minElement
 * 5. Return (maxElement, minElement)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Two-pass HashMap: O(n) time, O(k) space - OPTIMAL (implemented)
 * 2. Single-pass with tracking: O(n) time, O(k) space - More complex
 * 3. Sorting + Counting: O(n log n) time, O(1) space - Slower
 * 4. Heap-based: O(n log k) time, O(k) space - Overkill for this problem
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n + k) where n = array size, k = unique elements
 * - First pass: Count frequencies → O(n)
 * - Second pass: Find max/min → O(k)
 * - Total: O(n + k) = O(n) since k ≤ n
 * 
 * SPACE COMPLEXITY: O(k) where k is number of distinct elements
 * - HashMap stores k unique elements with their frequencies
 * - No additional data structures needed
 * - k can range from 1 (all same) to n (all unique)
 * 
 * WHY THIS APPROACH:
 * - Can't do better than O(n) - must examine each element
 * - HashMap gives O(1) lookups for frequency counting
 * - Single pass through frequency map is efficient
 * 
 * ============================================================================
 */

package basics.hashing

class HighestLowestFrequency {
    
    /**
     * Find elements with highest and lowest frequency
     * TIME: O(n), SPACE: O(k)
     * 
     * @param arr The input array
     * @return Pair of (highest frequency element, lowest frequency element)
     */
    fun findHighestLowestFrequency(arr: IntArray): Pair<Int, Int> {
        // Handle edge case: empty array
        if (arr.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty")
        }
        
        // Handle edge case: single element
        if (arr.size == 1) {
            return Pair(arr[0], arr[0])
        }
        
        // Step 1: Count frequencies using HashMap
        val frequencyMap = mutableMapOf<Int, Int>()
        for (element in arr) {
            frequencyMap[element] = frequencyMap.getOrDefault(element, 0) + 1
        }
        
        // Step 2: Find elements with max and min frequency
        var maxFreq = 0
        var minFreq = Int.MAX_VALUE
        var maxElement = arr[0]  // Initialize with first element
        var minElement = arr[0]
        
        // Iterate through frequency map to find max and min
        for ((element, frequency) in frequencyMap) {
            // Update maximum frequency element
            if (frequency > maxFreq) {
                maxFreq = frequency
                maxElement = element
            }
            
            // Update minimum frequency element
            if (frequency < minFreq) {
                minFreq = frequency
                minElement = element
            }
        }
        
        return Pair(maxElement, minElement)
    }
    
    /**
     * Find all elements with highest frequency
     * TIME: O(n), SPACE: O(k)
     * 
     * Returns all elements that have the maximum frequency
     */
    fun findAllHighestFrequency(arr: IntArray): List<Int> {
        if (arr.isEmpty()) return emptyList()
        
        // Count frequencies
        val frequencyMap = mutableMapOf<Int, Int>()
        for (element in arr) {
            frequencyMap[element] = frequencyMap.getOrDefault(element, 0) + 1
        }
        
        // Find maximum frequency
        val maxFreq = frequencyMap.values.maxOrNull() ?: 0
        
        // Collect all elements with maximum frequency
        return frequencyMap.filter { it.value == maxFreq }.keys.toList()
    }
    
    /**
     * Find all elements with lowest frequency
     * TIME: O(n), SPACE: O(k)
     * 
     * Returns all elements that have the minimum frequency
     */
    fun findAllLowestFrequency(arr: IntArray): List<Int> {
        if (arr.isEmpty()) return emptyList()
        
        // Count frequencies
        val frequencyMap = mutableMapOf<Int, Int>()
        for (element in arr) {
            frequencyMap[element] = frequencyMap.getOrDefault(element, 0) + 1
        }
        
        // Find minimum frequency
        val minFreq = frequencyMap.values.minOrNull() ?: 0
        
        // Collect all elements with minimum frequency
        return frequencyMap.filter { it.value == minFreq }.keys.toList()
    }
    
    /**
     * Find element with highest frequency and return with its count
     * TIME: O(n), SPACE: O(k)
     * 
     * @return Pair of (element, frequency)
     */
    fun findMostFrequent(arr: IntArray): Pair<Int, Int> {
        if (arr.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty")
        }
        
        val frequencyMap = mutableMapOf<Int, Int>()
        for (element in arr) {
            frequencyMap[element] = frequencyMap.getOrDefault(element, 0) + 1
        }
        
        // Find entry with maximum frequency
        val maxEntry = frequencyMap.maxByOrNull { it.value }!!
        return Pair(maxEntry.key, maxEntry.value)
    }
    
    /**
     * Find element with lowest frequency and return with its count
     * TIME: O(n), SPACE: O(k)
     * 
     * @return Pair of (element, frequency)
     */
    fun findLeastFrequent(arr: IntArray): Pair<Int, Int> {
        if (arr.isEmpty()) {
            throw IllegalArgumentException("Array cannot be empty")
        }
        
        val frequencyMap = mutableMapOf<Int, Int>()
        for (element in arr) {
            frequencyMap[element] = frequencyMap.getOrDefault(element, 0) + 1
        }
        
        // Find entry with minimum frequency
        val minEntry = frequencyMap.minByOrNull { it.value }!!
        return Pair(minEntry.key, minEntry.value)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [1, 2, 2, 3, 1, 4, 2]
 * 
 * STEP 1: Count Frequencies
 * Initial: frequencyMap = {}
 * 
 * Process array:
 * - element 1: map = {1=1}
 * - element 2: map = {1=1, 2=1}
 * - element 2: map = {1=1, 2=2}
 * - element 3: map = {1=1, 2=2, 3=1}
 * - element 1: map = {1=2, 2=2, 3=1}
 * - element 4: map = {1=2, 2=2, 3=1, 4=1}
 * - element 2: map = {1=2, 2=3, 3=1, 4=1}
 * 
 * Frequency Map: {1=2, 2=3, 3=1, 4=1}
 * 
 * STEP 2: Find Max and Min
 * Initialize: maxFreq = 0, minFreq = MAX_VALUE
 * 
 * Iterate through map:
 * 
 * Entry (1, 2):
 * - frequency 2 > maxFreq 0 → maxFreq = 2, maxElement = 1
 * - frequency 2 < minFreq MAX → minFreq = 2, minElement = 1
 * State: maxFreq=2, minFreq=2, maxElement=1, minElement=1
 * 
 * Entry (2, 3):
 * - frequency 3 > maxFreq 2 → maxFreq = 3, maxElement = 2
 * - frequency 3 NOT < minFreq 2
 * State: maxFreq=3, minFreq=2, maxElement=2, minElement=1
 * 
 * Entry (3, 1):
 * - frequency 1 NOT > maxFreq 3
 * - frequency 1 < minFreq 2 → minFreq = 1, minElement = 3
 * State: maxFreq=3, minFreq=1, maxElement=2, minElement=3
 * 
 * Entry (4, 1):
 * - frequency 1 NOT > maxFreq 3
 * - frequency 1 NOT < minFreq 1 (equal)
 * State: maxFreq=3, minFreq=1, maxElement=2, minElement=3
 * 
 * Return: (2, 3) ✓
 * 
 * Explanation:
 * - Element 2 appears 3 times (highest)
 * - Elements 3 and 4 both appear 1 time (lowest)
 * - We return 3 as it was found first
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. All elements same: [5, 5, 5, 5]
 *    → Highest and lowest are both 5
 * 
 * 2. All elements unique: [1, 2, 3, 4]
 *    → All have frequency 1, can return any
 * 
 * 3. Single element: [7]
 *    → Both highest and lowest are 7
 * 
 * 4. Two distinct frequencies: [1, 1, 2, 2, 3]
 *    → Clear highest and lowest
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = HighestLowestFrequency()
    
    println("=== Find Highest and Lowest Frequency Elements ===\n")
    
    // Test 1: Normal case
    println("Test 1: arr = [1, 2, 2, 3, 1, 4, 2]")
    val arr1 = intArrayOf(1, 2, 2, 3, 1, 4, 2)
    val result1 = solution.findHighestLowestFrequency(arr1)
    println("Result: $result1")
    println("Explanation: 2 appears 3 times (max), 3 or 4 appears 1 time (min)")
    println("Expected: (2, 3) or (2, 4)\n")
    
    // Test 2: All elements same
    println("Test 2: arr = [5, 5, 5, 5]")
    val arr2 = intArrayOf(5, 5, 5, 5)
    val result2 = solution.findHighestLowestFrequency(arr2)
    println("Result: $result2")
    println("Expected: (5, 5)\n")
    
    // Test 3: All elements unique
    println("Test 3: arr = [1, 2, 3, 4, 5]")
    val arr3 = intArrayOf(1, 2, 3, 4, 5)
    val result3 = solution.findHighestLowestFrequency(arr3)
    println("Result: $result3")
    println("Explanation: All have frequency 1, both can be any element")
    println("Expected: (any, any)\n")
    
    // Test 4: Single element
    println("Test 4: arr = [7]")
    val arr4 = intArrayOf(7)
    val result4 = solution.findHighestLowestFrequency(arr4)
    println("Result: $result4")
    println("Expected: (7, 7)\n")
    
    // Test 5: Clear highest and lowest
    println("Test 5: arr = [10, 20, 10, 30, 10, 40]")
    val arr5 = intArrayOf(10, 20, 10, 30, 10, 40)
    val result5 = solution.findHighestLowestFrequency(arr5)
    println("Result: $result5")
    println("Explanation: 10 appears 3 times (max), others appear 1 time (min)")
    println("Expected: (10, 20 or 30 or 40)\n")
    
    // Test 6: Find most frequent with count
    println("Test 6: Most frequent element with count")
    val arr6 = intArrayOf(1, 1, 2, 2, 2, 3, 4, 4)
    val mostFreq = solution.findMostFrequent(arr6)
    println("Array: ${arr6.contentToString()}")
    println("Most frequent: element ${mostFreq.first} appears ${mostFreq.second} times")
    println("Expected: element 2 appears 3 times\n")
    
    // Test 7: Find least frequent with count
    println("Test 7: Least frequent element with count")
    val leastFreq = solution.findLeastFrequent(arr6)
    println("Array: ${arr6.contentToString()}")
    println("Least frequent: element ${leastFreq.first} appears ${leastFreq.second} times")
    println("Expected: element 3 appears 1 time\n")
    
    // Test 8: Find all with highest frequency
    println("Test 8: All elements with highest frequency")
    val arr8 = intArrayOf(1, 1, 2, 2, 3, 4)
    val allHighest = solution.findAllHighestFrequency(arr8)
    println("Array: ${arr8.contentToString()}")
    println("All highest frequency elements: $allHighest")
    println("Expected: [1, 2] (both appear 2 times)\n")
    
    // Test 9: Find all with lowest frequency
    println("Test 9: All elements with lowest frequency")
    val allLowest = solution.findAllLowestFrequency(arr8)
    println("Array: ${arr8.contentToString()}")
    println("All lowest frequency elements: $allLowest")
    println("Expected: [3, 4] (both appear 1 time)\n")
    
    // Test 10: With negative numbers
    println("Test 10: With negative numbers")
    val arr10 = intArrayOf(-1, -2, -1, 0, 1, -2, -2)
    val result10 = solution.findHighestLowestFrequency(arr10)
    println("Array: ${arr10.contentToString()}")
    println("Result: $result10")
    println("Explanation: -2 appears 3 times (max), 0 or 1 appears 1 time (min)")
    println("Expected: (-2, 0 or 1)")
}
