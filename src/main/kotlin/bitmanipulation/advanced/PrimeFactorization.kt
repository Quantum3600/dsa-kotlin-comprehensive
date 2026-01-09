/**
 * ============================================================================
 * PROBLEM: Prime Factorization using Bit Manipulation
 * DIFFICULTY:  Medium
 * CATEGORY: Bit Manipulation - Advanced
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an integer n, find all prime factors of n and their powers.  Prime
 * factorization is the process of breaking down a composite number into its
 * prime factors. We'll optimize this using bit manipulation techniques.
 * 
 * INPUT FORMAT:
 * - An integer n:  n = 360
 * 
 * OUTPUT FORMAT:
 * - Map of prime factors to their powers:  {2=3, 3=2, 5=1}
 * - Meaning: 360 = 2³ × 3² × 5¹
 * 
 * CONSTRAINTS:
 * - 2 <= n <= 2^31 - 1
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Prime factorization finds all prime numbers that multiply to give n. We can
 * optimize the traditional approach using bit manipulation for handling powers
 * of 2, which appear frequently in factorizations.
 * 
 * KEY INSIGHT: 
 * - Powers of 2 can be counted using bit manipulation (trailing zeros)
 * - For odd primes, we only need to check up to √n
 * - Once we extract all 2s, the number becomes odd, making division by 2 unnecessary
 * 
 * WHY THIS WORKS:
 * 1. Every composite number can be uniquely expressed as a product of primes
 * 2. If a prime p divides n, we can keep dividing until it doesn't
 * 3. The power is the count of successful divisions
 * 4. Using bit manipulation for 2s is faster than repeated division
 * 
 * ALGORITHM STEPS:
 * 1. Count and remove all factors of 2 using bit manipulation
 * 2. Now n is odd, check odd numbers from 3 to √n
 * 3. For each factor found, count its power
 * 4. If n > 1 after all divisions, n itself is a prime factor
 * 
 * VISUAL EXAMPLE:  Factorize 360
 * ───────────────────────────────────────
 * n = 360
 * Binary: 101101000 (notice 3 trailing zeros = 3 factors of 2)
 * 
 * Step 1: Extract all 2s using bit manipulation
 * Count trailing zeros: 3
 * n = 360 >> 3 = 45 (divide by 2³)
 * Factors:  {2: 3}
 * 
 * Step 2: n = 45, check odd divisors starting from 3
 * 45 % 3 = 0 ✓
 * 45 / 3 = 15, count = 1
 * 15 % 3 = 0 ✓
 * 15 / 3 = 5, count = 2
 * 5 % 3 ≠ 0
 * Factors: {2: 3, 3: 2}
 * 
 * Step 3: n = 5, check divisor 5
 * 5 % 5 = 0 ✓
 * 5 / 5 = 1
 * Factors: {2: 3, 3: 2, 5: 1}
 * 
 * Result: 360 = 2³ × 3² × 5¹ ✓
 * 
 * ============================================================================
 * APPROACH 1:  OPTIMIZED WITH BIT MANIPULATION
 * ============================================================================
 * 
 * OPTIMIZATION:
 * - Use countTrailingZeros() to find power of 2 in O(1)
 * - Right shift to remove all 2s efficiently
 * - Only check odd divisors after removing 2s
 * 
 * TIME COMPLEXITY: O(√n)
 * - Counting trailing zeros: O(1)
 * - Checking divisors up to √n: O(√n)
 * 
 * SPACE COMPLEXITY:  O(log n)
 * - Map to store factors (at most log₂(n) different primes)
 * 
 * ============================================================================
 * APPROACH 2: TRADITIONAL METHOD
 * ============================================================================
 * 
 * ALGORITHM:
 * - Divide by 2 repeatedly until odd
 * - Check all odd numbers from 3 to √n
 * - More straightforward but slower for large numbers with many factors of 2
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(√n)
 * - For each prime p ≤ √n, we divide n by p until it's no longer divisible
 * - In the worst case (n is prime), we check all numbers up to √n
 * - Bit manipulation optimization reduces constant factors for powers of 2
 * 
 * SPACE COMPLEXITY: O(log n)
 * - The number of distinct prime factors is at most log₂(n)
 * - Example: 2^30 has only 1 prime factor, while 30 = 2×3×5 has 3
 * 
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Factorize 360
 * ────────────────────────
 * Input: n = 360
 * Binary:  101101000
 * 
 * Step 1: Count trailing zeros
 * trailing = 3 (360 has 3 trailing zero bits)
 * n = 360 >> 3 = 45
 * Result: {2: 3}
 * 
 * Step 2: Check divisor 3
 * 45 % 3 = 0, power = 0
 * 45 / 3 = 15, power = 1
 * 15 / 3 = 5, power = 2
 * Result: {2: 3, 3: 2}
 * 
 * Step 3: Check divisor 5
 * 5 % 5 = 0, power = 0
 * 5 / 5 = 1, power = 1
 * Result: {2: 3, 3: 2, 5: 1}
 * 
 * Final:  360 = 2³ × 3² × 5¹ ✓
 * 
 * Example 2: Factorize 128
 * ────────────────────────
 * Input: n = 128
 * Binary:  10000000
 * 
 * Step 1: Count trailing zeros
 * trailing = 7 (128 = 2⁷)
 * n = 128 >> 7 = 1
 * Result: {2: 7}
 * 
 * Final: 128 = 2⁷ ✓
 * 
 * Example 3: Factorize 17 (prime)
 * ────────────────────────────────
 * Input: n = 17
 * Binary: 10001
 * 
 * Step 1: Count trailing zeros
 * trailing = 0 (17 is odd)
 * 
 * Step 2: Check divisors 3 to √17 ≈ 4
 * None divide 17
 * 
 * Step 3: n = 17 > 1
 * 17 itself is prime
 * Result: {17: 1}
 * 
 * Final: 17 = 17¹ ✓
 */

package bitmanipulation.advanced

class PrimeFactorization {
    
    /**
     * APPROACH 1: Optimized prime factorization using bit manipulation
     * 
     * Uses countTrailingZeroBits() to efficiently extract all factors of 2,
     * then checks only odd divisors.
     * 
     * @param n The number to factorize
     * @return Map of prime factors to their powers
     */
    fun factorize(n: Int): Map<Int, Int> {
        require(n >= 2) { "Input must be >= 2" }
        
        val factors = mutableMapOf<Int, Int>()
        var num = n
        
        // Step 1: Extract all factors of 2 using bit manipulation
        // countTrailingZeroBits() gives us the power of 2 in O(1)
        val powerOf2 = num.countTrailingZeroBits()
        if (powerOf2 > 0) {
            factors[2] = powerOf2
            // Right shift by powerOf2 is equivalent to dividing by 2^powerOf2
            num = num shr powerOf2
        }
        
        // Step 2: Check odd divisors from 3 to √n
        // After removing all 2s, num is now odd, so we only check odd divisors
        var divisor = 3
        while (divisor * divisor <= num) {
            var power = 0
            
            // Count how many times divisor divides num
            while (num % divisor == 0) {
                num /= divisor
                power++
            }
            
            // If divisor was a factor, store its power
            if (power > 0) {
                factors[divisor] = power
            }
            
            // Move to next odd number (skip even numbers)
            divisor += 2
        }
        
        // Step 3: If num > 1, then it's a prime factor itself
        // This handles cases where n is prime or has a large prime factor
        if (num > 1) {
            factors[num] = 1
        }
        
        return factors
    }
    
    /**
     * APPROACH 2: Traditional prime factorization without bit manipulation
     * 
     * Uses repeated division for all primes including 2.
     * Simpler but slightly slower for numbers with many factors of 2.
     * 
     * @param n The number to factorize
     * @return Map of prime factors to their powers
     */
    fun factorizeTraditional(n: Int): Map<Int, Int> {
        require(n >= 2) { "Input must be >= 2" }
        
        val factors = mutableMapOf<Int, Int>()
        var num = n
        
        // Check for factor 2
        var power = 0
        while (num % 2 == 0) {
            num /= 2
            power++
        }
        if (power > 0) {
            factors[2] = power
        }
        
        // Check odd divisors from 3 to √n
        var divisor = 3
        while (divisor * divisor <= num) {
            power = 0
            while (num % divisor == 0) {
                num /= divisor
                power++
            }
            if (power > 0) {
                factors[divisor] = power
            }
            divisor += 2
        }
        
        // If num > 1, it's a prime factor
        if (num > 1) {
            factors[num] = 1
        }
        
        return factors
    }
    
    /**
     * Helper function to reconstruct the original number from factorization
     * Useful for verification. 
     * 
     * @param factors Map of prime factors to powers
     * @return The original number
     */
    fun reconstructNumber(factors: Map<Int, Int>): Long {
        var result = 1L
        for ((prime, power) in factors) {
            // Multiply by prime^power
            var primeProduct = 1L
            repeat(power) {
                primeProduct *= prime
            }
            result *= primeProduct
        }
        return result
    }
    
    /**
     * Pretty print the factorization in mathematical notation
     * 
     * @param n The original number
     * @param factors Map of prime factors to powers
     * @return String representation like "360 = 2³ × 3² × 5¹"
     */
    fun formatFactorization(n: Int, factors: Map<Int, Int>): String {
        val factorStrings = factors.entries
            .sortedBy { it. key }
            .map { (prime, power) -> 
                if (power == 1) "$prime" 
                else "$prime${powerToSuperscript(power)}"
            }
        return "$n = ${factorStrings.joinToString(" × ")}"
    }
    
    /**
     * Convert regular number to superscript for display
     */
    private fun powerToSuperscript(power: Int): String {
        val superscripts = mapOf(
            '0' to '⁰', '1' to '¹', '2' to '²', '3' to '³', '4' to '⁴',
            '5' to '⁵', '6' to '⁶', '7' to '⁷', '8' to '⁸', '9' to '⁹'
        )
        return power.toString().map { superscripts[it] ?: it }.joinToString("")
    }
}

/**
 * ============================================================================
 * TEST CASES & EXAMPLES
 * ============================================================================
 */
fun main() {
    val pf = PrimeFactorization()
    
    println("=". repeat(70))
    println("PRIME FACTORIZATION TESTS")
    println("=".repeat(70))
    
    // Test Case 1: Normal composite number
    println("\n Test Case 1: Factorize 360")
    println("─". repeat(50))
    val n1 = 360
    val factors1 = pf.factorize(n1)
    println("Input: $n1")
    println("Factors: $factors1")
    println("Formatted: ${pf.formatFactorization(n1, factors1)}")
    println("Verification: ${pf.reconstructNumber(factors1)} == $n1")
    // Expected: {2=3, 3=2, 5=1} → 360 = 2³ × 3² × 5¹
    
    // Test Case 2: Power of 2
    println("\n✅ Test Case 2: Factorize 128 (power of 2)")
    println("─".repeat(50))
    val n2 = 128
    val factors2 = pf.factorize(n2)
    println("Input:  $n2")
    println("Binary: ${n2.toString(2)}")
    println("Factors: $factors2")
    println("Formatted: ${pf.formatFactorization(n2, factors2)}")
    println("Verification: ${pf.reconstructNumber(factors2)} == $n2")
    // Expected: {2=7} → 128 = 2⁷
    
    // Test Case 3: Prime number
    println("\n✅ Test Case 3: Factorize 17 (prime)")
    println("─".repeat(50))
    val n3 = 17
    val factors3 = pf.factorize(n3)
    println("Input: $n3")
    println("Factors: $factors3")
    println("Formatted: ${pf.formatFactorization(n3, factors3)}")
    println("Verification: ${pf. reconstructNumber(factors3)} == $n3")
    // Expected: {17=1} → 17 = 17¹
    
    // Test Case 4: Small composite
    println("\n✅ Test Case 4: Factorize 84")
    println("─".repeat(50))
    val n4 = 84
    val factors4 = pf.factorize(n4)
    println("Input: $n4")
    println("Factors: $factors4")
    println("Formatted:  ${pf.formatFactorization(n4, factors4)}")
    println("Verification: ${pf.reconstructNumber(factors4)} == $n4")
    // Expected: {2=2, 3=1, 7=1} → 84 = 2² × 3 × 7
    
    // Test Case 5: Large prime factor
    println("\n✅ Test Case 5: Factorize 210 (2 × 3 × 5 × 7)")
    println("─".repeat(50))
    val n5 = 210
    val factors5 = pf.factorize(n5)
    println("Input: $n5")
    println("Factors: $factors5")
    println("Formatted: ${pf.formatFactorization(n5, factors5)}")
    println("Verification: ${pf. reconstructNumber(factors5)} == $n5")
    // Expected: {2=1, 3=1, 5=1, 7=1}
    
    // Test Case 6: Number with large prime factor
    println("\n✅ Test Case 6: Factorize 1024 (2¹⁰)")
    println("─".repeat(50))
    val n6 = 1024
    val factors6 = pf.factorize(n6)
    println("Input:  $n6")
    println("Binary: ${n6.toString(2)}")
    println("Trailing zeros: ${n6.countTrailingZeroBits()}")
    println("Factors: $factors6")
    println("Formatted: ${pf.formatFactorization(n6, factors6)}")
    println("Verification: ${pf.reconstructNumber(factors6)} == $n6")
    // Expected: {2=10}
    
    // Comparison:  Optimized vs Traditional
    println("\n" + "=".repeat(70))
    println("PERFORMANCE COMPARISON:  Optimized vs Traditional")
    println("=".repeat(70))
    
    val testNumber = 1048576 // 2^20
    println("\nFactorizing:  $testNumber (2²⁰)")
    
    var start = System.nanoTime()
    val result1 = pf.factorize(testNumber)
    var elapsed1 = System.nanoTime() - start
    println("Optimized (bit manipulation): ${result1} - ${elapsed1}ns")
    
    start = System.nanoTime()
    val result2 = pf.factorizeTraditional(testNumber)
    var elapsed2 = System.nanoTime() - start
    println("Traditional (repeated division): ${result2} - ${elapsed2}ns")
    
    println("\nSpeedup: ${elapsed2. toDouble() / elapsed1.toDouble()}x faster")
}
