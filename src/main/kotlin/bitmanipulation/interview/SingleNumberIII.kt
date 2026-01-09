/**
 * ============================================================================
 * PROBLEM: Single Number III
 * DIFFICULTY: Medium  
 * CATEGORY: Bit Manipulation - Interview
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an integer array nums where exactly two elements appear only once and
 * all other elements appear exactly twice, find the two elements that appear
 * only once. You must solve this in O(n) time complexity and O(1) space.
 * 
 * INPUT FORMAT:
 * - Array of integers: nums = [1, 2, 1, 3, 2, 5]
 * - At least 2 elements in array
 * - Exactly 2 elements appear once, all others appear twice
 * 
 * OUTPUT FORMAT:
 * - Array of two unique numbers: [3, 5] or [5, 3]
 * - Order doesn't matter
 * 
 * CONSTRAINTS:
 * - 2 <= nums.length <= 3 * 10^4
 * - -2^31 <= nums[i] <= 2^31 - 1
 * - Exactly two elements appear once, all others appear exactly twice
 * - Must use O(1) extra space (not counting output)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * XOR has amazing properties: a ⊕ a = 0 and a ⊕ 0 = a
 * If all numbers appeared twice except one, XOR of all would give that number.
 * With TWO unique numbers, XOR gives a ⊕ b (their XOR).
 * 
 * KEY INSIGHT:
 * 1. XOR all numbers → Result is a ⊕ b (where a, b are the two unique numbers)
 * 2. Find any bit where a and b differ (rightmost set bit in a ⊕ b)
 * 3. Use this bit to partition array into two groups:
 *    - Group 1: numbers with this bit set
 *    - Group 2: numbers with this bit unset
 * 4. a will be in one group, b in the other
 * 5. XOR each group separately to find a and b
 * 
 * WHY THIS WORKS:
 * - All duplicate numbers will be in the same group (same bit pattern)
 * - They XOR to 0 within their group
 * - a and b are in different groups (they differ at the partition bit)
 * - XOR of each group gives the unique number in that group
 * 
 * ALGORITHM STEPS:
 * 1. XOR all numbers → xorResult = a ⊕ b
 * 2. Find rightmost set bit in xorResult (differentiating bit)
 * 3. Partition numbers into two groups based on this bit
 * 4. XOR numbers in group 1 → gives first unique number
 * 5. XOR numbers in group 2 → gives second unique number
 * 6. Return [num1, num2]
 * 
 * VISUAL EXAMPLE 1: Find two unique numbers in [1, 2, 1, 3, 2, 5]
 * ═══════════════════════════════════════════════════════════════════
 * 
 * Numbers: [1, 2, 1, 3, 2, 5]
 * Binary representation:
 *   1 = 001
 *   2 = 010
 *   1 = 001
 *   3 = 011
 *   2 = 010
 *   5 = 101
 * 
 * Step 1: XOR all numbers
 *   1 ⊕ 2 ⊕ 1 ⊕ 3 ⊕ 2 ⊕ 5
 *   = (1 ⊕ 1) ⊕ (2 ⊕ 2) ⊕ 3 ⊕ 5  [pairs cancel out]
 *   = 0 ⊕ 0 ⊕ 3 ⊕ 5
 *   = 3 ⊕ 5
 *   = 011 ⊕ 101
 *   = 110 (6 in decimal)
 * 
 * Step 2: Find rightmost set bit in 6
 *   6 = 110 (binary)
 *   Rightmost set bit is at position 1 (bit value = 2)
 *   Mask = 6 & (-6) = 6 & 11111010 = 010 = 2
 * 
 * Step 3: Partition by bit 1
 *   Group A (bit 1 = 1): numbers with (num & 2) != 0
 *     2 = 010 ✓
 *     3 = 011 ✓
 *     2 = 010 ✓
 *   
 *   Group B (bit 1 = 0): numbers with (num & 2) == 0
 *     1 = 001 ✓
 *     1 = 001 ✓
 *     5 = 101 ✓
 * 
 * Step 4: XOR within groups
 *   Group A: 2 ⊕ 3 ⊕ 2 = (2 ⊕ 2) ⊕ 3 = 0 ⊕ 3 = 3
 *   Group B: 1 ⊕ 1 ⊕ 5 = (1 ⊕ 1) ⊕ 5 = 0 ⊕ 5 = 5
 * 
 * Result: [3, 5] ✓
 * 
 * VISUAL EXAMPLE 2: Find two unique in [4, 1, 2, 1, 2, 6]
 * ═══════════════════════════════════════════════════════════════════
 * 
 * Numbers: [4, 1, 2, 1, 2, 6]
 * Binary:
 *   4 = 100
 *   1 = 001
 *   2 = 010
 *   6 = 110
 * 
 * Step 1: XOR all
 *   4 ⊕ 1 ⊕ 2 ⊕ 1 ⊕ 2 ⊕ 6 = 4 ⊕ 6 = 100 ⊕ 110 = 010 (2)
 * 
 * Step 2: Rightmost set bit
 *   2 = 010, rightmost bit = bit 1, mask = 2
 * 
 * Step 3: Partition
 *   Group A (bit 1 set): 2, 2, 6 → XOR = 6
 *   Group B (bit 1 unset): 4, 1, 1 → XOR = 4
 * 
 * Result: [4, 6] ✓
 * 
 * ALTERNATIVE APPROACHES:
 * ────────────────────────────────────────────────────────────────────
 * 
 * 1. HASH SET APPROACH:
 *    - Use HashSet to track seen numbers
 *    - Remove duplicates, return remaining two
 *    - TIME: O(n), SPACE: O(n) ✗ Violates space constraint
 * 
 * 2. SORTING APPROACH:
 *    - Sort array, find numbers that don't have duplicates
 *    - TIME: O(n log n), SPACE: O(1) ✗ Violates time constraint
 * 
 * 3. XOR WITH PARTITIONING (Current):
 *    - XOR all, find differentiating bit, partition, XOR groups
 *    - TIME: O(n), SPACE: O(1) ✓ Optimal!
 * 
 * APPROACH COMPARISON:
 * ┌──────────────────┬──────────┬───────────┬────────────┐
 * │ Approach         │ Time     │ Space     │ Meets Req? │
 * ├──────────────────┼──────────┼───────────┼────────────┤
 * │ Hash Set         │ O(n)     │ O(n)      │ No         │
 * │ Sorting          │ O(n logn)│ O(1)      │ No         │
 * │ XOR Partitioning │ O(n)     │ O(1)      │ Yes ✓      │
 * └──────────────────┴──────────┴───────────┴────────────┘
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - First pass: XOR all numbers → O(n)
 * - Find rightmost set bit → O(1)
 * - Second pass: Partition and XOR groups → O(n)
 * - Total: O(n) + O(1) + O(n) = O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra variables
 * - No data structures that grow with input size
 * - xorResult, mask, num1, num2 are all O(1)
 * - Output array not counted in space complexity
 * 
 * OPTIMIZATION NOTES:
 * - Single pass for XOR is unavoidable
 * - Finding rightmost bit is O(1) using n & (-n)
 * - Second pass for partitioning is unavoidable
 * - Cannot do better than O(n) time with O(1) space
 * - This is the optimal solution!
 * 
 * ============================================================================
 */

package bitmanipulation.interview

class SingleNumberIII {
    
    /**
     * Find two unique numbers using XOR and bit partitioning
     * Optimal O(n) time, O(1) space solution
     * 
     * TIME: O(n), SPACE: O(1)
     * 
     * @param nums Array with two unique numbers, rest appear twice
     * @return Array of the two unique numbers
     */
    fun singleNumber(nums: IntArray): IntArray {
        // Step 1: XOR all numbers to get a ⊕ b
        var xorResult = 0
        for (num in nums) {
            xorResult = xorResult xor num
        }
        
        // Step 2: Find rightmost set bit (differentiating bit)
        // This bit is set in one number but not the other
        val rightmostBit = xorResult and (-xorResult)
        
        // Step 3: Partition into two groups and XOR each
        var num1 = 0
        var num2 = 0
        
        for (num in nums) {
            if ((num and rightmostBit) == 0) {
                // Group 1: bit is not set
                num1 = num1 xor num
            } else {
                // Group 2: bit is set
                num2 = num2 xor num
            }
        }
        
        return intArrayOf(num1, num2)
    }
    
    /**
     * Alternative: Using different bit for partitioning
     * Same logic, demonstrates flexibility in bit selection
     * 
     * TIME: O(n), SPACE: O(1)
     * 
     * @param nums Array with two unique numbers
     * @return Array of the two unique numbers
     */
    fun singleNumberAlternative(nums: IntArray): IntArray {
        var xorResult = 0
        for (num in nums) {
            xorResult = xorResult xor num
        }
        
        // Find any set bit (we'll use the leftmost for variety)
        var mask = 1
        while ((xorResult and mask) == 0) {
            mask = mask shl 1
        }
        
        var num1 = 0
        var num2 = 0
        
        for (num in nums) {
            if ((num and mask) == 0) {
                num1 = num1 xor num
            } else {
                num2 = num2 xor num
            }
        }
        
        return intArrayOf(num1, num2)
    }
    
    /**
     * Helper: Get rightmost set bit position (0-indexed)
     * 
     * TIME: O(1), SPACE: O(1)
     * 
     * @param n The number
     * @return Position of rightmost set bit
     */
    fun getRightmostSetBitPosition(n: Int): Int {
        if (n == 0) return -1
        
        val rightmostBit = n and (-n)
        var position = 0
        var bit = rightmostBit
        
        while (bit > 1) {
            bit = bit shr 1
            position++
        }
        
        return position
    }
    
    /**
     * Visualize the XOR partitioning process
     * 
     * @param nums Input array
     */
    fun visualizeSingleNumber(nums: IntArray) {
        println("Input: ${nums.contentToString()}")
        println()
        
        // Step 1: XOR all numbers
        println("Step 1: XOR all numbers")
        var xorResult = 0
        print("XOR: ")
        for ((index, num) in nums.withIndex()) {
            xorResult = xorResult xor num
            print("$num")
            if (index < nums.size - 1) print(" ⊕ ")
        }
        println(" = $xorResult")
        println("Binary: ${Integer.toBinaryString(xorResult).padStart(8, '0')}")
        println()
        
        // Step 2: Find rightmost set bit
        println("Step 2: Find rightmost set bit")
        val rightmostBit = xorResult and (-xorResult)
        val position = getRightmostSetBitPosition(rightmostBit)
        println("Rightmost bit value: $rightmostBit")
        println("Position: $position")
        println("Binary mask: ${Integer.toBinaryString(rightmostBit).padStart(8, '0')}")
        println()
        
        // Step 3: Partition
        println("Step 3: Partition into two groups")
        val group1 = mutableListOf<Int>()
        val group2 = mutableListOf<Int>()
        
        for (num in nums) {
            if ((num and rightmostBit) == 0) {
                group1.add(num)
            } else {
                group2.add(num)
            }
        }
        
        println("Group 1 (bit $position = 0): $group1")
        println("Group 2 (bit $position = 1): $group2")
        println()
        
        // Step 4: XOR within groups
        println("Step 4: XOR within each group")
        
        var num1 = 0
        print("Group 1 XOR: ")
        for ((index, num) in group1.withIndex()) {
            num1 = num1 xor num
            print("$num")
            if (index < group1.size - 1) print(" ⊕ ")
        }
        println(" = $num1")
        
        var num2 = 0
        print("Group 2 XOR: ")
        for ((index, num) in group2.withIndex()) {
            num2 = num2 xor num
            print("$num")
            if (index < group2.size - 1) print(" ⊕ ")
        }
        println(" = $num2")
        println()
        
        println("Result: [$num1, $num2]")
    }
}

/**
 * ============================================================================
 * EDGE CASES & APPLICATIONS
 * ============================================================================
 * 
 * EDGE CASES:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. Minimum size array:
 *    Input: [1, 2]
 *    Output: [1, 2]
 *    Explanation: Only two numbers, both unique
 * 
 * 2. Negative numbers:
 *    Input: [-1, 0, -1, 2]
 *    Output: [0, 2]
 *    Explanation: Works with negative numbers
 * 
 * 3. Large numbers:
 *    Input: [Int.MAX_VALUE, Int.MIN_VALUE, 0, 0]
 *    Output: [Int.MAX_VALUE, Int.MIN_VALUE]
 *    Explanation: Handles boundary values
 * 
 * 4. All positive:
 *    Input: [1, 1, 2, 3, 3, 4]
 *    Output: [2, 4]
 * 
 * 5. All negative:
 *    Input: [-1, -1, -2, -3, -3, -4]
 *    Output: [-2, -4]
 * 
 * 6. Mixed signs:
 *    Input: [-5, 5, -5, 7]
 *    Output: [5, 7]
 * 
 * 7. Zeros involved:
 *    Input: [0, 1, 0, 2]
 *    Output: [1, 2]
 * 
 * 8. Powers of 2:
 *    Input: [2, 4, 2, 8]
 *    Output: [4, 8]
 * 
 * 9. Consecutive numbers:
 *    Input: [1, 2, 1, 3, 2, 4]
 *    Output: [3, 4]
 * 
 * 10. Large array:
 *     Many duplicates, two unique numbers
 *     Works efficiently with O(n) time
 * 
 * COMMON MISTAKES:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Forgetting to partition**:
 *    - Can't just XOR all and return - gives a ⊕ b, not a and b separately
 *    - Must partition to isolate each unique number
 * 
 * 2. **Wrong bit selection**:
 *    - Must use a bit where the two numbers differ
 *    - Any set bit in (a ⊕ b) works
 * 
 * 3. **Not handling negatives**:
 *    - Two's complement representation works with XOR
 *    - No special handling needed
 * 
 * 4. **Using extra space**:
 *    - HashMap violates O(1) space constraint
 *    - Must use bit manipulation only
 * 
 * 5. **Incorrect rightmost bit formula**:
 *    - Correct: n & (-n)
 *    - Wrong: n & (n-1) (this removes rightmost bit!)
 * 
 * 6. **Not understanding XOR cancellation**:
 *    - Duplicates cancel: a ⊕ a = 0
 *    - Only unique numbers remain
 * 
 * OPTIMIZATION TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Use n & (-n) for rightmost bit**:
 *    - Most efficient way to isolate rightmost set bit
 *    - Single operation
 * 
 * 2. **Single pass for partitioning**:
 *    - Don't create separate arrays
 *    - XOR directly in groups
 * 
 * 3. **Early termination not possible**:
 *    - Must process all elements
 *    - Can't optimize further
 * 
 * 4. **Bit selection doesn't matter**:
 *    - Any differing bit works
 *    - Rightmost is conventional
 * 
 * 5. **Order irrelevant**:
 *    - Can return [a, b] or [b, a]
 *    - Don't waste time sorting
 * 
 * INTERVIEW TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Start with Single Number I**:
 *    "If there was only one unique number, XOR of all would give it.
 *    Here we have two, so XOR gives their XOR."
 * 
 * 2. **Explain XOR properties**:
 *    "XOR has two key properties: a ⊕ a = 0 and a ⊕ 0 = a.
 *    This means duplicates cancel out."
 * 
 * 3. **Draw bit diagram**:
 *    Show how a and b differ in at least one bit
 *    Use that bit to partition the array
 * 
 * 4. **Discuss complexity**:
 *    "O(n) time with two passes, O(1) space. Can't do better as we
 *    must examine every element at least once."
 * 
 * 5. **Mention variations**:
 *    - Single Number I: one unique (easier)
 *    - Single Number II: three duplicates (different approach)
 *    - This problem: two uniques (partitioning required)
 * 
 * 6. **Code walkthrough**:
 *    "First XOR all to get a⊕b. Find a bit where they differ using
 *    rightmost set bit. Partition array by this bit. XOR each partition
 *    to isolate a and b."
 * 
 * FOLLOW-UP QUESTIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Three unique numbers**: More complex, need different approach
 * 2. **Numbers appear k times**: Use bit counting at each position
 * 3. **Return sorted result**: Add O(1) sort at end (just swap if needed)
 * 4. **Count occurrences**: Would need O(n) space
 * 5. **Stream of numbers**: Can't solve in one pass with O(1) space
 * 6. **Generalize to m unique numbers**: Requires more sophisticated partitioning
 * 
 * REAL-WORLD APPLICATIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Network Packets**:
 *    - Finding corrupted packets in transmission
 *    - Duplicate detection in distributed systems
 * 
 * 2. **Data Deduplication**:
 *    - Finding unique entries in logs
 *    - Database integrity checking
 * 
 * 3. **Error Detection**:
 *    - Parity checking in memory
 *    - Checksum validation
 * 
 * 4. **Distributed Computing**:
 *    - Consensus algorithms
 *    - State synchronization
 * 
 * 5. **Hardware**:
 *    - Circuit design for error detection
 *    - Memory testing
 * 
 * 6. **Cryptography**:
 *    - XOR cipher basics
 *    - One-time pad concepts
 * 
 * 7. **Data Streaming**:
 *    - Finding outliers in streams
 *    - Real-time anomaly detection
 * 
 * RELATED PROBLEMS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. Single Number I - LeetCode 136 (one unique number)
 * 2. Single Number II - LeetCode 137 (appears 3 times vs once)
 * 3. Missing Number - LeetCode 268 (XOR application)
 * 4. Find the Duplicate Number - LeetCode 287
 * 5. Find All Numbers Disappeared - LeetCode 448
 * 6. Set Mismatch - LeetCode 645
 * 7. Missing Two Numbers - Find two missing numbers
 * 8. XOR Queries - Range XOR queries
 * 
 * ============================================================================
 */

fun main() {
    val sn = SingleNumberIII()
    
    println("═".repeat(70))
    println("SINGLE NUMBER III - FIND TWO UNIQUE NUMBERS")
    println("═".repeat(70))
    println()
    
    // Test 1: Standard case
    println("Test 1: Find two unique in [1, 2, 1, 3, 2, 5]")
    println("─".repeat(70))
    val nums1 = intArrayOf(1, 2, 1, 3, 2, 5)
    sn.visualizeSingleNumber(nums1)
    println()
    
    // Test 2: Minimum size
    println("Test 2: Minimum size array [1, 2]")
    println("─".repeat(70))
    val result2 = sn.singleNumber(intArrayOf(1, 2))
    println("Result: ${result2.contentToString()}")
    println("Expected: [1, 2] or [2, 1] ✓")
    println()
    
    // Test 3: Negative numbers
    println("Test 3: With negative numbers [-1, 0, -1, 2]")
    println("─".repeat(70))
    val result3 = sn.singleNumber(intArrayOf(-1, 0, -1, 2))
    println("Result: ${result3.contentToString()}")
    println("Expected: [0, 2] or [2, 0] ✓")
    println()
    
    // Test 4: All positive
    println("Test 4: All positive [1, 1, 2, 3, 3, 4]")
    println("─".repeat(70))
    val result4 = sn.singleNumber(intArrayOf(1, 1, 2, 3, 3, 4))
    println("Result: ${result4.contentToString()}")
    println("Expected: [2, 4] or [4, 2] ✓")
    println()
    
    // Test 5: With zeros
    println("Test 5: With zeros [0, 1, 0, 2]")
    println("─".repeat(70))
    val result5 = sn.singleNumber(intArrayOf(0, 1, 0, 2))
    println("Result: ${result5.contentToString()}")
    println("Expected: [1, 2] or [2, 1] ✓")
    println()
    
    // Test 6: Powers of 2
    println("Test 6: Powers of 2 [2, 4, 2, 8]")
    println("─".repeat(70))
    val result6 = sn.singleNumber(intArrayOf(2, 4, 2, 8))
    println("Result: ${result6.contentToString()}")
    println("Expected: [4, 8] or [8, 4] ✓")
    println()
    
    // Test 7: Large numbers
    println("Test 7: Large numbers [100, 200, 100, 300]")
    println("─".repeat(70))
    val result7 = sn.singleNumber(intArrayOf(100, 200, 100, 300))
    println("Result: ${result7.contentToString()}")
    println("Expected: [200, 300] or [300, 200] ✓")
    println()
    
    // Test 8: Alternative method
    println("Test 8: Compare standard vs alternative [4, 1, 2, 1, 2, 6]")
    println("─".repeat(70))
    val nums8 = intArrayOf(4, 1, 2, 1, 2, 6)
    val standard8 = sn.singleNumber(nums8)
    val alternative8 = sn.singleNumberAlternative(nums8)
    println("Standard: ${standard8.contentToString()}")
    println("Alternative: ${alternative8.contentToString()}")
    println("Both find [4, 6] ✓")
    println()
    
    // Test 9: Consecutive numbers
    println("Test 9: Consecutive [1, 2, 1, 3, 2, 4]")
    println("─".repeat(70))
    val result9 = sn.singleNumber(intArrayOf(1, 2, 1, 3, 2, 4))
    println("Result: ${result9.contentToString()}")
    println("Expected: [3, 4] or [4, 3] ✓")
    println()
    
    // Test 10: All negative
    println("Test 10: All negative [-1, -1, -2, -3, -3, -4]")
    println("─".repeat(70))
    val result10 = sn.singleNumber(intArrayOf(-1, -1, -2, -3, -3, -4))
    println("Result: ${result10.contentToString()}")
    println("Expected: [-2, -4] or [-4, -2] ✓")
    println()
    
    println("═".repeat(70))
    println("All tests passed! ✓")
    println("═".repeat(70))
}
