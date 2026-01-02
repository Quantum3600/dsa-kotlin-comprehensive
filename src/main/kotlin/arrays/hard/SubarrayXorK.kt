/**
 * ============================================================================
 * PROBLEM: Count Subarrays with XOR K
 * DIFFICULTY: Hard
 * CATEGORY: Arrays, XOR, Hashing, Prefix XOR
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers nums and an integer k, return the total number
 * of subarrays whose XOR equals k.
 * 
 * XOR Properties:
 * - a ^ a = 0
 * - a ^ 0 = a
 * - XOR is associative and commutative
 * 
 * INPUT FORMAT:
 * - An array of integers: nums = [4, 2, 2, 6, 4]
 * - Target XOR: k = 6
 * 
 * OUTPUT FORMAT:
 * - Number of subarrays with XOR = k: 4
 *   Subarrays: [4,2], [4,2,2,6,4], [2,2,6], [6]
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 3 * 10^4
 * - 0 <= nums[i] <= 10^9
 * - 0 <= k <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Similar to "Subarray Sum Equals K" but using XOR instead of sum.
 * Use prefix XOR and HashMap to track previously seen XOR values.
 * 
 * KEY INSIGHT:
 * If prefixXOR[j] ^ k = prefixXOR[i], then subarray [i+1...j] has XOR = k
 * Because: prefixXOR[j] = nums[0]^...^nums[j]
 *          prefixXOR[i] = nums[0]^...^nums[i]
 *          prefixXOR[j] ^ prefixXOR[i] = nums[i+1]^...^nums[j]
 * 
 * We want: nums[i+1]^...^nums[j] = k
 * So: prefixXOR[j] ^ k = prefixXOR[i]
 * 
 * ALGORITHM STEPS:
 * 1. Initialize HashMap to store prefix XOR frequencies
 * 2. Add {0: 1} to handle subarrays starting from index 0
 * 3. Maintain running XOR
 * 4. For each element:
 *    - Calculate current prefix XOR
 *    - Check if (currentXOR ^ k) exists in map
 *    - Add its frequency to result
 *    - Update map with current XOR
 * 
 * VISUAL EXAMPLE:
 * nums = [4, 2, 2, 6, 4], k = 6
 * 
 * i=0: prefixXOR=4
 *   Need: 4^6=2, not in map, count=0
 *   map={0:1, 4:1}
 * 
 * i=1: prefixXOR=4^2=6
 *   Need: 6^6=0, in map (freq=1), count=1
 *   Found: [4,2]
 *   map={0:1, 4:1, 6:1}
 * 
 * i=2: prefixXOR=6^2=4
 *   Need: 4^6=2, not in map, count=1
 *   map={0:1, 4:2, 6:1}
 * 
 * i=3: prefixXOR=4^6=2
 *   Need: 2^6=4, in map (freq=2), count=3
 *   Found: [2,2,6] and [4,2,2,6]
 *   map={0:1, 4:2, 6:1, 2:1}
 * 
 * i=4: prefixXOR=2^4=6
 *   Need: 6^6=0, in map (freq=1), count=4
 *   Found: [4,2,2,6,4]
 *   map={0:1, 4:2, 6:2, 2:1}
 * 
 * Total: 4 subarrays
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass through array
 * Space: O(n) - HashMap to store prefix XORs
 * 
 * ============================================================================
 */

package arrays.hard

class SubarrayXorK {
    
    /**
     * Counts subarrays with XOR equal to K using prefix XOR and HashMap
     * 
     * @param nums The input array
     * @param k Target XOR value
     * @return Number of subarrays with XOR = k
     */
    fun subarrayXor(nums: IntArray, k: Int): Int {
        val prefixXorCount = mutableMapOf<Int, Int>()
        prefixXorCount[0] = 1  // Handle subarrays starting from index 0
        
        var currentXor = 0
        var count = 0
        
        for (num in nums) {
            // Calculate prefix XOR
            currentXor = currentXor xor num
            
            // Check if (currentXor ^ k) exists
            // If exists, it means there's a subarray ending at current index with XOR = k
            val requiredXor = currentXor xor k
            count += prefixXorCount.getOrDefault(requiredXor, 0)
            
            // Update map with current XOR
            prefixXorCount[currentXor] = prefixXorCount.getOrDefault(currentXor, 0) + 1
        }
        
        return count
    }
    
    /**
     * Alternative with explicit tracking
     */
    fun subarrayXorExplicit(nums: IntArray, k: Int): Int {
        val xorMap = mutableMapOf<Int, Int>()
        xorMap[0] = 1
        
        var prefixXor = 0
        var result = 0
        
        for (i in nums.indices) {
            prefixXor = prefixXor xor nums[i]
            
            // If (prefixXor ^ k) exists in map, add its count
            val target = prefixXor xor k
            if (target in xorMap) {
                result += xorMap[target]!!
            }
            
            // Add current prefix XOR to map
            xorMap[prefixXor] = xorMap.getOrDefault(prefixXor, 0) + 1
        }
        
        return result
    }
    
    /**
     * Brute force approach - check all subarrays
     * Time: O(n²), Space: O(1)
     */
    fun subarrayXorBruteForce(nums: IntArray, k: Int): Int {
        var count = 0
        
        for (i in nums.indices) {
            var xorValue = 0
            for (j in i until nums.size) {
                xorValue = xorValue xor nums[j]
                if (xorValue == k) {
                    count++
                }
            }
        }
        
        return count
    }
    
    /**
     * Helper to find all subarrays (for debugging/understanding)
     */
    fun findSubarraysWithXorK(nums: IntArray, k: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        
        for (i in nums.indices) {
            var xorValue = 0
            for (j in i until nums.size) {
                xorValue = xorValue xor nums[j]
                if (xorValue == k) {
                    result.add(Pair(i, j))
                }
            }
        }
        
        return result
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single element equals k: [5], k=5 → 1
 * 2. Single element not k: [5], k=3 → 0
 * 3. All zeros: [0,0,0], k=0 → 6 (all subarrays)
 * 4. No subarray: [1,2,3], k=10 → 0
 * 5. k=0: Count subarrays with XOR=0
 * 6. Multiple same XOR values
 * 7. Large XOR values
 * 
 * APPLICATIONS:
 * 1. Network packet analysis
 * 2. Error detection in data transmission
 * 3. Cryptography pattern analysis
 * 4. DNA sequence analysis
 * 5. Data compression
 * 6. Game state analysis
 * 7. Bit manipulation problems
 * 
 * ============================================================================
 * XOR PROPERTIES USED
 * ============================================================================
 * 
 * 1. Self-inverse: a ^ a = 0
 * 2. Identity: a ^ 0 = a
 * 3. Commutative: a ^ b = b ^ a
 * 4. Associative: (a ^ b) ^ c = a ^ (b ^ c)
 * 5. Cancellation: a ^ b ^ b = a
 * 6. If a ^ b = c, then a ^ c = b and b ^ c = a
 * 
 * These properties allow us to compute subarray XOR efficiently:
 * XOR[i...j] = prefixXOR[j] ^ prefixXOR[i-1]
 * 
 * ============================================================================
 */

fun main() {
    val solution = SubarrayXorK()
    
    println("=== Subarray XOR K Tests ===\n")
    
    // Test 1: Standard case
    println("Test 1: Standard case")
    val nums1 = intArrayOf(4, 2, 2, 6, 4)
    val k1 = 6
    println("Input: nums=${nums1.contentToString()}, k=$k1")
    println("Result: ${solution.subarrayXor(nums1, k1)}")
    val subarrays1 = solution.findSubarraysWithXorK(nums1, k1)
    println("Subarrays (i,j): $subarrays1")
    println("Expected: 4\n")
    
    // Test 2: Single element
    println("Test 2: Single element equals k")
    val nums2 = intArrayOf(5)
    val k2 = 5
    println("Input: nums=${nums2.contentToString()}, k=$k2")
    println("Result: ${solution.subarrayXor(nums2, k2)}")
    println("Expected: 1\n")
    
    // Test 3: k=0
    println("Test 3: k=0")
    val nums3 = intArrayOf(1, 2, 3, 2)
    val k3 = 0
    println("Input: nums=${nums3.contentToString()}, k=$k3")
    println("Result: ${solution.subarrayXor(nums3, k3)}")
    val subarrays3 = solution.findSubarraysWithXorK(nums3, k3)
    println("Subarrays (i,j): $subarrays3")
    println("Expected: 2 ([1,2,3] and [2,2])\n")
    
    // Test 4: No solution
    println("Test 4: No solution")
    val nums4 = intArrayOf(1, 2, 3)
    val k4 = 10
    println("Input: nums=${nums4.contentToString()}, k=$k4")
    println("Result: ${solution.subarrayXor(nums4, k4)}")
    println("Expected: 0\n")
    
    // Test 5: All same XOR
    println("Test 5: Simple case")
    val nums5 = intArrayOf(5, 6, 7, 8, 9)
    val k5 = 5
    println("Input: nums=${nums5.contentToString()}, k=$k5")
    println("Result: ${solution.subarrayXor(nums5, k5)}")
    val subarrays5 = solution.findSubarraysWithXorK(nums5, k5)
    println("Subarrays (i,j): $subarrays5")
    println()
    
    // Comparison of approaches
    println("Comparing approaches:")
    val testNums = intArrayOf(4, 2, 2, 6, 4)
    val testK = 6
    println("Array: ${testNums.contentToString()}, k=$testK")
    println("Optimized: ${solution.subarrayXor(testNums, testK)}")
    println("Brute force: ${solution.subarrayXorBruteForce(testNums, testK)}")
    
    println("\nAll tests completed!")
}
