/**
 * ============================================================================
 * PROBLEM:  Single Element in a Sorted Array
 * DIFFICULTY: Medium
 * CATEGORY: Searching - Binary Search 1D
 * LEETCODE:  #540
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * You are given a sorted array consisting of only integers where every element 
 * appears exactly twice, except for one element which appears exactly once.
 * 
 * Return the single element that appears only once. 
 * 
 * Your solution must run in O(log n) time and O(1) space.
 * 
 * INPUT FORMAT:
 * - A sorted array:  arr = [1, 1, 2, 3, 3, 4, 4, 8, 8]
 * 
 * OUTPUT FORMAT:
 * - The single non-duplicate element:  2
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - 0 <= arr[i] <= 10^5
 * - Array size is always odd (since one element appears once, others twice)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * In a normal case where all elements appear twice: 
 * - First occurrence is at EVEN index:  0, 2, 4, 6... 
 * - Second occurrence is at ODD index: 1, 3, 5, 7... 
 * 
 * Example: [1, 1, 2, 2, 3, 3]
 * Indices:   0  1  2  3  4  5
 *           ↑  ↑  ↑  ↑  ↑  ↑
 *         even odd even odd even odd
 * 
 * After the single element appears, this pattern BREAKS! 
 * - Before single:  pairs at (even, odd)
 * - After single: pairs at (odd, even)
 * 
 * Example with single element: 
 * arr = [1, 1, 2, 3, 3, 4, 4]
 * Idx =  0  1  2  3  4  5  6
 *        ↑  ↑  ↑  ↑  ↑  ↑  ↑
 *      even odd SINGLE odd even odd even
 * 
 * Before index 2:  pairs at (0,1)
 * At index 2: single element
 * After index 2: pairs at (3,4), (5,6) - pattern shifted!
 * 
 * KEY INSIGHT:
 * Use binary search to find where the pattern changes.
 * 
 * CHECKING THE PATTERN:
 * At index i (even):
 * - If arr[i] == arr[i+1]:  Single is on the RIGHT (pattern not broken yet)
 * - If arr[i] != arr[i+1]: Single is on the LEFT (pattern already broken)
 * 
 * At index i (odd):
 * - If arr[i] == arr[i-1]: Single is on the RIGHT (pattern not broken yet)
 * - If arr[i] != arr[i-1]: Single is on the LEFT (pattern already broken)
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, right = n-1
 * 2. While left < right:
 *    a. Calculate mid
 *    b.  Ensure mid is even (if odd, make it mid-1)
 *    c. If arr[mid] == arr[mid+1]: 
 *       - Pattern holds, single is on right
 *       - left = mid + 2
 *    d. Else:
 *       - Pattern broken, single is on left (or at mid)
 *       - right = mid
 * 3. Return arr[left]
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 1, 2, 3, 3, 4, 4, 8, 8]
 * 
 * Step 1: Check mid = 4 (even)
 *         arr[4] = 3, arr[5] = 4
 *         3 != 4, pattern broken!  Single is on left
 *         right = 4
 * 
 * Step 2: Check mid = 2 (even)
 *         arr[2] = 2, arr[3] = 3
 *         2 != 3, pattern broken! Single is on left
 *         right = 2
 * 
 * Step 3: left == right = 2
 *         Found single at index 2!
 * 
 * OUTPUT: arr[2] = 2 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log n)
 * - Binary search divides search space by 2 each iteration
 * - Maximum iterations: log₂(n)
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only constant extra space for pointers
 * - No recursion or additional structures
 * 
 * COMPARISON: 
 * - XOR approach: O(n) time, O(1) space - Linear scan
 * - Binary search:  O(log n) time, O(1) space - Much faster!
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class SingleNonDuplicate {
    
    /**
     * Find the single non-duplicate element using binary search
     * 
     * @param arr Sorted array where all elements appear twice except one
     * @return The single element
     */
    fun singleNonDuplicate(arr: IntArray): Int {
        var left = 0
        var right = arr.size - 1
        
        while (left < right) {
            var mid = left + (right - left) / 2
            
            // Ensure mid is even
            // We always want to check pairs starting at even index
            if (mid % 2 == 1) {
                mid--
            }
            
            // Check if the pair pattern holds
            if (arr[mid] == arr[mid + 1]) {
                // Pattern holds:  pairs are (even, odd)
                // Single element is on the right
                left = mid + 2
            } else {
                // Pattern broken: single element is on the left or at mid
                right = mid
            }
        }
        
        // When left == right, we've found the single element
        return arr[left]
    }
    
    /**
     * Alternative approach:  Check using XOR of index
     * More elegant but requires understanding XOR properties
     */
    fun singleNonDuplicateXOR(arr: IntArray): Int {
        var left = 0
        var right = arr.size - 1
        
        while (left < right) {
            val mid = left + (right - left) / 2
            
            // XOR with 1 flips the last bit: 
            // Even index ^ 1 = index + 1 (pair partner on right)
            // Odd index ^ 1 = index - 1 (pair partner on left)
            if (arr[mid] == arr[mid xor 1]) {
                // Pattern holds, single is on right
                left = mid + 1
            } else {
                // Pattern broken, single is on left
                right = mid
            }
        }
        
        return arr[left]
    }
    
    /**
     * Brute force approach using XOR (for comparison)
     * O(n) time complexity
     */
    fun singleNonDuplicateLinear(arr: IntArray): Int {
        var result = 0
        for (num in arr) {
            result = result xor num
        }
        return result
    }
    
    /**
     * Alternative:  Check neighbors explicitly
     */
    fun singleNonDuplicateExplicit(arr: IntArray): Int {
        val n = arr.size
        
        // Edge cases
        if (n == 1) return arr[0]
        if (arr[0] != arr[1]) return arr[0]
        if (arr[n - 1] != arr[n - 2]) return arr[n - 1]
        
        var left = 1
        var right = n - 2
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            // Check if mid is the single element
            if (arr[mid] != arr[mid - 1] && arr[mid] != arr[mid + 1]) {
                return arr[mid]
            }
            
            // Determine which side to search
            // Check if we're in the left half (pattern not broken)
            val isLeftHalf = if (mid % 2 == 0) {
                arr[mid] == arr[mid + 1]
            } else {
                arr[mid] == arr[mid - 1]
            }
            
            if (isLeftHalf) {
                // Pattern holds, search right
                left = mid + 1
            } else {
                // Pattern broken, search left
                right = mid - 1
            }
        }
        
        return arr[left]
    }
    
    /**
     * Find index of single element (instead of value)
     */
    fun findSingleIndex(arr: IntArray): Int {
        var left = 0
        var right = arr.size - 1
        
        while (left < right) {
            var mid = left + (right - left) / 2
            
            if (mid % 2 == 1) {
                mid--
            }
            
            if (arr[mid] == arr[mid + 1]) {
                left = mid + 2
            } else {
                right = mid
            }
        }
        
        return left
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Single element in middle
 * INPUT: arr = [1, 1, 2, 3, 3, 4, 4, 8, 8]
 * Indices:      0  1  2  3  4  5  6  7  8
 * 
 * Iteration 1:
 * - left = 0, right = 8, mid = 4
 * - mid is even
 * - arr[4] = 3, arr[5] = 4
 * - 3 != 4, pattern broken
 * - right = 4
 * 
 * Iteration 2:
 * - left = 0, right = 4, mid = 2
 * - mid is even
 * - arr[2] = 2, arr[3] = 3
 * - 2 != 3, pattern broken
 * - right = 2
 * 
 * Iteration 3:
 * - left = 0, right = 2, mid = 1
 * - mid is odd, make it 0
 * - arr[0] = 1, arr[1] = 1
 * - 1 == 1, pattern holds
 * - left = 2
 * 
 * left == right = 2, exit
 * OUTPUT: arr[2] = 2 ✓
 * 
 * ---
 * 
 * Example 2: Single element at start
 * INPUT:  arr = [1, 2, 2, 3, 3, 4, 4]
 * 
 * Binary search will find that pattern breaks immediately
 * OUTPUT: 1 ✓
 * 
 * ---
 * 
 * Example 3: Single element at end
 * INPUT:  arr = [1, 1, 2, 2, 3, 3, 4]
 * 
 * Pattern holds until the end
 * OUTPUT: 4 ✓
 * 
 * ---
 * 
 * Example 4: Single element only
 * INPUT: arr = [1]
 * 
 * Array has only one element
 * OUTPUT: 1 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Single element only:  [1] → 1
 * 2. Single at start: [1,2,2,3,3] → 1
 * 3. Single at end: [1,1,2,2,3] → 3
 * 4. Single in middle: [1,1,2,3,3] → 2
 * 5. Two pairs + single: [1,1,2,3,3] → 2
 * 6. Large array: Works efficiently O(log n)
 * 7. All same pairs except one:  Handles correctly
 * 8. Zero values: [0,0,1,2,2] → 1
 * 9.  Negative numbers: Works correctly
 * 10. Large values: Works correctly
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not ensuring mid is even
 * ❌ Directly using mid without adjustment
 * ✅ if (mid % 2 == 1) mid--
 * 
 * Why?  We always want to check pairs starting at even index
 * 
 * MISTAKE 2: Wrong pointer update
 * ❌ left = mid + 1 when pattern holds
 * ✅ left = mid + 2 (skip the entire pair)
 * 
 * MISTAKE 3: Using XOR for entire array
 * ❌ XOR works but is O(n), not using sorted property
 * ✅ Binary search uses sorted property for O(log n)
 * 
 * MISTAKE 4: Not understanding the pattern shift
 * Before single: pairs at (even, odd) indices
 * After single: pairs at (odd, even) indices
 * This is the KEY insight!
 * 
 * MISTAKE 5: Comparing arr[mid] with both neighbors
 * ❌ Unnecessarily complex, can cause index errors
 * ✅ Just ensure mid is even and check mid vs mid+1
 * 
 * ============================================================================
 * UNDERSTANDING THE PATTERN
 * ============================================================================
 * 
 * NORMAL PATTERN (all pairs):
 * [1, 1, 2, 2, 3, 3, 4, 4]
 *  0  1  2  3  4  5  6  7
 *  E  O  E  O  E  O  E  O
 * 
 * First of pair: always at EVEN index
 * Second of pair: always at ODD index
 * 
 * WITH SINGLE ELEMENT:
 * [1, 1, 2, 3, 3, 4, 4]
 *  0  1  2  3  4  5  6
 *  E  O  S  O  E  O  E
 * 
 * Before single (indices 0-1): pair at (E, O) ✓
 * Single at index 2
 * After single (indices 3-6): pairs at (O, E), (O, E) - SHIFTED!
 * 
 * HOW TO DETECT: 
 * At even index i:
 * - If arr[i] == arr[i+1]: Pattern OK, single is ahead
 * - If arr[i] != arr[i+1]:  Pattern broken, single is behind or here
 * 
 * ============================================================================
 * XOR APPROACH EXPLANATION
 * ============================================================================
 * 
 * XOR PROPERTY:
 * - n ^ n = 0 (any number XOR itself is 0)
 * - n ^ 0 = n (any number XOR 0 is itself)
 * - XOR is commutative and associative
 * 
 * EXAMPLE: 
 * arr = [1, 1, 2, 3, 3]
 * result = 1 ^ 1 ^ 2 ^ 3 ^ 3
 *        = 0 ^ 2 ^ 0
 *        = 2 ✓
 * 
 * All pairs cancel out, leaving only the single element! 
 * 
 * WHY BINARY SEARCH IS BETTER:
 * - XOR:  O(n) time - must check every element
 * - Binary Search: O(log n) - uses sorted property
 * - For large arrays, binary search is MUCH faster
 * 
 * ============================================================================
 * INDEX XOR TRICK
 * ============================================================================
 * 
 * CLEVER OBSERVATION:
 * - Even ^ 1 = Even + 1 (next odd)
 * - Odd ^ 1 = Odd - 1 (previous even)
 * 
 * Examples:
 * - 0 ^ 1 = 1 (0 XOR 1 = 1)
 * - 1 ^ 1 = 0 (1 XOR 1 = 0)
 * - 2 ^ 1 = 3 (10 XOR 01 = 11 = 3)
 * - 3 ^ 1 = 2 (11 XOR 01 = 10 = 2)
 * 
 * So mid ^ 1 gives us the pair partner index!
 * - If mid is even:  mid ^ 1 = mid + 1 (right partner)
 * - If mid is odd: mid ^ 1 = mid - 1 (left partner)
 * 
 * This is used in the XOR approach to check pair in one line!
 * 
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. DATA INTEGRITY: 
 *    Find corrupted entry in sorted log where all entries should be paired
 * 
 * 2. NETWORK PACKETS:
 *    Find missing packet in sequence where packets are duplicated
 * 
 * 3. DATABASE REPLICATION:
 *    Find record that wasn't replicated (all others duplicated)
 * 
 * 4. ERROR DETECTION:
 *    In systems where data is stored twice for redundancy
 * 
 * 5. BIT MANIPULATION PROBLEMS:
 *    Pattern recognition in sorted bit sequences
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - Single number (unsorted, use XOR)
 * - Single number II (appears thrice)
 * - Single number III (two singles)
 * - Find duplicate in array
 * - Missing number in sequence
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val snd = SingleNonDuplicate()
    
    println("=== Testing Single Non-Duplicate Element ===\n")
    
    // Test 1: Single in middle
    val arr1 = intArrayOf(1, 1, 2, 3, 3, 4, 4, 8, 8)
    println("Test 1: arr = ${arr1.contentToString()}")
    println("Single element:  ${snd.singleNonDuplicate(arr1)}")
    println("Expected: 2\n")
    
    // Test 2: Single at start
    val arr2 = intArrayOf(1, 2, 2, 3, 3, 4, 4)
    println("Test 2: Single at start - ${arr2.contentToString()}")
    println("Single element: ${snd.singleNonDuplicate(arr2)}")
    println("Expected: 1\n")
    
    // Test 3: Single at end
    val arr3 = intArrayOf(1, 1, 2, 2, 3, 3, 4)
    println("Test 3: Single at end - ${arr3.contentToString()}")
    println("Single element: ${snd.singleNonDuplicate(arr3)}")
    println("Expected: 4\n")
    
    // Test 4: Single element only
    val arr4 = intArrayOf(1)
    println("Test 4: Single element [1]")
    println("Single element:  ${snd.singleNonDuplicate(arr4)}")
    println("Expected: 1\n")
    
    // Test 5: Three pairs + single
    val arr5 = intArrayOf(1, 1, 2, 2, 3, 4, 4)
    println("Test 5: arr = ${arr5.contentToString()}")
    println("Single element: ${snd.singleNonDuplicate(arr5)}")
    println("Expected:  3\n")
    
    // Test 6: Using XOR method
    println("Test 6: XOR method for ${arr1.contentToString()}")
    println("Single element: ${snd.singleNonDuplicateXOR(arr1)}")
    println("Expected: 2\n")
    
    // Test 7: Find index
    println("Test 7: Find index in ${arr1.contentToString()}")
    val index = snd.findSingleIndex(arr1)
    println("Single at index: $index (value: ${arr1[index]})")
    println("Expected: index 2, value 2\n")
    
    // Test 8: With zero
    val arr8 = intArrayOf(0, 0, 1, 2, 2, 3, 3)
    println("Test 8: With zero - ${arr8.contentToString()}")
    println("Single element: ${snd.singleNonDuplicate(arr8)}")
    println("Expected:  1\n")
    
    // Test 9: Negative numbers
    val arr9 = intArrayOf(-3, -3, -1, -1, 0, 1, 1)
    println("Test 9: With negatives - ${arr9.contentToString()}")
    println("Single element: ${snd.singleNonDuplicate(arr9)}")
    println("Expected:  0\n")
    
    // Test 10: Linear XOR approach (comparison)
    println("Test 10: Linear XOR approach for ${arr1.contentToString()}")
    println("Single element: ${snd. singleNonDuplicateLinear(arr1)}")
    println("Expected: 2")
    println("(Note: Linear is O(n), binary search is O(log n))\n")
    
    // Test 11: Explicit method
    println("Test 11: Explicit method for ${arr1.contentToString()}")
    println("Single element: ${snd. singleNonDuplicateExplicit(arr1)}")
    println("Expected: 2\n")
    
    // Test 12: Performance demonstration
    println("Test 12: Performance comparison")
    val largeArr = IntArray(1000001) { i ->
        if (i < 500000) i / 2
        else if (i == 500000) 999999 // Single element
        else (i - 1) / 2
    }.sortedArray()
    
    var startTime = System.nanoTime()
    val resultBinary = snd.singleNonDuplicate(largeArr)
    var endTime = System.nanoTime()
    val timeBinary = (endTime - startTime) / 1000
    
    startTime = System.nanoTime()
    val resultLinear = snd.singleNonDuplicateLinear(largeArr)
    endTime = System. nanoTime()
    val timeLinear = (endTime - startTime) / 1000
    
    println("Array size: 1,000,001")
    println("Binary search:  $timeBinary microseconds")
    println("Linear XOR: $timeLinear microseconds")
    println("Binary search is ${timeLinear / timeBinary}x faster!")
    println("Both found: $resultBinary\n")
}
