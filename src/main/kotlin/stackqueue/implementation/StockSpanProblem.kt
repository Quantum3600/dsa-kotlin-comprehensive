/**
 * ============================================================================
 * PROBLEM:  Stock Span Problem
 * DIFFICULTY: Medium
 * CATEGORY: Stack & Queue Implementation (Monotonic Stack)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Design an algorithm that calculates the span of stock's price for current day.
 * The span of the stock's price in one day is the maximum number of consecutive
 * days (starting from today going backwards) where the stock price was less than
 * or equal to the price of that day.
 * 
 * Implement StockSpanner class:
 * - StockSpanner(): Initialize the object
 * - next(price): Return the span of stock's price for current day
 * 
 * INPUT FORMAT:
 * Operations:  ["StockSpanner", "next", "next", "next", "next", "next"]
 * Parameters: [[], [100], [80], [60], [70], [60], [75], [85]]
 * 
 * OUTPUT FORMAT:
 * [null, 1, 1, 1, 2, 1, 4, 6]
 * 
 * Explanation:
 * Day 1: price=100, no previous higher → span=1
 * Day 2: price=80 < 100 → span=1
 * Day 3: price=60 < 80 → span=1
 * Day 4: price=70 > 60 → span=2 [60,70]
 * Day 5: price=60 < 70 → span=1
 * Day 6: price=75 > 60,70 → span=4 [60,70,60,75]
 * Day 7: price=85 > all except 100 → span=6 [60,70,60,75,85,80]
 * 
 * CONSTRAINTS:
 * - 1 <= price <= 10^5
 * - At most 10^4 calls to next()
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * For each day, we need to count how many consecutive previous days had
 * price <= current price.  Think of it like: 
 * - A person looking back at past days
 * - Stop when you find a day with higher price
 * - Count all days you can see
 * 
 * NAIVE APPROACH:  O(n²)
 * For each price, loop backwards counting consecutive smaller prices
 * 
 * OPTIMAL APPROACH: Use Monotonic Decreasing Stack
 * - Stack stores (price, span) pairs
 * - Pop elements with price <= current (we can "see past" them)
 * - Current span = 1 + sum of popped spans
 * 
 * KEY INSIGHT:
 * If price[i] >= price[j] where j < i: 
 * - We can "see through" day j
 * - All days that j could see, we can also see
 * - span[i] includes span[j] + itself
 * 
 * WHY STACK?
 * - LIFO: Most recent prices checked first
 * - Only keep prices that could block future spans
 * - Remove prices that current price can "see past"
 * 
 * ALGORITHM STEPS:
 * 1. **Initialize** empty stack and span = 1
 * 
 * 2. **For each new price:**
 *    a.  While stack not empty AND top price <= current: 
 *       - Pop from stack
 *       - Add popped span to current span
 *    b. Push (current price, current span) to stack
 *    c.  Return current span
 * 
 * VISUAL EXAMPLE:
 * ```
 * Prices: [100, 80, 60, 70, 60, 75, 85]
 * 
 * Day 1: price=100, stack=[(100,1)]
 *        span=1 ✓
 * 
 * Day 2: price=80, stack=[(100,1), (80,1)]
 *        80<100, push
 *        span=1 ✓
 * 
 * Day 3: price=60, stack=[(100,1), (80,1), (60,1)]
 *        60<80, push
 *        span=1 ✓
 * 
 * Day 4: price=70, pop (60,1) since 60<=70
 *        stack=[(100,1), (80,1), (70,2)]
 *        span=1+1=2 ✓
 * 
 * Day 5: price=60, stack=[(100,1), (80,1), (70,2), (60,1)]
 *        60<70, push
 *        span=1 ✓
 * 
 * Day 6: price=75, pop (60,1), pop (70,2) since both <=75
 *        stack=[(100,1), (80,1), (75,4)]
 *        span=1+1+2=4 ✓
 * 
 * Day 7: price=85, pop (75,4), pop (80,1) since both <=85
 *        stack=[(100,1), (85,6)]
 *        span=1+4+1=6 ✓
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(1) amortized per next() call
 * - Each price pushed once: O(1)
 * - Each price popped at most once: O(1) amortized
 * - Over n calls: O(n) total, O(1) amortized per call
 * 
 * WHY AMORTIZED O(1):
 * - Some calls might pop multiple elements
 * - But each element popped exactly once in total
 * - Total pops across all calls ≤ total pushes
 * 
 * SPACE COMPLEXITY: O(n)
 * - Stack stores at most n elements
 * - Worst case: All prices in decreasing order
 * 
 * ============================================================================
 * IMPLEMENTATION
 * ============================================================================
 */

class StockSpanner {
    // Stack stores pairs of (price, span)
    // Monotonic decreasing:  prices decrease from bottom to top
    private val stack = ArrayDeque<Pair<Int, Int>>()
    
    /**
     * Calculate span for current day's price
     * Span = # of consecutive days (including today) with price <= today
     */
    fun next(price: Int): Int {
        var span = 1
        
        // Pop all prices <= current price
        // Their spans get absorbed into current span
        while (stack.isNotEmpty() && stack.last().first <= price) {
            val (_, prevSpan) = stack.removeLast()
            span += prevSpan
        }
        
        // Push current price with its span
        stack.addLast(Pair(price, span))
        
        return span
    }
}

/**
 * ============================================================================
 * ALTERNATIVE IMPLEMENTATION: With Index Tracking
 * ============================================================================
 */

class StockSpannerV2 {
    // Store (price, index) instead of (price, span)
    private val stack = ArrayDeque<Pair<Int, Int>>()
    private var currentIndex = 0
    
    fun next(price: Int): Int {
        // Pop all prices <= current
        while (stack.isNotEmpty() && stack.last().first <= price) {
            stack.removeLast()
        }
        
        // Calculate span using indices
        val span = if (stack.isEmpty()) {
            currentIndex + 1  // All previous days
        } else {
            currentIndex - stack.last().second  // Days since last higher price
        }
        
        // Push current price with its index
        stack.addLast(Pair(price, currentIndex))
        currentIndex++
        
        return span
    }
}

/**
 * ============================================================================
 * USAGE EXAMPLES
 * ============================================================================
 */

fun main() {
    // Example 1: Basic stock span
    println("Example 1: Basic Stock Span")
    val spanner1 = StockSpanner()
    val prices1 = intArrayOf(100, 80, 60, 70, 60, 75, 85)
    println("Prices: ${prices1.contentToString()}")
    print("Spans:   [")
    prices1.forEach { price ->
        print("${spanner1.next(price)}, ")
    }
    println("\b\b]")  // [1, 1, 1, 2, 1, 4, 6]
    
    // Example 2: All increasing prices
    println("\nExample 2:  Increasing Prices")
    val spanner2 = StockSpanner()
    val prices2 = intArrayOf(10, 20, 30, 40, 50)
    println("Prices: ${prices2.contentToString()}")
    print("Spans:  [")
    prices2.forEachIndexed { index, price ->
        print("${spanner2.next(price)}, ")
    }
    println("\b\b]")  // [1, 2, 3, 4, 5]
    
    // Example 3: All decreasing prices
    println("\nExample 3: Decreasing Prices")
    val spanner3 = StockSpanner()
    val prices3 = intArrayOf(50, 40, 30, 20, 10)
    println("Prices: ${prices3.contentToString()}")
    print("Spans:  [")
    prices3.forEach { price ->
        print("${spanner3.next(price)}, ")
    }
    println("\b\b]")  // [1, 1, 1, 1, 1]
    
    // Example 4: All same prices
    println("\nExample 4: Same Prices")
    val spanner4 = StockSpanner()
    val prices4 = intArrayOf(50, 50, 50, 50)
    println("Prices: ${prices4.contentToString()}")
    print("Spans:  [")
    prices4.forEachIndexed { index, price ->
        print("${spanner4.next(price)}, ")
    }
    println("\b\b]")  // [1, 2, 3, 4]
}

/**
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: Brute Force with Array
 * ```kotlin
 * class StockSpannerBruteForce {
 *     private val prices = mutableListOf<Int>()
 *     
 *     fun next(price: Int): Int {
 *         prices.add(price)
 *         var span = 1
 *         for (i in prices.size - 2 downTo 0) {
 *             if (prices[i] <= price) span++
 *             else break
 *         }
 *         return span
 *     }
 * }
 * ```
 * Time: O(n) per call, Space: O(n)
 * ❌ Too slow for multiple queries
 * 
 * APPROACH 2: Store All Prices with Binary Search
 * - Keep sorted array of prices with indices
 * - Binary search for last higher price
 * Time: O(log n) per call, Space: O(n)
 * ⚠️ Complex, harder to maintain
 * 
 * WHY MONOTONIC STACK IS BEST: 
 * ✅ O(1) amortized time per call
 * ✅ Simple and elegant
 * ✅ O(n) space (same as others)
 * ✅ Standard for "next greater/smaller" problems
 * ✅ Easy to implement and understand
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * 1. **Next Greater Element** - Find next greater for each element
 * 2. **Daily Temperatures** - Days until warmer temperature
 * 3. **Largest Rectangle in Histogram** - Max rectangle area
 * 4. **Trapping Rain Water** - Calculate trapped water
 * 5. **Sum of Subarray Minimums** - Sum of mins in all subarrays
 * 
 * PATTERN: Monotonic Stack
 * - Maintain stack in monotonic (increasing/decreasing) order
 * - Pop elements that violate monotonic property
 * - Use for "next greater/smaller" style problems
 * - Can track spans, distances, or indices
 * 
 * ============================================================================
 * EDGE CASES & GOTCHAS
 * ============================================================================
 * 
 * 1. First price: Always span = 1
 * 2. All increasing: Span = day number (1, 2, 3, ...)
 * 3. All decreasing: All spans = 1
 * 4. All same:  Span increases each day
 * 5. Single peak: Peak has span = total days
 * 6. Duplicate prices: Treated as "see past" (<=, not <)
 * 
 * COMMON MISTAKES:
 * ❌ Using < instead of <= (duplicates matter!)
 * ❌ Forgetting to include current day in span (start with 1, not 0)
 * ❌ Not initializing span = 1 before while loop
 * ❌ Pushing price before calculating span
 * ❌ Not adding ALL popped spans (missing sum)
 * 
 * KEY POINTS:
 * ✅ Monotonic Stack is key pattern
 * ✅ Amortized O(1) complexity
 */
