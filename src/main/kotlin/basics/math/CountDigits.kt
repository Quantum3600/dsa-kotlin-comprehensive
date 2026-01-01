/**
 * ============================================================================
 * PROBLEM: Count Digits in a Number
 * DIFFICULTY: Easy
 * CATEGORY: Basic Math
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a positive integer N, count the number of digits in N.
 * 
 * INPUT FORMAT:
 * - A positive integer N
 * - Example: N = 12345
 * 
 * OUTPUT FORMAT:
 * - Number of digits in N
 * - Example: 5
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To count digits, we can repeatedly divide the number by 10 until it becomes 0.
 * Each division by 10 removes one digit from the right.
 * 
 * Example: 12345
 * - 12345 / 10 = 1234 (removed 5), count = 1
 * - 1234 / 10 = 123 (removed 4), count = 2
 * - 123 / 10 = 12 (removed 3), count = 3
 * - 12 / 10 = 1 (removed 2), count = 4
 * - 1 / 10 = 0 (removed 1), count = 5
 * 
 * ALGORITHM STEPS:
 * 1. Initialize count = 0
 * 2. While N > 0:
 *    a. Increment count
 *    b. Divide N by 10 (N = N / 10)
 * 3. Return count
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Iterative Division: O(log N) time, O(1) space - IMPLEMENTED
 * 2. String Conversion: O(log N) time, O(log N) space - Simple but uses extra space
 * 3. Logarithm: O(1) time, O(1) space - Using log10(N) + 1
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log₁₀ N) = O(d) where d is number of digits
 * - We divide N by 10 in each iteration
 * - Number of iterations = number of digits
 * - For N = 12345, iterations = 5
 * - For N = 1000000, iterations = 7
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using count variable
 * - No additional data structures
 * - No recursive calls
 * 
 * WHY O(log N):
 * - Each division by 10 reduces the number by a factor of 10
 * - This is logarithmic behavior: log₁₀(N)
 * - If N has d digits, log₁₀(N) ≈ d-1
 * 
 * ============================================================================
 */

package basics.math

class CountDigits {
    
    /**
     * Count digits using iterative division
     * TIME: O(log N), SPACE: O(1)
     * 
     * @param n The positive integer
     * @return Number of digits
     */
    fun countDigits(n: Int): Int {
        // Handle edge case: 0 has 1 digit
        if (n == 0) return 1
        
        // Initialize counter
        var count = 0
        
        // Create a copy to avoid modifying original
        var num = n
        
        // Keep dividing by 10 until number becomes 0
        while (num > 0) {
            // Increment count for each digit
            count++
            
            // Remove rightmost digit
            num /= 10
        }
        
        return count
    }
    
    /**
     * Count digits using string conversion
     * TIME: O(log N), SPACE: O(log N)
     * 
     * Simpler but uses extra space for string
     */
    fun countDigitsString(n: Int): Int {
        // Convert to string and get length
        return n.toString().length
    }
    
    /**
     * Count digits using logarithm
     * TIME: O(1), SPACE: O(1)
     * 
     * Mathematical approach: digits = floor(log₁₀(n)) + 1
     */
    fun countDigitsLog(n: Int): Int {
        // Handle special case
        if (n == 0) return 1
        
        // Use logarithm base 10
        return kotlin.math.floor(kotlin.math.log10(n.toDouble())).toInt() + 1
    }
    
    /**
     * Count digits that divide N evenly
     * 
     * Example: N = 12, digits 1 and 2 both divide 12
     * Output: 2
     */
    fun countDivisibleDigits(n: Int): Int {
        var count = 0
        var num = n
        
        while (num > 0) {
            val digit = num % 10
            
            // Check if digit divides n (avoid division by zero)
            if (digit != 0 && n % digit == 0) {
                count++
            }
            
            num /= 10
        }
        
        return count
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: n = 12345
 * 
 * EXECUTION TRACE:
 * 
 * Initial: num = 12345, count = 0
 * 
 * Iteration 1:
 * - num > 0? YES (12345)
 * - count++ → count = 1
 * - num /= 10 → num = 1234
 * 
 * Iteration 2:
 * - num > 0? YES (1234)
 * - count++ → count = 2
 * - num /= 10 → num = 123
 * 
 * Iteration 3:
 * - num > 0? YES (123)
 * - count++ → count = 3
 * - num /= 10 → num = 12
 * 
 * Iteration 4:
 * - num > 0? YES (12)
 * - count++ → count = 4
 * - num /= 10 → num = 1
 * 
 * Iteration 5:
 * - num > 0? YES (1)
 * - count++ → count = 5
 * - num /= 10 → num = 0
 * 
 * Loop ends (num = 0)
 * Return count = 5 ✓
 * 
 * ============================================================================
 * MATHEMATICAL INSIGHT
 * ============================================================================
 * 
 * Why log₁₀(N) gives digit count:
 * 
 * - Single digit (1-9): log₁₀(1) to log₁₀(9) = 0 to 0.95 → floor = 0, +1 = 1
 * - Two digits (10-99): log₁₀(10) to log₁₀(99) = 1 to 1.99 → floor = 1, +1 = 2
 * - Three digits (100-999): log₁₀(100) to log₁₀(999) = 2 to 2.99 → floor = 2, +1 = 3
 * 
 * Formula: digits = floor(log₁₀(N)) + 1
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Single digit (5): 1 iteration, count = 1
 * 2. Zero (0): Special case, return 1
 * 3. Two digits (42): 2 iterations, count = 2
 * 4. Large number (1000000): 7 iterations, count = 7
 * 5. Maximum int (2147483647): 10 iterations, count = 10
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = CountDigits()
    
    println("=== Count Digits in a Number ===\n")
    
    // Test 1: Single digit
    println("Test 1: n = 5")
    println("Iterative: ${solution.countDigits(5)}")
    println("String: ${solution.countDigitsString(5)}")
    println("Log: ${solution.countDigitsLog(5)}")
    println("Expected: 1\n")
    
    // Test 2: Two digits
    println("Test 2: n = 42")
    println("Iterative: ${solution.countDigits(42)}")
    println("String: ${solution.countDigitsString(42)}")
    println("Log: ${solution.countDigitsLog(42)}")
    println("Expected: 2\n")
    
    // Test 3: Multiple digits
    println("Test 3: n = 12345")
    println("Iterative: ${solution.countDigits(12345)}")
    println("String: ${solution.countDigitsString(12345)}")
    println("Log: ${solution.countDigitsLog(12345)}")
    println("Expected: 5\n")
    
    // Test 4: Power of 10
    println("Test 4: n = 1000")
    println("Iterative: ${solution.countDigits(1000)}")
    println("String: ${solution.countDigitsString(1000)}")
    println("Log: ${solution.countDigitsLog(1000)}")
    println("Expected: 4\n")
    
    // Test 5: Large number
    println("Test 5: n = 987654321")
    println("Iterative: ${solution.countDigits(987654321)}")
    println("String: ${solution.countDigitsString(987654321)}")
    println("Log: ${solution.countDigitsLog(987654321)}")
    println("Expected: 9\n")
    
    // Test 6: Zero
    println("Test 6: n = 0")
    println("Iterative: ${solution.countDigits(0)}")
    println("String: ${solution.countDigitsString(0)}")
    println("Log: ${solution.countDigitsLog(0)}")
    println("Expected: 1\n")
    
    // Test 7: Count divisible digits
    println("Test 7: Count digits that divide n = 12")
    println("Result: ${solution.countDivisibleDigits(12)}")
    println("Explanation: 1 divides 12, 2 divides 12")
    println("Expected: 2\n")
    
    // Test 8: Count divisible digits
    println("Test 8: Count digits that divide n = 128")
    println("Result: ${solution.countDivisibleDigits(128)}")
    println("Explanation: 1 divides 128, 2 divides 128, 8 divides 128")
    println("Expected: 3\n")
    
    // Performance comparison
    println("=== Performance Comparison ===")
    val testNum = 123456789
    val iterations = 100000
    
    var start = System.nanoTime()
    repeat(iterations) { solution.countDigits(testNum) }
    var time = (System.nanoTime() - start) / 1000
    println("Iterative: $time μs for $iterations iterations")
    
    start = System.nanoTime()
    repeat(iterations) { solution.countDigitsString(testNum) }
    time = (System.nanoTime() - start) / 1000
    println("String: $time μs for $iterations iterations")
    
    start = System.nanoTime()
    repeat(iterations) { solution.countDigitsLog(testNum) }
    time = (System.nanoTime() - start) / 1000
    println("Logarithm: $time μs for $iterations iterations")
}
