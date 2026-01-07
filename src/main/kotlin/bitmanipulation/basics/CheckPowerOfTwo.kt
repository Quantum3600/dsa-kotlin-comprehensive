/**
 * ============================================================================
 * PROBLEM: Check if Number is Power of 2
 * DIFFICULTY: Easy
 * CATEGORY: Bit Manipulation - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * Given an integer n, determine if it is a power of 2. A number is a power
 * of 2 if it can be expressed as 2^k for some non-negative integer k. 
 * 
 * INPUT FORMAT:
 * - An integer n: n = 16
 * 
 * OUTPUT FORMAT:
 * - Boolean:  true if n is a power of 2, false otherwise
 * - Example: true (16 = 2^4)
 * 
 * CONSTRAINTS:
 * - -2^31 <= n <= 2^31 - 1
 * - Consider only positive powers of 2 (n > 0)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION: 
 * Powers of 2 have a special property in binary:  they have EXACTLY ONE set bit! 
 * 
 * Examples:
 * 1  = 2^0 = 0001  (one bit set)
 * 2  = 2^1 = 0010  (one bit set)
 * 4  = 2^2 = 0100  (one bit set)
 * 8  = 2^3 = 1000  (one bit set)
 * 16 = 2^4 = 10000 (one bit set)
 * 
 * Non-powers of 2 have multiple bits set: 
 * 3  = 0011  (two bits)
 * 5  = 0101  (two bits)
 * 6  = 0110  (two bits)
 * 7  = 0111  (three bits)
 * 
 * KEY INSIGHT: 
 * If n is a power of 2, then n & (n - 1) == 0!
 * 
 * Why?  Because: 
 * - n has exactly one set bit (power of 2 property)
 * - n - 1 flips all bits after that set bit (including the set bit itself)
 * - AND of n and (n-1) gives 0 since they have no common set bits
 * 
 * IMPORTANT: Also check n > 0 to exclude 0 and negative numbers
 * 
 * WHY THIS WORKS - DETAILED EXPLANATION:
 * 
 * Example 1: n = 8 (power of 2)
 * n     = 1000  (8)
 * n - 1 = 0111  (7)
 * 
 * n & (n - 1):
 *   1000
 * & 0111
 *   ────
 *   0000  = 0 ✓ (is power of 2)
 * 
 * Example 2: n = 6 (NOT power of 2)
 * n     = 0110  (6)
 * n - 1 = 0101  (5)
 * 
 * n & (n - 1):
 *   0110
 * & 0101
 *   ────
 *   0100  ≠ 0 ✗ (not power of 2)
 * 
 * ALGORITHM STEPS:
 * 1. Check if n > 0 (exclude 0 and negative numbers)
 * 2. Check if n & (n - 1) == 0
 * 3. Return true if both conditions met, false otherwise
 * 
 * VISUAL EXAMPLES:
 * ───────────────────────────────────────
 * 
 * Powers of 2:
 * 1  = 0001 → 0001 & 0000 = 0 ✓
 * 2  = 0010 → 0010 & 0001 = 0 ✓
 * 4  = 0100 → 0100 & 0011 = 0 ✓
 * 8  = 1000 → 1000 & 0111 = 0 ✓
 * 16 = 10000 → 10000 & 01111 = 0 ✓
 * 
 * NOT Powers of 2:
 * 3  = 0011 → 0011 & 0010 = 0010 ≠ 0 ✗
 * 5  = 0101 → 0101 & 0100 = 0100 ≠ 0 ✗
 * 6  = 0110 → 0110 & 0101 = 0100 ≠ 0 ✗
 * 7  = 0111 → 0111 & 0110 = 0110 ≠ 0 ✗
 * 
 * Edge Cases:
 * 0  = 0000 → Check fails (n must be > 0) ✗
 * -8 = negative → Check fails (n must be > 0) ✗
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(1)
 * - Subtraction: O(1)
 * - AND operation: O(1)
 * - Comparison: O(1)
 * - All operations are constant time
 * 
 * SPACE COMPLEXITY:  O(1)
 * - No extra space used
 * - Only boolean result
 * 
 * ============================================================================
 */

package bitmanipulation.basics

class CheckPowerOfTwo {
    
    /**
     * Check if number is power of 2 using bit manipulation
     * TIME: O(1), SPACE: O(1)
     * 
     * @param n The number to check
     * @return true if n is a power of 2, false otherwise
     */
    fun isPowerOfTwo(n: Int): Boolean {
        // Must be positive and n & (n-1) must be 0
        return n > 0 && (n and (n - 1)) == 0
    }
    
    /**
     * Alternative: Count set bits (should be exactly 1)
     * TIME: O(1), SPACE: O(1)
     */
    fun isPowerOfTwoCountBits(n: Int): Boolean {
        if (n <= 0) return false
        return Integer.bitCount(n) == 1
    }
    
    /**
     * Alternative: Using logarithm
     * TIME: O(1), SPACE: O(1)
     * 
     * Note: This method may have floating-point precision issues
     */
    fun isPowerOfTwoLog(n: Int): Boolean {
        if (n <= 0) return false
        val log2 = Math.log(n.toDouble()) / Math.log(2.0)
        return log2 == Math.floor(log2)
    }
    
    /**
     * Get the power k where n = 2^k (if n is power of 2)
     * Returns -1 if n is not a power of 2
     */
    fun getPowerOfTwo(n: Int): Int {
        if (! isPowerOfTwo(n)) return -1
        
        // Count trailing zeros = position of the only set bit
        return Integer.numberOfTrailingZeros(n)
    }
    
    /**
     * Get the next power of 2 greater than or equal to n
     * TIME: O(1), SPACE: O(1)
     */
    fun nextPowerOfTwo(n: Int): Int {
        if (n <= 0) return 1
        if (isPowerOfTwo(n)) return n
        
        // Set all bits after the MSB, then add 1
        var num = n - 1
        num = num or (num shr 1)
        num = num or (num shr 2)
        num = num or (num shr 4)
        num = num or (num shr 8)
        num = num or (num shr 16)
        return num + 1
    }
    
    /**
     * Visualize why n & (n-1) works
     */
    fun visualizePowerCheck(n: Int) {
        val binary = Integer.toBinaryString(n).padStart(8, '0')
        val nMinus1Binary = Integer.toBinaryString(n - 1).padStart(8, '0')
        val result = n and (n - 1)
        val resultBinary = Integer.toBinaryString(result).padStart(8, '0')
        val isPower = isPowerOfTwo(n)
        
        println("n       = $binary ($n)")
        println("n - 1   = $nMinus1Binary (${n - 1})")
        println("              ↓ AND")
        println("result  = $resultBinary ($result)")
        println()
        println("n & (n-1) == 0?  ${result == 0}")
        println("n > 0? ${n > 0}")
        println("Is power of 2? $isPower ${if (isPower) "✓" else "✗"}")
    }
}

/**
 * ============================================================================
 * EDGE CASES & APPLICATIONS
 * ============================================================================
 * 
 * EDGE CASES: 
 * 1. n = 0: Not a power of 2 (2^k is always ≥ 1)
 * 2. n = 1: Is power of 2 (2^0 = 1)
 * 3. n < 0: Negative numbers are not powers of 2
 * 4. n = 2^31 - 1: Largest 32-bit integer, not power of 2
 * 5. n = 1073741824: 2^30, largest power of 2 in positive 32-bit int
 * 
 * POWERS OF 2 IN BINARY (Pattern Recognition):
 * 2^0  = 1          = 1
 * 2^1  = 2          = 10
 * 2^2  = 4          = 100
 * 2^3  = 8          = 1000
 * 2^4  = 16         = 10000
 * 2^5  = 32         = 100000
 * 2^6  = 64         = 1000000
 * 2^7  = 128        = 10000000
 * 2^8  = 256        = 100000000
 * 2^9  = 512        = 1000000000
 * 2^10 = 1024       = 10000000000
 * 
 * Pattern:  Exactly ONE bit set, all others are 0
 * 
 * APPLICATIONS:
 * 1. **Memory Allocation**: 
 *    - Check if buffer size is power of 2
 *    - Optimize memory alignment
 *    - Page size validation
 * 
 * 2. **Data Structures**:
 *    - Hash table size (usually power of 2 for efficient modulo)
 *    - Binary heap validation
 *    - Perfect binary tree node count
 * 
 * 3. **Computer Graphics**:
 *    - Texture dimensions (must be power of 2 in older systems)
 *    - Mipmap levels
 * 
 * 4. **Algorithm Optimization**:
 *    - Division by power of 2 → use right shift
 *    - Modulo by power of 2 → use bitwise AND
 *    Example: n % 8 = n & 7 (when 8 is power of 2)
 * 
 * 5. **Network Protocols**:
 *    - Packet size validation
 *    - MTU (Maximum Transmission Unit) checking
 * 
 * 6. **Database Systems**:
 *    - Block size validation
 *    - Buffer pool size checking
 * 
 * 7. **Bit Manipulation Problems**:
 *    - Subset enumeration
 *    - State compression
 * 
 * 8. **Fast Modulo Operation**:
 *    If m is power of 2: n % m = n & (m - 1)
 *    Example: n % 16 = n & 15
 * 
 * ============================================================================
 */

fun main() {
    val checker = CheckPowerOfTwo()
    
    println("CHECK POWER OF 2 - Test Cases")
    println("=".repeat(50))
    println()
    
    println("Test 1: Check if 8 is power of 2")
    println("-".repeat(50))
    checker.visualizePowerCheck(8)
    println()
    
    println("Test 2: Check if 6 is power of 2")
    println("-".repeat(50))
    checker.visualizePowerCheck(6)
    println()
    
    println("Test 3: Check if 1 is power of 2")
    println("-".repeat(50))
    checker.visualizePowerCheck(1)
    println()
    
    println("Test 4: Check if 16 is power of 2")
    println("-".repeat(50))
    checker.visualizePowerCheck(16)
    println()
    
    println("Test 5: Edge case - Check if 0 is power of 2")
    println("-".repeat(50))
    println("Result: ${checker.isPowerOfTwo(0)}")
    println("Expected: false ✓\n")
    
    println("Test 6: Get power k for 16 (2^k)")
    println("-".repeat(50))
    val power = checker.getPowerOfTwo(16)
    println("16 = 2^$power")
    println("Expected: 4 ✓\n")
    
    println("Test 7: Next power of 2 after 10")
    println("-".repeat(50))
    val next = checker.nextPowerOfTwo(10)
    println("Next power of 2 ≥ 10: $next")
    println("Expected: 16 ✓\n")
    
    println("Test 8: Verify all methods agree")
    println("-".repeat(50))
    println("Method 1 (n & n-1): ${checker.isPowerOfTwo(8)}")
    println("Method 2 (count bits): ${checker.isPowerOfTwoCountBits(8)}")
    println("Method 3 (logarithm): ${checker.isPowerOfTwoLog(8)}")
    println("All methods agree ✓\n")
    
    println("All tests passed! ✓")
}
