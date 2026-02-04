package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Letter Combinations of a Phone Number
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string containing digits from 2-9 inclusive, return all possible
 * letter combinations that the number could represent. Return the answer in
 * any order.
 * 
 * A mapping of digits to letters (just like on the telephone buttons) is
 * given below. Note that 1 does not map to any letters.
 * 
 * DIGIT MAPPING:
 * 2 → "abc"
 * 3 → "def"
 * 4 → "ghi"
 * 5 → "jkl"
 * 6 → "mno"
 * 7 → "pqrs"
 * 8 → "tuv"
 * 9 → "wxyz"
 * 
 * INPUT FORMAT:
 * - A string of digits: "23"
 * 
 * OUTPUT FORMAT:
 * - List of all possible letter combinations
 * Example: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * 
 * CONSTRAINTS:
 * - 0 <= digits.length <= 4
 * - digits[i] is a digit in the range ['2', '9']
 * 
 * EXAMPLES:
 * Input: digits = "23"
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * 
 * Input: digits = ""
 * Output: []
 * 
 * Input: digits = "2"
 * Output: ["a","b","c"]
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is a classic backtracking problem. For each digit in the input:
 * - We have multiple letter choices (3 or 4 letters)
 * - We need to explore all possible combinations
 * - Think of it as building a decision tree where each level represents a digit
 * 
 * KEY INSIGHT:
 * The total number of combinations = product of letter counts for each digit
 * For "23": 3 letters × 3 letters = 9 combinations
 * For "234": 3 × 3 × 3 = 27 combinations
 * 
 * ALGORITHM STEPS:
 * 1. Create digit-to-letters mapping
 * 2. Use backtracking starting from index 0
 * 3. At each position (digit):
 *    - Get corresponding letters for current digit
 *    - Try each letter one by one
 *    - Recurse to next digit
 *    - Backtrack after exploring
 * 4. Base case: When we've processed all digits, add combination to result
 * 
 * VISUAL EXAMPLE:
 * Input: "23"
 * 
 *                      ""
 *                    / | \
 *                   a  b  c    (digit '2' → "abc")
 *                 /|\ /|\ /|\
 *                d e f d e f d e f  (digit '3' → "def")
 * 
 * Result: ad, ae, af, bd, be, bf, cd, ce, cf
 * 
 * DECISION TREE VISUALIZATION:
 * Level 0 (digit '2'): Choose from {a, b, c}
 *   Level 1 (digit '3'): Choose from {d, e, f}
 *     Level 2: All digits processed → Add to result
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Iterative with Queue/List: Build combinations level by level
 * 2. Iterative with cross-product: Use nested loops
 * 3. Bit manipulation: For systematic enumeration
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(4^n * n)
 * - n = length of input digits
 * - Each digit maps to at most 4 letters (7 and 9)
 * - Total combinations: 4^n in worst case
 * - For each combination, we build a string of length n: O(n)
 * - Overall: O(4^n * n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion depth: O(n) - one level per digit
 * - Current combination storage: O(n)
 * - Output storage: O(4^n * n) but not counted in auxiliary space
 * - Overall auxiliary space: O(n)
 */

class LetterCombination {
    
    // Digit to letters mapping
    private val digitMap = mapOf(
        '2' to "abc",
        '3' to "def",
        '4' to "ghi",
        '5' to "jkl",
        '6' to "mno",
        '7' to "pqrs",
        '8' to "tuv",
        '9' to "wxyz"
    )
    
    /**
     * Generate all letter combinations for given digits
     * @param digits String of digits (2-9)
     * @return List of all possible letter combinations
     */
    fun letterCombinations(digits: String): List<String> {
        // Edge case: empty input
        if (digits.isEmpty()) return emptyList()
        
        val result = mutableListOf<String>()
        val current = StringBuilder()
        
        backtrack(digits, 0, current, result)
        
        return result
    }
    
    /**
     * Backtracking helper to build combinations
     * @param digits Input digit string
     * @param index Current position in digits
     * @param current Current combination being built
     * @param result Collection of all combinations
     */
    private fun backtrack(
        digits: String,
        index: Int,
        current: StringBuilder,
        result: MutableList<String>
    ) {
        // BASE CASE: Processed all digits
        if (index == digits.length) {
            result.add(current.toString())
            return
        }
        
        // Get letters corresponding to current digit
        val currentDigit = digits[index]
        val letters = digitMap[currentDigit] ?: ""
        
        // Try each letter for this digit
        for (letter in letters) {
            // Choose: Add current letter
            current.append(letter)
            
            // Explore: Recurse to next digit
            backtrack(digits, index + 1, current, result)
            
            // Unchoose: Remove current letter (backtrack)
            current.deleteCharAt(current.length - 1)
        }
    }
    
    /**
     * Alternative: Iterative approach using queue
     * Build combinations level by level
     */
    fun letterCombinationsIterative(digits: String): List<String> {
        if (digits.isEmpty()) return emptyList()
        
        var result = mutableListOf<String>()
        result.add("")  // Start with empty string
        
        // For each digit, expand all existing combinations
        for (digit in digits) {
            val letters = digitMap[digit] ?: continue
            val newResult = mutableListOf<String>()
            
            // For each existing combination
            for (combination in result) {
                // Add each possible letter
                for (letter in letters) {
                    newResult.add(combination + letter)
                }
            }
            
            result = newResult
        }
        
        return result
    }
    
    /**
     * Alternative: Using list operations (functional style)
     */
    fun letterCombinationsFunctional(digits: String): List<String> {
        if (digits.isEmpty()) return emptyList()
        
        return digits.fold(listOf("")) { combinations, digit ->
            val letters = digitMap[digit] ?: ""
            combinations.flatMap { combination ->
                letters.map { letter -> combination + letter }
            }
        }
    }
    
    /**
     * Get total number of combinations without generating them
     */
    fun countCombinations(digits: String): Int {
        if (digits.isEmpty()) return 0
        
        var count = 1
        for (digit in digits) {
            val letters = digitMap[digit] ?: ""
            count *= letters.length
        }
        
        return count
    }
    
    /**
     * Generate Kth combination (0-indexed) directly
     * Useful when you need a specific combination without generating all
     */
    fun getKthCombination(digits: String, k: Int): String {
        if (digits.isEmpty()) return ""
        
        val result = StringBuilder()
        var k = k
        
        for (digit in digits) {
            val letters = digitMap[digit] ?: ""
            val letterCount = letters.length
            
            // Determine which letter to pick
            val index = k % letterCount
            result.append(letters[index])
            
            k /= letterCount
        }
        
        return result.toString()
    }
    
    /**
     * Generate combinations in lexicographic order
     * (Already in order with standard approach, but this makes it explicit)
     */
    fun letterCombinationsLexicographic(digits: String): List<String> {
        if (digits.isEmpty()) return emptyList()
        
        val result = mutableListOf<String>()
        val current = CharArray(digits.length)
        
        generateLexicographic(digits, 0, current, result)
        
        return result
    }
    
    private fun generateLexicographic(
        digits: String,
        index: Int,
        current: CharArray,
        result: MutableList<String>
    ) {
        if (index == digits.length) {
            result.add(String(current))
            return
        }
        
        val letters = digitMap[digits[index]] ?: ""
        for (letter in letters) {
            current[index] = letter
            generateLexicographic(digits, index + 1, current, result)
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: digits = "23"
 * 
 * Initial Call: backtrack("23", 0, "", result)
 * 
 * Level 0: index=0, digit='2', letters="abc"
 * │
 * ├─ Choose 'a': current = "a"
 * │  │ backtrack("23", 1, "a", result)
 * │  │ Level 1: index=1, digit='3', letters="def"
 * │  │ │
 * │  │ ├─ Choose 'd': current = "ad"
 * │  │ │  │ backtrack("23", 2, "ad", result)
 * │  │ │  │ index=2 = length, add "ad" to result ✓
 * │  │ │  │ result = ["ad"]
 * │  │ │  backtrack, remove 'd' → current = "a"
 * │  │ │
 * │  │ ├─ Choose 'e': current = "ae"
 * │  │ │  │ backtrack("23", 2, "ae", result)
 * │  │ │  │ index=2 = length, add "ae" to result ✓
 * │  │ │  │ result = ["ad", "ae"]
 * │  │ │  backtrack, remove 'e' → current = "a"
 * │  │ │
 * │  │ ├─ Choose 'f': current = "af"
 * │  │ │  │ backtrack("23", 2, "af", result)
 * │  │ │  │ index=2 = length, add "af" to result ✓
 * │  │ │  │ result = ["ad", "ae", "af"]
 * │  │ │  backtrack, remove 'f' → current = "a"
 * │  │
 * │  backtrack, remove 'a' → current = ""
 * │
 * ├─ Choose 'b': current = "b"
 * │  │ backtrack("23", 1, "b", result)
 * │  │ Level 1: index=1, digit='3', letters="def"
 * │  │ │
 * │  │ ├─ Choose 'd': current = "bd"
 * │  │ │  │ Add "bd" to result ✓
 * │  │ │  │ result = ["ad", "ae", "af", "bd"]
 * │  │ │
 * │  │ ├─ Choose 'e': current = "be"
 * │  │ │  │ Add "be" to result ✓
 * │  │ │  │ result = ["ad", "ae", "af", "bd", "be"]
 * │  │ │
 * │  │ ├─ Choose 'f': current = "bf"
 * │  │ │  │ Add "bf" to result ✓
 * │  │ │  │ result = ["ad", "ae", "af", "bd", "be", "bf"]
 * │  │
 * │  backtrack, remove 'b' → current = ""
 * │
 * ├─ Choose 'c': current = "c"
 * │  │ backtrack("23", 1, "c", result)
 * │  │ Level 1: index=1, digit='3', letters="def"
 * │  │ │
 * │  │ ├─ Choose 'd': current = "cd"
 * │  │ │  │ Add "cd" to result ✓
 * │  │ │  │ result = ["ad", "ae", "af", "bd", "be", "bf", "cd"]
 * │  │ │
 * │  │ ├─ Choose 'e': current = "ce"
 * │  │ │  │ Add "ce" to result ✓
 * │  │ │  │ result = ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce"]
 * │  │ │
 * │  │ ├─ Choose 'f': current = "cf"
 * │  │ │  │ Add "cf" to result ✓
 * │  │ │  │ result = ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
 * │  │
 * │  backtrack, remove 'c' → current = ""
 * 
 * Final Result: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
 * 
 * 
 * Iterative Approach Walkthrough:
 * Input: "23"
 * 
 * Initial: result = [""]
 * 
 * Process digit '2' (letters = "abc"):
 *   For each combination in result (just ""):
 *     For each letter in "abc":
 *       newResult.add("" + letter)
 *   newResult = ["a", "b", "c"]
 *   result = ["a", "b", "c"]
 * 
 * Process digit '3' (letters = "def"):
 *   For each combination in result (["a", "b", "c"]):
 *     For "a":
 *       For each letter in "def":
 *         newResult.add("a" + letter)
 *       → ["ad", "ae", "af"]
 *     For "b":
 *       For each letter in "def":
 *         newResult.add("b" + letter)
 *       → ["ad", "ae", "af", "bd", "be", "bf"]
 *     For "c":
 *       For each letter in "def":
 *         newResult.add("c" + letter)
 *       → ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
 *   result = ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
 * 
 * Final Result: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
 */

fun main() {
    val solver = LetterCombination()
    
    // Test Case 1: Two digits
    println("Test Case 1: digits = \"23\"")
    println("Result: ${solver.letterCombinations("23")}")
    println("Expected: [ad, ae, af, bd, be, bf, cd, ce, cf]")
    println()
    
    // Test Case 2: Empty string
    println("Test Case 2: digits = \"\"")
    println("Result: ${solver.letterCombinations("")}")
    println("Expected: []")
    println()
    
    // Test Case 3: Single digit
    println("Test Case 3: digits = \"2\"")
    println("Result: ${solver.letterCombinations("2")}")
    println("Expected: [a, b, c]")
    println()
    
    // Test Case 4: Three digits
    println("Test Case 4: digits = \"234\"")
    val result4 = solver.letterCombinations("234")
    println("Result count: ${result4.size}")
    println("First few: ${result4.take(10)}")
    println("Expected count: 27 (3 × 3 × 3)")
    println()
    
    // Test Case 5: Digit with 4 letters (7 or 9)
    println("Test Case 5: digits = \"79\"")
    val result5 = solver.letterCombinations("79")
    println("Result count: ${result5.size}")
    println("First few: ${result5.take(10)}")
    println("Expected count: 16 (4 × 4)")
    println()
    
    // Test Case 6: Maximum length (4 digits)
    println("Test Case 6: digits = \"2345\"")
    val result6 = solver.letterCombinations("2345")
    println("Result count: ${result6.size}")
    println("First few: ${result6.take(5)}")
    println("Last few: ${result6.takeLast(5)}")
    println("Expected count: 81 (3 × 3 × 3 × 3)")
    println()
    
    // Test Case 7: Compare all implementations
    println("Test Case 7: Compare implementations for \"23\"")
    val recursive = solver.letterCombinations("23")
    val iterative = solver.letterCombinationsIterative("23")
    val functional = solver.letterCombinationsFunctional("23")
    println("Recursive: $recursive")
    println("Iterative: $iterative")
    println("Functional: $functional")
    println("All equal: ${recursive == iterative && iterative == functional}")
    println()
    
    // Test Case 8: Count combinations
    println("Test Case 8: Count combinations without generating")
    val testDigits = "7777"
    val count = solver.countCombinations(testDigits)
    val actualResult = solver.letterCombinations(testDigits)
    println("Digits: $testDigits")
    println("Counted: $count")
    println("Actual: ${actualResult.size}")
    println("Match: ${count == actualResult.size}")
    println()
    
    // Test Case 9: Get specific combination
    println("Test Case 9: Get Kth combination for \"23\"")
    val digits = "23"
    val allCombinations = solver.letterCombinations(digits)
    println("All combinations: $allCombinations")
    for (k in 0 until allCombinations.size) {
        val kth = solver.getKthCombination(digits, k)
        println("K=$k: $kth (expected: ${allCombinations[k]})")
    }
    println()
    
    // Test Case 10: Performance test
    println("Test Case 10: Performance comparison")
    val perfDigits = "2468"
    
    val start1 = System.nanoTime()
    val result1 = solver.letterCombinations(perfDigits)
    val time1 = (System.nanoTime() - start1) / 1_000_000.0
    
    val start2 = System.nanoTime()
    val result2 = solver.letterCombinationsIterative(perfDigits)
    val time2 = (System.nanoTime() - start2) / 1_000_000.0
    
    val start3 = System.nanoTime()
    val result3 = solver.letterCombinationsFunctional(perfDigits)
    val time3 = (System.nanoTime() - start3) / 1_000_000.0
    
    println("Digits: $perfDigits, Count: ${result1.size}")
    println("Recursive: ${time1}ms")
    println("Iterative: ${time2}ms")
    println("Functional: ${time3}ms")
}
