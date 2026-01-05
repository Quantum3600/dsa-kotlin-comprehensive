/**
 * ============================================================================
 * PROBLEM: Sum of Beauty of All Substrings
 * DIFFICULTY: Medium
 * CATEGORY: Strings - Medium
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * The beauty of a string is the difference between the maximum frequency and 
 * minimum frequency of any character in the string.
 * 
 * For example, the beauty of "aabcb" is 2 - 1 = 1 (max frequency 'a'=2, min 
 * frequency others=1).
 * 
 * Given a string s, return the sum of beauty of all of its substrings.
 * 
 * INPUT FORMAT:
 * - A string s: s = "aabcb"
 * 
 * OUTPUT FORMAT:
 * - Integer representing sum of beauty: 5
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 500
 * - s consists of only lowercase English letters
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We need to:
 * 1. Generate all possible substrings
 * 2. For each substring, calculate its beauty
 * 3. Beauty = max_frequency - min_frequency (min must be > 0)
 * 4. Sum all beauties
 * 
 * Example: "aabcb"
 * Substrings with beauty:
 * - "aa" → max=2, min=2 → beauty=0
 * - "aab" → max=2, min=1 → beauty=1
 * - "aabc" → max=2, min=1 → beauty=1
 * - "aabcb" → max=2, min=1 → beauty=1
 * - "ab" → max=1, min=1 → beauty=0
 * - "abc" → max=1, min=1 → beauty=0
 * - "abcb" → max=2, min=1 → beauty=1
 * - "bc" → max=1, min=1 → beauty=0
 * - "bcb" → max=2, min=1 → beauty=1
 * - "cb" → max=1, min=1 → beauty=0
 * 
 * Total beauty = 5
 * 
 * KEY INSIGHTS:
 * 1. Beauty is only interesting when frequencies differ
 * 2. Only characters present in substring count
 * 3. Single character substrings have beauty 0
 * 4. Need to track frequency of each character
 * 5. Can optimize by updating frequencies incrementally
 * 
 * ALGORITHM STEPS:
 * 1. For each starting position i:
 *    a. Initialize frequency array
 *    b. For each ending position j from i to end:
 *       - Add character at j to frequency
 *       - Find max and min frequencies (min > 0)
 *       - Calculate beauty = max - min
 *       - Add to total sum
 * 2. Return total sum
 * 
 * VISUAL EXAMPLE:
 * s = "aab"
 * 
 * Starting at index 0 ('a'):
 *   j=0: "a" → freq[a]=1 → max=1, min=1 → beauty=0
 *   j=1: "aa" → freq[a]=2 → max=2, min=2 → beauty=0
 *   j=2: "aab" → freq[a]=2, freq[b]=1 → max=2, min=1 → beauty=1
 * 
 * Starting at index 1 ('a'):
 *   j=1: "a" → freq[a]=1 → max=1, min=1 → beauty=0
 *   j=2: "ab" → freq[a]=1, freq[b]=1 → max=1, min=1 → beauty=0
 * 
 * Starting at index 2 ('b'):
 *   j=2: "b" → freq[b]=1 → max=1, min=1 → beauty=0
 * 
 * Total = 0 + 0 + 1 + 0 + 0 + 0 = 1
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force (generate all substrings): O(n³) time, O(n) space
 * 2. Optimized with frequency array: O(n² * 26) time, O(1) space - BETTER
 * 3. Sliding window (not applicable - need all substrings)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n² * 26) ≈ O(n²)
 * - Two nested loops for all substrings: O(n²)
 * - For each substring:
 *   - Update frequency: O(1)
 *   - Find max/min in frequency array: O(26) = O(1)
 * - Total: O(n² * 26) ≈ O(n²) since 26 is constant
 * - n can be up to 500, so n² = 250,000 operations
 * 
 * SPACE COMPLEXITY: O(1)
 * - Frequency array of size 26: O(26) = O(1)
 * - Few variables for tracking: O(1)
 * - Space independent of input size
 * 
 * WHY THIS IS OPTIMAL:
 * - Must examine all O(n²) substrings
 * - Each substring needs frequency analysis
 * - Incremental frequency update is optimal
 * - Cannot avoid O(n²) substring enumeration
 * 
 * ============================================================================
 */

package strings.medium

class SumOfBeauty {
    
    /**
     * Calculates sum of beauty of all substrings
     * 
     * @param s Input string
     * @return Sum of beauty values
     */
    fun beautySum(s: String): Int {
        var totalBeauty = 0
        
        // Try all starting positions
        for (i in s.indices) {
            // Frequency array for 26 lowercase letters
            val freq = IntArray(26)
            
            // Extend substring from position i
            for (j in i until s.length) {
                // Add current character to frequency
                freq[s[j] - 'a']++
                
                // Calculate beauty of substring s[i..j]
                val beauty = calculateBeauty(freq)
                totalBeauty += beauty
            }
        }
        
        return totalBeauty
    }
    
    /**
     * Calculates beauty from frequency array
     * Beauty = max frequency - min frequency (where min > 0)
     * 
     * @param freq Frequency array
     * @return Beauty value
     */
    private fun calculateBeauty(freq: IntArray): Int {
        var maxFreq = 0
        var minFreq = Int.MAX_VALUE
        
        // Find max and min frequencies (only for characters present)
        for (count in freq) {
            if (count > 0) {
                maxFreq = maxOf(maxFreq, count)
                minFreq = minOf(minFreq, count)
            }
        }
        
        // If all characters have same frequency, beauty is 0
        return maxFreq - minFreq
    }
    
    /**
     * Alternative: More explicit version with character tracking
     */
    fun beautySumExplicit(s: String): Int {
        var totalBeauty = 0
        
        // Generate all substrings
        for (i in s.indices) {
            // Map to track character frequencies
            val charCount = mutableMapOf<Char, Int>()
            
            for (j in i until s.length) {
                val char = s[j]
                charCount[char] = charCount.getOrDefault(char, 0) + 1
                
                // Find max and min frequencies
                if (charCount.isNotEmpty()) {
                    val frequencies = charCount.values
                    val maxFreq = frequencies.maxOrNull() ?: 0
                    val minFreq = frequencies.minOrNull() ?: 0
                    
                    totalBeauty += (maxFreq - minFreq)
                }
            }
        }
        
        return totalBeauty
    }
    
    /**
     * Helper function to get beauty of a single string
     * Useful for understanding and debugging
     */
    fun getBeauty(str: String): Int {
        if (str.isEmpty()) return 0
        
        val freq = IntArray(26)
        for (char in str) {
            freq[char - 'a']++
        }
        
        return calculateBeauty(freq)
    }
    
    /**
     * Helper function to list all substrings with their beauties
     */
    fun listSubstringBeauties(s: String): List<Triple<String, Int, Int>> {
        val result = mutableListOf<Triple<String, Int, Int>>()
        
        for (i in s.indices) {
            val freq = IntArray(26)
            
            for (j in i until s.length) {
                freq[s[j] - 'a']++
                
                val substring = s.substring(i, j + 1)
                val beauty = calculateBeauty(freq)
                
                // Only include if beauty > 0 for clarity
                if (beauty > 0) {
                    result.add(Triple(substring, beauty, i))
                }
            }
        }
        
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "aabcb"
 * 
 * DETAILED EXECUTION:
 * 
 * Starting position i=0 ('a'):
 * freq = [0, 0, ..., 0]
 * 
 * j=0: s[0]='a'
 *   freq[0]++ → freq = [1, 0, 0, ...]
 *   max=1, min=1 → beauty=0
 *   total=0
 * 
 * j=1: s[1]='a'
 *   freq[0]++ → freq = [2, 0, 0, ...]
 *   max=2, min=2 → beauty=0
 *   total=0
 * 
 * j=2: s[2]='b'
 *   freq[1]++ → freq = [2, 1, 0, ...]
 *   max=2 (a), min=1 (b) → beauty=1
 *   total=1
 * 
 * j=3: s[3]='c'
 *   freq[2]++ → freq = [2, 1, 1, ...]
 *   max=2 (a), min=1 (b,c) → beauty=1
 *   total=2
 * 
 * j=4: s[4]='b'
 *   freq[1]++ → freq = [2, 2, 1, ...]
 *   max=2 (a,b), min=1 (c) → beauty=1
 *   total=3
 * 
 * Starting position i=1 ('a'):
 * freq = [0, 0, ..., 0]
 * 
 * j=1: s[1]='a'
 *   freq = [1, 0, 0, ...]
 *   max=1, min=1 → beauty=0
 *   total=3
 * 
 * j=2: s[2]='b'
 *   freq = [1, 1, 0, ...]
 *   max=1, min=1 → beauty=0
 *   total=3
 * 
 * j=3: s[3]='c'
 *   freq = [1, 1, 1, ...]
 *   max=1, min=1 → beauty=0
 *   total=3
 * 
 * j=4: s[4]='b'
 *   freq = [1, 2, 1, ...]
 *   max=2 (b), min=1 (a,c) → beauty=1
 *   total=4
 * 
 * Starting position i=2 ('b'):
 * j=2: beauty=0, total=4
 * j=3: beauty=0, total=4
 * j=4: freq=[0,2,1,...], max=2, min=1 → beauty=1, total=5
 * 
 * Starting position i=3 ('c'):
 * j=3: beauty=0, total=5
 * j=4: beauty=0, total=5
 * 
 * Starting position i=4 ('b'):
 * j=4: beauty=0, total=5
 * 
 * OUTPUT: 5
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Single character "a" → 0 (max=min)
 * 2. Two same characters "aa" → 0 (all substrings have max=min)
 * 3. Two different "ab" → 0 (each substring has max=min=1)
 * 4. All same characters "aaaa" → 0 (all have max=min)
 * 5. All unique "abc" → 0 (each has max=min=1)
 * 6. Maximum length (500 chars) → handled efficiently
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Including characters with 0 frequency
 * ❌ Considering all 26 letters for min
 * ✅ Only consider frequencies > 0
 * 
 * MISTAKE 2: Not resetting frequency for each start
 * ❌ Reusing frequency array across starts
 * ✅ New frequency array for each starting position
 * 
 * MISTAKE 3: Wrong beauty calculation
 * ❌ beauty = max - min without checking min > 0
 * ✅ Only consider present characters
 * 
 * MISTAKE 4: Inefficient substring generation
 * ❌ Creating actual substring strings
 * ✅ Using frequency array with indices
 * 
 * MISTAKE 5: Not handling single character
 * ❌ Assuming beauty is always > 0
 * ✅ Correctly return 0 when max = min
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Text Analysis**: Measuring character distribution uniformity
 * 2. **Data Quality**: Detecting skewed character distributions
 * 3. **Bioinformatics**: Analyzing DNA sequence diversity
 * 4. **Cryptography**: Measuring entropy in text
 * 5. **Compression**: Finding patterns in character distribution
 * 6. **Language Processing**: Analyzing character frequency patterns
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * - Count Substrings with K Distinct Characters
 * - Longest Substring with At Most K Distinct Characters
 * - Subarrays with K Different Integers
 * - Frequency of the Most Frequent Element
 * - Minimum Window Substring
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = SumOfBeauty()
    
    println("=== Testing Sum of Beauty of All Substrings ===\n")
    
    // Test 1: Example from problem
    println("Test 1: Basic example")
    println("Input: s = \"aabcb\"")
    println("Output: ${solution.beautySum("aabcb")}")
    println("Expected: 5\n")
    
    // Test 2: Another example
    println("Test 2: Another example")
    println("Input: s = \"aabcbaa\"")
    println("Output: ${solution.beautySum("aabcbaa")}")
    println("Expected: 17\n")
    
    // Test 3: Single character
    println("Test 3: Single character")
    println("Input: s = \"a\"")
    println("Output: ${solution.beautySum("a")}")
    println("Expected: 0\n")
    
    // Test 4: All same characters
    println("Test 4: All same characters")
    println("Input: s = \"aaaa\"")
    println("Output: ${solution.beautySum("aaaa")}")
    println("Expected: 0\n")
    
    // Test 5: All different
    println("Test 5: All different characters")
    println("Input: s = \"abc\"")
    println("Output: ${solution.beautySum("abc")}")
    println("Expected: 0\n")
    
    // Test 6: Two different
    println("Test 6: Two different characters")
    println("Input: s = \"aab\"")
    println("Output: ${solution.beautySum("aab")}")
    println("Expected: 1\n")
    
    // Test 7: Using explicit version
    println("Test 7: Using explicit approach")
    println("Input: s = \"aabcb\"")
    println("Output: ${solution.beautySumExplicit("aabcb")}")
    println("Expected: 5\n")
    
    // Test 8: Get beauty of single string
    println("Test 8: Beauty of single string")
    println("Input: \"aabcb\"")
    println("Output: ${solution.getBeauty("aabcb")}")
    println("Expected: 1 (max=2 for 'a' or 'b', min=1 for 'c')\n")
    
    // Test 9: List substrings with beauty > 0
    println("Test 9: List substrings with beauty")
    println("Input: s = \"aab\"")
    val substrings = solution.listSubstringBeauties("aab")
    println("Substrings with beauty > 0:")
    for ((substring, beauty, startIndex) in substrings) {
        println("  \"$substring\" (from index $startIndex): beauty = $beauty")
    }
    println()
}
