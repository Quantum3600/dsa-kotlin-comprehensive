/**
 * ============================================================================
 * PROBLEM: Count and Say
 * DIFFICULTY: Medium
 * CATEGORY: Strings - Medium
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * The count-and-say sequence is a sequence of digit strings defined by the 
 * recursive formula:
 * - countAndSay(1) = "1"
 * - countAndSay(n) is the run-length encoding of countAndSay(n - 1)
 * 
 * Run-length encoding (RLE) is a string compression method that works by 
 * replacing consecutive identical characters with the count followed by the 
 * character. For example, "3322251" can be encoded as "23321511".
 * 
 * Given a positive integer n, return the nth element of the count-and-say sequence.
 * 
 * INPUT FORMAT:
 * - A positive integer n: n = 4
 * 
 * OUTPUT FORMAT:
 * - String representing the nth count-and-say sequence: "1211"
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 30
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The count-and-say sequence builds each term by "reading" the previous term:
 * 
 * n=1: "1"                  (base case)
 * n=2: "11"                 (one 1)
 * n=3: "21"                 (two 1s)
 * n=4: "1211"               (one 2, then one 1)
 * n=5: "111221"             (one 1, one 2, then two 1s)
 * n=6: "312211"             (three 1s, two 2s, then one 1)
 * 
 * KEY INSIGHTS:
 * 1. Each term is derived from the previous term
 * 2. Count consecutive identical digits
 * 3. Output format: count + digit
 * 4. Process sequentially from n=1 to n
 * 
 * ALGORITHM STEPS:
 * 1. Start with base case: "1"
 * 2. For each iteration from 2 to n:
 *    a. Initialize result string
 *    b. Count consecutive identical characters
 *    c. Append count + character to result
 *    d. Move to next group of characters
 * 3. Return the final string
 * 
 * VISUAL EXAMPLE:
 * Computing n=5:
 * 
 * n=1: "1"
 * 
 * n=2: Read "1"
 *      One '1' → "11"
 * 
 * n=3: Read "11"
 *      Two '1's → "21"
 * 
 * n=4: Read "21"
 *      One '2', One '1' → "1211"
 * 
 * n=5: Read "1211"
 *      One '1', One '2', Two '1's → "111221"
 * 
 * Detailed for n=5:
 * Input: "1211"
 * Position 0: '1' appears 1 time → "11"
 * Position 1: '2' appears 1 time → "12"
 * Position 2: '1' appears 2 times → "21"
 * Result: "11" + "12" + "21" = "111221"
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Iterative with String Builder: O(2^n) time, O(2^n) space - OPTIMAL
 * 2. Recursive: O(2^n) time, O(2^n) space - ELEGANT but stack overhead
 * 3. Memoization: O(2^n) time, O(2^n) space - No benefit since each n is computed once
 * 
 * Note: Time complexity is exponential because string length roughly doubles 
 * with each iteration.
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(2^n * m)
 * - We iterate n times
 * - Each iteration processes the previous string
 * - String length approximately doubles each time
 * - So we process: 1 + 2 + 4 + 8 + ... + 2^(n-1) ≈ O(2^n)
 * - m is average length of each string
 * - Total: O(2^n * m) ≈ O(2^n) since m is proportional to 2^n
 * 
 * More precisely:
 * - Iteration 1: Process 1 char
 * - Iteration 2: Process 2 chars
 * - Iteration 3: Process 4 chars
 * - ...
 * - Iteration n: Process ~2^(n-1) chars
 * - Total: 1 + 2 + 4 + ... + 2^(n-1) = 2^n - 1 ≈ O(2^n)
 * 
 * SPACE COMPLEXITY: O(2^n)
 * - Final string length grows exponentially
 * - At iteration n, string length is approximately 2^(n-1)
 * - StringBuilder uses O(current string length)
 * - Total space: O(2^n)
 * 
 * WHY THIS IS OPTIMAL:
 * - Must generate all intermediate sequences
 * - String length grows exponentially
 * - Cannot avoid processing each character
 * - Iterative approach avoids recursion overhead
 * 
 * ============================================================================
 */

package strings.medium

class CountAndSay {
    
    /**
     * Generates the nth count-and-say sequence
     * 
     * @param n The position in the sequence (1-indexed)
     * @return The nth count-and-say string
     */
    fun countAndSay(n: Int): String {
        // Base case: first element is always "1"
        if (n == 1) return "1"
        
        // Start with the base case
        var current = "1"
        
        // Build each sequence from 2 to n
        for (i in 2..n) {
            current = generateNext(current)
        }
        
        return current
    }
    
    /**
     * Generates the next count-and-say sequence from current string
     * Uses run-length encoding
     * 
     * @param current The current sequence
     * @return The next sequence based on current
     */
    private fun generateNext(current: String): String {
        val result = StringBuilder()
        
        // Pointer to track position in string
        var i = 0
        
        while (i < current.length) {
            // Current digit we're counting
            val digit = current[i]
            
            // Count consecutive occurrences of this digit
            var count = 1
            
            // Continue counting while we see the same digit
            while (i + count < current.length && current[i + count] == digit) {
                count++
            }
            
            // Append count followed by digit
            // Format: count + digit (e.g., "21" for two 1s)
            result.append(count)
            result.append(digit)
            
            // Move pointer past the counted digits
            i += count
        }
        
        return result.toString()
    }
    
    /**
     * Alternative recursive approach
     * More elegant but uses call stack
     */
    fun countAndSayRecursive(n: Int): String {
        // Base case
        if (n == 1) return "1"
        
        // Recursively get previous sequence
        val previous = countAndSayRecursive(n - 1)
        
        // Generate next sequence from previous
        return generateNext(previous)
    }
    
    /**
     * Helper function to visualize the sequence generation
     * Shows how each term is derived from the previous one
     */
    fun visualizeSequence(n: Int): List<String> {
        val sequence = mutableListOf<String>()
        var current = "1"
        sequence.add(current)
        
        for (i in 2..n) {
            current = generateNext(current)
            sequence.add(current)
        }
        
        return sequence
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: n = 4
 * 
 * STEP-BY-STEP EXECUTION:
 * 
 * Iteration 1 (i=1):
 * current = "1"
 * 
 * Iteration 2 (i=2):
 * generateNext("1"):
 *   - Position 0: digit='1', count=1
 *   - Append "11"
 * current = "11"
 * 
 * Iteration 3 (i=3):
 * generateNext("11"):
 *   - Position 0: digit='1', count=2 (both '1's)
 *   - Append "21"
 * current = "21"
 * 
 * Iteration 4 (i=4):
 * generateNext("21"):
 *   - Position 0: digit='2', count=1
 *   - Append "12"
 *   - Position 1: digit='1', count=1
 *   - Append "11"
 * current = "1211"
 * 
 * OUTPUT: "1211"
 * 
 * ---
 * 
 * DETAILED TRACE of generateNext("1211"):
 * 
 * Initial: result = "", i = 0
 * 
 * Step 1: i=0, digit='1'
 *   - Count consecutive '1's starting at position 0
 *   - current[0] = '1' ✓
 *   - current[1] = '2' ✗ (different)
 *   - count = 1
 *   - Append "1" + "1" = "11"
 *   - result = "11"
 *   - i = 0 + 1 = 1
 * 
 * Step 2: i=1, digit='2'
 *   - Count consecutive '2's starting at position 1
 *   - current[1] = '2' ✓
 *   - current[2] = '1' ✗ (different)
 *   - count = 1
 *   - Append "1" + "2" = "12"
 *   - result = "1112"
 *   - i = 1 + 1 = 2
 * 
 * Step 3: i=2, digit='1'
 *   - Count consecutive '1's starting at position 2
 *   - current[2] = '1' ✓
 *   - current[3] = '1' ✓
 *   - current[4] = out of bounds
 *   - count = 2
 *   - Append "2" + "1" = "21"
 *   - result = "111221"
 *   - i = 2 + 2 = 4
 * 
 * Step 4: i=4, exit loop (i >= length)
 * 
 * OUTPUT: "111221"
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. n = 1: Base case, return "1"
 * 2. n = 2: Simple case, "11"
 * 3. Large n (up to 30): Exponential growth handled
 * 4. Consecutive identical digits: Counted correctly
 * 5. Alternating digits: Each counted separately
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Confusing count and digit order
 * ❌ Appending digit + count (e.g., "12" for one 2)
 * ✅ Appending count + digit (e.g., "21" for two 1s)
 * 
 * MISTAKE 2: Not handling consecutive digits
 * ❌ Processing each digit individually
 * ✅ Grouping consecutive identical digits
 * 
 * MISTAKE 3: Off-by-one errors in counting
 * ❌ Starting count at 0 or not including current digit
 * ✅ Starting count at 1 (current digit) then counting more
 * 
 * MISTAKE 4: Inefficient string concatenation
 * ❌ Using + operator repeatedly (creates many strings)
 * ✅ Using StringBuilder for efficiency
 * 
 * MISTAKE 5: Not moving pointer correctly
 * ❌ i++ after each digit (misses grouped digits)
 * ✅ i += count (jumps past all counted digits)
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Data Compression**: Run-length encoding for image/video compression
 * 2. **Pattern Recognition**: Analyzing sequences in bioinformatics
 * 3. **Game Development**: Procedural generation using sequences
 * 4. **Audio Processing**: Encoding repetitive waveforms
 * 5. **Network Protocols**: Efficient data transmission
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * - Look and Say Sequence (mathematical sequence)
 * - Run-Length Encoding (data compression)
 * - String Compression (basic)
 * - Decode String (reverse process)
 * - Count Binary Substrings
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = CountAndSay()
    
    println("=== Testing Count and Say Sequence ===\n")
    
    // Test 1: Base case
    println("Test 1: Base case (n=1)")
    println("Input: n = 1")
    println("Output: ${solution.countAndSay(1)}")
    println("Expected: 1\n")
    
    // Test 2: Second element
    println("Test 2: Second element (n=2)")
    println("Input: n = 2")
    println("Output: ${solution.countAndSay(2)}")
    println("Expected: 11\n")
    
    // Test 3: Third element
    println("Test 3: Third element (n=3)")
    println("Input: n = 3")
    println("Output: ${solution.countAndSay(3)}")
    println("Expected: 21\n")
    
    // Test 4: Fourth element
    println("Test 4: Fourth element (n=4)")
    println("Input: n = 4")
    println("Output: ${solution.countAndSay(4)}")
    println("Expected: 1211\n")
    
    // Test 5: Fifth element
    println("Test 5: Fifth element (n=5)")
    println("Input: n = 5")
    println("Output: ${solution.countAndSay(5)}")
    println("Expected: 111221\n")
    
    // Test 6: Larger value
    println("Test 6: Larger value (n=8)")
    println("Input: n = 8")
    println("Output: ${solution.countAndSay(8)}")
    println("Expected: 1113213211\n")
    
    // Test 7: Recursive approach
    println("Test 7: Using recursive approach (n=4)")
    println("Input: n = 4")
    println("Output: ${solution.countAndSayRecursive(4)}")
    println("Expected: 1211\n")
    
    // Test 8: Visualize sequence
    println("Test 8: Visualize first 7 terms")
    val sequence = solution.visualizeSequence(7)
    sequence.forEachIndexed { index, term ->
        println("n=${index + 1}: $term")
    }
    println()
    
    // Test 9: Edge case - maximum constraint
    println("Test 9: Near maximum constraint (n=10)")
    println("Input: n = 10")
    val result = solution.countAndSay(10)
    println("Output: $result")
    println("Length: ${result.length}\n")
}
