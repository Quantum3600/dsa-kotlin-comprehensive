/**
 * ============================================================================
 * PROBLEM: Isomorphic Strings
 * DIFFICULTY: Easy
 * CATEGORY: Strings - Easy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two strings s and t, determine if they are isomorphic.
 * 
 * Two strings s and t are isomorphic if the characters in s can be replaced 
 * to get t. All occurrences of a character must be replaced with another 
 * character while preserving the order of characters. No two characters may 
 * map to the same character, but a character may map to itself.
 * 
 * INPUT FORMAT:
 * - Two strings s and t: s = "egg", t = "add"
 * 
 * OUTPUT FORMAT:
 * - Boolean: true if isomorphic, false otherwise
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 5 * 10^4
 * - t.length == s.length
 * - s and t consist of any valid ASCII character
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of isomorphic strings like a cipher or code:
 * - Each character in s maps to exactly one character in t
 * - Each character in t is mapped from exactly one character in s
 * - The mapping must be consistent throughout
 * 
 * Example: "egg" → "add"
 * - 'e' maps to 'a'
 * - 'g' maps to 'd'
 * - 'g' maps to 'd' (consistent!)
 * 
 * This is like a substitution cipher where:
 * - e → a
 * - g → d
 * 
 * KEY INSIGHTS:
 * 1. Need bidirectional mapping (s→t AND t→s)
 * 2. Each character can only map to one other character
 * 3. Two different characters in s cannot map to same character in t
 * 4. Must check both directions to ensure one-to-one mapping
 * 
 * WHY BIDIRECTIONAL?
 * "ab" and "aa":
 * - s→t: a→a, b→a (seems ok)
 * - t→s: a→a, a→b (conflict! 'a' maps to both 'a' and 'b')
 * - Therefore NOT isomorphic
 * 
 * ALGORITHM STEPS:
 * 1. Create two hash maps: mapS2T (s to t) and mapT2S (t to s)
 * 2. For each position i:
 *    a. Check if s[i] already has a mapping in mapS2T
 *       - If yes, verify it maps to t[i]
 *       - If no, add mapping s[i] → t[i]
 *    b. Check if t[i] already has a mapping in mapT2S
 *       - If yes, verify it maps to s[i]
 *       - If no, add mapping t[i] → s[i]
 *    c. If any verification fails, return false
 * 3. If all characters processed successfully, return true
 * 
 * VISUAL EXAMPLE:
 * s = "egg", t = "add"
 * 
 * i=0: s[0]='e', t[0]='a'
 *   mapS2T: e→a ✓
 *   mapT2S: a→e ✓
 * 
 * i=1: s[1]='g', t[1]='d'
 *   mapS2T: e→a, g→d ✓
 *   mapT2S: a→e, d→g ✓
 * 
 * i=2: s[2]='g', t[2]='d'
 *   mapS2T: g already maps to d ✓
 *   mapT2S: d already maps to g ✓
 * 
 * Result: true (isomorphic)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Two hash maps: O(n) time, O(k) space - CLEAR & SAFE
 * 2. Single transformation: O(n) time, O(n) space - CLEVER
 * 3. Character arrays (for ASCII): O(n) time, O(1) space - FAST
 * 
 * where k = number of unique characters (at most 256 for ASCII)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through both strings
 * - n is the length of the strings
 * - Hash map operations (get, put) are O(1) average
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(k)
 * - Two hash maps storing at most k unique characters
 * - k is bounded by character set size (at most 256 for ASCII)
 * - In worst case: O(n) if all characters are unique
 * - Typical case: O(1) if considering fixed ASCII size
 * 
 * WHY THIS IS OPTIMAL:
 * Must examine each character at least once, so O(n) time is optimal.
 * 
 * ============================================================================
 */

package strings.easy

class IsomorphicString {
    
    /**
     * Checks if two strings are isomorphic using two hash maps
     * 
     * @param s First string
     * @param t Second string
     * @return true if strings are isomorphic, false otherwise
     */
    fun isIsomorphic(s: String, t: String): Boolean {
        // Strings must have same length (given in constraints)
        if (s.length != t.length) return false
        
        // Maps to track character mappings
        val mapS2T = mutableMapOf<Char, Char>()  // s to t mapping
        val mapT2S = mutableMapOf<Char, Char>()  // t to s mapping
        
        // Check each character position
        for (i in s.indices) {
            val charS = s[i]
            val charT = t[i]
            
            // Check s to t mapping
            if (mapS2T.containsKey(charS)) {
                // charS already has a mapping, verify it matches charT
                if (mapS2T[charS] != charT) {
                    return false  // Inconsistent mapping
                }
            } else {
                // Create new mapping from charS to charT
                mapS2T[charS] = charT
            }
            
            // Check t to s mapping (reverse direction)
            if (mapT2S.containsKey(charT)) {
                // charT already has a mapping, verify it matches charS
                if (mapT2S[charT] != charS) {
                    return false  // Inconsistent mapping
                }
            } else {
                // Create new mapping from charT to charS
                mapT2S[charT] = charS
            }
        }
        
        // All characters mapped consistently
        return true
    }
    
    /**
     * Alternative approach using transformation pattern
     * Convert both strings to same pattern and compare
     */
    fun isIsomorphicPattern(s: String, t: String): Boolean {
        // Transform both strings to their pattern representation
        // Pattern represents order of first occurrence
        return getPattern(s) == getPattern(t)
    }
    
    /**
     * Helper: Convert string to pattern based on first occurrence
     * Example: "egg" → "011", "add" → "011" (same pattern)
     */
    private fun getPattern(str: String): String {
        val map = mutableMapOf<Char, Int>()
        val pattern = StringBuilder()
        var counter = 0
        
        for (char in str) {
            if (!map.containsKey(char)) {
                map[char] = counter++
            }
            pattern.append(map[char])
        }
        
        return pattern.toString()
    }
    
    /**
     * Optimized approach using character arrays for ASCII
     * Faster for ASCII strings due to array access vs hash map
     */
    fun isIsomorphicArray(s: String, t: String): Boolean {
        // Arrays to store mappings (256 for extended ASCII)
        val mapS2T = IntArray(256) { -1 }
        val mapT2S = IntArray(256) { -1 }
        
        for (i in s.indices) {
            val charS = s[i].code  // Get ASCII value
            val charT = t[i].code
            
            // Check s to t mapping
            if (mapS2T[charS] == -1) {
                mapS2T[charS] = charT
            } else if (mapS2T[charS] != charT) {
                return false
            }
            
            // Check t to s mapping
            if (mapT2S[charT] == -1) {
                mapT2S[charT] = charS
            } else if (mapT2S[charT] != charS) {
                return false
            }
        }
        
        return true
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "egg", t = "add"
 * 
 * DETAILED EXECUTION:
 * 
 * Initial state:
 * - mapS2T = {}
 * - mapT2S = {}
 * 
 * i=0: s[0]='e', t[0]='a'
 * - 'e' not in mapS2T, add: e→a
 * - 'a' not in mapT2S, add: a→e
 * - mapS2T = {e→a}
 * - mapT2S = {a→e}
 * 
 * i=1: s[1]='g', t[1]='d'
 * - 'g' not in mapS2T, add: g→d
 * - 'd' not in mapT2S, add: d→g
 * - mapS2T = {e→a, g→d}
 * - mapT2S = {a→e, d→g}
 * 
 * i=2: s[2]='g', t[2]='d'
 * - 'g' in mapS2T, check: g→d matches 'd' ✓
 * - 'd' in mapT2S, check: d→g matches 'g' ✓
 * - No changes needed
 * 
 * All checks passed!
 * OUTPUT: true ✓
 * 
 * ---
 * 
 * Example 2: s = "foo", t = "bar"
 * 
 * i=0: f→b, b→f
 * i=1: o→a, a→o
 * i=2: o already maps to a, check t[2]='r'
 *      'a' ≠ 'r' → CONFLICT!
 * 
 * OUTPUT: false ✗
 * 
 * ---
 * 
 * Example 3: s = "paper", t = "title"
 * 
 * i=0: p→t, t→p
 * i=1: a→i, i→a
 * i=2: p maps to t, check t[2]='t' ✓
 * i=3: e→l, l→e
 * i=4: r→e, e→r
 * 
 * All consistent!
 * OUTPUT: true ✓
 * 
 * ---
 * 
 * Example 4: s = "ab", t = "aa" (Why bidirectional matters)
 * 
 * Using only s→t mapping:
 * i=0: a→a
 * i=1: b→a
 * Looks OK with single map!
 * 
 * But using t→s mapping:
 * i=0: a→a
 * i=1: a→b, but 'a' already maps to 'a'!
 * CONFLICT detected!
 * 
 * OUTPUT: false ✗
 * 
 * This shows why we need BOTH mappings!
 * 
 * ============================================================================
 * PATTERN APPROACH WALKTHROUGH
 * ============================================================================
 * 
 * s = "egg", t = "add"
 * 
 * Pattern of "egg":
 * - 'e' first occurrence → 0
 * - 'g' first occurrence → 1
 * - 'g' seen before → 1
 * - Pattern: "011"
 * 
 * Pattern of "add":
 * - 'a' first occurrence → 0
 * - 'd' first occurrence → 1
 * - 'd' seen before → 1
 * - Pattern: "011"
 * 
 * "011" == "011" → true ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Same strings "test" "test" → true
 * 2. Single character "a" "b" → true
 * 3. All same character "aaa" "bbb" → true
 * 4. No mapping possible "ab" "aa" → false
 * 5. Reverse not isomorphic "aa" "ab" → false
 * 6. Long strings with pattern → handled
 * 7. Special characters → handled (ASCII)
 * 8. Numbers as strings "123" "456" → true
 * 9. Mixed case "Aa" "Bb" → true
 * 10. Maximum length (50,000) → O(n) handles efficiently
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Using only one hash map
 * ❌ Only checking s→t mapping
 * ✅ Must check both s→t AND t→s mappings
 * 
 * Why? "ab" and "aa" would pass with only s→t but should fail!
 * 
 * MISTAKE 2: Not checking existing mappings
 * ❌ Overwriting existing mappings
 * ✅ Check if mapping exists and verify consistency
 * 
 * MISTAKE 3: Comparing strings directly
 * ❌ if (s == t) return true // Too simple!
 * ✅ Check character-by-character with mappings
 * 
 * MISTAKE 4: Assuming character order
 * - Isomorphic is about structure, not specific characters
 * - "abc" and "def" are isomorphic even with different chars
 * 
 * MISTAKE 5: Not handling all ASCII
 * - Must handle all valid ASCII characters, not just letters
 * - Use appropriate data structure (map or 256-size array)
 * 
 * ============================================================================
 * WHEN TO USE THIS APPROACH
 * ============================================================================
 * 
 * USE WHEN:
 * ✅ Need to check structural equivalence of strings
 * ✅ Working with character mapping/substitution
 * ✅ Validating one-to-one correspondence
 * ✅ Pattern matching problems
 * 
 * SIMILAR PROBLEMS:
 * - Word Pattern (words instead of characters)
 * - Find and Replace Pattern
 * - Group Shifted Strings
 * - Custom Sort String
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Cryptography**: Substitution cipher validation
 * 2. **Pattern Matching**: Finding similar text structures
 * 3. **Data Deduplication**: Identifying structurally similar data
 * 4. **Code Analysis**: Detecting similar code patterns
 * 5. **Text Classification**: Grouping by structure
 * 6. **DNA Sequencing**: Finding structural similarities
 * 7. **Language Processing**: Identifying grammatical patterns
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = IsomorphicString()
    
    println("=== Testing Isomorphic Strings ===\n")
    
    // Test 1: Basic isomorphic
    println("Test 1: Basic isomorphic strings")
    println("s = \"egg\", t = \"add\"")
    println("Output: ${solution.isIsomorphic("egg", "add")}")
    println("Expected: true\n")
    
    // Test 2: Not isomorphic
    println("Test 2: Not isomorphic")
    println("s = \"foo\", t = \"bar\"")
    println("Output: ${solution.isIsomorphic("foo", "bar")}")
    println("Expected: false\n")
    
    // Test 3: Isomorphic with different pattern
    println("Test 3: Complex pattern")
    println("s = \"paper\", t = \"title\"")
    println("Output: ${solution.isIsomorphic("paper", "title")}")
    println("Expected: true\n")
    
    // Test 4: Why bidirectional matters
    println("Test 4: Demonstrates need for bidirectional check")
    println("s = \"ab\", t = \"aa\"")
    println("Output: ${solution.isIsomorphic("ab", "aa")}")
    println("Expected: false (two chars in s map to same char in t)\n")
    
    // Test 5: Reverse case
    println("Test 5: Reverse of test 4")
    println("s = \"aa\", t = \"ab\"")
    println("Output: ${solution.isIsomorphic("aa", "ab")}")
    println("Expected: false\n")
    
    // Test 6: Single character
    println("Test 6: Single character")
    println("s = \"a\", t = \"b\"")
    println("Output: ${solution.isIsomorphic("a", "b")}")
    println("Expected: true\n")
    
    // Test 7: Same strings
    println("Test 7: Identical strings")
    println("s = \"test\", t = \"test\"")
    println("Output: ${solution.isIsomorphic("test", "test")}")
    println("Expected: true\n")
    
    // Test 8: Pattern approach
    println("Test 8: Pattern approach")
    println("s = \"egg\", t = \"add\"")
    println("Pattern output: ${solution.isIsomorphicPattern("egg", "add")}")
    println("Expected: true\n")
    
    // Test 9: Array approach
    println("Test 9: Array approach (ASCII optimization)")
    println("s = \"paper\", t = \"title\"")
    println("Array output: ${solution.isIsomorphicArray("paper", "title")}")
    println("Expected: true\n")
    
    // Test 10: Long pattern
    println("Test 10: Longer strings")
    println("s = \"abcdefghij\", t = \"1234567890\"")
    println("Output: ${solution.isIsomorphic("abcdefghij", "1234567890")}")
    println("Expected: true (each char maps to unique char)\n")
}
