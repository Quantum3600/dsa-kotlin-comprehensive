/**
 * ============================================================================
 * PROBLEM: Check if One String is Rotation of Another
 * DIFFICULTY: Easy
 * CATEGORY: Strings - Easy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two strings s1 and s2, return true if s2 is a rotation of s1, or 
 * false otherwise.
 * 
 * A string s2 is a rotation of s1 if it can be obtained by shifting some 
 * number of characters from the beginning of s1 to the end.
 * 
 * INPUT FORMAT:
 * - Two strings: s1 = "abcde", s2 = "cdeab"
 * 
 * OUTPUT FORMAT:
 * - Boolean: true if s2 is rotation of s1, false otherwise
 * 
 * CONSTRAINTS:
 * - 1 <= s1.length, s2.length <= 100
 * - s1 and s2 consist of lowercase English letters
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of the string as a circular chain:
 * 
 * Original: a → b → c → d → e → (back to a)
 * 
 * Rotations are just different starting points:
 * - Start at 'a': "abcde"
 * - Start at 'b': "bcdea"
 * - Start at 'c': "cdeab"
 * - Start at 'd': "deabc"
 * - Start at 'e': "eabcd"
 * 
 * KEY INSIGHT (Brilliant Trick!):
 * If we concatenate s1 with itself: s1 + s1 = "abcdeabcde"
 * 
 * ALL possible rotations of s1 are substrings of s1 + s1!
 * - "abcde" ✓ (original)
 * - "bcdea" ✓ (rotation by 1)
 * - "cdeab" ✓ (rotation by 2)
 * - "deabc" ✓ (rotation by 3)
 * - "eabcd" ✓ (rotation by 4)
 * 
 * So: s2 is rotation of s1 ⟺ s2 is substring of (s1 + s1)
 * 
 * VISUAL EXAMPLE:
 * s1 = "abcde"
 * s2 = "cdeab"
 * 
 * s1 + s1 = "abcdeabcde"
 *             ^^^^^ 
 *             "cdeab" found!
 * 
 * Therefore, "cdeab" is a rotation of "abcde" ✓
 * 
 * ALGORITHM STEPS:
 * 1. Check if lengths are equal (must be for rotation)
 * 2. Check if either string is empty (edge case)
 * 3. Concatenate s1 with itself
 * 4. Check if s2 is a substring of s1 + s1
 * 5. Return result
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Contains check: O(n) time, O(n) space - ELEGANT & OPTIMAL
 * 2. Rotation comparison: O(n²) time, O(1) space - Naive
 * 3. KMP algorithm: O(n) time, O(n) space - Optimal but complex
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Length check: O(1)
 * - Concatenation: O(n) to create string of length 2n
 * - Contains check: O(n) using efficient string matching (KMP internally)
 * - Total: O(n) where n is length of strings
 * 
 * Note: In practice, the contains() method may use different algorithms.
 * Kotlin's contains uses indexOf which is typically O(n*m) worst case,
 * but O(n) average case with optimizations.
 * 
 * SPACE COMPLEXITY: O(n)
 * - Concatenated string s1 + s1 takes O(n) space
 * - Other variables use O(1) space
 * - Total: O(n)
 * 
 * WHY THIS IS CLEVER:
 * Instead of checking all n possible rotations, we use string matching once!
 * This is much simpler and more elegant than manual rotation checking.
 * 
 * ============================================================================
 */

package strings.easy

class CheckRotation {
    
    /**
     * Checks if s2 is a rotation of s1 using concatenation trick
     * 
     * @param s1 Original string
     * @param s2 String to check if it's a rotation
     * @return true if s2 is rotation of s1, false otherwise
     */
    fun isRotation(s1: String, s2: String): Boolean {
        // Strings must have same length to be rotations
        if (s1.length != s2.length) return false
        
        // Empty strings are rotations of each other
        if (s1.isEmpty()) return true
        
        // The magic check: s2 must be substring of s1 + s1
        // This works because s1 + s1 contains all possible rotations
        return (s1 + s1).contains(s2)
    }
    
    /**
     * Alternative approach: Manual rotation check
     * More educational but less efficient
     */
    fun isRotationManual(s1: String, s2: String): Boolean {
        if (s1.length != s2.length) return false
        if (s1.isEmpty()) return true
        
        // Try each possible rotation
        for (i in s1.indices) {
            // Check if rotating by i positions gives s2
            var isRotation = true
            for (j in s1.indices) {
                // Character at position j in rotation should match s2[j]
                // In rotation by i, position j in s1 moves to position (j-i+n)%n
                if (s1[(i + j) % s1.length] != s2[j]) {
                    isRotation = false
                    break
                }
            }
            if (isRotation) return true
        }
        
        return false
    }
    
    /**
     * Alternative: Using string rotation
     * Actually performs the rotation to check
     */
    fun isRotationByRotating(s1: String, s2: String): Boolean {
        if (s1.length != s2.length) return false
        if (s1.isEmpty()) return true
        
        // Try rotating s1 by each position
        var rotated = s1
        for (i in 0 until s1.length) {
            if (rotated == s2) return true
            // Rotate: move first character to end
            rotated = rotated.substring(1) + rotated[0]
        }
        
        return false
    }
    
    /**
     * Helper function to demonstrate rotation
     * Returns all possible rotations of a string
     */
    fun getAllRotations(s: String): List<String> {
        val rotations = mutableListOf<String>()
        for (i in s.indices) {
            // Rotation by i positions
            rotations.add(s.substring(i) + s.substring(0, i))
        }
        return rotations
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s1 = "abcde", s2 = "cdeab"
 * 
 * USING CONCATENATION TRICK:
 * 
 * Step 1: Check lengths
 * - s1.length = 5
 * - s2.length = 5
 * - Equal ✓
 * 
 * Step 2: Check empty
 * - Neither empty ✓
 * 
 * Step 3: Concatenate s1 with itself
 * - s1 + s1 = "abcde" + "abcde" = "abcdeabcde"
 * 
 * Step 4: Check if s2 is substring
 * - Look for "cdeab" in "abcdeabcde"
 * - Found at index 2: "ab[cdeab]cde"
 * - Result: true ✓
 * 
 * OUTPUT: true ✓
 * 
 * WHY IT WORKS:
 * When we rotate "abcde" by 2 positions to the left:
 * - Take "ab" from front
 * - Move to back
 * - Get "cde" + "ab" = "cdeab"
 * 
 * This exact pattern appears in "abcdeabcde"!
 * 
 * ---
 * 
 * Example 2: s1 = "abcde", s2 = "abced"
 * 
 * Step 1: Lengths equal ✓
 * Step 2: Not empty ✓
 * Step 3: s1 + s1 = "abcdeabcde"
 * Step 4: Is "abced" in "abcdeabcde"?
 *         NO! "abced" is not a substring
 * 
 * OUTPUT: false ✗
 * 
 * "abced" is NOT a rotation because characters are rearranged,
 * not just shifted.
 * 
 * ---
 * 
 * Example 3: s1 = "waterbottle", s2 = "erbottlewat"
 * 
 * All rotations of "waterbottle":
 * 0: "waterbottle"
 * 1: "aterbottlew"
 * 2: "terbottlewa"
 * 3: "erbottlewat" ← This is s2!
 * ...
 * 
 * s1 + s1 = "waterbottlewaterbottle"
 *            ^^^^^^^^^^^
 *            "erbottlewat" found!
 * 
 * OUTPUT: true ✓
 * 
 * ============================================================================
 * MANUAL ROTATION WALKTHROUGH
 * ============================================================================
 * 
 * s1 = "abc", s2 = "cab"
 * 
 * Try rotation by 0: "abc" ≠ "cab"
 * Try rotation by 1: "bca" ≠ "cab"
 * Try rotation by 2: "cab" = "cab" ✓
 * 
 * Found! Return true.
 * 
 * How rotation by 2 works:
 * - Original: "abc"
 * - Take from index 2 to end: "c"
 * - Take from index 0 to 2: "ab"
 * - Concatenate: "c" + "ab" = "cab"
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty strings "", "" → true
 * 2. Single character "a", "a" → true
 * 3. Single different char "a", "b" → false
 * 4. Different lengths "abc", "abcd" → false
 * 5. Same strings "test", "test" → true (rotation by 0)
 * 6. Complete rotation "abc", "abc" → true
 * 7. Not a rotation "abc", "acb" → false (permutation, not rotation)
 * 8. Long strings handled efficiently
 * 9. All same characters "aaaa", "aaaa" → true
 * 10. Partial match "abc", "abd" → false
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not checking length first
 * ❌ Directly checking contains without length check
 * ✅ Check lengths are equal first
 * 
 * MISTAKE 2: Thinking permutation is rotation
 * ❌ "abc" and "acb" are permutations but NOT rotations
 * ✅ Rotation maintains relative order, just shifts starting point
 * 
 * MISTAKE 3: Forgetting empty string case
 * ❌ Not handling empty strings
 * ✅ Empty string is rotation of empty string
 * 
 * MISTAKE 4: Off-by-one in manual rotation
 * ❌ Wrong substring indices
 * ✅ Rotation by i: substring(i) + substring(0, i)
 * 
 * MISTAKE 5: Not understanding the concatenation trick
 * - s1 + s1 contains ALL possible rotations as substrings
 * - This is the key insight that makes the solution elegant
 * 
 * ============================================================================
 * WHY CONCATENATION TRICK WORKS
 * ============================================================================
 * 
 * MATHEMATICAL PROOF:
 * 
 * Let s1 = "abcde" (length n = 5)
 * Any rotation by k positions is: s1[k:] + s1[:k]
 * 
 * When we form s1 + s1:
 * s1 + s1 = "abcde" + "abcde" = "abcdeabcde"
 * 
 * For any k (0 ≤ k < n):
 * - Rotation by k = s1[k:n] + s1[0:k]
 * - This exact substring appears in s1+s1 starting at index k
 * 
 * Example rotations in "abcdeabcde":
 * - k=0: "abcde" (indices 0-4 or 5-9)
 * - k=1: "bcdea" (indices 1-5)
 * - k=2: "cdeab" (indices 2-6)
 * - k=3: "deabc" (indices 3-7)
 * - k=4: "eabcd" (indices 4-8)
 * 
 * Therefore: s2 is rotation of s1 ⟺ s2 ∈ substrings(s1 + s1)
 * 
 * ============================================================================
 * WHEN TO USE THIS APPROACH
 * ============================================================================
 * 
 * USE WHEN:
 * ✅ Checking if one string is rotation of another
 * ✅ Need simple and elegant solution
 * ✅ String lengths are reasonable (not too large)
 * ✅ Have efficient contains/substring method available
 * 
 * SIMILAR PROBLEMS:
 * - Rotate String
 * - Implement strStr() / String matching
 * - Repeated String Match
 * - Circular Array Loop
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Security**: Checking rotated passwords/codes
 * 2. **DNA Analysis**: Circular DNA sequence matching
 * 3. **Text Processing**: Handling cyclic patterns
 * 4. **Gaming**: Rotating game boards/patterns
 * 5. **Cryptography**: Shifted ciphers (Caesar cipher variants)
 * 6. **Data Structures**: Circular buffers validation
 * 7. **Music**: Detecting transposed melodies
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = CheckRotation()
    
    println("=== Testing String Rotation ===\n")
    
    // Test 1: Basic rotation
    println("Test 1: Basic rotation")
    println("s1 = \"abcde\", s2 = \"cdeab\"")
    println("Output: ${solution.isRotation("abcde", "cdeab")}")
    println("Expected: true\n")
    
    // Test 2: Not a rotation
    println("Test 2: Not a rotation (rearranged)")
    println("s1 = \"abcde\", s2 = \"abced\"")
    println("Output: ${solution.isRotation("abcde", "abced")}")
    println("Expected: false\n")
    
    // Test 3: Classic example
    println("Test 3: Classic waterbottle example")
    println("s1 = \"waterbottle\", s2 = \"erbottlewat\"")
    println("Output: ${solution.isRotation("waterbottle", "erbottlewat")}")
    println("Expected: true\n")
    
    // Test 4: Same strings
    println("Test 4: Identical strings (rotation by 0)")
    println("s1 = \"test\", s2 = \"test\"")
    println("Output: ${solution.isRotation("test", "test")}")
    println("Expected: true\n")
    
    // Test 5: Different lengths
    println("Test 5: Different lengths")
    println("s1 = \"abc\", s2 = \"abcd\"")
    println("Output: ${solution.isRotation("abc", "abcd")}")
    println("Expected: false\n")
    
    // Test 6: Empty strings
    println("Test 6: Empty strings")
    println("s1 = \"\", s2 = \"\"")
    println("Output: ${solution.isRotation("", "")}")
    println("Expected: true\n")
    
    // Test 7: Single character
    println("Test 7: Single character")
    println("s1 = \"a\", s2 = \"a\"")
    println("Output: ${solution.isRotation("a", "a")}")
    println("Expected: true\n")
    
    // Test 8: Manual rotation approach
    println("Test 8: Manual rotation approach")
    println("s1 = \"abc\", s2 = \"cab\"")
    println("Manual output: ${solution.isRotationManual("abc", "cab")}")
    println("Expected: true\n")
    
    // Test 9: Rotation by rotating
    println("Test 9: By actual rotation")
    println("s1 = \"hello\", s2 = \"lohel\"")
    println("Rotating output: ${solution.isRotationByRotating("hello", "lohel")}")
    println("Expected: true\n")
    
    // Test 10: Show all rotations
    println("Test 10: Display all rotations")
    val str = "abc"
    println("All rotations of \"$str\":")
    solution.getAllRotations(str).forEachIndexed { index, rotation ->
        println("  Rotation by $index: $rotation")
    }
    println()
}
