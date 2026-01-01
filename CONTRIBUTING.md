# Contributing to DSA Kotlin Comprehensive

Thank you for your interest in contributing to this Data Structures and Algorithms repository! This guide will help you understand how to contribute effectively.

## 📋 Table of Contents
- [Getting Started](#getting-started)
- [Code Style Guidelines](#code-style-guidelines)
- [Documentation Standards](#documentation-standards)
- [Submission Process](#submission-process)
- [Problem Implementation Guidelines](#problem-implementation-guidelines)

## 🚀 Getting Started

### Prerequisites
- JDK 17 or higher
- Gradle 7.0+ (wrapper included)
- IntelliJ IDEA or any Kotlin-compatible IDE (recommended)
- Git

### Setting Up Development Environment
1. Fork the repository
2. Clone your fork:
   ```bash
   git clone https://github.com/YOUR_USERNAME/dsa-kotlin-comprehensive.git
   cd dsa-kotlin-comprehensive
   ```
3. Build the project:
   ```bash
   ./gradlew build
   ```

## 📝 Code Style Guidelines

### Kotlin Conventions
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Keep functions small and focused on single responsibility
- Use Kotlin idioms (data classes, extension functions, etc.)

### Naming Conventions
- **Classes**: PascalCase (e.g., `LargestElementInArray`)
- **Functions**: camelCase (e.g., `findLargest`)
- **Variables**: camelCase (e.g., `currentElement`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_VALUE`)
- **Files**: PascalCase matching the main class (e.g., `LargestElement.kt`)

### Code Organization
```kotlin
// 1. Package declaration
package arrays.easy

// 2. Imports
import kotlin.math.max

// 3. File-level documentation
/**
 * Problem documentation here
 */

// 4. Class definition
class ProblemName {
    // 5. Properties
    // 6. Methods
}

// 7. Main function with test cases
fun main() {
    // Test cases
}
```

## 📚 Documentation Standards

Every problem file must include comprehensive documentation following this structure:

### 1. Header Section
```kotlin
/**
 * ============================================================================
 * PROBLEM: [Problem Name]
 * DIFFICULTY: [Easy/Medium/Hard]
 * CATEGORY: [Category Name]
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * [Clear description of the problem]
 * 
 * INPUT FORMAT:
 * [Description with examples]
 * 
 * OUTPUT FORMAT:
 * [Description with examples]
 * 
 * CONSTRAINTS:
 * [List all constraints]
 */
```

### 2. Approach & Intuition
```kotlin
/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * [Explain the thought process behind the solution]
 * 
 * ALGORITHM STEPS:
 * [Step-by-step breakdown]
 * 
 * VISUAL EXAMPLE:
 * [ASCII diagrams or examples]
 * 
 * ALTERNATIVE APPROACHES:
 * [List other possible solutions with trade-offs]
 */
```

### 3. Complexity Analysis
```kotlin
/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(?)
 * [Detailed explanation of why]
 * 
 * SPACE COMPLEXITY: O(?)
 * [Detailed explanation of why]
 */
```

### 4. Code Comments
- Every variable declaration should be explained
- Every condition/loop should explain WHY not just WHAT
- Highlight edge cases
- Explain any non-obvious logic

### 5. Example Walkthrough
```kotlin
/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * [Dry run with sample input showing variable states]
 */
```

## 🔄 Submission Process

### 1. Create a Branch
```bash
git checkout -b feature/add-problem-name
```

### 2. Implement the Problem
- Create the file in the appropriate directory
- Follow all documentation standards
- Include comprehensive test cases in main()
- Ensure code compiles and runs

### 3. Test Your Implementation
```bash
# Compile
./gradlew build

# Run specific file
./gradlew run --args="path.to.YourClass"
```

### 4. Commit Your Changes
```bash
git add .
git commit -m "Add [Problem Name] implementation"
```

### 5. Push and Create Pull Request
```bash
git push origin feature/add-problem-name
```
Then create a pull request on GitHub with:
- Clear description of what was added
- Any relevant notes or considerations
- Link to original problem (if applicable)

## 💡 Problem Implementation Guidelines

### What to Include
1. **Complete Implementation**: All edge cases handled
2. **Multiple Approaches**: If applicable, show brute force and optimal
3. **Beginner-Friendly**: Explain concepts, no assumptions
4. **Test Cases**: Minimum 4-5 test cases covering:
   - Normal cases
   - Edge cases (empty, single element, etc.)
   - Boundary conditions
   - Negative numbers (if applicable)

### What to Avoid
- Incomplete implementations with TODOs (unless problem is ambiguous)
- Copying code without understanding
- Missing documentation
- Inadequate test coverage
- Overly complex solutions without explanation

### Example Structure
```kotlin
class ProblemSolution {
    /**
     * Main solution method
     * @param input Description
     * @return Description
     */
    fun solve(input: Type): ReturnType {
        // Implementation with detailed comments
    }
    
    /**
     * Alternative approach (if applicable)
     */
    fun solveAlternative(input: Type): ReturnType {
        // Alternative implementation
    }
}

fun main() {
    val solver = ProblemSolution()
    
    // Test Case 1: Normal case
    println(solver.solve(input1))  // Expected: output1
    
    // Test Case 2: Edge case
    println(solver.solve(input2))  // Expected: output2
    
    // Add more test cases...
}
```

## 🎯 Areas to Contribute

### High Priority
- Implementing unimplemented problems
- Adding more test cases to existing solutions
- Improving documentation clarity
- Adding visual diagrams

### Medium Priority
- Creating theory documentation in `docs/`
- Adding section README files
- Performance optimizations
- Alternative solution approaches

### Low Priority
- Code style improvements
- Typo fixes
- Example enhancements

## 📖 Documentation Contributions

### Theory Guides
When creating theory guides in `docs/`:
- Start with fundamentals
- Use visual examples
- Include practical applications
- Provide practice problems
- Link to related implementations

### Section READMEs
Each major section should have a README with:
- Overview of the data structure/algorithm
- When to use it
- Common patterns
- Links to all problems in the section
- Difficulty progression

## ✅ Review Checklist

Before submitting, ensure:
- [ ] Code compiles without errors
- [ ] All test cases pass
- [ ] Documentation is complete and follows standards
- [ ] Code follows Kotlin conventions
- [ ] Comments are clear and helpful
- [ ] Edge cases are handled
- [ ] No hardcoded values (use constants)
- [ ] Variable names are descriptive
- [ ] Complexity analysis is accurate
- [ ] Example walkthrough is included

## 🤝 Code Review Process

1. Maintainers will review your PR within 48 hours
2. Address any feedback or requested changes
3. Once approved, your PR will be merged
4. Your contribution will be acknowledged!

## 📞 Getting Help

- **Questions**: Open an issue with the `question` label
- **Bug Reports**: Open an issue with the `bug` label
- **Feature Requests**: Open an issue with the `enhancement` label
- **Discussions**: Use GitHub Discussions for general topics

## 🌟 Recognition

Contributors will be:
- Listed in the Contributors section of README
- Acknowledged in release notes
- Appreciated for making learning accessible!

Thank you for contributing to this educational resource! Your efforts help countless learners understand Data Structures and Algorithms better. 🚀
