/**
 * ============================================================================
 * PROBLEM: Asteroid Collision
 * DIFFICULTY: Medium
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * We are given an array asteroids of integers representing asteroids in a row.
 * For each asteroid, the absolute value represents its size, and the sign 
 * represents its direction (positive = right, negative = left). Each asteroid 
 * moves at the same speed.
 * 
 * Find out the state of the asteroids after all collisions. If two asteroids 
 * meet, the smaller one will explode. If both are the same size, both explode. 
 * Two asteroids moving in the same direction will never meet.
 * 
 * INPUT FORMAT:
 * - asteroids: Array of integers (non-zero)
 * Example: asteroids = [5, 10, -5]
 * 
 * OUTPUT FORMAT:
 * - Array of remaining asteroids after all collisions
 * Example: [5, 10]
 * 
 * CONSTRAINTS:
 * - 2 <= asteroids.length <= 10^4
 * - -1000 <= asteroids[i] <= 1000
 * - asteroids[i] != 0
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Asteroids moving right (positive) are candidates to collide with 
 * asteroids moving left (negative).
 * 
 * KEY OBSERVATIONS:
 * 1. Only collision: right-moving meets left-moving (positive then negative)
 * 2. Left-moving followed by right-moving: No collision (moving apart)
 * 3. Same direction: No collision
 * 
 * COLLISION RULES:
 * - If |positive| > |negative|: negative explodes
 * - If |positive| < |negative|: positive explodes
 * - If |positive| == |negative|: both explode
 * 
 * VISUAL EXAMPLE:
 * ```
 * asteroids = [5, 10, -5]
 * 
 * Initial state:
 * 5→  10→  ←5
 * 
 * Step 1: 5→ and 10→ don't collide (same direction)
 * Step 2: 10→ meets ←5
 *   Collision! |10| > |-5|, so -5 explodes
 * 
 * Final state:
 * 5→  10→
 * Result: [5, 10]
 * ```
 * 
 * ANOTHER EXAMPLE:
 * ```
 * asteroids = [8, -8]
 * 
 * 8→  ←8
 * Collision! |8| == |-8|, both explode
 * 
 * Result: []
 * ```
 * 
 * COMPLEX EXAMPLE:
 * ```
 * asteroids = [10, 2, -5]
 * 
 * Initial: 10→  2→  ←5
 * 
 * Step 1: 10→ and 2→ don't collide
 * Step 2: 2→ meets ←5
 *   Collision! |2| < |-5|, so 2 explodes
 *   Stack: [10], current: -5
 * Step 3: 10→ meets ←5
 *   Collision! |10| > |-5|, so -5 explodes
 * 
 * Result: [10]
 * ```
 * 
 * ALGORITHM USING STACK:
 * 1. Iterate through asteroids
 * 2. For each asteroid:
 *    a. If positive OR stack empty OR stack.top is negative:
 *       - No collision, push to stack
 *    b. If negative AND stack.top is positive:
 *       - Collision! Process collisions:
 *         - While stack not empty AND stack.top > 0 AND stack.top < |current|:
 *           Pop from stack (explodes)
 *         - If stack empty OR stack.top < 0:
 *           Push current (survived all collisions)
 *         - Else if stack.top == |current|:
 *           Pop from stack (both explode)
 *         - Else (stack.top > |current|):
 *           Current explodes, don't push
 * 3. Return stack as result
 * 
 * DETAILED TRACE:
 * ```
 * asteroids = [5, 10, -5]
 * stack = []
 * 
 * Process 5:
 *   Positive, push
 *   stack = [5]
 * 
 * Process 10:
 *   Positive, push
 *   stack = [5, 10]
 * 
 * Process -5:
 *   Negative, check collisions
 *   stack.top = 10 > 0 (right-moving)
 *   Collision! |10| > |-5| (10 > 5)
 *   -5 explodes, don't push
 *   stack = [5, 10]
 * 
 * Result: [5, 10] ✓
 * ```
 * 
 * ANOTHER TRACE:
 * ```
 * asteroids = [10, 2, -5]
 * stack = []
 * 
 * Process 10: stack = [10]
 * Process 2: stack = [10, 2]
 * 
 * Process -5:
 *   Collision with 2: |2| < |-5|, pop 2
 *   stack = [10]
 *   Collision with 10: |10| > |-5|, -5 explodes
 *   stack = [10]
 * 
 * Result: [10] ✓
 * ```
 * 
 * WHY STACK:
 * We need to check if the current left-moving asteroid collides with 
 * previous right-moving asteroids. Stack naturally maintains this order
 * and allows us to process collisions as we go.
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Simulation: Keep processing until no collisions - O(n²) worst case
 * 2. Stack: Process each asteroid once - O(n)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Each asteroid pushed at most once
 * - Each asteroid popped at most once
 * - Total operations: 2n = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Stack can hold up to n asteroids
 * - Worst case: All asteroids moving same direction
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*

class AsteroidCollision {
    
    /**
     * Main solution using stack
     */
    fun asteroidCollision(asteroids: IntArray): IntArray {
        val stack = Stack<Int>()
        
        for (asteroid in asteroids) {
            var current = asteroid
            var destroyed = false
            
            // Process collisions if current is moving left (negative)
            while (!destroyed && current < 0 && stack.isNotEmpty() && stack.peek() > 0) {
                val top = stack.peek()
                
                when {
                    top < -current -> {
                        // Top explodes, current continues
                        stack.pop()
                    }
                    top == -current -> {
                        // Both explode
                        stack.pop()
                        destroyed = true
                    }
                    else -> {
                        // Current explodes
                        destroyed = true
                    }
                }
            }
            
            // If current survived, add to stack
            if (!destroyed) {
                stack.push(current)
            }
        }
        
        // Convert stack to array
        return stack.toIntArray()
    }
    
    /**
     * Alternative implementation with cleaner logic
     */
    fun asteroidCollisionCleaner(asteroids: IntArray): IntArray {
        val stack = ArrayList<Int>()
        
        for (asteroid in asteroids) {
            if (asteroid > 0) {
                // Right-moving, always add
                stack.add(asteroid)
            } else {
                // Left-moving, check for collisions
                while (stack.isNotEmpty() && stack.last() > 0 && stack.last() < -asteroid) {
                    stack.removeAt(stack.size - 1)
                }
                
                if (stack.isEmpty() || stack.last() < 0) {
                    // No right-moving asteroids or all moving left
                    stack.add(asteroid)
                } else if (stack.last() == -asteroid) {
                    // Equal size, both explode
                    stack.removeAt(stack.size - 1)
                }
                // else: current explodes, don't add
            }
        }
        
        return stack.toIntArray()
    }
}

/**
 * ============================================================================
 * EDGE CASES & TESTING
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. No collisions (all positive): [5, 10, 15] → [5, 10, 15]
 * 2. No collisions (all negative): [-5, -10, -15] → [-5, -10, -15]
 * 3. No collisions (negative then positive): [-5, 10, 15] → [-5, 10, 15]
 * 4. All explode: [5, -5] → []
 * 5. Chain reaction: [10, 2, -5] → [10]
 * 6. Left survives: [5, -10] → [-10]
 * 7. Multiple left: [1, 2, 3, -10] → [-10]
 * 8. Complex: [5, 10, -5, -10, 15] → [15]
 * 
 * TRACE: asteroids = [8, -8]
 * 
 * Process 8: stack = [8]
 * Process -8:
 *   Collision with 8: |8| == |-8|, both explode
 *   stack = []
 * 
 * Result: [] ✓
 * 
 * TRACE: asteroids = [-2, -1, 1, 2]
 * 
 * Process -2: negative, stack empty, push, stack = [-2]
 * Process -1: negative, stack.top=-2 < 0, no collision, push, stack = [-2, -1]
 * Process 1: positive, push, stack = [-2, -1, 1]
 * Process 2: positive, push, stack = [-2, -1, 1, 2]
 * 
 * Result: [-2, -1, 1, 2] ✓ (no collisions)
 * 
 * TRACE: asteroids = [10, 2, -5]
 * 
 * Process 10: stack = [10]
 * Process 2: stack = [10, 2]
 * Process -5:
 *   While loop: stack.top=2, 2 < 5, pop 2
 *   stack = [10]
 *   While loop: stack.top=10, 10 > 5, exit loop
 *   -5 explodes
 * 
 * Result: [10] ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = AsteroidCollision()
    
    println("Asteroid Collision - Test Cases")
    println("=================================\n")
    
    // Test 1: Basic collision
    println("Test 1: Basic Collision")
    val ast1 = intArrayOf(5, 10, -5)
    println("Input: ${ast1.contentToString()}")
    println("Visual: 5→  10→  ←5")
    println("10 collides with -5: 10 survives")
    println("Output: ${solution.asteroidCollision(ast1).contentToString()}")
    println("Expected: [5, 10]\n")
    
    // Test 2: Both explode
    println("Test 2: Both Explode")
    val ast2 = intArrayOf(8, -8)
    println("Input: ${ast2.contentToString()}")
    println("Visual: 8→  ←8")
    println("Equal size: both explode")
    println("Output: ${solution.asteroidCollision(ast2).contentToString()}")
    println("Expected: []\n")
    
    // Test 3: Left survives
    println("Test 3: Left Asteroid Survives")
    val ast3 = intArrayOf(10, 2, -5)
    println("Input: ${ast3.contentToString()}")
    println("Visual: 10→  2→  ←5")
    println("-5 destroys 2, then destroyed by 10")
    println("Output: ${solution.asteroidCollision(ast3).contentToString()}")
    println("Expected: [10]\n")
    
    // Test 4: No collisions (all positive)
    println("Test 4: No Collisions (All Right)")
    val ast4 = intArrayOf(5, 10, 15)
    println("Input: ${ast4.contentToString()}")
    println("All moving right: no collisions")
    println("Output: ${solution.asteroidCollision(ast4).contentToString()}")
    println("Expected: [5, 10, 15]\n")
    
    // Test 5: No collisions (all negative)
    println("Test 5: No Collisions (All Left)")
    val ast5 = intArrayOf(-5, -10, -15)
    println("Input: ${ast5.contentToString()}")
    println("All moving left: no collisions")
    println("Output: ${solution.asteroidCollision(ast5).contentToString()}")
    println("Expected: [-5, -10, -15]\n")
    
    // Test 6: No collisions (moving apart)
    println("Test 6: No Collisions (Moving Apart)")
    val ast6 = intArrayOf(-2, -1, 1, 2)
    println("Input: ${ast6.contentToString()}")
    println("Left ones move left, right ones move right")
    println("Output: ${solution.asteroidCollision(ast6).contentToString()}")
    println("Expected: [-2, -1, 1, 2]\n")
    
    // Test 7: Chain reaction
    println("Test 7: Chain Reaction")
    val ast7 = intArrayOf(1, 2, 3, -10)
    println("Input: ${ast7.contentToString()}")
    println("-10 destroys all right-moving asteroids")
    println("Output: ${solution.asteroidCollision(ast7).contentToString()}")
    println("Expected: [-10]\n")
    
    // Test 8: Complex case
    println("Test 8: Complex Collisions")
    val ast8 = intArrayOf(5, -5, 10, -10)
    println("Input: ${ast8.contentToString()}")
    println("5 and -5 explode, 10 and -10 explode")
    println("Output: ${solution.asteroidCollision(ast8).contentToString()}")
    println("Expected: []\n")
    
    // Test 9: Large left survives
    println("Test 9: Large Left Asteroid")
    val ast9 = intArrayOf(5, 10, -15)
    println("Input: ${ast9.contentToString()}")
    println("-15 destroys both 10 and 5")
    println("Output: ${solution.asteroidCollision(ast9).contentToString()}")
    println("Expected: [-15]\n")
    
    // Test 10: Mixed directions
    println("Test 10: Mixed Directions")
    val ast10 = intArrayOf(-2, 2, 1, -2)
    println("Input: ${ast10.contentToString()}")
    println("Output: ${solution.asteroidCollision(ast10).contentToString()}")
    println("Expected: [-2]\n")
    
    // Compare implementations
    println("=== Implementation Comparison ===")
    println("Standard: ${solution.asteroidCollision(ast1).contentToString()}")
    println("Cleaner: ${solution.asteroidCollisionCleaner(ast1).contentToString()}")
    println("Both should return: [5, 10]\n")
    
    println("=== Collision Rules ===")
    println("Collision happens when: Right-moving meets Left-moving")
    println("  (positive value followed by negative value in processing)")
    println()
    println("Outcome:")
    println("  |positive| > |negative| → negative explodes")
    println("  |positive| < |negative| → positive explodes")
    println("  |positive| = |negative| → both explode")
    println()
    
    println("=== No Collision Cases ===")
    println("1. All moving right (all positive)")
    println("2. All moving left (all negative)")
    println("3. Left-moving before right-moving (moving apart)")
    println("4. Same direction always")
    println()
    
    println("=== Why Use Stack? ===")
    println("Stack maintains order of asteroids.")
    println("When left-moving asteroid arrives:")
    println("  - Check previous right-moving asteroids")
    println("  - Process collisions one by one")
    println("  - Stack naturally handles chain reactions")
    println()
    
    println("=== Performance ===")
    println("Time: O(n) - Each asteroid processed once")
    println("Space: O(n) - Stack storage")
}
