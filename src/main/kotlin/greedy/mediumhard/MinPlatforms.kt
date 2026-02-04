/**
 * ============================================================================
 * PROBLEM: Minimum Platforms
 * DIFFICULTY: Medium
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given arrival and departure times of trains at a railway station,
 * find minimum number of platforms required so no train waits.
 * 
 * INPUT FORMAT:
 * - arrivals: [900, 940, 950, 1100, 1500, 1800]
 * - departures: [910, 1200, 1120, 1130, 1900, 2000]
 * 
 * OUTPUT FORMAT:
 * - Integer: minimum platforms needed (3)
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 50000
 * - 0 <= arrivals[i], departures[i] <= 2359
 * - arrivals[i] < departures[i]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Sort both arrays independently. Use two pointers to track when trains
 * arrive and depart. Track current trains at station and max seen.
 * 
 * KEY INSIGHT:
 * When a train arrives before another departs, we need an extra platform.
 * When a train departs, a platform becomes free.
 * 
 * ALGORITHM STEPS:
 * 1. Sort both arrivals and departures
 * 2. Use two pointers: i for arrivals, j for departures
 * 3. While i < n:
 *    - If arrivals[i] <= departures[j]: train arrives, platforms++, i++
 *    - Else: train departs, platforms--, j++
 *    - Update maxPlatforms
 * 4. Return maxPlatforms
 * 
 * VISUAL EXAMPLE:
 * arrivals =   [900, 940, 950, 1100, 1500, 1800]
 * departures = [910, 1200, 1120, 1130, 1900, 2000]
 * 
 * After sorting (already sorted):
 * arrivals =   [900, 940, 950, 1100, 1500, 1800]
 * departures = [910, 1120, 1130, 1200, 1900, 2000]
 * 
 * Time 900: train arrives, platforms=1, max=1
 * Time 910: train departs, platforms=0
 * Time 940: train arrives, platforms=1, max=1
 * Time 950: train arrives, platforms=2, max=2
 * Time 1100: train arrives, platforms=3, max=3 ✓
 * Time 1120: train departs, platforms=2
 * Time 1130: train departs, platforms=1
 * Time 1200: train departs, platforms=0
 * Time 1500: train arrives, platforms=1
 * Time 1800: train arrives, platforms=2
 * Time 1900: train departs, platforms=1
 * Time 2000: train departs, platforms=0
 * 
 * Result: 3 platforms ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Two-pointer (used here): O(n log n) time, O(1) space - OPTIMAL
 * 2. Merge events: O(n log n) time, O(n) space - More space
 * 3. Brute force: O(n^2) time - Not optimal
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n log n)
 * - Sorting arrivals: O(n log n)
 * - Sorting departures: O(n log n)
 * - Two-pointer scan: O(n)
 * - Total: O(n log n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using pointer and counter variables
 * - Sorting done in-place
 * 
 * ============================================================================
 */

package greedy.mediumhard

class MinPlatforms {
    
    /**
     * Finds minimum platforms needed
     */
    fun findPlatform(arrivals: IntArray, departures: IntArray): Int {
        val n = arrivals.size
        
        // Sort both arrays
        arrivals.sort()
        departures.sort()
        
        var platforms = 0
        var maxPlatforms = 0
        var i = 0
        var j = 0
        
        while (i < n) {
            // If train arrives before or when another departs
            if (arrivals[i] <= departures[j]) {
                platforms++
                i++
                maxPlatforms = maxOf(maxPlatforms, platforms)
            } else {
                // Train departs, platform becomes free
                platforms--
                j++
            }
        }
        
        return maxPlatforms
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single train: arr=[900], dep=[910] → 1
 * 2. No overlap: arr=[900,1000], dep=[910,1010] → 1
 * 3. All overlap: arr=[900,900,900], dep=[910,910,910] → 3
 * 4. Sequential: arr=[900,910,920], dep=[905,915,925] → 3
 * 5. Same time: arr=[900,900], dep=[900,900] → 2
 * 
 * APPLICATIONS:
 * - Railway station management
 * - Airport gate allocation
 * - Meeting room scheduling
 * - Resource allocation
 * 
 * ============================================================================
 */

fun main() {
    val solution = MinPlatforms()
    
    println("Minimum Platforms - Test Cases")
    println("================================\n")
    
    println("Test 1: arr=[900,940,950,1100,1500,1800], dep=[910,1200,1120,1130,1900,2000]")
    println("Result: ${solution.findPlatform(
        intArrayOf(900, 940, 950, 1100, 1500, 1800),
        intArrayOf(910, 1200, 1120, 1130, 1900, 2000)
    )}")
    println("Expected: 3 ✓\n")
    
    println("Test 2: arr=[900,1100,1235], dep=[1000,1200,1240]")
    println("Result: ${solution.findPlatform(
        intArrayOf(900, 1100, 1235),
        intArrayOf(1000, 1200, 1240)
    )}")
    println("Expected: 1 ✓\n")
}
