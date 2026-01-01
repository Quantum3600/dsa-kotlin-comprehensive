/**
 * ============================================================================
 * TOPIC: Data Types in Kotlin
 * DIFFICULTY: Beginner
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * Kotlin is a statically-typed language with type inference. It has several
 * built-in data types that are represented as objects (no primitive types).
 * 
 * KEY CONCEPTS:
 * 1. Basic types: Numbers, Boolean, Char, String
 * 2. Type inference with var and val
 * 3. Nullable types
 * 4. Type conversion and casting
 * 5. Collections types
 * 
 * ============================================================================
 * NUMBER TYPES
 * ============================================================================
 * 
 * INTEGER TYPES:
 * - Byte:  8-bit  (-128 to 127)
 * - Short: 16-bit (-32,768 to 32,767)
 * - Int:   32-bit (-2^31 to 2^31-1)
 * - Long:  64-bit (-2^63 to 2^63-1)
 * 
 * FLOATING-POINT TYPES:
 * - Float:  32-bit IEEE 754 (6-7 decimal digits)
 * - Double: 64-bit IEEE 754 (15-16 decimal digits)
 * 
 * ============================================================================
 * TYPE INFERENCE
 * ============================================================================
 * 
 * Kotlin can automatically infer types:
 * - val x = 10          // Int inferred
 * - val y = 10L         // Long inferred
 * - val z = 10.0        // Double inferred
 * - val w = 10.0f       // Float inferred
 * 
 * ============================================================================
 */

package basics.syntax

class DataTypes {
    
    /**
     * INTEGER TYPES DEMONSTRATION
     */
    fun integerTypesExample() {
        println("=== Integer Types ===")
        
        // Byte: 8-bit signed integer
        val byteValue: Byte = 127
        println("Byte: $byteValue (range: ${Byte.MIN_VALUE} to ${Byte.MAX_VALUE})")
        
        // Short: 16-bit signed integer
        val shortValue: Short = 32767
        println("Short: $shortValue (range: ${Short.MIN_VALUE} to ${Short.MAX_VALUE})")
        
        // Int: 32-bit signed integer (most commonly used)
        val intValue: Int = 2147483647
        println("Int: $intValue (range: ${Int.MIN_VALUE} to ${Int.MAX_VALUE})")
        
        // Long: 64-bit signed integer
        val longValue: Long = 9223372036854775807L
        println("Long: $longValue (range: ${Long.MIN_VALUE} to ${Long.MAX_VALUE})")
        
        // Underscores for readability
        val million = 1_000_000
        val billion = 1_000_000_000L
        println("\nReadable numbers: $million, $billion")
        
        println()
    }
    
    /**
     * FLOATING-POINT TYPES DEMONSTRATION
     */
    fun floatingPointTypesExample() {
        println("=== Floating-Point Types ===")
        
        // Double: 64-bit (default for decimals)
        val pi: Double = 3.14159265359
        println("Double: $pi (15-16 decimal digits precision)")
        
        // Float: 32-bit (requires f/F suffix)
        val e: Float = 2.71828f
        println("Float: $e (6-7 decimal digits precision)")
        
        // Scientific notation
        val avogadro = 6.022e23  // Double
        val planck = 6.626e-34f  // Float
        println("\nScientific: $avogadro, $planck")
        
        // Special values
        println("\nSpecial values:")
        println("Positive Infinity: ${Double.POSITIVE_INFINITY}")
        println("Negative Infinity: ${Double.NEGATIVE_INFINITY}")
        println("NaN (Not a Number): ${Double.NaN}")
        
        println()
    }
    
    /**
     * BOOLEAN TYPE DEMONSTRATION
     */
    fun booleanTypeExample() {
        println("=== Boolean Type ===")
        
        val isKotlinFun: Boolean = true
        val isJavaVerbose: Boolean = false
        
        println("isKotlinFun: $isKotlinFun")
        println("isJavaVerbose: $isJavaVerbose")
        
        // Boolean operations
        println("\nBoolean operations:")
        println("AND: ${isKotlinFun && isJavaVerbose}")
        println("OR: ${isKotlinFun || isJavaVerbose}")
        println("NOT: ${!isKotlinFun}")
        println("XOR: ${isKotlinFun xor isJavaVerbose}")
        
        println()
    }
    
    /**
     * CHARACTER TYPE DEMONSTRATION
     */
    fun characterTypeExample() {
        println("=== Character Type ===")
        
        val letter: Char = 'A'
        val digit: Char = '5'
        val symbol: Char = '$'
        
        println("Letter: $letter")
        println("Digit: $digit")
        println("Symbol: $symbol")
        
        // Special characters
        println("\nSpecial characters:")
        println("Newline: \\n -> Hello\\nWorld")
        println("Tab: \\t -> Name\\t\\tAge")
        println("Backslash: \\\\ -> C:\\\\Users")
        
        // Character operations
        println("\nCharacter code: ${letter.code}")  // ASCII/Unicode value
        println("Is digit: ${digit.isDigit()}")
        println("Is letter: ${letter.isLetter()}")
        println("To lowercase: ${letter.lowercase()}")
        
        println()
    }
    
    /**
     * STRING TYPE DEMONSTRATION
     */
    fun stringTypeExample() {
        println("=== String Type ===")
        
        // String declaration
        val name: String = "Kotlin"
        val greeting = "Hello, $name!"  // String template
        
        println(greeting)
        
        // Multi-line strings (triple quotes)
        val poem = """
            Roses are red,
            Violets are blue,
            Kotlin is awesome,
            And so are you!
        """.trimIndent()
        
        println("\nMulti-line string:")
        println(poem)
        
        // String operations
        println("\nString operations:")
        println("Length: ${name.length}")
        println("Uppercase: ${name.uppercase()}")
        println("Lowercase: ${name.lowercase()}")
        println("Substring: ${name.substring(0, 3)}")
        println("Contains 'tl': ${name.contains("tl")}")
        
        // String concatenation
        val firstName = "John"
        val lastName = "Doe"
        println("\nConcatenation: $firstName + $lastName = ${firstName + " " + lastName}")
        
        println()
    }
    
    /**
     * TYPE INFERENCE DEMONSTRATION
     */
    fun typeInferenceExample() {
        println("=== Type Inference ===")
        
        // Kotlin infers types automatically
        val inferredInt = 42           // Int
        val inferredLong = 42L         // Long
        val inferredDouble = 42.0      // Double
        val inferredFloat = 42.0f      // Float
        val inferredString = "Hello"   // String
        val inferredBoolean = true     // Boolean
        
        println("Inferred Int: $inferredInt (${inferredInt::class.simpleName})")
        println("Inferred Long: $inferredLong (${inferredLong::class.simpleName})")
        println("Inferred Double: $inferredDouble (${inferredDouble::class.simpleName})")
        println("Inferred Float: $inferredFloat (${inferredFloat::class.simpleName})")
        println("Inferred String: $inferredString (${inferredString::class.simpleName})")
        println("Inferred Boolean: $inferredBoolean (${inferredBoolean::class.simpleName})")
        
        println()
    }
    
    /**
     * NULLABLE TYPES DEMONSTRATION
     */
    fun nullableTypesExample() {
        println("=== Nullable Types ===")
        
        // Non-nullable (default)
        val nonNull: String = "Cannot be null"
        // nonNull = null  // Compilation error!
        
        // Nullable (with ?)
        var nullable: String? = "Can be null"
        nullable = null  // OK
        
        println("Non-nullable: $nonNull")
        println("Nullable: $nullable")
        
        // Safe call operator
        val length = nullable?.length
        println("Length (safe call): $length")
        
        // Elvis operator
        val lengthOrDefault = nullable?.length ?: 0
        println("Length with default: $lengthOrDefault")
        
        // Not-null assertion (!!)
        nullable = "Not null anymore"
        val definiteLength = nullable!!.length
        println("Definite length: $definiteLength")
        
        println()
    }
    
    /**
     * TYPE CONVERSION DEMONSTRATION
     */
    fun typeConversionExample() {
        println("=== Type Conversion ===")
        
        val intNum = 42
        
        // Explicit conversions
        val toLong = intNum.toLong()
        val toDouble = intNum.toDouble()
        val toString = intNum.toString()
        val toByte = intNum.toByte()
        
        println("Int: $intNum")
        println("To Long: $toLong")
        println("To Double: $toDouble")
        println("To String: $toString (${toString::class.simpleName})")
        println("To Byte: $toByte")
        
        // String to number conversions
        val numString = "123"
        val parsedInt = numString.toInt()
        val parsedDouble = numString.toDouble()
        
        println("\nString \"$numString\" to Int: $parsedInt")
        println("String \"$numString\" to Double: $parsedDouble")
        
        // Safe conversions
        val invalidString = "abc"
        val safeInt = invalidString.toIntOrNull()
        println("\nInvalid string \"$invalidString\" to Int: $safeInt (null)")
        
        println()
    }
    
    /**
     * COLLECTIONS TYPES DEMONSTRATION
     */
    fun collectionsTypesExample() {
        println("=== Collections Types ===")
        
        // List (immutable)
        val fruits: List<String> = listOf("Apple", "Banana", "Orange")
        println("List: $fruits")
        
        // Mutable List
        val numbers: MutableList<Int> = mutableListOf(1, 2, 3)
        numbers.add(4)
        println("Mutable List: $numbers")
        
        // Set (unique elements)
        val uniqueNumbers: Set<Int> = setOf(1, 2, 2, 3, 3)
        println("Set: $uniqueNumbers")
        
        // Map (key-value pairs)
        val ages: Map<String, Int> = mapOf("Alice" to 25, "Bob" to 30)
        println("Map: $ages")
        
        // Array
        val array: Array<Int> = arrayOf(1, 2, 3, 4, 5)
        println("Array: ${array.contentToString()}")
        
        // Primitive arrays (more efficient)
        val intArray: IntArray = intArrayOf(1, 2, 3, 4, 5)
        println("IntArray: ${intArray.contentToString()}")
        
        println()
    }
    
    /**
     * VAR VS VAL DEMONSTRATION
     */
    fun varVsValExample() {
        println("=== var vs val ===")
        
        // val: immutable (read-only) reference
        val constantValue = 10
        // constantValue = 20  // Compilation error!
        println("val (immutable): $constantValue")
        
        // var: mutable reference
        var variableValue = 10
        variableValue = 20  // OK
        println("var (mutable): $variableValue")
        
        // Best practice: prefer val over var
        println("\nBest practice: Use 'val' by default, 'var' only when needed")
        
        println()
    }
}

/**
 * ============================================================================
 * TYPE HIERARCHY
 * ============================================================================
 * 
 *                    Any
 *                     |
 *        +------------+------------+
 *        |                         |
 *     Number                    Boolean, String, Char
 *        |
 *   +----+----+
 *   |         |
 * Byte    Short, Int, Long, Float, Double
 * 
 * - Any: Root of Kotlin type hierarchy
 * - Nothing: Bottom type (no instances)
 * - Unit: Similar to void in Java
 * 
 * ============================================================================
 * MEMORY SIZES
 * ============================================================================
 * 
 * TYPE     SIZE     RANGE
 * ----     ----     -----
 * Byte     8 bit    -128 to 127
 * Short    16 bit   -32,768 to 32,767
 * Int      32 bit   -2^31 to 2^31-1
 * Long     64 bit   -2^63 to 2^63-1
 * Float    32 bit   1.4E-45 to 3.4E38
 * Double   64 bit   4.9E-324 to 1.8E308
 * Boolean  8 bit    true or false
 * Char     16 bit   0 to 65,535 (Unicode)
 * 
 * ============================================================================
 */

fun main() {
    val demo = DataTypes()
    
    println("=== Kotlin Data Types ===\n")
    
    demo.integerTypesExample()
    demo.floatingPointTypesExample()
    demo.booleanTypeExample()
    demo.characterTypeExample()
    demo.stringTypeExample()
    demo.typeInferenceExample()
    demo.nullableTypesExample()
    demo.typeConversionExample()
    demo.collectionsTypesExample()
    demo.varVsValExample()
    
    println("=== Key Takeaways ===")
    println("✓ Kotlin has no primitive types - everything is an object")
    println("✓ Type inference makes code concise")
    println("✓ Nullable types (?") provide null safety")
    println("✓ Use val by default, var only when needed")
    println("✓ Explicit type conversion required (no implicit widening)")
    println("✓ Rich standard library for type operations")
}
