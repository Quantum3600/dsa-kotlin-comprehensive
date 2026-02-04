/**
 * ============================================================================
 * PROBLEM: Tower of Hanoi
 * DIFFICULTY: Hard
 * CATEGORY: Recursion/Backtracking
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * The Tower of Hanoi is a classic puzzle with three rods and N disks of
 * different sizes. The objective is to move the entire stack from the source
 * rod to the destination rod, following these rules:
 * 1. Only one disk can be moved at a time
 * 2. A disk can only be placed on top of a larger disk or on an empty rod
 * 3. Only the top disk of a stack can be moved
 * 
 * INPUT FORMAT:
 * - N: Number of disks (positive integer)
 * - source: Source rod name (e.g., "A")
 * - destination: Destination rod name (e.g., "C")
 * - auxiliary: Auxiliary rod name (e.g., "B")
 * 
 * OUTPUT FORMAT:
 * - Sequence of moves to transfer all disks
 * - Each move: "Move disk N from X to Y"
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 20
 * - Three distinct rod names
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The key insight is to break down the problem recursively:
 * - To move N disks from A to C using B:
 *   1. Move N-1 disks from A to B using C (recursive)
 *   2. Move the largest disk from A to C
 *   3. Move N-1 disks from B to C using A (recursive)
 * 
 * VISUAL EXAMPLE (N = 3):
 * 
 * Initial:        Goal:
 *   |               |               |
 *   1               |               |
 *   2               |               |
 *   3               |               |
 *  ---             ---             ---
 *   A               B               C
 * 
 * Step-by-step moves:
 * Move 1 from A to C
 * Move 2 from A to B
 * Move 1 from C to B
 * Move 3 from A to C
 * Move 1 from B to A
 * Move 2 from B to C
 * Move 1 from A to C
 * 
 * ALGORITHM STEPS:
 * 1. Base case: If N = 1, move disk from source to destination
 * 2. Recursive case for N disks:
 *    a. Move N-1 disks from source to auxiliary using destination
 *    b. Move disk N from source to destination
 *    c. Move N-1 disks from auxiliary to destination using source
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Recursive (implemented) - Elegant and intuitive
 * 2. Iterative with stack - More complex but avoids recursion
 * 3. Bit manipulation - Mathematical approach for finding moves
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(2^N)
 * - For N disks, we make 2^N - 1 moves
 * - Recurrence: T(N) = 2*T(N-1) + 1
 * - T(1) = 1
 * - T(N) = 2^N - 1
 * 
 * SPACE COMPLEXITY: O(N)
 * - Recursion stack depth is N
 * - No additional data structures used
 * 
 * ============================================================================
 */

package recursion.hard

class TowerOfHanoi {
    
    private var moveCount = 0
    private val moves = mutableListOf<String>()
    
    /**
     * Solve Tower of Hanoi puzzle
     * TIME: O(2^N), SPACE: O(N) for recursion stack
     * 
     * @param n Number of disks
     * @param source Source rod name
     * @param destination Destination rod name
     * @param auxiliary Auxiliary rod name
     * @return List of moves to solve the puzzle
     */
    fun solve(n: Int, source: String = "A", destination: String = "C", auxiliary: String = "B"): List<String> {
        moveCount = 0
        moves.clear()
        
        if (n <= 0) return emptyList()
        
        solveRecursive(n, source, destination, auxiliary)
        return moves.toList()
    }
    
    /**
     * Recursive helper function
     */
    private fun solveRecursive(n: Int, source: String, destination: String, auxiliary: String) {
        // Base case: only one disk to move
        if (n == 1) {
            val move = "Move disk 1 from $source to $destination"
            moves.add(move)
            moveCount++
            return
        }
        
        // Step 1: Move N-1 disks from source to auxiliary using destination
        solveRecursive(n - 1, source, auxiliary, destination)
        
        // Step 2: Move the largest disk from source to destination
        val move = "Move disk $n from $source to $destination"
        moves.add(move)
        moveCount++
        
        // Step 3: Move N-1 disks from auxiliary to destination using source
        solveRecursive(n - 1, auxiliary, destination, source)
    }
    
    /**
     * Get total number of moves required
     * Formula: 2^N - 1
     */
    fun getMinimumMoves(n: Int): Int {
        if (n <= 0) return 0
        return (1 shl n) - 1  // 2^n - 1
    }
    
    /**
     * Solve and print moves
     */
    fun solveAndPrint(n: Int, source: String = "A", destination: String = "C", auxiliary: String = "B") {
        println("Solving Tower of Hanoi with $n disks")
        println("From: $source -> To: $destination (using $auxiliary)")
        println("Minimum moves required: ${getMinimumMoves(n)}\n")
        
        val moves = solve(n, source, destination, auxiliary)
        moves.forEachIndexed { index, move ->
            println("${index + 1}. $move")
        }
        println("\nTotal moves: ${moves.size}")
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: N = 2, A -> C using B
 * 
 * solveRecursive(2, A, C, B):
 *   ├─ solveRecursive(1, A, B, C):
 *   │    └─ Move disk 1 from A to B
 *   ├─ Move disk 2 from A to C
 *   └─ solveRecursive(1, B, C, A):
 *        └─ Move disk 1 from B to C
 * 
 * OUTPUT:
 * 1. Move disk 1 from A to B
 * 2. Move disk 2 from A to C
 * 3. Move disk 1 from B to C
 * 
 * ============================================================================
 * MATHEMATICAL PROOF
 * ============================================================================
 * 
 * Minimum moves for N disks = 2^N - 1
 * 
 * Proof by induction:
 * Base case: N=1, moves = 1 = 2^1 - 1 ✓
 * 
 * Inductive step:
 * Assume true for N-1: moves(N-1) = 2^(N-1) - 1
 * For N disks:
 *   - Move N-1 disks: 2^(N-1) - 1 moves
 *   - Move largest disk: 1 move
 *   - Move N-1 disks again: 2^(N-1) - 1 moves
 *   Total = 2*(2^(N-1) - 1) + 1 = 2^N - 2 + 1 = 2^N - 1 ✓
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val hanoi = TowerOfHanoi()
    
    println("=== Tower of Hanoi ===\n")
    
    // Test 1: Single disk (base case)
    println("Test 1: 1 disk")
    hanoi.solveAndPrint(1)
    println("\n" + "=".repeat(60) + "\n")
    
    // Test 2: Two disks
    println("Test 2: 2 disks")
    hanoi.solveAndPrint(2)
    println("\n" + "=".repeat(60) + "\n")
    
    // Test 3: Three disks (classic example)
    println("Test 3: 3 disks")
    hanoi.solveAndPrint(3)
    println("\n" + "=".repeat(60) + "\n")
    
    // Test 4: Four disks
    println("Test 4: 4 disks")
    hanoi.solveAndPrint(4)
    println("\n" + "=".repeat(60) + "\n")
    
    // Test 5: Different rod names
    println("Test 5: 3 disks with custom rod names")
    hanoi.solveAndPrint(3, "Source", "Destination", "Helper")
    println("\n" + "=".repeat(60) + "\n")
    
    // Test 6: Calculate minimum moves
    println("Test 6: Minimum moves calculation")
    for (n in 1..10) {
        println("N=$n disks: ${hanoi.getMinimumMoves(n)} moves")
    }
}
