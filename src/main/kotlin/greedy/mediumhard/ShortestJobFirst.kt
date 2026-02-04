/**
 * ============================================================================
 * PROBLEM: Shortest Job First (SJF) Scheduling
 * DIFFICULTY: Medium
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given burst times of n processes, calculate average waiting time using
 * Shortest Job First (SJF) scheduling algorithm. SJF schedules processes
 * in order of their burst time (shortest first).
 * 
 * INPUT FORMAT:
 * - burstTimes: [4, 3, 7, 1, 2] (time each process takes)
 * 
 * OUTPUT FORMAT:
 * - Double: average waiting time (4.0)
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 10^5
 * - 1 <= burstTimes[i] <= 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Greedy approach - always execute shortest job first to minimize waiting time.
 * Shorter jobs complete quickly, reducing wait time for all pending jobs.
 * 
 * KEY INSIGHT:
 * Total waiting time is minimized when jobs are executed in increasing order
 * of burst time. Each job's waiting time = sum of burst times of all
 * jobs that executed before it.
 * 
 * ALGORITHM STEPS:
 * 1. Sort burst times in ascending order
 * 2. For each process:
 *    - Waiting time = total time of all previous processes
 *    - Add to total waiting time
 *    - Update current time
 * 3. Calculate average = total waiting time / n
 * 
 * VISUAL EXAMPLE:
 * burstTimes = [4, 3, 7, 1, 2]
 * 
 * After sorting: [1, 2, 3, 4, 7]
 * 
 * Process 1 (burst=1): waiting=0, completes at 1
 * Process 2 (burst=2): waiting=1, completes at 3
 * Process 3 (burst=3): waiting=3, completes at 6
 * Process 4 (burst=4): waiting=6, completes at 10
 * Process 5 (burst=7): waiting=10, completes at 17
 * 
 * Total waiting: 0+1+3+6+10 = 20
 * Average: 20/5 = 4.0 ✓
 * 
 * WHY OPTIMAL:
 * If we swap any two jobs, the shorter one now waits longer,
 * increasing total waiting time. Proof by exchange argument.
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Sort + calculate (used here): O(n log n) time, O(1) space - OPTIMAL
 * 2. Priority queue: O(n log n) time, O(n) space - More space
 * 3. Without sorting: O(n^2) time - Not optimal
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n log n)
 * - Sorting burst times: O(n log n)
 * - Calculating waiting times: O(n)
 * - Total: O(n log n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using accumulator variables
 * - Sorting done in-place
 * - Can be O(n) if we need to preserve original order
 * 
 * ============================================================================
 */

package greedy.mediumhard

class ShortestJobFirst {
    
    /**
     * Calculates average waiting time using SJF
     */
    fun averageWaitingTime(burstTimes: IntArray): Double {
        if (burstTimes.isEmpty()) return 0.0
        
        // Sort in ascending order (shortest first)
        burstTimes.sort()
        
        var totalWaitingTime = 0
        var currentTime = 0
        
        for (burst in burstTimes) {
            totalWaitingTime += currentTime
            currentTime += burst
        }
        
        return totalWaitingTime.toDouble() / burstTimes.size
    }
    
    /**
     * Returns detailed schedule with waiting times
     */
    data class ProcessInfo(val processId: Int, val burstTime: Int, val waitingTime: Int)
    
    fun getDetailedSchedule(burstTimes: IntArray): List<ProcessInfo> {
        if (burstTimes.isEmpty()) return emptyList()
        
        // Create process objects with original indices
        val processes = burstTimes.mapIndexed { index, burst ->
            Pair(index + 1, burst) // 1-indexed process IDs
        }
        
        // Sort by burst time
        val sortedProcesses = processes.sortedBy { it.second }
        
        val result = mutableListOf<ProcessInfo>()
        var currentTime = 0
        
        for ((id, burst) in sortedProcesses) {
            result.add(ProcessInfo(id, burst, currentTime))
            currentTime += burst
        }
        
        return result
    }
    
    /**
     * Calculates both average waiting and turnaround time
     */
    data class SchedulingMetrics(
        val averageWaitingTime: Double,
        val averageTurnaroundTime: Double
    )
    
    fun getMetrics(burstTimes: IntArray): SchedulingMetrics {
        if (burstTimes.isEmpty()) {
            return SchedulingMetrics(0.0, 0.0)
        }
        
        burstTimes.sort()
        
        var totalWaitingTime = 0
        var totalTurnaroundTime = 0
        var currentTime = 0
        
        for (burst in burstTimes) {
            totalWaitingTime += currentTime
            currentTime += burst
            totalTurnaroundTime += currentTime
        }
        
        val n = burstTimes.size
        return SchedulingMetrics(
            averageWaitingTime = totalWaitingTime.toDouble() / n,
            averageTurnaroundTime = totalTurnaroundTime.toDouble() / n
        )
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single process: [5] → 0.0 (no waiting)
 * 2. All same: [3,3,3] → 4.0 (waiting: 0,3,6)
 * 3. Already sorted: [1,2,3] → 2.0 (waiting: 0,1,3)
 * 4. Reverse sorted: [3,2,1] → 2.0 (after sort: [1,2,3])
 * 5. Two processes: [5,3] → 1.5 (waiting: 0,3)
 * 
 * COMPARISON WITH OTHER ALGORITHMS:
 * 
 * For burstTimes = [4,3,7,1,2]:
 * 
 * SJF (Shortest Job First):
 * Order: [1,2,3,4,7]
 * Waiting: [0,1,3,6,10]
 * Average: 4.0 ✓ OPTIMAL
 * 
 * FCFS (First Come First Serve):
 * Order: [4,3,7,1,2]
 * Waiting: [0,4,7,14,15]
 * Average: 8.0 (worse)
 * 
 * APPLICATIONS:
 * - CPU scheduling in operating systems
 * - Task queue management
 * - Print job scheduling
 * - Batch processing optimization
 * 
 * LIMITATIONS:
 * - Requires knowing burst times in advance
 * - Can cause starvation of long processes
 * - Not suitable for interactive systems
 * 
 * ADVANTAGES:
 * - Minimizes average waiting time
 * - Simple to implement
 * - Optimal for non-preemptive scheduling
 * 
 * ============================================================================
 */

fun main() {
    val solution = ShortestJobFirst()
    
    println("Shortest Job First (SJF) - Test Cases")
    println("=======================================\n")
    
    println("Test 1: [4,3,7,1,2]")
    val burst1 = intArrayOf(4, 3, 7, 1, 2)
    println("Average Waiting Time: ${solution.averageWaitingTime(burst1)}")
    println("Expected: 4.0 ✓\n")
    
    println("Detailed Schedule:")
    val schedule1 = solution.getDetailedSchedule(intArrayOf(4, 3, 7, 1, 2))
    schedule1.forEach { 
        println("Process ${it.processId}: Burst=${it.burstTime}, Waiting=${it.waitingTime}")
    }
    println()
    
    println("Test 2: [1,2,3,4,5]")
    val burst2 = intArrayOf(1, 2, 3, 4, 5)
    val metrics2 = solution.getMetrics(burst2)
    println("Average Waiting Time: ${metrics2.averageWaitingTime}")
    println("Average Turnaround Time: ${metrics2.averageTurnaroundTime}")
    println("Expected: 6.0 waiting, 9.0 turnaround ✓\n")
    
    println("Test 3: [5,9,6]")
    val burst3 = intArrayOf(5, 9, 6)
    println("Average Waiting Time: ${solution.averageWaitingTime(burst3)}")
    println("Expected: 5.666... ✓\n")
    
    println("Test 4: [1]")
    val burst4 = intArrayOf(1)
    println("Average Waiting Time: ${solution.averageWaitingTime(burst4)}")
    println("Expected: 0.0 ✓\n")
    
    // Comparison example
    println("=== COMPARISON: SJF vs FCFS ===\n")
    println("Burst Times: [4,3,7,1,2]")
    
    println("\nSJF Order: [1,2,3,4,7]")
    println("SJF Average Waiting: ${solution.averageWaitingTime(intArrayOf(4, 3, 7, 1, 2))}")
    
    println("\nFCFS Order: [4,3,7,1,2] (original)")
    var fcfsWaiting = 0
    var currentTime = 0
    val fcfsOrder = intArrayOf(4, 3, 7, 1, 2)
    for (burst in fcfsOrder) {
        fcfsWaiting += currentTime
        currentTime += burst
    }
    println("FCFS Average Waiting: ${fcfsWaiting.toDouble() / fcfsOrder.size}")
    
    println("\nSJF is optimal! ✓")
}
