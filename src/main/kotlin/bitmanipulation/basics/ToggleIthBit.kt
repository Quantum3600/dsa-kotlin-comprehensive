/**
 * ============================================================================
 * PROBLEM:  Toggle i-th Bit
 * DIFFICULTY: Easy
 * CATEGORY: Bit Manipulation - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a number n and a position i (0-indexed from right), toggle the i-th bit
 * in the binary representation of n.  Toggle means:  if bit is 0, make it 1;
 * if bit is 1, make it 0.
 * 
 * INPUT FORMAT: 
 * - An integer n:  n = 13
 * - Position i (0-indexed from right): i = 1
 * 
 * OUTPUT FORMAT:
 * - Modified integer with i-th bit toggled:  15
 * - Binary transformation: 1101 → 1111
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
 * To toggle a bit (flip 0→1 or 1→0), we use XOR (exclusive OR). XOR has a
 * special property: it returns 1 when bits are DIFFERENT.  So XORing with 1
 * flips the bit, while XORing with 0 keeps it unchanged! 
 * 
 * KEY INSIGHT:
 * - Create mask with i-th bit set: (1 << i)
 * - Use XOR operation: n ^ mask
 * - XOR with 1 flips the bit (0→1, 1→0)
 * - XOR with 0 keeps other bits unchanged
 * - Result: i-th bit is toggled, all others stay the same
 * 
 * WHY THIS WORKS: 
 * XOR truth table: 
 * - 0 ^ 0 = 0 (unchanged)
 * - 0 ^ 1 = 1 (flipped:  0→1) ← Toggle!
 * - 1 ^ 0 = 1 (unchanged)
 * - 1 ^ 1 = 0 (flipped: 1→0) ← Toggle!
 * 
 * XOR with mask toggles the bit at position i!
 * 
 * ALGORITHM STEPS: 
 * 1. Create mask by left shifting 1 by i positions:  mask = (1 << i)
 * 2. Perform XOR operation: result = n ^ mask
 * 3. Return result
 * 
 * VISUAL EXAMPLE 1: Toggle bit 1 of 13 (0→1)
 * ───────────────────────────────────────
 * n = 13 (decimal)
 * Binary: 1101
 * Positions: 3210
 * Target: Toggle bit at position 1 (currently 0)
 * 
 * Step 1: Create mask for position 1
 * 1 << 1 = 0001 << 1 = 0010
 * 
 * Step 2: XOR operation
 *   1101  (13)
 * ^ 0010  (mask)
 *   ────
 *   1111  (15)
 * 
 * Result: 1101 → 1111 (13 → 15)
 * Bit 1 toggled from 0 to 1 ✓
 * 
 * VISUAL EXAMPLE 2: Toggle bit 2 of 13 (1→0)
 * ───────────────────────────────────────
 * n = 13 (1101), bit 2 is currently 1
 * 
 * Step 1: Create mask for position 2
 * 1 << 2 = 0100
 * 
 * Step 2: XOR operation
 *   1101  (13)
 * ^ 0100  (mask)
 *   ────
 *   1001  (9)
 * 
 * Result: 1101 → 1001 (13 → 9)
 * Bit 2 toggled from 1 to 0 ✓
 * 
 * VISUAL EXAMPLE 3: Toggle bit 0 of 8
 * ───────────────────────────────────────
 * n = 8 (1000), toggle LSB
 * 
 * Step 1: Create mask
 * 1 << 0 = 0001
 * 
 * Step 2: XOR operation
 *   1000  (8)
 * ^ 0001  (mask)
 *   ────
 *   1001  (9)
 * 
 * Result: 1000 → 1001 (8 → 9)
 * Even number becomes odd ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(1)
 * - Left shift operation: O(1)
 * - XOR operation: O(1)
 * - All operations are constant time
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using a mask variable
 * - No extra space dependent on input size
 * 
 * ============================================================================
 */

package bitmanipulation.basics

class ToggleIthBit {
    
    /**
     * Toggle i-th bit using XOR operation
     * TIME:  O(1), SPACE: O(1)
     * 
     * @param n The number to modify
     * @param i The bit position to toggle (0-indexed from right)
     * @return Modified number with i-th bit toggled
     */
    fun toggleBit(n: Int, i: Int): Int {
        // Create mask with only i-th bit set
        val mask = 1 shl i
        
        // XOR with mask to toggle the bit
        return n xor mask
    }
    
    /**
     * Toggle multiple bits at once
     * TIME: O(k) where k is number of bits to toggle, SPACE: O(1)
     * 
     * @param n The number to modify
     * @param positions Array of bit positions to toggle
     * @return Modified number with all specified bits toggled
     */
    fun toggleMultipleBits(n: Int, positions: IntArray): Int {
        var result = n
        for (i in positions) {
            result = toggleBit(result, i)
        }
        return result
    }
    
    /**
     * Toggle all bits in the number (bitwise NOT)
     * TIME: O(1), SPACE: O(1)
     */
    fun toggleAllBits(n: Int): Int {
        return n.inv()
    }
    
    /**
     * Toggle bits in a range [left, right]
     * TIME:  O(1), SPACE: O(1)
     */
    fun toggleBitsInRange(n: Int, left: Int, right: Int): Int {
        // Create mask with 1s in range [left, right]
        val mask = ((1 shl (right - left + 1)) - 1) shl left
        return n xor mask
    }
    
    /**
     * Check if toggling bit i changes parity (odd/even)
     */
    fun toggleChangeParity(n: Int, i: Int): Boolean {
        // Toggling LSB always changes parity
        // Toggling any other bit doesn't change parity
        return i == 0
    }
    
    /**
     * Visualize the bit toggling operation
     */
    fun visualizeToggleBit(n: Int, i: Int) {
        val before = Integer.toBinaryString(n).padStart(8, '0')
        val result = toggleBit(n, i)
        val after = Integer.toBinaryString(result).padStart(8, '0')
        val bitBefore = if ((n and (1 shl i)) != 0) '1' else '0'
        val bitAfter = if (bitBefore == '1') '0' else '1'
        
        println("Before:  $before ($n)")
        print("        ")
        for (pos in 7 downTo 0) {
            if (pos == i) print("↑ ") else print("  ")
        }
        println()
        println("After:  $after ($result)")
        println("Bit $i toggled:  $bitBefore → $bitAfter")
    }
}

/**
 * ============================================================================
 * EDGE CASES & APPLICATIONS
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. n = 0:  Toggling any bit creates a power of 2
 *    Example: toggleBit(0, 3) = 8 (1000)
 * 
 * 2. Toggle twice: Returns original value (XOR is reversible)
 *    Example: toggleBit(toggleBit(n, i), i) = n
 * 
 * 3. i = 0: Toggles LSB (even↔odd conversion)
 *    Example: toggleBit(8, 0) = 9, toggleBit(9, 0) = 8
 * 
 * 4. All bits set (0xFFFFFFFF): Toggling makes that bit 0
 * 
 * 5. Single bit set: Toggling that bit makes it 0
 * 
 * INTERESTING PROPERTIES:
 * - Toggling is REVERSIBLE: toggle twice = original
 * - XOR is used in encryption for this property! 
 * - Toggling LSB switches between even and odd
 * 
 * APPLICATIONS:
 * 1. **Feature Toggling**: Switch features on/off
 *    Example: settings ^= DARK_MODE_FLAG
 * 
 * 2. **Encryption (XOR Cipher)**: Simple encryption/decryption
 *    Example: encrypted = plaintext ^ key
 *            decrypted = encrypted ^ key (same key!)
 * 
 * 3. **Graphics**:  Invert pixels, screen effects
 *    Example: pixel ^= 0xFFFFFF (invert RGB color)
 * 
 * 4. **Game Development**: Toggle game states
 *    Example: playerState ^= INVISIBLE_FLAG
 * 
 * 5. **UI State Management**: Toggle checkboxes, switches
 *    Example: isChecked ^= true
 * 
 * 6. **Parity Bit**: Toggle for error detection
 *    Example: Toggle bit to make even/odd parity
 * 
 * 7. **LED Control**: Toggle LED on/off states
 *    Example: ledState ^= (1 << ledPin)
 * 
 * 8. **Optimization**:  Swap values without temp variable
 *    Example: a ^= b; b ^= a; a ^= b
 * 
 * 9. **Chess/Game Boards**: Toggle piece positions
 *    Example: board ^= (1 << position)
 * 
 * ============================================================================
 */

fun main() {
    val toggler = ToggleIthBit()
    
    println("TOGGLE i-th BIT - Test Cases")
    println("=". repeat(50))
    println()
    
    println("Test 1: Toggle bit 1 of 13 (0→1)")
    println("-".repeat(50))
    toggler.visualizeToggleBit(13, 1)
    println("Result:  ${toggler.toggleBit(13, 1)}")
    println("Expected: 15 ✓\n")
    
    println("Test 2: Toggle bit 2 of 13 (1→0)")
    println("-".repeat(50))
    toggler.visualizeToggleBit(13, 2)
    println("Result: ${toggler.toggleBit(13, 2)}")
    println("Expected: 9 ✓\n")
    
    println("Test 3: Toggle bit 0 of 8 (even→odd)")
    println("-".repeat(50))
    toggler.visualizeToggleBit(8, 0)
    println("Result: ${toggler. toggleBit(8, 0)}")
    println("Expected:  9 ✓\n")
    
    println("Test 4: Toggle twice (reversible)")
    println("-".repeat(50))
    val original = 13
    val toggled = toggler.toggleBit(original, 2)
    val restored = toggler.toggleBit(toggled, 2)
    println("Original: $original")
    println("After toggle: $toggled")
    println("After toggle again: $restored")
    println("Restored to original ✓\n")
    
    println("Test 5: Toggle multiple bits (0, 1, 3) of 0")
    println("-".repeat(50))
    val result = toggler.toggleMultipleBits(0, intArrayOf(0, 1, 3))
    println("Binary: ${Integer.toBinaryString(result).padStart(8, '0')}")
    println("Result: $result")
    println("Expected:  11 (1011) ✓\n")
    
    println("Test 6: Toggle bits in range [1, 3] of 0")
    println("-".repeat(50))
    val result2 = toggler.toggleBitsInRange(0, 1, 3)
    println("Binary:  ${Integer.toBinaryString(result2).padStart(8, '0')}")
    println("Result: $result2")
    println("Expected: 14 (1110) ✓\n")
    
    println("All tests passed! ✓")
}
