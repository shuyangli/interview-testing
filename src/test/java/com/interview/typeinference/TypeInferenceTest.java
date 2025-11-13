package com.interview.typeinference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.interview.typeinference.TypeFactory.*;
import static com.interview.typeinference.TypeInference.inferReturnType;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test suite for type inference tests (Part 3)
 */
@Tag("type_inference")
public class TypeInferenceTest {

    /**
     * Helper method to check if return type matches expected signature.
     *
     * @param returnType the return type to check (may be null)
     * @param signature the expected signature string
     * @return true if returnType is non-null and matches the signature
     */
    private boolean returnTypeMatchesSignature(TypeNode returnType, String signature) {
        return returnType != null && returnType.getTypeSignature().equals(signature);
    }

    @Test
    @Tag("type_inference")
    @DisplayName("Test infer return type concrete types")
    public void testInferReturnTypeConcreteTypes() {
        FunctionType functionType = constructFunctionType(
            List.of(constructPrimitiveType("int")),
            constructPrimitiveType("int")
        );
        List<TypeNode> argumentTypes = List.of(constructPrimitiveType("int"));

        assertTrue(returnTypeMatchesSignature(
            inferReturnType(functionType, argumentTypes),
            "int"
        ));

        functionType = constructFunctionType(
            List.of(constructPrimitiveType("int")),
            constructPrimitiveType("str")
        );
        argumentTypes = List.of(constructPrimitiveType("int"));

        assertTrue(returnTypeMatchesSignature(
            inferReturnType(functionType, argumentTypes),
            "str"
        ));
    }

    @Test
    @Tag("type_inference")
    @DisplayName("Test infer return type returns null when argument types don't match")
    public void testInferReturnTypeReturnsNullWhenArgumentTypesDontMatch() {
        FunctionType functionType = constructFunctionType(
            List.of(constructPrimitiveType("int")),
            constructPrimitiveType("int")
        );
        List<TypeNode> argumentTypes = List.of(constructPrimitiveType("str"));

        assertNull(inferReturnType(functionType, argumentTypes));
    }

    @Test
    @Tag("type_inference")
    @DisplayName("Test infer return type handles type vars")
    public void testInferReturnTypeHandlesTypeVars() {
        FunctionType functionType = constructFunctionType(
            List.of(constructTypeVar("T"), constructTypeVar("T")),
            constructTypeVar("T")
        );

        List<TypeNode> intArgumentTypes = List.of(
            constructPrimitiveType("int"),
            constructPrimitiveType("int")
        );
        List<TypeNode> floatArgumentTypes = List.of(
            constructPrimitiveType("float"),
            constructPrimitiveType("float")
        );
        List<TypeNode> mixedArgumentTypes = List.of(
            constructPrimitiveType("int"),
            constructPrimitiveType("str")
        );

        assertTrue(returnTypeMatchesSignature(
            inferReturnType(functionType, intArgumentTypes),
            "int"
        ));
        assertTrue(returnTypeMatchesSignature(
            inferReturnType(functionType, floatArgumentTypes),
            "float"
        ));
        assertNull(inferReturnType(functionType, mixedArgumentTypes));
    }

    @Test
    @Tag("type_inference")
    @DisplayName("Test infer return type handles tuples")
    public void testInferReturnTypeHandlesTuples() {
        FunctionType functionType = constructFunctionType(
            List.of(
                constructTypeVar("T"),
                constructTupleType(List.of(constructTypeVar("T"), constructTypeVar("U")))
            ),
            constructTupleType(List.of(constructTypeVar("U"), constructTypeVar("T")))
        );

        List<TypeNode> matchingArgumentTypes = List.of(
            constructPrimitiveType("int"),
            constructTupleType(List.of(
                constructPrimitiveType("int"),
                constructPrimitiveType("str")
            ))
        );

        List<TypeNode> failingArgumentTypes = List.of(
            constructPrimitiveType("int"),
            constructTupleType(List.of(
                constructPrimitiveType("str"),
                constructPrimitiveType("str")
            ))
        );

        assertTrue(returnTypeMatchesSignature(
            inferReturnType(functionType, matchingArgumentTypes),
            "(str, int)"
        ));
        assertNull(inferReturnType(functionType, failingArgumentTypes));
    }

    @Test
    @Tag("type_inference")
    @DisplayName("Test infer return type with nested tuples")
    public void testInferReturnTypeWithNestedTuples() {
        FunctionType functionType = constructFunctionType(
            List.of(
                constructTupleType(List.of(
                    constructTypeVar("T"),
                    constructTupleType(List.of(
                        constructTypeVar("T"),
                        constructTypeVar("U")
                    ))
                ))
            ),
            constructTypeVar("U")
        );

        List<TypeNode> argumentTypes = List.of(
            constructTupleType(List.of(
                constructPrimitiveType("int"),
                constructTupleType(List.of(
                    constructPrimitiveType("int"),
                    constructPrimitiveType("float")
                ))
            ))
        );

        assertTrue(returnTypeMatchesSignature(
            inferReturnType(functionType, argumentTypes),
            "float"
        ));
    }
}
