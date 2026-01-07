/**
 * ============================================================================
 * INTRODUCTION:  Bit Manipulation Fundamentals
 * DIFFICULTY: Easy
 * CATEGORY: Bit Manipulation - Basics
 * ============================================================================
 * 
 * WHAT IS BIT MANIPULATION?
 * Bit manipulation involves directly manipulating individual bits in binary 
 * representation of numbers. It's one of the most efficient techniques in 
 * programming for solving certain problems.
 * 
 * WHY LEARN BIT MANIPULATION? 
 * ✅ Extremely fast operations (single CPU cycle)
 * ✅ Memory efficient (no extra space needed)
 * ✅ Elegant solutions to complex problems
 * ✅ Common in interviews and competitive programming
 * ✅ Used in cryptography, compression, graphics, networking
 * 
 * ============================================================================
 * BINARY NUMBER SYSTEM
 * ============================================================================
 * 
 * UNDERSTANDING BINARY: 
 * - Computers store numbers in binary (base-2) using only 0s and 1s
 * - Each position represents a power of 2
 * - Positions are 0-indexed from RIGHT to LEFT
 * 
 * EXAMPLE:  Decimal 13 in binary
 * 
 * Binary:     1    1    0    1
 * Position:  3    2    1    0   (0-indexed from right)
 * Value:     8  + 4  + 0  + 1  = 13
 * Power:    2³   2²   2¹   2⁰
 * 
 * VISUALIZATION:
 * ```
 * Decimal: 13
 * Binary:   1101
 *          ││││
 *          │││└─ Position 0:  1 × 2⁰ = 1
 *          ││└── Position 1: 0 × 2¹ = 0
 *          │└─── Position 2: 1 × 2² = 4
 *          └──── Position 3: 1 × 2³ = 8
 *                              Total = 13
 * ```
 * 
 * ============================================================================
 * BITWISE OPERATORS
 * ============================================================================
 * 
 * 1. AND (&) - Returns 1 if BOTH bits are 1
 * ───────────────────────────────────────────
 * Truth Table:
 *   0 & 0 = 0
 *   0 & 1 = 0
 *   1 & 0 = 0
 *   1 & 1 = 1
 * 
 * Example: 
 *   12 & 10
 *   1100  (12)
 * & 1010  (10)
 *   ────
 *   1000  (8)
 * 
 * USE CASE:  Check if bit is set, clear bits, extract specific bits
 * 
 * 
 * 2. OR (|) - Returns 1 if EITHER bit is 1
 * ───────────────────────────────────────────
 * Truth Table:
 *   0 | 0 = 0
 *   0 | 1 = 1
 *   1 | 0 = 1
 *   1 | 1 = 1
 * 
 * Example: 
 *   12 | 10
 *   1100  (12)
 * | 1010  (10)
 *   ────
 *   1110  (14)
 * 
 * USE CASE: Set bits to 1, combine flags
 * 
 * 
 * 3. XOR (^) - Returns 1 if bits are DIFFERENT
 * ───────────────────────────────────────────
 * Truth Table:
 *   0 ^ 0 = 0
 *   0 ^ 1 = 1
 *   1 ^ 0 = 1
 *   1 ^ 1 = 0
 * 
 * Example:
 *   12 ^ 10
 *   1100  (12)
 * ^ 1010  (10)
 *   ────
 *   0110  (6)
 * 
 * SPECIAL PROPERTIES:
 * - a ^ a = 0 (any number XOR itself is 0)
 * - a ^ 0 = a (XOR with 0 gives original)
 * - XOR is commutative and associative
 * 
 * USE CASE: Toggle bits, find unique element, swap without temp variable
 * 
 * 
 * 4. NOT (~) - Inverts all bits (0→1, 1→0)
 * ───────────────────────────────────────────
 * Example (8-bit):
 *   ~12
 *   00001100  (12)
 * ~ ────────
 *   11110011  (-13 in two's complement)
 * 
 * Note: In two's complement, ~n = -(n+1)
 * 
 * USE CASE:  Create masks, bitwise negation
 * 
 * 
 * 5. LEFT SHIFT (<<) - Shift bits left, fill with 0s
 * ───────────────────────────────────────────
 * Example: 
 *   5 << 2
 *   00000101  (5)
 *   ────────
 *   00010100  (20)
 * 
 * EFFECT: Multiplies by 2^n where n is shift amount
 * 5 << 2 = 5 × 2² = 5 × 4 = 20
 * 
 * USE CASE: Fast multiplication by powers of 2, create bit masks
 * 
 * 
 * 6. RIGHT SHIFT (>>) - Shift bits right, discard rightmost
 * ───────────────────────────────────────────
 * Example:
 *   20 >> 2
 *   00010100  (20)
 *   ────────
 *   00000101  (5)
 * 
 * EFFECT: Divides by 2^n where n is shift amount
 * 20 >> 2 = 20 ÷ 2² = 20 ÷ 4 = 5
 * 
 * USE CASE: Fast division by powers of 2, extract bits
 * 
 * ============================================================================
 * COMMON BIT MANIPULATION PATTERNS
 * ============================================================================
 * 
 * 1. CHECK IF i-th BIT IS SET: 
 *    (n & (1 << i)) != 0
 *    Example: Check if bit 2 of 13 (1101) is set
 *    13 & (1 << 2) = 1101 & 0100 = 0100 ≠ 0 → YES
 * 
 * 2. SET i-th BIT: 
 *    n | (1 << i)
 *    Example: Set bit 1 of 9 (1001)
 *    9 | (1 << 1) = 1001 | 0010 = 1011 = 11
 * 
 * 3. CLEAR i-th BIT:
 *    n & ~(1 << i)
 *    Example: Clear bit 2 of 13 (1101)
 *    13 & ~(1 << 2) = 1101 & 1011 = 1001 = 9
 * 
 * 4. TOGGLE i-th BIT:
 *    n ^ (1 << i)
 *    Example: Toggle bit 1 of 13 (1101)
 *    13 ^ (1 << 1) = 1101 ^ 0010 = 1111 = 15
 * 
 * 5. REMOVE LAST SET BIT:
 *    n & (n - 1)
 *    Example: 12 (1100)
 *    12 & 11 = 1100 & 1011 = 1000 = 8
 * 
 * 6. CHECK IF POWER OF 2:
 *    (n & (n - 1)) == 0 && n > 0
 *    Powers of 2 have exactly one set bit
 * 
 * 7. COUNT SET BITS (Brian Kernighan):
 *    Repeatedly use n & (n-1) until n becomes 0
 * 
 * 8. SWAP TWO NUMBERS (without temp):
 *    a ^= b
 *    b ^= a
 *    a ^= b
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. PERMISSIONS & FLAGS:
 *    - File permissions (rwxrwxrwx)
 *    - Feature flags in software
 *    - Access control lists
 * 
 * 2. DATA COMPRESSION:
 *    - Huffman coding
 *    - Run-length encoding
 *    - Image compression
 * 
 * 3. CRYPTOGRAPHY:
 *    - Encryption algorithms
 *    - Hash functions
 *    - XOR ciphers
 * 
 * 4. GRAPHICS & GAMES:
 *    - Color manipulation (RGB)
 *    - Collision detection
 *    - Sprite masking
 * 
 * 5. NETWORKING:
 *    - IP address masking
 *    - Subnet calculations
 *    - Protocol flags
 * 
 * 6. DATABASE OPTIMIZATION:
 *    - Bitmap indexes
 *    - Bloom filters
 *    - Space-efficient storage
 * 
 * 7. ALGORITHMS:
 *    - Finding unique elements
 *    - Subset generation
 *    - Dynamic programming optimization
 * 
 * ============================================================================
 */

package bitmanipulation.basics

class IntroToBitManipulation {
    
    /**
     * Demonstrates all basic bitwise operations
     */
    fun demonstrateOperators(a: Int, b: Int) {
        println("a = $a (${Integer.toBinaryString(a).padStart(8, '0')})")
        println("b = $b (${Integer.toBinaryString(b).padStart(8, '0')})")
        println()
        
        println("AND (a & b) = ${a and b} (${Integer.toBinaryString(a and b).padStart(8, '0')})")
        println("OR  (a | b) = ${a or b} (${Integer.toBinaryString(a or b).padStart(8, '0')})")
        println("XOR (a ^ b) = ${a xor b} (${Integer.toBinaryString(a xor b).padStart(8, '0')})")
        println("NOT (~a)    = ${a. inv()} (${Integer.toBinaryString(a.inv()).takeLast(8)})")
        println("Left Shift  (a << 2) = ${a shl 2} (${Integer.toBinaryString(a shl 2).padStart(8, '0')})")
        println("Right Shift (a >> 2) = ${a shr 2} (${Integer.toBinaryString(a shr 2).padStart(8, '0')})")
    }
    
    /**
     * Converts decimal to binary string representation
     */
    fun decimalToBinary(n: Int): String {
        return Integer.toBinaryString(n)
    }
    
    /**
     * Converts binary string to decimal
     */
    fun binaryToDecimal(binary: String): Int {
        return binary.toInt(2)
    }
    
    /**
     * Prints binary representation with position markers
     */
    fun printBinaryWithPositions(n: Int) {
        val binary = Integer.toBinaryString(n).padStart(8, '0')
        println("Number: $n")
        println("Binary: $binary")
        print("Pos:     ")
        for (i in 7 downTo 0) {
            print("$i ")
        }
        println("\n")
    }
}

/**
 * ============================================================================
 * KEY TAKEAWAYS
 * ============================================================================
 * 
 * 1. Bit manipulation is FAST and MEMORY EFFICIENT
 * 2. Master the 6 basic operators: &, |, ^, ~, <<, >>
 * 3. Common patterns solve many problems elegantly
 * 4. Used extensively in systems programming and optimization
 * 5. Practice is key - start with basic problems
 * 
 * ============================================================================
 */

fun main() {
    val intro = IntroToBitManipulation()
    
    println("BIT MANIPULATION - INTRODUCTION")
    println("=" .  repeat(50))
    println()
    
    println("Example 1: Binary Representation")
    println("-". repeat(50))
    intro.printBinaryWithPositions(13)
    
    println("\nExample 2: Bitwise Operators")
    println("-".repeat(50))
    intro.demonstrateOperators(12, 10)
    
    println("\n\nExample 3: Decimal ↔ Binary Conversion")
    println("-".repeat(50))
    println("Decimal 13 → Binary: ${intro.decimalToBinary(13)}")
    println("Binary 1101 → Decimal:  ${intro.binaryToDecimal("1101")}")
    
    println("\n✓ Bit manipulation fundamentals covered!")
}
