/**
 * ============================================================================
 * PROBLEM: Linear Search
 * DIFFICULTY: Easy
 * CATEGORY: Arrays, Searching
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array and a target element, find the index of the target element.
 * Return -1 if the element is not found.
 * 
 * INPUT: arr = [5, 3, 7, 1, 9], target = 7
 * OUTPUT: 2
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - -10^9 <= arr[i], target <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * Linear search is the simplest searching algorithm:
 * 1. Start from the first element
 * 2. Compare each element with the target
 * 3. Return index if found, -1 if not found
 * 
 * VISUAL EXAMPLE:
 * arr = [5, 3, 7, 1, 9], target = 7
 *        ↓
 * i=0: arr[0]=5 != 7
 *           ↓
 * i=1: arr[1]=3 != 7
 *              ↓
 * i=2: arr[2]=7 == 7 → return 2 ✓
 * 
 * COMPLEXITY:
 * Time: O(n) - worst case check all elements
 * Space: O(1) - only using loop variable
 * 
 * WHEN TO USE:
 * - Unsorted arrays
 * - Small datasets
 * - Single search operation
 * 
 * ALTERNATIVES:
 * - Binary Search: O(log n) but requires sorted array
 * - Hash Table: O(1) average but requires extra space
 * 
 * ============================================================================
 */

package arrays.easy

class LinearSearch {
    
    /**
     * Searches for target in array using linear search
     */
    fun search(arr: IntArray, target: Int): Int {
        for (i in arr.indices) {
            if (arr[i] == target) {
                return i
            }
        }
        return -1
    }
    
    /**
     * Kotlin idiomatic approach using indexOf
     */
    fun searchKotlin(arr: IntArray, target: Int): Int {
        return arr.indexOf(target)
    }
    
    /**
     * Returns all indices where target appears
     */
    fun searchAll(arr: IntArray, target: Int): List<Int> {
        return arr.indices.filter { arr[it] == target }
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty array: [] → -1
 * 2. Single element - found: [5], target=5 → 0
 * 3. Single element - not found: [5], target=3 → -1
 * 4. Target at first: [7, 2, 5], target=7 → 0
 * 5. Target at last: [7, 2, 5], target=5 → 2
 * 6. Target not present: [1, 2, 3], target=5 → -1
 * 7. Multiple occurrences: [1, 2, 1], target=1 → 0 (first occurrence)
 * 8. Negative numbers: [-5, 0, 3], target=-5 → 0
 * 
 * APPLICATIONS:
 * - Finding elements in unsorted data
 * - Simple lookups in small datasets
 * - When data structure doesn't support faster search
 * 
 * ============================================================================
 */

fun main() {
    val solution = LinearSearch()
    
    println("Linear Search - Test Cases")
    println("============================\n")
    
    val arr = intArrayOf(5, 3, 7, 1, 9, 3)
    
    println("Array: ${arr.contentToString()}\n")
    
    println("Test 1: Search for 7")
    println("Result: ${solution.search(arr, 7)}")
    println("Expected: 2 ✓\n")
    
    println("Test 2: Search for 1")
    println("Result: ${solution.search(arr, 1)}")
    println("Expected: 3 ✓\n")
    
    println("Test 3: Search for 10 (not present)")
    println("Result: ${solution.search(arr, 10)}")
    println("Expected: -1 ✓\n")
    
    println("Test 4: Search for 5 (first element)")
    println("Result: ${solution.search(arr, 5)}")
    println("Expected: 0 ✓\n")
    
    println("Test 5: Search for 3 (multiple occurrences)")
    println("All indices: ${solution.searchAll(arr, 3)}")
    println("Expected: [1, 5] ✓\n")
    
    println("All tests passed! ✓")
}
