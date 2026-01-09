/**
 * ============================================================================
 * PROBLEM: Single Number I
 * DIFFICULTY: Easy
 * CATEGORY: Bit Manipulation - Interview
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a non-empty array of integers where every element appears twice except
 * for one element which appears only once, find that single element.
 * 
 * You must implement a solution with O(n) time complexity and O(1) extra space.
 * 
 * INPUT FORMAT:
 * - Array of integers:  [2, 2, 1]
 * - Every element appears exactly twice except one element
 * 
 * OUTPUT FORMAT:
 * - The single element that appears once:  1
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 3 * 10^4
 * - -3 * 10^4 <= nums[i] <= 3 * 10^4
 * - Each element appears exactly twice except for one element which appears once
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is a classic XOR problem!  XOR has special mathematical properties that
 * make it perfect for this problem.  The key insight is that XOR-ing a number
 * with itself gives 0, and XOR-ing with 0 gives the number itself.
 * 
 * KEY INSIGHT - XOR PROPERTIES:
 * 1. a ^ a = 0  (any number XOR itself is 0)
 * 2. a ^ 0 = a  (any number XOR 0 is itself)
 * 3. XOR is commutative: a ^ b = b ^ a
 * 4. XOR is associative: (a ^ b) ^ c = a ^ (b ^ c)
 * 
 * WHY THIS WORKS:
 * If we XOR all numbers together, the pairs cancel out (become 0),
 * leaving only the single number! 
 * 
 * Example: [2, 2, 1]
 * 2 ^ 2 ^ 1 = (2 ^ 2) ^ 1 = 0 ^ 1 = 1 ✓
 * 
 * Example: [4, 1, 2, 1, 2]
 * 4 ^ 1 ^ 2 ^ 1 ^ 2
 * = 4 ^ (1 ^ 1) ^ (2 ^ 2)
 * = 4 ^ 0 ^ 0
 * = 4 ✓
 * 
 * ALGORITHM STEPS:
 * 1. Initialize result = 0
 * 2. XOR result with each element in array
 * 3. Return result (all pairs cancelled, only single remains)
 * 
 * VISUAL EXAMPLE 1: [2, 2, 1]
 * ───────────────────────────────────────
 * Initial: result = 0 (binary: 000)
 * 
 * Step 1: result = 0 ^ 2
 *   000  (0)
 * ^ 010  (2)
 *   ───
 *   010  (2)
 * 
 * Step 2: result = 2 ^ 2
 *   010  (2)
 * ^ 010  (2)
 *   ───
 *   000  (0)  ← Pair cancelled!
 * 
 * Step 3: result = 0 ^ 1
 *   000  (0)
 * ^ 001  (1)
 *   ───
 *   001  (1)
 * 
 * Result: 1 ✓
 * 
 * VISUAL EXAMPLE 2: [4, 1, 2, 1, 2]
 * ───────────────────────────────────────
 * result = 0
 * 
 * XOR with 4: 0 ^ 4 = 4  (100)
 * XOR with 1: 4 ^ 1 = 5  (101)
 * XOR with 2: 5 ^ 2 = 7  (111)
 * XOR with 1: 7 ^ 1 = 6  (110)
 * XOR with 2: 6 ^ 2 = 4  (100)
 * 
 * Result: 4 ✓
 * 
 * Let's see the cancellation:
 * 4 ^ 1 ^ 2 ^ 1 ^ 2
 * = 4 ^ (1 ^ 1) ^ (2 ^ 2)  [rearrange due to associativity]
 * = 4 ^ 0 ^ 0
 * = 4 ✓
 * 
 * ============================================================================
 * WHY OTHER APPROACHES DON'T WORK
 * ============================================================================
 * 
 * APPROACH:  HashMap/HashSet
 * - Store each number, remove if seen twice
 * - TIME: O(n), SPACE: O(n) ❌ (violates O(1) space requirement)
 * 
 * APPROACH: Sorting
 * - Sort array, check adjacent pairs
 * - TIME: O(n log n) ❌ (violates O(n) time requirement)
 * - SPACE: O(1) ✓
 * 
 * APPROACH: Sum Formula
 * - sum(unique) * 2 - sum(all)
 * - TIME: O(n), SPACE: O(n) for set ❌
 * 
 * XOR APPROACH:
 * - TIME: O(n) ✓
 * - SPACE:  O(1) ✓
 * - Elegant and efficient!  ✓✓✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - Single pass through array
 * - XOR operation is O(1)
 * - Total: O(n) where n is array length
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only one variable (result) used
 * - No additional data structures
 * - Perfect constant space! 
 * 
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: [2, 2, 1]
 * ────────────────────
 * Input: nums = [2, 2, 1]
 * 
 * Iteration 1: result = 0 ^ 2 = 2
 * Iteration 2: result = 2 ^ 2 = 0
 * Iteration 3: result = 0 ^ 1 = 1
 * 
 * Output: 1 ✓
 * 
 * Example 2: [4, 1, 2, 1, 2]
 * ──────────────────────────
 * Input: nums = [4, 1, 2, 1, 2]
 * 
 * Iteration 1: result = 0 ^ 4 = 4
 * Iteration 2: result = 4 ^ 1 = 5
 * Iteration 3: result = 5 ^ 2 = 7
 * Iteration 4: result = 7 ^ 1 = 6  (first 1 ^ second 1 partially cancels)
 * Iteration 5: result = 6 ^ 2 = 4  (pairs fully cancelled)
 * 
 * Output: 4 ✓
 * 
 * Example 3: [1]
 * ──────────────
 * Input: nums = [1]
 * 
 * Iteration 1: result = 0 ^ 1 = 1
 * 
 * Output: 1 ✓
 * 
 * ============================================================================
 * LEETCODE 136 - SINGLE NUMBER
 * ============================================================================
 * 
 * This problem is commonly known as LeetCode 136.
 * 
 * Similar Problems:
 * - Single Number II (LeetCode 137): Every element appears 3 times except one
 * - Single Number III (LeetCode 260): Two elements appear once, rest twice
 * - Missing Number (LeetCode 268): Similar XOR approach
 */

package bitmanipulation.interview

class SingleNumberI {
    
    /**
     * Find the single number that appears once using XOR. 
     * 
     * This is the optimal solution utilizing XOR properties: 
     * - a ^ a = 0 (pairs cancel)
     * - a ^ 0 = a (zero is identity)
     * - XOR is commutative and associative
     * 
     * @param nums Array where every element appears twice except one
     * @return The single element that appears once
     */
    fun findSingleNumber(nums: IntArray): Int {
        // XOR all elements together
        // Pairs will cancel out (a ^ a = 0), leaving only the single number
        var result = 0
        
        for (num in nums) {
            result = result xor num  // Can also write as:  result ^= num
        }
        
        return result
    }
    
    /**
     * Alternative implementation using fold (functional style).
     * 
     * More idiomatic Kotlin using reduce/fold operations.
     * Same logic, different syntax.
     * 
     * @param nums Array where every element appears twice except one
     * @return The single element that appears once
     */
    fun findSingleNumberFunctional(nums: IntArray): Int {
        // Fold (reduce) the array using XOR operation
        return nums.fold(0) { acc, num -> acc xor num }
    }
    
    /**
     * EDUCATIONAL:  HashMap approach (NOT optimal for this problem)
     * 
     * Uses extra space but demonstrates alternative thinking. 
     * Good to know, but XOR is superior. 
     * 
     * TIME: O(n), SPACE: O(n) ❌ Violates O(1) space requirement
     * 
     * @param nums Array where every element appears twice except one
     * @return The single element that appears once
     */
    fun findSingleNumberHashMap(nums: IntArray): Int {
        val frequency = mutableMapOf<Int, Int>()
        
        // Count occurrences
        for (num in nums) {
            frequency[num] = frequency.getOrDefault(num, 0) + 1
        }
        
        // Find number with count 1
        for ((num, count) in frequency) {
            if (count == 1) {
                return num
            }
        }
        
        throw IllegalArgumentException("No single number found")
    }
    
    /**
     * EDUCATIONAL: HashSet approach (NOT optimal)
     * 
     * Add unseen numbers, remove seen numbers.
     * Remaining number is the single one.
     * 
     * TIME: O(n), SPACE: O(n) ❌ Violates O(1) space requirement
     * 
     * @param nums Array where every element appears twice except one
     * @return The single element that appears once
     */
    fun findSingleNumberHashSet(nums: IntArray): Int {
        val seen = mutableSetOf<Int>()
        
        for (num in nums) {
            if (num in seen) {
                seen.remove(num)  // Seen twice, remove it
            } else {
                seen.add(num)  // First time seeing
            }
        }
        
        // Only the single number remains
        return seen. first()
    }
    
    /**
     * Verify solution by checking that all other numbers appear twice.
     * Useful for testing. 
     * 
     * @param nums Input array
     * @param single The claimed single number
     * @return true if single appears once and all others appear twice
     */
    fun verifySolution(nums: IntArray, single: Int): Boolean {
        val frequency = mutableMapOf<Int, Int>()
        
        for (num in nums) {
            frequency[num] = frequency.getOrDefault(num, 0) + 1
        }
        
        return frequency[single] == 1 && 
               frequency.all { (num, count) -> num == single || count == 2 }
    }
}

/**
 * ============================================================================
 * TEST CASES & EXAMPLES
 * ============================================================================
 */
fun main() {
    val solution = SingleNumberI()
    
    println("=". repeat(70))
    println("SINGLE NUMBER I - TESTS")
    println("=".repeat(70))
    
    // Test Case 1: Basic case
    println("\n✅ Test Case 1: [2, 2, 1]")
    println("─". repeat(50))
    val nums1 = intArrayOf(2, 2, 1)
    val result1 = solution.findSingleNumber(nums1)
    println("Input: ${nums1.contentToString()}")
    println("Output: $result1")
    println("Expected: 1")
    println("Verified: ${solution.verifySolution(nums1, result1)}")
    // Expected: 1
    
    // Test Case 2: Larger array
    println("\n✅ Test Case 2: [4, 1, 2, 1, 2]")
    println("─".repeat(50))
    val nums2 = intArrayOf(4, 1, 2, 1, 2)
    val result2 = solution.findSingleNumber(nums2)
    println("Input: ${nums2.contentToString()}")
    println("Output: $result2")
    println("Expected:  4")
    println("Verified: ${solution.verifySolution(nums2, result2)}")
    // Expected: 4
    
    // Test Case 3: Single element
    println("\n✅ Test Case 3: [1]")
    println("─".repeat(50))
    val nums3 = intArrayOf(1)
    val result3 = solution.findSingleNumber(nums3)
    println("Input: ${nums3.contentToString()}")
    println("Output: $result3")
    println("Expected: 1")
    println("Verified: ${solution.verifySolution(nums3, result3)}")
    // Expected: 1
    
    // Test Case 4: Negative numbers
    println("\n✅ Test Case 4: [-1, -1, -2, -2, 5]")
    println("─".repeat(50))
    val nums4 = intArrayOf(-1, -1, -2, -2, 5)
    val result4 = solution.findSingleNumber(nums4)
    println("Input: ${nums4.contentToString()}")
    println("Output: $result4")
    println("Expected: 5")
    println("Verified: ${solution.verifySolution(nums4, result4)}")
    // Expected: 5
    
    // Test Case 5: Larger array with many duplicates
    println("\n✅ Test Case 5: [10, 20, 30, 40, 50, 10, 20, 30, 40]")
    println("─".repeat(50))
    val nums5 = intArrayOf(10, 20, 30, 40, 50, 10, 20, 30, 40)
    val result5 = solution.findSingleNumber(nums5)
    println("Input: ${nums5.contentToString()}")
    println("Output: $result5")
    println("Expected:  50")
    println("Verified:  ${solution.verifySolution(nums5, result5)}")
    // Expected: 50
    
    // Test Case 6: Single number at beginning
    println("\n✅ Test Case 6: [7, 3, 3, 5, 5]")
    println("─".repeat(50))
    val nums6 = intArrayOf(7, 3, 3, 5, 5)
    val result6 = solution.findSingleNumber(nums6)
    println("Input: ${nums6.contentToString()}")
    println("Output: $result6")
    println("Expected: 7")
    println("Verified: ${solution.verifySolution(nums6, result6)}")
    // Expected: 7
    
    // Demonstration:  Show XOR process step by step
    println("\n" + "=".repeat(70))
    println("XOR PROCESS VISUALIZATION")
    println("=".repeat(70))
    
    val demo = intArrayOf(4, 1, 2, 1, 2)
    println("\nInput: ${demo.contentToString()}")
    println("Goal: Find the single number\n")
    
    var xorResult = 0
    println("Step-by-step XOR:")
    println("Initial result = 0 (binary: ${xorResult. toString(2).padStart(3, '0')})")
    
    for ((index, num) in demo.withIndex()) {
        val before = xorResult
        xorResult = xorResult xor num
        println("Step ${index + 1}: $before ^ $num = $xorResult " +
                "(binary: ${before.toString(2).padStart(3, '0')} ^ " +
                "${num.toString(2).padStart(3, '0')} = " +
                "${xorResult. toString(2).padStart(3, '0')})")
    }
    
    println("\nFinal result:  $xorResult")
    println("\nExplanation:  Pairs (1,1) and (2,2) cancelled out, leaving 4!")
    
    // Compare all approaches
    println("\n" + "=".repeat(70))
    println("APPROACH COMPARISON")
    println("=".repeat(70))
    
    val testArray = intArrayOf(4, 1, 2, 1, 2)
    
    println("\nTesting array: ${testArray.contentToString()}")
    println("\n1. XOR approach (Optimal):")
    println("   Result: ${solution.findSingleNumber(testArray)}")
    println("   Time: O(n), Space: O(1) ✓✓")
    
    println("\n2.  Functional XOR:")
    println("   Result: ${solution.findSingleNumberFunctional(testArray)}")
    println("   Time: O(n), Space: O(1) ✓✓")
    
    println("\n3. HashMap approach:")
    println("   Result:  ${solution.findSingleNumberHashMap(testArray)}")
    println("   Time: O(n), Space: O(n) ❌")
    
    println("\n4. HashSet approach:")
    println("   Result: ${solution.findSingleNumberHashSet(testArray)}")
    println("   Time: O(n), Space: O(n) ❌")
    
    println("\nConclusion: XOR is the clear winner!  🏆")
}
