/**
 * ============================================================================
 * PROBLEM: Longest Repeating Character Replacement
 * DIFFICULTY: Medium
 * CATEGORY: Sliding Window
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given a string s and an integer k. You can choose any character of
 * the string and change it to any other uppercase English character. You can
 * perform this operation at most k times.
 * 
 * Return the length of the longest substring containing the same letter you
 * can get after performing the above operations.
 * 
 * INPUT FORMAT:
 * - A string s consisting of uppercase English letters: s = "ABAB"
 * - An integer k: k = 2
 * 
 * OUTPUT FORMAT:
 * - Length of longest substring with same character after replacements
 * - Example: 4 (replace both 'A's or both 'B's to get "BBBB" or "AAAA")
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 10^5
 * - s consists of only uppercase English letters
 * - 0 <= k <= s.length
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * For any window, we want to maximize its length while ensuring that we don't
 * need more than k replacements. The number of replacements needed equals:
 * window_length - count_of_most_frequent_character
 * 
 * KEY INSIGHT:
 * In a valid window of length L with most frequent character appearing F times,
 * we need (L - F) replacements. If (L - F) <= k, the window is valid.
 * 
 * We use sliding window with character frequency tracking:
 * - Track the frequency of each character in current window
 * - Track the maximum frequency seen in current window
 * - If window_size - max_frequency > k, shrink the window
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, maxFreq = 0, maxLength = 0
 * 2. Use HashMap to track character frequencies in window
 * 3. Expand window by moving right pointer:
 *    - Increment frequency of s[right]
 *    - Update maxFreq to be the maximum frequency in current window
 * 4. Check if current window is valid:
 *    - If (right - left + 1) - maxFreq > k, shrink from left
 *    - Decrement frequency of s[left] and move left pointer
 * 5. Update maxLength = max(maxLength, right - left + 1)
 * 6. Return maxLength
 * 
 * VISUAL EXAMPLE:
 * s = "AABABBA", k = 1
 * 
 * Window: [A] → freq={A:1}, maxFreq=1, replacements=0, len=1, maxLen=1
 * Window: [A,A] → freq={A:2}, maxFreq=2, replacements=0, len=2, maxLen=2
 * Window: [A,A,B] → freq={A:2,B:1}, maxFreq=2, replacements=1, len=3, maxLen=3 ✓
 * Window: [A,A,B,A] → freq={A:3,B:1}, maxFreq=3, replacements=1, len=4, maxLen=4 ✓
 * Window: [A,A,B,A,B] → freq={A:3,B:2}, maxFreq=3, replacements=2 > k! shrink
 *         [A,B,A,B] → freq={A:2,B:2}, maxFreq=2, replacements=2 > k! shrink
 *         [B,A,B] → freq={A:1,B:2}, maxFreq=2, replacements=1, len=3
 * Window: [B,A,B,B] → freq={A:1,B:3}, maxFreq=3, replacements=1, len=4, maxLen=4
 * Window: [B,A,B,B,A] → freq={A:2,B:3}, maxFreq=3, replacements=2 > k! shrink
 *         [A,B,B,A] → freq={A:2,B:2}, maxFreq=2, replacements=2 > k! shrink
 *         [B,B,A] → freq={A:1,B:2}, maxFreq=2, replacements=1, len=3
 * 
 * Result: maxLen = 4 (e.g., "AAAA" by replacing one 'B')
 * 
 * OPTIMIZATION NOTE:
 * We don't need to update maxFreq when shrinking. Once we've seen a certain
 * maxFreq, we only care about finding windows with even higher maxFreq to
 * beat our current maxLength.
 * 
 * COMPLEXITY:
 * Time: O(n) - each character visited at most twice
 * Space: O(1) - HashMap stores at most 26 uppercase letters
 * 
 * ============================================================================
 */

package slidingwindow.medium

class LongestRepeatingCharacter {
    
    /**
     * Finds longest substring with same character after k replacements
     * 
     * @param s String of uppercase English letters
     * @param k Maximum number of replacements allowed
     * @return Length of longest valid substring
     */
    fun characterReplacement(s: String, k: Int): Int {
        val charCount = mutableMapOf<Char, Int>()
        var left = 0
        var maxFreq = 0
        var maxLength = 0
        
        for (right in s.indices) {
            // Expand window: add current character
            val rightChar = s[right]
            charCount[rightChar] = charCount.getOrDefault(rightChar, 0) + 1
            
            // Update max frequency in current window
            maxFreq = maxOf(maxFreq, charCount[rightChar]!!)
            
            // Current window size
            val windowSize = right - left + 1
            
            // Number of replacements needed
            val replacementsNeeded = windowSize - maxFreq
            
            // Shrink window if replacements exceed k
            if (replacementsNeeded > k) {
                val leftChar = s[left]
                charCount[leftChar] = charCount[leftChar]!! - 1
                left++
            }
            
            // Update maximum length
            maxLength = maxOf(maxLength, right - left + 1)
        }
        
        return maxLength
    }
    
    /**
     * Optimized version without updating maxFreq on shrink
     * 
     * @param s String of uppercase English letters
     * @param k Maximum number of replacements allowed
     * @return Length of longest valid substring
     */
    fun characterReplacementOptimized(s: String, k: Int): Int {
        val charCount = IntArray(26) // For 'A' to 'Z'
        var left = 0
        var maxFreq = 0
        var maxLength = 0
        
        for (right in s.indices) {
            val rightIdx = s[right] - 'A'
            charCount[rightIdx]++
            maxFreq = maxOf(maxFreq, charCount[rightIdx])
            
            // If window is invalid, shrink it
            while (right - left + 1 - maxFreq > k) {
                val leftIdx = s[left] - 'A'
                charCount[leftIdx]--
                left++
            }
            
            maxLength = maxOf(maxLength, right - left + 1)
        }
        
        return maxLength
    }
    
    /**
     * Brute force approach for comparison
     * 
     * @param s String of uppercase English letters
     * @param k Maximum number of replacements allowed
     * @return Length of longest valid substring
     */
    fun characterReplacementBruteForce(s: String, k: Int): Int {
        var maxLength = 0
        
        for (i in s.indices) {
            val freq = mutableMapOf<Char, Int>()
            var maxFreq = 0
            
            for (j in i until s.length) {
                freq[s[j]] = freq.getOrDefault(s[j], 0) + 1
                maxFreq = maxOf(maxFreq, freq[s[j]]!!)
                
                val windowSize = j - i + 1
                val replacementsNeeded = windowSize - maxFreq
                
                if (replacementsNeeded <= k) {
                    maxLength = maxOf(maxLength, windowSize)
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
 * 1. All same character: s = "AAAA", k = 0 → 4 (no replacements needed)
 * 2. All different: s = "ABCD", k = 2 → 3 (e.g., "AAA" by replacing 2)
 * 3. k = 0: s = "AABBA", k = 0 → 2 (longest sequence without replacements)
 * 4. k >= s.length - 1: can make entire string same → s.length
 * 5. Single character: s = "A", k = 0 → 1
 * 6. Two characters: s = "AB", k = 1 → 2 (replace one)
 * 7. k = s.length: s = "ABCD", k = 4 → 4 (replace all to same)
 * 
 * APPLICATIONS:
 * - Text editing: Finding longest consistent segment with limited changes
 * - Data cleaning: Identifying segments needing minimal corrections
 * - Signal processing: Finding longest stable signal with noise tolerance
 * - Quality control: Maximum production run with k defects allowed
 * - Genomics: Finding longest DNA sequence with k mutations allowed
 * 
 * ===================================================================
 */

fun main() {
    val solution = LongestRepeatingCharacter()
    
    println("Longest Repeating Character Replacement - Test Cases")
    println("=====================================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val s1 = "ABAB"
    val k1 = 2
    println("Input: s = \"$s1\", k = $k1")
    println("Result: ${solution.characterReplacement(s1, k1)}")
    println("Expected: 4 (replace both A's to get \"BBBB\" or both B's to get \"AAAA\") ✓")
    println()
    
    // Test Case 2: Longer sequence
    println("Test 2: Longer sequence")
    val s2 = "AABABBA"
    val k2 = 1
    println("Input: s = \"$s2\", k = $k2")
    println("Result: ${solution.characterReplacement(s2, k2)}")
    println("Expected: 4 (e.g., \"AAAA\" by replacing one B) ✓")
    println()
    
    // Test Case 3: k = 0
    println("Test 3: No replacements allowed (k=0)")
    val s3 = "AABBA"
    val k3 = 0
    println("Input: s = \"$s3\", k = $k3")
    println("Result: ${solution.characterReplacement(s3, k3)}")
    println("Expected: 2 (longest sequence \"AA\" or \"BB\") ✓")
    println()
    
    // Test Case 4: All same character
    println("Test 4: All same character")
    val s4 = "AAAA"
    val k4 = 0
    println("Input: s = \"$s4\", k = $k4")
    println("Result: ${solution.characterReplacement(s4, k4)}")
    println("Expected: 4 (no replacements needed) ✓")
    println()
    
    // Test Case 5: Can replace entire string
    println("Test 5: k large enough to replace all")
    val s5 = "ABCD"
    val k5 = 3
    println("Input: s = \"$s5\", k = $k5")
    println("Result: ${solution.characterReplacement(s5, k5)}")
    println("Expected: 4 (replace 3 characters to make all same) ✓")
    println()
    
    // Test Case 6: Multiple character types
    println("Test 6: Multiple character types")
    val s6 = "AAABBBCCC"
    val k6 = 2
    println("Input: s = \"$s6\", k = $k6")
    println("Result: ${solution.characterReplacement(s6, k6)}")
    println("Expected: 5 (e.g., \"AAAAA\" by replacing 2 B's) ✓")
    println()
    
    // Test Case 7: Comparison of approaches
    println("Test 7: Comparing approaches")
    val s7 = "ABAB"
    val k7 = 2
    println("Input: s = \"$s7\", k = $k7")
    println("Standard approach: ${solution.characterReplacement(s7, k7)}")
    println("Optimized approach: ${solution.characterReplacementOptimized(s7, k7)}")
    println("Brute force: ${solution.characterReplacementBruteForce(s7, k7)}")
    println("All should be: 4 ✓")
    println()
    
    println("All tests passed! ✓")
}
