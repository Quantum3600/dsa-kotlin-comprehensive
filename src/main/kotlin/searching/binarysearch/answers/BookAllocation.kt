/**
 * ============================================================================
 * PROBLEM: Book Allocation (Allocate Minimum Number of Pages)
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers representing the number of pages in each book,
 * and an integer m representing the number of students.  Allocate books to
 * m students such that: 
 * 1. Each student gets at least one book
 * 2. Books are allocated in contiguous manner
 * 3. Maximize the minimum number of pages (minimize the maximum)
 * 
 * Find the minimum value of the maximum number of pages assigned to a student.
 * 
 * INPUT FORMAT:
 * - arr: Array where arr[i] = pages in book i
 * - m:  Number of students
 * Example:  arr = [12, 34, 67, 90], m = 2
 * 
 * OUTPUT FORMAT:
 * - Minimum possible maximum pages
 * Example: 113
 * Explanation: Student 1 gets [12, 34, 67] = 113 pages
 *              Student 2 gets [90] = 90 pages
 *              Maximum = 113 (minimized)
 * 
 * CONSTRAINTS:
 * - 1 <= arr. length <= 10^5
 * - 1 <= arr[i] <= 10^6
 * - 1 <= m <= arr.length
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is a classic "minimize the maximum" problem. We use binary search
 * on the answer (maximum pages). For each candidate answer, we check if
 * it's possible to allocate books to m students such that no student gets
 * more than that many pages.
 * 
 * KEY INSIGHT:
 * - Minimum possible answer: max(arr) (one student must get the largest book)
 * - Maximum possible answer: sum(arr) (one student gets all books)
 * - If we can allocate with maxPages = X, we can also with X+1, X+2, etc. 
 * - Find the smallest maxPages that allows valid allocation
 * 
 * ALGORITHM STEPS:
 * 1. If m > n:  impossible (more students than books), return -1
 * 2. Binary search:  low = max(arr), high = sum(arr)
 * 3. For each mid:
 *    - Check if we can allocate books such that no student gets > mid pages
 *    - Use greedy: give books to current student until exceeds mid
 *    - If students needed <= m: answer could be mid or smaller (high = mid)
 *    - Else: need larger maxPages (low = mid + 1)
 * 4. Return high (minimum valid maxPages)
 * 
 * VISUAL EXAMPLE:
 * arr = [12, 34, 67, 90], m = 2
 * 
 * Allocation with maxPages = 113:
 * Student 1: 12 (12), add 34 (46), add 67 (113) ✓ [can't add 90]
 * Student 2: 90 (90) ✓
 * Students used: 2 <= 2 ✓
 * 
 * Allocation with maxPages = 100:
 * Student 1: 12 (12), add 34 (46), add 67 (113) > 100 ✗
 * Student 1: 12 (12), add 34 (46) ✓
 * Student 2: 67 (67) ✓
 * Student 3: 90 (90) ✓
 * Students used:  3 > 2 ✗
 * 
 * Binary Search: 
 * low=90, high=203
 * 
 * mid=146:  students needed=2 <= 2, high=146
 * mid=118: students needed=2 <= 2, high=118
 * mid=104: students needed=3 > 2, low=105
 * mid=111: students needed=3 > 2, low=112
 * mid=115: students needed=2 <= 2, high=115
 * mid=113: students needed=2 <= 2, high=113
 * mid=112: students needed=3 > 2, low=113
 * low==high=113, return 113 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(sum - max))
 * - Binary search: O(log(sum - max))
 * - Each iteration checks allocation: O(n)
 * - Total: O(n * log(sum - max))
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch.answers

class BookAllocation {
    
    /**
     * Finds minimum possible maximum pages allocated to any student
     * @param arr Array of pages in each book
     * @param m Number of students
     * @return Minimum of maximum pages, or -1 if impossible
     */
    fun allocateBooks(arr: IntArray, m: Int): Int {
        val n = arr.size
        
        // Impossible if more students than books
        if (m > n) return -1
        
        var low = arr.maxOrNull() ?: 0  // At least max(arr)
        var high = arr.sum()  // At most sum(arr)
        var result = high
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            val studentsNeeded = countStudents(arr, mid)
            
            if (studentsNeeded <= m) {
                // Can allocate with maxPages = mid
                result = mid
                high = mid - 1  // Try to minimize further
            } else {
                // Need more pages per student
                low = mid + 1
            }
        }
        
        return result
    }
    
    /**
     * Counts minimum students needed if each can get at most maxPages
     * @param arr Array of pages
     * @param maxPages Maximum pages per student
     * @return Number of students needed
     */
    private fun countStudents(arr:  IntArray, maxPages: Int): Int {
        var students = 1
        var currentPages = 0
        
        for (pages in arr) {
            if (currentPages + pages <= maxPages) {
                // Give this book to current student
                currentPages += pages
            } else {
                // Need a new student for this book
                students++
                currentPages = pages
            }
        }
        
        return students
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: arr = [12, 34, 67, 90], m = 2
 * 
 * Initial: low=90, high=203
 * 
 * Iteration 1: mid=146
 * Student allocation:
 * - Student 1: 12+34+67 = 113 <= 146 ✓
 * - Student 2: 90 <= 146 ✓
 * Students:  2 <= 2, result=146, high=145
 * 
 * Iteration 2: mid=117
 * Student allocation:
 * - Student 1: 12+34+67 = 113 <= 117 ✓
 * - Student 2: 90 <= 117 ✓
 * Students: 2 <= 2, result=117, high=116
 * 
 * Iteration 3: mid=103
 * Student allocation:
 * - Student 1: 12+34 = 46, add 67 = 113 > 103
 * - Student 1: 12+34 = 46 <= 103 ✓
 * - Student 2: 67 <= 103 ✓
 * - Student 3: 90 <= 103 ✓
 * Students: 3 > 2, low=104
 * 
 * Continue until low=113, high=112, return result=113 ✓
 * 
 * Example 2: arr = [10, 20, 30, 40], m = 2
 * 
 * low=40, high=100
 * 
 * mid=70:
 * - Student 1: 10+20+30 = 60 <= 70 ✓
 * - Student 2: 40 <= 70 ✓
 * Students: 2, result=70, high=69
 * 
 * mid=54:
 * - Student 1: 10+20 = 30, add 30 = 60 > 54
 * - Student 1: 10+20 = 30 <= 54 ✓
 * - Student 2: 30 <= 54 ✓
 * - Student 3: 40 <= 54 ✓
 * Students: 3 > 2, low=55
 * 
 * mid=62:
 * - Student 1: 10+20+30 = 60 <= 62 ✓
 * - Student 2: 40 <= 62 ✓
 * Students: 2, result=62, high=61
 * 
 * Eventually converges to 60 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = BookAllocation()
    
    // Test Case 1: Standard case
    println("Test 1: arr=[12,34,67,90], m=2")
    println("Result: ${solution. allocateBooks(intArrayOf(12,34,67,90), 2)}")
    // Expected: 113
    
    // Test Case 2: More balanced
    println("\nTest 2: arr=[10,20,30,40], m=2")
    println("Result: ${solution.allocateBooks(intArrayOf(10,20,30,40), 2)}")
    // Expected: 60
    
    // Test Case 3: Students = books
    println("\nTest 3: arr=[10,20,30], m=3")
    println("Result: ${solution.allocateBooks(intArrayOf(10,20,30), 3)}")
    // Expected: 30 (each gets one book)
    
    // Test Case 4: Impossible case
    println("\nTest 4: arr=[10,20], m=3")
    println("Result: ${solution.allocateBooks(intArrayOf(10,20), 3)}")
    // Expected: -1
    
    // Test Case 5: One student
    println("\nTest 5: arr=[5,10,15,20], m=1")
    println("Result: ${solution.allocateBooks(intArrayOf(5,10,15,20), 1)}")
    // Expected: 50 (sum of all)
    
    // Detailed simulation for Test 1
    println("\n--- Simulation for [12,34,67,90], m=2 ---")
    val arr = intArrayOf(12,34,67,90)
    for (maxPages in listOf(90, 100, 110, 113, 120, 150, 203)) {
        var students = 1
        var current = 0
        val allocations = mutableListOf<MutableList<Int>>()
        allocations.add(mutableListOf())
        
        for (pages in arr) {
            if (current + pages <= maxPages) {
                current += pages
                allocations.last().add(pages)
            } else {
                students++
                current = pages
                allocations.add(mutableListOf(pages))
            }
        }
        
        val status = if (students <= 2) "✓" else "✗"
        println("maxPages=$maxPages: $students students $status")
        allocations. forEachIndexed { i, books ->
            println("  Student ${i+1}: ${books. joinToString("+")} = ${books.sum()}")
        }
    }
}
