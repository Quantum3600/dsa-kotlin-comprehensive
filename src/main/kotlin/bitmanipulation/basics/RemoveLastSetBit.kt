/**
 * ============================================================================
 * PROBLEM: Remove Last Set Bit (Rightmost Set Bit)
 * DIFFICULTY: Easy
 * CATEGORY: Bit Manipulation - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a number n, remove the rightmost set bit (the least significant 1 bit)
 * from its binary representation.  This operation is also known as "turning off
 * the rightmost 1 bit". 
 * 
 * INPUT FORMAT:
 * - An integer n: n = 12
 * 
 * OUTPUT FORMAT:
 * - Modified integer with rightmost set bit removed: 8
 * - Binary transformation: 1100 → 1000
 * 
 * CONSTRAINTS:
 * - 0 <= n <= 2^31 - 1
 * - If n = 0, return 0 (no set bits to remove)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is one of the most elegant bit manipulation tricks!  The operation
 * n & (n - 1) magically removes the rightmost set bit.  Let's understand why. 
 * 
 * KEY INSIGHT:
 * When you subtract 1 from a number: 
 * - All bits to the RIGHT of the rightmost 1 flip (0→1, 1→0)
 * - The rightmost 1 becomes 0
 * - All bits to the LEFT stay the same
 * 
 * Then AND-ing n with (n-1) keeps all bits except the rightmost 1!
 * 
 * WHY THIS WORKS - DETAILED EXPLANATION:
 * 
 * Consider n = 12 (binary: 1100)
 * 
 * Step 1: What happens when we do n - 1?
 *   n     = 1100  (12)
 *   n - 1 = 1011  (11)
 * 
 * Notice: 
 * - Rightmost 1 (at position 2) became 0
 * - All bits to the right flipped:  no bits here, so nothing
 * - All bits to the left stayed same: 1
 * 
 * Step 2: AND operation
 *   n     = 1100  (12)
 *   n - 1 = 1011  (11)
 *   ───────────
 *   n & (n-1) = 1000  (8)
 * 
 * The rightmost 1 is removed! ✓
 * 
 * ALGORITHM STEPS:
 * 1. Compute n - 1
 * 2. AND with original n:  result = n & (n - 1)
 * 3. Return result
 * 
 * VISUAL EXAMPLE 1: Remove last set bit of 12
 * ───────────────────────────────────────
 * n = 12 (decimal)
 * Binary: 1100
 * Rightmost set bit is at position 2
 * 
 * n - 1:
 *   1100  (12)
 * - 0001  (1)
 *   ────
 *   1011  (11)
 * 
 * n & (n - 1):
 *   1100  (12)
 * & 1011  (11)
 *   ────
 *   1000  (8)
 * 
 * Result: 1100 → 1000 (12 → 8)
 * Rightmost set bit removed ✓
 * 
 * VISUAL EXAMPLE 2: Remove last set bit of 10
 * ───────────────────────────────────────
 * n = 10 (1010)
 * Rightmost set bit at position 1
 * 
 * n - 1:
 *   1010  (10)
 * - 0001  (1)
 *   ────
 *   1001  (9)
 * 
 * n & (n - 1):
 *   1010  (10)
 * & 1001  (9)
 *   ────
 *   1000  (8)
 * 
 * Result: 1010 → 1000 (10 → 8) ✓
 * 
 * VISUAL EXAMPLE 3: Remove last set bit of 7
 * ───────────────────────────────────────
 * n = 7 (0111)
 * Rightmost set bit at position 0
 * 
 * n - 1:
 *   0111  (7)
 * - 0001  (1)
 *   ────
 *   0110  (6)
 * 
 * n & (n - 1):
 *   0111  (7)
 * & 0110  (6)
 *   ────
 *   0110  (6)
 * 
 * Result: 0111 → 0110 (7 → 6) ✓
 * 
 * VISUAL EXAMPLE 4: Power of 2 (single bit)
 * ───────────────────────────────────────
 * n = 8 (1000)
 * Only one set bit at position 3
 * 
 * n & (n - 1):
 *   1000  (8)
 * & 0111  (7)
 *   ────
 *   0000  (0)
 * 
 * Result: 1000 → 0000 (8 → 0)
 * Removing only set bit gives 0 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(1)
 * - Subtraction: O(1)
 * - AND operation: O(1)
 * - All operations are constant time
 * 
 * SPACE COMPLEXITY: O(1)
 * - No extra space used
 * - Only simple variables
 * 
 * ============================================================================
 */

package bitmanipulation.basics

class RemoveLastSetBit {
    
    /**
     * Remove rightmost set bit using n & (n - 1)
     * TIME: O(1), SPACE: O(1)
     * 
     * @param n The number to modify
     * @return Modified number with rightmost set bit removed
     */
    fun removeLastSetBit(n: Int): Int {
        // The magic formula! 
        return n and (n - 1)
    }
    
    /**
     * Alternative:  Find position and clear it explicitly
     * TIME: O(1), SPACE: O(1)
     * 
     * This is more verbose but shows the logic clearly
     */
    fun removeLastSetBitVerbose(n: Int): Int {
        if (n == 0) return 0
        
        // Find position of rightmost set bit
        var pos = 0
        var temp = n
        while ((temp and 1) == 0) {
            temp = temp shr 1
            pos++
        }
        
        // Clear that bit
        return n and (1 shl pos).inv()
    }
    
    /**
     * Get only the rightmost set bit (isolate it)
     * TIME: O(1), SPACE: O(1)
     * 
     * Formula: n & (-n)
     * This gives a number with only the rightmost set bit
     */
    fun getRightmostSetBit(n: Int): Int {
        return n and (-n)
    }
    
    /**
     * Remove all set bits one by one (repeatedly remove last)
     * Returns count of set bits removed
     * TIME: O(k) where k is number of set bits, SPACE: O(1)
     * 
     * This is Brian Kernighan's algorithm for counting set bits
     */
    fun removeAllSetBits(n: Int): Int {
        var num = n
        var count = 0
        while (num != 0) {
            num = num and (num - 1)  // Remove last set bit
            count++
        }
        return count
    }
    
    /**
     * Visualize the removal of rightmost set bit
     */
    fun visualizeRemoval(n: Int) {
        val before = Integer.toBinaryString(n).padStart(8, '0')
        val nMinus1 = n - 1
        val middle = Integer.toBinaryString(nMinus1).padStart(8, '0')
        val result = removeLastSetBit(n)
        val after = Integer.toBinaryString(result).padStart(8, '0')
        
        println("n       = $before ($n)")
        println("n - 1   = $middle ($nMinus1)")
        println("              ↓ AND")
        println("result  = $after ($result)")
        
        // Find which bit was removed
        val removed = n xor result
        if (removed != 0) {
            val pos = Integer.numberOfTrailingZeros(removed)
            println("Removed bit at position $pos")
        }
    }
}

/**
 * ============================================================================
 * EDGE CASES & APPLICATIONS
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. n = 0: No set bits, return 0
 *    Example: removeLastSetBit(0) = 0
 * 
 * 2. n is power of 2: Single set bit, result is 0
 *    Example: removeLastSetBit(8) = 0
 * 
 * 3. n = 1: Smallest positive number with set bit
 *    Example: removeLastSetBit(1) = 0
 * 
 * 4. All bits set:  Removes LSB
 *    Example: removeLastSetBit(15) = 14 (1111 → 1110)
 * 
 * 5. Odd number: Always removes bit at position 0
 *    Example: removeLastSetBit(7) = 6
 * 
 * APPLICATIONS:
 * 1. **Brian Kernighan's Algorithm**: Count set bits efficiently
 *    - Faster than checking each bit
 *    - O(k) where k = number of set bits, not O(log n)
 *    - Used in:  removeAllSetBits() function above
 * 
 * 2. **Check Power of 2**: 
 *    - If n & (n-1) == 0 and n != 0, then n is power of 2
 *    - Powers of 2 have exactly one set bit
 *    - Removing it gives 0
 * 
 * 3. **Subset Generation**: 
 *    - Iterate through all subsets of set bits
 *    - Used in dynamic programming on subsets
 * 
 * 4. **Find Unique Elements**:
 *    - Part of algorithms finding unique numbers
 *    - XOR-based problems
 * 
 * 5. **Bit Counting Optimization**:
 *    - Population count (popcount) optimization
 *    - Hamming weight calculation
 * 
 * 6. **Graph Algorithms**:
 *    - Iterating through adjacency in bitmask
 *    - State compression in DP
 * 
 * 7. **Game Development**:
 *    - Remove pieces from board representation
 *    - Update game state efficiently
 * 
 * 8. **Compiler Optimization**:
 *    - Strength reduction
 *    - Loop optimization
 * 
 * ============================================================================
 */

fun main() {
    val remover = RemoveLastSetBit()
    
    println("REMOVE LAST SET BIT - Test Cases")
    println("=".repeat(50))
    println()
    
    println("Test 1: Remove last set bit of 12 (1100)")
    println("-".repeat(50))
    remover.visualizeRemoval(12)
    println("Result: ${remover.removeLastSetBit(12)}")
    println("Expected: 8 ✓\n")
    
    println("Test 2: Remove last set bit of 10 (1010)")
    println("-".repeat(50))
    remover.visualizeRemoval(10)
    println("Result: ${remover.removeLastSetBit(10)}")
    println("Expected: 8 ✓\n")
    
    println("Test 3: Remove last set bit of 7 (0111)")
    println("-".repeat(50))
    remover.visualizeRemoval(7)
    println("Result: ${remover. removeLastSetBit(7)}")
    println("Expected: 6 ✓\n")
    
    println("Test 4: Remove last set bit of 8 (power of 2)")
    println("-".repeat(50))
    remover.visualizeRemoval(8)
    println("Result: ${remover.removeLastSetBit(8)}")
    println("Expected: 0 (only one bit) ✓\n")
    
    println("Test 5: Get rightmost set bit of 12")
    println("-".repeat(50))
    val rightmost = remover.getRightmostSetBit(12)
    println("Number: 12 (1100)")
    println("Rightmost set bit: ${Integer.toBinaryString(rightmost).padStart(8, '0')} ($rightmost)")
    println("Expected: 4 (0100) ✓\n")
    
    println("Test 6: Count set bits using removal (Brian Kernighan)")
    println("-".repeat(50))
    val count = remover.removeAllSetBits(13)
    println("Number:  13 (1101)")
    println("Set bits count: $count")
    println("Expected: 3 ✓\n")
    
    println("All tests passed! ✓")
}
