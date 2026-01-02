/**
 * ============================================================================
 * PROBLEM: Reverse Words in a String
 * DIFFICULTY: Easy
 * CATEGORY: Strings - Easy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an input string s, reverse the order of the words. A word is defined as
 * a sequence of non-space characters. The words in s will be separated by at 
 * least one space.
 * 
 * Return a string of the words in reverse order concatenated by a single space.
 * Note: s may contain leading or trailing spaces or multiple spaces between 
 * two words. The returned string should only have a single space separating 
 * the words. Do not include any extra spaces.
 * 
 * INPUT FORMAT:
 * - A string s: "the sky is blue"
 * 
 * OUTPUT FORMAT:
 * - String with words reversed: "blue is sky the"
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 10^4
 * - s contains English letters (upper and lower case), digits, and spaces ' '
 * - There is at least one word in s
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of words like books on a shelf. To reverse the order:
 * 1. Take each book (word) off the shelf from left to right
 * 2. Stack them up
 * 3. Put them back in reverse order
 * 
 * Example: "the sky is blue"
 * - Words: ["the", "sky", "is", "blue"]
 * - Reverse: ["blue", "is", "sky", "the"]
 * - Join: "blue is sky the"
 * 
 * KEY INSIGHTS:
 * 1. Split string by spaces to get words
 * 2. Filter out empty strings (caused by multiple spaces)
 * 3. Reverse the word list
 * 4. Join with single space
 * 
 * ALGORITHM STEPS:
 * 1. Trim leading/trailing spaces
 * 2. Split by spaces
 * 3. Filter non-empty words
 * 4. Reverse the list
 * 5. Join with single space
 * 
 * VISUAL EXAMPLE:
 * Input: "  hello   world  "
 * 
 * Step 1: Trim → "hello   world"
 * Step 2: Split → ["hello", "", "", "world"]
 * Step 3: Filter → ["hello", "world"]
 * Step 4: Reverse → ["world", "hello"]
 * Step 5: Join → "world hello"
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Split + Reverse: O(n) time, O(n) space - SIMPLE & READABLE
 * 2. Two pointers: O(n) time, O(n) space - More complex
 * 3. Stack-based: O(n) time, O(n) space - Extra overhead
 * 4. In-place (char array): O(n) time, O(n) space - Mutable approach
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Trim: O(n)
 * - Split: O(n) - scans entire string
 * - Filter: O(w) where w is number of words
 * - Reverse: O(w)
 * - Join: O(n) - creates new string
 * - Total: O(n) where n is length of string
 * 
 * SPACE COMPLEXITY: O(n)
 * - Word list stores all characters (without spaces): O(n)
 * - Result string: O(n)
 * - Total: O(n)
 * 
 * WHY THIS IS OPTIMAL:
 * Must process each character at least once, so O(n) is optimal.
 * 
 * ============================================================================
 */

package strings.easy

class ReverseWords {
    
    /**
     * Reverses the order of words in a string
     * 
     * @param s Input string with words
     * @return String with words in reverse order
     */
    fun reverseWords(s: String): String {
        // Split by whitespace, filter empty strings, reverse, and join
        // trim() removes leading/trailing spaces
        // split("\\s+") splits by one or more whitespace characters
        // filter { it.isNotEmpty() } removes empty strings from multiple spaces
        // reversed() reverses the list order
        // joinToString(" ") joins with single space
        return s.trim()
            .split("\\s+".toRegex())
            .filter { it.isNotEmpty() }
            .reversed()
            .joinToString(" ")
    }
    
    /**
     * Alternative approach using manual parsing
     * More control over the process, same complexity
     */
    fun reverseWordsManual(s: String): String {
        val words = mutableListOf<String>()
        var word = StringBuilder()
        
        // Parse each character
        for (char in s) {
            if (char == ' ') {
                // End of word (if word is not empty)
                if (word.isNotEmpty()) {
                    words.add(word.toString())
                    word = StringBuilder()
                }
            } else {
                // Part of current word
                word.append(char)
            }
        }
        
        // Don't forget last word
        if (word.isNotEmpty()) {
            words.add(word.toString())
        }
        
        // Reverse and join
        return words.reversed().joinToString(" ")
    }
    
    /**
     * Two-pointer approach (more complex but educational)
     * Shows alternative thinking about the problem
     */
    fun reverseWordsTwoPointers(s: String): String {
        // Remove leading/trailing spaces and normalize internal spaces
        val normalized = s.trim().split("\\s+".toRegex()).joinToString(" ")
        
        // Convert to char array for in-place operations (conceptual)
        val chars = normalized.toCharArray()
        
        // Step 1: Reverse entire string
        reverse(chars, 0, chars.size - 1)
        
        // Step 2: Reverse each word
        var start = 0
        for (i in chars.indices) {
            if (chars[i] == ' ') {
                reverse(chars, start, i - 1)
                start = i + 1
            }
        }
        // Reverse last word
        reverse(chars, start, chars.size - 1)
        
        return String(chars)
    }
    
    /**
     * Helper function to reverse a portion of char array
     */
    private fun reverse(chars: CharArray, left: Int, right: Int) {
        var l = left
        var r = right
        while (l < r) {
            val temp = chars[l]
            chars[l] = chars[r]
            chars[r] = temp
            l++
            r--
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "the sky is blue"
 * 
 * STEP-BY-STEP EXECUTION:
 * 
 * 1. Trim: "the sky is blue" (no leading/trailing spaces)
 * 
 * 2. Split by spaces: ["the", "sky", "is", "blue"]
 * 
 * 3. Filter non-empty: ["the", "sky", "is", "blue"] (all non-empty)
 * 
 * 4. Reverse list: ["blue", "is", "sky", "the"]
 * 
 * 5. Join with space: "blue is sky the"
 * 
 * OUTPUT: "blue is sky the" ✓
 * 
 * ---
 * 
 * Example 2: s = "  hello world  "
 * 
 * 1. Trim: "hello world"
 * 2. Split: ["hello", "world"]
 * 3. Filter: ["hello", "world"]
 * 4. Reverse: ["world", "hello"]
 * 5. Join: "world hello"
 * 
 * OUTPUT: "world hello" ✓
 * 
 * ---
 * 
 * Example 3: s = "a good   example"
 * 
 * 1. Trim: "a good   example"
 * 2. Split by \\s+: ["a", "good", "example"]
 * 3. Filter: ["a", "good", "example"]
 * 4. Reverse: ["example", "good", "a"]
 * 5. Join: "example good a"
 * 
 * OUTPUT: "example good a" ✓
 * 
 * ============================================================================
 * TWO-POINTER APPROACH WALKTHROUGH
 * ============================================================================
 * 
 * Input: "the sky is blue" → normalized: "the sky is blue"
 * 
 * Step 1: Reverse entire string
 * "the sky is blue" → "eulb si yks eht"
 * 
 * Step 2: Reverse each word individually
 * "eulb" → "blue"
 * "si" → "is"
 * "yks" → "sky"
 * "eht" → "the"
 * 
 * Result: "blue is sky the" ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Single word "hello" → "hello"
 * 2. Two words "hello world" → "world hello"
 * 3. Leading spaces "  hello" → "hello"
 * 4. Trailing spaces "hello  " → "hello"
 * 5. Multiple spaces "hello    world" → "world hello"
 * 6. Mixed spaces "  hello   world  " → "world hello"
 * 7. Single character "a" → "a"
 * 8. All spaces handled correctly
 * 9. Special characters "hello-world test" → "test hello-world"
 * 10. Numbers "123 456" → "456 123"
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not handling multiple spaces
 * ❌ split(" ") - creates empty strings for multiple spaces
 * ✅ split("\\s+") - matches one or more spaces
 * 
 * MISTAKE 2: Forgetting to trim
 * ❌ Not removing leading/trailing spaces
 * ✅ Use trim() before processing
 * 
 * MISTAKE 3: Not filtering empty strings
 * ❌ Including empty strings in word list
 * ✅ filter { it.isNotEmpty() }
 * 
 * MISTAKE 4: String concatenation in loop
 * ❌ result += word + " " // O(n²) due to string immutability
 * ✅ Use joinToString() or StringBuilder
 * 
 * MISTAKE 5: Extra spaces in result
 * - Don't add space after last word
 * - Use joinToString(" ") which handles this correctly
 * 
 * ============================================================================
 * WHEN TO USE THIS APPROACH
 * ============================================================================
 * 
 * USE WHEN:
 * ✅ Need to reverse word order
 * ✅ Handling whitespace normalization
 * ✅ String tokenization required
 * ✅ Simplicity preferred over optimization
 * 
 * SIMILAR PROBLEMS:
 * - Reverse String
 * - Reverse String II
 * - Rotate String
 * - Valid Palindrome
 * - Reverse Vowels of a String
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Text Processing**: RTL (Right-to-Left) language conversion
 * 2. **Search Engines**: Query reformulation
 * 3. **NLP**: Sentence structure analysis
 * 4. **Data Cleaning**: Normalizing user input
 * 5. **Localization**: Adapting text for different reading directions
 * 6. **Code Formatting**: Reversing declaration order
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = ReverseWords()
    
    println("=== Testing Reverse Words ===\n")
    
    // Test 1: Basic example
    println("Test 1: Basic example")
    val test1 = "the sky is blue"
    println("Input: \"$test1\"")
    println("Output: \"${solution.reverseWords(test1)}\"")
    println("Expected: \"blue is sky the\"\n")
    
    // Test 2: Leading and trailing spaces
    println("Test 2: Leading and trailing spaces")
    val test2 = "  hello world  "
    println("Input: \"$test2\"")
    println("Output: \"${solution.reverseWords(test2)}\"")
    println("Expected: \"world hello\"\n")
    
    // Test 3: Multiple spaces between words
    println("Test 3: Multiple spaces between words")
    val test3 = "a good   example"
    println("Input: \"$test3\"")
    println("Output: \"${solution.reverseWords(test3)}\"")
    println("Expected: \"example good a\"\n")
    
    // Test 4: Single word
    println("Test 4: Single word")
    val test4 = "hello"
    println("Input: \"$test4\"")
    println("Output: \"${solution.reverseWords(test4)}\"")
    println("Expected: \"hello\"\n")
    
    // Test 5: Two words
    println("Test 5: Two words")
    val test5 = "hello world"
    println("Input: \"$test5\"")
    println("Output: \"${solution.reverseWords(test5)}\"")
    println("Expected: \"world hello\"\n")
    
    // Test 6: All spaces everywhere
    println("Test 6: Maximum spaces")
    val test6 = "   a   b   c   "
    println("Input: \"$test6\"")
    println("Output: \"${solution.reverseWords(test6)}\"")
    println("Expected: \"c b a\"\n")
    
    // Test 7: Manual approach comparison
    println("Test 7: Manual approach")
    val test7 = "the sky is blue"
    println("Input: \"$test7\"")
    println("Manual output: \"${solution.reverseWordsManual(test7)}\"")
    println("Expected: \"blue is sky the\"\n")
    
    // Test 8: Two-pointer approach
    println("Test 8: Two-pointer approach")
    val test8 = "the sky is blue"
    println("Input: \"$test8\"")
    println("Two-pointer output: \"${solution.reverseWordsTwoPointers(test8)}\"")
    println("Expected: \"blue is sky the\"\n")
    
    // Test 9: Long sentence
    println("Test 9: Long sentence")
    val test9 = "  This  is  a  test  string  "
    println("Input: \"$test9\"")
    println("Output: \"${solution.reverseWords(test9)}\"")
    println("Expected: \"string test a is This\"\n")
    
    // Test 10: Single character
    println("Test 10: Single character")
    val test10 = "a"
    println("Input: \"$test10\"")
    println("Output: \"${solution.reverseWords(test10)}\"")
    println("Expected: \"a\"\n")
}
