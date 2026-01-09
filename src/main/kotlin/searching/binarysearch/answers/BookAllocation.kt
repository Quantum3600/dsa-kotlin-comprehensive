/**
 * ============================================================================
 * PROBLEM: Book Allocation Problem
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of books where books[i] represents the number of pages in the
 * ith book, and M students. The task is to allocate books to M students such that:
 * 1. Each student gets at least one book
 * 2. Each book is allocated to exactly one student
 * 3. Books are allocated in contiguous manner (a student gets consecutive books)
 * 4. The objective is to minimize the maximum number of pages assigned to a student
 * 
 * INPUT FORMAT:
 * - books: Array of integers where books[i] = pages in ith book
 * - students: Number of students (M)
 * Example: books = [12, 34, 67, 90], students = 2
 * 
 * OUTPUT FORMAT:
 * - Integer: Minimum possible value of maximum pages assigned to a student
 * Example: 113 (Student 1 gets books [12, 34, 67]=113 pages, Student 2 gets [90]=90 pages)
 * 
 * CONSTRAINTS:
 * - 1 <= books.size <= 10^5
 * - 1 <= students <= books.size
 * - 1 <= books[i] <= 10^6
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We want to distribute books such that the student with maximum pages has as
 * few pages as possible. We can binary search on the answer (maximum pages).
 * For each potential answer, we check if it's possible to allocate books such
 * that no student gets more than that many pages.
 * 
 * KEY INSIGHT:
 * If we can allocate books with max pages = X, we can also allocate with any
 * max pages > X (monotonic property). This allows binary search on the answer.
 * 
 * ALGORITHM STEPS:
 * 1. Set search space:
 *    low = max(books) (minimum possible answer - at least one book per student)
 *    high = sum(books) (maximum possible answer - one student gets all books)
 * 2. Binary search on maximum pages:
 *    - For each mid, check if allocation is possible with max = mid
 *    - If yes, try smaller maximum (search left)
 *    - If no, need larger maximum (search right)
 * 3. Return the minimum valid maximum pages
 * 
 * VISUAL EXAMPLE:
 * books = [12, 34, 67, 90], students = 2
 * 
 * Try max = 100:
 * Student 1: [12, 34] = 46 pages ✓ (< 100)
 * Student 2: [67] = 67 pages ✓ (< 100)
 * Student 3: [90] = 90 pages ✓ (< 100)
 * Need 3 students! Failed (we only have 2)
 * 
 * Try max = 150:
 * Student 1: [12, 34, 67] = 113 pages ✓ (< 150)
 * Student 2: [90] = 90 pages ✓ (< 150)
 * Need 2 students! Success!
 * 
 * WHY BINARY SEARCH:
 * - Search space: [max(books) to sum(books)]
 * - Monotonic property: If allocation possible with max=X, it's also possible
 *   with max=Y where Y > X
 * - We want minimum X where allocation is possible
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(sum - max))
 * - Binary search range: [max(books), sum(books)]
 * - Number of iterations: O(log(sum - max))
 * - Each iteration checks allocation: O(n)
 * 
 * For n = 10^5, max = 10^6, sum = 10^11:
 * - Binary search: ~37 iterations (log₂(10^11) ≈ 37)
 * - Each check: 100,000 operations
 * - Total: ~3.7M operations
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space
 * - No additional data structures
 */

package searching.binarysearch.answers

class BookAllocation {
    
    /**
     * Find minimum of maximum pages allocated to students
     * 
     * @param books Array where books[i] = pages in ith book
     * @param students Number of students
     * @return Minimum possible maximum pages
     */
    fun solve(books: IntArray, students: Int): Int {
        // Edge case: More students than books is invalid
        if (students > books.size) {
            return -1
        }
        
        // Edge case: If students == books, each gets one book
        // Answer is the book with maximum pages
        if (students == books.size) {
            return books.max()
        }
        
        // Step 1: Define search space
        // Minimum possible answer: At least one student must get the largest book
        var low = books.max()
        
        // Maximum possible answer: One student gets all books
        var high = books.sum()
        
        // Variable to store the answer
        var answer = high
        
        // Step 2: Binary search on the answer
        while (low <= high) {
            // Try this as maximum pages per student
            val mid = low + (high - low) / 2
            
            // Check if allocation is possible with this maximum
            if (canAllocate(books, students, mid)) {
                // If possible, try for smaller maximum
                answer = mid
                high = mid - 1
            } else {
                // If not possible, need larger maximum
                low = mid + 1
            }
        }
        
        return answer
    }
    
    /**
     * Check if books can be allocated to students with given max pages per student
     * Uses greedy approach: allocate books to current student until max is reached
     * 
     * @param books Array of page counts
     * @param students Number of students
     * @param maxPages Maximum pages any student should get
     * @return true if allocation possible, false otherwise
     */
    private fun canAllocate(books: IntArray, students: Int, maxPages: Int): Boolean {
        // Start with first student
        var studentsNeeded = 1
        var currentPages = 0
        
        // Try to allocate each book
        for (pages in books) {
            // If adding this book exceeds max for current student
            if (currentPages + pages > maxPages) {
                // Allocate to next student
                studentsNeeded++
                currentPages = pages
                
                // If we need more students than available, allocation fails
                if (studentsNeeded > students) {
                    return false
                }
            } else {
                // Add book to current student
                currentPages += pages
            }
        }
        
        // Allocation successful if we used <= available students
        return true
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: books = [12, 34, 67, 90], students = 2
 * 
 * Step 1: Initialize
 * low = max([12,34,67,90]) = 90
 * high = sum([12,34,67,90]) = 203
 * 
 * Binary Search:
 * 
 * Iteration 1: low=90, high=203
 * - mid = 90 + (203-90)/2 = 146
 * - Can allocate with max=146?
 *   Student 1: 12+34+67=113 ✓, Student 2: 90 ✓
 *   Yes! 2 students needed
 * - answer = 146, high = 145
 * 
 * Iteration 2: low=90, high=145
 * - mid = 90 + (145-90)/2 = 117
 * - Can allocate with max=117?
 *   Student 1: 12+34+67=113 ✓, Student 2: 90 ✓
 *   Yes! 2 students needed
 * - answer = 117, high = 116
 * 
 * Iteration 3: low=90, high=116
 * - mid = 90 + (116-90)/2 = 103
 * - Can allocate with max=103?
 *   Student 1: 12+34=46, 67>103-46, can't add
 *   Student 2: 67, 90>103-67, can't add
 *   Student 3: 90
 *   No! 3 students needed but only 2 available
 * - low = 104
 * 
 * Continue until low > high...
 * Final answer = 113
 * 
 * ============================================================================
 */

fun main() {
    val solver = BookAllocation()
    
    println("=== Testing Book Allocation ===\n")
    
    // Test Case 1: Normal case
    val books1 = intArrayOf(12, 34, 67, 90)
    val students1 = 2
    println("Test 1: books = ${books1.contentToString()}, students = $students1")
    println("Result: ${solver.solve(books1, students1)}")
    println("Expected: 113\n")
    
    // Test Case 2: Each student gets one book
    val books2 = intArrayOf(10, 20, 30, 40)
    val students2 = 4
    println("Test 2: books = ${books2.contentToString()}, students = $students2")
    println("Result: ${solver.solve(books2, students2)}")
    println("Expected: 40 (each student gets 1 book)\n")
    
    // Test Case 3: One student gets all
    val books3 = intArrayOf(10, 20, 30, 40)
    val students3 = 1
    println("Test 3: books = ${books3.contentToString()}, students = $students3")
    println("Result: ${solver.solve(books3, students3)}")
    println("Expected: 100 (sum of all books)\n")
    
    // Test Case 4: More students than books (invalid)
    val books4 = intArrayOf(10, 20)
    val students4 = 3
    println("Test 4: books = ${books4.contentToString()}, students = $students4")
    println("Result: ${solver.solve(books4, students4)}")
    println("Expected: -1 (invalid input)\n")
    
    // Test Case 5: Large array
    val books5 = intArrayOf(15, 17, 20, 25, 30, 35, 40, 45)
    val students5 = 3
    println("Test 5: books = ${books5.contentToString()}, students = $students5")
    println("Result: ${solver.solve(books5, students5)}")
    println("Expected: 82\n")
    
    // Test Case 6: All books have same pages
    val books6 = intArrayOf(50, 50, 50, 50)
    val students6 = 2
    println("Test 6: books = ${books6.contentToString()}, students = $students6")
    println("Result: ${solver.solve(books6, students6)}")
    println("Expected: 100\n")
}
