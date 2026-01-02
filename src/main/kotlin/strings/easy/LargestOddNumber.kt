/**
 * ============================================================================
 * PROBLEM: Largest Odd Number in String
 * DIFFICULTY: Easy
 * CATEGORY: Strings - Easy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given a string num, representing a large integer. Return the 
 * largest-valued odd integer (as a string) that is a non-empty substring 
 * of num, or an empty string "" if no odd integer exists.
 * 
 * A substring is a contiguous sequence of characters within a string.
 * An odd number is one whose last digit is 1, 3, 5, 7, or 9.
 * 
 * INPUT FORMAT:
 * - A string num: "52"
 * 
 * OUTPUT FORMAT:
 * - String representing largest odd substring: "5"
 * 
 * CONSTRAINTS:
 * - 1 <= num.length <= 10^5
 * - num only consists of digits
 * - num does not contain any leading zeros
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * A number is odd if and only if its last digit is odd.
 * For the largest odd substring, we want:
 * 1. The longest possible substring (more digits = larger number)
 * 2. That ends with an odd digit
 * 
 * KEY INSIGHT:
 * - Start from the beginning and extend as far as possible
 * - Stop at the last odd digit we encounter
 * - This gives us the largest odd substring
 * 
 * Why? Because:
 * - Including more digits from the left makes the number larger
 * - We need to end at an odd digit for the number to be odd
 * - The rightmost odd digit gives us the longest (largest) substring
 * 
 * ALGORITHM STEPS:
 * 1. Scan string from right to left
 * 2. Find the first (rightmost) odd digit
 * 3. Return substring from start to that position (inclusive)
 * 4. If no odd digit found, return empty string
 * 
 * VISUAL EXAMPLE:
 * Input: "52468"
 * 
 * Scan from right:
 * '8' → even, skip
 * '6' → even, skip
 * '4' → even, skip
 * '2' → even, skip
 * '5' → odd, FOUND!
 * 
 * Return substring [0..0] = "5"
 * 
 * ---
 * 
 * Input: "35427"
 * 
 * Scan from right:
 * '7' → odd, FOUND at index 4!
 * 
 * Return substring [0..4] = "35427"
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Right-to-left scan: O(n) time, O(1) space - OPTIMAL
 * 2. Generate all substrings: O(n²) time, O(n) space - Inefficient
 * 3. Left-to-right with tracking: O(n) time, O(1) space - Same but less intuitive
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - In worst case, scan entire string from right to left
 * - Each character checked once
 * - Substring operation is O(n) but only done once
 * - Total: O(n) where n is length of string
 * 
 * SPACE COMPLEXITY: O(1) or O(n)
 * - Algorithm itself: O(1) - only use index variable
 * - Output substring: O(n) - required for return value
 * - In Kotlin, substring creates a new string: O(n)
 * - If we consider output, it's O(n), otherwise O(1)
 * 
 * WHY THIS IS OPTIMAL:
 * Must check at least one character to determine if any odd digit exists.
 * Our algorithm is O(n) worst case, which is optimal.
 * 
 * ============================================================================
 */

package strings.easy

class LargestOddNumber {
    
    /**
     * Finds the largest odd number that is a substring of the given string
     * 
     * @param num String representing a number
     * @return Largest odd substring, or empty string if none exists
     */
    fun largestOddNumber(num: String): String {
        // Scan from right to left to find the last odd digit
        // This gives us the largest odd number
        for (i in num.length - 1 downTo 0) {
            // Check if current digit is odd
            // A digit is odd if it's 1, 3, 5, 7, or 9
            // We can check by converting to int and checking % 2
            if (num[i].digitToInt() % 2 == 1) {
                // Found the rightmost odd digit
                // Return substring from start to this position (inclusive)
                // This is the largest odd substring
                return num.substring(0, i + 1)
            }
        }
        
        // No odd digit found, return empty string
        return ""
    }
    
    /**
     * Alternative approach using lastIndexOf with predicate
     * More functional style
     */
    fun largestOddNumberFunctional(num: String): String {
        // Find index of last odd digit
        val lastOddIndex = num.indexOfLast { it.digitToInt() % 2 == 1 }
        
        // If found, return substring; otherwise empty string
        return if (lastOddIndex != -1) {
            num.substring(0, lastOddIndex + 1)
        } else {
            ""
        }
    }
    
    /**
     * Helper function to check if a character is an odd digit
     */
    private fun isOddDigit(c: Char): Boolean {
        return c in "13579"
    }
    
    /**
     * Alternative using character set check (slightly faster)
     */
    fun largestOddNumberCharCheck(num: String): String {
        for (i in num.length - 1 downTo 0) {
            if (isOddDigit(num[i])) {
                return num.substring(0, i + 1)
            }
        }
        return ""
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: num = "52"
 * 
 * STEP-BY-STEP EXECUTION:
 * 
 * Start scanning from right (index 1):
 * - Index 1: '2' → 2 % 2 = 0 → even, continue
 * - Index 0: '5' → 5 % 2 = 1 → odd, FOUND!
 * 
 * Return substring(0, 0+1) = "5"
 * 
 * OUTPUT: "5" ✓
 * 
 * WHY NOT "52"?
 * "52" is even (ends with 2), so it's not odd.
 * "5" is odd (ends with 5), and it's a valid substring.
 * 
 * ---
 * 
 * Example 2: num = "4206"
 * 
 * Scanning from right:
 * - Index 3: '6' → even
 * - Index 2: '0' → even
 * - Index 1: '2' → even
 * - Index 0: '4' → even
 * 
 * No odd digit found!
 * 
 * OUTPUT: "" ✓
 * 
 * ---
 * 
 * Example 3: num = "35427"
 * 
 * Scanning from right:
 * - Index 4: '7' → 7 % 2 = 1 → odd, FOUND!
 * 
 * Return substring(0, 4+1) = "35427"
 * 
 * OUTPUT: "35427" ✓
 * 
 * This is the entire string because it ends with odd digit 7.
 * 
 * ---
 * 
 * Example 4: num = "123456789"
 * 
 * Scanning from right:
 * - Index 8: '9' → odd, FOUND!
 * 
 * OUTPUT: "123456789" (entire string)
 * 
 * ---
 * 
 * Example 5: num = "246813579"
 * 
 * Scanning from right:
 * - Index 8: '9' → odd, FOUND!
 * 
 * OUTPUT: "246813579" (entire string)
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Single odd digit "5" → "5"
 * 2. Single even digit "4" → ""
 * 3. All even digits "2468" → ""
 * 4. All odd digits "13579" → "13579"
 * 5. Odd at end "1234567" → "1234567"
 * 6. Even at end "123456" → "12345"
 * 7. Alternating "2468135" → "2468135" (ends with 5)
 * 8. Large numbers handled (up to 10^5 digits)
 * 9. Leading zeros not present (constraint)
 * 10. Empty result when no odd digits
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Checking if entire number is odd
 * ❌ Converting entire string to integer (fails for large numbers)
 * ✅ Only check last digit
 * 
 * MISTAKE 2: Not finding the rightmost odd digit
 * ❌ Stopping at first odd digit from left
 * ✅ Scan from right to find last odd digit
 * 
 * MISTAKE 3: Off-by-one in substring
 * ❌ substring(0, i) - excludes the odd digit itself!
 * ✅ substring(0, i+1) - includes the odd digit
 * 
 * MISTAKE 4: Not handling "no odd digit" case
 * ❌ Returning null or throwing exception
 * ✅ Return empty string ""
 * 
 * MISTAKE 5: Comparing characters wrong
 * ❌ if (num[i] % 2 == 1) - char is not an int!
 * ✅ if (num[i].digitToInt() % 2 == 1) - convert first
 * 
 * ============================================================================
 * WHY THIS APPROACH WORKS
 * ============================================================================
 * 
 * MATHEMATICAL REASONING:
 * 
 * 1. A number is odd ⟺ its last digit is odd
 *    - This is because: number = prefix × 10 + lastDigit
 *    - 10 is even, so prefix × 10 is even
 *    - odd = even + odd requires lastDigit to be odd
 * 
 * 2. For largest odd substring:
 *    - Want maximum number of digits (larger magnitude)
 *    - Must end at an odd digit
 *    - Start from position 0 (include all leading digits)
 *    - End at rightmost odd digit (maximize length)
 * 
 * 3. Why not check other substrings?
 *    - Any substring not starting at 0 is smaller
 *    - Any substring not ending at rightmost odd is shorter
 *    - Therefore, [0, lastOddIndex] is guaranteed largest
 * 
 * ============================================================================
 * WHEN TO USE THIS APPROACH
 * ============================================================================
 * 
 * USE WHEN:
 * ✅ Need to find substring with specific property
 * ✅ Property depends only on last character/digit
 * ✅ Want largest/longest valid substring
 * ✅ Working with numeric strings
 * 
 * SIMILAR PROBLEMS:
 * - Largest Even Number in String
 * - Largest Prime Substring
 * - Largest Number After Digit Swaps
 * - Maximum Number After K Swaps
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Data Validation**: Finding valid odd identifiers
 * 2. **Financial Systems**: Extracting odd transaction codes
 * 3. **Inventory Management**: Odd SKU numbers
 * 4. **Gaming**: Score calculations with odd bonuses
 * 5. **Data Mining**: Extracting patterns from numeric strings
 * 6. **Cryptography**: Odd number requirements in algorithms
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = LargestOddNumber()
    
    println("=== Testing Largest Odd Number ===\n")
    
    // Test 1: Basic example with even end
    println("Test 1: Ends with even digit")
    val test1 = "52"
    println("Input: \"$test1\"")
    println("Output: \"${solution.largestOddNumber(test1)}\"")
    println("Expected: \"5\"\n")
    
    // Test 2: All even digits
    println("Test 2: All even digits")
    val test2 = "4206"
    println("Input: \"$test2\"")
    println("Output: \"${solution.largestOddNumber(test2)}\"")
    println("Expected: \"\" (empty)\n")
    
    // Test 3: Ends with odd digit
    println("Test 3: Ends with odd digit")
    val test3 = "35427"
    println("Input: \"$test3\"")
    println("Output: \"${solution.largestOddNumber(test3)}\"")
    println("Expected: \"35427\"\n")
    
    // Test 4: Single odd digit
    println("Test 4: Single odd digit")
    val test4 = "7"
    println("Input: \"$test4\"")
    println("Output: \"${solution.largestOddNumber(test4)}\"")
    println("Expected: \"7\"\n")
    
    // Test 5: Single even digit
    println("Test 5: Single even digit")
    val test5 = "2"
    println("Input: \"$test5\"")
    println("Output: \"${solution.largestOddNumber(test5)}\"")
    println("Expected: \"\" (empty)\n")
    
    // Test 6: All odd digits
    println("Test 6: All odd digits")
    val test6 = "13579"
    println("Input: \"$test6\"")
    println("Output: \"${solution.largestOddNumber(test6)}\"")
    println("Expected: \"13579\"\n")
    
    // Test 7: Large number ending with even
    println("Test 7: Large number ending with even")
    val test7 = "123456789012"
    println("Input: \"$test7\"")
    println("Output: \"${solution.largestOddNumber(test7)}\"")
    println("Expected: \"12345678901\"\n")
    
    // Test 8: Functional approach
    println("Test 8: Functional approach")
    val test8 = "52"
    println("Input: \"$test8\"")
    println("Functional output: \"${solution.largestOddNumberFunctional(test8)}\"")
    println("Expected: \"5\"\n")
    
    // Test 9: Character check approach
    println("Test 9: Character check approach")
    val test9 = "35427"
    println("Input: \"$test9\"")
    println("Char check output: \"${solution.largestOddNumberCharCheck(test9)}\"")
    println("Expected: \"35427\"\n")
    
    // Test 10: Complex pattern
    println("Test 10: Complex pattern")
    val test10 = "2468135792468"
    println("Input: \"$test10\"")
    println("Output: \"${solution.largestOddNumber(test10)}\"")
    println("Expected: \"246813579\" (up to last odd digit 9)\n")
}
