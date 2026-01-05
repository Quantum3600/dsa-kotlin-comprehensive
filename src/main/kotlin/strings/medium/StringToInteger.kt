/**
 * ============================================================================
 * PROBLEM: String to Integer (atoi)
 * DIFFICULTY: Medium
 * CATEGORY: Strings - Medium
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement the myAtoi(string s) function, which converts a string to a 
 * 32-bit signed integer (similar to C/C++'s atoi function).
 * 
 * The algorithm for myAtoi(string s) is as follows:
 * 1. Read in and ignore any leading whitespace
 * 2. Check if the next character is '-' or '+', determining sign
 * 3. Read in next characters until non-digit or end of input
 * 4. Convert these digits to an integer
 * 5. If integer is out of 32-bit range, clamp to [-2^31, 2^31 - 1]
 * 
 * INPUT FORMAT:
 * - A string s: s = "42"
 * 
 * OUTPUT FORMAT:
 * - Integer value: 42
 * 
 * CONSTRAINTS:
 * - 0 <= s.length <= 200
 * - s consists of English letters, digits, ' ', '+', '-', and '.'
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We need to parse a string like a human would read a number:
 * 1. Skip leading spaces
 * 2. Check for sign (+ or -)
 * 3. Read digits one by one
 * 4. Stop at first non-digit
 * 5. Handle overflow/underflow
 * 
 * Example: "   -42abc"
 * - Skip "   " (spaces)
 * - Read "-" (negative sign)
 * - Read "42" (digits)
 * - Stop at "a" (non-digit)
 * - Return -42
 * 
 * KEY INSIGHTS:
 * 1. Leading whitespace should be ignored
 * 2. Sign is optional and must come before digits
 * 3. Stop reading at first non-digit
 * 4. Handle integer overflow carefully
 * 5. Return 0 for invalid input
 * 
 * ALGORITHM STEPS:
 * 1. Skip leading whitespace
 * 2. Check for optional sign (+ or -)
 * 3. Read digits and build number
 * 4. For each digit:
 *    a. Check for overflow before adding
 *    b. Multiply result by 10
 *    c. Add current digit
 * 5. Apply sign and return
 * 
 * VISUAL EXAMPLE:
 * s = "   -42"
 * 
 * Step 1: Skip spaces
 * "   -42"
 *    ^
 *    Start here
 * 
 * Step 2: Check sign
 * "   -42"
 *     ^
 *     sign = -1
 * 
 * Step 3: Read digits
 * "   -42"
 *      ^
 *      digit = 4
 *      result = 0 * 10 + 4 = 4
 * 
 * "   -42"
 *       ^
 *       digit = 2
 *       result = 4 * 10 + 2 = 42
 * 
 * Step 4: Apply sign
 * result = 42 * -1 = -42
 * 
 * OUTPUT: -42
 * 
 * OVERFLOW HANDLING:
 * INT_MAX = 2147483647 (2^31 - 1)
 * INT_MIN = -2147483648 (-2^31)
 * 
 * Before adding digit, check:
 * If result > INT_MAX / 10, overflow will occur
 * If result == INT_MAX / 10 and digit > 7, overflow will occur
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Manual parsing with overflow check: O(n) time, O(1) space - OPTIMAL
 * 2. Using regex: O(n) time, O(n) space - Cleaner but less efficient
 * 3. Try-catch: O(n) time, O(1) space - Not recommended (exception overhead)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through the string
 * - Skip whitespace: O(k) where k is number of spaces
 * - Read digits: O(m) where m is number of digits
 * - Total: O(n) where n is string length
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only use a few variables (index, sign, result)
 * - No additional data structures
 * - Space is independent of input size
 * 
 * WHY THIS IS OPTIMAL:
 * - Must read the string to parse it: O(n) minimum
 * - Only need constant space for tracking
 * - Cannot improve on O(n) time and O(1) space
 * 
 * ============================================================================
 */

package strings.medium

class StringToInteger {
    
    companion object {
        const val INT_MAX = 2147483647  // 2^31 - 1
        const val INT_MIN = -2147483648 // -2^31
    }
    
    /**
     * Converts string to integer (atoi implementation)
     * 
     * @param s Input string
     * @return Integer value
     */
    fun myAtoi(s: String): Int {
        // Edge case: empty string
        if (s.isEmpty()) return 0
        
        var index = 0
        val n = s.length
        
        // Step 1: Skip leading whitespace
        while (index < n && s[index] == ' ') {
            index++
        }
        
        // Check if we reached end after skipping spaces
        if (index == n) return 0
        
        // Step 2: Check for sign
        var sign = 1
        if (s[index] == '+' || s[index] == '-') {
            sign = if (s[index] == '-') -1 else 1
            index++
        }
        
        // Step 3: Read digits and build number
        var result = 0
        
        while (index < n && s[index].isDigit()) {
            val digit = s[index] - '0'
            
            // Check for overflow before multiplying and adding
            // If result > INT_MAX / 10, then result * 10 will overflow
            // If result == INT_MAX / 10 and digit > 7, then result * 10 + digit will overflow
            if (result > INT_MAX / 10 || (result == INT_MAX / 10 && digit > 7)) {
                return if (sign == 1) INT_MAX else INT_MIN
            }
            
            result = result * 10 + digit
            index++
        }
        
        // Step 4: Apply sign and return
        return result * sign
    }
    
    /**
     * Alternative: Using Kotlin's toLongOrNull with range checking
     * Simpler but relies on standard library
     */
    fun myAtoiSimple(s: String): Int {
        // Trim whitespace
        val trimmed = s.trim()
        if (trimmed.isEmpty()) return 0
        
        // Find where digits start
        var startIndex = 0
        var sign = 1
        
        if (trimmed[0] == '+' || trimmed[0] == '-') {
            sign = if (trimmed[0] == '-') -1 else 1
            startIndex = 1
        }
        
        // Extract digits
        var endIndex = startIndex
        while (endIndex < trimmed.length && trimmed[endIndex].isDigit()) {
            endIndex++
        }
        
        if (startIndex == endIndex) return 0
        
        // Parse to Long to detect overflow
        val digitStr = trimmed.substring(startIndex, endIndex)
        val num = digitStr.toLongOrNull() ?: return 0
        val result = num * sign
        
        // Clamp to 32-bit range
        return when {
            result > INT_MAX -> INT_MAX
            result < INT_MIN -> INT_MIN
            else -> result.toInt()
        }
    }
    
    /**
     * Helper function to explain overflow detection
     */
    fun explainOverflow(current: Int, digit: Int, sign: Int): Boolean {
        // Check if adding digit will cause overflow
        if (current > INT_MAX / 10) {
            println("Overflow: $current > ${INT_MAX / 10}")
            return true
        }
        if (current == INT_MAX / 10 && digit > 7) {
            println("Overflow: $current == ${INT_MAX / 10} and digit $digit > 7")
            return true
        }
        return false
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "   -42"
 * 
 * DETAILED EXECUTION:
 * 
 * Initial state:
 * index = 0, n = 6, sign = 1, result = 0
 * 
 * Step 1: Skip whitespace
 * index = 0: s[0] = ' ' → skip, index = 1
 * index = 1: s[1] = ' ' → skip, index = 2
 * index = 2: s[2] = ' ' → skip, index = 3
 * index = 3: s[3] = '-' → stop whitespace loop
 * 
 * Step 2: Check sign
 * s[3] = '-' → sign = -1, index = 4
 * 
 * Step 3: Read digits
 * 
 * Iteration 1:
 * index = 4: s[4] = '4' → is digit ✓
 * digit = '4' - '0' = 4
 * Check overflow: 0 <= 214748364, safe ✓
 * result = 0 * 10 + 4 = 4
 * index = 5
 * 
 * Iteration 2:
 * index = 5: s[5] = '2' → is digit ✓
 * digit = '2' - '0' = 2
 * Check overflow: 4 <= 214748364, safe ✓
 * result = 4 * 10 + 2 = 42
 * index = 6
 * 
 * index = 6: reached end of string
 * 
 * Step 4: Apply sign
 * result = 42 * -1 = -42
 * 
 * OUTPUT: -42
 * 
 * ---
 * 
 * Example 2: s = "4193 with words"
 * 
 * Skip spaces: none
 * Sign: none (sign = 1)
 * Read digits:
 *   '4' → result = 4
 *   '1' → result = 41
 *   '9' → result = 419
 *   '3' → result = 4193
 *   ' ' → stop (not a digit)
 * 
 * OUTPUT: 4193
 * 
 * ---
 * 
 * Example 3: s = "2147483648" (INT_MAX + 1)
 * 
 * Skip spaces: none
 * Sign: none (sign = 1)
 * Read digits up to 214748364:
 *   result = 214748364
 *   Next digit = 8
 *   Check: 214748364 == INT_MAX/10 and 8 > 7
 *   Overflow detected!
 * 
 * OUTPUT: 2147483647 (INT_MAX)
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty string "" → 0
 * 2. Only spaces "   " → 0
 * 3. Only sign "+" → 0
 * 4. No digits "abc" → 0
 * 5. Positive overflow "2147483648" → 2147483647
 * 6. Negative overflow "-2147483649" → -2147483648
 * 7. Leading zeros "00042" → 42
 * 8. Mixed content "42abc" → 42
 * 9. Sign after spaces "   +42" → 42
 * 10. Multiple signs "+-42" → 0 (invalid)
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not checking overflow before operation
 * ❌ result = result * 10 + digit (then check)
 * ✅ Check before: result > INT_MAX / 10
 * 
 * MISTAKE 2: Wrong overflow boundary
 * ❌ Checking result > INT_MAX (too late)
 * ✅ Check result > INT_MAX / 10 (before multiply)
 * 
 * MISTAKE 3: Not handling negative overflow
 * ❌ Only checking positive overflow
 * ✅ Return INT_MIN for negative overflow
 * 
 * MISTAKE 4: Continuing after non-digit
 * ❌ Trying to parse "42a43" as two numbers
 * ✅ Stop at first non-digit
 * 
 * MISTAKE 5: Not handling sign correctly
 * ❌ Applying sign to each digit
 * ✅ Apply sign at the end
 * 
 * MISTAKE 6: Integer overflow during parsing
 * ❌ Using Int type for intermediate result
 * ✅ Check overflow before each operation
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Compilers**: Parsing numeric literals in code
 * 2. **Database Systems**: Converting string input to numbers
 * 3. **Configuration Files**: Reading numeric settings
 * 4. **User Input**: Validating and converting form data
 * 5. **Command Line Tools**: Parsing arguments
 * 6. **Network Protocols**: Parsing numeric headers
 * 7. **File Parsers**: Reading numeric data from text files
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * - Valid Number
 * - Reverse Integer
 * - String to Integer (atoi)
 * - Integer to English Words
 * - Calculator implementations
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = StringToInteger()
    
    println("=== Testing String to Integer (atoi) ===\n")
    
    // Test 1: Basic number
    println("Test 1: Basic number")
    println("Input: s = \"42\"")
    println("Output: ${solution.myAtoi("42")}")
    println("Expected: 42\n")
    
    // Test 2: With leading spaces
    println("Test 2: Leading spaces")
    println("Input: s = \"   -42\"")
    println("Output: ${solution.myAtoi("   -42")}")
    println("Expected: -42\n")
    
    // Test 3: With trailing non-digits
    println("Test 3: Trailing non-digits")
    println("Input: s = \"4193 with words\"")
    println("Output: ${solution.myAtoi("4193 with words")}")
    println("Expected: 4193\n")
    
    // Test 4: Overflow
    println("Test 4: Positive overflow")
    println("Input: s = \"2147483648\"")
    println("Output: ${solution.myAtoi("2147483648")}")
    println("Expected: 2147483647 (INT_MAX)\n")
    
    // Test 5: Negative overflow
    println("Test 5: Negative overflow")
    println("Input: s = \"-2147483649\"")
    println("Output: ${solution.myAtoi("-2147483649")}")
    println("Expected: -2147483648 (INT_MIN)\n")
    
    // Test 6: No digits
    println("Test 6: No digits")
    println("Input: s = \"words and 987\"")
    println("Output: ${solution.myAtoi("words and 987")}")
    println("Expected: 0\n")
    
    // Test 7: Positive sign
    println("Test 7: Positive sign")
    println("Input: s = \"+123\"")
    println("Output: ${solution.myAtoi("+123")}")
    println("Expected: 123\n")
    
    // Test 8: Empty string
    println("Test 8: Empty string")
    println("Input: s = \"\"")
    println("Output: ${solution.myAtoi("")}")
    println("Expected: 0\n")
    
    // Test 9: Only spaces
    println("Test 9: Only spaces")
    println("Input: s = \"   \"")
    println("Output: ${solution.myAtoi("   ")}")
    println("Expected: 0\n")
    
    // Test 10: Leading zeros
    println("Test 10: Leading zeros")
    println("Input: s = \"00042\"")
    println("Output: ${solution.myAtoi("00042")}")
    println("Expected: 42\n")
}
