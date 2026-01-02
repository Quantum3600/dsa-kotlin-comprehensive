/**
 * ============================================================================
 * PROBLEM: Reverse Pairs
 * DIFFICULTY: Hard
 * CATEGORY: Arrays, Divide and Conquer, Merge Sort
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an integer array nums, return the number of reverse pairs in the array.
 * A reverse pair is a pair (i, j) where:
 * - 0 <= i < j < nums.length
 * - nums[i] > 2 * nums[j]
 * 
 * INPUT FORMAT:
 * - An array of integers: nums = [1, 3, 2, 3, 1]
 * 
 * OUTPUT FORMAT:
 * - Number of reverse pairs: 2
 *   Pairs: (3, 1) at indices (1, 4) and (3, 1) at indices (3, 4)
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 5 * 10^4
 * - -2^31 <= nums[i] <= 2^31 - 1
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Similar to Count Inversions but with condition nums[i] > 2 * nums[j].
 * Use modified merge sort to count pairs efficiently.
 * 
 * KEY INSIGHT:
 * - Count pairs BEFORE merging (unlike inversions counted DURING merge)
 * - For each element in left half, find how many in right half satisfy condition
 * - Use two pointers since both halves are sorted
 * 
 * ALGORITHM STEPS:
 * 1. Divide array into two halves
 * 2. Recursively count pairs in left half
 * 3. Recursively count pairs in right half
 * 4. Count cross pairs (left[i] > 2 * right[j])
 * 5. Merge the two halves
 * 6. Return total count
 * 
 * VISUAL EXAMPLE:
 * nums = [1, 3, 2, 3, 1]
 * 
 * After sorting halves during merge sort:
 * Left = [1, 3], Right = [1, 2, 3]
 * 
 * Check pairs:
 * i=0 (nums[0]=1): 1 > 2*1? No, 1 > 2*2? No, 1 > 2*3? No
 * i=1 (nums[1]=3): 3 > 2*1? Yes (count=1), 3 > 2*2? No
 * 
 * Total pairs from this merge = 1
 * 
 * COMPLEXITY:
 * Time: O(n log n) - modified merge sort
 * Space: O(n) - for temporary arrays
 * 
 * ============================================================================
 */

package arrays.hard

class ReversePairs {
    
    /**
     * Counts reverse pairs using modified merge sort
     * 
     * @param nums The input array
     * @return Number of reverse pairs
     */
    fun reversePairs(nums: IntArray): Int {
        if (nums.size <= 1) return 0
        return mergeSortAndCount(nums, 0, nums.size - 1)
    }
    
    /**
     * Performs merge sort and counts reverse pairs
     */
    private fun mergeSortAndCount(nums: IntArray, left: Int, right: Int): Int {
        if (left >= right) return 0
        
        val mid = left + (right - left) / 2
        
        // Count pairs in left and right halves
        var count = 0
        count += mergeSortAndCount(nums, left, mid)
        count += mergeSortAndCount(nums, mid + 1, right)
        
        // Count cross pairs before merging
        count += countPairs(nums, left, mid, right)
        
        // Merge the two halves
        merge(nums, left, mid, right)
        
        return count
    }
    
    /**
     * Counts pairs where nums[i] > 2 * nums[j]
     * Both halves are sorted
     */
    private fun countPairs(nums: IntArray, left: Int, mid: Int, right: Int): Int {
        var count = 0
        var j = mid + 1
        
        // For each element in left half
        for (i in left..mid) {
            // Find how many elements in right half satisfy condition
            while (j <= right && nums[i] > 2L * nums[j]) {
                j++
            }
            count += (j - (mid + 1))
        }
        
        return count
    }
    
    /**
     * Merges two sorted halves
     */
    private fun merge(nums: IntArray, left: Int, mid: Int, right: Int) {
        val temp = IntArray(right - left + 1)
        var i = left
        var j = mid + 1
        var k = 0
        
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++]
            } else {
                temp[k++] = nums[j++]
            }
        }
        
        while (i <= mid) temp[k++] = nums[i++]
        while (j <= right) temp[k++] = nums[j++]
        
        // Copy back to original array
        for (idx in temp.indices) {
            nums[left + idx] = temp[idx]
        }
    }
    
    /**
     * Brute force approach - check all pairs
     * Time: O(n²), Space: O(1)
     */
    fun reversePairsBruteForce(nums: IntArray): Int {
        var count = 0
        for (i in nums.indices) {
            for (j in i + 1 until nums.size) {
                if (nums[i] > 2L * nums[j]) {
                    count++
                }
            }
        }
        return count
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single element: [1] → 0 pairs
 * 2. Two elements: [5, 2] → 1 pair (5 > 2*2 = 4, so 5 > 4 is true)
 * 3. Two elements: [5, 1] → 1 pair (5 > 2*1 is true)
 * 4. Sorted array: [1,2,3,4] → 0 pairs
 * 5. Reverse sorted: [4,3,2,1] → Some pairs
 * 6. All same: [2,2,2,2] → 0 pairs
 * 7. Negative numbers: [-5,-2,-1] → Check carefully
 * 
 * APPLICATIONS:
 * 1. Financial analysis (price movements)
 * 2. Network analysis (packet delays)
 * 3. Quality control (defect patterns)
 * 4. Algorithm analysis
 * 5. Data anomaly detection
 * 6. Performance metrics
 * 7. Statistical outlier detection
 * 
 * ============================================================================
 */

fun main() {
    val solution = ReversePairs()
    
    println("=== Reverse Pairs Tests ===\n")
    
    // Test 1: Standard case
    println("Test 1: Standard case")
    val nums1 = intArrayOf(1, 3, 2, 3, 1)
    println("Input: ${nums1.contentToString()}")
    println("Result: ${solution.reversePairs(nums1.clone())}")
    println("Expected: 2\n")
    
    // Test 2: Another case
    println("Test 2: Another case")
    val nums2 = intArrayOf(2, 4, 3, 5, 1)
    println("Input: ${nums2.contentToString()}")
    println("Result: ${solution.reversePairs(nums2.clone())}")
    println("Expected: 3\n")
    
    // Test 3: No pairs
    println("Test 3: No pairs")
    val nums3 = intArrayOf(1, 2, 3, 4)
    println("Input: ${nums3.contentToString()}")
    println("Result: ${solution.reversePairs(nums3.clone())}")
    println("Expected: 0\n")
    
    // Test 4: Single element
    println("Test 4: Single element")
    val nums4 = intArrayOf(5)
    println("Input: ${nums4.contentToString()}")
    println("Result: ${solution.reversePairs(nums4.clone())}")
    println("Expected: 0\n")
    
    // Test 5: Large gap
    println("Test 5: Large gap")
    val nums5 = intArrayOf(10, 1)
    println("Input: ${nums5.contentToString()}")
    println("Result: ${solution.reversePairs(nums5.clone())}")
    println("Expected: 1 (10 > 2*1)\n")
    
    println("All tests completed!")
}
