/**
 * ============================================================================
 * PROBLEM: GCD (Greatest Common Divisor) / HCF (Highest Common Factor)
 * DIFFICULTY: Easy
 * CATEGORY: Basic Math
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two positive integers, find their Greatest Common Divisor (GCD),
 * also known as Highest Common Factor (HCF). The GCD is the largest positive
 * integer that divides both numbers without a remainder.
 * 
 * INPUT FORMAT:
 * - Two positive integers a and b
 * - Example: a = 12, b = 18
 * 
 * OUTPUT FORMAT:
 * - The GCD of a and b
 * - Example: 6
 * 
 * CONSTRAINTS:
 * - 1 <= a, b <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The GCD of 12 and 18 is 6 because:
 * - Divisors of 12: 1, 2, 3, 4, 6, 12
 * - Divisors of 18: 1, 2, 3, 6, 9, 18
 * - Common divisors: 1, 2, 3, 6
 * - Greatest: 6
 * 
 * EUCLIDEAN ALGORITHM INSIGHT:
 * Key observation: GCD(a, b) = GCD(b, a % b)
 * 
 * Why? If d divides both a and b, then d also divides (a % b)
 * 
 * Example: GCD(12, 18)
 * - GCD(18, 12) → GCD(12, 6) → GCD(6, 0) → Answer: 6
 * 
 * ALGORITHM STEPS (Euclidean):
 * 1. While b ≠ 0:
 *    a. temp = b
 *    b. b = a % b
 *    c. a = temp
 * 2. Return a
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(min(a,b)) - Check all numbers from 1 to min
 * 2. Euclidean Algorithm: O(log min(a,b)) - OPTIMAL (iterative)
 * 3. Recursive Euclidean: O(log min(a,b)) - Elegant but uses stack
 * 4. Binary GCD (Stein's): O(log max(a,b)) - Uses bit operations
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * EUCLIDEAN ALGORITHM:
 * TIME COMPLEXITY: O(log min(a, b))
 * - Each iteration reduces the problem size by at least half
 * - Maximum iterations ≈ 5 * number of digits
 * - For a=1000000, b=999999: ~20 iterations
 * 
 * SPACE COMPLEXITY: O(1)
 * - Iterative version uses constant space
 * - Recursive version uses O(log min(a,b)) stack space
 * 
 * WHY O(log n):
 * The Fibonacci sequence gives the worst case:
 * - GCD(F(n+1), F(n)) takes exactly n iterations
 * - F(n) grows exponentially, so n grows logarithmically
 * 
 * ============================================================================
 */

package basics.math

class GCD_HCF {
    
    /**
     * GCD using Euclidean algorithm (iterative)
     * TIME: O(log min(a,b)), SPACE: O(1)
     * 
     * @param a First number
     * @param b Second number
     * @return GCD of a and b
     */
    fun gcd(a: Int, b: Int): Int {
        var num1 = a
        var num2 = b
        
        // Euclidean algorithm: GCD(a, b) = GCD(b, a % b)
        while (num2 != 0) {
            val temp = num2
            num2 = num1 % num2
            num1 = temp
        }
        
        return num1
    }
    
    /**
     * GCD using recursive Euclidean algorithm
     * TIME: O(log min(a,b)), SPACE: O(log min(a,b))
     * 
     * More elegant but uses recursion stack
     */
    fun gcdRecursive(a: Int, b: Int): Int {
        // Base case: when b becomes 0, a is the GCD
        if (b == 0) return a
        
        // Recursive case: GCD(a, b) = GCD(b, a % b)
        return gcdRecursive(b, a % b)
    }
    
    /**
     * GCD using brute force approach
     * TIME: O(min(a,b)), SPACE: O(1)
     * 
     * Educational purposes - not optimal
     */
    fun gcdBruteForce(a: Int, b: Int): Int {
        var gcd = 1
        val minNum = minOf(a, b)
        
        // Check all numbers from 1 to min(a,b)
        for (i in 1..minNum) {
            if (a % i == 0 && b % i == 0) {
                gcd = i
            }
        }
        
        return gcd
    }
    
    /**
     * LCM (Least Common Multiple) using GCD
     * Formula: LCM(a, b) = (a * b) / GCD(a, b)
     * 
     * TIME: O(log min(a,b))
     */
    fun lcm(a: Int, b: Int): Long {
        // Use Long to avoid overflow
        return (a.toLong() * b) / gcd(a, b)
    }
    
    /**
     * GCD of array of numbers
     * TIME: O(n * log max(array))
     */
    fun gcdArray(arr: IntArray): Int {
        if (arr.isEmpty()) return 0
        
        var result = arr[0]
        for (i in 1 until arr.size) {
            result = gcd(result, arr[i])
            // Optimization: if GCD becomes 1, can't get smaller
            if (result == 1) break
        }
        
        return result
    }
    
    /**
     * Check if two numbers are coprime
     * Numbers are coprime if their GCD is 1
     */
    fun areCoprime(a: Int, b: Int): Boolean {
        return gcd(a, b) == 1
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH - Euclidean Algorithm
 * ============================================================================
 * 
 * INPUT: a = 48, b = 18
 * 
 * Iteration 1:
 * - num1 = 48, num2 = 18
 * - temp = 18
 * - num2 = 48 % 18 = 12
 * - num1 = 18
 * State: num1 = 18, num2 = 12
 * 
 * Iteration 2:
 * - num1 = 18, num2 = 12
 * - temp = 12
 * - num2 = 18 % 12 = 6
 * - num1 = 12
 * State: num1 = 12, num2 = 6
 * 
 * Iteration 3:
 * - num1 = 12, num2 = 6
 * - temp = 6
 * - num2 = 12 % 6 = 0
 * - num1 = 6
 * State: num1 = 6, num2 = 0
 * 
 * Loop ends (num2 = 0)
 * Return num1 = 6 ✓
 * 
 * ============================================================================
 * MATHEMATICAL PROPERTIES
 * ============================================================================
 * 
 * 1. **Commutative**: GCD(a, b) = GCD(b, a)
 * 2. **GCD with 0**: GCD(a, 0) = a
 * 3. **GCD with 1**: GCD(a, 1) = 1 (any number with 1 is coprime)
 * 4. **GCD of multiples**: GCD(a, ka) = a where k is positive integer
 * 5. **LCM relation**: GCD(a, b) * LCM(a, b) = a * b
 * 6. **Divisibility**: If d = GCD(a, b), then a = d*m and b = d*n where GCD(m,n) = 1
 * 
 * ============================================================================
 * WHY EUCLIDEAN ALGORITHM WORKS
 * ============================================================================
 * 
 * Proof that GCD(a, b) = GCD(b, a mod b):
 * 
 * Let a = bq + r where r = a mod b (0 ≤ r < b)
 * 
 * If d divides both a and b:
 * - d divides a → a = dk for some k
 * - d divides b → b = dm for some m
 * - Therefore: a - bq = r → dk - dmq = r → d(k - mq) = r
 * - So d divides r
 * 
 * Conversely, if d divides both b and r:
 * - d divides b and r
 * - Therefore: bq + r = a → d divides a
 * 
 * Thus, divisors of (a, b) = divisors of (b, r)
 * Therefore, GCD(a, b) = GCD(b, r) = GCD(b, a mod b) ✓
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = GCD_HCF()
    
    println("=== GCD (Greatest Common Divisor) ===\n")
    
    // Test 1: Normal case
    println("Test 1: a = 12, b = 18")
    println("Iterative: ${solution.gcd(12, 18)}")
    println("Recursive: ${solution.gcdRecursive(12, 18)}")
    println("Brute Force: ${solution.gcdBruteForce(12, 18)}")
    println("Expected: 6\n")
    
    // Test 2: One number divides other
    println("Test 2: a = 20, b = 10")
    println("GCD: ${solution.gcd(20, 10)}")
    println("Expected: 10\n")
    
    // Test 3: Coprime numbers
    println("Test 3: a = 17, b = 19")
    println("GCD: ${solution.gcd(17, 19)}")
    println("Are coprime: ${solution.areCoprime(17, 19)}")
    println("Expected: 1, true\n")
    
    // Test 4: Same numbers
    println("Test 4: a = 25, b = 25")
    println("GCD: ${solution.gcd(25, 25)}")
    println("Expected: 25\n")
    
    // Test 5: One is 1
    println("Test 5: a = 100, b = 1")
    println("GCD: ${solution.gcd(100, 1)}")
    println("Expected: 1\n")
    
    // Test 6: Large numbers
    println("Test 6: a = 1071, b = 462")
    println("GCD: ${solution.gcd(1071, 462)}")
    println("Expected: 21\n")
    
    // Test 7: LCM calculation
    println("Test 7: LCM of 12 and 18")
    println("LCM: ${solution.lcm(12, 18)}")
    println("Expected: 36")
    println("Verify: GCD * LCM = a * b")
    println("${solution.gcd(12, 18)} * ${solution.lcm(12, 18)} = ${12 * 18}\n")
    
    // Test 8: GCD of array
    println("Test 8: GCD of array [12, 18, 24, 36]")
    val arr = intArrayOf(12, 18, 24, 36)
    println("GCD: ${solution.gcdArray(arr)}")
    println("Expected: 6\n")
    
    // Test 9: Performance comparison
    println("=== Performance Comparison ===")
    val a = 123456
    val b = 789012
    val iterations = 100000
    
    var start = System.nanoTime()
    repeat(iterations) { solution.gcd(a, b) }
    var time = (System.nanoTime() - start) / 1000
    println("Euclidean (iterative): $time μs")
    
    start = System.nanoTime()
    repeat(iterations) { solution.gcdRecursive(a, b) }
    time = (System.nanoTime() - start) / 1000
    println("Euclidean (recursive): $time μs")
    
    // Note: Not testing brute force for large numbers (too slow!)
    
    println("\n=== Euclidean Algorithm is MUCH faster! ===")
}
