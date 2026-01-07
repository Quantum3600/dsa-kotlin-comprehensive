/**
 * ============================================================================
 * PROBLEM: Set i-th Bit
 * DIFFICULTY: Easy
 * CATEGORY: Bit Manipulation - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a number n and a position i (0-indexed from right), set the i-th bit
 * to 1 in the binary representation of n, regardless of its current value.
 * 
 * INPUT FORMAT:
 * - An integer n: n = 9
 * - Position i (0-indexed from right): i = 1
 * 
 * OUTPUT FORMAT:
 * - Modified integer with i-th bit set to 1:  11
 * - Binary transformation: 1001 → 1011
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
 * To set a bit to 1, we use the OR operation.  Remember:  OR returns 1 if
 * EITHER bit is 1. So if we OR with a mask that has the i-th bit as 1,
 * that position will become 1 regardless of the original value! 
 * 
 * KEY INSIGHT:
 * - Create a mask with only i-th bit set: (1 << i)
 * - Use OR operation:  n | mask
 * - OR with 1 makes the bit 1, OR with 0 keeps it unchanged
 * - Result: i-th bit becomes 1, all others remain the same
 * 
 * WHY THIS WORKS:
 * OR truth table:
 * - 0 | 0 = 0 (unchanged)
 * - 0 | 1 = 1 (set to 1)
 * - 1 | 0 = 1 (already 1, stays 1)
 * - 1 | 1 = 1 (already 1, stays 1)
 * 
 * So OR with a mask sets the bit to 1 without affecting others!
 * 
 * ALGORITHM STEPS:
 * 1. Create mask by left shifting 1 by i positions:  mask = (1 << i)
 * 2. Perform OR operation: result = n | mask
 * 3. Return result
 * 
 * VISUAL EXAMPLE 1: Set bit 1 of 9
 * ───────────────────────────────────────
 * n = 9 (decimal)
 * Binary: 1001
 * Positions: 3210
 * Target: Set bit at position 1
 * 
 * Step 1: Create mask for position 1
 * 1 << 1 = 0001 << 1 = 0010
 * 
 * Step 2: OR operation
 *   1001  (9)
 * | 0010  (mask)
 *   ────
 *   1011  (11)
 * 
 * Result: 1001 → 1011 (9 → 11) ✓
 * 
 * VISUAL EXAMPLE 2: Set bit 2 of 9 (already set)
 * ───────────────────────────────────────
 * n = 9 (1001), bit 0 is already 1
 * 
 * Step 1: Create mask for position 0
 * 1 << 0 = 0001
 * 
 * Step 2: OR operation
 *   1001  (9)
 * | 0001  (mask)
 *   ────
 *   1001  (9, unchanged)
 * 
 * Result:  Bit was already 1, remains 1 ✓
 * 
 * VISUAL EXAMPLE 3: Set bit 3 of 5
 * ───────────────────────────────────────
 * n = 5 (0101)
 * 
 * Step 1: Create mask for position 3
 * 1 << 3 = 1000
 * 
 * Step 2: OR operation
 *   0101  (5)
 * | 1000  (mask)
 *   ────
 *   1101  (13)
 * 
 * Result: 0101 → 1101 (5 → 13) ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(1)
 * - Left shift operation: O(1)
 * - OR operation: O(1)
 * - All operations are constant time, independent of input size
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only using a mask variable
 * - No extra space dependent on input
 * 
 * ============================================================================
 */

package bitmanipulation.basics

class SetIthBit {
    
    /**
     * Set i-th bit to 1 using OR operation
     * TIME: O(1), SPACE: O(1)
     * 
     * @param n The number to modify
     * @param i The bit position to set (0-indexed from right)
     * @return Modified number with i-th bit set to 1
     */
    fun setBit(n: Int, i:  Int): Int {
        // Create mask with only i-th bit set
        val mask = 1 shl i
        
        // OR with mask to set the bit
        return n or mask
    }
    
    /**
     * Set multiple bits at once
     * TIME: O(k) where k is number of bits to set, SPACE: O(1)
     * 
     * @param n The number to modify
     * @param positions Array of bit positions to set
     * @return Modified number with all specified bits set
     */
    fun setMultipleBits(n: Int, positions: IntArray): Int {
        var result = n
        for (i in positions) {
            result = setBit(result, i)
        }
        return result
    }
    
    /**
     * Visualize the bit setting operation
     */
    fun visualizeSetBit(n: Int, i: Int) {
        val before = Integer.toBinaryString(n).padStart(8, '0')
        val result = setBit(n, i)
        val after = Integer.toBinaryString(result).padStart(8, '0')
        
        println("Before: $before ($n)")
        print("        ")
        for (pos in 7 downTo 0) {
            if (pos == i) print("↑ ") else print("  ")
        }
        println()
        println("After:   $after ($result)")
        println("Bit $i set to 1")
    }
}

/**
 * ============================================================================
 * EDGE CASES & APPLICATIONS
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. n = 0: Setting any bit creates a power of 2
 *    Example: setBit(0, 3) = 8 (1000)
 * 
 * 2. Bit already set: Operation is idempotent (no change)
 *    Example: setBit(9, 0) where bit 0 is already 1
 * 
 * 3. i = 0: Set least significant bit (LSB)
 *    Makes any even number odd
 * 
 * 4. i = 31: Set most significant bit
 *    Creates negative number in signed 32-bit integer
 * 
 * 5. All bits set (0xFFFFFFFF): No change possible
 * 
 * APPLICATIONS:
 * 1. **Enable Permissions**: Grant user a specific permission
 *    Example:  permissions |= READ_PERMISSION
 * 
 * 2. **Feature Flags**: Enable a feature
 *    Example: features |= DARK_MODE_FLAG
 * 
 * 3. **Status Flags**: Set status bits
 *    Example: status |= CONNECTED_FLAG
 * 
 * 4. **Bitmap Operations**: Mark positions as occupied
 *    Example: Grid squares, seat reservations
 * 
 * 5. **Hardware Control**: Set control registers
 *    Example: GPIO pins, device configuration
 * 
 * 6. **Graphics**: Set RGB color channels
 *    Example: color |= (0xFF << 16) // Set red channel to max
 * 
 * 7. **Networking**: Set protocol flags in packet headers
 * 
 * ============================================================================
 */

fun main() {
    val setter = SetIthBit()
    
    println("SET i-th BIT - Test Cases")
    println("=" . repeat(50))
    println()
    
    println("Test 1: Set bit 1 of 9 (1001)")
    println("-".repeat(50))
    setter.visualizeSetBit(9, 1)
    println("Result: ${setter. setBit(9, 1)}")
    println("Expected: 11 ✓\n")
    
    println("Test 2: Set bit 0 of 9 (already set)")
    println("-".repeat(50))
    setter.visualizeSetBit(9, 0)
    println("Result:  ${setter.setBit(9, 0)}")
    println("Expected: 9 (unchanged) ✓\n")
    
    println("Test 3: Set bit 3 of 5 (0101)")
    println("-".repeat(50))
    setter.visualizeSetBit(5, 3)
    println("Result: ${setter. setBit(5, 3)}")
    println("Expected: 13 ✓\n")
    
    println("Test 4: Set bit 0 of 0 (all zeros)")
    println("-".repeat(50))
    setter.visualizeSetBit(0, 0)
    println("Result:  ${setter.setBit(0, 0)}")
    println("Expected: 1 ✓\n")
    
    println("Test 5: Set multiple bits")
    println("-".repeat(50))
    println("Set bits 1, 2, 4 of 0")
    val result = setter.setMultipleBits(0, intArrayOf(1, 2, 4))
    println("Result: $result (${Integer.toBinaryString(result).padStart(8, '0')})")
    println("Expected:  22 (00010110) ✓\n")
    
    println("All tests passed! ✓")
}
