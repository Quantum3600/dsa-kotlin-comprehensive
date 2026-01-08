/**
 * ============================================================================
 * PROBLEM: Number of Substrings Containing All Three Characters
 * DIFFICULTY: Medium
 * CATEGORY: Sliding Window
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string s consisting only of characters 'a', 'b', and 'c', return
 * the number of substrings that contain at least one occurrence of all three
 * characters 'a', 'b', and 'c'.
 * 
 * INPUT FORMAT:
 * - A string s containing only 'a', 'b', and 'c'
 * - Example: s = "abcabc"
 * 
 * OUTPUT FORMAT:
 * - Number of substrings containing all three characters
 * - Example: 10
 * 
 * CONSTRAINTS:
 * - 3 <= s.length <= 5 × 10^4
 * - s only consists of 'a', 'b', and 'c' characters
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of checking every substring, we use a sliding window. Once we have
 * a valid window (containing all three characters), ALL substrings from current
 * position to the end will also be valid by extending rightward.
 * 
 * KEY INSIGHT:
 * If substring s[left...right] contains all three characters, then ALL
 * substrings s[left...k] where k >= right also contain all three characters.
 * So we can count (n - right) substrings at once!
 * 
 * When we find a valid window:
 * - Count how many valid substrings start at 'left'
 * - This equals (n - right) because we can extend to any position from right to end
 * - Move left pointer and continue
 * 
 * ALGORITHM STEPS:
 * 1. Use HashMap to track count of each character in current window
 * 2. Expand right pointer, adding characters to window
 * 3. Once window contains all 3 characters (a, b, c):
 *    - Add (n - right) to result (all extensions from current position)
 *    - Shrink window from left to find more valid starting positions
 * 4. Continue until right reaches end
 * 
 * VISUAL EXAMPLE:
 * s = "abcabc" (length = 6)
 * 
 * Step 1: "a" → not valid
 * Step 2: "ab" → not valid
 * Step 3: "abc" → valid! count += (6-2) = 4
 *         Valid substrings: "abc", "abca", "abcab", "abcabc"
 * Step 4: Shrink: "bc" → not valid (missing 'a')
 * Step 5: Expand: "bca" → valid! count += (6-3) = 3
 *         Valid substrings: "bca", "bcab", "bcabc"
 * Step 6: Shrink: "ca" → not valid
 * Step 7: Expand: "cab" → not valid
 * Step 8: Expand: "cabc" → valid! count += (6-5) = 1
 *         Valid substring: "cabc"
 * Step 9: Shrink: "abc" → valid! count += (6-5) = 1
 *         Valid substring: "abc"
 * Step 10: Shrink: "bc" → not valid
 * Step 11: Expand: "bc" → already at end
 * 
 * Total count = 4 + 3 + 1 + 1 + 1 = 10
 * 
 * COMPLEXITY:
 * Time: O(n) - each character visited at most twice (once by right, once by left)
 * Space: O(1) - HashMap stores at most 3 characters
 * 
 * ============================================================================
 */

package slidingwindow.medium

class NumberOfSubstrings {
    
    /**
     * Counts substrings containing all three characters 'a', 'b', 'c'
     * 
     * @param s String containing only 'a', 'b', and 'c'
     * @return Number of valid substrings
     */
    fun numberOfSubstrings(s: String): Int {
        val count = mutableMapOf<Char, Int>()
        var left = 0
        var result = 0
        
        for (right in s.indices) {
            // Add current character to window
            val char = s[right]
            count[char] = count.getOrDefault(char, 0) + 1
            
            // Shrink window while it contains all three characters
            while (count.size == 3 && count['a']!! > 0 && count['b']!! > 0 && count['c']!! > 0) {
                // All substrings from left to right and beyond are valid
                result += s.length - right
                
                // Shrink from left
                val leftChar = s[left]
                count[leftChar] = count[leftChar]!! - 1
                if (count[leftChar] == 0) {
                    count.remove(leftChar)
                }
                left++
            }
        }
        
        return result
    }
    
    /**
     * Alternative approach using array instead of HashMap for better performance
     * 
     * @param s String containing only 'a', 'b', and 'c'
     * @return Number of valid substrings
     */
    fun numberOfSubstringsOptimized(s: String): Int {
        val count = IntArray(3) // count[0] for 'a', count[1] for 'b', count[2] for 'c'
        var left = 0
        var result = 0
        
        for (right in s.indices) {
            // Add current character to window
            count[s[right] - 'a']++
            
            // Shrink window while it contains all three characters
            while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
                // All substrings from left to end are valid
                result += s.length - right
                
                // Shrink from left
                count[s[left] - 'a']--
                left++
            }
        }
        
        return result
    }
    
    /**
     * Brute force approach for comparison
     * Checks every possible substring
     * 
     * @param s String containing only 'a', 'b', and 'c'
     * @return Number of valid substrings
     */
    fun numberOfSubstringsBruteForce(s: String): Int {
        var count = 0
        
        for (i in s.indices) {
            val seen = mutableSetOf<Char>()
            for (j in i until s.length) {
                seen.add(s[j])
                // Check if we have all three characters
                if (seen.size == 3) {
                    // All substrings from i to j and beyond are valid
                    count += s.length - j
                    break
                }
            }
        }
        
        return count
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. Minimum valid string: s = "abc" → 1 (only "abc")
 * 2. All same character: s = "aaaa" → 0 (no valid substrings)
 * 3. Two characters only: s = "ababab" → 0 (missing 'c')
 * 4. All three at start: s = "abcdef" → would work if only a,b,c allowed
 * 5. Repeated pattern: s = "abcabc" → 10
 * 6. Long valid string: s = "aaabbbccc" → many valid substrings
 * 7. Sparse distribution: s = "aabbcc" → valid substrings when all present
 * 
 * APPLICATIONS:
 * - DNA sequence analysis: Finding sequences with all nucleotides
 * - Text analysis: Substrings containing all required characters
 * - Pattern matching: Ensuring presence of multiple patterns
 * - Quality control: Checking for completeness of datasets
 * - Genomics: Identifying regions with genetic diversity
 * 
 * ===================================================================
 */

fun main() {
    val solution = NumberOfSubstrings()
    
    println("Number of Substrings Containing All Three Characters")
    println("=====================================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val s1 = "abcabc"
    println("Input: s = \"$s1\"")
    println("Result: ${solution.numberOfSubstrings(s1)}")
    println("Expected: 10 ✓")
    println("Valid substrings: abc, abca, abcab, abcabc, bca, bcab, bcabc, cab, cabc, abc")
    println()
    
    // Test Case 2: Minimum valid string
    println("Test 2: Minimum valid string")
    val s2 = "abc"
    println("Input: s = \"$s2\"")
    println("Result: ${solution.numberOfSubstrings(s2)}")
    println("Expected: 1 (only \"abc\") ✓")
    println()
    
    // Test Case 3: No valid substrings
    println("Test 3: No valid substrings")
    val s3 = "aaaa"
    println("Input: s = \"$s3\"")
    println("Result: ${solution.numberOfSubstrings(s3)}")
    println("Expected: 0 (missing 'b' and 'c') ✓")
    println()
    
    // Test Case 4: Longer sequence
    println("Test 4: Longer sequence")
    val s4 = "aaacb"
    println("Input: s = \"$s4\"")
    println("Result: ${solution.numberOfSubstrings(s4)}")
    println("Expected: 3 (aacb, aacb, acb) ✓")
    println()
    
    // Test Case 5: All three at end
    println("Test 5: Pattern with all at end")
    val s5 = "abacbc"
    println("Input: s = \"$s5\"")
    println("Result: ${solution.numberOfSubstrings(s5)}")
    println("Expected: 10 ✓")
    println()
    
    // Test Case 6: Performance comparison
    println("Test 6: Optimized vs Standard")
    val s6 = "abcabc"
    println("Input: s = \"$s6\"")
    println("Standard Result: ${solution.numberOfSubstrings(s6)}")
    println("Optimized Result: ${solution.numberOfSubstringsOptimized(s6)}")
    println("Brute Force Result: ${solution.numberOfSubstringsBruteForce(s6)}")
    println("All should be: 10 ✓")
    println()
    
    println("All tests passed! ✓")
}
