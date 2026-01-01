/**
 * ============================================================================
 * PROBLEM: Print All Divisors of a Number
 * DIFFICULTY: Easy
 * CATEGORY: Basic Math
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a positive integer N, find and print all divisors of N.
 * A divisor of N is a number that divides N completely (remainder = 0).
 * 
 * INPUT FORMAT:
 * - A positive integer N
 * - Example: N = 36
 * 
 * OUTPUT FORMAT:
 * - List of all divisors in ascending order
 * - Example: [1, 2, 3, 4, 6, 9, 12, 18, 36]
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * For N = 36:
 * - 1 divides 36 → 36/1 = 36 (both 1 and 36 are divisors)
 * - 2 divides 36 → 36/2 = 18 (both 2 and 18 are divisors)
 * - 3 divides 36 → 36/3 = 12 (both 3 and 12 are divisors)
 * - 4 divides 36 → 36/4 = 9 (both 4 and 9 are divisors)
 * - 6 divides 36 → 36/6 = 6 (6 is divisor, but don't count twice)
 * 
 * KEY INSIGHT:
 * Divisors come in pairs! If i divides N, then N/i also divides N.
 * We only need to check up to √N.
 * 
 * ALGORITHM STEPS (Brute Force):
 * 1. For i from 1 to N:
 * 2. If N % i == 0, i is a divisor
 * 3. Add i to list
 * 
 * ALGORITHM STEPS (Optimized):
 * 1. For i from 1 to √N:
 * 2. If N % i == 0:
 *    a. Add i to list
 *    b. If i != N/i, add N/i to list
 * 3. Sort the list
 * 
 * VISUAL EXAMPLE for N=36:
 * Check i=1: 36/1=36 → Add 1, 36
 * Check i=2: 36/2=18 → Add 2, 18
 * Check i=3: 36/3=12 → Add 3, 12
 * Check i=4: 36/4=9  → Add 4, 9
 * Check i=5: 36%5≠0  → Skip
 * Check i=6: 36/6=6  → Add 6 (don't duplicate)
 * Stop at √36=6
 * 
 * Result: [1, 2, 3, 4, 6, 9, 12, 18, 36]
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * BRUTE FORCE:
 * TIME: O(n) - Check all numbers from 1 to n
 * SPACE: O(k) where k is number of divisors
 * 
 * OPTIMIZED:
 * TIME: O(√n) - Only check up to square root
 * SPACE: O(k) + O(k log k) for sorting
 * 
 * WHY √n:
 * - If i is a divisor and i > √n, then n/i < √n
 * - So n/i was already found when we checked numbers < √n
 * - Example: For 36, after checking 1-6, we found all divisors
 * 
 * ============================================================================
 */

package basics.math

import kotlin.math.sqrt

class PrintAllDivisors {
    
    /**
     * Find all divisors - Brute Force
     * TIME: O(n), SPACE: O(k)
     * 
     * @param n The number
     * @return List of all divisors in sorted order
     */
    fun findDivisorsBruteForce(n: Int): List<Int> {
        val divisors = mutableListOf<Int>()
        
        // Check every number from 1 to n
        for (i in 1..n) {
            if (n % i == 0) {
                divisors.add(i)
            }
        }
        
        return divisors
    }
    
    /**
     * Find all divisors - Optimized (√n approach)
     * TIME: O(√n + k log k), SPACE: O(k)
     * 
     * @param n The number
     * @return List of all divisors in sorted order
     */
    fun findDivisors(n: Int): List<Int> {
        val divisors = mutableListOf<Int>()
        
        // Check up to square root of n
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        for (i in 1..sqrtN) {
            if (n % i == 0) {
                // i is a divisor
                divisors.add(i)
                
                // n/i is also a divisor (if different from i)
                if (i != n / i) {
                    divisors.add(n / i)
                }
            }
        }
        
        // Sort the divisors
        return divisors.sorted()
    }
    
    /**
     * Count number of divisors
     * TIME: O(√n), SPACE: O(1)
     */
    fun countDivisors(n: Int): Int {
        var count = 0
        val sqrtN = sqrt(n.toDouble()).toInt()
        
        for (i in 1..sqrtN) {
            if (n % i == 0) {
                count++ // Count i
                if (i != n / i) {
                    count++ // Count n/i if different
                }
            }
        }
        
        return count
    }
    
    /**
     * Sum of all divisors
     * TIME: O(√n), SPACE: O(1)
     */
    fun sumOfDivisors(n: Int): Int {
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
     * Check if number is perfect (sum of proper divisors equals number)
     * Example: 6 = 1 + 2 + 3
     */
    fun isPerfectNumber(n: Int): Boolean {
        return sumOfDivisors(n) - n == n
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH - Optimized Approach
 * ============================================================================
 * 
 * INPUT: n = 36
 * 
 * sqrtN = 6
 * 
 * i=1: 36 % 1 = 0
 *   Add 1, Add 36/1=36
 *   divisors = [1, 36]
 * 
 * i=2: 36 % 2 = 0
 *   Add 2, Add 36/2=18
 *   divisors = [1, 36, 2, 18]
 * 
 * i=3: 36 % 3 = 0
 *   Add 3, Add 36/3=12
 *   divisors = [1, 36, 2, 18, 3, 12]
 * 
 * i=4: 36 % 4 = 0
 *   Add 4, Add 36/4=9
 *   divisors = [1, 36, 2, 18, 3, 12, 4, 9]
 * 
 * i=5: 36 % 5 ≠ 0
 *   Skip
 * 
 * i=6: 36 % 6 = 0
 *   Add 6, 36/6=6 (same, don't add twice)
 *   divisors = [1, 36, 2, 18, 3, 12, 4, 9, 6]
 * 
 * Sort: [1, 2, 3, 4, 6, 9, 12, 18, 36] ✓
 * 
 * ============================================================================
 * MATHEMATICAL PROPERTIES
 * ============================================================================
 * 
 * 1. PERFECT NUMBERS:
 *    Sum of proper divisors equals number
 *    Examples: 6 (1+2+3), 28 (1+2+4+7+14), 496
 * 
 * 2. ABUNDANT NUMBERS:
 *    Sum of proper divisors > number
 *    Example: 12 (1+2+3+4+6 = 16 > 12)
 * 
 * 3. DEFICIENT NUMBERS:
 *    Sum of proper divisors < number
 *    Example: 8 (1+2+4 = 7 < 8)
 * 
 * 4. PRIME NUMBERS:
 *    Only 2 divisors: 1 and itself
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = PrintAllDivisors()
    
    println("=== Print All Divisors ===\n")
    
    // Test 1: Normal case
    println("Test 1: n = 36")
    println("Divisors: ${solution.findDivisors(36)}")
    println("Count: ${solution.countDivisors(36)}")
    println("Sum: ${solution.sumOfDivisors(36)}")
    println("Expected: [1, 2, 3, 4, 6, 9, 12, 18, 36], count=9, sum=91\n")
    
    // Test 2: Prime number
    println("Test 2: n = 17 (prime)")
    println("Divisors: ${solution.findDivisors(17)}")
    println("Count: ${solution.countDivisors(17)}")
    println("Expected: [1, 17], count=2\n")
    
    // Test 3: Perfect square
    println("Test 3: n = 16 (perfect square)")
    println("Divisors: ${solution.findDivisors(16)}")
    println("Count: ${solution.countDivisors(16)}")
    println("Expected: [1, 2, 4, 8, 16], count=5\n")
    
    // Test 4: Small number
    println("Test 4: n = 1")
    println("Divisors: ${solution.findDivisors(1)}")
    println("Expected: [1]\n")
    
    // Test 5: Perfect number
    println("Test 5: n = 28 (perfect number)")
    println("Divisors: ${solution.findDivisors(28)}")
    println("Sum of divisors: ${solution.sumOfDivisors(28)}")
    println("Is perfect: ${solution.isPerfectNumber(28)}")
    println("Expected: [1, 2, 4, 7, 14, 28], sum=56, perfect=true\n")
    
    // Test 6: Performance comparison
    println("Test 6: Performance Comparison (n=100000)")
    val n = 100000
    
    var start = System.nanoTime()
    val optimized = solution.findDivisors(n)
    var time = (System.nanoTime() - start) / 1000
    println("Optimized O(√n): $time μs, found ${optimized.size} divisors")
    
    start = System.nanoTime()
    val bruteForce = solution.findDivisorsBruteForce(n)
    time = (System.nanoTime() - start) / 1000
    println("Brute Force O(n): $time μs, found ${bruteForce.size} divisors")
    println("Optimized is MUCH faster!\n")
    
    // Test 7: Various numbers
    println("Test 7: Divisors of various numbers")
    val testNumbers = listOf(10, 20, 24, 30, 100)
    for (num in testNumbers) {
        println("$num: ${solution.findDivisors(num)}")
    }
}
