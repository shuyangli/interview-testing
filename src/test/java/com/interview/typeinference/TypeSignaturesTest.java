package com.interview.typeinference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.interview.typeinference.TypeFactory.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test suite for type signature tests (Part 1)
 */
@Tag("type_signatures")
public class TypeSignaturesTest {

    @Test
    @Tag("type_signatures")
    @DisplayName("Test primitive type signatures")
    public void testPrimitiveTypeSignatures() {
        PrimitiveType intType = constructPrimitiveType("int");
        assertEquals("int", intType.getTypeSignature());

        PrimitiveType strType = constructPrimitiveType("str");
        assertEquals("str", strType.getTypeSignature());

        PrimitiveType floatType = constructPrimitiveType("float");
        assertEquals("float", floatType.getTypeSignature());

        PrimitiveType boolType = constructPrimitiveType("bool");
        assertEquals("bool", boolType.getTypeSignature());
    }

    @Test
    @Tag("type_signatures")
    @DisplayName("Test type variable signatures")
    public void testTypeVariableSignatures() {
        TypeVar singleCharacterTypeVar = constructTypeVar("T");
        assertEquals("T", singleCharacterTypeVar.getTypeSignature());

        TypeVar multipleCharacterTypeVar = constructTypeVar("ABC");
        assertEquals("ABC", multipleCharacterTypeVar.getTypeSignature());
    }

    @Test
    @Tag("type_signatures")
    @DisplayName("Test tuple type signatures")
    public void testTupleTypeSignatures() {
        TupleType tupleType = constructTupleType(
            List.of(
                constructPrimitiveType("int"),
                constructPrimitiveType("str"),
                constructPrimitiveType("int")
            )
        );
        assertEquals("(int, str, int)", tupleType.getTypeSignature());

        TupleType nestedTupleType = constructTupleType(
            List.of(
                constructTypeVar("T"),
                constructTupleType(
                    List.of(
                        constructPrimitiveType("int"),
                        constructTypeVar("T")
                    )
                )
            )
        );
        assertEquals("(T, (int, T))", nestedTupleType.getTypeSignature());
    }

    @Test
    @Tag("type_signatures")
    @DisplayName("Test function type signature")
    public void testFunctionTypeSignature() {
        List<TypeNode> paramTypes = List.of(
            constructTypeVar("T"),
            constructTypeVar("T")
        );
        TypeNode returnType = constructTypeVar("T");
        FunctionType functionType = constructFunctionType(paramTypes, returnType);

        assertEquals("(T, T) -> T", functionType.getTypeSignature());
    }

    @Test
    @Tag("type_signatures")
    @DisplayName("Test construct primitive type rejects invalid")
    public void testConstructPrimitiveTypeRejectsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            constructPrimitiveType("unknown");
        });
    }
}
