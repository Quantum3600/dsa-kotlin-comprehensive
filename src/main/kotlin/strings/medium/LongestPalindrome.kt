/**
 * ============================================================================
 * PROBLEM: Longest Palindromic Substring
 * DIFFICULTY: Medium
 * CATEGORY: Strings - Medium
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string s, return the longest palindromic substring in s.
 * 
 * A palindrome is a string that reads the same forward and backward.
 * 
 * INPUT FORMAT:
 * - A string s: s = "babad"
 * 
 * OUTPUT FORMAT:
 * - String representing the longest palindromic substring: "bab" or "aba"
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 1000
 * - s consist of only digits and English letters
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * A palindrome reads the same forwards and backwards. The key insight is that
 * every palindrome has a center:
 * - Odd length: "racecar" has center 'e'
 * - Even length: "abba" has center between 'b' and 'b'
 * 
 * We can expand around each possible center to find all palindromes.
 * 
 * KEY INSIGHTS:
 * 1. Every palindrome expands around a center
 * 2. There are 2n-1 possible centers (n characters + n-1 gaps)
 * 3. Expand outward from each center while characters match
 * 4. Track the longest palindrome found
 * 
 * ALGORITHM STEPS (Expand Around Center - OPTIMAL):
 * 1. For each possible center position:
 *    a. Expand for odd-length palindrome (single center)
 *    b. Expand for even-length palindrome (two centers)
 * 2. Track the longest palindrome found
 * 3. Return the longest substring
 * 
 * VISUAL EXAMPLE:
 * s = "babad"
 * 
 * Center at 'b' (index 0):
 *   Odd: "b" (length 1)
 *   Even: no match
 * 
 * Center at 'a' (index 1):
 *   Odd: b[a]b → "bab" (length 3) ✓
 *   Even: ba|ab → no match
 * 
 * Center at 'b' (index 2):
 *   Odd: "b" (length 1)
 *   Even: ab|ba → no match
 * 
 * Center at 'a' (index 3):
 *   Odd: b[a]d → "a" (length 1)
 *   Even: ba|ad → no match
 * 
 * Center at 'd' (index 4):
 *   Odd: "d" (length 1)
 *   Even: ad|? → no match
 * 
 * Longest: "bab" (length 3)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Expand Around Center: O(n²) time, O(1) space - OPTIMAL for space
 * 2. Dynamic Programming: O(n²) time, O(n²) space - Clear but uses more space
 * 3. Brute Force: O(n³) time, O(1) space - Check all substrings
 * 4. Manacher's Algorithm: O(n) time, O(n) space - OPTIMAL but complex
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * EXPAND AROUND CENTER (Implemented):
 * TIME COMPLEXITY: O(n²)
 * - We have 2n-1 possible centers
 * - For each center, we expand up to n/2 characters on each side
 * - In the worst case (all same characters), we do n expansions for each center
 * - Total: O(n) centers × O(n) expansion = O(n²)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only use a few variables for tracking indices
 * - Result substring is required output, not counted
 * - No additional data structures needed
 * 
 * WHY THIS IS OPTIMAL (for space):
 * - Cannot find palindromes without checking characters: O(n) minimum
 * - Worst case requires checking many substrings: O(n²) for expand around center
 * - Uses constant space, which is optimal
 * - Manacher's algorithm achieves O(n) time but requires O(n) space
 * 
 * ============================================================================
 */

package strings.medium

class LongestPalindrome {
    
    /**
     * Finds the longest palindromic substring using expand around center
     * 
     * @param s Input string
     * @return Longest palindromic substring
     */
    fun longestPalindrome(s: String): String {
        // Edge case: empty or single character
        if (s.length < 2) return s
        
        // Track the longest palindrome found
        var start = 0
        var maxLength = 0
        
        // Try each possible center
        for (i in s.indices) {
            // Check for odd-length palindrome (single center)
            val len1 = expandAroundCenter(s, i, i)
            
            // Check for even-length palindrome (two centers)
            val len2 = expandAroundCenter(s, i, i + 1)
            
            // Get the maximum length from both checks
            val len = maxOf(len1, len2)
            
            // Update if we found a longer palindrome
            if (len > maxLength) {
                maxLength = len
                // Calculate starting position of palindrome
                // For a palindrome of length len centered at i:
                // start = i - (len - 1) / 2
                start = i - (len - 1) / 2
            }
        }
        
        // Extract and return the longest palindrome
        return s.substring(start, start + maxLength)
    }
    
    /**
     * Expands around a center to find the length of palindrome
     * 
     * @param s Input string
     * @param left Left pointer of center
     * @param right Right pointer of center
     * @return Length of palindrome around this center
     */
    private fun expandAroundCenter(s: String, left: Int, right: Int): Int {
        var l = left
        var r = right
        
        // Expand while characters match and within bounds
        while (l >= 0 && r < s.length && s[l] == s[r]) {
            l--
            r++
        }
        
        // Return length of palindrome
        // After loop, l and r are one step beyond the palindrome
        // Length = r - l - 1
        return r - l - 1
    }
    
    /**
     * Alternative: Dynamic Programming approach
     * Uses a 2D table to track palindromes
     */
    fun longestPalindromeDp(s: String): String {
        val n = s.length
        if (n < 2) return s
        
        // dp[i][j] = true if substring s[i..j] is palindrome
        val dp = Array(n) { BooleanArray(n) }
        
        var start = 0
        var maxLength = 1
        
        // Every single character is a palindrome
        for (i in 0 until n) {
            dp[i][i] = true
        }
        
        // Check for length 2
        for (i in 0 until n - 1) {
            if (s[i] == s[i + 1]) {
                dp[i][i + 1] = true
                start = i
                maxLength = 2
            }
        }
        
        // Check for lengths greater than 2
        for (len in 3..n) {
            for (i in 0 until n - len + 1) {
                val j = i + len - 1
                
                // Check if s[i..j] is palindrome
                // It's palindrome if s[i] == s[j] and s[i+1..j-1] is palindrome
                if (s[i] == s[j] && dp[i + 1][j - 1]) {
                    dp[i][j] = true
                    if (len > maxLength) {
                        start = i
                        maxLength = len
                    }
                }
            }
        }
        
        return s.substring(start, start + maxLength)
    }
    
    /**
     * Brute force approach - checks all substrings
     * For educational purposes only - not efficient
     */
    fun longestPalindromeBruteForce(s: String): String {
        if (s.length < 2) return s
        
        var longest = s.substring(0, 1)
        
        // Check all possible substrings
        for (i in s.indices) {
            for (j in i + 1..s.length) {
                val substring = s.substring(i, j)
                if (isPalindrome(substring) && substring.length > longest.length) {
                    longest = substring
                }
            }
        }
        
        return longest
    }
    
    /**
     * Helper function to check if a string is palindrome
     */
    private fun isPalindrome(s: String): Boolean {
        var left = 0
        var right = s.length - 1
        
        while (left < right) {
            if (s[left] != s[right]) return false
            left++
            right--
        }
        
        return true
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "babad"
 * 
 * EXPAND AROUND CENTER EXECUTION:
 * 
 * i=0 ('b'):
 *   Odd (0,0): "b" → length 1
 *   Even (0,1): 'b' != 'a' → length 0
 *   max = 1, start = 0
 * 
 * i=1 ('a'):
 *   Odd (1,1): 
 *     - Expand: l=1, r=1, s[1]='a' == s[1]='a' ✓
 *     - Expand: l=0, r=2, s[0]='b' == s[2]='b' ✓
 *     - Expand: l=-1, r=3, out of bounds
 *     - Length = 3 - (-1) - 1 = 3 ("bab")
 *   Even (1,2): 'a' != 'b' → length 0
 *   max = 3, start = 0
 * 
 * i=2 ('b'):
 *   Odd (2,2): "b" → length 1
 *   Even (2,3): 'b' != 'a' → length 0
 *   No update
 * 
 * i=3 ('a'):
 *   Odd (3,3): 
 *     - s[3]='a' == s[3]='a' ✓
 *     - Expand: s[2]='b' != s[4]='d' ✗
 *     - Length = 1
 *   Even (3,4): 'a' != 'd' → length 0
 *   No update
 * 
 * i=4 ('d'):
 *   Odd (4,4): "d" → length 1
 *   Even (4,5): out of bounds
 *   No update
 * 
 * Final: start=0, maxLength=3
 * OUTPUT: s.substring(0, 3) = "bab"
 * 
 * ---
 * 
 * Example 2: s = "cbbd"
 * 
 * i=0 ('c'): max=1, start=0
 * i=1 ('b'):
 *   Odd: "b" → length 1
 *   Even (1,2): s[1]='b' == s[2]='b' ✓
 *     - Expand: s[0]='c' != s[3]='d' ✗
 *     - Length = 2 ("bb")
 *   max=2, start=1
 * i=2 ('b'): No improvement
 * i=3 ('d'): No improvement
 * 
 * OUTPUT: s.substring(1, 3) = "bb"
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Single character "a" → "a"
 * 2. Two identical characters "aa" → "aa"
 * 3. Two different characters "ab" → "a" or "b"
 * 4. All same characters "aaaa" → "aaaa"
 * 5. No palindrome longer than 1 "abcde" → "a"
 * 6. Entire string is palindrome "racecar" → "racecar"
 * 7. Multiple palindromes "abacabad" → finds longest
 * 8. Even-length palindrome "cbbd" → "bb"
 * 9. Odd-length palindrome "babad" → "bab" or "aba"
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Only checking odd-length palindromes
 * ❌ Missing even-length palindromes like "abba"
 * ✅ Check both (i,i) and (i,i+1) centers
 * 
 * MISTAKE 2: Wrong start position calculation
 * ❌ start = i - len/2
 * ✅ start = i - (len-1)/2 (accounts for integer division)
 * 
 * MISTAKE 3: Off-by-one in substring
 * ❌ substring(start, start+maxLength-1)
 * ✅ substring(start, start+maxLength) (end is exclusive)
 * 
 * MISTAKE 4: Not handling edge cases
 * ❌ Empty string or null
 * ✅ Check length < 2 early
 * 
 * MISTAKE 5: Inefficient string comparison
 * ❌ Creating substrings to compare
 * ✅ Compare characters directly with indices
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **DNA Sequencing**: Finding palindromic sequences in genetics
 * 2. **Text Analysis**: Detecting patterns in natural language
 * 3. **Data Compression**: Identifying repeating patterns
 * 4. **String Matching**: Pattern recognition in search engines
 * 5. **Cryptography**: Analyzing structure in encrypted text
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * - Palindromic Substrings (count all)
 * - Shortest Palindrome
 * - Palindrome Partitioning
 * - Valid Palindrome
 * - Longest Palindrome (by concatenating letters)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = LongestPalindrome()
    
    println("=== Testing Longest Palindromic Substring ===\n")
    
    // Test 1: Multiple palindromes
    println("Test 1: Multiple palindromes")
    println("Input: s = \"babad\"")
    println("Output: ${solution.longestPalindrome("babad")}")
    println("Expected: bab or aba\n")
    
    // Test 2: Even-length palindrome
    println("Test 2: Even-length palindrome")
    println("Input: s = \"cbbd\"")
    println("Output: ${solution.longestPalindrome("cbbd")}")
    println("Expected: bb\n")
    
    // Test 3: Single character
    println("Test 3: Single character")
    println("Input: s = \"a\"")
    println("Output: ${solution.longestPalindrome("a")}")
    println("Expected: a\n")
    
    // Test 4: All same characters
    println("Test 4: All same characters")
    println("Input: s = \"aaaa\"")
    println("Output: ${solution.longestPalindrome("aaaa")}")
    println("Expected: aaaa\n")
    
    // Test 5: Entire string is palindrome
    println("Test 5: Entire string is palindrome")
    println("Input: s = \"racecar\"")
    println("Output: ${solution.longestPalindrome("racecar")}")
    println("Expected: racecar\n")
    
    // Test 6: No palindrome longer than 1
    println("Test 6: No long palindrome")
    println("Input: s = \"abc\"")
    println("Output: ${solution.longestPalindrome("abc")}")
    println("Expected: a (or b or c)\n")
    
    // Test 7: Dynamic Programming approach
    println("Test 7: Using DP approach")
    println("Input: s = \"babad\"")
    println("Output: ${solution.longestPalindromeDp("babad")}")
    println("Expected: bab or aba\n")
    
    // Test 8: Complex example
    println("Test 8: Complex palindrome")
    println("Input: s = \"bananas\"")
    println("Output: ${solution.longestPalindrome("bananas")}")
    println("Expected: anana\n")
    
    // Test 9: Two characters
    println("Test 9: Two identical characters")
    println("Input: s = \"bb\"")
    println("Output: ${solution.longestPalindrome("bb")}")
    println("Expected: bb\n")
}
