/**
 * ============================================================================
 * PROBLEM: Check if Number is Prime
 * DIFFICULTY: Easy
 * CATEGORY: Basic Math
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * A prime number is a natural number greater than 1 that has no positive
 * divisors other than 1 and itself. Given a number N, determine if it's prime.
 * 
 * INPUT FORMAT:
 * - A positive integer N
 * - Example: N = 17
 * 
 * OUTPUT FORMAT:
 * - true if prime, false otherwise
 * - Example: true
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * A prime number has exactly 2 divisors: 1 and itself.
 * Examples:
 * - 2 is prime (divisors: 1, 2)
 * - 3 is prime (divisors: 1, 3)
 * - 4 is NOT prime (divisors: 1, 2, 4)
 * - 5 is prime (divisors: 1, 5)
 * 
 * KEY INSIGHTS:
 * 1. If N has a divisor other than 1 and N, it's not prime
 * 2. We only need to check divisors up to √N
 * 3. If no divisor found up to √N, number is prime
 * 
 * WHY CHECK UP TO √N:
 * If N = a * b and a ≤ b, then a ≤ √N.
 * Example: 36 = 6 * 6, we find 6 at √36
 * Example: 35 = 5 * 7, we find 5 before √35
 * 
 * ALGORITHM STEPS (Optimized):
 * 1. Handle special cases: N ≤ 1 → not prime
 * 2. Check N = 2 or 3 → prime
 * 3. If N divisible by 2 or 3 → not prime
 * 4. Check divisors of form 6k±1 up to √N
 * 5. If any divides N → not prime
 * 6. Otherwise → prime
 * 
 * OPTIMIZATION:
 * All primes > 3 are of the form 6k±1
 * (because numbers of form 6k, 6k+2, 6k+4 are divisible by 2,
 * and 6k+3 is divisible by 3)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * BRUTE FORCE:
 * TIME: O(n) - Check all numbers from 2 to n-1
 * SPACE: O(1)
 * 
 * OPTIMIZED:
 * TIME: O(√n) - Check only up to square root
 * SPACE: O(1)
 * 
 * SIEVE OF ERATOSTHENES (for multiple numbers):
 * TIME: O(n log log n) - for finding all primes up to n
 * SPACE: O(n)
 * 
 * ============================================================================
 */

package basics.math

import kotlin.math.sqrt

class CheckPrime {
    
    /**
     * Check if number is prime - Brute Force
     * TIME: O(n), SPACE: O(1)
     * 
     * @param n The number to check
     * @return true if prime
     */
    fun isPrimeBruteForce(n: Int): Boolean {
        // Numbers ≤ 1 are not prime
        if (n <= 1) return false
        
        // Check if n has any divisor from 2 to n-1
        for (i in 2 until n) {
            if (n % i == 0) {
                return false  // Found a divisor, not prime
            }
        }
        
        return true  // No divisor found, prime
    }
    
    /**
     * Check if number is prime - Optimized (√n)
     * TIME: O(√n), SPACE: O(1)
     * 
     * @param n The number to check
     * @return true if prime
     */
    fun isPrime(n: Int): Boolean {
        // Handle special cases
        if (n <= 1) return false
        if (n <= 3) return true  // 2 and 3 are prime
        
        // Eliminate multiples of 2 and 3
        if (n % 2 == 0 || n % 3 == 0) return false
        
        // Check for divisors of form 6k±1 up to √n
        // All primes > 3 can be written as 6k±1
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        var i = 5
        while (i <= sqrtN) {
            // Check 6k-1 and 6k+1
            if (n % i == 0 || n % (i + 2) == 0) {
                return false
            }
            i += 6
        }
        
        return true
    }
    
    /**
     * Find all prime numbers up to n using Sieve of Eratosthenes
     * TIME: O(n log log n), SPACE: O(n)
     * 
     * @param n Upper limit
     * @return List of all primes up to n
     */
    fun sieveOfEratosthenes(n: Int): List<Int> {
        if (n < 2) return emptyList()
        
        // Create boolean array "prime[0..n]"
        // Initially, all entries are true
        val prime = BooleanArray(n + 1) { true }
        prime[0] = false
        prime[1] = false
        
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        // Start from 2
        for (p in 2..sqrtN) {
            // If prime[p] is still true, it's prime
            if (prime[p]) {
                // Mark all multiples of p as not prime
                for (i in p * p..n step p) {
                    prime[i] = false
                }
            }
        }
        
        // Collect all numbers where prime[i] = true
        return (2..n).filter { prime[it] }
    }
    
    /**
     * Count prime numbers less than n
     * TIME: O(n log log n), SPACE: O(n)
     */
    fun countPrimes(n: Int): Int {
        if (n < 2) return 0
        
        val prime = BooleanArray(n) { true }
        prime[0] = false
        if (n > 1) prime[1] = false
        
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        for (p in 2..sqrtN) {
            if (prime[p]) {
                for (i in p * p until n step p) {
                    prime[i] = false
                }
            }
        }
        
        return prime.count { it }
    }
    
    /**
     * Find next prime number after n
     * TIME: O(√n * gap), where gap is distance to next prime
     */
    fun nextPrime(n: Int): Int {
        var candidate = n + 1
        while (!isPrime(candidate)) {
            candidate++
        }
        return candidate
    }
    
    /**
     * Check if two numbers are twin primes
     * Twin primes differ by 2 (e.g., 11 and 13)
     */
    fun areTwinPrimes(n: Int, m: Int): Boolean {
        return isPrime(n) && isPrime(m) && kotlin.math.abs(n - m) == 2
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH - Optimized Approach
 * ============================================================================
 * 
 * INPUT: n = 29
 * 
 * Step 1: Check special cases
 * - n = 29 > 1 ✓
 * - n = 29 > 3 ✓
 * - 29 % 2 = 1 (not divisible by 2) ✓
 * - 29 % 3 = 2 (not divisible by 3) ✓
 * 
 * Step 2: Check divisors of form 6k±1
 * - sqrtN = √29 ≈ 5
 * 
 * i = 5:
 *   - 29 % 5 = 4 (not divisible) ✓
 *   - 29 % 7 = 1 (not divisible) ✓
 * 
 * i = 11 > sqrtN, stop
 * 
 * Result: No divisor found, 29 is PRIME ✓
 * 
 * ---
 * 
 * INPUT: n = 35
 * 
 * Step 1: Check special cases (pass)
 * 
 * Step 2: Check divisors
 * - sqrtN = √35 ≈ 5
 * 
 * i = 5:
 *   - 35 % 5 = 0 (divisible!) ✗
 * 
 * Result: Found divisor 5, 35 is NOT PRIME ✗
 * 
 * ============================================================================
 * PRIME NUMBER PROPERTIES
 * ============================================================================
 * 
 * 1. ONLY EVEN PRIME: 2
 * 2. TWIN PRIMES: Primes differing by 2 (3,5), (5,7), (11,13), (17,19)
 * 3. MERSENNE PRIMES: Of form 2ⁿ - 1
 * 4. GOLDBACH'S CONJECTURE: Every even number > 2 is sum of two primes
 * 5. PRIME DISTRIBUTION: Approximately n/ln(n) primes below n
 * 
 * FIRST 25 PRIMES:
 * 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
 * 53, 59, 61, 67, 71, 73, 79, 83, 89, 97
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = CheckPrime()
    
    println("=== Prime Number Checker ===\n")
    
    // Test 1: Small primes
    println("Test 1: Small primes")
    println("2: ${solution.isPrime(2)}")
    println("3: ${solution.isPrime(3)}")
    println("5: ${solution.isPrime(5)}")
    println("Expected: all true\n")
    
    // Test 2: Not primes
    println("Test 2: Not primes")
    println("1: ${solution.isPrime(1)}")
    println("4: ${solution.isPrime(4)}")
    println("6: ${solution.isPrime(6)}")
    println("Expected: all false\n")
    
    // Test 3: Larger primes
    println("Test 3: Larger primes")
    println("17: ${solution.isPrime(17)}")
    println("29: ${solution.isPrime(29)}")
    println("97: ${solution.isPrime(97)}")
    println("Expected: all true\n")
    
    // Test 4: Larger composites
    println("Test 4: Larger composites")
    println("35: ${solution.isPrime(35)}")
    println("91: ${solution.isPrime(91)}")
    println("100: ${solution.isPrime(100)}")
    println("Expected: all false\n")
    
    // Test 5: Sieve of Eratosthenes
    println("Test 5: All primes up to 50")
    val primesTo50 = solution.sieveOfEratosthenes(50)
    println("Primes: $primesTo50")
    println("Count: ${primesTo50.size}")
    println("Expected: [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47], count=15\n")
    
    // Test 6: Count primes
    println("Test 6: Count primes less than 100")
    println("Count: ${solution.countPrimes(100)}")
    println("Expected: 25\n")
    
    // Test 7: Next prime
    println("Test 7: Find next prime")
    println("Next prime after 10: ${solution.nextPrime(10)}")
    println("Next prime after 20: ${solution.nextPrime(20)}")
    println("Expected: 11, 23\n")
    
    // Test 8: Twin primes
    println("Test 8: Twin primes")
    println("11 and 13: ${solution.areTwinPrimes(11, 13)}")
    println("17 and 19: ${solution.areTwinPrimes(17, 19)}")
    println("10 and 12: ${solution.areTwinPrimes(10, 12)}")
    println("Expected: true, true, false\n")
    
    // Test 9: Performance comparison
    println("Test 9: Performance Comparison")
    val testNum = 10007  // Large prime
    
    var start = System.nanoTime()
    val result1 = solution.isPrime(testNum)
    var time = (System.nanoTime() - start) / 1000
    println("Optimized O(√n): $result1, $time μs")
    
    start = System.nanoTime()
    val result2 = solution.isPrimeBruteForce(testNum)
    time = (System.nanoTime() - start) / 1000
    println("Brute Force O(n): $result2, $time μs")
    println("Optimized is MUCH faster!\n")
    
    // Test 10: First 20 primes
    println("Test 10: First 20 primes")
    val first20 = solution.sieveOfEratosthenes(100).take(20)
    println(first20)
}
