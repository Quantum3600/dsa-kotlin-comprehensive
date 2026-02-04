package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Kth Permutation Sequence
 * DIFFICULTY: Hard
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * The set [1, 2, 3, ..., n] contains a total of n! unique permutations.
 * By listing and labeling all of the permutations in order, we get the
 * following sequence for n = 3:
 * 
 * 1. "123"
 * 2. "132"
 * 3. "213"
 * 4. "231"
 * 5. "312"
 * 6. "321"
 * 
 * Given n and k, return the kth permutation sequence.
 * 
 * INPUT FORMAT:
 * - n: Integer representing the set size
 * - k: The kth permutation to find (1-indexed)
 * 
 * OUTPUT FORMAT:
 * - String representing the kth permutation
 * Example: "213"
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 9
 * - 1 <= k <= n!
 * 
 * EXAMPLES:
 * Input: n = 3, k = 3
 * Output: "213"
 * 
 * Input: n = 4, k = 9
 * Output: "2314"
 * 
 * Input: n = 3, k = 1
 * Output: "123"
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of generating all permutations and picking the kth one (which is
 * expensive), we can DIRECTLY CONSTRUCT the kth permutation using mathematics.
 * 
 * KEY INSIGHT - Block Structure:
 * For n = 4, all permutations can be grouped by their first digit:
 * - First (n-1)! = 6 permutations start with '1': 1234, 1243, 1324, 1342, 1423, 1432
 * - Next 6 permutations start with '2': 2134, 2143, 2314, 2341, 2413, 2431
 * - Next 6 start with '3': 3124, 3142, 3214, 3241, 3412, 3421
 * - Last 6 start with '4': 4123, 4132, 4213, 4231, 4312, 4321
 * 
 * ALGORITHM STEPS:
 * 1. Precompute factorials: 0!, 1!, 2!, ..., n!
 * 2. Create a list of available numbers: [1, 2, 3, ..., n]
 * 3. Convert k to 0-indexed: k = k - 1
 * 4. For each position from left to right:
 *    a. Calculate which "block" k falls into: index = k / (n-1)!
 *    b. Pick the number at that index from available numbers
 *    c. Remove the picked number from available list
 *    d. Update k: k = k % (n-1)!
 *    e. Decrease n by 1
 * 5. Repeat until all positions filled
 * 
 * VISUAL EXAMPLE:
 * n = 4, k = 9 (looking for 9th permutation)
 * Available: [1, 2, 3, 4]
 * k = 9 - 1 = 8 (0-indexed)
 * 
 * Position 1:
 *   Block size = 3! = 6
 *   Block index = 8 / 6 = 1
 *   Pick available[1] = 2
 *   Available: [1, 3, 4]
 *   k = 8 % 6 = 2
 * 
 * Position 2:
 *   Block size = 2! = 2
 *   Block index = 2 / 2 = 1
 *   Pick available[1] = 3
 *   Available: [1, 4]
 *   k = 2 % 2 = 0
 * 
 * Position 3:
 *   Block size = 1! = 1
 *   Block index = 0 / 1 = 0
 *   Pick available[0] = 1
 *   Available: [4]
 *   k = 0 % 1 = 0
 * 
 * Position 4:
 *   Pick remaining: 4
 * 
 * Result: "2314"
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: Generate all permutations, return kth (O(n! * n))
 * 2. Backtracking with counter: Generate until count reaches k
 * 3. Next Permutation: Call next_permutation k-1 times
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n²)
 * - Precomputing factorials: O(n)
 * - Main loop runs n times
 * - Each iteration: 
 *   - Division/modulo: O(1)
 *   - Remove from list: O(n)
 * - Overall: O(n) + O(n * n) = O(n²)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Factorial array: O(n)
 * - Available numbers list: O(n)
 * - Result string: O(n)
 * - Overall: O(n)
 */

class KthPermutation {
    
    /**
     * Find the kth permutation sequence
     * @param n Size of the set [1..n]
     * @param k The kth permutation (1-indexed)
     * @return The kth permutation as a string
     */
    fun getPermutation(n: Int, k: Int): String {
        // Precompute factorials
        val factorial = IntArray(n)
        factorial[0] = 1
        for (i in 1 until n) {
            factorial[i] = factorial[i - 1] * i
        }
        
        // Create list of available numbers
        val numbers = mutableListOf<Int>()
        for (i in 1..n) {
            numbers.add(i)
        }
        
        // Convert k to 0-indexed
        var k = k - 1
        
        val result = StringBuilder()
        
        // Build the permutation digit by digit
        for (i in n - 1 downTo 0) {
            // Find which block k falls into
            val index = k / factorial[i]
            
            // Pick the number at that index
            result.append(numbers[index])
            
            // Remove the picked number
            numbers.removeAt(index)
            
            // Update k for next iteration
            k %= factorial[i]
        }
        
        return result.toString()
    }
    
    /**
     * Alternative: Using recursion
     */
    fun getPermutationRecursive(n: Int, k: Int): String {
        val factorial = IntArray(n)
        factorial[0] = 1
        for (i in 1 until n) {
            factorial[i] = factorial[i - 1] * i
        }
        
        val numbers = mutableListOf<Int>()
        for (i in 1..n) {
            numbers.add(i)
        }
        
        return buildPermutation(numbers, k - 1, factorial, StringBuilder()).toString()
    }
    
    private fun buildPermutation(
        numbers: MutableList<Int>,
        k: Int,
        factorial: IntArray,
        result: StringBuilder
    ): StringBuilder {
        // BASE CASE: Only one number left
        if (numbers.size == 1) {
            result.append(numbers[0])
            return result
        }
        
        val n = numbers.size
        val blockSize = factorial[n - 1]
        val index = k / blockSize
        
        // Pick the number
        result.append(numbers[index])
        numbers.removeAt(index)
        
        // Recurse for remaining positions
        return buildPermutation(numbers, k % blockSize, factorial, result)
    }
    
    /**
     * Brute force approach: Generate all permutations
     * WARNING: Only use for small n (n <= 8)
     */
    fun getPermutationBruteForce(n: Int, k: Int): String {
        val numbers = IntArray(n) { it + 1 }
        val permutations = mutableListOf<String>()
        
        generatePermutations(numbers, 0, permutations)
        permutations.sort()
        
        return permutations[k - 1]
    }
    
    private fun generatePermutations(
        numbers: IntArray,
        start: Int,
        result: MutableList<String>
    ) {
        if (start == numbers.size) {
            result.add(numbers.joinToString(""))
            return
        }
        
        for (i in start until numbers.size) {
            // Swap
            numbers[start] = numbers[i].also { numbers[i] = numbers[start] }
            
            // Recurse
            generatePermutations(numbers, start + 1, result)
            
            // Backtrack
            numbers[start] = numbers[i].also { numbers[i] = numbers[start] }
        }
    }
    
    /**
     * Using backtracking with early termination
     */
    fun getPermutationBacktracking(n: Int, k: Int): String {
        val numbers = mutableListOf<Int>()
        for (i in 1..n) {
            numbers.add(i)
        }
        
        val result = StringBuilder()
        var count = intArrayOf(0)  // Use array to pass by reference
        
        if (backtrack(numbers, mutableListOf(), result, k, count)) {
            return result.toString()
        }
        
        return ""
    }
    
    private fun backtrack(
        available: MutableList<Int>,
        current: MutableList<Int>,
        result: StringBuilder,
        k: Int,
        count: IntArray
    ): Boolean {
        // BASE CASE: Found complete permutation
        if (current.size == available.size + current.size) {
            count[0]++
            
            // Check if this is the kth permutation
            if (count[0] == k) {
                for (num in current) {
                    result.append(num)
                }
                return true
            }
            return false
        }
        
        // Try each available number
        for (i in available.indices) {
            val num = available[i]
            
            // Pick
            current.add(num)
            available.removeAt(i)
            
            // Recurse
            if (backtrack(available, current, result, k, count)) {
                return true
            }
            
            // Backtrack
            available.add(i, num)
            current.removeAt(current.size - 1)
        }
        
        return false
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: n = 4, k = 9
 * 
 * Step 1: Precompute factorials
 * factorial = [1, 1, 2, 6]
 * factorial[0] = 0! = 1
 * factorial[1] = 1! = 1
 * factorial[2] = 2! = 2
 * factorial[3] = 3! = 6
 * 
 * Step 2: Initialize
 * numbers = [1, 2, 3, 4]
 * k = 9 - 1 = 8 (convert to 0-indexed)
 * result = ""
 * 
 * Step 3: Build permutation
 * 
 * Iteration 1 (i = 3, n-1 = 3):
 *   factorial[3] = 6
 *   index = 8 / 6 = 1
 *   Pick numbers[1] = 2
 *   result = "2"
 *   numbers = [1, 3, 4]
 *   k = 8 % 6 = 2
 * 
 * Iteration 2 (i = 2):
 *   factorial[2] = 2
 *   index = 2 / 2 = 1
 *   Pick numbers[1] = 3
 *   result = "23"
 *   numbers = [1, 4]
 *   k = 2 % 2 = 0
 * 
 * Iteration 3 (i = 1):
 *   factorial[1] = 1
 *   index = 0 / 1 = 0
 *   Pick numbers[0] = 1
 *   result = "231"
 *   numbers = [4]
 *   k = 0 % 1 = 0
 * 
 * Iteration 4 (i = 0):
 *   factorial[0] = 1
 *   index = 0 / 1 = 0
 *   Pick numbers[0] = 4
 *   result = "2314"
 *   numbers = []
 *   k = 0 % 1 = 0
 * 
 * Final Result: "2314"
 * 
 * Verification:
 * All permutations of [1,2,3,4]:
 * 1. 1234  2. 1243  3. 1324  4. 1342  5. 1423  6. 1432
 * 7. 2134  8. 2143  9. 2314 ✓ (9th permutation)
 */

fun main() {
    val solver = KthPermutation()
    
    // Test Case 1: Basic case
    println("Test Case 1: n = 3, k = 3")
    println("Result: ${solver.getPermutation(3, 3)}")
    println("Expected: 213")
    println()
    
    // Test Case 2: Given example
    println("Test Case 2: n = 4, k = 9")
    println("Result: ${solver.getPermutation(4, 9)}")
    println("Expected: 2314")
    println()
    
    // Test Case 3: First permutation
    println("Test Case 3: n = 3, k = 1")
    println("Result: ${solver.getPermutation(3, 1)}")
    println("Expected: 123")
    println()
    
    // Test Case 4: Last permutation
    println("Test Case 4: n = 3, k = 6")
    println("Result: ${solver.getPermutation(3, 6)}")
    println("Expected: 321")
    println()
    
    // Test Case 5: n = 1
    println("Test Case 5: n = 1, k = 1")
    println("Result: ${solver.getPermutation(1, 1)}")
    println("Expected: 1")
    println()
    
    // Test Case 6: Larger n
    println("Test Case 6: n = 5, k = 16")
    println("Result: ${solver.getPermutation(5, 16)}")
    println("Expected: 14352")
    println()
    
    // Test Case 7: Middle permutation
    println("Test Case 7: n = 4, k = 12")
    println("Result: ${solver.getPermutation(4, 12)}")
    println("Expected: 2431")
    println()
    
    // Test Case 8: Verify all permutations for n=3
    println("Test Case 8: All permutations for n = 3")
    for (k in 1..6) {
        println("k=$k: ${solver.getPermutation(3, k)}")
    }
    println("Expected order: 123, 132, 213, 231, 312, 321")
    println()
    
    // Test Case 9: Compare implementations
    println("Test Case 9: Compare different implementations")
    val n = 4
    val k = 9
    val result1 = solver.getPermutation(n, k)
    val result2 = solver.getPermutationRecursive(n, k)
    println("Iterative: $result1")
    println("Recursive: $result2")
    println("Match: ${result1 == result2}")
    println()
    
    // Test Case 10: Performance comparison
    println("Test Case 10: Performance test")
    val testN = 8
    val testK = 12345
    
    val start1 = System.currentTimeMillis()
    val mathResult = solver.getPermutation(testN, testK)
    val time1 = System.currentTimeMillis() - start1
    
    println("Mathematical approach: $mathResult (${time1}ms)")
}
