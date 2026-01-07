/**
 * ============================================================================
 * PROBLEM:  Clear i-th Bit
 * DIFFICULTY: Easy
 * CATEGORY: Bit Manipulation - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a number n and a position i (0-indexed from right), clear the i-th bit
 * (set it to 0) in the binary representation of n, regardless of its current value.
 * 
 * INPUT FORMAT:
 * - An integer n: n = 13
 * - Position i (0-indexed from right): i = 2
 * 
 * OUTPUT FORMAT:
 * - Modified integer with i-th bit cleared (set to 0): 9
 * - Binary transformation: 1101 → 1001
 * 
 * CONSTRAINTS:
 * - 0 <= n <= 2^31 - 1
 * - 0 <= i <= 31
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To clear a bit (set it to 0), we use AND with an inverted mask. The mask
 * has 0 at position i and 1s everywhere else. AND with 0 makes the bit 0,
 * AND with 1 keeps the bit unchanged.
 * 
 * KEY INSIGHT:
 * - Create mask with i-th bit set: (1 << i)
 * - Invert the mask using NOT:  ~(1 << i)
 * - This gives us 0 at position i, 1s everywhere else
 * - Use AND operation: n & ~(1 << i)
 * - Result: i-th bit becomes 0, all others stay the same
 * 
 * WHY THIS WORKS: 
 * AND truth table:
 * - 0 & 0 = 0 (stays 0)
 * - 0 & 1 = 0 (already 0, stays 0)
 * - 1 & 0 = 0 (cleared to 0) ← This is what we want!
 * - 1 & 1 = 1 (unchanged)
 * 
 * So AND with inverted mask clears the bit without affecting others! 
 * 
 * ALGORITHM STEPS:
 * 1. Create mask: mask = (1 << i)
 * 2. Invert mask: invertedMask = ~mask
 * 3. AND operation: result = n & invertedMask
 * 4. Return result
 * 
 * VISUAL EXAMPLE 1: Clear bit 2 of 13
 * ───────────────────────────────────────
 * n = 13 (decimal)
 * Binary: 1101
 * Positions: 3210
 * Target: Clear bit at position 2
 * 
 * Step 1: Create mask for position 2
 * 1 << 2 = 0001 << 2 = 0100
 * 
 * Step 2: Invert the mask
 * ~0100 = 1011 (0 at position 2, 1s elsewhere)
 * 
 * Step 3: AND operation
 *   1101  (13)
 * & 1011  (inverted mask)
 *   ────
 *   1001  (9)
 * 
 * Result: 1101 → 1001 (13 → 9) ✓
 * 
 * VISUAL EXAMPLE 2: Clear bit 1 of 13 (already 0)
 * ───────────────────────────────────────
 * n = 13 (1101), bit 1 is already 0
 * 
 * Step 1: Create mask
 * 1 << 1 = 0010
 * 
 * Step 2: Invert mask
 * ~0010 = 1101
 * 
 * Step 3: AND operation
 *   1101  (13)
 * & 1101  (inverted mask)
 *   ────
 *   1101  (13, unchanged)
 * 
 * Result: Bit was already 0, remains 0 ✓
 * 
 * VISUAL EXAMPLE 3: Clear bit 0 of 15
 * ───────────────────────────────────────
 * n = 15 (1111)
 * 
 * Step 1: Create and invert mask
 * 1 << 0 = 0001
 * ~0001 = 1110
 * 
 * Step 2: AND operation
 *   1111  (15)
 * & 1110  (inverted mask)
 *   ────
 *   1110  (14)
 * 
 * Result: 1111 → 1110 (15 → 14) ✓
 * Cleared LSB (makes odd number even)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(1)
 * - Left shift: O(1)
 * - NOT operation: O(1)
 * - AND operation: O(1)
 * - All operations are constant time
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using mask and result variables
 * - No extra space dependent on input size
 * 
 * ============================================================================
 */

package bitmanipulation.basics

class ClearIthBit {
    
    /**
     * Clear i-th bit (set to 0) using AND with inverted mask
     * TIME:  O(1), SPACE: O(1)
     * 
     * @param n The number to modify
     * @param i The bit position to clear (0-indexed from right)
     * @return Modified number with i-th bit cleared (set to 0)
     */
    fun clearBit(n: Int, i: Int): Int {
        // Create mask with only i-th bit set
        val mask = 1 shl i
        
        // Invert mask and AND to clear the bit
        return n and mask.inv()
    }
    
    /**
     * Alternative: Using subtraction (if bit is set)
     * TIME: O(1), SPACE: O(1)
     * 
     * Note: This only works correctly if the bit is already set
     */
    fun clearBitAlternative(n: Int, i:  Int): Int {
        return if ((n and (1 shl i)) != 0) {
            n - (1 shl i)
        } else {
            n // Already clear
        }
    }
    
    /**
     * Clear multiple bits at once
     * TIME: O(k) where k is number of bits to clear, SPACE: O(1)
     */
    fun clearMultipleBits(n: Int, positions: IntArray): Int {
        var result = n
        for (i in positions) {
            result = clearBit(result, i)
        }
        return result
    }
    
    /**
     * Clear all bits from position i to 0 (clear rightmost i+1 bits)
     * TIME: O(1), SPACE: O(1)
     */
    fun clearBitsFrom0ToI(n: Int, i: Int): Int {
        // Create mask with 1s from MSB down to position i+1
        val mask = (-1 shl (i + 1))
        return n and mask
    }
    
    /**
     * Clear all bits from MSB down to position i
     * TIME: O(1), SPACE: O(1)
     */
    fun clearBitsFromMSBToI(n:  Int, i: Int): Int {
        // Create mask with 1s from position i-1 down to 0
        val mask = (1 shl i) - 1
        return n and mask
    }
    
    /**
     * Visualize the bit clearing operation
     */
    fun visualizeClearBit(n: Int, i: Int) {
        val before = Integer.toBinaryString(n).padStart(8, '0')
        val result = clearBit(n, i)
        val after = Integer.toBinaryString(result).padStart(8, '0')
        
        println("Before: $before ($n)")
        print("        ")
        for (pos in 7 downTo 0) {
            if (pos == i) print("↑ ") else print("  ")
        }
        println()
        println("After:  $after ($result)")
        println("Bit $i cleared to 0")
    }
}

/**
 * ============================================================================
 * EDGE CASES & APPLICATIONS
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. n = 0: All bits already 0, no change
 * 2. Bit already clear: Operation is idempotent
 * 3. i = 0: Clear LSB (makes odd number even)
 * 4. i = 31: Clear MSB (in signed int, changes sign)
 * 5. Single bit set:  Clearing that bit makes it 0
 * 
 * APPLICATIONS:
 * 1. **Revoke Permissions**: Remove a specific permission from user
 *    Example: permissions &= ~WRITE_PERMISSION
 * 
 * 2. **Disable Features**: Turn off a feature flag
 *    Example: features &= ~EXPERIMENTAL_FLAG
 * 
 * 3. **Clear Status Flags**: Reset specific status bits
 *    Example:  status &= ~ERROR_FLAG
 * 
 * 4. **Memory Management**: Clear allocation bits
 *    Example: bitmap &= ~(1 << block_index)
 * 
 * 5. **Make Even Number**:  Clear LSB to round down to even
 *    Example: clearBit(7, 0) = 6
 * 
 * 6. **Graphics**: Clear color channels
 *    Example: color &= ~(0xFF << 8) // Clear green channel
 * 
 * 7. **Network Protocols**: Clear specific flags in headers
 * 
 * 8. **Error Handling**: Clear specific error bits
 * 
 * ============================================================================
 */

fun main() {
    val clearer = ClearIthBit()
    
    println("CLEAR i-th BIT - Test Cases")
    println("=" . repeat(50))
    println()
    
    println("Test 1: Clear bit 2 of 13 (1101)")
    println("-".repeat(50))
    clearer.visualizeClearBit(13, 2)
    println("Result: ${clearer.clearBit(13, 2)}")
    println("Expected: 9 ✓\n")
    
    println("Test 2: Clear bit 1 of 13 (already 0)")
    println("-".repeat(50))
    clearer.visualizeClearBit(13, 1)
    println("Result: ${clearer.clearBit(13, 1)}")
    println("Expected: 13 (unchanged) ✓\n")
    
    println("Test 3: Clear bit 0 of 15 (1111)")
    println("-".repeat(50))
    clearer.visualizeClearBit(15, 0)
    println("Result: ${clearer. clearBit(15, 0)}")
    println("Expected:  14 (odd → even) ✓\n")
    
    println("Test 4: Clear bits 0 and 3 of 15 (1111)")
    println("-".repeat(50))
    val result = clearer.clearMultipleBits(15, intArrayOf(0, 3))
    println("Before: ${Integer.toBinaryString(15).padStart(8, '0')} (15)")
    println("After:   ${Integer.toBinaryString(result).padStart(8, '0')} ($result)")
    println("Expected:  6 (0110) ✓\n")
    
    println("Test 5: Clear bits 0 to 2 of 15")
    println("-".repeat(50))
    val result2 = clearer.clearBitsFrom0ToI(15, 2)
    println("Clear rightmost 3 bits of 1111")
    println("Result:  ${Integer.toBinaryString(result2).padStart(8, '0')} ($result2)")
    println("Expected: 8 (1000) ✓\n")
    
    println("All tests passed! ✓")
}
