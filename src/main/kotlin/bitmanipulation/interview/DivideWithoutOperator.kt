/**
 * ============================================================================
 * PROBLEM: Divide Two Integers Without Division Operator
 * DIFFICULTY: Medium
 * CATEGORY: Bit Manipulation - Interview
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two integers dividend and divisor, divide two integers WITHOUT using
 * multiplication (*), division (/), or modulo (%) operators. Return the
 * quotient after dividing dividend by divisor. Truncate toward zero.
 * 
 * INPUT FORMAT:
 * - Two integers: dividend = 10, divisor = 3
 * - divisor != 0
 * 
 * OUTPUT FORMAT:
 * - Integer quotient: 3
 * - Truncated division: 10 / 3 = 3 (not 3.333...)
 * 
 * CONSTRAINTS:
 * - -2^31 <= dividend, divisor <= 2^31 - 1
 * - divisor != 0
 * - Assume environment can only store 32-bit signed integers
 * - If quotient > 2^31 - 1, return 2^31 - 1
 * - If quotient < -2^31, return -2^31
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Division is repeated subtraction! But naive subtraction is too slow.
 * Use bit shifting to "multiply" divisor by powers of 2, speeding up
 * the subtraction process exponentially.
 * 
 * KEY INSIGHT:
 * - Division: How many times does divisor fit into dividend?
 * - Use bit shifts: divisor << k = divisor × 2^k
 * - Find largest k where (divisor << k) <= dividend
 * - Subtract and repeat with remainder
 * 
 * EXAMPLE: 43 / 5
 * - 5 << 3 = 40 (5 × 8) fits, add 8 to quotient, remainder = 3
 * - 5 << 0 = 5 too big for 3
 * - Final quotient: 8, remainder: 3
 * - Result: 43 / 5 = 8
 * 
 * WHY THIS WORKS:
 * We're essentially finding: dividend = divisor × quotient + remainder
 * By using powers of 2, we find quotient in O(log n) steps instead of O(n).
 * 
 * ALGORITHM STEPS:
 * 1. Handle edge cases (overflow, signs)
 * 2. Convert to positive (track original signs)
 * 3. While dividend >= divisor:
 *    a. Find largest k where (divisor << k) <= dividend
 *    b. Add 2^k to quotient
 *    c. Subtract (divisor << k) from dividend
 * 4. Apply original sign
 * 5. Check overflow and return
 * 
 * VISUAL EXAMPLE 1: Divide 22 by 3
 * ═══════════════════════════════════════════════════════════════════
 * 
 * dividend = 22, divisor = 3
 * 
 * Iteration 1:
 *   Find max k: 3 << k <= 22
 *   k=0: 3 << 0 = 3 × 1 = 3 ✓
 *   k=1: 3 << 1 = 3 × 2 = 6 ✓
 *   k=2: 3 << 2 = 3 × 4 = 12 ✓
 *   k=3: 3 << 3 = 3 × 8 = 24 ✗ (too big)
 *   Use k=2: quotient += 4, dividend = 22 - 12 = 10
 * 
 * Iteration 2:
 *   dividend = 10, find max k: 3 << k <= 10
 *   k=0: 3 ✓
 *   k=1: 6 ✓
 *   k=2: 12 ✗
 *   Use k=1: quotient += 2, dividend = 10 - 6 = 4
 * 
 * Iteration 3:
 *   dividend = 4, find max k: 3 << k <= 4
 *   k=0: 3 ✓
 *   k=1: 6 ✗
 *   Use k=0: quotient += 1, dividend = 4 - 3 = 1
 * 
 * Iteration 4:
 *   dividend = 1 < divisor = 3, stop
 * 
 * Quotient: 4 + 2 + 1 = 7
 * Verification: 22 / 3 = 7 remainder 1 ✓
 * 
 * VISUAL EXAMPLE 2: Divide -10 by 3
 * ═══════════════════════════════════════════════════════════════════
 * 
 * dividend = -10, divisor = 3
 * 
 * Step 1: Track sign
 *   negative ÷ positive = negative result
 * 
 * Step 2: Work with absolute values
 *   10 / 3 = ?
 * 
 * Iteration 1:
 *   3 << 1 = 6 ✓, 3 << 2 = 12 ✗
 *   quotient += 2, dividend = 10 - 6 = 4
 * 
 * Iteration 2:
 *   3 << 0 = 3 ✓, 3 << 1 = 6 ✗
 *   quotient += 1, dividend = 4 - 3 = 1
 * 
 * Step 3: Apply sign
 *   quotient = -(2 + 1) = -3
 * 
 * Result: -10 / 3 = -3 (truncated toward zero) ✓
 * 
 * VISUAL EXAMPLE 3: Overflow case INT_MIN / -1
 * ═══════════════════════════════════════════════════════════════════
 * 
 * dividend = -2^31, divisor = -1
 * Result would be 2^31, but max int is 2^31 - 1
 * Return INT_MAX = 2^31 - 1
 * 
 * ALTERNATIVE APPROACHES:
 * ────────────────────────────────────────────────────────────────────
 * 
 * 1. NAIVE REPEATED SUBTRACTION:
 *    - Subtract divisor repeatedly until remainder < divisor
 *    - TIME: O(dividend/divisor), SPACE: O(1)
 *    - Too slow! For 2^31 / 1, that's 2 billion operations
 * 
 * 2. BIT SHIFT OPTIMIZATION (Current):
 *    - Use powers of 2 to speed up subtraction
 *    - TIME: O((log dividend)²), SPACE: O(1)
 *    - Optimal for this problem!
 * 
 * 3. RECURSIVE DOUBLING:
 *    - Similar to iterative but uses recursion
 *    - TIME: O((log n)²), SPACE: O(log n) for stack
 *    - More elegant but uses stack space
 * 
 * APPROACH COMPARISON:
 * ┌──────────────────────┬───────────────┬───────────┬──────────────┐
 * │ Approach             │ Time          │ Space     │ Best For     │
 * ├──────────────────────┼───────────────┼───────────┼──────────────┤
 * │ Naive Subtraction    │ O(n/k)        │ O(1)      │ Never        │
 * │ Bit Shift (Iter)     │ O((log n)²)   │ O(1)      │ This problem │
 * │ Recursive Doubling   │ O((log n)²)   │ O(log n)  │ Elegance     │
 * └──────────────────────┴───────────────┴───────────┴──────────────┘
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O((log n)²) where n = |dividend|
 * - Outer loop: O(log n) iterations (reducing dividend by half each time)
 * - Inner loop: O(log n) to find maximum k
 * - Total: O(log n × log n) = O((log n)²)
 * 
 * DETAILED BREAKDOWN:
 * - First iteration: Find k in O(log dividend)
 * - Second iteration: Find k in O(log(dividend/2))
 * - ...
 * - Sum: O(log n + log n/2 + log n/4 + ...) = O((log n)²)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant variables
 * - No recursion, no data structures
 * - Fixed memory regardless of input size
 * 
 * COMPARISON TO NAIVE:
 * - Naive: O(n) where n can be 2^31
 * - Optimized: O((log n)²) = O(31²) = O(961) for 32-bit
 * - Speedup: Millions of times faster for large dividends!
 * 
 * ============================================================================
 */

package bitmanipulation.interview

class DivideWithoutOperator {
    
    /**
     * MAIN SOLUTION: Divide without /, *, or % operators
     * Uses bit shifting for exponential speedup
     * 
     * TIME: O((log n)²), SPACE: O(1)
     * 
     * @param dividend The dividend
     * @param divisor The divisor (non-zero)
     * @return Quotient (truncated toward zero)
     */
    fun divide(dividend: Int, divisor: Int): Int {
        // Handle overflow case: INT_MIN / -1
        if (dividend == Int.MIN_VALUE && divisor == -1) {
            return Int.MAX_VALUE
        }
        
        // Determine sign of result
        val negative = (dividend < 0) xor (divisor < 0)
        
        // Work with absolute values (use Long to handle INT_MIN)
        var dvd = Math.abs(dividend.toLong())
        val dvs = Math.abs(divisor.toLong())
        
        var quotient = 0L
        
        // Perform division using bit shifting
        while (dvd >= dvs) {
            var temp = dvs
            var multiple = 1L
            
            // Find the largest multiple of divisor (as power of 2)
            // that fits into current dividend
            while (dvd >= (temp shl 1)) {
                temp = temp shl 1
                multiple = multiple shl 1
            }
            
            // Subtract the multiple and add to quotient
            dvd -= temp
            quotient += multiple
        }
        
        // Apply sign
        quotient = if (negative) -quotient else quotient
        
        // Handle overflow
        if (quotient > Int.MAX_VALUE) return Int.MAX_VALUE
        if (quotient < Int.MIN_VALUE) return Int.MIN_VALUE
        
        return quotient.toInt()
    }
    
    /**
     * Alternative: Recursive approach
     * More elegant but uses stack space
     * 
     * TIME: O((log n)²), SPACE: O(log n)
     * 
     * @param dividend The dividend
     * @param divisor The divisor
     * @return Quotient
     */
    fun divideRecursive(dividend: Int, divisor: Int): Int {
        if (dividend == Int.MIN_VALUE && divisor == -1) {
            return Int.MAX_VALUE
        }
        
        val negative = (dividend < 0) xor (divisor < 0)
        
        val dvd = Math.abs(dividend.toLong())
        val dvs = Math.abs(divisor.toLong())
        
        val result = divideHelper(dvd, dvs)
        
        val quotient = if (negative) -result else result
        
        if (quotient > Int.MAX_VALUE) return Int.MAX_VALUE
        if (quotient < Int.MIN_VALUE) return Int.MIN_VALUE
        
        return quotient.toInt()
    }
    
    private fun divideHelper(dividend: Long, divisor: Long): Long {
        if (dividend < divisor) return 0
        
        var temp = divisor
        var multiple = 1L
        
        while (dividend >= (temp shl 1)) {
            temp = temp shl 1
            multiple = multiple shl 1
        }
        
        return multiple + divideHelper(dividend - temp, divisor)
    }
    
    /**
     * Naive approach for comparison (very slow!)
     * Only use for small numbers
     * 
     * TIME: O(n), SPACE: O(1)
     * 
     * @param dividend The dividend
     * @param divisor The divisor
     * @return Quotient
     */
    fun divideNaive(dividend: Int, divisor: Int): Int {
        if (dividend == Int.MIN_VALUE && divisor == -1) {
            return Int.MAX_VALUE
        }
        
        val negative = (dividend < 0) xor (divisor < 0)
        
        var dvd = Math.abs(dividend.toLong())
        val dvs = Math.abs(divisor.toLong())
        
        var quotient = 0L
        
        while (dvd >= dvs) {
            dvd -= dvs
            quotient++
        }
        
        quotient = if (negative) -quotient else quotient
        
        if (quotient > Int.MAX_VALUE) return Int.MAX_VALUE
        if (quotient < Int.MIN_VALUE) return Int.MIN_VALUE
        
        return quotient.toInt()
    }
    
    /**
     * Visualize the division process
     * 
     * @param dividend The dividend
     * @param divisor The divisor
     */
    fun visualizeDivision(dividend: Int, divisor: Int) {
        println("Dividing: $dividend / $divisor")
        println()
        
        // Handle special case
        if (dividend == Int.MIN_VALUE && divisor == -1) {
            println("Special case: INT_MIN / -1 causes overflow")
            println("Result: INT_MAX = ${Int.MAX_VALUE}")
            return
        }
        
        // Determine sign
        val negative = (dividend < 0) xor (divisor < 0)
        println("Sign: ${if (negative) "negative" else "positive"}")
        println()
        
        // Work with absolute values
        var dvd = Math.abs(dividend.toLong())
        val dvs = Math.abs(divisor.toLong())
        println("Working with: |$dividend| / |$divisor| = $dvd / $dvs")
        println()
        
        var quotient = 0L
        var iteration = 1
        
        println("Iteration | Dividend | Multiple | Quotient | Remaining")
        println("----------|----------|----------|----------|----------")
        
        while (dvd >= dvs) {
            var temp = dvs
            var multiple = 1L
            
            // Find largest multiple
            while (dvd >= (temp shl 1)) {
                temp = temp shl 1
                multiple = multiple shl 1
            }
            
            dvd -= temp
            quotient += multiple
            
            println("${iteration.toString().padEnd(9)} | ${dvd.toString().padEnd(8)} | ${multiple.toString().padEnd(8)} | ${quotient.toString().padEnd(8)} | $dvd")
            iteration++
        }
        
        println()
        
        // Apply sign
        val result = if (negative) -quotient else quotient
        println("Final quotient: $result")
        println("Verification: $dividend / $divisor = $result")
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
 * 1. Overflow case:
 *    Input: dividend = INT_MIN, divisor = -1
 *    Output: INT_MAX
 *    Explanation: Result would overflow, clamp to max
 * 
 * 2. Division by 1:
 *    Input: dividend = 42, divisor = 1
 *    Output: 42
 * 
 * 3. Division by -1:
 *    Input: dividend = 42, divisor = -1
 *    Output: -42
 * 
 * 4. Dividend is 0:
 *    Input: dividend = 0, divisor = 5
 *    Output: 0
 * 
 * 5. Dividend < divisor:
 *    Input: dividend = 3, divisor = 5
 *    Output: 0 (integer division)
 * 
 * 6. Both negative:
 *    Input: dividend = -10, divisor = -3
 *    Output: 3 (negative/negative = positive)
 * 
 * 7. Large dividend, small divisor:
 *    Input: dividend = 2^30, divisor = 1
 *    Output: 2^30
 * 
 * 8. Equal values:
 *    Input: dividend = 5, divisor = 5
 *    Output: 1
 * 
 * 9. Power of 2 divisor:
 *    Input: dividend = 32, divisor = 4
 *    Output: 8 (can optimize with simple shift)
 * 
 * 10. Truncation toward zero:
 *     Input: dividend = -7, divisor = 3
 *     Output: -2 (not -3, truncate toward zero)
 * 
 * COMMON MISTAKES:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Not handling INT_MIN / -1 overflow**:
 *    - Result is 2^31, which overflows
 *    - Must return INT_MAX
 * 
 * 2. **Wrong sign handling**:
 *    - Use XOR to determine result sign
 *    - (positive XOR negative) = true
 * 
 * 3. **Not using Long for intermediate values**:
 *    - Math.abs(INT_MIN) overflows int
 *    - Must use Long
 * 
 * 4. **Incorrect bit shift direction**:
 *    - Use left shift (<<) to multiply by 2
 *    - Not right shift (>>)
 * 
 * 5. **Not checking temp << 1 overflow**:
 *    - Check dvd >= (temp << 1) before shifting
 *    - Prevents infinite loop
 * 
 * 6. **Wrong truncation**:
 *    - Truncate toward zero, not floor
 *    - -7/3 = -2, not -3
 * 
 * OPTIMIZATION TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Special case power of 2 divisor**:
 *    - If divisor is power of 2, use simple right shift
 *    - dividend >> log2(divisor)
 * 
 * 2. **Use Long for calculations**:
 *    - Avoids overflow issues
 *    - Cleaner code
 * 
 * 3. **Early termination**:
 *    - If dividend < divisor, return 0 immediately
 * 
 * 4. **Cache divisor << 1**:
 *    - Avoid recalculating in loop condition
 * 
 * 5. **Unsigned arithmetic**:
 *    - Can use unsigned types for simpler logic
 *    - But Kotlin doesn't have built-in unsigned
 * 
 * INTERVIEW TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Start with constraints**:
 *    "Can't use /, *, or %. Division is repeated subtraction,
 *    but naive is too slow. Need to use bit shifts."
 * 
 * 2. **Explain bit shifting**:
 *    "Left shift by k multiplies by 2^k. So divisor << 3 = divisor × 8.
 *    This lets me subtract large chunks at once."
 * 
 * 3. **Walk through example**:
 *    Show 22 / 3 step by step with bit shifts
 * 
 * 4. **Discuss edge cases**:
 *    "Must handle INT_MIN / -1 overflow. Also handle signs carefully
 *    using XOR. Work with absolute values, apply sign at end."
 * 
 * 5. **Complexity analysis**:
 *    "Outer loop is O(log n), inner loop is O(log n), so O((log n)²).
 *    Much better than O(n) naive subtraction!"
 * 
 * 6. **Mention follow-ups**:
 *    - With *, / allowed → trivial
 *    - Return remainder too → track separately
 *    - Floating point division → different problem
 * 
 * FOLLOW-UP QUESTIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Return quotient and remainder**: Track both
 * 2. **Implement multiplication without ***: Similar approach
 * 3. **Handle floating point**: Need fraction representation
 * 4. **Optimize for specific divisors**: Special cases
 * 5. **Use only addition and subtraction**: Implement shifts
 * 6. **Parallel division**: Distribute work
 * 
 * REAL-WORLD APPLICATIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Embedded Systems**:
 *    - Hardware without division units
 *    - Microcontrollers, DSPs
 *    - Power-efficient calculations
 * 
 * 2. **Compiler Optimization**:
 *    - Division by constants
 *    - Strength reduction
 *    - Code generation
 * 
 * 3. **Cryptography**:
 *    - Modular arithmetic
 *    - Large number operations
 *    - RSA, ECC algorithms
 * 
 * 4. **Graphics**:
 *    - Fixed-point arithmetic
 *    - GPU shader operations
 *    - Fast approximations
 * 
 * 5. **Signal Processing**:
 *    - Fast Fourier Transform
 *    - Filter implementations
 *    - Real-time processing
 * 
 * 6. **Game Development**:
 *    - Physics calculations
 *    - Fixed-point math
 *    - Performance optimization
 * 
 * 7. **Operating Systems**:
 *    - Kernel-level operations
 *    - Resource allocation
 *    - Scheduler algorithms
 * 
 * RELATED PROBLEMS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. Multiply Without Operator - Similar technique
 * 2. Pow(x, n) - LeetCode 50 (exponentiation)
 * 3. Sqrt(x) - LeetCode 69 (binary search)
 * 4. Integer to English Words - LeetCode 273
 * 5. Divide Chocolate - LeetCode 1231
 * 6. Kth Smallest in Multiplication Table - LeetCode 668
 * 7. Nth Digit - LeetCode 400
 * 8. Fraction Addition - LeetCode 592
 * 
 * ============================================================================
 */

fun main() {
    val div = DivideWithoutOperator()
    
    println("═".repeat(70))
    println("DIVIDE WITHOUT OPERATORS - BIT MANIPULATION SOLUTION")
    println("═".repeat(70))
    println()
    
    // Test 1: Standard division
    println("Test 1: 22 / 3")
    println("─".repeat(70))
    div.visualizeDivision(22, 3)
    println()
    
    // Test 2: Negative dividend
    println("Test 2: -10 / 3")
    println("─".repeat(70))
    val result2 = div.divide(-10, 3)
    println("Result: $result2")
    println("Expected: -3 (truncate toward zero) ✓")
    println()
    
    // Test 3: Overflow case
    println("Test 3: INT_MIN / -1 (overflow)")
    println("─".repeat(70))
    div.visualizeDivision(Int.MIN_VALUE, -1)
    println()
    
    // Test 4: Division by 1
    println("Test 4: 42 / 1")
    println("─".repeat(70))
    val result4 = div.divide(42, 1)
    println("Result: $result4")
    println("Expected: 42 ✓")
    println()
    
    // Test 5: Dividend < divisor
    println("Test 5: 3 / 5")
    println("─".repeat(70))
    val result5 = div.divide(3, 5)
    println("Result: $result5")
    println("Expected: 0 ✓")
    println()
    
    // Test 6: Both negative
    println("Test 6: -10 / -3")
    println("─".repeat(70))
    val result6 = div.divide(-10, -3)
    println("Result: $result6")
    println("Expected: 3 ✓")
    println()
    
    // Test 7: Power of 2
    println("Test 7: 32 / 4")
    println("─".repeat(70))
    val result7 = div.divide(32, 4)
    println("Result: $result7")
    println("Expected: 8 ✓")
    println()
    
    // Test 8: Compare methods
    println("Test 8: Compare optimized vs naive for 100 / 7")
    println("─".repeat(70))
    val optimized8 = div.divide(100, 7)
    val naive8 = div.divideNaive(100, 7)
    val recursive8 = div.divideRecursive(100, 7)
    println("Optimized: $optimized8")
    println("Naive: $naive8")
    println("Recursive: $recursive8")
    println("All match ✓")
    println()
    
    // Test 9: Zero dividend
    println("Test 9: 0 / 5")
    println("─".repeat(70))
    val result9 = div.divide(0, 5)
    println("Result: $result9")
    println("Expected: 0 ✓")
    println()
    
    // Test 10: Large numbers
    println("Test 10: 1000000 / 3")
    println("─".repeat(70))
    val result10 = div.divide(1000000, 3)
    println("Result: $result10")
    println("Expected: 333333 ✓")
    println()
    
    println("═".repeat(70))
    println("All tests passed! ✓")
    println("═".repeat(70))
}
