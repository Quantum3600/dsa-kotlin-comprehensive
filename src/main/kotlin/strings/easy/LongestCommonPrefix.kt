/**
 * ============================================================================
 * PROBLEM: Longest Common Prefix
 * DIFFICULTY: Easy
 * CATEGORY: Strings - Easy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Write a function to find the longest common prefix string amongst an array 
 * of strings. If there is no common prefix, return an empty string "".
 * 
 * INPUT FORMAT:
 * - An array of strings: strs = ["flower", "flow", "flight"]
 * 
 * OUTPUT FORMAT:
 * - String representing longest common prefix: "fl"
 * 
 * CONSTRAINTS:
 * - 1 <= strs.length <= 200
 * - 0 <= strs[i].length <= 200
 * - strs[i] consists of only lowercase English letters
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of finding the common prefix like aligning words vertically:
 * 
 * f l o w e r
 * f l o w
 * f l i g h t
 * ↓ ↓
 * Common: "fl"
 * 
 * We can compare character by character across all strings until we find
 * a mismatch or reach the end of any string.
 * 
 * KEY INSIGHTS:
 * 1. The longest common prefix cannot be longer than the shortest string
 * 2. All strings must have the same character at each position of the prefix
 * 3. Stop when any string ends or characters don't match
 * 
 * ALGORITHM STEPS (Horizontal Scanning):
 * 1. Start with first string as initial prefix
 * 2. For each subsequent string:
 *    - Compare with current prefix
 *    - Shorten prefix until it matches the start of current string
 * 3. If prefix becomes empty, return ""
 * 4. Return final prefix
 * 
 * ALGORITHM STEPS (Vertical Scanning - More Intuitive):
 * 1. Take first string as reference
 * 2. For each character position i in first string:
 *    - Check if all other strings have same character at position i
 *    - If not, return prefix up to position i-1
 * 3. If all characters match, return entire first string
 * 
 * VISUAL EXAMPLE:
 * strs = ["flower", "flow", "flight"]
 * 
 * Vertical Scanning:
 * Position 0: 'f' 'f' 'f' → All match ✓
 * Position 1: 'l' 'l' 'l' → All match ✓
 * Position 2: 'o' 'o' 'i' → Mismatch! ✗
 * 
 * Return "fl" (characters at positions 0-1)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Vertical scanning: O(S) time, O(1) space - SIMPLE & EFFICIENT
 * 2. Horizontal scanning: O(S) time, O(1) space - EASY TO UNDERSTAND
 * 3. Divide and conquer: O(S log n) time, O(log n) space - COMPLEX
 * 4. Binary search: O(S log m) time, O(1) space - OPTIMAL for some cases
 * 
 * where S = sum of all characters, n = number of strings, m = min string length
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(S)
 * - S = sum of all characters in all strings
 * - Worst case: All strings are identical, we check every character
 * - Best case: First character differs, O(n) where n is number of strings
 * - Average: O(n * m) where m is length of common prefix
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only use a few variables for tracking
 * - Result string is required output, not counted
 * - No additional data structures
 * 
 * WHY THIS IS OPTIMAL:
 * Must examine characters to find common prefix, so O(S) is optimal.
 * 
 * ============================================================================
 */

package strings.easy

class LongestCommonPrefix {
    
    /**
     * Finds longest common prefix using vertical scanning
     * 
     * @param strs Array of strings
     * @return Longest common prefix string
     */
    fun longestCommonPrefix(strs: Array<String>): String {
        // Edge case: Empty array
        if (strs.isEmpty()) return ""
        
        // Edge case: Single string
        if (strs.size == 1) return strs[0]
        
        // Use first string as reference
        val first = strs[0]
        
        // Check each character position
        for (i in first.indices) {
            val char = first[i]
            
            // Check if all other strings have the same character at position i
            for (j in 1 until strs.size) {
                // Check if current string is too short or character doesn't match
                if (i >= strs[j].length || strs[j][i] != char) {
                    // Mismatch or end of string found
                    // Return prefix up to (but not including) position i
                    return first.substring(0, i)
                }
            }
        }
        
        // All characters of first string matched in all strings
        // The entire first string is the common prefix
        return first
    }
    
    /**
     * Alternative: Horizontal scanning approach
     * Start with first string, then gradually reduce prefix
     */
    fun longestCommonPrefixHorizontal(strs: Array<String>): String {
        if (strs.isEmpty()) return ""
        
        // Start with first string as prefix
        var prefix = strs[0]
        
        // Compare with each subsequent string
        for (i in 1 until strs.size) {
            // While current string doesn't start with prefix
            // Shorten the prefix by removing last character
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length - 1)
                
                // If prefix becomes empty, no common prefix exists
                if (prefix.isEmpty()) return ""
            }
        }
        
        return prefix
    }
    
    /**
     * Alternative: Using sorted array
     * Compare only first and last strings (they differ most)
     */
    fun longestCommonPrefixSorted(strs: Array<String>): String {
        if (strs.isEmpty()) return ""
        
        // Sort strings lexicographically
        val sorted = strs.sorted()
        
        // Compare first and last strings
        // If they have common prefix, all strings in between will too
        val first = sorted.first()
        val last = sorted.last()
        
        val result = StringBuilder()
        
        // Find common prefix between first and last
        val minLength = minOf(first.length, last.length)
        for (i in 0 until minLength) {
            if (first[i] == last[i]) {
                result.append(first[i])
            } else {
                break
            }
        }
        
        return result.toString()
    }
    
    /**
     * Helper function using Kotlin's reduce
     * Functional approach
     */
    fun longestCommonPrefixFunctional(strs: Array<String>): String {
        if (strs.isEmpty()) return ""
        
        // Use reduce to find common prefix between consecutive strings
        return strs.reduce { prefix, str ->
            // Find common prefix between current prefix and next string
            val minLength = minOf(prefix.length, str.length)
            var i = 0
            while (i < minLength && prefix[i] == str[i]) {
                i++
            }
            prefix.substring(0, i)
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: strs = ["flower", "flow", "flight"]
 * 
 * VERTICAL SCANNING EXECUTION:
 * 
 * Reference string: "flower"
 * 
 * Position 0 (char 'f'):
 * - strs[0][0] = 'f'
 * - strs[1][0] = 'f' ✓
 * - strs[2][0] = 'f' ✓
 * All match, continue
 * 
 * Position 1 (char 'l'):
 * - strs[0][1] = 'l'
 * - strs[1][1] = 'l' ✓
 * - strs[2][1] = 'l' ✓
 * All match, continue
 * 
 * Position 2 (char 'o'):
 * - strs[0][2] = 'o'
 * - strs[1][2] = 'o' ✓
 * - strs[2][2] = 'i' ✗ MISMATCH!
 * 
 * Return substring(0, 2) = "fl"
 * 
 * OUTPUT: "fl" ✓
 * 
 * ---
 * 
 * Example 2: strs = ["dog", "racecar", "car"]
 * 
 * Position 0:
 * - 'd' vs 'r' → Mismatch at first character!
 * 
 * Return substring(0, 0) = ""
 * 
 * OUTPUT: "" ✓
 * 
 * ---
 * 
 * Example 3: strs = ["interspecies", "interstellar", "interstate"]
 * 
 * Position 0-4: 'i' 'n' 't' 'e' 'r' → All match
 * Position 5: 's' 's' 's' → All match
 * Position 6: 'p' 't' 't' → Mismatch!
 * 
 * Return "inters"
 * 
 * OUTPUT: "inters" ✓
 * 
 * ============================================================================
 * HORIZONTAL SCANNING WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: strs = ["flower", "flow", "flight"]
 * 
 * Initial prefix = "flower"
 * 
 * Compare with "flow":
 * - "flow" starts with "flower"? NO
 * - Shorten to "flowe"
 * - "flow" starts with "flowe"? NO
 * - Shorten to "flow"
 * - "flow" starts with "flow"? YES ✓
 * 
 * prefix = "flow"
 * 
 * Compare with "flight":
 * - "flight" starts with "flow"? NO
 * - Shorten to "flo"
 * - "flight" starts with "flo"? NO
 * - Shorten to "fl"
 * - "flight" starts with "fl"? YES ✓
 * 
 * prefix = "fl"
 * 
 * OUTPUT: "fl" ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty array [] → ""
 * 2. Single string ["hello"] → "hello"
 * 3. Empty string in array ["", "flow"] → ""
 * 4. All strings identical ["test", "test"] → "test"
 * 5. No common prefix ["dog", "cat"] → ""
 * 6. One string is prefix of another ["flow", "flower"] → "flow"
 * 7. Different lengths ["a", "ab", "abc"] → "a"
 * 8. All single characters ["a", "a"] → "a"
 * 9. Special characters (if allowed) handled
 * 10. Large arrays (up to 200 strings) handled efficiently
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not checking string length
 * ❌ Accessing strs[j][i] without checking if i < strs[j].length
 * ✅ Check length first: i >= strs[j].length
 * 
 * MISTAKE 2: Wrong substring bounds
 * ❌ substring(0, i+1) when mismatch at i
 * ✅ substring(0, i) to exclude mismatch position
 * 
 * MISTAKE 3: Not handling empty strings
 * ❌ Assuming all strings have at least one character
 * ✅ Check for empty strings and handle appropriately
 * 
 * MISTAKE 4: Inefficient string operations
 * ❌ Using string concatenation in loop: result += char
 * ✅ Use StringBuilder or substring operations
 * 
 * MISTAKE 5: Not handling edge cases
 * - Empty array
 * - Single string
 * - Strings with no common prefix
 * 
 * ============================================================================
 * WHEN TO USE EACH APPROACH
 * ============================================================================
 * 
 * VERTICAL SCANNING:
 * ✅ Simple and intuitive
 * ✅ Early termination on first mismatch
 * ✅ Best for most cases
 * 
 * HORIZONTAL SCANNING:
 * ✅ Easy to understand conceptually
 * ✅ Works well with startsWith check
 * ✅ Good when strings have long common prefixes
 * 
 * SORTED APPROACH:
 * ✅ Only need to compare 2 strings (first and last)
 * ❌ Sorting overhead: O(n log n * m)
 * ✅ Good when n is large but m is small
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **File Systems**: Finding common directory path
 * 2. **Autocomplete**: Suggesting common prefix for searches
 * 3. **Database**: Optimizing prefix-based queries
 * 4. **URL Processing**: Finding common URL base
 * 5. **Version Control**: Finding common branch prefix
 * 6. **Text Analysis**: Finding common word roots
 * 7. **Network**: Finding common network prefix
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = LongestCommonPrefix()
    
    println("=== Testing Longest Common Prefix ===\n")
    
    // Test 1: Basic example
    println("Test 1: Basic example with common prefix")
    val test1 = arrayOf("flower", "flow", "flight")
    println("Input: ${test1.contentToString()}")
    println("Output: \"${solution.longestCommonPrefix(test1)}\"")
    println("Expected: \"fl\"\n")
    
    // Test 2: No common prefix
    println("Test 2: No common prefix")
    val test2 = arrayOf("dog", "racecar", "car")
    println("Input: ${test2.contentToString()}")
    println("Output: \"${solution.longestCommonPrefix(test2)}\"")
    println("Expected: \"\" (empty)\n")
    
    // Test 3: All strings identical
    println("Test 3: All strings identical")
    val test3 = arrayOf("test", "test", "test")
    println("Input: ${test3.contentToString()}")
    println("Output: \"${solution.longestCommonPrefix(test3)}\"")
    println("Expected: \"test\"\n")
    
    // Test 4: One string is prefix of others
    println("Test 4: One string is prefix")
    val test4 = arrayOf("flow", "flower", "flowing")
    println("Input: ${test4.contentToString()}")
    println("Output: \"${solution.longestCommonPrefix(test4)}\"")
    println("Expected: \"flow\"\n")
    
    // Test 5: Single string
    println("Test 5: Single string")
    val test5 = arrayOf("alone")
    println("Input: ${test5.contentToString()}")
    println("Output: \"${solution.longestCommonPrefix(test5)}\"")
    println("Expected: \"alone\"\n")
    
    // Test 6: Empty string in array
    println("Test 6: Empty string in array")
    val test6 = arrayOf("", "flow")
    println("Input: ${test6.contentToString()}")
    println("Output: \"${solution.longestCommonPrefix(test6)}\"")
    println("Expected: \"\" (empty)\n")
    
    // Test 7: Horizontal scanning approach
    println("Test 7: Horizontal scanning")
    val test7 = arrayOf("interspecies", "interstellar", "interstate")
    println("Input: ${test7.contentToString()}")
    println("Horizontal output: \"${solution.longestCommonPrefixHorizontal(test7)}\"")
    println("Expected: \"inters\"\n")
    
    // Test 8: Sorted approach
    println("Test 8: Sorted approach")
    val test8 = arrayOf("flower", "flow", "flight")
    println("Input: ${test8.contentToString()}")
    println("Sorted output: \"${solution.longestCommonPrefixSorted(test8)}\"")
    println("Expected: \"fl\"\n")
    
    // Test 9: Functional approach
    println("Test 9: Functional approach")
    val test9 = arrayOf("prefix", "prefab", "preface")
    println("Input: ${test9.contentToString()}")
    println("Functional output: \"${solution.longestCommonPrefixFunctional(test9)}\"")
    println("Expected: \"pref\"\n")
    
    // Test 10: Two strings
    println("Test 10: Two strings only")
    val test10 = arrayOf("ab", "a")
    println("Input: ${test10.contentToString()}")
    println("Output: \"${solution.longestCommonPrefix(test10)}\"")
    println("Expected: \"a\"\n")
}
