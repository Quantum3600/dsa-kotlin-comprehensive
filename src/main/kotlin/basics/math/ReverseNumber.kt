/**
 * ============================================================================
 * PROBLEM: Reverse a Number
 * DIFFICULTY: Easy
 * CATEGORY: Basic Math
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an integer N, reverse its digits.
 * 
 * INPUT FORMAT:
 * - An integer N (can be positive or negative)
 * - Example: N = 12345
 * 
 * OUTPUT FORMAT:
 * - The number with its digits reversed
 * - Example: 54321
 * 
 * CONSTRAINTS:
 * - -2^31 <= N <= 2^31 - 1
 * - If reversed number overflows, return 0
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To reverse digits, we repeatedly:
 * 1. Extract the last digit using modulo (%)
 * 2. Add it to our result (multiplying result by 10 to shift left)
 * 3. Remove the last digit from original number (divide by 10)
 * 
 * VISUAL EXAMPLE:
 * N = 12345
 * 
 * Step 1: Extract 5, result = 5, N = 1234
 * Step 2: Extract 4, result = 54, N = 123
 * Step 3: Extract 3, result = 543, N = 12
 * Step 4: Extract 2, result = 5432, N = 1
 * Step 5: Extract 1, result = 54321, N = 0
 * 
 * ALGORITHM STEPS:
 * 1. Initialize result = 0
 * 2. Handle negative numbers (remember sign)
 * 3. While N > 0:
 *    a. Extract last digit: digit = N % 10
 *    b. Check for overflow
 *    c. Add to result: result = result * 10 + digit
 *    d. Remove last digit: N = N / 10
 * 4. Apply sign and return
 * 
 * OVERFLOW HANDLING:
 * - Int.MAX_VALUE = 2147483647
 * - If result > 214748364, overflow will occur
 * - Also check if result == 214748364 and digit > 7
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log₁₀ N) = O(d) where d is number of digits
 * - We process each digit once
 * - Number of iterations = number of digits
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using a few variables
 * - No additional data structures
 * 
 * ============================================================================
 */

package basics.math

class ReverseNumber {
    
    /**
     * Reverse digits of an integer
     * TIME: O(log N), SPACE: O(1)
     * 
     * @param n The integer to reverse
     * @return Reversed number, or 0 if overflow
     */
    fun reverse(n: Int): Int {
        // Handle sign
        val isNegative = n < 0
        var num = if (isNegative) -n else n
        
        var result = 0
        
        while (num > 0) {
            // Extract last digit
            val digit = num % 10
            
            // Check for overflow before multiplying by 10
            // Int.MAX_VALUE = 2147483647
            // If result > 214748364, overflow will occur
            if (result > Int.MAX_VALUE / 10) {
                return 0
            }
            
            // If result == 214748364, check if adding digit causes overflow
            if (result == Int.MAX_VALUE / 10 && digit > 7) {
                return 0
            }
            
            // Build reversed number
            result = result * 10 + digit
            
            // Remove last digit from original
            num /= 10
        }
        
        // Apply original sign
        return if (isNegative) -result else result
    }
    
    /**
     * Reverse using string conversion
     * Simpler but uses extra space
     */
    fun reverseString(n: Int): Int {
        val isNegative = n < 0
        val num = if (isNegative) -n else n
        
        try {
            val reversed = num.toString().reversed().toInt()
            return if (isNegative) -reversed else reversed
        } catch (e: NumberFormatException) {
            return 0  // Overflow occurred
        }
    }
    
    /**
     * Check if number is palindrome
     * A number is palindrome if it equals its reverse
     */
    fun isPalindrome(n: Int): Boolean {
        // Negative numbers are not palindromes
        if (n < 0) return false
        
        // Reverse and compare
        return n == reverse(n)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: n = 12345
 * 
 * Initial: num = 12345, result = 0
 * 
 * Iteration 1:
 * - digit = 12345 % 10 = 5
 * - result = 0 * 10 + 5 = 5
 * - num = 12345 / 10 = 1234
 * 
 * Iteration 2:
 * - digit = 1234 % 10 = 4
 * - result = 5 * 10 + 4 = 54
 * - num = 1234 / 10 = 123
 * 
 * Iteration 3:
 * - digit = 123 % 10 = 3
 * - result = 54 * 10 + 3 = 543
 * - num = 123 / 10 = 12
 * 
 * Iteration 4:
 * - digit = 12 % 10 = 2
 * - result = 543 * 10 + 2 = 5432
 * - num = 12 / 10 = 1
 * 
 * Iteration 5:
 * - digit = 1 % 10 = 1
 * - result = 5432 * 10 + 1 = 54321
 * - num = 1 / 10 = 0
 * 
 * Loop ends
 * Return 54321 ✓
 * 
 * ============================================================================
 * OVERFLOW EXAMPLE
 * ============================================================================
 * 
 * INPUT: n = 1534236469 (will overflow when reversed)
 * 
 * Reversed would be: 9646324351
 * But Int.MAX_VALUE = 2147483647
 * 
 * During reversal:
 * - When result = 964632435
 * - Next digit = 1
 * - result * 10 would be 9646324350 > Int.MAX_VALUE
 * - Return 0 (overflow detected)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = ReverseNumber()
    
    println("=== Reverse Number ===\n")
    
    // Test 1: Positive number
    println("Test 1: n = 12345")
    println("Reversed: ${solution.reverse(12345)}")
    println("Expected: 54321\n")
    
    // Test 2: Negative number
    println("Test 2: n = -12345")
    println("Reversed: ${solution.reverse(-12345)}")
    println("Expected: -54321\n")
    
    // Test 3: Number with trailing zeros
    println("Test 3: n = 12000")
    println("Reversed: ${solution.reverse(12000)}")
    println("Expected: 21 (leading zeros dropped)\n")
    
    // Test 4: Single digit
    println("Test 4: n = 7")
    println("Reversed: ${solution.reverse(7)}")
    println("Expected: 7\n")
    
    // Test 5: Zero
    println("Test 5: n = 0")
    println("Reversed: ${solution.reverse(0)}")
    println("Expected: 0\n")
    
    // Test 6: Palindrome
    println("Test 6: n = 12321")
    println("Reversed: ${solution.reverse(12321)}")
    println("Is Palindrome: ${solution.isPalindrome(12321)}")
    println("Expected: 12321, true\n")
    
    // Test 7: Overflow case
    println("Test 7: n = 1534236469 (will overflow)")
    println("Reversed: ${solution.reverse(1534236469)}")
    println("Expected: 0 (overflow)\n")
    
    // Test 8: Near max value
    println("Test 8: n = 2147483647 (Int.MAX_VALUE)")
    println("Reversed: ${solution.reverse(2147483647)}")
    println("Expected: 0 (overflow)\n")
    
    // Test 9: Using string method
    println("Test 9: String method, n = 98765")
    println("Reversed: ${solution.reverseString(98765)}")
    println("Expected: 56789\n")
    
    // Test 10: Check palindromes
    println("Test 10: Palindrome Checks")
    val testCases = listOf(121, 12321, 123, -121, 0)
    for (num in testCases) {
        println("$num is palindrome: ${solution.isPalindrome(num)}")
    }
}
