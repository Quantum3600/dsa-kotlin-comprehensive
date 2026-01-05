/**
 * ============================================================================
 * PROBLEM: Reverse Every Word in a String
 * DIFFICULTY: Medium
 * CATEGORY: Strings - Medium
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string s, reverse the order of characters in each word within a 
 * sentence while still preserving whitespace and initial word order.
 * 
 * A word is defined as a sequence of non-space characters.
 * 
 * INPUT FORMAT:
 * - A string s: s = "Let's take LeetCode contest"
 * 
 * OUTPUT FORMAT:
 * - String with each word reversed: "s'teL ekat edoCteeL tsetnoc"
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 5 * 10^4
 * - s contains printable ASCII characters
 * - s does not contain any leading or trailing spaces
 * - There is at least one word in s
 * - All words are separated by a single space
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We need to reverse characters within each word but keep words in the same
 * order. Think of it as:
 * - Split sentence into words
 * - Reverse each word individually
 * - Join them back with spaces
 * 
 * Example: "Let's take LeetCode contest"
 * - Words: ["Let's", "take", "LeetCode", "contest"]
 * - Reverse each: ["s'teL", "ekat", "edoCteeL", "tsetnoc"]
 * - Join: "s'teL ekat edoCteeL tsetnoc"
 * 
 * KEY INSIGHTS:
 * 1. Word order remains unchanged
 * 2. Only characters within each word are reversed
 * 3. Spaces act as word delimiters
 * 4. Each word is processed independently
 * 
 * ALGORITHM STEPS (String Split and Reverse):
 * 1. Split string by spaces into words
 * 2. For each word:
 *    a. Reverse its characters
 * 3. Join reversed words with spaces
 * 4. Return result
 * 
 * ALGORITHM STEPS (Two Pointer - In-place):
 * 1. Convert string to character array
 * 2. For each word:
 *    a. Find start and end of word
 *    b. Reverse characters using two pointers
 *    c. Move to next word
 * 3. Convert array back to string
 * 
 * VISUAL EXAMPLE:
 * s = "Let's take"
 * 
 * Split: ["Let's", "take"]
 * 
 * Reverse "Let's":
 *   L e t ' s
 *   ↓       ↓  swap
 *   s e t ' L
 *   ↓     ↓    swap
 *   s ' t e L
 *     ↓ ↓      swap (middle, done)
 *   s ' t e L
 *   Result: "s'teL"
 * 
 * Reverse "take":
 *   t a k e
 *   ↓     ↓  swap
 *   e a k t
 *     ↓ ↓    swap
 *   e k a t
 *   Result: "ekat"
 * 
 * Join: "s'teL ekat"
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Split and Reverse: O(n) time, O(n) space - SIMPLE & CLEAR
 * 2. Two Pointer: O(n) time, O(n) space - More manual control
 * 3. Stack-based: O(n) time, O(n) space - Overkill
 * 4. Regex: O(n) time, O(n) space - Advanced but less readable
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * SPLIT AND REVERSE APPROACH:
 * TIME COMPLEXITY: O(n)
 * - Split string: O(n) to scan through string
 * - Reverse each word: O(n) total (each character reversed once)
 * - Join words: O(n) to concatenate
 * - Total: O(n) where n is length of string
 * 
 * SPACE COMPLEXITY: O(n)
 * - Array of words: O(n)
 * - Reversed words: O(n)
 * - Result string: O(n) (required output)
 * - Total: O(n)
 * 
 * TWO POINTER APPROACH:
 * TIME COMPLEXITY: O(n)
 * - Single pass through string
 * - Each character processed once
 * 
 * SPACE COMPLEXITY: O(n)
 * - Character array: O(n)
 * - In Kotlin, strings are immutable, so we need array
 * 
 * WHY THIS IS OPTIMAL:
 * - Must process each character at least once: O(n) minimum
 * - Must create result string: O(n) space minimum
 * - Cannot improve on O(n) time and O(n) space
 * 
 * ============================================================================
 */

package strings.medium

class ReverseEveryWord {
    
    /**
     * Reverses each word in the string using split and reverse
     * 
     * @param s Input string
     * @return String with each word reversed
     */
    fun reverseWords(s: String): String {
        // Split string into words
        val words = s.split(" ")
        
        // Reverse each word and join
        return words.joinToString(" ") { word ->
            word.reversed()
        }
    }
    
    /**
     * Alternative: Using two-pointer approach
     * More manual but gives more control
     */
    fun reverseWordsTwoPointer(s: String): String {
        // Convert to character array for in-place modification
        val chars = s.toCharArray()
        
        var start = 0
        
        // Process each word
        for (end in chars.indices) {
            // Found end of word (space or end of string)
            if (end == chars.size - 1 || chars[end + 1] == ' ') {
                // Reverse characters from start to end
                reverseChars(chars, start, end)
                // Move start to beginning of next word
                start = end + 2
            }
        }
        
        return String(chars)
    }
    
    /**
     * Helper function to reverse characters in array between indices
     */
    private fun reverseChars(chars: CharArray, left: Int, right: Int) {
        var l = left
        var r = right
        
        // Swap characters from both ends moving towards center
        while (l < r) {
            val temp = chars[l]
            chars[l] = chars[r]
            chars[r] = temp
            l++
            r--
        }
    }
    
    /**
     * Alternative: Using StringBuilder for each word
     */
    fun reverseWordsStringBuilder(s: String): String {
        val result = StringBuilder()
        var word = StringBuilder()
        
        for (i in s.indices) {
            val char = s[i]
            
            if (char == ' ') {
                // End of word, reverse and append
                result.append(word.reverse())
                result.append(' ')
                word.clear()
            } else {
                word.append(char)
            }
        }
        
        // Don't forget last word
        result.append(word.reverse())
        
        return result.toString()
    }
    
    /**
     * Functional style using map
     */
    fun reverseWordsFunctional(s: String): String {
        return s.split(" ")
            .map { it.reversed() }
            .joinToString(" ")
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "Let's take LeetCode contest"
 * 
 * SPLIT AND REVERSE EXECUTION:
 * 
 * Step 1: Split by space
 * words = ["Let's", "take", "LeetCode", "contest"]
 * 
 * Step 2: Reverse each word
 * 
 * "Let's" → reverse → "s'teL"
 *   Indices: 0 1 2 3 4
 *   Chars:   L e t ' s
 *   Reverse: s ' t e L
 * 
 * "take" → reverse → "ekat"
 *   Indices: 0 1 2 3
 *   Chars:   t a k e
 *   Reverse: e k a t
 * 
 * "LeetCode" → reverse → "edoCteeL"
 *   Indices: 0 1 2 3 4 5 6 7
 *   Chars:   L e e t C o d e
 *   Reverse: e d o C t e e L
 * 
 * "contest" → reverse → "tsetnoc"
 *   Indices: 0 1 2 3 4 5 6
 *   Chars:   c o n t e s t
 *   Reverse: t s e t n o c
 * 
 * Step 3: Join with spaces
 * result = "s'teL ekat edoCteeL tsetnoc"
 * 
 * OUTPUT: "s'teL ekat edoCteeL tsetnoc"
 * 
 * ---
 * 
 * TWO POINTER TRACE for "Hi there":
 * 
 * Initial: chars = ['H', 'i', ' ', 't', 'h', 'e', 'r', 'e']
 *          start = 0
 * 
 * Iteration 1 (end=0):
 *   Not end of word, continue
 * 
 * Iteration 2 (end=1):
 *   chars[2] = ' ' → end of word "Hi"
 *   Reverse chars[0..1]: "Hi" → "iH"
 *   chars = ['i', 'H', ' ', 't', 'h', 'e', 'r', 'e']
 *   start = 1 + 2 = 3
 * 
 * Iterations 3-7:
 *   Process "there" at indices 3-7
 *   Reverse chars[3..7]: "there" → "ereht"
 *   chars = ['i', 'H', ' ', 'e', 'r', 'e', 'h', 't']
 * 
 * OUTPUT: "iH ereht"
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Single word "hello" → "olleh"
 * 2. Single character word "a" → "a"
 * 3. Two words "ab cd" → "ba dc"
 * 4. Special characters "hello!" → "!olleh"
 * 5. Mixed case "HeLLo" → "oLLeH"
 * 6. Numbers "abc123" → "321cba"
 * 7. Multiple spaces handled per constraints (not present in input)
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Reversing entire string
 * ❌ s.reversed() → "tsetnoc edoCteeL ekat s'teL"
 * ✅ Reverse each word individually
 * 
 * MISTAKE 2: Changing word order
 * ❌ Reversing word order instead of characters
 * ✅ Keep word order, reverse characters within each word
 * 
 * MISTAKE 3: Not handling last word
 * ❌ Missing last word when iterating by spaces
 * ✅ Handle last word after loop or check end condition
 * 
 * MISTAKE 4: Wrong split handling
 * ❌ split() without delimiter can handle extra spaces differently
 * ✅ Use split(" ") for single space (as per constraints)
 * 
 * MISTAKE 5: Index out of bounds
 * ❌ Not checking bounds when looking for next space
 * ✅ Check end == chars.size - 1 or chars[end + 1]
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Text Obfuscation**: Simple text scrambling for privacy
 * 2. **Word Games**: Puzzles and brain teasers
 * 3. **Data Masking**: Protecting sensitive information
 * 4. **Encoding**: Simple transformation for data transmission
 * 5. **Text Analysis**: Studying word structure patterns
 * 6. **Educational Tools**: Teaching string manipulation
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * - Reverse String (entire string)
 * - Reverse Words in a String (word order)
 * - Reverse String II (every 2k characters)
 * - Reverse Vowels of a String
 * - Reverse Only Letters
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = ReverseEveryWord()
    
    println("=== Testing Reverse Every Word ===\n")
    
    // Test 1: Multiple words
    println("Test 1: Multiple words")
    println("Input: s = \"Let's take LeetCode contest\"")
    println("Output: ${solution.reverseWords("Let's take LeetCode contest")}")
    println("Expected: s'teL ekat edoCteeL tsetnoc\n")
    
    // Test 2: Short sentence
    println("Test 2: Short sentence")
    println("Input: s = \"God Ding\"")
    println("Output: ${solution.reverseWords("God Ding")}")
    println("Expected: doG gniD\n")
    
    // Test 3: Single word
    println("Test 3: Single word")
    println("Input: s = \"hello\"")
    println("Output: ${solution.reverseWords("hello")}")
    println("Expected: olleh\n")
    
    // Test 4: Single character
    println("Test 4: Single character")
    println("Input: s = \"a\"")
    println("Output: ${solution.reverseWords("a")}")
    println("Expected: a\n")
    
    // Test 5: Special characters
    println("Test 5: Special characters")
    println("Input: s = \"hello! world@\"")
    println("Output: ${solution.reverseWords("hello! world@")}")
    println("Expected: !olleh @dlrow\n")
    
    // Test 6: Two pointer approach
    println("Test 6: Using two-pointer approach")
    println("Input: s = \"Let's take LeetCode contest\"")
    println("Output: ${solution.reverseWordsTwoPointer("Let's take LeetCode contest")}")
    println("Expected: s'teL ekat edoCteeL tsetnoc\n")
    
    // Test 7: StringBuilder approach
    println("Test 7: Using StringBuilder approach")
    println("Input: s = \"God Ding\"")
    println("Output: ${solution.reverseWordsStringBuilder("God Ding")}")
    println("Expected: doG gniD\n")
    
    // Test 8: Functional style
    println("Test 8: Using functional style")
    println("Input: s = \"hello world\"")
    println("Output: ${solution.reverseWordsFunctional("hello world")}")
    println("Expected: olleh dlrow\n")
    
    // Test 9: Numbers and letters
    println("Test 9: Mixed content")
    println("Input: s = \"abc123 def456\"")
    println("Output: ${solution.reverseWords("abc123 def456")}")
    println("Expected: 321cba 654fed\n")
}
