package com.tensorzero.typeinference;

import java.util.List;

/**
 * PART 3 - TYPE INFERENCE
 *
 * Provides functionality for inferring return types.
 */
public class TypeInference {

    /**
     * Infer the return type of a function given its type signature and argument types.
     *
     * This method should infer type variables based on the provided argument types
     * and return the inferred return type. If inference is not possible or the
     * arguments are incompatible, return null.
     *
     * @param functionType the function type with possibly generic type variables
     * @param argumentTypes the list of concrete argument types
     * @return the inferred return type, or null if inference fails
     */
    public static TypeNode inferReturnType(FunctionType functionType, List<TypeNode> argumentTypes) {
        throw new UnsupportedOperationException("Not implemented yet"); // TODO
    }
}
