# Interview: Type Inference (Java)

## Context

Imagine you are working for a company that is building a programming language with a static type system.

A senior engineer has set up the skeleton for a type system that needs to handle primitives, tuples, functions, and type variables (generics).

Let's take it step by step. Don't overthink it.

## Task

**Implement a simple type system with type checking and type inference capabilities.**

This interview is divided into three progressive parts:

### Part 1: Type System Design & Signatures

Implement the four core type classes and their constructor functions:

| Name       | Type Class      | `getTypeSignature()` Format          |
| ---------- | --------------- | -------------------------------------- |
| Primitives | `PrimitiveType` | `int`, `float`, `bool`, `str`          |
| Tuple      | `TupleType`     | `(type1, type2, ...)`                  |
| Function   | `FunctionType`  | `(param1, param2, ...) -> return_type` |
| Generics   | `TypeVar`       | `T`, `U`, `V`, ...                     |

To run the tests, run `mvn test -Dtest=TypeSignaturesTest`.

### Part 2: Type Checking

Implement `TypeChecker.typeCheck(functionType, argumentTypes)` that validates whether provided arguments match a function's parameter types.

#### Example

```java
// Function: (int, str) -> bool
FunctionType functionType = constructFunctionType(
    List.of(constructPrimitiveType("int"), constructPrimitiveType("str")),
    constructPrimitiveType("bool")
);
// Arguments: (int, str)
List<TypeNode> argumentTypes = List.of(constructPrimitiveType("int"), constructPrimitiveType("str"));
assertTrue(typeCheck(functionType, argumentTypes));
```

```java
// Function: (int, str) -> bool
FunctionType functionType = constructFunctionType(
    List.of(constructPrimitiveType("int"), constructPrimitiveType("str")),
    constructPrimitiveType("bool")
);
// Arguments: (int, int)
List<TypeNode> argumentTypes = List.of(constructPrimitiveType("int"), constructPrimitiveType("int"));
assertFalse(typeCheck(functionType, argumentTypes));
```

You should make the tests pass. To run the tests, run `mvn test -Dtest=TypeCheckingTest`.

### Part 3: Type Inference

Implement `TypeInference.inferReturnType(functionType, argumentTypes)` that type checks the arguments and infers concrete types for any generic type variables.
Return the properly instantiated return type or `null` if type inference is not possible.

#### Example

```java
// Function: (int) -> int
FunctionType functionType = constructFunctionType(
    List.of(constructPrimitiveType("int")),
    constructPrimitiveType("int")
);
// Arguments: (int)
List<TypeNode> argumentTypes = List.of(constructPrimitiveType("int"));
// Inferred Return Type: int
assertEquals("int", inferReturnType(functionType, argumentTypes).getTypeSignature());
```

You should make the tests pass. To run the tests, run `mvn test -Dtest=TypeInferenceTest`.