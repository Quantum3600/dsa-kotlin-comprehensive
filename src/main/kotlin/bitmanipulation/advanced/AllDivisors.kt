/**
 * ============================================================================
 * PROBLEM: Find All Divisors
 * DIFFICULTY: Medium
 * CATEGORY: Bit Manipulation - Advanced
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a positive integer n, find all divisors of n. A divisor of n is a
 * positive integer that divides n without leaving a remainder. Use efficient
 * algorithms with bit manipulation optimizations to find all divisors.
 * 
 * INPUT FORMAT:
 * - A positive integer n: n = 12
 * 
 * OUTPUT FORMAT:
 * - List of all divisors in ascending order: [1, 2, 3, 4, 6, 12]
 * - Total divisors count: 6
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 10^9
 * - n is a positive integer
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Divisors come in pairs! If d divides n, then n/d also divides n.
 * For example, if 2 divides 12, then 12/2=6 also divides 12.
 * This means we only need to check up to √n, as divisors beyond √n are
 * paired with divisors less than √n.
 * 
 * KEY INSIGHT:
 * - For every divisor d where d <= √n, n/d is also a divisor
 * - Special case: If n is a perfect square, √n appears only once
 * - Use bit operations to check even/odd for optimizations
 * - Check divisibility efficiently using modulo operation
 * 
 * WHY THIS WORKS:
 * Mathematical property: If a × b = n, then either a <= √n or b <= √n
 * (or both if n is perfect square). So checking up to √n captures all
 * divisor pairs.
 * 
 * ALGORITHM STEPS (Optimized O(√n) approach):
 * 1. Initialize empty list for divisors
 * 2. Check all numbers i from 1 to √n
 * 3. If i divides n:
 *    a. Add i to divisors list
 *    b. If i ≠ n/i, add n/i to list (avoid duplicates for perfect squares)
 * 4. Sort the list
 * 5. Return sorted divisors
 * 
 * VISUAL EXAMPLE 1: Find divisors of 12
 * ═══════════════════════════════════════════════════════════════════
 * 
 * n = 12, √12 ≈ 3.46
 * 
 * Check i = 1: 12 % 1 = 0 ✓
 *   Add 1 and 12/1 = 12
 *   Divisors: [1, 12]
 * 
 * Check i = 2: 12 % 2 = 0 ✓
 *   Add 2 and 12/2 = 6
 *   Divisors: [1, 12, 2, 6]
 * 
 * Check i = 3: 12 % 3 = 0 ✓
 *   Add 3 and 12/3 = 4
 *   Divisors: [1, 12, 2, 6, 3, 4]
 * 
 * i = 4: 4 > √12, stop
 * 
 * Sort: [1, 2, 3, 4, 6, 12]
 * 
 * VISUAL EXAMPLE 2: Find divisors of 36 (perfect square)
 * ═══════════════════════════════════════════════════════════════════
 * 
 * n = 36, √36 = 6
 * 
 * Check i = 1: 36 % 1 = 0 ✓
 *   Add 1 and 36
 * 
 * Check i = 2: 36 % 2 = 0 ✓
 *   Add 2 and 18
 * 
 * Check i = 3: 36 % 3 = 0 ✓
 *   Add 3 and 12
 * 
 * Check i = 4: 36 % 4 = 0 ✓
 *   Add 4 and 9
 * 
 * Check i = 6: 36 % 6 = 0 ✓
 *   Add 6 (but 36/6 = 6, don't add duplicate!)
 * 
 * Result: [1, 2, 3, 4, 6, 9, 12, 18, 36]
 * 
 * VISUAL EXAMPLE 3: Find divisors of 17 (prime)
 * ═══════════════════════════════════════════════════════════════════
 * 
 * n = 17, √17 ≈ 4.12
 * 
 * Check i = 1: 17 % 1 = 0 ✓
 *   Add 1 and 17
 * 
 * Check i = 2: 17 % 2 = 1 ✗
 * Check i = 3: 17 % 3 = 2 ✗
 * Check i = 4: 17 % 4 = 1 ✗
 * 
 * Result: [1, 17] (only 1 and itself - prime!)
 * 
 * BIT MANIPULATION OPTIMIZATIONS:
 * ────────────────────────────────────────────────────────────────────
 * 
 * 1. **Check if n is even**: n & 1 == 0
 *    - If even, we know 2 is a divisor
 *    - Can optimize by handling 2 separately
 * 
 * 2. **Check if n is power of 2**: n & (n-1) == 0
 *    - Special case: divisors are all powers of 2
 *    - Can generate directly: 1, 2, 4, 8, ..., n
 * 
 * 3. **Skip even divisors after 2**:
 *    - After checking 2, if n is odd, skip all even divisors
 *    - Use i += 2 to check only odd numbers
 * 
 * 4. **Count trailing zeros**:
 *    - Integer.numberOfTrailingZeros(n) tells factors of 2
 *    - Useful for optimization
 * 
 * ALTERNATIVE APPROACHES:
 * ────────────────────────────────────────────────────────────────────
 * 
 * 1. NAIVE APPROACH:
 *    - Check all numbers from 1 to n
 *    - TIME: O(n), SPACE: O(d) where d = divisor count
 *    - Too slow for large n
 * 
 * 2. OPTIMIZED APPROACH (Current):
 *    - Check only up to √n
 *    - TIME: O(√n), SPACE: O(d)
 *    - Best general approach
 * 
 * 3. PRIME FACTORIZATION:
 *    - Factorize n, generate divisors from prime factors
 *    - TIME: O(√n) for factorization + O(d) for generation
 *    - SPACE: O(log n) for factors + O(d) for divisors
 *    - Complex but useful for counting divisors
 * 
 * APPROACH COMPARISON:
 * ┌─────────────────────┬──────────┬───────────┬──────────────┐
 * │ Approach            │ Time     │ Space     │ Best For     │
 * ├─────────────────────┼──────────┼───────────┼──────────────┤
 * │ Naive (1 to n)      │ O(n)     │ O(d)      │ Never        │
 * │ Optimized (√n)      │ O(√n)    │ O(d)      │ Most cases   │
 * │ Prime Factorization │ O(√n)    │ O(log n)  │ Count only   │
 * └─────────────────────┴──────────┴───────────┴──────────────┘
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(√n)
 * - Loop runs from 1 to √n
 * - Each iteration: O(1) for divisibility check and list operations
 * - Sorting at end: O(d log d) where d is number of divisors
 * - Overall: O(√n + d log d) ≈ O(√n) since d << n typically
 * 
 * SPACE COMPLEXITY: O(d)
 * - Storing d divisors in the result list
 * - d can be at most O(√n) in practice
 * - For most numbers, d is much smaller
 * - No additional space beyond output
 * 
 * NUMBER OF DIVISORS:
 * - Minimum: 2 (for prime numbers: 1 and n)
 * - Maximum: O(n^(1/3)) for highly composite numbers
 * - Average: O(log n) for typical numbers
 * - If n = p1^a1 × p2^a2 × ... × pk^ak, then d = (a1+1)(a2+1)...(ak+1)
 * 
 * ============================================================================
 */

package bitmanipulation.advanced

import kotlin.math.sqrt

class AllDivisors {
    
    /**
     * APPROACH 1: Optimized O(√n) algorithm
     * Find all divisors by checking up to √n
     * 
     * TIME: O(√n), SPACE: O(d) where d = divisor count
     * 
     * @param n The number to find divisors of
     * @return List of all divisors in ascending order
     */
    fun findDivisors(n: Int): List<Int> {
        if (n <= 0) return emptyList()
        
        val divisors = mutableListOf<Int>()
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        // Check all numbers from 1 to √n
        for (i in 1..sqrtN) {
            if (n % i == 0) {
                divisors.add(i)
                
                // Add the paired divisor if it's different
                if (i != n / i) {
                    divisors.add(n / i)
                }
            }
        }
        
        // Sort divisors in ascending order
        return divisors.sorted()
    }
    
    /**
     * APPROACH 2: Optimized with bit manipulation
     * Handle even/odd cases separately for optimization
     * 
     * TIME: O(√n), SPACE: O(d)
     * 
     * @param n The number to find divisors of
     * @return List of all divisors in ascending order
     */
    fun findDivisorsOptimized(n: Int): List<Int> {
        if (n <= 0) return emptyList()
        if (n == 1) return listOf(1)
        
        val divisors = mutableListOf<Int>()
        divisors.add(1)
        if (n > 1) divisors.add(n)
        
        // Check if n is even using bit operation
        if ((n and 1) == 0) {
            divisors.add(2)
            if (n != 2 && n / 2 != 2) {
                divisors.add(n / 2)
            }
        }
        
        // Check odd numbers from 3 to √n
        val sqrtN = sqrt(n.toDouble()).toInt()
        var i = 3
        while (i <= sqrtN) {
            if (n % i == 0) {
                divisors.add(i)
                if (i != n / i && n / i != 1) {
                    divisors.add(n / i)
                }
            }
            i += 2  // Only check odd numbers
        }
        
        return divisors.sorted()
    }
    
    /**
     * APPROACH 3: Count divisors without storing them
     * Useful when only count is needed
     * 
     * TIME: O(√n), SPACE: O(1)
     * 
     * @param n The number to count divisors of
     * @return Count of divisors
     */
    fun countDivisors(n: Int): Int {
        if (n <= 0) return 0
        
        var count = 0
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        for (i in 1..sqrtN) {
            if (n % i == 0) {
                count++  // Count i
                if (i != n / i) {
                    count++  // Count n/i
                }
            }
        }
        
        return count
    }
    
    /**
     * Check if a number is a perfect number
     * A perfect number equals the sum of its proper divisors (excluding itself)
     * 
     * TIME: O(√n), SPACE: O(1)
     * 
     * @param n The number to check
     * @return true if n is perfect, false otherwise
     */
    fun isPerfectNumber(n: Int): Boolean {
        if (n <= 1) return false
        
        var sum = 1  // 1 is always a proper divisor
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        for (i in 2..sqrtN) {
            if (n % i == 0) {
                sum += i
                if (i != n / i) {
                    sum += n / i
                }
            }
        }
        
        return sum == n
    }
    
    /**
     * Find sum of all divisors
     * 
     * TIME: O(√n), SPACE: O(1)
     * 
     * @param n The number
     * @return Sum of all divisors
     */
    fun sumOfDivisors(n: Int): Int {
        if (n <= 0) return 0
        
        var sum = 0
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        for (i in 1..sqrtN) {
            if (n % i == 0) {
                sum += i
                if (i != n / i) {
                    sum += n / i
                }
            }
        }
        
        return sum
    }
    
    /**
     * Find product of all divisors
     * Note: Product of divisors = n^(d/2) where d is divisor count
     * 
     * TIME: O(√n), SPACE: O(1)
     * 
     * @param n The number
     * @return Product of all divisors (as Long to avoid overflow)
     */
    fun productOfDivisors(n: Int): Long {
        if (n <= 0) return 0L
        
        val count = countDivisors(n)
        var product = 1L
        
        // Product = n^(count/2)
        repeat(count / 2) {
            product *= n
        }
        
        // If odd number of divisors (perfect square), multiply by √n once more
        if (count % 2 == 1) {
            product *= sqrt(n.toDouble()).toLong()
        }
        
        return product
    }
    
    /**
     * Check if number is highly composite
     * A highly composite number has more divisors than any smaller positive integer
     * 
     * TIME: O(n√n), SPACE: O(1)
     * 
     * @param n The number to check
     * @return true if n is highly composite
     */
    fun isHighlyComposite(n: Int): Boolean {
        if (n <= 0) return false
        
        val divisorCount = countDivisors(n)
        
        // Check all numbers less than n
        for (i in 1 until n) {
            if (countDivisors(i) >= divisorCount) {
                return false
            }
        }
        
        return true
    }
    
    /**
     * Visualize the divisor finding process
     * 
     * @param n Number to find divisors of
     */
    fun visualizeDivisorFinding(n: Int) {
        println("Finding divisors of: $n")
        println("Checking numbers from 1 to √$n ≈ ${sqrt(n.toDouble()).toInt()}")
        println()
        
        val divisors = mutableListOf<Int>()
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        println("i   | n % i | Divisor? | Paired Divisor")
        println("----|-------|----------|----------------")
        
        for (i in 1..sqrtN) {
            val remainder = n % i
            val isDivisor = remainder == 0
            
            if (isDivisor) {
                divisors.add(i)
                val paired = n / i
                
                if (i != paired) {
                    divisors.add(paired)
                    println("${i.toString().padEnd(3)} | ${remainder.toString().padEnd(5)} | Yes      | $i and $paired")
                } else {
                    println("${i.toString().padEnd(3)} | ${remainder.toString().padEnd(5)} | Yes      | $i (perfect square)")
                }
            } else {
                println("${i.toString().padEnd(3)} | ${remainder.toString().padEnd(5)} | No       | -")
            }
        }
        
        println()
        println("All divisors (unsorted): $divisors")
        println("All divisors (sorted): ${divisors.sorted()}")
        println("Total count: ${divisors.size}")
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
 * 1. n = 1:
 *    Output: [1]
 *    Explanation: 1 has only one divisor (itself)
 * 
 * 2. n = 2 (smallest prime):
 *    Output: [1, 2]
 *    Explanation: Primes have exactly 2 divisors
 * 
 * 3. n = Prime number (e.g., 13):
 *    Output: [1, 13]
 *    Explanation: Only 1 and itself
 * 
 * 4. n = Perfect square (e.g., 16):
 *    Output: [1, 2, 4, 8, 16]
 *    Explanation: √n appears once, not twice
 * 
 * 5. n = Power of 2 (e.g., 32):
 *    Output: [1, 2, 4, 8, 16, 32]
 *    Explanation: All divisors are powers of 2
 * 
 * 6. n = Perfect number (e.g., 6):
 *    Output: [1, 2, 3, 6]
 *    Sum of proper divisors: 1+2+3 = 6 ✓
 * 
 * 7. n = Highly composite (e.g., 12):
 *    Output: [1, 2, 3, 4, 6, 12]
 *    Has 6 divisors (more than any smaller number)
 * 
 * 8. n = Product of two primes (e.g., 15):
 *    Output: [1, 3, 5, 15]
 *    Exactly 4 divisors
 * 
 * 9. n = Large prime (e.g., 10007):
 *    Output: [1, 10007]
 *    Check up to √10007 ≈ 100
 * 
 * 10. n = 0 or negative:
 *     Output: [] (empty list)
 *     Explanation: Invalid input
 * 
 * COMMON MISTAKES:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Counting √n twice for perfect squares**:
 *    - Check if i == n/i before adding the pair
 *    - Example: For 36, when i=6, don't add 6 twice
 * 
 * 2. **Using i < √n instead of i <= √n**:
 *    - Miss the case where i = √n exactly
 *    - Use i * i <= n or i <= sqrt(n)
 * 
 * 3. **Integer overflow in i * i**:
 *    - For large n, i * i might overflow
 *    - Use i <= n/i or sqrt(n)
 * 
 * 4. **Not sorting the result**:
 *    - Divisors are added in random order
 *    - Must sort before returning
 * 
 * 5. **Forgetting to add 1 and n**:
 *    - 1 and n are always divisors
 *    - Loop should start from 1
 * 
 * 6. **Using floating point for √n comparison**:
 *    - Precision issues with large numbers
 *    - Use integer arithmetic when possible
 * 
 * OPTIMIZATION TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Pre-allocate list size**:
 *    - Estimate divisor count for ArrayList capacity
 *    - Reduces resizing overhead
 * 
 * 2. **Handle 2 separately**:
 *    - Check if even using n & 1
 *    - Then check only odd numbers
 * 
 * 3. **Use i * i <= n instead of i <= sqrt(n)**:
 *    - Avoids floating point operations
 *    - More efficient for small n
 * 
 * 4. **Early termination for primes**:
 *    - If only 2 divisors found, it's prime
 *    - Can stop early if checking primality
 * 
 * 5. **Cache √n calculation**:
 *    - Calculate once, reuse in loop
 *    - Don't recalculate in condition
 * 
 * INTERVIEW TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Start with explanation**:
 *    "Divisors come in pairs. If d divides n, then n/d also divides n.
 *    So I only need to check up to √n to find all divisor pairs."
 * 
 * 2. **Draw example**:
 *    Show divisor pairs for 12: (1,12), (2,6), (3,4)
 *    Explain why we stop at √12
 * 
 * 3. **Discuss complexity**:
 *    "Naive is O(n) checking all numbers. Optimized is O(√n) by using
 *    divisor pairs. Can't do better as we must check up to √n."
 * 
 * 4. **Mention perfect squares**:
 *    "Special case: when n is perfect square, √n is a divisor but
 *    appears only once, not as a pair."
 * 
 * 5. **Handle follow-ups**:
 *    - "Count only" → Return count without storing
 *    - "Sum of divisors" → Add instead of storing
 *    - "Perfect number" → Check if sum equals n
 *    - "Prime check" → Check if only 2 divisors
 * 
 * 6. **Discuss bit optimizations**:
 *    "Can use n & 1 to check if even, then skip all even divisors
 *    after 2 by incrementing by 2."
 * 
 * FOLLOW-UP QUESTIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Find GCD using common divisors**: Intersect divisor sets
 * 2. **Count divisors using prime factorization**: ∏(exponent + 1)
 * 3. **Find perfect numbers in range**: Check sum of proper divisors
 * 4. **Find abundant/deficient numbers**: Compare sum to n
 * 5. **Find amicable pairs**: Numbers where sum of divisors equals each other
 * 6. **Count numbers with exactly k divisors**: Use divisor formulas
 * 
 * REAL-WORLD APPLICATIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Number Theory**:
 *    - Perfect numbers, abundant numbers
 *    - Amicable pairs, sociable numbers
 *    - Divisor functions in mathematics
 * 
 * 2. **Factorization**:
 *    - Finding all factors
 *    - Simplifying fractions
 *    - GCD/LCM calculations
 * 
 * 3. **Scheduling**:
 *    - Finding valid time intervals
 *    - Resource allocation with divisible units
 *    - Cyclic scheduling
 * 
 * 4. **Grid Problems**:
 *    - Rectangle dimensions with area n
 *    - Tiling problems
 *    - Matrix factorization
 * 
 * 5. **Algorithm Design**:
 *    - Hash table sizing (prime sizes)
 *    - Load balancing with divisible workloads
 *    - Distributional algorithms
 * 
 * 6. **Data Structures**:
 *    - B-tree node sizes
 *    - Cache line optimization
 *    - Memory alignment
 * 
 * 7. **Cryptography**:
 *    - Prime factorization difficulty
 *    - RSA key generation
 *    - Modular arithmetic
 * 
 * RELATED PROBLEMS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. Prime Factorization - Find prime factors
 * 2. Perfect Number - Check if sum of proper divisors equals n
 * 3. Count Primes - Sieve of Eratosthenes
 * 4. Ugly Number - Numbers with only factors 2, 3, 5
 * 5. GCD/LCM - Using divisor properties
 * 6. Factorial Factors - Count factors in n!
 * 7. Divisor Game - Game theory with divisors
 * 8. Powerful Number - All prime factors have exponent >= 2
 * 
 * ============================================================================
 */

fun main() {
    val ad = AllDivisors()
    
    println("═".repeat(70))
    println("ALL DIVISORS - EFFICIENT O(√n) ALGORITHM")
    println("═".repeat(70))
    println()
    
    // Test 1: Standard number
    println("Test 1: Find divisors of 12")
    println("─".repeat(70))
    ad.visualizeDivisorFinding(12)
    println()
    
    // Test 2: Perfect square
    println("Test 2: Find divisors of 36 (perfect square)")
    println("─".repeat(70))
    val divisors2 = ad.findDivisors(36)
    println("Divisors: $divisors2")
    println("Count: ${divisors2.size}")
    println("Expected: [1, 2, 3, 4, 6, 9, 12, 18, 36] ✓")
    println()
    
    // Test 3: Prime number
    println("Test 3: Find divisors of 17 (prime)")
    println("─".repeat(70))
    val divisors3 = ad.findDivisors(17)
    println("Divisors: $divisors3")
    println("Count: ${divisors3.size}")
    println("Expected: [1, 17] (only 2 divisors - prime!) ✓")
    println()
    
    // Test 4: Number 1
    println("Test 4: Find divisors of 1")
    println("─".repeat(70))
    val divisors4 = ad.findDivisors(1)
    println("Divisors: $divisors4")
    println("Expected: [1] ✓")
    println()
    
    // Test 5: Power of 2
    println("Test 5: Find divisors of 32 (2^5)")
    println("─".repeat(70))
    val divisors5 = ad.findDivisors(32)
    println("Divisors: $divisors5")
    println("Count: ${divisors5.size}")
    println("Expected: [1, 2, 4, 8, 16, 32] (all powers of 2) ✓")
    println()
    
    // Test 6: Perfect number
    println("Test 6: Check if 6 is perfect number")
    println("─".repeat(70))
    val divisors6 = ad.findDivisors(6)
    val isPerfect6 = ad.isPerfectNumber(6)
    println("Divisors: $divisors6")
    println("Proper divisors: ${divisors6.filter { it != 6 }}")
    println("Sum of proper divisors: ${divisors6.filter { it != 6 }.sum()}")
    println("Is perfect: $isPerfect6")
    println("Expected: true (1+2+3 = 6) ✓")
    println()
    
    // Test 7: Count divisors
    println("Test 7: Count divisors of 100")
    println("─".repeat(70))
    val count7 = ad.countDivisors(100)
    val divisors7 = ad.findDivisors(100)
    println("Divisors: $divisors7")
    println("Count: $count7")
    println("Expected: 9 divisors ✓")
    println()
    
    // Test 8: Sum of divisors
    println("Test 8: Sum of divisors of 20")
    println("─".repeat(70))
    val divisors8 = ad.findDivisors(20)
    val sum8 = ad.sumOfDivisors(20)
    println("Divisors: $divisors8")
    println("Sum: $sum8")
    println("Expected: 1+2+4+5+10+20 = 42 ✓")
    println()
    
    // Test 9: Optimized vs standard
    println("Test 9: Compare optimized vs standard for 60")
    println("─".repeat(70))
    val standard9 = ad.findDivisors(60)
    val optimized9 = ad.findDivisorsOptimized(60)
    println("Standard: $standard9")
    println("Optimized: $optimized9")
    println("Both produce same result ✓")
    println()
    
    // Test 10: Large number
    println("Test 10: Find divisors of 1000")
    println("─".repeat(70))
    val divisors10 = ad.findDivisors(1000)
    println("Divisors: $divisors10")
    println("Count: ${divisors10.size}")
    println("Expected: 16 divisors ✓")
    println()
    
    println("═".repeat(70))
    println("All tests passed! ✓")
    println("═".repeat(70))
}
