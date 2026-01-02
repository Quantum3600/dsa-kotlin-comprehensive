/**
 * ============================================================================
 * PROBLEM: Majority Element N/3
 * DIFFICULTY: Hard
 * CATEGORY: Arrays, Boyer-Moore Voting Algorithm
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an integer array of size n, find all elements that appear more than
 * ⌊n/3⌋ times. The algorithm should run in linear time and O(1) space.
 * 
 * INPUT FORMAT:
 * - An array of integers: nums = [3, 2, 3]
 * 
 * OUTPUT FORMAT:
 * - List of majority elements: [3]
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 5 * 10^4
 * - -10^9 <= nums[i] <= 10^9
 * - At most 2 elements can appear more than n/3 times
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * There can be at most 2 elements appearing more than n/3 times.
 * Why? If 3 elements each appear > n/3 times, total > n (impossible).
 * 
 * Use extended Boyer-Moore voting algorithm with two candidates.
 * 
 * KEY INSIGHT:
 * - Maintain two candidates with their counts
 * - First pass: Find potential candidates
 * - Second pass: Verify candidates actually appear > n/3 times
 * 
 * ALGORITHM STEPS:
 * 1. Initialize two candidates and their counts to 0
 * 2. First pass - Find candidates:
 *    - If num matches candidate1: increment count1
 *    - Else if num matches candidate2: increment count2
 *    - Else if count1 == 0: set candidate1 = num, count1 = 1
 *    - Else if count2 == 0: set candidate2 = num, count2 = 1
 *    - Else: decrement both counts
 * 3. Second pass - Verify candidates:
 *    - Count actual occurrences of each candidate
 *    - Add to result if count > n/3
 * 
 * VISUAL EXAMPLE:
 * nums = [1, 1, 1, 2, 2, 3, 3, 3, 3]
 * n = 9, threshold = 3
 * 
 * First pass (find candidates):
 * i=0: num=1, cand1=1, cnt1=1
 * i=1: num=1, cnt1=2
 * i=2: num=1, cnt1=3
 * i=3: num=2, cand2=2, cnt2=1
 * i=4: num=2, cnt2=2
 * i=5: num=3, cnt1--, cnt2-- (both become 2, 1)
 * i=6: num=3, cnt1--, cnt2-- (both become 1, 0)
 * i=7: num=3, cand2=3, cnt2=1
 * i=8: num=3, cnt2=2
 * 
 * Candidates: 1, 3
 * 
 * Second pass (verify):
 * Count of 1 = 3 (need count > 3, but 3 is not > 3, so excluded)
 * Count of 3 = 4 (4 > 3? Yes, included)
 * 
 * Result: [3]
 * 
 * COMPLEXITY:
 * Time: O(n) - two passes through array
 * Space: O(1) - only constant variables
 * 
 * ============================================================================
 */

package arrays.hard

class MajorityElementN3 {
    
    /**
     * Finds all elements appearing more than n/3 times
     * 
     * @param nums The input array
     * @return List of majority elements
     */
    fun majorityElement(nums: IntArray): List<Int> {
        if (nums.isEmpty()) return emptyList()
        
        // First pass: Find potential candidates
        var candidate1: Int? = null
        var candidate2: Int? = null
        var count1 = 0
        var count2 = 0
        
        for (num in nums) {
            when {
                candidate1 == num -> count1++
                candidate2 == num -> count2++
                count1 == 0 -> {
                    candidate1 = num
                    count1 = 1
                }
                count2 == 0 -> {
                    candidate2 = num
                    count2 = 1
                }
                else -> {
                    count1--
                    count2--
                }
            }
        }
        
        // Second pass: Verify candidates
        val result = mutableListOf<Int>()
        val threshold = nums.size / 3
        
        candidate1?.let {
            if (nums.count { num -> num == it } > threshold) {
                result.add(it)
            }
        }
        
        candidate2?.let {
            if (it != candidate1 && nums.count { num -> num == it } > threshold) {
                result.add(it)
            }
        }
        
        return result
    }
    
    /**
     * Alternative implementation with explicit counting
     */
    fun majorityElementExplicit(nums: IntArray): List<Int> {
        if (nums.isEmpty()) return emptyList()
        
        var candidate1 = 0
        var candidate2 = 0
        var count1 = 0
        var count2 = 0
        
        // First pass
        for (num in nums) {
            if (num == candidate1) {
                count1++
            } else if (num == candidate2) {
                count2++
            } else if (count1 == 0) {
                candidate1 = num
                count1 = 1
            } else if (count2 == 0) {
                candidate2 = num
                count2 = 1
            } else {
                count1--
                count2--
            }
        }
        
        // Second pass
        count1 = 0
        count2 = 0
        for (num in nums) {
            if (num == candidate1) count1++
            else if (num == candidate2) count2++
        }
        
        val result = mutableListOf<Int>()
        val threshold = nums.size / 3
        
        if (count1 > threshold) result.add(candidate1)
        if (count2 > threshold) result.add(candidate2)
        
        return result
    }
    
    /**
     * Brute force using HashMap
     * Time: O(n), Space: O(n)
     */
    fun majorityElementHashMap(nums: IntArray): List<Int> {
        val counts = mutableMapOf<Int, Int>()
        for (num in nums) {
            counts[num] = counts.getOrDefault(num, 0) + 1
        }
        
        val threshold = nums.size / 3
        return counts.filter { it.value > threshold }.keys.toList()
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single element: [1] → [1]
 * 2. Two elements same: [1, 1] → [1]
 * 3. Two elements different: [1, 2] → []
 * 4. Three same: [1, 1, 1] → [1]
 * 5. No majority: [1, 2, 3] → []
 * 6. Two majorities: [1, 1, 2, 2, 3] → [1, 2]
 * 7. All same: [5, 5, 5, 5] → [5]
 * 
 * APPLICATIONS:
 * 1. Finding popular items in datasets
 * 2. Fraud detection (frequent patterns)
 * 3. Network traffic analysis
 * 4. Voting systems
 * 5. Data mining (frequent itemsets)
 * 6. Log analysis (common errors)
 * 7. Social media trending topics
 * 
 * ============================================================================
 */

fun main() {
    val solution = MajorityElementN3()
    
    println("=== Majority Element N/3 Tests ===\n")
    
    // Test 1: Single majority
    println("Test 1: Single majority")
    val nums1 = intArrayOf(3, 2, 3)
    println("Input: ${nums1.contentToString()}")
    println("Result: ${solution.majorityElement(nums1)}")
    println("Expected: [3]\n")
    
    // Test 2: Two majorities
    println("Test 2: Two majorities")
    val nums2 = intArrayOf(1, 1, 1, 2, 2, 2, 3)
    println("Input: ${nums2.contentToString()}")
    println("Result: ${solution.majorityElement(nums2)}")
    println("Expected: [1, 2]\n")
    
    // Test 3: No majority
    println("Test 3: No majority")
    val nums3 = intArrayOf(1, 2, 3, 4)
    println("Input: ${nums3.contentToString()}")
    println("Result: ${solution.majorityElement(nums3)}")
    println("Expected: []\n")
    
    // Test 4: Single element
    println("Test 4: Single element")
    val nums4 = intArrayOf(1)
    println("Input: ${nums4.contentToString()}")
    println("Result: ${solution.majorityElement(nums4)}")
    println("Expected: [1]\n")
    
    // Test 5: All same
    println("Test 5: All same")
    val nums5 = intArrayOf(5, 5, 5, 5)
    println("Input: ${nums5.contentToString()}")
    println("Result: ${solution.majorityElement(nums5)}")
    println("Expected: [5]\n")
    
    // Test 6: Complex case
    println("Test 6: Complex case")
    val nums6 = intArrayOf(1, 1, 1, 2, 2, 3, 3, 3, 3)
    println("Input: ${nums6.contentToString()}")
    println("Result: ${solution.majorityElement(nums6)}")
    println("Expected: [3]\n")
    
    println("All tests completed!")
}
