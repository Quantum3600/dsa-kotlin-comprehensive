/**
 * ============================================================================
 * PROBLEM: Check if Two Strings are Anagrams
 * DIFFICULTY: Easy
 * CATEGORY: Strings - Easy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two strings s and t, return true if t is an anagram of s, and false 
 * otherwise.
 * 
 * An anagram is a word or phrase formed by rearranging the letters of a 
 * different word or phrase, using all the original letters exactly once.
 * 
 * INPUT FORMAT:
 * - Two strings: s = "anagram", t = "nagaram"
 * 
 * OUTPUT FORMAT:
 * - Boolean: true if t is anagram of s, false otherwise
 * 
 * CONSTRAINTS:
 * - 1 <= s.length, t.length <= 5 * 10^4
 * - s and t consist of lowercase English letters
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Anagrams have the exact same characters, just in different order.
 * Think of it like having a bag of Scrabble tiles:
 * - Both words must use the same tiles
 * - Same number of each letter
 * - Just arranged differently
 * 
 * Example: "listen" and "silent"
 * Both have: 1 'l', 1 'i', 1 's', 1 't', 1 'e', 1 'n'
 * 
 * KEY INSIGHTS:
 * 1. Anagrams must have the same length
 * 2. Each character must appear the same number of times
 * 3. Order doesn't matter, only frequency
 * 
 * MULTIPLE APPROACHES:
 * 
 * APPROACH 1: Sorting
 * - Sort both strings
 * - If sorted strings are equal, they're anagrams
 * - Time: O(n log n), Space: O(n)
 * 
 * APPROACH 2: Character Count (Hash Map)
 * - Count frequency of each character in both strings
 * - Compare the frequency maps
 * - Time: O(n), Space: O(1) for fixed alphabet
 * 
 * APPROACH 3: Character Count (Array)
 * - Use array of size 26 for lowercase letters
 * - Increment for s, decrement for t
 * - If all zeros, they're anagrams
 * - Time: O(n), Space: O(1)
 * 
 * ALGORITHM STEPS (Array Approach - OPTIMAL):
 * 1. Check if lengths are equal (if not, return false)
 * 2. Create frequency array of size 26
 * 3. For each character in s, increment its count
 * 4. For each character in t, decrement its count
 * 5. If all counts are 0, strings are anagrams
 * 
 * VISUAL EXAMPLE:
 * s = "anagram", t = "nagaram"
 * 
 * Frequency array (showing non-zero values):
 * After processing s:
 * a: +3, n: +1, g: +1, r: +1, m: +1
 * 
 * After processing t:
 * a: +3-3=0, n: +1-1=0, g: +1-1=0, r: +1-1=0, m: +1-1=0
 * 
 * All zeros → Anagram! ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * APPROACH 1 (SORTING):
 * TIME COMPLEXITY: O(n log n)
 * - Sorting both strings: O(n log n)
 * - Comparison: O(n)
 * - Total: O(n log n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Sorted strings: O(n)
 * - In Kotlin, strings are immutable, so sorting creates new strings
 * 
 * APPROACH 2 (HASH MAP):
 * TIME COMPLEXITY: O(n)
 * - Building frequency map for s: O(n)
 * - Checking against t: O(n)
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(k)
 * - k is number of unique characters
 * - For lowercase English: O(1) since k ≤ 26
 * 
 * APPROACH 3 (ARRAY - OPTIMAL):
 * TIME COMPLEXITY: O(n)
 * - Single pass through both strings: O(n)
 * - Checking array: O(26) = O(1)
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Fixed array of size 26
 * - Constant space regardless of input size
 * 
 * WHY ARRAY APPROACH IS OPTIMAL:
 * - O(n) time (linear, can't do better)
 * - O(1) space (constant for fixed alphabet)
 * - Simple and efficient
 * 
 * ============================================================================
 */

package strings.easy

class CheckAnagram {
    
    /**
     * Checks if two strings are anagrams using character frequency array
     * OPTIMAL approach for lowercase English letters
     * 
     * @param s First string
     * @param t Second string
     * @return true if strings are anagrams, false otherwise
     */
    fun isAnagram(s: String, t: String): Boolean {
        // Different lengths cannot be anagrams
        if (s.length != t.length) return false
        
        // Frequency array for 26 lowercase letters
        // Index 0 = 'a', Index 1 = 'b', ..., Index 25 = 'z'
        val count = IntArray(26)
        
        // Count characters: increment for s, decrement for t
        for (i in s.indices) {
            // Increment count for character in s
            count[s[i] - 'a']++
            
            // Decrement count for character in t
            count[t[i] - 'a']--
        }
        
        // Check if all counts are zero
        // If any count is non-zero, strings are not anagrams
        for (c in count) {
            if (c != 0) return false
        }
        
        // All counts are zero, strings are anagrams
        return true
    }
    
    /**
     * Alternative approach using sorting
     * Simpler but less efficient
     */
    fun isAnagramSorting(s: String, t: String): Boolean {
        // Different lengths cannot be anagrams
        if (s.length != t.length) return false
        
        // Sort both strings and compare
        // If sorted strings are equal, original strings are anagrams
        return s.toCharArray().sorted() == t.toCharArray().sorted()
    }
    
    /**
     * Alternative approach using hash map
     * More flexible (works with any character set)
     */
    fun isAnagramHashMap(s: String, t: String): Boolean {
        if (s.length != t.length) return false
        
        // Build frequency map for s
        val charCount = mutableMapOf<Char, Int>()
        for (char in s) {
            charCount[char] = charCount.getOrDefault(char, 0) + 1
        }
        
        // Decrement counts for t
        for (char in t) {
            val count = charCount.getOrDefault(char, 0) - 1
            if (count < 0) {
                // Character in t not in s, or too many occurrences
                return false
            }
            charCount[char] = count
        }
        
        // Check if all counts are zero
        return charCount.values.all { it == 0 }
    }
    
    /**
     * One-pass hash map approach (alternative style)
     */
    fun isAnagramHashMapOneline(s: String, t: String): Boolean {
        if (s.length != t.length) return false
        
        // Group characters by frequency for both strings and compare
        return s.groupingBy { it }.eachCount() == t.groupingBy { it }.eachCount()
    }
    
    /**
     * Helper function to get character frequency map
     * Useful for debugging and understanding
     */
    fun getCharFrequency(str: String): Map<Char, Int> {
        val freq = mutableMapOf<Char, Int>()
        for (char in str) {
            freq[char] = freq.getOrDefault(char, 0) + 1
        }
        return freq
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "anagram", t = "nagaram"
 * 
 * ARRAY APPROACH EXECUTION:
 * 
 * Initial state:
 * - count = [0, 0, 0, ..., 0] (26 zeros)
 * 
 * Processing characters (i = 0 to 6):
 * 
 * i=0: s[0]='a', t[0]='n'
 * - count['a' - 'a'] = count[0]++ → count[0] = 1
 * - count['n' - 'a'] = count[13]-- → count[13] = -1
 * 
 * i=1: s[1]='n', t[1]='a'
 * - count[13]++ → count[13] = 0
 * - count[0]-- → count[0] = 0
 * 
 * i=2: s[2]='a', t[2]='g'
 * - count[0]++ → count[0] = 1
 * - count[6]-- → count[6] = -1
 * 
 * i=3: s[3]='g', t[3]='a'
 * - count[6]++ → count[6] = 0
 * - count[0]-- → count[0] = 0
 * 
 * i=4: s[4]='r', t[4]='r'
 * - count[17]++ → count[17] = 1
 * - count[17]-- → count[17] = 0
 * 
 * i=5: s[5]='a', t[5]='a'
 * - count[0]++ → count[0] = 1
 * - count[0]-- → count[0] = 0
 * 
 * i=6: s[6]='m', t[6]='m'
 * - count[12]++ → count[12] = 1
 * - count[12]-- → count[12] = 0
 * 
 * Final count = [0, 0, ..., 0] (all zeros)
 * 
 * OUTPUT: true ✓
 * 
 * ---
 * 
 * Example 2: s = "rat", t = "car"
 * 
 * Same length ✓
 * 
 * Processing:
 * After s: r=1, a=1, t=1
 * After t: r=1-0=1, a=1-1=0, t=1-0=1, c=0-1=-1
 * 
 * Final count has non-zero values!
 * - count['r'] = 1 (extra 'r' in s)
 * - count['t'] = 1 (extra 't' in s)
 * - count['c'] = -1 (extra 'c' in t)
 * 
 * OUTPUT: false ✗
 * 
 * ---
 * 
 * Example 3 (SORTING): s = "listen", t = "silent"
 * 
 * Sort s: "listen" → "eilnst"
 * Sort t: "silent" → "eilnst"
 * 
 * "eilnst" == "eilnst" → true ✓
 * 
 * OUTPUT: true ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty strings "", "" → true (vacuous truth)
 * 2. Single character "a", "a" → true
 * 3. Single different char "a", "b" → false
 * 4. Different lengths "abc", "abcd" → false
 * 5. Same string "test", "test" → true
 * 6. All same character "aaaa", "aaaa" → true
 * 7. One character repeated "ab", "ba" → true
 * 8. No common characters "abc", "xyz" → false
 * 9. Subset "ab", "aab" → false (different lengths)
 * 10. Long strings (up to 50,000 chars) handled efficiently
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not checking length first
 * ❌ Processing strings without length check
 * ✅ Return false immediately if lengths differ
 * 
 * MISTAKE 2: Using only one direction count
 * ❌ Only counting characters in s
 * ✅ Count in s (increment) and t (decrement) together
 * 
 * MISTAKE 3: Wrong array index calculation
 * ❌ count[char] instead of count[char - 'a']
 * ✅ Subtract 'a' to get 0-25 index
 * 
 * MISTAKE 4: Assuming case-insensitive
 * ❌ Treating 'A' and 'a' as same
 * ✅ Problem specifies lowercase only (or handle cases explicitly)
 * 
 * MISTAKE 5: Forgetting to check all counts
 * ❌ Stopping at first zero
 * ✅ Check all 26 positions (or use any() with condition)
 * 
 * ============================================================================
 * WHEN TO USE EACH APPROACH
 * ============================================================================
 * 
 * ARRAY APPROACH (RECOMMENDED):
 * ✅ Optimal for lowercase English letters
 * ✅ O(n) time, O(1) space
 * ✅ Fastest in practice
 * 
 * SORTING APPROACH:
 * ✅ Simplest to understand and implement
 * ✅ Works with any character set
 * ❌ O(n log n) time
 * ✅ Good for interviews (easy to explain)
 * 
 * HASH MAP APPROACH:
 * ✅ Flexible (any character set, Unicode, etc.)
 * ✅ O(n) time
 * ❌ Slightly more space overhead than array
 * ✅ Use when character set is large or unknown
 * 
 * ============================================================================
 * VARIATIONS AND EXTENSIONS
 * ============================================================================
 * 
 * 1. **Case-insensitive anagrams**: Convert to lowercase first
 * 2. **Ignore spaces**: Filter out spaces before checking
 * 3. **Unicode support**: Use hash map instead of array
 * 4. **Group anagrams**: Use sorted string or frequency as key
 * 5. **Find all anagrams**: Check multiple strings against one
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Word Games**: Scrabble, Words with Friends
 * 2. **Spell Checkers**: Finding similar words
 * 3. **Search Engines**: Query expansion with anagrams
 * 4. **Data Deduplication**: Identifying similar text
 * 5. **Cryptography**: Analyzing letter frequency
 * 6. **Plagiarism Detection**: Finding rearranged text
 * 7. **Text Analysis**: Finding word patterns
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * - Group Anagrams (LeetCode 49)
 * - Find All Anagrams in a String (LeetCode 438)
 * - Valid Anagram (this problem)
 * - Permutation in String (LeetCode 567)
 * - Minimum Window Substring (LeetCode 76)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = CheckAnagram()
    
    println("=== Testing Anagram Check ===\n")
    
    // Test 1: Basic anagram
    println("Test 1: Basic anagram")
    println("s = \"anagram\", t = \"nagaram\"")
    println("Output: ${solution.isAnagram("anagram", "nagaram")}")
    println("Expected: true\n")
    
    // Test 2: Not an anagram
    println("Test 2: Not an anagram")
    println("s = \"rat\", t = \"car\"")
    println("Output: ${solution.isAnagram("rat", "car")}")
    println("Expected: false\n")
    
    // Test 3: Classic example
    println("Test 3: Listen and Silent")
    println("s = \"listen\", t = \"silent\"")
    println("Output: ${solution.isAnagram("listen", "silent")}")
    println("Expected: true\n")
    
    // Test 4: Different lengths
    println("Test 4: Different lengths")
    println("s = \"abc\", t = \"abcd\"")
    println("Output: ${solution.isAnagram("abc", "abcd")}")
    println("Expected: false\n")
    
    // Test 5: Same strings
    println("Test 5: Identical strings")
    println("s = \"test\", t = \"test\"")
    println("Output: ${solution.isAnagram("test", "test")}")
    println("Expected: true\n")
    
    // Test 6: Empty strings
    println("Test 6: Empty strings")
    println("s = \"\", t = \"\"")
    println("Output: ${solution.isAnagram("", "")}")
    println("Expected: true\n")
    
    // Test 7: Single character
    println("Test 7: Single character")
    println("s = \"a\", t = \"a\"")
    println("Output: ${solution.isAnagram("a", "a")}")
    println("Expected: true\n")
    
    // Test 8: Sorting approach
    println("Test 8: Using sorting approach")
    println("s = \"anagram\", t = \"nagaram\"")
    println("Sorting output: ${solution.isAnagramSorting("anagram", "nagaram")}")
    println("Expected: true\n")
    
    // Test 9: Hash map approach
    println("Test 9: Using hash map approach")
    println("s = \"listen\", t = \"silent\"")
    println("HashMap output: ${solution.isAnagramHashMap("listen", "silent")}")
    println("Expected: true\n")
    
    // Test 10: Show character frequencies
    println("Test 10: Character frequency comparison")
    val s = "anagram"
    val t = "nagaram"
    println("s = \"$s\"")
    println("Frequency: ${solution.getCharFrequency(s)}")
    println("t = \"$t\"")
    println("Frequency: ${solution.getCharFrequency(t)}")
    println("Are anagrams: ${solution.isAnagram(s, t)}\n")
}
