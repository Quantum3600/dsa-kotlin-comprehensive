package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Palindrome Partitioning
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string s, partition s such that every substring of the partition
 * is a palindrome. Return all possible palindrome partitioning of s.
 * 
 * A palindrome is a string that reads the same backward as forward.
 * 
 * INPUT FORMAT:
 * - A string s: "aab"
 * 
 * OUTPUT FORMAT:
 * - List of all possible palindrome partitions
 * Example: [["a","a","b"], ["aa","b"]]
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 16
 * - s contains only lowercase English letters
 * 
 * EXAMPLES:
 * Input: s = "aab"
 * Output: [["a","a","b"], ["aa","b"]]
 * 
 * Input: s = "a"
 * Output: [["a"]]
 * 
 * Input: s = "abba"
 * Output: [["a","b","b","a"], ["a","bb","a"], ["abba"]]
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * At each position in the string, we need to decide where to make a cut.
 * A cut is valid only if the substring formed is a palindrome.
 * 
 * KEY INSIGHT:
 * - At each index, try making cuts at all possible positions
 * - For each cut, check if the substring is a palindrome
 * - If yes, recurse on the remaining part of the string
 * - If we reach the end of string, we found a valid partition
 * 
 * ALGORITHM STEPS:
 * 1. Start from index 0
 * 2. Try cutting at each position from current index to end
 * 3. For each cut position:
 *    a. Extract substring from start to cut position
 *    b. Check if it's a palindrome
 *    c. If yes, add to current partition and recurse on remaining string
 *    d. Backtrack after exploring
 * 4. Base case: Reached end of string → add partition to result
 * 
 * VISUAL EXAMPLE:
 * Input: "aab"
 * 
 *                      "aab" (start=0)
 *                    /    |     \
 *               "a"|ab  "aa"|b  "aab"
 *               ✓(pal)   ✓(pal)  ✗(not pal)
 *                 /        |
 *             "ab"        "b"
 *             /  \         |
 *         "a"|b  "ab"     "b"
 *         ✓(pal) ✗(pal)   ✓(pal)
 *           /              |
 *         "b"            [done]
 *         ✓(pal)
 *          |
 *        [done]
 * 
 * Result paths:
 * 1. "a" → "a" → "b" → ["a","a","b"]
 * 2. "aa" → "b" → ["aa","b"]
 * 
 * OPTIMIZATION - PREPROCESSING:
 * Instead of checking palindrome every time during recursion,
 * precompute a 2D DP table where dp[i][j] = true if s[i..j] is palindrome.
 * This reduces palindrome checks from O(n) to O(1).
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Without DP preprocessing (check palindrome each time)
 * 2. Memoization: Cache results for substrings
 * 3. Iterative with stack/queue
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * 2^n)
 * - In worst case (all characters same), every partition is valid
 * - Number of ways to partition a string of length n: 2^(n-1)
 * - For each partition, we copy strings: O(n)
 * - Overall: O(n * 2^n)
 * 
 * With DP preprocessing:
 * - Preprocessing: O(n²) to build palindrome table
 * - Backtracking: O(n * 2^n)
 * - Overall: O(n * 2^n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion depth: O(n) - worst case all single characters
 * - Current partition storage: O(n)
 * - DP table (if used): O(n²)
 * - Overall: O(n²) with DP, O(n) without DP
 */

class PalindromePartitioning {
    
    /**
     * Find all palindrome partitions of the string
     * @param s Input string
     * @return List of all valid palindrome partitions
     */
    fun partition(s: String): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val current = mutableListOf<String>()
        
        backtrack(s, 0, current, result)
        
        return result
    }
    
    /**
     * Backtracking helper to find all partitions
     * @param s Input string
     * @param start Starting index for current partition
     * @param current Current partition being built
     * @param result Collection of all valid partitions
     */
    private fun backtrack(
        s: String,
        start: Int,
        current: MutableList<String>,
        result: MutableList<List<String>>
    ) {
        // BASE CASE: Reached end of string
        if (start == s.length) {
            result.add(ArrayList(current))
            return
        }
        
        // Try all possible cuts from current position
        for (end in start until s.length) {
            // Extract substring from start to end (inclusive)
            val substring = s.substring(start, end + 1)
            
            // Check if substring is palindrome
            if (isPalindrome(substring)) {
                // Choose: Add palindrome to current partition
                current.add(substring)
                
                // Explore: Recurse on remaining string
                backtrack(s, end + 1, current, result)
                
                // Unchoose: Remove last added (backtrack)
                current.removeAt(current.size - 1)
            }
        }
    }
    
    /**
     * Check if a string is palindrome
     * @param s String to check
     * @return True if palindrome, false otherwise
     */
    private fun isPalindrome(s: String): Boolean {
        var left = 0
        var right = s.length - 1
        
        while (left < right) {
            if (s[left] != s[right]) {
                return false
            }
            left++
            right--
        }
        
        return true
    }
    
    /**
     * Optimized version with DP preprocessing
     * Precompute which substrings are palindromes
     */
    fun partitionOptimized(s: String): List<List<String>> {
        val n = s.length
        
        // Build palindrome DP table
        // dp[i][j] = true if s[i..j] is palindrome
        val dp = Array(n) { BooleanArray(n) }
        
        // Every single character is a palindrome
        for (i in 0 until n) {
            dp[i][i] = true
        }
        
        // Check for palindromes of length 2
        for (i in 0 until n - 1) {
            if (s[i] == s[i + 1]) {
                dp[i][i + 1] = true
            }
        }
        
        // Check for palindromes of length 3 and more
        for (length in 3..n) {
            for (i in 0..n - length) {
                val j = i + length - 1
                // s[i..j] is palindrome if:
                // - s[i] == s[j] AND
                // - s[i+1..j-1] is palindrome
                if (s[i] == s[j] && dp[i + 1][j - 1]) {
                    dp[i][j] = true
                }
            }
        }
        
        val result = mutableListOf<List<String>>()
        val current = mutableListOf<String>()
        
        backtrackWithDP(s, 0, current, result, dp)
        
        return result
    }
    
    private fun backtrackWithDP(
        s: String,
        start: Int,
        current: MutableList<String>,
        result: MutableList<List<String>>,
        dp: Array<BooleanArray>
    ) {
        if (start == s.length) {
            result.add(ArrayList(current))
            return
        }
        
        for (end in start until s.length) {
            // O(1) palindrome check using DP table
            if (dp[start][end]) {
                val substring = s.substring(start, end + 1)
                current.add(substring)
                backtrackWithDP(s, end + 1, current, result, dp)
                current.removeAt(current.size - 1)
            }
        }
    }
    
    /**
     * Alternative: Return count of valid partitions instead of all partitions
     */
    fun countPartitions(s: String): Int {
        return countHelper(s, 0)
    }
    
    private fun countHelper(s: String, start: Int): Int {
        if (start == s.length) {
            return 1
        }
        
        var count = 0
        
        for (end in start until s.length) {
            val substring = s.substring(start, end + 1)
            if (isPalindrome(substring)) {
                count += countHelper(s, end + 1)
            }
        }
        
        return count
    }
    
    /**
     * Find minimum cuts needed for palindrome partitioning
     * (Related problem - not all partitions, just minimum cuts)
     */
    fun minCut(s: String): Int {
        val n = s.length
        
        // Build palindrome DP table
        val isPal = Array(n) { BooleanArray(n) }
        for (i in 0 until n) {
            isPal[i][i] = true
        }
        for (i in 0 until n - 1) {
            if (s[i] == s[i + 1]) {
                isPal[i][i + 1] = true
            }
        }
        for (length in 3..n) {
            for (i in 0..n - length) {
                val j = i + length - 1
                if (s[i] == s[j] && isPal[i + 1][j - 1]) {
                    isPal[i][j] = true
                }
            }
        }
        
        // DP for minimum cuts
        // cuts[i] = minimum cuts needed for s[0..i]
        val cuts = IntArray(n)
        
        for (i in 0 until n) {
            if (isPal[0][i]) {
                cuts[i] = 0  // No cut needed if whole substring is palindrome
            } else {
                cuts[i] = i  // Maximum cuts needed
                for (j in 0 until i) {
                    if (isPal[j + 1][i]) {
                        cuts[i] = minOf(cuts[i], cuts[j] + 1)
                    }
                }
            }
        }
        
        return cuts[n - 1]
    }
    
    /**
     * Check if string can be partitioned into k palindromes
     */
    fun canPartitionKPalindromes(s: String, k: Int): Boolean {
        // Minimum palindromes needed = 1 (if whole string is palindrome)
        // Maximum palindromes needed = s.length (each character)
        if (k > s.length) return false
        if (k == s.length) return true
        
        // Need to check if we can partition into exactly k palindromes
        // This requires at least (k-1) cuts
        val minCuts = minCut(s)
        return minCuts <= k - 1
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: s = "aab"
 * 
 * backtrack("aab", 0, [], result)
 * │
 * ├─ Try end=0: substring="a" (index 0 to 0)
 * │  │ isPalindrome("a") = true ✓
 * │  │ current = ["a"]
 * │  │ backtrack("aab", 1, ["a"], result)
 * │  │ │
 * │  │ ├─ Try end=1: substring="a" (index 1 to 1)
 * │  │ │  │ isPalindrome("a") = true ✓
 * │  │ │  │ current = ["a", "a"]
 * │  │ │  │ backtrack("aab", 2, ["a","a"], result)
 * │  │ │  │ │
 * │  │ │  │ ├─ Try end=2: substring="b" (index 2 to 2)
 * │  │ │  │ │  │ isPalindrome("b") = true ✓
 * │  │ │  │ │  │ current = ["a", "a", "b"]
 * │  │ │  │ │  │ backtrack("aab", 3, ["a","a","b"], result)
 * │  │ │  │ │  │ │ start=3 = length ✓
 * │  │ │  │ │  │ │ Add ["a","a","b"] to result
 * │  │ │  │ │  │ │ result = [["a","a","b"]]
 * │  │ │  │ │  │ backtrack, remove "b" → current = ["a", "a"]
 * │  │ │  │ │
 * │  │ │  │ backtrack, remove "a" → current = ["a"]
 * │  │ │
 * │  │ ├─ Try end=2: substring="ab" (index 1 to 2)
 * │  │ │  │ isPalindrome("ab") = false ✗
 * │  │ │  │ Skip this branch
 * │  │ │
 * │  │ backtrack, remove "a" → current = []
 * │
 * ├─ Try end=1: substring="aa" (index 0 to 1)
 * │  │ isPalindrome("aa") = true ✓
 * │  │ current = ["aa"]
 * │  │ backtrack("aab", 2, ["aa"], result)
 * │  │ │
 * │  │ ├─ Try end=2: substring="b" (index 2 to 2)
 * │  │ │  │ isPalindrome("b") = true ✓
 * │  │ │  │ current = ["aa", "b"]
 * │  │ │  │ backtrack("aab", 3, ["aa","b"], result)
 * │  │ │  │ │ start=3 = length ✓
 * │  │ │  │ │ Add ["aa","b"] to result
 * │  │ │  │ │ result = [["a","a","b"], ["aa","b"]]
 * │  │ │  │ backtrack, remove "b" → current = ["aa"]
 * │  │ │
 * │  │ backtrack, remove "aa" → current = []
 * │
 * ├─ Try end=2: substring="aab" (index 0 to 2)
 * │  │ isPalindrome("aab") = false ✗
 * │  │ Skip this branch
 * 
 * Final Result: [["a","a","b"], ["aa","b"]]
 * 
 * 
 * Palindrome Check Example:
 * isPalindrome("aa"):
 *   left=0, right=1
 *   s[0]='a' == s[1]='a' ✓
 *   left++ → left=1
 *   right-- → right=0
 *   left >= right, exit loop
 *   return true
 * 
 * isPalindrome("aab"):
 *   left=0, right=2
 *   s[0]='a' != s[2]='b' ✗
 *   return false
 */

fun main() {
    val solver = PalindromePartitioning()
    
    // Test Case 1: Basic example
    println("Test Case 1: s = \"aab\"")
    println("Result: ${solver.partition("aab")}")
    println("Expected: [[a,a,b], [aa,b]]")
    println()
    
    // Test Case 2: Single character
    println("Test Case 2: s = \"a\"")
    println("Result: ${solver.partition("a")}")
    println("Expected: [[a]]")
    println()
    
    // Test Case 3: All same characters
    println("Test Case 3: s = \"aaa\"")
    println("Result: ${solver.partition("aaa")}")
    println("Expected: Multiple partitions")
    println()
    
    // Test Case 4: Palindrome string
    println("Test Case 4: s = \"abba\"")
    println("Result: ${solver.partition("abba")}")
    println("Expected: [[a,b,b,a], [a,bb,a], [abba]]")
    println()
    
    // Test Case 5: No middle palindromes
    println("Test Case 5: s = \"abc\"")
    println("Result: ${solver.partition("abc")}")
    println("Expected: [[a,b,c]] (only single characters are palindromes)")
    println()
    
    // Test Case 6: Complex case
    println("Test Case 6: s = \"racecar\"")
    val result6 = solver.partition("racecar")
    println("Result count: ${result6.size}")
    println("Some partitions: ${result6.take(5)}")
    println("Expected: Multiple partitions including [racecar]")
    println()
    
    // Test Case 7: Compare implementations
    println("Test Case 7: Compare standard vs optimized")
    val testStr = "aabb"
    
    val start1 = System.nanoTime()
    val result1 = solver.partition(testStr)
    val time1 = (System.nanoTime() - start1) / 1_000_000.0
    
    val start2 = System.nanoTime()
    val result2 = solver.partitionOptimized(testStr)
    val time2 = (System.nanoTime() - start2) / 1_000_000.0
    
    println("Standard: ${result1.size} partitions in ${time1}ms")
    println("Optimized: ${result2.size} partitions in ${time2}ms")
    println("Results match: ${result1.toSet() == result2.toSet()}")
    println()
    
    // Test Case 8: Count partitions
    println("Test Case 8: Count partitions for \"aab\"")
    val count = solver.countPartitions("aab")
    val actual = solver.partition("aab")
    println("Counted: $count")
    println("Actual: ${actual.size}")
    println("Match: ${count == actual.size}")
    println()
    
    // Test Case 9: Minimum cuts
    println("Test Case 9: Minimum cuts needed")
    println("s = \"aab\": ${solver.minCut("aab")} cuts")
    println("Expected: 1 (aa|b)")
    println("s = \"abcba\": ${solver.minCut("abcba")} cuts")
    println("Expected: 0 (entire string is palindrome)")
    println("s = \"abc\": ${solver.minCut("abc")} cuts")
    println("Expected: 2 (a|b|c)")
    println()
    
    // Test Case 10: Edge cases
    println("Test Case 10: Edge cases")
    println("s = \"efe\": ${solver.partition("efe")}")
    println("Expected: [[e,f,e], [efe]]")
    println()
    println("s = \"bb\": ${solver.partition("bb")}")
    println("Expected: [[b,b], [bb]]")
    println()
    
    // Test Case 11: Performance test
    println("Test Case 11: Performance test with longer string")
    val longStr = "aaaaaaaaaa"  // 10 'a's
    val startTime = System.currentTimeMillis()
    val longResult = solver.partitionOptimized(longStr)
    val endTime = System.currentTimeMillis()
    println("String: $longStr (length ${longStr.length})")
    println("Partitions found: ${longResult.size}")
    println("Time: ${endTime - startTime}ms")
    println("Expected: 2^(n-1) = ${1 shl (longStr.length - 1)} partitions")
}
