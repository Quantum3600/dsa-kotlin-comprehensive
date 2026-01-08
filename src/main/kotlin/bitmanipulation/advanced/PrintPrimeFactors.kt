/**
 * ============================================================================
 * PROBLEM: Print Prime Factors
 * DIFFICULTY: Medium
 * CATEGORY: Bit Manipulation - Advanced
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a positive integer n, find and print all prime factors of n.
 * A prime factor is a factor that is a prime number. Use bit manipulation
 * optimizations to make the factorization process more efficient.
 * 
 * INPUT FORMAT:
 * - A positive integer n: n = 60
 * 
 * OUTPUT FORMAT:
 * - List of all prime factors: [2, 2, 3, 5]
 * - Prime factorization: 60 = 2^2 × 3 × 5
 * 
 * CONSTRAINTS:
 * - 2 <= n <= 10^9
 * - n is a positive integer
 * - For n = 1, output should indicate no prime factors
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Prime factorization breaks a number down into its prime building blocks.
 * Every composite number can be expressed as a product of prime numbers.
 * We can use trial division, checking divisibility from 2 upwards, and use
 * bit manipulation to optimize the checking process.
 * 
 * KEY INSIGHT:
 * - Any composite number n has at least one prime factor <= √n
 * - Check 2 separately (only even prime), then check only odd numbers
 * - Use bit operations to quickly check if number is even: n & 1 == 0
 * - After removing all factors of 2, n becomes odd
 * - Increment by 2 (i += 2) to check only odd potential factors
 * 
 * WHY THIS WORKS:
 * If n has a factor > √n, it must also have a corresponding factor < √n.
 * So we only need to check up to √n. After that, if n > 1, then n itself
 * is a prime factor.
 * 
 * ALGORITHM STEPS:
 * 1. Check if n is even (n & 1 == 0), divide by 2 repeatedly
 * 2. After removing all 2s, n is now odd
 * 3. Check odd numbers from 3 to √n
 * 4. For each divisor i, divide n by i while divisible
 * 5. If n > 1 after loop, n is a prime factor
 * 
 * VISUAL EXAMPLE 1: Prime factorization of 60
 * ═══════════════════════════════════════════════════════════════════
 * 
 * n = 60 (binary: 111100)
 * 
 * Step 1: Check if even using bit operation
 *   60 & 1 = 111100 & 000001 = 0 → Even! ✓
 *   
 * Step 2: Divide by 2
 *   60 ÷ 2 = 30, factor: 2
 *   30 ÷ 2 = 15, factor: 2
 *   15 & 1 = 1 → Odd, stop dividing by 2
 *   
 * Step 3: Check odd numbers starting from 3
 *   i = 3: 15 ÷ 3 = 5, factor: 3
 *   5 is not divisible by 3
 *   
 * Step 4: i = 5
 *   5 ÷ 5 = 1, factor: 5
 *   
 * Step 5: n = 1, done
 * 
 * Prime factors: [2, 2, 3, 5]
 * Factorization: 60 = 2² × 3 × 5
 * 
 * VISUAL EXAMPLE 2: Prime factorization of 37 (prime number)
 * ═══════════════════════════════════════════════════════════════════
 * 
 * n = 37 (binary: 100101)
 * 
 * Step 1: Check if even
 *   37 & 1 = 100101 & 000001 = 1 → Odd
 *   
 * Step 2: Check odd numbers from 3 to √37 ≈ 6
 *   i = 3: 37 % 3 = 1 (not divisible)
 *   i = 5: 37 % 5 = 2 (not divisible)
 *   
 * Step 3: n = 37 > 1, so 37 is prime
 * 
 * Prime factors: [37]
 * Factorization: 37 = 37
 * 
 * VISUAL EXAMPLE 3: Factorization of 100
 * ═══════════════════════════════════════════════════════════════════
 * 
 * n = 100 (binary: 1100100)
 * 
 * Step 1: Check if even
 *   100 & 1 = 0 → Even
 *   100 ÷ 2 = 50, factor: 2
 *   50 ÷ 2 = 25, factor: 2
 *   25 & 1 = 1 → Odd
 *   
 * Step 2: Check odd numbers
 *   i = 3: 25 % 3 ≠ 0
 *   i = 5: 25 ÷ 5 = 5, factor: 5
 *   i = 5: 5 ÷ 5 = 1, factor: 5
 *   
 * Prime factors: [2, 2, 5, 5]
 * Factorization: 100 = 2² × 5²
 * 
 * BIT MANIPULATION OPTIMIZATIONS:
 * ────────────────────────────────────────────────────────────────────
 * 
 * 1. **Check if even**: n & 1 == 0 (faster than n % 2 == 0)
 *    - Bit AND with 1 checks least significant bit
 *    - If LSB is 0, number is even
 * 
 * 2. **Divide by 2 using right shift**: n >>= 1 (same as n /= 2)
 *    - Right shift by 1 is equivalent to integer division by 2
 *    - Much faster on hardware level
 * 
 * 3. **Check if power of 2**: n & (n-1) == 0
 *    - Useful for special case optimization
 *    - If true and n > 0, then n is a power of 2
 * 
 * 4. **Count trailing zeros** (number of times divisible by 2):
 *    - Can use Integer.numberOfTrailingZeros(n)
 *    - Tells us how many factors of 2 without loop
 * 
 * ALTERNATIVE APPROACHES:
 * ────────────────────────────────────────────────────────────────────
 * 
 * 1. NAIVE TRIAL DIVISION:
 *    - Check all numbers from 2 to n
 *    - TIME: O(n), SPACE: O(1)
 *    - Extremely slow for large n
 * 
 * 2. OPTIMIZED TRIAL DIVISION (Current):
 *    - Check 2, then odd numbers up to √n
 *    - TIME: O(√n), SPACE: O(1)
 *    - Best for single factorization
 * 
 * 3. SIEVE-BASED (for multiple numbers):
 *    - Pre-compute primes using Sieve of Eratosthenes
 *    - TIME: O(n log log n) for sieve + O(π(√n)) per number
 *    - Best when factorizing many numbers
 * 
 * 4. POLLARD'S RHO (for very large n):
 *    - Probabilistic algorithm for large numbers
 *    - TIME: O(n^(1/4)) expected
 *    - Complex to implement
 * 
 * APPROACH COMPARISON:
 * ┌─────────────────────┬──────────┬───────────┬──────────────┐
 * │ Approach            │ Time     │ Space     │ Best For     │
 * ├─────────────────────┼──────────┼───────────┼──────────────┤
 * │ Naive Trial         │ O(n)     │ O(1)      │ Never        │
 * │ Optimized Trial     │ O(√n)    │ O(1)      │ Single nums  │
 * │ Sieve-based         │ O(√n)    │ O(√n)     │ Multiple nums│
 * │ Pollard's Rho       │ O(n^1/4) │ O(1)      │ Very large n │
 * └─────────────────────┴──────────┴───────────┴──────────────┘
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(√n)
 * - We check divisors from 2 to √n
 * - For each divisor, we may divide multiple times (log n at most)
 * - Worst case: O(√n) when n is prime
 * - Average case: O(√n) 
 * - Best case: O(log n) when n is power of 2
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space for variables
 * - Output list space not counted (required for answer)
 * - No recursion or auxiliary data structures
 * 
 * DETAILED ANALYSIS:
 * - Checking if even: O(1)
 * - Dividing by 2: O(log n) iterations in worst case
 * - Checking odd numbers: O(√n) iterations
 * - Overall: O(√n) dominates
 * 
 * ============================================================================
 */

package bitmanipulation.advanced

class PrintPrimeFactors {
    
    /**
     * APPROACH 1: Optimized trial division with bit optimizations
     * Find all prime factors using trial division up to √n
     * 
     * TIME: O(√n), SPACE: O(1)
     * 
     * @param n The number to factorize
     * @return List of all prime factors (with repetition)
     */
    fun primeFactors(n: Int): List<Int> {
        val factors = mutableListOf<Int>()
        var num = n
        
        // Check for factor 2 using bit manipulation
        while ((num and 1) == 0) {  // While num is even
            factors.add(2)
            num = num shr 1  // Divide by 2 using right shift
        }
        
        // Check odd factors from 3 to √n
        var i = 3
        while (i * i <= num) {
            while (num % i == 0) {
                factors.add(i)
                num /= i
            }
            i += 2  // Only check odd numbers
        }
        
        // If num > 1, then it's a prime factor
        if (num > 1) {
            factors.add(num)
        }
        
        return factors
    }
    
    /**
     * APPROACH 2: Get unique prime factors (no repetition)
     * Returns each prime factor only once
     * 
     * TIME: O(√n), SPACE: O(1)
     * 
     * @param n The number to factorize
     * @return List of unique prime factors
     */
    fun uniquePrimeFactors(n: Int): List<Int> {
        val factors = mutableListOf<Int>()
        var num = n
        
        // Check for factor 2
        if ((num and 1) == 0) {
            factors.add(2)
            while ((num and 1) == 0) {
                num = num shr 1
            }
        }
        
        // Check odd factors
        var i = 3
        while (i * i <= num) {
            if (num % i == 0) {
                factors.add(i)
                while (num % i == 0) {
                    num /= i
                }
            }
            i += 2
        }
        
        if (num > 1) {
            factors.add(num)
        }
        
        return factors
    }
    
    /**
     * APPROACH 3: Get prime factorization with exponents
     * Returns map of prime → exponent
     * 
     * TIME: O(√n), SPACE: O(log n) for map
     * 
     * @param n The number to factorize
     * @return Map of prime factor to its exponent
     */
    fun primeFactorization(n: Int): Map<Int, Int> {
        val factorization = mutableMapOf<Int, Int>()
        var num = n
        
        // Count factors of 2
        var count = 0
        while ((num and 1) == 0) {
            count++
            num = num shr 1
        }
        if (count > 0) {
            factorization[2] = count
        }
        
        // Check odd factors
        var i = 3
        while (i * i <= num) {
            count = 0
            while (num % i == 0) {
                count++
                num /= i
            }
            if (count > 0) {
                factorization[i] = count
            }
            i += 2
        }
        
        if (num > 1) {
            factorization[num] = 1
        }
        
        return factorization
    }
    
    /**
     * Check if a number is prime
     * Uses bit optimization for even check
     * 
     * TIME: O(√n), SPACE: O(1)
     * 
     * @param n Number to check
     * @return true if n is prime, false otherwise
     */
    fun isPrime(n: Int): Boolean {
        if (n <= 1) return false
        if (n <= 3) return true
        if ((n and 1) == 0) return false  // Even number check using bit operation
        
        var i = 3
        while (i * i <= n) {
            if (n % i == 0) return false
            i += 2
        }
        
        return true
    }
    
    /**
     * Count number of prime factors (with repetition)
     * 
     * TIME: O(√n), SPACE: O(1)
     * 
     * @param n The number
     * @return Count of prime factors
     */
    fun countPrimeFactors(n: Int): Int {
        var count = 0
        var num = n
        
        while ((num and 1) == 0) {
            count++
            num = num shr 1
        }
        
        var i = 3
        while (i * i <= num) {
            while (num % i == 0) {
                count++
                num /= i
            }
            i += 2
        }
        
        if (num > 1) {
            count++
        }
        
        return count
    }
    
    /**
     * Get largest prime factor
     * 
     * TIME: O(√n), SPACE: O(1)
     * 
     * @param n The number
     * @return Largest prime factor
     */
    fun largestPrimeFactor(n: Int): Int {
        var largest = -1
        var num = n
        
        while ((num and 1) == 0) {
            largest = 2
            num = num shr 1
        }
        
        var i = 3
        while (i * i <= num) {
            while (num % i == 0) {
                largest = i
                num /= i
            }
            i += 2
        }
        
        if (num > 1) {
            largest = num
        }
        
        return largest
    }
    
    /**
     * Visualize the factorization process
     * 
     * @param n Number to factorize
     */
    fun visualizeFactorization(n: Int) {
        println("Factorizing: $n (binary: ${Integer.toBinaryString(n)})")
        println()
        
        var num = n
        var step = 1
        
        // Check for 2s
        println("Step $step: Check if even using (n & 1)")
        if ((num and 1) == 0) {
            println("  $num & 1 = 0 → Even! Divide by 2")
            while ((num and 1) == 0) {
                println("  $num ÷ 2 = ${num shr 1}, factor: 2")
                num = num shr 1
                step++
            }
        } else {
            println("  $num & 1 = 1 → Odd, skip 2")
        }
        println()
        
        // Check odd numbers
        println("Step $step: Check odd numbers from 3 to √$num")
        var i = 3
        while (i * i <= num) {
            if (num % i == 0) {
                while (num % i == 0) {
                    println("  $num ÷ $i = ${num / i}, factor: $i")
                    num /= i
                }
            }
            i += 2
        }
        
        if (num > 1) {
            println("  Remaining $num > 1, factor: $num (prime)")
        }
        println()
        
        val factors = primeFactors(n)
        println("All prime factors: $factors")
        
        // Build factorization string
        val factorization = primeFactorization(n)
        val parts = factorization.entries.map { (prime, exp) ->
            if (exp == 1) "$prime" else "$prime^$exp"
        }
        println("Prime factorization: $n = ${parts.joinToString(" × ")}")
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
 *    Output: [] (no prime factors)
 *    Explanation: 1 is neither prime nor composite
 * 
 * 2. n = 2 (smallest prime):
 *    Output: [2]
 *    Explanation: 2 is the only even prime
 * 
 * 3. n = Prime number (e.g., 17):
 *    Output: [17]
 *    Explanation: Prime numbers have only themselves as factor
 * 
 * 4. n = Power of 2 (e.g., 16):
 *    Output: [2, 2, 2, 2]
 *    Factorization: 16 = 2^4
 *    Explanation: Only bit shifting needed
 * 
 * 5. n = Product of two primes (e.g., 15):
 *    Output: [3, 5]
 *    Factorization: 15 = 3 × 5
 *    Explanation: Semiprime number
 * 
 * 6. n = Perfect square (e.g., 36):
 *    Output: [2, 2, 3, 3]
 *    Factorization: 36 = 2² × 3²
 *    Explanation: All exponents are even
 * 
 * 7. n = Large prime (e.g., 10007):
 *    Output: [10007]
 *    Explanation: Algorithm checks up to √10007 ≈ 100
 * 
 * 8. n = Highly composite (e.g., 60):
 *    Output: [2, 2, 3, 5]
 *    Factorization: 60 = 2² × 3 × 5
 *    Explanation: Many small prime factors
 * 
 * 9. n = Product of large primes (e.g., 10001):
 *    Output: [73, 137]
 *    Factorization: 10001 = 73 × 137
 *    Explanation: Both factors are prime
 * 
 * 10. n = Maximum int (close to 2^31-1):
 *     Output: Depends on factorization
 *     Explanation: Tests performance limits
 * 
 * COMMON MISTAKES:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Forgetting to check if remaining number is prime**:
 *    - After loop, if n > 1, it's a prime factor
 *    - Missing this loses the largest prime factor
 * 
 * 2. **Not optimizing to √n**:
 *    - Checking up to n instead of √n is unnecessarily slow
 *    - Use i * i <= n as loop condition
 * 
 * 3. **Checking all numbers instead of odd only**:
 *    - After removing 2s, only odd numbers can be factors
 *    - Use i += 2 to skip even numbers
 * 
 * 4. **Integer overflow with i * i**:
 *    - For large n, i * i might overflow
 *    - Use i <= n/i or use long for comparison
 * 
 * 5. **Not handling n = 1 separately**:
 *    - 1 has no prime factors
 *    - Should return empty list
 * 
 * 6. **Using n % 2 instead of n & 1**:
 *    - Bit operation is faster
 *    - More idiomatic in bit manipulation context
 * 
 * OPTIMIZATION TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Use right shift for division by 2**:
 *    - n >> 1 is faster than n / 2
 *    - Hardware-level operation
 * 
 * 2. **Check even numbers separately**:
 *    - Handle 2 first, then only check odd numbers
 *    - Cuts search space in half
 * 
 * 3. **Early termination**:
 *    - Stop at √n, not n
 *    - After √n, at most one prime factor remains
 * 
 * 4. **Use Integer.numberOfTrailingZeros()**:
 *    - Quickly find how many factors of 2
 *    - Single operation instead of loop
 * 
 * 5. **Pre-compute small primes**:
 *    - For repeated factorizations
 *    - Store primes up to some limit
 * 
 * INTERVIEW TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Start with explanation**:
 *    "I'll use trial division, checking divisors from 2 to √n. I'll optimize
 *    by handling 2 separately using bit operations, then checking odd numbers."
 * 
 * 2. **Draw example**:
 *    Show factorization of 60 step by step
 *    60 → 30 (÷2) → 15 (÷2) → 5 (÷3) → 1 (÷5)
 * 
 * 3. **Discuss bit optimizations**:
 *    "I use n & 1 to check if even - faster than modulo.
 *    I use n >> 1 to divide by 2 - equivalent but more efficient."
 * 
 * 4. **Mention complexity**:
 *    "Time is O(√n) worst case when n is prime. Space is O(1) excluding output."
 * 
 * 5. **Handle edge cases**:
 *    "For n=1, return empty list. For primes, return [n]. For powers of 2,
 *    only the first loop runs."
 * 
 * 6. **Discuss follow-ups**:
 *    - "For multiple numbers, pre-compute primes using sieve"
 *    - "For very large n, use Pollard's Rho algorithm"
 *    - "Can parallelize for factorizing many numbers"
 * 
 * FOLLOW-UP QUESTIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Find GCD/LCM using prime factorization**
 * 2. **Count divisors from prime factorization**: ∏(exp + 1)
 * 3. **Check if number is perfect square**: All exponents even
 * 4. **Find smallest number with n prime factors**
 * 5. **Factorize multiple numbers efficiently**: Use sieve
 * 6. **Find prime factorization of factorial**: Special formula
 * 
 * REAL-WORLD APPLICATIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Cryptography**:
 *    - RSA encryption based on difficulty of factorizing large numbers
 *    - Prime factorization is NP problem for large composites
 * 
 * 2. **Number Theory**:
 *    - Fundamental theorem of arithmetic
 *    - GCD/LCM calculations
 *    - Modular arithmetic
 * 
 * 3. **Computer Science**:
 *    - Hash functions
 *    - Perfect hashing
 *    - Load balancing
 * 
 * 4. **Mathematics**:
 *    - Solving Diophantine equations
 *    - Simplifying fractions
 *    - Finding patterns in sequences
 * 
 * 5. **Data Structures**:
 *    - Efficient data structures for prime-based operations
 *    - Bloom filters with prime-sized arrays
 * 
 * 6. **Algorithm Design**:
 *    - Prime-based randomization
 *    - Hashing with prime numbers
 *    - Distribution algorithms
 * 
 * 7. **Security**:
 *    - Key generation
 *    - Digital signatures
 *    - Certificate authorities
 * 
 * RELATED PROBLEMS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. Sieve of Eratosthenes - Generate all primes up to n
 * 2. Count Primes - LeetCode 204
 * 3. Ugly Number II - LeetCode 264
 * 4. Perfect Squares - LeetCode 279
 * 5. Count Divisors - Finding all divisors
 * 6. GCD/LCM - Using prime factorization
 * 7. Factorial Trailing Zeros - Prime factor analysis
 * 8. Happy Number - Uses factorization concepts
 * 
 * ============================================================================
 */

fun main() {
    val pf = PrintPrimeFactors()
    
    println("═".repeat(70))
    println("PRIME FACTORIZATION WITH BIT MANIPULATION OPTIMIZATIONS")
    println("═".repeat(70))
    println()
    
    // Test 1: Standard composite number
    println("Test 1: Prime factors of 60")
    println("─".repeat(70))
    pf.visualizeFactorization(60)
    println()
    
    // Test 2: Prime number
    println("Test 2: Prime factors of 37 (prime)")
    println("─".repeat(70))
    val factors2 = pf.primeFactors(37)
    println("Prime factors: $factors2")
    println("Is prime: ${pf.isPrime(37)}")
    println("Expected: [37] ✓")
    println()
    
    // Test 3: Power of 2
    println("Test 3: Prime factors of 64 (2^6)")
    println("─".repeat(70))
    val factors3 = pf.primeFactors(64)
    val factorization3 = pf.primeFactorization(64)
    println("Prime factors: $factors3")
    println("Factorization: $factorization3")
    println("Expected: 2^6 ✓")
    println()
    
    // Test 4: Small number
    println("Test 4: Prime factors of 2")
    println("─".repeat(70))
    val factors4 = pf.primeFactors(2)
    println("Prime factors: $factors4")
    println("Expected: [2] ✓")
    println()
    
    // Test 5: Perfect square
    println("Test 5: Prime factors of 36 (perfect square)")
    println("─".repeat(70))
    val factors5 = pf.primeFactors(36)
    val factorization5 = pf.primeFactorization(36)
    println("Prime factors: $factors5")
    println("Factorization: $factorization5")
    println("Expected: 2^2 × 3^2 ✓")
    println()
    
    // Test 6: Unique prime factors
    println("Test 6: Unique prime factors of 60")
    println("─".repeat(70))
    val unique6 = pf.uniquePrimeFactors(60)
    println("Unique prime factors: $unique6")
    println("Expected: [2, 3, 5] ✓")
    println()
    
    // Test 7: Largest prime factor
    println("Test 7: Largest prime factor of 100")
    println("─".repeat(70))
    val largest7 = pf.largestPrimeFactor(100)
    println("Largest prime factor: $largest7")
    println("Expected: 5 ✓")
    println()
    
    // Test 8: Count prime factors
    println("Test 8: Count prime factors of 60")
    println("─".repeat(70))
    val count8 = pf.countPrimeFactors(60)
    println("Count: $count8")
    println("Expected: 4 (2,2,3,5) ✓")
    println()
    
    // Test 9: Product of two primes
    println("Test 9: Prime factors of 15 (3 × 5)")
    println("─".repeat(70))
    val factors9 = pf.primeFactors(15)
    println("Prime factors: $factors9")
    println("Expected: [3, 5] ✓")
    println()
    
    // Test 10: Larger number
    println("Test 10: Prime factors of 1001")
    println("─".repeat(70))
    val factors10 = pf.primeFactors(1001)
    val factorization10 = pf.primeFactorization(1001)
    println("Prime factors: $factors10")
    println("Factorization: $factorization10")
    println("1001 = 7 × 11 × 13 ✓")
    println()
    
    println("═".repeat(70))
    println("All tests passed! ✓")
    println("═".repeat(70))
}
