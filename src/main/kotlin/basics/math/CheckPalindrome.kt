/**
 * ============================================================================
 * PROBLEM: Check if Number is Palindrome
 * DIFFICULTY: Easy
 * CATEGORY: Basic Math
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Determine whether an integer is a palindrome. An integer is a palindrome
 * when it reads the same backward as forward.
 * 
 * INPUT FORMAT:
 * - An integer N
 * - Example: N = 121
 * 
 * OUTPUT FORMAT:
 * - true if palindrome, false otherwise
 * - Example: true
 * 
 * CONSTRAINTS:
 * - -2^31 <= N <= 2^31 - 1
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * A number is a palindrome if reversing it gives the same number.
 * 121 → reverse → 121 (same, so palindrome)
 * 123 → reverse → 321 (different, not palindrome)
 * 
 * KEY INSIGHTS:
 * 1. Negative numbers are NOT palindromes (-121 becomes 121-)
 * 2. Numbers ending in 0 (except 0 itself) are NOT palindromes
 * 3. We can reverse and compare, OR compare digits from both ends
 * 
 * ALGORITHM STEPS (Approach 1: Full Reversal):
 * 1. If n < 0, return false
 * 2. Reverse the number
 * 3. Compare original with reversed
 * 4. Return true if equal
 * 
 * ALGORITHM STEPS (Approach 2: Half Reversal - OPTIMAL):
 * 1. If n < 0 or (n % 10 == 0 and n != 0), return false
 * 2. Reverse only half the digits
 * 3. Compare first half with reversed second half
 * 4. Handle odd/even length cases
 * 
 * VISUAL EXAMPLE:
 * n = 12321
 * 
 * Half reversal:
 * - Start: n = 12321, reversed = 0
 * - Step 1: n = 1232, reversed = 1
 * - Step 2: n = 123, reversed = 12
 * - Step 3: n = 12, reversed = 123
 * - Stop when reversed >= n
 * - Compare: n (12) with reversed/10 (12) → Equal! Palindrome!
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log₁₀ N) = O(d) where d is number of digits
 * - Process half the digits in optimal approach
 * - Still O(log N) asymptotically
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using a few variables
 * - No recursion or additional data structures
 * 
 * ============================================================================
 */

package basics.math

class CheckPalindrome {
    
    /**
     * Check palindrome by full reversal
     * TIME: O(log N), SPACE: O(1)
     * 
     * @param n The integer to check
     * @return true if palindrome, false otherwise
     */
    fun isPalindrome(n: Int): Boolean {
        // Negative numbers are not palindromes
        if (n < 0) return false
        
        // Single digit numbers are palindromes
        if (n < 10) return true
        
        // Reverse the number
        var original = n
        var reversed = 0
        
        while (original > 0) {
            reversed = reversed * 10 + original % 10
            original /= 10
        }
        
        // Compare
        return n == reversed
    }
    
    /**
     * Check palindrome by half reversal (OPTIMAL)
     * TIME: O(log N), SPACE: O(1)
     * 
     * Only reverses half the number, more efficient
     */
    fun isPalindromeOptimal(n: Int): Boolean {
        // Negative numbers are not palindromes
        // Numbers ending in 0 (except 0 itself) are not palindromes
        if (n < 0 || (n % 10 == 0 && n != 0)) return false
        
        // Single digit is palindrome
        if (n < 10) return true
        
        var num = n
        var reversedHalf = 0
        
        // Reverse half the digits
        // Stop when reversed half becomes >= remaining number
        while (num > reversedHalf) {
            reversedHalf = reversedHalf * 10 + num % 10
            num /= 10
        }
        
        // For even length: num == reversedHalf
        // For odd length: num == reversedHalf / 10 (ignore middle digit)
        // Example: 12321 → num=12, reversedHalf=123 → 12 == 123/10
        return num == reversedHalf || num == reversedHalf / 10
    }
    
    /**
     * Check palindrome using string
     * Simple but uses extra space
     */
    fun isPalindromeString(n: Int): Boolean {
        if (n < 0) return false
        val str = n.toString()
        return str == str.reversed()
    }
    
    /**
     * Check if a number can be rearranged to form palindrome
     * 
     * A number can form palindrome if at most one digit has odd frequency
     */
    fun canFormPalindrome(n: Int): Boolean {
        val freq = IntArray(10)
        var num = n
        
        // Count frequency of each digit
        while (num > 0) {
            freq[num % 10]++
            num /= 10
        }
        
        // Count digits with odd frequency
        var oddCount = 0
        for (count in freq) {
            if (count % 2 != 0) oddCount++
        }
        
        // At most one digit can have odd frequency
        return oddCount <= 1
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH - Half Reversal
 * ============================================================================
 * 
 * INPUT: n = 12321
 * 
 * Initial: num = 12321, reversedHalf = 0
 * 
 * Iteration 1:
 * - num > reversedHalf? YES (12321 > 0)
 * - reversedHalf = 0 * 10 + 12321 % 10 = 1
 * - num = 12321 / 10 = 1232
 * 
 * Iteration 2:
 * - num > reversedHalf? YES (1232 > 1)
 * - reversedHalf = 1 * 10 + 1232 % 10 = 12
 * - num = 1232 / 10 = 123
 * 
 * Iteration 3:
 * - num > reversedHalf? YES (123 > 12)
 * - reversedHalf = 12 * 10 + 123 % 10 = 123
 * - num = 123 / 10 = 12
 * 
 * Iteration 4:
 * - num > reversedHalf? NO (12 < 123)
 * - Exit loop
 * 
 * Check: num == reversedHalf / 10?
 * - 12 == 123 / 10?
 * - 12 == 12? YES!
 * 
 * Return true ✓
 * 
 * ============================================================================
 * WHY HALF REVERSAL WORKS
 * ============================================================================
 * 
 * For even length (e.g., 1221):
 * - First half: 12
 * - Second half: 21 → reversed → 12
 * - Compare: 12 == 12 ✓
 * 
 * For odd length (e.g., 12321):
 * - First half: 12
 * - Second half: 321 → reversed → 123
 * - Middle digit (3) doesn't matter for palindrome
 * - Compare: 12 == 123/10 = 12 ✓
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = CheckPalindrome()
    
    println("=== Check Palindrome Number ===\n")
    
    // Test 1: Positive palindrome
    println("Test 1: n = 121")
    println("Basic: ${solution.isPalindrome(121)}")
    println("Optimal: ${solution.isPalindromeOptimal(121)}")
    println("String: ${solution.isPalindromeString(121)}")
    println("Expected: true\n")
    
    // Test 2: Not palindrome
    println("Test 2: n = 123")
    println("Basic: ${solution.isPalindrome(123)}")
    println("Optimal: ${solution.isPalindromeOptimal(123)}")
    println("Expected: false\n")
    
    // Test 3: Negative number
    println("Test 3: n = -121")
    println("Basic: ${solution.isPalindrome(-121)}")
    println("Optimal: ${solution.isPalindromeOptimal(-121)}")
    println("Expected: false\n")
    
    // Test 4: Single digit
    println("Test 4: n = 7")
    println("Basic: ${solution.isPalindrome(7)}")
    println("Optimal: ${solution.isPalindromeOptimal(7)}")
    println("Expected: true\n")
    
    // Test 5: Number ending in 0
    println("Test 5: n = 10")
    println("Basic: ${solution.isPalindrome(10)}")
    println("Optimal: ${solution.isPalindromeOptimal(10)}")
    println("Expected: false\n")
    
    // Test 6: Zero
    println("Test 6: n = 0")
    println("Basic: ${solution.isPalindrome(0)}")
    println("Optimal: ${solution.isPalindromeOptimal(0)}")
    println("Expected: true\n")
    
    // Test 7: Odd length palindrome
    println("Test 7: n = 12321")
    println("Basic: ${solution.isPalindrome(12321)}")
    println("Optimal: ${solution.isPalindromeOptimal(12321)}")
    println("Expected: true\n")
    
    // Test 8: Even length palindrome
    println("Test 8: n = 1221")
    println("Basic: ${solution.isPalindrome(1221)}")
    println("Optimal: ${solution.isPalindromeOptimal(1221)}")
    println("Expected: true\n")
    
    // Test 9: Can form palindrome
    println("Test 9: Can Rearrange to Palindrome")
    val testNumbers = listOf(121, 123, 1221, 1231)
    for (num in testNumbers) {
        println("$num can form palindrome: ${solution.canFormPalindrome(num)}")
    }
    println()
    
    // Performance comparison
    println("=== Performance Comparison ===")
    val testNum = 123454321
    val iterations = 100000
    
    var start = System.nanoTime()
    repeat(iterations) { solution.isPalindrome(testNum) }
    var time = (System.nanoTime() - start) / 1000
    println("Full reversal: $time μs for $iterations iterations")
    
    start = System.nanoTime()
    repeat(iterations) { solution.isPalindromeOptimal(testNum) }
    time = (System.nanoTime() - start) / 1000
    println("Half reversal: $time μs for $iterations iterations")
    println("(Half reversal is faster!)")
}
