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

| Name       | Type Class      | `get_type_signature()` Format          |
| ---------- | --------------- | -------------------------------------- |
| Primitives | `PrimitiveType` | `int`, `float`, `bool`, `str`          |
| Tuple      | `TupleType`     | `(type1, type2, ...)`                  |
| Function   | `FunctionType`  | `(param1, param2, ...) -> return_type` |
| Generics   | `TypeVar`       | `T`, `U`, `V`, ...                     |

You should make the tests pass. To run the tests, run `mvn test -Dtest=TypeSignaturesTest`.

### Part 2: Type Checking

Implement `type_check(function_type, argument_types)` that validates whether provided arguments match a function's parameter types.

#### Example

```python
# Function: (int, str) -> bool
function_type = construct_function_type(
    [construct_primitive_type("int"), construct_primitive_type("str")], construct_primitive_type("bool")
)
# Arguments: (int, str)
argument_types = [construct_primitive_type("int"), construct_primitive_type("str")]
assert type_check(function_type, argument_types) == True
```

```python
# Function: (int, str) -> bool
function_type = construct_function_type(
    [construct_primitive_type("int"), construct_primitive_type("str")], construct_primitive_type("bool")
)
# Arguments: (int, int)
argument_types = [construct_primitive_type("int"), construct_primitive_type("int")]
assert type_check(function_type, argument_types) == False
```

You should make the tests pass. To run the tests, run `mvn test -Dtest=TypeCheckingTest`.

### Part 3: Type Inference

Implement `infer_return_type(function_type, argument_types)` that type checks the arguments and infers concrete types for any generic type variables.
Return the properly instantiated return type or `None` if type inference is not possible.

#### Example

```python
# Function: (int) -> int
function_type = construct_function_type([construct_primitive_type("int")], construct_primitive_type("int"))
# Arguments: (int)
argument_types = [construct_primitive_type("int")]
# Inferred Return Type: int
assert infer_return_type(function_type, argument_types).get_type_signature() == "int"
```

You should make the tests pass. To run the tests, run `mvn test -Dtest=TypeInferenceTest`.