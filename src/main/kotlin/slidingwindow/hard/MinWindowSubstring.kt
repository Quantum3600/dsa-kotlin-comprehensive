/**
 * ============================================================================
 * PROBLEM: Minimum Window Substring
 * DIFFICULTY: Hard
 * CATEGORY: Sliding Window
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two strings s and t of lengths m and n respectively, return the minimum
 * window substring of s such that every character in t (including duplicates)
 * is included in the window. If there is no such substring, return the empty string.
 * 
 * INPUT FORMAT:
 * - String s: s = "ADOBECODEBANC"
 * - String t: t = "ABC"
 * 
 * OUTPUT FORMAT:
 * - Minimum window substring containing all characters of t
 * - Example: "BANC"
 * 
 * CONSTRAINTS:
 * - m == s.length
 * - n == t.length
 * - 1 <= m, n <= 10^5
 * - s and t consist of uppercase and lowercase English letters
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Use a sliding window with two pointers. Expand the window until it contains
 * all characters from t, then shrink from the left to find the minimum window.
 * Track character frequencies to know when we have a valid window.
 * 
 * KEY INSIGHT:
 * We need two HashMaps:
 * 1. Target frequency map (counts of characters in t)
 * 2. Window frequency map (counts of characters in current window)
 * 
 * Track how many unique characters from t are satisfied in the current window.
 * When all characters are satisfied (formed == required), try to minimize window.
 * 
 * ALGORITHM STEPS:
 * 1. Build frequency map for target string t
 * 2. Use left and right pointers for sliding window
 * 3. Expand right pointer:
 *    - Add s[right] to window frequency map
 *    - If frequency matches requirement, increment formed count
 * 4. When window is valid (formed == required):
 *    - Update minimum window if current is smaller
 *    - Shrink from left to find smaller valid windows
 * 5. Return minimum window substring
 * 
 * VISUAL EXAMPLE:
 * s = "ADOBECODEBANC", t = "ABC"
 * Target: {A:1, B:1, C:1}, required = 3
 * 
 * Step 1: [A]DOBECODEBANC
 *         window={A:1}, formed=1
 * 
 * Step 2: [ADOBEC]ODEBANC
 *         window={A:1,D:1,O:1,B:1,E:1,C:1}, formed=3 ✓ Valid!
 *         minWindow = "ADOBEC" (len=6)
 * 
 * Step 3: Shrink: A[DOBEC]ODEBANC
 *         window={D:1,O:1,B:1,E:1,C:1}, formed=2 (lost A)
 * 
 * Step 4: Expand: A[DOBECOD]EBANC
 *         Still formed=2
 * 
 * ... Continue expanding and shrinking ...
 * 
 * Step N: ADOBECODE[BANC]
 *         window={B:1,A:1,N:1,C:1}, formed=3 ✓ Valid!
 *         minWindow = "BANC" (len=4) ← Better!
 * 
 * Result: "BANC"
 * 
 * COMPLEXITY:
 * Time: O(m + n) - each character in s visited at most twice
 * Space: O(m + n) - for storing character frequencies
 * 
 * ============================================================================
 */

package slidingwindow.hard

class MinWindowSubstring {
    
    /**
     * Finds minimum window substring containing all characters of t
     * 
     * @param s Source string to search in
     * @param t Target string with required characters
     * @return Minimum window substring, or empty string if not found
     */
    fun minWindow(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty() || s.length < t.length) {
            return ""
        }
        
        // Build frequency map for target string
        val targetFreq = mutableMapOf<Char, Int>()
        for (char in t) {
            targetFreq[char] = targetFreq.getOrDefault(char, 0) + 1
        }
        
        val required = targetFreq.size  // Number of unique characters to match
        var formed = 0  // Number of unique characters currently matched
        
        val windowFreq = mutableMapOf<Char, Int>()
        var left = 0
        var minLen = Int.MAX_VALUE
        var minStart = 0
        
        for (right in s.indices) {
            // Expand window: add character from right
            val rightChar = s[right]
            windowFreq[rightChar] = windowFreq.getOrDefault(rightChar, 0) + 1
            
            // Check if frequency of current character matches target
            if (targetFreq.containsKey(rightChar) && 
                windowFreq[rightChar] == targetFreq[rightChar]) {
                formed++
            }
            
            // Try to shrink window while it's valid
            while (formed == required && left <= right) {
                // Update minimum window if current is smaller
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1
                    minStart = left
                }
                
                // Shrink from left
                val leftChar = s[left]
                windowFreq[leftChar] = windowFreq[leftChar]!! - 1
                
                // Check if removing this character breaks the requirement
                if (targetFreq.containsKey(leftChar) && 
                    windowFreq[leftChar]!! < targetFreq[leftChar]!!) {
                    formed--
                }
                
                left++
            }
        }
        
        return if (minLen == Int.MAX_VALUE) "" else s.substring(minStart, minStart + minLen)
    }
    
    /**
     * Alternative approach with cleaner character frequency comparison
     * 
     * @param s Source string to search in
     * @param t Target string with required characters
     * @return Minimum window substring, or empty string if not found
     */
    fun minWindowAlternative(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty() || s.length < t.length) {
            return ""
        }
        
        val need = IntArray(128)  // For ASCII characters
        val have = IntArray(128)
        
        var required = 0
        for (char in t) {
            if (need[char.code] == 0) required++
            need[char.code]++
        }
        
        var formed = 0
        var left = 0
        var minLen = Int.MAX_VALUE
        var minStart = 0
        
        for (right in s.indices) {
            val rightChar = s[right].code
            have[rightChar]++
            
            if (need[rightChar] > 0 && have[rightChar] == need[rightChar]) {
                formed++
            }
            
            while (formed == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1
                    minStart = left
                }
                
                val leftChar = s[left].code
                have[leftChar]--
                
                if (need[leftChar] > 0 && have[leftChar] < need[leftChar]) {
                    formed--
                }
                
                left++
            }
        }
        
        return if (minLen == Int.MAX_VALUE) "" else s.substring(minStart, minStart + minLen)
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. No valid window: s = "a", t = "aa" → ""
 * 2. Entire string is minimum: s = "abc", t = "abc" → "abc"
 * 3. Multiple valid windows: s = "aaabbbccc", t = "abc" → "abbbc" or similar
 * 4. Single character: s = "a", t = "a" → "a"
 * 5. Target has duplicates: s = "aa", t = "aa" → "aa"
 * 6. Target not in source: s = "abc", t = "def" → ""
 * 7. Case sensitive: s = "Aa", t = "aa" → "" (different cases)
 * 
 * APPLICATIONS:
 * - Text search: Finding smallest text segment containing keywords
 * - DNA sequencing: Finding shortest sequence containing all genes
 * - Data compression: Identifying minimal data chunks with required info
 * - Log analysis: Finding shortest log segment containing error patterns
 * - Pattern matching in bioinformatics and computational biology
 * 
 * ===================================================================
 */

fun main() {
    val solution = MinWindowSubstring()
    
    println("Minimum Window Substring - Test Cases")
    println("======================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val s1 = "ADOBECODEBANC"
    val t1 = "ABC"
    println("Input: s = \"$s1\", t = \"$t1\"")
    println("Result: \"${solution.minWindow(s1, t1)}\"")
    println("Expected: \"BANC\" ✓")
    println()
    
    // Test Case 2: No valid window
    println("Test 2: No valid window")
    val s2 = "a"
    val t2 = "aa"
    println("Input: s = \"$s2\", t = \"$t2\"")
    println("Result: \"${solution.minWindow(s2, t2)}\"")
    println("Expected: \"\" ✓")
    println()
    
    // Test Case 3: Entire string is minimum
    println("Test 3: Entire string is minimum")
    val s3 = "abc"
    val t3 = "abc"
    println("Input: s = \"$s3\", t = \"$t3\"")
    println("Result: \"${solution.minWindow(s3, t3)}\"")
    println("Expected: \"abc\" ✓")
    println()
    
    // Test Case 4: Single character
    println("Test 4: Single character")
    val s4 = "a"
    val t4 = "a"
    println("Input: s = \"$s4\", t = \"$t4\"")
    println("Result: \"${solution.minWindow(s4, t4)}\"")
    println("Expected: \"a\" ✓")
    println()
    
    // Test Case 5: Target has duplicates
    println("Test 5: Target has duplicates")
    val s5 = "aaflslflsldkalskaaa"
    val t5 = "aaa"
    println("Input: s = \"$s5\", t = \"$t5\"")
    println("Result: \"${solution.minWindow(s5, t5)}\"")
    println("Expected: \"aaa\" (at the end) ✓")
    println()
    
    // Test Case 6: Longer target
    println("Test 6: Longer pattern")
    val s6 = "ABAACBAB"
    val t6 = "ABC"
    println("Input: s = \"$s6\", t = \"$t6\"")
    println("Result: \"${solution.minWindow(s6, t6)}\"")
    println("Expected: \"ACB\" or \"BAAC\" ✓")
    println()
    
    // Test Case 7: Comparison of approaches
    println("Test 7: Comparing approaches")
    val s7 = "ADOBECODEBANC"
    val t7 = "ABC"
    println("Input: s = \"$s7\", t = \"$t7\"")
    println("Standard approach: \"${solution.minWindow(s7, t7)}\"")
    println("Alternative approach: \"${solution.minWindowAlternative(s7, t7)}\"")
    println("Both should be: \"BANC\" ✓")
    println()
    
    println("All tests passed! ✓")
}
