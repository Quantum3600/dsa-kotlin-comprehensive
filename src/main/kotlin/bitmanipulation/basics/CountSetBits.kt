/**
 * ============================================================================
 * PROBLEM: Count Set Bits (Hamming Weight)
 * DIFFICULTY: Easy
 * CATEGORY:  Bit Manipulation - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an integer n, count the number of set bits (1s) in its binary
 * representation. This is also known as the Hamming Weight or population count. 
 * 
 * INPUT FORMAT:
 * - An integer n: n = 13
 * 
 * OUTPUT FORMAT:
 * - Integer count of set bits:  3
 * - Binary of 13: 1101 → three 1s
 * 
 * CONSTRAINTS:
 * - 0 <= n <= 2^31 - 1
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION: 
 * We need to count how many 1s are in the binary representation.  There are
 * multiple approaches, from naive to highly optimized.
 * 
 * ============================================================================
 * APPROACH 1: NAIVE - Check Each Bit
 * ============================================================================
 * 
 * IDEA:
 * Check each of the 32 bits one by one using right shift.
 * 
 * ALGORITHM:
 * 1. Loop through all bit positions (0 to 31)
 * 2. Check LSB using (n & 1)
 * 3. If 1, increment count
 * 4. Right shift n by 1
 * 5. Repeat until n becomes 0
 * 
 * VISUAL EXAMPLE:  Count bits in 13 (1101)
 * 
 * Iteration 1: n = 1101, LSB = 1 → count = 1, shift → 110
 * Iteration 2: n = 110,  LSB = 0 → count = 1, shift → 11
 * Iteration 3: n = 11,   LSB = 1 → count = 2, shift → 1
 * Iteration 4: n = 1,    LSB = 1 → count = 3, shift → 0
 * 
 * Result: count = 3 ✓
 * 
 * TIME:  O(log n) or O(32) = O(1) for fixed-size integers
 * SPACE:  O(1)
 * 
 * ============================================================================
 * APPROACH 2: BRIAN KERNIGHAN'S ALGORITHM (Optimal)
 * ============================================================================
 * 
 * KEY INSIGHT:
 * Instead of checking ALL bits, only process the SET bits! 
 * Use n & (n - 1) to remove the rightmost set bit in each iteration.
 * 
 * ALGORITHM:
 * 1. Initialize count = 0
 * 2. While n != 0:
 *    a. Increment count
 *    b. Remove last set bit:  n = n & (n - 1)
 * 3. Return count
 * 
 * VISUAL EXAMPLE: Count bits in 13 (1101)
 * 
 * Iteration 1: n = 1101 (13)
 *   n & (n-1) = 1101 & 1100 = 1100 (12)
 *   count = 1
 * 
 * Iteration 2: n = 1100 (12)
 *   n & (n-1) = 1100 & 1011 = 1000 (8)
 *   count = 2
 * 
 * Iteration 3: n = 1000 (8)
 *   n & (n-1) = 1000 & 0111 = 0000 (0)
 *   count = 3
 * 
 * n = 0, stop
 * Result: count = 3 ✓
 * 
 * WHY IT'S BETTER:
 * - Only k iterations where k = number of set bits
 * - For sparse numbers (few set bits), much faster! 
 * - Example: n = 0x80000000 (only 1 bit set)
 *   Naive: 32 iterations
 *   Brian Kernighan: 1 iteration! 
 * 
 * TIME: O(k) where k = number of set bits
 * SPACE: O(1)
 * 
 * ============================================================================
 * APPROACH 3: BUILT-IN FUNCTION
 * ============================================================================
 * 
 * Use Integer.bitCount(n) in Kotlin/Java
 * 
 * Internally uses optimized table lookup or processor instruction (POPCNT)
 * TIME: O(1) - hardware instruction on modern CPUs
 * SPACE:  O(1)
 * 
 * ============================================================================
 * APPROACH 4: LOOKUP TABLE (Advanced)
 * ============================================================================
 * 
 * Precompute counts for all 8-bit or 16-bit numbers
 * Break n into chunks and look up each chunk
 * 
 * TIME: O(1) - constant number of lookups
 * SPACE: O(2^k) for k-bit chunks
 * 
 * ============================================================================
 * COMPLEXITY COMPARISON
 * ============================================================================
 * 
 * Approach           | Time       | Space | Best For
 * -------------------|------------|-------|---------------------------
 * Naive (check all)  | O(log n)   | O(1)  | Learning, simple code
 * Brian Kernighan    | O(k)       | O(1)  | Sparse bits, interviews
 * Built-in           | O(1)       | O(1)  | Production, efficiency
 * Lookup table       | O(1)       | O(n)  | Ultra-high performance
 * 
 * where k = number of set bits
 * 
 * ============================================================================
 */

package bitmanipulation.basics

class CountSetBits {
    
    /**
     * APPROACH 1: Naive - check each bit
     * TIME: O(log n), SPACE: O(1)
     * 
     * @param n The number to count bits in
     * @return Number of set bits
     */
    fun countSetBitsNaive(n: Int): Int {
        var count = 0
        var num = n
        
        while (num != 0) {
            // Check if LSB is 1
            if ((num and 1) == 1) {
                count++
            }
            // Right shift to check next bit
            num = num shr 1
        }
        
        return count
    }
    
    /**
     * APPROACH 2: Brian Kernighan's Algorithm (Optimal)
     * TIME: O(k) where k = set bits, SPACE: O(1)
     * 
     * This is the BEST approach for interviews!
     * 
     * @param n The number to count bits in
     * @return Number of set bits
     */
    fun countSetBits(n: Int): Int {
        var count = 0
        var num = n
        
        // Each iteration removes one set bit
        while (num != 0) {
            num = num and (num - 1)  // Remove last set bit
            count++
        }
        
        return count
    }
    
    /**
     * APPROACH 3: Built-in function
     * TIME: O(1), SPACE: O(1)
     * 
     * Uses hardware POPCNT instruction if available
     * 
     * @param n The number to count bits in
     * @return Number of set bits
     */
    fun countSetBitsBuiltin(n: Int): Int {
        return Integer.bitCount(n)
    }
    
    /**
     * APPROACH 4: Recursive approach
     * TIME: O(log n), SPACE: O(log n) for recursion stack
     * 
     * @param n The number to count bits in
     * @return Number of set bits
     */
    fun countSetBitsRecursive(n: Int): Int {
        if (n == 0) return 0
        return (n and 1) + countSetBitsRecursive(n shr 1)
    }
    
    /**
     * Count set bits in a range [a, b]
     * TIME: O((b-a) * k), SPACE: O(1)
     */
    fun countSetBitsInRange(a: Int, b:  Int): Int {
        var totalCount = 0
        for (i in a..b) {
            totalCount += countSetBits(i)
        }
        return totalCount
    }
    
    /**
     * Check if number has even or odd number of set bits
     * TIME: O(k), SPACE: O(1)
     */
    fun hasEvenParity(n: Int): Boolean {
        return countSetBits(n) % 2 == 0
    }
    
    /**
     * Visualize bit counting process
     */
    fun visualizeCountingNaive(n: Int) {
        println("NAIVE APPROACH - Check each bit")
        println("Number: $n")
        println("Binary: ${Integer.toBinaryString(n).padStart(8, '0')}")
        println()
        
        var num = n
        var count = 0
        var position = 0
        
        println("Iteration | Number | LSB | Count")
        println("----------|--------|-----|------")
        
        while (num != 0) {
            val lsb = num and 1
            if (lsb == 1) count++
            val binary = Integer.toBinaryString(num).padStart(8, '0')
            println("   $position      | $binary | $lsb   | $count")
            num = num shr 1
            position++
        }
        
        println("\nTotal set bits: $count")
    }
    
    /**
     * Visualize Brian Kernighan's algorithm
     */
    fun visualizeCountingBrianKernighan(n: Int) {
        println("BRIAN KERNIGHAN'S ALGORITHM")
        println("Number: $n")
        println("Binary: ${Integer.toBinaryString(n).padStart(8, '0')}")
        println()
        
        var num = n
        var count = 0
        
        println("Iteration | n        | n-1      | n&(n-1)  | Count")
        println("----------|----------|----------|----------|------")
        
        while (num != 0) {
            val nMinus1 = num - 1
            val result = num and nMinus1
            count++
            
            val nBin = Integer.toBinaryString(num).padStart(8, '0')
            val n1Bin = Integer.toBinaryString(nMinus1).padStart(8, '0')
            val resBin = Integer.toBinaryString(result).padStart(8, '0')
            
            println("   $count      | $nBin | $n1Bin | $resBin | $count")
            
            num = result
        }
        
        println("\nTotal set bits: $count")
    }
}

/**
 * ============================================================================
 * EDGE CASES & APPLICATIONS
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. n = 0: No set bits, return 0
 * 2. n = 1: Single set bit, return 1
 * 3. n = -1 (all 1s in two's complement): 32 set bits
 * 4. n = 2^31 - 1: Maximum positive int, 31 set bits
 * 5. Powers of 2: Exactly 1 set bit
 * 
 * APPLICATIONS:
 * 1. **Hamming Distance**: 
 *    - Count differing bits between two numbers
 *    - Distance = countSetBits(a XOR b)
 *    - Used in:  Error detection, DNA sequence comparison
 * 
 * 2. **Hamming Weight**:
 *    - Weight of binary code
 *    - Error-correcting codes
 *    - Network protocols
 * 
 * 3. **Subset Enumeration**:
 *    - Iterate through subsets of a set
 *    - Each bit represents inclusion of element
 *    - Used in: Dynamic programming on subsets
 * 
 * 4. **Parity Checking**:
 *    - Even/odd parity for error detection
 *    - Network packet validation
 *    - Memory ECC (Error-Correcting Code)
 * 
 * 5. **Binary Decision Diagrams**:
 *    - Graph algorithms
 *    - Boolean function representation
 * 
 * 6. **Cryptography**:
 *    - Key generation
 *    - Hash functions
 *    - Random number quality
 * 
 * 7. **Image Processing**:
 *    - Pixel counting
 *    - Binary image operations
 *    - Feature detection
 * 
 * 8. **Network Algorithms**:
 *    - Routing tables
 *    - Subnet masks
 *    - IP address analysis
 * 
 * 9. **Compiler Optimization**:
 *    - Register allocation
 *    - Liveness analysis
 * 
 * 10. **Machine Learning**:
 *     - Feature hashing
 *     - Sparse vector operations
 *     - Bloom filters
 * 
 * ============================================================================
 */

fun main() {
    val counter = CountSetBits()
    
    println("COUNT SET BITS (Hamming Weight)")
    println("=". repeat(50))
    println()
    
    println("Test 1: Count set bits in 13 (1101) - Naive")
    println("-".repeat(50))
    counter.visualizeCountingNaive(13)
    println()
    
    println("Test 2: Count set bits in 13 (1101) - Brian Kernighan")
    println("-".repeat(50))
    counter.visualizeCountingBrianKernighan(13)
    println()
    
    println("Test 3: Count set bits in 15 (1111)")
    println("-".repeat(50))
    println("Number: 15 (${Integer.toBinaryString(15).padStart(8, '0')})")
    println("Naive:            ${counter.countSetBitsNaive(15)}")
    println("Brian Kernighan:  ${counter.countSetBits(15)}")
    println("Built-in:        ${counter.countSetBitsBuiltin(15)}")
    println("Recursive:       ${counter.countSetBitsRecursive(15)}")
    println("Expected: 4 ✓\n")
    
    println("Test 4: Count set bits in 0")
    println("-".repeat(50))
    println("Result: ${counter.countSetBits(0)}")
    println("Expected: 0 ✓\n")
    
    println("Test 5: Count set bits in power of 2 (16)")
    println("-".repeat(50))
    println("Number:  16 (${Integer.toBinaryString(16).padStart(8, '0')})")
    println("Result: ${counter.countSetBits(16)}")
    println("Expected: 1 ✓\n")
    
    println("Test 6: Performance comparison (sparse vs dense)")
    println("-".repeat(50))
    val sparse = 0x80000000.toInt()  // Only 1 bit set
    val dense = 0x7FFFFFFF           // 31 bits set
    println("Sparse number (1 bit):  iterations ≈ 1")
    println("Dense number (31 bits): iterations ≈ 31")
    println("Brian Kernighan is better for sparse numbers!\n")
    
    println("Test 7: Hamming distance between 5 and 10")
    println("-".repeat(50))
    println("5  = ${Integer.toBinaryString(5).padStart(8, '0')}")
    println("10 = ${Integer.toBinaryString(10).padStart(8, '0')}")
    val xor = 5 xor 10
    println("XOR = ${Integer.toBinaryString(xor).padStart(8, '0')}")
    val distance = counter.countSetBits(xor)
    println("Hamming distance:  $distance")
    println("Expected: 4 ✓\n")
    
    println("Test 8: Even/odd parity check")
    println("-".repeat(50))
    println("13 has even parity?  ${counter.hasEvenParity(13)} (3 bits - odd)")
    println("15 has even parity? ${counter.hasEvenParity(15)} (4 bits - even)")
    println("✓\n")
    
    println("All tests passed! ✓")
}
