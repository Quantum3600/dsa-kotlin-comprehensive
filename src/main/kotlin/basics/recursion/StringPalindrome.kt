/**
 * ============================================================================
 * PROBLEM: Check if String is Palindrome using Recursion
 * DIFFICULTY: Easy
 * CATEGORY: Recursion/Strings
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string, determine if it is a palindrome using recursion. A palindrome
 * is a string that reads the same backward as forward.
 * 
 * INPUT FORMAT:
 * - A string
 * - Example: "racecar"
 * 
 * OUTPUT FORMAT:
 * - true if palindrome, false otherwise
 * - Example: true
 * 
 * CONSTRAINTS:
 * - 0 <= string.length <= 10^5
 * - String contains only lowercase letters (for simplicity)
 * - Empty string and single character are palindromes
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * A string is a palindrome if:
 * - First and last characters are same
 * - AND the substring between them is also a palindrome
 * 
 * RECURSIVE THINKING:
 * - Base case 1: Empty string or single char → palindrome
 * - Base case 2: Two chars → check if they're equal
 * - Recursive: Check first == last AND middle is palindrome
 * 
 * TWO-POINTER APPROACH:
 * - Compare characters at start and end indices
 * - If different → not palindrome
 * - If same → check substring (start+1 to end-1)
 * - When start >= end → palindrome
 * 
 * VISUAL EXAMPLES:
 * 
 * Example 1: "racecar"
 *             ↓     ↓
 *             r = r ✓ → Check "aceca"
 *              ↓   ↓
 *              a = a ✓ → Check "cec"
 *               ↓ ↓
 *               c = c ✓ → Check "e"
 *                ↓
 *             single char ✓
 * Result: PALINDROME ✓
 * 
 * Example 2: "hello"
 *             ↓   ↓
 *             h ≠ o ✗
 * Result: NOT PALINDROME ✗
 * 
 * ALGORITHM STEPS:
 * 1. Base case: if start >= end, return true
 * 2. If char at start != char at end, return false
 * 3. Recursively check substring (start+1, end-1)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Recursive two-pointer: O(n) time, O(n) space - IMPLEMENTED
 * 2. Iterative two-pointer: O(n) time, O(1) space - More efficient
 * 3. Reverse and compare: O(n) time, O(n) space - Simple but uses extra space
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - We make n/2 recursive calls (one for each comparison)
 * - Each call does O(1) work (character comparison)
 * - Total: O(n/2) = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion depth is n/2
 * - Each frame stores indices
 * - Total stack space: O(n/2) = O(n)
 * 
 * WHY n/2 CALLS:
 * - We compare from both ends moving toward middle
 * - Stop when indices meet or cross
 * - For string of length n, we make n/2 comparisons
 * 
 * ============================================================================
 */

package basics.recursion

class StringPalindrome {
    
    /**
     * Check if string is palindrome using recursion
     * TIME: O(n), SPACE: O(n) stack space
     */
    fun isPalindrome(str: String): Boolean {
        return isPalindromeHelper(str, 0, str.length - 1)
    }
    
    /**
     * Helper function with start and end pointers
     */
    private fun isPalindromeHelper(str: String, start: Int, end: Int): Boolean {
        // Base case: when pointers meet or cross, it's a palindrome
        if (start >= end) {
            return true
        }
        
        // Check if characters at start and end match
        if (str[start] != str[end]) {
            return false  // Not a palindrome
        }
        
        // Recursively check the substring between start and end
        return isPalindromeHelper(str, start + 1, end - 1)
    }
    
    /**
     * Check palindrome using single index
     * TIME: O(n), SPACE: O(n)
     */
    fun isPalindromeSingleIndex(str: String): Boolean {
        return isPalindromeSingleHelper(str, 0)
    }
    
    private fun isPalindromeSingleHelper(str: String, index: Int): Boolean {
        // Base case: checked half the string
        if (index >= str.length / 2) {
            return true
        }
        
        // Compare with mirror position
        val mirrorIndex = str.length - 1 - index
        if (str[index] != str[mirrorIndex]) {
            return false
        }
        
        // Recurse for next position
        return isPalindromeSingleHelper(str, index + 1)
    }
    
    /**
     * Check palindrome ignoring case and spaces
     * TIME: O(n), SPACE: O(n)
     */
    fun isPalindromeIgnoreCaseAndSpaces(str: String): Boolean {
        // Clean the string: remove spaces and convert to lowercase
        val cleaned = str.replace(" ", "").lowercase()
        return isPalindrome(cleaned)
    }
    
    /**
     * Check if substring is palindrome
     * TIME: O(n), SPACE: O(n)
     */
    fun isPalindromeSubstring(str: String, start: Int, end: Int): Boolean {
        return isPalindromeHelper(str, start, end)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: str = "racecar"
 * 
 * RECURSIVE CALLS:
 * 
 * isPalindromeHelper("racecar", 0, 6):
 * - start=0, end=6
 * - str[0]='r', str[6]='r', equal ✓
 * - Call isPalindromeHelper("racecar", 1, 5)
 * 
 * isPalindromeHelper("racecar", 1, 5):
 * - start=1, end=5
 * - str[1]='a', str[5]='a', equal ✓
 * - Call isPalindromeHelper("racecar", 2, 4)
 * 
 * isPalindromeHelper("racecar", 2, 4):
 * - start=2, end=4
 * - str[2]='c', str[4]='c', equal ✓
 * - Call isPalindromeHelper("racecar", 3, 3)
 * 
 * isPalindromeHelper("racecar", 3, 3):
 * - start=3, end=3, start >= end
 * - BASE CASE: return true
 * 
 * All return true → Result: true (PALINDROME) ✓
 * 
 * ---
 * 
 * INPUT: str = "hello"
 * 
 * isPalindromeHelper("hello", 0, 4):
 * - start=0, end=4
 * - str[0]='h', str[4]='o', NOT equal ✗
 * - Return false
 * 
 * Result: false (NOT PALINDROME) ✗
 * 
 * ============================================================================
 * CALL STACK VISUALIZATION
 * ============================================================================
 * 
 * For "racecar":
 * 
 * isPalindromeHelper("racecar", 0, 6)  [r == r ✓]
 *   └─ isPalindromeHelper("racecar", 1, 5)  [a == a ✓]
 *        └─ isPalindromeHelper("racecar", 2, 4)  [c == c ✓]
 *             └─ isPalindromeHelper("racecar", 3, 3)  [base case]
 *                  └─ return true
 * 
 * For "madam":
 * 
 * isPalindromeHelper("madam", 0, 4)  [m == m ✓]
 *   └─ isPalindromeHelper("madam", 1, 3)  [a == a ✓]
 *        └─ isPalindromeHelper("madam", 2, 2)  [base case]
 *             └─ return true
 * 
 * ============================================================================
 * PALINDROME EXAMPLES
 * ============================================================================
 * 
 * SINGLE CHARACTER: "a" → true
 * TWO CHARACTERS SAME: "aa" → true
 * TWO CHARACTERS DIFFERENT: "ab" → false
 * ODD LENGTH: "racecar" → true
 * EVEN LENGTH: "noon" → true
 * 
 * COMMON PALINDROMES:
 * - "racecar"
 * - "madam"
 * - "noon"
 * - "level"
 * - "kayak"
 * - "refer"
 * - "civic"
 * - "radar"
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = StringPalindrome()
    
    println("=== Check String Palindrome using Recursion ===\n")
    
    // Test 1: Odd length palindrome
    println("Test 1: 'racecar'")
    println("Result: ${solution.isPalindrome("racecar")}")
    println("Expected: true\n")
    
    // Test 2: Even length palindrome
    println("Test 2: 'noon'")
    println("Result: ${solution.isPalindrome("noon")}")
    println("Expected: true\n")
    
    // Test 3: Not a palindrome
    println("Test 3: 'hello'")
    println("Result: ${solution.isPalindrome("hello")}")
    println("Expected: false\n")
    
    // Test 4: Single character
    println("Test 4: 'a'")
    println("Result: ${solution.isPalindrome("a")}")
    println("Expected: true\n")
    
    // Test 5: Two same characters
    println("Test 5: 'aa'")
    println("Result: ${solution.isPalindrome("aa")}")
    println("Expected: true\n")
    
    // Test 6: Two different characters
    println("Test 6: 'ab'")
    println("Result: ${solution.isPalindrome("ab")}")
    println("Expected: false\n")
    
    // Test 7: Empty string
    println("Test 7: '' (empty)")
    println("Result: ${solution.isPalindrome("")}")
    println("Expected: true\n")
    
    // Test 8: More palindromes
    println("Test 8: Common palindromes")
    val palindromes = listOf("madam", "level", "kayak", "refer", "civic", "radar")
    for (word in palindromes) {
        println("$word: ${solution.isPalindrome(word)}")
    }
    println("All should be true\n")
    
    // Test 9: Non-palindromes
    println("Test 9: Non-palindromes")
    val nonPalindromes = listOf("world", "kotlin", "programming", "test")
    for (word in nonPalindromes) {
        println("$word: ${solution.isPalindrome(word)}")
    }
    println("All should be false\n")
    
    // Test 10: Single index approach
    println("Test 10: Single index approach")
    println("'racecar': ${solution.isPalindromeSingleIndex("racecar")}")
    println("'hello': ${solution.isPalindromeSingleIndex("hello")}")
    println("Expected: true, false\n")
    
    // Test 11: With spaces and case (after cleaning)
    println("Test 11: Ignore case and spaces")
    println("'A man a plan a canal Panama': ${solution.isPalindromeIgnoreCaseAndSpaces("A man a plan a canal Panama")}")
    println("Expected: true\n")
    
    // Test 12: Substring check
    println("Test 12: Check substring")
    val str = "abcdcba"
    println("String: '$str'")
    println("Full string: ${solution.isPalindrome(str)}")
    println("Substring [2,4] 'cdc': ${solution.isPalindromeSubstring(str, 2, 4)}")
    println("Expected: true, true")
}
