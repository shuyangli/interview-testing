package com.tensorzero.typeinference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tensorzero.typeinference.TypeChecker.typeCheck;
import static com.tensorzero.typeinference.TypeFactory.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test suite for type checking tests (Part 2)
 */
@Tag("type_check")
public class TypeCheckingTest {

    @Test
    @Tag("type_check")
    @DisplayName("Test type check handles concrete types")
    public void testTypeCheckHandlesConcreteTypes() {
        FunctionType functionType = constructFunctionType(
            List.of(constructPrimitiveType("int")),
            constructPrimitiveType("int")
        );

        assertTrue(typeCheck(functionType, List.of(constructPrimitiveType("int"))));
        assertFalse(typeCheck(functionType, List.of(constructPrimitiveType("str"))));
        assertFalse(typeCheck(functionType, List.of(constructPrimitiveType("float"))));
        assertFalse(typeCheck(functionType, List.of(constructPrimitiveType("bool"))));
        assertFalse(typeCheck(functionType, List.of(
            constructPrimitiveType("str"),
            constructPrimitiveType("int")
        )));
    }

    @Test
    @Tag("type_check")
    @DisplayName("Test type check handles multiple param types")
    public void testTypeCheckHandlesMultipleParamTypes() {
        FunctionType functionType = constructFunctionType(
            List.of(
                constructPrimitiveType("float"),
                constructPrimitiveType("bool"),
                constructPrimitiveType("str"),
                constructPrimitiveType("int")
            ),
            constructPrimitiveType("int")
        );

        assertTrue(typeCheck(
            functionType,
            List.of(
                constructPrimitiveType("float"),
                constructPrimitiveType("bool"),
                constructPrimitiveType("str"),
                constructPrimitiveType("int")
            )
        ));

        assertFalse(typeCheck(
            functionType,
            List.of(
                constructPrimitiveType("int"),
                constructPrimitiveType("str"),
                constructPrimitiveType("float"),
                constructPrimitiveType("bool")
            )
        ));

        assertFalse(typeCheck(
            functionType,
            List.of(
                constructPrimitiveType("float"),
                constructPrimitiveType("bool"),
                constructPrimitiveType("str")
            )
        ));

        assertFalse(typeCheck(functionType, List.of(constructPrimitiveType("float"))));
    }

    @Test
    @Tag("type_check")
    @DisplayName("Test type check handles tuple param types")
    public void testTypeCheckHandlesTupleParamTypes() {
        FunctionType functionType = constructFunctionType(
            List.of(constructTupleType(List.of(
                constructPrimitiveType("float"),
                constructPrimitiveType("bool")
            ))),
            constructPrimitiveType("int")
        );

        assertTrue(typeCheck(
            functionType,
            List.of(constructTupleType(List.of(
                constructPrimitiveType("float"),
                constructPrimitiveType("bool")
            )))
        ));

        assertFalse(typeCheck(
            functionType,
            List.of(constructTupleType(List.of(
                constructPrimitiveType("int"),
                constructPrimitiveType("str")
            )))
        ));

        assertFalse(typeCheck(
            functionType,
            List.of(
                constructPrimitiveType("float"),
                constructPrimitiveType("bool"),
                constructPrimitiveType("str")
            )
        ));

        assertFalse(typeCheck(
            functionType,
            List.of(
                constructPrimitiveType("float"),
                constructPrimitiveType("bool")
            )
        ));
    }

    @Test
    @Tag("type_check")
    @DisplayName("Test type check handles nested tuple param types")
    public void testTypeCheckHandlesNestedTupleParamTypes() {
        FunctionType functionType = constructFunctionType(
            List.of(
                constructTupleType(List.of(
                    constructPrimitiveType("float"),
                    constructTupleType(List.of(
                        constructPrimitiveType("bool"),
                        constructPrimitiveType("str")
                    ))
                ))
            ),
            constructPrimitiveType("int")
        );

        assertTrue(typeCheck(
            functionType,
            List.of(
                constructTupleType(List.of(
                    constructPrimitiveType("float"),
                    constructTupleType(List.of(
                        constructPrimitiveType("bool"),
                        constructPrimitiveType("str")
                    ))
                ))
            )
        ));

        assertFalse(typeCheck(
            functionType,
            List.of(
                constructTupleType(List.of(
                    constructPrimitiveType("int"),
                    constructTupleType(List.of(
                        constructPrimitiveType("bool"),
                        constructPrimitiveType("str")
                    ))
                ))
            )
        ));
    }

    @Test
    @Tag("type_check")
    @DisplayName("Test type check handles type var param types")
    public void testTypeCheckHandlesTypeVarParamTypes() {
        FunctionType functionType = constructFunctionType(
            List.of(constructTypeVar("T"), constructTypeVar("U")),
            constructPrimitiveType("int")
        );

        assertTrue(typeCheck(
            functionType,
            List.of(constructPrimitiveType("int"), constructPrimitiveType("float"))
        ));

        assertTrue(typeCheck(
            functionType,
            List.of(constructPrimitiveType("int"), constructPrimitiveType("int"))
        ));

        assertTrue(typeCheck(
            functionType,
            List.of(constructPrimitiveType("bool"), constructPrimitiveType("str"))
        ));
    }

    @Test
    @Tag("type_check")
    @DisplayName("Test type check binds type vars consistently")
    public void testTypeCheckBindsTypeVarsConsistently() {
        FunctionType functionType = constructFunctionType(
            List.of(constructTypeVar("T"), constructTypeVar("T")),
            constructPrimitiveType("int")
        );

        assertTrue(typeCheck(
            functionType,
            List.of(constructPrimitiveType("int"), constructPrimitiveType("int"))
        ));

        assertTrue(typeCheck(
            functionType,
            List.of(constructPrimitiveType("float"), constructPrimitiveType("float"))
        ));

        assertFalse(typeCheck(
            functionType,
            List.of(constructPrimitiveType("int"), constructPrimitiveType("float"))
        ));

        assertFalse(typeCheck(
            functionType,
            List.of(constructPrimitiveType("bool"), constructPrimitiveType("str"))
        ));
    }

    @Test
    @Tag("type_check")
    @DisplayName("Test type check also handles type vars symmetrically")
    public void testTypeCheckAlsoHandlesTypeVarsSymmetrically() {
        FunctionType functionType = constructFunctionType(
            List.of(
                constructTupleType(List.of(constructTypeVar("T"), constructTypeVar("U"))),
                constructTypeVar("U")
            ),
            constructTypeVar("T")
        );

        List<TypeNode> goodArgs = List.of(
            constructTupleType(List.of(
                constructPrimitiveType("int"),
                constructPrimitiveType("str")
            )),
            constructPrimitiveType("str")
        );

        List<TypeNode> badArgs = List.of(
            constructTupleType(List.of(
                constructPrimitiveType("int"),
                constructPrimitiveType("str")
            )),
            constructPrimitiveType("int")
        );

        assertTrue(typeCheck(functionType, goodArgs));
        assertFalse(typeCheck(functionType, badArgs));
    }
}
