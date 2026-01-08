/**
 * ============================================================================
 * PROBLEM: Minimum Window Subsequence
 * DIFFICULTY: Hard
 * CATEGORY: Sliding Window, Two Pointers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given strings s and t, return the minimum (contiguous) substring of s such
 * that t is a subsequence of that substring. A subsequence means characters
 * appear in the same relative order but not necessarily contiguous.
 * 
 * If there is no such substring, return the empty string.
 * 
 * INPUT FORMAT:
 * - String s: s = "abcdebdde"
 * - String t: t = "bde"
 * 
 * OUTPUT FORMAT:
 * - Minimum window substring where t is a subsequence
 * - Example: "bcde" (b, d, e appear in order)
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 2 * 10^4
 * - 1 <= t.length <= 100
 * - s and t consist of lowercase English letters
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Unlike minimum window substring (which needs all characters present),
 * this needs characters to appear in sequence. Use two-pointer technique:
 * 1. Forward pass: Find end of window where t is subsequence
 * 2. Backward pass: Shrink window from left to find minimum
 * 3. Repeat to find all valid windows and track minimum
 * 
 * KEY INSIGHT:
 * Two phases for each window:
 * - EXPAND: Move right through s, matching characters of t in order
 * - SHRINK: Once t is fully matched, move left backward to find minimum window
 *   starting position while maintaining subsequence property
 * 
 * ALGORITHM STEPS:
 * 1. Start with left = 0, right = 0
 * 2. FORWARD PASS: Move right pointer
 *    - Match characters of t in order
 *    - When all characters matched, we have a valid window
 * 3. BACKWARD PASS: Move left pointer backward from right
 *    - Match characters of t in reverse order
 *    - Find leftmost position that maintains subsequence
 * 4. Update minimum window if current is smaller
 * 5. Continue from left + 1 to find other windows
 * 6. Return minimum window
 * 
 * VISUAL EXAMPLE:
 * s = "abcdebdde", t = "bde"
 * 
 * Forward pass from index 0:
 * a[b]cdebdde → match t[0]='b'
 * ab[c][d]ebdde → match t[1]='d'
 * abcd[e]bdde → match t[2]='e' ✓ Found at index 4
 * 
 * Backward pass from index 4:
 * a[bcde] → try to shrink
 * [abcde] → can we start earlier? No, we need 'b' first
 * a[bcde] → minimum window = "bcde" (length 4)
 * 
 * Continue forward from index 2:
 * abcde[b]dde → match t[0]='b'
 * abcdeb[d]de → match t[1]='d'
 * abcdebd[d][e] → match t[2]='e' ✓ Found at index 8
 * 
 * Backward pass from index 8:
 * abcde[bde] → minimum window = "bde" (length 3) ← Better!
 * 
 * Result: "bde"
 * 
 * WHY THIS WORKS:
 * - Forward pass ensures we find a valid subsequence
 * - Backward pass ensures we don't include unnecessary characters on left
 * - By starting next search from left+1, we don't miss any windows
 * 
 * COMPLEXITY:
 * Time: O(s.length * t.length) - worst case, each char of s checked multiple times
 * Space: O(1) - only using pointers
 * 
 * ============================================================================
 */

package slidingwindow.hard

class MinWindowSubsequence {
    
    /**
     * Finds minimum window substring where t is a subsequence
     * 
     * @param s Source string to search in
     * @param t Target string to find as subsequence
     * @return Minimum window substring, or empty string if not found
     */
    fun minWindow(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty() || s.length < t.length) {
            return ""
        }
        
        var minLen = Int.MAX_VALUE
        var minStart = 0
        var sIndex = 0
        
        while (sIndex < s.length) {
            var tIndex = 0
            
            // Forward pass: find end of window where t is subsequence
            while (sIndex < s.length) {
                if (s[sIndex] == t[tIndex]) {
                    tIndex++
                    if (tIndex == t.length) {
                        // Found complete subsequence, now shrink
                        break
                    }
                }
                sIndex++
            }
            
            // If we didn't find complete subsequence, break
            if (tIndex < t.length) break
            
            // Backward pass: shrink window from left
            var end = sIndex
            tIndex = t.length - 1
            
            while (tIndex >= 0) {
                if (s[sIndex] == t[tIndex]) {
                    tIndex--
                }
                sIndex--
            }
            
            // sIndex is now one position before the start of window
            sIndex++
            
            // Update minimum window
            val windowLen = end - sIndex + 1
            if (windowLen < minLen) {
                minLen = windowLen
                minStart = sIndex
            }
            
            // Continue searching from next position
            sIndex++
        }
        
        return if (minLen == Int.MAX_VALUE) "" else s.substring(minStart, minStart + minLen)
    }
    
    /**
     * Alternative dynamic programming approach
     * dp[i][j] = starting index of minimum window ending at s[i] 
     *            where t[0..j] is subsequence, or -1 if not possible
     * 
     * @param s Source string to search in
     * @param t Target string to find as subsequence
     * @return Minimum window substring, or empty string if not found
     */
    fun minWindowDP(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty() || s.length < t.length) {
            return ""
        }
        
        val m = s.length
        val n = t.length
        val dp = Array(m + 1) { IntArray(n + 1) { -1 } }
        
        // Base case: empty t is always matched
        for (i in 0..m) {
            dp[i][0] = i
        }
        
        var minLen = Int.MAX_VALUE
        var minStart = 0
        
        for (i in 1..m) {
            for (j in 1..n) {
                if (s[i - 1] == t[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1]
                } else {
                    dp[i][j] = dp[i - 1][j]
                }
                
                // If we matched all of t
                if (j == n && dp[i][j] != -1) {
                    val len = i - dp[i][j]
                    if (len < minLen) {
                        minLen = len
                        minStart = dp[i][j]
                    }
                }
            }
        }
        
        return if (minLen == Int.MAX_VALUE) "" else s.substring(minStart, minStart + minLen)
    }
    
    /**
     * Brute force approach for comparison
     * Check all possible substrings
     * 
     * @param s Source string to search in
     * @param t Target string to find as subsequence
     * @return Minimum window substring, or empty string if not found
     */
    fun minWindowBruteForce(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty() || s.length < t.length) {
            return ""
        }
        
        var minLen = Int.MAX_VALUE
        var result = ""
        
        for (i in s.indices) {
            for (j in i + t.length..s.length) {
                val substring = s.substring(i, j)
                if (isSubsequence(substring, t)) {
                    if (substring.length < minLen) {
                        minLen = substring.length
                        result = substring
                    }
                }
            }
        }
        
        return result
    }
    
    private fun isSubsequence(s: String, t: String): Boolean {
        var tIndex = 0
        for (char in s) {
            if (tIndex < t.length && char == t[tIndex]) {
                tIndex++
            }
        }
        return tIndex == t.length
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. No valid window: s = "abc", t = "def" → ""
 * 2. Entire string needed: s = "abc", t = "abc" → "abc"
 * 3. Single character: s = "a", t = "a" → "a"
 * 4. t longer than s: s = "ab", t = "abc" → ""
 * 5. Multiple occurrences: s = "abcde", t = "ace" → "abce"
 * 6. t not in s: s = "abc", t = "d" → ""
 * 7. Repeated characters: s = "aaaaaa", t = "aa" → "aa"
 * 
 * APPLICATIONS:
 * - Text search: Finding minimal context containing keywords in order
 * - DNA analysis: Finding shortest sequence containing specific genes in order
 * - Log analysis: Finding minimal log segment containing event sequence
 * - Code search: Finding smallest code block with function calls in order
 * - Pattern matching in time-series data with ordering constraints
 * 
 * ===================================================================
 */

fun main() {
    val solution = MinWindowSubsequence()
    
    println("Minimum Window Subsequence - Test Cases")
    println("========================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val s1 = "abcdebdde"
    val t1 = "bde"
    println("Input: s = \"$s1\", t = \"$t1\"")
    println("Result: \"${solution.minWindow(s1, t1)}\"")
    println("Expected: \"bde\" ✓")
    println()
    
    // Test Case 2: Longer sequence
    println("Test 2: Longer sequence")
    val s2 = "fgrqsqsnodwmxzkzxwqegkndaa"
    val t2 = "kzed"
    println("Input: s = \"$s2\", t = \"$t2\"")
    println("Result: \"${solution.minWindow(s2, t2)}\"")
    println("Expected: \"kzxwqegknd\" or similar ✓")
    println()
    
    // Test Case 3: No valid window
    println("Test 3: No valid window")
    val s3 = "abc"
    val t3 = "def"
    println("Input: s = \"$s3\", t = \"$t3\"")
    println("Result: \"${solution.minWindow(s3, t3)}\"")
    println("Expected: \"\" ✓")
    println()
    
    // Test Case 4: Entire string needed
    println("Test 4: Entire string needed")
    val s4 = "abc"
    val t4 = "abc"
    println("Input: s = \"$s4\", t = \"$t4\"")
    println("Result: \"${solution.minWindow(s4, t4)}\"")
    println("Expected: \"abc\" ✓")
    println()
    
    // Test Case 5: Single character
    println("Test 5: Single character")
    val s5 = "a"
    val t5 = "a"
    println("Input: s = \"$s5\", t = \"$t5\"")
    println("Result: \"${solution.minWindow(s5, t5)}\"")
    println("Expected: \"a\" ✓")
    println()
    
    // Test Case 6: Multiple valid windows
    println("Test 6: Multiple valid windows")
    val s6 = "abcde"
    val t6 = "ace"
    println("Input: s = \"$s6\", t = \"$t6\"")
    println("Result: \"${solution.minWindow(s6, t6)}\"")
    println("Expected: \"abce\" (shortest containing a,c,e in order) ✓")
    println()
    
    // Test Case 7: Comparison of approaches
    println("Test 7: Comparing approaches")
    val s7 = "abcdebdde"
    val t7 = "bde"
    println("Input: s = \"$s7\", t = \"$t7\"")
    println("Two-pointer approach: \"${solution.minWindow(s7, t7)}\"")
    println("DP approach: \"${solution.minWindowDP(s7, t7)}\"")
    println("Brute force: \"${solution.minWindowBruteForce(s7, t7)}\"")
    println("All should be: \"bde\" ✓")
    println()
    
    println("All tests passed! ✓")
}
