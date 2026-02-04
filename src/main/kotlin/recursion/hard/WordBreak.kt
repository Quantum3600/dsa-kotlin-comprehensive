/**
 * ============================================================================
 * PROBLEM: Word Break
 * DIFFICULTY: Hard
 * CATEGORY: Recursion/Backtracking/Dynamic Programming
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string s and a dictionary of words, determine if s can be segmented
 * into a space-separated sequence of one or more dictionary words. Also,
 * find all possible such segmentations.
 * 
 * INPUT FORMAT:
 * - s: Input string (no spaces)
 * - wordDict: List of valid dictionary words
 * 
 * OUTPUT FORMAT:
 * - canBreak(): Boolean - true if string can be segmented
 * - getAllBreaks(): List of all possible segmentations
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 20
 * - 1 <= wordDict.length <= 1000
 * - 1 <= wordDict[i].length <= 10
 * - All strings consist of lowercase English letters
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * For each position in the string, try to match dictionary words:
 * 1. At each index, check if any dictionary word matches the prefix
 * 2. If a word matches, recursively solve for the remaining substring
 * 3. Use memoization to avoid recomputing same subproblems
 * 4. Backtrack to find all possible segmentations
 * 
 * VISUAL EXAMPLE:
 * 
 * Input: s = "catsanddog", wordDict = ["cat", "cats", "and", "sand", "dog"]
 * 
 * Recursion Tree:
 *                    catsanddog
 *                   /          \
 *              cat|sanddog    cats|anddog
 *                /                 \
 *          sand|dog              and|dog
 *             |                     |
 *           dog|✓                 dog|✓
 * 
 * Solutions:
 * 1. "cat sand dog"
 * 2. "cats and dog"
 * 
 * ALGORITHM STEPS:
 * 1. Base case: if string is empty, return true/add to result
 * 2. For each position i in string:
 *    a. Check if s[0...i] is in dictionary
 *    b. If yes, recursively check s[i+1...end]
 *    c. If recursive call succeeds, we found a valid break
 * 3. Use memoization to cache results for substrings
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Recursive with memoization (implemented)
 * 2. Dynamic Programming (bottom-up)
 * 3. BFS with queue
 * 4. Trie-based for large dictionaries
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(N² + 2^N)
 * - Without memoization: O(2^N) - try all possible breaks
 * - With memoization: O(N²) for canBreak check
 * - Finding all breaks: O(2^N) in worst case (exponential paths)
 * 
 * SPACE COMPLEXITY: O(N)
 * - Recursion stack: O(N)
 * - Memoization map: O(N)
 * - Result storage: O(2^N) for all segmentations
 * 
 * ============================================================================
 */

package recursion.hard

class WordBreak {
    
    /**
     * Check if string can be segmented into dictionary words
     * TIME: O(N²), SPACE: O(N) with memoization
     * 
     * @param s Input string
     * @param wordDict List of dictionary words
     * @return true if string can be segmented
     */
    fun canBreak(s: String, wordDict: List<String>): Boolean {
        val wordSet = wordDict.toSet()  // O(1) lookup
        val memo = mutableMapOf<String, Boolean>()
        return canBreakHelper(s, wordSet, memo)
    }
    
    /**
     * Recursive helper with memoization
     */
    private fun canBreakHelper(
        s: String,
        wordSet: Set<String>,
        memo: MutableMap<String, Boolean>
    ): Boolean {
        // Base case: empty string
        if (s.isEmpty()) return true
        
        // Check memo
        if (s in memo) return memo[s]!!
        
        // Try all possible first words
        for (i in 1..s.length) {
            val prefix = s.substring(0, i)
            if (prefix in wordSet) {
                val suffix = s.substring(i)
                if (canBreakHelper(suffix, wordSet, memo)) {
                    memo[s] = true
                    return true
                }
            }
        }
        
        memo[s] = false
        return false
    }
    
    /**
     * Find all possible word break combinations
     * TIME: O(2^N), SPACE: O(2^N)
     * 
     * @param s Input string
     * @param wordDict List of dictionary words
     * @return List of all possible segmentations
     */
    fun getAllBreaks(s: String, wordDict: List<String>): List<String> {
        val wordSet = wordDict.toSet()
        val memo = mutableMapOf<String, List<String>>()
        return getAllBreaksHelper(s, wordSet, memo)
    }
    
    /**
     * Recursive helper to find all segmentations
     */
    private fun getAllBreaksHelper(
        s: String,
        wordSet: Set<String>,
        memo: MutableMap<String, List<String>>
    ): List<String> {
        // Base case: empty string
        if (s.isEmpty()) return listOf("")
        
        // Check memo
        if (s in memo) return memo[s]!!
        
        val results = mutableListOf<String>()
        
        // Try all possible first words
        for (i in 1..s.length) {
            val prefix = s.substring(0, i)
            if (prefix in wordSet) {
                val suffix = s.substring(i)
                val suffixBreaks = getAllBreaksHelper(suffix, wordSet, memo)
                
                for (suffixBreak in suffixBreaks) {
                    val sentence = if (suffixBreak.isEmpty()) {
                        prefix
                    } else {
                        "$prefix $suffixBreak"
                    }
                    results.add(sentence)
                }
            }
        }
        
        memo[s] = results
        return results
    }
    
    /**
     * Count total number of ways to segment the string
     */
    fun countBreaks(s: String, wordDict: List<String>): Int {
        return getAllBreaks(s, wordDict).size
    }
    
    /**
     * Find the segmentation with minimum number of words
     */
    fun getMinWordBreak(s: String, wordDict: List<String>): String? {
        val allBreaks = getAllBreaks(s, wordDict)
        return allBreaks.minByOrNull { it.split(" ").size }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "catsanddog", wordDict = ["cat", "cats", "and", "sand", "dog"]
 * 
 * Recursive Exploration:
 * 
 * catsanddog
 *   ├─ Check "c" - not in dict
 *   ├─ Check "ca" - not in dict
 *   ├─ Check "cat" - IN DICT! ✓
 *   │   └─ Recurse on "sanddog"
 *   │       ├─ Check "s" - not in dict
 *   │       ├─ Check "sa" - not in dict
 *   │       ├─ Check "san" - not in dict
 *   │       ├─ Check "sand" - IN DICT! ✓
 *   │       │   └─ Recurse on "dog"
 *   │       │       └─ Check "dog" - IN DICT! ✓
 *   │       │           └─ Empty string - SUCCESS
 *   │       │           Result: "cat sand dog"
 *   ├─ Check "cats" - IN DICT! ✓
 *   │   └─ Recurse on "anddog"
 *   │       ├─ Check "and" - IN DICT! ✓
 *   │       │   └─ Recurse on "dog"
 *   │       │       └─ Check "dog" - IN DICT! ✓
 *   │       │           Result: "cats and dog"
 * 
 * OUTPUT: ["cat sand dog", "cats and dog"]
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty string: return true (or [""])
 * 2. No valid segmentation: return false (or [])
 * 3. Multiple ways to segment: return all
 * 4. Single character words: "aaa" with dict ["a"] -> "a a a"
 * 5. Overlapping words: "aaab" with dict ["a", "aa", "aaa"] -> multiple
 * 6. Word appears multiple times: handle correctly
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = WordBreak()
    
    println("=== Word Break ===\n")
    
    // Test 1: Basic example
    println("Test 1: Basic word break")
    val s1 = "leetcode"
    val dict1 = listOf("leet", "code")
    println("String: $s1")
    println("Dictionary: $dict1")
    println("Can break: ${solution.canBreak(s1, dict1)}")
    println("All breaks: ${solution.getAllBreaks(s1, dict1)}")
    println()
    
    // Test 2: Multiple segmentations
    println("Test 2: Multiple segmentations")
    val s2 = "catsanddog"
    val dict2 = listOf("cat", "cats", "and", "sand", "dog")
    println("String: $s2")
    println("Dictionary: $dict2")
    println("Can break: ${solution.canBreak(s2, dict2)}")
    val breaks2 = solution.getAllBreaks(s2, dict2)
    println("All breaks (${breaks2.size}):")
    breaks2.forEach { println("  \"$it\"") }
    println()
    
    // Test 3: No valid segmentation
    println("Test 3: No valid segmentation")
    val s3 = "catsandog"
    val dict3 = listOf("cats", "dog", "sand", "and", "cat")
    println("String: $s3")
    println("Dictionary: $dict3")
    println("Can break: ${solution.canBreak(s3, dict3)}")
    println("All breaks: ${solution.getAllBreaks(s3, dict3)}")
    println()
    
    // Test 4: Overlapping words
    println("Test 4: Overlapping words")
    val s4 = "aaaaaaa"
    val dict4 = listOf("a", "aa", "aaa", "aaaa")
    println("String: $s4")
    println("Dictionary: $dict4")
    println("Can break: ${solution.canBreak(s4, dict4)}")
    println("Number of ways: ${solution.countBreaks(s4, dict4)}")
    val breaks4 = solution.getAllBreaks(s4, dict4)
    println("First 5 breaks:")
    breaks4.take(5).forEach { println("  \"$it\"") }
    println()
    
    // Test 5: Single word
    println("Test 5: Single word")
    val s5 = "apple"
    val dict5 = listOf("apple", "pen")
    println("String: $s5")
    println("Dictionary: $dict5")
    println("Can break: ${solution.canBreak(s5, dict5)}")
    println("All breaks: ${solution.getAllBreaks(s5, dict5)}")
    println()
    
    // Test 6: Complex example
    println("Test 6: Complex example")
    val s6 = "pineapplepenapple"
    val dict6 = listOf("apple", "pen", "applepen", "pine", "pineapple")
    println("String: $s6")
    println("Dictionary: $dict6")
    println("Can break: ${solution.canBreak(s6, dict6)}")
    val breaks6 = solution.getAllBreaks(s6, dict6)
    println("All breaks (${breaks6.size}):")
    breaks6.forEach { println("  \"$it\"") }
    println("Min word break: ${solution.getMinWordBreak(s6, dict6)}")
}
