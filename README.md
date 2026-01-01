# 🚀 Comprehensive DSA in Kotlin

<div align="center">

**A Complete Data Structures and Algorithms Repository in Kotlin**  
*From Beginner to Advanced - Learn by Doing*

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-blue.svg)](https://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)

[Getting Started](#-getting-started) • [Structure](#-repository-structure) • [Learning Path](#-learning-path) • [Contributing](#-contributing)

</div>

---

## 📖 About This Repository

This repository is a **comprehensive educational resource** for learning Data Structures and Algorithms using Kotlin. Whether you're preparing for technical interviews, strengthening your problem-solving skills, or learning DSA from scratch, this repository has you covered.

### ✨ What Makes This Special?

- 🎯 **200+ Problems** - Carefully curated problems covering all major DSA topics
- 📚 **Extensive Documentation** - Every solution includes detailed explanations, intuition, and complexity analysis
- 🔰 **Beginner-Friendly** - No prior DSA knowledge required; concepts explained from first principles
- 💡 **Multiple Approaches** - Brute force to optimal solutions with trade-off analysis
- 🧪 **Tested Code** - All implementations include comprehensive test cases
- 🎨 **Visual Learning** - ASCII diagrams and step-by-step walkthroughs
- 🏗️ **Production Quality** - Clean, well-organized Kotlin code following best practices

## 🎯 Target Audience

- 🎓 **Students** learning DSA for the first time
- 💼 **Job Seekers** preparing for technical interviews
- 🔄 **Career Changers** transitioning to software development
- 🧠 **Kotlin Developers** wanting to strengthen DSA skills
- 👨‍🏫 **Educators** looking for teaching resources

## 🚀 Getting Started

### Prerequisites

- **JDK 17 or higher** - [Download here](https://adoptium.net/)
- **Gradle** (wrapper included in project)
- **IDE** - IntelliJ IDEA recommended (Community Edition is free)

### Quick Start

```bash
# Clone the repository
git clone https://github.com/Quantum3600/dsa-kotlin-comprehensive.git
cd dsa-kotlin-comprehensive

# Build the project
./gradlew build

# Run a specific problem (example)
./gradlew run --args="arrays.easy.LargestElement"
```

### Project Setup

1. **Clone and Build**
   ```bash
   git clone https://github.com/Quantum3600/dsa-kotlin-comprehensive.git
   cd dsa-kotlin-comprehensive
   ./gradlew build
   ```

2. **Open in IntelliJ IDEA**
   - File → Open → Select the project directory
   - IDEA will automatically detect the Gradle configuration
   - Wait for dependencies to download

3. **Run Your First Problem**
   - Navigate to `src/main/kotlin/basics/syntax/`
   - Open any `.kt` file
   - Click the green play button next to `fun main()`

## 📁 Repository Structure

```
dsa-kotlin-comprehensive/
├── src/main/kotlin/
│   ├── basics/                    # Language fundamentals
│   │   ├── syntax/               # Kotlin syntax basics (11 files)
│   │   ├── math/                 # Basic math problems (7 files)
│   │   ├── recursion/            # Recursion fundamentals (9 files)
│   │   └── hashing/              # Hashing basics (3 files)
│   │
│   ├── sorting/                   # Sorting algorithms (7 files)
│   │
│   ├── arrays/                    # Array problems
│   │   ├── easy/                 # 14 easy problems
│   │   ├── medium/               # 14 medium problems
│   │   └── hard/                 # 12 hard problems
│   │
│   ├── searching/                 # Search algorithms
│   │   ├── binarysearch/
│   │   │   ├── onedim/          # 1D binary search (13 files)
│   │   │   ├── answers/         # BS on answers (14 files)
│   │   │   └── twodim/          # 2D binary search (5 files)
│   │   └── advanced/            # Advanced search (4 files)
│   │
│   ├── strings/                   # String problems
│   │   ├── easy/                 # 7 easy problems
│   │   ├── medium/               # 8 medium problems
│   │   └── hard/                 # 10 hard problems
│   │
│   ├── linkedlist/                # Linked list problems
│   │   ├── singly/               # Singly linked lists (5 files)
│   │   ├── doubly/               # Doubly linked lists (7 files)
│   │   ├── medium/               # 15 medium problems
│   │   └── hard/                 # 4 hard problems
│   │
│   ├── recursion/                 # Advanced recursion
│   │   ├── stronghold/           # Core recursion (5 files)
│   │   ├── subsequences/         # Subsequence problems (12 files)
│   │   └── hard/                 # Hard problems (8 files)
│   │
│   ├── bitmanipulation/           # Bit manipulation
│   │   ├── basics/               # Fundamentals (8 files)
│   │   ├── interview/            # Interview problems (5 files)
│   │   └── math/                 # Math with bits (5 files)
│   │
│   ├── stackqueue/                # Stack and Queue
│   │   ├── basics/               # Basic implementations (8 files)
│   │   ├── conversions/          # Expression conversions (6 files)
│   │   ├── monotonic/            # Monotonic stack/queue (11 files)
│   │   └── implementation/       # Advanced implementations (5 files)
│   │
│   ├── slidingwindow/             # Sliding window technique
│   │   ├── medium/               # 8 medium problems
│   │   └── hard/                 # 4 hard problems
│   │
│   ├── heaps/                     # Heap data structure
│   │   ├── easy/                 # Basics (4 files)
│   │   ├── medium/               # 7 medium problems
│   │   └── hard/                 # 6 hard problems
│   │
│   ├── greedy/                    # Greedy algorithms
│   │   ├── easy/                 # 5 easy problems
│   │   └── mediumhard/           # 11 advanced problems
│   │
│   ├── trees/                     # Tree data structures
│   │   ├── binarytree/
│   │   │   ├── traversals/      # All traversals (12 files)
│   │   │   ├── medium/          # 12 medium problems
│   │   │   └── hard/            # 14 hard problems
│   │   └── bst/
│   │       ├── basics/          # BST fundamentals (3 files)
│   │       └── problems/        # 13 BST problems
│   │
│   ├── graphs/                    # Graph algorithms
│   │   ├── basics/               # Graph theory (3 files)
│   │   ├── bfsdfs/               # BFS/DFS problems (14 files)
│   │   ├── toposort/             # Topological sort (7 files)
│   │   ├── shortestpath/         # Shortest paths (14 files)
│   │   ├── mst/                  # MST & Disjoint Set (10 files)
│   │   └── others/               # Advanced topics (3 files)
│   │
│   ├── dynamicprogramming/        # Dynamic programming
│   │   ├── intro/                # DP introduction (1 file)
│   │   ├── onedim/               # 1D DP (5 files)
│   │   ├── multidim/             # 2D/3D DP (7 files)
│   │   ├── subsequences/         # Subsequence DP (12 files)
│   │   ├── strings/              # String DP (10 files)
│   │   ├── stocks/               # Stock problems (6 files)
│   │   ├── lis/                  # LIS problems (7 files)
│   │   ├── mcm/                  # Matrix chain (7 files)
│   │   └── squares/              # Square problems (2 files)
│   │
│   ├── tries/                     # Trie data structure
│   │   ├── basics/               # Trie basics (1 file)
│   │   └── problems/             # 5 trie problems
│   │
│   └── utils/                     # Utility classes
│
├── docs/                          # Theory documentation
│   ├── TimeComplexity.md
│   ├── SpaceComplexity.md
│   ├── RecursionGuide.md
│   ├── DPPatterns.md
│   ├── GraphAlgorithms.md
│   ├── TreeTraversals.md
│   └── SortingComparison.md
│
├── build.gradle.kts               # Gradle build configuration
├── settings.gradle.kts            # Gradle settings
├── .gitignore
├── README.md
└── CONTRIBUTING.md
```

## 📚 Learning Path

### 🌱 For Complete Beginners

**Week 1-2: Foundations**
1. Start with `basics/syntax/` - Learn Kotlin basics
2. Move to `basics/math/` - Simple problem solving
3. Practice `basics/recursion/` - Understanding recursion
4. Learn `basics/hashing/` - Introduction to hash maps

**Week 3-4: Core Data Structures**
5. Master `sorting/` - All sorting algorithms
6. Dive into `arrays/easy/` - Array manipulation
7. Study `strings/easy/` - String operations
8. Explore `linkedlist/singly/` - Linked list basics

**Week 5-8: Intermediate Topics**
9. `searching/binarysearch/` - Binary search patterns
10. `arrays/medium/` and `strings/medium/` - Harder problems
11. `stackqueue/basics/` - Stack and queue
12. `recursion/subsequences/` - Pattern recognition

**Week 9-16: Advanced Topics**
13. `trees/binarytree/` - Tree algorithms
14. `graphs/bfsdfs/` - Graph traversals
15. `dynamicprogramming/` - DP mastery
16. `heaps/`, `greedy/`, `tries/` - Specialized topics

### 🎯 For Interview Preparation

**2-Week Sprint**
- **Days 1-3**: Arrays (easy + medium) and Strings
- **Days 4-6**: Linked Lists and Stacks/Queues
- **Days 7-9**: Binary Search, Trees, and Graphs
- **Days 10-12**: Dynamic Programming fundamentals
- **Days 13-14**: Practice mixed problems, review mistakes

### 💡 Topic-Wise Learning

Pick any topic you want to focus on:
- **Arrays** → `arrays/` directory
- **Strings** → `strings/` directory
- **Trees** → `trees/` directory
- **Graphs** → `graphs/` directory
- **DP** → `dynamicprogramming/` directory

## 📊 Progress Tracker

Track your progress as you solve problems!

### Basics (30 problems)
- [ ] Syntax (11/11)
- [ ] Math (7/7)
- [ ] Recursion (9/9)
- [ ] Hashing (3/3)

### Core Topics (110+ problems)
- [ ] Sorting (7/7)
- [ ] Arrays (40/40)
- [ ] Searching (36/36)
- [ ] Strings (25/25)
- [ ] Linked Lists (31/31)

### Advanced Topics (100+ problems)
- [ ] Recursion Advanced (25/25)
- [ ] Bit Manipulation (18/18)
- [ ] Stack & Queue (30/30)
- [ ] Sliding Window (12/12)
- [ ] Heaps (17/17)
- [ ] Greedy (16/16)
- [ ] Trees (41/41)
- [ ] Graphs (51/51)
- [ ] Dynamic Programming (57/57)
- [ ] Tries (6/6)

**Total: 200+ Problems**

## 💻 Code Example

Here's a sample of what you'll find in this repository:

```kotlin
/**
 * ============================================================================
 * PROBLEM: Find the Largest Element in an Array
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ============================================================================
 */

class LargestElementInArray {
    /**
     * TIME COMPLEXITY: O(n) - Single pass through array
     * SPACE COMPLEXITY: O(1) - Only one variable used
     */
    fun findLargest(arr: IntArray): Int {
        require(arr.isNotEmpty()) { "Array cannot be empty" }
        
        // Initialize max with first element
        var max = arr[0]
        
        // Compare each element with current max
        for (i in 1 until arr.size) {
            if (arr[i] > max) {
                max = arr[i]
            }
        }
        
        return max
    }
}

fun main() {
    val solver = LargestElementInArray()
    println(solver.findLargest(intArrayOf(1, 5, 3, 9, 2)))  // Output: 9
}
```

## 🎓 Documentation Style

Every problem includes:
- ✅ **Problem Statement** - Clear description with examples
- ✅ **Intuition** - Thought process behind the solution
- ✅ **Algorithm Steps** - Step-by-step breakdown
- ✅ **Visual Examples** - ASCII diagrams where helpful
- ✅ **Complexity Analysis** - Time and space with explanations
- ✅ **Code Comments** - Line-by-line explanations
- ✅ **Example Walkthrough** - Dry run with sample input
- ✅ **Edge Cases** - All corner cases handled
- ✅ **Test Cases** - Multiple test scenarios

## 🔧 Built With

- **Kotlin 1.9.20** - Modern, concise JVM language
- **Gradle** - Build automation
- **JUnit 5** - Testing framework
- **JDK 17** - Java Development Kit

## 🤝 Contributing

We welcome contributions! Whether it's:
- 🐛 Bug fixes
- ✨ New problem implementations
- 📝 Documentation improvements
- 💡 Alternative solutions
- 🎨 Better explanations

Please read our [Contributing Guide](CONTRIBUTING.md) for details on our code of conduct and submission process.

## 📖 Additional Resources

### Theory Documentation
- [Time Complexity Guide](docs/TimeComplexity.md) - Understanding Big O notation
- [Space Complexity Guide](docs/SpaceComplexity.md) - Memory analysis
- [Recursion Guide](docs/RecursionGuide.md) - Master recursion
- [DP Patterns](docs/DPPatterns.md) - Dynamic programming patterns
- [Graph Algorithms](docs/GraphAlgorithms.md) - Graph theory basics
- [Tree Traversals](docs/TreeTraversals.md) - All traversal methods
- [Sorting Comparison](docs/SortingComparison.md) - Compare sorting algorithms

### External Resources
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)

## 🎯 Goals of This Repository

1. **Accessibility** - Make DSA learning accessible to everyone
2. **Clarity** - Explain concepts in simple, understandable terms
3. **Completeness** - Cover all major DSA topics comprehensively
4. **Quality** - Maintain high code and documentation standards
5. **Community** - Build a helpful learning community

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🌟 Star History

If you find this repository helpful, please consider giving it a ⭐!

## 📞 Contact & Support

- 🐛 **Issues**: [GitHub Issues](https://github.com/Quantum3600/dsa-kotlin-comprehensive/issues)
- 💬 **Discussions**: [GitHub Discussions](https://github.com/Quantum3600/dsa-kotlin-comprehensive/discussions)
- 📧 **Email**: For private inquiries

## 🙏 Acknowledgments

This repository is built for the learning community. Special thanks to:
- All contributors who help improve this resource
- The Kotlin community for excellent documentation
- Everyone learning DSA - keep going! 💪

---

<div align="center">

**Happy Coding! 🚀**

*Remember: The only way to learn DSA is by doing. Start with any problem and keep practicing!*

Made with ❤️ for learners worldwide

</div>
