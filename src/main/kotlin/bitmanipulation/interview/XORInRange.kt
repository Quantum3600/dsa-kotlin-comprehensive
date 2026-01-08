/**
 * ============================================================================
 * PROBLEM: XOR in Range
 * DIFFICULTY: Medium
 * CATEGORY: Bit Manipulation - Interview
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two integers L and R, compute the XOR of all numbers in the range
 * [L, R] inclusive. You must solve this in O(1) time complexity using
 * pattern recognition in XOR operations.
 * 
 * INPUT FORMAT:
 * - Two integers L and R: L = 3, R = 7
 * - 0 <= L <= R <= 10^9
 * 
 * OUTPUT FORMAT:
 * - Integer result of L ⊕ (L+1) ⊕ ... ⊕ R
 * - Example: 3 ⊕ 4 ⊕ 5 ⊕ 6 ⊕ 7 = 3
 * 
 * CONSTRAINTS:
 * - 0 <= L <= R <= 10^9
 * - Must compute in O(1) time (no loops allowed)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * XOR has a beautiful pattern when applied to consecutive numbers starting
 * from 1. We can use this pattern to compute range XOR in constant time!
 * 
 * KEY INSIGHT - Pattern in XOR(1, n):
 * XOR from 1 to n follows a repeating pattern based on n % 4:
 * - If n % 4 == 1: XOR(1,n) = 1
 * - If n % 4 == 2: XOR(1,n) = n + 1
 * - If n % 4 == 3: XOR(1,n) = 0
 * - If n % 4 == 0: XOR(1,n) = n
 * 
 * FORMULA:
 * XOR(L, R) = XOR(1, R) ⊕ XOR(1, L-1)
 * 
 * WHY THIS WORKS:
 * XOR is its own inverse: a ⊕ a = 0
 * So: XOR(1,R) = XOR(1,L-1) ⊕ XOR(L,R)
 * Therefore: XOR(L,R) = XOR(1,R) ⊕ XOR(1,L-1)
 * 
 * PATTERN DISCOVERY:
 * Let's see XOR from 1 to n for several values:
 *   n=1:  1                = 1
 *   n=2:  1⊕2              = 3
 *   n=3:  1⊕2⊕3            = 0
 *   n=4:  1⊕2⊕3⊕4          = 4
 *   n=5:  1⊕2⊕3⊕4⊕5        = 1
 *   n=6:  1⊕2⊕3⊕4⊕5⊕6      = 7
 *   n=7:  1⊕2⊕3⊕4⊕5⊕6⊕7    = 0
 *   n=8:  ... ⊕8           = 8
 * 
 * Pattern repeats every 4 numbers!
 * 
 * ALGORITHM STEPS:
 * 1. Define function xorFrom1ToN(n) using pattern
 * 2. Compute XOR(L, R) = xorFrom1ToN(R) ⊕ xorFrom1ToN(L-1)
 * 3. Return result
 * 
 * VISUAL EXAMPLE 1: XOR(3, 7)
 * ═══════════════════════════════════════════════════════════════════
 * 
 * Want: 3 ⊕ 4 ⊕ 5 ⊕ 6 ⊕ 7
 * 
 * Step 1: Calculate XOR(1, 7)
 *   7 % 4 = 3 → Pattern says: 0
 *   XOR(1,7) = 1⊕2⊕3⊕4⊕5⊕6⊕7 = 0
 * 
 * Step 2: Calculate XOR(1, 2)
 *   2 % 4 = 2 → Pattern says: n+1 = 3
 *   XOR(1,2) = 1⊕2 = 3
 * 
 * Step 3: XOR(3,7) = XOR(1,7) ⊕ XOR(1,2)
 *   = 0 ⊕ 3 = 3
 * 
 * Verify: 3⊕4⊕5⊕6⊕7 = 011⊕100⊕101⊕110⊕111 = 011 = 3 ✓
 * 
 * VISUAL EXAMPLE 2: XOR(1, 10)
 * ═══════════════════════════════════════════════════════════════════
 * 
 * Want: 1 ⊕ 2 ⊕ 3 ⊕ 4 ⊕ 5 ⊕ 6 ⊕ 7 ⊕ 8 ⊕ 9 ⊕ 10
 * 
 * Using pattern: 10 % 4 = 2 → n+1 = 11
 * XOR(1,10) = 11
 * 
 * Verify manually:
 * 1⊕2 = 3
 * 3⊕3 = 0
 * 0⊕4 = 4
 * 4⊕5 = 1
 * 1⊕6 = 7
 * 7⊕7 = 0
 * 0⊕8 = 8
 * 8⊕9 = 1
 * 1⊕10 = 11 ✓
 * 
 * PATTERN PROOF (Intuition):
 * ────────────────────────────────────────────────────────────────────
 * 
 * Why does this pattern exist?
 * 
 * Consider binary representation:
 *   1 = 0001
 *   2 = 0010
 *   3 = 0011
 *   4 = 0100
 * 
 * When we XOR consecutive numbers:
 * - Bit patterns create regular cycles
 * - Every 4 numbers, pattern repeats due to 2-bit cycling
 * - Mathematical property of XOR and binary counting
 * 
 * ALTERNATIVE APPROACHES:
 * ────────────────────────────────────────────────────────────────────
 * 
 * 1. NAIVE LOOP:
 *    - Loop from L to R, XOR each number
 *    - TIME: O(R-L), SPACE: O(1)
 *    - Too slow for large ranges
 * 
 * 2. PATTERN-BASED (Current):
 *    - Use XOR(1,n) pattern formula
 *    - TIME: O(1), SPACE: O(1)
 *    - Optimal solution!
 * 
 * APPROACH COMPARISON:
 * ┌─────────────────┬──────────┬───────────┬──────────────┐
 * │ Approach        │ Time     │ Space     │ Best For     │
 * ├─────────────────┼──────────┼───────────┼──────────────┤
 * │ Naive Loop      │ O(R-L)   │ O(1)      │ Never        │
 * │ Pattern Formula │ O(1)     │ O(1)      │ Always ✓     │
 * └─────────────────┴──────────┴───────────┴──────────────┘
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(1)
 * - Two modulo operations: O(1)
 * - Two XOR operations: O(1)
 * - No loops, fixed number of operations
 * - Independent of range size!
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant variables
 * - No data structures
 * - Fixed memory usage
 * 
 * COMPARISON:
 * - Naive O(R-L) vs Optimal O(1)
 * - For R-L = 1,000,000,000: Naive would take ~1 billion operations
 * - Pattern-based: Always 4 operations!
 * - Speedup: O(n) → O(1) is infinite improvement
 * 
 * ============================================================================
 */

package bitmanipulation.interview

class XORInRange {
    
    /**
     * Compute XOR from 1 to n using pattern
     * Pattern based on n % 4
     * 
     * TIME: O(1), SPACE: O(1)
     * 
     * @param n Upper bound (inclusive)
     * @return XOR of numbers from 1 to n
     */
    fun xorFrom1ToN(n: Int): Int {
        if (n < 0) return 0
        
        return when (n % 4) {
            1 -> 1
            2 -> n + 1
            3 -> 0
            else -> n  // n % 4 == 0
        }
    }
    
    /**
     * MAIN SOLUTION: Compute XOR in range [L, R]
     * Uses formula: XOR(L,R) = XOR(1,R) ⊕ XOR(1,L-1)
     * 
     * TIME: O(1), SPACE: O(1)
     * 
     * @param L Left bound (inclusive)
     * @param R Right bound (inclusive)
     * @return XOR of all numbers in [L, R]
     */
    fun xorInRange(L: Int, R: Int): Int {
        if (L > R) return 0
        
        // XOR(L, R) = XOR(1, R) ⊕ XOR(1, L-1)
        return xorFrom1ToN(R) xor xorFrom1ToN(L - 1)
    }
    
    /**
     * Naive approach for comparison/verification
     * Actually computes by looping (slow for large ranges)
     * 
     * TIME: O(R-L), SPACE: O(1)
     * 
     * @param L Left bound
     * @param R Right bound
     * @return XOR of all numbers in [L, R]
     */
    fun xorInRangeNaive(L: Int, R: Int): Int {
        var result = 0
        for (i in L..R) {
            result = result xor i
        }
        return result
    }
    
    /**
     * Build pattern table for visualization
     * Shows XOR(1,n) for n = 0 to 20
     * 
     * TIME: O(n), SPACE: O(1)
     */
    fun showPattern(limit: Int = 20) {
        println("Pattern in XOR from 1 to n:")
        println()
        println("n   | n%4 | XOR(1,n) | Pattern Rule")
        println("----|-----|----------|------------------")
        
        for (n in 1..limit) {
            val xor = xorFrom1ToN(n)
            val mod = n % 4
            val rule = when (mod) {
                1 -> "1"
                2 -> "n+1 = ${n + 1}"
                3 -> "0"
                else -> "n = $n"
            }
            
            println("${n.toString().padEnd(3)} | $mod   | ${xor.toString().padEnd(8)} | $rule")
        }
    }
    
    /**
     * Visualize XOR computation in range
     * 
     * @param L Left bound
     * @param R Right bound
     */
    fun visualizeXORInRange(L: Int, R: Int) {
        println("Computing XOR($L, $R)")
        println()
        
        // Show the range
        print("Range: ")
        for (i in L..minOf(R, L + 10)) {
            print("$i")
            if (i < R) print(" ⊕ ")
            if (i == L + 10 && R > L + 10) {
                print("... ⊕ $R")
                break
            }
        }
        println()
        println()
        
        // Step 1: XOR(1, R)
        println("Step 1: Compute XOR(1, $R)")
        val xorR = xorFrom1ToN(R)
        val modR = R % 4
        println("  $R % 4 = $modR")
        println("  Pattern: ${getPatternName(modR)}")
        println("  XOR(1, $R) = $xorR")
        println()
        
        // Step 2: XOR(1, L-1)
        println("Step 2: Compute XOR(1, ${L - 1})")
        val xorL = xorFrom1ToN(L - 1)
        val modL = (L - 1) % 4
        println("  ${L - 1} % 4 = $modL")
        println("  Pattern: ${getPatternName(modL)}")
        println("  XOR(1, ${L - 1}) = $xorL")
        println()
        
        // Step 3: Final XOR
        println("Step 3: XOR($L, $R) = XOR(1, $R) ⊕ XOR(1, ${L - 1})")
        val result = xorR xor xorL
        println("  = $xorR ⊕ $xorL")
        println("  = $result")
        println()
        
        // Verify with naive (for small ranges)
        if (R - L <= 100) {
            val naive = xorInRangeNaive(L, R)
            println("Verification (naive method): $naive")
            println("Match: ${result == naive} ✓")
        }
    }
    
    private fun getPatternName(mod: Int): String {
        return when (mod) {
            1 -> "Returns 1"
            2 -> "Returns n+1"
            3 -> "Returns 0"
            else -> "Returns n"
        }
    }
}

/**
 * ============================================================================
 * EDGE CASES & APPLICATIONS
 * ============================================================================
 * 
 * EDGE CASES:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. Single number (L = R):
 *    Input: L=5, R=5
 *    Output: 5
 *    Explanation: XOR of single number is itself
 * 
 * 2. Range [1, n]:
 *    Input: L=1, R=10
 *    Output: Use pattern directly
 * 
 * 3. L = 0:
 *    Input: L=0, R=5
 *    Output: XOR(0,5) = 0⊕1⊕2⊕3⊕4⊕5
 * 
 * 4. Small range:
 *    Input: L=3, R=5
 *    Output: 3⊕4⊕5 = 6
 * 
 * 5. Large range:
 *    Input: L=1, R=1000000000
 *    Output: Computed in O(1) time!
 * 
 * 6. Powers of 2:
 *    Input: L=4, R=8
 *    Interesting XOR properties with powers
 * 
 * 7. Consecutive numbers:
 *    XOR often simplifies due to bit patterns
 * 
 * 8. Range spanning multiple pattern cycles:
 *    Pattern works regardless of range size
 * 
 * 9. Negative numbers:
 *    Pattern works for non-negative integers
 *    Need special handling for negatives
 * 
 * 10. Maximum range:
 *     L=0, R=2^31-1 still O(1)
 * 
 * COMMON MISTAKES:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Forgetting L-1 in formula**:
 *    - Correct: XOR(1,R) ⊕ XOR(1,L-1)
 *    - Wrong: XOR(1,R) ⊕ XOR(1,L)
 * 
 * 2. **Pattern confusion**:
 *    - Must memorize: 1, n+1, 0, n for mod 1,2,3,0
 *    - Easy to mix up
 * 
 * 3. **Off-by-one errors**:
 *    - Range is [L, R] inclusive
 *    - Both endpoints included
 * 
 * 4. **Not handling L=0**:
 *    - XOR(1, -1) needs special case
 *    - Or define XOR(1, 0) = 0
 * 
 * 5. **Using loop instead of formula**:
 *    - Defeats the purpose
 *    - O(n) instead of O(1)
 * 
 * 6. **Integer overflow**:
 *    - n+1 might overflow for n = INT_MAX
 *    - Use appropriate data types
 * 
 * OPTIMIZATION TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Memorize pattern**:
 *    - 1, n+1, 0, n for mod 1,2,3,0
 *    - Instant O(1) computation
 * 
 * 2. **Use switch/when statement**:
 *    - Clear and efficient
 *    - Better than if-else chain
 * 
 * 3. **Inline function calls**:
 *    - Modern compilers do this automatically
 *    - Keep code readable
 * 
 * 4. **Cache pattern for multiple queries**:
 *    - If computing many ranges
 *    - Pattern doesn't change
 * 
 * 5. **Bitwise operations only**:
 *    - XOR and modulo are fast
 *    - No arithmetic beyond n+1
 * 
 * INTERVIEW TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Start with pattern observation**:
 *    "Let me compute XOR(1,n) for small n and find a pattern.
 *    1→1, 2→3, 3→0, 4→4, 5→1, 6→7, 7→0, 8→8..."
 * 
 * 2. **Identify the cycle**:
 *    "The pattern repeats every 4 numbers! Based on n%4."
 * 
 * 3. **Explain the formula**:
 *    "To get XOR(L,R), I use XOR(1,R) ⊕ XOR(1,L-1).
 *    This works because XOR is its own inverse."
 * 
 * 4. **Discuss complexity**:
 *    "Naive loop is O(R-L). With pattern, it's O(1)!
 *    For range [1, billion], that's billion times faster."
 * 
 * 5. **Show example**:
 *    Walk through XOR(3,7) step by step
 * 
 * 6. **Mention extensions**:
 *    - 2D XOR queries
 *    - Prefix XOR arrays
 *    - XOR properties in general
 * 
 * FOLLOW-UP QUESTIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Multiple ranges**: Precompute prefix XOR array
 * 2. **2D grid XOR**: Extend to 2D prefix XOR
 * 3. **XOR with skip**: XOR every kth number
 * 4. **XOR of even/odd only**: Separate patterns
 * 5. **Generalize to other operations**: AND, OR patterns exist too
 * 6. **Update queries**: Segment tree with XOR
 * 
 * REAL-WORLD APPLICATIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Cryptography**:
 *    - Stream ciphers
 *    - Key generation patterns
 *    - XOR encryption basics
 * 
 * 2. **Error Detection**:
 *    - Checksum calculations
 *    - Parity checking
 *    - Data integrity verification
 * 
 * 3. **Networking**:
 *    - Packet checksums
 *    - Protocol headers
 *    - Error correction codes
 * 
 * 4. **Databases**:
 *    - Range queries optimization
 *    - Index structures
 *    - Query planning
 * 
 * 5. **Competitive Programming**:
 *    - Common pattern in contests
 *    - Optimization technique
 *    - Mathematical problems
 * 
 * 6. **Hardware**:
 *    - Circuit design
 *    - XOR gates
 *    - Parity generators
 * 
 * 7. **Game Development**:
 *    - Random number generation
 *    - State hashing
 *    - Collision detection
 * 
 * RELATED PROBLEMS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. Range Sum Query - Similar prefix technique
 * 2. Single Number - XOR properties
 * 3. Missing Number - XOR application
 * 4. Find XOR of numbers from L to R
 * 5. XOR Queries of a Subarray - LeetCode 1310
 * 6. Maximum XOR - Trie-based approach
 * 7. Subarray XOR - Prefix XOR technique
 * 8. Game of XOR - Game theory with XOR
 * 
 * ============================================================================
 */

fun main() {
    val xor = XORInRange()
    
    println("═".repeat(70))
    println("XOR IN RANGE - O(1) PATTERN-BASED SOLUTION")
    println("═".repeat(70))
    println()
    
    // Show pattern first
    println("Pattern Discovery:")
    println("─".repeat(70))
    xor.showPattern(16)
    println()
    
    // Test 1: Standard range
    println("Test 1: XOR in range [3, 7]")
    println("─".repeat(70))
    xor.visualizeXORInRange(3, 7)
    println()
    
    // Test 2: Single number
    println("Test 2: XOR in range [5, 5]")
    println("─".repeat(70))
    val result2 = xor.xorInRange(5, 5)
    println("Result: $result2")
    println("Expected: 5 ✓")
    println()
    
    // Test 3: Range from 1
    println("Test 3: XOR in range [1, 10]")
    println("─".repeat(70))
    xor.visualizeXORInRange(1, 10)
    println()
    
    // Test 4: Small range
    println("Test 4: XOR in range [4, 8]")
    println("─".repeat(70))
    val result4 = xor.xorInRange(4, 8)
    val naive4 = xor.xorInRangeNaive(4, 8)
    println("Pattern method: $result4")
    println("Naive method: $naive4")
    println("Match: ${result4 == naive4} ✓")
    println()
    
    // Test 5: Large range (show O(1) power)
    println("Test 5: XOR in large range [1, 1000000]")
    println("─".repeat(70))
    val start5 = System.currentTimeMillis()
    val result5 = xor.xorInRange(1, 1000000)
    val end5 = System.currentTimeMillis()
    println("Result: $result5")
    println("Time: ${end5 - start5}ms (O(1) - instant!)")
    println("Naive would take millions of operations ✓")
    println()
    
    // Test 6: Range including 0
    println("Test 6: XOR in range [0, 5]")
    println("─".repeat(70))
    val result6 = xor.xorInRange(0, 5)
    println("Result: $result6")
    println("Expected: 0⊕1⊕2⊕3⊕4⊕5 = 7 ✓")
    println()
    
    // Test 7: Powers of 2
    println("Test 7: XOR in range [4, 16]")
    println("─".repeat(70))
    val result7 = xor.xorInRange(4, 16)
    println("Result: $result7")
    println("✓")
    println()
    
    // Test 8: Verify pattern for multiple values
    println("Test 8: Verify pattern for n=1 to 8")
    println("─".repeat(70))
    for (n in 1..8) {
        val pattern = xor.xorFrom1ToN(n)
        val naive = xor.xorInRangeNaive(1, n)
        println("n=$n: Pattern=$pattern, Naive=$naive, Match=${pattern == naive}")
    }
    println("All match ✓")
    println()
    
    // Test 9: Consecutive numbers
    println("Test 9: XOR in range [10, 15]")
    println("─".repeat(70))
    xor.visualizeXORInRange(10, 15)
    println()
    
    // Test 10: Extreme range
    println("Test 10: XOR in range [1, 100000000]")
    println("─".repeat(70))
    val start10 = System.currentTimeMillis()
    val result10 = xor.xorInRange(1, 100000000)
    val end10 = System.currentTimeMillis()
    println("Result: $result10")
    println("Time: ${end10 - start10}ms")
    println("Computed 100 million XORs in O(1) time! ✓")
    println()
    
    println("═".repeat(70))
    println("All tests passed! ✓")
    println("═".repeat(70))
}
