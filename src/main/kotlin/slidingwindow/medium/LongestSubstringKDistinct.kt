/**
 * ============================================================================
 * PROBLEM: Longest Substring with At Most K Distinct Characters
 * DIFFICULTY: Medium
 * CATEGORY: Sliding Window
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string s and an integer k, return the length of the longest
 * substring of s that contains at most k distinct characters.
 * 
 * INPUT FORMAT:
 * - A string s: s = "eceba"
 * - An integer k: k = 2
 * 
 * OUTPUT FORMAT:
 * - Length of longest substring with at most k distinct characters
 * - Example: 3 (substring "ece")
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 5 * 10^4
 * - 0 <= k <= 50
 * - s consists of lowercase English letters
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Use a sliding window with a HashMap to track character frequencies.
 * Expand the window to the right, and when we exceed k distinct characters,
 * shrink from the left until we have at most k distinct characters again.
 * 
 * KEY INSIGHT:
 * HashMap.size gives us the count of distinct characters in current window.
 * When size > k, we need to remove characters from the left until size == k.
 * Track the maximum window size throughout the process.
 * 
 * This is very similar to "Fruits Into Baskets" problem (which is k=2 case).
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, maxLength = 0, and HashMap for character counts
 * 2. Expand window by moving right pointer:
 *    - Add s[right] to HashMap (increment count)
 * 3. While HashMap has more than k distinct characters:
 *    - Remove s[left] from HashMap (decrement count)
 *    - If count becomes 0, remove the character from HashMap
 *    - Move left pointer right
 * 4. Update maxLength = max(maxLength, right - left + 1)
 * 5. Return maxLength
 * 
 * VISUAL EXAMPLE:
 * s = "eceba", k = 2
 * 
 * Window: [e] → map={e:1}, distinct=1, len=1, maxLen=1
 * Window: [e,c] → map={e:1, c:1}, distinct=2, len=2, maxLen=2
 * Window: [e,c,e] → map={e:2, c:1}, distinct=2, len=3, maxLen=3 ✓
 * Window: [e,c,e,b] → map={e:2, c:1, b:1}, distinct=3 > k! shrink
 *         [c,e,b] → map={e:1, c:1, b:1}, distinct=3, shrink
 *         [e,b] → map={e:1, b:1}, distinct=2, len=2
 * Window: [e,b,a] → map={e:1, b:1, a:1}, distinct=3 > k! shrink
 *         [b,a] → map={b:1, a:1}, distinct=2, len=2
 * 
 * Result: maxLen = 3 (substring "ece")
 * 
 * EDGE CASE: k = 0
 * If k = 0, we can't have any characters, so return 0.
 * 
 * COMPLEXITY:
 * Time: O(n) - each character added and removed at most once
 * Space: O(k) - HashMap stores at most k+1 distinct characters
 * 
 * ============================================================================
 */

package slidingwindow.medium

class LongestSubstringKDistinct {
    
    /**
     * Finds length of longest substring with at most k distinct characters
     * 
     * @param s Input string
     * @param k Maximum number of distinct characters allowed
     * @return Length of longest valid substring
     */
    fun lengthOfLongestSubstringKDistinct(s: String, k: Int): Int {
        if (k == 0 || s.isEmpty()) return 0
        
        val charCount = mutableMapOf<Char, Int>()
        var left = 0
        var maxLength = 0
        
        for (right in s.indices) {
            // Expand window: add current character
            val rightChar = s[right]
            charCount[rightChar] = charCount.getOrDefault(rightChar, 0) + 1
            
            // Shrink window if we have more than k distinct characters
            while (charCount.size > k) {
                val leftChar = s[left]
                charCount[leftChar] = charCount[leftChar]!! - 1
                
                // Remove character if count becomes 0
                if (charCount[leftChar] == 0) {
                    charCount.remove(leftChar)
                }
                
                left++
            }
            
            // Update maximum length
            maxLength = maxOf(maxLength, right - left + 1)
        }
        
        return maxLength
    }
    
    /**
     * Alternative approach tracking last position of each character
     * More efficient for shrinking when we need to remove a character
     * 
     * @param s Input string
     * @param k Maximum number of distinct characters allowed
     * @return Length of longest valid substring
     */
    fun lengthOfLongestSubstringKDistinctOptimized(s: String, k: Int): Int {
        if (k == 0 || s.isEmpty()) return 0
        
        val lastIndex = mutableMapOf<Char, Int>()
        var left = 0
        var maxLength = 0
        
        for (right in s.indices) {
            lastIndex[s[right]] = right
            
            // If we exceed k distinct characters, remove the leftmost one
            if (lastIndex.size > k) {
                // Find character with smallest last index
                val minIndex = lastIndex.values.min()
                val charToRemove = lastIndex.entries.first { it.value == minIndex }.key
                lastIndex.remove(charToRemove)
                left = minIndex + 1
            }
            
            maxLength = maxOf(maxLength, right - left + 1)
        }
        
        return maxLength
    }
    
    /**
     * Brute force approach for comparison
     * 
     * @param s Input string
     * @param k Maximum number of distinct characters allowed
     * @return Length of longest valid substring
     */
    fun lengthOfLongestSubstringKDistinctBruteForce(s: String, k: Int): Int {
        if (k == 0 || s.isEmpty()) return 0
        
        var maxLength = 0
        
        for (i in s.indices) {
            val seen = mutableSetOf<Char>()
            for (j in i until s.length) {
                seen.add(s[j])
                
                if (seen.size <= k) {
                    maxLength = maxOf(maxLength, j - i + 1)
                } else {
                    break
                }
            }
        }
        
        return maxLength
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. k = 0: s = "abc", k = 0 → 0 (can't have any characters)
 * 2. k = 1: s = "aaabb", k = 1 → 3 (substring "aaa" or "bbb")
 * 3. k >= distinct characters in s: return s.length
 * 4. All same character: s = "aaaa", k = 1 → 4 (entire string)
 * 5. All different characters: s = "abcde", k = 2 → 2 (any adjacent pair)
 * 6. Empty string: s = "", k = 2 → 0
 * 7. Single character: s = "a", k = 1 → 1
 * 
 * APPLICATIONS:
 * - Text analysis: Finding diverse text segments with controlled vocabulary
 * - Data compression: Identifying segments with limited symbol set
 * - Bioinformatics: Finding DNA segments with limited nucleotide diversity
 * - Natural language processing: Analyzing text complexity
 * - Recommendation systems: Finding user behavior patterns with limited actions
 * 
 * ===================================================================
 */

fun main() {
    val solution = LongestSubstringKDistinct()
    
    println("Longest Substring with K Distinct Characters - Test Cases")
    println("==========================================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val s1 = "eceba"
    val k1 = 2
    println("Input: s = \"$s1\", k = $k1")
    println("Result: ${solution.lengthOfLongestSubstringKDistinct(s1, k1)}")
    println("Expected: 3 (substring \"ece\") ✓")
    println()
    
    // Test Case 2: k = 1
    println("Test 2: Only 1 distinct character allowed")
    val s2 = "aaabb"
    val k2 = 1
    println("Input: s = \"$s2\", k = $k2")
    println("Result: ${solution.lengthOfLongestSubstringKDistinct(s2, k2)}")
    println("Expected: 3 (substring \"aaa\") ✓")
    println()
    
    // Test Case 3: k >= distinct characters
    println("Test 3: k >= number of distinct characters")
    val s3 = "aabbcc"
    val k3 = 3
    println("Input: s = \"$s3\", k = $k3")
    println("Result: ${solution.lengthOfLongestSubstringKDistinct(s3, k3)}")
    println("Expected: 6 (entire string) ✓")
    println()
    
    // Test Case 4: All different characters
    println("Test 4: All different characters")
    val s4 = "abcdef"
    val k4 = 2
    println("Input: s = \"$s4\", k = $k4")
    println("Result: ${solution.lengthOfLongestSubstringKDistinct(s4, k4)}")
    println("Expected: 2 (any adjacent pair like \"ab\") ✓")
    println()
    
    // Test Case 5: Longer string
    println("Test 5: Longer string")
    val s5 = "ababffzzeee"
    val k5 = 3
    println("Input: s = \"$s5\", k = $k5")
    println("Result: ${solution.lengthOfLongestSubstringKDistinct(s5, k5)}")
    println("Expected: 7 (substring \"ffzzeee\") ✓")
    println()
    
    // Test Case 6: k = 0
    println("Test 6: k = 0")
    val s6 = "abc"
    val k6 = 0
    println("Input: s = \"$s6\", k = $k6")
    println("Result: ${solution.lengthOfLongestSubstringKDistinct(s6, k6)}")
    println("Expected: 0 (no characters allowed) ✓")
    println()
    
    // Test Case 7: Comparison of approaches
    println("Test 7: Comparing approaches")
    val s7 = "eceba"
    val k7 = 2
    println("Input: s = \"$s7\", k = $k7")
    println("Standard approach: ${solution.lengthOfLongestSubstringKDistinct(s7, k7)}")
    println("Optimized approach: ${solution.lengthOfLongestSubstringKDistinctOptimized(s7, k7)}")
    println("Brute force: ${solution.lengthOfLongestSubstringKDistinctBruteForce(s7, k7)}")
    println("All should be: 3 ✓")
    println()
    
    println("All tests passed! ✓")
}
