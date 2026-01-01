/**
 * ============================================================================
 * PROBLEM: Check Armstrong Number
 * DIFFICULTY: Easy
 * CATEGORY: Basic Math
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * An Armstrong number (also known as narcissistic number) is a number that is
 * equal to the sum of its own digits each raised to the power of the number
 * of digits.
 * 
 * INPUT FORMAT:
 * - A positive integer N
 * - Example: N = 153
 * 
 * OUTPUT FORMAT:
 * - true if Armstrong number, false otherwise
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
 * For a 3-digit number like 153:
 * - Number of digits = 3
 * - Check: 1³ + 5³ + 3³ = 1 + 125 + 27 = 153 ✓
 * 
 * For a 4-digit number like 1634:
 * - Number of digits = 4
 * - Check: 1⁴ + 6⁴ + 3⁴ + 4⁴ = 1 + 1296 + 81 + 256 = 1634 ✓
 * 
 * KEY INSIGHT:
 * The power is equal to the number of digits!
 * 
 * ALGORITHM STEPS:
 * 1. Count number of digits in N
 * 2. Extract each digit
 * 3. Raise each digit to power of digit count
 * 4. Sum all powered digits
 * 5. Compare sum with original number
 * 
 * EXAMPLES:
 * - Single digit: 5 → 5¹ = 5 ✓ (all single digits are Armstrong)
 * - Two digit: 10 → 1² + 0² = 1 ≠ 10 ✗
 * - Three digit: 370 → 3³ + 7³ + 0³ = 27 + 343 + 0 = 370 ✓
 * - Four digit: 1634 → 1⁴ + 6⁴ + 3⁴ + 4⁴ = 1634 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(d) where d is number of digits
 * - Count digits: O(d)
 * - Process each digit: O(d)
 * - Total: O(d)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using a few variables
 * - No additional data structures
 * 
 * ============================================================================
 */

package basics.math

import kotlin.math.pow

class ArmstrongNumber {
    
    /**
     * Check if number is Armstrong number
     * TIME: O(d), SPACE: O(1) where d = digits
     * 
     * @param n The number to check
     * @return true if Armstrong number
     */
    fun isArmstrong(n: Int): Boolean {
        if (n < 0) return false
        
        // Get number of digits
        val digitCount = countDigits(n)
        
        // Calculate sum of digits raised to power
        var sum = 0
        var num = n
        
        while (num > 0) {
            val digit = num % 10
            sum += digit.toDouble().pow(digitCount.toDouble()).toInt()
            num /= 10
        }
        
        return sum == n
    }
    
    /**
     * Count digits in a number
     */
    private fun countDigits(n: Int): Int {
        if (n == 0) return 1
        var count = 0
        var num = n
        while (num > 0) {
            count++
            num /= 10
        }
        return count
    }
    
    /**
     * Find all Armstrong numbers up to limit
     * TIME: O(n * d), SPACE: O(k) where k is count of Armstrong numbers
     */
    fun findAllArmstrong(limit: Int): List<Int> {
        val result = mutableListOf<Int>()
        for (i in 0..limit) {
            if (isArmstrong(i)) {
                result.add(i)
            }
        }
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: n = 153
 * 
 * Step 1: Count digits
 * - digitCount = 3
 * 
 * Step 2: Calculate sum
 * - Extract 3: 3³ = 27, sum = 27
 * - Extract 5: 5³ = 125, sum = 152
 * - Extract 1: 1³ = 1, sum = 153
 * 
 * Step 3: Compare
 * - sum (153) == n (153)? YES!
 * 
 * OUTPUT: true ✓
 * 
 * ============================================================================
 * KNOWN ARMSTRONG NUMBERS
 * ============================================================================
 * 
 * Single digit (all): 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
 * Three digit: 153, 370, 371, 407
 * Four digit: 1634, 8208, 9474
 * Five digit: 54748, 92727, 93084
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = ArmstrongNumber()
    
    println("=== Armstrong Number Checker ===\n")
    
    // Test 1: Single digit
    println("Test 1: Single digit (5)")
    println("Is Armstrong: ${solution.isArmstrong(5)}")
    println("Expected: true\n")
    
    // Test 2: Famous Armstrong number
    println("Test 2: Three digit (153)")
    println("Is Armstrong: ${solution.isArmstrong(153)}")
    println("Expected: true\n")
    
    // Test 3: Not Armstrong
    println("Test 3: Not Armstrong (123)")
    println("Is Armstrong: ${solution.isArmstrong(123)}")
    println("Expected: false\n")
    
    // Test 4: Four digit Armstrong
    println("Test 4: Four digit (1634)")
    println("Is Armstrong: ${solution.isArmstrong(1634)}")
    println("Expected: true\n")
    
    // Test 5: Zero
    println("Test 5: Zero")
    println("Is Armstrong: ${solution.isArmstrong(0)}")
    println("Expected: true\n")
    
    // Test 6: Find all up to 1000
    println("Test 6: All Armstrong numbers up to 1000")
    val armstrongs = solution.findAllArmstrong(1000)
    println("Found: $armstrongs")
    println("Expected: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 153, 370, 371, 407]\n")
    
    // Test 7: More examples
    val testCases = listOf(370, 371, 407, 100, 1000)
    println("Test 7: Multiple test cases")
    for (num in testCases) {
        println("$num: ${solution.isArmstrong(num)}")
    }
}
