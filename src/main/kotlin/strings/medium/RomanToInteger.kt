/**
 * ============================================================================
 * PROBLEM: Roman to Integer
 * DIFFICULTY: Medium
 * CATEGORY: Strings - Medium
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 * 
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 
 * Roman numerals are usually written largest to smallest from left to right.
 * However, there are special cases for subtraction:
 * - I before V (5) or X (10) makes 4 or 9
 * - X before L (50) or C (100) makes 40 or 90
 * - C before D (500) or M (1000) makes 400 or 900
 * 
 * Given a roman numeral, convert it to an integer.
 * 
 * INPUT FORMAT:
 * - A string s representing a Roman numeral: s = "MCMXCIV"
 * 
 * OUTPUT FORMAT:
 * - Integer value: 1994
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 15
 * - s contains only characters 'I', 'V', 'X', 'L', 'C', 'D', 'M'
 * - It is guaranteed that s is a valid roman numeral in range [1, 3999]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Roman numerals follow an additive pattern with some subtractive cases.
 * 
 * Key observation:
 * - If current numeral is less than next numeral → SUBTRACT it
 * - Otherwise → ADD it
 * 
 * Examples:
 * "VI" = V + I = 5 + 1 = 6 (normal addition)
 * "IV" = V - I = 5 - 1 = 4 (subtraction case)
 * 
 * Why? In "IV", I (1) comes before V (5), which is larger, so we subtract.
 * In "VI", I (1) comes after V (5), which is larger, so we add.
 * 
 * KEY INSIGHTS:
 * 1. Process from left to right
 * 2. Compare current with next numeral
 * 3. If current < next: subtract current
 * 4. If current >= next: add current
 * 5. Last numeral is always added
 * 
 * ALGORITHM STEPS:
 * 1. Create a map of Roman numerals to values
 * 2. Initialize result = 0
 * 3. For each character (except last):
 *    a. Get current value and next value
 *    b. If current < next: subtract current
 *    c. Else: add current
 * 4. Add last character's value
 * 5. Return result
 * 
 * VISUAL EXAMPLE:
 * s = "MCMXCIV" = 1994
 * 
 * Breakdown:
 * M = 1000
 * CM = 900 (C before M, so 1000 - 100)
 * XC = 90 (X before C, so 100 - 10)
 * IV = 4 (I before V, so 5 - 1)
 * 
 * Step by step:
 * Index  Char  Value  Next  Action          Result
 * 0      M     1000   C     1000 > 100      +1000  = 1000
 * 1      C     100    M     100 < 1000      -100   = 900
 * 2      M     1000   X     1000 > 10       +1000  = 1900
 * 3      X     10     C     10 < 100        -10    = 1890
 * 4      C     100    I     100 > 1         +100   = 1990
 * 5      I     1      V     1 < 5           -1     = 1989
 * 6      V     5      -     (last)          +5     = 1994
 * 
 * OUTPUT: 1994
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Left-to-right comparison: O(n) time, O(1) space - OPTIMAL
 * 2. Replace special cases first: O(n) time, O(n) space - Less efficient
 * 3. Right-to-left scanning: O(n) time, O(1) space - Alternative style
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through the string
 * - Each character processed once
 * - Constant time operations per character (map lookup, comparison)
 * - n is the length of string (max 15)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Map of 7 Roman numerals (constant size)
 * - Few variables for tracking
 * - No data structures that grow with input
 * - Even though map size is fixed, we count it as O(1)
 * 
 * WHY THIS IS OPTIMAL:
 * - Must read each character at least once: O(n) minimum
 * - Fixed number of Roman numerals: O(1) space
 * - Cannot improve on O(n) time complexity
 * 
 * ============================================================================
 */

package strings.medium

class RomanToInteger {
    
    // Map of Roman numerals to their integer values
    private val romanMap = mapOf(
        'I' to 1,
        'V' to 5,
        'X' to 10,
        'L' to 50,
        'C' to 100,
        'D' to 500,
        'M' to 1000
    )
    
    /**
     * Converts Roman numeral to integer
     * 
     * @param s Roman numeral string
     * @return Integer value
     */
    fun romanToInt(s: String): Int {
        var result = 0
        
        // Process each character except the last
        for (i in 0 until s.length - 1) {
            val current = romanMap[s[i]]!!
            val next = romanMap[s[i + 1]]!!
            
            // If current is less than next, subtract it (subtractive case)
            // Otherwise, add it (normal case)
            if (current < next) {
                result -= current
            } else {
                result += current
            }
        }
        
        // Always add the last numeral
        result += romanMap[s[s.length - 1]]!!
        
        return result
    }
    
    /**
     * Alternative: Right-to-left approach
     * Process from right to left, maintaining previous value
     */
    fun romanToIntRightToLeft(s: String): Int {
        var result = 0
        var prevValue = 0
        
        // Process from right to left
        for (i in s.length - 1 downTo 0) {
            val currentValue = romanMap[s[i]]!!
            
            // If current value is less than previous, subtract it
            // This handles the subtractive cases (IV, IX, etc.)
            if (currentValue < prevValue) {
                result -= currentValue
            } else {
                result += currentValue
            }
            
            prevValue = currentValue
        }
        
        return result
    }
    
    /**
     * Alternative: Replace special cases first then sum
     * More intuitive but less efficient
     */
    fun romanToIntReplacement(s: String): Int {
        var str = s
        var result = 0
        
        // Define special two-character combinations
        val specialCases = mapOf(
            "IV" to 4,
            "IX" to 9,
            "XL" to 40,
            "XC" to 90,
            "CD" to 400,
            "CM" to 900
        )
        
        // Replace special cases and add their values
        for ((pattern, value) in specialCases) {
            while (pattern in str) {
                result += value
                str = str.replaceFirst(pattern, "")
            }
        }
        
        // Add remaining single numerals
        for (char in str) {
            result += romanMap[char]!!
        }
        
        return result
    }
    
    /**
     * Functional style using fold
     */
    fun romanToIntFunctional(s: String): Int {
        // Pair of (previousValue, runningSum)
        val (_, result) = s.reversed().fold(0 to 0) { (prevValue, sum), char ->
            val currentValue = romanMap[char]!!
            val newSum = if (currentValue < prevValue) {
                sum - currentValue
            } else {
                sum + currentValue
            }
            currentValue to newSum
        }
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "MCMXCIV"
 * 
 * DETAILED EXECUTION:
 * 
 * Initial state: result = 0
 * 
 * i=0: s[0]='M' (1000), s[1]='C' (100)
 *   1000 >= 100 → Add
 *   result = 0 + 1000 = 1000
 * 
 * i=1: s[1]='C' (100), s[2]='M' (1000)
 *   100 < 1000 → Subtract (CM = 900)
 *   result = 1000 - 100 = 900
 * 
 * i=2: s[2]='M' (1000), s[3]='X' (10)
 *   1000 >= 10 → Add
 *   result = 900 + 1000 = 1900
 * 
 * i=3: s[3]='X' (10), s[4]='C' (100)
 *   10 < 100 → Subtract (XC = 90)
 *   result = 1900 - 10 = 1890
 * 
 * i=4: s[4]='C' (100), s[5]='I' (1)
 *   100 >= 1 → Add
 *   result = 1890 + 100 = 1990
 * 
 * i=5: s[5]='I' (1), s[6]='V' (5)
 *   1 < 5 → Subtract (IV = 4)
 *   result = 1990 - 1 = 1989
 * 
 * Last character: s[6]='V' (5)
 *   Always add last
 *   result = 1989 + 5 = 1994
 * 
 * OUTPUT: 1994
 * 
 * ---
 * 
 * Example 2: s = "LVIII" = 58
 * 
 * i=0: 'L' (50), 'V' (5)
 *   50 >= 5 → Add → 50
 * 
 * i=1: 'V' (5), 'I' (1)
 *   5 >= 1 → Add → 55
 * 
 * i=2: 'I' (1), 'I' (1)
 *   1 >= 1 → Add → 56
 * 
 * i=3: 'I' (1), last
 *   Add → 57
 * 
 * Last: 'I' (1)
 *   Add → 58
 * 
 * OUTPUT: 58
 * 
 * ---
 * 
 * Example 3: s = "IX" = 9
 * 
 * i=0: 'I' (1), 'X' (10)
 *   1 < 10 → Subtract → -1
 * 
 * Last: 'X' (10)
 *   Add → -1 + 10 = 9
 * 
 * OUTPUT: 9
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Single character "I" → 1
 * 2. All same characters "III" → 3
 * 3. Maximum value "MMMCMXCIX" → 3999
 * 4. Minimum value "I" → 1
 * 5. All subtractive cases "IVXLCDM" → correctly handled
 * 6. Mixed cases "MCMXCIV" → 1994
 * 7. No subtractive cases "MDCLXVI" → 1666
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not handling last character
 * ❌ Loop through all characters without special handling
 * ✅ Add last character after loop
 * 
 * MISTAKE 2: Wrong subtraction logic
 * ❌ Only checking specific pairs (IV, IX, etc.)
 * ✅ General rule: if current < next, subtract
 * 
 * MISTAKE 3: Index out of bounds
 * ❌ Accessing s[i+1] without checking length
 * ✅ Loop to length-1 and handle last separately
 * 
 * MISTAKE 4: Hardcoding all combinations
 * ❌ Checking all 6 special cases individually
 * ✅ Use comparison rule (current < next)
 * 
 * MISTAKE 5: Not using map efficiently
 * ❌ if-else chain for each Roman numeral
 * ✅ Use map for O(1) lookup
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Historical Documents**: Reading old texts with Roman numerals
 * 2. **Clock Faces**: Converting Roman numeral time displays
 * 3. **Page Numbers**: Books using Roman numerals for preface
 * 4. **Movie Credits**: Copyright years in Roman numerals
 * 5. **Architecture**: Building dates and inscriptions
 * 6. **Educational Software**: Teaching number systems
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * - Integer to Roman (reverse conversion)
 * - Excel Sheet Column Number
 * - Excel Sheet Column Title
 * - Number of Steps to Reduce to Zero
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = RomanToInteger()
    
    println("=== Testing Roman to Integer ===\n")
    
    // Test 1: Classic example
    println("Test 1: MCMXCIV")
    println("Input: s = \"MCMXCIV\"")
    println("Output: ${solution.romanToInt("MCMXCIV")}")
    println("Expected: 1994\n")
    
    // Test 2: With subtractive notation
    println("Test 2: LVIII")
    println("Input: s = \"LVIII\"")
    println("Output: ${solution.romanToInt("LVIII")}")
    println("Expected: 58\n")
    
    // Test 3: Simple subtractive
    println("Test 3: IX")
    println("Input: s = \"IX\"")
    println("Output: ${solution.romanToInt("IX")}")
    println("Expected: 9\n")
    
    // Test 4: Single character
    println("Test 4: Single numeral")
    println("Input: s = \"I\"")
    println("Output: ${solution.romanToInt("I")}")
    println("Expected: 1\n")
    
    // Test 5: All same
    println("Test 5: Repeated numerals")
    println("Input: s = \"III\"")
    println("Output: ${solution.romanToInt("III")}")
    println("Expected: 3\n")
    
    // Test 6: Large number
    println("Test 6: Large number")
    println("Input: s = \"MMMCMXCIX\"")
    println("Output: ${solution.romanToInt("MMMCMXCIX")}")
    println("Expected: 3999\n")
    
    // Test 7: All additive
    println("Test 7: No subtractive cases")
    println("Input: s = \"MDCLXVI\"")
    println("Output: ${solution.romanToInt("MDCLXVI")}")
    println("Expected: 1666\n")
    
    // Test 8: Right-to-left approach
    println("Test 8: Using right-to-left")
    println("Input: s = \"MCMXCIV\"")
    println("Output: ${solution.romanToIntRightToLeft("MCMXCIV")}")
    println("Expected: 1994\n")
    
    // Test 9: Functional style
    println("Test 9: Using functional style")
    println("Input: s = \"LVIII\"")
    println("Output: ${solution.romanToIntFunctional("LVIII")}")
    println("Expected: 58\n")
}
