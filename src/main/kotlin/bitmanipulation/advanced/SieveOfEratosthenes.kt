/**
 * ============================================================================
 * PROBLEM:  Sieve of Eratosthenes with Bit Manipulation
 * DIFFICULTY: Medium
 * CATEGORY:  Bit Manipulation - Advanced
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Find all prime numbers up to a given number n.  The Sieve of Eratosthenes is
 * an ancient algorithm for finding all primes up to n. We'll optimize it using
 * bit manipulation to reduce space by 8x compared to boolean arrays.
 * 
 * INPUT FORMAT:
 * - An integer n:  n = 30
 * 
 * OUTPUT FORMAT:
 * - List of all primes up to n:  [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
 * 
 * CONSTRAINTS:
 * - 2 <= n <= 10^7
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The Sieve of Eratosthenes works by iteratively marking multiples of each
 * prime as composite. We can optimize space by using bits instead of booleans,
 * storing 8 numbers per byte instead of 1.
 * 
 * KEY INSIGHT:
 * - Every composite number has a prime factor ≤ √n
 * - Instead of checking each number for primality, we mark composites
 * - We can skip even numbers (except 2) to halve the work
 * - Using bits instead of booleans reduces memory by 8x
 * 
 * WHY THIS WORKS:
 * 1. Start with all numbers assumed prime
 * 2. For each prime p, mark all multiples of p as composite
 * 3. Only need to check up to √n (larger primes have no unmarked multiples)
 * 4. Remaining unmarked numbers are prime
 * 
 * ALGORITHM STEPS:
 * 1. Create a bit array representing numbers 2 to n
 * 2. Mark 2 as prime, skip all even numbers after
 * 3. For each odd number p from 3 to √n: 
 *    - If p is not marked, it's prime
 *    - Mark all odd multiples of p (p², p²+2p, p²+4p, ...)
 * 4. Collect all unmarked numbers as primes
 * 
 * VISUAL EXAMPLE:  Sieve up to 30
 * ───────────────────────────────────────
 * Initial: 2  3  4  5  6  7  8  9  10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30
 * 
 * Step 1: Mark multiples of 2 (except 2 itself)
 *         2  3  ✗  5  ✗  7  ✗  9  ✗  11  ✗  13  ✗  15  ✗  17  ✗  19  ✗  21  ✗  23  ✗  25  ✗  27  ✗  29  ✗
 * 
 * Step 2: Mark multiples of 3 (except 3 itself)
 *         2  3  ✗  5  ✗  7  ✗  ✗  ✗  11  ✗  13  ✗  ✗  ✗  17  ✗  19  ✗  ✗  ✗  23  ✗  25  ✗  ✗  ✗  29  ✗
 * 
 * Step 3: Mark multiples of 5 (except 5 itself)
 *         2  3  ✗  5  ✗  7  ✗  ✗  ✗  11  ✗  13  ✗  ✗  ✗  17  ✗  19  ✗  ✗  ✗  23  ✗  ✗  ✗  ✗  ✗  29  ✗
 * 
 * Step 4: √30 ≈ 5.5, so we're done checking
 * Primes: 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 ✓
 * 
 * ============================================================================
 * BIT MANIPULATION OPTIMIZATION
 * ============================================================================
 * 
 * SPACE OPTIMIZATION:
 * - Boolean array:  1 byte per number → n bytes
 * - Bit array: 1 bit per number → n/8 bytes (8x less space!)
 * - Further optimization:  Store only odd numbers → n/16 bytes
 * 
 * BIT OPERATIONS:
 * - Check if bit i is set:  (array[i/32] & (1 << (i%32))) != 0
 * - Set bit i: array[i/32] |= (1 << (i%32))
 * - Clear bit i: array[i/32] &= ~(1 << (i%32))
 * 
 * IMPLEMENTATION CHOICES:
 * - Use IntArray where each Int stores 32 bits
 * - For n=1000, need only 1000/32 = 32 Ints (128 bytes)
 * - Compare to boolean array: 1000 bytes
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n log log n)
 * - For each prime p, we mark n/p multiples
 * - Sum over all primes: n(1/2 + 1/3 + 1/5 + 1/7 + ...)
 * - This sum is the harmonic series of primes ≈ log log n
 * - Total: O(n log log n)
 * 
 * SPACE COMPLEXITY: O(n) or O(n/32) with bit optimization
 * - Standard:  Boolean array of size n → O(n) bytes
 * - Optimized: Bit array → O(n/8) bytes
 * - Further optimized (odd only): O(n/16) bytes
 * 
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example:  Find primes up to 20
 * ────────────────────────────────
 * Input: n = 20
 * 
 * Step 1: Initialize array for numbers 2-20
 * isPrime = [T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T, T]
 * Indices:    2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20
 * 
 * Step 2: Process p=2 (first prime)
 * Mark multiples:  4, 6, 8, 10, 12, 14, 16, 18, 20
 * isPrime = [T, T, F, T, F, T, F, F, F, T, F, T, F, F, F, T, F, T, F]
 * 
 * Step 3: Process p=3 (next unmarked)
 * Mark multiples: 9, 15 (6, 12, 18 already marked)
 * isPrime = [T, T, F, T, F, T, F, F, F, T, F, T, F, F, F, T, F, T, F]
 * 
 * Step 4: Process p=5 (next unmarked)
 * Start from p²=25 > 20, so done
 * 
 * Step 5: Collect primes
 * Result: [2, 3, 5, 7, 11, 13, 17, 19] ✓
 * 
 * ============================================================================
 * OPTIMIZATION TECHNIQUES
 * ============================================================================
 * 
 * 1. SKIP EVEN NUMBERS:
 *    - Only store odd numbers (except 2)
 *    - Reduces space by 50%
 *    - Reduces time by ~50%
 * 
 * 2. START FROM p²:
 *    - For prime p, start marking from p² not 2p
 *    - All multiples < p² already marked by smaller primes
 *    - Significant speedup for large n
 * 
 * 3. BIT PACKING:
 *    - Use bits instead of booleans
 *    - 8x space reduction
 *    - Slight time overhead for bit operations
 * 
 * 4. WHEEL FACTORIZATION:
 *    - Skip multiples of 2, 3, 5 in advance
 *    - More complex but faster for very large n
 */

package bitmanipulation.advanced

import kotlin.math.sqrt

class SieveOfEratosthenes {
    
    /**
     * APPROACH 1: Standard Sieve with Boolean Array
     * 
     * Most readable implementation using boolean array. 
     * Good for understanding the algorithm.
     * 
     * @param n Upper limit to find primes
     * @return List of all primes up to n
     */
    fun findPrimesStandard(n: Int): List<Int> {
        require(n >= 2) { "n must be >= 2" }
        
        // isPrime[i] represents whether (i+2) is prime
        // We offset by 2 to save space (0 and 1 are not prime)
        val isPrime = BooleanArray(n - 1) { true }
        
        // Only need to check up to √n
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        // Start from 2 (index 0)
        for (i in 0..sqrtN - 2) {
            val num = i + 2
            
            // If number is prime, mark all its multiples as composite
            if (isPrime[i]) {
                // Start from num² (earlier multiples already marked)
                var multiple = num * num
                while (multiple <= n) {
                    isPrime[multiple - 2] = false
                    multiple += num
                }
            }
        }
        
        // Collect all primes
        val primes = mutableListOf<Int>()
        for (i in isPrime.indices) {
            if (isPrime[i]) {
                primes.add(i + 2)
            }
        }
        
        return primes
    }
    
    /**
     * APPROACH 2: Optimized Sieve - Skip Even Numbers
     * 
     * Only processes odd numbers, treating 2 as special case.
     * 2x faster and uses 50% less memory. 
     * 
     * @param n Upper limit to find primes
     * @return List of all primes up to n
     */
    fun findPrimesOptimized(n: Int): List<Int> {
        require(n >= 2) { "n must be >= 2" }
        
        if (n == 2) return listOf(2)
        
        // Only store odd numbers:  isPrime[i] represents 2*i+3
        // Index 0 → 3, Index 1 → 5, Index 2 → 7, etc.
        val size = (n - 1) / 2
        val isPrime = BooleanArray(size) { true }
        
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        // Process odd numbers only
        for (i in 0 until (sqrtN - 1) / 2) {
            if (isPrime[i]) {
                val prime = 2 * i + 3
                
                // Mark odd multiples:  prime², prime²+2*prime, prime²+4*prime... 
                // Convert to array index: (prime² - 3) / 2
                var j = (prime * prime - 3) / 2
                while (j < size) {
                    isPrime[j] = false
                    j += prime // Skip by prime (every odd multiple)
                }
            }
        }
        
        // Collect primes:  2 + all unmarked odds
        val primes = mutableListOf(2)
        for (i in isPrime.indices) {
            if (isPrime[i]) {
                primes.add(2 * i + 3)
            }
        }
        
        return primes
    }
    
    /**
     * APPROACH 3: Bit-Packed Sieve
     * 
     * Uses bit manipulation to pack 32 numbers per Int.
     * 32x space efficient compared to boolean array.
     * Slightly slower due to bit operations but worth it for large n.
     * 
     * @param n Upper limit to find primes
     * @return List of all primes up to n
     */
    fun findPrimesBitPacked(n: Int): List<Int> {
        require(n >= 2) { "n must be >= 2" }
        
        // Bit array:  each Int stores 32 bits
        // bits[i] stores numbers [32i+2, 32i+33]
        val size = (n - 1) / 32 + 1
        val bits = IntArray(size) { -1 } // All 1s (assume all prime initially)
        
        // Helper:  Check if bit for number x is set (is prime)
        fun isPrime(x: Int): Boolean {
            val index = (x - 2) / 32
            val bitPos = (x - 2) % 32
            return (bits[index] and (1 shl bitPos)) != 0
        }
        
        // Helper: Clear bit for number x (mark as composite)
        fun markComposite(x: Int) {
            val index = (x - 2) / 32
            val bitPos = (x - 2) % 32
            bits[index] = bits[index] and (1 shl bitPos).inv()
        }
        
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        // Sieve process
        for (p in 2.. sqrtN) {
            if (isPrime(p)) {
                // Mark multiples starting from p²
                var multiple = p * p
                while (multiple <= n) {
                    markComposite(multiple)
                    multiple += p
                }
            }
        }
        
        // Collect primes
        val primes = mutableListOf<Int>()
        for (num in 2.. n) {
            if (isPrime(num)) {
                primes.add(num)
            }
        }
        
        return primes
    }
    
    /**
     * Count primes up to n (without storing them)
     * Useful when you only need the count, not the primes themselves. 
     * 
     * @param n Upper limit
     * @return Count of primes up to n
     */
    fun countPrimes(n: Int): Int {
        require(n >= 2) { "n must be >= 2" }
        
        if (n == 2) return 1
        
        val size = (n - 1) / 2
        val isPrime = BooleanArray(size) { true }
        val sqrtN = sqrt(n. toDouble()).toInt()
        
        for (i in 0 until (sqrtN - 1) / 2) {
            if (isPrime[i]) {
                val prime = 2 * i + 3
                var j = (prime * prime - 3) / 2
                while (j < size) {
                    isPrime[j] = false
                    j += prime
                }
            }
        }
        
        // Count:  1 (for 2) + count of unmarked odds
        return 1 + isPrime.count { it }
    }
}

/**
 * ============================================================================
 * TEST CASES & EXAMPLES
 * ============================================================================
 */
fun main() {
    val sieve = SieveOfEratosthenes()
    
    println("=". repeat(70))
    println("SIEVE OF ERATOSTHENES TESTS")
    println("=".repeat(70))
    
    // Test Case 1: Small range
    println("\n✅ Test Case 1: Find primes up to 30")
    println("─".repeat(50))
    val primes1 = sieve. findPrimesStandard(30)
    println("Primes up to 30: $primes1")
    println("Count: ${primes1.size}")
    // Expected: [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
    
    // Test Case 2: Minimal input
    println("\n✅ Test Case 2: Find primes up to 10")
    println("─".repeat(50))
    val primes2 = sieve.findPrimesOptimized(10)
    println("Primes up to 10: $primes2")
    println("Count: ${primes2.size}")
    // Expected: [2, 3, 5, 7]
    
    // Test Case 3: Larger range
    println("\n✅ Test Case 3: Find primes up to 100")
    println("─".repeat(50))
    val primes3 = sieve.findPrimesBitPacked(100)
    println("Primes up to 100: $primes3")
    println("Count: ${primes3.size}")
    // Expected: 25 primes
    
    // Test Case 4: Count only (efficient)
    println("\n✅ Test Case 4: Count primes up to 1000")
    println("─".repeat(50))
    val count4 = sieve.countPrimes(1000)
    println("Number of primes up to 1000: $count4")
    // Expected: 168
    
    // Test Case 5: First 20 primes
    println("\n✅ Test Case 5: First 20 primes")
    println("─".repeat(50))
    val primes5 = sieve.findPrimesOptimized(100).take(20)
    println("First 20 primes: $primes5")
    // Expected: [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71]
    
    // Performance comparison
    println("\n" + "=".repeat(70))
    println("PERFORMANCE COMPARISON")
    println("=".repeat(70))
    
    val testN = 1000000
    println("\nFinding all primes up to $testN")
    println("─".repeat(50))
    
    // Standard approach
    var start = System.currentTimeMillis()
    val result1 = sieve.findPrimesStandard(testN)
    var elapsed1 = System.currentTimeMillis() - start
    println("Standard (Boolean array): ${result1.size} primes in ${elapsed1}ms")
    
    // Optimized (skip evens)
    start = System. currentTimeMillis()
    val result2 = sieve.findPrimesOptimized(testN)
    var elapsed2 = System.currentTimeMillis() - start
    println("Optimized (Skip evens): ${result2.size} primes in ${elapsed2}ms")
    println("Speedup: ${elapsed1. toDouble() / elapsed2.toDouble()}x faster")
    
    // Bit-packed
    start = System. currentTimeMillis()
    val result3 = sieve.findPrimesBitPacked(testN)
    var elapsed3 = System.currentTimeMillis() - start
    println("Bit-packed (32x space): ${result3.size} primes in ${elapsed3}ms")
    
    // Verify all methods give same result
    println("\n✅ Verification:  All methods agree:  ${result1 == result2 && result2 == result3}")
    
    // Memory usage estimate
    println("\n" + "=".repeat(70))
    println("MEMORY USAGE ESTIMATES for n = $testN")
    println("=".repeat(70))
    println("Standard boolean array: ${testN / 1024} KB")
    println("Optimized (odd only): ${testN / 2 / 1024} KB")
    println("Bit-packed:  ${testN / 32 / 1024} KB")
}
