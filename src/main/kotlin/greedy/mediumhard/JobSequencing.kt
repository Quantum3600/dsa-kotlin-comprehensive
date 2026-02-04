/**
 * ============================================================================
 * PROBLEM: Job Sequencing Problem
 * DIFFICULTY: Medium
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a list of jobs where each job has a deadline and profit.
 * Each job takes 1 unit of time. Maximize total profit by scheduling jobs
 * before their deadlines. Only one job can be scheduled at a time.
 * 
 * INPUT FORMAT:
 * - jobs: [(id, deadline, profit)] 
 * - Example: [(1,4,20), (2,1,10), (3,1,40), (4,1,30)]
 * 
 * OUTPUT FORMAT:
 * - Pair: (number of jobs done, maximum profit)
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 10^5
 * - 1 <= deadline <= n
 * - 1 <= profit <= 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Greedy approach - prioritize jobs with higher profit.
 * Schedule each job as late as possible before deadline to leave room
 * for other jobs.
 * 
 * KEY INSIGHT:
 * Sort jobs by profit (descending). For each job, find latest available
 * slot before its deadline.
 * 
 * ALGORITHM STEPS:
 * 1. Sort jobs by profit in descending order
 * 2. Create slots array to track scheduled times
 * 3. For each job:
 *    - Find latest free slot <= deadline
 *    - If found, schedule job and mark slot occupied
 * 4. Return count and total profit
 * 
 * VISUAL EXAMPLE:
 * jobs = [(1,4,20), (2,1,10), (3,1,40), (4,1,30)]
 * 
 * After sorting by profit: [(3,1,40), (4,1,30), (1,4,20), (2,1,10)]
 * slots = [-1, -1, -1, -1] (indices 0-3 for time 1-4)
 * 
 * Job 3 (deadline=1, profit=40): Schedule at slot 0 (time 1)
 * slots = [3, -1, -1, -1], profit=40
 * 
 * Job 4 (deadline=1, profit=30): Slot 0 taken, can't schedule
 * slots = [3, -1, -1, -1], profit=40
 * 
 * Job 1 (deadline=4, profit=20): Schedule at slot 3 (time 4)
 * slots = [3, -1, -1, 1], profit=60
 * 
 * Job 2 (deadline=1, profit=10): Slot 0 taken, can't schedule
 * 
 * Result: (2 jobs, 60 profit) ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Greedy with slots (used here): O(n^2) time, O(n) space
 * 2. Disjoint set: O(n log n) time - More complex
 * 3. Priority queue: O(n log n) time - Better for large deadlines
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n^2)
 * - Sorting: O(n log n)
 * - Finding slots: O(n * maxDeadline) = O(n^2) in worst case
 * - Total: O(n^2)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Slots array: O(maxDeadline) ≤ O(n)
 * 
 * ============================================================================
 */

package greedy.mediumhard

class JobSequencing {
    
    data class Job(val id: Int, val deadline: Int, val profit: Int)
    
    /**
     * Finds maximum profit and job count
     */
    fun jobScheduling(jobs: Array<Job>): Pair<Int, Int> {
        if (jobs.isEmpty()) return Pair(0, 0)
        
        // Sort by profit descending
        val sortedJobs = jobs.sortedByDescending { it.profit }
        
        // Find max deadline
        val maxDeadline = jobs.maxOf { it.deadline }
        
        // Create slots (-1 means free)
        val slots = IntArray(maxDeadline) { -1 }
        
        var jobCount = 0
        var totalProfit = 0
        
        // Schedule each job
        for (job in sortedJobs) {
            // Find latest free slot before deadline
            for (slot in minOf(job.deadline, maxDeadline) - 1 downTo 0) {
                if (slots[slot] == -1) {
                    slots[slot] = job.id
                    jobCount++
                    totalProfit += job.profit
                    break
                }
            }
        }
        
        return Pair(jobCount, totalProfit)
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single job: [(1,1,100)] → (1, 100)
 * 2. All same deadline: [(1,1,10),(2,1,20)] → (1, 20)
 * 3. No conflicts: [(1,1,10),(2,2,20)] → (2, 30)
 * 4. All fit: [(1,3,20),(2,3,30)] → (2, 50)
 * 5. Long deadlines: [(1,100,10)] → (1, 10)
 * 
 * APPLICATIONS:
 * - Task scheduling
 * - Project management
 * - CPU scheduling
 * - Deadline-based planning
 * 
 * ============================================================================
 */

fun main() {
    val solution = JobSequencing()
    
    println("Job Sequencing - Test Cases")
    println("=============================\n")
    
    println("Test 1: [(1,4,20), (2,1,10), (3,1,40), (4,1,30)]")
    val jobs1 = arrayOf(
        JobSequencing.Job(1, 4, 20),
        JobSequencing.Job(2, 1, 10),
        JobSequencing.Job(3, 1, 40),
        JobSequencing.Job(4, 1, 30)
    )
    println("Result: ${solution.jobScheduling(jobs1)}")
    println("Expected: (2, 60) ✓\n")
    
    println("Test 2: [(1,2,100), (2,1,19), (3,2,27), (4,1,25), (5,1,15)]")
    val jobs2 = arrayOf(
        JobSequencing.Job(1, 2, 100),
        JobSequencing.Job(2, 1, 19),
        JobSequencing.Job(3, 2, 27),
        JobSequencing.Job(4, 1, 25),
        JobSequencing.Job(5, 1, 15)
    )
    println("Result: ${solution.jobScheduling(jobs2)}")
    println("Expected: (2, 127) ✓\n")
}
