/**
 * ============================================================================
 * PROBLEM: Assign Cookies
 * DIFFICULTY: Easy
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You want to give cookies to children. Each child has a greed factor g[i] 
 * (minimum cookie size they will be content with), and each cookie has a size s[j].
 * A child will be content if s[j] >= g[i]. Maximize the number of content children.
 * 
 * INPUT FORMAT:
 * - greed: [1, 2, 3] (greed factor of each child)
 * - cookies: [1, 1] (size of each cookie)
 * 
 * OUTPUT FORMAT:
 * - Integer: maximum number of content children (1)
 * 
 * CONSTRAINTS:
 * - 1 <= greed.size, cookies.size <= 3 * 10^4
 * - 0 <= greed[i], cookies[j] <= 2^31 - 1
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Greedy approach - assign smallest cookie that satisfies each child.
 * Sort both arrays and use two pointers to match children with cookies.
 * 
 * ALGORITHM STEPS:
 * 1. Sort both greed array and cookies array
 * 2. Use two pointers: one for children, one for cookies
 * 3. For each child, try to assign smallest available cookie that satisfies them
 * 4. If cookie satisfies child, move both pointers forward
 * 5. If cookie too small, move only cookie pointer forward
 * 
 * VISUAL EXAMPLE:
 * greed = [1, 2, 3], cookies = [1, 1]
 * After sorting: greed = [1, 2, 3], cookies = [1, 1]
 * 
 * child=0 (greed=1), cookie=0 (size=1): 1>=1 ✓ → assign, content=1
 * child=1 (greed=2), cookie=1 (size=1): 1<2 ✗ → no more cookies
 * Result: 1 child content
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Greedy with sorting (used here): O(n log n) time, O(1) space - OPTIMAL
 * 2. Brute force matching: O(n * m) time - Not optimal
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n log n + m log m)
 * - Sorting greed array: O(n log n)
 * - Sorting cookies array: O(m log m)
 * - Two-pointer traversal: O(n + m)
 * - Total: O(n log n + m log m)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using two pointer variables
 * - Sorting done in-place
 * 
 * ============================================================================
 */

package greedy.easy

class AssignCookies {
    
    /**
     * Assigns cookies to children to maximize content children
     */
    fun findContentChildren(greed: IntArray, cookies: IntArray): Int {
        greed.sort()
        cookies.sort()
        
        var childIndex = 0
        var cookieIndex = 0
        var contentChildren = 0
        
        while (childIndex < greed.size && cookieIndex < cookies.size) {
            if (cookies[cookieIndex] >= greed[childIndex]) {
                contentChildren++
                childIndex++
                cookieIndex++
            } else {
                cookieIndex++
            }
        }
        
        return contentChildren
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. No cookies: greed=[1,2], cookies=[] → 0
 * 2. No children: greed=[], cookies=[1,2] → 0
 * 3. All satisfied: greed=[1,1], cookies=[2,2] → 2
 * 4. None satisfied: greed=[5,5], cookies=[1,1] → 0
 * 5. More cookies than children: greed=[1], cookies=[1,2,3] → 1
 * 6. More children than cookies: greed=[1,2,3], cookies=[1] → 1
 * 
 * APPLICATIONS:
 * - Resource allocation problems
 * - Task assignment optimization
 * - Matching algorithms
 * 
 * ============================================================================
 */

fun main() {
    val solution = AssignCookies()
    
    println("Assign Cookies - Test Cases")
    println("============================\n")
    
    println("Test 1: greed=[1,2,3], cookies=[1,1]")
    println("Result: ${solution.findContentChildren(intArrayOf(1, 2, 3), intArrayOf(1, 1))}")
    println("Expected: 1 ✓\n")
    
    println("Test 2: greed=[1,2], cookies=[1,2,3]")
    println("Result: ${solution.findContentChildren(intArrayOf(1, 2), intArrayOf(1, 2, 3))}")
    println("Expected: 2 ✓\n")
    
    println("Test 3: greed=[10,9,8,7], cookies=[5,6,7,8]")
    println("Result: ${solution.findContentChildren(intArrayOf(10, 9, 8, 7), intArrayOf(5, 6, 7, 8))}")
    println("Expected: 2 ✓\n")
}
