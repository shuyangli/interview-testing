package com.tensorzero.typeinference;

import java.util.List;

/**
 * PART 1 - TYPE SYSTEM DESIGN & SIGNATURES
 *
 * Factory class for constructing type nodes.
 */
public class TypeFactory {

    /**
     * Construct a primitive type with the given name.
     *
     * @param name the name of the primitive type (e.g., "int", "str", "bool")
     * @return a new PrimitiveType instance
     */
    public static PrimitiveType constructPrimitiveType(String name) {
        throw new UnsupportedOperationException("Not implemented yet"); // TODO
    }

    /**
     * Construct a tuple type containing the given types.
     *
     * @param types the list of types in the tuple
     * @return a new TupleType instance
     */
    public static TupleType constructTupleType(List<TypeNode> types) {
        throw new UnsupportedOperationException("Not implemented yet"); // TODO
    }

    /**
     * Construct a function type with parameter types and a return type.
     *
     * @param paramTypes the list of parameter types
     * @param returnType the return type
     * @return a new FunctionType instance
     */
    public static FunctionType constructFunctionType(List<TypeNode> paramTypes, TypeNode returnType) {
        throw new UnsupportedOperationException("Not implemented yet"); // TODO
    }

    /**
     * Construct a type variable with the given name.
     *
     * @param name the name of the type variable (e.g., "T", "U", "V")
     * @return a new TypeVar instance
     */
    public static TypeVar constructTypeVar(String name) {
        throw new UnsupportedOperationException("Not implemented yet"); // TODO
    }
}
