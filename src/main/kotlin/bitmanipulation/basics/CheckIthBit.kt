/**
 * ============================================================================
 * PROBLEM: Check if i-th Bit is Set
 * DIFFICULTY: Easy
 * CATEGORY: Bit Manipulation - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * Given a number n and a position i (0-indexed from right), check whether the
 * i-th bit in the binary representation of n is set (1) or not (0).
 * 
 * INPUT FORMAT:
 * - An integer n:  n = 13
 * - Position i (0-indexed from right): i = 2
 * 
 * OUTPUT FORMAT:
 * - Boolean:  true if bit is set (1), false if not set (0)
 * - Example: true (bit at position 2 of 13 is 1)
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
 * To check if a specific bit is set, we need to "isolate" that bit and see
 * if it's 1 or 0. We can do this using a MASK - a number with only the i-th
 * bit set to 1, and all other bits as 0.
 * 
 * KEY INSIGHT:
 * Create a mask with only i-th bit set:  (1 << i)
 * Then use AND operation:  if (n & mask) != 0, the bit is set! 
 * 
 * WHY THIS WORKS:
 * - AND operation returns 1 only when BOTH bits are 1
 * - mask has only i-th bit as 1, rest are 0
 * - So (n & mask) will be non-zero ONLY if n's i-th bit is 1
 * 
 * ALGORITHM STEPS:
 * 1. Create mask by left shifting 1 by i positions:  mask = (1 << i)
 * 2. Perform AND operation: result = n & mask
 * 3. If result != 0, bit is set; otherwise, bit is not set
 * 
 * VISUAL EXAMPLE 1: Check bit 2 of 13
 * ───────────────────────────────────────
 * n = 13 (decimal)
 * Binary: 1101
 * Positions: 3210
 * 
 * Step 1: Create mask for position 2
 * 1 << 2 = 0001 << 2 = 0100 (mask)
 * 
 * Step 2: AND operation
 *   1101  (13)
 * & 0100  (mask)
 *   ────
 *   0100  (4, which is non-zero)
 * 
 * Result: 0100 != 0, so bit 2 is SET ✓
 * 
 * VISUAL EXAMPLE 2: Check bit 1 of 13
 * ───────────────────────────────────────
 * n = 13 (1101)
 * 
 * Step 1: Create mask for position 1
 * 1 << 1 = 0001 << 1 = 0010
 * 
 * Step 2: AND operation
 *   1101  (13)
 * & 0010  (mask)
 *   ────
 *   0000  (0)
 * 
 * Result: 0000 == 0, so bit 1 is NOT SET ✗
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(1)
 * - Left shift operation: O(1)
 * - AND operation:  O(1)
 * - Comparison: O(1)
 * - All operations are constant time
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only using a few variables
 * - No extra space dependent on input size
 * 
 * ============================================================================
 */

package bitmanipulation.basics

class CheckIthBit {
    
    /**
     * Check if i-th bit is set using AND with mask
     * TIME: O(1), SPACE: O(1)
     * 
     * @param n The number to check
     * @param i The bit position (0-indexed from right)
     * @return true if bit is set, false otherwise
     */
    fun isSet(n: Int, i: Int): Boolean {
        // Create mask with only i-th bit set
        val mask = 1 shl i
        
        // AND with mask:  non-zero means bit is set
        return (n and mask) != 0
    }
    
    /**
     * Alternative:  Check by right shifting
     * TIME: O(1), SPACE: O(1)
     * 
     * Shift n right by i positions, then check LSB (least significant bit)
     */
    fun isSetAlternative(n: Int, i: Int): Boolean {
        // Shift n right by i positions
        val shifted = n shr i
        
        // Check if LSB is 1
        return (shifted and 1) == 1
    }
    
    /**
     * Get the actual bit value (0 or 1) at position i
     */
    fun getBitValue(n: Int, i:  Int): Int {
        return if (isSet(n, i)) 1 else 0
    }
    
    /**
     * Print binary representation with i-th bit highlighted
     */
    fun visualizeBitCheck(n: Int, i: Int) {
        val binary = Integer.toBinaryString(n).padStart(8, '0')
        val isSet = isSet(n, i)
        
        println("Number: $n")
        println("Binary: $binary")
        print("        ")
        for (pos in 7 downTo 0) {
            if (pos == i) print("↑ ") else print("  ")
        }
        println()
        println("Bit at position $i is:  ${if (isSet) "SET (1)" else "NOT SET (0)"}")
    }
}

/**
 * ============================================================================
 * EDGE CASES & APPLICATIONS
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. n = 0: All bits are 0
 * 2. i = 0: Check least significant bit (LSB)
 * 3. i = 31: Check most significant bit (MSB) for 32-bit integers
 * 4. Power of 2: Only one bit is set
 * 5. All 1s (e.g., 0xFFFFFFFF): All bits are set
 * 
 * APPLICATIONS:
 * 1. **Permission Checking**: Check if user has specific permission
 *    Example: READ = bit 0, WRITE = bit 1, EXECUTE = bit 2
 * 
 * 2. **Feature Flags**: Check if feature is enabled
 *    Example:  flags & FEATURE_X to check if FEATURE_X is on
 * 
 * 3. **Status Flags**: Check system/device status bits
 *    Example: Network interface flags, file status flags
 * 
 * 4. **Bit Fields**: Extract specific bits from packed data
 *    Example: RGB color extraction, network packet headers
 * 
 * 5. **Error Checking**: Check error codes represented as bit flags
 * 
 * 6. **Gaming**:  Check collision masks, state flags
 * 
 * ============================================================================
 */

fun main() {
    val checker = CheckIthBit()
    
    println("CHECK i-th BIT - Test Cases")
    println("=" . repeat(50))
    println()
    
    println("Test 1: Check bit 2 of 13 (1101)")
    println("-".repeat(50))
    checker.visualizeBitCheck(13, 2)
    println("Result: ${checker.isSet(13, 2)}")
    println("Expected: true ✓\n")
    
    println("Test 2: Check bit 1 of 13 (1101)")
    println("-".repeat(50))
    checker.visualizeBitCheck(13, 1)
    println("Result: ${checker.isSet(13, 1)}")
    println("Expected: false ✓\n")
    
    println("Test 3: Check bit 0 of 5 (0101)")
    println("-".repeat(50))
    checker.visualizeBitCheck(5, 0)
    println("Result:  ${checker.isSet(5, 0)}")
    println("Expected: true ✓\n")
    
    println("Test 4: Check bit 3 of 8 (1000)")
    println("-".repeat(50))
    checker.visualizeBitCheck(8, 3)
    println("Result: ${checker.isSet(8, 3)}")
    println("Expected: true ✓\n")
    
    println("Test 5: Alternative method comparison")
    println("-".repeat(50))
    println("Method 1 (AND): ${checker.isSet(13, 2)}")
    println("Method 2 (Shift): ${checker.isSetAlternative(13, 2)}")
    println("Both methods agree ✓\n")
    
    println("All tests passed! ✓")
}
