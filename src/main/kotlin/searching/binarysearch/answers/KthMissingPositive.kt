/**
 * ============================================================================
 * PROBLEM:  Kth Missing Positive Number
 * DIFFICULTY: Easy
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of positive integers sorted in strictly increasing order,
 * and an integer k, return the kth positive integer that is missing from
 * the array.
 * 
 * INPUT FORMAT: 
 * - arr:  Sorted array of positive integers
 * - k: The kth missing number to find
 * Example: arr = [2, 3, 4, 7, 11], k = 5
 * 
 * OUTPUT FORMAT:
 * - The kth missing positive integer
 * Example: 9
 * Missing numbers: 1, 5, 6, 8, 9, 10...  → 9 is the 5th missing
 * 
 * CONSTRAINTS:
 * - 1 <= arr.length <= 1000
 * - 1 <= arr[i] <= 1000
 * - 1 <= k <= 1000
 * - arr is strictly increasing
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * At each position i in the array, we can calculate how many positive numbers
 * are missing before arr[i]. This is:  arr[i] - (i + 1)
 * 
 * For example:  arr = [2, 3, 4, 7, 11]
 * - At i=0: arr[0]=2, missing = 2-(0+1) = 1 (missing:  1)
 * - At i=1: arr[1]=3, missing = 3-(1+1) = 1 (missing: 1)
 * - At i=2: arr[2]=4, missing = 4-(2+1) = 1 (missing: 1)
 * - At i=3: arr[3]=7, missing = 7-(3+1) = 3 (missing: 1,5,6)
 * - At i=4: arr[4]=11, missing = 11-(4+1) = 6 (missing: 1,5,6,8,9,10)
 * 
 * KEY INSIGHT:
 * Use binary search to find the index where the number of missing elements
 * transitions from < k to >= k.
 * 
 * ALGORITHM STEPS:
 * 1. Use binary search to find the largest index where missing < k
 * 2. If found at index i:  answer = arr[i] + (k - missing[i])
 * 3. If not found: answer = k (all k missing numbers are before arr[0])
 * 
 * VISUAL EXAMPLE:
 * arr = [2, 3, 4, 7, 11], k = 5
 * Natural sequence: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... 
 * Array elements:       2, 3, 4,       7,          11
 * Missing:           1,          5, 6,    8, 9, 10
 * 
 * Index:   0  1  2  3   4
 * Array:  2  3  4  7  11
 * Missing: 1  1  1  3   6
 * 
 * We need the 5th missing number. 
 * At i=3: 3 missing < 5, continue
 * At i=4: 6 missing >= 5, found! 
 * 
 * Answer = arr[3] + (5 - 3) = 7 + 2 = 9 ✓
 * 
 * ALTERNATIVE APPROACH:
 * Linear scan:  O(n) time, simpler but less optimal for large arrays
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log n)
 * - Binary search on array of size n
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only using a few variables
 * 
 * ============================================================================
 */

package searching.binarysearch.answers

class KthMissingPositive {
    
    /**
     * Finds the kth missing positive number using binary search
     * @param arr Sorted array of positive integers
     * @param k The kth missing number to find
     * @return The kth missing positive integer
     */
    fun findKthPositive(arr: IntArray, k: Int): Int {
        var low = 0
        var high = arr.size - 1
        
        // Binary search to find the insertion point
        while (low <= high) {
            val mid = low + (high - low) / 2
            val missing = arr[mid] - (mid + 1)
            
            if (missing < k) {
                low = mid + 1  // Need more missing numbers
            } else {
                high = mid - 1  // Have enough or too many missing
            }
        }
        
        // At this point, high is the largest index where missing < k
        // low is the first index where missing >= k
        
        // If high == -1, all k missing numbers are before arr[0]
        // Otherwise, answer is arr[high] + remaining missing count
        return if (high == -1) {
            k
        } else {
            val missingBeforeHigh = arr[high] - (high + 1)
            arr[high] + (k - missingBeforeHigh)
        }
    }
    
    /**
     * Alternative simple formula
     */
    fun findKthPositiveSimple(arr: IntArray, k: Int): Int {
        var low = 0
        var high = arr.size - 1
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            val missing = arr[mid] - (mid + 1)
            
            if (missing < k) {
                low = mid + 1
            } else {
                high = mid - 1
            }
        }
        
        // Formula: k + number of elements seen = k + low
        return k + low
    }
    
    /**
     * Linear solution for comparison - O(n)
     */
    fun findKthPositiveLinear(arr: IntArray, k: Int): Int {
        var missing = k
        
        for (num in arr) {
            if (num <= missing) {
                missing++
            } else {
                break
            }
        }
        
        return missing
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: arr = [2, 3, 4, 7, 11], k = 5
 * 
 * Initial: low=0, high=4
 * 
 * Iteration 1: mid=2
 * missing = 4-(2+1) = 1
 * 1 < 5, low=3
 * 
 * Iteration 2: mid=3
 * missing = 7-(3+1) = 3
 * 3 < 5, low=4
 * 
 * Iteration 3: mid=4
 * missing = 11-(4+1) = 6
 * 6 >= 5, high=3
 * 
 * low > high, stop
 * high=3, arr[high]=7
 * missingBeforeHigh = 3
 * Answer = 7 + (5-3) = 9 ✓
 * 
 * Example 2: arr = [1, 2, 3, 4], k = 2
 * All numbers from 1-4 are present
 * Missing:  5, 6, 7... 
 * 
 * Binary search will eventually have high=3
 * missing at high = 4-(3+1) = 0
 * Answer = 4 + (2-0) = 6
 * Or using formula: k + low = 2 + 4 = 6 ✓
 * 
 * Example 3: arr = [2], k = 1
 * Missing: 1
 * 
 * mid=0, missing = 2-1 = 1
 * 1 >= 1, high=-1
 * Answer = k = 1 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = KthMissingPositive()
    
    // Test Case 1: Standard case
    println("Test 1: arr=[2,3,4,7,11], k=5 → ${solution.findKthPositive(intArrayOf(2,3,4,7,11), 5)}")  
    // Expected: 9
    
    // Test Case 2: All consecutive
    println("Test 2: arr=[1,2,3,4], k=2 → ${solution.findKthPositive(intArrayOf(1,2,3,4), 2)}")  
    // Expected: 6
    
    // Test Case 3: Missing at beginning
    println("Test 3: arr=[2,3,4], k=1 → ${solution. findKthPositive(intArrayOf(2,3,4), 1)}")  
    // Expected: 1
    
    // Test Case 4: Large gap
    println("Test 4: arr=[1,10,20], k=5 → ${solution.findKthPositive(intArrayOf(1,10,20), 5)}")  
    // Expected: 6
    
    // Test Case 5: Single element
    println("Test 5: arr=[5], k=3 → ${solution.findKthPositive(intArrayOf(5), 3)}")  
    // Expected: 3
    
    // Compare with linear solution
    println("\nComparing binary search vs linear:")
    val arr = intArrayOf(2,3,4,7,11)
    println("Binary Search: ${solution.findKthPositive(arr, 5)}")
    println("Linear: ${solution. findKthPositiveLinear(arr, 5)}")
}
