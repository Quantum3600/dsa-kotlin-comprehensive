/**
 * ============================================================================
 * PROBLEM: Single Number II
 * DIFFICULTY: Medium
 * CATEGORY: Bit Manipulation - Interview
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers where every element appears three times except
 * for one element which appears exactly once, find that single element.
 * 
 * You must implement a solution with O(n) time complexity and O(1) extra space.
 * 
 * INPUT FORMAT: 
 * - Array of integers:  [2, 2, 3, 2]
 * - Every element appears exactly three times except one element
 * 
 * OUTPUT FORMAT:
 * - The single element that appears once:  3
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 3 * 10^4
 * - -2^31 <= nums[i] <= 2^31 - 1
 * - Each element appears exactly three times except for one element which appears once
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Unlike Single Number I where we used simple XOR, this problem requires
 * tracking bit occurrences modulo 3. If a bit appears 3 times, it should
 * become 0 (reset). The remaining bits form the single number.
 * 
 * KEY INSIGHT: 
 * - Count each bit position across all numbers
 * - If count[bit] % 3 == 1, that bit belongs to the single number
 * - If count[bit] % 3 == 0, that bit came from the triplicates
 * 
 * WHY SIMPLE XOR DOESN'T WORK:
 * - a ^ a ^ a = a (not 0!)
 * - XOR has period 2, but we need period 3
 * - Need a different approach for modulo 3
 * 
 * THOUGHT PROCESS:
 * For each bit position (0-31):
 * - Count how many numbers have that bit set
 * - count % 3 gives 0 (from triplicates) or 1 (from single)
 * - Build result from bits that appear once
 * 
 * VISUAL EXAMPLE:   [2, 2, 3, 2]
 * ───────────────────────────────────────
 * Numbers in binary: 
 * 2 = 010
 * 2 = 010
 * 3 = 011
 * 2 = 010
 * 
 * Count each bit: 
 * Bit 0: 0+0+1+0 = 1 → 1 % 3 = 1 ✓
 * Bit 1: 1+1+1+1 = 4 → 4 % 3 = 1 ✓
 * Bit 2: 0+0+0+0 = 0 → 0 % 3 = 0
 * 
 * Result:   bits 0 and 1 are set → 011 = 3 ✓
 * 
 * ============================================================================
 * APPROACH 1: BIT COUNTING (Intuitive)
 * ============================================================================
 * 
 * ALGORITHM: 
 * 1. For each of 32 bit positions: 
 *    a. Count how many numbers have that bit set
 *    b. If count % 3 == 1, set that bit in result
 * 2. Return result
 * 
 * TIME:  O(32n) = O(n)
 * SPACE: O(1)
 * 
 * VISUAL WALKTHROUGH:  [2, 2, 3, 2]
 * ────────────────────────────────
 * 
 * Bit 0 (LSB):
 *   2:  0
 *   2: 0
 *   3: 1
 *   2: 0
 *   Sum: 1 → 1 % 3 = 1 → Set bit 0 in result
 * 
 * Bit 1:
 *   2: 1
 *   2: 1
 *   3: 1
 *   2: 1
 *   Sum: 4 → 4 % 3 = 1 → Set bit 1 in result
 * 
 * Bit 2:
 *   2: 0
 *   2: 0
 *   3: 0
 *   2: 0
 *   Sum: 0 → 0 % 3 = 0 → Don't set bit 2
 * 
 * Result:  011 (binary) = 3 (decimal) ✓
 * 
 * ============================================================================
 * APPROACH 2: TWO VARIABLES (State Machine) - OPTIMAL
 * ============================================================================
 * 
 * INTUITION:
 * We can track bit counts using two variables (ones and twos) that represent
 * a state machine with 3 states: seen 0 times, seen 1 time, seen 2 times.
 * When a bit is seen 3 times, it resets to 0.
 * 
 * STATE MACHINE: 
 * - ones:  bits that appeared 1 or 4 or 7...  times (count % 3 == 1)
 * - twos: bits that appeared 2 or 5 or 8... times (count % 3 == 2)
 * - When both are set, reset both (seen 3 times)
 * 
 * ALGORITHM:
 * 1. For each number:
 *    a. twos |= (ones & num)  // Bits in both ones and num move to twos
 *    b. ones ^= num           // Add num to ones (toggle)
 *    c. threes = ones & twos  // Bits that appeared 3 times
 *    d. ones &= ~threes       // Remove bits seen 3 times from ones
 *    e. twos &= ~threes       // Remove bits seen 3 times from twos
 * 2. Return ones (bits that appeared once)
 * 
 * VISUAL EXAMPLE: [2, 2, 3, 2]
 * ────────────────────────────────
 * Initial: ones = 000, twos = 000
 * 
 * Process 2 (010):
 *   twos = 000 | (000 & 010) = 000
 *   ones = 000 ^ 010 = 010
 *   threes = 010 & 000 = 000
 *   ones = 010 & ~000 = 010
 *   twos = 000 & ~000 = 000
 *   State: ones=010, twos=000
 * 
 * Process 2 (010):
 *   twos = 000 | (010 & 010) = 010
 *   ones = 010 ^ 010 = 000
 *   threes = 000 & 010 = 000
 *   ones = 000 & ~000 = 000
 *   twos = 010 & ~000 = 010
 *   State: ones=000, twos=010
 * 
 * Process 3 (011):
 *   twos = 010 | (000 & 011) = 010
 *   ones = 000 ^ 011 = 011
 *   threes = 011 & 010 = 010
 *   ones = 011 & ~010 = 001
 *   twos = 010 & ~010 = 000
 *   State: ones=001, twos=000
 * 
 * Process 2 (010):
 *   twos = 000 | (001 & 010) = 000
 *   ones = 001 ^ 010 = 011
 *   threes = 011 & 000 = 000
 *   ones = 011 & ~000 = 011
 *   twos = 000 & ~000 = 000
 *   State:  ones=011, twos=000
 * 
 * Result: ones = 011 = 3 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * APPROACH 1 (Bit Counting):
 * TIME COMPLEXITY: O(32n) = O(n)
 * - Loop through 32 bits
 * - For each bit, loop through n numbers
 * - Total: 32 * n operations
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only a few variables regardless of input size
 * 
 * APPROACH 2 (State Machine):
 * TIME COMPLEXITY: O(n)
 * - Single pass through array
 * - Constant time operations per element
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only two variables (ones and twos)
 * 
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: [2, 2, 3, 2]
 * ───────────────────────
 * Input: [2, 2, 3, 2]
 * Binary representation:
 *   2 = 010
 *   2 = 010
 *   3 = 011
 *   2 = 010
 * 
 * Bit 0: 0+0+1+0 = 1 → 1%3 = 1 ✓
 * Bit 1: 1+1+1+1 = 4 → 4%3 = 1 ✓
 * Bit 2: 0+0+0+0 = 0 → 0%3 = 0
 * 
 * Result: 011 = 3 ✓
 * 
 * Example 2: [0, 1, 0, 1, 0, 1, 99]
 * ──────────────────────────────────
 * Input: [0, 1, 0, 1, 0, 1, 99]
 * 
 * 0 appears 3 times → contributes 0
 * 1 appears 3 times → contributes 0
 * 99 appears 1 time → contributes 99
 * 
 * Result: 99 ✓
 * 
 * Example 3: [-2, -2, 1, -2]
 * ───────────────────────────
 * Input: [-2, -2, 1, -2]
 * 
 * -2 appears 3 times → contributes 0
 * 1 appears 1 time → contributes 1
 * 
 * Result: 1 ✓
 * 
 * ============================================================================
 * LEETCODE 137 - SINGLE NUMBER II
 * ============================================================================
 * 
 * This problem is commonly known as LeetCode 137.
 * 
 * Similar Problems: 
 * - Single Number I (LeetCode 136): Every element appears 2 times except one
 * - Single Number III (LeetCode 260): Two elements appear once
 * - Find the Duplicate Number (LeetCode 287)
 * 
 * KEY DIFFERENCES FROM SINGLE NUMBER I:
 * - Period changed from 2 to 3
 * - XOR doesn't work (period 2 only)
 * - Need modulo 3 arithmetic
 * - More complex state tracking
 */

package bitmanipulation.interview

class SingleNumberII {
    
    /**
     * APPROACH 1: Bit counting (Most intuitive)
     * 
     * Count occurrences of each bit across all numbers. 
     * Bits with count % 3 == 1 belong to the single number.
     * 
     * @param nums Array where every element appears three times except one
     * @return The single element that appears once
     */
    fun findSingleNumberBitCount(nums: IntArray): Int {
        var result = 0
        
        // Check each of the 32 bit positions
        for (bitPos in 0 until 32) {
            var bitSum = 0
            
            // Count how many numbers have this bit set
            for (num in nums) {
                // Check if bit at position bitPos is set in num
                if ((num shr bitPos) and 1 == 1) {
                    bitSum++
                }
            }
            
            // If bitSum % 3 == 1, this bit belongs to the single number
            if (bitSum % 3 == 1) {
                result = result or (1 shl bitPos)
            }
        }
        
        return result
    }
    
    /**
     * APPROACH 2: State machine with two variables (Optimal)
     * 
     * Uses two variables (ones and twos) to track bit counts modulo 3.
     * - ones: bits seen 1 time (mod 3)
     * - twos: bits seen 2 times (mod 3)
     * - When seen 3 times, reset both
     * 
     * This is the most elegant and efficient solution! 
     * 
     * @param nums Array where every element appears three times except one
     * @return The single element that appears once
     */
    fun findSingleNumber(nums: IntArray): Int {
        var ones = 0  // Bits that appeared 1 time (mod 3)
        var twos = 0  // Bits that appeared 2 times (mod 3)
        
        for (num in nums) {
            // Bits that were in ones and now appear again go to twos
            twos = twos or (ones and num)
            
            // Toggle bits in ones (add new bits, remove if already there)
            ones = ones xor num
            
            // Bits that appear in both ones and twos have been seen 3 times
            val threes = ones and twos
            
            // Remove bits that appeared 3 times from both ones and twos
            ones = ones and threes. inv()
            twos = twos and threes.inv()
        }
        
        // ones contains bits that appeared exactly once
        return ones
    }
    
    /**
     * APPROACH 3: Alternative state machine (Simplified logic)
     * 
     * Similar to approach 2 but with slightly different bit manipulation.
     * Same complexity, different implementation style.
     * 
     * @param nums Array where every element appears three times except one
     * @return The single element that appears once
     */
    fun findSingleNumberAlternative(nums: IntArray): Int {
        var ones = 0
        var twos = 0
        
        for (num in nums) {
            // Update twos: add bits that are in both ones and current num
            twos = twos or (ones and num)
            
            // Update ones: XOR with current num
            ones = ones xor num
            
            // Calculate common:  bits that appeared 3 times
            val common = ones and twos
            
            // Remove common bits from both
            ones = ones xor common
            twos = twos xor common
        }
        
        return ones
    }
    
    /**
     * EDUCATIONAL:  HashMap approach (NOT optimal for this problem)
     * 
     * Count frequency of each number, return the one with count 1.
     * 
     * TIME: O(n), SPACE: O(n) ❌ Violates O(1) space requirement
     * 
     * @param nums Array where every element appears three times except one
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
     * EDUCATIONAL: Mathematical approach using sets
     * 
     * Formula: 3 * sum(unique) - sum(all) = 2 * single
     * single = (3 * sum(unique) - sum(all)) / 2
     * 
     * TIME: O(n), SPACE: O(n) ❌ Violates O(1) space requirement
     * 
     * @param nums Array where every element appears three times except one
     * @return The single element that appears once
     */
    fun findSingleNumberMath(nums: IntArray): Int {
        val unique = nums.toSet()
        val sumUnique = unique.sumOf { it. toLong() }
        val sumAll = nums.sumOf { it.toLong() }
        
        // 3 * sum(unique) - sum(all) = 2 * single
        return ((3 * sumUnique - sumAll) / 2).toInt()
    }
    
    /**
     * Verify solution by checking counts. 
     * Useful for testing. 
     * 
     * @param nums Input array
     * @param single The claimed single number
     * @return true if single appears once and all others appear three times
     */
    fun verifySolution(nums: IntArray, single: Int): Boolean {
        val frequency = mutableMapOf<Int, Int>()
        
        for (num in nums) {
            frequency[num] = frequency.getOrDefault(num, 0) + 1
        }
        
        return frequency[single] == 1 && 
               frequency.all { (num, count) -> num == single || count == 3 }
    }
}

/**
 * ============================================================================
 * TEST CASES & EXAMPLES
 * ============================================================================
 */
fun main() {
    val solution = SingleNumberII()
    
    println("=". repeat(70))
    println("SINGLE NUMBER II - TESTS")
    println("=".repeat(70))
    
    // Test Case 1: Basic case
    println("\n✅ Test Case 1: [2, 2, 3, 2]")
    println("─". repeat(50))
    val nums1 = intArrayOf(2, 2, 3, 2)
    val result1 = solution.findSingleNumber(nums1)
    println("Input: ${nums1.contentToString()}")
    println("Output: $result1")
    println("Expected: 3")
    println("Verified: ${solution.verifySolution(nums1, result1)}")
    // Expected: 3
    
    // Test Case 2: Larger array
    println("\n✅ Test Case 2: [0, 1, 0, 1, 0, 1, 99]")
    println("─".repeat(50))
    val nums2 = intArrayOf(0, 1, 0, 1, 0, 1, 99)
    val result2 = solution.findSingleNumber(nums2)
    println("Input: ${nums2.contentToString()}")
    println("Output: $result2")
    println("Expected: 99")
    println("Verified: ${solution.verifySolution(nums2, result2)}")
    // Expected: 99
    
    // Test Case 3: Negative numbers
    println("\n✅ Test Case 3: [-2, -2, 1, -2]")
    println("─".repeat(50))
    val nums3 = intArrayOf(-2, -2, 1, -2)
    val result3 = solution.findSingleNumber(nums3)
    println("Input: ${nums3.contentToString()}")
    println("Output: $result3")
    println("Expected: 1")
    println("Verified: ${solution. verifySolution(nums3, result3)}")
    // Expected: 1
    
    // Test Case 4: Single element
    println("\n✅ Test Case 4: [5]")
    println("─".repeat(50))
    val nums4 = intArrayOf(5)
    val result4 = solution.findSingleNumber(nums4)
    println("Input:  ${nums4.contentToString()}")
    println("Output: $result4")
    println("Expected: 5")
    println("Verified: ${solution.verifySolution(nums4, result4)}")
    // Expected: 5
    
    // Test Case 5: Larger numbers
    println("\n✅ Test Case 5: [100, 200, 300, 100, 200, 100, 200]")
    println("─".repeat(50))
    val nums5 = intArrayOf(100, 200, 300, 100, 200, 100, 200)
    val result5 = solution.findSingleNumber(nums5)
    println("Input: ${nums5.contentToString()}")
    println("Output: $result5")
    println("Expected: 300")
    println("Verified:  ${solution.verifySolution(nums5, result5)}")
    // Expected: 300
    
    // Test Case 6: Mixed positive and negative
    println("\n✅ Test Case 6: [-1, -1, -1, -2, -2, -2, 7]")
    println("─".repeat(50))
    val nums6 = intArrayOf(-1, -1, -1, -2, -2, -2, 7)
    val result6 = solution.findSingleNumber(nums6)
    println("Input: ${nums6.contentToString()}")
    println("Output: $result6")
    println("Expected: 7")
    println("Verified: ${solution. verifySolution(nums6, result6)}")
    // Expected: 7
    
    // Demonstration:  Show bit counting process
    println("\n" + "=".repeat(70))
    println("BIT COUNTING VISUALIZATION")
    println("=".repeat(70))
    
    val demo = intArrayOf(2, 2, 3, 2)
    println("\nInput: ${demo.contentToString()}")
    println("\nBinary representations:")
    for (num in demo) {
        println("  $num = ${num.toString(2).padStart(3, '0')}")
    }
    
    println("\nBit position analysis:")
    for (bit in 0.. 2) {
        var count = 0
        for (num in demo) {
            if ((num shr bit) and 1 == 1) {
                count++
            }
        }
        val inResult = count % 3 == 1
        println("  Bit $bit: count=$count → $count%3=${count%3} → ${if (inResult) "SET ✓" else "NOT SET"}")
    }
    
    println("\nResult: ${solution.findSingleNumber(demo)}")
    
    // State machine demonstration
    println("\n" + "=".repeat(70))
    println("STATE MACHINE VISUALIZATION")
    println("=".repeat(70))
    
    println("\nProcessing [2, 2, 3, 2] step by step:")
    println("─".repeat(50))
    
    var ones = 0
    var twos = 0
    println("Initial: ones=${ones. toString(2).padStart(3, '0')}, twos=${twos.toString(2).padStart(3, '0')}")
    
    for ((index, num) in demo.withIndex()) {
        val oldOnes = ones
        val oldTwos = twos
        
        twos = twos or (ones and num)
        ones = ones xor num
        val threes = ones and twos
        ones = ones and threes.inv()
        twos = twos and threes.inv()
        
        println("\nStep ${index + 1}: Process $num (${num.toString(2).padStart(3, '0')})")
        println("  Before: ones=${oldOnes.toString(2).padStart(3, '0')}, twos=${oldTwos. toString(2).padStart(3, '0')}")
        println("  After:  ones=${ones.toString(2).padStart(3, '0')}, twos=${twos.toString(2).padStart(3, '0')}")
    }
    
    println("\nFinal result: ones = ${ones.toString(2).padStart(3, '0')} = $ones")
    
    // Compare all approaches
    println("\n" + "=".repeat(70))
    println("APPROACH COMPARISON")
    println("=".repeat(70))
    
    val testArray = intArrayOf(2, 2, 3, 2)
    
    println("\nTesting array: ${testArray.contentToString()}")
    
    println("\n1. State Machine (Optimal):")
    var start = System.nanoTime()
    val r1 = solution.findSingleNumber(testArray)
    var elapsed = System.nanoTime() - start
    println("   Result: $r1")
    println("   Time: O(n), Space: O(1) ✓✓")
    println("   Execution:  ${elapsed}ns")
    
    println("\n2. Bit Counting:")
    start = System.nanoTime()
    val r2 = solution.findSingleNumberBitCount(testArray)
    elapsed = System.nanoTime() - start
    println("   Result: $r2")
    println("   Time: O(32n), Space: O(1) ✓")
    println("   Execution:  ${elapsed}ns")
    
    println("\n3. HashMap:")
    start = System.nanoTime()
    val r3 = solution.findSingleNumberHashMap(testArray)
    elapsed = System.nanoTime() - start
    println("   Result: $r3")
    println("   Time: O(n), Space: O(n) ❌")
    println("   Execution: ${elapsed}ns")
    
    println("\n4. Mathematical:")
    start = System. nanoTime()
    val r4 = solution.findSingleNumberMath(testArray)
    elapsed = System.nanoTime() - start
    println("   Result: $r4")
    println("   Time: O(n), Space: O(n) ❌")
    println("   Execution: ${elapsed}ns")
    
    println("\nConclusion: State Machine is the optimal solution!  🏆")
}
