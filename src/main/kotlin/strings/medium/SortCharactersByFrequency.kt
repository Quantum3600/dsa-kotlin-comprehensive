/**
 * ============================================================================
 * PROBLEM: Sort Characters By Frequency
 * DIFFICULTY: Medium
 * CATEGORY: Strings - Medium
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string s, sort it in decreasing order based on the frequency of 
 * the characters. The frequency of a character is the number of times it 
 * appears in the string.
 * 
 * Return the sorted string. If there are multiple answers, return any of them.
 * 
 * INPUT FORMAT:
 * - A string s: s = "tree"
 * 
 * OUTPUT FORMAT:
 * - String sorted by character frequency: "eert" or "eetr"
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 5 * 10^5
 * - s consists of uppercase and lowercase English letters and digits
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We need to count how many times each character appears, then arrange 
 * characters by their frequency (most frequent first).
 * 
 * Think of it like organizing a closet:
 * 1. Count items: 3 shirts, 5 socks, 2 pants
 * 2. Sort by count: 5 socks, 3 shirts, 2 pants
 * 3. Arrange: socks first, then shirts, then pants
 * 
 * KEY INSIGHTS:
 * 1. Count frequency of each character
 * 2. Sort characters by frequency (descending)
 * 3. Build result string with characters repeated by frequency
 * 4. Order among same-frequency characters doesn't matter
 * 
 * ALGORITHM STEPS (HashMap + Sort):
 * 1. Count frequency of each character using a map
 * 2. Sort characters by their frequency (descending)
 * 3. Build result by repeating each character by its frequency
 * 4. Return result string
 * 
 * VISUAL EXAMPLE:
 * s = "tree"
 * 
 * Step 1: Count frequencies
 * t: 1
 * r: 1
 * e: 2
 * 
 * Step 2: Sort by frequency (descending)
 * e: 2  ← highest
 * t: 1
 * r: 1
 * 
 * Step 3: Build result
 * "ee" (e repeated 2 times)
 * "eet" (t repeated 1 time)
 * "eetr" (r repeated 1 time)
 * 
 * OUTPUT: "eetr" (or "eert")
 * 
 * Alternative visualization for "aabbbc":
 * 
 * Count:  a:2, b:3, c:1
 * Sort:   b:3, a:2, c:1
 * Result: "bbbaac"
 * 
 * ALTERNATIVE APPROACHES:
 * 1. HashMap + Sort: O(n + k log k) time, O(n) space - SIMPLE & EFFICIENT
 * 2. Bucket Sort: O(n) time, O(n) space - OPTIMAL for time
 * 3. Priority Queue: O(n log k) time, O(n) space - Alternative
 * 4. Frequency Array + Sort: O(n + k log k) time, O(1) space - For ASCII only
 * 
 * where n = string length, k = number of unique characters
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * HASHMAP + SORT APPROACH:
 * TIME COMPLEXITY: O(n + k log k)
 * - Count frequencies: O(n) to iterate through string
 * - Sort characters: O(k log k) where k is unique characters
 * - Build result: O(n) to construct output string
 * - Total: O(n + k log k)
 * - Since k <= n, worst case is O(n log n) when all characters unique
 * - Best case is O(n) when only one unique character
 * 
 * SPACE COMPLEXITY: O(n)
 * - HashMap to store frequencies: O(k) ≤ O(n)
 * - Sorted list of characters: O(k) ≤ O(n)
 * - Result string: O(n) (required output)
 * - Total: O(n)
 * 
 * BUCKET SORT APPROACH:
 * TIME COMPLEXITY: O(n)
 * - Count frequencies: O(n)
 * - Create buckets: O(n)
 * - Build result: O(n)
 * - Total: O(n) - OPTIMAL
 * 
 * SPACE COMPLEXITY: O(n)
 * - Frequency map: O(k)
 * - Buckets array: O(n)
 * - Total: O(n)
 * 
 * WHY BUCKET SORT IS OPTIMAL:
 * - Must read each character: O(n) minimum
 * - Frequency count limited by string length
 * - Can sort by frequency in O(n) using buckets
 * - Overall O(n) time is optimal
 * 
 * ============================================================================
 */

package strings.medium

class SortCharactersByFrequency {
    
    /**
     * Sorts characters by frequency using HashMap and sorting
     * 
     * @param s Input string
     * @return String with characters sorted by frequency
     */
    fun frequencySort(s: String): String {
        // Step 1: Count frequency of each character
        val freqMap = mutableMapOf<Char, Int>()
        for (char in s) {
            freqMap[char] = freqMap.getOrDefault(char, 0) + 1
        }
        
        // Step 2: Sort characters by frequency (descending)
        val sortedChars = freqMap.entries
            .sortedByDescending { it.value }
        
        // Step 3: Build result string
        val result = StringBuilder()
        for ((char, count) in sortedChars) {
            // Repeat character 'count' times
            repeat(count) {
                result.append(char)
            }
        }
        
        return result.toString()
    }
    
    /**
     * Alternative: Bucket sort approach for O(n) time complexity
     * More efficient for large strings
     */
    fun frequencySortBucket(s: String): String {
        // Step 1: Count frequency of each character
        val freqMap = mutableMapOf<Char, Int>()
        for (char in s) {
            freqMap[char] = freqMap.getOrDefault(char, 0) + 1
        }
        
        // Step 2: Create buckets where index = frequency
        // buckets[i] contains all characters that appear i times
        val buckets = Array<MutableList<Char>>(s.length + 1) { mutableListOf() }
        for ((char, freq) in freqMap) {
            buckets[freq].add(char)
        }
        
        // Step 3: Build result from highest frequency to lowest
        val result = StringBuilder()
        for (freq in buckets.size - 1 downTo 1) {
            for (char in buckets[freq]) {
                // Repeat character 'freq' times
                repeat(freq) {
                    result.append(char)
                }
            }
        }
        
        return result.toString()
    }
    
    /**
     * Alternative: Using priority queue (max heap)
     */
    fun frequencySortPriorityQueue(s: String): String {
        // Count frequencies
        val freqMap = mutableMapOf<Char, Int>()
        for (char in s) {
            freqMap[char] = freqMap.getOrDefault(char, 0) + 1
        }
        
        // Sort entries by frequency (descending) - Kotlin doesn't have built-in PriorityQueue
        // So we'll use sorted list instead
        val sortedEntries = freqMap.entries.sortedByDescending { it.value }
        
        // Build result
        val result = StringBuilder()
        for ((char, count) in sortedEntries) {
            repeat(count) {
                result.append(char)
            }
        }
        
        return result.toString()
    }
    
    /**
     * Functional style using groupingBy and sortedBy
     */
    fun frequencySortFunctional(s: String): String {
        return s.groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .joinToString("") { (char, count) ->
                char.toString().repeat(count)
            }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "tree"
 * 
 * HASHMAP + SORT EXECUTION:
 * 
 * Step 1: Count frequencies
 * Iterate through "tree":
 * - 't': freqMap = {t: 1}
 * - 'r': freqMap = {t: 1, r: 1}
 * - 'e': freqMap = {t: 1, r: 1, e: 1}
 * - 'e': freqMap = {t: 1, r: 1, e: 2}
 * 
 * Final freqMap: {t: 1, r: 1, e: 2}
 * 
 * Step 2: Sort by frequency (descending)
 * Entries: [(t, 1), (r, 1), (e, 2)]
 * Sorted: [(e, 2), (t, 1), (r, 1)]
 * Note: (t, 1) and (r, 1) order doesn't matter
 * 
 * Step 3: Build result
 * Process (e, 2): Append "ee"
 * Process (t, 1): Append "t" → "eet"
 * Process (r, 1): Append "r" → "eetr"
 * 
 * OUTPUT: "eetr"
 * 
 * ---
 * 
 * BUCKET SORT TRACE for "aabbbc":
 * 
 * Step 1: Count frequencies
 * freqMap: {a: 2, b: 3, c: 1}
 * 
 * Step 2: Create buckets
 * buckets[0]: []
 * buckets[1]: [c]
 * buckets[2]: [a]
 * buckets[3]: [b]
 * buckets[4..6]: []
 * 
 * Step 3: Build result (from high to low frequency)
 * freq=3: Process 'b' → "bbb"
 * freq=2: Process 'a' → "bbbaa"
 * freq=1: Process 'c' → "bbbaac"
 * 
 * OUTPUT: "bbbaac"
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Single character "a" → "a"
 * 2. All same characters "aaaa" → "aaaa"
 * 3. All unique characters "abc" → "abc" (any order)
 * 4. Mixed frequencies "aabbbc" → "bbbaac"
 * 5. Numbers and letters "aa11b" → "aa11b" or variations
 * 6. Case sensitive "Aa" → "Aa" or "aA" (treated separately)
 * 7. Long strings (up to 500,000) handled efficiently
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not handling same-frequency characters
 * ❌ Assuming fixed order for same frequency
 * ✅ Any order is acceptable for same frequency
 * 
 * MISTAKE 2: Inefficient string concatenation
 * ❌ Using + operator in loop (creates many strings)
 * ✅ Using StringBuilder for efficiency
 * 
 * MISTAKE 3: Wrong sort order
 * ❌ Sorting ascending instead of descending
 * ✅ Use sortedByDescending or reverse order
 * 
 * MISTAKE 4: Not repeating characters
 * ❌ Appending each character once
 * ✅ Repeat character by its frequency
 * 
 * MISTAKE 5: Modifying input during processing
 * ❌ Removing characters from original string
 * ✅ Build new string, keep input unchanged
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Text Compression**: Huffman coding preparation
 * 2. **Search Engines**: Ranking terms by frequency
 * 3. **Data Analysis**: Finding most common elements
 * 4. **Spell Checkers**: Prioritizing common letters
 * 5. **Cryptography**: Frequency analysis of ciphers
 * 6. **Log Analysis**: Finding common error messages
 * 7. **Social Media**: Trending hashtags/topics
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * - Top K Frequent Elements
 * - Top K Frequent Words
 * - First Unique Character in String
 * - Sort Array by Increasing Frequency
 * - Reorganize String
 * - K Closest Points to Origin (similar bucket sort)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = SortCharactersByFrequency()
    
    println("=== Testing Sort Characters By Frequency ===\n")
    
    // Test 1: Basic example
    println("Test 1: Basic case")
    println("Input: s = \"tree\"")
    println("Output: ${solution.frequencySort("tree")}")
    println("Expected: eetr or eert\n")
    
    // Test 2: Different frequencies
    println("Test 2: Different frequencies")
    println("Input: s = \"cccaaa\"")
    println("Output: ${solution.frequencySort("cccaaa")}")
    println("Expected: cccaaa or aaaccc\n")
    
    // Test 3: All unique
    println("Test 3: All unique characters")
    println("Input: s = \"Aabb\"")
    println("Output: ${solution.frequencySort("Aabb")}")
    println("Expected: bbAa or bbaA or similar\n")
    
    // Test 4: Single character
    println("Test 4: Single character")
    println("Input: s = \"a\"")
    println("Output: ${solution.frequencySort("a")}")
    println("Expected: a\n")
    
    // Test 5: All same
    println("Test 5: All same characters")
    println("Input: s = \"aaaa\"")
    println("Output: ${solution.frequencySort("aaaa")}")
    println("Expected: aaaa\n")
    
    // Test 6: Complex case
    println("Test 6: Complex frequencies")
    println("Input: s = \"loveleetcode\"")
    println("Output: ${solution.frequencySort("loveleetcode")}")
    println("Expected: eeeeoollvtdc or similar\n")
    
    // Test 7: Bucket sort approach
    println("Test 7: Using bucket sort")
    println("Input: s = \"tree\"")
    println("Output: ${solution.frequencySortBucket("tree")}")
    println("Expected: eetr or eert\n")
    
    // Test 8: Functional style
    println("Test 8: Using functional style")
    println("Input: s = \"cccaaa\"")
    println("Output: ${solution.frequencySortFunctional("cccaaa")}")
    println("Expected: cccaaa or aaaccc\n")
    
    // Test 9: Numbers and letters
    println("Test 9: Mixed content")
    println("Input: s = \"2a554442f544asfasssffffasss\"")
    val result = solution.frequencySort("2a554442f544asfasssffffasss")
    println("Output: $result")
    println("Length: ${result.length}\n")
}
