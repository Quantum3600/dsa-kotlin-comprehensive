/**
 * ============================================================================
 * PROBLEM: Calculate Factorial of N using Recursion
 * DIFFICULTY: Easy
 * CATEGORY: Recursion/Basic
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a non-negative integer N, calculate its factorial (N!) using recursion.
 * Factorial is the product of all positive integers less than or equal to N.
 * 
 * INPUT FORMAT:
 * - A non-negative integer N
 * - Example: N = 5
 * 
 * OUTPUT FORMAT:
 * - Factorial of N
 * - Example: 120 (because 5! = 5 × 4 × 3 × 2 × 1 = 120)
 * 
 * CONSTRAINTS:
 * - 0 <= N <= 20 (for Int)
 * - 0! = 1 (by definition)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Factorial can be defined recursively:
 * - N! = N × (N-1)!
 * - 0! = 1 (base case)
 * - 1! = 1
 * 
 * Example: 5! = 5 × 4!
 *              = 5 × 4 × 3!
 *              = 5 × 4 × 3 × 2!
 *              = 5 × 4 × 3 × 2 × 1!
 *              = 5 × 4 × 3 × 2 × 1
 *              = 120
 * 
 * RECURSIVE DEFINITION:
 * factorial(N) = N × factorial(N-1)
 * factorial(0) = 1
 * 
 * VISUAL EXAMPLE (N = 5):
 * 
 * factorial(5)
 *   └─ return 5 × factorial(4)
 *                 └─ return 4 × factorial(3)
 *                              └─ return 3 × factorial(2)
 *                                         └─ return 2 × factorial(1)
 *                                                    └─ return 1 × factorial(0)
 *                                                               └─ return 1
 * 
 * Result: 5 × 4 × 3 × 2 × 1 × 1 = 120
 * 
 * ALGORITHM STEPS:
 * 1. Base case: if N = 0 or N = 1, return 1
 * 2. Recursive case: return N × factorial(N-1)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(N)
 * - N recursive calls
 * - Each call does O(1) multiplication
 * 
 * SPACE COMPLEXITY: O(N)
 * - Recursion stack depth is N
 * - Each frame stores N and return address
 * 
 * NOTE ON OVERFLOW:
 * - Int can hold factorial up to 12! (479,001,600)
 * - 13! = 6,227,020,800 exceeds Int.MAX_VALUE
 * - Use Long for factorial up to 20!
 * - Use BigInteger for larger factorials
 * 
 * ============================================================================
 */

package basics.recursion

class FactorialN {
    
    /**
     * Calculate factorial using recursion
     * TIME: O(N), SPACE: O(N)
     */
    fun factorial(n: Int): Long {
        // Base case: 0! = 1 and 1! = 1
        if (n <= 1) {
            return 1
        }
        
        // Recursive case: N! = N × (N-1)!
        return n.toLong() * factorial(n - 1)
    }
    
    /**
     * Tail recursive version
     * TIME: O(N), SPACE: O(1) with optimization
     */
    tailrec fun factorialTailRec(n: Int, accumulator: Long = 1): Long {
        // Base case
        if (n <= 1) {
            return accumulator
        }
        
        // Tail recursive call with updated accumulator
        return factorialTailRec(n - 1, accumulator * n)
    }
    
    /**
     * Calculate factorial iteratively (for comparison)
     * TIME: O(N), SPACE: O(1)
     */
    fun factorialIterative(n: Int): Long {
        var result = 1L
        for (i in 2..n) {
            result *= i
        }
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: N = 5
 * 
 * RECURSIVE CALLS:
 * 
 * factorial(5):
 *   return 5 × factorial(4)
 *     return 4 × factorial(3)
 *       return 3 × factorial(2)
 *         return 2 × factorial(1)
 *           return 1 [BASE CASE]
 *         return 2 × 1 = 2
 *       return 3 × 2 = 6
 *     return 4 × 6 = 24
 *   return 5 × 24 = 120 ✓
 * 
 * CALL STACK VISUALIZATION:
 * factorial(5) = 5 × 24  = 120
 * factorial(4) = 4 × 6   = 24
 * factorial(3) = 3 × 2   = 6
 * factorial(2) = 2 × 1   = 2
 * factorial(1) = 1
 * 
 * TAIL RECURSIVE (N = 5):
 * 
 * factorialTailRec(5, 1):
 *   factorialTailRec(4, 1×5=5):
 *     factorialTailRec(3, 5×4=20):
 *       factorialTailRec(2, 20×3=60):
 *         factorialTailRec(1, 60×2=120):
 *           return 120 [BASE CASE]
 * 
 * Result: 120 ✓
 * 
 * ============================================================================
 * FACTORIAL VALUES
 * ============================================================================
 * 
 * 0! = 1
 * 1! = 1
 * 2! = 2
 * 3! = 6
 * 4! = 24
 * 5! = 120
 * 6! = 720
 * 7! = 5,040
 * 8! = 40,320
 * 9! = 362,880
 * 10! = 3,628,800
 * 11! = 39,916,800
 * 12! = 479,001,600 (last to fit in Int)
 * 13! = 6,227,020,800 (needs Long)
 * 20! = 2,432,902,008,176,640,000
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = FactorialN()
    
    println("=== Factorial using Recursion ===\n")
    
    // Test 1: Base case
    println("Test 1: 0! (base case)")
    println("Recursive: ${solution.factorial(0)}")
    println("Tail Recursive: ${solution.factorialTailRec(0)}")
    println("Expected: 1\n")
    
    // Test 2: Small factorial
    println("Test 2: 5!")
    println("Recursive: ${solution.factorial(5)}")
    println("Tail Recursive: ${solution.factorialTailRec(5)}")
    println("Iterative: ${solution.factorialIterative(5)}")
    println("Expected: 120\n")
    
    // Test 3: Another case
    println("Test 3: 7!")
    println("Recursive: ${solution.factorial(7)}")
    println("Expected: 5040\n")
    
    // Test 4: Larger factorial
    println("Test 4: 10!")
    println("Recursive: ${solution.factorial(10)}")
    println("Tail Recursive: ${solution.factorialTailRec(10)}")
    println("Expected: 3628800\n")
    
    // Test 5: Maximum safe factorial for Int
    println("Test 5: 12! (last to fit in Int)")
    println("Recursive: ${solution.factorial(12)}")
    println("Expected: 479001600\n")
    
    // Test 6: Requires Long
    println("Test 6: 15!")
    println("Recursive: ${solution.factorial(15)}")
    println("Expected: 1307674368000\n")
    
    // Test 7: Near limit
    println("Test 7: 20!")
    println("Recursive: ${solution.factorial(20)}")
    println("Tail Recursive: ${solution.factorialTailRec(20)}")
    println("Expected: 2432902008176640000\n")
    
    // Verification table
    println("=== Factorial Table ===")
    println("N\tRecursive\tTail Rec\tIterative")
    for (n in 0..10) {
        println("$n\t${solution.factorial(n)}\t\t${solution.factorialTailRec(n)}\t\t${solution.factorialIterative(n)}")
    }
}
