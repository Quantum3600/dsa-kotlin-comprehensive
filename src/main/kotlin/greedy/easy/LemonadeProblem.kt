/**
 * ============================================================================
 * PROBLEM: Lemonade Change
 * DIFFICULTY: Easy
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You're selling lemonade for $5. Customers pay with $5, $10, or $20 bills.
 * You must provide correct change. Initially you have no change.
 * Return true if you can provide change to every customer, false otherwise.
 * 
 * INPUT FORMAT:
 * - bills: [5, 5, 5, 10, 20] (bills received from each customer)
 * 
 * OUTPUT FORMAT:
 * - Boolean: true if can provide change to all, false otherwise
 * 
 * CONSTRAINTS:
 * - 1 <= bills.length <= 10^5
 * - bills[i] is either 5, 10, or 20
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Greedy approach - simulate the process and track bills.
 * For $10 bill: give back $5
 * For $20 bill: prefer giving $10+$5 over three $5s (keep $5s for flexibility)
 * 
 * ALGORITHM STEPS:
 * 1. Track count of $5 and $10 bills
 * 2. For each customer:
 *    - If $5: accept, increment count
 *    - If $10: need one $5 as change, decrement $5, increment $10
 *    - If $20: prefer $10+$5, else three $5s
 * 3. Return false if can't make change at any point
 * 
 * VISUAL EXAMPLE:
 * bills = [5, 5, 5, 10, 20]
 * 
 * Customer 0: $5 → five=1, ten=0
 * Customer 1: $5 → five=2, ten=0
 * Customer 2: $5 → five=3, ten=0
 * Customer 3: $10 → give $5, five=2, ten=1
 * Customer 4: $20 → give $10+$5, five=1, ten=0
 * Result: true ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Greedy simulation (used here): O(n) time, O(1) space - OPTIMAL
 * 2. Backtracking: Exponential time - Not feasible
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Iterate through bills array once: O(n)
 * - Each operation takes O(1) time
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only tracking two counters (five, ten)
 * 
 * ============================================================================
 */

package greedy.easy

class LemonadeProblem {
    
    /**
     * Checks if we can provide change to all customers
     */
    fun lemonadeChange(bills: IntArray): Boolean {
        var five = 0
        var ten = 0
        
        for (bill in bills) {
            when (bill) {
                5 -> {
                    five++
                }
                10 -> {
                    if (five == 0) return false
                    five--
                    ten++
                }
                20 -> {
                    // Prefer giving $10 + $5 (keep $5s for flexibility)
                    if (ten > 0 && five > 0) {
                        ten--
                        five--
                    } else if (five >= 3) {
                        five -= 3
                    } else {
                        return false
                    }
                }
            }
        }
        
        return true
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. All $5 bills: [5,5,5] → true
 * 2. First customer $10: [10] → false
 * 3. First customer $20: [20] → false
 * 4. Can't make change: [5,5,10,10,20] → false
 * 5. Single $5: [5] → true
 * 6. Exact change needed: [5,5,10,20] → true
 * 
 * APPLICATIONS:
 * - Cash register simulation
 * - Change-making problems
 * - Real-time transaction processing
 * 
 * ============================================================================
 */

fun main() {
    val solution = LemonadeProblem()
    
    println("Lemonade Change - Test Cases")
    println("==============================\n")
    
    println("Test 1: [5,5,5,10,20]")
    println("Result: ${solution.lemonadeChange(intArrayOf(5, 5, 5, 10, 20))}")
    println("Expected: true ✓\n")
    
    println("Test 2: [5,5,10,10,20]")
    println("Result: ${solution.lemonadeChange(intArrayOf(5, 5, 10, 10, 20))}")
    println("Expected: false ✓\n")
    
    println("Test 3: [5,5,10]")
    println("Result: ${solution.lemonadeChange(intArrayOf(5, 5, 10))}")
    println("Expected: true ✓\n")
    
    println("Test 4: [10,10]")
    println("Result: ${solution.lemonadeChange(intArrayOf(10, 10))}")
    println("Expected: false ✓\n")
}
