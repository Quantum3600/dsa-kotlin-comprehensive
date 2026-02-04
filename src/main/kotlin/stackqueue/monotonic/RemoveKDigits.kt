/**
 * ============================================================================
 * PROBLEM: Remove K Digits
 * DIFFICULTY: Medium
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given string num representing a non-negative integer num, and an integer k, 
 * return the smallest possible integer after removing k digits from num.
 * 
 * INPUT FORMAT:
 * - num: String representing a non-negative integer
 * - k: Number of digits to remove
 * Example: num = "1432219", k = 3
 * 
 * OUTPUT FORMAT:
 * - String representing the smallest possible integer
 * Example: "1219"
 * 
 * CONSTRAINTS:
 * - 1 <= k <= num.length <= 10^5
 * - num consists of only digits
 * - num does not have any leading zeros except for the zero itself
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To get the smallest number, we want smaller digits at the front.
 * When should we remove a digit? When the next digit is smaller!
 * 
 * Think of it this way:
 * "1432" - Should we keep 4? No! Because 3 < 4, so "132" < "142"
 * 
 * KEY INSIGHT - GREEDY APPROACH:
 * Scan left to right. If current digit < previous digit, remove previous.
 * This is a monotonic increasing stack problem!
 * 
 * VISUAL EXAMPLE:
 * ```
 * num = "1432219", k = 3
 * 
 * We want to remove 3 digits to get smallest number.
 * 
 * Step-by-step:
 * i=0, digit='1': stack=[], push '1', stack=['1']
 * 
 * i=1, digit='4': 4 > 1, push '4', stack=['1','4']
 * 
 * i=2, digit='3': 3 < 4, remove '4' (k=2 left)
 *                 3 > 1, push '3'
 *                 stack=['1','3']
 * 
 * i=3, digit='2': 2 < 3, remove '3' (k=1 left)
 *                 2 > 1, push '2'
 *                 stack=['1','2']
 * 
 * i=4, digit='2': 2 == 2, push '2'
 *                 stack=['1','2','2']
 * 
 * i=5, digit='1': 1 < 2, remove '2' (k=0 left)
 *                 Can't remove more, push '1'
 *                 stack=['1','2','1']
 * 
 * i=6, digit='9': 9 > 1, push '9'
 *                 stack=['1','2','1','9']
 * 
 * Result: "1219" ✓
 * ```
 * 
 * ALGORITHM:
 * 1. Use a stack (or StringBuilder for efficiency)
 * 2. For each digit in num:
 *    a. While stack not empty AND k > 0 AND stack.top > current:
 *       - Pop from stack (remove larger digit)
 *       - Decrement k
 *    b. Push current digit
 * 
 * 3. If k > 0 (didn't remove enough):
 *    - Remove from end (increasing sequence case)
 * 
 * 4. Remove leading zeros
 * 
 * 5. If result is empty, return "0"
 * 
 * EDGE CASES TO HANDLE:
 * 1. All digits removed: "9", k=1 → "0"
 * 2. Increasing sequence: "12345", k=2 → "123" (remove from end)
 * 3. Decreasing sequence: "54321", k=2 → "321" (remove from start)
 * 4. Leading zeros: "10200", k=1 → "200" (not "0200")
 * 5. All same: "1111", k=2 → "11"
 * 6. Result is zero: "10", k=2 → "0"
 * 
 * DETAILED TRACE:
 * ```
 * num = "1432219", k = 3
 * stack = []
 * 
 * Process '1': stack=['1'], k=3
 * Process '4': stack=['1','4'], k=3
 * Process '3': '3'<'4', pop '4', k=2, push '3', stack=['1','3']
 * Process '2': '2'<'3', pop '3', k=1, push '2', stack=['1','2']
 * Process '2': '2'=='2', push '2', stack=['1','2','2']
 * Process '1': '1'<'2', pop '2', k=0, push '1', stack=['1','2','1']
 * Process '9': can't pop (k=0), push '9', stack=['1','2','1','9']
 * 
 * k=0, so done removing
 * Result: "1219"
 * No leading zeros
 * Final: "1219" ✓
 * ```
 * 
 * WHY MONOTONIC STACK:
 * We maintain a monotonic increasing (or equal) sequence.
 * Whenever we see a smaller digit, we pop larger ones (within k removals).
 * This ensures the final result is as small as possible.
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: Try all combinations of removing k digits - O(C(n,k) * n)
 * 2. Greedy with Stack: O(n) - Current approach
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through num: O(n)
 * - Each digit pushed once: n pushes
 * - Each digit popped at most once: n pops
 * - Remove leading zeros: O(n)
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Stack/StringBuilder: O(n)
 * - Result string: O(n)
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*

class RemoveKDigits {
    
    /**
     * Main solution using monotonic increasing stack
     */
    fun removeKdigits(num: String, k: Int): String {
        if (k == num.length) return "0"
        
        val stack = StringBuilder()
        var toRemove = k
        
        // Build result using monotonic increasing stack
        for (digit in num) {
            // Remove larger digits while we can
            while (stack.isNotEmpty() && toRemove > 0 && stack.last() > digit) {
                stack.deleteCharAt(stack.length - 1)
                toRemove--
            }
            stack.append(digit)
        }
        
        // If we still need to remove digits (increasing sequence case)
        while (toRemove > 0) {
            stack.deleteCharAt(stack.length - 1)
            toRemove--
        }
        
        // Remove leading zeros
        var i = 0
        while (i < stack.length && stack[i] == '0') {
            i++
        }
        
        val result = stack.substring(i)
        return if (result.isEmpty()) "0" else result
    }
    
    /**
     * Alternative implementation using Stack
     */
    fun removeKdigitsWithStack(num: String, k: Int): String {
        if (k == num.length) return "0"
        
        val stack = Stack<Char>()
        var toRemove = k
        
        for (digit in num) {
            while (stack.isNotEmpty() && toRemove > 0 && stack.peek() > digit) {
                stack.pop()
                toRemove--
            }
            stack.push(digit)
        }
        
        // Remove remaining digits from end
        while (toRemove > 0) {
            stack.pop()
            toRemove--
        }
        
        // Build result and remove leading zeros
        val result = StringBuilder()
        for (digit in stack) {
            if (result.isEmpty() && digit == '0') continue
            result.append(digit)
        }
        
        return if (result.isEmpty()) "0" else result.toString()
    }
}

/**
 * ============================================================================
 * EDGE CASES & TESTING
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. All digits removed: "9", k=1 → "0"
 * 2. Remove all but one: "54321", k=4 → "1"
 * 3. Increasing sequence: "12345", k=2 → "123"
 * 4. Decreasing sequence: "54321", k=2 → "321"
 * 5. With zeros: "10200", k=1 → "200"
 * 6. Leading zeros: "10", k=1 → "0"
 * 7. All same: "1111", k=2 → "11"
 * 8. Result is zero: "100", k=2 → "0"
 * 
 * TRACE: num = "10200", k = 1
 * 
 * Process '1': stack=['1'], k=1
 * Process '0': '0'<'1', pop '1', k=0, push '0', stack=['0']
 * Process '2': can't pop (k=0), push '2', stack=['0','2']
 * Process '0': can't pop (k=0), push '0', stack=['0','2','0']
 * Process '0': can't pop (k=0), push '0', stack=['0','2','0','0']
 * 
 * k=0, done
 * Result: "0200"
 * Remove leading zeros: "200" ✓
 * 
 * TRACE: num = "12345", k = 2
 * 
 * Process all digits: stack=['1','2','3','4','5'], k=2
 * (No popping because increasing sequence)
 * 
 * Still k=2 remaining, remove from end:
 * Pop '5', k=1
 * Pop '4', k=0
 * 
 * Result: "123" ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = RemoveKDigits()
    
    println("Remove K Digits - Test Cases")
    println("=============================\n")
    
    // Test 1: Standard example
    println("Test 1: Standard Example")
    val num1 = "1432219"
    val k1 = 3
    println("Input: num = \"$num1\", k = $k1")
    println("We want to remove 3 digits to get smallest number")
    println("Remove 4, 3, 2 → \"1219\"")
    println("Output: \"${solution.removeKdigits(num1, k1)}\"")
    println("Expected: \"1219\"\n")
    
    // Test 2: Remove one digit
    println("Test 2: Remove One Digit")
    val num2 = "10200"
    val k2 = 1
    println("Input: num = \"$num2\", k = $k2")
    println("Remove the '1' → \"0200\" → \"200\" (remove leading zeros)")
    println("Output: \"${solution.removeKdigits(num2, k2)}\"")
    println("Expected: \"200\"\n")
    
    // Test 3: All digits same
    println("Test 3: All Same Digits")
    val num3 = "1111"
    val k3 = 2
    println("Input: num = \"$num3\", k = $k3")
    println("Any 2 digits removed gives \"11\"")
    println("Output: \"${solution.removeKdigits(num3, k3)}\"")
    println("Expected: \"11\"\n")
    
    // Test 4: Remove all digits
    println("Test 4: Remove All Digits")
    val num4 = "9"
    val k4 = 1
    println("Input: num = \"$num4\", k = $k4")
    println("Output: \"${solution.removeKdigits(num4, k4)}\"")
    println("Expected: \"0\"\n")
    
    // Test 5: Increasing sequence
    println("Test 5: Increasing Sequence")
    val num5 = "12345"
    val k5 = 2
    println("Input: num = \"$num5\", k = $k5")
    println("Can't remove from front (increasing), remove from end")
    println("Output: \"${solution.removeKdigits(num5, k5)}\"")
    println("Expected: \"123\"\n")
    
    // Test 6: Decreasing sequence
    println("Test 6: Decreasing Sequence")
    val num6 = "54321"
    val k6 = 2
    println("Input: num = \"$num6\", k = $k6")
    println("Remove largest digits from front: 5, 4")
    println("Output: \"${solution.removeKdigits(num6, k6)}\"")
    println("Expected: \"321\"\n")
    
    // Test 7: Leading zeros result
    println("Test 7: Result with Leading Zeros")
    val num7 = "10"
    val k7 = 1
    println("Input: num = \"$num7\", k = $k7")
    println("Remove '1' → \"0\"")
    println("Output: \"${solution.removeKdigits(num7, k7)}\"")
    println("Expected: \"0\"\n")
    
    // Test 8: Complex case
    println("Test 8: Complex Case")
    val num8 = "5337"
    val k8 = 2
    println("Input: num = \"$num8\", k = $k8")
    println("Remove 5, 3 → \"37\"")
    println("Output: \"${solution.removeKdigits(num8, k8)}\"")
    println("Expected: \"37\"\n")
    
    // Test 9: Large k
    println("Test 9: Remove Almost All")
    val num9 = "112"
    val k9 = 1
    println("Input: num = \"$num9\", k = $k9")
    println("Output: \"${solution.removeKdigits(num9, k9)}\"")
    println("Expected: \"11\"\n")
    
    // Test 10: All zeros
    println("Test 10: Result is Zero")
    val num10 = "100"
    val k10 = 2
    println("Input: num = \"$num10\", k = $k10")
    println("Output: \"${solution.removeKdigits(num10, k10)}\"")
    println("Expected: \"0\"\n")
    
    // Compare implementations
    println("=== Implementation Comparison ===")
    println("StringBuilder: \"${solution.removeKdigits(num1, k1)}\"")
    println("Stack: \"${solution.removeKdigitsWithStack(num1, k1)}\"")
    println("Both should return: \"1219\"\n")
    
    println("=== Key Insights ===")
    println("1. Greedy approach: Remove digit if next digit is smaller")
    println("2. Monotonic increasing stack ensures smallest result")
    println("3. If k remains, remove from end (increasing sequence)")
    println("4. Must handle leading zeros carefully")
    println("5. Empty result should return \"0\"")
    println()
    
    println("=== Algorithm Pattern ===")
    println("For each digit:")
    println("  While (stack not empty) AND (k > 0) AND (stack.top > current):")
    println("    Pop from stack")
    println("    Decrement k")
    println("  Push current digit")
    println()
    println("If k > 0: Remove k digits from end")
    println("Remove leading zeros")
    println()
    
    println("=== Why It Works ===")
    println("We want smallest number, so smaller digits should come first.")
    println("When we see a smaller digit, we remove previous larger ones.")
    println("This greedy choice gives optimal result!")
    println()
    println("Example: \"1432\" with k=1")
    println("  When we see '3', we remove '4' → \"132\"")
    println("  This is smaller than keeping '4' → \"142\"")
    
    println("\n=== Performance ===")
    println("Time: O(n) - Single pass through string")
    println("Space: O(n) - Stack storage")
    println("\nFor n=100,000:")
    println("Operations: ~300,000 (read + push + pop)")
}
