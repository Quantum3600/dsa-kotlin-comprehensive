/**
 * ============================================================================
 * PROBLEM: Print a Name N Times using Recursion
 * DIFFICULTY: Easy
 * CATEGORY: Recursion/Basic
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a name (string) and a positive integer N, print the name N times
 * using recursion. Each name should be printed on a new line.
 * 
 * INPUT FORMAT:
 * - A string name
 * - A positive integer N
 * - Example: name = "Raj", N = 3
 * 
 * OUTPUT FORMAT:
 * - Print the name N times, one per line
 * - Example:
 *   Raj
 *   Raj
 *   Raj
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 1000
 * - 1 <= name.length <= 100
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Recursion means a function calling itself. For this problem:
 * - If N = 3, print name once, then print it 2 more times
 * - If N = 2, print name once, then print it 1 more time
 * - If N = 1, print name once, then stop
 * - If N = 0, stop (base case)
 * 
 * RECURSIVE THINKING:
 * To print name N times:
 * 1. Print the name once
 * 2. Print it N-1 more times (recursive call)
 * 
 * BASE CASE: When N = 0, don't print anything
 * RECURSIVE CASE: Print name, then call function with N-1
 * 
 * VISUAL EXAMPLE (N = 3):
 * 
 * printNameNTimes("Raj", 3)
 *   ├─ Print "Raj"
 *   └─ printNameNTimes("Raj", 2)
 *        ├─ Print "Raj"
 *        └─ printNameNTimes("Raj", 1)
 *             ├─ Print "Raj"
 *             └─ printNameNTimes("Raj", 0)
 *                  └─ return (base case)
 * 
 * ALGORITHM STEPS:
 * 1. Check base case: if N == 0, return
 * 2. Print the name
 * 3. Recursively call with N-1
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Recursion (Top-down): Print then recurse - IMPLEMENTED
 * 2. Recursion (Bottom-up): Recurse then print - Also valid
 * 3. Iteration: Use a loop - Not recursive, defeats purpose
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(N)
 * - Function is called N times
 * - Each call does O(1) work (printing)
 * - Total: N * O(1) = O(N)
 * 
 * SPACE COMPLEXITY: O(N)
 * - Recursion depth is N
 * - Each call takes space on call stack
 * - Maximum stack depth = N
 * - Each stack frame has constant space
 * - Total: O(N) stack space
 * 
 * WHY RECURSION USES STACK SPACE:
 * - Each function call waits for the next to complete
 * - All N calls exist on the stack simultaneously
 * - After base case, they unwind one by one
 * 
 * ============================================================================
 */

package basics.recursion

class PrintNameNTimes {
    
    /**
     * Print name N times using recursion (top-down)
     * TIME: O(N), SPACE: O(N) stack space
     * 
     * @param name The name to print
     * @param n Number of times to print
     */
    fun printName(name: String, n: Int) {
        // Base case: when n reaches 0, stop
        if (n == 0) {
            return
        }
        
        // Print the name
        println(name)
        
        // Recursive call with n-1
        printName(name, n - 1)
    }
    
    /**
     * Print name N times using recursion (bottom-up)
     * TIME: O(N), SPACE: O(N)
     * 
     * First recurse, then print (reverse order of execution)
     */
    fun printNameBottomUp(name: String, n: Int) {
        // Base case
        if (n == 0) {
            return
        }
        
        // Recursive call first (goes deep)
        printNameBottomUp(name, n - 1)
        
        // Print after recursive call returns
        println(name)
    }
    
    /**
     * Print name N times with counter (starting from 1)
     * TIME: O(N), SPACE: O(N)
     * 
     * Helper function that uses a counter
     */
    fun printNameWithCounter(name: String, n: Int) {
        printNameHelper(name, 1, n)
    }
    
    /**
     * Helper function with current count parameter
     */
    private fun printNameHelper(name: String, current: Int, target: Int) {
        // Base case: when current exceeds target
        if (current > target) {
            return
        }
        
        // Print with counter
        println("$current. $name")
        
        // Recursive call with incremented counter
        printNameHelper(name, current + 1, target)
    }
    
    /**
     * Print name N times with tail recursion optimization
     * TIME: O(N), SPACE: O(1) with tail call optimization
     * 
     * Kotlin optimizes tail recursive calls
     */
    tailrec fun printNameTailRec(name: String, n: Int) {
        // Base case
        if (n == 0) {
            return
        }
        
        // Print the name
        println(name)
        
        // Tail recursive call (last operation)
        printNameTailRec(name, n - 1)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: name = "Raj", n = 3
 * 
 * CALL STACK EVOLUTION:
 * 
 * Call 1: printName("Raj", 3)
 * - n = 3, not 0, so continue
 * - Print "Raj"
 * - Call printName("Raj", 2)
 * 
 * Call 2: printName("Raj", 2)
 * - n = 2, not 0, so continue
 * - Print "Raj"
 * - Call printName("Raj", 1)
 * 
 * Call 3: printName("Raj", 1)
 * - n = 1, not 0, so continue
 * - Print "Raj"
 * - Call printName("Raj", 0)
 * 
 * Call 4: printName("Raj", 0)
 * - n = 0, BASE CASE reached
 * - Return immediately
 * 
 * UNWINDING:
 * Call 4 returns → Call 3 completes → Call 2 completes → Call 1 completes
 * 
 * OUTPUT:
 * Raj
 * Raj
 * Raj
 * 
 * CALL STACK DIAGRAM:
 * 
 * printName("Raj", 3)  ←─ Initial call
 *   |
 *   └─→ printName("Raj", 2)
 *         |
 *         └─→ printName("Raj", 1)
 *               |
 *               └─→ printName("Raj", 0) [BASE CASE, returns]
 * 
 * ============================================================================
 * RECURSION CONCEPTS
 * ============================================================================
 * 
 * KEY COMPONENTS OF RECURSION:
 * 1. BASE CASE: Condition to stop recursion (n == 0)
 * 2. RECURSIVE CASE: Function calls itself with modified input (n-1)
 * 3. PROGRESS: Each call moves toward base case (n decreases)
 * 
 * WHY THIS WORKS:
 * - Each recursive call reduces n by 1
 * - Eventually, n becomes 0 (base case)
 * - Base case prevents infinite recursion
 * - Stack unwinds after base case
 * 
 * TAIL RECURSION:
 * - When recursive call is the LAST operation
 * - Compiler can optimize to iteration
 * - No need to keep stack frames
 * - Kotlin's 'tailrec' keyword enables this
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = PrintNameNTimes()
    
    println("=== Print Name N Times using Recursion ===\n")
    
    // Test 1: Normal case
    println("Test 1: Print 'Raj' 3 times")
    solution.printName("Raj", 3)
    println()
    
    // Test 2: Single time
    println("Test 2: Print 'Alice' 1 time")
    solution.printName("Alice", 1)
    println()
    
    // Test 3: Multiple times
    println("Test 3: Print 'Bob' 5 times")
    solution.printName("Bob", 5)
    println()
    
    // Test 4: Zero times (base case)
    println("Test 4: Print 'Charlie' 0 times (should print nothing)")
    solution.printName("Charlie", 0)
    println("(no output expected)")
    println()
    
    // Test 5: Bottom-up approach
    println("Test 5: Bottom-up approach - Print 'Dana' 3 times")
    solution.printNameBottomUp("Dana", 3)
    println()
    
    // Test 6: With counter
    println("Test 6: Print 'Eve' 4 times with counter")
    solution.printNameWithCounter("Eve", 4)
    println()
    
    // Test 7: Tail recursion
    println("Test 7: Tail recursive - Print 'Frank' 3 times")
    solution.printNameTailRec("Frank", 3)
    println()
    
    // Test 8: Longer name
    println("Test 8: Print 'Alexander' 2 times")
    solution.printName("Alexander", 2)
    println()
    
    // Demonstrate call stack depth
    println("=== Recursion Depth Demo ===")
    println("Printing 'Test' 10 times (10 recursive calls)")
    solution.printName("Test", 10)
}
